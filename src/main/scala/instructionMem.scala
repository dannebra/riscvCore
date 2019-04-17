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

  memory.write(0.U, "b00000000001100010000001110010011".U) // add x1 3 x7
  memory.write(1.U, "b00000000010100010000010000010011".U)  // add x1 5 x8
  //loadMemoryFromFile(memory, file)
  io.instruction := memory(io.address >> 2)
  printf("INSTRUCTION OUTPUTED; %d\n", io.instruction)
}