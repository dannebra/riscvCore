package scala

import chisel3._
import chisel3.iotesters.{SteppedHWIOTester, ChiselFlatSpec, PeekPokeTester}

// The Anhyzer simulator.

class AnhyzerSimulation extends SteppedHWIOTester {
  val device_under_test = Module( new Anhyzer() )
  val core = device_under_test
  enable_all_debug = true
  poke(core.io.start, 0)
  step(5)
}

class CoreTester extends ChiselFlatSpec {
  "Core" should "simulate correctly" in {
    assertTesterPasses{ new AnhyzerSimulation }
  }
}