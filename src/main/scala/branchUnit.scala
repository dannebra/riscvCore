package scala

import chisel3._
import chisel3.util._

class BranchUnit extends Module {
  val io = IO(new Bundle {
    val offset       = Input(UInt(32.W))
    val pc           = Input(UInt(32.W))
    val branchTarget = Output(UInt(32.W))
  })

    io.branchTarget := io.pc + io.offset
}

