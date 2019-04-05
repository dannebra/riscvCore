package scala

import chisel3._
import chisel3.util._

class ImmGenI extends Module {
  val io = IO(new Bundle {
    val imm  = Input(UInt(12.W))
    val out    = Output(UInt(32.W)) 
  })

  val sign = io.imm(11)
  val extended = Cat(Fill(20, sign), io.imm(11, 0))
  io.out := extended
}