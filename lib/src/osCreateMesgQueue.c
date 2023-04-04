#include "libultra_internal.h"

void osCreateMesgQueue(OSMesgQueue *mq, OSMesg *msgBuf, s32 count) {
    mq->mtqueue = (OSThread *) &D_80334890; //?
    mq->fullqueue = (OSThread *) &D_80334890;
    mq->validCount = 0;
    mq->first = 0;
    mq->msgCount = count;
    mq->msg = msgBuf;
    return;
}
