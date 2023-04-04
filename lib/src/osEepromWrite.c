#include "libultra_internal.h"
#include "osContInternal.h"

#ifndef AVOID_UB
ALIGNED8 u32 D_80365E00[15];
u32 D_80365E3C;
#else
// Certain code accesses the 16th element (D_80365E3C) in this array, making a seperate
// definition UB when gcc rearranges those.
ALIGNED8 u32 D_80365E00[16];
#endif
extern u8 _osLastSentSiCmd;

typedef struct {
    u16 unk00;
    u8 unk02;
    u8 unk03;
} unkStruct;
typedef struct {
    u8 unk00;
    u8 unk01;
    u8 unk02;
    u8 unk03;
    u8 unk04;
    u8 unk05;
    u8 unk06;
    u8 unk07;
} unkStruct3;

typedef struct {
    u8 unk00;
    u8 unk01;
    u8 unk02;
    u8 unk03;
    unkStruct3 unk04;
} unkStruct2;

s32 __osEepStatus(OSMesgQueue *, unkStruct *);
s32 __osPackEepWriteData(u8, u8 *);

s32 osEepromWrite(OSMesgQueue *mq, u8 address, u8 *buffer) {
    s32 sp34;
    s32 sp30;
    u8 *sp2c;
    unkStruct2 sp20;
    unkStruct sp1c;
    sp34 = 0;
    sp2c = (u8 *) &D_80365E00;

    if (address > 0x40) {
        return -1;
    }

    __osSiGetAccess();
    sp34 = __osEepStatus(mq, &sp1c);

    if (sp34 != 0 || sp1c.unk00 != 0x8000) {
        return 8;
    }

    while (sp1c.unk02 & 0x80) {
        __osEepStatus(mq, &sp1c);
    }

    __osPackEepWriteData(address, buffer);

    sp34 = __osSiRawStartDma(OS_WRITE, &D_80365E00);
    osRecvMesg(mq, NULL, OS_MESG_BLOCK);

    for (sp30 = 0; sp30 < 0x10; sp30++) {
        (D_80365E00)[sp30] = 255;
    }

    D_80365E3C = 0;
    sp34 = __osSiRawStartDma(OS_READ, D_80365E00);
    _osLastSentSiCmd = 5;
    osRecvMesg(mq, NULL, OS_MESG_BLOCK);

    for (sp30 = 0; sp30 < 4; sp30++) {
        sp2c++;
    }

    sp20 = *(unkStruct2 *) sp2c;
    sp34 = (sp20.unk01 & 0xc0) >> 4;
    __osSiRelAccess();
    return sp34;
}

s32 __osPackEepWriteData(u8 address, u8 *buffer) {
    u8 *sp14;
    unkStruct2 sp8;
    s32 sp4;
    sp14 = (u8 *) &D_80365E00;
    for (sp4 = 0; sp4 < 0x10; sp4++) {
        D_80365E00[sp4] = 255;
    }
    D_80365E3C = 1;
    sp8.unk00 = 10;
    sp8.unk01 = 1;
    sp8.unk02 = 5;
    sp8.unk03 = address;
    for (sp4 = 0; sp4 < 8; sp4++) {
        ((u8 *) &sp8.unk04)[sp4] = *buffer++;
    }
    for (sp4 = 0; sp4 < 4; sp4++) {
        *sp14++ = 0;
    }
    *(unkStruct2 *) sp14 = sp8;
    sp14 += 0xc;
    *sp14 = 254;
#ifdef AVOID_UB
    return 0;
#endif
}

s32 __osEepStatus(OSMesgQueue *a0, unkStruct *a1) {
    u32 sp2c = 0;
    s32 sp28;
    u8 *sp24 = (u8 *) D_80365E00;
    unkStruct3 sp1c;

    for (sp28 = 0; sp28 < 0x10; sp28++) {
        D_80365E00[sp28] = 0;
    }

    D_80365E3C = 1;
    sp24 = (u8 *) D_80365E00;
    for (sp28 = 0; sp28 < 4; sp28++) {
        *sp24++ = 0;
    }

    sp1c.unk00 = 255;
    sp1c.unk01 = 1;
    sp1c.unk02 = 3;
    sp1c.unk03 = 0;
    sp1c.unk04 = 255;
    sp1c.unk05 = 255;
    sp1c.unk06 = 255;
    sp1c.unk07 = 255;
    *(unkStruct3 *) sp24 = sp1c;

    sp24 += 8;
    sp24[0] = 254;

    sp2c = __osSiRawStartDma(OS_WRITE, D_80365E00);
    osRecvMesg(a0, NULL, OS_MESG_BLOCK);

    _osLastSentSiCmd = 5;

    sp2c = __osSiRawStartDma(OS_READ, D_80365E00);
    osRecvMesg(a0, NULL, OS_MESG_BLOCK);

    if (sp2c != 0) {
        return sp2c;
    }

    sp24 = (u8 *) D_80365E00;
    for (sp28 = 0; sp28 < 4; sp28++) {
        *sp24++ = 0;
    }

    sp1c = *(unkStruct3 *) sp24;
    a1->unk03 = (sp1c.unk02 & 0xc0) >> 4;
    a1->unk00 = (sp1c.unk05 << 8) | sp1c.unk04;
    a1->unk02 = sp1c.unk06;
    if (a1->unk03 != 0) {
        return a1->unk03;
    }
    return 0;
}
