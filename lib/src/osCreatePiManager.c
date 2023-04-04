#include "libultra_internal.h"

#define OS_PI_MGR_MESG_BUFF_SIZE 1

#ifdef VERSION_SH // TODO: In libreultra this is in an include
extern OSPiHandle *CartRomHandle;
extern OSPiHandle *LeoDiskHandle;
#endif

OSMgrArgs __osPiDevMgr = { 0 };
#if defined(VERSION_EU) || defined(VERSION_SH)
OSPiHandle *__osPiTable = NULL;
#endif
#ifdef VERSION_SH
OSPiHandle **__osCurrentHandle[2] = { &CartRomHandle, &LeoDiskHandle };
#endif
OSThread piMgrThread;
u32 piMgrStack[0x400]; // stack bottom
OSMesgQueue __osPiMesgQueue;
OSMesg piMgrMesgBuff[OS_PI_MGR_MESG_BUFF_SIZE + 1];

extern u32 gOsPiAccessQueueCreated;
extern OSMesgQueue gOsPiMessageQueue;
void __osDevMgrMain(void *);

void osCreatePiManager(OSPri pri, OSMesgQueue *cmdQ, OSMesg *cmdBuf, s32 cmdMsgCnt) {
    u32 int_disabled;
    OSPri newPri;
    OSPri currentPri;

    if (!__osPiDevMgr.initialized) {
        osCreateMesgQueue(cmdQ, cmdBuf, cmdMsgCnt);
        osCreateMesgQueue(&__osPiMesgQueue, &piMgrMesgBuff[0], OS_PI_MGR_MESG_BUFF_SIZE);
        if (!gOsPiAccessQueueCreated) {
            __osPiCreateAccessQueue();
        } // what is this constant geez
        osSetEventMesg(OS_EVENT_PI, &__osPiMesgQueue, (void *) 0x22222222);
        newPri = -1;
        currentPri = osGetThreadPri(NULL);
        if (currentPri < pri) {
            newPri = currentPri;
            osSetThreadPri(NULL, pri);
        }
        int_disabled = __osDisableInt();
        __osPiDevMgr.initialized = TRUE;
        __osPiDevMgr.mgrThread = &piMgrThread;
        __osPiDevMgr.cmdQueue = cmdQ;
        __osPiDevMgr.eventQueue = &__osPiMesgQueue;
        __osPiDevMgr.accessQueue = &gOsPiMessageQueue;
        __osPiDevMgr.dma_func = osPiRawStartDma;
#if defined(VERSION_EU) || defined(VERSION_SH)
        __osPiDevMgr.edma_func = osEPiRawStartDma;
#endif
        osCreateThread(&piMgrThread, 0, __osDevMgrMain, (void *) &__osPiDevMgr, &piMgrStack[0x400], pri);
        osStartThread(&piMgrThread);
        __osRestoreInt(int_disabled);
        if (newPri != -1) {
            osSetThreadPri(NULL, newPri);
        }
    }
}
