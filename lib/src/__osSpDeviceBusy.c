#include "libultra_internal.h"
#include "hardware.h"

s32 __osSpDeviceBusy() {
    register u32 status = HW_REG(SP_STATUS_REG, u32);
    if (status & (SPSTATUS_IO_FULL | SPSTATUS_DMA_FULL | SPSTATUS_DMA_BUSY)) {
        return 1;
    }
    return 0;
}
