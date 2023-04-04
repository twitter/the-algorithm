#include "libultra_internal.h"
#include "hardware.h"

extern u32 osRomBase; // TODO: figure out why this is like this

s32 osPiRawStartDma(s32 dir, u32 cart_addr, void *dram_addr, size_t size) {
    register int status;
    status = HW_REG(PI_STATUS_REG, u32);
    while (status & (PI_STATUS_BUSY | PI_STATUS_IOBUSY | PI_STATUS_ERROR)) {
        status = HW_REG(PI_STATUS_REG, u32);
    }

    HW_REG(PI_DRAM_ADDR_REG, void *) = (void *) osVirtualToPhysical(dram_addr);

    HW_REG(PI_CART_ADDR_REG, void *) = (void *) (((uintptr_t) osRomBase | cart_addr) & 0x1fffffff);

    switch (dir) {
        case 0:
            HW_REG(PI_WR_LEN_REG, u32) = size - 1;
            break;
        case 1:
            HW_REG(PI_RD_LEN_REG, u32) = size - 1;
            break;
        default:
            return -1;
            break;
    }
    return 0;
}

#ifdef VERSION_EU
/*s32 osPiRawStartDma_2(s32 dir, u32 cart_addr, void *dram_addr, size_t size) {
    register int status;
    status = HW_REG(PI_STATUS_REG, u32);
    while (status & (PI_STATUS_BUSY | PI_STATUS_IOBUSY | PI_STATUS_ERROR)) {
        status = HW_REG(PI_STATUS_REG, u32);
    }

    HW_REG(PI_DRAM_ADDR_REG, void *) = (void *) osVirtualToPhysical(dram_addr);

    HW_REG(PI_CART_ADDR_REG, void *) = (void *) (((uintptr_t) osRomBase | cart_addr) & 0x1fffffff);

    switch (dir) {
        case 0:
            HW_REG(PI_WR_LEN_REG, u32) = size - 1;
            break;
        case 1:
            HW_REG(PI_RD_LEN_REG, u32) = size - 1;
            break;
        default:
            return -1;
            break;
    }
    return 0;
}*/
#endif
