#ifndef _ULTRA64_OS_MISC_H_
#define _ULTRA64_OS_MISC_H_
#include <PR/ultratypes.h>
/* Miscellaneous OS functions */

void osInitialize(void);
u32 osGetCount(void);

uintptr_t osVirtualToPhysical(void *);

#endif
