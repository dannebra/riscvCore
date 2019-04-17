package scala

import chisel3._
import chisel3.iotesters.{SteppedHWIOTester, ChiselFlatSpec, PeekPokeTester}

class AnhyzerTest extends SteppedHWIOTester {
  val device_under_test = Module( new Anhyzer() )
  val core = device_under_test
  enable_all_debug = true
  printf("\n\n")
  poke(core.io.start, 0)
  step(1)
}

class CoreTester extends ChiselFlatSpec {
  "Core" should "simulate correctly" in {
    assertTesterPasses{ new AnhyzerTest }
  }
}