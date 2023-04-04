#include "libultra_internal.h"
#include "hardware.h"

s32 __osSiRawReadIo(void *a0, u32 *a1) {
    if (__osSiDeviceBusy()) {
        return -1;
    }
    *a1 = HW_REG((uintptr_t) a0, u32);
    return 0;
}
