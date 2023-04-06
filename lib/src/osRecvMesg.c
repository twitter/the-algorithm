#include "libultra_internal.h"

s32 osRecvMesg(OSMesgQueue *mq, OSMesg *msg, s32 flag) {
    register u32 int_disabled;
    register OSThread *thread;
    int_disabled = __osDisableInt();

    while (!mq->validCount) {
        if (!flag) {
            __osRestoreInt(int_disabled);
            return -1;
        }
        D_803348A0->state = OS_STATE_WAITING;
        __osEnqueueAndYield(&mq->mtqueue);
    }

    if (msg != NULL) {
        *msg = *(mq->first + mq->msg);
    }

    mq->first = (mq->first + 1) % mq->msgCount;
    mq->validCount--;

    if (mq->fullqueue->next != NULL) {
        thread = __osPopThread(&mq->fullqueue);
        osStartThread(thread);
    }

    __osRestoreInt(int_disabled);
    return 0;
}
