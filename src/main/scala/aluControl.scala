package scala

import chisel3._
import chisel3.util._

class AluControl extends Module {
  val io = IO(new Bundle {
    val aluOp      = Input(UInt(3.W))
    val funct7     = Input(UInt(7.W))
    val funct3     = Input(UInt(3.W))
    val output     = Output(UInt(5.W))
  })

 io.output := 16.U
  switch(io.aluOp) {
      is("b000".U) { io.output := "b00010".U } // Load/store
      is("b010".U) { // R-type
          when(io.funct7 === "b0000000".U) {
                  switch(io.funct3) {
                      is("b000".U) { io.output := "b00010".U } // ADD
                      is("b001".U) { io.output := "b00011".U } // SLL
                      is("b010".U) { io.output := "b00100".U } // SLT
                      is("b011".U) { io.output := "b01000".U } // SLTU
                      is("b100".U) { io.output := "b00101".U } // XOR
                      is("b101".U) { io.output := "b00111".U } // SRL
                      is("b110".U) { io.output := "b00001".U } // OR
                      is("b111".U) { io.output := "b00000".U } // AND
                  }
          }
          .otherwise {
                  switch(io.funct3) {
                      is("b000".U) { io.output := "b00110".U } // SUB
                      is("b101".U) { io.output := "b01001".U } // SRA
                  }
          }
      }
      is("b011".U) { // I-type
          switch(io.funct3) {
              is("b000".U) { io.output := "b00010".U } // ADDI
              is("b010".U) { io.output := "b00100".U } // SLTI
              is("b011".U) { io.output := "b01000".U } // SLTIU
              is("b100".U) { io.output := "b00101".U } // XORI
              is("b110".U) { io.output := "b00001".U } // ORI
              is("b111".U) { io.output := "b00000".U } // ANDI
              is("b001".U) { io.output := "b00011".U } // SLLI
              is("b101".U) {
                  when(io.funct7 === "b0".U) { 
                      io.output := "b00111".U // SRLI
                      }
                      .otherwise {
                          io.output := "b01001".U // SRAI
                      }
                  }      
          }
      }
      is("b111".U) { io.output := "b01010".U} // LUI
      is("b101".U) { io.output := "b00010".U} // AUIPC
  }
}