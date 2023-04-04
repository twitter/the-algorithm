.set noreorder // don't insert nops after branches
.set gp=64

#include "macros.inc"


.section .text, "ax"

// This file is handwritten

// void osMapTLB(s32 index, OSPageMask pm, void *vaddr, u32 evenpaddr, u32 oddpaddr, s32 asid);
glabel osMapTLB
    mfc0  $t0, $10
    mtc0  $a0, $0
    mtc0  $a1, $5
    lw    $t1, 0x14($sp) #asid
    beq   $t1, -1, .L803214D8
    li    $t4, 1
    li    $t2, 30
    b     .L803214DC
    or    $a2, $a2, $t1 #vaddr
.L803214D8:
    li    $t2, 31
.L803214DC:
    mtc0  $a2, $10 #vaddr
    beq   $a3, -1, .L80321500 #even paddr
    nop
    srl   $t3, $a3, 6   #evenpaddr
    or    $t3, $t3, $t2
    mtc0  $t3, $2
    b     .L80321504
    nop
.L80321500:
    mtc0  $t4, $2
.L80321504:
    lw    $t3, 0x10($sp) #oddpaddr
    beq   $t3, -1, .L80321528
    nop
    srl   $t3, $t3, 6
    or    $t3, $t3, $t2
    mtc0  $t3, $3
    b     .L80321540
    nop
.L80321528:
    mtc0  $t4, $3
    bne   $a3, -1, .L80321540 #evenpaddr
    nop
    lui   $t3, 0x8000
    mtc0  $t3, $10
.L80321540:
    nop
    tlbwi
    nop
    nop
    nop
    nop
    mtc0  $t0, $10
    jr    $ra
    nop   #file gets padded but
    nop
    nop
    nop

