#include "libultra_internal.h"

s32 osJamMesg(OSMesgQueue *mq, OSMesg msg, s32 flag) {
    register s32 int_disabled;
    int_disabled = __osDisableInt();
    while (mq->validCount >= mq->msgCount) {
        if (flag == OS_MESG_BLOCK) {
            D_803348A0->state = OS_STATE_WAITING;
            __osEnqueueAndYield(&mq->fullqueue);
        } else {
            __osRestoreInt(int_disabled);
            return -1;
        }
    }

    mq->first = (mq->first + mq->msgCount - 1) % mq->msgCount;
    mq->msg[mq->first] = msg;
    mq->validCount++;
    if (mq->mtqueue->next != NULL) {
        osStartThread(__osPopThread(&mq->mtqueue));
    }
    __osRestoreInt(int_disabled);
    return 0;
}
