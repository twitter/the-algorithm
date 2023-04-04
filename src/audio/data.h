#ifndef AUDIO_DATA_H
#define AUDIO_DATA_H

#include <PR/ultratypes.h>

#include "internal.h"
#include "types.h"

#define AUDIO_LOCK_UNINITIALIZED 0
#define AUDIO_LOCK_NOT_LOADING 0x76557364
#define AUDIO_LOCK_LOADING 0x19710515

#define NUMAIBUFFERS 3

// constant .data
#if defined(VERSION_EU) || defined(VERSION_SH)
extern struct AudioSessionSettingsEU gAudioSessionPresets[];
#else
extern struct AudioSessionSettings gAudioSessionPresets[18];
#endif
extern u16 D_80332388[128]; // unused

#if defined(VERSION_EU) || defined(VERSION_SH)
extern f32 gPitchBendFrequencyScale[256];
#else
extern f32 gPitchBendFrequencyScale[255];
#endif
extern f32 gNoteFrequencies[128];

extern u8 gDefaultShortNoteVelocityTable[16];
extern u8 gDefaultShortNoteDurationTable[16];
extern s8 gVibratoCurve[16];
extern struct AdsrEnvelope gDefaultEnvelope[3];

#if defined(VERSION_EU) || defined(VERSION_SH)
extern s16 gEuUnknownWave7[256];
extern s16 *gWaveSamples[6];
#else
extern s16 *gWaveSamples[4];
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
extern u8 euUnknownData_8030194c[4];
#ifdef VERSION_EU
extern u16 gHeadsetPanQuantization[0x10];
#else
extern u16 gHeadsetPanQuantization[0x40];
#endif
extern s16 euUnknownData_80301950[64];
extern struct NoteSubEu gZeroNoteSub;
extern struct NoteSubEu gDefaultNoteSub;
#else
extern u16 gHeadsetPanQuantization[10];
#endif
extern f32 gHeadsetPanVolume[128];
extern f32 gStereoPanVolume[128];
extern f32 gDefaultPanVolume[128];

extern f32 gVolRampingLhs136[128];
extern f32 gVolRampingRhs136[128];
extern f32 gVolRampingLhs144[128];
extern f32 gVolRampingRhs144[128];
extern f32 gVolRampingLhs128[128];
extern f32 gVolRampingRhs128[128];

// non-constant .data
extern s16 gTatumsPerBeat;
extern s8 gUnusedCount80333EE8;
extern s32 gAudioHeapSize; // AUDIO_HEAP_SIZE
extern s32 gAudioInitPoolSize; // AUDIO_INIT_POOL_SIZE
extern volatile s32 gAudioLoadLock;

// .bss
extern volatile s32 gAudioFrameCount;

// number of DMAs performed during this frame
#if defined(VERSION_EU) || defined(VERSION_SH)
extern s32 gCurrAudioFrameDmaCount;
#else
extern volatile s32 gCurrAudioFrameDmaCount;
#endif

extern s32 gAudioTaskIndex;
extern s32 gCurrAiBufferIndex;

extern u64 *gAudioCmdBuffers[2];
extern u64 *gAudioCmd;

extern struct SPTask *gAudioTask;
extern struct SPTask gAudioTasks[2];

#if defined(VERSION_EU) || defined(VERSION_SH)
extern f32 D_EU_802298D0;
extern s32 gRefreshRate;
#endif

extern s16 *gAiBuffers[NUMAIBUFFERS];
extern s16 gAiBufferLengths[NUMAIBUFFERS];
#if defined(VERSION_SH)
#define AIBUFFER_LEN 0xb00
#elif defined(VERSION_EU)
#define AIBUFFER_LEN (0xa0 * 17)
#else
#define AIBUFFER_LEN (0xa0 * 16)
#endif

extern u32 gUnused80226E58[0x10];
extern u16 gUnused80226E98[0x10];

extern u32 gAudioRandom;

#ifdef VERSION_SH
extern f32 unk_sh_data_1[];

extern volatile u32 gAudioLoadLockSH;

extern u8 D_SH_80350F18;
extern u8 D_SH_80350F19;

extern OSMesg D_SH_80350F1C[1];
extern OSMesgQueue D_SH_80350F20; // address written to D_SH_80350F38
extern OSMesgQueue *D_SH_80350F38;

extern OSMesg D_SH_80350F40[4];
extern OSMesgQueue D_SH_80350F50; // address written to D_SH_80350F68
extern OSMesgQueue *D_SH_80350F68;

extern OSMesg D_SH_80350F6C[1];
extern OSMesgQueue D_SH_80350F70; // address written to D_SH_80350F88
extern OSMesgQueue *D_SH_80350F88;

extern OSMesg D_SH_80350F8C[1];
extern OSMesgQueue D_SH_80350F90; // address written to D_SH_80350F90
extern OSMesgQueue *D_SH_80350FA8;
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
#define UNUSED_COUNT_80333EE8 24
#define AUDIO_HEAP_SIZE 0x2c500
#define AUDIO_INIT_POOL_SIZE 0x2c00
#else
#define UNUSED_COUNT_80333EE8 16
#define AUDIO_HEAP_SIZE 0x31150
#define AUDIO_INIT_POOL_SIZE 0x2500
#endif

#ifdef VERSION_SH
extern u32 D_SH_80315EF0;
extern u16 D_SH_80315EF4;
extern u16 D_SH_80315EF8;
extern u16 D_SH_80315EFC;
#endif

#endif // AUDIO_DATA_H
