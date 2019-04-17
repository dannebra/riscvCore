package scala

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import firrtl.annotations.MemoryLoadFileType

// This is the instruction memory!

class InstructionMemory() extends Module { // Should take String file as input
  val io = IO(new Bundle {
    val address           = Input(UInt(32.W))
    val instruction       = Output(UInt(32.W))
  })

  val memory = Mem(1024, UInt(32.W))

  memory.write(0.U, "b00000000001100010000001110010011".U) // add x1 3 x7
  memory.write(1.U, "b00000000010100010000010000010011".U)  // add x1 5 x8
  memory.write(2.U, "b0000000_01000_00111_000_01001_0110011".U) // add x7 x8 x9
  memory.write(3.U, "b11111111111111111111111111111111".U) // Nonsense
  //loadMemoryFromFile(memory, file)
  io.instruction := memory(io.address >> 2)
  printf(p"Instruction outputed: ${Binary(io.instruction)}\n")
}