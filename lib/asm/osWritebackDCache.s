.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

glabel osWritebackDCache
    blez  $a1, .osWritebackDCacheReturn
     nop
    li    $t3, 8192
    bgeu  $a1, $t3, .L80324E40
     nop
    move  $t0, $a0
    addu  $t1, $a0, $a1
    bgeu  $t0, $t1, .osWritebackDCacheReturn
     nop
    andi  $t2, $t0, 0xf
    addiu $t1, $t1, -0x10
    subu  $t0, $t0, $t2
.L80324E28:
    cache 0x19, ($t0)
    bltu  $t0, $t1, .L80324E28
     addiu $t0, $t0, 0x10
.osWritebackDCacheReturn:
    jr    $ra
     nop

.L80324E40:
    lui   $t0, 0x8000
    addu  $t1, $t0, $t3
    addiu $t1, $t1, -0x10
.L80324E4C:
    cache 1, ($t0)
    bltu  $t0, $t1, .L80324E4C
     addiu $t0, 0x10 # addiu $t0, $t0, 0x10
    jr    $ra
     nop
