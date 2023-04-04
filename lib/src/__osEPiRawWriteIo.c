#include "libultra_internal.h"
#include "hardware.h"

s32 __osEPiRawWriteIo(OSPiHandle *a0, u32 a1, u32 a2) {
    register u32 a3 = HW_REG(PI_STATUS_REG, u32);
    while (a3 & PI_STATUS_ERROR)
        a3 = HW_REG(PI_STATUS_REG, u32);
    HW_REG(a0->baseAddress | a1, u32) = a2;
    return 0;
}
/*
/ 0B69A0 802F71A0 3C0EA460 /  lui   $t6, %hi(PI_STATUS_REG) # $t6, 0xa460
/ 0B69A4 802F71A4 8DC70010 /  lw    $a3, %lo(PI_STATUS_REG)($t6)
/ 0B69A8 802F71A8 27BDFFF8 /  addiu $sp, $sp, -8
/ 0B69AC 802F71AC 30EF0003 /  andi  $t7, $a3, 3

/ 0B69B0 802F71B0 11E00006 /  beqz  $t7, .L802F71CC
/ 0B69B4 802F71B4 00000000 /   nop
.L802F71B8:
/ 0B69B8 802F71B8 3C18A460 /  lui   $t8, %hi(PI_STATUS_REG) # $t8, 0xa460
/ 0B69BC 802F71BC 8F070010 /  lw    $a3, %lo(PI_STATUS_REG)($t8)
/ 0B69C0 802F71C0 30F90003 /  andi  $t9, $a3, 3
/ 0B69C4 802F71C4 1720FFFC /  bnez  $t9, .L802F71B8
/ 0B69C8 802F71C8 00000000 /   nop
.L802F71CC:
/ 0B69CC 802F71CC 8C88000C /  lw    $t0, 0xc($a0)
/ 0B69D0 802F71D0 3C01A000 /  lui   $at, 0xa000
/ 0B69D4 802F71D4 27BD0008 /  addiu $sp, $sp, 8
/ 0B69D8 802F71D8 01054825 /  or    $t1, $t0, $a1
/ 0B69DC 802F71DC 01215025 /  or    $t2, $t1, $at
/ 0B69E0 802F71E0 AD460000 /  sw    $a2, ($t2)
/ 0B69E4 802F71E4 03E00008 /  jr    $ra
/ 0B69E8 802F71E8 00001025 /   move  $v0, $zero

/ 0B69EC 802F71EC 00000000 /  nop   */
