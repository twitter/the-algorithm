#ifndef AUDIO_DATA_H
#define AUDIO_DATA_H

#include "internal.h"

#define AUDIO_LOCK_UNINITIALIZED 0
#define AUDIO_LOCK_NOT_LOADING 0x76557364
#define AUDIO_LOCK_LOADING 0x19710515

#define NUMAIBUFFERS 3

// constant .data
#ifdef VERSION_EU
extern struct AudioSessionSettingsEU gAudioSessionPresets[];
#else
extern struct AudioSessionSettings gAudioSessionPresets[18];
#endif
extern u16 D_80332388[128]; // unused

#ifdef VERSION_EU
extern f32 gPitchBendFrequencyScale[256];
#else
extern f32 gPitchBendFrequencyScale[255];
#endif
extern f32 gNoteFrequencies[128];

extern u8 gDefaultShortNoteVelocityTable[16];
extern u8 gDefaultShortNoteDurationTable[16];
extern s8 gVibratoCurve[16];
extern struct AdsrEnvelope gDefaultEnvelope[3];

#ifdef VERSION_EU
extern s16 gEuUnknownWave7[256];
extern s16 *gWaveSamples[6];
#else
extern s16 *gWaveSamples[4];
#endif

#ifdef VERSION_EU
extern u8 euUnknownData_8030194c[4];
extern u16 gHeadsetPanQuantization[0x10];
extern s32 euUnknownData_80301950[32];
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
extern s32 gAudioHeapSize;
extern s32 D_80333EF0; // amount of heap designated to gAudioInitPool, 0x2500
extern volatile s32 gAudioLoadLock;

// .bss
extern volatile s32 gAudioFrameCount;

// number of DMAs performed during this frame
#ifdef VERSION_EU
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

#ifdef VERSION_EU
extern f32 D_EU_802298D0;
extern s32 gRefreshRate;
#endif

extern u16 *gAiBuffers[NUMAIBUFFERS];
extern s16 gAiBufferLengths[NUMAIBUFFERS];
#ifdef VERSION_EU
#define AIBUFFER_LEN (0xa0 * 17)
#else
#define AIBUFFER_LEN (0xa0 * 16)
#endif

extern u32 gUnused80226E58[0x10];
extern u16 gUnused80226E98[0x10];

extern u32 gAudioRandom;

//make my life easier
#ifdef VERSION_EU
#define UNUSED_COUNT_80333EE8 24
#define AUDIO_HEAP_SIZE 0x2c500
#define D_80333EF0_VAL 0x2c00
#else
#define UNUSED_COUNT_80333EE8 16
#define AUDIO_HEAP_SIZE 0x31150
#define D_80333EF0_VAL 0x2500
#endif


#endif /* AUDIO_DATA_H */
