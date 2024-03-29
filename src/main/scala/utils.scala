package scala

import chisel3._
import chisel3.util._

// --- These are utility units ----


// Simple adder.

class Adder extends Module {
  val io = IO(new Bundle {
    val in1               = Input(UInt(32.W))
    val in2               = Input(UInt(32.W))
    val result            = Output(UInt(32.W))
  })

    io.result := io.in1 + io.in2
  }

// The unit that decides the input to the program counter.

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

    printf("PC-select: pcPlus4: %d, branch: %d, jump: %d, jalr: %d, brancSignal: %d, jumpSignal: %d, jalrSignal: %d, Output: %d\n",
            io.pcPlus4, io.branch, io.jump, io.jalr, io.branchSignal, io.jumpSignal, io.jalrSignal, io.output)
}