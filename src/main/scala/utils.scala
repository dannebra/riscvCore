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
        val pcPlus4  = Input(UInt(32.W))
        val branch   = Input(UInt(32.W))
        val jump     = Input(UInt(32.W))
        val jalr     = Input(UInt(32.W))
        val output   = Output(UInt(32.W))

        val branchSignal = Input(Bool())
        val jumpSignal = Input(Bool())
        val jalrSignal = Input(Bool())
    })

    when(io.branchSignal) {
        io.output := io.branch
    }
    .elsewhen(io.jumpSignal) {
        io.output := io.jump
    }
    .elsewhen(io.jalrSignal) {
        io.output := io.jalr
    }
    .otherwise {io.output := io.pcPlus4}
}