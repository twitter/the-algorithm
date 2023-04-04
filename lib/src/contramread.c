#include "libultra_internal.h"
#include "PR/rcp.h"
#include "controller.h"

extern s32 func_8030A5C0(OSMesgQueue *, s32);
void __osPackRamReadData(int channel, u16 address);

s32 __osContRamRead(OSMesgQueue *mq, int channel, u16 address, u8 *buffer) {
    s32 ret;
    int i;
    u8 *ptr;
    __OSContRamReadFormat ramreadformat;
    int retry;
    ret = 0;
    ptr = (u8 *)&__osPfsPifRam;
    retry = 2;
    __osSiGetAccess();
    _osLastSentSiCmd = CONT_CMD_READ_MEMPACK;
    __osPackRamReadData(channel, address);
    ret = __osSiRawStartDma(OS_WRITE, &__osPfsPifRam);
    osRecvMesg(mq, NULL, OS_MESG_BLOCK);
    do {
        ret = __osSiRawStartDma(OS_READ, &__osPfsPifRam);
        osRecvMesg(mq, NULL, OS_MESG_BLOCK);
        ptr = (u8 *)&__osPfsPifRam;
        if (channel != 0) {
            for (i = 0; i < channel; i++) {
                ptr++;
            }
        }
        ramreadformat = *(__OSContRamReadFormat *)ptr;
        ret = CHNL_ERR(ramreadformat);
        if (ret == 0) {
            u8 c;
            c = __osContDataCrc((u8*)&ramreadformat.data);
            if (c != ramreadformat.datacrc) {
                ret = func_8030A5C0(mq, channel);
                if (ret != 0) {
                    __osSiRelAccess();
                    return ret;
                }
                ret = PFS_ERR_CONTRFAIL;
            } else {
                for (i = 0; i < ARRLEN(ramreadformat.data); i++) {
                    *buffer++ = ramreadformat.data[i];
                }
            }
        } else {
            ret = PFS_ERR_NOPACK;
        }
        if (ret != PFS_ERR_CONTRFAIL) {
            break;
        }
    } while (retry-- >= 0);
    __osSiRelAccess();
    return ret;
}

void __osPackRamReadData(int channel, u16 address) {
    u8 *ptr;
    __OSContRamReadFormat ramreadformat;
    int i;

    ptr = (u8 *)__osPfsPifRam.ramarray;

    for (i = 0; i < ARRLEN(__osPfsPifRam.ramarray) + 1; i++) { // also clear pifstatus
        __osPfsPifRam.ramarray[i] = 0;
    }

    __osPfsPifRam.pifstatus = CONT_CMD_EXE;
    ramreadformat.dummy = CONT_CMD_NOP;
    ramreadformat.txsize = CONT_CMD_READ_MEMPACK_TX;
    ramreadformat.rxsize = CONT_CMD_READ_MEMPACK_RX;
    ramreadformat.cmd = CONT_CMD_READ_MEMPACK;
    ramreadformat.address = (address << 0x5) | __osContAddressCrc(address);
    ramreadformat.datacrc = CONT_CMD_NOP;
    for (i = 0; i < ARRLEN(ramreadformat.data); i++) {
        ramreadformat.data[i] = CONT_CMD_NOP;
    }
    if (channel != 0) {
        for (i = 0; i < channel; i++) {
            *ptr++ = 0;
        }
    }
    *(__OSContRamReadFormat *)ptr = ramreadformat;
    ptr += sizeof(__OSContRamReadFormat);
    ptr[0] = CONT_CMD_END;
}
