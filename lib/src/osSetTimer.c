#include "libultra_internal.h"
extern OSTimer *D_80334830;
extern u64 __osInsertTimer(OSTimer *);

u32 osSetTimer(OSTimer *a0, OSTime a1, u64 a2, OSMesgQueue *a3, OSMesg a4) {
    u64 sp18;
    a0->next = NULL;
    a0->prev = NULL;
    a0->interval = a2;
    if (a1 != 0) {
        a0->remaining = a1;
    } else {
        a0->remaining = a2;
    }
    a0->mq = a3;
    a0->msg = a4;
    sp18 = __osInsertTimer(a0);
    if (D_80334830->next == a0) {
        __osSetTimerIntr(sp18);
    }
    return 0;
}
