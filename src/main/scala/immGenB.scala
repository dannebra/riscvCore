package scala

import chisel3._
import chisel3.util._

class ImmGenB extends Module {
  val io = IO(new Bundle {
    val inUp  = Input(UInt(7.W)) // [31:25]
    val inLow  = Input(UInt(5.W)) // [11:7]
    val out = Output(UInt(32.W)) 
  })

  val sign = io.inUp(6)
  val upperTemp = Fill(20, sign)
  val upper = Cat(upperTemp, io.inLow(0))
  val lowTemp = Cat(io.inUp(5, 0), io.inLow(4, 1))
  val imm = Cat(upper, lowTemp)
  val extended = Cat(imm, 0.U)
  io.out := extended
}