#include "libultra_internal.h"
#include "hardware.h"

s32 __osSiRawWriteIo(void *a0, u32 a1) {
    if (__osSiDeviceBusy()) {
        return -1;
    }
    HW_REG((uintptr_t) a0, u32) = a1;
    return 0;
}
