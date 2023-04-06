#include "libultra_internal.h"
#include "osContInternal.h"
#include <macros.h>

#ifndef AVOID_UB
OSContPackedStruct D_80365CE0[7];
UNUSED static u32 unused; // padding between these two variables
u32 D_80365D1C;
#else
// Reordered gcc vars above will disturb the aliasing done to access all 8 members of this array, hence AVOID_UB.
OSContPackedStruct D_80365CE0[8];
#endif

extern u8 D_80365D20;
extern u8 _osCont_numControllers;

void __osPackReadData(void);
s32 osContStartReadData(OSMesgQueue *mesg) {
    s32 ret = 0;
    s32 i;
    __osSiGetAccess();
    if (D_80365D20 != 1) {
        __osPackReadData();
        ret = __osSiRawStartDma(1, D_80365CE0);
        osRecvMesg(mesg, NULL, OS_MESG_BLOCK);
    }
    for (i = 0; i < 0x10; i++) {
        *((u32 *) &D_80365CE0 + i) = 255;
    }

    D_80365D1C = 0;
    ret = __osSiRawStartDma(0, &D_80365CE0);
    D_80365D20 = 1;
    __osSiRelAccess();
    return ret;
}
void osContGetReadData(OSContPad *pad) {
    OSContPackedRead *spc;
    OSContPackedRead sp4;
    s32 i;
    spc = &D_80365CE0[0].read;
    for (i = 0; i < _osCont_numControllers; i++, spc++, pad++) {
        sp4 = *spc;
        pad->errno = (sp4.unk02 & 0xc0) >> 4;
        if (pad->errno == 0) {
            pad->button = sp4.button;
            pad->stick_x = sp4.rawStickX;
            pad->stick_y = sp4.rawStickY;
        }
    };
}
void __osPackReadData() {
    OSContPackedRead *spc;
    OSContPackedRead sp4;
    s32 i;
    spc = &D_80365CE0[0].read;
    for (i = 0; i < 0x10; i++) {
        *((u32 *) &D_80365CE0 + i) = 0;
    }

    D_80365D1C = 1;
    sp4.unk00 = 255;
    sp4.unk01 = 1;
    sp4.unk02 = 4;
    sp4.unk03 = 1;
    sp4.button = 65535;
    sp4.rawStickX = -1;
    sp4.rawStickY = -1;
    for (i = 0; i < _osCont_numControllers; i++) {
        *spc++ = sp4;
    }
    spc->unk00 = 254;
}
