#include "libultra_internal.h"
#include "hardware.h"

s32 __osSpSetPc(void *pc) {
    register u32 status = HW_REG(SP_STATUS_REG, u32);
    if (!(status & SPSTATUS_HALT)) {
        return -1;
    } else {
        HW_REG(SP_PC_REG, void *) = pc;
        return 0;
    }
}
