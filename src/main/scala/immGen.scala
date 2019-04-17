package scala

import chisel3._
import chisel3.util._

// The immediate generator, responsible for sign extending the different immediates.

class ImmGen extends Module {
  val io = IO(new Bundle {
    val instr        = Input(UInt(32.W))
    val extendedI    = Output(UInt(32.W))
    val extendedB    = Output(UInt(32.W))
    val extendedS    = Output(UInt(32.W))
    val extendedU    = Output(UInt(32.W))
    val extendedJ    = Output(UInt(32.W))    
  })

    io.extendedI := 0.U
    io.extendedB := 0.U
    io.extendedS := 0.U
    io.extendedU := 0.U
    io.extendedJ := 0.U

    val opcode = io.instr(6, 0)
    val sign = io.instr(31)
    switch(opcode) {
        is("b1100011".U) { // Branch (B-type)
            val imm = Cat(io.instr(7), io.instr(30, 25), io.instr(11, 8), 0.U)
            io.extendedB := Cat(Fill(19, sign), imm)
        }
        is("b0010011".U) { // Immediate arithmetic (I-type)
            val imm = io.instr(31, 20)
            io.extendedI := Cat(Fill(20, sign), imm)
        }
        is("b0000011".U) { // Load (I-type)
            val imm = io.instr(31, 20)
            io.extendedI := Cat(Fill(20, sign), imm)
        }
        is("b1100111".U){ //  jalr (I-type)
            val imm = io.instr(31, 20)
            io.extendedI := Cat(Fill(20, sign), imm)
        }
        is("b0100011".U) { // Store (S-type)
            val imm = Cat(io.instr(31, 25), io.instr(11, 7))
            io.extendedU := Cat(Fill(20, sign), imm)
        }
        is("b0110111".U) { // LUI (U-type)
            val imm = io.instr(31, 12)
            io.extendedU := Cat(imm, Fill(12, 0.U))
        }
        is("b1101111".U) { // jal (J-type)
            io.extendedJ := Cat(Fill(11, sign), io.instr(19, 12), io.instr(20), io.instr(30, 21), 0.U)
        }
    }
    printf("Immediate generator: I-type: %d, B-type: %d, S-type: %d, U-type: %d, J-extended: %d\n", 
                              io.extendedI, io.extendedB, io.extendedS, io.extendedU, io.extendedJ)

  }
