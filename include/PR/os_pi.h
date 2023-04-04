#ifndef _ULTRA64_PI_H_
#define _ULTRA64_PI_H_
#include <ultra64.h>

/* Ultra64 Parallel Interface */

/* Types */

typedef struct {
#if !defined(VERSION_EU)
    u32 errStatus;
#endif
    void *dramAddr;
    void *C2Addr;
    u32 sectorSize;
    u32 C1ErrNum;
    u32 C1ErrSector[4];
} __OSBlockInfo;

typedef struct {
    u32 cmdType;       // 0
    u16 transferMode;  // 4
    u16 blockNum;      // 6
    s32 sectorNum;     // 8
    uintptr_t devAddr; // c
#if defined(VERSION_EU)
    u32 errStatus; //error status added moved to blockinfo
#endif
    u32 bmCtlShadow;        // 10
    u32 seqCtlShadow;       // 14
    __OSBlockInfo block[2]; // 18
} __OSTranxInfo;

typedef struct OSPiHandle_s {
    struct OSPiHandle_s *next;
    u8 type;
    u8 latency;
    u8 pageSize;
    u8 relDuration;
    u8 pulse;
    u8 domain;
    u32 baseAddress;
    u32 speed;
    __OSTranxInfo transferInfo;
} OSPiHandle;

typedef struct {
    u8 type;
    uintptr_t address;
} OSPiInfo;

typedef struct {
    u16 type;
    u8 pri;
    u8 status;
    OSMesgQueue *retQueue;
} OSIoMesgHdr;

typedef struct {
    /*0x00*/ OSIoMesgHdr hdr;
    /*0x08*/ void *dramAddr;
    /*0x0C*/ uintptr_t devAddr;
    /*0x10*/ size_t size;
#if defined(VERSION_EU) || defined(VERSION_SH)
    OSPiHandle *piHandle; // from the official definition
#endif
} OSIoMesg;

/* Definitions */

#define OS_READ 0  // device -> RDRAM
#define OS_WRITE 1 // device <- RDRAM

#define OS_MESG_PRI_NORMAL 0
#define OS_MESG_PRI_HIGH 1

/* Functions */

s32 osPiStartDma(OSIoMesg *mb, s32 priority, s32 direction, uintptr_t devAddr, void *vAddr,
                 size_t nbytes, OSMesgQueue *mq);
void osCreatePiManager(OSPri pri, OSMesgQueue *cmdQ, OSMesg *cmdBuf, s32 cmdMsgCnt);
OSMesgQueue *osPiGetCmdQueue(void);
s32 osPiWriteIo(uintptr_t devAddr, u32 data);
s32 osPiReadIo(uintptr_t devAddr, u32 *data);

s32 osPiRawStartDma(s32 dir, u32 cart_addr, void *dram_addr, size_t size);
s32 osEPiRawStartDma(OSPiHandle *piHandle, s32 dir, u32 cart_addr, void *dram_addr, size_t size);
#endif
