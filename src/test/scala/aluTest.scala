// Unit tests for the ALU
package scala

import chisel3._

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

// Tests the ALU (Modified test from Davis In-Order (DINO) CPU model)

class AluUnitTester(c: ALU) extends PeekPokeTester(c) {
   private val alu = c

  val maxInt = BigInt("FFFFFFFF", 16)

  def test(op: UInt, f: (BigInt, BigInt) => BigInt) {
    for (i <- 0 until 10) {
      val x = rnd.nextInt(100000000)
      val y = rnd.nextInt(500000000)
      poke(alu.io.aluOp, op)
      poke(alu.io.in1, x)
      poke(alu.io.in2, y)
      step(1)
      val expectOut = f(x, y).toInt & maxInt
      expect(alu.io.result, expectOut, s"for operation ${op.toInt.toBinaryString}")
    }
  }

  test("b00000".U, (x: BigInt, y: BigInt) => (x & y))
  test("b00001".U, (x: BigInt, y: BigInt) => (x | y))
  test("b00010".U, (x: BigInt, y: BigInt) => (x + y))
  test("b00110".U, (x: BigInt, y: BigInt) => (x - y))
  test("b00100".U, (x: BigInt, y: BigInt) => (if (x < y) 1 else 0))
  test("b00100".U, (x: BigInt, y: BigInt) => (if (x < y) 1 else 0))
  test("b00011".U, (x: BigInt, y: BigInt) => (x << (y.toInt & 0x1f)))
  test("b00111".U, (x: BigInt, y: BigInt) => (x >> (y.toInt & 0x1f)))
  test("b01001".U, (x: BigInt, y: BigInt) => (x >> (y.toInt & 0x1f)))
  test("b00101".U, (x: BigInt, y: BigInt) => (x ^ y))
}

class AluTester extends ChiselFlatSpec {
  "ALU" should s"match expectations for each intruction type" in {
    Driver(() => new ALU) {
      c => new AluUnitTester(c)
    } should be (true)
  }
}