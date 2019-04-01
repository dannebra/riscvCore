package scala

import Chisel._
import Chisel.iotesters.{SteppedHWIOTester, ChiselFlatSpec}


class ALUTest extends SteppedHWIOTester {
  val device_under_test = Module( new ALU() )
  val c = device_under_test
  enable_all_debug = true
  
  poke(c.io.in1, 2)
  poke(c.io.in2, 2)
  poke(c.io.aluop, 0)
  expect(c.io.result, 2 & 2)
  step(1)
  poke(c.io.in1, 3)
  poke(c.io.in2, 3)
  poke(c.io.aluop, 5)
  expect(c.io.result, 3 ^ 3)
  step(1)
  poke(c.io.in1, 4)
  poke(c.io.in2, 4)
  poke(c.io.aluop, 2)
  expect(c.io.result, 8)
  step(1)
}

class ALUTester extends ChiselFlatSpec {
  "ALU" should "compile and run without incident" in {
    assertTesterPasses { new ALUTest }
  }
}