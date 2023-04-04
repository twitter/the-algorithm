.set noreorder // don't insert nops after branches
.set gp=64

#include "macros.inc"


.section .text, "ax"

glabel __osGetCause
    mfc0  $v0, $13
    jr    $ra
     nop

    nop

