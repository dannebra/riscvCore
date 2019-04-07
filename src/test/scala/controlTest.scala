// Unit tests for the main control logic

package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Control unit

class ControlUnitTester(c: Control) extends PeekPokeTester(c) {
  private val ctl = c

  val tests = List(
    // opcode    memToReg,  regWrite, memRead, memWrite, writeSrc, aluOp,   aluSrc1, aluSrc2, branch, jumpReg,  jump,   Type
    ( "b0110011".U,  0.U,   1.U,      0.U,     0.U,      0.U,      "b010".U,  0.U,      0.U,   0.U,      0.U,   0.U),  //"R-type"
    ( "b0000011".U,  1.U,   1.U,      1.U,     0.U,      0.U,      "b000".U,  0.U,      1.U,   0.U,      0.U,   0.U),  //"Load"
    ( "b0100011".U,  0.U,   0.U,      0.U,     1.U,      0.U,      "b000".U,  0.U,      2.U,   0.U,      0.U,   0.U),  //"Store"
    ( "b1100011".U,  0.U,   0.U,      0.U,     0.U,      0.U,      "b000".U,  0.U,      0.U,   1.U,      0.U,   0.U),  //"Branch"
    ( "b1100111".U,  0.U,   0.U,      0.U,     0.U,      1.U,      "b000".U,  0.U,      0.U,   0.U,      1.U,   0.U),  //"JALR"
    ( "b1101111".U,  0.U,   0.U,      0.U,     0.U,      1.U,      "b000".U,  0.U,      0.U,   0.U,      0.U,   1.U),  //"Jump"
    ( "b0010011".U,  0.U,   1.U,      0.U,     0.U,      0.U,      "b011".U,  0.U,      1.U,   0.U,      0.U,   0.U),  //"I-type"
    ( "b0110111".U,  0.U,   1.U,      0.U,     0.U,      0.U,      "b111".U,  1.U,      0.U,   0.U,      0.U,   0.U),  //"LUI"
    ( "b0010111".U,  0.U,   1.U,      0.U,     0.U,      0.U,      "b101".U,  1.U,      3.U,   0.U,      0.U,   0.U)   //"AUIPC"
  )

  for (t <- tests) {
    poke(ctl.io.opcode, t._1)
    step(1)
    expect(ctl.io.memToReg, t._2)
    expect(ctl.io.regWrite, t._3)
    expect(ctl.io.memRead, t._4)
    expect(ctl.io.memWrite, t._5)
    expect(ctl.io.writeSrc, t._6)
    expect(ctl.io.aluOp, t._7)
    expect(ctl.io.aluSrc1, t._8)
    expect(ctl.io.aluSrc2, t._9)
    expect(ctl.io.branch, t._10)
    expect(ctl.io.jumpReg, t._11)
    expect(ctl.io.jump, t._12)
    
  }
}

class ControlTester extends ChiselFlatSpec {
  "Control" should s"match expectations for each intruction type" in {
    Driver(() => new Control) {
      c => new ControlUnitTester(c)
    } should be (true)
  }
}