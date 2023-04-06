#ifndef NEW_FUNC_H
#define NEW_FUNC_H
#include "libultra_internal.h"
#include "hardware.h"

#define WAIT_ON_IOBUSY(var)                                                                            \
    var = HW_REG(PI_STATUS_REG, u32);                                                                  \
    while (var & PI_STATUS_IOBUSY)                                                                     \
        var = HW_REG(PI_STATUS_REG, u32);

extern u32 EU_D_80302090;

extern OSPiHandle *__osDiskHandle; //possibly __osPiTable

extern volatile u32 D_8030208C;
s32 osEPiRawStartDma(OSPiHandle *arg0, s32 dir, u32 cart_addr, void *dram_addr, u32 size);
void func_802F4B08();
void func_802F4A20();
void func_802F7140(u32);
void func_802F71A0(OSPiHandle*, u32, u32); //osEPi something
void func_802F71F0();
#endif
