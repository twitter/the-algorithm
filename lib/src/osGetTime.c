#include "libultra_internal.h"

extern OSTime _osCurrentTime;
extern u32 D_80365DA8;

OSTime osGetTime() {
    u32 sp34;
    u32 sp30;
    OSTime sp28;
    register u32 int_disabled;
    int_disabled = __osDisableInt();
    sp34 = osGetCount();
    sp30 = sp34 - D_80365DA8;
    sp28 = _osCurrentTime;
    __osRestoreInt(int_disabled);
    return sp28 + sp30;
}
