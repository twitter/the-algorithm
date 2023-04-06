#include "new_func.h"

void func_802F4A20() {
    __OSTranxInfo *sp1c;
    volatile u32 sp18;
    //  lui   $t6, %hi(__osDiskHandle) # $t6, 0x8033
    //  lw    $t6, %lo(__osDiskHandle)($t6)
    //  addiu $sp, $sp, -0x20
    //  sw    $ra, 0x14($sp)
    //  addiu $t7, $t6, 0x14
    //  sw    $t7, 0x1c($sp)
    sp1c = &__osDiskHandle->transferInfo;
    //  lui   $t8, %hi(PI_STATUS_REG) # $t8, 0xa460
    //  lw    $t9, %lo(PI_STATUS_REG)($t8)
    //  sw    $t9, 0x18($sp)
    // sp18 = HW_REG(PI_STATUS_REG, u32);
    // while(sp18 & 0x2) sp18 = HW_REG(PI_STATUS_REG, u32);
    WAIT_ON_IOBUSY(sp18);
    //  lw    $t0, 0x18($sp)
    //  andi  $t1, $t0, 2
    //  beqz  $t1, .L802F4A70
    //   nop
    // L802F4A54:
    //  lui   $t2, %hi(PI_STATUS_REG) # $t2, 0xa460
    //  lw    $t3, %lo(PI_STATUS_REG)($t2)
    //  sw    $t3, 0x18($sp)
    //  lw    $t4, 0x18($sp)
    //  andi  $t5, $t4, 2
    //  bnez  $t5, .L802F4A54
    //   nop

    // L802F4A70:
    //  lw    $t6, 0x1c($sp)
    //  lui   $at, 0x1000
    //  lui   $t9, %hi(D_A5000510) # $t9, 0xa500
    //  lw    $t7, 0x14($t6)
    //  lui   $t0, %hi(PI_STATUS_REG) # $t0, 0xa460
    //  or    $t8, $t7, $at
    //  sw    $t8, %lo(D_A5000510)($t9)
    HW_REG(ASIC_BM_CTL, u32) = BUFFER_MANAGER_RESET | sp1c->bmCtlShadow; //should be unk10??
    //  lw    $t1, %lo(PI_STATUS_REG)($t0)
    //  sw    $t1, 0x18($sp)
    //  lw    $t2, 0x18($sp)
    //  andi  $t3, $t2, 2
    //  beqz  $t3, .L802F4AC0
    //   nop
    WAIT_ON_IOBUSY(sp18);
    // L802F4AA4:
    //  lui   $t4, %hi(PI_STATUS_REG) # $t4, 0xa460
    //  lw    $t5, %lo(PI_STATUS_REG)($t4)
    //  sw    $t5, 0x18($sp)
    //  lw    $t6, 0x18($sp)
    //  andi  $t7, $t6, 2
    //  bnez  $t7, .L802F4AA4
    //   nop
    // L802F4AC0:

    //  lw    $t8, 0x1c($sp)
    //  lui   $t0, %hi(D_A5000510) # $t0, 0xa500
    //  lw    $t9, 0x14($t8)
    //  jal   func_802F4B08
    //   sw    $t9, %lo(D_A5000510)($t0)
    HW_REG(ASIC_BM_CTL, u32) = sp1c->bmCtlShadow;
    func_802F4B08();
    //  li    $t1, 2
    //  lui   $t2, %hi(PI_STATUS_REG) # $t2, 0xa460
    //  sw    $t1, %lo(PI_STATUS_REG)($t2)
    HW_REG(PI_STATUS_REG, u32) = PI_STATUS_CLEAR_INTR;
    //  lui   $t3, %hi(D_8030208C) # $t3, 0x8030
    //  lw    $t3, %lo(D_8030208C)($t3)
    //  lui   $at, (0x00100401 >> 16) # lui $at, 0x10
    //  lw    $ra, 0x14($sp)
    //  ori   $at, (0x00100401 & 0xFFFF) # ori $at, $at, 0x401
    //  or    $t4, $t3, $at
    //  lui   $at, %hi(D_8030208C) # $at, 0x8030
    //  sw    $t4, %lo(D_8030208C)($at)
    D_8030208C |= 0x00100401; //TODO: fix magic numbers
    //  jr    $ra
    //   addiu $sp, $sp, 0x20
}

typedef struct OSEventMessageStruct_0_s {
    OSMesgQueue *queue;
    OSMesg msg;
} OSEventMessageStruct_0;

extern OSEventMessageStruct_0 D_80363830[16]; // should be OS_NUM_EVENTS + 1 I think
void func_802F4B08() {
    OSEventMessageStruct_0 *sp2c;
    OSMesgQueue *sp28;
    u32 sp24;
    register OSThread *s0;
    //  addiu $sp, $sp, -0x30
    //  lui   $t6, %hi(D_80363830) # $t6, 0x8033
    //  addiu $t6, %lo(D_80363830) # addiu $t6, $t6, 0x36d0
    //  addiu $t7, $t6, 0x40
    //  sw    $ra, 0x1c($sp)
    //  sw    $s0, 0x18($sp)
    //  sw    $t7, 0x2c($sp)
    sp2c = &D_80363830[OS_EVENT_PI];
    //  lw    $t8, 0x40($t6)
    //  beqz  $t8, .L802F4BE0
    //   sw    $t8, 0x28($sp)
    sp28 = sp2c->queue;
    //  lw    $t9, 8($t8)
    //  lw    $t0, 0x10($t8)
    //  slt   $at, $t9, $t0
    //  beqz  $at, .L802F4BE0
    //   nop
    if (!sp28 || sp28->validCount >= sp28->msgCount)
        return;
    //  lw    $t1, 0x28($sp)
    //  lw    $t6, 0x2c($sp)
    //  lw    $t2, 0xc($t1)
    //  lw    $t3, 8($t1)
    //  lw    $t5, 0x10($t1)
    //  addu  $t4, $t2, $t3
    //  div   $zero, $t4, $t5
    //  mfhi  $t7
    //  sw    $t7, 0x24($sp)
    sp24 = (sp28->first + sp28->validCount) % sp28->msgCount;
    //  lw    $t0, 0x14($t1)
    //  lw    $t8, 4($t6)
    //  sll   $t9, $t7, 2
    //  addu  $t2, $t0, $t9
    //  sw    $t8, ($t2)
    sp28->msg[sp24] = sp2c->msg;
    //  lw    $t3, 0x28($sp)
    //  bnez  $t5, .L802F4B8C
    //   nop
    //  break 7
    // L802F4B8C:
    //  li    $at, -1
    //  bne   $t5, $at, .L802F4BA4
    //   lui   $at, 0x8000
    //  bne   $t4, $at, .L802F4BA4
    //   nop
    //  break 6
    // L802F4BA4:
    //  lw    $t4, 8($t3)
    //  addiu $t5, $t4, 1
    //  sw    $t5, 8($t3)
    sp28->validCount += 1;
    //  lw    $t6, 0x28($sp)
    //  lw    $t7, ($t6)
    //  lw    $t1, ($t7)
    //  beqz  $t1, .L802F4BE0
    //   nop
    //  jal   __osPopThread
    //   move  $a0, $t6
    if (sp28->mtqueue->next != NULL) {
        s0 = __osPopThread(&sp28->mtqueue);
        //  move  $s0, $v0
        //  lui   $a0, %hi(D_80334898) # $a0, 0x8030
        //  addiu $a0, %lo(D_80334898) # addiu $a0, $a0, 0x2ef8
        //  jal   __osEnqueueThread
        //   move  $a1, $s0
        __osEnqueueThread(&D_80334898, s0);
    }
    // L802F4BE0:
    //  lw    $ra, 0x1c($sp)
    //  lw    $s0, 0x18($sp)
    //  addiu $sp, $sp, 0x30
    //  jr    $ra
    //   nop

    //  nop
    //  nop
    //  nop
}
