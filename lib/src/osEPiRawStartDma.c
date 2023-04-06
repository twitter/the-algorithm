#include "libultra_internal.h"
#include "hardware.h"
#include "new_func.h"

s32 osEPiRawStartDma(OSPiHandle *arg0, s32 dir, u32 cart_addr, void *dram_addr, u32 size) {
    register int status;
    status = HW_REG(PI_STATUS_REG, u32);
    while (status & PI_STATUS_ERROR)
        status = HW_REG(PI_STATUS_REG, u32);
    HW_REG(PI_DRAM_ADDR_REG, void *) = (void *) osVirtualToPhysical(dram_addr);
    HW_REG(PI_CART_ADDR_REG, void *) = (void *) (((uintptr_t) arg0->baseAddress | cart_addr) & 0x1fffffff);

    switch (dir) {
        case OS_READ:
            HW_REG(PI_WR_LEN_REG, u32) = size - 1;
            break;
        case OS_WRITE:
            HW_REG(PI_RD_LEN_REG, u32) = size - 1;
            break;
        default:
            return -1;
    }
    return 0;
}
