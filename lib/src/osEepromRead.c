#include "libultra_internal.h"

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
s32 __osPackEepReadData(u8);

s32 osEepromRead(OSMesgQueue *mq, u8 address, u8 *buffer) {
    s32 sp34;
    s32 sp30;
    u8 *sp2c;
    unkStruct sp28;
    unkStruct2 sp20;
    sp34 = 0;
    sp30 = 0;
    sp2c = (u8 *) &D_80365E00;
    if (address > 0x40) {
        return -1;
    }
    __osSiGetAccess();
    sp34 = __osEepStatus(mq, &sp28);
    if (sp34 != 0 || sp28.unk00 != 0x8000) {

        return 8;
    }
    while (sp28.unk02 & 0x80) {
        __osEepStatus(mq, &sp28);
    }
    __osPackEepReadData(address);
    sp34 = __osSiRawStartDma(OS_WRITE, &D_80365E00);
    osRecvMesg(mq, NULL, OS_MESG_BLOCK);
    for (sp30 = 0; sp30 < 0x10; sp30++) {
        (D_80365E00)[sp30] = 255;
    }
    D_80365E3C = 0;
    sp34 = __osSiRawStartDma(OS_READ, D_80365E00);
    _osLastSentSiCmd = 4;
    osRecvMesg(mq, NULL, OS_MESG_BLOCK);
    for (sp30 = 0; sp30 < 4; sp30++) {
        sp2c++;
    }
    sp20 = *(unkStruct2 *) sp2c;
    sp34 = (sp20.unk01 & 0xc0) >> 4;
    if (sp34 == 0) {
        for (sp30 = 0; sp30 < 8; sp30++) {
            *buffer++ = ((u8 *) &sp20.unk04)[sp30];
        }
    }
    __osSiRelAccess();
    return sp34;
}

s32 __osPackEepReadData(u8 address) {
    u8 *sp14;
    unkStruct2 sp8;
    s32 sp4;
    sp14 = (u8 *) &D_80365E00;
    for (sp4 = 0; sp4 < 0x10; sp4++) {
        D_80365E00[sp4] = 255;
    }
    D_80365E3C = 1;
    sp8.unk00 = 2;
    sp8.unk01 = 8;
    sp8.unk02 = 4;
    sp8.unk03 = address;
    for (sp4 = 0; sp4 < 8; sp4++) {
        ((u8 *) &sp8.unk04)[sp4] = 0;
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
