package scala

import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val in1        = Input(UInt(32.W))
    val in2        = Input(UInt(32.W))
    val aluop      = Input(UInt(4.W))
    val result     = Output(UInt(32.W))
    val zero       = Output(Bool())
  })

io.result := 0.U 
val shamt = io.in2(24, 20).asUInt

switch(io.aluop) {
  is(0.U) { io.result := io.in1 & io.in2 } // AND
  is(1.U) { io.result := io.in1 | io.in2 } // OR
  is(2.U) { io.result := io.in1 + io.in2 } // ADD
  is(3.U) { io.result := io.in1 << shamt } // SLL
  is(4.U) { io.result := io.in1.asSInt < io.in2.asSInt } // SLT
  is(5.U) { io.result := io.in1 ^ io.in2 } // XOR
  is(6.U) { io.result := io.in1 - io.in2 } // SUB
  is(7.U) { io.result := io.in1 >> shamt } // SRL
  is(8.U) { io.result := io.in1 < io.in2} // SLTU
  is(9.U) { io.result := (io.in1.asSInt >> shamt).asUInt } // SRA
  }

io.zero := io.result === 0.U

}