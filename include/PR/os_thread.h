#ifndef _ULTRA64_THREAD_H_
#define _ULTRA64_THREAD_H_
#include "ultratypes.h"
/* Recommended priorities for system threads */
#define OS_PRIORITY_MAX      255
#define OS_PRIORITY_VIMGR    254
#define OS_PRIORITY_RMON     250
#define OS_PRIORITY_RMONSPIN 200
#define OS_PRIORITY_PIMGR    150
#define OS_PRIORITY_SIMGR    140
#define OS_PRIORITY_APPMAX   127
#define OS_PRIORITY_IDLE       0

#define OS_STATE_STOPPED    1
#define OS_STATE_RUNNABLE    2
#define OS_STATE_RUNNING    4
#define OS_STATE_WAITING    8

/* Types */

typedef s32 OSPri;
typedef s32 OSId;

typedef union
{
    struct {f32 f_odd; f32 f_even;} f;
} __OSfp;

typedef struct
{
    /* registers */
    /*0x20*/  u64 at, v0, v1, a0, a1, a2, a3;
    /*0x58*/  u64 t0, t1, t2, t3, t4, t5, t6, t7;
    /*0x98*/  u64 s0, s1, s2, s3, s4, s5, s6, s7;
    /*0xD8*/  u64 t8, t9, gp, sp, s8, ra;
    /*0x108*/ u64 lo, hi;
    /*0x118*/ u32 sr, pc, cause, badvaddr, rcp;
    /*0x12C*/ u32 fpcsr;
    __OSfp  fp0,  fp2,  fp4,  fp6,  fp8, fp10, fp12, fp14;
    __OSfp fp16, fp18, fp20, fp22, fp24, fp26, fp28, fp30;
} __OSThreadContext;

typedef struct
{
    u32 flag;
    u32 count;
    u64 time;
} __OSThreadprofile_s;

typedef struct OSThread_s
{
    /*0x00*/ struct OSThread_s *next;
    /*0x04*/ OSPri priority;
    /*0x08*/ struct OSThread_s **queue;
    /*0x0C*/ struct OSThread_s *tlnext;
    /*0x10*/ u16 state;
    /*0x12*/ u16 flags;
    /*0x14*/ OSId id;
    /*0x18*/ int fp;
    /*0x1C*/ __OSThreadprofile_s *thprof;
    /*0x20*/ __OSThreadContext context;
} OSThread;


/* Functions */

void osCreateThread(OSThread *thread, OSId id, void (*entry)(void *),
    void *arg, void *sp, OSPri pri);
OSId osGetThreadId(OSThread *thread);
OSPri osGetThreadPri(OSThread *thread);
void osSetThreadPri(OSThread *thread, OSPri pri);
void osStartThread(OSThread *thread);
void osStopThread(OSThread *thread);

#endif
