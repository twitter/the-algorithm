.set noat      # allow manual use of $at
.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"

.ifdef VERSION_SH
.set VERSION_EU, 1 # HACK, someone fix this file, its poorly diff'd!
.endif

.section .text, "ax"

.ifdef AVOID_UB
.set D_80334890, D_80334890_fix
.endif

glabel __osExceptionPreamble
    lui   $k0, %hi(__osException) # $k0, 0x8032
    addiu $k0, %lo(__osException) # addiu $k0, $k0, 0x66d0
    jr    $k0
     nop


glabel __osException
.ifndef VERSION_EU
    lui   $k0, %hi(gInterruptedThread) # $k0, 0x8036
    addiu $k0, %lo(gInterruptedThread) # addiu $k0, $k0, 0x5f40
    sd    $at, 0x20($k0)
    mfc0  $k1, $12
    sw    $k1, 0x118($k0)
    li    $at, -4
    and   $k1, $k1, $at
    mtc0  $k1, $12
    sd    $t0, 0x58($k0)
    sd    $t1, 0x60($k0)
    sd    $t2, 0x68($k0)
    sw    $zero, 0x18($k0)
    mfc0  $t0, $13

    andi  $t1, $t0, 0x7c
    li    $t2, 0
    bne   $t1, $t2, .L80326750
     nop
    and   $t1, $k1, $t0
    andi  $t2, $t1, 0x4000
    beqz  $t2, .L80326734
     nop
    li    $t1, 1
    lui   $at, %hi(D_80334934) # $at, 0x8033
    b     .L80326794
     sw    $t1, %lo(D_80334934)($at)
.L80326734:
    andi  $t2, $t1, 0x2000
    beqz  $t2, .L80326750
     nop
    li    $t1, 1
    lui   $at, %hi(D_80334938) # $at, 0x8033
    b     .L80326794
     sw    $t1, %lo(D_80334938)($at)
.L80326750:
    lui   $at, %hi(D_80334934) # $at, 0x8033
    sw    $zero, %lo(D_80334934)($at)
    lui   $at, %hi(D_80334938) # $at, 0x8033

    move  $t0, $k0
    sw    $zero, %lo(D_80334938)($at)
    lui   $k0, %hi(D_80334890 + 0x10) # $k0, 0x8033
    lw    $k0, %lo(D_80334890 + 0x10)($k0)
    ld    $t1, 0x20($t0)
    sd    $t1, 0x20($k0)
    ld    $t1, 0x118($t0)
    sd    $t1, 0x118($k0)
    ld    $t1, 0x58($t0)
    sd    $t1, 0x58($k0)
    ld    $t1, 0x60($t0)
    sd    $t1, 0x60($k0)
    ld    $t1, 0x68($t0)
    sd    $t1, 0x68($k0)
.L80326794:
    mflo  $t0
    sd    $t0, 0x108($k0)
    mfhi  $t0
    sd    $v0, 0x28($k0)
    sd    $v1, 0x30($k0)
    sd    $a0, 0x38($k0)
    sd    $a1, 0x40($k0)
    sd    $a2, 0x48($k0)
    sd    $a3, 0x50($k0)
    sd    $t3, 0x70($k0)
    sd    $t4, 0x78($k0)
    sd    $t5, 0x80($k0)
    sd    $t6, 0x88($k0)
    sd    $t7, 0x90($k0)
    sd    $s0, 0x98($k0)
    sd    $s1, 0xa0($k0)
    sd    $s2, 0xa8($k0)
    sd    $s3, 0xb0($k0)
    sd    $s4, 0xb8($k0)
    sd    $s5, 0xc0($k0)
    sd    $s6, 0xc8($k0)
    sd    $s7, 0xd0($k0)
    sd    $t8, 0xd8($k0)
    sd    $t9, 0xe0($k0)
    sd    $gp, 0xe8($k0)
    sd    $sp, 0xf0($k0)
    sd    $fp, 0xf8($k0)
    sd    $ra, 0x100($k0)
.ifndef VERSION_EU
    sd    $t0, 0x110($k0)
.else
    beqz $t1, .L802F3A18
    sd    $t0, 0x110($k0)
    lui   $t0, %hi(D_8030208C) # $t0, 0x8030
    addiu $t0, $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
    lw    $t0, ($t0)
    li    $at, -1
    xor   $t0, $t0, $at
    lui   $at, (0xFFFF00FF >> 16) # lui $at, 0xffff
    andi  $t0, $t0, 0xff00
    ori   $at, (0xFFFF00FF & 0xFFFF) # ori $at, $at, 0xff
    or    $t1, $t1, $t0
    and   $k1, $k1, $at
    or    $k1, $k1, $t1
    sw    $k1, 0x118($k0)
.L802F3A18:
    lui   $t1, %hi(MI_INTR_MASK_REG) # $t1, 0xa430
    lw    $t1, %lo(MI_INTR_MASK_REG)($t1)
    beqz  $t1, .L802F3A50
     nop   
    lui   $t0, %hi(D_8030208C) # $t0, 0x8030
    addiu $t0, $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
    lw    $t0, ($t0)
    lw    $t4, 0x128($k0)
    li    $at, -1
    srl   $t0, $t0, 0x10
    xor   $t0, $t0, $at
    andi  $t0, $t0, 0x3f
    and   $t0, $t0, $t4
    or    $t1, $t1, $t0
.L802F3A50:
    sw    $t1, 0x128($k0)
.endif


    mfc0  $t0, $14
    sw    $t0, 0x11c($k0)
    lw    $t0, 0x18($k0)
    beqz  $t0, .L80326868
     nop
    cfc1  $t0, $31
    nop
    sw    $t0, 0x12c($k0)
    sdc1  $f0, 0x130($k0)
    sdc1  $f2, 0x138($k0)
    sdc1  $f4, 0x140($k0)
    sdc1  $f6, 0x148($k0)
    sdc1  $f8, 0x150($k0)
    sdc1  $f10, 0x158($k0)
    sdc1  $f12, 0x160($k0)
    sdc1  $f14, 0x168($k0)
    sdc1  $f16, 0x170($k0)
    sdc1  $f18, 0x178($k0)
    sdc1  $f20, 0x180($k0)
    sdc1  $f22, 0x188($k0)
    sdc1  $f24, 0x190($k0)
    sdc1  $f26, 0x198($k0)
    sdc1  $f28, 0x1a0($k0)
    sdc1  $f30, 0x1a8($k0)
.L80326868:
    mfc0  $t0, $13
    sw    $t0, 0x120($k0)
    lui   $t1, %hi(MI_INTR_MASK_REG) # $t1, 0xa430
    lw    $t1, %lo(MI_INTR_MASK_REG)($t1)
    sw    $t1, 0x128($k0)
    li    $t1, 2
    sh    $t1, 0x10($k0)
    lui   $t1, %hi(D_80334934) # $t1, 0x8033
    lw    $t1, %lo(D_80334934)($t1)
    beqz  $t1, .L803268B4
     nop
    lui   $t2, %hi(D_C0000008) # $t2, 0xc000
    sw    $zero, %lo(D_C0000008)($t2)
    lui   $a0, %hi(D_C0000000)
    addiu $t2, %lo(D_C0000008) # addiu $t2, $t2, 8
    jal   kdebugserver
     lw    $a0, %lo(D_C0000000)($a0)
    b     .L80326E08
     nop
.L803268B4:
    lui   $t1, %hi(D_80334938) # $t1, 0x8033
    lw    $t1, %lo(D_80334938)($t1)
    beqz  $t1, .L80326900
     nop
    lui   $t2, %hi(D_C000000C) # $t2, 0xc000
    sw    $zero, %lo(D_C000000C)($t2)
    lui   $t1, %hi(D_80334A40) # $t1, 0x8033
    lw    $t1, %lo(D_80334A40)($t1)
    addiu $t2, %lo(D_C000000C) # addiu $t2, $t2, 0xc
    beqz  $t1, .L803268E8
     nop
    jal   send_mesg
     li    $a0, 120
.L803268E8:
    lui   $t1, %hi(D_80334A44) # $t1, 0x8033
    lw    $t1, %lo(D_80334A44)($t1)
    lui   $at, %hi(D_80334A44) # $at, 0x8033
    addi  $t1, $t1, 1
    b     .L80326E08
     sw    $t1, %lo(D_80334A44)($at)
.L80326900:
    andi  $t1, $t0, 0x7c
    li    $t2, 36
    beq   $t1, $t2, .L80326B84
     nop
    li    $t2, 44
    beq   $t1, $t2, .L80326CCC
     nop
    li    $t2, 0
    bne   $t1, $t2, .L80326BE8
     nop
    and   $s0, $k1, $t0
.L8032692C:
    andi  $t1, $s0, 0xff00
    srl   $t2, $t1, 0xc
    bnez  $t2, .L80326944
     nop
    srl   $t2, $t1, 8
    addi  $t2, $t2, 0x10
.L80326944:
    lui   $at, %hi(D_80338610)
    addu  $at, $at, $t2
    lbu   $t2, %lo(D_80338610)($at)
    lui   $at, %hi(jtbl_80338630)
    addu  $at, $at, $t2
    lw    $t2, %lo(jtbl_80338630)($at)
    jr    $t2
     nop
glabel L80326964
    mfc0  $t1, $11
    mtc0  $t1, $11
    jal   send_mesg
     li    $a0, 24
    lui   $at, (0xFFFF7FFF >> 16) # lui $at, 0xffff
    ori   $at, (0xFFFF7FFF & 0xFFFF) # ori $at, $at, 0x7fff
    b     .L8032692C
     and   $s0, $s0, $at
glabel L80326984
    li    $t2, 4
    lui   $at, %hi(D_80334920)
    addu  $at, $at, $t2
    lw    $t2, %lo(D_80334920)($at)
    beqz  $t2, .L803269A4
     nop
    jalr  $t2
    nop
.L803269A4:
    jal   send_mesg
     li    $a0, 16
    li    $at, -2049
    b     .L8032692C
     and   $s0, $s0, $at
glabel L803269B8
    lui   $s1, %hi(MI_INTR_REG) # $s1, 0xa430
    lw    $s1, %lo(MI_INTR_REG)($s1)
    andi  $s1, $s1, 0x3f
    andi  $t1, $s1, 1
    beqz  $t1, .L80326A18
     nop
    lui   $t4, %hi(SP_STATUS_REG) # $t4, 0xa404
    lw    $t4, %lo(SP_STATUS_REG)($t4)
    li    $t1, 8
    lui   $at, %hi(SP_STATUS_REG) # $at, 0xa404
    andi  $t4, $t4, 0x300
    andi  $s1, $s1, 0x3e
    beqz  $t4, .L80326A08
     sw    $t1, %lo(SP_STATUS_REG)($at)
    jal   send_mesg
     li    $a0, 32
    beqz  $s1, .L80326ADC
     nop
    b     .L80326A18
     nop
.L80326A08:
    jal   send_mesg
     li    $a0, 88
    beqz  $s1, .L80326ADC
     nop
.L80326A18:
    andi  $t1, $s1, 8
    beqz  $t1, .L80326A3C
     lui   $at, %hi(VI_CURRENT_REG) # $at, 0xa440
    andi  $s1, $s1, 0x37
    sw    $zero, %lo(VI_CURRENT_REG)($at)
    jal   send_mesg
     li    $a0, 56
    beqz  $s1, .L80326ADC
     nop
.L80326A3C:
    andi  $t1, $s1, 4
    beqz  $t1, .L80326A68
     nop
    li    $t1, 1
    lui   $at, %hi(AI_STATUS_REG) # $at, 0xa450
    andi  $s1, $s1, 0x3b
    sw    $t1, %lo(AI_STATUS_REG)($at)
    jal   send_mesg
     li    $a0, 48
    beqz  $s1, .L80326ADC
     nop
.L80326A68:
    andi  $t1, $s1, 2
    beqz  $t1, .L80326A8C
     lui   $at, %hi(SI_STATUS_REG) # $at, 0xa480
    andi  $s1, $s1, 0x3d
    sw    $zero, %lo(SI_STATUS_REG)($at)
    jal   send_mesg
     li    $a0, 40
    beqz  $s1, .L80326ADC
     nop
.L80326A8C:
    andi  $t1, $s1, 0x10
    beqz  $t1, .L80326AB8
     nop
    li    $t1, 2
    lui   $at, %hi(PI_STATUS_REG) # $at, 0xa460
    andi  $s1, $s1, 0x2f
    sw    $t1, %lo(PI_STATUS_REG)($at)
    jal   send_mesg
     li    $a0, 64
    beqz  $s1, .L80326ADC
     nop
.L80326AB8:
    andi  $t1, $s1, 0x20
    beqz  $t1, .L80326ADC
     nop
    li    $t1, 2048
    lui   $at, %hi(MI_MODE_REG)
    andi  $s1, $s1, 0x1f
    sw    $t1, %lo(MI_MODE_REG)($at)
    jal   send_mesg
     li    $a0, 72
.L80326ADC:
    li    $at, -1025
    b     .L8032692C
     and   $s0, $s0, $at
glabel L80326AE8
    lw    $k1, 0x118($k0)
    li    $at, -4097
    lui   $t1, %hi(D_80334808) # $t1, 0x8033
    and   $k1, $k1, $at
    sw    $k1, 0x118($k0)
    addiu $t1, %lo(D_80334808) # addiu $t1, $t1, 0x4808
    lw    $t2, ($t1)
    beqz  $t2, .L80326B14
     li    $at, -4097
    b     .L80326B9C
     and   $s0, $s0, $at
.L80326B14:
    li    $t2, 1
    sw    $t2, ($t1)
    jal   send_mesg
     li    $a0, 112
    lui   $t2, %hi(D_80334890 + 0x8) # $t2, 0x8033
    lw    $t2, %lo(D_80334890 + 0x8)($t2)
    li    $at, -4097
    and   $s0, $s0, $at
    lw    $k1, 0x118($t2)
    and   $k1, $k1, $at
    b     .L80326B9C
     sw    $k1, 0x118($t2)
glabel L80326B44
    li    $at, -513
    and   $t0, $t0, $at
    mtc0  $t0, $13
    jal   send_mesg
     li    $a0, 8
    li    $at, -513
    b     .L8032692C
     and   $s0, $s0, $at
glabel L80326B64
    li    $at, -257
    and   $t0, $t0, $at
    mtc0  $t0, $13
    jal   send_mesg
     li    $a0, 0
    li    $at, -257
    b     .L8032692C
     and   $s0, $s0, $at
.L80326B84:
    li    $t1, 1
    sh    $t1, 0x12($k0)
    jal   send_mesg
     li    $a0, 80
    b     .L80326B9C
     nop
.L80326B9C:
glabel L80326B9C
    lui   $t2, %hi(D_80334890 + 0x8) # $t2, 0x8033
    lw    $t2, %lo(D_80334890 + 0x8)($t2)
    lw    $t1, 4($k0)
    lw    $t3, 4($t2)
    slt   $at, $t1, $t3
    beqz  $at, .L80326BD0
     nop
    lui   $a0, %hi(D_80334890 + 0x8) # $a0, 0x8033
    move  $a1, $k0
    jal   __osEnqueueThread
     addiu $a0, %lo(D_80334890 + 0x8) # addiu $a0, $a0, 0x4898
    j     __osDispatchThread
     nop

.L80326BD0:
    lui   $t1, %hi(D_80334890 + 0x8) # $t1, 0x8033
    addiu $t1, %lo(D_80334890 + 0x8) # addiu $t1, $t1, 0x4898
    lw    $t2, ($t1)
    sw    $t2, ($k0)
    j     __osDispatchThread
     sw    $k0, ($t1)

.L80326BE8:
glabel L80326BE8
    lui   $at, %hi(D_80334890 + 0x14) # $at, 0x8033
    sw    $k0, %lo(D_80334890 + 0x14)($at)
    li    $t1, 1
    sh    $t1, 0x10($k0)
    li    $t1, 2
    sh    $t1, 0x12($k0)
    mfc0  $t2, $8
    sw    $t2, 0x124($k0)
    jal   send_mesg
     li    $a0, 96
    j     __osDispatchThread
     nop
.else
  lui   $k0, %hi(gInterruptedThread) # $k0, 0x8033
  addiu $k0, %lo(gInterruptedThread) # addiu $k0, $k0, 0x6ce0
  sd    $at, 0x20($k0)
  mfc0  $k1, $12
  sw    $k1, 0x118($k0)
  li    $at, -4
  and   $k1, $k1, $at
  mtc0  $k1, $12
  sd    $t0, 0x58($k0)
  sd    $t1, 0x60($k0)
  sd    $t2, 0x68($k0)
  sw    $zero, 0x18($k0)
  mfc0  $t0, $13
  move  $t0, $k0
  lui   $k0, %hi(D_80334890 + 0x10) # $k0, 0x8030
  lw    $k0, %lo(D_80334890 + 0x10)($k0)
  ld    $t1, 0x20($t0)
  sd    $t1, 0x20($k0)
  ld    $t1, 0x118($t0)
  sd    $t1, 0x118($k0)
  ld    $t1, 0x58($t0)
  sd    $t1, 0x58($k0)
  ld    $t1, 0x60($t0)
  sd    $t1, 0x60($k0)
  ld    $t1, 0x68($t0)
  sd    $t1, 0x68($k0)
  lw    $k1, 0x118($k0)
  mflo  $t0
  sd    $t0, 0x108($k0)
  mfhi  $t0
  andi  $t1, $k1, 0xff00
  sd    $v0, 0x28($k0)
  sd    $v1, 0x30($k0)
  sd    $a0, 0x38($k0)
  sd    $a1, 0x40($k0)
  sd    $a2, 0x48($k0)
  sd    $a3, 0x50($k0)
  sd    $t3, 0x70($k0)
  sd    $t4, 0x78($k0)
  sd    $t5, 0x80($k0)
  sd    $t6, 0x88($k0)
  sd    $t7, 0x90($k0)
  sd    $s0, 0x98($k0)
  sd    $s1, 0xa0($k0)
  sd    $s2, 0xa8($k0)
  sd    $s3, 0xb0($k0)
  sd    $s4, 0xb8($k0)
  sd    $s5, 0xc0($k0)
  sd    $s6, 0xc8($k0)
  sd    $s7, 0xd0($k0)
  sd    $t8, 0xd8($k0)
  sd    $t9, 0xe0($k0)
  sd    $gp, 0xe8($k0)
  sd    $sp, 0xf0($k0)
  sd    $fp, 0xf8($k0)
  sd    $ra, 0x100($k0)
  beqz  $t1, .L802F3A18
   sd    $t0, 0x110($k0)
  lui   $t0, %hi(D_8030208C) # $t0, 0x8030
  addiu $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
  lw    $t0, ($t0)
  li    $at, -1
  xor   $t0, $t0, $at
  lui   $at, (0xFFFF00FF >> 16) # lui $at, 0xffff
  andi  $t0, $t0, 0xff00
  ori   $at, (0xFFFF00FF & 0xFFFF) # ori $at, $at, 0xff
  or    $t1, $t1, $t0
  and   $k1, $k1, $at
  or    $k1, $k1, $t1
  sw    $k1, 0x118($k0)
.L802F3A18:
  lui   $t1, %hi(MI_INTR_MASK_REG) # $t1, 0xa430
  lw    $t1, %lo(MI_INTR_MASK_REG)($t1)
  beqz  $t1, .L802F3A50
   nop   
  lui   $t0, %hi(D_8030208C) # $t0, 0x8030
  addiu $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
  lw    $t0, ($t0)
  lw    $t4, 0x128($k0)
  li    $at, -1
  srl   $t0, $t0, 0x10
  xor   $t0, $t0, $at
  andi  $t0, $t0, 0x3f
  and   $t0, $t0, $t4
  or    $t1, $t1, $t0
.L802F3A50:
  sw    $t1, 0x128($k0)
  mfc0  $t0, $14
  sw    $t0, 0x11c($k0)
  lw    $t0, 0x18($k0)
  beqz  $t0, .L802F3AB4
   nop   
  cfc1  $t0, $31
  nop   
  sw    $t0, 0x12c($k0)
  sdc1  $f0, 0x130($k0)
  sdc1  $f2, 0x138($k0)
  sdc1  $f4, 0x140($k0)
  sdc1  $f6, 0x148($k0)
  sdc1  $f8, 0x150($k0)
  sdc1  $f10, 0x158($k0)
  sdc1  $f12, 0x160($k0)
  sdc1  $f14, 0x168($k0)
  sdc1  $f16, 0x170($k0)
  sdc1  $f18, 0x178($k0)
  sdc1  $f20, 0x180($k0)
  sdc1  $f22, 0x188($k0)
  sdc1  $f24, 0x190($k0)
  sdc1  $f26, 0x198($k0)
  sdc1  $f28, 0x1a0($k0)
  sdc1  $f30, 0x1a8($k0)
.L802F3AB4:
  mfc0  $t0, $13
  sw    $t0, 0x120($k0)
  li    $t1, 2
  sh    $t1, 0x10($k0)
  andi  $t1, $t0, 0x7c
  li    $t2, 36
  beq   $t1, $t2, .L802F3D90
   nop   
  li    $t2, 44
  beq   $t1, $t2, .L80326CCC
   nop   
  li    $t2, 0
  bne   $t1, $t2, .L80326BE8
   nop   
  and   $s0, $k1, $t0
.L802F3AF0:
  andi  $t1, $s0, 0xff00
  srl   $t2, $t1, 0xc
  bnez  $t2, .L802F3B08
   nop   
  srl   $t2, $t1, 8
  addi  $t2, $t2, 0x10
.L802F3B08:
  lui   $at, %hi(D_80338610)
  addu  $at, $at, $t2
  lbu   $t2, %lo(D_80338610)($at)
  lui   $at, %hi(jtbl_80338630)
  addu  $at, $at, $t2
  lw    $t2, %lo(jtbl_80338630)($at)
  jr    $t2
   nop   
  li    $at, -8193
  b     .L802F3AF0
   and   $s0, $s0, $at
  li    $at, -16385
  b     .L802F3AF0
   and   $s0, $s0, $at
glabel L80326964 #probably not right...
  mfc0  $t1, $11
  mtc0  $t1, $11
  jal   send_mesg
   li    $a0, 24
  lui   $at, (0xFFFF7FFF >> 16) # lui $at, 0xffff
  ori   $at, (0xFFFF7FFF & 0xFFFF) # ori $at, $at, 0x7fff
  b     .L802F3AF0
   and   $s0, $s0, $at
glabel L80326984 #possibly wrong 
  li    $at, -2049
  and   $s0, $s0, $at
  li    $t2, 4
  lui   $at, %hi(D_80334920)
  addu  $at, $at, $t2
  lw    $t2, %lo(D_80334920)($at)
  lui   $sp, %hi(D_80365E40) # $sp, 0x8033 #.bss stack for D_802F4380
  addiu $sp, %lo(D_80365E40) # addiu $sp, $sp, 0x5c20
  li    $a0, 16
  beqz  $t2, .L802F3BA4
   addiu $sp, $sp, 0xff0
  jalr  $t2
  nop   
  beqz  $v0, .L802F3BA4
   nop   
  b     .L802F3DA8
   nop   
.L802F3BA4:
  jal   send_mesg
   nop   
  b     .L802F3AF0
   nop   
glabel L803269B8
  lui   $t0, %hi(D_8030208C) # $t0, 0x8030
  addiu $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
  lw    $t0, ($t0)
  lui   $s1, %hi(MI_INTR_REG) # $s1, 0xa430
  lw    $s1, %lo(MI_INTR_REG)($s1)
  srl   $t0, $t0, 0x10
  and   $s1, $s1, $t0
  andi  $t1, $s1, 1
  beqz  $t1, .L802F3C24
   nop   
  lui   $t4, %hi(SP_STATUS_REG) # $t4, 0xa404
  lw    $t4, %lo(SP_STATUS_REG)($t4)
  li    $t1, 8
  lui   $at, %hi(SP_STATUS_REG) # $at, 0xa404
  andi  $t4, $t4, 0x300
  andi  $s1, $s1, 0x3e
  beqz  $t4, .L802F3C14
   sw    $t1, %lo(SP_STATUS_REG)($at)
  jal   send_mesg
   li    $a0, 32
  beqz  $s1, .L802F3CE8
   nop   
  b     .L802F3C24
   nop   
.L802F3C14:
  jal   send_mesg
   li    $a0, 88
  beqz  $s1, .L802F3CE8
   nop   
.L802F3C24:
  andi  $t1, $s1, 8
  beqz  $t1, .L802F3C48
   lui   $at, %hi(VI_CURRENT_REG) # $at, 0xa440
  andi  $s1, $s1, 0x37
  sw    $zero, %lo(VI_CURRENT_REG)($at)
  jal   send_mesg
   li    $a0, 56
  beqz  $s1, .L802F3CE8
   nop   
.L802F3C48:
  andi  $t1, $s1, 4
  beqz  $t1, .L802F3C74
   nop   
  li    $t1, 1
  lui   $at, %hi(AI_STATUS_REG) # $at, 0xa450
  andi  $s1, $s1, 0x3b
  sw    $t1, %lo(AI_STATUS_REG)($at)
  jal   send_mesg
   li    $a0, 48
  beqz  $s1, .L802F3CE8
   nop   
.L802F3C74:
  andi  $t1, $s1, 2
  beqz  $t1, .L802F3C98
   lui   $at, %hi(SI_STATUS_REG) # $at, 0xa480
  andi  $s1, $s1, 0x3d
  sw    $zero, %lo(SI_STATUS_REG)($at)
  jal   send_mesg
   li    $a0, 40
  beqz  $s1, .L802F3CE8
   nop   
.L802F3C98:
  andi  $t1, $s1, 0x10
  beqz  $t1, .L802F3CC4
   nop   
  li    $t1, 2
  lui   $at, %hi(PI_STATUS_REG) # $at, 0xa460
  andi  $s1, $s1, 0x2f
  sw    $t1, %lo(PI_STATUS_REG)($at)
  jal   send_mesg
   li    $a0, 64
  beqz  $s1, .L802F3CE8
   nop   
.L802F3CC4:
  andi  $t1, $s1, 0x20
  beqz  $t1, .L802F3CE8
   nop   
  li    $t1, 2048
  lui   $at, 0xa430
  andi  $s1, $s1, 0x1f
  sw    $t1, ($at)
  jal   send_mesg
   li    $a0, 72
.L802F3CE8:
  li    $at, -1025
  b     .L802F3AF0
   and   $s0, $s0, $at
glabel L80326AE8
  lw    $k1, 0x118($k0)
  li    $at, -4097
  lui   $t1, %hi(D_80334808) # $t1, 0x8030
  and   $k1, $k1, $at
  sw    $k1, 0x118($k0)
  addiu $t1, %lo(D_80334808) # addiu $t1, $t1, 0x2088
  lw    $t2, ($t1)
  beqz  $t2, .L802F3D20
   li    $at, -4097
  b     .L802F3DA8
   and   $s0, $s0, $at
.L802F3D20:
  li    $t2, 1
  sw    $t2, ($t1)
  jal   send_mesg
   li    $a0, 112
  lui   $t2, %hi(D_80334890 + 0x8) # $t2, 0x8030
  lw    $t2, %lo(D_80334890 + 0x8)($t2)
  li    $at, -4097
  and   $s0, $s0, $at
  lw    $k1, 0x118($t2)
  and   $k1, $k1, $at
  b     .L802F3DA8
   sw    $k1, 0x118($t2)
glabel L80326B44
  li    $at, -513
  and   $t0, $t0, $at
  mtc0  $t0, $13
  jal   send_mesg
   li    $a0, 8
  li    $at, -513
  b     .L802F3AF0
   and   $s0, $s0, $at
glabel L80326B64
  li    $at, -257
  and   $t0, $t0, $at
  mtc0  $t0, $13
  jal   send_mesg
   li    $a0, 0
  li    $at, -257
  b     .L802F3AF0
   and   $s0, $s0, $at
.L802F3D90:
  li    $t1, 1
  sh    $t1, 0x12($k0)
  jal   send_mesg
   li    $a0, 80
  b     .L802F3DA8
   nop   
.L802F3DA8:
glabel L80326B9C
  lui   $t2, %hi(D_80334890 + 0x8) # $t2, 0x8030
  lw    $t2, %lo(D_80334890 + 0x8)($t2)
  lw    $t1, 4($k0)
  lw    $t3, 4($t2)
  slt   $at, $t1, $t3
  beqz  $at, .L80326BD0
   nop   
  lui   $a0, %hi(D_80334890 + 0x8) # $a0, 0x8030
  move  $a1, $k0
  jal   __osEnqueueThread
   addiu $a0, %lo(D_80334890 + 0x8) # addiu $a0, $a0, 0x2ef8
  j     __osDispatchThread
   nop   

.L80326BD0:
  lui   $t1, %hi(D_80334890 + 0x8) # $t1, 0x8030
  addiu $t1, %lo(D_80334890 + 0x8) # addiu $t1, $t1, 0x2ef8
  lw    $t2, ($t1)
  sw    $t2, ($k0)
  j     __osDispatchThread
   sw    $k0, ($t1)

.L80326BE8:
glabel L80326BE8
  lui   $at, %hi(D_80334890 + 0x14) # $at, 0x8030
  sw    $k0, %lo(D_80334890 + 0x14)($at)
  li    $t1, 1
  sh    $t1, 0x10($k0)
  li    $t1, 2
  sh    $t1, 0x12($k0)
  mfc0  $t2, $8
  sw    $t2, 0x124($k0)
  jal   send_mesg
   li    $a0, 96
  j     __osDispatchThread
   nop   
.endif

glabel send_mesg
    lui   $t2, %hi(D_80363830) # $t2, 0x8036
    addiu $t2, %lo(D_80363830) # addiu $t2, $t2, 0x3830
    addu  $t2, $t2, $a0
    lw    $t1, ($t2)
    move  $s2, $ra
    beqz  $t1, .L80326CC4
     nop
    lw    $t3, 8($t1)
    lw    $t4, 0x10($t1)
    slt   $at, $t3, $t4
    beqz  $at, .L80326CC4
     nop
    lw    $t5, 0xc($t1)
    addu  $t5, $t5, $t3
    div   $zero, $t5, $t4
    bnez  $t4, .L80326C60
     nop
    break 7
.L80326C60:
    li    $at, -1
    bne   $t4, $at, .L80326C78
     lui   $at, 0x8000
    bne   $t5, $at, .L80326C78
     nop
    break 6
.L80326C78:
    lw    $t4, 0x14($t1)
    mfhi  $t5
    sll   $t5, $t5, 2
    addu  $t4, $t4, $t5
    lw    $t5, 4($t2)
    addiu $t2, $t3, 1
    sw    $t5, ($t4)
    sw    $t2, 8($t1)
    lw    $t2, ($t1)
    lw    $t3, ($t2)
    beqz  $t3, .L80326CC4
     nop
    jal   __osPopThread
     move  $a0, $t1
    move  $t2, $v0
    lui   $a0, %hi(D_80334890 + 0x8) # $a0, 0x8033
    move  $a1, $t2
    jal   __osEnqueueThread
     addiu $a0, %lo(D_80334890 + 0x8) # addiu $a0, $a0, 0x4898
.L80326CC4:
    jr    $s2
     nop
.L80326CCC:
    lui   $at, 0x3000
    and   $t1, $t0, $at
    srl   $t1, $t1, 0x1c
    li    $t2, 1
    bne   $t1, $t2, .L80326BE8
     nop
    lw    $k1, 0x118($k0)
    lui   $at, 0x2000
    li    $t1, 1
    or    $k1, $k1, $at
    sw    $t1, 0x18($k0)
    b     .L80326BD0
     sw    $k1, 0x118($k0)


glabel __osEnqueueAndYield
    lui   $a1, %hi(D_80334890 + 0x10) # $a1, 0x8033
    lw    $a1, %lo(D_80334890 + 0x10)($a1)
    mfc0  $t0, $12
    lw    $k1, 0x18($a1)
    ori   $t0, $t0, 2
    sw    $t0, 0x118($a1)
    sd    $s0, 0x98($a1)
    sd    $s1, 0xa0($a1)
    sd    $s2, 0xa8($a1)
    sd    $s3, 0xb0($a1)
    sd    $s4, 0xb8($a1)
    sd    $s5, 0xc0($a1)
    sd    $s6, 0xc8($a1)
    sd    $s7, 0xd0($a1)
    sd    $gp, 0xe8($a1)
    sd    $sp, 0xf0($a1)
    sd    $fp, 0xf8($a1)
    sd    $ra, 0x100($a1)
.ifdef VERSION_EU
    beqz  $k1, .L802F3F7C
.else
    beqz  $k1, .L80326D70
.endif
     sw    $ra, 0x11c($a1)
    cfc1  $k1, $31
    sdc1  $f20, 0x180($a1)
    sdc1  $f22, 0x188($a1)
    sdc1  $f24, 0x190($a1)
    sdc1  $f26, 0x198($a1)
    sdc1  $f28, 0x1a0($a1)
    sdc1  $f30, 0x1a8($a1)
    sw    $k1, 0x12c($a1)

.ifdef VERSION_EU
.L802F3F7C:
/* 0B377C 802F3F7C 8CBB0118 */  lw    $k1, 0x118($a1)
/* 0B3780 802F3F80 3369FF00 */  andi  $t1, $k1, 0xff00
/* 0B3784 802F3F84 1120000D */  beqz  $t1, .L802F3FBC
/* 0B3788 802F3F88 00000000 */   nop   
/* 0B378C 802F3F8C 3C088030 */  lui   $t0, %hi(D_8030208C) # $t0, 0x8030
/* 0B3790 802F3F90 2508208C */  addiu $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
/* 0B3794 802F3F94 8D080000 */  lw    $t0, ($t0)
/* 0B3798 802F3F98 2401FFFF */  li    $at, -1
/* 0B379C 802F3F9C 01014026 */  xor   $t0, $t0, $at
/* 0B37A0 802F3FA0 3C01FFFF */  lui   $at, (0xFFFF00FF >> 16) # lui $at, 0xffff
/* 0B37A4 802F3FA4 3108FF00 */  andi  $t0, $t0, 0xff00
/* 0B37A8 802F3FA8 342100FF */  ori   $at, (0xFFFF00FF & 0xFFFF) # ori $at, $at, 0xff
/* 0B37AC 802F3FAC 01284825 */  or    $t1, $t1, $t0
/* 0B37B0 802F3FB0 0361D824 */  and   $k1, $k1, $at
/* 0B37B4 802F3FB4 0369D825 */  or    $k1, $k1, $t1
/* 0B37B8 802F3FB8 ACBB0118 */  sw    $k1, 0x118($a1)
.L802F3FBC:
/* 0B37BC 802F3FBC 3C1BA430 */  lui   $k1, %hi(MI_INTR_MASK_REG) # $k1, 0xa430
/* 0B37C0 802F3FC0 8F7B000C */  lw    $k1, %lo(MI_INTR_MASK_REG)($k1)
/* 0B37C4 802F3FC4 1360000B */  beqz  $k1, .L802F3FF4
/* 0B37C8 802F3FC8 00000000 */   nop   
/* 0B37CC 802F3FCC 3C1A8030 */  lui   $k0, %hi(D_8030208C) # $k0, 0x8030
/* 0B37D0 802F3FD0 275A208C */  addiu $k0, %lo(D_8030208C) # addiu $k0, $k0, 0x208c
/* 0B37D4 802F3FD4 8F5A0000 */  lw    $k0, ($k0)
/* 0B37D8 802F3FD8 8CA80128 */  lw    $t0, 0x128($a1)
/* 0B37DC 802F3FDC 2401FFFF */  li    $at, -1
/* 0B37E0 802F3FE0 001AD402 */  srl   $k0, $k0, 0x10
/* 0B37E4 802F3FE4 0341D026 */  xor   $k0, $k0, $at
/* 0B37E8 802F3FE8 335A003F */  andi  $k0, $k0, 0x3f
/* 0B37EC 802F3FEC 0348D024 */  and   $k0, $k0, $t0
/* 0B37F0 802F3FF0 037AD825 */  or    $k1, $k1, $k0
.L802F3FF4:
.endif


.L80326D70:
.ifndef VERSION_EU
    lui   $k1, %hi(MI_INTR_MASK_REG) # $k1, 0xa430
    lw    $k1, %lo(MI_INTR_MASK_REG)($k1)
.endif
    beqz  $a0, .L80326D88
     sw    $k1, 0x128($a1)
    jal   __osEnqueueThread
     nop
.L80326D88:
    j     __osDispatchThread
     nop


#enqueue and pop look like compiled functions?  but there's no easy way to extract them
glabel __osEnqueueThread
    lw    $t8, ($a0)
    lw    $t7, 4($a1)
    move  $t9, $a0
    lw    $t6, 4($t8)
    slt   $at, $t6, $t7
    bnez  $at, .L80326DC4
     nop
.L80326DAC:
    move  $t9, $t8
    lw    $t8, ($t8)
    lw    $t6, 4($t8)
    slt   $at, $t6, $t7
    beqz  $at, .L80326DAC
     nop
.L80326DC4:
    lw    $t8, ($t9)
    sw    $t8, ($a1)
    sw    $a1, ($t9)
    jr    $ra
     sw    $a0, 8($a1)

glabel __osPopThread
    lw    $v0, ($a0)
    lw    $t9, ($v0)
    jr    $ra
     sw    $t9, ($a0)

glabel __osDispatchThread
    lui   $a0, %hi(D_80334890 + 0x8) # $a0, 0x8033
    jal   __osPopThread
     addiu $a0, %lo(D_80334890 + 0x8) # addiu $a0, $a0, 0x4898
    lui   $at, %hi(D_80334890 + 0x10) # $at, 0x8033
    sw    $v0, %lo(D_80334890 + 0x10)($at)
    li    $t0, 4
    sh    $t0, 0x10($v0)
    move  $k0, $v0

.ifdef VERSION_EU

/* 0B3884 802F4084 3C088030 */  lui   $t0, %hi(D_8030208C) # $t0, 0x8030
/* 0B3888 802F4088 8F5B0118 */  lw    $k1, 0x118($k0)
/* 0B388C 802F408C 2508208C */  addiu $t0, %lo(D_8030208C) # addiu $t0, $t0, 0x208c
/* 0B3890 802F4090 8D080000 */  lw    $t0, ($t0)
/* 0B3894 802F4094 3C01FFFF */  lui   $at, (0xFFFF00FF >> 16) # lui $at, 0xffff
/* 0B3898 802F4098 3369FF00 */  andi  $t1, $k1, 0xff00
/* 0B389C 802F409C 342100FF */  ori   $at, (0xFFFF00FF & 0xFFFF) # ori $at, $at, 0xff
/* 0B38A0 802F40A0 3108FF00 */  andi  $t0, $t0, 0xff00
/* 0B38A4 802F40A4 01284824 */  and   $t1, $t1, $t0
/* 0B38A8 802F40A8 0361D824 */  and   $k1, $k1, $at
/* 0B38AC 802F40AC 0369D825 */  or    $k1, $k1, $t1
/* 0B38B0 802F40B0 409B6000 */  mtc0  $k1, $12
.endif
.L80326E08:
    ld    $k1, 0x108($k0)
    ld    $at, 0x20($k0)
    ld    $v0, 0x28($k0)
    mtlo  $k1
    ld    $k1, 0x110($k0)
    ld    $v1, 0x30($k0)
    ld    $a0, 0x38($k0)
    ld    $a1, 0x40($k0)
    ld    $a2, 0x48($k0)
    ld    $a3, 0x50($k0)
    ld    $t0, 0x58($k0)
    ld    $t1, 0x60($k0)
    ld    $t2, 0x68($k0)
    ld    $t3, 0x70($k0)
    ld    $t4, 0x78($k0)
    ld    $t5, 0x80($k0)
    ld    $t6, 0x88($k0)
    ld    $t7, 0x90($k0)
    ld    $s0, 0x98($k0)
    ld    $s1, 0xa0($k0)
    ld    $s2, 0xa8($k0)
    ld    $s3, 0xb0($k0)
    ld    $s4, 0xb8($k0)
    ld    $s5, 0xc0($k0)
    ld    $s6, 0xc8($k0)
    ld    $s7, 0xd0($k0)
    ld    $t8, 0xd8($k0)
    ld    $t9, 0xe0($k0)
    ld    $gp, 0xe8($k0)
    mthi  $k1
    ld    $sp, 0xf0($k0)
    ld    $fp, 0xf8($k0)
    ld    $ra, 0x100($k0)
    lw    $k1, 0x11c($k0)
    mtc0  $k1, $14
.ifndef VERSION_EU
    lw    $k1, 0x118($k0)
    mtc0  $k1, $12
.endif
    lw    $k1, 0x18($k0)
    beqz  $k1, .L80326EF0
     nop
    lw    $k1, 0x12c($k0)
    ctc1  $k1, $31
    ldc1  $f0, 0x130($k0)
    ldc1  $f2, 0x138($k0)
    ldc1  $f4, 0x140($k0)
    ldc1  $f6, 0x148($k0)
    ldc1  $f8, 0x150($k0)
    ldc1  $f10, 0x158($k0)
    ldc1  $f12, 0x160($k0)
    ldc1  $f14, 0x168($k0)
    ldc1  $f16, 0x170($k0)
    ldc1  $f18, 0x178($k0)
    ldc1  $f20, 0x180($k0)
    ldc1  $f22, 0x188($k0)
    ldc1  $f24, 0x190($k0)
    ldc1  $f26, 0x198($k0)
    ldc1  $f28, 0x1a0($k0)
    ldc1  $f30, 0x1a8($k0)
.L80326EF0:
    lw    $k1, 0x128($k0)
.ifdef VERSION_EU
/* 0B3998 802F4198 3C1A8030 */  lui   $k0, %hi(D_8030208C) # $k0, 0x8030
/* 0B399C 802F419C 275A208C */  addiu $k0, %lo(D_8030208C) # addiu $k0, $k0, 0x208c
/* 0B39A0 802F41A0 8F5A0000 */  lw    $k0, ($k0)
/* 0B39A4 802F41A4 001AD402 */  srl   $k0, $k0, 0x10
/* 0B39A8 802F41A8 037AD824 */  and   $k1, $k1, $k0
.endif
    sll   $k1, $k1, 1
    lui   $k0, %hi(D_803386D0) # $k0, 0x8034
    addiu $k0, %lo(D_803386D0) # addiu $k0, $k0, -0x7930
    addu  $k1, $k1, $k0
    lhu   $k1, ($k1)
    lui   $k0, %hi(MI_INTR_MASK_REG) # $k0, 0xa430
    addiu $k0, %lo(MI_INTR_MASK_REG) # addiu $k0, $k0, 0xc
    sw    $k1, ($k0)
    nop
    nop
    nop
    nop
    eret
glabel __osCleanupThread
    jal   osDestroyThread
     move  $a0, $zero

.section .data

glabel D_80334920
    .word 0
    .word 0
    .word 0
    .word 0
    .word 0

glabel D_80334934
    .word 0

glabel D_80334938
    .word 0
    .word 0

.section .rodata

glabel D_80338610
    .byte 0x00,0x14,0x18,0x18,0x1C,0x1C,0x1C,0x1C,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x00,0x04,0x08,0x08,0x0C,0x0C,0x0C,0x0C,0x10,0x10,0x10,0x10,0x10,0x10,0x10,0x10

glabel jtbl_80338630
    .word L80326B9C
    .word L80326B64
    .word L80326B44
    .word L803269B8
    .word L80326984
    .word L80326AE8
.ifdef VERSION_EU
    .word 0x802f3b28 
    .word 0x802f3b34
.else
    .word L80326BE8
    .word L80326BE8
.endif
    .word L80326964
    .word 0
    .word 0
    .word 0
