#ifdef VERSION_SH
#include <ultra64.h>

#include "synthesis.h"
#include "heap.h"
#include "data.h"
#include "load.h"
#include "seqplayer.h"
#include "internal.h"
#include "external.h"


#define DMEM_ADDR_TEMP 0x450
#define DMEM_ADDR_RESAMPLED 0x470
#define DMEM_ADDR_RESAMPLED2 0x5f0
#define DMEM_ADDR_UNCOMPRESSED_NOTE 0x5f0
#define DMEM_ADDR_NOTE_PAN_TEMP 0x650
#define DMEM_ADDR_COMPRESSED_ADPCM_DATA 0x990
#define DMEM_ADDR_LEFT_CH 0x990
#define DMEM_ADDR_RIGHT_CH 0xb10
#define DMEM_ADDR_WET_LEFT_CH 0xc90
#define DMEM_ADDR_WET_RIGHT_CH 0xe10

#define aSetLoadBufferPair(pkt, c, off)                                                                \
    aSetBuffer(pkt, 0, c + DMEM_ADDR_WET_LEFT_CH, 0, DEFAULT_LEN_1CH - c);                             \
    aLoadBuffer(pkt, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.ringBuffer.left + (off)));                  \
    aSetBuffer(pkt, 0, c + DMEM_ADDR_WET_RIGHT_CH, 0, DEFAULT_LEN_1CH - c);                            \
    aLoadBuffer(pkt, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.ringBuffer.right + (off)))

#define aSetSaveBufferPair(pkt, c, d, off)                                                             \
    aSetBuffer(pkt, 0, 0, c + DMEM_ADDR_WET_LEFT_CH, d);                                               \
    aSaveBuffer(pkt, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.ringBuffer.left +  (off)));                 \
    aSetBuffer(pkt, 0, 0, c + DMEM_ADDR_WET_RIGHT_CH, d);                                              \
    aSaveBuffer(pkt, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.ringBuffer.right + (off)));

#define ALIGN(val, amnt) (((val) + (1 << amnt) - 1) & ~((1 << amnt) - 1))

struct VolumeChange {
    u16 sourceLeft;
    u16 sourceRight;
    u16 targetLeft;
    u16 targetRight;
};

u64 *synthesis_do_one_audio_update(s16 *aiBuf, s32 bufLen, u64 *cmd, s32 updateIndex);
u64 *synthesis_process_note(s32 noteIndex, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s16 *aiBuf, s32 bufLen, u64 *cmd, s32 updateIndex);
u64 *load_wave_samples(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s32 nSamplesToLoad);
u64 *final_resample(u64 *cmd, struct NoteSynthesisState *synthesisState, s32 count, u16 pitch, u16 dmemIn, u32 flags);
u64 *process_envelope(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s32 nSamples, u16 inBuf, s32 headsetPanSettings, u32 flags);
u64 *note_apply_headset_pan_effects(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *note, s32 bufLen, s32 flags, s32 leftRight);

struct SynthesisReverb gSynthesisReverbs[4];
u8 sAudioSynthesisPad[0x10];

s16 gVolume;
s8 gUseReverb;
s8 gNumSynthesisReverbs;
s16 D_SH_803479B4; // contains 4096
struct NoteSubEu *gNoteSubsEu;

// Equivalent functionality as the US/JP version,
// just that the reverb structure is chosen from an array with index
// Identical in EU.
void prepare_reverb_ring_buffer(s32 chunkLen, u32 updateIndex, s32 reverbIndex) {
    struct ReverbRingBufferItem *item;
    struct SynthesisReverb *reverb = &gSynthesisReverbs[reverbIndex];
    s32 srcPos;
    s32 dstPos;
    s32 nSamples;
    s32 excessiveSamples;
    s32 UNUSED pad[3];
    if (reverb->downsampleRate != 1) {
        if (reverb->framesLeftToIgnore == 0) {
            // Now that the RSP has finished, downsample the samples produced two frames ago by skipping
            // samples.
            item = &reverb->items[reverb->curFrame][updateIndex];

            // Touches both left and right since they are adjacent in memory
            osInvalDCache(item->toDownsampleLeft, DEFAULT_LEN_2CH);

            for (srcPos = 0, dstPos = 0; dstPos < item->lengthA / 2;
                 srcPos += reverb->downsampleRate, dstPos++) {
                reverb->ringBuffer.left[item->startPos + dstPos] =
                    item->toDownsampleLeft[srcPos];
                reverb->ringBuffer.right[item->startPos + dstPos] =
                    item->toDownsampleRight[srcPos];
            }
            for (dstPos = 0; dstPos < item->lengthB / 2; srcPos += reverb->downsampleRate, dstPos++) {
                reverb->ringBuffer.left[dstPos] = item->toDownsampleLeft[srcPos];
                reverb->ringBuffer.right[dstPos] = item->toDownsampleRight[srcPos];
            }
        }
    }

    item = &reverb->items[reverb->curFrame][updateIndex];
    nSamples = chunkLen / reverb->downsampleRate;
    excessiveSamples = (nSamples + reverb->nextRingBufferPos) - reverb->bufSizePerChannel;
    if (excessiveSamples < 0) {
        // There is space in the ring buffer before it wraps around
        item->lengthA = nSamples * 2;
        item->lengthB = 0;
        item->startPos = (s32) reverb->nextRingBufferPos;
        reverb->nextRingBufferPos += nSamples;
    } else {
        // Ring buffer wrapped around
        item->lengthA = (nSamples - excessiveSamples) * 2;
        item->lengthB = excessiveSamples * 2;
        item->startPos = reverb->nextRingBufferPos;
        reverb->nextRingBufferPos = excessiveSamples;
    }
    // These fields are never read later
    item->numSamplesAfterDownsampling = nSamples;
    item->chunkLen = chunkLen;
}

u64 *synthesis_load_reverb_ring_buffer(u64 *cmd, u16 addr, u16 srcOffset, s32 len, s32 reverbIndex) {
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.left[srcOffset]),
                addr, len);
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.right[srcOffset]),
                addr + DEFAULT_LEN_1CH, len);
    return cmd;
}

u64 *synthesis_save_reverb_ring_buffer(u64 *cmd, u16 addr, u16 destOffset, s32 len, s32 reverbIndex) {
    aSaveBuffer(cmd++, addr,
                VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.left[destOffset]), len);
    aSaveBuffer(cmd++, addr + DEFAULT_LEN_1CH,
                VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.right[destOffset]), len);
    return cmd;
}

void func_sh_802ed644(s32 updateIndexStart, s32 noteIndex) {
    s32 i;

    for (i = updateIndexStart + 1; i < gAudioBufferParameters.updatesPerFrame; i++) {
        if (!gNoteSubsEu[gMaxSimultaneousNotes * i + noteIndex].needsInit) {
            gNoteSubsEu[gMaxSimultaneousNotes * i + noteIndex].enabled = FALSE;
        } else {
            break;
        }
    }
}

void synthesis_load_note_subs_eu(s32 updateIndex) {
    struct NoteSubEu *src;
    struct NoteSubEu *dest;
    s32 i;

    for (i = 0; i < gMaxSimultaneousNotes; i++) {
        src = &gNotes[i].noteSubEu;
        dest = &gNoteSubsEu[gMaxSimultaneousNotes * updateIndex + i];
        if (src->enabled) {
            *dest = *src;
            src->needsInit = FALSE;
        } else {
            dest->enabled = FALSE;
        }
    }
}

// TODO: (Scrub C) pointless mask and whitespace
u64 *synthesis_execute(u64 *cmdBuf, s32 *writtenCmds, s16 *aiBuf, s32 bufLen) {
    s32 i, j;
    u32 *aiBufPtr;
    u64 *cmd = cmdBuf;
    s32 chunkLen;

    for (i = gAudioBufferParameters.updatesPerFrame; i > 0; i--) {
        process_sequences(i - 1);
        synthesis_load_note_subs_eu(gAudioBufferParameters.updatesPerFrame - i);
    }
    aiBufPtr = (u32 *) aiBuf;
    for (i = gAudioBufferParameters.updatesPerFrame; i > 0; i--) {
        if (i == 1) {
            chunkLen = bufLen;
        } else {
            if (bufLen / i >= gAudioBufferParameters.samplesPerUpdateMax) {
                chunkLen = gAudioBufferParameters.samplesPerUpdateMax;
            } else if (bufLen / i <= gAudioBufferParameters.samplesPerUpdateMin) {
                chunkLen = gAudioBufferParameters.samplesPerUpdateMin;
            } else {
                chunkLen = gAudioBufferParameters.samplesPerUpdate;
            }
        }
        for (j = 0; j < gNumSynthesisReverbs; j++) {
            if (gSynthesisReverbs[j].useReverb != 0) {
                prepare_reverb_ring_buffer(chunkLen, gAudioBufferParameters.updatesPerFrame - i, j);
            }
        }
        cmd = synthesis_do_one_audio_update((s16 *) aiBufPtr, chunkLen, cmd, gAudioBufferParameters.updatesPerFrame - i);
        bufLen -= chunkLen;
        aiBufPtr += chunkLen;
    }

    for (j = 0; j < gNumSynthesisReverbs; j++) {
        if (gSynthesisReverbs[j].framesLeftToIgnore != 0) {
            gSynthesisReverbs[j].framesLeftToIgnore--;
        }
        gSynthesisReverbs[j].curFrame ^= 1;
    }
    *writtenCmds = cmd - cmdBuf;
    return cmd;
}

u64 *synthesis_resample_and_mix_reverb(u64 *cmd, s32 bufLen, s16 reverbIndex, s16 updateIndex) {
    struct ReverbRingBufferItem *item;
    s16 startPad;
    s16 paddedLengthA;

    item = &gSynthesisReverbs[reverbIndex].items[gSynthesisReverbs[reverbIndex].curFrame][updateIndex];

    if (gSynthesisReverbs[reverbIndex].downsampleRate == 1) {
        cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_WET_LEFT_CH, item->startPos, item->lengthA, reverbIndex);
        if (item->lengthB != 0) {
            cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_WET_LEFT_CH + item->lengthA, 0, item->lengthB, reverbIndex);
        }
        aAddMixer(cmd++, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_LEFT_CH, DEFAULT_LEN_2CH);
        aMix(cmd++, 0x8000 + gSynthesisReverbs[reverbIndex].reverbGain, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_2CH);
    } else {
        startPad = (item->startPos % 8u) * 2;
        paddedLengthA = ALIGN(startPad + item->lengthA, 4);

        cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_RESAMPLED, (item->startPos - startPad / 2), DEFAULT_LEN_1CH, reverbIndex);
        if (item->lengthB != 0) {
            cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_RESAMPLED + paddedLengthA, 0, DEFAULT_LEN_1CH - paddedLengthA, reverbIndex);
        }

        aSetBuffer(cmd++, 0, DMEM_ADDR_RESAMPLED + startPad, DMEM_ADDR_WET_LEFT_CH, bufLen * 2);
        aResample(cmd++, gSynthesisReverbs[reverbIndex].resampleFlags, gSynthesisReverbs[reverbIndex].resampleRate, VIRTUAL_TO_PHYSICAL2(gSynthesisReverbs[reverbIndex].resampleStateLeft));

        aSetBuffer(cmd++, 0, DMEM_ADDR_RESAMPLED2 + startPad, DMEM_ADDR_WET_RIGHT_CH, bufLen * 2);
        aResample(cmd++, gSynthesisReverbs[reverbIndex].resampleFlags, gSynthesisReverbs[reverbIndex].resampleRate, VIRTUAL_TO_PHYSICAL2(gSynthesisReverbs[reverbIndex].resampleStateRight));

        aAddMixer(cmd++, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_LEFT_CH, DEFAULT_LEN_2CH);
        aMix(cmd++, 0x8000 + gSynthesisReverbs[reverbIndex].reverbGain, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_2CH);
    }
    if (gSynthesisReverbs[reverbIndex].panRight != 0 || gSynthesisReverbs[reverbIndex].panLeft != 0) {
        // Leak some audio from the left reverb channel into the right reverb channel and vice versa (pan)
        aDMEMMove(cmd++, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_RESAMPLED, DEFAULT_LEN_1CH);
        aMix(cmd++, gSynthesisReverbs[reverbIndex].panRight, DMEM_ADDR_WET_RIGHT_CH, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_1CH);
        aMix(cmd++, gSynthesisReverbs[reverbIndex].panLeft, DMEM_ADDR_RESAMPLED, DMEM_ADDR_WET_RIGHT_CH, DEFAULT_LEN_1CH);
    }
    return cmd;
}

u64 *synthesis_load_reverb_samples(u64 *cmd, s16 reverbIndex, s16 updateIndex) {
    struct ReverbRingBufferItem *item;
    struct SynthesisReverb *reverb;

    reverb = &gSynthesisReverbs[reverbIndex];
    item = &reverb->items[reverb->curFrame][updateIndex];
    // Get the oldest samples in the ring buffer into the wet channels
    cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_RESAMPLED, item->startPos, item->lengthA, reverbIndex);
    if (item->lengthB != 0) {
        // Ring buffer wrapped
        cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_RESAMPLED + item->lengthA, 0, item->lengthB, reverbIndex);
    }
    return cmd;
}

u64 *synthesis_save_reverb_samples(u64 *cmd, s16 reverbIndex, s16 updateIndex) {
    struct ReverbRingBufferItem *item;

    item = &gSynthesisReverbs[reverbIndex].items[gSynthesisReverbs[reverbIndex].curFrame][updateIndex];
    switch (gSynthesisReverbs[reverbIndex].downsampleRate) {
        case 1:
            // Put the oldest samples in the ring buffer into the wet channels
            cmd = synthesis_save_reverb_ring_buffer(cmd, DMEM_ADDR_WET_LEFT_CH, item->startPos, item->lengthA, reverbIndex);
            if (item->lengthB != 0) {
                // Ring buffer wrapped
                cmd = synthesis_save_reverb_ring_buffer(cmd, DMEM_ADDR_WET_LEFT_CH + item->lengthA, 0, item->lengthB, reverbIndex);
            }
            break;

        default:
            // Downsampling is done later by CPU when RSP is done, therefore we need to have double
            // buffering. Left and right buffers are adjacent in memory.
            aSaveBuffer(cmd++, DMEM_ADDR_WET_LEFT_CH,
                    VIRTUAL_TO_PHYSICAL2(gSynthesisReverbs[reverbIndex].items[gSynthesisReverbs[reverbIndex].curFrame][updateIndex].toDownsampleLeft), DEFAULT_LEN_2CH);
            break;
    }
    gSynthesisReverbs[reverbIndex].resampleFlags = 0;
    return cmd;
}

u64 *func_sh_802EDF24(u64 *cmd, s16 reverbIndex, s16 updateIndex) {
    struct ReverbRingBufferItem *item;
    struct SynthesisReverb *reverb;

    reverb = &gSynthesisReverbs[reverbIndex];
    item = &reverb->items[reverb->curFrame][updateIndex];
    // Put the oldest samples in the ring buffer into the wet channels
    cmd = synthesis_save_reverb_ring_buffer(cmd, DMEM_ADDR_RESAMPLED, item->startPos, item->lengthA, reverbIndex);
    if (item->lengthB != 0) {
        // Ring buffer wrapped
        cmd = synthesis_save_reverb_ring_buffer(cmd, DMEM_ADDR_RESAMPLED + item->lengthA, 0, item->lengthB, reverbIndex);
    }
    return cmd;
}

u64 *synthesis_do_one_audio_update(s16 *aiBuf, s32 bufLen, u64 *cmd, s32 updateIndex) {
    struct NoteSubEu *noteSubEu;
    u8 noteIndices[56];
    s32 temp;
    s32 i;
    s16 j;
    s16 notePos = 0;

    if (gNumSynthesisReverbs == 0) {
        for (i = 0; i < gMaxSimultaneousNotes; i++) {
            if (gNoteSubsEu[gMaxSimultaneousNotes * updateIndex + i].enabled) {
                noteIndices[notePos++] = i;
            }
        }
    } else {
        for (j = 0; j < gNumSynthesisReverbs; j++) {
            for (i = 0; i < gMaxSimultaneousNotes; i++) {
                noteSubEu = &gNoteSubsEu[gMaxSimultaneousNotes * updateIndex + i];
                if (noteSubEu->enabled && j == noteSubEu->reverbIndex) {
                    noteIndices[notePos++] = i;
                }
            }
        }

        for (i = 0; i < gMaxSimultaneousNotes; i++) {
            noteSubEu = &gNoteSubsEu[gMaxSimultaneousNotes * updateIndex + i];
            if (noteSubEu->enabled && noteSubEu->reverbIndex >= gNumSynthesisReverbs) {
                noteIndices[notePos++] = i;
            }
        }
    }
    aClearBuffer(cmd++, DMEM_ADDR_LEFT_CH, DEFAULT_LEN_2CH);
    i = 0;
    for (j = 0; j < gNumSynthesisReverbs; j++) {
        gUseReverb = gSynthesisReverbs[j].useReverb;
        if (gUseReverb != 0) {
            cmd = synthesis_resample_and_mix_reverb(cmd, bufLen, j, updateIndex);
        }
        for (; i < notePos; i++) {
            temp = updateIndex * gMaxSimultaneousNotes;
            if (j == gNoteSubsEu[temp + noteIndices[i]].reverbIndex) {
                cmd = synthesis_process_note(noteIndices[i],
                                             &gNoteSubsEu[temp + noteIndices[i]],
                                             &gNotes[noteIndices[i]].synthesisState,
                                             aiBuf, bufLen, cmd, updateIndex);
                continue;
            } else {
                break;
            }
        }
        if (gSynthesisReverbs[j].useReverb != 0) {
            if (gSynthesisReverbs[j].unk100 != NULL) {
                aFilter(cmd++, 0x02, bufLen * 2, gSynthesisReverbs[j].unk100);
                aFilter(cmd++, gSynthesisReverbs[j].resampleFlags, DMEM_ADDR_WET_LEFT_CH, gSynthesisReverbs[j].unk108);
            }
            if (gSynthesisReverbs[j].unk104 != NULL) {
                aFilter(cmd++, 0x02, bufLen * 2, gSynthesisReverbs[j].unk104);
                aFilter(cmd++, gSynthesisReverbs[j].resampleFlags, DMEM_ADDR_WET_RIGHT_CH, gSynthesisReverbs[j].unk10C);
            }
            cmd = synthesis_save_reverb_samples(cmd, j, updateIndex);
            if (gSynthesisReverbs[j].unk5 != -1) {
                if (gSynthesisReverbs[gSynthesisReverbs[j].unk5].downsampleRate == 1) {
                    cmd = synthesis_load_reverb_samples(cmd, gSynthesisReverbs[j].unk5, updateIndex);
                    aMix(cmd++, gSynthesisReverbs[j].unk08, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_RESAMPLED, DEFAULT_LEN_2CH);
                    cmd = func_sh_802EDF24(cmd++, gSynthesisReverbs[j].unk5, updateIndex);
                }
            }
        }
    }
    for (; i < notePos; i++) {
        struct NoteSubEu *noteSubEu2 = &gNoteSubsEu[updateIndex * gMaxSimultaneousNotes + noteIndices[i]];
        cmd = synthesis_process_note(noteIndices[i],
                                     noteSubEu2,
                                     &gNotes[noteIndices[i]].synthesisState,
                                     aiBuf, bufLen, cmd, updateIndex);
    }

    temp = bufLen * 2;
    aInterleave(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_LEFT_CH, DMEM_ADDR_RIGHT_CH, temp);
    aSaveBuffer(cmd++, DMEM_ADDR_TEMP, VIRTUAL_TO_PHYSICAL2(aiBuf), temp * 2);
    return cmd;
}

u64 *synthesis_process_note(s32 noteIndex, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, UNUSED s16 *aiBuf, s32 bufLen, u64 *cmd, s32 updateIndex) {
    UNUSED s32 pad0[3];
    struct AudioBankSample *audioBookSample; // sp164, sp138
    struct AdpcmLoop *loopInfo; // sp160, sp134
    s16 *curLoadedBook; // sp154, sp130
    UNUSED u8 padEU[0x04];
    UNUSED u8 pad8[0x04];
    s32 noteFinished; // 150 t2, sp124
    s32 restart; // 14c t3, sp120
    s32 flags; // sp148, sp11C, t8
    u16 resamplingRateFixedPoint; // sp5c, sp11A
    s32 nSamplesToLoad; //s0, Ec
    UNUSED u8 pad7[0x0c]; // sp100
    s32 sp130; //sp128, sp104
    UNUSED s32 tempBufLen;
    UNUSED u32 pad9;
    s32 t0;
    u8 *sampleAddr; // sp120, spF4
    s32 s6;
    s32 samplesLenAdjusted; // 108,      spEC
    s32 nAdpcmSamplesProcessed; // signed required for US // spc0
    s32 endPos; // sp110,    spE4
    s32 nSamplesToProcess; // sp10c/a0, spE0
    // Might have been used to store (samplesLenFixedPoint >> 0x10), but doing so causes strange
    // behavior with the break near the end of the loop, causing US and JP to need a goto instead
    UNUSED s32 samplesLenInt;
    s32 s2;
    s32 leftRight; //s0
    s32 s5; //s4
    u32 samplesLenFixedPoint; // v1_1
    s32 s3;     // spA0
    s32 nSamplesInThisIteration; // v1_2
    u32 a3;
    u8 *v0_2;
    s32 unk_s6; // sp90
    s32 s5Aligned;
    s32 sp88;
    s32 sp84;
    u32 temp;
    s32 nParts; // spE8, spBC
    s32 curPart; // spE4, spB8
    s32 aligned;
    UNUSED u32 padSH1;
    s32 resampledTempLen; // spD8, spAC, sp6c
    u16 noteSamplesDmemAddrBeforeResampling; // spD6, spAA, sp6a -- 6C
    UNUSED u32 padSH2;
    UNUSED u32 padSH3;
    UNUSED u32 padSH4;
    struct Note *note;  // sp58
    u16 sp56;           // sp56
    u16 addr;
    u8 synthesisVolume;

    curLoadedBook = NULL;
    note = &gNotes[noteIndex];
    flags = 0;
    if (noteSubEu->needsInit == TRUE) {
        flags = A_INIT;
        synthesisState->restart = 0;
        synthesisState->samplePosInt = 0;
        synthesisState->samplePosFrac = 0;
        synthesisState->curVolLeft = 0;
        synthesisState->curVolRight = 0;
        synthesisState->prevHeadsetPanRight = 0;
        synthesisState->prevHeadsetPanLeft = 0;
        synthesisState->reverbVol = noteSubEu->reverbVol;
        synthesisState->unk5 = 0;
        note->noteSubEu.finished = 0;
    }

    resamplingRateFixedPoint = noteSubEu->resamplingRateFixedPoint;
    nParts = noteSubEu->hasTwoAdpcmParts + 1;
    samplesLenFixedPoint = (resamplingRateFixedPoint * bufLen * 2) + synthesisState->samplePosFrac;
    nSamplesToLoad = (samplesLenFixedPoint >> 0x10);
    synthesisState->samplePosFrac = samplesLenFixedPoint & 0xFFFF;

    if ((synthesisState->unk5 == 1) && (nParts == 2)) {
        nSamplesToLoad += 2;
        sp56 = 2;
    } else if ((synthesisState->unk5 == 2) && (nParts == 1)) {
        nSamplesToLoad -= 4;
        sp56 = 4;
    } else {
        sp56 = 0;
    }


    synthesisState->unk5 = nParts;

    if (noteSubEu->isSyntheticWave) {
        cmd = load_wave_samples(cmd, noteSubEu, synthesisState, nSamplesToLoad);
        noteSamplesDmemAddrBeforeResampling = (synthesisState->samplePosInt * 2) + DMEM_ADDR_UNCOMPRESSED_NOTE;
        synthesisState->samplePosInt += nSamplesToLoad;
    } else {
        // ADPCM note
        audioBookSample = noteSubEu->sound.audioBankSound->sample;
        loopInfo = audioBookSample->loop;
        endPos = loopInfo->end;
        sampleAddr = audioBookSample->sampleAddr;
        resampledTempLen = 0;
        for (curPart = 0; curPart < nParts; curPart++) {
            nAdpcmSamplesProcessed = 0; // s8
            s5 = 0; // s4

            if (nParts == 1) {
                samplesLenAdjusted = nSamplesToLoad;
            } else if (nSamplesToLoad & 1) {
                samplesLenAdjusted = (nSamplesToLoad & ~1) + (curPart * 2);
            } else {
                samplesLenAdjusted = nSamplesToLoad;
            }

            if (audioBookSample->codec == CODEC_ADPCM) {
                if (curLoadedBook != (*audioBookSample->book).book) {
                    u32 nEntries;
                    switch (noteSubEu->bookOffset) {
                        case 1:
                            curLoadedBook = euUnknownData_80301950 + 1;
                            break;
                        case 2:
                            curLoadedBook = euUnknownData_80301950 + 2;
                            break;
                        case 3:
                        default:
                            curLoadedBook = audioBookSample->book->book;
                            break;
                    }
                    nEntries = 16 * audioBookSample->book->order * audioBookSample->book->npredictors;
                    aLoadADPCM(cmd++, nEntries, VIRTUAL_TO_PHYSICAL2(curLoadedBook));
                }
            }

            while (nAdpcmSamplesProcessed != samplesLenAdjusted) {
                s32 samplesRemaining; // v1
                s32 s0;

                noteFinished = FALSE;
                restart = FALSE;
                s2 = synthesisState->samplePosInt & 0xf;
                samplesRemaining = endPos - synthesisState->samplePosInt;
                nSamplesToProcess = samplesLenAdjusted - nAdpcmSamplesProcessed;

                if (s2 == 0 && synthesisState->restart == FALSE) {
                    s2 = 16;
                }
                s6 = 16 - s2; // a1
                if (nSamplesToProcess < samplesRemaining) {
                    t0 = (nSamplesToProcess - s6 + 0xf) / 16;
                    s0 = t0 * 16;
                    s3 = s6 + s0 - nSamplesToProcess;
                } else {
                    s0 = samplesRemaining - s6;
                    s3 = 0;
                    if (s0 <= 0) {
                        s0 = 0;
                        s6 = samplesRemaining;
                    }
                    t0 = (s0 + 0xf) / 16;
                    if (loopInfo->count != 0) {
                        // Loop around and restart
                        restart = 1;
                    } else {
                        noteFinished = 1;
                    }
                }
                switch (audioBookSample->codec) {
                    case CODEC_ADPCM:
                        unk_s6 = 9;
                        sp88 = 0x10;
                        sp84 = 0;
                        break;
                    case CODEC_S8:
                        unk_s6 = 0x10;
                        sp88 = 0x10;
                        sp84 = 0;
                        break;
                    case CODEC_SKIP: goto skip;
                }
                if (t0 != 0) {
                    temp = (synthesisState->samplePosInt + sp88 - s2) / 16;
                    if (audioBookSample->medium == 0) {
                        v0_2 = sp84 + (temp * unk_s6) + sampleAddr;
                    } else {
                        v0_2 = dma_sample_data((uintptr_t)(sp84 + (temp * unk_s6) + sampleAddr),
                                ALIGN(t0 * unk_s6 + 16, 4), flags, &synthesisState->sampleDmaIndex, audioBookSample->medium);
                    }

                    a3 = ((uintptr_t)v0_2 & 0xf);
                    aligned = ALIGN(t0 * unk_s6 + 16, 4);
                    addr = (DMEM_ADDR_COMPRESSED_ADPCM_DATA - aligned) & 0xffff;
                    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(v0_2 - a3), addr, ALIGN(t0 * unk_s6 + 16, 4));
                } else {
                    s0 = 0;
                    a3 = 0;
                }
                if (synthesisState->restart != FALSE) {
                    aSetLoop(cmd++, VIRTUAL_TO_PHYSICAL2(audioBookSample->loop->state));
                    flags = A_LOOP; // = 2
                    synthesisState->restart = FALSE;
                }
                nSamplesInThisIteration = s0 + s6 - s3;
                if (nAdpcmSamplesProcessed == 0) {
                    switch (audioBookSample->codec) {
                        case CODEC_ADPCM:
                            aligned = ALIGN(t0 * unk_s6 + 16, 4);
                            addr = (DMEM_ADDR_COMPRESSED_ADPCM_DATA - aligned) & 0xffff;
                            aSetBuffer(cmd++, 0, addr + a3, DMEM_ADDR_UNCOMPRESSED_NOTE, s0 * 2);
                            aADPCMdec(cmd++, flags, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->adpcmdecState));
                            break;
                        case CODEC_S8:
                            aligned = ALIGN(t0 * unk_s6 + 16, 4);
                            addr = (DMEM_ADDR_COMPRESSED_ADPCM_DATA - aligned) & 0xffff;
                            aSetBuffer(cmd++, 0, addr + a3, DMEM_ADDR_UNCOMPRESSED_NOTE, s0 * 2);
                            aS8Dec(cmd++, flags, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->adpcmdecState));
                            break;
                    }
                    sp130 = s2 * 2;
                } else {
                    s5Aligned = ALIGN(s5 + 16, 4);
                    switch (audioBookSample->codec) {
                        case CODEC_ADPCM:
                            aligned = ALIGN(t0 * unk_s6 + 16, 4);
                            addr = (DMEM_ADDR_COMPRESSED_ADPCM_DATA - aligned) & 0xffff;
                            aSetBuffer(cmd++, 0, addr + a3, DMEM_ADDR_UNCOMPRESSED_NOTE + s5Aligned, s0 * 2);
                            aADPCMdec(cmd++, flags, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->adpcmdecState));
                            break;
                        case CODEC_S8:
                            aligned = ALIGN(t0 * unk_s6 + 16, 4);
                            addr = (DMEM_ADDR_COMPRESSED_ADPCM_DATA - aligned) & 0xffff;
                            aSetBuffer(cmd++, 0, addr + a3, DMEM_ADDR_UNCOMPRESSED_NOTE + s5Aligned, s0 * 2);
                            aS8Dec(cmd++, flags, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->adpcmdecState));
                            break;
                    }
                    aDMEMMove(cmd++, DMEM_ADDR_UNCOMPRESSED_NOTE + s5Aligned + (s2 * 2), DMEM_ADDR_UNCOMPRESSED_NOTE + s5, (nSamplesInThisIteration) * 2);
                }
                nAdpcmSamplesProcessed += nSamplesInThisIteration;
                switch (flags) {
                    case A_INIT: // = 1
                        sp130 = 0x20;
                        s5 = (s0 + 0x10) * 2;
                        break;
                    case A_LOOP: // = 2
                        s5 = (nSamplesInThisIteration) * 2 + s5;
                        break;
                    default:
                        if (s5 != 0) {
                            s5 = (nSamplesInThisIteration) * 2 + s5;
                        } else {
                            s5 = (s2 + (nSamplesInThisIteration)) * 2;
                        }
                        break;
                }
                flags = 0;
skip:
                if (noteFinished) {
                    aClearBuffer(cmd++, DMEM_ADDR_UNCOMPRESSED_NOTE + s5,
                            (samplesLenAdjusted - nAdpcmSamplesProcessed) * 2);
                    noteSubEu->finished = 1;
                    note->noteSubEu.finished = 1;
                    func_sh_802ed644(updateIndex, noteIndex);
                    break;
                }
                if (restart != 0) {
                    synthesisState->restart = TRUE;
                    synthesisState->samplePosInt = loopInfo->start;
                } else {
                    synthesisState->samplePosInt += nSamplesToProcess;
                }
            }

            switch (nParts) {
                case 1:
                    noteSamplesDmemAddrBeforeResampling = DMEM_ADDR_UNCOMPRESSED_NOTE + sp130;
                    break;
                case 2:
                    switch (curPart) {
                        case 0:
                            aDownsampleHalf(cmd++, ALIGN(samplesLenAdjusted / 2, 3), DMEM_ADDR_UNCOMPRESSED_NOTE + sp130, DMEM_ADDR_RESAMPLED);
                            resampledTempLen = samplesLenAdjusted;
                            noteSamplesDmemAddrBeforeResampling = DMEM_ADDR_RESAMPLED;
                            if (noteSubEu->finished != FALSE) {
                                aClearBuffer(cmd++, noteSamplesDmemAddrBeforeResampling + resampledTempLen, samplesLenAdjusted + 0x10);
                            }
                            break;
                        case 1:
                            aDownsampleHalf(cmd++, ALIGN(samplesLenAdjusted / 2, 3), DMEM_ADDR_UNCOMPRESSED_NOTE + sp130, resampledTempLen + DMEM_ADDR_RESAMPLED);
                            break;
                    }
            }
            if (noteSubEu->finished != FALSE) {
                break;
            }
        }
    }
    flags = 0;
    if (noteSubEu->needsInit == TRUE) {
        flags = A_INIT;
        noteSubEu->needsInit = FALSE;
    }
    flags = flags | sp56;
    cmd = final_resample(cmd, synthesisState, bufLen * 2, resamplingRateFixedPoint,
            noteSamplesDmemAddrBeforeResampling, flags);
    if ((flags & 1) != 0) {
        flags = 1;
    }

    if (noteSubEu->filter) {
        aFilter(cmd++, 0x02, bufLen * 2, noteSubEu->filter);
        aFilter(cmd++, flags, DMEM_ADDR_TEMP, synthesisState->synthesisBuffers->filterBuffer);

    }

    if (noteSubEu->bookOffset == 3) {
        aUnknown25(cmd++, 0, bufLen * 2, DMEM_ADDR_TEMP, DMEM_ADDR_TEMP);
    }

    synthesisVolume = noteSubEu->synthesisVolume;
    if (synthesisVolume != 0) {
        if (synthesisVolume < 0x10) {
            synthesisVolume = 0x10;
        }

        aHiLoGain(cmd++, synthesisVolume, (bufLen + 0x10) * 2, DMEM_ADDR_TEMP);
    }

    if (noteSubEu->headsetPanRight != 0 || synthesisState->prevHeadsetPanRight != 0) {
        leftRight = 1;
    } else if (noteSubEu->headsetPanLeft != 0 || synthesisState->prevHeadsetPanLeft != 0) {
        leftRight = 2;
    } else {
        leftRight = 0;
    }
    cmd = process_envelope(cmd, noteSubEu, synthesisState, bufLen, DMEM_ADDR_TEMP, leftRight, flags);
    if (noteSubEu->usesHeadsetPanEffects) {
        if ((flags & 1) == 0) {
            flags = 0;
        }
        cmd = note_apply_headset_pan_effects(cmd, noteSubEu, synthesisState, bufLen * 2, flags, leftRight);
    }

    return cmd;
}

u64 *load_wave_samples(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s32 nSamplesToLoad) {
    s32 a3;
    s32 repeats;
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(noteSubEu->sound.samples),
                DMEM_ADDR_UNCOMPRESSED_NOTE, 128);

    synthesisState->samplePosInt &= 0x3f;
    a3 = 64 - synthesisState->samplePosInt;
    if (a3 < nSamplesToLoad) {
        repeats = (nSamplesToLoad - a3 + 63) / 64;
        if (repeats != 0) {
            aDuplicate(cmd++,
                    /*dmemin*/ DMEM_ADDR_UNCOMPRESSED_NOTE,
                    /*dmemout*/ DMEM_ADDR_UNCOMPRESSED_NOTE + 128,
                    /*copies*/ repeats);
        }
    }
    return cmd;
}

u64 *final_resample(u64 *cmd, struct NoteSynthesisState *synthesisState, s32 count, u16 pitch, u16 dmemIn, u32 flags) {
    if (pitch == 0) {
        aClearBuffer(cmd++, DMEM_ADDR_TEMP, count);
    } else {
        aSetBuffer(cmd++, /*flags*/ 0, dmemIn, /*dmemout*/ DMEM_ADDR_TEMP, count);
        aResample(cmd++, flags, pitch, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->finalResampleState));
    }
    return cmd;
}

u64 *process_envelope(u64 *cmd, struct NoteSubEu *note, struct NoteSynthesisState *synthesisState, s32 nSamples, u16 inBuf, s32 headsetPanSettings, UNUSED u32 flags) {
    u16 sourceRight;
    u16 sourceLeft;
    u16 targetLeft;
    u16 targetRight;
    s16 rampLeft;
    s16 rampRight;
    s32 sourceReverbVol;
    s16 rampReverb;
    s32 reverbVolDiff = 0;

    sourceLeft = synthesisState->curVolLeft;
    sourceRight = synthesisState->curVolRight;
    targetLeft = note->targetVolLeft;
    targetRight = note->targetVolRight;
    targetLeft <<= 4;
    targetRight <<= 4;

    if (targetLeft != sourceLeft) {
        rampLeft = (targetLeft - sourceLeft) / (nSamples >> 3);
    } else {
        rampLeft = 0;
    }
    if (targetRight != sourceRight) {
        rampRight = (targetRight - sourceRight) / (nSamples >> 3);
    } else {
        rampRight = 0;
    }

    sourceReverbVol = synthesisState->reverbVol;
    if (note->reverbVol != sourceReverbVol) {
        reverbVolDiff = ((note->reverbVol & 0x7f) - (sourceReverbVol & 0x7f)) << 9;
        rampReverb = reverbVolDiff / (nSamples >> 3);
        synthesisState->reverbVol = note->reverbVol;
    } else {
        rampReverb = 0;
    }
    synthesisState->curVolLeft = sourceLeft + rampLeft * (nSamples >> 3);
    synthesisState->curVolRight = sourceRight + rampRight * (nSamples >> 3);

    if (note->usesHeadsetPanEffects) {
        aClearBuffer(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DEFAULT_LEN_1CH);
        aEnvSetup1(cmd++, (sourceReverbVol & 0x7f) * 2, rampReverb, rampLeft, rampRight);
        aEnvSetup2(cmd++, sourceLeft, sourceRight);

        switch (headsetPanSettings) {
            case 1:
                aEnvMixer(cmd++,
                    inBuf, nSamples,
                    (sourceReverbVol & 0x80) >> 7,
                    note->stereoStrongRight, note->stereoStrongLeft,
                    DMEM_ADDR_NOTE_PAN_TEMP,
                    DMEM_ADDR_RIGHT_CH,
                    DMEM_ADDR_WET_LEFT_CH,
                    DMEM_ADDR_WET_RIGHT_CH);
                break;
            case 2:
                aEnvMixer(cmd++,
                    inBuf, nSamples,
                    (sourceReverbVol & 0x80) >> 7,
                    note->stereoStrongRight, note->stereoStrongLeft,
                    DMEM_ADDR_LEFT_CH,
                    DMEM_ADDR_NOTE_PAN_TEMP,
                    DMEM_ADDR_WET_LEFT_CH,
                    DMEM_ADDR_WET_RIGHT_CH);
                break;
            default:
                aEnvMixer(cmd++,
                    inBuf, nSamples,
                    (sourceReverbVol & 0x80) >> 7,
                    note->stereoStrongRight, note->stereoStrongLeft,
                    DMEM_ADDR_LEFT_CH,
                    DMEM_ADDR_RIGHT_CH,
                    DMEM_ADDR_WET_LEFT_CH,
                    DMEM_ADDR_WET_RIGHT_CH);
                break;
        }
    } else {
        aEnvSetup1(cmd++, (sourceReverbVol & 0x7f) * 2, rampReverb, rampLeft, rampRight);
        aEnvSetup2(cmd++, sourceLeft, sourceRight);
        aEnvMixer(cmd++,
                inBuf, nSamples,
                (sourceReverbVol & 0x80) >> 7,
                note->stereoStrongRight, note->stereoStrongLeft,
                DMEM_ADDR_LEFT_CH,
                DMEM_ADDR_RIGHT_CH,
                DMEM_ADDR_WET_LEFT_CH,
                DMEM_ADDR_WET_RIGHT_CH);
    }
    return cmd;
}

u64 *note_apply_headset_pan_effects(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *note, s32 bufLen, s32 flags, s32 leftRight) {
    u16 dest;
    u16 pitch;
    u8 prevPanShift;
    u8 panShift;
    UNUSED u8 unkDebug;

    switch (leftRight) {
        case 1:
            dest = DMEM_ADDR_LEFT_CH;
            panShift = noteSubEu->headsetPanRight;
            note->prevHeadsetPanLeft = 0;
            prevPanShift = note->prevHeadsetPanRight;
            note->prevHeadsetPanRight = panShift;
            break;
        case 2:
            dest = DMEM_ADDR_RIGHT_CH;
            panShift = noteSubEu->headsetPanLeft;
            note->prevHeadsetPanRight = 0;

            prevPanShift = note->prevHeadsetPanLeft;
            note->prevHeadsetPanLeft = panShift;
            break;
        default:
            return cmd;
    }

    if (flags != 1) { // A_INIT?
        // Slightly adjust the sample rate in order to fit a change in pan shift
        if (panShift != prevPanShift) {
            pitch = (((bufLen << 0xf) / 2) - 1) / ((bufLen + panShift - prevPanShift - 2) / 2);
            aSetBuffer(cmd++, 0, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP, (bufLen + panShift) - prevPanShift);
            aResampleZoh(cmd++, pitch, 0);
        } else {
            aDMEMMove(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP, bufLen);
        }

        if (prevPanShift != 0) {
            aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panSamplesBuffer),
                        DMEM_ADDR_NOTE_PAN_TEMP, ALIGN(prevPanShift, 4));
            aDMEMMove(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_NOTE_PAN_TEMP + prevPanShift, bufLen + panShift - prevPanShift);
        } else {
            aDMEMMove(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_NOTE_PAN_TEMP, bufLen + panShift);
        }
    } else {
        // Just shift right
        aDMEMMove(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP, bufLen);
        aClearBuffer(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, panShift);
        aDMEMMove(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_NOTE_PAN_TEMP + panShift, bufLen);
    }

    if (panShift) {
        // Save excessive samples for next iteration
        aSaveBuffer(cmd++, DMEM_ADDR_NOTE_PAN_TEMP + bufLen,
                    VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panSamplesBuffer), ALIGN(panShift, 4));
    }

    aAddMixer(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, dest, (bufLen + 0x3f) & 0xffc0);

    return cmd;
}
#endif
