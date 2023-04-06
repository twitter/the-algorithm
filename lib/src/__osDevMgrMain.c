#include "libultra_internal.h"
#include "macros.h"
#if defined(VERSION_EU)
#include "new_func.h"
void __osDevMgrMain(void *args) {
    OSIoMesg *sp44;
    OSMesg sp40;
    OSMesg sp3c;
    s32 sp38;
    OSMgrArgs *sp34;
    UNUSED u32 sp30;
    u32 sp2c;
    __OSBlockInfo *sp28;
    __OSTranxInfo *sp24;
    sp30 = 0;
    sp2c = 0;
    sp44 = NULL;
    sp38 = 0;
    sp34 = (OSMgrArgs *) args;
    while (1) {
        osRecvMesg(sp34->unk08, (OSMesg) &sp44, OS_MESG_BLOCK);
        if (sp44->piHandle != NULL && sp44->piHandle->type == 2
            && (sp44->piHandle->transferInfo.cmdType == 0
                || sp44->piHandle->transferInfo.cmdType == 1)) {
            sp24 = &sp44->piHandle->transferInfo;
            sp28 = &sp24->block[sp24->blockNum];
            sp24->sectorNum = -1;
            if (sp24->transferMode != 3) {
                sp28->dramAddr = (void *) ((u32) sp28->dramAddr - sp28->sectorSize);
            }
            if (sp24->transferMode == 2 && sp44->piHandle->transferInfo.cmdType == 0)
                sp2c = 1;
            else
                sp2c = 0;
            osRecvMesg(sp34->unk10, &sp3c, OS_MESG_BLOCK);
            func_802F7140(0x00100401); // remove magic constant!
            func_802F71A0(sp44->piHandle, 0x05000510, (sp24->bmCtlShadow | 0x80000000));
            while (1) {
                osRecvMesg(sp34->unk0c, &sp40, OS_MESG_BLOCK);
                sp30 = osSendMesg(sp44->hdr.retQueue, sp44, OS_MESG_NOBLOCK);
                if (sp2c != 1 || sp44->piHandle->transferInfo.unk10 != 0)
                    break;
                sp2c = 0;
            }
            osSendMesg(sp34->unk10, NULL, OS_MESG_NOBLOCK);
            if (sp44->piHandle->transferInfo.blockNum == 1)
                func_802F71F0();
        } else {
            switch (sp44->hdr.type) {
                case 11:
                    osRecvMesg(sp34->unk10, &sp3c, OS_MESG_BLOCK);
                    sp38 = sp34->dma_func(OS_READ, sp44->devAddr, sp44->dramAddr, sp44->size);
                    break;
                case 12:
                    osRecvMesg(sp34->unk10, &sp3c, OS_MESG_BLOCK);
                    sp38 = sp34->dma_func(OS_WRITE, sp44->devAddr, sp44->dramAddr, sp44->size);
                    break;
                case 15:
                    osRecvMesg(sp34->unk10, &sp3c, OS_MESG_BLOCK);
                    sp38 = sp34->edma_func(sp44->piHandle, OS_READ, sp44->devAddr, sp44->dramAddr,
                                           sp44->size);
                    break;
                case 16:
                    osRecvMesg(sp34->unk10, &sp3c, OS_MESG_BLOCK);
                    sp38 = sp34->edma_func(sp44->piHandle, OS_WRITE, sp44->devAddr, sp44->dramAddr,
                                           sp44->size);
                    break;
                case 10:
                    osSendMesg(sp44->hdr.retQueue, sp44, OS_MESG_NOBLOCK);
                    sp38 = -1;
                    break;
                    break;
                default:
                    sp38 = -1;
                    break;
            }
            if (sp38 == 0) {
                osRecvMesg(sp34->unk0c, &sp40, OS_MESG_BLOCK);
                sp30 = osSendMesg(sp44->hdr.retQueue, sp44, OS_MESG_NOBLOCK);
                osSendMesg(sp34->unk10, NULL, OS_MESG_NOBLOCK);
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
    while (1) {
        osRecvMesg(sp24->unk08, (OSMesg) &sp34, OS_MESG_BLOCK);
        switch (sp34->hdr.type) {
            case 11:
                osRecvMesg(sp24->unk10, &sp2c, OS_MESG_BLOCK);
                sp28 = sp24->dma_func(OS_READ, sp34->devAddr, sp34->dramAddr, sp34->size);
                break;
            case 12:
                osRecvMesg(sp24->unk10, &sp2c, OS_MESG_BLOCK);
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
            osRecvMesg(sp24->unk0c, &sp30, OS_MESG_BLOCK);
            osSendMesg(sp34->hdr.retQueue, sp34, OS_MESG_NOBLOCK);
            osSendMesg(sp24->unk10, NULL, OS_MESG_NOBLOCK);
        }
    }
}
#endif
