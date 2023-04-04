#include "libultra_internal.h"

#define SIAccessQueueSize 2

OSMesg osSiMesgBuff[SIAccessQueueSize];
OSMesgQueue gOsSiMessageQueue;
u32 gOsSiAccessQueueCreated = 0;

void __osSiCreateAccessQueue() {
    gOsSiAccessQueueCreated = 1;
    osCreateMesgQueue(&gOsSiMessageQueue, &osSiMesgBuff[0], SIAccessQueueSize - 1);
    osSendMesg(&gOsSiMessageQueue, NULL, OS_MESG_NOBLOCK);
}

void __osSiGetAccess(void) {
    OSMesg sp1c;
    if (!gOsSiAccessQueueCreated) {
        __osSiCreateAccessQueue();
    }
    osRecvMesg(&gOsSiMessageQueue, &sp1c, OS_MESG_BLOCK);
}

void __osSiRelAccess(void) {
    osSendMesg(&gOsSiMessageQueue, NULL, OS_MESG_NOBLOCK);
}
