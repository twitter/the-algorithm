#include "libultra_internal.h"

OSYieldResult osSpTaskYielded(OSTask *task) {
    s32 status;
    u32 int_disabledult;
    status = __osSpGetStatus();
    if (status & SPSTATUS_SIGNAL1_SET) {
        int_disabledult = 1;
    } else {
        int_disabledult = 0;
    }
    if (status & SPSTATUS_SIGNAL0_SET) {
        task->t.flags |= int_disabledult;
        task->t.flags &= ~(M_TASK_FLAG1);
    }
    return int_disabledult;
}
