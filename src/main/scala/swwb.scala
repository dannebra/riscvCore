package scala

import chisel3._
import chisel3.util._

// The Something Went Wrong Box
// This is the Anhyzer core's error handler.

class Swwb extends Module {
  val io = IO(new Bundle {
    val error        = Input(Bool())
    val crash        = Input(Bool())
    val sel          = Output(Bool())
  })

    io.sel := 0.U
    when(io.crash) {
        assert(io.error =/= 1.U)
    }
    when(io.error) {
        io.sel := 1.U
    }
  printf("SWWB: error: %d, crash?: %d, select: %d\n", io.error, io.crash, io.sel)
}