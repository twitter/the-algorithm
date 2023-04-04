#ifndef _ULTRA64_LIBAUDIO_H_
#define _ULTRA64_LIBAUDIO_H_

#include "abi.h"

typedef struct
{
    u8 *offset;
    s32 len;
#ifdef VERSION_SH
    s8 medium;
    s8 magic; // tbl: 0x04, otherwise: 0x03

    // for ctl (else zeros):
    union {
        // unused, just for clarification (big endian)
        struct {
            u8 bank;
            u8 ff;
            u8 numInstruments;
            u8 numDrums;
        } as_u8;

        // used
        struct {
            s16 bankAndFf;
            s16 numInstrumentsAndDrums;
        } as_s16;
    } ctl;
#endif
} ALSeqData;

typedef struct
{
#ifndef VERSION_SH
    s16 revision;
#endif
    s16 seqCount;
#ifdef VERSION_SH
    s16 unk2;
    u8 *data;
#if !IS_64_BIT
    s32 pad[2];
#endif
#endif
    ALSeqData seqArray[1];
} ALSeqFile;

void alSeqFileNew(ALSeqFile *f, u8 *base);

#endif
