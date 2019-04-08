// Unit tests for the Anhyzer core
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Anhyzer core

class CoreUnitTester(c: Anhyzer) extends PeekPokeTester(c) {
   private val core = c
   val rf = core.regFile
   val im = core.instrMem 
   
   poke(rf.io.writeReg, 1)
   poke(rf.io.writeData, 100)
   poke(rf.io.regWrite, true)
   step(1)
    

    im.memory(0) := "b00000000000100010000001110110011".U  // add x1 x2 x7
    im.memory(1) := "b00000000000100010000010000110011".U  // add x1 x2 x8


    for (i <- 0 to 1) {
        if(i == 0) {
            expect(rf.io.readData1, 101)
            expect(rf.io.readData2, 102)
            expect(core.control.io.memToReg, 1)
        }
        else {
            expect(rf.io.readData1, 101)
            expect(rf.io.readData2, 102)
            expect(core.control.io.memToReg, 1)
        }
        step(1)
    }
}

class CoreTester extends ChiselFlatSpec {
  "Anhyzer" should s"match expectations for each value" in {
    Driver(() => new Anhyzer) {
      c => new CoreUnitTester(c)
    } should be (true)
  }
}