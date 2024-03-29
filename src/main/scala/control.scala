package scala

import chisel3._
import chisel3.util._

// The main control unit, responsible for outputing the correct control signals.

class Control extends Module {
  val io = IO(new Bundle {
    val opcode       = Input(UInt(7.W))
    val memToReg     = Output(Bool())
    val regWrite     = Output(Bool())
    val memRead      = Output(Bool())
    val memWrite     = Output(Bool())
    val writeSrc     = Output(Bool())
    val aluOp        = Output(UInt(3.W))
    val aluSrc1      = Output(Bool())
    val aluSrc2      = Output(UInt(2.W))
    val branch       = Output(Bool())
    val jumpReg      = Output(Bool())
    val jump         = Output(Bool())
  })


io.memToReg := 0.U // 0 = data from ALU, 1 = data from memory
io.regWrite := 0.U // 0 = do not write to reg, 1 = write data to reg
io.memRead := 0.U // 0 = do not read memory, 1 = read memory
io.memWrite := 0.U // 0 = do not write to memory, 1 = write to memory
io.writeSrc := 0.U // 0 = source to write from ALU/mem, 1 = PC+4
io.aluOp := 1.U // ALU opcodes to ALU control (if 1 is outputed - ERROR)
io.aluSrc1 := 0.U  // 0 = register rs1, 1 = U-type extended
io.aluSrc2 := 0.U // 0 = register rs2, 1 = I-type extended, 2 = S-type extended, 3 = PC
io.branch := 0.U // 0 = no branch, 1 = branch
io.jumpReg := 0.U // input to PC = jumpreg output
io.jump := 0.U // Input to PC = jump output

  switch(io.opcode) {
      is("b0110011".U) { // R-type
        io.regWrite := 1.U
        io.aluOp := "b010".U
      }
      is("b0000011".U) { // Load
        io.aluSrc2 := 1.U
        io.memToReg := 1.U
        io.regWrite := 1.U
        io.memRead := 1.U
        io.aluOp := "b000".U
      }
      is("b0100011".U) { // Store
        io.aluSrc2 := 2.U
        io.memWrite := 1.U
        io.aluOp := "b000".U
      }
      is("b1100011".U) { // Branch
        io.branch := 1.U
      }
      is("b1100111".U) { // JALR
        io.jumpReg := 1.U
        io.writeSrc := 1.U
        io.aluOp := "b000".U // Not used
      }
      is("b1101111".U) { // JAL/Jump
          io.jump := 1.U
          io.writeSrc := 1.U
          io.aluOp := "b000".U // Not used
      }
      is("b0010011".U) { // I-type
        io.aluSrc2 := 1.U
        io.regWrite := 1.U
        io.aluOp := "b011".U
      }
      is("b0110111".U) { // LUI
        io.aluSrc1 := 1.U
        io.regWrite := 1.U
        io.aluOp := "b111".U
      }
      is("b0010111".U) { // AUIPC
        io.aluSrc1 := 1.U
        io.aluSrc2 := 3.U
        io.regWrite := 1.U
        io.aluOp := "b101".U
      }
  }
  printf("Control: Opcode: %d, memtoReg: %d, regWrite: %d, memRead: %d, memWrite: %d, wirteSrc: %d, aluOp: %d, aluSrc1: %d, aluSrc2: %d, branch: %d, jumpReg: %d, jump: %d\n", io.opcode, io.memToReg, io.regWrite, io.memRead, io.memWrite, io.writeSrc, io.aluOp, io.aluSrc1, io.aluSrc2, io.branch, io.jumpReg, io.jump)
}