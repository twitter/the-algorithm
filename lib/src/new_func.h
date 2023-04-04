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

extern volatile u32 __OSGlobalIntMask;
s32 osEPiRawStartDma(OSPiHandle *arg0, s32 dir, u32 cart_addr, void *dram_addr, u32 size);
void func_802F4B08(void);
void func_802F4A20(void);
void __osResetGlobalIntMask(u32 mask);
void __osEPiRawWriteIo(OSPiHandle*, u32, u32);
void func_802F71F0(void);
#ifdef VERSION_SH
void __osSetGlobalIntMask(s32 arg0);
s32 __osEPiRawReadIo(OSPiHandle *arg0, u32 devAddr, u32 *arg2);
#endif

#endif
