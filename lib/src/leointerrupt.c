#include "libultra_internal.h"
#include "PR/rcp.h"
#include "bstring.h"
#include "piint.h"
#include "osint.h"

u8 leoDiskStack[OS_PIM_STACKSIZE]; // technically should have a OS_LEO_STACKSIZE or something..

#ifdef VERSION_SH

// TODO: so many magic constants :'(
static void __osLeoResume(void);
static void __osLeoAbnormalResume(void);

s32 __osLeoInterrupt() {
    u32 stat;
    volatile u32 pi_stat;
    u32 bm_stat;
    __OSTranxInfo *info;
    __OSBlockInfo *blockInfo;
    stat = 0;
    info = &__osDiskHandle->transferInfo;
    blockInfo = &info->block[info->blockNum];
    pi_stat = IO_READ(PI_STATUS_REG);
    if (pi_stat & PI_STATUS_DMA_BUSY) {
        __OSGlobalIntMask = __OSGlobalIntMask & ~SR_IBIT4; //cartridge interrupt
        blockInfo->errStatus = LEO_ERROR_29;
        __osLeoResume();
        return 1;
    }
    WAIT_ON_IOBUSY(pi_stat);
    stat = IO_READ(LEO_STATUS);
    if (stat & LEO_STATUS_MECHANIC_INTERRUPT) {
        WAIT_ON_IOBUSY(pi_stat);
        IO_WRITE(LEO_BM_CTL, info->bmCtlShadow | LEO_BM_CTL_CLR_MECHANIC_INTR);
        blockInfo->errStatus = LEO_ERROR_GOOD;
        return 0;
    }
    if (info->cmdType == LEO_CMD_TYPE_2) {
        return 1;
    }
    if (stat & LEO_STATUS_BUFFER_MANAGER_ERROR) {
        WAIT_ON_IOBUSY(pi_stat);
        stat = IO_READ(LEO_STATUS);
        blockInfo->errStatus = LEO_ERROR_22;
        __osLeoResume();
        IO_WRITE(PI_STATUS_REG, PI_STATUS_CLR_INTR);
        __OSGlobalIntMask |= OS_IM_PI;
        return 1;
    }
    if (info->cmdType == LEO_CMD_TYPE_1) {
        if ((stat & LEO_STATUS_DATA_REQUEST) == 0) {
            if (info->sectorNum + 1 != info->transferMode * 85) {
                blockInfo->errStatus = LEO_ERROR_24;
                __osLeoAbnormalResume();
                return 1;
            }
            IO_WRITE(PI_STATUS_REG, PI_STATUS_CLR_INTR);
            __OSGlobalIntMask |= OS_IM_PI;
            blockInfo->errStatus = LEO_ERROR_GOOD;
            __osLeoResume();
            return 1;
        }
        blockInfo->dramAddr = (void *)((u32)blockInfo->dramAddr + blockInfo->sectorSize);
        info->sectorNum++;
        osEPiRawStartDma(__osDiskHandle, OS_WRITE, LEO_SECTOR_BUFF, blockInfo->dramAddr, blockInfo->sectorSize);
        return 1;
    }
    if (info->cmdType == LEO_CMD_TYPE_0) {
        if (info->transferMode == LEO_SECTOR_MODE) {
            if ((s32)blockInfo->C1ErrNum + 17 < info->sectorNum) {
                blockInfo->errStatus = LEO_ERROR_GOOD;
                __osLeoAbnormalResume();
                return 1;
            }
            if ((stat & LEO_STATUS_DATA_REQUEST) == 0) {
                blockInfo->errStatus = LEO_ERROR_23;
                __osLeoAbnormalResume();
                return 1;
            }
        } else {
            blockInfo->dramAddr = (void *)((u32)blockInfo->dramAddr + blockInfo->sectorSize);
        }
        bm_stat = IO_READ(LEO_BM_STATUS);
        if ((bm_stat & LEO_BM_STATUS_C1SINGLE && bm_stat & LEO_BM_STATUS_C1DOUBLE) || bm_stat & LEO_BM_STATUS_MICRO) {
            if (blockInfo->C1ErrNum > 3) {
                if (info->transferMode != LEO_SECTOR_MODE || info->sectorNum > 0x52) {
                    blockInfo->errStatus = LEO_ERROR_23;
                    __osLeoAbnormalResume();
                    return 1;
                }
            } else {
                int errNum = blockInfo->C1ErrNum;
                blockInfo->C1ErrSector[errNum] = info->sectorNum + 1;
            }
            blockInfo->C1ErrNum++;
        }
        if (stat & LEO_STATUS_C2_TRANSFER) {
            if (info->sectorNum != 87) {
                blockInfo->errStatus = LEO_ERROR_24;
                __osLeoAbnormalResume();
            }
            if (info->transferMode == LEO_TRACK_MODE && info->blockNum == 0) {
                info->blockNum = 1;
                info->sectorNum = -1;
                info->block[1].dramAddr = (void *)((u32)info->block[1].dramAddr - info->block[1].sectorSize);

                blockInfo->errStatus = LEO_ERROR_22;
            } else {
                IO_WRITE(PI_STATUS_REG, PI_STATUS_CLR_INTR);
                __OSGlobalIntMask |= OS_IM_PI;
                info->cmdType = LEO_CMD_TYPE_2;
                blockInfo->errStatus = LEO_ERROR_GOOD;
            }
            osEPiRawStartDma(__osDiskHandle, OS_READ, LEO_C2_BUFF, blockInfo->C2Addr, blockInfo->sectorSize * 4);
            return 1;
        }

        if (info->sectorNum == -1 && info->transferMode == LEO_TRACK_MODE && info->blockNum == 1) {
            __OSBlockInfo *bptr = &info->block[0];
            if (bptr->C1ErrNum == 0) {
                if (((u32 *)bptr->C2Addr)[0] | ((u32 *)bptr->C2Addr)[1] | ((u32 *)bptr->C2Addr)[2] | ((u32 *)bptr->C2Addr)[3]) {
                    bptr->errStatus = LEO_ERROR_24;
                    __osLeoAbnormalResume();
                    return 1;
                }
            }
            bptr->errStatus = 0;
            __osLeoResume();
        }
        info->sectorNum++;
        if (stat & LEO_STATUS_DATA_REQUEST) {
            if (info->sectorNum > 0x54) {
                blockInfo->errStatus = LEO_ERROR_24;
                __osLeoAbnormalResume();
                return 1;
            }
            osEPiRawStartDma(__osDiskHandle, 0, LEO_SECTOR_BUFF, blockInfo->dramAddr, blockInfo->sectorSize);
            blockInfo->errStatus = LEO_ERROR_GOOD;
            return 1;
        }
        if (info->sectorNum <= 0x54) {
            blockInfo->errStatus = LEO_ERROR_24;
            __osLeoAbnormalResume();
            return 1;
        }
        return 1;
    }
    blockInfo->errStatus = LEO_ERROR_4;
    __osLeoAbnormalResume();
    return 1;
}

static void __osLeoAbnormalResume(void) {
    __OSTranxInfo *info;
    u32 pi_stat;
    info = &__osDiskHandle->transferInfo;
    WAIT_ON_IOBUSY(pi_stat);
    IO_WRITE(LEO_BM_CTL, info->bmCtlShadow | LEO_BM_CTL_RESET);
    WAIT_ON_IOBUSY(pi_stat);
    IO_WRITE(LEO_BM_CTL, info->bmCtlShadow);
    __osLeoResume();
    IO_WRITE(PI_STATUS_REG, PI_STATUS_CLR_INTR);
    __OSGlobalIntMask |= OS_IM_PI;
}

static void __osLeoResume(void) {
    __OSEventState *es;
    OSMesgQueue *mq;
    s32 last;
    es = &__osEventStateTab[OS_EVENT_PI];
    mq = es->messageQueue;
    if (mq == NULL || MQ_IS_FULL(mq)) {
        return;
    }
    last = (mq->first + mq->validCount) % mq->msgCount;
    mq->msg[last] = es->message;
    mq->validCount++;
    if (mq->mtqueue->next != NULL) {
        __osEnqueueThread(&D_80334898, __osPopThread(&mq->mtqueue));
    }
}

#endif
