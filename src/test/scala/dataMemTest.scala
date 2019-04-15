// Unit test for the Data memory
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Data memory

class DMUnitTester(c: DataMemory) extends PeekPokeTester(c) {
   private val dmem = c
   // Write i to data memory at address 0 - 10
   for (i <- 0 until 40 by 4) {
    poke(dmem.io.readAddress, i) 
    poke(dmem.io.writeData, i)
    poke(dmem.io.memWrite, 1)
    poke(dmem.io.memRead, 0)
    step(1)
   }

   // Read from address 0 - 10
   for (j <- 0 until 40 by 4) {
    poke(dmem.io.readAddress, j)
    poke(dmem.io.memRead, 1)
    poke(dmem.io.memWrite, 0)
    poke(dmem.io.writeData, 0)
    step(1)
    expect(dmem.io.readDataOutput, j)
  }
}

class DmemControlTester extends ChiselFlatSpec {
  "Data memory" should s"match expectations for each intruction type" in {
    Driver(() => new DataMemory) {
      c => new DMUnitTester(c)
    } should be (true)
  }
}