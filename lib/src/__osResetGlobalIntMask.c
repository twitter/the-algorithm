#include "libultra_internal.h"
#include "new_func.h"

void __osResetGlobalIntMask(u32 mask) {
    register u32 prev;
    prev = __osDisableInt();
    __OSGlobalIntMask &= ~(-0x402 & mask);
    __osRestoreInt(prev);
}
