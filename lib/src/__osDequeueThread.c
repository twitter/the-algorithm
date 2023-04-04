#include "libultra_internal.h"

// these don't feel like they belong here
// but it makes the most logical since there was printf data before
#ifndef AVOID_UB
OSThread *D_80334890 = NULL;
u32 D_80334894 = -1;
OSThread *D_80334898 = (OSThread *) &D_80334890;
OSThread *D_8033489C = (OSThread *) &D_80334890;
OSThread *D_803348A0 = NULL;
u32 D_803348A4 = 0; // UNKNOWN
#else
OSThread_ListHead D_80334890_fix = {NULL, -1, (OSThread *) &D_80334890_fix, (OSThread *) &D_80334890_fix, NULL, 0};
#endif

void __osDequeueThread(OSThread **queue, OSThread *thread) {
    register OSThread **a2;
    register OSThread *a3;
    a2 = queue;
    a3 = *a2;
    while (a3 != NULL) {
        if (a3 == thread) {
            *a2 = thread->next;
            return;
        }
        a2 = &a3->next;
        a3 = *a2;
    }
}
