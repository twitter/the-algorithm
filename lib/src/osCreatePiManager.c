#include "libultra_internal.h"

#define OS_PI_MGR_MESG_BUFF_SIZE 1

OSMgrArgs piMgrArgs = { 0 };
#if defined(VERSION_EU) || defined(VERSION_SH)
OSPiHandle *D_80302DFC = NULL;
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

    if (!piMgrArgs.initialized) {
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
        piMgrArgs.initialized = TRUE;
        piMgrArgs.mgrThread = &piMgrThread;
        piMgrArgs.unk08 = cmdQ;
        piMgrArgs.unk0c = &__osPiMesgQueue;
        piMgrArgs.unk10 = &gOsPiMessageQueue;
        piMgrArgs.dma_func = osPiRawStartDma;
#ifdef VERSION_EU
        piMgrArgs.edma_func = osEPiRawStartDma;
#endif
        osCreateThread(&piMgrThread, 0, __osDevMgrMain, (void *) &piMgrArgs, &piMgrStack[0x400], pri);
        osStartThread(&piMgrThread);
        __osRestoreInt(int_disabled);
        if (newPri != -1) {
            osSetThreadPri(NULL, newPri);
        }
    }
}
