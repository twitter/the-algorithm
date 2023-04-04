#include "libultra_internal.h"

extern OSMgrArgs __osPiDevMgr;

OSMesgQueue *osPiGetCmdQueue(void) {
    if (!__osPiDevMgr.initialized) {
        return NULL;
    }
    return __osPiDevMgr.cmdQueue;
}
