.set noat      # allow manual use of $at
.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

glabel osWritebackDCacheAll
    li    $t0, K0BASE
    li    $t2, 8192
    addu  $t1, $t0, $t2
    addiu $t1, $t1, -0x10
.L80322020:
    cache 1, ($t0)
    sltu  $at, $t0, $t1
    bnez  $at, .L80322020
     addiu $t0, $t0, 0x10
    jr    $ra
     nop

    nop
    nop
