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
io.zero := 0.U

switch(io.aluop) {
  is(0.U) { io.result := io.in1 & io.in2 } // AND
  is(1.U) { io.result := io.in1 | io.in2 } // OR
  is(2.U) { io.result := io.in1 + io.in2 } // ADD
  is(6.U) { io.result := io.in1 - io.in2 } // SUB
  } 
}