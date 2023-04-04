#include "libultra_internal.h"
#include "hardware.h"
#include "new_func.h"

void __osSetGlobalIntMask(s32 arg0) {
    register u32 prev = __osDisableInt();
    __OSGlobalIntMask |= arg0;
    __osRestoreInt(prev);
}
