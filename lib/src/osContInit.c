#include "libultra_internal.h"
#include "osContInternal.h"

void __osPackRequestData(u8);
void __osContGetInitData(u8 *, OSContStatus *);

u32 D_80334810 = 0; // probably initialized

extern u64 osClockRate;

// these probably belong in EEPROMlongread or something
u8 D_80365D20;
u8 _osCont_numControllers;
OSTimer D_80365D28; // some kind of buffer, or maybe an as yet unknown struct
OSMesgQueue _osContMesgQueue;
OSMesg _osContMesgBuff[4];
s32 osContInit(OSMesgQueue *mq, u8 *a1, OSContStatus *status) {
    OSMesg mesg;
    u32 sp78 = 0;
    OSTime currentTime;
    OSTimer sp50;
    OSMesgQueue sp38;

    if (D_80334810) {
        return 0;
    }
    D_80334810 = 1;
    currentTime = osGetTime();
    if (500000 * osClockRate / 1000000 > currentTime) {
        osCreateMesgQueue(&sp38, &mesg, 1);
        osSetTimer(&sp50, 500000 * osClockRate / 1000000 - currentTime, 0, &sp38, &mesg);
        osRecvMesg(&sp38, &mesg, OS_MESG_BLOCK);
    }
    _osCont_numControllers = 4; // TODO: figure out what it means
#ifdef VERSION_EU
    __osPackRequestData(0);
#else
    __osPackRequestData(255);
#endif
    sp78 = __osSiRawStartDma(1, D_80365CE0);
    osRecvMesg(mq, &mesg, OS_MESG_BLOCK);
    sp78 = __osSiRawStartDma(0, D_80365CE0);
    osRecvMesg(mq, &mesg, OS_MESG_BLOCK);
    __osContGetInitData(a1, status);
#ifdef VERSION_EU
    D_80365D20 = 0;
#else
    D_80365D20 = 255;
#endif
    __osSiCreateAccessQueue();
    osCreateMesgQueue(&_osContMesgQueue, _osContMesgBuff, 1);
    return sp78;
}
void __osContGetInitData(u8 *a0, OSContStatus *status) {
    OSContPackedRequest *sp14;
    OSContPackedRequest spc;
    s32 i;
    u8 sp7;

    sp7 = 0;
    sp14 = &(D_80365CE0[0].request);
    for (i = 0; i < _osCont_numControllers; i++, sp14++, status++) {
        spc = *(OSContPackedRequest *) sp14;
        status->errno = (spc.unk02 & 0xc0) >> 4;
        if (status->errno == 0) {
            status->type = spc.unk05 << 8 | spc.unk04;
            status->status = spc.unk06;

            sp7 |= 1 << i;
        }
    }
    *a0 = sp7;
}
void __osPackRequestData(u8 a0) {
    OSContPackedRequest *spc;
    OSContPackedRequest sp4;
    s32 i;

    // some kind of weird zeroing code
    for (i = 0; i < 0x10; i++) {
        *((u32 *) &D_80365CE0 + i) = 0;
    }

    D_80365D1C = 1;
    spc = &D_80365CE0[0].request;
    sp4.unk00 = 255;
    sp4.unk01 = 1;
    sp4.unk02 = 3;
    sp4.unk03 = a0;
    sp4.unk04 = 255;
    sp4.unk05 = 255;
    sp4.unk06 = 255;
    sp4.unk07 = 255;

    for (i = 0; i < _osCont_numControllers; i++) {
        *spc++ = sp4;
    }
    spc->unk00 = 254;
}
