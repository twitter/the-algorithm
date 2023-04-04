#ifndef _CONTROLLER_H
#define _CONTROLLER_H
#include "PR/os_internal.h"
#include "PR/os.h"
#include "PR/rcp.h"

//should go somewhere else but
#define ARRLEN(x) ((s32)(sizeof(x) / sizeof(x[0])))
#define CHNL_ERR(format) ((format.rxsize & CHNL_ERR_MASK) >> 4)

typedef struct
{
    /* 0x0 */ u32 ramarray[15];
    /* 0x3C */ u32 pifstatus;
} OSPifRam;

typedef struct
{
    /* 0x0 */ u8 dummy;
    /* 0x1 */ u8 txsize;
    /* 0x2 */ u8 rxsize;
    /* 0x3 */ u8 cmd;
    /* 0x4 */ u16 button;
    /* 0x6 */ s8 stick_x;
    /* 0x7 */ s8 stick_y;
} __OSContReadFormat;

typedef struct
{
    /* 0x0 */ u8 dummy;
    /* 0x1 */ u8 txsize;
    /* 0x2 */ u8 rxsize;
    /* 0x3 */ u8 cmd;
    /* 0x4 */ u8 typeh;
    /* 0x5 */ u8 typel;
    /* 0x6 */ u8 status;
    /* 0x7 */ u8 dummy1;
} __OSContRequesFormat;

typedef struct
{
    /* 0x0 */ u8 txsize;
    /* 0x1 */ u8 rxsize;
    /* 0x2 */ u8 cmd;
    /* 0x3 */ u8 typeh;
    /* 0x4 */ u8 typel;
    /* 0x5 */ u8 status;
} __OSContRequesFormatShort;

typedef struct
{
    /* 0x0 */ u8 dummy;
    /* 0x1 */ u8 txsize;
    /* 0x2 */ u8 rxsize;
    /* 0x3 */ u8 cmd;
    /* 0x4 */ u16 address;
    /* 0x6 */ u8 data[BLOCKSIZE];
    /* 0x26 */ u8 datacrc;
} __OSContRamReadFormat;

typedef union {
    /* 0x0 */ struct
    {
        /* 0x0 */ u8 bank;
        /* 0x1 */ u8 page;
    } inode_t;
    /* 0x0 */ u16 ipage;
} __OSInodeUnit;

typedef struct
{
    /* 0x0 */ u32 game_code;
    /* 0x4 */ u16 company_code;
    /* 0x6 */ __OSInodeUnit start_page;
    /* 0x8 */ u8 status;
    /* 0x9 */ s8 reserved;
    /* 0xA */ u16 data_sum;
    /* 0xC */ u8 ext_name[PFS_FILE_EXT_LEN];
    /* 0x10 */ u8 game_name[PFS_FILE_NAME_LEN];
} __OSDir;

typedef struct
{
    /* 0x0 */ __OSInodeUnit inode_page[128];
} __OSInode;

typedef struct
{
    /* 0x0 */ u32 repaired;
    /* 0x4 */ u32 random;
    /* 0x8 */ u64 serial_mid;
    /* 0x10 */ u64 serial_low;
    /* 0x18 */ u16 deviceid;
    /* 0x1A */ u8 banks;
    /* 0x1B */ u8 version;
    /* 0x1C */ u16 checksum;
    /* 0x1E */ u16 inverted_checksum;
} __OSPackId;

typedef struct
{
    /* 0x0 */ u8 txsize;
    /* 0x1 */ u8 rxsize;
    /* 0x2 */ u8 cmd;
    /* 0x3 */ u8 address;
    /* 0x4 */ u8 data[EEPROM_BLOCK_SIZE];
} __OSContEepromFormat;

//from: http://en64.shoutwiki.com/wiki/SI_Registers_Detailed#CONT_CMD_Usage
#define CONT_CMD_REQUEST_STATUS 0
#define CONT_CMD_READ_BUTTON 1
#define CONT_CMD_READ_MEMPACK 2
#define CONT_CMD_WRITE_MEMPACK 3
#define CONT_CMD_READ_EEPROM 4
#define CONT_CMD_WRITE_EEPROM 5
#define CONT_CMD_RESET 0xff

#define CONT_CMD_REQUEST_STATUS_TX 1
#define CONT_CMD_READ_BUTTON_TX 1
#define CONT_CMD_READ_MEMPACK_TX 3
#define CONT_CMD_WRITE_MEMPACK_TX 35
#define CONT_CMD_READ_EEPROM_TX 2
#define CONT_CMD_WRITE_EEPROM_TX 10
#define CONT_CMD_RESET_TX 1

#define CONT_CMD_REQUEST_STATUS_RX 3
#define CONT_CMD_READ_BUTTON_RX 4
#define CONT_CMD_READ_MEMPACK_RX 33
#define CONT_CMD_WRITE_MEMPACK_RX 1
#define CONT_CMD_READ_EEPROM_RX 8
#define CONT_CMD_WRITE_EEPROM_RX 1
#define CONT_CMD_RESET_RX 3

#define CONT_CMD_NOP 0xff
#define CONT_CMD_END 0xfe //indicates end of a command
#define CONT_CMD_EXE 1    //set pif ram status byte to this to do a command

#define DIR_STATUS_EMPTY 0
#define DIR_STATUS_UNKNOWN 1
#define DIR_STATUS_OCCUPIED 2


typedef struct
{
    /* 0x0 */ __OSInode inode;
    /* 0x100 */ u8 bank;
    /* 0x101 */ u8 map[256];
} __OSInodeCache;

extern s32 __osEepStatus(OSMesgQueue *, OSContStatus *);
u16 __osSumcalc(u8 *ptr, int length);
s32 __osIdCheckSum(u16 *ptr, u16 *csum, u16 *icsum);
s32 __osRepairPackId(OSPfs *pfs, __OSPackId *badid, __OSPackId *newid);
s32 __osCheckPackId(OSPfs *pfs, __OSPackId *temp);
s32 __osGetId(OSPfs *pfs);
s32 __osCheckId(OSPfs *pfs);
s32 __osPfsRWInode(OSPfs *pfs, __OSInode *inode, u8 flag, u8 bank);
s32 __osPfsSelectBank(OSPfs *pfs);
s32 __osPfsDeclearPage(OSPfs *pfs, __OSInode *inode, int file_size_in_pages, int *first_page, u8 bank, int *decleared, int *last_page);
s32 __osPfsReleasePages(OSPfs *pfs, __OSInode *inode, u8 start_page, u16 *sum, u8 bank, __OSInodeUnit *last_page, int flag);
s32 __osBlockSum(OSPfs *pfs, u8 page_no, u16 *sum, u8 bank);
s32 __osContRamRead(OSMesgQueue *mq, int channel, u16 address, u8 *buffer);
s32 __osContRamWrite(OSMesgQueue *mq, int channel, u16 address, u8 *buffer, int force);
void __osContGetInitData(u8 *pattern, OSContStatus *data);
void __osPackRequestData(u8 cmd);
void __osPfsRequestData(u8 cmd);
void __osPfsGetInitData(u8* pattern, OSContStatus* data);
u8 __osContAddressCrc(u16 addr);
u8 __osContDataCrc(u8 *data);
s32 __osPfsGetStatus(OSMesgQueue *queue, int channel);

extern u8 _osLastSentSiCmd;
extern OSTimer __osEepromTimer;
extern OSMesg __osEepromTimerMsg;
extern OSMesgQueue __osEepromTimerQ;
extern OSPifRam __osEepPifRam;
extern OSPifRam __osContPifRam;
extern OSPifRam __osPfsPifRam;
extern u8 _osContNumControllers;

//some version of this almost certainly existed since there's plenty of times where it's used right before a return 0
#define ERRCK(fn) \
    ret = fn;     \
    if (ret != 0) \
        return ret;

#define SET_ACTIVEBANK_TO_ZERO        \
    if (pfs->activebank != 0)         \
    {                                 \
        pfs->activebank = 0;          \
        ERRCK(__osPfsSelectBank(pfs)) \
    }

#define PFS_CHECK_ID                              \
    if (__osCheckId(pfs) == PFS_ERR_NEW_PACK) \
        return PFS_ERR_NEW_PACK;
#endif

#define PFS_CHECK_STATUS                          \
    if ((pfs->status & PFS_INITIALIZED) == 0) \
        return PFS_ERR_INVALID;

#define PFS_GET_STATUS                      \
    __osSiGetAccess();                      \
    ret = __osPfsGetStatus(queue, channel); \
    __osSiRelAccess();                      \
    if (ret != 0)                           \
        return ret;
