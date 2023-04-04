#include "libultra_internal.h"

void osStartThread(OSThread *thread) {
    register u32 int_disabled;
    register uintptr_t state;
    int_disabled = __osDisableInt();
    state = thread->state;

    if (state != OS_STATE_STOPPED) {
        if (state == OS_STATE_WAITING) {
            do {
            } while (0);
            thread->state = OS_STATE_RUNNABLE;
            __osEnqueueThread(&D_80334898, thread);
        }
    } else {
        if (thread->queue == NULL || thread->queue == &D_80334898) {
            thread->state = OS_STATE_RUNNABLE;

            __osEnqueueThread(&D_80334898, thread);
        } else {
            thread->state = OS_STATE_WAITING;
            __osEnqueueThread(thread->queue, thread);
            state = (uintptr_t) __osPopThread(thread->queue);
            __osEnqueueThread(&D_80334898, (OSThread *) state);
        }
    }
    if (D_803348A0 == NULL) {
        __osDispatchThread();
    } else {
        if (D_803348A0->priority < D_80334898->priority) {
            D_803348A0->state = OS_STATE_RUNNABLE;
            __osEnqueueAndYield(&D_80334898);
        }
    }
    __osRestoreInt(int_disabled);
}
