.rsp

.include "rsp/rsp_defs.inc"
.include "rsp/gbi.inc"

// This file assumes DATA_FILE and CODE_FILE are set on the command line

.if version() < 110
    .error "armips 0.11 or newer is required"
.endif

// Overlay table data member offsets
overlay_load equ 0x0000
overlay_len  equ 0x0004
overlay_imem equ 0x0006
.macro OverlayEntry, loadStart, loadEnd, imemAddr
  .dw loadStart
  .dh (loadEnd - loadStart - 1) & 0xFFFF
  .dh (imemAddr) & 0xFFFF
.endmacro

.macro jumpTableEntry, addr
  .dh addr & 0xFFFF
.endmacro

// RSP DMEM
.create DATA_FILE, 0x0000

// 0x0000-0x0027: Overlay Table
overlayInfo0:
  OverlayEntry orga(Overlay0Address), orga(Overlay0End), Overlay0Address
overlayInfo1:
  OverlayEntry orga(Overlay1Address), orga(Overlay1End), Overlay1Address
overlayInfo2:
  OverlayEntry orga(Overlay2Address), orga(Overlay2End), Overlay2Address
overlayInfo3:
  OverlayEntry orga(Overlay3Address), orga(Overlay3End), Overlay3Address
overlayInfo4:
  OverlayEntry orga(Overlay4Address), orga(Overlay4End), Overlay4Address

// 0x0028-0x009F: ??
.dw 0x0FFAF006
.dw 0x7FFF0000
.dw 0x00000001
.dw 0x0002FFFF
.dw 0x40000004
.dw 0x06330200
.dw 0x7FFFFFF8
.dw 0x00080040
.dw 0x00208000
.dw 0x01CCCCCC
.dw 0x0001FFFF
.dw 0x00010001
.dw 0x0001FFFF
.dw 0x00010001
.dw 0x00020002
.dw 0x00020002

// 0x0068
.dw 0x00020002
.dw 0x00020002

data0070:
.dw 0x00010000

// 0x0074
.dh 0x0000
// 0x0076
.dh 0x0001

// 0x0078
.dw 0x00000001
.dw 0x00000001
.dw 0x00010000
.dw 0x0000FFFF
.dw 0x00000001
.dw 0x0000FFFF
.dw 0x00000000
.dw 0x0001FFFF
.dw 0x00000000
.dw 0x00010001

// 0x00A0-0x00A1
lightEntry:
  jumpTableEntry load_lighting

// 0x00A2-0x00A3: ??
.dh 0x7FFF

// 0x00A4-0x00B3: ??
.dw 0x571D3A0C
.dw 0x00010002
.dw 0x01000200
.dw 0x40000040

// 0x00B4
.dh 0x0000

// 0x00B6-0x00B7
taskDoneEntry:
  jumpTableEntry overlay_4_entry

// 0x00B8
lower24Mask:
.dw 0x00FFFFFF

// 0x00BC: Operation Types
operationJumpTable:
  jumpTableEntry dispatch_dma // cmds 0x00-0x3f
spNoopEntry:
  jumpTableEntry SP_NOOP      // cmds 0x40-0x7f
  jumpTableEntry dispatch_imm // cmds 0x80-0xbf
  jumpTableEntry dispatch_rdp // cmds 0xc0-0xff

// 0x00C4: DMA operations
dmaJumpTable:
  jumpTableEntry SP_NOOP      // 0x00
  jumpTableEntry dma_MTX      // 0x01
  jumpTableEntry SP_NOOP      // 0x02
  jumpTableEntry dma_MOVEMEM  // 0x03
  jumpTableEntry dma_VTX      // 0x04
  jumpTableEntry SP_NOOP      // 0x05
  jumpTableEntry dma_DL       // 0x06
  jumpTableEntry SP_NOOP      // 0x07
  jumpTableEntry SP_NOOP      // 0x08
  jumpTableEntry SP_NOOP      // 0x09

// 0x00D8: Immediate operations
immediateJumpTableBase equ (immediateJumpTable - ((0xB2 << 1) & 0xFE))
.ifdef F3D_OLD
  jumpTableEntry imm_UNKNOWN
.endif
immediateJumpTable:
  jumpTableEntry imm_RDPHALF_CONT      // 0xB2
  jumpTableEntry imm_RDPHALF_2         // 0xB3
  jumpTableEntry imm_RDPHALF_1         // 0xB4
  jumpTableEntry SP_NOOP               // 0xB5?
  jumpTableEntry imm_CLEARGEOMETRYMODE // 0xB6
  jumpTableEntry imm_SETGEOMETRYMODE   // 0xB7
  jumpTableEntry imm_ENDDL             // 0xB8
  jumpTableEntry imm_SETOTHERMODE_L    // 0xB9
  jumpTableEntry imm_SETOTHERMODE_H    // 0xBA
  jumpTableEntry imm_TEXTURE           // 0xBB
  jumpTableEntry imm_MOVEWORD          // 0xBC
  jumpTableEntry imm_POPMTX            // 0xBD
  jumpTableEntry imm_CULLDL            // 0xBE
  jumpTableEntry imm_TRI1              // 0xBF

// 0x00F6: Label constants
labelLUT:
  jumpTableEntry found_in
foundOutEntry:
  jumpTableEntry found_out
  jumpTableEntry found_first_in
  jumpTableEntry found_first_out
clipDrawEntry:
  jumpTableEntry clip_draw_loop
performClipEntry:
  jumpTableEntry perform_clip
nextClipEntry:
  jumpTableEntry next_clip
DMAWaitEntry:
  jumpTableEntry dma_wait_dl

// 0x0106: ??
data0106:
.dh 0x0000

.ifdef F3D_NEW
.dh 0x0000
.endif

// 0x0108: DRAM pointer
dramPtr:
.dw 0x00000000
.dh 0x0000     // 0x10C: RDPHALF_2

.dh 0x0000

// 0x110: display list stack size
displayListStackSize:
.dh 0x0000
.dh 0xFFFF     // 0x112: RDPHALF_1

.dw 0x00000000 // 0x114: geometrymode (bit 1 is texture ON)
.dw 0xEF080CFF // 0x118: othermode
.dw 0x00000000
.dw 0x00000000 // 0x120: texture max mipmap levels, tile descriptor enable/disable
.dh 0x0000     // 0x124: texture scaling factor S axis (horizontal) U16 fraction
.dh 0x0000     // 0x126: texture scaling factor T axis (vertical)
.dw 0x00000000 // 0x128: some dpc dma address state

numLights:
.dw 0x80000040 // 0x12c: num lights, bit 31 = needs init, bits 11:0 = (num_lights+1)*32
.dw 0x00000000 // 0x130: dram stack pointer 1
.dw 0x00000000 // 0x134: dram stack pointer modelview matrices

data0138:
.dw 0x40004000 // 0x138: txtatt (unused?)

.dw 0x00000000
.dw 0x00000000
.dw 0x00000000
.dw 0x00000000
.dw 0x00000000

.dw 0x00000000 // 0x150: output buffer

.dw 0x00000000 // 0x154: output buffer size

data0158:
.dh 0x0000     // 0x158: ??
.dh 0x0000
.dw 0x00000000 // 0x15c: dram stack end?

// 0x160-0x19f: RSP memory segment table
segmentTable:
.fill 0x40, 0

// 0x1a0: Lights
.dw 0x80000000
.dw 0x80000000
.dw 0x00000000
.dw 0x00000000

lookAtY:       // 0x1b0: lookaty
.dw 0x00800000, 0x00800000, 0x7F000000, 0x00000000
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000

lookAtX:       // 0x1d0: lookatx
.dw 0x00000000, 0x00000000, 0x007F0000, 0x00000000
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000

// 0x1f0: L0..L7 light info (32 bytes each)
lightInfo0: // 0x1f0
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000
.dw 0x00000000, 0x00000000, 0xE0011FFF, 0x00040000

lightInfo1: // 0x210
.dw 0xFF000000, 0xFF000000, 0x00000000, 0x00000000
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000

lightInfo2: // 0x230
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000

lightInfo3: // 0x250
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000

lightInfo4: // 0x270

// L4-L7 overlap with version string
.definelabel lightInfo5, lightInfo4 + 0x20 // 0x290
.definelabel lightInfo6, lightInfo5 + 0x20 // 0x2b0
.definelabel lightInfo7, lightInfo6 + 0x20 // 0x2d0

.if defined(F3D_OLD) || defined(VERSION_EU)
  .asciiz "RSP SW Version: 2.0D, 04-01-96"
.elseif defined(F3D_NEW)
  .asciiz "RSP SW Version: 2.0H, 02-12-97"
.endif
.asciiz "SGI U64 GFX SW TEAM: S Anderson, S Carr, H Cheng, K Luster, R Moore, N Pooley, A Srinivasan", 0x0A

.dw 0x00000000

// 0x2f0-0x31f: DMEM table
dmemTableOffset equ (dmemTable - 0x80)
dmemTable:
  .dh viewport,   lookAtY,    lookAtX,      lightInfo0, lightInfo1,    lightInfo2,    lightInfo3,    lightInfo4
  .dh lightInfo5, lightInfo6, lightInfo7,   data0138,   mpMatrix+0x10, mpMatrix+0x20, mpMatrix+0x30, mpMatrix
  .dh numLights,  data0070,   segmentTable, fogFactors, lightInfo0,    pointsBuffer
.ifdef F3D_NEW
  .dh displayListStackSize, 0x0000
.else
  .dh 0x0000, 0x0000
.endif

// 0x320: Viewport (0x010 bytes)
viewport:
.dw 0x00000000, 0x00000000, 0x00000000, 0x00000000

// 0x330: fog factors (three 16-bit integers: mul, add, min)
fogFactors:
.dh 0x0100, 0x0000, 0x00FF

// 0x336: display list stack (return addresses)
displayListStack: // this is not 4-byte aligned
.fill 0x2a, 0

// 0x360-0x39f: Modelview matrix top of stack (0x40 bytes)
modelViewMatrixStack:
.fill 0x40, 0

// 0x3a0-0x3df Projection Matrix top of stack (0x40 bytes)
projectionMatrixStack:
.fill 0x40, 0

// 0x3e0-0x41f: MP matrix (Modelview * Projection)
mpMatrix:
.fill 0x40, 0

// 0x420: Points buffer (0x280 bytes)
pointsBuffer:
.fill 0x280, 0

// 0x6a0-0x7df: input display list buffer
inputDisplayList:
.fill 0x140, 0

// 0x7E0-0x7ff: input data
inputData:
.fill (0x800 - 0x7E0), 0

.close // DATA_FILE

// uninitialized variables
.definelabel setupTemp, 0x08E0
.definelabel data08e4, 0x08E4
.definelabel data08e8, 0x08E8
.definelabel data08ec, 0x08EC
.definelabel data08f0, 0x08F0
.definelabel clipTemp, 0x0940
.definelabel data0942, 0x0942
.definelabel data0944, 0x0944
.definelabel data0946, 0x0946
.definelabel rdpOutput, 0x09E0
.definelabel scratchSpace, 0x0DE0
.definelabel data0DE4, 0x0DE4
.definelabel data0DE8, 0x0DE8

.create CODE_FILE, 0x04001080

// Overlay 0
Overlay0Address:
    j     f3d_04001780
     addi  $29, $zero, displayListStackSize
    jal   segmented_to_physical
     add   $19, $24, $zero
    add   $20, $zero, $22
    jal   dma_read_write
     addi  $17, $zero, 0x00

// $1 = most significant 2 bits of cmd byte << 1
// $25 = first command word
dispatch_task:
    lh    $2, (operationJumpTable)($1)
    jr    $2
     srl   $2, $25, 23        // $2 = MSbyte << 1

SP_NOOP:
    mfc0  $2, SP_STATUS
    andi  $2, $2, 0x0080
    bne   $2, $zero, f3d_040010cc
     lh    $21, 0x26($zero)
f3d_040010b8:
    bgtz  $28, read_next_task_entry
     nop
    j     load_display_list_dma
     lh    $ra, DMAWaitEntry
f3d_040010c8:
    lh    $21, taskDoneEntry
f3d_040010cc:
    j     load_overlay
     ori   $30, $zero, overlayInfo4

load_display_list_dma:
    addi  $28, $zero, 0x0140    // size of display list
    add   $21, $zero, $ra
    addi  $20, $zero, inputDisplayList
    add   $19, $zero, $26       // TASK_DATA_PTR
    addi  $18, $zero, 0x013f
    jal   dma_read_write
     addi  $17, $zero, 0x00
    jr    $21
     addi  $27, $zero, inputDisplayList // initial pointer

// load overlay into IMEM
// $30 = offset into overlay table
// $21 = return address
load_overlay_fcn:
    add   $21, $zero, $ra
load_overlay:
    lw    $19, overlay_load($30)
    lh    $18, overlay_len($30)
    lh    $20, overlay_imem($30)
    jal   dma_read_write
     addi  $17, $zero, 0x00
    jal   wait_while_dma_busy
     nop
    jr    $21

segmented_to_physical:
     lw    $11, lower24Mask
    srl   $12, $19, 22
    andi  $12, $12, 0x3c
    and   $19, $19, $11
    add   $13, $zero, $12
    lw    $12, 0x0160($13)
    jr    $ra
     add   $19, $19, $12

// $20 = SP_MEM address
// $19 = DRAM address
// $18 = length - 1
// $17 = 1:write, 0:read
dma_read_write:
@@dma_full:
    mfc0  $11, SP_DMA_FULL
    bne   $11, $zero, @@dma_full
     nop
    mtc0  $20, SP_MEM_ADDR
    bgtz  $17, @@dma_write
     mtc0  $19, SP_DRAM_ADDR
    jr    $ra
     mtc0  $18, SP_RD_LEN
@@dma_write:
    jr    $ra
     mtc0  $18, SP_WR_LEN

wait_while_dma_busy:
    mfc0  $11, SP_DMA_BUSY
    bne   $11, $zero, wait_while_dma_busy
     nop
    jr    $ra
     nop

f3d_04001178:                // sends stuff to RDP
    add   $21, $zero, $ra
    lw    $19, 0x18($29)
    addi  $18, $23, -0x09e0
    lw    $23, 0x44($29)
    blez  $18, f3d_040011f4
     add   $20, $19, $18
    sub   $20, $23, $20
    bgez  $20, f3d_040011b8
f3d_04001198:
     mfc0  $20, DPC_STATUS
    andi  $20, $20, DPC_STATUS_START_VALID
    bne   $20, $zero, f3d_04001198
f3d_040011a4:
     mfc0  $23, DPC_CURRENT
    lw    $19, 0x40($29)
    beq   $23, $19, f3d_040011a4
     nop
    mtc0  $19, DPC_START
f3d_040011b8:
    mfc0  $23, DPC_CURRENT
    sub   $20, $19, $23
    bgez  $20, f3d_040011d4
     add   $20, $19, $18
    sub   $20, $20, $23
    bgez  $20, f3d_040011b8
     nop
f3d_040011d4:
    add   $23, $19, $18
    addi  $18, $18, -0x01
    addi  $20, $zero, rdpOutput   // output to RDP
    jal   dma_read_write
     addi  $17, $zero, 0x01
    jal   wait_while_dma_busy
     sw    $23, 0x18($29)
    mtc0  $23, DPC_END
f3d_040011f4:
    jr    $21
     addi  $23, $zero, rdpOutput

// codes 0x80-0xBF
// $2 = immediate cmd byte << 1
dispatch_imm:
    andi  $2, $2, 0x00fe
    lh    $2, (immediateJumpTableBase)($2) // data IMM offset
    jr    $2
     lbu   $1, -0x01($27)

imm_TRI1:
    lbu   $5, -0x04($27)
    lbu   $1, -0x03($27)
    lbu   $2, -0x02($27)
    lbu   $3, -0x01($27)
    sll   $5, $5, 2
    sll   $1, $1, 2
    sll   $2, $2, 2
    sll   $3, $3, 2
    addi  $1, $1, pointsBuffer
    addi  $2, $2, pointsBuffer
    addi  $3, $3, pointsBuffer
    sw    $1, scratchSpace
    sw    $2, data0DE4
    sw    $3, data0DE8
    lw    $4, (scratchSpace)($5)
    j     f3d_04001998
     lh    $30, spNoopEntry

imm_POPMTX:
.ifdef F3D_NEW
    sbv   $v31[6], 0x1c($29)
.endif
    lw    $19, 0x24($29)
    lw    $3, 0x4c($29)
    addi  $20, $zero, modelViewMatrixStack
    addi  $18, $zero, 0x3f
    sub   $3, $3, $19
    addi  $3, $3, -0x0280
    bgez  $3, SP_NOOP          // stop if stack is empty
     addi  $19, $19, -0x40
    jal   dma_read_write       // read new top from DRAM
     addi  $17, $zero, 0x00
    jal   wait_while_dma_busy
     addi  $3, $zero, mpMatrix // MP matrix (modelview * projection)
    j     f3d_04001444         // recompute MP matrix
     sw    $19, 0x24($29)

imm_MOVEWORD:
    lbu   $1, -0x05($27)
    lhu   $2, -0x07($27)
    lh    $5, 0x030e($1)
    add   $5, $5, $2
    j     SP_NOOP
     sw    $24, 0x00($5)

imm_TEXTURE:
    sw    $25, 0x10($29)
    sw    $24, 0x14($29)
    lh    $2, 0x06($29)
    andi  $2, $2, 0xfffd
    andi  $3, $25, 0x0001
    sll   $3, $3, 1
    or    $2, $2, $3
    j     SP_NOOP
     sh    $2, 0x06($29)

imm_SETOTHERMODE_H:
    j     @f3d_040012d0
     addi  $7, $29, 8
imm_SETOTHERMODE_L:
    addi  $7, $29, 0x0c
@f3d_040012d0:
    lw    $3, 0x00($7)
    addi  $8, $zero, -1
    lbu   $5, -0x05($27)
    lbu   $6, -0x06($27)
    addi  $2, $zero, 1
    sllv  $2, $2, $5
    addi  $2, $2, -1
    sllv  $2, $2, $6
    xor   $2, $2, $8
    and   $2, $2, $3
    or    $3, $2, $24
    sw    $3, 0x00($7)
    lw    $25, 0x08($29)
    j     f3d_040013a8
     lw    $24, 0x0c($29)

imm_CULLDL:
    andi  $25, $25, 0x03ff
.ifdef F3D_OLD
    ori   $2, $zero, 0xffff
.else
    ori   $2, $zero, 0x7070
.endif
@@f3d_04001314:
    lh    $3, 0x0444($25)
    addi  $25, $25, 0x28
    bne   $25, $24, @@f3d_04001314
     and   $2, $2, $3
    beq   $2, $zero, SP_NOOP

DL_STACK_SIZE_OFFSET equ (defined(F3D_OLD) ? 0x00 : 0x4A)

imm_ENDDL:
     lb    $2, (DL_STACK_SIZE_OFFSET)($29)
    addi  $2, $2, -4
    bltz  $2, f3d_040010c8
     addi  $3, $2, displayListStack
    lw    $26, 0x00($3)
    sb    $2, (DL_STACK_SIZE_OFFSET)($29)
    j     SP_NOOP
     addi  $28, $zero, 0

imm_SETGEOMETRYMODE:
    lw    $2, 0x04($29)
    or    $2, $2, $24
    j     SP_NOOP
     sw    $2, 0x04($29)

imm_CLEARGEOMETRYMODE:
    lw    $2, 0x04($29)
    addi  $3, $zero, -1
    xor   $3, $3, $24
    and   $2, $2, $3
    j     SP_NOOP
     sw    $2, 0x04($29)

.ifdef F3D_OLD
imm_RDPHALF_1:
    j     SP_NOOP
     sh    $24, 0x02($29)
imm_RDPHALF_2:
.else
imm_RDPHALF_1:
.endif
    j     f3d_040010b8
     sw    $24, -0x04($29)

.ifdef F3D_OLD
imm_UNKNOWN:
.else
imm_RDPHALF_CONT:
.endif
    ori   $2, $zero, 0x0000
.ifdef F3D_OLD
imm_RDPHALF_CONT:
.else
imm_RDPHALF_2:
.endif
    j     f3d_040013a8
     lw    $25, -0x04($29)

// codes 0xC0-0xFF
dispatch_rdp:
    sra   $2, $25, 24
    addi  $2, $2, 3
    bltz  $2, f3d_040013a8
     addi  $2, $2, 0x18
    jal   segmented_to_physical
     add   $19, $24, $zero
    add   $24, $19, $zero
f3d_040013a8:
    sw    $25, 0x00($23)
    sw    $24, 0x04($23)
    jal   f3d_04001178
     addi  $23, $23, 0x08
    bgtz  $2, SP_NOOP
     nop
    j     f3d_040010b8

dispatch_dma:
     andi  $2, $2, 0x01fe
    lh    $2, (dmaJumpTable)($2)
    jal   wait_while_dma_busy
     lbu   $1, -0x07($27)
    jr    $2
     andi  $6, $1, 0x000f

dma_MTX:
    sbv   $v31[6], 0x1c($29)     // lights need re-init
    andi  $8, $1, 0x0001         // 1=projection, 0=modelview
    bne   $8, $zero, f3d_04001454
     andi  $7, $1, 0x0002        // 1=load, 0=multiply
    addi  $20, $zero, modelViewMatrixStack
    andi  $8, $1, 0x0004         // 1=push, 0=no push
    beq   $8, $zero, f3d_04001420
     lqv   $v26[0], 0x30($22)
    lw    $19, 0x24($29)         // DRAM stack pointer 2
    lw    $8, 0x4c($29)          // DRAM stack end
    addi  $17, $zero, 1
    addi  $1, $19, 0x40
    beq   $19, $8, f3d_04001420
     addi  $12, $zero, 0x3f      // BUG: wrong register, should be $18
    jal   dma_read_write
     sw    $1, 0x24($29)
    jal   wait_while_dma_busy
f3d_04001420:
     lqv   $v28[0], 0x10($22)
    beq   $7, $zero, f3d_04001460
     lqv   $v27[0], 0x20($22)
    sqv   $v26[0], 0x30($20)
    lqv   $v29[0], 0x00($22)
    sqv   $v28[0], 0x10($20)
f3d_04001438:
    addi  $3, $zero, mpMatrix
    sqv   $v27[0], 0x20($20)
    sqv   $v29[0], 0x00($20)
f3d_04001444:
    addi  $1, $zero, modelViewMatrixStack
    addi  $2, $zero, projectionMatrixStack
    j     f3d_04001484
     lh    $ra, spNoopEntry
f3d_04001454:
    lqv   $v26[0], 0x30($22)
    j     f3d_04001420
     addi  $20, $zero, projectionMatrixStack
f3d_04001460:
    addiu $3, $zero, scratchSpace
    addu  $1, $zero, $22         // input matrix from user
    jal   f3d_04001484
     addu  $2, $zero, $20        // current P matrix or M top
    sqv   $v6[0], 0x30($20)      // store result to P or M
    sqv   $v5[0], 0x10($20)
    lqv   $v27[0], 0x00($3)
    j     f3d_04001438
     lqv   $v29[0], -0x20($3)
f3d_04001484:
    addi  $19, $3, 0x10
f3d_04001488:
    vmudh $v5, $v31, $v31[0]     // clear accumulator and $v5
    addi  $18, $1, 8
f3d_04001490:
    ldv   $v3[0], 0x00($2)
    ldv   $v4[0], 0x20($2)
    lqv   $v1[0], 0x00($1)
    lqv   $v2[0], 0x20($1)
    ldv   $v3[8], 0x00($2)
    ldv   $v4[8], 0x20($2)
    vmadl $v6, $v4, $v2[0h]
    addi  $1, $1, 2
    vmadm $v6, $v3, $v2[0h]
    addi  $2, $2, 8
    vmadn $v6, $v4, $v1[0h]
    vmadh $v5, $v3, $v1[0h]
    bne   $1, $18, f3d_04001490
     vmadn $v6, $v31, $v31[0]
    addi  $2, $2, -0x20
    addi  $1, $1, 8
    sqv   $v5[0], 0x00($3)
    sqv   $v6[0], 0x20($3)
    bne   $3, $19, f3d_04001488
     addi  $3, $3, 0x10
    jr    $ra
     nop
f3d_040014e8:
    addi  $8, $zero, viewport
    lqv   $v3[0], 0x50($zero)
    lsv   $v19[0], 0x02($29)     // RDPHALF_1, contains persp normalize
    lh    $3, 0x04($29)          // geometrymode
    ldv   $v0[0], 0x00($8)       // viewport scale
    ldv   $v1[0], 0x08($8)       // viewport translate
    ldv   $v0[8], 0x00($8)
    ldv   $v1[8], 0x08($8)
    jr    $ra
     vmudh $v0, $v0, $v3         // negate Y?

load_mp_matrix:
    addi  $8, $zero, mpMatrix
    ldv   $v11[0], 0x18($8)      // load into $v8-v15, dup lower half and higher half
    ldv   $v11[8], 0x18($8)      // $v8-v11 integer parts, $v12-v15 frac parts
    ldv   $v15[0], 0x38($8)
    ldv   $v15[8], 0x38($8)
f3d_04001524:
    ldv   $v8[0], 0x00($8)
    ldv   $v9[0], 0x08($8)
    ldv   $v10[0], 0x10($8)
    ldv   $v12[0], 0x20($8)
    ldv   $v13[0], 0x28($8)
    ldv   $v14[0], 0x30($8)
    ldv   $v8[8], 0x00($8)
    ldv   $v9[8], 0x08($8)
    ldv   $v10[8], 0x10($8)
    ldv   $v12[8], 0x20($8)
    ldv   $v13[8], 0x28($8)
    jr    $ra
     ldv   $v14[8], 0x30($8)

dma_MOVEMEM:
    lqv   $v0[0], 0x00($22)
    lh    $5, (dmemTableOffset)($1)
    j     SP_NOOP
     sqv   $v0[0], 0x00($5)

dma_VTX:
    lh    $8, spNoopEntry
    sh    $8, data0106
    srl   $1, $1, 4
    addi  $5, $1, 1              // num vertex
    addi  $9, $5, 0
    ldv   $v2[0], 0x00($22)      // input data
    ldv   $v2[8], 0x10($22)      // load 2nd vertex (assuming it exists)
    addi  $7, $zero, pointsBuffer
    sll   $8, $6, 5              // dest index
    sll   $6, $6, 3
    add   $8, $6, $8             // 40 bytes per vertex
    jal   f3d_040014e8
     add   $7, $7, $8
    llv   $v17[0], 0x14($29)     // texture scaling
    jal   load_mp_matrix
     llv   $v17[8], 0x14($29)
@f3d_040015a8:
    vmudn $v28, $v12, $v2[0h]    // x * first row frac
    llv   $v18[0], 0x08($22)
    vmadh $v28, $v8, $v2[0h]     // x * first row int
    lw    $15, 0x0c($22)         // XR, YG, ZB, AA
    vmadn $v28, $v13, $v2[1h]    // y * second row frac
    lw    $16, 0x1c($22)
    vmadh $v28, $v9, $v2[1h]     // y * second row int
    andi  $1, $3, G_LIGHTING_H
    vmadn $v28, $v14, $v2[2h]    // z * third row frac
    vmadh $v28, $v10, $v2[2h]    // z * third row int
    vmadn $v28, $v15, $v31[1]    // 1 * fourth row frac
    llv   $v18[8], 0x18($22)
    vmadh $v29, $v11, $v31[1]    // 1 * fourth row int
    bne   $1, $zero, load_lighting
     addi  $22, $22, 0x20        // next 2 vertices
@f3d_040015e4:
    vmudm $v18, $v18, $v17       // U *= S scale, V *= T scale (result >> 16)
@f3d_040015e8:
    lsv   $v21[0], 0x76($zero)
    vmudn $v20, $v28, $v21[0]
    vmadh $v21, $v29, $v21[0]
    vch   $v3, $v29, $v29[3h]    // do trivial clip rejection
    vcl   $v3, $v28, $v28[3h]    // by comparing xyz with w
    cfc2  $13, vcc
    vch   $v3, $v29, $v21[3h]
    vcl   $v3, $v28, $v20[3h]
    andi  $8, $13, 0x0707        // filter out xyz clip result for 1st vertex
    andi  $13, $13, 0x7070       // filter out xyz clip result for 2nd vertex
    sll   $8, $8, 4
    sll   $13, $13, 16
    or    $13, $13, $8
    cfc2  $14, vcc
    andi  $8, $14, 0x0707
    vadd  $v21, $v29, $v31[0]
    andi  $14, $14, 0x7070
    vadd  $v20, $v28, $v31[0]
    sll   $14, $14, 12
    vmudl $v28, $v28, $v19[0]    // persp normalize, used to improve precision
    or    $8, $8, $14
    vmadm $v29, $v29, $v19[0]
    or    $8, $8, $13
    vmadn $v28, $v31, $v31[0]
    sh    $8, 0x24($7)
    jal   f3d_04001000           // compute 1/w
     lh    $13, -0x1a($22)       // $13 unused?
    vge   $v6, $v27, $v31[0]     // 1/w >= 0?
    sdv   $v21[0], 0x00($7)      // store xyzw int
    vmrg  $v6, $v27, $v30[0]
    sdv   $v20[0], 0x08($7)      // store xyzw frac
    vmudl $v5, $v20, $v26[3h]    // mul xyzw with 1/w
    vmadm $v5, $v21, $v26[3h]
    vmadn $v5, $v20, $v6[3h]
    vmadh $v4, $v21, $v6[3h]
    addi  $9, $9, -1             // decrement vertex input count
    vmudl $v5, $v5, $v19[0]      // take away persp normalize factor
    vmadm $v4, $v4, $v19[0]
    vmadn $v5, $v31, $v31[0]
    andi  $12, $3, G_FOG_H
    ldv   $v2[0], 0x00($22)      // pre-load next vertices from input
    vmudh $v7, $v1, $v31[1]      // viewport translate * 0001
    ldv   $v2[8], 0x10($22)
    vmadn $v7, $v5, $v0          // viewport scale
    ldv   $v29[0], 0x28($zero)
    vmadh $v6, $v4, $v0
    ldv   $v29[8], 0x28($zero)
    vmadn $v7, $v31, $v31[0]     // $v6$v7 contains vertex results after viewport
    vge   $v6, $v6, $v29[1q]     // some saturating 0FFA-F006
    sw    $15, 0x10($7)
    beq   $12, $zero, @@f3d_040016e0 // skip fog?
     vlt   $v6, $v6, $v29[0q]
    lqv   $v3[0], 0x0330($zero)
    vmudn $v5, $v5, $v3[0]       // mul fog factor (default 1)
    vmadh $v4, $v4, $v3[0]
    vadd  $v4, $v4, $v3[1]       // add parameter (default 0)
    vge   $v4, $v4, $v31[0]
    vlt   $v4, $v4, $v3[2]       // min parameter (default 0xff)
    sbv   $v4[5], 0x13($7)       // high z for 1st vertex, store in AA
    sw    $16, 0x18($7)
    sbv   $v4[13], 0x1b($7)      // high z for 2nd vertex, store in AA
    lw    $16, 0x18($7)
@@f3d_040016e0:
    slv   $v18[0], 0x14($7)      // texture coordinates, 1st vertex
    sdv   $v6[0], 0x18($7)       // xyz_int after viewport
    ssv   $v7[4], 0x1e($7)       // z_frac after viewport
    ssv   $v27[6], 0x20($7)      // 1/w
    ssv   $v26[6], 0x22($7)
    blez  $9, @@f3d_04001728
     addi  $9, $9, -1            // decrement vertex input counter again
    sdv   $v21[8], 0x28($7)
    sdv   $v20[8], 0x30($7)
    slv   $v18[8], 0x3c($7)      // texture coordinates, 2nd vertex
    sw    $16, 0x38($7)
    sdv   $v6[8], 0x40($7)
    ssv   $v7[12], 0x46($7)
    ssv   $v27[14], 0x48($7)
    ssv   $v26[14], 0x4a($7)
    sw    $8, 0x4c($7)           // puts high hword first
    addi  $7, $7, 0x50
    bgtz  $9, @f3d_040015a8
@@f3d_04001728:
     lh    $8, data0106
    jr    $8
     nop

dma_DL:
    bgtz  $1, @@f3d_04001754     // 0=store ret addr, 1=end DL after branch
     lb    $2, (DL_STACK_SIZE_OFFSET)($29)
    addi  $4, $2, -0x24          // DL stack full?
    bgtz  $4, SP_NOOP
     addi  $3, $2, displayListStack
    addi  $2, $2, 4
    sw    $26, 0x00($3)          // store return address on DL stack
    sb    $2, (DL_STACK_SIZE_OFFSET)($29)
@@f3d_04001754:
    jal   segmented_to_physical
     add   $19, $24, $zero
    add   $26, $19, $zero
    j     SP_NOOP
     addi  $28, $zero, 0x00

// Overlays 2-4 will overwrite the following code
.org 0x04001768
f3d_04001768:
    ori   $30, $zero, overlayInfo2
    b     load_overlay
     lh    $21, performClipEntry

load_lighting:
    ori   $30, $zero, overlayInfo3
    b     load_overlay
     lh    $21, lightEntry

f3d_04001780:
    ori   $2, $zero, 0x2800      // clear yielded, clear taskdone
    mtc0  $2, SP_STATUS
    lqv   $v31[0], 0x30($zero)
    lqv   $v30[0], 0x40($zero)
    lw    $4, OSTask_addr + OSTask_flags
    andi  $4, $4, 0x0001
    bne   $4, $zero, @@f3d_04001870
     nop
    lw    $23, 0x28($1)          // task output buff
    lw    $3, 0x2c($1)           // task output buff size
    sw    $23, 0x40($29)
    sw    $3, 0x44($29)
    mfc0  $4, DPC_STATUS
    andi  $4, $4, DPC_STATUS_XBUS_DMA
    bne   $4, $zero, @@f3d_040017e4
     mfc0  $4, DPC_END
    sub   $23, $23, $4
    bgtz  $23, @@f3d_040017e4
     mfc0  $5, DPC_CURRENT
    beq   $5, $zero, @@f3d_040017e4
     nop
    beq   $5, $4, @@f3d_040017e4
     nop
    j     @@f3d_04001800
     ori   $3, $4, 0x0000
@@f3d_040017e4:
    mfc0  $4, DPC_STATUS
    andi  $4, $4, DPC_STATUS_START_VALID
    bne   $4, $zero, @@f3d_040017e4
     addi  $4, $zero, DPC_STATUS_CLR_XBUS
    mtc0  $4, DPC_STATUS
    mtc0  $3, DPC_START
    mtc0  $3, DPC_END
@@f3d_04001800:
    sw    $3, 0x18($29)
    addi  $23, $zero, rdpOutput
    lw    $5, 0x10($1)           // TASK_UCODE (DRAM address)
    lw    $2, overlayInfo1
    lw    $3, overlayInfo2
    lw    $4, overlayInfo3
    lw    $6, overlayInfo4
    add   $2, $2, $5             // apply DRAM offset
    add   $3, $3, $5
    add   $4, $4, $5
    add   $6, $6, $5
    sw    $2, overlayInfo1       // store back with DRAM offsets
    sw    $3, overlayInfo2
    sw    $4, overlayInfo3
    sw    $6, overlayInfo4
    jal   load_overlay_fcn
     addi  $30, $zero, overlayInfo1
    jal   load_display_list_dma
     lw    $26, 0x30($1)         // TASK_DATA_PTR
    lw    $2, 0x20($1)           // TASK_DRAM_STACK
    sw    $2, 0x20($29)
    sw    $2, 0x24($29)
    addi  $2, $2, 0x0280         // end of stack?
    sw    $2, 0x4c($29)
    lw    $2, -0x08($zero)       // TASK_YIELD_DATA_PTR
    sw    $2, dramPtr
    j     dma_wait_dl
     nop
@@f3d_04001870:
    jal   load_overlay_fcn
     addi  $30, $zero, overlayInfo1
    lw    $23, data08F0
    lw    $28, data08E4
    lw    $27, data08E8
    j     SP_NOOP
     lw    $26, data08EC

// 0x0400188c-0x04001987: bunch of nops
.fill 0xfc, 0
.ifdef F3D_OLD
  .fill 16, 0
.endif

// from G_TRI1
f3d_04001998:
    lh    $11, 0x24($3)
    lh    $8, 0x24($2)
    lh    $9, 0x24($1)
    and   $12, $11, $8
    or    $11, $11, $8
    and   $12, $12, $9
    andi  $12, $12, 0x7070
    bne   $12, $zero, SP_NOOP    // all vertices outside screen, return
     or    $11, $11, $9
    andi  $11, $11, 0x4343
    bne   $11, $zero, f3d_04001768   // halfway outside, so trigger clipping routine
f3d_040019c4:
     llv   $v13[0], 0x18($1)     // xy_int after viewport
    llv   $v14[0], 0x18($2)
    llv   $v15[0], 0x18($3)
    lw    $13, 0x04($29)         // geometrymode
    addi  $8, $zero, setupTemp   // setup temp area
    lsv   $v21[0], 0x02($29)
    lsv   $v5[0], 0x06($1)       // w_int p1
    vsub  $v10, $v14, $v13       // p2-p1
    lsv   $v6[0], 0x0e($1)       // w_frac p1
    vsub  $v9, $v15, $v13        // p3-p1
    lsv   $v5[2], 0x06($2)
    vsub  $v12, $v13, $v14       // p1-p2
    lsv   $v6[2], 0x0e($2)
    lsv   $v5[4], 0x06($3)
    lsv   $v6[4], 0x0e($3)
    vmudh $v16, $v9, $v10[1]     // (p3-p1)*((p2-p1)_y)
    lh    $9, 0x1a($1)           // y_int after viewport
    vsar  $v18, $v18, $v18[1]    // high into $v18
    lh    $10, 0x1a($2)
    vsar  $v17, $v17, $v17[0]    // bits 47..31 of ACC
    lh    $11, 0x1a($3)
    vmudh $v16, $v12, $v9[1]     // (p1-p2)*((p3-p1)_y)
    andi  $14, $13, G_CULL_FRONT
    vsar  $v20, $v20, $v20[1]
    andi  $15, $13, G_CULL_BACK
    vsar  $v19, $v19, $v19[0]
    addi  $12, $zero, 0         // now sort p1,p2,p3 by y
@@sort_points_loop:
    slt   $7, $10, $9
    blez  $7, @@f3d_04001a58
     add   $7, $10, $zero       // y2_int < y1_int (after viewport)
    add   $10, $9, $zero        // swap $9/$10 and swap $1/$2
    add   $9, $7, $zero
    addu  $7, $2, $zero
    addu  $2, $1, $zero
    addu  $1, $7, $zero
    xori  $12, $12, 0x0001       // xor that we swapped p1 and p2
    nop                          // interesting place for NOP
@@f3d_04001a58:
    vaddc $v28, $v18, $v20
    slt   $7, $11, $10
    vadd  $v29, $v17, $v19
    blez  $7, @@f3d_04001a88
     add   $7, $11, $zero        // y3_int < y2_int?
    add   $11, $10, $zero        // swap p2, p3
    add   $10, $7, $zero
    addu  $7, $3, $zero
    addu  $3, $2, $zero
    addu  $2, $7, $zero
    j     @@sort_points_loop     // go back to test y1 and new y2
     xori  $12, $12, 0x0001      // xor that we swapped p2 and p3
@@f3d_04001a88:
    vlt   $v27, $v29, $v31[0]
    llv   $v15[0], 0x18($3)      // xy_int after viewport for new p3
    vor   $v26, $v29, $v28
    llv   $v14[0], 0x18($2)
    llv   $v13[0], 0x18($1)
    blez  $12, @@f3d_04001ab0    // skip if even number of swaps
     vsub  $v4, $v15, $v14       // p3-p2
    vmudn $v28, $v28, $v31[3]
    vmadh $v29, $v29, $v31[3]
    vmadn $v28, $v31, $v31[0]
@@f3d_04001ab0:
    vsub  $v10, $v14, $v13       // p2-p1
    mfc2  $17, $v27[0]
    vsub  $v9, $v15, $v13        // p3-p1
    mfc2  $16, $v26[0]
    sra   $17, $17, 31
    vmov  $v29[3], $v29[0]
    and   $15, $15, $17
    vmov  $v28[3], $v28[0]
    vmov  $v4[2], $v10[0]
    beq   $16, $zero, @@f3d_04001fd0 // skip this triangle?
     xori  $17, $17, 0xffff
    vlt   $v27, $v29, $v31[0]
    and   $14, $14, $17
    vmov  $v4[3], $v10[1]
    or    $16, $15, $14
    vmov  $v4[4], $v9[0]
    bgtz  $16, @@f3d_04001fd0
     vmov  $v4[5], $v9[1]
    mfc2  $7, $v27[0]
    jal   f3d_04001000
     addi  $6, $zero, 0x80       // left major flag
    bltz  $7, @@f3d_04001b10
     lb    $5, 0x07($29)         // low byte for geometrymode
    addi  $6, $zero, 0
@@f3d_04001b10:
    vmudm $v9, $v4, $v31[4]
    vmadn $v10, $v31, $v31[0]
    vrcp  $v8[1], $v4[1]
    vrcph $v7[1], $v31[0]
    ori   $5, $5, 0x00c8         // OR with RDP command code
    lb    $7, 0x12($29)          // mpmap level and tile ID
    vrcp  $v8[3], $v4[3]
    vrcph $v7[3], $v31[0]
    vrcp  $v8[5], $v4[5]
    vrcph $v7[5], $v31[0]
    or    $6, $6, $7
    vmudl $v8, $v8, $v30[4]
    sb    $5, 0x00($23)
    vmadm $v7, $v7, $v30[4]
    sb    $6, 0x01($23)
    vmadn $v8, $v31, $v31[0]
    vmudh $v4, $v4, $v31[5]
    lsv   $v12[0], 0x18($2)
    vmudl $v6, $v6, $v21[0]
    lsv   $v12[4], 0x18($1)
    vmadm $v5, $v5, $v21[0]
    lsv   $v12[8], 0x18($1)
    vmadn $v6, $v31, $v31[0]
    sll   $7, $9, 14
    vmudl $v1, $v8, $v10[0q]
    vmadm $v1, $v7, $v10[0q]
    vmadn $v1, $v8, $v9[0q]
    vmadh $v0, $v7, $v9[0q]
    mtc2  $7, $v2[0]
    vmadn $v1, $v31, $v31[0]
    sw    $3, 0x00($8)
    vmudl $v8, $v8, $v31[4]
    vmadm $v7, $v7, $v31[4]
    vmadn $v8, $v31, $v31[0]
    vmudl $v1, $v1, $v31[4]
    vmadm $v0, $v0, $v31[4]
    vmadn $v1, $v31, $v31[0]
    sh    $11, 0x02($23)         // YL
    vand  $v16, $v1, $v30[1]
    sh    $9, 0x06($23)          // YH
    vmudm $v12, $v12, $v31[4]
    sw    $2, 0x04($8)
    vmadn $v13, $v31, $v31[0]
    sw    $1, 0x08($8)
    sh    $10, 0x04($23)         // YM
    vcr   $v0, $v0, $v30[6]
    ssv   $v12[0], 0x08($23)     // XL
    vmudl $v11, $v16, $v2[0]
    ssv   $v13[0], 0x0a($23)     // XL, frac
    vmadm $v10, $v0, $v2[0]
    ssv   $v0[2], 0x0c($23)      // DxLDy
    vmadn $v11, $v31, $v31[0]
    ssv   $v1[2], 0x0e($23)      // DxLDy, frac
    andi  $7, $5, G_TEXTURE_ENABLE
    addi  $15, $8, 8
    addi  $16, $8, 0x10
    vsubc $v3, $v13, $v11[1q]
    ssv   $v0[10], 0x14($23)     // DxHDy
    vsub  $v9, $v12, $v10[1q]
    ssv   $v1[10], 0x16($23)     // DxHDy, frac
    vsubc $v21, $v6, $v6[1]
    ssv   $v0[6], 0x1c($23)      // DxMDy
    vlt   $v19, $v5, $v5[1]
    ssv   $v1[6], 0x1e($23)      // DxMDy, frac
    vmrg  $v20, $v6, $v6[1]
    ssv   $v9[8], 0x10($23)      // XH
    vsubc $v21, $v20, $v6[2]
    ssv   $v3[8], 0x12($23)      // XH, frac
    vlt   $v19, $v19, $v5[2]
    ssv   $v9[4], 0x18($23)      // XM
    vmrg  $v20, $v20, $v6[2]
    ssv   $v3[4], 0x1a($23)      // XM, frac
    addi  $23, $23, 0x20
    blez  $7, @@f3d_04001cfc     // no texture?
     vmudl $v20, $v20, $v30[5]
    lw    $14, 0x00($15)
    vmadm $v19, $v19, $v30[5]
    lw    $17, -0x04($15)
    vmadn $v20, $v31, $v31[0]
    lw    $18, -0x08($15)
    llv   $v9[0], 0x14($14)
    llv   $v9[8], 0x14($17)
    llv   $v22[0], 0x14($18)
    lsv   $v11[0], 0x22($14)
    lsv   $v12[0], 0x20($14)
    lsv   $v11[8], 0x22($17)
    vmov  $v9[2], $v30[0]
    lsv   $v12[8], 0x20($17)
    vmov  $v9[6], $v30[0]
    lsv   $v24[0], 0x22($18)
    vmov  $v22[2], $v30[0]
    lsv   $v25[0], 0x20($18)
    vmudl $v6, $v11, $v20[0]
    vmadm $v6, $v12, $v20[0]
    ssv   $v19[0], 0x44($8)
    vmadn $v6, $v11, $v19[0]
    ssv   $v20[0], 0x4c($8)
    vmadh $v5, $v12, $v19[0]
    vmudl $v16, $v24, $v20[0]
    vmadm $v16, $v25, $v20[0]
    vmadn $v20, $v24, $v19[0]
    vmadh $v19, $v25, $v19[0]
    vmudm $v16, $v9, $v6[0h]
    vmadh $v9, $v9, $v5[0h]
    vmadn $v10, $v31, $v31[0]
    vmudm $v16, $v22, $v20[0]
    vmadh $v22, $v22, $v19[0]
    vmadn $v23, $v31, $v31[0]
    sdv   $v9[8], 0x10($16)
    sdv   $v10[8], 0x18($16)
    sdv   $v9[0], 0x00($16)
    sdv   $v10[0], 0x08($16)
    sdv   $v22[0], 0x20($16)
    sdv   $v23[0], 0x28($16)
    vabs  $v9, $v9, $v9
    llv   $v19[0], 0x10($16)
    vabs  $v22, $v22, $v22
    llv   $v20[0], 0x18($16)
    vabs  $v19, $v19, $v19
    vge   $v17, $v9, $v22
    vmrg  $v18, $v10, $v23
    vge   $v17, $v17, $v19
    vmrg  $v18, $v18, $v20
@@f3d_04001cfc:
    slv   $v17[0], 0x40($8)
    slv   $v18[0], 0x48($8)
    andi  $7, $5, (G_SHADE | G_TEXTURE_ENABLE | G_ZBUFFER)
    blez  $7, @@f3d_04001fcc     // skip code below if no bits set
     vxor  $v18, $v31, $v31
    luv   $v25[0], 0x10($3)
    vadd  $v16, $v18, $v30[5]
    luv   $v15[0], 0x10($1)
    vadd  $v24, $v18, $v30[5]
    andi  $7, $13, 0x0200
    vadd  $v5, $v18, $v30[5]
    bgtz  $7, @@f3d_04001d3c
     luv   $v23[0], 0x10($2)
    luv   $v25[0], 0x10($4)
    luv   $v15[0], 0x10($4)
    luv   $v23[0], 0x10($4)
@@f3d_04001d3c:
    vmudm $v25, $v25, $v31[7]
    vmudm $v15, $v15, $v31[7]
    vmudm $v23, $v23, $v31[7]
    ldv   $v16[8], 0x18($8)
    ldv   $v15[8], 0x10($8)
    ldv   $v24[8], 0x28($8)
    ldv   $v23[8], 0x20($8)
    ldv   $v5[8], 0x38($8)
    ldv   $v25[8], 0x30($8)
    lsv   $v16[14], 0x1e($1)
    lsv   $v15[14], 0x1c($1)
    lsv   $v24[14], 0x1e($2)
    lsv   $v23[14], 0x1c($2)
    lsv   $v5[14], 0x1e($3)
    lsv   $v25[14], 0x1c($3)
    vsubc $v12, $v24, $v16
    vsub  $v11, $v23, $v15
    vsubc $v20, $v16, $v5
    vsub  $v19, $v15, $v25
    vsubc $v10, $v5, $v16
    vsub  $v9, $v25, $v15
    vsubc $v22, $v16, $v24
    vsub  $v21, $v15, $v23
    vmudn $v6, $v10, $v4[3]
    vmadh $v6, $v9, $v4[3]
    vmadn $v6, $v22, $v4[5]
    vmadh $v6, $v21, $v4[5]
    vsar  $v9, $v9, $v9[0]
    vsar  $v10, $v10, $v10[1]
    vmudn $v6, $v12, $v4[4]
    vmadh $v6, $v11, $v4[4]
    vmadn $v6, $v20, $v4[2]
    vmadh $v6, $v19, $v4[2]
    vsar  $v11, $v11, $v11[0]
    vsar  $v12, $v12, $v12[1]
    vmudl $v6, $v10, $v26[3]
    vmadm $v6, $v9, $v26[3]
    vmadn $v10, $v10, $v27[3]
    vmadh $v9, $v9, $v27[3]
    vmudl $v6, $v12, $v26[3]
    vmadm $v6, $v11, $v26[3]
    vmadn $v12, $v12, $v27[3]
    sdv   $v9[0], 0x08($23)
    vmadh $v11, $v11, $v27[3]
    sdv   $v10[0], 0x18($23)
    vmudn $v6, $v12, $v31[1]
    vmadh $v6, $v11, $v31[1]
    vmadl $v6, $v10, $v1[5]
    vmadm $v6, $v9, $v1[5]
    vmadn $v14, $v10, $v0[5]
    sdv   $v11[0], 0x28($23)
    vmadh $v13, $v9, $v0[5]
    sdv   $v12[0], 0x38($23)
    vmudl $v28, $v14, $v2[0]
    sdv   $v13[0], 0x20($23)
    vmadm $v6, $v13, $v2[0]
    sdv   $v14[0], 0x30($23)
    vmadn $v28, $v31, $v31[0]
    vsubc $v18, $v16, $v28
    vsub  $v17, $v15, $v6
    andi  $7, $5, G_SHADE
    blez  $7, @@f3d_04001e44
     andi  $7, $5, G_TEXTURE_ENABLE
    addi  $23, $23, 0x40
    sdv   $v17[0], -0x40($23)
    sdv   $v18[0], -0x30($23)
@@f3d_04001e44:
    blez  $7, @@f3d_04001f48
     andi  $7, $5, G_ZBUFFER
    addi  $16, $zero, 0x0800
    mtc2  $16, $v19[0]
    vabs  $v24, $v9, $v9
    ldv   $v20[8], 0x40($8)
    vabs  $v25, $v11, $v11
    ldv   $v21[8], 0x48($8)
    vmudm $v24, $v24, $v19[0]
    vmadn $v26, $v31, $v31[0]
    vmudm $v25, $v25, $v19[0]
    vmadn $v27, $v31, $v31[0]
    vmudl $v21, $v21, $v19[0]
    vmadm $v20, $v20, $v19[0]
    vmadn $v21, $v31, $v31[0]
    vmudn $v26, $v26, $v31[2]
    vmadh $v24, $v24, $v31[2]
    vmadn $v26, $v31, $v31[0]
    vmadn $v23, $v27, $v31[1]
    vmadh $v22, $v25, $v31[1]
    addi  $16, $zero, 0x40
    vmadn $v6, $v21, $v31[1]
    mtc2  $16, $v19[0]
    vmadh $v5, $v20, $v31[1]
    vsubc $v23, $v6, $v6[5]
    vge   $v5, $v5, $v5[5]
    vmrg  $v6, $v6, $v6[5]
    vsubc $v23, $v6, $v6[6]
    vge   $v5, $v5, $v5[6]
    vmrg  $v6, $v6, $v6[6]
    vmudl $v6, $v6, $v19[0]
    vmadm $v5, $v5, $v19[0]
    vmadn $v6, $v31, $v31[0]
    vrcph $v23[0], $v5[4]
    vrcpl $v6[0], $v6[4]
    vrcph $v5[0], $v31[0]
    vmudn $v6, $v6, $v31[2]
    vmadh $v5, $v5, $v31[2]
    vlt   $v5, $v5, $v31[1]
    vmrg  $v6, $v6, $v31[0]
    vmudl $v20, $v18, $v6[0]
    vmadm $v20, $v17, $v6[0]
    vmadn $v20, $v18, $v5[0]
    vmadh $v19, $v17, $v5[0]
    vmudl $v22, $v10, $v6[0]
    vmadm $v22, $v9, $v6[0]
    vmadn $v22, $v10, $v5[0]
    sdv   $v19[8], 0x00($23)
    vmadh $v21, $v9, $v5[0]
    sdv   $v20[8], 0x10($23)
    vmudl $v24, $v12, $v6[0]
    vmadm $v24, $v11, $v6[0]
    vmadn $v24, $v12, $v5[0]
    sdv   $v21[8], 0x08($23)
    vmadh $v23, $v11, $v5[0]
    sdv   $v22[8], 0x18($23)
    vmudl $v26, $v14, $v6[0]
    vmadm $v26, $v13, $v6[0]
    vmadn $v26, $v14, $v5[0]
    sdv   $v23[8], 0x28($23)
    vmadh $v25, $v13, $v5[0]
    sdv   $v24[8], 0x38($23)
    addi  $23, $23, 0x40
    sdv   $v25[8], -0x20($23)
    sdv   $v26[8], -0x10($23)
@@f3d_04001f48:
    blez  $7, @@f3d_04001fcc
     vmudn $v14, $v14, $v30[4]
    vmadh $v13, $v13, $v30[4]
    vmadn $v14, $v31, $v31[0]
    vmudn $v16, $v16, $v30[4]
    vmadh $v15, $v15, $v30[4]
    vmadn $v16, $v31, $v31[0]
    ssv   $v13[14], 0x08($23)
    vmudn $v10, $v10, $v30[4]
    ssv   $v14[14], 0x0a($23)
    vmadh $v9, $v9, $v30[4]
    vmadn $v10, $v31, $v31[0]
    vmudn $v12, $v12, $v30[4]
    vmadh $v11, $v11, $v30[4]
    vmadn $v12, $v31, $v31[0]
    lbu   $7, 0x11($29)
    sub   $7, $zero, $7
    beq   $7, $zero, @@f3d_04001f9c
     mtc2  $7, $v6[0]
    vch   $v11, $v11, $v6[0]
    vcl   $v12, $v12, $v31[0]
@@f3d_04001f9c:
    ssv   $v9[14], 0x04($23)
    vmudl $v28, $v14, $v2[0]
    ssv   $v10[14], 0x06($23)
    vmadm $v6, $v13, $v2[0]
    ssv   $v11[14], 0x0c($23)
    vmadn $v28, $v31, $v31[0]
    ssv   $v12[14], 0x0e($23)
    vsubc $v18, $v16, $v28
    vsub  $v17, $v15, $v6
    addi  $23, $23, 0x10
    ssv   $v17[14], -0x10($23)
    ssv   $v18[14], -0x0e($23)
@@f3d_04001fcc:
    jal   f3d_04001178
@@f3d_04001fd0:
     nop
    jr    $30
     nop
    nop
Overlay0End:

// Overlay 1
.headersize 0x04001000 - orga()
.definelabel Overlay1LoadStart, orga()
// reciprocal method, see RSP Programmers Guide page 79
// $v29[3]=s_int, $v28[3]=s_frac, $v29[7]=t_int, $v28[7]=t_frac
// out: $v27[3,7]=s,t int, $v26[3,7]=s,t frac
Overlay1Address:
f3d_04001000:
    vrcph $v27[3], $v29[3]
    vrcpl $v26[3], $v28[3]
    vrcph $v27[3], $v29[7]
    vrcpl $v26[7], $v28[7]
    vrcph $v27[7], $v31[0]
    vmudn $v26, $v26, $v31[2] // 0002, << 1 since input is S15.16
    vmadh $v27, $v27, $v31[2]
    vmadn $v26, $v31, $v31[0]
    // $v27[3]=sres_int, $v26[3]=sres_frac, $v27[7]=tres_int, $v26[7]=tres_frac
    lqv   $v23[0], 0x60($zero)
    vxor  $v22, $v31, $v31    // (1/w)*w
    vmudl $v24, $v26, $v28
    vmadm $v24, $v27, $v28
    vmadn $v24, $v26, $v29
    vmadh $v25, $v27, $v29
    // $v24=frac, $v25=int, should be very close to 1.0
    vsubc $v24, $v22, $v24    // take 2.0-result (better rounding?)
    vsub  $v25, $v23, $v25
    vmudl $v22, $v26, $v24    // (2.0-(1/w)*w)*(1/w)
    vmadm $v23, $v27, $v24
    vmadn $v26, $v26, $v25
    vmadh $v27, $v27, $v25
    jr    $ra
     nop

dma_wait_dl:
    jal   wait_while_dma_busy
     addi  $27, $zero, inputDisplayList

read_next_task_entry:
    lw    $25, 0x00($27)     // first command word
    lw    $24, 0x04($27)     // second command word
    srl   $1, $25, 29
    andi  $1, $1, 0x0006     // $1 = (two MSbits) << 1
    addi  $26, $26, 8        // increase next task in DRAM ptr
    addi  $27, $27, 8        // increase next task in DMEM ptr
    addi  $28, $28, -8       // decrease task count left in DMEM
    bgtz  $1, dispatch_task
     andi  $18, $25, 0x01ff
    addi  $22, $zero, inputData  // command that loads data input
Overlay1End:


// Overlay 2
.headersize 0x04001768 - orga()
Overlay2Address:
    b     perform_clip
     sh    $ra, data0158
    nop
    nop
    ori   $30, $zero, overlayInfo3
    b     load_overlay
     lh    $21, lightEntry

perform_clip:
    sh    $3, clipTemp
    sh    $2, data0942
    sh    $1, data0944
    sh    $zero, data0946
    ori   $7, $zero, 0x0db8
    ori   $30, $zero, clipTemp
    ori   $6, $zero, 0x000c
next_clip:
    or    $5, $30, $30
    xori  $30, $30, 0x0014
f3d_040017a8:
    beq   $6, $zero, @f3d_04001954
     lh    $11, 0xa6($6)
    addi  $6, $6, -2
    ori   $17, $zero, 0x0000
    or    $18, $zero, $zero

found_in:
    ori   $2, $5, 0x0000

found_out:
    j     f3d_040017d4
     addi  $14, $30, 2
f3d_040017c8:
    and   $8, $8, $11
    beq   $8, $18, f3d_o2_04001804
     addi  $2, $2, 2
f3d_040017d4:
    or    $20, $10, $zero
    sh    $10, 0x00($14)
    addi  $14, $14, 2
f3d_040017e0:
    lh    $10, 0x00($2)
    bne   $10, $zero, f3d_040017c8
     lh    $8, 0x24($10)
    addi  $8, $17, -2
    bgtz  $8, f3d_040017e0
     ori   $2, $5, 0x0000
    beq   $8, $zero, f3d_040017a8
     nop
    j     f3d_04001980
f3d_o2_04001804:
     xor   $18, $18, $11
    lh    $8, lo(labelLUT)($17)
    addi  $17, $17, 2
    jr    $8
     lh    $8, nextClipEntry

found_first_in:
    mtc2  $10, $v13[0]
    or    $10, $20, $zero
    mfc2  $20, $v13[0]
    ori   $14, $30, 0x0000
    lh    $8, foundOutEntry

found_first_out:
    sh    $8, data0106
    addi  $7, $7, 0x28
    sh    $7, 0x00($14)
    sh    $zero, 0x02($14)
    ldv   $v9[0], 0x00($10)
    ldv   $v10[0], 0x08($10)
    ldv   $v4[0], 0x00($20)
    ldv   $v5[0], 0x08($20)
    sll   $8, $6, 2
    ldv   $v1[0], 0x70($8)
    vmudh $v0, $v1, $v31[3]
    vmudn $v12, $v5, $v1
    vmadh $v11, $v4, $v1
    vmadn $v12, $v31, $v31[0]
    vmadn $v28, $v10, $v0
    vmadh $v29, $v9, $v0
    vmadn $v28, $v31, $v31[0]
    vaddc $v26, $v28, $v28[0q]
    vadd  $v27, $v29, $v29[0q]
    vaddc $v28, $v26, $v26[1h]
    vadd  $v29, $v27, $v27[1h]
    mfc2  $8, $v29[6]
    vrcph $v7[3], $v29[3]
    vrcpl $v3[3], $v28[3]
    vrcph $v7[3], $v31[0]
    vmudn $v3, $v3, $v31[2]
    bgez  $8, f3d_040018a4
     vmadh $v7, $v7, $v31[2]
    vmudn $v3, $v3, $v31[3]
    vmadh $v7, $v7, $v31[3]
f3d_040018a4:
    veq   $v7, $v7, $v31[0]
    vmrg  $v3, $v3, $v31[3]
    vmudl $v28, $v28, $v3[3]
    vmadm $v29, $v29, $v3[3]
    jal   f3d_04001000
     vmadn $v28, $v31, $v31[0]
    vaddc $v28, $v12, $v12[0q]
    vadd  $v29, $v11, $v11[0q]
    vaddc $v12, $v28, $v28[1h]
    vadd  $v11, $v29, $v29[1h]
    vmudl $v15, $v12, $v26
    vmadm $v15, $v11, $v26
    vmadn $v15, $v12, $v27
    vmadh $v8, $v11, $v27
    vmudl $v28, $v31, $v31[5]
    vmadl $v15, $v15, $v3[3]
    vmadm $v8, $v8, $v3[3]
    vmadn $v15, $v31, $v31[0]
    veq   $v8, $v8, $v31[0]
    vmrg  $v15, $v15, $v31[3]
    vne   $v15, $v15, $v31[0]
    vmrg  $v15, $v15, $v31[1]
    vnxor $v8, $v15, $v31[0]
    vaddc $v8, $v8, $v31[1]
    vadd  $v29, $v29, $v29
    vmudl $v28, $v5, $v8[3h]
    vmadm $v29, $v4, $v8[3h]
    vmadl $v28, $v10, $v15[3h]
    vmadm $v29, $v9, $v15[3h]
    vmadn $v28, $v31, $v31[0]
    luv   $v12[0], 0x10($10)
    luv   $v11[0], 0x10($20)
    llv   $v12[8], 0x14($10)
    llv   $v11[8], 0x14($20)
    vmudm $v18, $v12, $v15[3]
    vmadm $v18, $v11, $v8[3]
    suv   $v18[0], 0x00($7)
    sdv   $v18[8], 0x08($7)
    ldv   $v18[0], 0x08($7)
    jal   f3d_040014e8
     lw    $15, 0x00($7)
    mfc2  $10, $v13[0]
    j     @f3d_040015e8
     ori   $9, $zero, 0x0001
@f3d_04001954:
    lh    $8, 0x00($5)
    sh    $8, 0xb4($zero)
    sh    $5, data0106
    lh    $30, clipDrawEntry

clip_draw_loop:
    lh    $8, data0106
    lh    $3, 0xb4($zero)
    lh    $2, 0x02($8)
    lh    $1, 0x04($8)
    addi  $8, $8, 2
    bne   $1, $zero, f3d_040019c4
     sh    $8, data0106
f3d_04001980:
    j     SP_NOOP
     nop
Overlay2End:

// Overlay 3
.headersize 0x04001768 - orga()
Overlay3Address:
    ori   $30, $zero, overlayInfo2
    b     load_overlay
     lh    $21, performClipEntry
    lw    $1, numLights
    sw    $15, 0x00($7)        // normal vector 1st vertex
    sw    $16, 0x04($7)        // normal vector 2nd vertex
    bltz  $1, @init_lights
     lpv   $v4[0], 0x00($7)
    luv   $v7[0], 0x01d0($1)   // ambient RGB
    vxor  $v27, $v27, $v27
@@f3d_04001790:
    vge   $v7, $v7, $v31[0]    // max(0, $v7)
    lpv   $v5[0], 0x01c0($1)   // calculated light
    vadd  $v27, $v27, $v7
    luv   $v7[0], 0x01b0($1)   // light's RGB
    vor   $v20, $v6, $v31[0]
    vmulf $v6, $v4, $v5        // mul normal vector
    vadd  $v3, $v6, $v6[1q]
    vadd  $v6, $v3, $v6[2h]
    vmulf $v7, $v7, $v6[0h]    // $v6[0] and $v6[4] contain dot product
    bgtz  $1, @@f3d_04001790
     addi  $1, $1, -0x20
    suv   $v27[0], 0x00($7)
    andi  $8, $3, G_TEXTURE_GEN_H
    sb    $15, 0x03($7)
    sb    $16, 0x07($7)
    lw    $15, 0x00($7)
    beq   $8, $zero, @f3d_040015e4
     lw    $16, 0x04($7)
    andi  $8, $3, G_TEXTURE_GEN_LINEAR_H // not used in SM64
    lpv   $v7[0], 0x90($29)
    ldv   $v6[0], 0xa0($zero)
    vmadn $v20, $v7, $v20[0h]
    beq   $8, $zero, @@f3d_o3_04001804
     vmadm $v18, $v31, $v31[0]
    vmulf $v7, $v18, $v18
    vmulf $v7, $v7, $v18
    vmulf $v20, $v7, $v6[1]
    vmacf $v20, $v7, $v6[3]
    vmacf $v18, $v18, $v6[2]
@@f3d_o3_04001804:
    j     @f3d_040015e4
     vadd  $v18, $v18, $v31[4]

@init_lights:
    andi  $1, $1, 0x0fff
    sw    $1, numLights
    jal   f3d_04001524
     addi  $8, $zero, modelViewMatrixStack
    ori   $8, $zero, scratchSpace
    stv   $v8[2], 0x10($8)     // transpose
    stv   $v8[4], 0x20($8)
    stv   $v8[12], 0x30($8)
    stv   $v8[14], 0x40($8)
    ltv   $v8[14], 0x10($8)
    ltv   $v8[12], 0x20($8)
    ltv   $v8[4], 0x30($8)
    ltv   $v8[2], 0x40($8)
    sdv   $v12[8], 0x10($8)
    sdv   $v13[8], 0x20($8)
    sdv   $v14[8], 0x30($8)
    ldv   $v12[0], 0x10($8)
    ldv   $v13[0], 0x20($8)
    ldv   $v14[0], 0x30($8)
f3d_04001858:
    lpv   $v5[0], 0x01b8($1)   // this light's dir vector
    vmulf $v5, $v5, $v31[4]
    vmudn $v6, $v12, $v5[0h]
    vmadn $v6, $v13, $v5[1h]
    vmadn $v6, $v14, $v5[2h]
    vmadm $v3, $v31, $v31[0]
    vmudm $v6, $v3, $v31[2]
    vmacf $v3, $v8, $v5[0h]
    vmacf $v3, $v9, $v5[1h]
    vmacf $v3, $v10, $v5[2h]
    vmadn $v6, $v31, $v31[0]
    vmudl $v5, $v6, $v6
    vmadm $v5, $v3, $v6
    vmadn $v5, $v6, $v3
    vmadh $v26, $v3, $v3
    vaddc $v7, $v5, $v5[1q]
    vadd  $v4, $v26, $v26[1q]
    vaddc $v7, $v5, $v7[0h]
    vadd  $v4, $v26, $v4[0h]
    vrsqh $v11[0], $v4[2]      // normalize vector
    vrsql $v15[0], $v7[2]
    vrsqh $v11[0], $v31[0]
    vmudl $v15, $v15, $v30[3]
    vmadm $v11, $v11, $v30[3]
    vmadn $v15, $v31, $v31[0]
    vmudl $v7, $v6, $v15[0]
    vmadm $v7, $v3, $v15[0]
    vmadn $v7, $v6, $v11[0]
    vmadh $v4, $v3, $v11[0]
    vmadn $v7, $v31, $v31[0]
    ldv   $v2[0], 0xf8($29)
    vge   $v7, $v7, $v2[0]
    vlt   $v7, $v7, $v2[1]
    vmudn $v7, $v7, $v2[2]
    spv   $v7[0], 0x01c0($1)
    lw    $8, 0x01c0($1)
    sw    $8, 0x01c4($1)
    bgtz  $1, f3d_04001858
     addi  $1, $1, -0x20
    j     load_mp_matrix
     lh    $ra, lightEntry
    nop
Overlay3End:

// Overlay 4
.headersize 0x04001768 - orga()
Overlay4Address:
    j     f3d_04001788
     nop
overlay_4_entry:
    nop
    jal   wait_while_dma_busy
     ori   $2, $zero, 0x4000
    mtc0  $2, SP_STATUS
    break
    nop
f3d_04001788:
    ori   $2, $zero, 0x1000
    sw    $28, data08E4
    sw    $27, data08E8
    sw    $26, data08EC
    sw    $23, data08F0
    lw    $19, dramPtr
    ori   $20, $zero, 0x0000
    ori   $18, $zero, 0x08ff
    jal   dma_read_write
     ori   $17, $zero, 0x0001
    jal   wait_while_dma_busy
     nop
    j     f3d_040010c8
     mtc0  $2, SP_STATUS
    nop
    nop
    addiu $zero, $zero, 0xbeef
    nop
Overlay4End:

.close // CODE_FILE
