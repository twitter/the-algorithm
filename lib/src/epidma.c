#ifdef VERSION_SH

#include "PR/os_internal.h"
#include "piint.h"

s32 osEPiStartDma(OSPiHandle *pihandle, OSIoMesg *mb, s32 direction) {
    register s32 ret;

    if (!__osPiDevMgr.active) {
        return -1;
    }
    mb->piHandle = pihandle;
    if (direction == OS_READ) {
        mb->hdr.type = OS_MESG_TYPE_EDMAREAD;
    } else {
        mb->hdr.type = OS_MESG_TYPE_EDMAWRITE;
    }
    if (mb->hdr.pri == OS_MESG_PRI_HIGH) {
        ret = osJamMesg(osPiGetCmdQueue(), mb, OS_MESG_NOBLOCK);
    } else {
        ret = osSendMesg(osPiGetCmdQueue(), mb, OS_MESG_NOBLOCK);
    }
    return ret;
}

#endif
