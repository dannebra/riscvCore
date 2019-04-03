package scala

import chisel3._
import chisel3.util._

class immGen extends Module {
  val io = IO(new Bundle {
    val in  = Input(UInt(12.W))
    val out = Output(UInt(32.W)) 
  })

  val sign = io.in(11)
  val extended = Cat(Fill(20, sign), io.in)
  io.out := extended
}