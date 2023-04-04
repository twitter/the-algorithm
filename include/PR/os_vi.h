#ifndef _ULTRA64_VI_H_
#define _ULTRA64_VI_H_

#include <PR/ultratypes.h>
#include <PR/os_message.h>

/* Ultra64 Video Interface */


/* Special Features */
#define OS_VI_GAMMA_ON 0x0001
#define OS_VI_GAMMA_OFF 0x0002
#define OS_VI_GAMMA_DITHER_ON 0x0004
#define OS_VI_GAMMA_DITHER_OFF 0x0008
#define OS_VI_DIVOT_ON 0x0010
#define OS_VI_DIVOT_OFF 0x0020
#define OS_VI_DITHER_FILTER_ON 0x0040
#define OS_VI_DITHER_FILTER_OFF 0x0080

#define OS_VI_GAMMA 0x08
#define OS_VI_GAMMA_DITHER 0x04
#define OS_VI_DIVOT 0x10
#define OS_VI_DITHER_FILTER 0x10000
#define OS_VI_UNK200 0x200
#define OS_VI_UNK100 0x100


/* Types */

typedef struct
{
    u32 ctrl;
    u32 width;
    u32 burst;
    u32 vSync;
    u32 hSync;
    u32 leap;
    u32 hStart;
    u32 xScale;
    u32 vCurrent;
} OSViCommonRegs;

typedef struct
{
    u32 origin;
    u32 yScale;
    u32 vStart;
    u32 vBurst;
    u32 vIntr;
} OSViFieldRegs;

typedef struct
{
    u8 type;
    OSViCommonRegs comRegs;
    OSViFieldRegs fldRegs[2];
} OSViMode;

typedef struct
{
    /* 0x00 */ u16 unk00; //some kind of flags.  swap buffer sets to 0x10
    /* 0x02 */ u16 retraceCount;
    /* 0x04 */ void* buffer;
    /* 0x08 */ OSViMode *modep;
    /* 0x0c */ u32 features;
    /* 0x10 */ OSMesgQueue *mq;
    /* 0x14 */ OSMesg *msg;
    /* 0x18 */ u32 unk18;
    /* 0x1c */ u32 unk1c;
    /* 0x20 */ u32 unk20;
    /* 0x24 */ f32 unk24;
    /* 0x28 */ u16 unk28;
    /* 0x2c */ u32 unk2c;
} OSViContext;

void osCreateViManager(OSPri pri);
void osViSetMode(OSViMode *mode);
void osViSetEvent(OSMesgQueue *mq, OSMesg msg, u32 retraceCount);
void osViBlack(u8 active);
void osViSetSpecialFeatures(u32 func);
void osViSwapBuffer(void *vaddr);


#define OS_VI_NTSC_LPN1		0	/* NTSC */
#define OS_VI_NTSC_LPF1		1
#define OS_VI_NTSC_LAN1		2
#define OS_VI_NTSC_LAF1		3
#define OS_VI_NTSC_LPN2		4
#define OS_VI_NTSC_LPF2		5
#define OS_VI_NTSC_LAN2		6
#define OS_VI_NTSC_LAF2		7
#define OS_VI_NTSC_HPN1		8
#define OS_VI_NTSC_HPF1		9
#define OS_VI_NTSC_HAN1		10
#define OS_VI_NTSC_HAF1		11
#define OS_VI_NTSC_HPN2		12
#define OS_VI_NTSC_HPF2		13

#define OS_VI_PAL_LPN1		14	/* PAL */
#define OS_VI_PAL_LPF1		15
#define OS_VI_PAL_LAN1		16
#define OS_VI_PAL_LAF1		17
#define OS_VI_PAL_LPN2		18
#define OS_VI_PAL_LPF2		19
#define OS_VI_PAL_LAN2		20
#define OS_VI_PAL_LAF2		21
#define OS_VI_PAL_HPN1		22
#define OS_VI_PAL_HPF1		23
#define OS_VI_PAL_HAN1		24
#define OS_VI_PAL_HAF1		25
#define OS_VI_PAL_HPN2		26
#define OS_VI_PAL_HPF2		27

extern OSViMode	osViModeTable[];	/* Global VI mode table */


#endif
