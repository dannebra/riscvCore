package scala

import chisel3._

// Currently unused.

class CoreIO extends Bundle {
  val instrmem = Module(new InstructionMemory())
  val datamem  = Module(new DataMemory())

  instrmem.io.instruction := DontCare
  instrmem.io.address := DontCare
  datamem.io.readAddress := DontCare
  datamem.io.writeData := DontCare
  datamem.io.memWrite := DontCare
  datamem.io.memRead := DontCare
  datamem.io.readDataOutput := DontCare
}