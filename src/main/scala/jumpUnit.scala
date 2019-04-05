package scala

import chisel3._
import chisel3.util._

class JumpUnit extends Module {
  val io = IO(new Bundle {
    val pc               = Input(UInt(32.W))
    val instr            = Input(UInt(32.W))
    val jumpTarget       = Output(UInt(32.W))
  })

    val sign = io.instr(31)
    val offset = Cat(Fill(11, sign), io.instr(19, 12), io.instr(20), io.instr(30, 25), io.instr(24, 21), 0.U)
    io.jumpTarget := io.pc + offset
  }
