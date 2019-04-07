package scala

import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val in1        = Input(UInt(32.W))
    val in2        = Input(UInt(32.W))
    val aluOp      = Input(UInt(5.W))
    val result     = Output(UInt(32.W))
  })

io.result := 0.U 
val shamt = io.in2(4, 0).asUInt // shift amount is encoded in the lower 5 bits

switch(io.aluOp) {
  is("b00000".U) { io.result := io.in1 & io.in2 } // AND
  is("b00001".U) { io.result := io.in1 | io.in2 } // OR/ORI
  is("b00010".U) { io.result := io.in1 + io.in2 } // ADD/ADDI
  is("b00011".U) { io.result := io.in1 << shamt } // SLL/SLLI
  is("b00100".U) { io.result := io.in1.asSInt < io.in2.asSInt } // SLT/SLTI
  is("b00101".U) { io.result := io.in1 ^ io.in2 } // XOR/XORI
  is("b00110".U) { io.result := io.in1 - io.in2 } // SUB
  is("b00111".U) { io.result := io.in1 >> shamt } // SRL/SRLI
  is("b01000".U) { io.result := io.in1 < io.in2} // SLTU/SLTIU
  is("b01001".U) { io.result := (io.in1.asSInt >> shamt).asUInt } // SRA/SRAI
  is("b01010".U){ io.result := io.in1} // LUI
  }
}