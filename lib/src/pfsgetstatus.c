#include "libultra_internal.h"
#include "controller.h"

void __osPfsRequestOneChannel(int channel);
void __osPfsGetOneChannelData(int channel, OSContStatus *data);

s32 __osPfsGetStatus(OSMesgQueue *queue, int channel) {
    s32 ret;
    OSMesg dummy;
    OSContStatus data;
    ret = 0;
    __osPfsRequestOneChannel(channel);
    ret = __osSiRawStartDma(OS_WRITE, &__osPfsPifRam);
    osRecvMesg(queue, &dummy, OS_MESG_BLOCK);
    ret = __osSiRawStartDma(OS_READ, &__osPfsPifRam);
    osRecvMesg(queue, &dummy, OS_MESG_BLOCK);
    __osPfsGetOneChannelData(channel, &data);
    if (((data.status & CONT_CARD_ON) != 0) && ((data.status & CONT_CARD_PULL) != 0)) {
        return PFS_ERR_NEW_PACK;
    }
    if ((data.errnum != 0) || ((data.status & CONT_CARD_ON) == 0)) {
        return PFS_ERR_NOPACK;
    }
    if ((data.status & CONT_ADDR_CRC_ER) != 0) {
        return PFS_ERR_CONTRFAIL;
    }
    return ret;
}

void __osPfsRequestOneChannel(int channel) {
    u8 *ptr;
    __OSContRequesFormatShort requestformat;
    int i;

    _osLastSentSiCmd = CONT_CMD_REQUEST_STATUS;
    __osPfsPifRam.pifstatus = CONT_CMD_EXE;
    ptr = (u8 *)&__osPfsPifRam;

    requestformat.txsize = CONT_CMD_REQUEST_STATUS_TX;
    requestformat.rxsize = CONT_CMD_REQUEST_STATUS_RX;
    requestformat.cmd = CONT_CMD_REQUEST_STATUS;
    requestformat.typeh = CONT_CMD_NOP;
    requestformat.typel = CONT_CMD_NOP;
    requestformat.status = CONT_CMD_NOP;
    for (i = 0; i < channel; i++) {
        *ptr++ = 0;
    }

    *(__OSContRequesFormatShort *)ptr = requestformat;
    ptr += sizeof(__OSContRequesFormatShort);
    *ptr = CONT_CMD_END;
}

void __osPfsGetOneChannelData(int channel, OSContStatus *data) {
    u8 *ptr;
    __OSContRequesFormatShort requestformat;
    int i;
    ptr = (u8*)&__osPfsPifRam;
    for (i = 0; i < channel; i++) {
        ptr++;
    }
    requestformat = *(__OSContRequesFormatShort *)ptr;
    data->errnum = CHNL_ERR(requestformat);
    if (data->errnum == 0) {
        data->type = (requestformat.typel << 8) | (requestformat.typeh);
        data->status = requestformat.status;
    }
}
