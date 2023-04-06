#include <ultra64.h>
#include <macros.h>

#include "heap.h"
#include "data.h"
#include "load.h"
#include "seqplayer.h"
#include "external.h"
#include "effects.h"

#define PORTAMENTO_IS_SPECIAL(x) ((x).mode & 0x80)
#define PORTAMENTO_MODE(x) ((x).mode & ~0x80)
#define PORTAMENTO_MODE_1 1
#define PORTAMENTO_MODE_2 2
#define PORTAMENTO_MODE_3 3
#define PORTAMENTO_MODE_4 4
#define PORTAMENTO_MODE_5 5

void seq_channel_layer_process_script(struct SequenceChannelLayer *layer);
void sequence_channel_process_script(struct SequenceChannel *seqChannel);
u8 get_instrument(struct SequenceChannel *seqChannel, u8 instId, struct Instrument **instOut,
                  struct AdsrSettings *adsr);

void sequence_channel_init(struct SequenceChannel *seqChannel) {
    s32 i;

    seqChannel->enabled = FALSE;
    seqChannel->finished = FALSE;
    seqChannel->stopScript = FALSE;
    seqChannel->stopSomething2 = FALSE;
    seqChannel->hasInstrument = FALSE;
    seqChannel->stereoHeadsetEffects = FALSE;
    seqChannel->transposition = 0;
    seqChannel->largeNotes = FALSE;
#ifdef VERSION_EU
    seqChannel->bookOffset = 0;
    seqChannel->changes.as_u8 = 0xff;
    seqChannel->scriptState.depth = 0;
    seqChannel->newPan = 0x40;
    seqChannel->panChannelWeight = 0x80;
    seqChannel->noteUnused = NULL;
    seqChannel->reverbIndex = 0;
#else
    seqChannel->scriptState.depth = 0;
    seqChannel->volume = 1.0f;
    seqChannel->volumeScale = 1.0f;
    seqChannel->freqScale = 1.0f;
    seqChannel->pan = 0.5f;
    seqChannel->panChannelWeight = 1.0f;
    seqChannel->noteUnused = NULL;
#endif
    seqChannel->reverb = 0;
    seqChannel->notePriority = NOTE_PRIORITY_DEFAULT;
    seqChannel->delay = 0;
    seqChannel->adsr.envelope = gDefaultEnvelope;
    seqChannel->adsr.releaseRate = 0x20;
    seqChannel->adsr.sustain = 0;
#ifndef VERSION_EU
    seqChannel->updatesPerFrameUnused = gAudioUpdatesPerFrame;
#endif
    seqChannel->vibratoRateTarget = 0x800;
    seqChannel->vibratoRateStart = 0x800;
    seqChannel->vibratoExtentTarget = 0;
    seqChannel->vibratoExtentStart = 0;
    seqChannel->vibratoRateChangeDelay = 0;
    seqChannel->vibratoExtentChangeDelay = 0;
    seqChannel->vibratoDelay = 0;
#ifdef VERSION_EU
    seqChannel->volume = 1.0f;
    seqChannel->volumeScale = 1.0f;
    seqChannel->freqScale = 1.0f;
#endif

    for (i = 0; i < 8; i++) {
        seqChannel->soundScriptIO[i] = -1;
    }

    seqChannel->unused = FALSE;
    init_note_lists(&seqChannel->notePool);
}

s32 seq_channel_set_layer(struct SequenceChannel *seqChannel, s32 layerIndex) {
    struct SequenceChannelLayer *layer;

    if (seqChannel->layers[layerIndex] == NULL) {
#ifdef VERSION_EU
        struct SequenceChannelLayer *layer;
#endif
        layer = audio_list_pop_back(&gLayerFreeList);
        seqChannel->layers[layerIndex] = layer;
        if (layer == NULL) {
            seqChannel->layers[layerIndex] = NULL;
            return -1;
        }
    } else {
        seq_channel_layer_note_decay(seqChannel->layers[layerIndex]);
    }

    layer = seqChannel->layers[layerIndex];
    layer->seqChannel = seqChannel;
    layer->adsr = seqChannel->adsr;
    layer->adsr.releaseRate = 0;
    layer->enabled = TRUE;
    layer->stopSomething = FALSE;
    layer->continuousNotes = FALSE;
    layer->finished = FALSE;
#ifdef VERSION_EU
    layer->ignoreDrumPan = FALSE;
#endif
    layer->portamento.mode = 0;
    layer->scriptState.depth = 0;
    layer->status = SOUND_LOAD_STATUS_NOT_LOADED;
    layer->noteDuration = 0x80;
#ifdef VERSION_EU
    layer->pan = 0x40;
#endif
    layer->transposition = 0;
    layer->delay = 0;
    layer->duration = 0;
    layer->delayUnused = 0;
    layer->note = NULL;
    layer->instrument = NULL;
#ifdef VERSION_EU
    layer->freqScale = 1.0f;
    layer->velocitySquare = 0.0f;
    layer->instOrWave = 0xff;
#else
    layer->velocitySquare = 0.0f;
    layer->pan = 0.5f;
#endif
    return 0;
}

void seq_channel_layer_disable(struct SequenceChannelLayer *layer) {
    if (layer != NULL) {
        seq_channel_layer_note_decay(layer);
        layer->enabled = FALSE;
        layer->finished = TRUE;
    }
}

void seq_channel_layer_free(struct SequenceChannel *seqChannel, s32 layerIndex) {
    struct SequenceChannelLayer *layer = seqChannel->layers[layerIndex];

    if (layer != NULL) {
#ifdef VERSION_EU
        audio_list_push_back(&gLayerFreeList, &layer->listItem);
#else
        struct AudioListItem *item = &layer->listItem;
        if (item->prev == NULL) {
            gLayerFreeList.prev->next = item;
            item->prev = gLayerFreeList.prev;
            item->next = &gLayerFreeList;
            gLayerFreeList.prev = item;
            gLayerFreeList.u.count++;
            item->pool = gLayerFreeList.pool;
        }
#endif
        seq_channel_layer_disable(layer);
        seqChannel->layers[layerIndex] = NULL;
    }
}

void sequence_channel_disable(struct SequenceChannel *seqChannel) {
    s32 i;
    for (i = 0; i < LAYERS_MAX; i++) {
        seq_channel_layer_free(seqChannel, i);
    }

    note_pool_clear(&seqChannel->notePool);
    seqChannel->enabled = FALSE;
    seqChannel->finished = TRUE;
}

struct SequenceChannel *allocate_sequence_channel(void) {
    s32 i;
    for (i = 0; i < ARRAY_COUNT(gSequenceChannels); i++) {
        if (gSequenceChannels[i].seqPlayer == NULL) {
#ifdef VERSION_EU
            return &gSequenceChannels[i];
#else
            return gSequenceChannels + i;
#endif
        }
    }
    return &gSequenceChannelNone;
}

void sequence_player_init_channels(struct SequencePlayer *seqPlayer, u16 channelBits) {
    struct SequenceChannel *seqChannel;
    s32 i;

    for (i = 0; i < CHANNELS_MAX; i++) {
        if (channelBits & 1) {
            seqChannel = seqPlayer->channels[i];
            if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) == TRUE && seqChannel->seqPlayer == seqPlayer) {
                sequence_channel_disable(seqChannel);
                seqChannel->seqPlayer = NULL;
            }
            seqChannel = allocate_sequence_channel();
            if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) == FALSE) {
                gAudioErrorFlags = i + 0x10000;
                seqPlayer->channels[i] = seqChannel;
            } else {
                sequence_channel_init(seqChannel);
                seqPlayer->channels[i] = seqChannel;
                seqChannel->seqPlayer = seqPlayer;
                seqChannel->bankId = seqPlayer->defaultBank[0];
                seqChannel->muteBehavior = seqPlayer->muteBehavior;
                seqChannel->noteAllocPolicy = seqPlayer->noteAllocPolicy;
            }
        }
#ifdef VERSION_EU
        channelBits = channelBits >> 1;
#else
        channelBits >>= 1;
#endif
    }
}

void sequence_player_disable_channels(struct SequencePlayer *seqPlayer, u16 channelBits) {
    struct SequenceChannel *seqChannel;
    s32 i;

    for (i = 0; i < CHANNELS_MAX; i++) {
        if (channelBits & 1) {
            seqChannel = seqPlayer->channels[i];
            if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) == TRUE) {
                if (seqChannel->seqPlayer == seqPlayer) {
                    sequence_channel_disable(seqChannel);
                    seqChannel->seqPlayer = NULL;
                }
#ifdef VERSION_EU
                if (0) {}
#endif
                seqPlayer->channels[i] = &gSequenceChannelNone;
            }
        }
#ifdef VERSION_EU
        channelBits = channelBits >> 1;
#else
        channelBits >>= 1;
#endif
    }
}

void sequence_channel_enable(struct SequencePlayer *seqPlayer, u8 channelIndex, void *arg2) {
    struct SequenceChannel *seqChannel = seqPlayer->channels[channelIndex];
    s32 i;

#ifdef VERSION_EU
    if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) == FALSE) {
        struct SequencePlayer *bgMusic = &gSequencePlayers[0];
        struct SequencePlayer *miscMusic = &gSequencePlayers[1];

        if (seqPlayer == bgMusic) {
        } else if (seqPlayer == miscMusic) {
        } else {
        }
    } else {
#else
    if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) != FALSE) {
#endif
        seqChannel->enabled = TRUE;
        seqChannel->finished = FALSE;
        seqChannel->scriptState.depth = 0;
        seqChannel->scriptState.pc = arg2;
        seqChannel->delay = 0;
        for (i = 0; i < LAYERS_MAX; i++) {
            if (seqChannel->layers[i] != NULL) {
                seq_channel_layer_free(seqChannel, i);
            }
        }
    }
}

void sequence_player_disable(struct SequencePlayer *seqPlayer) {
    sequence_player_disable_channels(seqPlayer, 0xffff);
    note_pool_clear(&seqPlayer->notePool);
    seqPlayer->finished = TRUE;
    seqPlayer->enabled = FALSE;

    if (IS_SEQ_LOAD_COMPLETE(seqPlayer->seqId)) {
        gSeqLoadStatus[seqPlayer->seqId] = SOUND_LOAD_STATUS_DISCARDABLE;
    }

    if (IS_BANK_LOAD_COMPLETE(seqPlayer->defaultBank[0])) {
        gBankLoadStatus[seqPlayer->defaultBank[0]] = SOUND_LOAD_STATUS_DISCARDABLE;
    }

    // (Note that if this is called from alloc_bank_or_seq, the side will get swapped
    // later in that function. Thus, we signal that we want to load into the slot
    // of the bank that we no longer need.)
#ifdef VERSION_EU
    if (seqPlayer->defaultBank[0] == gBankLoadedPool.temporary.entries[0].id) {
        gBankLoadedPool.temporary.nextSide = 1;
    } else if (seqPlayer->defaultBank[0] == gBankLoadedPool.temporary.entries[1].id) {
        gBankLoadedPool.temporary.nextSide = 0;
    }
#else
    if (gBankLoadedPool.temporary.entries[0].id == seqPlayer->defaultBank[0]) {
        gBankLoadedPool.temporary.nextSide = 1;
    } else if (gBankLoadedPool.temporary.entries[1].id == seqPlayer->defaultBank[0]) {
        gBankLoadedPool.temporary.nextSide = 0;
    }
#endif
}

/**
 * Add an item to the end of a list, if it's not already in any list.
 */
void audio_list_push_back(struct AudioListItem *list, struct AudioListItem *item) {
    if (item->prev == NULL) {
        list->prev->next = item;
        item->prev = list->prev;
        item->next = list;
        list->prev = item;
        list->u.count++;
        item->pool = list->pool;
    }
}

/**
 * Remove the last item from a list, and return it (or NULL if empty).
 */
void *audio_list_pop_back(struct AudioListItem *list) {
    struct AudioListItem *item = list->prev;
    if (item == list) {
        return NULL;
    }
    item->prev->next = list;
    list->prev = item->prev;
    item->prev = NULL;
    list->u.count--;
    return item->u.value;
}

void init_layer_freelist(void) {
    s32 i;

    gLayerFreeList.prev = &gLayerFreeList;
    gLayerFreeList.next = &gLayerFreeList;
    gLayerFreeList.u.count = 0;
    gLayerFreeList.pool = NULL;

    for (i = 0; i < ARRAY_COUNT(gSequenceLayers); i++) {
#ifdef VERSION_EU
        gSequenceLayers[i].listItem.u.value = &gSequenceLayers[i];
#else
        gSequenceLayers[i].listItem.u.value = gSequenceLayers + i;
#endif
        gSequenceLayers[i].listItem.prev = NULL;
        audio_list_push_back(&gLayerFreeList, &gSequenceLayers[i].listItem);
    }
}

u8 m64_read_u8(struct M64ScriptState *state) {
#ifdef VERSION_EU
    return *(state->pc++);
#else
    u8 *midiArg = state->pc++;
    return *midiArg;
#endif
}

s16 m64_read_s16(struct M64ScriptState *state) {
    s16 ret = *(state->pc++) << 8;
    ret = *(state->pc++) | ret;
    return ret;
}

u16 m64_read_compressed_u16(struct M64ScriptState *state) {
    u16 ret = *(state->pc++);
    if (ret & 0x80) {
        ret = (ret << 8) & 0x7f00;
        ret = *(state->pc++) | ret;
    }
    return ret;
}

#ifdef NON_MATCHING
void seq_channel_layer_process_script(struct SequenceChannelLayer *layer) {
    struct SequencePlayer *seqPlayer;   // sp5C, t4
    struct SequenceChannel *seqChannel; // sp58, t5
    struct M64ScriptState *state;       // v0
#ifndef VERSION_EU
    struct AdsrSettings *adsr; // v0
#endif
    struct Portamento *portamento; // v0
#ifndef VERSION_EU
    struct Instrument **instOut;   // a1
    struct Instrument *inst;       // a0
#endif
    struct AudioBankSound *sound;  // v0
    struct Instrument *instrument; // v1
    struct Drum *drum;
#ifdef VERSION_EU
    s32 sameSound;
#else
    u8 sameSound;    // sp3F
#endif
    u8 cmd;          // a0
    u8 allocNewNote; // sp3D, t0
    u8 loBits;
    u16 sp3A; // t2, a0, a1
    s32 vel; // sp30, t3
#ifdef VERSION_EU
    f32 velFloat;
#endif
    f32 freqScale; // sp28, f0
#ifndef VERSION_EU
    f32 sp24;
#endif
#ifdef VERSION_EU
    s8 temp8;
#else
    u8 temp8;
#endif
#ifndef VERSION_EU
    u8 *old;
    u8 *old2;
#endif
    u8 semitone; // v0
    u8 usedSemitone; // a1
    f32 temp_f12;
    f32 temp_f2;
    s32 temp_a0_5;
    u8 drumIndex; // t0
    s32 cmdBase; // t1
    u8 temp_a0_6;
    u8 portamentoTargetNote; // t7
#ifndef VERSION_EU
    s32 bankId; // a3
#endif
    u8 instId; // v0
    s32 cmdSemitone; // v1
    f32 tuning; // f0

    sameSound = TRUE;
    if (layer->enabled == FALSE) {
        return;
    }

    if (layer->delay > 1) {
        layer->delay--;
        if (!layer->stopSomething && layer->delay <= layer->duration) {
            seq_channel_layer_note_decay(layer);
            layer->stopSomething = TRUE;
        }
        return;
    }

    if (!layer->continuousNotes) {
        seq_channel_layer_note_decay(layer);
    }

    if (PORTAMENTO_MODE(layer->portamento) == PORTAMENTO_MODE_1 ||
        PORTAMENTO_MODE(layer->portamento) == PORTAMENTO_MODE_2) {
        layer->portamento.mode = 0;
    }

    seqChannel = layer->seqChannel;
    seqPlayer = seqChannel->seqPlayer;

#ifdef VERSION_EU
    layer->notePropertiesNeedInit = TRUE;
#endif

    for (;;) {
        // (Moving state outside the loop improves initial regalloc, but is wrong)
        state = &layer->scriptState;
#ifdef VERSION_EU
        cmd = m64_read_u8(state);
#else
        old2 = state->pc++;
        cmd = *old2;
#endif
        if (cmd <= 0xc0) {
            break;
        }

        switch (cmd) {
            case 0xff: // layer_end; function return or end of script
                if (state->depth == 0) {
                    // N.B. this function call is *not* inlined even though it's
                    // within the same file, unlike in the rest of this function.
                    seq_channel_layer_disable(layer);
                    return;
                }
                state->depth--, state->pc = state->stack[state->depth];
                break;

            case 0xfc: // layer_call
                // Something is wrong with the codegen here... It almost looks like
                // it's inlining m64_read_s16, but it lacks a s16 cast.
                // Maybe they did macro-based inlining since there are more layers
                // than channels or sequences, making the code hotter.
#ifdef VERSION_EU
                sp3A = m64_read_s16(state);
                state->stack[state->depth++] = state->pc;
#else
                sp3A = *(state->pc++) << 8;
                sp3A = *(state->pc++) | sp3A;
                state->depth++;
                state->stack[state->depth - 1] = state->pc;
#endif
                state->pc = seqPlayer->seqData + sp3A;
                break;

            case 0xf8: // layer_loop; loop start, N iterations (or 256 if N = 0)
#ifdef VERSION_EU
                state->remLoopIters[state->depth] = m64_read_u8(state);
                state->stack[state->depth++] = state->pc;
#else
                old = state->pc++;
                state->remLoopIters[state->depth] = *old;
                state->depth++;
                state->stack[state->depth - 1] = state->pc;
#endif
                break;

            case 0xf7: // layer_loopend
                state->remLoopIters[state->depth - 1]--;
                if (state->remLoopIters[state->depth - 1] != 0) {
                    state->pc = state->stack[state->depth - 1];
                } else {
                    state->depth--;
                }
                break;

            case 0xfb: // layer_jump
#ifdef VERSION_EU
                sp3A = m64_read_s16(state);
#else
                sp3A = *(state->pc++) << 8;
                sp3A = *(state->pc++) | sp3A;
#endif
                state->pc = seqPlayer->seqData + sp3A;
                break;

#ifdef VERSION_EU
            case 0xf4:
                temp8 = m64_read_u8(state);
                state->pc += temp8;
                break;
#endif

            case 0xc1: // layer_setshortnotevelocity
            case 0xca: // layer_setpan
                temp_a0_5 = *(state->pc++);
                if (cmd == 0xc1) {
                    layer->velocitySquare = (f32)(temp_a0_5 * temp_a0_5);
                } else {
#ifdef VERSION_EU
                    layer->pan = temp_a0_5;
#else
                    layer->pan = (f32) temp_a0_5 / US_FLOAT(128.0);
#endif
                }
                break;

            case 0xc2: // layer_transpose; set transposition in semitones
            case 0xc9: // layer_setshortnoteduration
                temp_a0_6 = *(state->pc++);
                if (cmd == 0xc9) {
                    layer->noteDuration = temp_a0_6;
                } else {
                    layer->transposition = temp_a0_6;
                }
                break;

            case 0xc4: // layer_somethingon
            case 0xc5: // layer_somethingoff
#ifdef VERSION_EU
                if (cmd == 0xc4) {
                    layer->continuousNotes = TRUE;
                } else {
                    layer->continuousNotes = FALSE;
                }
#else
                if (cmd == 0xc4) {
                    temp8 = TRUE;
                } else {
                    temp8 = FALSE;
                }
                layer->continuousNotes = temp8;
#endif
                seq_channel_layer_note_decay(layer);
                break;

            case 0xc3: // layer_setshortnotedefaultplaypercentage
                // This doesn't match very well... sp3A is definitely set here
                // (it's falsely preserved until after the loop), but maybe there's
                // also inlining going on, with sp3A as a temp variable being used
                // for no good reason? Or it could just be a macro.
#ifdef VERSION_EU
                sp3A = m64_read_compressed_u16(state);
#else
                sp3A = *(state->pc++);
                if (sp3A & 0x80) {
                    sp3A = (sp3A << 8) & 0x7f00;
                    sp3A = *(state->pc++) | sp3A;
                }
#endif
                layer->shortNoteDefaultPlayPercentage = sp3A;
                break;

            case 0xc6: // layer_setinstr
#ifdef VERSION_EU
                instId = m64_read_u8(state);
#else
                old = state->pc++;
                instId = *old;
#endif
                // The rest of this case is identical to
                // if (instId < 0x7f) {
                //     get_instrument(seqChannel, instId, &layer->instrument, &layer->adsr);
                // }
                // except without seqChannelCpy...
                // interestingly, get_instrument comes just *after* this function,
                // which I think wouldn't be the case with __inline (maybe if they
                // both inline a common helper?)
                if (instId >= 0x7f) {
#ifdef VERSION_EU
                    if (instId == 0x7f) {
                        layer->instOrWave = 0;
                    } else {
                        layer->instOrWave = instId;
                        layer->instrument = NULL;
                    }
                    if (instId != 0xff) {
                        break;
                    }
                    layer->adsr.releaseRate = 0;
#endif
                    break;
                }

#ifdef VERSION_EU
                instId = get_instrument(seqChannel, instId, &layer->instrument, &layer->adsr);
                layer->instOrWave = instId;
                if (instId == 0) {
                    layer->instOrWave = 0xff;
                }
                //layer->instOrWave = instId == 0 ? 0xff : instId;
                break;
#else
                bankId = seqChannel->bankId; // maybe a temp, to match get_instrument
                if (instId >= gCtlEntries[bankId].numInstruments) {
                    instId = gCtlEntries[bankId].numInstruments;
                    if (instId == 0) {
                        break;
                    }
                    instId--;
                }

                instOut = &layer->instrument;
                inst = gCtlEntries[bankId].instruments[instId];
                if (inst == NULL) {
                    while (instId != 0xff) {
                        inst = gCtlEntries[bankId].instruments[instId];
                        if (inst != NULL) {
                            break;
                        }
                        instId--;
                    }
                }

                adsr = &layer->adsr;
                if (((uintptr_t) gBankLoadedPool.persistent.pool.start <= (uintptr_t) inst
                     && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.persistent.pool.start
                                            + gBankLoadedPool.persistent.pool.size))
                    || ((uintptr_t) gBankLoadedPool.temporary.pool.start <= (uintptr_t) inst
                        && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.temporary.pool.start
                                               + gBankLoadedPool.temporary.pool.size))) {
                    adsr->envelope = inst->envelope;
                    adsr->releaseRate = inst->releaseRate;
                    *instOut = inst;
                    // instId++;
                } else {
                    gAudioErrorFlags = instId + 0x20000;
                    *instOut = NULL;
                }
#endif
                break;

            case 0xc7: // layer_portamento
#ifdef VERSION_EU
                layer->portamento.mode = m64_read_u8(state);
                portamentoTargetNote = m64_read_u8(state) + seqChannel->transposition +
                    layer->transposition + seqPlayer->transposition;
#else
                old = state->pc++;
                layer->portamento.mode = *old;
                old = state->pc++;
                portamentoTargetNote = *old + seqChannel->transposition +
                    layer->transposition + seqPlayer->transposition;
#endif
                if (portamentoTargetNote >= 0x80) {
                    portamentoTargetNote = 0;
                }
                layer->portamentoTargetNote = portamentoTargetNote;

                // If special, the next param is u8 instead of var
                if (PORTAMENTO_IS_SPECIAL(layer->portamento)) {
                    layer->portamentoTime = *(state->pc++);
                    break;
                }

#ifdef VERSION_EU
                sp3A = m64_read_compressed_u16(state);
#else
                sp3A = *(state->pc++);
                if (sp3A & 0x80) {
                    sp3A = (sp3A << 8) & 0x7f00;
                    sp3A = *(state->pc++) | sp3A;
                }
#endif
                layer->portamentoTime = sp3A;
                break;

            case 0xc8: // layer_disableportamento
                layer->portamento.mode = 0;
                break;

#ifdef VERSION_EU
            case 0xcb:
                sp3A = m64_read_s16(state);
                layer->adsr.envelope = (struct AdsrEnvelope *) (seqPlayer->seqData + sp3A);
                layer->adsr.releaseRate = m64_read_u8(state);
                break;

            case 0xcc:
                layer->ignoreDrumPan = TRUE;
                break;
#endif

            default:
                loBits = cmd & 0xf;
                switch (cmd & 0xf0) {
                    case 0xd0: // layer_setshortnotevelocityfromtable
                        sp3A = seqPlayer->shortNoteVelocityTable[loBits];
                        layer->velocitySquare = (f32)(sp3A * sp3A);
                        break;
                    case 0xe0: // layer_setshortnotedurationfromtable
                        layer->noteDuration = seqPlayer->shortNoteDurationTable[loBits];
                        break;
                }
        }
    }

    state = &layer->scriptState;
    if (cmd == 0xc0) // layer_delay
    {
#ifdef VERSION_EU
        sp3A = m64_read_compressed_u16(state);
#else
        sp3A = *(state->pc++);
        if (sp3A & 0x80) {
            sp3A = (sp3A << 8) & 0x7f00;
            sp3A = *(state->pc++) | sp3A;
        }
#endif
        layer->delay = sp3A;
        layer->stopSomething = TRUE;
    } else {
        layer->stopSomething = FALSE;

        if (seqChannel->largeNotes == TRUE) {
            cmdBase = cmd & 0xc0;

            // phi_a0_3 = sp3A; // real assignment, or same vars?
            state = &layer->scriptState;
            switch (cmdBase) {
                case 0x00: // layer_note0 (play percentage, velocity, duration)
#ifdef VERSION_EU
                    sp3A = m64_read_compressed_u16(state);
#else
                    sp3A = *(state->pc++);
                    if (sp3A & 0x80) {
                        sp3A = (sp3A << 8) & 0x7f00;
                        sp3A = *(state->pc++) | sp3A;
                    }
#endif
                    vel = *(state->pc++);
                    layer->noteDuration = *(state->pc++);
                    layer->playPercentage = sp3A;
                    break;

                case 0x40: // layer_note1 (play percentage, velocity)
#ifdef VERSION_EU
                    sp3A = m64_read_compressed_u16(state);
#else
                    sp3A = *(state->pc++);
                    if (sp3A & 0x80) {
                        sp3A = (sp3A << 8) & 0x7f00;
                        sp3A = *(state->pc++) | sp3A;
                    }
#endif
                    vel = *(state->pc++);
                    layer->noteDuration = 0;
                    layer->playPercentage = sp3A;
                    break;

                case 0x80: // layer_note2 (velocity, duration; uses last play percentage)
                    vel = *(state->pc++);
                    layer->noteDuration = *(state->pc++);
                    sp3A = layer->playPercentage;
                    break;
            }

#ifdef VERSION_EU
            velFloat = (f32) vel;
            layer->velocitySquare = velFloat * velFloat;
            cmdSemitone = (cmd - (cmd & 0xc0)) & 0xff;
#else
            layer->velocitySquare = vel * vel;
            cmdSemitone = cmd - cmdBase;
#endif
        } else {
            cmdBase = cmd & 0xc0;

            state = &layer->scriptState;

            // phi_a0_3 = sp3A;
            switch (cmdBase) {
                case 0x00: // play note, type 0 (play percentage)
#ifdef VERSION_EU
                    sp3A = m64_read_compressed_u16(state);
#else
                    sp3A = *(state->pc++);
                    if (sp3A & 0x80) {
                        sp3A = (sp3A << 8) & 0x7f00;
                        sp3A = *(state->pc++) | sp3A;
                    }
#endif
                    layer->playPercentage = sp3A;
                    break;

                case 0x40: // play note, type 1 (uses default play percentage)
                    sp3A = layer->shortNoteDefaultPlayPercentage;
                    break;

                case 0x80: // play note, type 2 (uses last play percentage)
                    sp3A = layer->playPercentage;
                    break;
            }

#ifdef VERSION_EU
            cmdSemitone = (cmd - (cmd & 0xc0)) & 0xff;
#else
            cmdSemitone = cmd - cmdBase;
#endif
        }

        layer->delay = sp3A;
#ifdef VERSION_EU
        layer->duration = layer->noteDuration * sp3A >> 8;
#else
        layer->duration = layer->noteDuration * sp3A / 256;
#endif
        if ((seqPlayer->muted && (seqChannel->muteBehavior & MUTE_BEHAVIOR_STOP_NOTES) != 0)
            || seqChannel->stopSomething2
#ifndef VERSION_EU
            || !seqChannel->hasInstrument
#endif
        ) {
            layer->stopSomething = TRUE;
        } else {
#ifdef VERSION_EU
            if ((layer->instOrWave == 0xff ? seqChannel->instOrWave : layer->instOrWave) == 0) {
                drumIndex = cmdSemitone + seqChannel->transposition + layer->transposition;
                drum = get_drum(seqChannel->bankId, drumIndex);
#else
            if (seqChannel->instOrWave == 0) { // drum
                drumIndex = cmdSemitone + seqChannel->transposition + layer->transposition;
                if (drumIndex >= gCtlEntries[seqChannel->bankId].numDrums) {
                    drumIndex = gCtlEntries[seqChannel->bankId].numDrums;
                    if (drumIndex == 0) {
                        // this goto looks a bit like a function return...
                        layer->stopSomething = TRUE;
                        goto skip;
                    }
                    drumIndex--;
                }

                drum = gCtlEntries[seqChannel->bankId].drums[drumIndex];
#endif
                if (drum == NULL) {
                    layer->stopSomething = TRUE;
                } else {
                    layer->adsr.envelope = drum->envelope;
                    layer->adsr.releaseRate = drum->releaseRate;
#ifdef VERSION_EU
                    if (!layer->ignoreDrumPan) {
                        layer->pan = drum->pan;
                    }
#else
                    layer->pan = FLOAT_CAST(drum->pan) / US_FLOAT(128.0);
#endif
                    layer->sound = &drum->sound;
                    layer->freqScale = layer->sound->tuning;
                }
#ifndef VERSION_EU
            skip:;
#endif
            } else { // instrument
                semitone = cmdSemitone + seqPlayer->transposition + seqChannel->transposition
                             + layer->transposition;
                if (semitone >= 0x80) {
                    layer->stopSomething = TRUE;
                } else {
#ifdef VERSION_EU
                    if (layer->instOrWave == 0xff) {
                        instrument = seqChannel->instrument;
                    } else {
                        instrument = layer->instrument;
                    }
#else
                    instrument = layer->instrument;
                    if (layer->instrument == NULL) {
                        instrument = seqChannel->instrument;
                    }
#endif

                    if (layer->portamento.mode != 0) {
                        usedSemitone = layer->portamentoTargetNote;
                        if (usedSemitone < semitone) {
                            usedSemitone = semitone;
                        }
                        if (instrument != NULL) {
#ifdef VERSION_EU
                            sound = instrument_get_audio_bank_sound(instrument, usedSemitone);
#else
                            if (usedSemitone < instrument->normalRangeLo) {
                                sound = &instrument->lowNotesSound;
                            } else if (usedSemitone <= instrument->normalRangeHi) {
                                sound = &instrument->normalNotesSound;
                            } else {
                                sound = &instrument->highNotesSound;
                            }
#endif
                            sameSound = (sound == layer->sound);
                            layer->sound = sound;
                            tuning = sound->tuning;
                        } else {
                            layer->sound = NULL;
                            tuning = 1.0f;
                        }

                        temp_f2 = gNoteFrequencies[semitone] * tuning;
                        temp_f12 = gNoteFrequencies[layer->portamentoTargetNote] * tuning;

                        portamento = &layer->portamento;
                        switch (PORTAMENTO_MODE(layer->portamento)) {
                            case PORTAMENTO_MODE_1:
                            case PORTAMENTO_MODE_3:
                            case PORTAMENTO_MODE_5:
#ifndef VERSION_EU
                                sp24 = temp_f2;
#endif
                                freqScale = temp_f12;
                                break;
                            case PORTAMENTO_MODE_2:
                            case PORTAMENTO_MODE_4:
#ifndef VERSION_EU
                                sp24 = temp_f12;
#endif
                                freqScale = temp_f2;
                                break;
                        }

#ifdef VERSION_EU
                        portamento->extent = temp_f2 / freqScale - 1.0f;
#else
                        portamento->extent = sp24 / freqScale - US_FLOAT(1.0);
#endif
                        if (PORTAMENTO_IS_SPECIAL(layer->portamento)) {
                            portamento->speed = US_FLOAT(32512.0) * FLOAT_CAST(seqPlayer->tempo)
                                                / ((f32) layer->delay * (f32) gTempoInternalToExternal
                                                   * FLOAT_CAST(layer->portamentoTime));
                        } else {
                            portamento->speed = US_FLOAT(127.0) / FLOAT_CAST(layer->portamentoTime);
                        }
                        portamento->cur = 0.0f;
                        layer->freqScale = freqScale;
                        if (PORTAMENTO_MODE(layer->portamento) == PORTAMENTO_MODE_5) {
                            layer->portamentoTargetNote = semitone;
                        }
                    } else if (instrument != NULL) {
#ifdef VERSION_EU
                        sound = instrument_get_audio_bank_sound(instrument, semitone);
#else
                        if (semitone < instrument->normalRangeLo) {
                            sound = &instrument->lowNotesSound;
                        } else if (semitone <= instrument->normalRangeHi) {
                            sound = &instrument->normalNotesSound;
                        } else {
                            sound = &instrument->highNotesSound;
                        }
#endif
                        sameSound = (sound == layer->sound);
                        layer->sound = sound;
                        layer->freqScale = sound->tuning * gNoteFrequencies[semitone];
                    } else {
                        layer->sound = NULL;
                        layer->freqScale = gNoteFrequencies[semitone];
                    }
                }
            }
            layer->delayUnused = layer->delay;
        }
    }

    if (layer->stopSomething == TRUE) {
        if (layer->note != NULL || layer->continuousNotes) {
            seq_channel_layer_note_decay(layer);
        }
        return;
    }

    if (!layer->continuousNotes) {
        allocNewNote = TRUE;
    } else if (layer->note == NULL || layer->status == SOUND_LOAD_STATUS_NOT_LOADED) {
        allocNewNote = TRUE;
    } else if (sameSound == FALSE) {
        seq_channel_layer_note_decay(layer);
        allocNewNote = TRUE;
#ifdef VERSION_EU
    } else if (layer != layer->note->parentLayer) {
        allocNewNote = TRUE;
#endif
    } else {
        allocNewNote = FALSE;
        if (layer->sound == NULL) {
            init_synthetic_wave(layer->note, layer);
        }
    }

    if (allocNewNote != FALSE) {
        layer->note = alloc_note(layer);
    }

    if (layer->note != NULL && layer->note->parentLayer == layer) {
        note_vibrato_init(layer->note);
    }
}

#elif defined(VERSION_EU)
GLOBAL_ASM("asm/non_matchings/eu/audio/seq_channel_layer_process_script.s")
#elif defined(VERSION_JP)
GLOBAL_ASM("asm/non_matchings/seq_channel_layer_process_script_jp.s")
#else
GLOBAL_ASM("asm/non_matchings/seq_channel_layer_process_script_us.s")
#endif

u8 get_instrument(struct SequenceChannel *seqChannel, u8 instId, struct Instrument **instOut,
                  struct AdsrSettings *adsr)
{
    struct Instrument *inst;
#ifdef VERSION_EU
    inst = get_instrument_inner(seqChannel->bankId, instId);
    if (inst == NULL)
    {
        *instOut = NULL;
        return 0;
    }
    adsr->envelope = inst->envelope;
    adsr->releaseRate = inst->releaseRate;
    *instOut = inst;
    instId++;
    return instId;
#else
    UNUSED u32 pad;

    if (instId >= gCtlEntries[seqChannel->bankId].numInstruments) {
        instId = gCtlEntries[seqChannel->bankId].numInstruments;
        if (instId == 0) {
            return 0;
        }
        instId--;
    }

    inst = gCtlEntries[seqChannel->bankId].instruments[instId];
    if (inst == NULL) {
        struct SequenceChannel seqChannelCpy = *seqChannel;

        while (instId != 0xff) {
            inst = gCtlEntries[seqChannelCpy.bankId].instruments[instId];
            if (inst != NULL) {
                break;
            }
            instId--;
        }
    }

    if (((uintptr_t) gBankLoadedPool.persistent.pool.start <= (uintptr_t) inst
         && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.persistent.pool.start
                    + gBankLoadedPool.persistent.pool.size))
        || ((uintptr_t) gBankLoadedPool.temporary.pool.start <= (uintptr_t) inst
            && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.temporary.pool.start
                                   + gBankLoadedPool.temporary.pool.size))) {
        adsr->envelope = inst->envelope;
        adsr->releaseRate = inst->releaseRate;
        *instOut = inst;
        instId++;
        return instId;
    }

    gAudioErrorFlags = instId + 0x20000;
    *instOut = NULL;
    return 0;
#endif
}

void set_instrument(struct SequenceChannel *seqChannel, u8 instId) {
    if (instId >= 0x80) {
        seqChannel->instOrWave = instId;
        seqChannel->instrument = NULL;
    } else if (instId == 0x7f) {
        seqChannel->instOrWave = 0;
        seqChannel->instrument = (struct Instrument *) 1;
    } else {
#ifdef VERSION_EU
        if ((seqChannel->instOrWave =
            get_instrument(seqChannel, instId, &seqChannel->instrument, &seqChannel->adsr)) == 0)
#else
        seqChannel->instOrWave =
            get_instrument(seqChannel, instId, &seqChannel->instrument, &seqChannel->adsr);
        if (seqChannel->instOrWave == 0)
#endif
        {
            seqChannel->hasInstrument = FALSE;
            return;
        }
    }
    seqChannel->hasInstrument = TRUE;
}

void sequence_channel_set_volume(struct SequenceChannel *seqChannel, u8 volume) {
    seqChannel->volume = FLOAT_CAST(volume) / US_FLOAT(127.0);
}

#ifdef NON_MATCHING
void sequence_channel_process_script(struct SequenceChannel *seqChannel) {
    u16 sp5A;
    s8 value; // sp53
    u8 sp38;
    u8 cmd;    // v1, s1
    u8 loBits; // t0, a0
    struct M64ScriptState *state;
    struct SequencePlayer *seqPlayer;
    u8 temp;
    s8 tempSigned;
    s32 offset;
    s32 i;
    u8 temp2;
#ifdef VERSION_EU
    u8 *arr;
#endif

    if (!seqChannel->enabled) {
        return;
    }

    if (seqChannel->stopScript) {
        for (i = 0; i < LAYERS_MAX; i++) {
            if (seqChannel->layers[i] != NULL) {
                seq_channel_layer_process_script(seqChannel->layers[i]);
            }
        }
        return;
    }

    seqPlayer = seqChannel->seqPlayer;
    if (seqPlayer->muted && (seqChannel->muteBehavior & MUTE_BEHAVIOR_STOP_SCRIPT) != 0) {
        return;
    }

    if (seqChannel->delay != 0) {
        seqChannel->delay--;
    }

    state = &seqChannel->scriptState;
    if (seqChannel->delay == 0) {
        for (;;) {
            cmd = m64_read_u8(state);
#ifndef VERSION_EU
            if (cmd == 0xff) // chan_end
            {
                // This fixes a reordering in 'case 0x90', somehow
                sp5A = state->depth;
                if (sp5A == 0) {
                    sequence_channel_disable(seqChannel);
                    break;
                }
                state->depth--, state->pc = state->stack[state->depth];
            }
            if (cmd == 0xfe) // chan_delay1
            {
                break;
            }
            if (cmd == 0xfd) // chan_delay
            {
                seqChannel->delay = m64_read_compressed_u16(state);
                break;
            }
            if (cmd == 0xf3) // chan_hang
            {
                seqChannel->stopScript = TRUE;
                break;
            }
#endif

            // (new_var = cmd fixes order of s1/s2, but causes a reordering
            // towards the bottom of the function)
            if (cmd > 0xc0) {
                switch (cmd) {
#ifdef VERSION_EU
                    case 0xff: // chan_end
                        if (state->depth == 0) {
                            sequence_channel_disable(seqChannel);
                            goto out;
                        } else {
                            state->depth--, state->pc = state->stack[state->depth];
                        }
                        break;

                    case 0xfe: // chan_delay1
                        goto out;

                    case 0xfd: // chan_delay
                        seqChannel->delay = m64_read_compressed_u16(state);
                        goto out;

                    case 0xea:
                        seqChannel->stopScript = TRUE;
                        goto out;
#endif
                    case 0xfc: // chan_call
                        sp5A = m64_read_s16(state);
#ifdef VERSION_EU
                        state->stack[state->depth++] = state->pc;
#else
                        state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                        state->pc = seqPlayer->seqData + sp5A;
                        break;

                    case 0xf8: // chan_loop; loop start, N iterations (or 256 if N = 0)
                        state->remLoopIters[state->depth] = m64_read_u8(state);
#ifdef VERSION_EU
                        state->stack[state->depth++] = state->pc;
#else
                        state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                        break;

                    case 0xf7: // chan_loopend
                        state->remLoopIters[state->depth - 1]--;
                        if (state->remLoopIters[state->depth - 1] != 0) {
                            state->pc = state->stack[state->depth - 1];
                        } else {
                            state->depth--;
                        }
                        break;

                    case 0xf6: // chan_break; break loop, if combined with jump
                        state->depth--;
                        break;

                    case 0xfb: // chan_jump
                    case 0xfa: // chan_beqz
                    case 0xf9: // chan_bltz
                    case 0xf5: // chan_bgez
                        sp5A = m64_read_s16(state);
                        if (cmd == 0xfa && value != 0)
                            break;
                        if (cmd == 0xf9 && value >= 0)
                            break;
                        if (cmd == 0xf5 && value < 0)
                            break;
                        state->pc = seqPlayer->seqData + sp5A;
                        break;

#ifdef VERSION_EU
                    case 0xf4:
                    case 0xf3:
                    case 0xf2:
                        tempSigned = m64_read_u8(state);
                        if (cmd == 0xf3 && value != 0)
                            break;
                        if (cmd == 0xf2 && value >= 0)
                            break;
                        state->pc += tempSigned;
                        break;
#endif

#ifdef VERSION_EU
                    case 0xf1: // chan_reservenotes
#else
                    case 0xf2: // chan_reservenotes
#endif
                        // seqChannel->notePool should live in a saved register
                        note_pool_clear(&seqChannel->notePool);
                        temp = m64_read_u8(state);
                        note_pool_fill(&seqChannel->notePool, temp);
                        break;

#ifdef VERSION_EU
                    case 0xf0: // chan_unreservenotes
#else
                    case 0xf1: // chan_unreservenotes
#endif
                        note_pool_clear(&seqChannel->notePool);
                        break;

                    case 0xc2: // chan_setdyntable
                        sp5A = m64_read_s16(state);
                        seqChannel->dynTable = (void *) (seqPlayer->seqData + sp5A);
                        break;

                    case 0xc5: // chan_dynsetdyntable
                        if (value != -1) {
                            sp5A = (*seqChannel->dynTable)[value][1]
                                   + ((*seqChannel->dynTable)[value][0] << 8);
                            seqChannel->dynTable = (void *) (seqPlayer->seqData + sp5A);
                        }
                        break;

#ifdef VERSION_EU
                    case 0xeb:
                        temp = m64_read_u8(state);
                        // Switch to the temp's (0-indexed) bank in this sequence's
                        // bank set. Note that in the binary format (not in the JSON!)
                        // the banks are listed backwards, so we counts from the back.
                        // (gAlBankSets[offset] is number of banks)
                        offset = ((u16 *) gAlBankSets)[seqPlayer->seqId];
                        temp = gAlBankSets[offset + gAlBankSets[offset] - temp];
                        // temp should be in a saved register across this call
                        if (get_bank_or_seq(&gBankLoadedPool, 2, temp) != NULL) {
                            seqChannel->bankId = temp;
                        }
                        // fallthrough
#endif

                    case 0xc1: // chan_setinstr ("set program"?)
                        set_instrument(seqChannel, m64_read_u8(state));
                        break;

                    case 0xc3: // chan_largenotesoff
                        seqChannel->largeNotes = FALSE;
                        break;

                    case 0xc4: // chan_largenoteson
                        seqChannel->largeNotes = TRUE;
                        break;

                    case 0xdf: // chan_setvol
                        sequence_channel_set_volume(seqChannel, m64_read_u8(state));
#ifdef VERSION_EU
                        seqChannel->changes.as_bitfields.volume = TRUE;
#endif
                        break;

                    case 0xe0: // chan_setvolscale
                        seqChannel->volumeScale = FLOAT_CAST(m64_read_u8(state)) / US_FLOAT(128.0);
#ifdef VERSION_EU
                        seqChannel->changes.as_bitfields.volume = TRUE;
#endif
                        break;

                    case 0xde: // chan_freqscale; pitch bend using raw frequency multiplier N/2^15 (N is u16)
                        sp5A = m64_read_s16(state);
#ifdef VERSION_EU
                        seqChannel->changes.as_bitfields.freqScale = TRUE;
#endif
                        seqChannel->freqScale = FLOAT_CAST(sp5A) / US_FLOAT(32768.0);
                        break;

                    case 0xd3: // chan_pitchbend; pitch bend by <= 1 octave in either direction (-127..127)
                        // (m64_read_u8(state) is really s8 here)
                        temp = m64_read_u8(state) + 127;
                        seqChannel->freqScale = gPitchBendFrequencyScale[temp];
#ifdef VERSION_EU
                        seqChannel->changes.as_bitfields.freqScale = TRUE;
#endif
                        break;

                    case 0xdd: // chan_setpan
#ifdef VERSION_EU
                        seqChannel->newPan = m64_read_u8(state);
                        seqChannel->changes.as_bitfields.pan = TRUE;
#else
                        seqChannel->pan = FLOAT_CAST(m64_read_u8(state)) / US_FLOAT(128.0);
#endif
                        break;

                    case 0xdc: // chan_setpanmix; set proportion of pan to come from channel (0..128)
#ifdef VERSION_EU
                        seqChannel->panChannelWeight = m64_read_u8(state);
                        seqChannel->changes.as_bitfields.pan = TRUE;
#else
                        seqChannel->panChannelWeight = FLOAT_CAST(m64_read_u8(state)) / US_FLOAT(128.0);
#endif
                        break;

                    case 0xdb: // chan_transpose; set transposition in semitones
                        tempSigned = *state->pc;
                        state->pc++;
                        seqChannel->transposition = tempSigned;
                        break;

                    case 0xda: // chan_setenvelope
                        sp5A = m64_read_s16(state);
                        seqChannel->adsr.envelope = (struct AdsrEnvelope *) (seqPlayer->seqData + sp5A);
                        break;

                    case 0xd9: // chan_setdecayrelease
                        seqChannel->adsr.releaseRate = m64_read_u8(state);
                        break;

                    case 0xd8: // chan_setvibratoextent
                        seqChannel->vibratoExtentTarget = m64_read_u8(state) * 8;
                        seqChannel->vibratoExtentStart = 0;
                        seqChannel->vibratoExtentChangeDelay = 0;
                        break;

                    case 0xd7: // chan_setvibratorate
                        seqChannel->vibratoRateStart = seqChannel->vibratoRateTarget =
                            m64_read_u8(state) * 32;
                        seqChannel->vibratoRateChangeDelay = 0;
                        break;

                    case 0xe2: // chan_setvibratoextentlinear
                        seqChannel->vibratoExtentStart = m64_read_u8(state) * 8;
                        seqChannel->vibratoExtentTarget = m64_read_u8(state) * 8;
                        seqChannel->vibratoExtentChangeDelay = m64_read_u8(state) * 16;
                        break;

                    case 0xe1: // chan_setvibratoratelinear
                        seqChannel->vibratoRateStart = m64_read_u8(state) * 32;
                        seqChannel->vibratoRateTarget = m64_read_u8(state) * 32;
                        seqChannel->vibratoRateChangeDelay = m64_read_u8(state) * 16;
                        break;

                    case 0xe3: // chan_setvibratodelay
                        seqChannel->vibratoDelay = m64_read_u8(state) * 16;
                        break;

#ifndef VERSION_EU
                    case 0xd6: // chan_setupdatesperframe_unimplemented
                        temp = m64_read_u8(state);
                        if (temp == 0) {
                            temp = gAudioUpdatesPerFrame;
                        }
                        seqChannel->updatesPerFrameUnused = temp;
                        break;
#endif

                    case 0xd4: // chan_setreverb
                        seqChannel->reverb = m64_read_u8(state);
                        break;

                    case 0xc6: // chan_setbank; switch bank within set
                        temp = m64_read_u8(state);
                        // Switch to the temp's (0-indexed) bank in this sequence's
                        // bank set. Note that in the binary format (not in the JSON!)
                        // the banks are listed backwards, so we counts from the back.
                        // (gAlBankSets[offset] is number of banks)
                        offset = ((u16 *) gAlBankSets)[seqPlayer->seqId];
                        temp = gAlBankSets[offset + gAlBankSets[offset] - temp];
                        // temp should be in a saved register across this call
                        if (get_bank_or_seq(&gBankLoadedPool, 2, temp) != NULL) {
                            seqChannel->bankId = temp;
                        }
                        break;

                    case 0xc7: // chan_writeseq; write to sequence data (!)
                        // sp38 doesn't go on the stack
                        sp38 = value;
                        temp2 = m64_read_u8(state);
                        sp5A = m64_read_s16(state);
                        seqPlayer->seqData[sp5A] = sp38 + temp2;
                        break;

                    case 0xc8: // chan_subtract
                    case 0xc9: // chan_bitand
                    case 0xcc: // chan_setval
                        temp = m64_read_u8(state);
                        if (cmd == 0xc8) {
                            value -= temp;
                        } else if (cmd == 0xcc) {
                            value = temp;
                        } else {
                            value &= temp;
                        }
                        break;

                    case 0xca: // chan_setmutebhv
                        seqChannel->muteBehavior = m64_read_u8(state);
                        break;

                    case 0xcb: // chan_readseq
                        sp5A = m64_read_s16(state);
                        value = seqPlayer->seqData[sp5A + value];
                        break;

                    case 0xd0: // chan_stereoheadseteffects
                        seqChannel->stereoHeadsetEffects = m64_read_u8(state);
                        break;

                    case 0xd1: // chan_setnoteallocationpolicy
                        seqChannel->noteAllocPolicy = m64_read_u8(state);
                        break;

                    case 0xd2: // chan_setsustain
#ifdef VERSION_EU
                        seqChannel->adsr.sustain = m64_read_u8(state);
#else
                        seqChannel->adsr.sustain = m64_read_u8(state) << 8;
#endif
                        break;
#ifdef VERSION_EU
                    case 0xe5:
                        seqChannel->reverbIndex = m64_read_u8(state);
                        break;
#endif
                    case 0xe4: // chan_dyncall
                        if (value != -1) {
                            u8(*thingy)[2] = *seqChannel->dynTable;
#ifdef VERSION_EU
                            state->stack[state->depth++] = state->pc;
#else
                            state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                            sp5A = thingy[value][1] + (thingy[value][0] << 8);
                            state->pc = seqPlayer->seqData + sp5A;
                        }
                        break;

#ifdef VERSION_EU
                    case 0xe6:
                        seqChannel->bookOffset = m64_read_u8(state);
                        break;

                    case 0xe7:
                        sp5A = m64_read_s16(state);
                        arr = seqPlayer->seqData + sp5A;
                        seqChannel->muteBehavior = *arr++;
                        seqChannel->noteAllocPolicy = *arr++;
                        seqChannel->notePriority = *arr++;
                        seqChannel->transposition = (s8) *arr++;
                        seqChannel->newPan = *arr++;
                        seqChannel->panChannelWeight = *arr++;
                        seqChannel->reverb = *arr++;
                        seqChannel->reverbIndex = *arr++; // reverb index?
                        seqChannel->changes.as_bitfields.pan = TRUE;
                        break;

                    case 0xe8:
                        seqChannel->muteBehavior = m64_read_u8(state);
                        seqChannel->noteAllocPolicy = m64_read_u8(state);
                        seqChannel->notePriority = m64_read_u8(state);
                        seqChannel->transposition = (s8) m64_read_u8(state);
                        seqChannel->newPan = m64_read_u8(state);
                        seqChannel->panChannelWeight = m64_read_u8(state);
                        seqChannel->reverb = m64_read_u8(state);
                        seqChannel->reverbIndex = m64_read_u8(state);
                        seqChannel->changes.as_bitfields.pan = TRUE;
                        break;

                    case 0xec:
                        seqChannel->vibratoExtentTarget = 0;
                        seqChannel->vibratoExtentStart = 0;
                        seqChannel->vibratoExtentChangeDelay = 0;
                        seqChannel->vibratoRateTarget = 0;
                        seqChannel->vibratoRateStart = 0;
                        seqChannel->vibratoRateChangeDelay = 0;
                        seqChannel->freqScale = 1.0f;
                        break;

                    case 0xe9:
                        seqChannel->notePriority = m64_read_u8(state);
                        break;
#endif
                }
            } else {
                // loBits is recomputed a lot
                loBits = cmd & 0xf;
                // #define loBits (cmd & 0xf)
                switch (cmd & 0xf0) {
                    case 0x00: // chan_testlayerfinished
                        if (seqChannel->layers[loBits] != NULL) {
                            value = seqChannel->layers[loBits]->finished;
                        }
#ifdef VERSION_EU
                        else {
                            value = -1;
                        }
#endif
                        break;

                    case 0x70: // chan_iowriteval; write data back to audio lib
                        seqChannel->soundScriptIO[loBits] = value;
                        break;

                    case 0x80: // chan_ioreadval; read data from audio lib
                        value = seqChannel->soundScriptIO[loBits];
                        if (loBits < 4) {
                            seqChannel->soundScriptIO[loBits] = -1;
                        }
                        break;

                    case 0x50: // chan_ioreadvalsub; subtract with read data from audio lib
                        value -= seqChannel->soundScriptIO[loBits];
                        break;

#ifdef VERSION_EU
                    case 0x60:
                        seqChannel->delay = loBits;
                        goto out;
#endif

                    case 0x90: // chan_setlayer
                        sp5A = m64_read_s16(state);
                        if (seq_channel_set_layer(seqChannel, loBits) == 0) {
                            seqChannel->layers[loBits]->scriptState.pc = seqPlayer->seqData + sp5A;
                        }
                        break;

                    case 0xa0: // chan_freelayer
                        seq_channel_layer_free(seqChannel, loBits);
                        break;

                    case 0xb0: // chan_dynsetlayer
                        if (value != -1 && seq_channel_set_layer(seqChannel, loBits) != -1) {
                            sp5A = ((*seqChannel->dynTable)[value][0] << 8)
                                   + (*seqChannel->dynTable)[value][1];
                            seqChannel->layers[loBits]->scriptState.pc = seqPlayer->seqData + sp5A;
                        }
                        break;

#ifndef VERSION_EU
                    case 0x60: // chan_setnotepriority (arg must be >= 2)
                        seqChannel->notePriority = loBits;
                        break;
#endif

                    case 0x10: // chan_startchannel
                        sp5A = m64_read_s16(state);
                        sequence_channel_enable(seqPlayer, loBits, seqPlayer->seqData + sp5A);
                        break;

                    case 0x20: // chan_disablechannel
                        sequence_channel_disable(seqPlayer->channels[loBits]);
                        break;

                    case 0x30: // chan_iowriteval2; write data back to audio lib for another channel
                        seqPlayer->channels[loBits]->soundScriptIO[m64_read_u8(state)] = value;
                        break;

                    case 0x40: // chan_ioreadval2; read data from audio lib from another channel
                        value = seqPlayer->channels[loBits]->soundScriptIO[m64_read_u8(state)];
                        break;
                }
            }
        }
    }
#ifdef VERSION_EU
    out:
#endif

    for (i = 0; i < LAYERS_MAX; i++) {
        if (seqChannel->layers[i] != 0) {
            seq_channel_layer_process_script(seqChannel->layers[i]);
        }
    }
}

#elif defined(VERSION_EU)
GLOBAL_ASM("asm/non_matchings/eu/audio/sequence_channel_process_script.s")
#elif defined(VERSION_JP)
GLOBAL_ASM("asm/non_matchings/sequence_channel_process_script_jp.s")
#else
GLOBAL_ASM("asm/non_matchings/sequence_channel_process_script_us.s")
#endif

void sequence_player_process_sequence(struct SequencePlayer *seqPlayer) {
    u8 cmd;
    u8 loBits;
    u8 temp;
    s32 value;
    s32 i;
    u16 u16v;
    u8 *tempPtr;
    struct M64ScriptState *state;
#ifdef VERSION_EU
    s32 temp32;
#endif

    if (seqPlayer->enabled == FALSE) {
        return;
    }

    if (seqPlayer->bankDmaInProgress == TRUE) {
#ifdef VERSION_EU
        if (osRecvMesg(&seqPlayer->bankDmaMesgQueue, NULL, 0) == -1) {
            return;
        }
        if (seqPlayer->bankDmaRemaining == 0) {
            seqPlayer->bankDmaInProgress = FALSE;
            patch_audio_bank((struct AudioBank *)(gCtlEntries[seqPlayer->loadingBankId].instruments - 1),
                             gAlTbl->seqArray[seqPlayer->loadingBankId].offset,
                             gCtlEntries[seqPlayer->loadingBankId].numInstruments,
                             gCtlEntries[seqPlayer->loadingBankId].numDrums);
            gCtlEntries[seqPlayer->loadingBankId].drums =
                ((struct AudioBank *)(gCtlEntries[seqPlayer->loadingBankId].instruments - 1))->drums;
            gBankLoadStatus[seqPlayer->loadingBankId] = SOUND_LOAD_STATUS_COMPLETE;
        } else {
            audio_dma_partial_copy_async(&seqPlayer->bankDmaCurrDevAddr, &seqPlayer->bankDmaCurrMemAddr,
                                         &seqPlayer->bankDmaRemaining, &seqPlayer->bankDmaMesgQueue,
                                         &seqPlayer->bankDmaIoMesg);
        }
#else
        if (seqPlayer->bankDmaMesg == NULL) {
            return;
        }
        if (seqPlayer->bankDmaRemaining == 0) {
            seqPlayer->bankDmaInProgress = FALSE;
            patch_audio_bank(seqPlayer->loadingBank, gAlTbl->seqArray[seqPlayer->loadingBankId].offset,
                          seqPlayer->loadingBankNumInstruments, seqPlayer->loadingBankNumDrums);
            gCtlEntries[seqPlayer->loadingBankId].numInstruments = seqPlayer->loadingBankNumInstruments;
            gCtlEntries[seqPlayer->loadingBankId].numDrums = seqPlayer->loadingBankNumDrums;
            gCtlEntries[seqPlayer->loadingBankId].instruments = seqPlayer->loadingBank->instruments;
            gCtlEntries[seqPlayer->loadingBankId].drums = seqPlayer->loadingBank->drums;
            gBankLoadStatus[seqPlayer->loadingBankId] = SOUND_LOAD_STATUS_COMPLETE;
        } else {
            osCreateMesgQueue(&seqPlayer->bankDmaMesgQueue, &seqPlayer->bankDmaMesg, 1);
            seqPlayer->bankDmaMesg = NULL;
            audio_dma_partial_copy_async(&seqPlayer->bankDmaCurrDevAddr, &seqPlayer->bankDmaCurrMemAddr,
                                         &seqPlayer->bankDmaRemaining, &seqPlayer->bankDmaMesgQueue,
                                         &seqPlayer->bankDmaIoMesg);
        }
#endif
        return;
    }

    if (seqPlayer->seqDmaInProgress == TRUE) {
#ifdef VERSION_EU
        if (osRecvMesg(&seqPlayer->seqDmaMesgQueue, NULL, 0) == -1) {
            return;
        }
#ifndef AVOID_UB
        if (temp) {
        }
#endif
#else
        if (seqPlayer->seqDmaMesg == NULL) {
            return;
        }
#endif
        seqPlayer->seqDmaInProgress = FALSE;
        gSeqLoadStatus[seqPlayer->seqId] = SOUND_LOAD_STATUS_COMPLETE;
    }

    // If discarded, bail out.
    if (IS_SEQ_LOAD_COMPLETE(seqPlayer->seqId) == FALSE
        || IS_BANK_LOAD_COMPLETE(seqPlayer->defaultBank[0]) == FALSE) {
        sequence_player_disable(seqPlayer);
        return;
    }

    // Remove possible SOUND_LOAD_STATUS_DISCARDABLE marks.
    gSeqLoadStatus[seqPlayer->seqId] = SOUND_LOAD_STATUS_COMPLETE;
    gBankLoadStatus[seqPlayer->defaultBank[0]] = SOUND_LOAD_STATUS_COMPLETE;

    if (seqPlayer->muted && (seqPlayer->muteBehavior & MUTE_BEHAVIOR_STOP_SCRIPT) != 0) {
        return;
    }

    // Check if we surpass the number of ticks needed for a tatum, else stop.
    seqPlayer->tempoAcc += seqPlayer->tempo;
    if (seqPlayer->tempoAcc < gTempoInternalToExternal) {
        return;
    }
    seqPlayer->tempoAcc -= (u16) gTempoInternalToExternal;

    state = &seqPlayer->scriptState;
    if (seqPlayer->delay > 1) {
#ifndef AVOID_UB
        if (temp) {
        }
#endif
        seqPlayer->delay--;
    } else {
#ifdef VERSION_EU
        seqPlayer->recalculateVolume = 1;
#endif
        for (;;) {
            cmd = m64_read_u8(state);
            if (cmd == 0xff) // seq_end
            {
                if (state->depth == 0) {
                    sequence_player_disable(seqPlayer);
                    break;
                }
#ifdef VERSION_EU
                state->pc = state->stack[--state->depth];
#else
                state->depth--, state->pc = state->stack[state->depth];
#endif
            }

            if (cmd == 0xfd) // seq_delay
            {
                seqPlayer->delay = m64_read_compressed_u16(state);
                break;
            }

            if (cmd == 0xfe) // seq_delay1
            {
                seqPlayer->delay = 1;
                break;
            }

            if (cmd >= 0xc0) {
                switch (cmd) {
                    case 0xff: // seq_end
                        break;

                    case 0xfc: // seq_call
                        u16v = m64_read_s16(state);
#ifdef VERSION_EU
                        state->stack[state->depth++] = state->pc;
#else
                        state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                        state->pc = seqPlayer->seqData + u16v;
                        break;

                    case 0xf8: // seq_loop; loop start, N iterations (or 256 if N = 0)
                        state->remLoopIters[state->depth] = m64_read_u8(state);
#ifdef VERSION_EU
                        state->stack[state->depth++] = state->pc;
#else
                        state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                        break;

                    case 0xf7: // seq_loopend
                        state->remLoopIters[state->depth - 1]--;
                        if (state->remLoopIters[state->depth - 1] != 0) {
                            state->pc = state->stack[state->depth - 1];
                        } else {
                            state->depth--;
                        }
                        break;

                    case 0xfb: // seq_jump
                    case 0xfa: // seq_beqz; jump if == 0
                    case 0xf9: // seq_bltz; jump if < 0
                    case 0xf5: // seq_bgez; jump if >= 0
                        u16v = m64_read_s16(state);
                        if (cmd == 0xfa && value != 0) {
                            break;
                        }
                        if (cmd == 0xf9 && value >= 0) {
                            break;
                        }
                        if (cmd == 0xf5 && value < 0) {
                            break;
                        }
                        state->pc = seqPlayer->seqData + u16v;
                        break;

#ifdef VERSION_EU
                    case 0xf4:
                    case 0xf3:
                    case 0xf2:
                        temp = m64_read_u8(state);
                        if (cmd == 0xf3 && value != 0) {
                            break;
                        }
                        if (cmd == 0xf2 && value >= 0) {
                            break;
                        }
                        state->pc += (s8)temp;
                        break;
#endif

#ifdef VERSION_EU
                    case 0xf1: // seq_reservenotes
#else
                    case 0xf2: // seq_reservenotes
#endif
                        note_pool_clear(&seqPlayer->notePool);
                        note_pool_fill(&seqPlayer->notePool, m64_read_u8(state));
                        break;

#ifdef VERSION_EU
                    case 0xf0: // seq_unreservenotes
#else
                    case 0xf1: // seq_unreservenotes
#endif
                        note_pool_clear(&seqPlayer->notePool);
                        break;

                    case 0xdf: // seq_transpose; set transposition in semitones
                        seqPlayer->transposition = 0;
                        // fallthrough

                    case 0xde: // seq_transposerel; add transposition
                        seqPlayer->transposition += (s8) m64_read_u8(state);
                        break;

                    case 0xdd: // seq_settempo (bpm)
                    case 0xdc: // seq_addtempo (bpm)
                        temp = m64_read_u8(state);
                        if (cmd == 0xdd) {
                            seqPlayer->tempo = temp * TEMPO_SCALE;
                        } else {
                            seqPlayer->tempo += (s8) temp * TEMPO_SCALE;
                        }

                        if (seqPlayer->tempo > gTempoInternalToExternal) {
                            seqPlayer->tempo = gTempoInternalToExternal;
                        }

                        if ((s16) seqPlayer->tempo <= 0) {
                            seqPlayer->tempo = 1;
                        }
                        break;

#ifdef VERSION_EU
                    case 0xda:
                        temp = m64_read_u8(state);
                        u16v = m64_read_s16(state);
                        switch (temp) {
                            case SEQUENCE_PLAYER_STATE_0:
                            case SEQUENCE_PLAYER_STATE_FADE_OUT:
                                if (seqPlayer->state != SEQUENCE_PLAYER_STATE_2) {
                                    seqPlayer->fadeTimerUnkEu = u16v;
                                    seqPlayer->state = temp;
                                }
                                break;
                            case SEQUENCE_PLAYER_STATE_2:
                                seqPlayer->fadeTimer = u16v;
                                seqPlayer->state = temp;
                                seqPlayer->fadeVelocity = (0.0f - seqPlayer->fadeVolume) / (s32) (u16v & 0xFFFFu);
                                break;
                        }
                        break;

                    case 0xdb:
                        temp32 = m64_read_u8(state);
                        switch (seqPlayer->state) {
                            case SEQUENCE_PLAYER_STATE_2:
                                break;
                            case SEQUENCE_PLAYER_STATE_FADE_OUT:
                                seqPlayer->state = SEQUENCE_PLAYER_STATE_0;
                                seqPlayer->fadeVolume = 0.0f;
                                // fallthrough
                            case SEQUENCE_PLAYER_STATE_0:
                                seqPlayer->fadeTimer = seqPlayer->fadeTimerUnkEu;
                                if (seqPlayer->fadeTimerUnkEu != 0) {
                                    seqPlayer->fadeVelocity = (temp32 / 127.0f - seqPlayer->fadeVolume) / FLOAT_CAST(seqPlayer->fadeTimer);
                                } else {
                                    seqPlayer->fadeVolume = temp32 / 127.0f;
                                }
                        }
                        break;
#else
                    case 0xdb: // seq_setvol
                        temp = m64_read_u8(state);
                        switch (seqPlayer->state) {
                            case SEQUENCE_PLAYER_STATE_2:
                                if (seqPlayer->fadeTimer != 0) {
                                    f32 targetVolume = FLOAT_CAST(temp) / US_FLOAT(127.0);
                                    seqPlayer->fadeVelocity = (targetVolume - seqPlayer->fadeVolume)
                                                              / FLOAT_CAST(seqPlayer->fadeTimer);
                                    break;
                                }
                                // fallthrough
                            case SEQUENCE_PLAYER_STATE_0:
                                seqPlayer->fadeVolume = FLOAT_CAST(temp) / US_FLOAT(127.0);
                                break;
                            case SEQUENCE_PLAYER_STATE_FADE_OUT:
                            case SEQUENCE_PLAYER_STATE_4:
                                seqPlayer->volume = FLOAT_CAST(temp) / US_FLOAT(127.0);
                                break;
                        }
                        break;

                    case 0xda: // seq_changevol
                        temp = m64_read_u8(state);
                        seqPlayer->fadeVolume =
                            seqPlayer->fadeVolume + (f32) (s8)temp / US_FLOAT(127.0);
                        break;
#endif

#ifdef VERSION_EU
                    case 0xd9:
                        temp = m64_read_u8(state);
                        seqPlayer->fadeVolumeScale = (s8)temp / 127.0f;
                        break;
#endif

                    case 0xd7: // seq_initchannels
                        u16v = m64_read_s16(state);
                        sequence_player_init_channels(seqPlayer, u16v);
                        break;

                    case 0xd6: // seq_disablechannels
                        u16v = m64_read_s16(state);
                        sequence_player_disable_channels(seqPlayer, u16v);
                        break;

                    case 0xd5: // seq_setmutescale
                        temp = m64_read_u8(state);
                        seqPlayer->muteVolumeScale = (f32) (s8)temp / US_FLOAT(127.0);
                        break;

                    case 0xd4: // seq_mute
                        seqPlayer->muted = TRUE;
                        break;

                    case 0xd3: // seq_setmutebhv
                        seqPlayer->muteBehavior = m64_read_u8(state);
                        break;

                    case 0xd2: // seq_setshortnotevelocitytable
                    case 0xd1: // seq_setshortnotedurationtable
                        u16v = m64_read_s16(state);
                        tempPtr = seqPlayer->seqData + u16v;
                        if (cmd == 0xd2) {
                            seqPlayer->shortNoteVelocityTable = tempPtr;
                        } else {
                            seqPlayer->shortNoteDurationTable = tempPtr;
                        }
                        break;

                    case 0xd0: // seq_setnoteallocationpolicy
                        seqPlayer->noteAllocPolicy = m64_read_u8(state);
                        break;

                    case 0xcc: // seq_setval
                        value = m64_read_u8(state);
                        break;

                    case 0xc9: // seq_bitand
#ifdef VERSION_EU
                        value &= m64_read_u8(state);
#else
                        value = m64_read_u8(state) & value;
#endif
                        break;

                    case 0xc8: // seq_subtract
                        value = value - m64_read_u8(state);
                        break;
                }
            } else {
                loBits = cmd & 0xf;
                switch (cmd & 0xf0) {
                    case 0x00: // seq_testchdisabled
#ifdef VERSION_EU
                        value = seqPlayer->channels[loBits]->finished;
#else
                        if (IS_SEQUENCE_CHANNEL_VALID(seqPlayer->channels[loBits]) == TRUE) {
                            value = seqPlayer->channels[loBits]->finished;
                        }
#endif
                        break;
                    case 0x10:
                        break;
                    case 0x20:
                        break;
                    case 0x40:
                        break;
                    case 0x50: // seq_subvariation
#ifdef VERSION_EU
                        value -= seqPlayer->seqVariationEu[0];
#else
                        value -= seqPlayer->seqVariation;
#endif
                        break;
                    case 0x60:
                        break;
                    case 0x70: // seq_setvariation
#ifdef VERSION_EU
                        seqPlayer->seqVariationEu[0] = value;
#else
                        seqPlayer->seqVariation = value;
#endif
                        break;
                    case 0x80: // seq_getvariation
#ifdef VERSION_EU
                        value = seqPlayer->seqVariationEu[0];
#else
                        value = seqPlayer->seqVariation;
#endif
                        break;
                    case 0x90: // seq_startchannel
                        u16v = m64_read_s16(state);
                        sequence_channel_enable(seqPlayer, loBits, seqPlayer->seqData + u16v);
                        break;
                    case 0xa0:
                        break;
#ifndef VERSION_EU
                    case 0xd8: // (this makes no sense)
                        break;
                    case 0xd9:
                        break;
#endif
                }
            }
        }
    }

    for (i = 0; i < CHANNELS_MAX; i++) {
#ifdef VERSION_EU
        if (IS_SEQUENCE_CHANNEL_VALID(seqPlayer->channels[i]) == TRUE) {
            sequence_channel_process_script(seqPlayer->channels[i]);
        }
#else
        if (seqPlayer->channels[i] != &gSequenceChannelNone) {
            sequence_channel_process_script(seqPlayer->channels[i]);
        }
#endif
    }
}

// This runs 240 times per second.
void process_sequences(UNUSED s32 iterationsRemaining) {
    s32 i;
    for (i = 0; i < SEQUENCE_PLAYERS; i++) {
        if (gSequencePlayers[i].enabled == TRUE) {
#ifdef VERSION_EU
            sequence_player_process_sequence(&gSequencePlayers[i]);
            sequence_player_process_sound(&gSequencePlayers[i]);
#else
            sequence_player_process_sequence(gSequencePlayers + i);
            sequence_player_process_sound(gSequencePlayers + i);
#endif
        }
    }
#ifndef VERSION_EU
    reclaim_notes();
#endif
    process_notes();
}

void init_sequence_player(u32 player) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];
#ifdef VERSION_EU
    sequence_player_disable(seqPlayer);
#endif
    seqPlayer->muted = FALSE;
    seqPlayer->delay = 0;
#ifdef VERSION_EU
    seqPlayer->state = 1;
#else
    seqPlayer->state = SEQUENCE_PLAYER_STATE_0;
#endif
    seqPlayer->fadeTimer = 0;
#ifdef VERSION_EU
    seqPlayer->fadeTimerUnkEu = 0;
#endif
    seqPlayer->tempoAcc = 0;
    seqPlayer->tempo = 120 * TEMPO_SCALE; // 120 BPM
    seqPlayer->transposition = 0;
    seqPlayer->muteBehavior = MUTE_BEHAVIOR_STOP_SCRIPT | MUTE_BEHAVIOR_STOP_NOTES | MUTE_BEHAVIOR_SOFTEN;
    seqPlayer->noteAllocPolicy = 0;
    seqPlayer->shortNoteVelocityTable = gDefaultShortNoteVelocityTable;
    seqPlayer->shortNoteDurationTable = gDefaultShortNoteDurationTable;
    seqPlayer->fadeVolume = 1.0f;
#ifdef VERSION_EU
    seqPlayer->fadeVolumeScale = 1.0f;
#endif
    seqPlayer->fadeVelocity = 0.0f;
    seqPlayer->volume = 0.0f;
    seqPlayer->muteVolumeScale = 0.5f;
}

void init_sequence_players(void) {
    // Initialization function, called from audio_init
    s32 i, j;

    for (i = 0; i < ARRAY_COUNT(gSequenceChannels); i++) {
        gSequenceChannels[i].seqPlayer = NULL;
        gSequenceChannels[i].enabled = FALSE;
#ifndef VERSION_EU
    }

    for (i = 0; i < ARRAY_COUNT(gSequenceChannels); i++) {
#endif
        // @bug Size of wrong array. Zeroes out second half of gSequenceChannels[0],
        // all of gSequenceChannels[1..31], and part of gSequenceLayers[0].
        // However, this is only called at startup, so it's harmless.
#ifdef AVOID_UB
#define LAYERS_SIZE LAYERS_MAX
#else
#define LAYERS_SIZE ARRAY_COUNT(gSequenceLayers)
#endif
        for (j = 0; j < LAYERS_SIZE; j++) {
            gSequenceChannels[i].layers[j] = NULL;
        }
    }

    init_layer_freelist();

    for (i = 0; i < ARRAY_COUNT(gSequenceLayers); i++) {
        gSequenceLayers[i].seqChannel = NULL;
        gSequenceLayers[i].enabled = FALSE;
    }

    for (i = 0; i < SEQUENCE_PLAYERS; i++) {
        for (j = 0; j < CHANNELS_MAX; j++) {
            gSequencePlayers[i].channels[j] = &gSequenceChannelNone;
        }

#ifdef VERSION_EU
        gSequencePlayers[i].seqVariationEu[0] = -1;
#else
        gSequencePlayers[i].seqVariation = -1;
#endif
        gSequencePlayers[i].bankDmaInProgress = FALSE;
        gSequencePlayers[i].seqDmaInProgress = FALSE;
        init_note_lists(&gSequencePlayers[i].notePool);
        init_sequence_player(i);
    }
}
