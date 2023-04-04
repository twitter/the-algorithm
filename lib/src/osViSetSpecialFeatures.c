#include "libultra_internal.h"

extern OSViContext *__osViNext;

void osViSetSpecialFeatures(u32 func) {
    register u32 int_disabled = __osDisableInt();
    if (func & OS_VI_GAMMA_ON) {
        __osViNext->features |= OS_VI_GAMMA;
    }
    if (func & OS_VI_GAMMA_OFF) {
        __osViNext->features &= ~OS_VI_GAMMA;
    }
    if (func & OS_VI_GAMMA_DITHER_ON) {
        __osViNext->features |= OS_VI_GAMMA_DITHER;
    }
    if (func & OS_VI_GAMMA_DITHER_OFF) {
        __osViNext->features &= ~OS_VI_GAMMA_DITHER;
    }
    if (func & OS_VI_DIVOT_ON) {
        __osViNext->features |= OS_VI_DIVOT;
    }
    if (func & OS_VI_DIVOT_OFF) {
        __osViNext->features &= ~OS_VI_DIVOT;
    }
    if (func & OS_VI_DITHER_FILTER_ON) {
        __osViNext->features |= OS_VI_DITHER_FILTER;
        __osViNext->features &= ~(OS_VI_UNK200 | OS_VI_UNK100);
    }
    if (func & OS_VI_DITHER_FILTER_OFF) {
        __osViNext->features &= ~OS_VI_DITHER_FILTER;
        __osViNext->features |= __osViNext->modep->comRegs.ctrl & (OS_VI_UNK200 | OS_VI_UNK100);
    }
    __osViNext->unk00 |= 8;
    __osRestoreInt(int_disabled);
}
