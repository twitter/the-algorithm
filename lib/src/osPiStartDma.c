#include "libultra_internal.h"

extern OSMgrArgs __osPiDevMgr;

s32 osPiStartDma(OSIoMesg *mb, s32 priority, s32 direction, uintptr_t devAddr, void *vAddr,
                 size_t nbytes, OSMesgQueue *mq) {
    register s32 result;
    register OSMesgQueue *cmdQueue;
    if (!__osPiDevMgr.initialized) {
        return -1;
    }

    // TODO: name magic constants
    if (direction == OS_READ) {
        mb->hdr.type = 11;
    } else {
        mb->hdr.type = 12;
    }

    mb->hdr.pri = priority;
    mb->hdr.retQueue = mq;
    mb->dramAddr = vAddr;
    mb->devAddr = devAddr;
    mb->size = nbytes;
#if defined(VERSION_EU) || defined(VERSION_SH)
    mb->piHandle = NULL;
#endif

    if (priority == OS_MESG_PRI_HIGH) {
        cmdQueue = osPiGetCmdQueue();
        result = osJamMesg(cmdQueue, mb, OS_MESG_NOBLOCK);
    } else {
        cmdQueue = osPiGetCmdQueue();
        result = osSendMesg(cmdQueue, mb, OS_MESG_NOBLOCK);
    }
    return result;
}
