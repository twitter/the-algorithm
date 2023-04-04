
/*====================================================================
 * os.h
 *
 * Copyright 1995, Silicon Graphics, Inc.
 * All Rights Reserved.
 *
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Silicon Graphics,
 * Inc.; the contents of this file may not be disclosed to third
 * parties, copied or duplicated in any form, in whole or in part,
 * without the prior written permission of Silicon Graphics, Inc.
 *
 * RESTRICTED RIGHTS LEGEND:
 * Use, duplication or disclosure by the Government is subject to
 * restrictions as set forth in subdivision (c)(1)(ii) of the Rights
 * in Technical Data and Computer Software clause at DFARS
 * 252.227-7013, and/or in similar or successor clauses in the FAR,
 * DOD or NASA FAR Supplement. Unpublished - rights reserved under the
 * Copyright Laws of the United States.
 *====================================================================*/

/**************************************************************************
 *
 *  $Revision: 1.149 $
 *  $Date: 1997/12/15 04:30:52 $
 *  $Source: /disk6/Master/cvsmdev2/PR/include/os.h,v $
 *
 **************************************************************************/


#ifndef _OS_H_
#define    _OS_H_

#ifdef _LANGUAGE_C_PLUS_PLUS
extern "C" {
#endif

#include <PR/ultratypes.h>
#include "PR/os_message.h"

#if defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS)

/**************************************************************************
 *
 * Type definitions
 *
 */

/*
 * Structure for device manager block
 */
typedef struct {
        s32             active;        /* Status flag */
    OSThread    *thread;    /* Calling thread */
        OSMesgQueue      *cmdQueue;    /* Command queue */
        OSMesgQueue      *evtQueue;    /* Event queue */
        OSMesgQueue      *acsQueue;    /* Access queue */
                    /* Raw DMA routine */
        s32             (*dma)(s32, u32, void *, u32);
        s32             (*edma)(OSPiHandle *, s32, u32, void *, u32);
} OSDevMgr;

/*
 * Structure for file system
 */



typedef struct {
    int        status;
    OSMesgQueue     *queue;
    int        channel;
    u8        id[32];
    u8        label[32];
    int        version;
    int        dir_size;
    int        inode_table;        /* block location */
    int        minode_table;        /* mirrioring inode_table */
    int        dir_table;        /* block location */
    int        inode_start_page;    /* page # */
    u8        banks;
    u8        activebank;
} OSPfs;


typedef struct {
    u32    file_size;    /* bytes */
      u32     game_code;
      u16     company_code;
      char      ext_name[4];
      char     game_name[16];
} OSPfsState;
    
/*
 * Structure for Profiler 
 */
typedef struct {
    u16    *histo_base;        /* histogram base */
    u32    histo_size;        /* histogram size */
    u32    *text_start;        /* start of text segment */
    u32    *text_end;        /* end of text segment */
} OSProf;

#endif /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

/**************************************************************************
 *
 * Global definitions
 *
 */

/* Thread states */

#define OS_STATE_STOPPED    1
#define OS_STATE_RUNNABLE    2
#define OS_STATE_RUNNING    4
#define OS_STATE_WAITING    8

/* Events */
#ifdef _FINALROM
#define OS_NUM_EVENTS           15
#else
#define OS_NUM_EVENTS           23
#endif

#define OS_EVENT_SW1              0     /* CPU SW1 interrupt */
#define OS_EVENT_SW2              1     /* CPU SW2 interrupt */
#define OS_EVENT_CART             2     /* Cartridge interrupt: used by rmon */
#define OS_EVENT_COUNTER          3     /* Counter int: used by VI/Timer Mgr */
#define OS_EVENT_SP               4     /* SP task done interrupt */
#define OS_EVENT_SI               5     /* SI (controller) interrupt */
#define OS_EVENT_AI               6     /* AI interrupt */
#define OS_EVENT_VI               7     /* VI interrupt: used by VI/Timer Mgr */
#define OS_EVENT_PI               8     /* PI interrupt: used by PI Manager */
#define OS_EVENT_DP               9     /* DP full sync interrupt */
#define OS_EVENT_CPU_BREAK        10    /* CPU breakpoint: used by rmon */
#define OS_EVENT_SP_BREAK         11    /* SP breakpoint:  used by rmon */
#define OS_EVENT_FAULT            12    /* CPU fault event: used by rmon */
#define OS_EVENT_THREADSTATUS     13    /* CPU thread status: used by rmon */
#define OS_EVENT_PRENMI           14    /* Pre NMI interrupt */
#ifndef _FINALROM
#define OS_EVENT_RDB_READ_DONE    15    /* RDB read ok event: used by rmon */
#define OS_EVENT_RDB_LOG_DONE     16    /* read of log data complete */
#define OS_EVENT_RDB_DATA_DONE    17    /* read of hostio data complete */
#define OS_EVENT_RDB_REQ_RAMROM   18    /* host needs ramrom access */
#define OS_EVENT_RDB_FREE_RAMROM  19    /* host is done with ramrom access */
#define OS_EVENT_RDB_DBG_DONE     20
#define OS_EVENT_RDB_FLUSH_PROF   21
#define OS_EVENT_RDB_ACK_PROF     22
#endif

/* Flags for debugging purpose */

#define    OS_FLAG_CPU_BREAK    1    /* Break exception has occurred */
#define    OS_FLAG_FAULT        2    /* CPU fault has occurred */

/* Interrupt masks */

#define    OS_IM_NONE    0x00000001
#define    OS_IM_SW1    0x00000501
#define    OS_IM_SW2    0x00000601
#define    OS_IM_CART    0x00000c01
#define    OS_IM_PRENMI    0x00001401
#define OS_IM_RDBWRITE    0x00002401
#define OS_IM_RDBREAD    0x00004401
#define    OS_IM_COUNTER    0x00008401
#define    OS_IM_CPU    0x0000ff01
#define    OS_IM_SP    0x00010401
#define    OS_IM_SI    0x00020401
#define    OS_IM_AI    0x00040401
#define    OS_IM_VI    0x00080401
#define    OS_IM_PI    0x00100401
#define    OS_IM_DP    0x00200401
#define    OS_IM_ALL    0x003fff01
#define    RCP_IMASK    0x003f0000
#define    RCP_IMASKSHIFT    16

/* Recommended thread priorities for the system threads */

#define OS_PRIORITY_MAX        255
#define OS_PRIORITY_VIMGR    254
#define OS_PRIORITY_RMON    250
#define OS_PRIORITY_RMONSPIN    200
#define OS_PRIORITY_PIMGR    150
#define OS_PRIORITY_SIMGR    140
#define    OS_PRIORITY_APPMAX    127
#define OS_PRIORITY_IDLE      0    /* Must be 0 */


/* Flags to turn blocking on/off when sending/receiving message */

#define    OS_MESG_NOBLOCK        0
#define    OS_MESG_BLOCK        1

/* Flags to indicate direction of data transfer */

#define    OS_READ            0        /* device -> RDRAM */
#define    OS_WRITE        1        /* device <- RDRAM */
#define    OS_OTHERS        2        /* for Leo disk only */

/*
 * I/O message types
 */
#define OS_MESG_TYPE_BASE    (10)
#define OS_MESG_TYPE_LOOPBACK    (OS_MESG_TYPE_BASE+0)
#define OS_MESG_TYPE_DMAREAD    (OS_MESG_TYPE_BASE+1)
#define OS_MESG_TYPE_DMAWRITE    (OS_MESG_TYPE_BASE+2)
#define OS_MESG_TYPE_VRETRACE    (OS_MESG_TYPE_BASE+3)
#define OS_MESG_TYPE_COUNTER    (OS_MESG_TYPE_BASE+4)
#define OS_MESG_TYPE_EDMAREAD    (OS_MESG_TYPE_BASE+5)
#define OS_MESG_TYPE_EDMAWRITE    (OS_MESG_TYPE_BASE+6)

/*
 * I/O message priority
 */
#define OS_MESG_PRI_NORMAL    0
#define OS_MESG_PRI_HIGH    1

/*
 * Page size argument for TLB routines
 */
#define OS_PM_4K    0x0000000
#define OS_PM_16K    0x0006000
#define OS_PM_64K    0x001e000
#define OS_PM_256K    0x007e000
#define OS_PM_1M    0x01fe000
#define OS_PM_4M    0x07fe000
#define OS_PM_16M    0x1ffe000

/*
 * Stack size for I/O device managers: PIM (PI Manager), VIM (VI Manager),
 *    SIM (SI Manager)
 *
 */
#define OS_PIM_STACKSIZE    4096
#define OS_VIM_STACKSIZE    4096
#define OS_SIM_STACKSIZE    4096

#define    OS_MIN_STACKSIZE    72

/*
 * Values for osTvType 
 */
#define    OS_TV_PAL        0
#define    OS_TV_NTSC        1
#define    OS_TV_MPAL        2

/*
 * Video Interface (VI) mode type
 */
#define OS_VI_NTSC_LPN1        0    /* NTSC */
#define OS_VI_NTSC_LPF1        1
#define OS_VI_NTSC_LAN1        2
#define OS_VI_NTSC_LAF1        3
#define OS_VI_NTSC_LPN2        4
#define OS_VI_NTSC_LPF2        5
#define OS_VI_NTSC_LAN2        6
#define OS_VI_NTSC_LAF2        7
#define OS_VI_NTSC_HPN1        8
#define OS_VI_NTSC_HPF1        9
#define OS_VI_NTSC_HAN1        10
#define OS_VI_NTSC_HAF1        11
#define OS_VI_NTSC_HPN2        12
#define OS_VI_NTSC_HPF2        13

#define OS_VI_PAL_LPN1        14    /* PAL */
#define OS_VI_PAL_LPF1        15
#define OS_VI_PAL_LAN1        16
#define OS_VI_PAL_LAF1        17
#define OS_VI_PAL_LPN2        18
#define OS_VI_PAL_LPF2        19
#define OS_VI_PAL_LAN2        20
#define OS_VI_PAL_LAF2        21
#define OS_VI_PAL_HPN1        22
#define OS_VI_PAL_HPF1        23
#define OS_VI_PAL_HAN1        24
#define OS_VI_PAL_HAF1        25
#define OS_VI_PAL_HPN2        26
#define OS_VI_PAL_HPF2        27

#define OS_VI_MPAL_LPN1        28    /* MPAL - mainly Brazil */
#define OS_VI_MPAL_LPF1        29
#define OS_VI_MPAL_LAN1        30
#define OS_VI_MPAL_LAF1        31
#define OS_VI_MPAL_LPN2        32
#define OS_VI_MPAL_LPF2        33
#define OS_VI_MPAL_LAN2        34
#define OS_VI_MPAL_LAF2        35
#define OS_VI_MPAL_HPN1        36
#define OS_VI_MPAL_HPF1        37
#define OS_VI_MPAL_HAN1        38
#define OS_VI_MPAL_HAF1        39
#define OS_VI_MPAL_HPN2        40
#define OS_VI_MPAL_HPF2        41

/*
 * Video Interface (VI) special features
 */
#define    OS_VI_GAMMA_ON            0x0001
#define    OS_VI_GAMMA_OFF            0x0002
#define    OS_VI_GAMMA_DITHER_ON        0x0004
#define    OS_VI_GAMMA_DITHER_OFF        0x0008
#define    OS_VI_DIVOT_ON            0x0010
#define    OS_VI_DIVOT_OFF            0x0020
#define    OS_VI_DITHER_FILTER_ON        0x0040
#define    OS_VI_DITHER_FILTER_OFF        0x0080

/*
 * Video Interface (VI) mode attribute bit
 */
#define OS_VI_BIT_NONINTERLACE        0x0001          /* lo-res */
#define OS_VI_BIT_INTERLACE        0x0002          /* lo-res */
#define OS_VI_BIT_NORMALINTERLACE    0x0004          /* hi-res */
#define OS_VI_BIT_DEFLICKINTERLACE    0x0008          /* hi-res */
#define OS_VI_BIT_ANTIALIAS        0x0010
#define OS_VI_BIT_POINTSAMPLE        0x0020
#define OS_VI_BIT_16PIXEL        0x0040
#define OS_VI_BIT_32PIXEL        0x0080
#define OS_VI_BIT_LORES            0x0100
#define OS_VI_BIT_HIRES            0x0200
#define OS_VI_BIT_NTSC            0x0400
#define OS_VI_BIT_PAL            0x0800

/* 
 * Leo Disk 
 */

/* transfer mode */

#define LEO_BLOCK_MODE    1
#define LEO_TRACK_MODE    2
#define LEO_SECTOR_MODE    3

/*
 *  Controllers  number
 */

#ifndef _HW_VERSION_1
#define MAXCONTROLLERS  4
#else
#define MAXCONTROLLERS  6
#endif

/* controller errors */
#define CONT_NO_RESPONSE_ERROR          0x8
#define CONT_OVERRUN_ERROR              0x4
#ifdef _HW_VERSION_1
#define CONT_FRAME_ERROR                0x2
#define CONT_COLLISION_ERROR            0x1
#endif 

/* Controller type */

#define CONT_ABSOLUTE           0x0001
#define CONT_RELATIVE           0x0002
#define CONT_JOYPORT            0x0004
#define CONT_EEPROM        0x8000
#define CONT_EEP16K        0x4000
#define    CONT_TYPE_MASK        0x1f07
#define    CONT_TYPE_NORMAL    0x0005
#define    CONT_TYPE_MOUSE        0x0002

/* Controller status */

#define CONT_CARD_ON            0x01
#define CONT_CARD_PULL          0x02
#define CONT_ADDR_CRC_ER        0x04
#define CONT_EEPROM_BUSY    0x80

/* EEPROM TYPE */

#define EEPROM_TYPE_4K        0x01
#define EEPROM_TYPE_16K        0x02

/* Buttons */

#define CONT_A      0x8000
#define CONT_B      0x4000
#define CONT_G        0x2000
#define CONT_START  0x1000
#define CONT_UP     0x0800
#define CONT_DOWN   0x0400
#define CONT_LEFT   0x0200
#define CONT_RIGHT  0x0100
#define CONT_L      0x0020
#define CONT_R      0x0010
#define CONT_E      0x0008
#define CONT_D      0x0004
#define CONT_C      0x0002
#define CONT_F      0x0001

/* Nintendo's official button names */

#define A_BUTTON    CONT_A
#define B_BUTTON    CONT_B
#define L_TRIG        CONT_L
#define R_TRIG        CONT_R
#define Z_TRIG        CONT_G
#define START_BUTTON    CONT_START
#define U_JPAD        CONT_UP
#define L_JPAD        CONT_LEFT
#define R_JPAD        CONT_RIGHT
#define D_JPAD        CONT_DOWN
#define U_CBUTTONS    CONT_E
#define L_CBUTTONS    CONT_C
#define R_CBUTTONS    CONT_F
#define D_CBUTTONS    CONT_D

/* File System size */
#define    OS_PFS_VERSION        0x0200
#define    OS_PFS_VERSION_HI    (OS_PFS_VERSION >> 8)
#define    OS_PFS_VERSION_LO    (OS_PFS_VERSION & 255)

#define PFS_FILE_NAME_LEN       16
#define PFS_FILE_EXT_LEN        4
#define BLOCKSIZE        32        /* bytes */
#define PFS_ONE_PAGE            8        /* blocks */
#define PFS_MAX_BANKS        62

/* File System flag */

#define PFS_READ                0
#define PFS_WRITE               1
#define PFS_CREATE              2

/* File System status */
#define PFS_INITIALIZED        0x1
#define PFS_CORRUPTED        0x2        /* File system was corrupted */

/* File System error number */

#define PFS_ERR_NOPACK        1    /* no memory card is plugged or   */
#define PFS_ERR_NEW_PACK        2    /* ram pack has been changed to a */
                    /* different one           */
#define PFS_ERR_INCONSISTENT    3    /* need to run Pfschecker       */
#define PFS_ERR_CONTRFAIL    CONT_OVERRUN_ERROR              
#define PFS_ERR_INVALID         5    /* invalid parameter or file not exist*/
#define PFS_ERR_BAD_DATA        6       /* the data read from pack are bad*/
#define PFS_DATA_FULL           7    /* no free pages on ram pack      */
#define PFS_DIR_FULL            8    /* no free directories on ram pack*/
#define PFS_ERR_EXIST        9    /* file exists               */
#define PFS_ERR_ID_FATAL    10    /* dead ram pack */
#define PFS_ERR_DEVICE        11    /* wrong device type*/

/* definition for EEPROM */

#define EEPROM_MAXBLOCKS    64
#define EEP16K_MAXBLOCKS    256
#define EEPROM_BLOCK_SIZE    8

/*
 * PI/EPI
 */
#define PI_DOMAIN1      0
#define PI_DOMAIN2      1

/*
 * Profiler constants
 */
#define PROF_MIN_INTERVAL    50    /* microseconds */

/*
 * Boot addresses
 */
#define    BOOT_ADDRESS_ULTRA    0x80000400
#define    BOOT_ADDRESS_COSIM    0x80002000
#define    BOOT_ADDRESS_EMU    0x20010000
#define    BOOT_ADDRESS_INDY     0x88100000

/*
 * Size of buffer the retains contents after NMI
 */
#define OS_APP_NMI_BUFSIZE    64

#if defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS)

/**************************************************************************
 *
 * Macro definitions
 *
 */

/* PARTNER-N64 */
#ifdef PTN64
#define osReadHost osReadHost_pt
#define osWriteHost osWriteHost_pt
#endif

/* Get count of valid messages in queue */
#define MQ_GET_COUNT(mq)        ((mq)->validCount)

/* Figure out if message queue is empty or full */
#define MQ_IS_EMPTY(mq)        (MQ_GET_COUNT(mq) == 0)
#define MQ_IS_FULL(mq)        (MQ_GET_COUNT(mq) >= (mq)->msgCount)

/*
 * CPU counter increments at 3/4 of bus clock rate:
 *
 * Bus Clock    Proc Clock    Counter (1/2 Proc Clock)
 * ---------    ----------    ------------------------
 * 62.5 Mhz    93.75 Mhz    46.875 Mhz
 */
extern u64 osClockRate;

#define    OS_CLOCK_RATE        62500000LL
#define    OS_CPU_COUNTER        (OS_CLOCK_RATE*3/4)
#define OS_NSEC_TO_CYCLES(n)    (((u64)(n)*(OS_CPU_COUNTER/15625000LL))/(1000000000LL/15625000LL))
#define OS_USEC_TO_CYCLES(n)    (((u64)(n)*(OS_CPU_COUNTER/15625LL))/(1000000LL/15625LL))
#define OS_CYCLES_TO_NSEC(c)    (((u64)(c)*(1000000000LL/15625000LL))/(OS_CPU_COUNTER/15625000LL))
#define OS_CYCLES_TO_USEC(c)    (((u64)(c)*(1000000LL/15625LL))/(OS_CPU_COUNTER/15625LL))

/**************************************************************************
 *
 * Extern variables
 *
 */
extern OSViMode    osViModeTable[];    /* Global VI mode table */

extern OSViMode    osViModeNtscLpn1;    /* Individual VI NTSC modes */
extern OSViMode    osViModeNtscLpf1;
extern OSViMode    osViModeNtscLan1;
extern OSViMode    osViModeNtscLaf1;
extern OSViMode    osViModeNtscLpn2;
extern OSViMode    osViModeNtscLpf2;
extern OSViMode    osViModeNtscLan2;
extern OSViMode    osViModeNtscLaf2;
extern OSViMode    osViModeNtscHpn1;
extern OSViMode    osViModeNtscHpf1;
extern OSViMode    osViModeNtscHan1;
extern OSViMode    osViModeNtscHaf1;
extern OSViMode    osViModeNtscHpn2;
extern OSViMode    osViModeNtscHpf2;

extern OSViMode    osViModePalLpn1;    /* Individual VI PAL modes */
extern OSViMode    osViModePalLpf1;
extern OSViMode    osViModePalLan1;
extern OSViMode    osViModePalLaf1;
extern OSViMode    osViModePalLpn2;
extern OSViMode    osViModePalLpf2;
extern OSViMode    osViModePalLan2;
extern OSViMode    osViModePalLaf2;
extern OSViMode    osViModePalHpn1;
extern OSViMode    osViModePalHpf1;
extern OSViMode    osViModePalHan1;
extern OSViMode    osViModePalHaf1;
extern OSViMode    osViModePalHpn2;
extern OSViMode    osViModePalHpf2;

extern OSViMode    osViModeMpalLpn1;    /* Individual VI MPAL modes */
extern OSViMode    osViModeMpalLpf1;
extern OSViMode    osViModeMpalLan1;
extern OSViMode    osViModeMpalLaf1;
extern OSViMode    osViModeMpalLpn2;
extern OSViMode    osViModeMpalLpf2;
extern OSViMode    osViModeMpalLan2;
extern OSViMode    osViModeMpalLaf2;
extern OSViMode    osViModeMpalHpn1;
extern OSViMode    osViModeMpalHpf1;
extern OSViMode    osViModeMpalHan1;
extern OSViMode    osViModeMpalHaf1;
extern OSViMode    osViModeMpalHpn2;
extern OSViMode    osViModeMpalHpf2;

extern s32     osRomType;    /* Bulk or cartridge ROM. 0=cartridge 1=bulk */
extern u32  osRomBase;    /* Rom base address of the game image */
extern u32     osTvType;    /* 0 = PAL, 1 = NTSC, 2 = MPAL */
extern u32     osResetType;    /* 0 = cold reset, 1 = NMI */
extern s32     osCicId;
extern s32     osVersion;
extern u32    osMemSize;    /* Memory Size */
extern s32    osAppNMIBuffer[];

extern OSIntMask __OSGlobalIntMask;    /* global interrupt mask */
extern OSPiHandle      *__osPiTable;    /* The head of OSPiHandle link list */
extern OSPiHandle      *__osDiskHandle; /* For exceptasm to get disk info*/



/**************************************************************************
 *
 * Function prototypes
 *
 */

/* Thread operations */

extern void        osCreateThread(OSThread *, OSId, void (*)(void *),
                       void *, void *, OSPri);
extern void        osDestroyThread(OSThread *);
extern void        osYieldThread(void);
extern void        osStartThread(OSThread *);
extern void        osStopThread(OSThread *);
extern OSId        osGetThreadId(OSThread *);
extern void        osSetThreadPri(OSThread *, OSPri);
extern OSPri        osGetThreadPri(OSThread *);

/* Message operations */

extern void        osCreateMesgQueue(OSMesgQueue *, OSMesg *, s32);
extern s32        osSendMesg(OSMesgQueue *, OSMesg, s32);
extern s32        osJamMesg(OSMesgQueue *, OSMesg, s32);
extern s32        osRecvMesg(OSMesgQueue *, OSMesg *, s32);

/* Event operations */

extern void        osSetEventMesg(OSEvent, OSMesgQueue *, OSMesg);

/* Interrupt operations */

extern OSIntMask    osGetIntMask(void);
extern OSIntMask    osSetIntMask(OSIntMask);

/* RDB port operations */

extern void             osInitRdb(u8 *sendBuf, u32 sendSize);

/* Cache operations and macros */

extern void        osInvalDCache(void *, size_t);
extern void        osInvalICache(void *, size_t);
extern void        osWritebackDCache(void *, size_t);
extern void        osWritebackDCacheAll(void);

#define    OS_DCACHE_ROUNDUP_ADDR(x)    (void *)(((((u32)(x)+0xf)/0x10)*0x10))
#define    OS_DCACHE_ROUNDUP_SIZE(x)    (u32)(((((u32)(x)+0xf)/0x10)*0x10))

/* TLB management routines */

extern void        osMapTLB(s32, OSPageMask, void *, u32, u32, s32);
extern void        osMapTLBRdb(void);
extern void        osUnmapTLB(s32);
extern void        osUnmapTLBAll(void);
extern void        osSetTLBASID(s32);

/* Address translation routines and macros */

extern u32         osVirtualToPhysical(void *);
extern void *         osPhysicalToVirtual(u32);

#define    OS_K0_TO_PHYSICAL(x)    (u32)(((char *)(x)-0x80000000))
#define    OS_K1_TO_PHYSICAL(x)    (u32)(((char *)(x)-0xa0000000))

#define    OS_PHYSICAL_TO_K0(x)    (void *)(((u32)(x)+0x80000000))
#define    OS_PHYSICAL_TO_K1(x)    (void *)(((u32)(x)+0xa0000000))

/* I/O operations */

/* Audio interface (Ai) */
extern u32         osAiGetStatus(void);
extern u32         osAiGetLength(void);
extern s32        osAiSetFrequency(u32);
extern s32        osAiSetNextBuffer(void *, u32);

/* Display processor interface (Dp) */
extern u32         osDpGetStatus(void);
extern void        osDpSetStatus(u32);
extern void         osDpGetCounters(u32 *);
extern s32        osDpSetNextBuffer(void *, u64);

/* Peripheral interface (Pi) */
extern u32         osPiGetStatus(void);
extern s32        osPiGetDeviceType(void);
extern s32        osPiRawWriteIo(u32, u32);
extern s32        osPiRawReadIo(u32, u32 *);
extern s32        osPiRawStartDma(s32, u32, void *, u32);
extern s32        osPiWriteIo(u32, u32);
extern s32        osPiReadIo(u32, u32 *);
extern s32        osPiStartDma(OSIoMesg *, s32, s32, u32, void *, u32,
                     OSMesgQueue *);
extern void        osCreatePiManager(OSPri, OSMesgQueue *, OSMesg *, s32);

/* Video interface (Vi) */
extern u32        osViGetStatus(void);
extern u32        osViGetCurrentMode(void);
extern u32        osViGetCurrentLine(void);
extern u32        osViGetCurrentField(void);
extern void        *osViGetCurrentFramebuffer(void);
extern void        *osViGetNextFramebuffer(void);
extern void        osViSetXScale(f32);
extern void        osViSetYScale(f32);
extern void        osViSetSpecialFeatures(u32);
extern void        osViSetMode(OSViMode *);
extern void        osViSetEvent(OSMesgQueue *, OSMesg, u32);
extern void        osViSwapBuffer(void *);
extern void        osViBlack(u8);
extern void        osViFade(u8, u16);
extern void        osViRepeatLine(u8);
extern void        osCreateViManager(OSPri);

/* Timer interface */

extern OSTime        osGetTime(void);
extern void        osSetTime(OSTime);
extern u32        osSetTimer(OSTimer *, OSTime, OSTime,
                   OSMesgQueue *, OSMesg);
extern int        osStopTimer(OSTimer *);

/* Controller interface */

extern s32        osContInit(OSMesgQueue *, u8 *, OSContStatus *);
extern s32        osContReset(OSMesgQueue *, OSContStatus *);
extern s32        osContStartQuery(OSMesgQueue *);
extern s32        osContStartReadData(OSMesgQueue *);
#ifndef _HW_VERSION_1
extern s32        osContSetCh(u8);
#endif
extern void        osContGetQuery(OSContStatus *);
extern void        osContGetReadData(OSContPad *);

/* file system interface */

extern s32 osPfsInitPak(OSMesgQueue *, OSPfs *, int);
extern s32 osPfsRepairId(OSPfs *);
extern s32 osPfsInit(OSMesgQueue *, OSPfs *, int);
extern s32 osPfsReFormat(OSPfs *, OSMesgQueue *, int);
extern s32 osPfsChecker(OSPfs *);
extern s32 osPfsAllocateFile(OSPfs *, u16, u32, u8 *, u8 *, int, s32 *);
extern s32 osPfsFindFile(OSPfs *, u16, u32, u8 *, u8 *, s32 *);
extern s32 osPfsDeleteFile(OSPfs *, u16, u32, u8 *, u8 *);
extern s32 osPfsReadWriteFile(OSPfs *, s32, u8, int, int, u8 *);
extern s32 osPfsFileState(OSPfs *, s32, OSPfsState *);
extern s32 osPfsGetLabel(OSPfs *, u8 *, int *);
extern s32 osPfsSetLabel(OSPfs *, u8 *);
extern s32 osPfsIsPlug(OSMesgQueue *, u8 *);
extern s32 osPfsFreeBlocks(OSPfs *, s32 *);
extern s32 osPfsNumFiles(OSPfs *, s32 *, s32 *);

/* EEPROM interface */

extern s32 osEepromProbe(OSMesgQueue *);
extern s32 osEepromRead(OSMesgQueue *, u8, u8 *);
extern s32 osEepromWrite(OSMesgQueue *, u8, u8 *);
extern s32 osEepromLongRead(OSMesgQueue *, u8, u8 *, int);
extern s32 osEepromLongWrite(OSMesgQueue *, u8, u8 *, int);

/* MOTOR interface */

extern s32 osMotorInit(OSMesgQueue *, OSPfs *, int);
extern s32 osMotorStop(OSPfs *);
extern s32 osMotorStart(OSPfs *);

/* Enhanced PI interface */

extern OSPiHandle *osCartRomInit(void);
extern OSPiHandle *osLeoDiskInit(void);
extern OSPiHandle *osDriveRomInit(void);

extern s32 osEPiDeviceType(OSPiHandle *, OSPiInfo *);
extern s32 osEPiRawWriteIo(OSPiHandle *, u32 , u32);
extern s32 osEPiRawReadIo(OSPiHandle *, u32 , u32 *);
extern s32 osEPiRawStartDma(OSPiHandle *, s32 , u32 , void *, u32 );
extern s32 osEPiWriteIo(OSPiHandle *, u32 , u32 );
extern s32 osEPiReadIo(OSPiHandle *, u32 , u32 *);
extern s32 osEPiStartDma(OSPiHandle *, OSIoMesg *, s32);
extern s32 osEPiLinkHandle(OSPiHandle *);

/* Profiler Interface */

extern void        osProfileInit(OSProf *, u32 profcnt);
extern void        osProfileStart(u32);
extern void        osProfileFlush(void);
extern void        osProfileStop(void);

/* Game <> Host data transfer functions */

extern s32        osTestHost(void);
extern void        osReadHost(void *, u32);
extern void        osWriteHost(void *, u32);
extern void        osAckRamromRead(void);
extern void        osAckRamromWrite(void);


/* byte string operations */

extern void     bcopy(const void *, void *, size_t);
extern int      bcmp(const void *, const void *, int);
extern void     bzero(void *, size_t);

/* Miscellaneous operations */

extern void        osInitialize(void);
extern u32        osGetCount(void);
extern void        osExit(void);
extern u32         osGetMemSize(void);

/* Printf */

extern int        sprintf(char *s, const char *fmt, ...);
extern void        osSyncPrintf(const char *fmt, ...);
extern void        osAsyncPrintf(const char *fmt, ...);
extern int        osSyncGetChars(char *buf);
extern int        osAsyncGetChars(char *buf);

#endif  /* defined(_LANGUAGE_C) || defined(_LANGUAGE_C_PLUS_PLUS) */

#ifdef _LANGUAGE_C_PLUS_PLUS
}
#endif

#endif /* !_OS_H */
