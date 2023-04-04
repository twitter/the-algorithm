#include "libultra_internal.h"

typedef struct OSEventMessageStruct_0_s {
    OSMesgQueue *queue;
    OSMesg msg;
} OSEventMessageStruct_0;

OSEventMessageStruct_0 __osEventStateTab[16];

void osSetEventMesg(OSEvent e, OSMesgQueue *mq, OSMesg msg) {
    register u32 int_disabled;
    OSEventMessageStruct_0 *msgs;
    int_disabled = __osDisableInt();
    msgs = __osEventStateTab + e;
    msgs->queue = mq;
    msgs->msg = msg;
    __osRestoreInt(int_disabled);
}
