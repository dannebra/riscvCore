// Unit tests for the Anhyzer core

package scala

import chisel3.core._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Anhyzer core

class CoreUnitTester(c: Anhyzer) extends PeekPokeTester(c) {
   private val core = c
   val rf = core.regFile
    

    step(1)
    /*
    expect(rf.io.readData1, 101)
    expect(rf.io.readData2, 102)
    expect(core.control.io.memToReg, 1)
    step(1)
    expect(rf.io.readData1, 101)
    expect(rf.io.readData2, 102)
    expect(core.control.io.memToReg, 1)   
    */ 
    
}

class CoreTester extends ChiselFlatSpec {
  "Anhyzer" should s"start the simulation..." in {
    Driver(() => new Anhyzer) {
      c => new CoreUnitTester(c)
    } should be (true)
  }
}