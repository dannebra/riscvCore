package scala

import chisel3._
import chisel3.util._

class InstructionMemory extends Module {
  val io = IO(new Bundle {
    val readAddress           = Input(UInt(32.W))
    val toControl             = Output(UInt(7.W))
    val readRegFile1          = Output(UInt(5.W))
    val readRegFile2          = Output(UInt(5.W))
    val writeRegFile          = Output(UInt(5.W))
    val immediate             = Output(UInt(12.W))
    val aluControlFunct7      = Output(Bool())
    val aluControlFunct3      = Output(UInt(3.W))
  })
}