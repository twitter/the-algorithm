.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

glabel osGetCount
    mfc0  $v0, $9
    jr    $ra
     nop

    nop

