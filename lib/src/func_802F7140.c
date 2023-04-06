#include "libultra_internal.h"
extern u32 D_8030208C;
void func_802F7140(u32 a0) {
    register u32 s0;
    s0 = __osDisableInt();
    D_8030208C &= ~(-0x402 & a0);
    __osRestoreInt(s0);
}
/*
/ 0B6940 802F7140 27BDFFD8 /  addiu $sp, $sp, -0x28
/ 0B6944 802F7144 AFBF001C /  sw    $ra, 0x1c($sp)
/ 0B6948 802F7148 AFA40028 /  sw    $a0, 0x28($sp)
/ 0B694C 802F714C 0C0BD400 /  jal   __osDisableInt
/ 0B6950 802F7150 AFB00018 /   sw    $s0, 0x18($sp)
/ 0B6954 802F7154 8FAF0028 /  lw    $t7, 0x28($sp)
/ 0B6958 802F7158 3C0E8030 /  lui   $t6, %hi(D_8030208C) # $t6, 0x8030
/ 0B695C 802F715C 8DCE208C /  lw    $t6, %lo(D_8030208C)($t6)
/ 0B6960 802F7160 2401FBFE /  li    $at, -1026
/ 0B6964 802F7164 01E1C024 /  and   $t8, $t7, $at
/ 0B6968 802F7168 0300C827 /  not   $t9, $t8
/ 0B696C 802F716C 00408025 /  move  $s0, $v0
/ 0B6970 802F7170 3C018030 /  lui   $at, %hi(D_8030208C) # $at, 0x8030
/ 0B6974 802F7174 01D94024 /  and   $t0, $t6, $t9
/ 0B6978 802F7178 AC28208C /  sw    $t0, %lo(D_8030208C)($at)
/ 0B697C 802F717C 0C0BD408 /  jal   __osRestoreInt
/ 0B6980 802F7180 02002025 /   move  $a0, $s0
/ 0B6984 802F7184 8FBF001C /  lw    $ra, 0x1c($sp)
/ 0B6988 802F7188 8FB00018 /  lw    $s0, 0x18($sp)
/ 0B698C 802F718C 27BD0028 /  addiu $sp, $sp, 0x28
/ 0B6990 802F7190 03E00008 /  jr    $ra
/ 0B6994 802F7194 00000000 /   nop   */
