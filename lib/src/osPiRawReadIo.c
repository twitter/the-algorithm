#include "libultra_internal.h"
#include "hardware.h"

extern u32 osRomBase;

s32 osPiRawReadIo(u32 a0, u32 *a1) {
    register int status;
    status = HW_REG(PI_STATUS_REG, u32);
    while (status & (PI_STATUS_BUSY | PI_STATUS_IOBUSY | PI_STATUS_ERROR)) {
        status = HW_REG(PI_STATUS_REG, u32);
    }
    *a1 = HW_REG(osRomBase | a0, u32);
    return 0;
}
