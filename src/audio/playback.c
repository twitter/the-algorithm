#include <ultra64.h>

#include "heap.h"
#include "data.h"
#include "load.h"
#include "seqplayer.h"
#include "playback.h"
#include "synthesis.h"
#include "effects.h"
#include "external.h"

void note_set_resampling_rate(struct Note *note, f32 resamplingRateInput);

#if defined(VERSION_EU) || defined(VERSION_SH)
#ifdef VERSION_SH
void note_set_vel_pan_reverb(struct Note *note, struct ReverbInfo *reverbInfo)
#else
void note_set_vel_pan_reverb(struct Note *note, f32 velocity, u8 pan, u8 reverbVol)
#endif
{
    struct NoteSubEu *sub = &note->noteSubEu;
    f32 volRight, volLeft;
    u8 strongRight;
    u8 strongLeft;
    s32 smallPanIndex;
#ifdef VERSION_EU
    u16 unkMask = ~0x80;
#else
    UNUSED u32 pad;
    UNUSED u32 pad1;
    f32 velocity;
    u8 pan;
    u8 reverbVol;
    struct ReverbBitsData reverbBits;
#endif

#ifdef VERSION_SH
    note_set_resampling_rate(note, reverbInfo->freqScale);
    velocity = reverbInfo->velocity;
    pan = reverbInfo->pan;
    reverbVol = reverbInfo->reverbVol;
    reverbBits = reverbInfo->reverbBits.s;
    pan &= 0x7f;
#else
    pan &= unkMask;
#endif

    if (note->noteSubEu.stereoHeadsetEffects && gSoundMode == SOUND_MODE_HEADSET) {
#ifdef VERSION_SH
        smallPanIndex = pan >> 1;
#else
        smallPanIndex = pan >> 3;
#endif
        if (smallPanIndex >= ARRAY_COUNT(gHeadsetPanQuantization)) {
            smallPanIndex = ARRAY_COUNT(gHeadsetPanQuantization) - 1;
        }

        sub->headsetPanLeft = gHeadsetPanQuantization[smallPanIndex];
        sub->headsetPanRight = gHeadsetPanQuantization[ARRAY_COUNT(gHeadsetPanQuantization) - 1 - smallPanIndex];
        sub->stereoStrongRight = FALSE;
        sub->stereoStrongLeft = FALSE;
        sub->usesHeadsetPanEffects = TRUE;

        volLeft = gHeadsetPanVolume[pan];
        volRight = gHeadsetPanVolume[127 - pan];
    } else if (sub->stereoHeadsetEffects && gSoundMode == SOUND_MODE_STEREO) {
#ifdef VERSION_SH
        strongRight = FALSE;
        strongLeft = FALSE;
        sub->headsetPanRight = 0;
        sub->headsetPanLeft = 0;
#else
        strongLeft = FALSE;
        strongRight = FALSE;
        sub->headsetPanLeft = 0;
        sub->headsetPanRight = 0;
#endif

        sub->usesHeadsetPanEffects = FALSE;

        volLeft = gStereoPanVolume[pan];
        volRight = gStereoPanVolume[127 - pan];
        if (pan < 0x20) {
            strongLeft = TRUE;
        } else if (pan > 0x60) {
            strongRight = TRUE;
        }

        sub->stereoStrongRight = strongRight;
        sub->stereoStrongLeft = strongLeft;

#ifdef VERSION_SH
        switch (reverbBits.stereoHeadsetEffects) {
            case 0:
                sub->stereoStrongRight = reverbBits.strongRight;
                sub->stereoStrongLeft = reverbBits.strongLeft;
                break;

            case 1:
                break;

            case 2:
                sub->stereoStrongRight = reverbBits.strongRight | strongRight;
                sub->stereoStrongLeft = reverbBits.strongLeft | strongLeft;
                break;

            case 3:
                sub->stereoStrongRight = reverbBits.strongRight ^ strongRight;
                sub->stereoStrongLeft = reverbBits.strongLeft ^ strongLeft;
                break;
        }
#endif
    } else if (gSoundMode == SOUND_MODE_MONO) {
        volLeft = 0.707f;
        volRight = 0.707f;
    } else {
        volLeft = gDefaultPanVolume[pan];
        volRight = gDefaultPanVolume[127 - pan];
    }

#ifdef VERSION_SH
    if (velocity < 0.0f) {
        velocity = 0.0f;
    }
    if (velocity > 1.0f) {
        velocity = 1.0f;
    }

    sub->targetVolLeft =  ((s32) (velocity * volLeft * 4095.999f));
    sub->targetVolRight = ((s32) (velocity * volRight * 4095.999f));
    sub->synthesisVolume = reverbInfo->synthesisVolume;
    sub->filter = reverbInfo->filter;
#else
    if (velocity < 0.0f) {
        stubbed_printf("Audio: setvol: volume minus %f\n", velocity);
        velocity = 0.0f;
    }
    if (velocity > 32767.f) {
        stubbed_printf("Audio: setvol: volume overflow %f\n", velocity);
        velocity = 32767.f;
    }

    sub->targetVolLeft =  ((s32) (velocity * volLeft) & 0xffff) >> 5;
    sub->targetVolRight = ((s32) (velocity * volRight) & 0xffff) >> 5;
#endif

    //! @bug for the change to UQ0.7, the if statement should also have been changed accordingly
    if (sub->reverbVol != reverbVol) {
#ifdef VERSION_SH
        sub->reverbVol = reverbVol >> 1;
#else
        sub->reverbVol = reverbVol;
#endif
        sub->envMixerNeedsInit = TRUE;
        return;
    }

    if (sub->needsInit) {
        sub->envMixerNeedsInit = TRUE;
    } else {
        sub->envMixerNeedsInit = FALSE;
    }
}

#ifdef VERSION_SH
#define MIN_RESAMPLING_RATE 1.99998f
#else
#define MIN_RESAMPLING_RATE 1.99996f
#endif

void note_set_resampling_rate(struct Note *note, f32 resamplingRateInput) {
    f32 resamplingRate = 0.0f;
    struct NoteSubEu *tempSub = &note->noteSubEu;

#ifdef VERSION_EU
    if (resamplingRateInput < 0.0f) {
        stubbed_printf("Audio: setpitch: pitch minus %f\n", resamplingRateInput);
        resamplingRateInput = 0.0f;
    }
#endif
    if (resamplingRateInput < 2.0f) {
        tempSub->hasTwoAdpcmParts = 0;

        if (MIN_RESAMPLING_RATE < resamplingRateInput) {
            resamplingRate = MIN_RESAMPLING_RATE;
        } else {
            resamplingRate = resamplingRateInput;
        }

    } else {
        tempSub->hasTwoAdpcmParts = 1;
        if (2 * MIN_RESAMPLING_RATE < resamplingRateInput) {
            resamplingRate = MIN_RESAMPLING_RATE;
        } else {
            resamplingRate = resamplingRateInput * 0.5f;
        }
    }
    note->noteSubEu.resamplingRateFixedPoint = (s32) (resamplingRate * 32768.0f);
}

#ifdef VERSION_EU
struct AudioBankSound *instrument_get_audio_bank_sound(struct Instrument *instrument, s32 semitone) {
    struct AudioBankSound *sound;
    if (semitone < instrument->normalRangeLo) {
        sound = &instrument->lowNotesSound;
    } else if (semitone <= instrument->normalRangeHi) {
        sound = &instrument->normalNotesSound;
    } else {
        sound = &instrument->highNotesSound;
    }
    return sound;
}

struct Instrument *get_instrument_inner(s32 bankId, s32 instId) {
    struct Instrument *inst;

    if (IS_BANK_LOAD_COMPLETE(bankId) == FALSE) {
        stubbed_printf("Audio: voiceman: No bank error %d\n", bankId);
        gAudioErrorFlags = bankId + 0x10000000;
        return NULL;
    }

    if (instId >= gCtlEntries[bankId].numInstruments) {
        stubbed_printf("Audio: voiceman: progNo. overflow %d,%d\n",
                instId, gCtlEntries[bankId].numInstruments);
        gAudioErrorFlags = ((bankId << 8) + instId) + 0x3000000;
        return NULL;
    }

    inst = gCtlEntries[bankId].instruments[instId];
    if (inst == NULL) {
        stubbed_printf("Audio: voiceman: progNo. undefined %d,%d\n", bankId, instId);
        gAudioErrorFlags = ((bankId << 8) + instId) + 0x1000000;
        return inst;
    }

#ifdef VERSION_EU
    if (((uintptr_t) gBankLoadedPool.persistent.pool.start <= (uintptr_t) inst
         && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.persistent.pool.start
                    + gBankLoadedPool.persistent.pool.size))
        || ((uintptr_t) gBankLoadedPool.temporary.pool.start <= (uintptr_t) inst
            && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.temporary.pool.start
                                   + gBankLoadedPool.temporary.pool.size))) {
        return inst;
    }

    stubbed_printf("Audio: voiceman: BAD Voicepointer %x,%d,%d\n", inst, bankId, instId);
    gAudioErrorFlags = ((bankId << 8) + instId) + 0x2000000;
    return NULL;
#else
    return inst;
#endif
}

struct Drum *get_drum(s32 bankId, s32 drumId) {
    struct Drum *drum;

#ifdef VERSION_SH
    if (IS_BANK_LOAD_COMPLETE(bankId) == FALSE) {
        stubbed_printf("Audio: voiceman: No bank error %d\n", bankId);
        gAudioErrorFlags = bankId + 0x10000000;
        return NULL;
    }
#endif

    if (drumId >= gCtlEntries[bankId].numDrums) {
        stubbed_printf("Audio: voiceman: Percussion Overflow %d,%d\n",
                drumId, gCtlEntries[bankId].numDrums);
        gAudioErrorFlags = ((bankId << 8) + drumId) + 0x4000000;
        return NULL;
    }

#ifndef NO_SEGMENTED_MEMORY
    if ((uintptr_t) gCtlEntries[bankId].drums < 0x80000000U) {
        stubbed_printf("Percussion Pointer Error\n");
        return NULL;
    }
#endif

    drum = gCtlEntries[bankId].drums[drumId];
    if (drum == NULL) {
        stubbed_printf("Audio: voiceman: Percpointer NULL %d,%d\n", bankId, drumId);
        gAudioErrorFlags = ((bankId << 8) + drumId) + 0x5000000;
    }
    return drum;
}
#endif
#endif // VERSION_EU

#if defined(VERSION_EU) || defined(VERSION_SH)
void note_init_for_layer(struct Note *note, struct SequenceChannelLayer *seqLayer);
#else
s32 note_init_for_layer(struct Note *note, struct SequenceChannelLayer *seqLayer);
#endif

void note_init(struct Note *note) {
    if (note->parentLayer->adsr.releaseRate == 0) {
        adsr_init(&note->adsr, note->parentLayer->seqChannel->adsr.envelope, &note->adsrVolScale);
    } else {
        adsr_init(&note->adsr, note->parentLayer->adsr.envelope, &note->adsrVolScale);
    }
#ifdef VERSION_SH
    note->unkSH34 = 0;
#endif
    note->adsr.state = ADSR_STATE_INITIAL;
#if defined(VERSION_EU) || defined(VERSION_SH)
    note->noteSubEu = gDefaultNoteSub;
#else
    note_init_volume(note);
    note_enable(note);
#endif
}

#if defined(VERSION_EU) || defined(VERSION_SH)
#define note_disable2 note_disable
void note_disable(struct Note *note) {
    if (note->noteSubEu.needsInit == TRUE) {
        note->noteSubEu.needsInit = FALSE;
    }
#ifdef VERSION_EU
    else {
        note_set_vel_pan_reverb(note, 0, 0x40, 0);
    }
#endif
    note->priority = NOTE_PRIORITY_DISABLED;
#ifdef VERSION_SH
    note->unkSH34 = 0;
#endif
    note->parentLayer = NO_LAYER;
    note->prevParentLayer = NO_LAYER;
    note->noteSubEu.enabled = FALSE;
    note->noteSubEu.finished = FALSE;
#ifdef VERSION_SH
    note->adsr.state = ADSR_STATE_DISABLED;
    note->adsr.current = 0;
#endif
}
#else
void note_disable2(struct Note *note) {
    note_disable(note);
}
#endif // VERSION_EU || VERSION_SH

void process_notes(void) {
    f32 scale;
#ifndef VERSION_SH
    f32 frequency;
#if defined(VERSION_JP) || defined(VERSION_US)
    u8 reverbVol;
#endif
    f32 velocity;
#if defined(VERSION_JP) || defined(VERSION_US)
    f32 pan;
    f32 cap;
#endif
#endif
    struct Note *note;
#if defined(VERSION_EU) || defined(VERSION_SH)
    struct NotePlaybackState *playbackState;
    struct NoteSubEu *noteSubEu;
#ifndef VERSION_SH
    UNUSED u8 pad[12];
    u8 reverbVol;
    UNUSED u8 pad3;
    u8 pan;
#else
    UNUSED u8 pad[8];
    struct ReverbInfo reverbInfo;
#endif
    u8 bookOffset;
#endif
    struct NoteAttributes *attributes;
#if defined(VERSION_JP) || defined(VERSION_US)
    struct AudioListItem *it;
#endif
    s32 i;

    // Macro versions of audio_list_push_front and audio_list_remove.
    // Should ideally be changed to use copt.
#define PREPEND(item, head_arg)                                                                        \
    ((it = (item), it->prev != NULL)                                                                   \
         ? it                                                                                          \
         : (it->prev = (head_arg), it->next = (head_arg)->next, (head_arg)->next->prev = it,           \
            (head_arg)->next = it, (head_arg)->u.count++, it->pool = (head_arg)->pool, it))
#define POP(item)                                                                                      \
    ((it = (item), it->prev == NULL)                                                                   \
         ? it                                                                                          \
         : (it->prev->next = it->next, it->next->prev = it->prev, it->prev = NULL, it))

    for (i = 0; i < gMaxSimultaneousNotes; i++) {
        note = &gNotes[i];
#if defined(VERSION_EU) || defined(VERSION_SH)
        playbackState = (struct NotePlaybackState *) &note->priority;
        if (note->parentLayer != NO_LAYER) {
#ifndef NO_SEGMENTED_MEMORY
            if ((uintptr_t) playbackState->parentLayer < 0x7fffffffU) {
                continue;
            }
#endif
#ifdef VERSION_SH
            if (note != playbackState->parentLayer->note && playbackState->unkSH34 == 0) {
                playbackState->adsr.action |= ADSR_ACTION_RELEASE;
                playbackState->adsr.fadeOutVel = gAudioBufferParameters.updatesPerFrameInv;
                playbackState->priority = 1;
                playbackState->unkSH34 = 2;
                goto d;
            } else if (!playbackState->parentLayer->enabled && playbackState->unkSH34 == 0 &&
                       playbackState->priority >= 1) {
                // do nothing
            } else if (playbackState->parentLayer->seqChannel->seqPlayer == NULL) {
                sequence_channel_disable(playbackState->parentLayer->seqChannel);
                playbackState->priority = 1;
                playbackState->unkSH34 = 1;
                continue;
            } else if (playbackState->parentLayer->seqChannel->seqPlayer->muted &&
                       (playbackState->parentLayer->seqChannel->muteBehavior
                       & (MUTE_BEHAVIOR_STOP_NOTES))) {
                // do nothing
            } else {
                goto d;
            }

            seq_channel_layer_note_release(playbackState->parentLayer);
            audio_list_remove(&note->listItem);
            audio_list_push_front(&note->listItem.pool->decaying, &note->listItem);
            playbackState->priority = 1;
            playbackState->unkSH34 = 2;
        } else if (playbackState->unkSH34 == 0 && playbackState->priority >= 1) {
            continue;
        }
#else
            if (!playbackState->parentLayer->enabled && playbackState->priority >= NOTE_PRIORITY_MIN) {
                goto c;
            } else if (playbackState->parentLayer->seqChannel->seqPlayer == NULL) {
                eu_stubbed_printf_0("CAUTION:SUB IS SEPARATED FROM GROUP");
                sequence_channel_disable(playbackState->parentLayer->seqChannel);
                playbackState->priority = NOTE_PRIORITY_STOPPING;
                continue;
            } else if (playbackState->parentLayer->seqChannel->seqPlayer->muted) {
                if ((playbackState->parentLayer->seqChannel->muteBehavior
                    & (MUTE_BEHAVIOR_STOP_SCRIPT | MUTE_BEHAVIOR_STOP_NOTES))) {
                    goto c;
                }
            }
            goto d;
            if (1) {
                c:
                seq_channel_layer_note_release(playbackState->parentLayer);
                audio_list_remove(&note->listItem);
                audio_list_push_front(&note->listItem.pool->decaying, &note->listItem);
                playbackState->priority = NOTE_PRIORITY_STOPPING;
            }
        } else if (playbackState->priority >= NOTE_PRIORITY_MIN) {
            continue;
        }
#endif
        d:
        if (playbackState->priority != NOTE_PRIORITY_DISABLED) {
#ifdef VERSION_SH
            if (1) {}
#endif
            noteSubEu = &note->noteSubEu;
#ifdef VERSION_SH
            if (playbackState->unkSH34 >= 1 || noteSubEu->finished) {
#else
            if (playbackState->priority == NOTE_PRIORITY_STOPPING || noteSubEu->finished) {
#endif
                if (playbackState->adsr.state == ADSR_STATE_DISABLED || noteSubEu->finished) {
                    if (playbackState->wantedParentLayer != NO_LAYER) {
                        note_disable(note);
                        if (playbackState->wantedParentLayer->seqChannel != NULL) {
                            note_init_for_layer(note, playbackState->wantedParentLayer);
                            note_vibrato_init(note);
                            audio_list_remove(&note->listItem);
                            audio_list_push_back(&note->listItem.pool->active, &note->listItem);
                            playbackState->wantedParentLayer = NO_LAYER;
                            // don't skip
                        } else {
                            eu_stubbed_printf_0("Error:Wait Track disappear\n");
                            note_disable(note);
                            audio_list_remove(&note->listItem);
                            audio_list_push_back(&note->listItem.pool->disabled, &note->listItem);
                            playbackState->wantedParentLayer = NO_LAYER;
                            goto skip;
                        }
                    } else {
                        note_disable(note);
                        audio_list_remove(&note->listItem);
                        audio_list_push_back(&note->listItem.pool->disabled, &note->listItem);
                        goto skip;
                    }
                }
#ifndef VERSION_SH
                if (1) {
                }
#endif
            } else if (playbackState->adsr.state == ADSR_STATE_DISABLED) {
                note_disable(note);
                audio_list_remove(&note->listItem);
                audio_list_push_back(&note->listItem.pool->disabled, &note->listItem);
                goto skip;
            }

            scale = adsr_update(&playbackState->adsr);
            note_vibrato_update(note);
            attributes = &playbackState->attributes;
#ifdef VERSION_SH
            if (playbackState->unkSH34 == 1 || playbackState->unkSH34 == 2) {
                reverbInfo.freqScale = attributes->freqScale;
                reverbInfo.velocity = attributes->velocity;
                reverbInfo.pan = attributes->pan;
                reverbInfo.reverbVol = attributes->reverbVol;
                reverbInfo.reverbBits = attributes->reverbBits;
                reverbInfo.synthesisVolume = attributes->synthesisVolume;
                reverbInfo.filter = attributes->filter;
                bookOffset = noteSubEu->bookOffset;
            } else {
                reverbInfo.freqScale = playbackState->parentLayer->noteFreqScale;
                reverbInfo.velocity = playbackState->parentLayer->noteVelocity;
                reverbInfo.pan = playbackState->parentLayer->notePan;
                reverbInfo.reverbBits = playbackState->parentLayer->reverbBits;
                reverbInfo.reverbVol = playbackState->parentLayer->seqChannel->reverbVol;
                reverbInfo.synthesisVolume = playbackState->parentLayer->seqChannel->synthesisVolume;
                reverbInfo.filter = playbackState->parentLayer->seqChannel->filter;
                bookOffset = playbackState->parentLayer->seqChannel->bookOffset & 0x7;
                if (playbackState->parentLayer->seqChannel->seqPlayer->muted
                    && (playbackState->parentLayer->seqChannel->muteBehavior & 8)) {
                    reverbInfo.freqScale = 0.0f;
                    reverbInfo.velocity = 0.0f;
                }
            }

            reverbInfo.freqScale *= playbackState->vibratoFreqScale * playbackState->portamentoFreqScale;
            reverbInfo.freqScale *= gAudioBufferParameters.resampleRate;
            reverbInfo.velocity *= scale;
            note_set_vel_pan_reverb(note, &reverbInfo);
#else
            if (playbackState->priority == NOTE_PRIORITY_STOPPING) {
                frequency = attributes->freqScale;
                velocity = attributes->velocity;
                pan = attributes->pan;
                reverbVol = attributes->reverbVol;
                if (1) {
                }
                bookOffset = noteSubEu->bookOffset;
            } else {
                frequency = playbackState->parentLayer->noteFreqScale;
                velocity = playbackState->parentLayer->noteVelocity;
                pan = playbackState->parentLayer->notePan;
                reverbVol = playbackState->parentLayer->seqChannel->reverbVol;
                bookOffset = playbackState->parentLayer->seqChannel->bookOffset & 0x7;
            }

            frequency *= playbackState->vibratoFreqScale * playbackState->portamentoFreqScale;
            frequency *= gAudioBufferParameters.resampleRate;
            velocity = velocity * scale * scale;
            note_set_resampling_rate(note, frequency);
            note_set_vel_pan_reverb(note, velocity, pan, reverbVol);
#endif
            noteSubEu->bookOffset = bookOffset;
            skip:;
        }
#else
        if (note->priority != NOTE_PRIORITY_DISABLED) {
            if (note->priority == NOTE_PRIORITY_STOPPING || note->finished) {
                if (note->adsrVolScale == 0 || note->finished) {
                    if (note->wantedParentLayer != NO_LAYER) {
                        note_disable2(note);
                        if (note->wantedParentLayer->seqChannel != NULL) {
                            if (note_init_for_layer(note, note->wantedParentLayer) == TRUE) {
                                note_disable2(note);
                                POP(&note->listItem);
                                PREPEND(&note->listItem, &gNoteFreeLists.disabled);
                            } else {
                                note_vibrato_init(note);
                                audio_list_push_back(&note->listItem.pool->active,
                                                     POP(&note->listItem));
                                note->wantedParentLayer = NO_LAYER;
                            }
                        } else {
                            note_disable2(note);
                            audio_list_push_back(&note->listItem.pool->disabled, POP(&note->listItem));
                            note->wantedParentLayer = NO_LAYER;
                            continue;
                        }
                    } else {
                        note_disable2(note);
                        audio_list_push_back(&note->listItem.pool->disabled, POP(&note->listItem));
                        continue;
                    }
                }
            } else {
                if (note->adsr.state == ADSR_STATE_DISABLED) {
                    note_disable2(note);
                    audio_list_push_back(&note->listItem.pool->disabled, POP(&note->listItem));
                    continue;
                }
            }

            adsr_update(&note->adsr);
            note_vibrato_update(note);
            attributes = &note->attributes;
            if (note->priority == NOTE_PRIORITY_STOPPING) {
                frequency = attributes->freqScale;
                velocity = attributes->velocity;
                pan = attributes->pan;
                reverbVol = attributes->reverbVol;
            } else {
                frequency = note->parentLayer->noteFreqScale;
                velocity = note->parentLayer->noteVelocity;
                pan = note->parentLayer->notePan;
                reverbVol = note->parentLayer->seqChannel->reverbVol;
            }

            scale = note->adsrVolScale;
            frequency *= note->vibratoFreqScale * note->portamentoFreqScale;
            cap = 3.99992f;
            if (gAiFrequency != 32006) {
                frequency *= US_FLOAT(32000.0) / (f32) gAiFrequency;
            }
            frequency = (frequency < cap ? frequency : cap);
            scale *= 4.3498e-5f; // ~1 / 23000
            velocity = velocity * scale * scale;
            note_set_frequency(note, frequency);
            note_set_vel_pan_reverb(note, velocity, pan, reverbVol);
            continue;
        }
#endif
    }
#undef PREPEND
#undef POP
}

#if defined(VERSION_SH)
// These three are matching but have been moved from above in shindou:
struct AudioBankSound *instrument_get_audio_bank_sound(struct Instrument *instrument, s32 semitone) {
    struct AudioBankSound *sound;
    if (semitone < instrument->normalRangeLo) {
        sound = &instrument->lowNotesSound;
    } else if (semitone <= instrument->normalRangeHi) {
        sound = &instrument->normalNotesSound;
    } else {
        sound = &instrument->highNotesSound;
    }
    return sound;
}
struct Instrument *get_instrument_inner(s32 bankId, s32 instId) {
    struct Instrument *inst;

    if (IS_BANK_LOAD_COMPLETE(bankId) == FALSE) {
        gAudioErrorFlags = bankId + 0x10000000;
        return NULL;
    }

    if (instId >= gCtlEntries[bankId].numInstruments) {
        gAudioErrorFlags = ((bankId << 8) + instId) + 0x3000000;
        return NULL;
    }

    inst = gCtlEntries[bankId].instruments[instId];
    if (inst == NULL) {
        gAudioErrorFlags = ((bankId << 8) + instId) + 0x1000000;
        return inst;
    }

    return inst;
}

struct Drum *get_drum(s32 bankId, s32 drumId) {
    struct Drum *drum;

    if (IS_BANK_LOAD_COMPLETE(bankId) == FALSE) {
        gAudioErrorFlags = bankId + 0x10000000;
        return NULL;
    }

    if (drumId >= gCtlEntries[bankId].numDrums) {
        gAudioErrorFlags = ((bankId << 8) + drumId) + 0x4000000;
        return NULL;
    }

#ifndef NO_SEGMENTED_MEMORY
    if ((uintptr_t) gCtlEntries[bankId].drums < 0x80000000U) {
        return NULL;
    }
#endif

    drum = gCtlEntries[bankId].drums[drumId];
    if (drum == NULL) {
        gAudioErrorFlags = ((bankId << 8) + drumId) + 0x5000000;
    }
    return drum;
}
#endif

void seq_channel_layer_decay_release_internal(struct SequenceChannelLayer *seqLayer, s32 target) {
    struct Note *note;
    struct NoteAttributes *attributes;

    if (seqLayer == NO_LAYER) {
        return;
    }

#ifdef VERSION_SH
    seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
#endif

    if (seqLayer->note == NULL) {
        return;
    }

    note = seqLayer->note;
    attributes = &note->attributes;

#if defined(VERSION_JP) || defined(VERSION_US)
    if (seqLayer->seqChannel != NULL && seqLayer->seqChannel->noteAllocPolicy == 0) {
        seqLayer->note = NULL;
    }
#endif

    if (note->wantedParentLayer == seqLayer) {
        note->wantedParentLayer = NO_LAYER;
    }

    if (note->parentLayer != seqLayer) {

#if defined(VERSION_EU) || defined(VERSION_SH)
        if (note->parentLayer == NO_LAYER && note->wantedParentLayer == NO_LAYER &&
                note->prevParentLayer == seqLayer && target != ADSR_STATE_DECAY) {
            // Just guessing that this printf goes here... it's hard to parse.
            eu_stubbed_printf_0("Slow Release Batting\n");
            note->adsr.fadeOutVel = gAudioBufferParameters.updatesPerFrameInv;
            note->adsr.action |= ADSR_ACTION_RELEASE;
        }
#endif
        return;
    }

#ifndef VERSION_SH
    seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
#endif
    if (note->adsr.state != ADSR_STATE_DECAY) {
        attributes->freqScale = seqLayer->noteFreqScale;
        attributes->velocity = seqLayer->noteVelocity;
        attributes->pan = seqLayer->notePan;
#ifdef VERSION_SH
        attributes->reverbBits = seqLayer->reverbBits;
#endif
        if (seqLayer->seqChannel != NULL) {
            attributes->reverbVol = seqLayer->seqChannel->reverbVol;
#ifdef VERSION_SH
            attributes->synthesisVolume = seqLayer->seqChannel->synthesisVolume;
            attributes->filter = seqLayer->seqChannel->filter;
            if (seqLayer->seqChannel->seqPlayer->muted && (seqLayer->seqChannel->muteBehavior & 8) != 0) {
                note->noteSubEu.finished = TRUE;
            }
            note->priority = seqLayer->seqChannel->unkSH06;
#endif
        }
#ifdef VERSION_SH
        else {
#endif
            note->priority = NOTE_PRIORITY_STOPPING;
#ifdef VERSION_SH
        }
#endif
        note->prevParentLayer = note->parentLayer;
        note->parentLayer = NO_LAYER;
        if (target == ADSR_STATE_RELEASE) {
#if defined(VERSION_EU) || defined(VERSION_SH)
            note->adsr.fadeOutVel = gAudioBufferParameters.updatesPerFrameInv;
#else
            note->adsr.fadeOutVel = 0x8000 / gAudioUpdatesPerFrame;
#endif
            note->adsr.action |= ADSR_ACTION_RELEASE;
#ifdef VERSION_SH
            note->unkSH34 = 2;
#endif
        } else {
#ifdef VERSION_SH
            note->unkSH34 = 1;
#endif
            note->adsr.action |= ADSR_ACTION_DECAY;
#if defined(VERSION_EU) || defined(VERSION_SH)
            if (seqLayer->adsr.releaseRate == 0) {
                note->adsr.fadeOutVel = seqLayer->seqChannel->adsr.releaseRate * gAudioBufferParameters.unkUpdatesPerFrameScaled;
            } else {
                note->adsr.fadeOutVel = seqLayer->adsr.releaseRate * gAudioBufferParameters.unkUpdatesPerFrameScaled;
            }
            note->adsr.sustain = (FLOAT_CAST(seqLayer->seqChannel->adsr.sustain) * note->adsr.current) / 256.0f;
#else
            if (seqLayer->adsr.releaseRate == 0) {
                note->adsr.fadeOutVel = seqLayer->seqChannel->adsr.releaseRate * 24;
            } else {
                note->adsr.fadeOutVel = seqLayer->adsr.releaseRate * 24;
            }
            note->adsr.sustain = (note->adsr.current * seqLayer->seqChannel->adsr.sustain) / 0x10000;
#endif
        }
    }

    if (target == ADSR_STATE_DECAY) {
        audio_list_remove(&note->listItem);
        audio_list_push_front(&note->listItem.pool->decaying, &note->listItem);
    }
}

void seq_channel_layer_note_decay(struct SequenceChannelLayer *seqLayer) {
    seq_channel_layer_decay_release_internal(seqLayer, ADSR_STATE_DECAY);
}

void seq_channel_layer_note_release(struct SequenceChannelLayer *seqLayer) {
    seq_channel_layer_decay_release_internal(seqLayer, ADSR_STATE_RELEASE);
}

#if defined(VERSION_EU) || defined(VERSION_SH)
s32 build_synthetic_wave(struct Note *note, struct SequenceChannelLayer *seqLayer, s32 waveId) {
    f32 freqScale;
    f32 ratio;
    u8 sampleCountIndex;

    if (waveId < 128) {
#ifdef VERSION_EU
        stubbed_printf("Audio:Wavemem: Bad voiceno (%d)\n", waveId);
#endif
        waveId = 128;
    }

    freqScale = seqLayer->freqScale;
    if (seqLayer->portamento.mode != 0 && 0.0f < seqLayer->portamento.extent) {
        freqScale *= (seqLayer->portamento.extent + 1.0f);
    }
    if (freqScale < 1.0f) {
        sampleCountIndex = 0;
        ratio = 1.0465f;
    } else if (freqScale < 2.0f) {
        sampleCountIndex = 1;
        ratio = 0.52325f;
    } else if (freqScale < 4.0f) {
        sampleCountIndex = 2;
        ratio = 0.26263f;
    } else {
        sampleCountIndex = 3;
        ratio = 0.13081f;
    }
    seqLayer->freqScale *= ratio;
    note->waveId = waveId;
    note->sampleCountIndex = sampleCountIndex;

    note->noteSubEu.sound.samples = &gWaveSamples[waveId - 128][sampleCountIndex * 64];

    return sampleCountIndex;
}

#else
void build_synthetic_wave(struct Note *note, struct SequenceChannelLayer *seqLayer) {
    s32 i;
    s32 j;
    s32 pos;
    s32 stepSize;
    s32 offset;
    u8 lim;
    u8 origSampleCount = note->sampleCount;

    if (seqLayer->freqScale < US_FLOAT(1.0)) {
        note->sampleCount = 64;
        seqLayer->freqScale *= US_FLOAT(1.0465);
        stepSize = 1;
    } else if (seqLayer->freqScale < US_FLOAT(2.0)) {
        note->sampleCount = 32;
        seqLayer->freqScale *= US_FLOAT(0.52325);
        stepSize = 2;
    } else if (seqLayer->freqScale < US_FLOAT(4.0)) {
        note->sampleCount = 16;
        seqLayer->freqScale *= US_FLOAT(0.26263);
        stepSize = 4;
    } else {
        note->sampleCount = 8;
        seqLayer->freqScale *= US_FLOAT(0.13081);
        stepSize = 8;
    }

    if (note->sampleCount == origSampleCount && seqLayer->seqChannel->instOrWave == note->instOrWave) {
        return;
    }

    // Load wave sample
    note->instOrWave = (u8) seqLayer->seqChannel->instOrWave;
    for (i = -1, pos = 0; pos < 0x40; pos += stepSize) {
        i++;
        note->synthesisBuffers->samples[i] = gWaveSamples[seqLayer->seqChannel->instOrWave - 0x80][pos];
    }

    // Repeat sample
    for (offset = note->sampleCount; offset < 0x40; offset += note->sampleCount) {
        lim = note->sampleCount;
        if (offset < 0 || offset > 0) {
            for (j = 0; j < lim; j++) {
                note->synthesisBuffers->samples[offset + j] = note->synthesisBuffers->samples[j];
            }
        } else {
            for (j = 0; j < lim; j++) {
                note->synthesisBuffers->samples[offset + j] = note->synthesisBuffers->samples[j];
            }
        }
    }

    osWritebackDCache(note->synthesisBuffers->samples, sizeof(note->synthesisBuffers->samples));
}
#endif

void init_synthetic_wave(struct Note *note, struct SequenceChannelLayer *seqLayer) {
#if defined(VERSION_EU) || defined(VERSION_SH)
    s32 sampleCountIndex;
    s32 waveSampleCountIndex;
    s32 waveId = seqLayer->instOrWave;
    if (waveId == 0xff) {
        waveId = seqLayer->seqChannel->instOrWave;
    }
    sampleCountIndex = note->sampleCountIndex;
    waveSampleCountIndex = build_synthetic_wave(note, seqLayer, waveId);
#if defined(VERSION_EU) || defined(VERSION_SH)
    note->synthesisState.samplePosInt = note->synthesisState.samplePosInt * euUnknownData_8030194c[waveSampleCountIndex] / euUnknownData_8030194c[sampleCountIndex];
#else // Not a real change. Just temporary so I can remove this variable.
    note->synthesisState.samplePosInt = note->synthesisState.samplePosInt * gDefaultShortNoteVelocityTable[waveSampleCountIndex] / gDefaultShortNoteVelocityTable[sampleCountIndex];
#endif
#else
    s32 sampleCount = note->sampleCount;
    build_synthetic_wave(note, seqLayer);
    if (sampleCount != 0) {
        note->samplePosInt *= note->sampleCount / sampleCount;
    } else {
        note->samplePosInt = 0;
    }
#endif
}

void init_note_list(struct AudioListItem *list) {
    list->prev = list;
    list->next = list;
    list->u.count = 0;
}

void init_note_lists(struct NotePool *pool) {
    init_note_list(&pool->disabled);
    init_note_list(&pool->decaying);
    init_note_list(&pool->releasing);
    init_note_list(&pool->active);
    pool->disabled.pool = pool;
    pool->decaying.pool = pool;
    pool->releasing.pool = pool;
    pool->active.pool = pool;
}

void init_note_free_list(void) {
    s32 i;

    init_note_lists(&gNoteFreeLists);
    for (i = 0; i < gMaxSimultaneousNotes; i++) {
        gNotes[i].listItem.u.value = &gNotes[i];
        gNotes[i].listItem.prev = NULL;
        audio_list_push_back(&gNoteFreeLists.disabled, &gNotes[i].listItem);
    }
}

void note_pool_clear(struct NotePool *pool) {
    s32 i;
    struct AudioListItem *source;
    struct AudioListItem *cur;
    struct AudioListItem *dest;
    UNUSED s32 j; // unused in EU

    for (i = 0; i < 4; i++) {
        switch (i) {
            case 0:
                source = &pool->disabled;
                dest = &gNoteFreeLists.disabled;
                break;

            case 1:
                source = &pool->decaying;
                dest = &gNoteFreeLists.decaying;
                break;

            case 2:
                source = &pool->releasing;
                dest = &gNoteFreeLists.releasing;
                break;

            case 3:
                source = &pool->active;
                dest = &gNoteFreeLists.active;
                break;
        }

#if defined(VERSION_EU) || defined(VERSION_SH)
        for (;;) {
            cur = source->next;
            if (cur == source) {
                break;
            }
            if (cur == NULL) {
                eu_stubbed_printf_0("Audio: C-Alloc : Dealloc voice is NULL\n");
                break;
            }
            audio_list_remove(cur);
            audio_list_push_back(dest, cur);
        }
#else
        j = 0;
        do {
            cur = source->next;
            if (cur == source) {
                break;
            }
            audio_list_remove(cur);
            audio_list_push_back(dest, cur);
            j++;
        } while (j <= gMaxSimultaneousNotes);
#endif
    }
}

void note_pool_fill(struct NotePool *pool, s32 count) {
    s32 i;
    s32 j;
    struct Note *note;
    struct AudioListItem *source;
    struct AudioListItem *dest;

    note_pool_clear(pool);

    for (i = 0, j = 0; j < count; i++) {
        if (i == 4) {
            eu_stubbed_printf_1("Alloc Error:Dim voice-Alloc %d", count);
            return;
        }

        switch (i) {
            case 0:
                source = &gNoteFreeLists.disabled;
                dest = &pool->disabled;
                break;

            case 1:
                source = &gNoteFreeLists.decaying;
                dest = &pool->decaying;
                break;

            case 2:
                source = &gNoteFreeLists.releasing;
                dest = &pool->releasing;
                break;

            case 3:
                source = &gNoteFreeLists.active;
                dest = &pool->active;
                break;
        }

        while (j < count) {
            note = audio_list_pop_back(source);
            if (note == NULL) {
                break;
            }
            audio_list_push_back(dest, &note->listItem);
            j++;
        }
    }
}

void audio_list_push_front(struct AudioListItem *list, struct AudioListItem *item) {
    // add 'item' to the front of the list given by 'list', if it's not in any list
    if (item->prev != NULL) {
        eu_stubbed_printf_0("Error:Same List Add\n");
    } else {
        item->prev = list;
        item->next = list->next;
        list->next->prev = item;
        list->next = item;
        list->u.count++;
        item->pool = list->pool;
    }
}

void audio_list_remove(struct AudioListItem *item) {
    // remove 'item' from the list it's in, if any
    if (item->prev == NULL) {
        eu_stubbed_printf_0("Already Cut\n");
    } else {
        item->prev->next = item->next;
        item->next->prev = item->prev;
        item->prev = NULL;
    }
}

struct Note *pop_node_with_lower_prio(struct AudioListItem *list, s32 limit) {
    struct AudioListItem *cur = list->next;
    struct AudioListItem *best;

    if (cur == list) {
        return NULL;
    }

    for (best = cur; cur != list; cur = cur->next) {
        if (((struct Note *) best->u.value)->priority >= ((struct Note *) cur->u.value)->priority) {
            best = cur;
        }
    }

#if defined(VERSION_EU) || defined(VERSION_SH)
    if (best == NULL) {
        return NULL;
    }

    if (limit <= ((struct Note *) best->u.value)->priority) {
        return NULL;
    }
#else
    if (limit < ((struct Note *) best->u.value)->priority) {
        return NULL;
    }
#endif

#ifndef VERSION_SH
    audio_list_remove(best);
#endif
    return best->u.value;
}

#if defined(VERSION_EU) || defined(VERSION_SH)
void note_init_for_layer(struct Note *note, struct SequenceChannelLayer *seqLayer) {
    UNUSED s32 pad[4];
    s16 instId;
    struct NoteSubEu *sub = &note->noteSubEu;

    note->prevParentLayer = NO_LAYER;
    note->parentLayer = seqLayer;
    note->priority = seqLayer->seqChannel->notePriority;
    seqLayer->notePropertiesNeedInit = TRUE;
    seqLayer->status = SOUND_LOAD_STATUS_DISCARDABLE; // "loaded"
    seqLayer->note = note;
    seqLayer->seqChannel->noteUnused = note;
    seqLayer->seqChannel->layerUnused = seqLayer;
    seqLayer->noteVelocity = 0.0f;
    note_init(note);
    instId = seqLayer->instOrWave;
    if (instId == 0xff) {
        instId = seqLayer->seqChannel->instOrWave;
    }
    sub->sound.audioBankSound = seqLayer->sound;

    if (instId >= 0x80) {
        sub->isSyntheticWave = TRUE;
    } else {
        sub->isSyntheticWave = FALSE;
    }

    if (sub->isSyntheticWave) {
        build_synthetic_wave(note, seqLayer, instId);
    }
#ifdef VERSION_SH
    note->bankId = seqLayer->seqChannel->bankId;
#else
    sub->bankId = seqLayer->seqChannel->bankId;
#endif
    sub->stereoHeadsetEffects = seqLayer->seqChannel->stereoHeadsetEffects;
    sub->reverbIndex = seqLayer->seqChannel->reverbIndex & 3;
}
#else
s32 note_init_for_layer(struct Note *note, struct SequenceChannelLayer *seqLayer) {
    note->prevParentLayer = NO_LAYER;
    note->parentLayer = seqLayer;
    note->priority = seqLayer->seqChannel->notePriority;
    if (IS_BANK_LOAD_COMPLETE(seqLayer->seqChannel->bankId) == FALSE) {
        return TRUE;
    }

    note->bankId = seqLayer->seqChannel->bankId;
    note->stereoHeadsetEffects = seqLayer->seqChannel->stereoHeadsetEffects;
    note->sound = seqLayer->sound;
    seqLayer->status = SOUND_LOAD_STATUS_DISCARDABLE; // "loaded"
    seqLayer->note = note;
    seqLayer->seqChannel->noteUnused = note;
    seqLayer->seqChannel->layerUnused = seqLayer;
    if (note->sound == NULL) {
        build_synthetic_wave(note, seqLayer);
    }
    note_init(note);
    return FALSE;
}
#endif

void func_80319728(struct Note *note, struct SequenceChannelLayer *seqLayer) {
    seq_channel_layer_note_release(note->parentLayer);
    note->wantedParentLayer = seqLayer;
}

void note_release_and_take_ownership(struct Note *note, struct SequenceChannelLayer *seqLayer) {
    note->wantedParentLayer = seqLayer;
#ifdef VERSION_SH
    note->priority = seqLayer->seqChannel->notePriority;
#else
    note->priority = NOTE_PRIORITY_STOPPING;
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
    note->adsr.fadeOutVel = gAudioBufferParameters.updatesPerFrameInv;
#else
    note->adsr.fadeOutVel = 0x8000 / gAudioUpdatesPerFrame;
#endif
    note->adsr.action |= ADSR_ACTION_RELEASE;
}

struct Note *alloc_note_from_disabled(struct NotePool *pool, struct SequenceChannelLayer *seqLayer) {
    struct Note *note = audio_list_pop_back(&pool->disabled);
    if (note != NULL) {
#if defined(VERSION_EU) || defined(VERSION_SH)
        note_init_for_layer(note, seqLayer);
#else
        if (note_init_for_layer(note, seqLayer) == TRUE) {
            audio_list_push_front(&gNoteFreeLists.disabled, &note->listItem);
            return NULL;
        }
#endif
        audio_list_push_front(&pool->active, &note->listItem);
    }
    return note;
}

struct Note *alloc_note_from_decaying(struct NotePool *pool, struct SequenceChannelLayer *seqLayer) {
    struct Note *note = audio_list_pop_back(&pool->decaying);
    if (note != NULL) {
        note_release_and_take_ownership(note, seqLayer);
        audio_list_push_back(&pool->releasing, &note->listItem);
    }
    return note;
}

struct Note *alloc_note_from_active(struct NotePool *pool, struct SequenceChannelLayer *seqLayer) {
#ifdef VERSION_SH
    struct Note *rNote;
#endif
    struct Note *aNote;
#ifdef VERSION_SH
    s32 rPriority, aPriority;
    rPriority = aPriority = 0x10;

    rNote = pop_node_with_lower_prio(&pool->releasing, seqLayer->seqChannel->notePriority);

    if (rNote != NULL) {
        rPriority = rNote->priority;
    }
#endif

    aNote = pop_node_with_lower_prio(&pool->active, seqLayer->seqChannel->notePriority);

    if (aNote == NULL) {
        eu_stubbed_printf_0("Audio: C-Alloc : lowerPrio is NULL\n");
    } else {
#ifdef VERSION_SH
        aPriority = aNote->priority;
#else
        func_80319728(aNote, seqLayer);
        audio_list_push_back(&pool->releasing, &aNote->listItem);
#endif
    }

#ifdef VERSION_SH
    if (rNote == NULL && aNote == NULL) {
        return NULL;
    }

    if (aPriority < rPriority) {
        audio_list_remove(&aNote->listItem);
        func_80319728(aNote, seqLayer);
        audio_list_push_back(&pool->releasing, &aNote->listItem);
        aNote->priority = seqLayer->seqChannel->notePriority;
        return aNote;
    }
    rNote->wantedParentLayer = seqLayer;
    rNote->priority = seqLayer->seqChannel->notePriority;
    return rNote;
#else
    return aNote;
#endif
}

struct Note *alloc_note(struct SequenceChannelLayer *seqLayer) {
    struct Note *ret;
    u32 policy = seqLayer->seqChannel->noteAllocPolicy;

    if (policy & NOTE_ALLOC_LAYER) {
        ret = seqLayer->note;
        if (ret != NULL && ret->prevParentLayer == seqLayer
#if defined(VERSION_EU) || defined(VERSION_SH)
                && ret->wantedParentLayer == NO_LAYER
#endif
                ) {
            note_release_and_take_ownership(ret, seqLayer);
            audio_list_remove(&ret->listItem);
#if defined(VERSION_EU) || defined(VERSION_SH)
            audio_list_push_back(&ret->listItem.pool->releasing, &ret->listItem);
#else
            audio_list_push_back(&gNoteFreeLists.releasing, &ret->listItem);
#endif
            return ret;
        }
    }

    if (policy & NOTE_ALLOC_CHANNEL) {
        if (!(ret = alloc_note_from_disabled(&seqLayer->seqChannel->notePool, seqLayer))
            && !(ret = alloc_note_from_decaying(&seqLayer->seqChannel->notePool, seqLayer))
            && !(ret = alloc_note_from_active(&seqLayer->seqChannel->notePool, seqLayer))) {
#ifdef VERSION_SH
            goto null_return;
#else
            eu_stubbed_printf_0("Sub Limited Warning: Drop Voice");
            seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
            return NULL;
#endif
        }
        return ret;
    }

    if (policy & NOTE_ALLOC_SEQ) {
        if (!(ret = alloc_note_from_disabled(&seqLayer->seqChannel->notePool, seqLayer))
            && !(ret = alloc_note_from_disabled(&seqLayer->seqChannel->seqPlayer->notePool, seqLayer))
            && !(ret = alloc_note_from_decaying(&seqLayer->seqChannel->notePool, seqLayer))
            && !(ret = alloc_note_from_decaying(&seqLayer->seqChannel->seqPlayer->notePool, seqLayer))
            && !(ret = alloc_note_from_active(&seqLayer->seqChannel->notePool, seqLayer))
            && !(ret = alloc_note_from_active(&seqLayer->seqChannel->seqPlayer->notePool, seqLayer))) {
#ifdef VERSION_SH
            goto null_return;
#else
            eu_stubbed_printf_0("Warning: Drop Voice");
            seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
            return NULL;
#endif
        }
        return ret;
    }

    if (policy & NOTE_ALLOC_GLOBAL_FREELIST) {
        if (!(ret = alloc_note_from_disabled(&gNoteFreeLists, seqLayer))
            && !(ret = alloc_note_from_decaying(&gNoteFreeLists, seqLayer))
            && !(ret = alloc_note_from_active(&gNoteFreeLists, seqLayer))) {
#ifdef VERSION_SH
            goto null_return;
#else
            eu_stubbed_printf_0("Warning: Drop Voice");
            seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
            return NULL;
#endif
        }
        return ret;
    }

    if (!(ret = alloc_note_from_disabled(&seqLayer->seqChannel->notePool, seqLayer))
        && !(ret = alloc_note_from_disabled(&seqLayer->seqChannel->seqPlayer->notePool, seqLayer))
        && !(ret = alloc_note_from_disabled(&gNoteFreeLists, seqLayer))
        && !(ret = alloc_note_from_decaying(&seqLayer->seqChannel->notePool, seqLayer))
        && !(ret = alloc_note_from_decaying(&seqLayer->seqChannel->seqPlayer->notePool, seqLayer))
        && !(ret = alloc_note_from_decaying(&gNoteFreeLists, seqLayer))
        && !(ret = alloc_note_from_active(&seqLayer->seqChannel->notePool, seqLayer))
        && !(ret = alloc_note_from_active(&seqLayer->seqChannel->seqPlayer->notePool, seqLayer))
        && !(ret = alloc_note_from_active(&gNoteFreeLists, seqLayer))) {
#ifdef VERSION_SH
        goto null_return;
#else
        eu_stubbed_printf_0("Warning: Drop Voice");
        seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
        return NULL;
#endif
    }
    return ret;

#ifdef VERSION_SH
null_return:
    seqLayer->status = SOUND_LOAD_STATUS_NOT_LOADED;
    return NULL;
#endif
}

#if defined(VERSION_JP) || defined(VERSION_US)
void reclaim_notes(void) {
    struct Note *note;
    s32 i;
    s32 cond;

    for (i = 0; i < gMaxSimultaneousNotes; i++) {
        note = &gNotes[i];
        if (note->parentLayer != NO_LAYER) {
            cond = FALSE;
            if (!note->parentLayer->enabled && note->priority >= NOTE_PRIORITY_MIN) {
                cond = TRUE;
            } else if (note->parentLayer->seqChannel == NULL) {
                audio_list_push_back(&gLayerFreeList, &note->parentLayer->listItem);
                seq_channel_layer_disable(note->parentLayer);
                note->priority = NOTE_PRIORITY_STOPPING;
            } else if (note->parentLayer->seqChannel->seqPlayer == NULL) {
                sequence_channel_disable(note->parentLayer->seqChannel);
                note->priority = NOTE_PRIORITY_STOPPING;
            } else if (note->parentLayer->seqChannel->seqPlayer->muted) {
                if (note->parentLayer->seqChannel->muteBehavior
                    & (MUTE_BEHAVIOR_STOP_SCRIPT | MUTE_BEHAVIOR_STOP_NOTES)) {
                    cond = TRUE;
                }
            } else {
                cond = FALSE;
            }

            if (cond) {
                seq_channel_layer_note_release(note->parentLayer);
                audio_list_remove(&note->listItem);
                audio_list_push_front(&note->listItem.pool->disabled, &note->listItem);
                note->priority = NOTE_PRIORITY_STOPPING;
            }
        }
    }
}
#endif

void note_init_all(void) {
    struct Note *note;
    s32 i;

    for (i = 0; i < gMaxSimultaneousNotes; i++) {
        note = &gNotes[i];
#if defined(VERSION_EU) || defined(VERSION_SH)
        note->noteSubEu = gZeroNoteSub;
#else
        note->enabled = FALSE;
        note->stereoStrongRight = FALSE;
        note->stereoStrongLeft = FALSE;
        note->stereoHeadsetEffects = FALSE;
#endif
        note->priority = NOTE_PRIORITY_DISABLED;
#ifdef VERSION_SH
        note->unkSH34 = 0;
#endif
        note->parentLayer = NO_LAYER;
        note->wantedParentLayer = NO_LAYER;
        note->prevParentLayer = NO_LAYER;
#if defined(VERSION_EU) || defined(VERSION_SH)
        note->waveId = 0;
#else
        note->reverbVol = 0;
        note->usesHeadsetPanEffects = FALSE;
        note->sampleCount = 0;
        note->instOrWave = 0;
        note->targetVolLeft = 0;
        note->targetVolRight = 0;
        note->frequency = 0.0f;
        note->unused1 = 0x3f;
#endif
        note->attributes.velocity = 0.0f;
        note->adsrVolScale = 0;
        note->adsr.state = ADSR_STATE_DISABLED;
        note->adsr.action = 0;
        note->vibratoState.active = FALSE;
        note->portamento.cur = 0.0f;
        note->portamento.speed = 0.0f;
#if defined(VERSION_SH)
        note->synthesisState.synthesisBuffers = sound_alloc_uninitialized(&gNotesAndBuffersPool, sizeof(struct NoteSynthesisBuffers));
#elif defined(VERSION_EU)
        note->synthesisState.synthesisBuffers = soundAlloc(&gNotesAndBuffersPool, sizeof(struct NoteSynthesisBuffers));
#else
        note->synthesisBuffers = soundAlloc(&gNotesAndBuffersPool, sizeof(struct NoteSynthesisBuffers));
#endif
    }
}
