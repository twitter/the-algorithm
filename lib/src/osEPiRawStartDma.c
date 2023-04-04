#include "libultra_internal.h"
#include "hardware.h"
#include "new_func.h"
#include "PR/R4300.h"
// TODO: This define is from piint.h, but including that causes problems...
#define UPDATE_REG(reg, var)                                                                           \
    if (cHandle->var != pihandle->var) \
        IO_WRITE(reg, pihandle->var);
// TODO: This define is from os.h, but including that causes problems...
#define PI_DOMAIN1 0
// TODO: These defines are from PR/rcp.h, but including that causes problems...
#define IO_WRITE(addr, data) (*(vu32 *) PHYS_TO_K1(addr) = (u32)(data))

#ifdef VERSION_SH
extern OSPiHandle *__osCurrentHandle[2];
#endif

s32 osEPiRawStartDma(OSPiHandle *pihandle, s32 dir, u32 cart_addr, void *dram_addr, u32 size) {
#ifdef VERSION_SH
    u32 status;
    u32 domain;
#else
    register int status;
#endif

    status = HW_REG(PI_STATUS_REG, u32);
    while (status & PI_STATUS_ERROR)
        status = HW_REG(PI_STATUS_REG, u32);
#ifdef VERSION_SH // TODO: This is the EPI_SYNC macro
    domain = pihandle->domain;
    if (__osCurrentHandle[domain] != pihandle) {
        OSPiHandle *cHandle = __osCurrentHandle[domain];
        if (domain == PI_DOMAIN1) {
            UPDATE_REG(PI_BSD_DOM1_LAT_REG, latency);
            UPDATE_REG(PI_BSD_DOM1_PGS_REG, pageSize);
            UPDATE_REG(PI_BSD_DOM1_RLS_REG, relDuration);
            UPDATE_REG(PI_BSD_DOM1_PWD_REG, pulse);
        } else {
            UPDATE_REG(PI_BSD_DOM2_LAT_REG, latency);
            UPDATE_REG(PI_BSD_DOM2_PGS_REG, pageSize);
            UPDATE_REG(PI_BSD_DOM2_RLS_REG, relDuration);
            UPDATE_REG(PI_BSD_DOM2_PWD_REG, pulse);
        }
        __osCurrentHandle[domain] = pihandle;
    }
#endif
    HW_REG(PI_DRAM_ADDR_REG, void *) = (void *) osVirtualToPhysical(dram_addr);
    HW_REG(PI_CART_ADDR_REG, void *) = (void *) (((uintptr_t) pihandle->baseAddress | cart_addr) & 0x1fffffff);

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
