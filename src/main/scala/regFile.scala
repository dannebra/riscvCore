package scala

import chisel3._
import chisel3.util._

class RegFile extends Module {
    val io = IO(new Bundle {
      val readReg1    = Input(UInt(5.W))
      val readReg2    = Input(UInt(5.W))
      val writeReg    = Input(UInt(5.W))
      val writeData   = Input(UInt(32.W))
      val readData1   = Output(UInt(32.W))
      val readData2   = Output(UInt(32.W))
      val regWrite    = Input(Bool())
    })  

    val registers  = Mem(32, UInt(32.W)) // Create 32 registers with width 32
    registers(0)   := 0.U // x0 is always 0
    io.readData1   := registers(io.readReg1)
    io.readData2   := registers(io.readReg2)

    when(io.regWrite && io.writeReg =/= 0.U) { registers(io.writeReg) := io.writeData } // Ignore writes to x0
}
    