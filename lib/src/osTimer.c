#include "libultra_internal.h"


// TODO: document
OSTimer D_80365D80;
OSTimer *D_80334830 = &D_80365D80;
OSTime _osCurrentTime;
u32 D_80365DA8;
u32 __osViIntrCount;
u32 D_80365DB0;
void __osTimerServicesInit() {
    _osCurrentTime = 0;
    D_80365DA8 = 0;
    __osViIntrCount = 0;
    D_80334830->prev = D_80334830;
    D_80334830->next = D_80334830->prev;
    D_80334830->remaining = 0;
    D_80334830->interval = D_80334830->remaining;
    D_80334830->mq = NULL;
    D_80334830->msg = NULL;
}

void __osTimerInterrupt() {
    OSTimer *sp24;
    u32 sp20;
    u32 sp1c;
    if (D_80334830->next == D_80334830) {
        return;
    }
    while (1) {
        sp24 = D_80334830->next;
        if (sp24 == D_80334830) {
            __osSetCompare(0);
            D_80365DB0 = 0;
            break;
        }
        sp20 = osGetCount();
        sp1c = sp20 - D_80365DB0;
        D_80365DB0 = sp20;
        if (sp1c < sp24->remaining) {
            sp24->remaining -= sp1c;
            __osSetTimerIntr(sp24->remaining);
            return;
        } else {
            sp24->prev->next = sp24->next;
            sp24->next->prev = sp24->prev;
            sp24->next = NULL;
            sp24->prev = NULL;
            if (sp24->mq != NULL) {
                osSendMesg(sp24->mq, sp24->msg, OS_MESG_NOBLOCK);
            }
            if (sp24->interval != 0) {
                sp24->remaining = sp24->interval;
                __osInsertTimer(sp24);
            }
        }
    }
}

void __osSetTimerIntr(u64 a0) {
    u64 tmp;
    s32 intDisabled = __osDisableInt();
    D_80365DB0 = osGetCount();
    tmp = a0 + D_80365DB0;
    __osSetCompare(tmp);
    __osRestoreInt(intDisabled);
}

u64 __osInsertTimer(OSTimer *a0) {
    OSTimer *sp34;
    u64 sp28;
    s32 intDisabled;
    intDisabled = __osDisableInt();
    for (sp34 = D_80334830->next, sp28 = a0->remaining; sp34 != D_80334830 && sp28 > sp34->remaining;
         sp28 -= sp34->remaining, sp34 = sp34->next) {
        ;
    }
    a0->remaining = sp28;
    if (sp34 != D_80334830) {
        sp34->remaining -= sp28;
    }
    a0->next = sp34;
    a0->prev = sp34->prev;
    sp34->prev->next = a0;
    sp34->prev = a0;
    __osRestoreInt(intDisabled);
    return sp28;
}
