.set noat      // allow manual use of $at
.set noreorder // don't insert nops after branches
.set gp=64

#include "macros.inc"


.section .text, "ax"

glabel osMapTLBRdb
    mfc0  $t0, $10
    li    $t1, 31
    mtc0  $t1, $0
    mtc0  $zero, $5
    li    $t2, 23
    lui   $t1, 0xc000
    mtc0  $t1, $10
    lui   $t1, 0x8000
    srl   $t3, $t1, 6
    or    $t3, $t3, $t2
    mtc0  $t3, $2
    li    $t1, 1
    mtc0  $t1, $3
    nop
    tlbwi
    nop
    nop
    nop
    nop
    mtc0  $t0, $10
    jr    $ra
     nop

    nop
    nop

