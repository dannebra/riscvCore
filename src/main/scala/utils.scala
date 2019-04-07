package scala

import chisel3._
import chisel3.util._

class Adder extends Module {
  val io = IO(new Bundle {
    val in1               = Input(UInt(32.W))
    val in2               = Input(UInt(32.W))
    val result            = Output(UInt(32.W))
  })

    io.result := io.in1 + io.in2
  }

class PcSelect extends Module {
    val io = IO(new Bundle {
        val pc_plus4 = Input(UInt(32.W))
        val branch   = Input(UInt(32.W))
        val jump     = Input(UInt(32.W))
        val jalr     = Input(UInt(32.W))
        val output   = Output(UInt(32.W))

        val branch_signal = Input(Bool())
        val jump_signal = Input(Bool())
        val jalr_signal = Input(Bool())
    })

    when(io.branch_signal) {
        io.output := io.branch
    }
    .elsewhen(io.jump_signal) {
        io.output := io.jump
    }
    .elsewhen(io.jalr_signal) {
        io.output := io.jalr
    }
    .otherwise {io.output := io.pc_plus4}
}