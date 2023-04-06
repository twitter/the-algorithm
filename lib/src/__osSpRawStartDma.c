#include "libultra_internal.h"
#include "hardware.h"

s32 __osSpRawStartDma(u32 dir, void *sp_ptr, void *dram_ptr, size_t size) {
    if (__osSpDeviceBusy()) {
        return -1;
    }
    HW_REG(SP_MEM_ADDR_REG, void *) = sp_ptr;
    HW_REG(SP_DRAM_ADDR_REG, void *) = (void *) osVirtualToPhysical(dram_ptr);
    if (dir == 0) {
        HW_REG(SP_WR_LEN_REG, u32) = size - 1;
    } else {
        HW_REG(SP_RD_LEN_REG, u32) = size - 1;
    }
    return 0;
}
