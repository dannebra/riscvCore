package scala

import chisel3._
import chisel3.util._

class JumpReg extends Module {
  val io = IO(new Bundle {
    val reg1       = Input(UInt(32.W))
    val imm        = Input(UInt(32.W))
    val output     = Output(UInt(32.W))
  })
  
  val address = io.reg1 + io.imm
  val jumpTarget = Cat(address(31, 1), 0.U)
  io.output := jumpTarget
}