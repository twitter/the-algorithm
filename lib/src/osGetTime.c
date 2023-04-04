#include "libultra_internal.h"

extern OSTime __osCurrentTime;
extern u32 __osBaseCounter;

OSTime osGetTime() {
    u32 tmpTime;
    u32 elapseCount;
    OSTime currentCount;
    register u32 saveMask;

    saveMask = __osDisableInt();
    tmpTime = osGetCount();
    elapseCount = tmpTime - __osBaseCounter;
    currentCount = __osCurrentTime;
    __osRestoreInt(saveMask);
    return currentCount + elapseCount;
}
