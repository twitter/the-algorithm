#include "libultra_internal.h"

extern OSViContext *__osViNext;

void osViSetEvent(OSMesgQueue *mq, OSMesg msg, u32 retraceCount) {
    register u32 int_disabled = __osDisableInt();
    (__osViNext)->mq = mq;
    (__osViNext)->msg = msg;
    (__osViNext)->retraceCount = retraceCount;
    __osRestoreInt(int_disabled);
}
