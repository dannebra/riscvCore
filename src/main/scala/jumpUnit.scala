package scala

import chisel3._
import chisel3.util._

class JumpUnit extends Module {
  val io = IO(new Bundle {
    val pc               = Input(UInt(32.W))
    val offset            = Input(UInt(32.W))
    val jumpTarget       = Output(UInt(32.W))
  })

    io.jumpTarget := io.pc + io.offset
  }
