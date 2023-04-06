#ifndef _ULTRA64_OS_INTERNAL_H_
#define _ULTRA64_OS_INTERNAL_H_

/* Internal functions used by the operating system */
/* Do not include this header in application code */

/* Variables */

//extern u64 osClockRate;

/* Functions */

/*u32 __osProbeTLB(void *);
u32 __osDisableInt(void);
void __osRestoreInt(u32);*/
OSThread *__osGetCurrFaultedThread(void);

#endif
