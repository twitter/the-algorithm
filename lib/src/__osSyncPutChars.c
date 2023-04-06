#include "libultra_internal.h"

typedef struct {
    u8 unk00 : 2;
    u8 pad : 4;
    u8 unk01 : 2;
    u8 unk2[3];
} unkStruct;

u32 D_80334A40 = 0;
u32 D_80334A44 = 1;

void __osSyncPutChars(s32 a0, s32 a1, u8 *a2) {
    unkStruct sp24;
    s32 sp20;
    u32 sp1c;
    sp24.unk00 = a0;
    sp24.unk01 = a1;

    for (sp20 = 0; sp20 < a1; sp20++) {
        sp24.unk2[sp20] = a2[sp20];
    }

    while (!__osAtomicDec(&D_80334A44)) {
        ;
    }

    sp1c = __osDisableInt();

    *(u32 *) 0xC0000000 = *(u32 *) &sp24;
    while (!(__osGetCause() & 0x2000)) {
        ;
    }
    *(u32 *) 0xC000000C = 0;
    D_80334A44++;

    __osRestoreInt(sp1c);
}
