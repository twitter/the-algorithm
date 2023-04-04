#ifndef AUDIO_SYNTHESIS_H
#define AUDIO_SYNTHESIS_H

#include "internal.h"

#ifdef VERSION_SH
#define DEFAULT_LEN_1CH 0x180
#define DEFAULT_LEN_2CH 0x300
#else
#define DEFAULT_LEN_1CH 0x140
#define DEFAULT_LEN_2CH 0x280
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
#define MAX_UPDATES_PER_FRAME 5
#else
#define MAX_UPDATES_PER_FRAME 4
#endif

struct ReverbRingBufferItem {
    s16 numSamplesAfterDownsampling;
    s16 chunkLen; // never read
    s16 *toDownsampleLeft;
    s16 *toDownsampleRight; // data pointed to by left and right are adjacent in memory
    s32 startPos; // start pos in ring buffer
    s16 lengthA; // first length in ring buffer (from startPos, at most until end)
    s16 lengthB; // second length in ring buffer (from pos 0)
}; // size = 0x14

struct SynthesisReverb {
    /*0x00, 0x00, 0x00*/ u8 resampleFlags;
    /*0x01, 0x01, 0x01*/ u8 useReverb;
    /*0x02, 0x02, 0x02*/ u8 framesLeftToIgnore;
    /*0x03, 0x03, 0x03*/ u8 curFrame;
#if defined(VERSION_EU) || defined(VERSION_SH)
    /*      0x04, 0x04*/ u8 downsampleRate;
#ifdef VERSION_SH
    /*            0x05*/ s8 unk5;
#endif
    /*      0x06, 0x06*/ u16 windowSize; // same as bufSizePerChannel
#endif
#ifdef VERSION_SH
    /*            0x08*/ u16 unk08;
#endif
    /*0x04, 0x08, 0x0A*/ u16 reverbGain;
    /*0x06, 0x0A, 0x0C*/ u16 resampleRate;
#ifdef VERSION_SH
    /*            0x0E*/ u16 panRight;
    /*            0x10*/ u16 panLeft;
#endif
    /*0x08, 0x0C, 0x14*/ s32 nextRingBufferPos;
    /*0x0C, 0x10, 0x18*/ s32 unkC; // never read
    /*0x10, 0x14, 0x1C*/ s32 bufSizePerChannel;
    struct {
        s16 *left;
        s16 *right;
    } ringBuffer;
    /*0x1C, 0x20, 0x28*/ s16 *resampleStateLeft;
    /*0x20, 0x24, 0x2C*/ s16 *resampleStateRight;
    /*0x24, 0x28, 0x30*/ s16 *unk24; // never read
    /*0x28, 0x2C, 0x34*/ s16 *unk28; // never read
    /*0x2C, 0x30, 0x38*/ struct ReverbRingBufferItem items[2][MAX_UPDATES_PER_FRAME];
#if defined(VERSION_EU) || defined(VERSION_SH)
    // Only used in sh:
    /*            0x100*/ s16 *unk100;
    /*            0x104*/ s16 *unk104;
    /*            0x108*/ s16 *unk108;
    /*            0x10C*/ s16 *unk10C;
#endif
}; // 0xCC <= size <= 0x100
#if defined(VERSION_EU) || defined(VERSION_SH)
extern struct SynthesisReverb gSynthesisReverbs[4];
extern s8 gNumSynthesisReverbs;
extern struct NoteSubEu *gNoteSubsEu;
extern f32 gLeftVolRampings[3][1024];
extern f32 gRightVolRampings[3][1024];
extern f32 *gCurrentLeftVolRamping; // Points to any of the three left buffers above
extern f32 *gCurrentRightVolRamping; // Points to any of the three right buffers above
#else
extern struct SynthesisReverb gSynthesisReverb;
#endif

#ifdef VERSION_SH
extern s16 D_SH_803479B4;
#endif

u64 *synthesis_execute(u64 *cmdBuf, s32 *writtenCmds, s16 *aiBuf, s32 bufLen);
#if defined(VERSION_JP) || defined(VERSION_US)
void note_init_volume(struct Note *note);
void note_set_vel_pan_reverb(struct Note *note, f32 velocity, f32 pan, u8 reverbVol);
void note_set_frequency(struct Note *note, f32 frequency);
void note_enable(struct Note *note);
void note_disable(struct Note *note);
#endif

#endif // AUDIO_SYNTHESIS_H
