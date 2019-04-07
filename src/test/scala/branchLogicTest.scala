// Unit tests for the Branch Logic
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the Branch Logic (Modified test from Davis In-Order (DINO) CPU model)

class BranchLogicTester(c: BranchLogic) extends PeekPokeTester(c) {
   private val bl = c

  def test(op: UInt, f: (BigInt, BigInt) => Boolean) {
    for (i <- 0 until 10) {
      val x = rnd.nextInt(100000000)
      val y = rnd.nextInt(500000000)
      poke(bl.io.branch, 1)
      poke(bl.io.funct3, op)
      poke(bl.io.reg1, x)
      poke(bl.io.reg2, y)
      step(1)
      val expectOut = f(x, y)
      expect(bl.io.result, expectOut, s"for operation ${op.toInt.toBinaryString}")
    }
  }

  test("b000".U, (x: BigInt, y: BigInt) => (x == y))
  test("b001".U, (x: BigInt, y: BigInt) => (x != y))
  test("b100".U, (x: BigInt, y: BigInt) => (x.intValue < y.intValue))
  test("b101".U, (x: BigInt, y: BigInt) => (x.intValue >= y.intValue))
  test("b110".U, (x: BigInt, y: BigInt) => (x < y))
  test("b111".U, (x: BigInt, y: BigInt) => (x >= y))
}

class BranchLTester extends ChiselFlatSpec {
  "Branch logic" should s"match expectations for each intruction type" in {
    Driver(() => new BranchLogic) {
      c => new BranchLogicTester(c)
    } should be (true)
  }
}