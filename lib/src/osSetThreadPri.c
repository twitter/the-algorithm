#include "libultra_internal.h"

void osSetThreadPri(OSThread *thread, OSPri pri) {
    register u32 int_disabled = __osDisableInt();
    if (thread == NULL) {
        thread = D_803348A0;
    }

    if (thread->priority != pri) {
        thread->priority = pri;
        if (thread != D_803348A0) {
            if (thread->state != OS_STATE_STOPPED) {
                __osDequeueThread(thread->queue, thread);
                __osEnqueueThread(thread->queue, thread);
            }
        }
        if (D_803348A0->priority < D_80334898->priority) {
            D_803348A0->state = OS_STATE_RUNNABLE;
            __osEnqueueAndYield(&D_80334898);
        }
    }

    __osRestoreInt(int_disabled);
}
