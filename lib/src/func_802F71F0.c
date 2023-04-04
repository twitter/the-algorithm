#include "libultra_internal.h"

void func_802F71F0(void) {
    register u32 s0 = __osDisableInt();
    D_803348A0->state = OS_STATE_RUNNABLE;
    __osEnqueueAndYield(&D_80334898);
    __osRestoreInt(s0);
}
