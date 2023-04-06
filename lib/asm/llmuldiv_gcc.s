# assembler directives
.set noat      # allow manual use of $at
.set noreorder # don't insert nops after branches
.set gp=64

.include "macros.inc"


.section .text, "ax"

/* -------------------------------------------------------------------------------------- */
/* need to asm these functions because lib32gcc-7-dev-mips-cross does not exist so we     */
/* cannot naturally link a libgcc variant for this target given this architecture and     */
/* compiler. Until we have a good workaround with a gcc target that doesn't involve       */
/* assuming a 32-bit to 64-bit change, we have to encode these functions as raw assembly  */
/* for it to compile.                                                                     */
/* -------------------------------------------------------------------------------------- */

/* TODO: Is there a non-insane way to fix this hack that doesn't involve the user compiling */
/* a library themselves?                                                                    */
glabel __umoddi3
    sw    $a0, ($sp)
    sw    $a1, 4($sp)
    sw    $a2, 8($sp)
    sw    $a3, 0xc($sp)
    ld    $t7, 8($sp)
    ld    $t6, ($sp)
    ddivu $zero, $t6, $t7
    bnez  $t7, .L80324144
     nop
    break 7
.L80324144:
    mfhi  $v0
    dsll32 $v1, $v0, 0
    dsra32 $v1, $v1, 0
    jr    $ra
     dsra32 $v0, $v0, 0

glabel __udivdi3
    sw    $a0, ($sp)
    sw    $a1, 4($sp)
    sw    $a2, 8($sp)
    sw    $a3, 0xc($sp)
    ld    $t7, 8($sp)
    ld    $t6, ($sp)
    ddivu $zero, $t6, $t7
    bnez  $t7, .L80324180
     nop
    break 7
.L80324180:
    mflo  $v0
    dsll32 $v1, $v0, 0
    dsra32 $v1, $v1, 0
    jr    $ra
     dsra32 $v0, $v0, 0

glabel __moddi3
    sw    $a0, ($sp)
    sw    $a1, 4($sp)
    sw    $a2, 8($sp)
    sw    $a3, 0xc($sp)
    ld    $t7, 8($sp)
    ld    $t6, ($sp)
    ddivu $zero, $t6, $t7
    bnez  $t7, .L803241E8
     nop
    break 7
.L803241E8:
    mfhi  $v0
    dsll32 $v1, $v0, 0
    dsra32 $v1, $v1, 0
    jr    $ra
     dsra32 $v0, $v0, 0

glabel __divdi3
    sw    $a0, ($sp)
    sw    $a1, 4($sp)
    sw    $a2, 8($sp)
    sw    $a3, 0xc($sp)
    ld    $t7, 8($sp)
    ld    $t6, ($sp)
    ddiv  $zero, $t6, $t7
    nop
    bnez  $t7, .L80324228
     nop
    break 7
.L80324228:
    daddiu $at, $zero, -1
    bne   $t7, $at, .L80324244
     daddiu $at, $zero, 1
    dsll32 $at, $at, 0x1f
    bne   $t6, $at, .L80324244
     nop
    break 6
.L80324244:
    mflo  $v0
    dsll32 $v1, $v0, 0
    dsra32 $v1, $v1, 0
    jr    $ra
     dsra32 $v0, $v0, 0
