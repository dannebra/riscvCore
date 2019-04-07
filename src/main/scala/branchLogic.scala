package scala

import chisel3._
import chisel3.util._

class BranchLogic extends Module {
  val io = IO(new Bundle {
    val branch       = Input(Bool())
    val funct3       = Input(UInt(3.W))
    val reg1         = Input(UInt(32.W))
    val reg2         = Input(UInt(32.W))
    val result       = Output(Bool())
  })

    io.result := 0.U 

    switch(io.funct3) {
        is("b000".U) { io.result := io.reg1.asSInt === io.reg2.asSInt & io.branch} // BEQ
        is("b001".U) { io.result := io.reg1.asSInt =/= io.reg2.asSInt & io.branch} // BNEQ
        is("b100".U) { io.result := io.reg1.asSInt < io.reg2.asSInt & io.branch } // BLT
        is("b101".U) { io.result := io.reg1.asSInt >= io.reg2.asSInt & io.branch } // BGE
        is("b110".U) { io.result := io.reg1 < io.reg2 & io.branch} // BLTU
        is("b111".U) { io.result := io.reg1 >= io.reg2 & io.branch} // BGEU
    }
}