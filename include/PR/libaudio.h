#ifndef _ULTRA64_LIBAUDIO_H_
#define _ULTRA64_LIBAUDIO_H_

#include "abi.h"

typedef struct
{
    u8 *offset;
    s32 len;
} ALSeqData;

typedef struct
{
    s16 revision;
    s16 seqCount;
    ALSeqData seqArray[1];
} ALSeqFile;

void alSeqFileNew(ALSeqFile *f, u8 *base);

#endif
