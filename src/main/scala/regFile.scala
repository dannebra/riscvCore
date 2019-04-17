package scala

import chisel3._
import chisel3.util._

// The register file, consisting of 32 32-bit registers.

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

    when(io.regWrite && io.writeReg =/= 0.U) { registers.write(io.writeReg, io.writeData) } // Ignore writes to x0
    
    
    printf("Register file: read1: %d, read2: %d, writeReg: %d, writeData: %d, out1: %d, out2: %d, regWrite: %d\n", 
          io.readReg1, io.readReg2, io.writeReg, io.writeData, io.readData1, io.readData2, io.regWrite)
    printf("Registers:\n")
    for(i <- 0 until 32) {
      if (i % 8 == 0) {
        printf("\n")
      }
      printf("x%d: %d\n", i.U, registers(i))
    }
  printf("\n")
}
    