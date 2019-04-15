// Unit test for the PC-select
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the PC selector

class PCselUnitTester(c: PcSelect) extends PeekPokeTester(c) {
   private val sel = c

   val tests = List(
    // pcPlus4,  branch,  jump,    jalr,    branchSignal, jumpSignal, jalrSignal, output
    ( "b10".U,  "b01".U, "b00".U, "b11".U,    true,        false,      false,     "b01".U),
    ( "b10".U,  "b01".U, "b00".U, "b11".U,    false,       true,       false,     "b00".U),
    ( "b10".U,  "b01".U, "b00".U, "b11".U,    false,       false,      true,      "b11".U),
    ( "b10".U,  "b01".U, "b00".U, "b11".U,    false,       false,      false,     "b10".U)
  )

  for (t <- tests) {
    poke(sel.io.pcPlus4, t._1)
    poke(sel.io.branch, t._2)
    poke(sel.io.jump, t._3)
    poke(sel.io.jalr, t._4)
    poke(sel.io.branchSignal, t._5)
    poke(sel.io.jumpSignal, t._6)
    poke(sel.io.jalrSignal, t._7)
    step(1)
    expect(sel.io.output, t._8)
  }  
}

class PcSelectTester extends ChiselFlatSpec {
  "PC select" should s"match expectations for each signal type" in {
    Driver(() => new PcSelect) {
      c => new PCselUnitTester(c)
    } should be (true)
  }
}