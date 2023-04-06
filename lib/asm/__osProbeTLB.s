.set noat      # allow manual use of $at
.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

glabel __osProbeTLB
    mfc0  $t0, $10
    andi  $t1, $t0, 0xff
    li    $at, -8192
    and   $t2, $a0, $at
    or    $t1, $t1, $t2
    mtc0  $t1, $10
    nop
    nop
    nop
    tlbp
    nop
    nop
    mfc0  $t3, $0
    lui   $at, 0x8000
    and   $t3, $t3, $at
    bnez  $t3, .L8032A0D8
     nop
    tlbr
    nop
    nop
    nop
    mfc0  $t3, $5
    addi  $t3, $t3, 0x2000
    srl   $t3, $t3, 1
    and   $t4, $t3, $a0
    bnez  $t4, .L8032A0A8
     addi  $t3, $t3, -1
    mfc0  $v0, $2
    b     .L8032A0AC
     nop
.L8032A0A8:
    mfc0  $v0, $3
.L8032A0AC:
    andi  $t5, $v0, 2
    beqz  $t5, .L8032A0D8
     nop
    lui   $at, (0x3FFFFFC0 >> 16) # lui $at, 0x3fff
    ori   $at, (0x3FFFFFC0 & 0xFFFF) # ori $at, $at, 0xffc0
    and   $v0, $v0, $at
    sll   $v0, $v0, 6
    and   $t5, $a0, $t3
    add   $v0, $v0, $t5
    b     .L8032A0DC
     nop
.L8032A0D8:
    li    $v0, -1
.L8032A0DC:
    mtc0  $t0, $10
    jr    $ra
     nop

    nop
    nop

