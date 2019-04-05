package scala

import chisel3._
import chisel3.util._

class ImmGenS extends Module {
  val io = IO(new Bundle {
    val inUpper  = Input(UInt(7.W)) // [31:25]
    val inLower = Input(UInt(5.W)) // [11:7]
    val out = Output(UInt(32.W)) 
  })

  val sign = io.inUpper(6)
  val offset = Cat(io.inUpper, io.inLower)
  val extended = Cat(Fill(20, sign), offset)
  io.out := extended
}