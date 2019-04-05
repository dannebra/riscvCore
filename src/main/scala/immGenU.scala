package scala

import chisel3._
import chisel3.util._

class ImmGenU extends Module {
  val io = IO(new Bundle {
    val in  = Input(UInt(20.W)) // [31:12]
    val out = Output(UInt(32.W)) 
  })

  val extended = Cat(io.in, Fill(12, 0.U))
  io.out := extended
}