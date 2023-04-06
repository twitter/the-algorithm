.late_rodata
glabel jtbl_80338418
    .word L8031FBAC
    .word L8031FBEC
    .word L8031FC2C
    .word L8031FC6C
    .word L8031FCAC
    .word L8031FCEC
    .word L8031FD2C
    .word L8031FD54

.text
glabel process_level_music_dynamics # US: 803208EC
/* 0DAA4C 8031FA4C 27BDFFA0 */  addiu $sp, $sp, -0x60
/* 0DAA50 8031FA50 AFBF0024 */  sw    $ra, 0x24($sp)
/* 0DAA54 8031FA54 AFB20020 */  sw    $s2, 0x20($sp)
/* 0DAA58 8031FA58 AFB1001C */  sw    $s1, 0x1c($sp)
/* 0DAA5C 8031FA5C AFB00018 */  sw    $s0, 0x18($sp)
/* 0DAA60 8031FA60 0C0C7E5B */  jal   func_8031F96C
/* 0DAA64 8031FA64 00002025 */   move  $a0, $zero
/* 0DAA68 8031FA68 0C0C7E5B */  jal   func_8031F96C
/* 0DAA6C 8031FA6C 24040002 */   li    $a0, 2
/* 0DAA70 8031FA70 0C0C83B6 */  jal   func_80320ED8
/* 0DAA74 8031FA74 00000000 */   nop   
/* 0DAA78 8031FA78 3C038033 */  lui   $v1, %hi(sMusicDynamicDelay) # $v1, 0x8033
/* 0DAA7C 8031FA7C 246320A0 */  addiu $v1, %lo(sMusicDynamicDelay) # addiu $v1, $v1, 0x20a0
/* 0DAA80 8031FA80 90620000 */  lbu   $v0, ($v1)
/* 0DAA84 8031FA84 10400003 */  beqz  $v0, .L8031FA94
/* 0DAA88 8031FA88 244EFFFF */   addiu $t6, $v0, -1
/* 0DAA8C 8031FA8C 10000005 */  b     .L8031FAA4
/* 0DAA90 8031FA90 A06E0000 */   sb    $t6, ($v1)
.L8031FA94:
/* 0DAA94 8031FA94 3C0F8033 */  lui   $t7, %hi(sPlayer0CurSeqId) # $t7, 0x8033
/* 0DAA98 8031FA98 91EF209C */  lbu   $t7, %lo(sPlayer0CurSeqId)($t7)
/* 0DAA9C 8031FA9C 3C018033 */  lui   $at, %hi(sBackgroundMusicForDynamics) # $at, 0x8033
/* 0DAAA0 8031FAA0 A02F1EB0 */  sb    $t7, %lo(sBackgroundMusicForDynamics)($at)
.L8031FAA4:
/* 0DAAA4 8031FAA4 3C188033 */  lui   $t8, %hi(gCurrLevelNum) # $t8, 0x8033
/* 0DAAA8 8031FAA8 8718CE98 */  lh    $t8, %lo(gCurrLevelNum)($t8)
/* 0DAAAC 8031FAAC 3C098033 */  lui   $t1, %hi(sLevelDynamics)
/* 0DAAB0 8031FAB0 3C0C8033 */  lui   $t4, %hi(sBackgroundMusicForDynamics) # $t4, 0x8033
/* 0DAAB4 8031FAB4 0018C880 */  sll   $t9, $t8, 2
/* 0DAAB8 8031FAB8 01394821 */  addu  $t1, $t1, $t9
/* 0DAABC 8031FABC 8D291EB4 */  lw    $t1, %lo(sLevelDynamics)($t1)
/* 0DAAC0 8031FAC0 918C1EB0 */  lbu   $t4, %lo(sBackgroundMusicForDynamics)($t4)
/* 0DAAC4 8031FAC4 852D0000 */  lh    $t5, ($t1)
/* 0DAAC8 8031FAC8 558D00FC */  bnel  $t4, $t5, .L8031FEBC
/* 0DAACC 8031FACC 8FBF0024 */   lw    $ra, 0x24($sp)
/* 0DAAD0 8031FAD0 852F0002 */  lh    $t7, 2($t1)
/* 0DAAD4 8031FAD4 24110002 */  li    $s1, 2
/* 0DAAD8 8031FAD8 3C0B8034 */  lui   $t3, %hi(gCurrAreaIndex) # $t3, 0x8034
/* 0DAADC 8031FADC 31F0FF00 */  andi  $s0, $t7, 0xff00
/* 0DAAE0 8031FAE0 3218FF00 */  andi  $t8, $s0, 0xff00
/* 0DAAE4 8031FAE4 130000B9 */  beqz  $t8, .L8031FDCC
/* 0DAAE8 8031FAE8 A3AF0057 */   sb    $t7, 0x57($sp)
/* 0DAAEC 8031FAEC 3C0A8036 */  lui   $t2, %hi(gMarioCurrentRoom) # $t2, 0x8036
/* 0DAAF0 8031FAF0 3C078034 */  lui   $a3, %hi(gMarioStates) # $a3, 0x8034
/* 0DAAF4 8031FAF4 24E79E00 */  addiu $a3, %lo(gMarioStates) # addiu $a3, $a3, -0x6200
/* 0DAAF8 8031FAF8 254AFEE0 */  addiu $t2, %lo(gMarioCurrentRoom) # addiu $t2, $t2, -0x120
/* 0DAAFC 8031FAFC 256BA75A */  addiu $t3, %lo(gCurrAreaIndex) # addiu $t3, $t3, -0x58a6
/* 0DAB00 8031FB00 27A8003C */  addiu $t0, $sp, 0x3c
/* 0DAB04 8031FB04 27A60044 */  addiu $a2, $sp, 0x44
.L8031FB08:
/* 0DAB08 8031FB08 00001025 */  move  $v0, $zero
/* 0DAB0C 8031FB0C 00002025 */  move  $a0, $zero
/* 0DAB10 8031FB10 34058000 */  li    $a1, 32768
.L8031FB14:
/* 0DAB14 8031FB14 00B0C824 */  and   $t9, $a1, $s0
/* 0DAB18 8031FB18 1320000F */  beqz  $t9, .L8031FB58
/* 0DAB1C 8031FB1C 00A01825 */   move  $v1, $a1
/* 0DAB20 8031FB20 00116040 */  sll   $t4, $s1, 1
/* 0DAB24 8031FB24 012C6821 */  addu  $t5, $t1, $t4
/* 0DAB28 8031FB28 85AE0000 */  lh    $t6, ($t5)
/* 0DAB2C 8031FB2C 00047840 */  sll   $t7, $a0, 1
/* 0DAB30 8031FB30 01046021 */  addu  $t4, $t0, $a0
/* 0DAB34 8031FB34 00CFC021 */  addu  $t8, $a2, $t7
/* 0DAB38 8031FB38 26310001 */  addiu $s1, $s1, 1
/* 0DAB3C 8031FB3C 24840001 */  addiu $a0, $a0, 1
/* 0DAB40 8031FB40 323900FF */  andi  $t9, $s1, 0xff
/* 0DAB44 8031FB44 308D00FF */  andi  $t5, $a0, 0xff
/* 0DAB48 8031FB48 A70E0000 */  sh    $t6, ($t8)
/* 0DAB4C 8031FB4C 03208825 */  move  $s1, $t9
/* 0DAB50 8031FB50 A1820000 */  sb    $v0, ($t4)
/* 0DAB54 8031FB54 01A02025 */  move  $a0, $t5
.L8031FB58:
/* 0DAB58 8031FB58 24420001 */  addiu $v0, $v0, 1
/* 0DAB5C 8031FB5C 304F00FF */  andi  $t7, $v0, 0xff
/* 0DAB60 8031FB60 00032843 */  sra   $a1, $v1, 1
/* 0DAB64 8031FB64 30AEFFFF */  andi  $t6, $a1, 0xffff
/* 0DAB68 8031FB68 29E10008 */  slti  $at, $t7, 8
/* 0DAB6C 8031FB6C 01E01025 */  move  $v0, $t7
/* 0DAB70 8031FB70 1420FFE8 */  bnez  $at, .L8031FB14
/* 0DAB74 8031FB74 01C02825 */   move  $a1, $t6
/* 0DAB78 8031FB78 00001025 */  move  $v0, $zero
/* 0DAB7C 8031FB7C 18800083 */  blez  $a0, .L8031FD8C
/* 0DAB80 8031FB80 00801825 */   move  $v1, $a0
.L8031FB84:
/* 0DAB84 8031FB84 0102C021 */  addu  $t8, $t0, $v0
/* 0DAB88 8031FB88 93190000 */  lbu   $t9, ($t8)
/* 0DAB8C 8031FB8C 2F210008 */  sltiu $at, $t9, 8
/* 0DAB90 8031FB90 10200079 */  beqz  $at, .L8031FD78
/* 0DAB94 8031FB94 0019C880 */   sll   $t9, $t9, 2
/* 0DAB98 8031FB98 3C018034 */  lui   $at, %hi(jtbl_80338418)
/* 0DAB9C 8031FB9C 00390821 */  addu  $at, $at, $t9
/* 0DABA0 8031FBA0 8C398418 */  lw    $t9, %lo(jtbl_80338418)($at)
/* 0DABA4 8031FBA4 03200008 */  jr    $t9
/* 0DABA8 8031FBA8 00000000 */   nop   
glabel L8031FBAC
/* 0DABAC 8031FBAC C4E4003C */  lwc1  $f4, 0x3c($a3)
/* 0DABB0 8031FBB0 0002C040 */  sll   $t8, $v0, 1
/* 0DABB4 8031FBB4 00D8C821 */  addu  $t9, $a2, $t8
/* 0DABB8 8031FBB8 4600218D */  trunc.w.s $f6, $f4
/* 0DABBC 8031FBBC 872C0000 */  lh    $t4, ($t9)
/* 0DABC0 8031FBC0 440D3000 */  mfc1  $t5, $f6
/* 0DABC4 8031FBC4 00000000 */  nop   
/* 0DABC8 8031FBC8 000D7C00 */  sll   $t7, $t5, 0x10
/* 0DABCC 8031FBCC 000F7403 */  sra   $t6, $t7, 0x10
/* 0DABD0 8031FBD0 01CC082A */  slt   $at, $t6, $t4
/* 0DABD4 8031FBD4 50200069 */  beql  $at, $zero, .L8031FD7C
/* 0DABD8 8031FBD8 24420001 */   addiu $v0, $v0, 1
/* 0DABDC 8031FBDC 24620001 */  addiu $v0, $v1, 1
/* 0DABE0 8031FBE0 304D00FF */  andi  $t5, $v0, 0xff
/* 0DABE4 8031FBE4 10000064 */  b     .L8031FD78
/* 0DABE8 8031FBE8 01A01025 */   move  $v0, $t5
glabel L8031FBEC
/* 0DABEC 8031FBEC C4E80040 */  lwc1  $f8, 0x40($a3)
/* 0DABF0 8031FBF0 00026040 */  sll   $t4, $v0, 1
/* 0DABF4 8031FBF4 00CC6821 */  addu  $t5, $a2, $t4
/* 0DABF8 8031FBF8 4600428D */  trunc.w.s $f10, $f8
/* 0DABFC 8031FBFC 85AF0000 */  lh    $t7, ($t5)
/* 0DAC00 8031FC00 44185000 */  mfc1  $t8, $f10
/* 0DAC04 8031FC04 00000000 */  nop   
/* 0DAC08 8031FC08 0018CC00 */  sll   $t9, $t8, 0x10
/* 0DAC0C 8031FC0C 00197403 */  sra   $t6, $t9, 0x10
/* 0DAC10 8031FC10 01CF082A */  slt   $at, $t6, $t7
/* 0DAC14 8031FC14 50200059 */  beql  $at, $zero, .L8031FD7C
/* 0DAC18 8031FC18 24420001 */   addiu $v0, $v0, 1
/* 0DAC1C 8031FC1C 24620001 */  addiu $v0, $v1, 1
/* 0DAC20 8031FC20 305800FF */  andi  $t8, $v0, 0xff
/* 0DAC24 8031FC24 10000054 */  b     .L8031FD78
/* 0DAC28 8031FC28 03001025 */   move  $v0, $t8
glabel L8031FC2C
/* 0DAC2C 8031FC2C C4F00044 */  lwc1  $f16, 0x44($a3)
/* 0DAC30 8031FC30 00027840 */  sll   $t7, $v0, 1
/* 0DAC34 8031FC34 00CFC021 */  addu  $t8, $a2, $t7
/* 0DAC38 8031FC38 4600848D */  trunc.w.s $f18, $f16
/* 0DAC3C 8031FC3C 87190000 */  lh    $t9, ($t8)
/* 0DAC40 8031FC40 440C9000 */  mfc1  $t4, $f18
/* 0DAC44 8031FC44 00000000 */  nop   
/* 0DAC48 8031FC48 000C6C00 */  sll   $t5, $t4, 0x10
/* 0DAC4C 8031FC4C 000D7403 */  sra   $t6, $t5, 0x10
/* 0DAC50 8031FC50 01D9082A */  slt   $at, $t6, $t9
/* 0DAC54 8031FC54 50200049 */  beql  $at, $zero, .L8031FD7C
/* 0DAC58 8031FC58 24420001 */   addiu $v0, $v0, 1
/* 0DAC5C 8031FC5C 24620001 */  addiu $v0, $v1, 1
/* 0DAC60 8031FC60 304C00FF */  andi  $t4, $v0, 0xff
/* 0DAC64 8031FC64 10000044 */  b     .L8031FD78
/* 0DAC68 8031FC68 01801025 */   move  $v0, $t4
glabel L8031FC6C
/* 0DAC6C 8031FC6C C4E4003C */  lwc1  $f4, 0x3c($a3)
/* 0DAC70 8031FC70 0002C840 */  sll   $t9, $v0, 1
/* 0DAC74 8031FC74 00D96021 */  addu  $t4, $a2, $t9
/* 0DAC78 8031FC78 4600218D */  trunc.w.s $f6, $f4
/* 0DAC7C 8031FC7C 858D0000 */  lh    $t5, ($t4)
/* 0DAC80 8031FC80 440F3000 */  mfc1  $t7, $f6
/* 0DAC84 8031FC84 00000000 */  nop   
/* 0DAC88 8031FC88 000FC400 */  sll   $t8, $t7, 0x10
/* 0DAC8C 8031FC8C 00187403 */  sra   $t6, $t8, 0x10
/* 0DAC90 8031FC90 01CD082A */  slt   $at, $t6, $t5
/* 0DAC94 8031FC94 54200039 */  bnezl $at, .L8031FD7C
/* 0DAC98 8031FC98 24420001 */   addiu $v0, $v0, 1
/* 0DAC9C 8031FC9C 24620001 */  addiu $v0, $v1, 1
/* 0DACA0 8031FCA0 304F00FF */  andi  $t7, $v0, 0xff
/* 0DACA4 8031FCA4 10000034 */  b     .L8031FD78
/* 0DACA8 8031FCA8 01E01025 */   move  $v0, $t7
glabel L8031FCAC
/* 0DACAC 8031FCAC C4E80040 */  lwc1  $f8, 0x40($a3)
/* 0DACB0 8031FCB0 00026840 */  sll   $t5, $v0, 1
/* 0DACB4 8031FCB4 00CD7821 */  addu  $t7, $a2, $t5
/* 0DACB8 8031FCB8 4600428D */  trunc.w.s $f10, $f8
/* 0DACBC 8031FCBC 85F80000 */  lh    $t8, ($t7)
/* 0DACC0 8031FCC0 44195000 */  mfc1  $t9, $f10
/* 0DACC4 8031FCC4 00000000 */  nop   
/* 0DACC8 8031FCC8 00196400 */  sll   $t4, $t9, 0x10
/* 0DACCC 8031FCCC 000C7403 */  sra   $t6, $t4, 0x10
/* 0DACD0 8031FCD0 01D8082A */  slt   $at, $t6, $t8
/* 0DACD4 8031FCD4 54200029 */  bnezl $at, .L8031FD7C
/* 0DACD8 8031FCD8 24420001 */   addiu $v0, $v0, 1
/* 0DACDC 8031FCDC 24620001 */  addiu $v0, $v1, 1
/* 0DACE0 8031FCE0 305900FF */  andi  $t9, $v0, 0xff
/* 0DACE4 8031FCE4 10000024 */  b     .L8031FD78
/* 0DACE8 8031FCE8 03201025 */   move  $v0, $t9
glabel L8031FCEC
/* 0DACEC 8031FCEC C4F00044 */  lwc1  $f16, 0x44($a3)
/* 0DACF0 8031FCF0 0002C040 */  sll   $t8, $v0, 1
/* 0DACF4 8031FCF4 00D8C821 */  addu  $t9, $a2, $t8
/* 0DACF8 8031FCF8 4600848D */  trunc.w.s $f18, $f16
/* 0DACFC 8031FCFC 872C0000 */  lh    $t4, ($t9)
/* 0DAD00 8031FD00 440D9000 */  mfc1  $t5, $f18
/* 0DAD04 8031FD04 00000000 */  nop   
/* 0DAD08 8031FD08 000D7C00 */  sll   $t7, $t5, 0x10
/* 0DAD0C 8031FD0C 000F7403 */  sra   $t6, $t7, 0x10
/* 0DAD10 8031FD10 01CC082A */  slt   $at, $t6, $t4
/* 0DAD14 8031FD14 54200019 */  bnezl $at, .L8031FD7C
/* 0DAD18 8031FD18 24420001 */   addiu $v0, $v0, 1
/* 0DAD1C 8031FD1C 24620001 */  addiu $v0, $v1, 1
/* 0DAD20 8031FD20 304D00FF */  andi  $t5, $v0, 0xff
/* 0DAD24 8031FD24 10000014 */  b     .L8031FD78
/* 0DAD28 8031FD28 01A01025 */   move  $v0, $t5
glabel L8031FD2C
/* 0DAD2C 8031FD2C 0002C040 */  sll   $t8, $v0, 1
/* 0DAD30 8031FD30 00D8C821 */  addu  $t9, $a2, $t8
/* 0DAD34 8031FD34 872E0000 */  lh    $t6, ($t9)
/* 0DAD38 8031FD38 856F0000 */  lh    $t7, ($t3)
/* 0DAD3C 8031FD3C 51EE000F */  beql  $t7, $t6, .L8031FD7C
/* 0DAD40 8031FD40 24420001 */   addiu $v0, $v0, 1
/* 0DAD44 8031FD44 24620001 */  addiu $v0, $v1, 1
/* 0DAD48 8031FD48 304C00FF */  andi  $t4, $v0, 0xff
/* 0DAD4C 8031FD4C 1000000A */  b     .L8031FD78
/* 0DAD50 8031FD50 01801025 */   move  $v0, $t4
glabel L8031FD54
/* 0DAD54 8031FD54 0002C040 */  sll   $t8, $v0, 1
/* 0DAD58 8031FD58 00D8C821 */  addu  $t9, $a2, $t8
/* 0DAD5C 8031FD5C 872F0000 */  lh    $t7, ($t9)
/* 0DAD60 8031FD60 854D0000 */  lh    $t5, ($t2)
/* 0DAD64 8031FD64 51AF0005 */  beql  $t5, $t7, .L8031FD7C
/* 0DAD68 8031FD68 24420001 */   addiu $v0, $v0, 1
/* 0DAD6C 8031FD6C 24620001 */  addiu $v0, $v1, 1
/* 0DAD70 8031FD70 304E00FF */  andi  $t6, $v0, 0xff
/* 0DAD74 8031FD74 01C01025 */  move  $v0, $t6
.L8031FD78:
/* 0DAD78 8031FD78 24420001 */  addiu $v0, $v0, 1
.L8031FD7C:
/* 0DAD7C 8031FD7C 304C00FF */  andi  $t4, $v0, 0xff
/* 0DAD80 8031FD80 0183082A */  slt   $at, $t4, $v1
/* 0DAD84 8031FD84 1420FF7F */  bnez  $at, .L8031FB84
/* 0DAD88 8031FD88 01801025 */   move  $v0, $t4
.L8031FD8C:
/* 0DAD8C 8031FD8C 14620003 */  bne   $v1, $v0, .L8031FD9C
/* 0DAD90 8031FD90 0011C040 */   sll   $t8, $s1, 1
/* 0DAD94 8031FD94 1000000A */  b     .L8031FDC0
/* 0DAD98 8031FD98 00001825 */   move  $v1, $zero
.L8031FD9C:
/* 0DAD9C 8031FD9C 0138C821 */  addu  $t9, $t1, $t8
/* 0DADA0 8031FDA0 872F0000 */  lh    $t7, ($t9)
/* 0DADA4 8031FDA4 26310001 */  addiu $s1, $s1, 1
/* 0DADA8 8031FDA8 322E00FF */  andi  $t6, $s1, 0xff
/* 0DADAC 8031FDAC 01E01825 */  move  $v1, $t7
/* 0DADB0 8031FDB0 306DFF00 */  andi  $t5, $v1, 0xff00
/* 0DADB4 8031FDB4 01A01825 */  move  $v1, $t5
/* 0DADB8 8031FDB8 01C08825 */  move  $s1, $t6
/* 0DADBC 8031FDBC A3AF0057 */  sb    $t7, 0x57($sp)
.L8031FDC0:
/* 0DADC0 8031FDC0 306CFF00 */  andi  $t4, $v1, 0xff00
/* 0DADC4 8031FDC4 1580FF50 */  bnez  $t4, .L8031FB08
/* 0DADC8 8031FDC8 00608025 */   move  $s0, $v1
.L8031FDCC:
/* 0DADCC 8031FDCC 3C028033 */  lui   $v0, %hi(sCurrentMusicDynamic) # $v0, 0x8033
/* 0DADD0 8031FDD0 90421EAC */  lbu   $v0, %lo(sCurrentMusicDynamic)($v0)
/* 0DADD4 8031FDD4 93B80057 */  lbu   $t8, 0x57($sp)
/* 0DADD8 8031FDD8 240100FF */  li    $at, 255
/* 0DADDC 8031FDDC 00008825 */  move  $s1, $zero
/* 0DADE0 8031FDE0 53020036 */  beql  $t8, $v0, .L8031FEBC
/* 0DADE4 8031FDE4 8FBF0024 */   lw    $ra, 0x24($sp)
/* 0DADE8 8031FDE8 1441000C */  bne   $v0, $at, .L8031FE1C
/* 0DADEC 8031FDEC 24030001 */   li    $v1, 1
/* 0DADF0 8031FDF0 00187880 */  sll   $t7, $t8, 2
/* 0DADF4 8031FDF4 01F87823 */  subu  $t7, $t7, $t8
/* 0DADF8 8031FDF8 3C0E8033 */  lui   $t6, %hi(sMusicDynamics) # $t6, 0x8033
/* 0DADFC 8031FDFC 24190001 */  li    $t9, 1
/* 0DAE00 8031FE00 25CE1F50 */  addiu $t6, %lo(sMusicDynamics) # addiu $t6, $t6, 0x1f50
/* 0DAE04 8031FE04 000F7880 */  sll   $t7, $t7, 2
/* 0DAE08 8031FE08 240D0001 */  li    $t5, 1
/* 0DAE0C 8031FE0C A7B9003A */  sh    $t9, 0x3a($sp)
/* 0DAE10 8031FE10 A7AD0038 */  sh    $t5, 0x38($sp)
/* 0DAE14 8031FE14 1000000C */  b     .L8031FE48
/* 0DAE18 8031FE18 01EE9021 */   addu  $s2, $t7, $t6
.L8031FE1C:
/* 0DAE1C 8031FE1C 93AC0057 */  lbu   $t4, 0x57($sp)
/* 0DAE20 8031FE20 3C0D8033 */  lui   $t5, %hi(sMusicDynamics) # $t5, 0x8033
/* 0DAE24 8031FE24 25AD1F50 */  addiu $t5, %lo(sMusicDynamics) # addiu $t5, $t5, 0x1f50
/* 0DAE28 8031FE28 000CC880 */  sll   $t9, $t4, 2
/* 0DAE2C 8031FE2C 032CC823 */  subu  $t9, $t9, $t4
/* 0DAE30 8031FE30 0019C880 */  sll   $t9, $t9, 2
/* 0DAE34 8031FE34 032D9021 */  addu  $s2, $t9, $t5
/* 0DAE38 8031FE38 86580004 */  lh    $t8, 4($s2)
/* 0DAE3C 8031FE3C 864F000A */  lh    $t7, 0xa($s2)
/* 0DAE40 8031FE40 A7B8003A */  sh    $t8, 0x3a($sp)
/* 0DAE44 8031FE44 A7AF0038 */  sh    $t7, 0x38($sp)
.L8031FE48:
/* 0DAE48 8031FE48 864E0000 */  lh    $t6, ($s2)
/* 0DAE4C 8031FE4C 00608025 */  move  $s0, $v1
/* 0DAE50 8031FE50 00002025 */  move  $a0, $zero
/* 0DAE54 8031FE54 01C36024 */  and   $t4, $t6, $v1
/* 0DAE58 8031FE58 11800004 */  beqz  $t4, .L8031FE6C
/* 0DAE5C 8031FE5C 322500FF */   andi  $a1, $s1, 0xff
/* 0DAE60 8031FE60 92460003 */  lbu   $a2, 3($s2)
/* 0DAE64 8031FE64 0C0C7E22 */  jal   fade_channel_volume_scale
/* 0DAE68 8031FE68 97A7003A */   lhu   $a3, 0x3a($sp)
.L8031FE6C:
/* 0DAE6C 8031FE6C 86590006 */  lh    $t9, 6($s2)
/* 0DAE70 8031FE70 00002025 */  move  $a0, $zero
/* 0DAE74 8031FE74 322500FF */  andi  $a1, $s1, 0xff
/* 0DAE78 8031FE78 03306824 */  and   $t5, $t9, $s0
/* 0DAE7C 8031FE7C 11A00003 */  beqz  $t5, .L8031FE8C
/* 0DAE80 8031FE80 97A70038 */   lhu   $a3, 0x38($sp)
/* 0DAE84 8031FE84 0C0C7E22 */  jal   fade_channel_volume_scale
/* 0DAE88 8031FE88 92460009 */   lbu   $a2, 9($s2)
.L8031FE8C:
/* 0DAE8C 8031FE8C 26310001 */  addiu $s1, $s1, 1
/* 0DAE90 8031FE90 322F00FF */  andi  $t7, $s1, 0xff
/* 0DAE94 8031FE94 00101840 */  sll   $v1, $s0, 1
/* 0DAE98 8031FE98 29E10010 */  slti  $at, $t7, 0x10
/* 0DAE9C 8031FE9C 3078FFFF */  andi  $t8, $v1, 0xffff
/* 0DAEA0 8031FEA0 01E08825 */  move  $s1, $t7
/* 0DAEA4 8031FEA4 1420FFE8 */  bnez  $at, .L8031FE48
/* 0DAEA8 8031FEA8 03001825 */   move  $v1, $t8
/* 0DAEAC 8031FEAC 93AE0057 */  lbu   $t6, 0x57($sp)
/* 0DAEB0 8031FEB0 3C018033 */  lui   $at, %hi(sCurrentMusicDynamic) # $at, 0x8033
/* 0DAEB4 8031FEB4 A02E1EAC */  sb    $t6, %lo(sCurrentMusicDynamic)($at)
/* 0DAEB8 8031FEB8 8FBF0024 */  lw    $ra, 0x24($sp)
.L8031FEBC:
/* 0DAEBC 8031FEBC 8FB00018 */  lw    $s0, 0x18($sp)
/* 0DAEC0 8031FEC0 8FB1001C */  lw    $s1, 0x1c($sp)
/* 0DAEC4 8031FEC4 8FB20020 */  lw    $s2, 0x20($sp)
/* 0DAEC8 8031FEC8 03E00008 */  jr    $ra
/* 0DAECC 8031FECC 27BD0060 */   addiu $sp, $sp, 0x60
