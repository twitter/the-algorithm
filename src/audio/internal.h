#ifndef AUDIO_INTERNAL_H
#define AUDIO_INTERNAL_H

#include <ultra64.h>

#include "types.h"

#ifdef VERSION_EU
#define SEQUENCE_PLAYERS 4
#define SEQUENCE_CHANNELS 48
#define SEQUENCE_LAYERS 64
#else
#define SEQUENCE_PLAYERS 3
#define SEQUENCE_CHANNELS 32
#ifdef VERSION_JP
#define SEQUENCE_LAYERS 48
#else
#define SEQUENCE_LAYERS 52
#endif
#endif

#define LAYERS_MAX       4
#define CHANNELS_MAX     16

#define NO_LAYER ((struct SequenceChannelLayer *)(-1))

#define MUTE_BEHAVIOR_STOP_SCRIPT 0x80 // stop processing sequence/channel scripts
#define MUTE_BEHAVIOR_STOP_NOTES 0x40  // prevent further notes from playing
#define MUTE_BEHAVIOR_SOFTEN 0x20      // lower volume, by default to half

#define SEQUENCE_PLAYER_STATE_0 0
#define SEQUENCE_PLAYER_STATE_FADE_OUT 1
#define SEQUENCE_PLAYER_STATE_2 2
#define SEQUENCE_PLAYER_STATE_3 3
#define SEQUENCE_PLAYER_STATE_4 4

#define NOTE_PRIORITY_DISABLED 0
#define NOTE_PRIORITY_STOPPING 1
#define NOTE_PRIORITY_MIN 2
#define NOTE_PRIORITY_DEFAULT 3

#define TATUMS_PER_BEAT 48

#ifdef VERSION_JP
#define TEMPO_SCALE 1
#else
#define TEMPO_SCALE TATUMS_PER_BEAT
#endif

// TODO: US_FLOAT should probably be renamed to JP_DOUBLE since eu seems to use floats too
#ifdef VERSION_JP
#define US_FLOAT(x) x
#else
#define US_FLOAT(x) x ## f
#endif

// Convert u8 or u16 to f32. On JP, this uses a u32->f32 conversion,
// resulting in more bloated codegen, while on US it goes through s32.
// Since u8 and u16 fit losslessly in both, behavior is the same.
#ifdef VERSION_JP
#define FLOAT_CAST(x) (f32) (x)
#else
#define FLOAT_CAST(x) (f32) (s32) (x)
#endif

struct NotePool;

struct AudioListItem
{
    // A node in a circularly linked list. Each node is either a head or an item:
    // - Items can be either detached (prev = NULL), or attached to a list.
    //   'value' points to something of interest.
    // - List heads are always attached; if a list is empty, its head points
    //   to itself. 'count' contains the size of the list.
    // If the list holds notes, 'pool' points back to the pool where it lives.
    // Otherwise, that member is NULL.
    struct AudioListItem *prev;
    struct AudioListItem *next;
    union {
        void *value; // either Note* or SequenceChannelLayer*
        s32 count;
    } u;
    struct NotePool *pool;
}; // size = 0x10

struct NotePool
{
    struct AudioListItem disabled;
    struct AudioListItem decaying;
    struct AudioListItem releasing;
    struct AudioListItem active;
};

struct VibratoState {
    /*0x00, 0x00*/ struct SequenceChannel *seqChannel;
    /*0x04, 0x04*/ u32 time;
#ifdef VERSION_EU
    /*    , 0x08*/ s16 *curve;
    /*    , 0x0C*/ f32 extent;
    /*    , 0x10*/ f32 rate;
    /*    , 0x14*/ u8 active;
#endif
#ifndef VERSION_EU
    /*0x08,     */ s8 *curve;
    /*0x0C,     */ u8 active;
    /*0x0E,     */ u16 rate;
    /*0x10,     */ u16 extent;
#endif
    /*0x12, 0x16*/ u16 rateChangeTimer;
    /*0x14, 0x18*/ u16 extentChangeTimer;
    /*0x16, 0x1A*/ u16 delay;
}; // size = 0x18, 0x1C on EU

// Pitch sliding by up to one octave in the positive direction. Negative
// direction is "supported" by setting extent to be negative. The code
// exterpolates exponentially in the wrong direction in that case, but that
// doesn't prevent seqplayer from doing it, AFAICT.
struct Portamento {
    u8 mode; // bit 0x80 denotes something; the rest are an index 0-5
    f32 cur;
    f32 speed;
    f32 extent;
}; // size = 0x10

struct AdsrEnvelope {
    s16 delay;
    s16 arg;
}; // size = 0x4

struct AdpcmLoop
{
    u32 start;
    u32 end;
    u32 count;
    u32 pad;
    s16 state[16]; // only exists if count != 0. 8-byte aligned
};

struct AdpcmBook
{
    s32 order;
    s32 npredictors;
    s16 book[1]; // size 8 * order * npredictors. 8-byte aligned
};

struct AudioBankSample
{
    u8 unused;
    u8 loaded;
    u8 *sampleAddr;
    struct AdpcmLoop *loop;
    struct AdpcmBook *book;
    u32 sampleSize; // never read. either 0 or 1 mod 9, depending on padding
};

struct AudioBankSound
{
    struct AudioBankSample *sample;
    f32 tuning; // frequency scale factor
}; // size = 0x8

struct Instrument
{
    /*0x00*/ u8 loaded;
    /*0x01*/ u8 normalRangeLo;
    /*0x02*/ u8 normalRangeHi;
    /*0x03*/ u8 releaseRate;
    /*0x04*/ struct AdsrEnvelope *envelope;
    /*0x08*/ struct AudioBankSound lowNotesSound;
    /*0x10*/ struct AudioBankSound normalNotesSound;
    /*0x18*/ struct AudioBankSound highNotesSound;
}; // size = 0x20

struct Drum
{
    u8 releaseRate;
    u8 pan;
    u8 loaded;
    struct AudioBankSound sound;
    struct AdsrEnvelope *envelope;
};

struct AudioBank
{
    struct Drum **drums;
    struct Instrument *instruments[1];
}; // dynamic size

struct CtlEntry
{
    u8 unused;
    u8 numInstruments;
    u8 numDrums;
    struct Instrument **instruments;
    struct Drum **drums;
}; // size = 0xC

struct M64ScriptState {
    u8 *pc;
    u8 *stack[4];
    u8 remLoopIters[4];
    u8 depth;
}; // size = 0x1C

struct SequencePlayer
{
    /*US/JP, EU   */
#ifdef VERSION_EU
    /*0x000, 0x000*/ u8 enabled : 1;
#else
    /*0x000, 0x000*/ volatile u8 enabled : 1;
#endif
    /*0x000, 0x000*/ u8 finished : 1; // never read
    /*0x000, 0x000*/ u8 muted : 1;
    /*0x000, 0x000*/ u8 seqDmaInProgress : 1;
    /*0x000, 0x000*/ u8 bankDmaInProgress : 1;
#ifdef VERSION_EU
    /*       0x000*/ u8 recalculateVolume : 1;
#endif
#ifndef VERSION_EU
    /*0x001       */ s8 seqVariation;
#endif
    /*0x002, 0x001*/ u8 state;
    /*0x003, 0x002*/ u8 noteAllocPolicy;
    /*0x004, 0x003*/ u8 muteBehavior;
    /*0x005, 0x004*/ u8 seqId;
    /*0x006, 0x005*/ u8 defaultBank[1]; // must be an array to get a comparison
    // to match; other u8's might also be part of that array
    /*0x007, 0x006*/ u8 loadingBankId;
#ifndef VERSION_EU
    /*0x008, ?????*/ u8 loadingBankNumInstruments;
    /*0x009, ?????*/ u8 loadingBankNumDrums;
#endif
#ifdef VERSION_EU
    /*     , 0x007*/ s8 seqVariationEu[1];
#endif
    /*0x00A, 0x008*/ u16 tempo; // beats per minute in JP, tatums per minute in US/EU
    /*0x00C, 0x00A*/ u16 tempoAcc;
#ifndef VERSION_EU
    /*0x00E, 0x010*/ u16 fadeTimer;
#endif
    /*0x010, 0x00C*/ s16 transposition;
    /*0x012, 0x00E*/ u16 delay;
#ifdef VERSION_EU
    /*0x00E, 0x010*/ u16 fadeTimer;
    /*     , 0x012*/ u16 fadeTimerUnkEu;
#endif
    /*0x014, 0x014*/ u8 *seqData; // buffer of some sort
    /*0x018, 0x018*/ f32 fadeVolume; // set to 1.0f
    /*0x01C, 0x01C*/ f32 fadeVelocity; // set to 0.0f
    /*0x020, 0x020*/ f32 volume; // set to 0.0f
    /*0x024, 0x024*/ f32 muteVolumeScale; // set to 0.5f
#ifdef VERSION_EU
    /*     , 0x028*/ f32 fadeVolumeScale;
    /*     , 0x02C*/ f32 appliedFadeVolume;
#else
    /*            */ u8 pad2[4];
#endif
    /*0x02C, 0x030*/ struct SequenceChannel *channels[CHANNELS_MAX];
    /*0x06C, 0x070*/ struct M64ScriptState scriptState;
    /*0x088, 0x08C*/ u8 *shortNoteVelocityTable;
    /*0x08C, 0x090*/ u8 *shortNoteDurationTable;
    /*0x090, 0x094*/ struct NotePool notePool;
    /*0x0D0, 0x0D4*/ OSMesgQueue seqDmaMesgQueue;
    /*0x0E8, 0x0EC*/ OSMesg seqDmaMesg;
    /*0x0EC, 0x0F0*/ OSIoMesg seqDmaIoMesg;
    /*0x100, 0x108*/ OSMesgQueue bankDmaMesgQueue;
    /*0x118, 0x120*/ OSMesg bankDmaMesg;
    /*0x11C, 0x124*/ OSIoMesg bankDmaIoMesg;
    /*0x130, 0x13C*/ u8 *bankDmaCurrMemAddr;
#ifndef VERSION_EU
    /*0x134, ?????*/ struct AudioBank *loadingBank;
#endif
    /*0x138, 0x140*/ uintptr_t bankDmaCurrDevAddr;
    /*0x13C, 0x144*/ ssize_t bankDmaRemaining;
}; // size = 0x140, 0x148 on EU

struct AdsrSettings
{
    u8 releaseRate;
#ifdef VERSION_EU
    u8 sustain;
#else
    u16 sustain; // sustain level, 2^16 = max
#endif
    struct AdsrEnvelope *envelope;
}; // size = 0x8

struct AdsrState {
    /*0x00, 0x00*/ u8 action;
    /*0x01, 0x01*/ u8 state;
#ifndef VERSION_EU
    /*0x02,     */ s16 initial; // always 0
    /*0x04,     */ s16 target;
    /*0x06,     */ s16 current;
#endif
    /*0x08, 0x02*/ s16 envIndex;
    /*0x0A, 0x04*/ s16 delay;
#ifdef VERSION_EU
    /*    , 0x08*/ f32 sustain;
    /*    , 0x0C*/ f32 velocity;
    /*    , 0x10*/ f32 fadeOutVel;
    /*    , 0x14*/ f32 current;
    /*    , 0x18*/ f32 target;
    s32 pad1C;
#else
    /*0x0C,     */ s16 sustain;
    /*0x0E,     */ s16 fadeOutVel;
    /*0x10,     */ s32 velocity;
    /*0x14,     */ s32 currentHiRes;
    /*0x18,     */ s16 *volOut;
#endif
    /*0x1C, 0x20*/ struct AdsrEnvelope *envelope;
}; // size = 0x20, 0x24 in EU

struct NoteAttributes
{
    s8 reverb;
#ifdef VERSION_EU
    u8 pan;
#endif
    f32 freqScale;
    f32 velocity;
#ifndef VERSION_EU
    f32 pan;
#endif
}; // size = 0x10

struct SequenceChannel
{
    /* U/J, EU  */
    /*0x00, 0x00*/ u8 enabled : 1;
    /*0x00, 0x00*/ u8 finished : 1;
    /*0x00, 0x00*/ u8 stopScript : 1;
    /*0x00, 0x00*/ u8 stopSomething2 : 1; // sets SequenceChannelLayer.stopSomething
    /*0x00, 0x00*/ u8 hasInstrument : 1;
    /*0x00, 0x00*/ u8 stereoHeadsetEffects : 1;
    /*0x00, ????*/ u8 largeNotes : 1; // notes specify duration and velocity
    /*0x00, ????*/ u8 unused : 1; // never read, set to 0
#ifdef VERSION_EU
    /*    , 0x01*/ union {
        struct {
            u8 freqScale : 1;
            u8 volume : 1;
            u8 pan : 1;
        } as_bitfields;
        u8 as_u8;
    } changes;
#endif
    /*0x01, 0x02*/ u8 noteAllocPolicy;
    /*0x02, 0x03*/ u8 muteBehavior;
    /*0x03, 0x04*/ u8 reverb; // or dry/wet mix
    /*0x04, ????*/ u8 notePriority; // 0-3
    /*0x05, 0x06*/ u8 bankId;
#ifdef VERSION_EU
    /*    , 0x07*/ u8 reverbIndex;
    /*    , 0x08*/ u8 bookOffset;
    /*    , 0x09*/ u8 newPan;
    /*    , 0x0A*/ u8 panChannelWeight; // proportion of pan that comes from the channel (0..128)
#else
    /*0x06,     */ u8 updatesPerFrameUnused;
#endif
    /*0x08, 0x0C*/ u16 vibratoRateStart; // initially 0x800
    /*0x0A, 0x0E*/ u16 vibratoExtentStart;
    /*0x0C, 0x10*/ u16 vibratoRateTarget; // initially 0x800
    /*0x0E, 0x12*/ u16 vibratoExtentTarget;
    /*0x10, 0x14*/ u16 vibratoRateChangeDelay;
    /*0x12, 0x16*/ u16 vibratoExtentChangeDelay;
    /*0x14, 0x18*/ u16 vibratoDelay;
    /*0x16, 0x1A*/ u16 delay;
    /*0x18, 0x1C*/ s16 instOrWave; // either 0 (none), instrument index + 1, or
    // 0x80..0x83 for sawtooth/triangle/sine/square waves.
    /*0x1A, 0x1E*/ s16 transposition;
    /*0x1C, 0x20*/ f32 volumeScale;
    /*0x20, 0x24*/ f32 volume;
#ifndef VERSION_EU
    /*0x24,     */ f32 pan;
    /*0x28,     */ f32 panChannelWeight; // proportion of pan that comes from the channel (0..1)
#else
    /*    , 0x28*/ s32 pan;
    /*    , 0x2C*/ f32 appliedVolume;
#endif
    /*0x2C, 0x30*/ f32 freqScale;
    /*0x30, 0x34*/ u8 (*dynTable)[][2];
    /*0x34, ????*/ struct Note *noteUnused; // never read
    /*0x38, ????*/ struct SequenceChannelLayer *layerUnused; // never read
    /*0x3C, 0x40*/ struct Instrument *instrument;
    /*0x40, 0x44*/ struct SequencePlayer *seqPlayer;
    /*0x44, 0x48*/ struct SequenceChannelLayer *layers[LAYERS_MAX];
    /*0x54, 0x58*/ s8 soundScriptIO[8]; // bridge between sound script and audio lib. For player 2,
    // [0] contains enabled, [4] contains sound ID, [5] contains reverb adjustment
    /*0x5C, 0x60*/ struct M64ScriptState scriptState;
    /*0x78, 0x7C*/ struct AdsrSettings adsr;
    /*0x80, 0x84*/ struct NotePool notePool;
}; // size = 0xC0, 0xC4 in EU

struct SequenceChannelLayer // Maybe SequenceTrack?
{
    /* U/J, EU */
    /*0x00, 0x00*/ u8 enabled : 1;
    /*0x00, 0x00*/ u8 finished : 1;
    /*0x00, 0x00*/ u8 stopSomething : 1; // ?
    /*0x00, 0x00*/ u8 continuousNotes : 1; // keep the same note for consecutive notes with the same sound
#ifdef VERSION_EU
    /*    , 0x00*/ u8 unusedEu0b8 : 1;
    /*    , 0x00*/ u8 notePropertiesNeedInit : 1;
    /*    , 0x00*/ u8 ignoreDrumPan : 1;
    /*    , 0x01*/ u8 instOrWave;
#endif
    /*0x01, 0x02*/ u8 status;
    /*0x02, 0x03*/ u8 noteDuration; // set to 0x80
    /*0x03, 0x04*/ u8 portamentoTargetNote;
#ifdef VERSION_EU
    /*    , 0x05*/ u8 pan; // 0..128
    /*    , 0x06*/ u8 notePan;
#endif
    /*0x04, 0x08*/ struct Portamento portamento;
    /*0x14, 0x18*/ struct AdsrSettings adsr;
    /*0x1C, 0x20*/ u16 portamentoTime;
    /*0x1E, 0x22*/ s16 transposition; // #semitones added to play commands
    // (m64 instruction encoding only allows referring to the limited range
    // 0..0x3f; this makes 0x40..0x7f accessible as well)
    /*0x20, 0x24*/ f32 freqScale;
    /*0x24, 0x28*/ f32 velocitySquare;
#ifndef VERSION_EU
    /*0x28,     */ f32 pan; // 0..1
#endif
    /*0x2C, 0x2C*/ f32 noteVelocity;
#ifndef VERSION_EU
    /*0x30*/ f32 notePan;
#endif
    /*0x34, 0x30*/ f32 noteFreqScale;
    /*0x38, 0x34*/ s16 shortNoteDefaultPlayPercentage;
    /*0x3A, 0x36*/ s16 playPercentage; // it's not really a percentage...
    /*0x3C, 0x38*/ s16 delay;
    /*0x3E, 0x3A*/ s16 duration;
    /*0x40, 0x3C*/ s16 delayUnused; // set to 'delay', never read
    /*0x44, 0x40*/ struct Note *note;
    /*0x48, 0x44*/ struct Instrument *instrument;
    /*0x4C, 0x48*/ struct AudioBankSound *sound;
    /*0x50, 0x4C*/ struct SequenceChannel *seqChannel;
    /*0x54, 0x50*/ struct M64ScriptState scriptState;
    /*0x70, 0x6C*/ struct AudioListItem listItem;
#ifdef VERSION_EU
    u8 pad2[4];
#endif
}; // size = 0x80

#ifdef VERSION_EU
struct NoteSynthesisState
{
    /*0x00*/ u8 restart;
    /*0x01*/ u8 sampleDmaIndex;
    /*0x02*/ u8 prevHeadsetPanRight;
    /*0x03*/ u8 prevHeadsetPanLeft;
    /*0x04*/ u16 samplePosFrac;
    /*0x08*/ s32 samplePosInt;
    /*0x0C*/ struct NoteSynthesisBuffers *synthesisBuffers;
    /*0x10*/ s16 curVolLeft;
    /*0x12*/ s16 curVolRight;
};
struct NotePlaybackState
{
    /* U/J, EU  */
    /*0x04, 0x00*/ u8 priority;
    /*      0x01*/ u8 waveId;
    /*      0x02*/ u8 sampleCountIndex;
    /*0x08, 0x04*/ s16 adsrVolScale;
    /*0x18, 0x08*/ f32 portamentoFreqScale;
    /*0x1C, 0x0C*/ f32 vibratoFreqScale;
    /*0x28, 0x10*/ struct SequenceChannelLayer *prevParentLayer;
    /*0x2C, 0x14*/ struct SequenceChannelLayer *parentLayer;
    /*0x30, 0x18*/ struct SequenceChannelLayer *wantedParentLayer;
    /*    , 0x1C*/ struct NoteAttributes attributes;
    /*0x54, 0x28*/ struct AdsrState adsr;
    /*0x74, 0x4C*/ struct Portamento portamento;
    /*0x84, 0x5C*/ struct VibratoState vibratoState;
};
struct NoteSubEu
{
    /*0x00*/ volatile u8 enabled : 1;
    /*0x00*/ u8 needsInit : 1;
    /*0x00*/ u8 finished : 1;
    /*0x00*/ u8 envMixerNeedsInit : 1;
    /*0x00*/ u8 stereoStrongRight : 1;
    /*0x00*/ u8 stereoStrongLeft : 1;
    /*0x00*/ u8 stereoHeadsetEffects : 1;
    /*0x00*/ u8 usesHeadsetPanEffects : 1;
    /*0x01*/ u8 reverbIndex : 3;
    /*0x01*/ u8 bookOffset : 3;
    /*0x01*/ u8 isSyntheticWave : 1;
    /*0x01*/ u8 hasTwoAdpcmParts : 1;
    /*0x02*/ u8 bankId;
    /*0x03*/ u8 headsetPanRight;
    /*0x04*/ u8 headsetPanLeft;
    /*0x05*/ u8 reverbVol;
    /*0x06*/ u16 targetVolLeft;
    /*0x08*/ u16 targetVolRight;
    /*0x0A*/ u16 resamplingRateFixedPoint; // stored as signed but loaded as u16
    /*0x0C*/ union {
        s16 *samples;
        struct AudioBankSound *audioBankSound;
    } sound;
};
struct Note
{
    /* U/J, EU  */
    /*0xA4, 0x00*/ struct AudioListItem listItem;
    /*      0x10*/ struct NoteSynthesisState synthesisState;
#ifdef TARGET_N64
    u8 pad0[12];
#endif
    /*0x04, 0x30*/ u8 priority;
    /*      0x31*/ u8 waveId;
    /*      0x32*/ u8 sampleCountIndex;
    /*0x08, 0x34*/ s16 adsrVolScale;
    /*0x18, 0x38*/ f32 portamentoFreqScale;
    /*0x1C, 0x3C*/ f32 vibratoFreqScale;
    /*0x28, 0x40*/ struct SequenceChannelLayer *prevParentLayer;
    /*0x2C, 0x44*/ struct SequenceChannelLayer *parentLayer;
    /*0x30, 0x48*/ struct SequenceChannelLayer *wantedParentLayer;
    /*    , 0x4C*/ struct NoteAttributes attributes;
    /*0x54, 0x58*/ struct AdsrState adsr;
    /*0x74, 0x7C*/ struct Portamento portamento;
    /*0x84, 0x8C*/ struct VibratoState vibratoState;
    u8 pad3[8];
    /*    , 0xB0*/ struct NoteSubEu noteSubEu;
}; // size = 0xC0
#else
struct vNote
{
    /* U/J, EU  */
    /*0x00*/ volatile u8 enabled : 1;
    long long int force_structure_alignment;
}; // size = 0xC0
struct Note
{
    /* U/J, EU  */
    /*0x00*/ u8 enabled : 1;
    /*0x00*/ u8 needsInit : 1;
    /*0x00*/ u8 restart : 1;
    /*0x00*/ u8 finished : 1;
    /*0x00*/ u8 envMixerNeedsInit : 1;
    /*0x00*/ u8 stereoStrongRight : 1;
    /*0x00*/ u8 stereoStrongLeft : 1;
    /*0x00*/ u8 stereoHeadsetEffects : 1;
    /*0x01*/ u8 usesHeadsetPanEffects;
    /*0x02*/ u8 unk2;
    /*0x03*/ u8 sampleDmaIndex;
    /*0x04, 0x30*/ u8 priority;
    /*0x05*/ u8 sampleCount; // 0, 8, 16, 32 or 64
    /*0x06*/ u8 instOrWave;
    /*0x07*/ u8 bankId; // in NoteSubEu on EU
    /*0x08*/ s16 adsrVolScale;
    /*    */ u8 pad1[2];
    /*0x0C, 0xB3*/ u16 headsetPanRight;
    /*0x0E, 0xB4*/ u16 headsetPanLeft;
    /*0x10*/ u16 prevHeadsetPanRight;
    /*0x12*/ u16 prevHeadsetPanLeft;
    /*0x14*/ s32 samplePosInt;
    /*0x18, 0x38*/ f32 portamentoFreqScale;
    /*0x1C, 0x3C*/ f32 vibratoFreqScale;
    /*0x20*/ u16 samplePosFrac;
    /*0x24*/ struct AudioBankSound *sound;
    /*0x28, 0x40*/ struct SequenceChannelLayer *prevParentLayer;
    /*0x2C, 0x44*/ struct SequenceChannelLayer *parentLayer;
    /*0x30, 0x48*/ struct SequenceChannelLayer *wantedParentLayer;
    /*0x34*/ struct NoteSynthesisBuffers *synthesisBuffers;
    /*0x38*/ f32 frequency;
    /*0x3C*/ u16 targetVolLeft;
    /*0x3E*/ u16 targetVolRight;
    /*0x40*/ u8 reverb;
    /*0x41*/ u8 unused1; // never read, set to 0x3f
    /*0x44*/ struct NoteAttributes attributes;
    /*0x54, 0x58*/ struct AdsrState adsr;
    /*0x74, 0x7C*/ struct Portamento portamento;
    /*0x84, 0x8C*/ struct VibratoState vibratoState;
    /*0x9C*/ s16 curVolLeft;
    /*0x9E*/ s16 curVolRight;
    /*0xA0*/ s16 reverbVol;
    /*0xA2*/ s16 unused2; // never read, set to 0
    /*0xA4, 0x00*/ struct AudioListItem listItem;
    /*          */ u8 pad2[0xc];
}; // size = 0xC0
#endif

struct NoteSynthesisBuffers
{
    s16 adpcmdecState[0x10];
    s16 finalResampleState[0x10];
    s16 mixEnvelopeState[0x28];
    s16 panResampleState[0x10];
    s16 panSamplesBuffer[0x20];
    s16 dummyResampleState[0x10];
#ifndef VERSION_EU
    s16 samples[0x40];
#endif
};

struct ReverbSettingsEU
{
    u8 downsampleRate;
    u8 windowSize; // To be multiplied by 64
    u16 gain;
};

struct AudioSessionSettingsEU
{
    u32 frequency;
    u8 unk1;  // always 1
    u8 maxSimultaneousNotes;
    u8 numReverbs; // always 1
    u8 unk2;  // always 0
    struct ReverbSettingsEU *reverbSettings;
    u16 volume;
    u16 unk3; // always 0
    u32 persistentSeqMem;
    u32 persistentBankMem;
    u32 temporarySeqMem;
    u32 temporaryBankMem;
};

struct AudioSessionSettings
{
    /*0x00*/ u32 frequency;
    /*0x04*/ u8 maxSimultaneousNotes;
    /*0x05*/ u8 reverbDownsampleRate; // always 1
    /*0x06*/ u16 reverbWindowSize;
    /*0x08*/ u16 reverbGain;
    /*0x0A*/ u16 volume;
    /*0x0C*/ u32 persistentSeqMem;
    /*0x10*/ u32 persistentBankMem;
    /*0x14*/ u32 temporarySeqMem;
    /*0x18*/ u32 temporaryBankMem;
}; // size = 0x1C

struct AudioBufferParametersEU {
    /*0x00*/ s16 presetUnk4; // audio frames per vsync?
    /*0x02*/ u16 frequency;
    /*0x04*/ u16 aiFrequency; // ?16
    /*0x06*/ s16 samplesPerFrameTarget;
    /*0x08*/ s16 maxAiBufferLength;
    /*0x0A*/ s16 minAiBufferLength;
    /*0x0C*/ s16 updatesPerFrame;
    /*0x0E*/ s16 samplesPerUpdate;
    /*0x10*/ s16 samplesPerUpdateMax;
    /*0x12*/ s16 samplesPerUpdateMin;
    /*0x14*/ f32 resampleRate; // contains 32000.0f / frequency
    /*0x18*/ f32 updatesPerFrameInv; // 1.0f / updatesPerFrame
    /*0x1C*/ f32 unkUpdatesPerFrameScaled; // 3.0f / (1280.0f * updatesPerFrame)
};

struct EuAudioCmd {
    union {
#if IS_BIG_ENDIAN
        struct {
            u8 op;
            u8 arg1;
            u8 arg2;
            u8 arg3;
        } s;
#else
        struct {
            u8 arg3;
            u8 arg2;
            u8 arg1;
            u8 op;
        } s;
#endif
        s32 first;
    } u;
    union {
        s32 as_s32;
        u32 as_u32;
        f32 as_f32;
#if IS_BIG_ENDIAN
        u8 as_u8;
        s8 as_s8;
#else
        struct {
            u8 pad0[3];
            u8 as_u8;
        };
        struct {
            u8 pad1[3];
            s8 as_s8;
        };
#endif
    } u2;
};

#endif /* AUDIO_INTERNAL_H */
