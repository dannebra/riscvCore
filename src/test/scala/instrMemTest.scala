// Unit tests for the Instruction memory
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Instruction memory

class IMUnitTester(c: InstructionMemory) extends PeekPokeTester(c) {
   private val mem = c
   poke(mem.io.address, 0)
   step(1)
   expect(mem.io.instruction, "b00000000000100010000001110110011".U)
   poke(mem.io.address, 4)
   step(1)
   expect(mem.io.instruction, "b00000000000100010000010000110011".U)

}

class ImemControlTester extends ChiselFlatSpec {
  "Instruction memory" should s"match expectations for each intruction type" in {
    Driver(() => new InstructionMemory) {
      c => new IMUnitTester(c)
    } should be (true)
  }
}