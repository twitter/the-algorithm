#include "libultra_internal.h"

#define PIAccessQueueSize 2

OSMesg osPiMesgBuff[PIAccessQueueSize];
OSMesgQueue gOsPiMessageQueue;
u32 gOsPiAccessQueueCreated = 0;

void __osPiCreateAccessQueue() {
    gOsPiAccessQueueCreated = 1;
    osCreateMesgQueue(&gOsPiMessageQueue, &osPiMesgBuff[0], PIAccessQueueSize - 1);
    osSendMesg(&gOsPiMessageQueue, NULL, OS_MESG_NOBLOCK);
}

void __osPiGetAccess() {
    OSMesg sp1c;
    if (!gOsPiAccessQueueCreated) {
        __osPiCreateAccessQueue();
    }
    osRecvMesg(&gOsPiMessageQueue, &sp1c, OS_MESG_BLOCK);
}

void __osPiRelAccess() {
    osSendMesg(&gOsPiMessageQueue, NULL, OS_MESG_NOBLOCK);
}
