#include "libultra_internal.h"
#include "hardware.h"

// this file must include some globally referenced data because it is not called anywhere
// data, comes shortly before _Ldtob I think, before crash_screen

extern OSPiHandle *__osPiTable;
// bss
OSPiHandle LeoDiskHandle;
OSPiHandle *__osDiskHandle;

OSPiHandle *osLeoDiskInit(void) {
    s32 sp1c;
    LeoDiskHandle.type = 2;
    LeoDiskHandle.baseAddress = (0xa0000000 | 0x05000000);
    LeoDiskHandle.latency = 3;
    LeoDiskHandle.pulse = 6;
    LeoDiskHandle.pageSize = 6;
    LeoDiskHandle.relDuration = 2;
#ifdef VERSION_SH
    LeoDiskHandle.domain = 1;
#endif
    HW_REG(PI_BSD_DOM2_LAT_REG, u32) = LeoDiskHandle.latency;
    HW_REG(PI_BSD_DOM2_PWD_REG, u32) = LeoDiskHandle.pulse;
    HW_REG(PI_BSD_DOM2_PGS_REG, u32) = LeoDiskHandle.pageSize;
    HW_REG(PI_BSD_DOM2_RLS_REG, u32) = LeoDiskHandle.relDuration;
    bzero(&LeoDiskHandle.transferInfo, sizeof(__OSTranxInfo));
    sp1c = __osDisableInt();
    LeoDiskHandle.next = __osPiTable;
    __osPiTable = &LeoDiskHandle;
    __osDiskHandle = &LeoDiskHandle;
    __osRestoreInt(sp1c);
    return &LeoDiskHandle;
}
