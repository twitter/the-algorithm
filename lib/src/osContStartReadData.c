#include "libultra_internal.h"
#include "osContInternal.h"
#include <macros.h>

#ifndef AVOID_UB
ALIGNED8 OSContPackedStruct _osContCmdBuf[7];
UNUSED static u32 unused; // padding between these two variables
u32 _osContPifCtrl;
#else
// Reordered gcc vars above will disturb the aliasing done to access all 8 members of this array, hence AVOID_UB.
ALIGNED8 OSContPackedStruct _osContCmdBuf[8];
#endif

extern u8 _osLastSentSiCmd;
extern u8 _osContNumControllers;

void __osPackReadData(void);
s32 osContStartReadData(OSMesgQueue *mesg) {
    s32 ret = 0;
    s32 i;
    __osSiGetAccess();
    if (_osLastSentSiCmd != 1) {
        __osPackReadData();
        ret = __osSiRawStartDma(OS_WRITE, _osContCmdBuf);
        osRecvMesg(mesg, NULL, OS_MESG_BLOCK);
    }
    for (i = 0; i < 0x10; i++) {
        *((u32 *) &_osContCmdBuf + i) = 255;
    }

    _osContPifCtrl = 0;
    ret = __osSiRawStartDma(OS_READ, _osContCmdBuf);
    _osLastSentSiCmd = 1;
    __osSiRelAccess();
    return ret;
}
void osContGetReadData(OSContPad *pad) {
    OSContPackedRead *cmdBufPtr;
    OSContPackedRead response;
    s32 i;
    cmdBufPtr = &_osContCmdBuf[0].read;
    for (i = 0; i < _osContNumControllers; i++, cmdBufPtr++, pad++) {
        response = *cmdBufPtr;
        pad->errnum = (response.rxLen & 0xc0) >> 4;
        if (pad->errnum == 0) {
            pad->button = response.button;
            pad->stick_x = response.rawStickX;
            pad->stick_y = response.rawStickY;
        }
    };
}
void __osPackReadData() {
    OSContPackedRead *cmdBufPtr;
    OSContPackedRead request;
    s32 i;
    cmdBufPtr = &_osContCmdBuf[0].read;
    for (i = 0; i < 0x10; i++) {
        *((u32 *) &_osContCmdBuf + i) = 0;
    }

    _osContPifCtrl = 1;
    request.padOrEnd = 255;
    request.txLen = 1;
    request.rxLen = 4;
    request.command = 1;
    request.button = 65535;
    request.rawStickX = -1;
    request.rawStickY = -1;
    for (i = 0; i < _osContNumControllers; i++) {
        *cmdBufPtr++ = request;
    }
    cmdBufPtr->padOrEnd = 254;
}
