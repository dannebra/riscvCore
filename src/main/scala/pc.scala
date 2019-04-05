package scala

import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
    val io = IO(new Bundle {
      val counter       = RegInit(0.U(32.W))
      val toAdder       = Output(UInt(32.W))
      val toJump        = Output(UInt(32.W))
      val toBranch      = Output(UInt(32.W))
      val toIMem        = Output(UInt(32.W)) 
    })


}