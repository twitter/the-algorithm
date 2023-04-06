.set noat      # allow manual use of $at
.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

glabel osInvalDCache
    blez  $a1, .L80323500
    nop
    li    $t3, 8192
    sltu  $at, $a1, $t3
    beqz  $at, .L80323508
    nop
    move  $t0, $a0
    addu  $t1, $a0, $a1
    sltu  $at, $t0, $t1
    beqz  $at, .L80323500
    nop
    andi  $t2, $t0, 0xf
    beqz  $t2, .L803234D0
    addiu $t1, $t1, -0x10
    subu  $t0, $t0, $t2
    cache 0x15, ($t0)
    sltu  $at, $t0, $t1
    beqz  $at, .L80323500
    nop
    addiu $t0, $t0, 0x10
.L803234D0:
    andi  $t2, $t1, 0xf
    beqz  $t2, .L803234F0
    nop
    subu  $t1, $t1, $t2
    cache 0x15, 0x10($t1)
    sltu  $at, $t1, $t0
    bnez  $at, .L80323500
    nop
.L803234F0:
    cache 0x11, ($t0)
    sltu  $at, $t0, $t1
    bnez  $at, .L803234F0
    addiu $t0, $t0, 0x10
.L80323500:
    jr    $ra
    nop

.L80323508:
    li    $t0, K0BASE
    addu  $t1, $t0, $t3
    addiu $t1, $t1, -0x10
.L80323514:
    cache 1, ($t0)
    sltu  $at, $t0, $t1
    bnez  $at, .L80323514
     addiu $t0, $t0, 0x10
    jr    $ra
     nop
