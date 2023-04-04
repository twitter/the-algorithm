.set noat      // allow manual use of $at
.set noreorder // don't insert nops after branches
.set gp=64

#include "macros.inc"


.section .text, "ax"

glabel osInvalICache
    blez  $a1, .L80323728
     nop
    li    $t3, 16384
    sltu  $at, $a1, $t3
    beqz  $at, .L80323730
     nop
    move  $t0, $a0
    addu  $t1, $a0, $a1
    sltu  $at, $t0, $t1
    beqz  $at, .L80323728
     nop
    andi  $t2, $t0, 0x1f
    addiu $t1, $t1, -0x20
    subu  $t0, $t0, $t2
.L80323718:
    cache 0x10, ($t0)
    sltu  $at, $t0, $t1
    bnez  $at, .L80323718
     addiu $t0, $t0, 0x20
.L80323728:
    jr    $ra
     nop

.L80323730:
    li    $t0, K0BASE
    addu  $t1, $t0, $t3
    addiu $t1, $t1, -0x20
.L8032373C:
    cache 0, ($t0)
    sltu  $at, $t0, $t1
    bnez  $at, .L8032373C
     addiu $t0, $t0, 0x20
    jr    $ra
     nop
