#include "libultra_internal.h"

// TODO: merge with osEepromWrite
typedef struct {
    u16 unk00;
    u8 unk02;
    u8 unk03;
} unkStruct;

s32 __osEepStatus(OSMesgQueue *, unkStruct *);
s32 osEepromProbe(OSMesgQueue *mq) {
    s32 status = 0;
    unkStruct sp18;

    __osSiGetAccess();
    status = __osEepStatus(mq, &sp18);
    if (status == 0 && (sp18.unk00 & 0x8000) != 0) {
        status = 1;
    } else {
        status = 0;
    }
    __osSiRelAccess();
    return status;
}
