package scala

import chisel3._
import chisel3.util._

class Control extends Module {
  val io = IO(new Bundle {
    val opcode       = Input(UInt(7.W))
    val aluSrc       = Output(Bool())
    val memToReg     = Output(Bool())
    val branch       = Output(Bool())
    val regWrite     = Output(Bool())
    val memRead      = Output(Bool())
    val memWrite     = Output(Bool())
    val aluOp        = Output(UInt(3.W))
  })

io.aluSrc := 0.U
io.memToReg := 0.U
io.regWrite := 0.U
io.memRead := 0.U
io.memWrite := 0.U
io.branch := 0.U
io.aluOp := 0.U

  switch(io.opcode) {
      is("b0010011".U) { // R-type
        io.regWrite := 1.U
        io.aluOp := "b010".U
      }
      is("b0000011".U) { // Load
        io.aluSrc := 1.U
        io.memToReg := 1.U
        io.regWrite := 1.U
        io.memRead := 1.U
        io.aluOp := "b000".U
      }
      is("b0100011".U) { // Store
        io.aluSrc := 1.U
        io.memWrite := 1.U
        io.aluOp := "b000".U
      }
      is("b1100011".U) { // Branch
        io.branch := 1.U
        io.aluOp := "b001".U
      }
       is("b1100111".U) { // Jump/JAL
        io.branch := 1.U
        io.aluOp := "b101".U
      }
      is("b0010011".U) { // I-type
        io.aluSrc := 1.U
        io.regWrite := 1.U
        io.aluOp := "b011".U
        }
  }
}