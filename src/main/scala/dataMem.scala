package scala

import chisel3._
import chisel3.util._

class DataMemory extends Module {
  val io = IO(new Bundle {
    val readAddress             = Input(UInt(32.W))
    val writeData               = Input(UInt(32.W))
    val memWrite                = Input(Bool())
    val memRead                 = Input(Bool())
    val readDataOutput          = Output(UInt(32.W))
  })

  val memory = Mem(1024, UInt(32.W))
  io.readDataOutput := 0.U
  when ( io.memRead )  { io.readDataOutput := memory(io.readAddress) }
  when ( io.memWrite ) { memory(io.readAddress) := io.writeData }
}