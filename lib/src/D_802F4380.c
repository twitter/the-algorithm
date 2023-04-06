#include "libultra_internal.h"
#include "hardware.h"
#include "new_func.h"
#include "macros.h"
#if defined(VERSION_EU) || defined(VERSION_SH)
u32 D_802F4380() {
    u32 sp3c;
    u32 sp38;
    u32 sp34;
    __OSTranxInfo *sp30;
    __OSBlockInfo *sp2c;
    u32 sp28;
    UNUSED __OSBlockInfo *sp24;
    if (!EU_D_80302090) {
        return 0;
    }
    sp30 = &__osDiskHandle->transferInfo;
    sp2c = &sp30->block[sp30->blockNum];
    sp38 = HW_REG(PI_STATUS_REG, u32);
    if (sp38 & PI_STATUS_BUSY) {
        HW_REG(PI_STATUS_REG, u32) = PI_STATUS_RESET_CONTROLLER | PI_STATUS_CLEAR_INTR;
        WAIT_ON_IOBUSY(sp38);
        sp3c = HW_REG(ASIC_STATUS, u32);
        if (sp3c & MECHANIC_INTERRUPT) {
            WAIT_ON_IOBUSY(sp38);
            HW_REG(ASIC_BM_CTL, u32) = sp30->bmCtlShadow | MECHANIC_INTERRUPT_RESET;
        }
        sp30->unk10 = 75;
        func_802F4A20();
        return 1;
    }
    WAIT_ON_IOBUSY(sp38);
    sp3c = HW_REG(ASIC_STATUS, u32);
    if (sp3c & MECHANIC_INTERRUPT) {
        WAIT_ON_IOBUSY(sp38);
        HW_REG(ASIC_BM_CTL, u32) = sp30->bmCtlShadow | MECHANIC_INTERRUPT_RESET;
        sp30->unk10 = 0;
        return 0;
    }
    if (sp3c & BUFFER_MANAGER_ERROR) {
        sp30->unk10 = 3;
        func_802F4A20();
        return 1;
    }
    if (sp30->cmdType == 1) {
        if ((sp3c & DATA_REQUEST) == 0) {
            if (sp30->sectorNum + 1 != sp30->transferMode * 85) {
                sp30->unk10 = 6;
                func_802F4A20();
                return 1;
            }
            HW_REG(PI_STATUS_REG, u32) = PI_STATUS_CLEAR_INTR;
            D_8030208C |= 0x00100401;
            sp30->unk10 = 0;
            func_802F4B08();
            return 1;
        }
        sp2c->dramAddr = (void *) ((u32) sp2c->dramAddr + sp2c->sectorSize);
        sp30->sectorNum += 1;
        osEPiRawStartDma(__osDiskHandle, 1, 0x05000400, sp2c->dramAddr, sp2c->sectorSize);
        return 1;
    }
    if (sp30->cmdType == 0) {
        if (sp30->transferMode == 3) {
            if ((s32)(sp2c->C1ErrNum + 17) < sp30->sectorNum) {
                sp30->unk10 = 0;
                func_802F4A20();
                return 1;
            }
            if ((sp3c & DATA_REQUEST) == 0) {
                sp30->unk10 = 17;
                func_802F4A20();
                return 1;
            }
        } else {
            sp2c->dramAddr = (void *) ((u32) sp2c->dramAddr + sp2c->sectorSize);
        }
        sp34 = HW_REG(ASIC_BM_STATUS, u32);
        if (((C1_SINGLE & sp34) && (C1_DOUBLE & sp34)) || (sp34 & MICRO_STATUS))
        {
            if (sp2c->C1ErrNum > 3) {
                if (sp30->transferMode != 3 || sp30->sectorNum > 0x52) {
                    sp30->unk10 = 17;
                    func_802F4A20();
                    return 1;
                }
            } else {
                sp28 = sp2c->C1ErrNum;
                sp2c->C1ErrSector[sp28] = sp30->sectorNum + 1;
            }
            sp2c->C1ErrNum += 1;
        }
        if (sp3c & C2_TRANSFER) {
            if (sp30->sectorNum != 87) {
                sp30->unk10 = 6;
                func_802F4A20();
            }
            if (sp30->transferMode == 2 && sp30->blockNum == 0) {
                sp30->blockNum = 1;
                sp30->sectorNum = -1;
                sp30->block[1].dramAddr =
                    (void *) ((u32) sp30->block[1].dramAddr - sp30->block[1].sectorSize);
            } else {
                HW_REG(PI_STATUS_REG, u32) = PI_STATUS_CLEAR_INTR;
                D_8030208C |= 0x00100401;
            }
            osEPiRawStartDma(__osDiskHandle, 0, 0x5000000, sp2c->C2Addr, sp2c->sectorSize * 4);
            sp30->unk10 = 0;
            return 1;
        }
        
        if (sp30->sectorNum == -1 && sp30->transferMode == 2 && sp30->blockNum == 1) {
            sp24 = &sp30->block[0];
            if (sp30->block[0].C1ErrNum == 0) {
                if (((u32 *) sp30->block[0].C2Addr)[0] | ((u32 *) sp30->block[0].C2Addr)[1]
                    | ((u32 *) sp30->block[0].C2Addr)[2] | ((u32 *) sp30->block[0].C2Addr)[3]) {
                    sp30->unk10 = 6;
                    func_802F4A20();
                    return 1;
                }
            }
            sp30->unk10 = 0;
            func_802F4B08();
        }
        sp30->sectorNum += 1;
        if (sp3c & DATA_REQUEST) {
            if (sp30->sectorNum > 0x54) {
                sp30->unk10 = 6;
                func_802F4A20();
                return 1;
            }
            osEPiRawStartDma(__osDiskHandle, 0, 0x05000400, sp2c->dramAddr, sp2c->sectorSize);
            sp30->unk10 = 0;
            return 1;
        }
        if (sp30->sectorNum <= 0x54) {
            sp30->unk10 = 6;
            func_802F4A20();
            return 1;
        }
        return 1;
    }
    sp30->unk10 = 75;
    func_802F4A20();
    return 1;
}
#endif
