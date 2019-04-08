package scala

import chisel3._

class CoreIO extends Bundle {
  val instrmem = Module(new InstructionMemory())
  val datamem  = Module(new DataMemory())
}