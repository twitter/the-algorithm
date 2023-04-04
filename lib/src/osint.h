#ifndef _OSINT_H
#define _OSINT_H
#include "libultra_internal.h"

typedef struct __OSEventState
{
    OSMesgQueue *messageQueue;
    OSMesg message;
} __OSEventState;
extern struct __osThreadTail
{
    OSThread *next;
    OSPri priority;
} __osThreadTail;

//maybe should be in exceptasm.h?
extern void __osEnqueueAndYield(OSThread **);
extern void __osDequeueThread(OSThread **, OSThread *);
extern void __osEnqueueThread(OSThread **, OSThread *);
extern OSThread *__osPopThread(OSThread **);
extern void __osDispatchThread(void);

extern void __osSetTimerIntr(OSTime);
extern OSTime __osInsertTimer(OSTimer *);
extern void __osTimerInterrupt(void);
extern u32 __osProbeTLB(void *);
extern int     __osSpDeviceBusy(void);

#ifdef AVOID_UB
extern OSThread_ListHead D_80334890_fix;
#else
extern OSThread *__osRunningThread;
extern OSThread *D_8033489C;
extern OSThread *__osFaultedThread;
extern OSThread *D_80334898;
#endif

extern OSTimer *__osTimerList;
extern OSTimer __osBaseTimer;
extern OSTime __osCurrentTime;
extern u32 __osBaseCounter;
extern u32 __osViIntrCount;
extern u32 __osTimerCounter;

extern __OSEventState __osEventStateTab[OS_NUM_EVENTS];


//not sure if this should be here
extern s32 osViClock;
extern void __osTimerServicesInit(void);
extern s32 __osAiDeviceBusy(void);
extern int __osDpDeviceBusy(void);
#endif
