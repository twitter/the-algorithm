#include "libultra_internal.h"
#include "controller.h"

s32 func_8030A5C0(OSMesgQueue *arg0, s32 arg1) { // TODO: This is almost certainly __osPfsGetStatus.
    s32 sp34 = 0;
    OSMesg sp30;
    u8 sp2f;
    OSContStatus sp1c[4];
    __osPfsRequestData(0);
    sp34 = __osSiRawStartDma(1, &__osPfsPifRam);
    osRecvMesg(arg0, &sp30, 1);
    sp34 = __osSiRawStartDma(0, &__osPfsPifRam);
    osRecvMesg(arg0, &sp30, 1);
    __osPfsGetInitData(&sp2f, sp1c);
    if (sp1c[arg1].status & CONT_CARD_ON && sp1c[arg1].status & CONT_CARD_PULL) {
        return 2;
    }
    if (sp1c[arg1].errnum || !(sp1c[arg1].status & CONT_CARD_ON)) {
        return 1;
    }
    if (sp1c[arg1].status & CONT_ADDR_CRC_ER) {
        return 4;
    }
    return sp34;
    
}
