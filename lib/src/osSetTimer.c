#include "libultra_internal.h"

extern OSTimer *__osTimerList;
extern u64 __osInsertTimer(OSTimer *);

u32 osSetTimer(OSTimer *t, OSTime countdown, OSTime interval, OSMesgQueue *mq, OSMesg msg) {
    OSTime time;
    t->next = NULL;
    t->prev = NULL;
    t->interval = interval;
    if (countdown != 0) {
        t->remaining = countdown;
    } else {
        t->remaining = interval;
    }
    t->mq = mq;
    t->msg = msg;
    time = __osInsertTimer(t);
    if (__osTimerList->next == t) {
        __osSetTimerIntr(time);
    }
    return 0;
}
