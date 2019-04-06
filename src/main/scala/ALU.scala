package scala

import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val in1        = Input(UInt(32.W))
    val in2        = Input(UInt(32.W))
    val aluop      = Input(UInt(5.W))
    val result     = Output(UInt(32.W))
    val zero       = Output(Bool())
  })

io.result := 0.U 
io.zero := 0.U
val shamt = io.in2(4, 0).asUInt // shift amount is encoded in the lower 5 bits

switch(io.aluop) {
  is(0.U) { io.result := io.in1 & io.in2 } // AND
  is(1.U) { io.result := io.in1 | io.in2 } // OR/ORI
  is(2.U) { io.result := io.in1 + io.in2 } // ADD/ADDI
  is(3.U) { io.result := io.in1 << shamt } // SLL/SLLI
  is(4.U) { io.result := io.in1.asSInt < io.in2.asSInt } // SLT/SLTI
  is(5.U) { io.result := io.in1 ^ io.in2 } // XOR/XORI
  is(6.U) { io.result := io.in1 - io.in2 } // SUB
  is(7.U) { io.result := io.in1 >> shamt } // SRL/SRLI
  is(8.U) { io.result := io.in1 < io.in2} // SLTU
  is(9.U) { io.result := (io.in1.asSInt >> shamt).asUInt } // SRA
  is(16.U) { io.zero := 1.U } // JAL/JALR
  }
}