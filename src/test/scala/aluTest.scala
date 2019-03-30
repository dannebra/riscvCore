package scala

import Chisel._
import Chisel.iotesters.{SteppedHWIOTester, ChiselFlatSpec}


class ALUTest extends SteppedHWIOTester {
  val device_under_test = Module( new ALU() )
  val c = device_under_test
  enable_all_debug = true
  
  for (i <- 0 until 10) {
    poke(c.io.in1, i)
    poke(c.io.in2, i)
    poke(c.io.aluop, 0)
    expect(c.io.result, i & i)
    step(1)
  }
}

class ALUTester extends ChiselFlatSpec {
  "ALU" should "compile and run without incident" in {
    assertTesterPasses { new ALUTest }
  }
}