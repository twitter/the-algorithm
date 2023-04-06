#ifndef _LIBAUDIO_INTERNAL_H_
#define _LIBAUDIO_INTERNAL_H_
#include <ultra64.h>
#define AL_BANK_VERSION 0x4231 /* 'B1' */

typedef u8 ALPan;
typedef s32 ALMicroTime;

/* Possible wavetable types */
enum
{
    AL_ADPCM_WAVE = 0,
    AL_RAW16_WAVE
};

typedef struct
{
    u32 start;
    u32 end;
    u32 count;
} ALRawLoop;

typedef struct
{
    u32 start;
    u32 end;
    u32 count;
    ADPCM_STATE state;
} ALADPCMloop;

typedef struct
{
    s32 order;
    s32 npredictors;
    s16 book[1]; // variable size, 8-byte aligned
} ALADPCMBook;

typedef struct
{
    ALMicroTime attackTime;
    ALMicroTime decayTime;
    ALMicroTime releaseTime;
    u8 attackVolume;
    u8 decayVolume;
} ALEnvelope;

typedef struct
{
    u8 velocityMin;
    u8 velocityMax;
    u8 keyMin;
    u8 keyMax;
    u8 keyBase;
    s8 detune;
} ALKeyMap;

typedef struct
{
    ALADPCMloop *loop;
    ALADPCMBook *book;
} ALADPCMWaveInfo;

typedef struct
{
    ALRawLoop *loop;
} ALRAWWaveInfo;

typedef struct ALWaveTable_s
{
    u8 *base; /* ptr to start of wave data    */
    s32 len;  /* length of data in bytes      */
    u8 type;  /* compression type             */
    u8 flags; /* offset/address flags         */
    union {
        ALADPCMWaveInfo adpcmWave;
        ALRAWWaveInfo rawWave;
    } waveInfo;
} ALWaveTable;

typedef struct ALSound_s
{
    ALEnvelope *envelope;
    ALKeyMap *keyMap;
    ALWaveTable *wavetable; /* offset to wavetable struct           */
    ALPan samplePan;
    u8 sampleVolume;
    u8 flags;
} ALSound;

typedef struct
{
    u8 volume;   /* overall volume for this instrument   */
    ALPan pan;   /* 0 = hard left, 127 = hard right      */
    u8 priority; /* voice priority for this instrument   */
    u8 flags;
    u8 tremType;    /* the type of tremelo osc. to use      */
    u8 tremRate;    /* the rate of the tremelo osc.         */
    u8 tremDepth;   /* the depth of the tremelo osc         */
    u8 tremDelay;   /* the delay for the tremelo osc        */
    u8 vibType;     /* the type of tremelo osc. to use      */
    u8 vibRate;     /* the rate of the tremelo osc.         */
    u8 vibDepth;    /* the depth of the tremelo osc         */
    u8 vibDelay;    /* the delay for the tremelo osc        */
    s16 bendRange;  /* pitch bend range in cents            */
    s16 soundCount; /* number of sounds in this array       */
    ALSound *soundArray[1];
} ALInstrument;

typedef struct ALBank_s
{
    s16 instCount; /* number of programs in this bank */
    u8 flags;
    u8 pad;
    s32 sampleRate;             /* e.g. 44100, 22050, etc...       */
    ALInstrument *percussion;   /* default percussion for GM       */
    ALInstrument *instArray[1]; /* ARRAY of instruments            */
} ALBank;

typedef struct
{                         /* Note: sizeof won't be correct        */
    s16 revision;         /* format revision of this file         */
    s16 bankCount;        /* number of banks                      */
    ALBank *bankArray[1]; /* ARRAY of bank offsets                */
} ALBankFile;

void alBnkfNew(ALBankFile *f, u8 *table);
#endif
