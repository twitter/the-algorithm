#include "libultra_internal.h"
extern OSViContext *D_80334914;
void osViSetSpecialFeatures(u32 func) {
    register u32 int_disabled = __osDisableInt();
    if (func & OS_VI_GAMMA_ON) {
        D_80334914->features |= OS_VI_GAMMA;
    }
    if (func & OS_VI_GAMMA_OFF) {
        D_80334914->features &= ~OS_VI_GAMMA;
    }
    if (func & OS_VI_GAMMA_DITHER_ON) {
        D_80334914->features |= OS_VI_GAMMA_DITHER;
    }
    if (func & OS_VI_GAMMA_DITHER_OFF) {

        D_80334914->features &= ~OS_VI_GAMMA_DITHER;
    }
    if (func & OS_VI_DIVOT_ON) {

        D_80334914->features |= OS_VI_DIVOT;
    }
    if (func & OS_VI_DIVOT_OFF) {

        D_80334914->features &= ~OS_VI_DIVOT;
    }
    if (func & OS_VI_DITHER_FILTER_ON) {
        D_80334914->features |= OS_VI_DITHER_FILTER;
        D_80334914->features &= ~(OS_VI_UNK200 | OS_VI_UNK100);
    }
    if (func & OS_VI_DITHER_FILTER_OFF) {
        D_80334914->features &= ~OS_VI_DITHER_FILTER;
        D_80334914->features |= D_80334914->unk08->comRegs.ctrl & (OS_VI_UNK200 | OS_VI_UNK100);
    }
    D_80334914->unk00 |= 8;
    __osRestoreInt(int_disabled);
}
