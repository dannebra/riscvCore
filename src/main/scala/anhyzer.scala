package scala

import chisel3._
import chisel3.util._

// This is the Anhyzer core!

class Anhyzer extends Module {
    val io = IO(new Bundle {
        val start = Input(UInt(32.W))
    })
    io := DontCare // Start value

    val pc          = RegInit(io.start)
    val pcSelect    = Module(new PcSelect())
    val pcPlusFour  = Module(new Adder())
    val alu         = Module(new ALU())
    val aluControl  = Module(new AluControl())
    val control     = Module(new Control())
    val immGen      = Module(new ImmGen())
    val branchLogic = Module(new BranchLogic())
    val branchUnit  = Module(new Adder())
    val jumpReg     = Module(new JumpReg())
    val regFile     = Module(new RegFile())
    val jumpAdder   = Module(new Adder())
    val dataMem     = Module(new DataMemory())
    val instrMem    = Module(new InstructionMemory())
    val swwb        = Module(new Swwb())

    // Mux from data memory
    val dataMux    = Mux(control.io.memToReg, dataMem.io.readDataOutput, alu.io.result)

    // Mux to register file
    val regFileMux = Mux(control.io.writeSrc, pcPlusFour.io.result, dataMux)

    // PC + 4
    pcPlusFour.io.in1 := pc
    pcPlusFour.io.in2 := 4.U 

    // Instruction memory
    instrMem.io.address := pc
    val instruction = instrMem.io.instruction
    val opcode      = instruction(6, 0)

    // Control
    control.io.opcode := opcode

    // SWWB
    swwb.io.error := alu.io.error
    swwb.io.crash := DontCare
    val rdMux = Mux(swwb.io.error, 0.U, instruction(11, 7)) // 0 or rd from instruction

    // Register file
    regFile.io.readReg1  := instruction(19, 15) // rs1
    regFile.io.readReg2  := instruction(24, 20) // rs2
    regFile.io.writeReg  := rdMux
    regFile.io.regWrite  := control.io.regWrite
    regFile.io.writeData := regFileMux
    

    // ALU
    val aluMux1 = Mux(control.io.aluSrc1, immGen.io.extendedU, regFile.io.readData1)
    alu.io.in1 := aluMux1
    val src = control.io.aluSrc2
    val aluMux2 = Mux(src === 1.U, immGen.io.extendedI, 
                  Mux(src === 2.U, immGen.io.extendedS, 
                  Mux(src === 3.U, pc, regFile.io.readData2)))
    alu.io.in2 := aluMux2
    alu.io.aluOp := aluControl.io.output

    // ALU control
    aluControl.io.aluOp  := control.io.aluOp
    aluControl.io.funct7 := instruction(31, 25)
    aluControl.io.funct3 := instruction(14, 12) 

    // Data Memory
    dataMem.io.readAddress := alu.io.result
    dataMem.io.writeData   := regFile.io.readData2
    dataMem.io.memWrite    := control.io.memWrite
    dataMem.io.memRead     := control.io.memRead

    // Immediate generator
    immGen.io.instr := instruction

    // Branch logic
    branchLogic.io.reg1 := regFile.io.readData1
    branchLogic.io.reg2 := regFile.io.readData2
    branchLogic.io.branch := control.io.branch
    branchLogic.io.funct3 := instruction(14, 12)

    // Jump reg
    jumpReg.io.reg1 := regFile.io.readData1
    jumpReg.io.imm  := immGen.io.extendedI

    // Jump
    jumpAdder.io.in1 := pc
    jumpAdder.io.in2 := immGen.io.extendedJ

    // Branch
    branchUnit.io.in1 := pc
    branchUnit.io.in2 := immGen.io.extendedB

    // PC-select
    pcSelect.io.pcPlus4      := pcPlusFour.io.result
    pcSelect.io.branch       := branchUnit.io.result
    pcSelect.io.jump         := jumpAdder.io.result
    pcSelect.io.jalr         := jumpReg.io.output
    pcSelect.io.branchSignal := branchLogic.io.result
    pcSelect.io.jumpSignal   := control.io.jump
    pcSelect.io.jalrSignal   := control.io.jumpReg 

    pc := pcSelect.io.output
}