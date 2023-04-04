#ifndef VERSION_SH
#include <ultra64.h>

#include "synthesis.h"
#include "heap.h"
#include "data.h"
#include "load.h"
#include "seqplayer.h"
#include "internal.h"
#include "external.h"


#define DMEM_ADDR_TEMP 0x0
#define DMEM_ADDR_RESAMPLED 0x20
#define DMEM_ADDR_RESAMPLED2 0x160
#define DMEM_ADDR_UNCOMPRESSED_NOTE 0x180
#define DMEM_ADDR_NOTE_PAN_TEMP 0x200
#define DMEM_ADDR_STEREO_STRONG_TEMP_DRY 0x200
#define DMEM_ADDR_STEREO_STRONG_TEMP_WET 0x340
#define DMEM_ADDR_COMPRESSED_ADPCM_DATA 0x3f0
#define DMEM_ADDR_LEFT_CH 0x4c0
#define DMEM_ADDR_RIGHT_CH 0x600
#define DMEM_ADDR_WET_LEFT_CH 0x740
#define DMEM_ADDR_WET_RIGHT_CH 0x880

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
#ifdef VERSION_EU
u64 *synthesis_process_note(struct Note *note, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s16 *aiBuf, s32 bufLen, u64 *cmd);
u64 *load_wave_samples(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s32 nSamplesToLoad);
u64 *final_resample(u64 *cmd, struct NoteSynthesisState *synthesisState, s32 count, u16 pitch, u16 dmemIn, u32 flags);
u64 *process_envelope(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s32 nSamples, u16 inBuf, s32 headsetPanSettings, u32 flags);
u64 *note_apply_headset_pan_effects(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *note, s32 bufLen, s32 flags, s32 leftRight);
#else
u64 *synthesis_process_notes(s16 *aiBuf, s32 bufLen, u64 *cmd);
u64 *load_wave_samples(u64 *cmd, struct Note *note, s32 nSamplesToLoad);
u64 *final_resample(u64 *cmd, struct Note *note, s32 count, u16 pitch, u16 dmemIn, u32 flags);
u64 *process_envelope(u64 *cmd, struct Note *note, s32 nSamples, u16 inBuf, s32 headsetPanSettings,
                      u32 flags);
u64 *process_envelope_inner(u64 *cmd, struct Note *note, s32 nSamples, u16 inBuf,
                            s32 headsetPanSettings, struct VolumeChange *vol);
u64 *note_apply_headset_pan_effects(u64 *cmd, struct Note *note, s32 bufLen, s32 flags, s32 leftRight);
#endif

#ifdef VERSION_EU
struct SynthesisReverb gSynthesisReverbs[4];
u8 sAudioSynthesisPad[0x10];
#else
struct SynthesisReverb gSynthesisReverb;
u8 sAudioSynthesisPad[0x20];
#endif

#ifdef VERSION_EU
s16 gVolume;
s8 gUseReverb;
s8 gNumSynthesisReverbs;
struct NoteSubEu *gNoteSubsEu;
#endif

#ifdef VERSION_EU
f32 gLeftVolRampings[3][1024];
f32 gRightVolRampings[3][1024];
f32 *gCurrentLeftVolRamping; // Points to any of the three left buffers above
f32 *gCurrentRightVolRamping; // Points to any of the three right buffers above

u8 audioString1[] = "pitch %x: delaybytes %d : olddelay %d\n";
u8 audioString2[] = "cont %x: delaybytes %d : olddelay %d\n";
#endif

#ifdef VERSION_EU
// Equivalent functionality as the US/JP version,
// just that the reverb structure is chosen from an array with index
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
#else
void prepare_reverb_ring_buffer(s32 chunkLen, u32 updateIndex) {
    struct ReverbRingBufferItem *item;
    s32 srcPos;
    s32 dstPos;
    s32 nSamples;
    s32 numSamplesAfterDownsampling;
    s32 excessiveSamples;
    if (gReverbDownsampleRate != 1) {
        if (gSynthesisReverb.framesLeftToIgnore == 0) {
            // Now that the RSP has finished, downsample the samples produced two frames ago by skipping
            // samples.
            item = &gSynthesisReverb.items[gSynthesisReverb.curFrame][updateIndex];

            // Touches both left and right since they are adjacent in memory
            osInvalDCache(item->toDownsampleLeft, DEFAULT_LEN_2CH);

            for (srcPos = 0, dstPos = 0; dstPos < item->lengthA / 2;
                 srcPos += gReverbDownsampleRate, dstPos++) {
                gSynthesisReverb.ringBuffer.left[dstPos + item->startPos] =
                    item->toDownsampleLeft[srcPos];
                gSynthesisReverb.ringBuffer.right[dstPos + item->startPos] =
                    item->toDownsampleRight[srcPos];
            }
            for (dstPos = 0; dstPos < item->lengthB / 2; srcPos += gReverbDownsampleRate, dstPos++) {
                gSynthesisReverb.ringBuffer.left[dstPos] = item->toDownsampleLeft[srcPos];
                gSynthesisReverb.ringBuffer.right[dstPos] = item->toDownsampleRight[srcPos];
            }
        }
    }
    item = &gSynthesisReverb.items[gSynthesisReverb.curFrame][updateIndex];

    numSamplesAfterDownsampling = chunkLen / gReverbDownsampleRate;
    if (((numSamplesAfterDownsampling + gSynthesisReverb.nextRingBufferPos) - gSynthesisReverb.bufSizePerChannel) < 0) {
        // There is space in the ring buffer before it wraps around
        item->lengthA = numSamplesAfterDownsampling * 2;
        item->lengthB = 0;
        item->startPos = (s32) gSynthesisReverb.nextRingBufferPos;
        gSynthesisReverb.nextRingBufferPos += numSamplesAfterDownsampling;
    } else {
        // Ring buffer wrapped around
        excessiveSamples =
            (numSamplesAfterDownsampling + gSynthesisReverb.nextRingBufferPos) - gSynthesisReverb.bufSizePerChannel;
        nSamples = numSamplesAfterDownsampling - excessiveSamples;
        item->lengthA = nSamples * 2;
        item->lengthB = excessiveSamples * 2;
        item->startPos = gSynthesisReverb.nextRingBufferPos;
        gSynthesisReverb.nextRingBufferPos = excessiveSamples;
    }
    // These fields are never read later
    item->numSamplesAfterDownsampling = numSamplesAfterDownsampling;
    item->chunkLen = chunkLen;
}
#endif

#ifdef VERSION_EU
u64 *synthesis_load_reverb_ring_buffer(u64 *cmd, u16 addr, u16 srcOffset, s32 len, s32 reverbIndex) {
    aSetBuffer(cmd++, 0, addr, 0, len);
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.left[srcOffset]));

    aSetBuffer(cmd++, 0, addr + DEFAULT_LEN_1CH, 0, len);
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.right[srcOffset]));

    return cmd;
}
#endif

#ifdef VERSION_EU
u64 *synthesis_save_reverb_ring_buffer(u64 *cmd, u16 addr, u16 destOffset, s32 len, s32 reverbIndex) {
    aSetBuffer(cmd++, 0, 0, addr, len);
    aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.left[destOffset]));

    aSetBuffer(cmd++, 0, 0, addr + DEFAULT_LEN_1CH, len);
    aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(&gSynthesisReverbs[reverbIndex].ringBuffer.right[destOffset]));

    return cmd;
}
#endif

#ifdef VERSION_EU
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
#endif

#ifndef VERSION_EU
s32 get_volume_ramping(u16 sourceVol, u16 targetVol, s32 arg2) {
    // This roughly computes 2^16 * (targetVol / sourceVol) ^ (8 / arg2),
    // but with discretizations of targetVol, sourceVol and arg2.
    f32 ret;
    switch (arg2) {
        default:
            ret = gVolRampingLhs136[targetVol >> 8] * gVolRampingRhs136[sourceVol >> 8];
            break;
        case 128:
            ret = gVolRampingLhs128[targetVol >> 8] * gVolRampingRhs128[sourceVol >> 8];
            break;
        case 136:
            ret = gVolRampingLhs136[targetVol >> 8] * gVolRampingRhs136[sourceVol >> 8];
            break;
        case 144:
            ret = gVolRampingLhs144[targetVol >> 8] * gVolRampingRhs144[sourceVol >> 8];
            break;
    }
    return ret;
}
#endif

#ifdef VERSION_EU
// TODO: (Scrub C) pointless mask and whitespace
u64 *synthesis_execute(u64 *cmdBuf, s32 *writtenCmds, s16 *aiBuf, s32 bufLen) {
    s32 i, j;
    f32 *leftVolRamp;
    f32 *rightVolRamp;
    u32 *aiBufPtr;
    u64 *cmd = cmdBuf;
    s32 chunkLen;
    s32 nextVolRampTable;

    for (i = gAudioBufferParameters.updatesPerFrame; i > 0; i--) {
        process_sequences(i - 1);
        synthesis_load_note_subs_eu(gAudioBufferParameters.updatesPerFrame - i);
    }
    aSegment(cmd++, 0, 0);
    aiBufPtr = (u32 *) aiBuf;
    for (i = gAudioBufferParameters.updatesPerFrame; i > 0; i--) {
        if (i == 1) {
#pragma GCC diagnostic push
#if defined(__clang__)
#pragma GCC diagnostic ignored "-Wself-assign"
#endif
            // self-assignment has no affect when added here, could possibly simplify a macro definition
            chunkLen = bufLen; nextVolRampTable = nextVolRampTable; leftVolRamp = gLeftVolRampings[nextVolRampTable]; rightVolRamp = gRightVolRampings[nextVolRampTable & 0xFFFFFFFF];
#pragma GCC diagnostic pop
        } else {
            if (bufLen / i >= gAudioBufferParameters.samplesPerUpdateMax) {
                chunkLen = gAudioBufferParameters.samplesPerUpdateMax; nextVolRampTable = 2; leftVolRamp = gLeftVolRampings[2]; rightVolRamp = gRightVolRampings[2];
            } else if (bufLen / i <= gAudioBufferParameters.samplesPerUpdateMin) {
                chunkLen = gAudioBufferParameters.samplesPerUpdateMin; nextVolRampTable = 0; leftVolRamp = gLeftVolRampings[0]; rightVolRamp = gRightVolRampings[0];
            } else {
                chunkLen = gAudioBufferParameters.samplesPerUpdate; nextVolRampTable = 1; leftVolRamp = gLeftVolRampings[1]; rightVolRamp = gRightVolRampings[1];
            }
        }
        gCurrentLeftVolRamping = leftVolRamp;
        gCurrentRightVolRamping = rightVolRamp;
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
#else
// bufLen will be divisible by 16
u64 *synthesis_execute(u64 *cmdBuf, s32 *writtenCmds, s16 *aiBuf, s32 bufLen) {
    s32 chunkLen;
    s32 i;
    u32 *aiBufPtr = (u32 *) aiBuf;
    u64 *cmd = cmdBuf + 1;
    s32 v0;

    aSegment(cmdBuf, 0, 0);

    for (i = gAudioUpdatesPerFrame; i > 0; i--) {
        if (i == 1) {
            // 'bufLen' will automatically be divisible by 8, no need to round
            chunkLen = bufLen;
        } else {
            v0 = bufLen / i;
            // chunkLen = v0 rounded to nearest multiple of 8
            chunkLen = v0 - (v0 & 7);

            if ((v0 & 7) >= 4) {
                chunkLen += 8;
            }
        }
        process_sequences(i - 1);
        if (gSynthesisReverb.useReverb != 0) {
            prepare_reverb_ring_buffer(chunkLen, gAudioUpdatesPerFrame - i);
        }
        cmd = synthesis_do_one_audio_update((s16 *) aiBufPtr, chunkLen, cmd, gAudioUpdatesPerFrame - i);
        bufLen -= chunkLen;
        aiBufPtr += chunkLen;
    }
    if (gSynthesisReverb.framesLeftToIgnore != 0) {
        gSynthesisReverb.framesLeftToIgnore--;
    }
    gSynthesisReverb.curFrame ^= 1;
    *writtenCmds = cmd - cmdBuf;
    return cmd;
}
#endif


#ifdef VERSION_EU
u64 *synthesis_resample_and_mix_reverb(u64 *cmd, s32 bufLen, s16 reverbIndex, s16 updateIndex) {
    struct ReverbRingBufferItem *item;
    s16 startPad;
    s16 paddedLengthA;

    item = &gSynthesisReverbs[reverbIndex].items[gSynthesisReverbs[reverbIndex].curFrame][updateIndex];

    aClearBuffer(cmd++, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_2CH);
    if (gSynthesisReverbs[reverbIndex].downsampleRate == 1) {
        cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_WET_LEFT_CH, item->startPos, item->lengthA, reverbIndex);
        if (item->lengthB != 0) {
            cmd = synthesis_load_reverb_ring_buffer(cmd, DMEM_ADDR_WET_LEFT_CH + item->lengthA, 0, item->lengthB, reverbIndex);
        }
        aSetBuffer(cmd++, 0, 0, 0, DEFAULT_LEN_2CH);
        aMix(cmd++, 0, 0x7fff, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_LEFT_CH);
        aMix(cmd++, 0, 0x8000 + gSynthesisReverbs[reverbIndex].reverbGain, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_WET_LEFT_CH);
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

        aSetBuffer(cmd++, 0, 0, 0, DEFAULT_LEN_2CH);
        aMix(cmd++, 0, 0x7fff, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_LEFT_CH);
        aMix(cmd++, 0, 0x8000 + gSynthesisReverbs[reverbIndex].reverbGain, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_WET_LEFT_CH);
    }
    return cmd;
}

u64 *synthesis_save_reverb_samples(u64 *cmd, s16 reverbIndex, s16 updateIndex) {
    struct ReverbRingBufferItem *item;

    item = &gSynthesisReverbs[reverbIndex].items[gSynthesisReverbs[reverbIndex].curFrame][updateIndex];
    if (gSynthesisReverbs[reverbIndex].useReverb != 0) {
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
                aSetBuffer(cmd++, 0, 0, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_2CH);
                aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(gSynthesisReverbs[reverbIndex].items[gSynthesisReverbs[reverbIndex].curFrame][updateIndex].toDownsampleLeft));
                gSynthesisReverbs[reverbIndex].resampleFlags = 0;
                break;
        }
    }
    return cmd;
}
#endif

#ifdef VERSION_EU
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
                cmd = synthesis_process_note(&gNotes[noteIndices[i]],
                                             &gNoteSubsEu[temp + noteIndices[i]],
                                             &gNotes[noteIndices[i]].synthesisState,
                                             aiBuf, bufLen, cmd);
                continue;
            } else {
                break;
            }
        }
        if (gSynthesisReverbs[j].useReverb != 0) {
            cmd = synthesis_save_reverb_samples(cmd, j, updateIndex);
        }
    }
    for (; i < notePos; i++) {
        temp = updateIndex * gMaxSimultaneousNotes;
        if (IS_BANK_LOAD_COMPLETE(gNoteSubsEu[temp + noteIndices[i]].bankId) == TRUE) {
            cmd = synthesis_process_note(&gNotes[noteIndices[i]],
                                         &gNoteSubsEu[temp + noteIndices[i]],
                                         &gNotes[noteIndices[i]].synthesisState,
                                         aiBuf, bufLen, cmd);
        } else {
            gAudioErrorFlags = (gNoteSubsEu[temp + noteIndices[i]].bankId + (i << 8)) + 0x10000000;
        }
    }

    temp = bufLen * 2;
    aSetBuffer(cmd++, 0, 0, DMEM_ADDR_TEMP, temp);
    aInterleave(cmd++, DMEM_ADDR_LEFT_CH, DMEM_ADDR_RIGHT_CH);
    aSetBuffer(cmd++, 0, 0, DMEM_ADDR_TEMP, temp * 2);
    aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(aiBuf));
    return cmd;
}
#else
u64 *synthesis_do_one_audio_update(s16 *aiBuf, s32 bufLen, u64 *cmd, s32 updateIndex) {
    UNUSED s32 pad1[1];
    s16 ra;
    s16 t4;
    UNUSED s32 pad[2];
    struct ReverbRingBufferItem *v1;
    UNUSED s32 pad2[1];
    s16 temp;

    v1 = &gSynthesisReverb.items[gSynthesisReverb.curFrame][updateIndex];

    if (gSynthesisReverb.useReverb == 0) {
        aClearBuffer(cmd++, DMEM_ADDR_LEFT_CH, DEFAULT_LEN_2CH);
        cmd = synthesis_process_notes(aiBuf, bufLen, cmd);
    } else {
        if (gReverbDownsampleRate == 1) {
            // Put the oldest samples in the ring buffer into the wet channels
            aSetLoadBufferPair(cmd++, 0, v1->startPos);
            if (v1->lengthB != 0) {
                // Ring buffer wrapped
                aSetLoadBufferPair(cmd++, v1->lengthA, 0);
                temp = 0;
            }

            // Use the reverb sound as initial sound for this audio update
            aDMEMMove(cmd++, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_LEFT_CH, DEFAULT_LEN_2CH);

            // (Hopefully) lower the volume of the wet channels. New reverb will later be mixed into
            // these channels.
            aSetBuffer(cmd++, 0, 0, 0, DEFAULT_LEN_2CH);
            // 0x8000 here is -100%
            aMix(cmd++, 0, /*gain*/ 0x8000 + gSynthesisReverb.reverbGain, /*in*/ DMEM_ADDR_WET_LEFT_CH,
                 /*out*/ DMEM_ADDR_WET_LEFT_CH);
        } else {
            // Same as above but upsample the previously downsampled samples used for reverb first
            temp = 0; //! jesus christ
            t4 = (v1->startPos & 7) * 2;
            ra = ALIGN(v1->lengthA + t4, 4);
            aSetLoadBufferPair(cmd++, 0, v1->startPos - t4 / 2);
            if (v1->lengthB != 0) {
                // Ring buffer wrapped
                aSetLoadBufferPair(cmd++, ra, 0);
                //! We need an empty statement (even an empty ';') here to make the function match (because IDO).
                //! However, copt removes extraneous statements and dead code. So we need to trick copt
                //! into thinking 'temp' could be undefined, and luckily the compiler optimizes out the
                //! useless assignment.
                ra = ra + temp;
            }
            aSetBuffer(cmd++, 0, t4 + DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_LEFT_CH, bufLen << 1);
            aResample(cmd++, gSynthesisReverb.resampleFlags, (u16) gSynthesisReverb.resampleRate, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.resampleStateLeft));
            aSetBuffer(cmd++, 0, t4 + DMEM_ADDR_WET_RIGHT_CH, DMEM_ADDR_RIGHT_CH, bufLen << 1);
            aResample(cmd++, gSynthesisReverb.resampleFlags, (u16) gSynthesisReverb.resampleRate, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.resampleStateRight));
            aSetBuffer(cmd++, 0, 0, 0, DEFAULT_LEN_2CH);
            aMix(cmd++, 0, /*gain*/ 0x8000 + gSynthesisReverb.reverbGain, /*in*/ DMEM_ADDR_LEFT_CH, /*out*/ DMEM_ADDR_LEFT_CH);
            aDMEMMove(cmd++, DMEM_ADDR_LEFT_CH, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_2CH);
        }
        cmd = synthesis_process_notes(aiBuf, bufLen, cmd);
        if (gReverbDownsampleRate == 1) {
            aSetSaveBufferPair(cmd++, 0, v1->lengthA, v1->startPos);
            if (v1->lengthB != 0) {
                // Ring buffer wrapped
                aSetSaveBufferPair(cmd++, v1->lengthA, v1->lengthB, 0);
            }
        } else {
            // Downsampling is done later by CPU when RSP is done, therefore we need to have double
            // buffering. Left and right buffers are adjacent in memory.
            aSetBuffer(cmd++, 0, 0, DMEM_ADDR_WET_LEFT_CH, DEFAULT_LEN_2CH);
            aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(gSynthesisReverb.items[gSynthesisReverb.curFrame][updateIndex].toDownsampleLeft));
            gSynthesisReverb.resampleFlags = 0;
        }
    }
    return cmd;
}
#endif

#ifdef VERSION_EU
// Processes just one note, not all
u64 *synthesis_process_note(struct Note *note, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, UNUSED s16 *aiBuf, s32 bufLen, u64 *cmd) {
    UNUSED s32 pad0[3];
#else
u64 *synthesis_process_notes(s16 *aiBuf, s32 bufLen, u64 *cmd) {
    s32 noteIndex;                           // sp174
    struct Note *note;                       // s7
    UNUSED u8 pad0[0x08];
#endif
    struct AudioBankSample *audioBookSample; // sp164, sp138
    struct AdpcmLoop *loopInfo;              // sp160, sp134
    s16 *curLoadedBook = NULL;               // sp154, sp130
#ifdef VERSION_EU
    UNUSED u8 padEU[0x04];
#endif
    UNUSED u8 pad8[0x04];
#ifndef VERSION_EU
    u16 resamplingRateFixedPoint;            // sp5c, sp11A
#endif
    s32 noteFinished;                        // 150 t2, sp124
    s32 restart;                             // 14c t3, sp120
    s32 flags;                               // sp148, sp11C
#ifdef VERSION_EU
    u16 resamplingRateFixedPoint;            // sp5c, sp11A
#endif
    UNUSED u8 pad7[0x0c];                    // sp100
    UNUSED s32 tempBufLen;
#ifdef VERSION_EU
    s32 sp130;  //sp128, sp104
    UNUSED u32 pad9;
#else
    UNUSED u32 pad9;
    s32 sp130;  //sp128, sp104
#endif
    s32 nAdpcmSamplesProcessed; // signed required for US
    s32 t0;
#ifdef VERSION_EU
    u8 *sampleAddr;                          // sp120, spF4
    s32 s6;
#else
    s32 s6;
    u8 *sampleAddr;                          // sp120, spF4
#endif

#ifdef VERSION_EU
    s32 samplesLenAdjusted; // 108,      spEC
    // Might have been used to store (samplesLenFixedPoint >> 0x10), but doing so causes strange
    // behavior with the break near the end of the loop, causing US and JP to need a goto instead
    UNUSED s32 samplesLenInt;
    s32 endPos;             // sp110,    spE4
    s32 nSamplesToProcess;  // sp10c/a0, spE0
    s32 s2;
#else
    // Might have been used to store (samplesLenFixedPoint >> 0x10), but doing so causes strange
    // behavior with the break near the end of the loop, causing US and JP to need a goto instead
    UNUSED s32 samplesLenInt;
    s32 samplesLenAdjusted; // 108
    s32 s2;
    s32 endPos;             // sp110,    spE4
    s32 nSamplesToProcess;  // sp10c/a0, spE0
#endif

    s32 leftRight;
    s32 s3;
    s32 s5; //s4

    u32 samplesLenFixedPoint;    // v1_1
    s32 nSamplesInThisIteration; // v1_2
    u32 a3;
#ifndef VERSION_EU
    s32 t9;
#endif
    u8 *v0_2;
    s32 nParts;                 // spE8, spBC
    s32 curPart;                // spE4, spB8

#ifndef VERSION_EU
    f32 resamplingRate; // f12
#endif
    s32 temp;

#ifdef VERSION_EU
    s32 s5Aligned;
#endif
    s32 resampledTempLen;                    // spD8, spAC
    u16 noteSamplesDmemAddrBeforeResampling; // spD6, spAA


#ifndef VERSION_EU
    for (noteIndex = 0; noteIndex < gMaxSimultaneousNotes; noteIndex++) {
        note = &gNotes[noteIndex];
#ifdef VERSION_US
        //! This function requires note->enabled to be volatile, but it breaks other functions like note_enable.
        //! Casting to a struct with just the volatile bitfield works, but there may be a better way to match.
        if (((struct vNote *)note)->enabled && IS_BANK_LOAD_COMPLETE(note->bankId) == FALSE) {
#else
        if (IS_BANK_LOAD_COMPLETE(note->bankId) == FALSE) {
#endif
            gAudioErrorFlags = (note->bankId << 8) + noteIndex + 0x1000000;
        } else if (((struct vNote *)note)->enabled) {
#else
        if (note->noteSubEu.enabled == FALSE) {
            return cmd;
        } else {
#endif
            flags = 0;
#ifdef VERSION_EU
            tempBufLen = bufLen;
#endif

#ifdef VERSION_EU
            if (noteSubEu->needsInit == TRUE) {
#else
            if (note->needsInit == TRUE) {
#endif
                flags = A_INIT;
#ifndef VERSION_EU
                note->samplePosInt = 0;
                note->samplePosFrac = 0;
#else
                synthesisState->restart = FALSE;
                synthesisState->samplePosInt = 0;
                synthesisState->samplePosFrac = 0;
                synthesisState->curVolLeft = 1;
                synthesisState->curVolRight = 1;
                synthesisState->prevHeadsetPanRight = 0;
                synthesisState->prevHeadsetPanLeft = 0;
#endif
            }

#ifndef VERSION_EU
            if (note->frequency < US_FLOAT(2.0)) {
                nParts = 1;
                if (note->frequency > US_FLOAT(1.99996)) {
                    note->frequency = US_FLOAT(1.99996);
                }
                resamplingRate = note->frequency;
            } else {
                // If frequency is > 2.0, the processing must be split into two parts
                nParts = 2;
                if (note->frequency >= US_FLOAT(3.99993)) {
                    note->frequency = US_FLOAT(3.99993);
                }
                resamplingRate = note->frequency * US_FLOAT(.5);
            }

            resamplingRateFixedPoint = (u16)(s32)(resamplingRate * 32768.0f);
            samplesLenFixedPoint = note->samplePosFrac + (resamplingRateFixedPoint * bufLen) * 2;
            note->samplePosFrac = samplesLenFixedPoint & 0xFFFF; // 16-bit store, can't reuse
#else
            resamplingRateFixedPoint = noteSubEu->resamplingRateFixedPoint;
            nParts = noteSubEu->hasTwoAdpcmParts + 1;
            samplesLenFixedPoint = (resamplingRateFixedPoint * tempBufLen * 2) + synthesisState->samplePosFrac;
            synthesisState->samplePosFrac = samplesLenFixedPoint & 0xFFFF;
#endif

#ifdef VERSION_EU
            if (noteSubEu->isSyntheticWave) {
                cmd = load_wave_samples(cmd, noteSubEu, synthesisState, samplesLenFixedPoint >> 0x10);
                noteSamplesDmemAddrBeforeResampling = (synthesisState->samplePosInt * 2) + DMEM_ADDR_UNCOMPRESSED_NOTE;
                synthesisState->samplePosInt += samplesLenFixedPoint >> 0x10;
            }
#else
            if (note->sound == NULL) {
                // A wave synthesis note (not ADPCM)

                cmd = load_wave_samples(cmd, note, samplesLenFixedPoint >> 0x10);
                noteSamplesDmemAddrBeforeResampling = DMEM_ADDR_UNCOMPRESSED_NOTE + note->samplePosInt * 2;
                note->samplePosInt += (samplesLenFixedPoint >> 0x10);
                flags = 0;
            }
#endif
            else {
                // ADPCM note

#ifdef VERSION_EU
                audioBookSample = noteSubEu->sound.audioBankSound->sample;
#else
                audioBookSample = note->sound->sample;
#endif

                loopInfo = audioBookSample->loop;
                endPos = loopInfo->end;
                sampleAddr = audioBookSample->sampleAddr;
                resampledTempLen = 0;
                for (curPart = 0; curPart < nParts; curPart++) {
                    nAdpcmSamplesProcessed = 0; // s8
                    s5 = 0;                     // s4

                    if (nParts == 1) {
                        samplesLenAdjusted = samplesLenFixedPoint >> 0x10;
                    } else if ((samplesLenFixedPoint >> 0x10) & 1) {
                        samplesLenAdjusted = ((samplesLenFixedPoint >> 0x10) & ~1) + (curPart * 2);
                    }
                    else {
                        samplesLenAdjusted = (samplesLenFixedPoint >> 0x10);
                    }

                    if (curLoadedBook != audioBookSample->book->book) {
                        u32 nEntries; // v1
                        curLoadedBook = audioBookSample->book->book;
#ifdef VERSION_EU
                        nEntries = 16 * audioBookSample->book->order * audioBookSample->book->npredictors;
                        aLoadADPCM(cmd++, nEntries, VIRTUAL_TO_PHYSICAL2(curLoadedBook + noteSubEu->bookOffset));
#else
                        nEntries = audioBookSample->book->order * audioBookSample->book->npredictors;
                        aLoadADPCM(cmd++, nEntries * 16, VIRTUAL_TO_PHYSICAL2(curLoadedBook));
#endif
                    }

#ifdef VERSION_EU
                    if (noteSubEu->bookOffset) {
                        curLoadedBook = euUnknownData_80301950; // what's this? never read
                    }
#endif

                    while (nAdpcmSamplesProcessed != samplesLenAdjusted) {
                        s32 samplesRemaining; // v1
                        s32 s0;

                        noteFinished = FALSE;
                        restart = FALSE;
                        nSamplesToProcess = samplesLenAdjusted - nAdpcmSamplesProcessed;
#ifdef VERSION_EU
                        s2 = synthesisState->samplePosInt & 0xf;
                        samplesRemaining = endPos - synthesisState->samplePosInt;
#else
                        s2 = note->samplePosInt & 0xf;
                        samplesRemaining = endPos - note->samplePosInt;
#endif

#ifdef VERSION_EU
                        if (s2 == 0 && synthesisState->restart == FALSE) {
                            s2 = 16;
                        }
#else
                        if (s2 == 0 && note->restart == FALSE) {
                            s2 = 16;
                        }
#endif
                        s6 = 16 - s2; // a1

                        if (nSamplesToProcess < samplesRemaining) {
                            t0 = (nSamplesToProcess - s6 + 0xf) / 16;
                            s0 = t0 * 16;
                            s3 = s6 + s0 - nSamplesToProcess;
                        } else {
#ifndef VERSION_EU
                            s0 = samplesRemaining + s2 - 0x10;
#else
                            s0 = samplesRemaining - s6;
#endif
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

                        if (t0 != 0) {
#ifdef VERSION_EU
                            temp = (synthesisState->samplePosInt - s2 + 0x10) / 16;
                            if (audioBookSample->loaded == 0x81) {
                                v0_2 = sampleAddr + temp * 9;
                            } else {
                                v0_2 = dma_sample_data(
                                    (uintptr_t) (sampleAddr + temp * 9),
                                    t0 * 9, flags, &synthesisState->sampleDmaIndex);
                            }
#else
                            temp = (note->samplePosInt - s2 + 0x10) / 16;
                            v0_2 = dma_sample_data(
                                (uintptr_t) (sampleAddr + temp * 9),
                                t0 * 9, flags, &note->sampleDmaIndex);
#endif
                            a3 = (u32)((uintptr_t) v0_2 & 0xf);
                            aSetBuffer(cmd++, 0, DMEM_ADDR_COMPRESSED_ADPCM_DATA, 0, t0 * 9 + a3);
                            aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(v0_2 - a3));
                        } else {
                            s0 = 0;
                            a3 = 0;
                        }

#ifdef VERSION_EU
                        if (synthesisState->restart != FALSE) {
                            aSetLoop(cmd++, VIRTUAL_TO_PHYSICAL2(audioBookSample->loop->state));
                            flags = A_LOOP; // = 2
                            synthesisState->restart = FALSE;
                        }
#else
                        if (note->restart != FALSE) {
                            aSetLoop(cmd++, VIRTUAL_TO_PHYSICAL2(audioBookSample->loop->state));
                            flags = A_LOOP; // = 2
                            note->restart = FALSE;
                        }
#endif

                        nSamplesInThisIteration = s0 + s6 - s3;
#ifdef VERSION_EU
                        if (nAdpcmSamplesProcessed == 0) {
                            aSetBuffer(cmd++, 0, DMEM_ADDR_COMPRESSED_ADPCM_DATA + a3,
                                       DMEM_ADDR_UNCOMPRESSED_NOTE, s0 * 2);
                            aADPCMdec(cmd++, flags,
                                      VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->adpcmdecState));
                            sp130 = s2 * 2;
                        } else {
                            s5Aligned = ALIGN(s5, 5);
                            aSetBuffer(cmd++, 0, DMEM_ADDR_COMPRESSED_ADPCM_DATA + a3,
                                       DMEM_ADDR_UNCOMPRESSED_NOTE + s5Aligned, s0 * 2);
                            aADPCMdec(cmd++, flags,
                                      VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->adpcmdecState));
                            aDMEMMove(cmd++, DMEM_ADDR_UNCOMPRESSED_NOTE + s5Aligned + (s2 * 2),
                                      DMEM_ADDR_UNCOMPRESSED_NOTE + s5, (nSamplesInThisIteration) * 2);
                        }
#else
                        if (nAdpcmSamplesProcessed == 0) {
                            aSetBuffer(cmd++, 0, DMEM_ADDR_COMPRESSED_ADPCM_DATA + a3, DMEM_ADDR_UNCOMPRESSED_NOTE, s0 * 2);
                            aADPCMdec(cmd++, flags, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->adpcmdecState));
                            sp130 = s2 * 2;
                        } else {
                            aSetBuffer(cmd++, 0, DMEM_ADDR_COMPRESSED_ADPCM_DATA + a3, DMEM_ADDR_UNCOMPRESSED_NOTE + ALIGN(s5, 5), s0 * 2);
                            aADPCMdec(cmd++, flags, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->adpcmdecState));
                            aDMEMMove(cmd++, DMEM_ADDR_UNCOMPRESSED_NOTE + ALIGN(s5, 5) + (s2 * 2), DMEM_ADDR_UNCOMPRESSED_NOTE + s5, (nSamplesInThisIteration) * 2);
                        }
#endif

                        nAdpcmSamplesProcessed += nSamplesInThisIteration;

                        switch (flags) {
                            case A_INIT: // = 1
                                sp130 = 0;
                                s5 = s0 * 2 + s5;
                                break;

                            case A_LOOP: // = 2
                                s5 = nSamplesInThisIteration * 2 + s5;
                                break;

                            default:
                                if (s5 != 0) {
                                    s5 = nSamplesInThisIteration * 2 + s5;
                                } else {
                                    s5 = (s2 + nSamplesInThisIteration) * 2;
                                }
                                break;
                        }
                        flags = 0;

                        if (noteFinished) {
                            aClearBuffer(cmd++, DMEM_ADDR_UNCOMPRESSED_NOTE + s5,
                                         (samplesLenAdjusted - nAdpcmSamplesProcessed) * 2);
#ifdef VERSION_EU
                            noteSubEu->finished = 1;
                            note->noteSubEu.finished = 1;
                            note->noteSubEu.enabled = 0;
#else
                            note->samplePosInt = 0;
                            note->finished = 1;
                            ((struct vNote *)note)->enabled = 0;
#endif
                            break;
                        }
#ifdef VERSION_EU
                        if (restart) {
                            synthesisState->restart = TRUE;
                            synthesisState->samplePosInt = loopInfo->start;
                        } else {
                            synthesisState->samplePosInt += nSamplesToProcess;
                        }
#else
                        if (restart) {
                            note->restart = TRUE;
                            note->samplePosInt = loopInfo->start;
                        } else {
                            note->samplePosInt += nSamplesToProcess;
                        }
#endif
                    }

                    switch (nParts) {
                        case 1:
                            noteSamplesDmemAddrBeforeResampling = DMEM_ADDR_UNCOMPRESSED_NOTE + sp130;
                            break;

                        case 2:
                            switch (curPart) {
                                case 0:
                                    aSetBuffer(cmd++, 0, DMEM_ADDR_UNCOMPRESSED_NOTE + sp130, DMEM_ADDR_RESAMPLED, samplesLenAdjusted + 4);
#ifdef VERSION_EU
                                    aResample(cmd++, A_INIT, 0xff60, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->dummyResampleState));
#else
                                    aResample(cmd++, A_INIT, 0xff60, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->dummyResampleState));
#endif
                                    resampledTempLen = samplesLenAdjusted + 4;
                                    noteSamplesDmemAddrBeforeResampling = DMEM_ADDR_RESAMPLED + 4;
#ifdef VERSION_EU
                                    if (noteSubEu->finished != FALSE) {
#else
                                    if (note->finished != FALSE) {
#endif
                                        aClearBuffer(cmd++, DMEM_ADDR_RESAMPLED + resampledTempLen, samplesLenAdjusted + 0x10);
                                    }
                                    break;

                                case 1:
                                    aSetBuffer(cmd++, 0, DMEM_ADDR_UNCOMPRESSED_NOTE + sp130,
                                               DMEM_ADDR_RESAMPLED2,
                                               samplesLenAdjusted + 8);
#ifdef VERSION_EU
                                    aResample(cmd++, A_INIT, 0xff60,
                                              VIRTUAL_TO_PHYSICAL2(
                                                  synthesisState->synthesisBuffers->dummyResampleState));
#else
                                    aResample(cmd++, A_INIT, 0xff60,
                                              VIRTUAL_TO_PHYSICAL2(
                                                  note->synthesisBuffers->dummyResampleState));
#endif
                                    aDMEMMove(cmd++, DMEM_ADDR_RESAMPLED2 + 4,
                                              DMEM_ADDR_RESAMPLED + resampledTempLen,
                                              samplesLenAdjusted + 4);
                                    break;
                            }
                    }

#ifdef VERSION_EU
                    if (noteSubEu->finished != FALSE) {
#else
                    if (note->finished != FALSE) {
#endif
                        break;
                    }
                }
            }

            flags = 0;

#ifdef VERSION_EU
            if (noteSubEu->needsInit == TRUE) {
                flags = A_INIT;
                noteSubEu->needsInit = FALSE;
            }

            cmd = final_resample(cmd, synthesisState, bufLen * 2, resamplingRateFixedPoint,
                                 noteSamplesDmemAddrBeforeResampling, flags);
#else
            if (note->needsInit == TRUE) {
                flags = A_INIT;
                note->needsInit = FALSE;
            }

            cmd = final_resample(cmd, note, bufLen * 2, resamplingRateFixedPoint,
                                 noteSamplesDmemAddrBeforeResampling, flags);
#endif

#ifdef VERSION_EU
            if (noteSubEu->headsetPanRight != 0 || synthesisState->prevHeadsetPanRight != 0) {
                leftRight = 1;
            } else if (noteSubEu->headsetPanLeft != 0 || synthesisState->prevHeadsetPanLeft != 0) {
                leftRight = 2;
#else
            if (note->headsetPanRight != 0 || note->prevHeadsetPanRight != 0) {
                leftRight = 1;
            } else if (note->headsetPanLeft != 0 || note->prevHeadsetPanLeft != 0) {
                leftRight = 2;
#endif
            } else {
                leftRight = 0;
            }

#ifdef VERSION_EU
            cmd = process_envelope(cmd, noteSubEu, synthesisState, bufLen, 0, leftRight, flags);
#else
            cmd = process_envelope(cmd, note, bufLen, 0, leftRight, flags);
#endif

#ifdef VERSION_EU
            if (noteSubEu->usesHeadsetPanEffects) {
                cmd = note_apply_headset_pan_effects(cmd, noteSubEu, synthesisState, bufLen * 2, flags, leftRight);
            }
#else
            if (note->usesHeadsetPanEffects) {
                cmd = note_apply_headset_pan_effects(cmd, note, bufLen * 2, flags, leftRight);
            }
#endif
        }
#ifndef VERSION_EU
    }

    t9 = bufLen * 2;
    aSetBuffer(cmd++, 0, 0, DMEM_ADDR_TEMP, t9);
    aInterleave(cmd++, DMEM_ADDR_LEFT_CH, DMEM_ADDR_RIGHT_CH);
    t9 *= 2;
    aSetBuffer(cmd++, 0, 0, DMEM_ADDR_TEMP, t9);
    aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(aiBuf));
#endif

    return cmd;
}

#ifdef VERSION_EU
u64 *load_wave_samples(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *synthesisState, s32 nSamplesToLoad) {
    s32 a3;
    s32 repeats;
    s32 i;
    aSetBuffer(cmd++, /*flags*/ 0, /*dmemin*/ DMEM_ADDR_UNCOMPRESSED_NOTE, /*dmemout*/ 0, /*count*/ 128);
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(noteSubEu->sound.samples));

    synthesisState->samplePosInt &= 0x3f;
    a3 = 64 - synthesisState->samplePosInt;
    if (a3 < nSamplesToLoad) {
        repeats = (nSamplesToLoad - a3 + 63) / 64;
        for (i = 0; i < repeats; i++) {
            aDMEMMove(cmd++,
                      /*dmemin*/ DMEM_ADDR_UNCOMPRESSED_NOTE,
                      /*dmemout*/ DMEM_ADDR_UNCOMPRESSED_NOTE + (1 + i) * 128,
                      /*count*/ 128);
        }
    }
    return cmd;
}
#else
u64 *load_wave_samples(u64 *cmd, struct Note *note, s32 nSamplesToLoad) {
    s32 a3;
    s32 i;
    aSetBuffer(cmd++, /*flags*/ 0, /*dmemin*/ DMEM_ADDR_UNCOMPRESSED_NOTE, /*dmemout*/ 0,
               /*count*/ sizeof(note->synthesisBuffers->samples));
    aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->samples));
    note->samplePosInt &= (note->sampleCount - 1);
    a3 = 64 - note->samplePosInt;
    if (a3 < nSamplesToLoad) {
        for (i = 0; i <= (nSamplesToLoad - a3 + 63) / 64 - 1; i++) {
            aDMEMMove(cmd++, /*dmemin*/ DMEM_ADDR_UNCOMPRESSED_NOTE, /*dmemout*/ DMEM_ADDR_UNCOMPRESSED_NOTE + (1 + i) * sizeof(note->synthesisBuffers->samples), /*count*/ sizeof(note->synthesisBuffers->samples));
        }
    }
    return cmd;
}
#endif

#ifdef VERSION_EU
u64 *final_resample(u64 *cmd, struct NoteSynthesisState *synthesisState, s32 count, u16 pitch, u16 dmemIn, u32 flags) {
    aSetBuffer(cmd++, /*flags*/ 0, dmemIn, /*dmemout*/ DMEM_ADDR_TEMP, count);
    aResample(cmd++, flags, pitch, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->finalResampleState));
    return cmd;
}
#else
u64 *final_resample(u64 *cmd, struct Note *note, s32 count, u16 pitch, u16 dmemIn, u32 flags) {
    aSetBuffer(cmd++, /*flags*/ 0, dmemIn, /*dmemout*/ DMEM_ADDR_TEMP, count);
    aResample(cmd++, flags, pitch, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->finalResampleState));
    return cmd;
}
#endif

#ifndef VERSION_EU
u64 *process_envelope(u64 *cmd, struct Note *note, s32 nSamples, u16 inBuf, s32 headsetPanSettings,
                      UNUSED u32 flags) {
    UNUSED u8 pad[16];
    struct VolumeChange vol;
    vol.sourceLeft = note->curVolLeft;
    vol.sourceRight = note->curVolRight;
    vol.targetLeft = note->targetVolLeft;
    vol.targetRight = note->targetVolRight;
    note->curVolLeft = vol.targetLeft;
    note->curVolRight = vol.targetRight;
    return process_envelope_inner(cmd, note, nSamples, inBuf, headsetPanSettings, &vol);
}

u64 *process_envelope_inner(u64 *cmd, struct Note *note, s32 nSamples, u16 inBuf,
                            s32 headsetPanSettings, struct VolumeChange *vol) {
    UNUSED u8 pad[3];
    u8 mixerFlags;
    UNUSED u8 pad2[8];
    s32 rampLeft, rampRight;
#elif defined(VERSION_EU)
u64 *process_envelope(u64 *cmd, struct NoteSubEu *note, struct NoteSynthesisState *synthesisState, s32 nSamples, u16 inBuf, s32 headsetPanSettings, UNUSED u32 flags) {
    UNUSED u8 pad1[20];
    u16 sourceRight;
    u16 sourceLeft;
    UNUSED u8 pad2[4];
    u16 targetLeft;
    u16 targetRight;
    s32 mixerFlags;
    s32 rampLeft;
    s32 rampRight;

    sourceLeft = synthesisState->curVolLeft;
    sourceRight = synthesisState->curVolRight;
    targetLeft = (note->targetVolLeft << 5);
    targetRight = (note->targetVolRight << 5);
    if (targetLeft == 0) {
        targetLeft++;
    }
    if (targetRight == 0) {
        targetRight++;
    }
    synthesisState->curVolLeft = targetLeft;
    synthesisState->curVolRight = targetRight;
#endif

    // For aEnvMixer, five buffers and count are set using aSetBuffer.
    // in, dry left, count without A_AUX flag.
    // dry right, wet left, wet right with A_AUX flag.

    if (note->usesHeadsetPanEffects) {
        aClearBuffer(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DEFAULT_LEN_1CH);

        switch (headsetPanSettings) {
            case 1:
                aSetBuffer(cmd++, 0, inBuf, DMEM_ADDR_NOTE_PAN_TEMP, nSamples * 2);
                aSetBuffer(cmd++, A_AUX, DMEM_ADDR_RIGHT_CH, DMEM_ADDR_WET_LEFT_CH,
                           DMEM_ADDR_WET_RIGHT_CH);
                break;
            case 2:
                aSetBuffer(cmd++, 0, inBuf, DMEM_ADDR_LEFT_CH, nSamples * 2);
                aSetBuffer(cmd++, A_AUX, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_WET_LEFT_CH,
                           DMEM_ADDR_WET_RIGHT_CH);
                break;
            default:
                aSetBuffer(cmd++, 0, inBuf, DMEM_ADDR_LEFT_CH, nSamples * 2);
                aSetBuffer(cmd++, A_AUX, DMEM_ADDR_RIGHT_CH, DMEM_ADDR_WET_LEFT_CH,
                           DMEM_ADDR_WET_RIGHT_CH);
                break;
        }
    } else {
        // It's a bit unclear what the "stereo strong" concept does.
        // Instead of mixing the opposite channel to the normal buffers, the sound is first
        // mixed into a temporary buffer and then subtracted from the normal buffer.
        if (note->stereoStrongRight) {
            aClearBuffer(cmd++, DMEM_ADDR_STEREO_STRONG_TEMP_DRY, DEFAULT_LEN_2CH);
            aSetBuffer(cmd++, 0, inBuf, DMEM_ADDR_STEREO_STRONG_TEMP_DRY, nSamples * 2);
            aSetBuffer(cmd++, A_AUX, DMEM_ADDR_RIGHT_CH, DMEM_ADDR_STEREO_STRONG_TEMP_WET,
                       DMEM_ADDR_WET_RIGHT_CH);
        } else if (note->stereoStrongLeft) {
            aClearBuffer(cmd++, DMEM_ADDR_STEREO_STRONG_TEMP_DRY, DEFAULT_LEN_2CH);
            aSetBuffer(cmd++, 0, inBuf, DMEM_ADDR_LEFT_CH, nSamples * 2);
            aSetBuffer(cmd++, A_AUX, DMEM_ADDR_STEREO_STRONG_TEMP_DRY, DMEM_ADDR_WET_LEFT_CH,
                       DMEM_ADDR_STEREO_STRONG_TEMP_WET);
        } else {
            aSetBuffer(cmd++, 0, inBuf, DMEM_ADDR_LEFT_CH, nSamples * 2);
            aSetBuffer(cmd++, A_AUX, DMEM_ADDR_RIGHT_CH, DMEM_ADDR_WET_LEFT_CH, DMEM_ADDR_WET_RIGHT_CH);
        }
    }

#ifdef VERSION_EU
    if (targetLeft == sourceLeft && targetRight == sourceRight && !note->envMixerNeedsInit) {
#else
    if (vol->targetLeft == vol->sourceLeft && vol->targetRight == vol->sourceRight
        && !note->envMixerNeedsInit) {
#endif
        mixerFlags = A_CONTINUE;
    } else {
        mixerFlags = A_INIT;

#ifdef VERSION_EU
        rampLeft = gCurrentLeftVolRamping[targetLeft >> 5] * gCurrentRightVolRamping[sourceLeft >> 5];
        rampRight = gCurrentLeftVolRamping[targetRight >> 5] * gCurrentRightVolRamping[sourceRight >> 5];
#else
        rampLeft = get_volume_ramping(vol->sourceLeft, vol->targetLeft, nSamples);
        rampRight = get_volume_ramping(vol->sourceRight, vol->targetRight, nSamples);
#endif

        // The operation's parameters change meanings depending on flags
#ifdef VERSION_EU
        aSetVolume(cmd++, A_VOL | A_LEFT, sourceLeft, 0, 0);
        aSetVolume(cmd++, A_VOL | A_RIGHT, sourceRight, 0, 0);
        aSetVolume32(cmd++, A_RATE | A_LEFT, targetLeft, rampLeft);
        aSetVolume32(cmd++, A_RATE | A_RIGHT, targetRight, rampRight);
        aSetVolume(cmd++, A_AUX, gVolume, 0, note->reverbVol << 8);
#else
        aSetVolume(cmd++, A_VOL | A_LEFT, vol->sourceLeft, 0, 0);
        aSetVolume(cmd++, A_VOL | A_RIGHT, vol->sourceRight, 0, 0);
        aSetVolume32(cmd++, A_RATE | A_LEFT, vol->targetLeft, rampLeft);
        aSetVolume32(cmd++, A_RATE | A_RIGHT, vol->targetRight, rampRight);
        aSetVolume(cmd++, A_AUX, gVolume, 0, note->reverbVolShifted);
#endif
    }

#ifdef VERSION_EU
    if (gUseReverb && note->reverbVol != 0) {
        aEnvMixer(cmd++, mixerFlags | A_AUX,
                  VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->mixEnvelopeState));
#else
    if (gSynthesisReverb.useReverb && note->reverbVol != 0) {
        aEnvMixer(cmd++, mixerFlags | A_AUX,
                  VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->mixEnvelopeState));
#endif
        if (note->stereoStrongRight) {
            aSetBuffer(cmd++, 0, 0, 0, nSamples * 2);
            // 0x8000 is -100%, so subtract sound instead of adding...
            aMix(cmd++, 0, /*gain*/ 0x8000, /*in*/ DMEM_ADDR_STEREO_STRONG_TEMP_DRY,
                 /*out*/ DMEM_ADDR_LEFT_CH);
            aMix(cmd++, 0, /*gain*/ 0x8000, /*in*/ DMEM_ADDR_STEREO_STRONG_TEMP_WET,
                 /*out*/ DMEM_ADDR_WET_LEFT_CH);
        } else if (note->stereoStrongLeft) {
            aSetBuffer(cmd++, 0, 0, 0, nSamples * 2);
            aMix(cmd++, 0, /*gain*/ 0x8000, /*in*/ DMEM_ADDR_STEREO_STRONG_TEMP_DRY,
                 /*out*/ DMEM_ADDR_RIGHT_CH);
            aMix(cmd++, 0, /*gain*/ 0x8000, /*in*/ DMEM_ADDR_STEREO_STRONG_TEMP_WET,
                 /*out*/ DMEM_ADDR_WET_RIGHT_CH);
        }
    } else {
#ifdef VERSION_EU
        aEnvMixer(cmd++, mixerFlags, VIRTUAL_TO_PHYSICAL2(synthesisState->synthesisBuffers->mixEnvelopeState));
#else
        aEnvMixer(cmd++, mixerFlags, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->mixEnvelopeState));
#endif
        if (note->stereoStrongRight) {
            aSetBuffer(cmd++, 0, 0, 0, nSamples * 2);
            aMix(cmd++, 0, /*gain*/ 0x8000, /*in*/ DMEM_ADDR_STEREO_STRONG_TEMP_DRY,
                 /*out*/ DMEM_ADDR_LEFT_CH);
        } else if (note->stereoStrongLeft) {
            aSetBuffer(cmd++, 0, 0, 0, nSamples * 2);
            aMix(cmd++, 0, /*gain*/ 0x8000, /*in*/ DMEM_ADDR_STEREO_STRONG_TEMP_DRY,
                 /*out*/ DMEM_ADDR_RIGHT_CH);
        }
    }
    return cmd;
}

#ifdef VERSION_EU
u64 *note_apply_headset_pan_effects(u64 *cmd, struct NoteSubEu *noteSubEu, struct NoteSynthesisState *note, s32 bufLen, s32 flags, s32 leftRight) {
#else
u64 *note_apply_headset_pan_effects(u64 *cmd, struct Note *note, s32 bufLen, s32 flags, s32 leftRight) {
#endif
    u16 dest;
    u16 pitch;
#ifdef VERSION_EU
    u8 prevPanShift;
    u8 panShift;
    UNUSED u8 unkDebug;
#else
    u16 prevPanShift;
    u16 panShift;
#endif

    switch (leftRight) {
        case 1:
            dest = DMEM_ADDR_LEFT_CH;
#ifdef VERSION_EU
            panShift = noteSubEu->headsetPanRight;
#else
            panShift = note->headsetPanRight;
#endif
            note->prevHeadsetPanLeft = 0;
            prevPanShift = note->prevHeadsetPanRight;
            note->prevHeadsetPanRight = panShift;
            break;
        case 2:
            dest = DMEM_ADDR_RIGHT_CH;
#ifdef VERSION_EU
            panShift = noteSubEu->headsetPanLeft;
#else
            panShift = note->headsetPanLeft;
#endif
            note->prevHeadsetPanRight = 0;

            prevPanShift = note->prevHeadsetPanLeft;
            note->prevHeadsetPanLeft = panShift;
            break;
        default:
            return cmd;
    }

    if (flags != 1) { // A_INIT?
        // Slightly adjust the sample rate in order to fit a change in pan shift
        if (prevPanShift == 0) {
            // Kind of a hack that moves the first samples into the resample state
            aDMEMMove(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP, 8);
            aClearBuffer(cmd++, 8, 8); // Set pitch accumulator to 0 in the resample state
            aDMEMMove(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP + 0x10,
                      0x10); // No idea, result seems to be overwritten later

            aSetBuffer(cmd++, 0, 0, DMEM_ADDR_TEMP, 32);
            aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panResampleState));

#ifdef VERSION_EU
            pitch = (bufLen << 0xf) / (bufLen + panShift - prevPanShift + 8);
            if (pitch) {
            }
#else
            pitch = (bufLen << 0xf) / (panShift + bufLen - prevPanShift + 8);
#endif
            aSetBuffer(cmd++, 0, DMEM_ADDR_NOTE_PAN_TEMP + 8, DMEM_ADDR_TEMP, panShift + bufLen - prevPanShift);
            aResample(cmd++, 0, pitch, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panResampleState));
        } else {
            if (panShift == 0) {
                pitch = (bufLen << 0xf) / (bufLen - prevPanShift - 4);
            } else {
                pitch = (bufLen << 0xf) / (bufLen + panShift - prevPanShift);
            }

#if defined(VERSION_EU) && !defined(AVOID_UB)
            if (unkDebug) { // UB
            }
#endif
            aSetBuffer(cmd++, 0, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP, panShift + bufLen - prevPanShift);
            aResample(cmd++, 0, pitch, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panResampleState));
        }

        if (prevPanShift != 0) {
            aSetBuffer(cmd++, 0, DMEM_ADDR_NOTE_PAN_TEMP, 0, prevPanShift);
            aLoadBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panSamplesBuffer));
            aDMEMMove(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_NOTE_PAN_TEMP + prevPanShift, panShift + bufLen - prevPanShift);
        } else {
            aDMEMMove(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_NOTE_PAN_TEMP, panShift + bufLen - prevPanShift);
        }
    } else {
        // Just shift right
        aDMEMMove(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, DMEM_ADDR_TEMP, bufLen);
        aDMEMMove(cmd++, DMEM_ADDR_TEMP, DMEM_ADDR_NOTE_PAN_TEMP + panShift, bufLen);
        aClearBuffer(cmd++, DMEM_ADDR_NOTE_PAN_TEMP, panShift);
    }

    if (panShift) {
        // Save excessive samples for next iteration
        aSetBuffer(cmd++, 0, 0, DMEM_ADDR_NOTE_PAN_TEMP + bufLen, panShift);
        aSaveBuffer(cmd++, VIRTUAL_TO_PHYSICAL2(note->synthesisBuffers->panSamplesBuffer));
    }

    aSetBuffer(cmd++, 0, 0, 0, bufLen);
    aMix(cmd++, 0, /*gain*/ 0x7fff, /*in*/ DMEM_ADDR_NOTE_PAN_TEMP, /*out*/ dest);

    return cmd;
}

#ifndef VERSION_EU
// Moved to playback.c in EU

void note_init_volume(struct Note *note) {
    note->targetVolLeft = 0;
    note->targetVolRight = 0;
    note->reverbVol = 0;
    note->reverbVolShifted = 0;
    note->unused2 = 0;
    note->curVolLeft = 1;
    note->curVolRight = 1;
    note->frequency = 0.0f;
}

void note_set_vel_pan_reverb(struct Note *note, f32 velocity, f32 pan, u8 reverbVol) {
    s32 panIndex;
    f32 volLeft;
    f32 volRight;
    // Anding with 127 avoids out-of-bounds reads when pan is outside of [0, 1].
    // This can occur during PU movement -- see the bug comment in get_sound_pan
    // in external.c. An out-of-bounds read by itself doesn't crash, but if the
    // resulting value is a nan or denormal, performing arithmetic on it crashes
    // on console.
#ifdef VERSION_JP
    panIndex = MIN((s32)(pan * 127.5), 127);
#else
    panIndex = (s32)(pan * 127.5f) & 127;
#endif
    if (note->stereoHeadsetEffects && gSoundMode == SOUND_MODE_HEADSET) {
        s8 smallPanIndex;
        s8 temp = (s8)(pan * 10.0f);
        if (temp < 9) {
            smallPanIndex = temp;
        } else {
            smallPanIndex = 9;
        }
        note->headsetPanLeft = gHeadsetPanQuantization[smallPanIndex];
        note->headsetPanRight = gHeadsetPanQuantization[9 - smallPanIndex];
        note->stereoStrongRight = FALSE;
        note->stereoStrongLeft = FALSE;
        note->usesHeadsetPanEffects = TRUE;
        volLeft = gHeadsetPanVolume[panIndex];
        volRight = gHeadsetPanVolume[127 - panIndex];
    } else if (note->stereoHeadsetEffects && gSoundMode == SOUND_MODE_STEREO) {
        u8 strongLeft;
        u8 strongRight;
        strongLeft = FALSE;
        strongRight = FALSE;
        note->headsetPanLeft = 0;
        note->headsetPanRight = 0;
        note->usesHeadsetPanEffects = FALSE;
        volLeft = gStereoPanVolume[panIndex];
        volRight = gStereoPanVolume[127 - panIndex];
        if (panIndex < 0x20) {
            strongLeft = TRUE;
        } else if (panIndex > 0x60) {
            strongRight = TRUE;
        }
        note->stereoStrongRight = strongRight;
        note->stereoStrongLeft = strongLeft;
    } else if (gSoundMode == SOUND_MODE_MONO) {
        volLeft = .707f;
        volRight = .707f;
    } else {
        volLeft = gDefaultPanVolume[panIndex];
        volRight = gDefaultPanVolume[127 - panIndex];
    }

    if (velocity < 0) {
        velocity = 0;
    }
#ifdef VERSION_JP
    note->targetVolLeft = (u16)(velocity * volLeft) & ~0x80FF; // 0x7F00, but that doesn't match
    note->targetVolRight = (u16)(velocity * volRight) & ~0x80FF;
#else
    note->targetVolLeft = (u16)(s32)(velocity * volLeft) & ~0x80FF;
    note->targetVolRight = (u16)(s32)(velocity * volRight) & ~0x80FF;
#endif
    if (note->targetVolLeft == 0) {
        note->targetVolLeft++;
    }
    if (note->targetVolRight == 0) {
        note->targetVolRight++;
    }
    if (note->reverbVol != reverbVol) {
        note->reverbVol = reverbVol;
        note->reverbVolShifted = reverbVol << 8;
        note->envMixerNeedsInit = TRUE;
        return;
    }

    if (note->needsInit) {
        note->envMixerNeedsInit = TRUE;
    } else {
        note->envMixerNeedsInit = FALSE;
    }
}

void note_set_frequency(struct Note *note, f32 frequency) {
    note->frequency = frequency;
}

void note_enable(struct Note *note) {
    note->enabled = TRUE;
    note->needsInit = TRUE;
    note->restart = FALSE;
    note->finished = FALSE;
    note->stereoStrongRight = FALSE;
    note->stereoStrongLeft = FALSE;
    note->usesHeadsetPanEffects = FALSE;
    note->headsetPanLeft = 0;
    note->headsetPanRight = 0;
    note->prevHeadsetPanRight = 0;
    note->prevHeadsetPanLeft = 0;
}

void note_disable(struct Note *note) {
    if (note->needsInit == TRUE) {
        note->needsInit = FALSE;
    } else {
        note_set_vel_pan_reverb(note, 0, .5, 0);
    }
    note->priority = NOTE_PRIORITY_DISABLED;
    note->enabled = FALSE;
    note->finished = FALSE;
    note->parentLayer = NO_LAYER;
    note->prevParentLayer = NO_LAYER;
}
#endif
#endif
