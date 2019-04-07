// Unit tests for the Register file
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Register file (Modified test from Davis In-Order (DINO) CPU model)

class RegisterFileUnitTester(c: RegFile) extends PeekPokeTester(c) {
   private val rf = c
   
    for (i <- 0 to 31) {
        poke(rf.io.writeReg, i)
        poke(rf.io.writeData, i+100)
        poke(rf.io.regWrite, true)
        step(1)
    }

    for (i <- 0 to 31 by 2) {
        poke(rf.io.readReg1, i)
        poke(rf.io.readReg2, i+1)
        poke(rf.io.writeReg, false)
        if(i == 0) {
            expect(rf.io.readData1, 0)
            expect(rf.io.readData2, i+101)
        }
        else {
            expect(rf.io.readData1, i+100)
            expect(rf.io.readData2, i+101)
        }
        step(1)
    }
}

class RegFileTester extends ChiselFlatSpec {
  "Register file" should s"match expectations for each value" in {
    Driver(() => new RegFile) {
      c => new RegisterFileUnitTester(c)
    } should be (true)
  }
}