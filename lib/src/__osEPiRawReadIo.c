#include "libultra_internal.h"
#include "hardware.h"

s32 __osEPiRawReadIo(OSPiHandle *arg0, u32 devAddr, u32 *data) {
    register s32 stat;
    while (stat = HW_REG(PI_STATUS_REG, s32), stat & (PI_STATUS_BUSY | PI_STATUS_IOBUSY | PI_STATUS_ERROR)) {
        ;
    }
    *data = HW_REG(arg0->baseAddress | devAddr, s32);
    return 0;
}
