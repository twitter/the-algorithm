#include "libultra_internal.h"

OSTimer __osBaseTimer;
OSTimer *__osTimerList = &__osBaseTimer;
OSTime __osCurrentTime;
u32 __osBaseCounter;
u32 __osViIntrCount;
u32 __osTimerCounter;

void __osTimerServicesInit(void) {
    __osCurrentTime = 0;
    __osBaseCounter = 0;
    __osViIntrCount = 0;
    __osTimerList->prev = __osTimerList;
    __osTimerList->next = __osTimerList->prev;
    __osTimerList->remaining = 0;
    __osTimerList->interval = __osTimerList->remaining;
    __osTimerList->mq = NULL;
    __osTimerList->msg = NULL;
}

void __osTimerInterrupt(void) {
    OSTimer *t;
    u32 count;
    u32 elapsedCycles;

    if (__osTimerList->next == __osTimerList) {
        return;
    }

    while (TRUE) {
        t = __osTimerList->next;
        if (t == __osTimerList) {
            __osSetCompare(0);
            __osTimerCounter = 0;
            break;
        }
        count = osGetCount();
        elapsedCycles = count - __osTimerCounter;
        __osTimerCounter = count;
        if (elapsedCycles < t->remaining) {
            t->remaining -= elapsedCycles;
            __osSetTimerIntr(t->remaining);
            return;
        } else {
            t->prev->next = t->next;
            t->next->prev = t->prev;
            t->next = NULL;
            t->prev = NULL;
            if (t->mq != NULL) {
                osSendMesg(t->mq, t->msg, OS_MESG_NOBLOCK);
            }
            if (t->interval != 0) {
                t->remaining = t->interval;
                __osInsertTimer(t);
            }
        }
    }
}

void __osSetTimerIntr(OSTime time) {
    OSTime newTime;
    s32 savedMask = __osDisableInt();
    __osTimerCounter = osGetCount();
    newTime = time + __osTimerCounter;
    __osSetCompare(newTime);
    __osRestoreInt(savedMask);
}

OSTime __osInsertTimer(OSTimer *t) {
    OSTimer *timep;
    OSTime time;
    s32 savedMask;

    savedMask = __osDisableInt();
    for (timep = __osTimerList->next, time = t->remaining; timep != __osTimerList && time > timep->remaining;
         time -= timep->remaining, timep = timep->next) {
        ;
    }
    t->remaining = time;
    if (timep != __osTimerList) {
        timep->remaining -= time;
    }
    t->next = timep;
    t->prev = timep->prev;
    timep->prev->next = t;
    timep->prev = t;
    __osRestoreInt(savedMask);
    return time;
}
