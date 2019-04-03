package scala

import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
    val io = IO(new Bundle {
      val counter       = RegInit(0.U(32.W))
    })
}