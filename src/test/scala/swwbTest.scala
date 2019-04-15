package scala

import chisel3._
import chisel3.iotesters.{SteppedHWIOTester, ChiselFlatSpec}

class SwwbTest extends SteppedHWIOTester {
  val device_under_test = Module( new Swwb() )
  val swwb = device_under_test
  enable_all_debug = true

  poke(swwb.io.error, 1) 
  poke(swwb.io.crash, 0)
  step(1)
  expect(swwb.io.sel, 1)
}

class SwwbTester extends ChiselFlatSpec {
  "Swwb" should "output 1" in {
    assertTesterPasses{ new SwwbTest }
  }
}