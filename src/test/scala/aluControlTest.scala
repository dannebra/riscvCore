// Unit tests for the ALU control logic

package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}


class ALUControlUnitTester(c: AluControl) extends PeekPokeTester(c) {
  private val ctl = c

  // Copied from Patterson and Waterman Figure 2.3
  val tests = List(
    // aluOp       Funct7,       Func3,    Output      Input
    ( "b000".U,  "b0000000".U, "b000".U, "b00010".U, "load/store"),
    ( "b010".U,  "b0000000".U, "b000".U, "b00010".U, "add"),
    ( "b010".U,  "b0100000".U, "b000".U, "b00110".U, "sub"),
    ( "b010".U,  "b0000000".U, "b001".U, "b00011".U, "sll"),
    ( "b010".U,  "b0000000".U, "b010".U, "b00100".U, "slt"),
    ( "b010".U,  "b0000000".U, "b011".U, "b01000".U, "sltu"),
    ( "b010".U,  "b0000000".U, "b100".U, "b00101".U, "xor"),
    ( "b010".U,  "b0000000".U, "b101".U, "b00111".U, "srl"),
    ( "b010".U,  "b0100000".U, "b101".U, "b01001".U, "sra"),
    ( "b010".U,  "b0000000".U, "b110".U, "b00001".U, "or"),
    ( "b010".U,  "b0000000".U, "b111".U, "b00000".U, "and"),
    ( "b011".U,  "b0000000".U, "b000".U, "b00010".U, "addi"),
    ( "b011".U,  "b0000000".U, "b011".U, "b01000".U, "sltiu"),
    ( "b011".U,  "b0000000".U, "b100".U, "b00101".U, "xori"),
    ( "b011".U,  "b0000000".U, "b110".U, "b00001".U, "ori"),
    ( "b011".U,  "b0000000".U, "b111".U, "b00000".U, "andi"),
    ( "b011".U,  "b0000000".U, "b001".U, "b00011".U, "slli"),
    ( "b011".U,  "b0000000".U, "b101".U, "b00111".U, "srli"),
    ( "b011".U,  "b0100000".U, "b101".U, "b01001".U, "srai")
  )

  for (t <- tests) {
    poke(ctl.io.aluOp, t._1)
    poke(ctl.io.funct7, t._2)
    poke(ctl.io.funct3, t._3)
    step(1)
    expect(ctl.io.output, t._4, s"${t._5} wrong")
  }
}

/**
  * This is a trivial example of how to run this Specification
  * From within sbt use:
  * {{{
  * testOnly dinocpu.ALUControlTester
  * }}}
  * From a terminal shell use:
  * {{{
  * sbt 'testOnly dinocpu.ALUControlTester'
  * }}}
  */
class ALUControlTester extends ChiselFlatSpec {
  "ALUControl" should s"match expectations for each intruction type" in {
    Driver(() => new AluControl) {
      c => new ALUControlUnitTester(c)
    } should be (true)
  }
}