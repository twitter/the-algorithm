#include "libultra_internal.h"

#define OS_VI_MANAGER_MESSAGE_BUFF_SIZE 5

OSMgrArgs viMgrMainArgs = { 0 };
static OSThread viMgrThread;
static u32 viMgrStack[0x400]; // stack bottom
static OSMesgQueue __osViMesgQueue;
static OSMesg viMgrMesgBuff[OS_VI_MANAGER_MESSAGE_BUFF_SIZE];

static OSIoMesg viEventViMesg;
static OSIoMesg viEventCounterMesg;

extern void __osTimerServicesInit(void);
extern void __osTimerInterrupt(void);
extern OSTime __osCurrentTime;
extern u32 __osBaseCounter;
extern u32 __osViIntrCount;
void viMgrMain(void *);

void osCreateViManager(OSPri pri) {
    u32 int_disabled;
    OSPri newPri;
    OSPri currentPri;
    if (!viMgrMainArgs.initialized) {
        __osTimerServicesInit();
        osCreateMesgQueue(&__osViMesgQueue, &viMgrMesgBuff[0], OS_VI_MANAGER_MESSAGE_BUFF_SIZE);
        viEventViMesg.hdr.type = 13;
        viEventViMesg.hdr.pri = 0;
        viEventViMesg.hdr.retQueue = 0;
        viEventCounterMesg.hdr.type = 14;
        viEventCounterMesg.hdr.pri = 0;
        viEventCounterMesg.hdr.retQueue = 0;
        osSetEventMesg(OS_EVENT_VI, &__osViMesgQueue, &viEventViMesg);
        osSetEventMesg(OS_EVENT_COUNTER, &__osViMesgQueue, &viEventCounterMesg);
        newPri = -1;
        currentPri = osGetThreadPri(NULL);
        if (currentPri < pri) {
            newPri = currentPri;
            osSetThreadPri(NULL, pri);
        }
        int_disabled = __osDisableInt();
        viMgrMainArgs.initialized = TRUE;
        viMgrMainArgs.mgrThread = &viMgrThread;
        viMgrMainArgs.cmdQueue = &__osViMesgQueue;
        viMgrMainArgs.eventQueue = &__osViMesgQueue;
        viMgrMainArgs.accessQueue = NULL;
        viMgrMainArgs.dma_func = NULL;
#if defined(VERSION_EU) || defined(VERSION_SH)
        viMgrMainArgs.edma_func = NULL;
#endif

        osCreateThread(&viMgrThread, 0, viMgrMain, (void *) &viMgrMainArgs, &viMgrStack[0x400], pri);
        __osViInit();
        osStartThread(&viMgrThread);
        __osRestoreInt(int_disabled);
        if (newPri != -1) {
            osSetThreadPri(NULL, newPri);
        }
    }
}

void viMgrMain(void *vargs) {
    static u16 retrace;
    OSViContext *context;
    OSMgrArgs *args;
    OSMesg mesg;
    u32 sp28; // always 0
    u32 sp24; // time related
    mesg = NULL;
    sp28 = FALSE;
    context = __osViGetCurrentContext();

    if ((retrace = context->retraceCount) == 0) {
        retrace = 1;
    }

    args = (OSMgrArgs *) vargs;

    while (TRUE) {
        osRecvMesg(args->eventQueue, &mesg, OS_MESG_BLOCK);
        switch (*(u16 *) mesg) {
            case 13:
                __osViSwapContext();
                if (!--retrace) {
                    context = __osViGetCurrentContext();
                    if (context->mq != NULL) {
                        osSendMesg(context->mq, context->msg, OS_MESG_NOBLOCK);
                    }
                    retrace = context->retraceCount;
                }
                __osViIntrCount++;
                if (sp28) {
                    sp24 = osGetCount();
                    __osCurrentTime = sp24;
                    sp28 = 0;
                }
                sp24 = __osBaseCounter;
                __osBaseCounter = osGetCount();
                sp24 = __osBaseCounter - sp24;
                __osCurrentTime = __osCurrentTime + sp24;
                break;

            case 14:
                __osTimerInterrupt();
                break;
        }
    }
}
