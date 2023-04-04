#include "libultra_internal.h"
#include "macros.h"

#if defined(VERSION_EU) || defined(VERSION_SH)
#include "new_func.h"

void __osDevMgrMain(void *args) {
    OSIoMesg *mb;
    OSMesg em;
    OSMesg dummy;
    s32 ret;
    OSMgrArgs *sp34;
#ifdef VERSION_EU
    UNUSED u32 sp30;
#endif
    u32 sp2c;
    __OSBlockInfo *sp28;
    __OSTranxInfo *sp24;
#ifdef VERSION_SH
    u32 tmp;
#endif
#ifdef VERSION_EU
    sp30 = 0;
#endif
    sp2c = 0;
    mb = NULL;
    ret = 0;
    sp34 = (OSMgrArgs *) args;
    while (TRUE) {
        osRecvMesg(sp34->cmdQueue, (OSMesg) &mb, OS_MESG_BLOCK);
        if (mb->piHandle != NULL && mb->piHandle->type == 2
            && (mb->piHandle->transferInfo.cmdType == 0
                || mb->piHandle->transferInfo.cmdType == 1)) {
            sp24 = &mb->piHandle->transferInfo;
            sp28 = &sp24->block[sp24->blockNum];
            sp24->sectorNum = -1;
            if (sp24->transferMode != 3) {
                sp28->dramAddr = (void *) ((u32) sp28->dramAddr - sp28->sectorSize);
            }
            if (sp24->transferMode == 2 && mb->piHandle->transferInfo.cmdType == 0) {
                sp2c = 1;
            } else {
                sp2c = 0;
            }
            osRecvMesg(sp34->accessQueue, &dummy, OS_MESG_BLOCK);
            __osResetGlobalIntMask(0x00100401); // remove magic constant!
            __osEPiRawWriteIo(mb->piHandle, 0x05000510, (sp24->bmCtlShadow | 0x80000000));
            while (TRUE) {
                osRecvMesg(sp34->eventQueue, &em, OS_MESG_BLOCK);
#ifdef VERSION_SH
                sp24 = &mb->piHandle->transferInfo;
                sp28 = &sp24->block[sp24->blockNum];
                if (sp28->errStatus == 0x1D) {
                    __osEPiRawWriteIo(mb->piHandle, 0x5000510, sp24->bmCtlShadow | 0x10000000);
                    __osEPiRawWriteIo(mb->piHandle, 0x5000510, sp24->bmCtlShadow);
                    __osEPiRawReadIo(mb->piHandle, 0x5000508, &tmp);
                    if ((tmp & 0x2000000) != 0) {
                        __osEPiRawWriteIo(mb->piHandle, 0x5000510, sp24->bmCtlShadow | 0x1000000);
                    }
                    sp28->errStatus = 4;
                    HW_REG(PI_STATUS_REG, u32) = PI_STATUS_CLEAR_INTR;
                    __osSetGlobalIntMask(0x100C01);
                }
                osSendMesg(mb->hdr.retQueue, mb, OS_MESG_NOBLOCK);
                if (sp2c != 1 || mb->piHandle->transferInfo.block[0].errStatus != 0) {
                    break;
                }
#else
                sp30 = osSendMesg(mb->hdr.retQueue, mb, OS_MESG_NOBLOCK);
                if (sp2c != 1 || mb->piHandle->transferInfo.errStatus != 0) {
                    break;
                }
#endif
                sp2c = 0;
            }
            osSendMesg(sp34->accessQueue, NULL, OS_MESG_NOBLOCK);
            if (mb->piHandle->transferInfo.blockNum == 1) {
                func_802F71F0();
            }
        } else {
            switch (mb->hdr.type) {
                case 11:
                    osRecvMesg(sp34->accessQueue, &dummy, OS_MESG_BLOCK);
                    ret = sp34->dma_func(OS_READ, mb->devAddr, mb->dramAddr, mb->size);
                    break;
                case 12:
                    osRecvMesg(sp34->accessQueue, &dummy, OS_MESG_BLOCK);
                    ret = sp34->dma_func(OS_WRITE, mb->devAddr, mb->dramAddr, mb->size);
                    break;
                case 15:
                    osRecvMesg(sp34->accessQueue, &dummy, OS_MESG_BLOCK);
                    ret = sp34->edma_func(mb->piHandle, OS_READ, mb->devAddr, mb->dramAddr,
                                           mb->size);
                    break;
                case 16:
                    osRecvMesg(sp34->accessQueue, &dummy, OS_MESG_BLOCK);
                    ret = sp34->edma_func(mb->piHandle, OS_WRITE, mb->devAddr, mb->dramAddr,
                                           mb->size);
                    break;
                case 10:
                    osSendMesg(mb->hdr.retQueue, mb, OS_MESG_NOBLOCK);
                    ret = -1;
                    break;
                    break;
                default:
                    ret = -1;
                    break;
            }
            if (ret == 0) {
                osRecvMesg(sp34->eventQueue, &em, OS_MESG_BLOCK);
#ifdef VERSION_EU
                sp30 =
#endif
                osSendMesg(mb->hdr.retQueue, mb, OS_MESG_NOBLOCK);
                osSendMesg(sp34->accessQueue, NULL, OS_MESG_NOBLOCK);
            }
        }
    }
}
#else
void __osDevMgrMain(void *args) {
    OSIoMesg *sp34;
    OSMesg sp30;
    OSMesg sp2c;
    s32 sp28;
    OSMgrArgs *sp24;
    sp34 = NULL;
    sp28 = 0;
    sp24 = (OSMgrArgs *) args;
    while (TRUE) {
        osRecvMesg(sp24->cmdQueue, (OSMesg) &sp34, OS_MESG_BLOCK);
        switch (sp34->hdr.type) {
            case 11:
                osRecvMesg(sp24->accessQueue, &sp2c, OS_MESG_BLOCK);
                sp28 = sp24->dma_func(OS_READ, sp34->devAddr, sp34->dramAddr, sp34->size);
                break;
            case 12:
                osRecvMesg(sp24->accessQueue, &sp2c, OS_MESG_BLOCK);
                sp28 = sp24->dma_func(OS_WRITE, sp34->devAddr, sp34->dramAddr, sp34->size);
                break;
            case 10:
                osSendMesg(sp34->hdr.retQueue, sp34, OS_MESG_NOBLOCK);
                sp28 = -1;
                break;
            default:
                sp28 = -1;
                break;
        }
        if (sp28 == 0) {
            osRecvMesg(sp24->eventQueue, &sp30, OS_MESG_BLOCK);
            osSendMesg(sp34->hdr.retQueue, sp34, OS_MESG_NOBLOCK);
            osSendMesg(sp24->accessQueue, NULL, OS_MESG_NOBLOCK);
        }
    }
}
#endif
