#ifndef AUDIO_LOAD_H
#define AUDIO_LOAD_H

#include "internal.h"

#define AUDIO_FRAME_DMA_QUEUE_SIZE 0x40

#define PRELOAD_BANKS 2
#define PRELOAD_SEQUENCE 1

#define IS_SEQUENCE_CHANNEL_VALID(ptr) ((uintptr_t)(ptr) != (uintptr_t)&gSequenceChannelNone)

extern struct Note *gNotes;

// Music in SM64 is played using 3 players:
// gSequencePlayers[0] is level background music
// gSequencePlayers[1] is misc music, like the puzzle jingle
// gSequencePlayers[2] is sound
extern struct SequencePlayer gSequencePlayers[SEQUENCE_PLAYERS];

extern struct SequenceChannel gSequenceChannels[SEQUENCE_CHANNELS];
extern struct SequenceChannelLayer gSequenceLayers[SEQUENCE_LAYERS];

extern struct SequenceChannel gSequenceChannelNone;

extern struct AudioListItem gLayerFreeList;
extern struct NotePool gNoteFreeLists;

extern OSMesgQueue gCurrAudioFrameDmaQueue;
extern u32 gSampleDmaNumListItems;
extern ALSeqFile *gAlTbl;
extern u8 *gAlBankSets;

extern struct CtlEntry *gCtlEntries;
#ifdef VERSION_EU
extern struct AudioBufferParametersEU gAudioBufferParameters;
#endif
extern s32 gAiFrequency;
extern u32 D_80226D68;
extern s32 gMaxAudioCmds;

extern s32 gMaxSimultaneousNotes;
extern s32 gSamplesPerFrameTarget;
extern s32 gMinAiBufferLength;
extern s16 gTempoInternalToExternal;
extern s8 gAudioUpdatesPerFrame; // = 4
extern s8 gSoundMode;

void audio_dma_partial_copy_async(uintptr_t *devAddr, u8 **vAddr, ssize_t *remaining, OSMesgQueue *queue, OSIoMesg *mesg);
void decrease_sample_dma_ttls(void);
void *dma_sample_data(uintptr_t devAddr, u32 size, s32 arg2, u8 *arg3);
void init_sample_dma_buffers(s32 arg0);
void patch_audio_bank(struct AudioBank *mem, u8 *offset, u32 numInstruments, u32 numDrums);
void preload_sequence(u32 seqId, u8 preloadMask);
void load_sequence(u32 player, u32 seqId, s32 loadAsync);

#endif /* AUDIO_LOAD_H */
