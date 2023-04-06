#ifndef AUDIO_HEAP_H
#define AUDIO_HEAP_H

#include "internal.h"

#define SOUND_LOAD_STATUS_NOT_LOADED     0
#define SOUND_LOAD_STATUS_IN_PROGRESS    1
#define SOUND_LOAD_STATUS_COMPLETE       2
#define SOUND_LOAD_STATUS_DISCARDABLE    3

#define IS_BANK_LOAD_COMPLETE(bankId) (gBankLoadStatus[bankId] >= SOUND_LOAD_STATUS_COMPLETE)
#define IS_SEQ_LOAD_COMPLETE(seqId) (gSeqLoadStatus[seqId] >= SOUND_LOAD_STATUS_COMPLETE)

struct SoundAllocPool
{
    u8 *start;
    u8 *cur;
    u32 size;
    s32 unused; // set to 0, never read
}; // size = 0x10

struct SeqOrBankEntry {
    u8 *ptr;
    u32 size;
    s32 id; // seqId or bankId
}; // size = 0xC

struct PersistentPool
{
    /*0x00*/ u32 numEntries;
    /*0x04*/ struct SoundAllocPool pool;
    /*0x14*/ struct SeqOrBankEntry entries[32];
}; // size = 0x194

struct TemporaryPool
{
    /*0x00*/ u32 nextSide;
    /*0x04*/ struct SoundAllocPool pool;
    /*0x14*/ struct SeqOrBankEntry entries[2];
}; // size = 0x2C

struct SoundMultiPool
{
    /*0x000*/ struct PersistentPool persistent;
    /*0x194*/ struct TemporaryPool temporary;
    /*     */ u32 pad2[4];
}; // size = 0x1D0

extern u8 gAudioHeap[];
extern s16 gVolume;
extern s8 gReverbDownsampleRate;
extern struct SoundAllocPool gAudioInitPool;
extern struct SoundAllocPool gNotesAndBuffersPool;
extern struct SoundMultiPool gSeqLoadedPool;
extern struct SoundMultiPool gBankLoadedPool;
extern u8 gBankLoadStatus[64];
extern u8 gSeqLoadStatus[256];
extern volatile u8 gAudioResetStatus;
extern u8 gAudioResetPresetIdToLoad;

void *soundAlloc(struct SoundAllocPool *pool, u32 size);
void sound_init_main_pools(s32 sizeForAudioInitPool);
void *alloc_bank_or_seq(struct SoundMultiPool *arg0, s32 arg1, s32 size, s32 arg3, s32 id);
void *get_bank_or_seq(struct SoundMultiPool *arg0, s32 arg1, s32 arg2);
#ifdef VERSION_EU
s32 audio_shut_down_and_reset_step(void);
void audio_reset_session(void);
#else
void audio_reset_session(struct AudioSessionSettings *preset);
#endif

#endif /* AUDIO_HEAP_H */
