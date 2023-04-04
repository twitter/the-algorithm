.set noreorder // don't insert nops after branches
.set gp=64
.set noat

#include "macros.inc"


.section .text, "ax"
// cache related
glabel __os_eu_802ef550
    lui     $t0,0x8000
    li      $t2,0x2000
    addu    $t1,$t0,$t2
    addiu   $t1,$t1,-0x10
.L:    cache   0x1,0($t0)
    sltu    $at,$t0,$t1
    bnez    $at,.L
    addiu   $t0,$t0,0x10
    jr      $ra
    nop
    nop
    nop
