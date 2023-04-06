#include "libultra_internal.h"

void func_802F71F0() {
    register u32 s0 = __osDisableInt();
    D_803348A0->state = OS_STATE_RUNNABLE;
    __osEnqueueAndYield(&D_80334898);
    __osRestoreInt(s0);
}
/*
/ 0B69F0 802F71F0 27BDFFD8 /  addiu $sp, $sp, -0x28
/ 0B69F4 802F71F4 AFBF001C /  sw    $ra, 0x1c($sp)
/ 0B69F8 802F71F8 0C0BD400 /  jal   __osDisableInt
/ 0B69FC 802F71FC AFB00018 /   sw    $s0, 0x18($sp)
/ 0B6A00 802F7200 3C0F8030 /  lui   $t7, %hi(D_803348A0) # $t7, 0x8030
/ 0B6A04 802F7204 8DEF2F00 /  lw    $t7, %lo(D_803348A0)($t7)
/ 0B6A08 802F7208 240E0002 /  li    $t6, 2
/ 0B6A0C 802F720C 3C048030 /  lui   $a0, %hi(D_80334898) # $a0, 0x8030
/ 0B6A10 802F7210 00408025 /  move  $s0, $v0
/ 0B6A14 802F7214 24842EF8 /  addiu $a0, %lo(D_80334898) # addiu $a0, $a0, 0x2ef8
/ 0B6A18 802F7218 0C0BCFC3 /  jal   __osEnqueueAndYield
/ 0B6A1C 802F721C A5EE0010 /   sh    $t6, 0x10($t7)

/ 0B6A20 802F7220 0C0BD408 /  jal   __osRestoreInt
/ 0B6A24 802F7224 02002025 /   move  $a0, $s0
/ 0B6A28 802F7228 8FBF001C /  lw    $ra, 0x1c($sp)
/ 0B6A2C 802F722C 8FB00018 /  lw    $s0, 0x18($sp)
/ 0B6A30 802F7230 27BD0028 /  addiu $sp, $sp, 0x28
/ 0B6A34 802F7234 03E00008 /  jr    $ra
/ 0B6A38 802F7238 00000000 /   nop

/ 0B6A3C 802F723C 00000000 /  nop   */
