.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

glabel osUnmapTLBAll
    mfc0  $t0, $10
    li    $t1, 31
    lui   $t2, 0x8000
    mtc0  $t2, $10
    mtc0  $zero, $2
    mtc0  $zero, $3
.L80321588:
    mtc0  $t1, $0
    nop
    tlbwi
    nop
    nop
    addi  $t1, $t1, -1
    bnezl $t1, .L80321588  #bnezl, bnez but with likely hint
     nop
    mtc0  $t0, $10
    jr    $ra
     nop

    nop
    nop
    nop

