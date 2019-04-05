package scala

import chisel3._
import chisel3.util._

class ImmGenI extends Module {
  val io = IO(new Bundle {
    val instr  = Input(UInt(32.W))
    val out    = Output(UInt(32.W)) 
  })

  val sign = io.instr(31)
  val extended = Cat(Fill(20, sign), io.instr(31, 20))
  io.out := extended
}