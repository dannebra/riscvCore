package scala

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import firrtl.annotations.MemoryLoadFileType


class InstructionMemory() extends Module { // Should take String file as input
  val io = IO(new Bundle {
    val address           = Input(UInt(32.W))
    val instruction       = Output(UInt(32.W))
  })

  val memory = Mem(1024, UInt(32.W))

  memory.write(0.U, "b00000000000100010000001110110011".U) // add x1 x2 x7
  memory.write(1.U, "b00000000000100010000010000110011".U)  // add x1 x2 x8
  //loadMemoryFromFile(memory, file)
  io.instruction := memory(io.address >> 2)
}