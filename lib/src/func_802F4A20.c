#include "new_func.h"

void func_802F4A20(void) {
    __OSTranxInfo *sp1c;
    volatile u32 sp18;
    sp1c = &__osDiskHandle->transferInfo;
    WAIT_ON_IOBUSY(sp18);
    HW_REG(ASIC_BM_CTL, u32) = BUFFER_MANAGER_RESET | sp1c->bmCtlShadow; //should be unk10??
    WAIT_ON_IOBUSY(sp18);
    HW_REG(ASIC_BM_CTL, u32) = sp1c->bmCtlShadow;
    func_802F4B08();
    HW_REG(PI_STATUS_REG, u32) = PI_STATUS_CLEAR_INTR;
    __OSGlobalIntMask |= 0x00100401; // TODO: fix magic numbers
}

typedef struct OSEventMessageStruct_0_s {
    OSMesgQueue *queue;
    OSMesg msg;
} OSEventMessageStruct_0;

extern OSEventMessageStruct_0 __osEventStateTab[16]; // should be OS_NUM_EVENTS + 1 I think

void func_802F4B08(void) {
    OSEventMessageStruct_0 *sp2c;
    OSMesgQueue *sp28;
    u32 sp24;
    register OSThread *s0;
    sp2c = &__osEventStateTab[OS_EVENT_PI];
    sp28 = sp2c->queue;
    if (!sp28 || sp28->validCount >= sp28->msgCount) {
        return;
    }
    sp24 = (sp28->first + sp28->validCount) % sp28->msgCount;
    sp28->msg[sp24] = sp2c->msg;
    sp28->validCount++;
    if (sp28->mtqueue->next != NULL) {
        s0 = __osPopThread(&sp28->mtqueue);
        __osEnqueueThread(&D_80334898, s0);
    }
}
