#include "libultra_internal.h"
extern OSViContext *D_80334914;
void osViSetEvent(OSMesgQueue *mq, OSMesg msg, u32 retraceCount) {

    register u32 int_disabled = __osDisableInt();
    (D_80334914)->mq = mq;
    (D_80334914)->msg = msg;
    (D_80334914)->retraceCount = retraceCount;
    __osRestoreInt(int_disabled);
}
