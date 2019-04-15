package scala

import chisel3._
import chisel3.util._

// The Something Went Wrong Box

class Swwb extends Module {
  val io = IO(new Bundle {
    val error        = Input(Bool())
    val crash        = Input(Bool())
    val output       = Output(Bool())
  })

    io.output := 0.U
    when(io.crash) {
        assert(io.error =/= 1.U)
    }
    when(io.error) {
        io.output := 1.U
    }
}