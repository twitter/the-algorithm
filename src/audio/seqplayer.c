#include <PR/ultratypes.h>

#include "data.h"
#include "effects.h"
#include "external.h"
#include "heap.h"
#include "load.h"
#include "seqplayer.h"

#define PORTAMENTO_IS_SPECIAL(x) ((x).mode & 0x80)
#define PORTAMENTO_MODE(x) ((x).mode & ~0x80)
#define PORTAMENTO_MODE_1 1
#define PORTAMENTO_MODE_2 2
#define PORTAMENTO_MODE_3 3
#define PORTAMENTO_MODE_4 4
#define PORTAMENTO_MODE_5 5

#ifdef VERSION_SH
void seq_channel_layer_process_script_part1(struct SequenceChannelLayer *layer);
s32 seq_channel_layer_process_script_part2(struct SequenceChannelLayer *layer);
s32 seq_channel_layer_process_script_part3(struct SequenceChannelLayer *layer, s32 cmd);
s32 seq_channel_layer_process_script_part4(struct SequenceChannelLayer *layer, s32 cmd);
s32 seq_channel_layer_process_script_part5(struct SequenceChannelLayer *layer, s32 cmd);
#endif
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
#if defined(VERSION_EU) || defined(VERSION_SH)
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
    seqChannel->reverbVol = 0;
#ifdef VERSION_SH
    seqChannel->synthesisVolume = 0;
#endif
    seqChannel->notePriority = NOTE_PRIORITY_DEFAULT;
#ifdef VERSION_SH
    seqChannel->unkSH06 = 1;
#endif
    seqChannel->delay = 0;
    seqChannel->adsr.envelope = gDefaultEnvelope;
    seqChannel->adsr.releaseRate = 0x20;
    seqChannel->adsr.sustain = 0;
#if defined(VERSION_JP) || defined(VERSION_US)
    seqChannel->updatesPerFrameUnused = gAudioUpdatesPerFrame;
#endif
    seqChannel->vibratoRateTarget = 0x800;
    seqChannel->vibratoRateStart = 0x800;
    seqChannel->vibratoExtentTarget = 0;
    seqChannel->vibratoExtentStart = 0;
    seqChannel->vibratoRateChangeDelay = 0;
    seqChannel->vibratoExtentChangeDelay = 0;
    seqChannel->vibratoDelay = 0;
#ifdef VERSION_SH
    seqChannel->filter = NULL;
#endif
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_EU) || defined(VERSION_SH)
    layer->ignoreDrumPan = FALSE;
#endif
#ifdef VERSION_SH
    layer->reverbBits.asByte = 0x40;
#endif
    layer->portamento.mode = 0;
    layer->scriptState.depth = 0;
    layer->status = SOUND_LOAD_STATUS_NOT_LOADED;
    layer->noteDuration = 0x80;
#if defined(VERSION_EU) || defined(VERSION_SH)
    layer->pan = 0x40;
#endif
    layer->transposition = 0;
    layer->delay = 0;
    layer->duration = 0;
    layer->delayUnused = 0;
    layer->note = NULL;
    layer->instrument = NULL;
#if defined(VERSION_EU) || defined(VERSION_SH)
    layer->freqScale = 1.0f;
    layer->velocitySquare = 0.0f;
#ifdef VERSION_SH
    layer->freqScaleMultiplier = 1.0f;
#endif
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
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_EU) || defined(VERSION_SH)
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
                eu_stubbed_printf_0("Audio:Track:Warning: No Free Notetrack\n");
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
#if defined(VERSION_EU) || defined(VERSION_SH)
        channelBits = channelBits >> 1;
#else
        channelBits >>= 1;
#endif
    }
}

void sequence_player_disable_channels(struct SequencePlayer *seqPlayer, u16 channelBits) {
    struct SequenceChannel *seqChannel;
    s32 i;

    eu_stubbed_printf_0("SUBTRACK DIM\n");
    for (i = 0; i < CHANNELS_MAX; i++) {
        if (channelBits & 1) {
            seqChannel = seqPlayer->channels[i];
            if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) == TRUE) {
                if (seqChannel->seqPlayer == seqPlayer) {
                    sequence_channel_disable(seqChannel);
                    seqChannel->seqPlayer = NULL;
                }
#if defined(VERSION_EU) || defined(VERSION_SH)
                else {
#ifdef VERSION_EU
                    stubbed_printf("Audio:Track: Warning SUBTRACK PARENT CHANGED\n");
#endif
                }
#endif
                seqPlayer->channels[i] = &gSequenceChannelNone;
            }
        }
#if defined(VERSION_EU) || defined(VERSION_SH)
        channelBits = channelBits >> 1;
#else
        channelBits >>= 1;
#endif
    }
}

void sequence_channel_enable(struct SequencePlayer *seqPlayer, u8 channelIndex, void *script) {
    struct SequenceChannel *seqChannel = seqPlayer->channels[channelIndex];
    s32 i;
    if (IS_SEQUENCE_CHANNEL_VALID(seqChannel) == FALSE) {
#ifdef VERSION_EU
        struct SequencePlayer *bgMusic = &gSequencePlayers[0];
        struct SequencePlayer *miscMusic = &gSequencePlayers[1];

        if (seqPlayer == bgMusic) {
            stubbed_printf("GROUP 0:");
        } else if (seqPlayer == miscMusic) {
            stubbed_printf("GROUP 1:");
        } else {
            stubbed_printf("SEQID %d,BANKID %d\n",
                    seqPlayer->seqId, seqPlayer->defaultBank[0]);
        }
        stubbed_printf("ERR:SUBTRACK %d NOT ALLOCATED\n", channelIndex);
#endif
    } else {
        seqChannel->enabled = TRUE;
        seqChannel->finished = FALSE;
        seqChannel->scriptState.depth = 0;
        seqChannel->scriptState.pc = script;
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

    if (IS_SEQ_LOAD_COMPLETE(seqPlayer->seqId)
#ifdef VERSION_SH
        && gSeqLoadStatus[seqPlayer->seqId] != 5
#endif
    ) {
        gSeqLoadStatus[seqPlayer->seqId] = SOUND_LOAD_STATUS_DISCARDABLE;
    }

    if (IS_BANK_LOAD_COMPLETE(seqPlayer->defaultBank[0])
#ifdef VERSION_SH
        && gBankLoadStatus[seqPlayer->defaultBank[0]] != 5
#endif
    ) {
#ifdef VERSION_SH
        gBankLoadStatus[seqPlayer->defaultBank[0]] = 4;
#else
        gBankLoadStatus[seqPlayer->defaultBank[0]] = SOUND_LOAD_STATUS_DISCARDABLE;
#endif
    }

    // (Note that if this is called from alloc_bank_or_seq, the side will get swapped
    // later in that function. Thus, we signal that we want to load into the slot
    // of the bank that we no longer need.)
#if defined(VERSION_EU) || defined(VERSION_SH)
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
    if (item->prev != NULL) {
        eu_stubbed_printf_0("Error:Same List Add\n");
    } else {
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
#if defined(VERSION_EU) || defined(VERSION_SH)
        gSequenceLayers[i].listItem.u.value = &gSequenceLayers[i];
#else
        gSequenceLayers[i].listItem.u.value = gSequenceLayers + i;
#endif
        gSequenceLayers[i].listItem.prev = NULL;
        audio_list_push_back(&gLayerFreeList, &gSequenceLayers[i].listItem);
    }
}

u8 m64_read_u8(struct M64ScriptState *state) {
#if defined(VERSION_EU) || defined(VERSION_SH)
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

#if defined(VERSION_SH)
void seq_channel_layer_process_script(struct SequenceChannelLayer *layer) {
    s32 cmd;

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

    seq_channel_layer_process_script_part1(layer);
    cmd = seq_channel_layer_process_script_part2(layer);
    if (cmd != -1) {
        cmd = seq_channel_layer_process_script_part3(layer, cmd);
        if (cmd != -1) {
            cmd = seq_channel_layer_process_script_part4(layer, cmd);
        }
        if (cmd != -1) {
            seq_channel_layer_process_script_part5(layer, cmd);
        }

        if (layer->stopSomething == TRUE) {
            if (layer->note != NULL || layer->continuousNotes) {
                seq_channel_layer_note_decay(layer);
            }
        }
    }
}
#elif defined(VERSION_EU)
void seq_channel_layer_process_script(struct SequenceChannelLayer *layer) {
    struct SequencePlayer *seqPlayer;
    struct SequenceChannel *seqChannel;
#ifdef VERSION_EU
    UNUSED u32 pad0;
#endif
    struct M64ScriptState *state;
    struct Portamento *portamento;
    struct AudioBankSound *sound;
    struct Instrument *instrument;
    struct Drum *drum;
    s32 temp_a0_5;
#ifdef VERSION_EU
    u16 sp3A;
    s32 sameSound;
#endif
    UNUSED u32 pad1;
#ifndef VERSION_EU
    u8 sameSound;
#endif
    u8 cmd;
    UNUSED u8 cmdSemitone;
#ifndef VERSION_EU
    u16 sp3A;
#endif
    f32 tuning;
    s32 vel;
    UNUSED s32 usedSemitone;
    f32 freqScale;
#ifndef VERSION_EU
    UNUSED f32 sp24;
#endif
    f32 temp_f12;
    f32 temp_f2;

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
#if defined(VERSION_EU) || defined(VERSION_SH)
    layer->notePropertiesNeedInit = TRUE;
#endif

    for (;;) {
        state = &layer->scriptState;
        cmd = m64_read_u8(state);

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
                state->pc = state->stack[--state->depth];
                break;

            case 0xfc: // layer_call
                if (0 && state->depth >= 4) {
                    eu_stubbed_printf_0("Macro Level Over Error!\n");
                }
                sp3A = m64_read_s16(state);
                state->stack[state->depth++] = state->pc;
                state->pc = seqPlayer->seqData + sp3A;
                break;

            case 0xf8: // layer_loop; loop start, N iterations (or 256 if N = 0)
                if (0 && state->depth >= 4) {
                    eu_stubbed_printf_0("Macro Level Over Error!\n");
                }
                state->remLoopIters[state->depth] = m64_read_u8(state);
                state->stack[state->depth++] = state->pc;
                break;

            case 0xf7: // layer_loopend
                if (--state->remLoopIters[state->depth - 1] != 0) {
                    state->pc = state->stack[state->depth - 1];
                } else {
                    state->depth--;
                }
                break;

            case 0xfb: // layer_jump
                sp3A = m64_read_s16(state);
                state->pc = seqPlayer->seqData + sp3A;
                break;

#if defined(VERSION_EU) || defined(VERSION_SH)
            case 0xf4:
                state->pc += (s8)m64_read_u8(state);
                break;
#endif

            case 0xc1: // layer_setshortnotevelocity
            case 0xca: // layer_setpan
                temp_a0_5 = *(state->pc++);
                if (cmd == 0xc1) {
                    layer->velocitySquare = (f32)(temp_a0_5 * temp_a0_5);
                } else {
#if defined(VERSION_EU) || defined(VERSION_SH)
                    layer->pan = temp_a0_5;
#else
                    layer->pan = (f32) temp_a0_5 / US_FLOAT(128.0);
#endif
                }
                break;

            case 0xc2: // layer_transpose; set transposition in semitones
            case 0xc9: // layer_setshortnoteduration
                temp_a0_5 = *(state->pc++);
                if (cmd == 0xc9) {
                    layer->noteDuration = temp_a0_5;
                } else {
                    layer->transposition = temp_a0_5;
                }
                break;

            case 0xc4: // layer_somethingon
            case 0xc5: // layer_somethingoff
                if (cmd == 0xc4) {
                    layer->continuousNotes = TRUE;
                } else {
                    layer->continuousNotes = FALSE;
                }
                seq_channel_layer_note_decay(layer);
                break;

            case 0xc3: // layer_setshortnotedefaultplaypercentage
                sp3A = m64_read_compressed_u16(state);
                layer->shortNoteDefaultPlayPercentage = sp3A;
                break;

            case 0xc6: // layer_setinstr
                cmd = m64_read_u8(state);
#if defined(VERSION_JP) || defined(VERSION_US)
                if (cmd < 127) {
                    cmd = get_instrument(seqChannel, cmd, &layer->instrument, &layer->adsr);
                }
#else
                if (cmd >= 0x7f) {
                    if (cmd == 0x7f) {
                        layer->instOrWave = 0;
                    } else {
                        layer->instOrWave = cmd;
                        layer->instrument = NULL;
                    }

                    if (1) {
                    }

                    if (cmd == 0xff) {
                        layer->adsr.releaseRate = 0;
                    }
                    break;
                }

                if ((layer->instOrWave = get_instrument(seqChannel, cmd, &layer->instrument, &layer->adsr)) == 0) {
                    eu_stubbed_printf_1("WARNING: NPRG: cannot change %d\n", cmd);
                    layer->instOrWave = 0xff;
                }
#endif
                break;

            case 0xc7: // layer_portamento
                layer->portamento.mode = m64_read_u8(state);

                // cmd is reused for the portamento's semitone
                cmd = m64_read_u8(state) + seqChannel->transposition +
                    layer->transposition + seqPlayer->transposition;

                if (cmd >= 0x80) {
                    cmd = 0;
                }

                layer->portamentoTargetNote = cmd;

                // If special, the next param is u8 instead of var
                if (PORTAMENTO_IS_SPECIAL(layer->portamento)) {
                    layer->portamentoTime = *((state)->pc++);
                    break;
                }

                sp3A = m64_read_compressed_u16(state);
                layer->portamentoTime = sp3A;
                break;

            case 0xc8: // layer_disableportamento
                layer->portamento.mode = 0;
                break;

#if defined(VERSION_EU) || defined(VERSION_SH)
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
                switch (cmd & 0xf0) {
                    case 0xd0: // layer_setshortnotevelocityfromtable
                        sp3A = seqPlayer->shortNoteVelocityTable[cmd & 0xf];
                        layer->velocitySquare = (f32)(sp3A * sp3A);
                        break;
                    case 0xe0: // layer_setshortnotedurationfromtable
                        layer->noteDuration = seqPlayer->shortNoteDurationTable[cmd & 0xf];
                        break;
                    default:
                        eu_stubbed_printf_1("Audio:Track:NOTE:UNDEFINED NOTE COM. %x\n", cmd);
                        break;
                }
        }
    }

    if (cmd == 0xc0) { // layer_delay
        layer->delay = m64_read_compressed_u16(state);
        layer->stopSomething = TRUE;
    } else {
        layer->stopSomething = FALSE;

        if (seqChannel->largeNotes == TRUE) {
            switch (cmd & 0xc0) {
                case 0x00: // layer_note0 (play percentage, velocity, duration)
                    sp3A = m64_read_compressed_u16(state);
                    vel = *(state->pc++);
                    layer->noteDuration = *(state->pc++);
                    layer->playPercentage = sp3A;
                    break;

                case 0x40: // layer_note1 (play percentage, velocity)
                    sp3A = m64_read_compressed_u16(state);
                    vel = *(state->pc++);
                    layer->noteDuration = 0;
                    layer->playPercentage = sp3A;
                    break;

                case 0x80: // layer_note2 (velocity, duration; uses last play percentage)
                    sp3A = layer->playPercentage;
                    vel = *(state->pc++);
                    layer->noteDuration = *(state->pc++);
                    break;
            }

            // the remaining bits are used for the semitone
            cmd -= (cmd & 0xc0);
#if defined(VERSION_EU) || defined(VERSION_SH)
            layer->velocitySquare = (f32)(vel) * (f32)vel;
#else
            layer->velocitySquare = vel * vel;
#endif
        } else {
            switch (cmd & 0xc0) {
                case 0x00: // play note, type 0 (play percentage)
                    sp3A = m64_read_compressed_u16(state);
                    layer->playPercentage = sp3A;
                    break;

                case 0x40: // play note, type 1 (uses default play percentage)
                    sp3A = layer->shortNoteDefaultPlayPercentage;
                    break;

                case 0x80: // play note, type 2 (uses last play percentage)
                    sp3A = layer->playPercentage;
                    break;
            }

            // the remaining bits are used for the semitone
            cmd -= cmd & 0xc0;
        }

        layer->delay = sp3A;
#if defined(VERSION_EU) || defined(VERSION_SH)
        layer->duration = layer->noteDuration * sp3A >> 8;
#else
        layer->duration = layer->noteDuration * sp3A / 256;
#endif
        if ((seqPlayer->muted && (seqChannel->muteBehavior & MUTE_BEHAVIOR_STOP_NOTES) != 0)
            || seqChannel->stopSomething2
#if defined(VERSION_JP) || defined(VERSION_US)
            || !seqChannel->hasInstrument
#endif
        ) {
            layer->stopSomething = TRUE;
        } else {
#if defined(VERSION_EU) || defined(VERSION_SH)
            s32 temp = layer->instOrWave;
            if (temp == 0xff) temp = seqChannel->instOrWave;
            if (temp == 0)
#else
            if (seqChannel->instOrWave == 0)
#endif
            { // drum
                // cmd is reused for the drum semitone
                cmd += seqChannel->transposition + layer->transposition;

#if defined(VERSION_EU)
                drum = get_drum(seqChannel->bankId, cmd);
#else
                if (cmd >= gCtlEntries[seqChannel->bankId].numDrums) {
                    cmd = gCtlEntries[seqChannel->bankId].numDrums;
                    if (cmd == 0) {
                        // this goto looks a bit like a function return...
                        layer->stopSomething = TRUE;
                        goto skip;
                    }

                    cmd--;
                }

                drum = gCtlEntries[seqChannel->bankId].drums[cmd];
#endif
                if (drum == NULL) {
                    layer->stopSomething = TRUE;
                } else {
                    layer->adsr.envelope = drum->envelope;
                    layer->adsr.releaseRate = drum->releaseRate;
#if defined(VERSION_EU) || defined(VERSION_SH)
                    if (!layer->ignoreDrumPan) {
                        layer->pan = drum->pan;
                    }
#else
                    layer->pan = FLOAT_CAST(drum->pan) / US_FLOAT(128.0);
#endif
                    layer->sound = &drum->sound;
                    layer->freqScale = layer->sound->tuning;
                }
#if defined(VERSION_JP) || defined(VERSION_US) || defined(VERSION_SH)
            skip:;
#endif
            } else { // instrument
                // cmd is reused for the instrument semitone
                cmd += seqPlayer->transposition + seqChannel->transposition + layer->transposition;

                if (cmd >= 0x80) {
                    layer->stopSomething = TRUE;
                } else {
#if defined(VERSION_EU) || defined(VERSION_SH)
                    if (layer->instOrWave == 0xffu) {
                        instrument = seqChannel->instrument;
                    } else {
                        instrument = layer->instrument;
                    }
#else
                    instrument = layer->instrument;
                    if (instrument == NULL) {
                        instrument = seqChannel->instrument;
                    }
#endif

                    if (layer->portamento.mode != 0) {
                        if (layer->portamentoTargetNote < cmd) {
                            vel = cmd;
                        } else {
                            vel = layer->portamentoTargetNote;
                        }

                        if (instrument != NULL) {
#if defined(VERSION_EU)
                            sound = instrument_get_audio_bank_sound(instrument, vel);
#else
                            sound = (u8) vel <  instrument->normalRangeLo ? &instrument->lowNotesSound
                                  : (u8) vel <= instrument->normalRangeHi ?
                                        &instrument->normalNotesSound : &instrument->highNotesSound;
#endif
                            sameSound = (sound == layer->sound);
                            layer->sound = sound;
                            tuning = sound->tuning;
                        } else {
                            layer->sound = NULL;
                            tuning = 1.0f;
                        }

                        temp_f2 = gNoteFrequencies[cmd] * tuning;
                        temp_f12 = gNoteFrequencies[layer->portamentoTargetNote] * tuning;

                        portamento = &layer->portamento;
                        switch (PORTAMENTO_MODE(layer->portamento)) {
                            case PORTAMENTO_MODE_1:
                            case PORTAMENTO_MODE_3:
                            case PORTAMENTO_MODE_5:
#if defined(VERSION_JP) || defined(VERSION_US)
                                sp24 = temp_f2;
#endif
                                freqScale = temp_f12;
                                break;

                            case PORTAMENTO_MODE_2:
                            case PORTAMENTO_MODE_4:
#if defined(VERSION_EU) || defined(VERSION_SH)
                            default:
#endif
                                freqScale = temp_f2;
#if defined(VERSION_JP) || defined(VERSION_US)
                                sp24 = temp_f12;
#endif
                                break;
                        }

#if defined(VERSION_EU) || defined(VERSION_SH)
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
                            layer->portamentoTargetNote = cmd;
                        }
                    } else if (instrument != NULL) {
#if defined(VERSION_EU)
                        sound = instrument_get_audio_bank_sound(instrument, cmd);
#else
                        sound = cmd < instrument->normalRangeLo ?
                                         &instrument->lowNotesSound : cmd <= instrument->normalRangeHi ?
                                         &instrument->normalNotesSound : &instrument->highNotesSound;
#endif
                        sameSound = (sound == layer->sound);
                        layer->sound = sound;
                        layer->freqScale = gNoteFrequencies[cmd] * sound->tuning;
                    } else {
                        layer->sound = NULL;
                        layer->freqScale = gNoteFrequencies[cmd];
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

    cmd = FALSE;
    if (!layer->continuousNotes) {
        cmd = TRUE;
    } else if (layer->note == NULL || layer->status == SOUND_LOAD_STATUS_NOT_LOADED) {
        cmd = TRUE;
    } else if (sameSound == FALSE) {
        seq_channel_layer_note_decay(layer);
        cmd = TRUE;
    }
#if defined(VERSION_EU) || defined(VERSION_SH)
    else if (layer != layer->note->parentLayer) {
        cmd = TRUE;
    }
#endif
    else if (layer->sound == NULL) {
        init_synthetic_wave(layer->note, layer);
    }

    if (cmd != FALSE) {
        layer->note = alloc_note(layer);
    }

    if (layer->note != NULL && layer->note->parentLayer == layer) {
        note_vibrato_init(layer->note);
    }
#if defined(VERSION_EU) || defined(VERSION_SH)
    if (seqChannel) {
    }
#endif
}

#ifdef VERSION_EU
u8 audioString106[] = "Audio: Note:Velocity Error %d\n";
u8 audioString107[] = "Error: Your assignchannel is stolen.\n";
#endif

#else
// US/JP version with macros to simulate inlining by copt. Edit if you dare.
#include "copt/seq_channel_layer_process_script_copt.inc.c"
#endif

#ifdef VERSION_SH
void seq_channel_layer_process_script_part1(struct SequenceChannelLayer *layer) {
    if (!layer->continuousNotes) {
        seq_channel_layer_note_decay(layer);
    } else if (layer->note != NULL && layer->note->wantedParentLayer == layer) {
        seq_channel_layer_note_decay(layer);
    }

    if (PORTAMENTO_MODE(layer->portamento) == PORTAMENTO_MODE_1 ||
        PORTAMENTO_MODE(layer->portamento) == PORTAMENTO_MODE_2) {
        layer->portamento.mode = 0;
    }

    layer->notePropertiesNeedInit = TRUE;
}

s32 seq_channel_layer_process_script_part5(struct SequenceChannelLayer *layer, s32 cmd) {
    if (!layer->stopSomething && layer->sound != NULL && layer->sound->sample->codec == CODEC_SKIP &&
        layer->sound->sample->medium != 0) {
        layer->stopSomething = TRUE;
        return -1;
    }

    if (layer->continuousNotes == 1 && layer->note != NULL && layer->status && cmd == 1 &&
        layer->note->parentLayer == layer) {
        if (layer->sound == NULL) {
            init_synthetic_wave(layer->note, layer);
        }
    } else {
        if (cmd == 0) {
            seq_channel_layer_note_decay(layer);
        }
        layer->note = alloc_note(layer);
    }
    if (layer->note != NULL && layer->note->parentLayer == layer) {
        note_vibrato_init(layer->note);
    }
    return 0;
}

s32 seq_channel_layer_process_script_part2(struct SequenceChannelLayer *layer) {
    struct SequenceChannel *seqChannel = layer->seqChannel;
    struct SequencePlayer *seqPlayer = seqChannel->seqPlayer;
    struct M64ScriptState *state;
    s32 temp_a0_5;
    u16 sp3A;
    u8 cmd;

    for (;;) {
        state = &layer->scriptState;
        cmd = m64_read_u8(state);

        if (cmd <= 0xc0) {
            return cmd;
        }

        switch (cmd) {
            case 0xff: // layer_end; function return or end of script
                if (state->depth == 0) {
                    // N.B. this function call is *not* inlined even though it's
                    // within the same file, unlike in the rest of this function.
                    seq_channel_layer_disable(layer);
                    return -1;
                }
                state->pc = state->stack[--state->depth];
                break;

            case 0xfc: // layer_call
                if (0 && state->depth >= 4) {
                    eu_stubbed_printf_0("Macro Level Over Error!\n");
                }
                sp3A = m64_read_s16(state);
                state->stack[state->depth++] = state->pc;
                state->pc = seqPlayer->seqData + sp3A;
                break;

            case 0xf8: // layer_loop; loop start, N iterations (or 256 if N = 0)
                if (0 && state->depth >= 4) {
                    eu_stubbed_printf_0("Macro Level Over Error!\n");
                }
                state->remLoopIters[state->depth] = m64_read_u8(state);
                state->stack[state->depth++] = state->pc;
                break;

            case 0xf7: // layer_loopend
                if (--state->remLoopIters[state->depth - 1] != 0) {
                    state->pc = state->stack[state->depth - 1];
                } else {
                    state->depth--;
                }
                break;

            case 0xfb: // layer_jump
                sp3A = m64_read_s16(state);
                state->pc = seqPlayer->seqData + sp3A;
                break;

            case 0xf4:
                state->pc += (s8)m64_read_u8(state);
                break;

            case 0xc1: // layer_setshortnotevelocity
            case 0xca: // layer_setpan
                temp_a0_5 = *(state->pc++);
                if (cmd == 0xc1) {
                    layer->velocitySquare = (f32) (temp_a0_5 * temp_a0_5) / (f32) (127 * 127);
                } else {
                    layer->pan = temp_a0_5;
                }
                break;

            case 0xc2: // layer_transpose; set transposition in semitones
            case 0xc9: // layer_setshortnoteduration
                temp_a0_5 = *(state->pc++);
                if (cmd == 0xc9) {
                    layer->noteDuration = temp_a0_5;
                } else {
                    layer->transposition = temp_a0_5;
                }
                break;

            case 0xc4: // layer_somethingon
            case 0xc5: // layer_somethingoff
                if (cmd == 0xc4) {
                    layer->continuousNotes = TRUE;
                } else {
                    layer->continuousNotes = FALSE;
                }
                seq_channel_layer_note_decay(layer);
                break;

            case 0xc3: // layer_setshortnotedefaultplaypercentage
                sp3A = m64_read_compressed_u16(state);
                layer->shortNoteDefaultPlayPercentage = sp3A;
                break;

            case 0xc6: // layer_setinstr
                cmd = m64_read_u8(state);
                if (cmd >= 0x7f) {
                    if (cmd == 0x7f) {
                        layer->instOrWave = 0;
                    } else {
                        layer->instOrWave = cmd;
                        layer->instrument = NULL;
                    }

                    if (1) {
                    }

                    if (cmd == 0xff) {
                        layer->adsr.releaseRate = 0;
                    }
                    break;
                }

                if ((layer->instOrWave = get_instrument(seqChannel, cmd, &layer->instrument, &layer->adsr)) == 0) {
                    eu_stubbed_printf_1("WARNING: NPRG: cannot change %d\n", cmd);
                    layer->instOrWave = 0xff;
                }
                break;

            case 0xc7: // layer_portamento
                layer->portamento.mode = m64_read_u8(state);

                // cmd is reused for the portamento's semitone
                cmd = m64_read_u8(state) + seqChannel->transposition +
                    layer->transposition + seqPlayer->transposition;

                if (cmd >= 0x80) {
                    cmd = 0;
                }

                layer->portamentoTargetNote = cmd;

                // If special, the next param is u8 instead of var
                if (PORTAMENTO_IS_SPECIAL(layer->portamento)) {
                    layer->portamentoTime = *((state)->pc++);
                    break;
                }

                sp3A = m64_read_compressed_u16(state);
                layer->portamentoTime = sp3A;
                break;

            case 0xc8: // layer_disableportamento
                layer->portamento.mode = 0;
                break;

            case 0xcb:
                sp3A = m64_read_s16(state);
                layer->adsr.envelope = (struct AdsrEnvelope *) (seqPlayer->seqData + sp3A);
                layer->adsr.releaseRate = m64_read_u8(state);
                break;

            case 0xcc:
                layer->ignoreDrumPan = TRUE;
                break;

            case 0xcd:
                layer->reverbBits.asByte = m64_read_u8(state);
                break;

            case 0xce:
                cmd = m64_read_u8(state) + 0x80;
                layer->freqScaleMultiplier = unk_sh_data_1[cmd];
                // missing break :)

            default:
                switch (cmd & 0xf0) {
                    case 0xd0: // layer_setshortnotevelocityfromtable
                        sp3A = seqPlayer->shortNoteVelocityTable[cmd & 0xf];
                        layer->velocitySquare = (f32) (sp3A * sp3A) / (f32) (127 * 127);
                        break;
                    case 0xe0: // layer_setshortnotedurationfromtable
                        layer->noteDuration = seqPlayer->shortNoteDurationTable[cmd & 0xf];
                        break;
                    default:
                        eu_stubbed_printf_1("Audio:Track:NOTE:UNDEFINED NOTE COM. %x\n", cmd);
                        break;
                }
        }
    }
    return cmd;
}

s32 seq_channel_layer_process_script_part4(struct SequenceChannelLayer *layer, s32 cmd1) {
    s32 sameSound = TRUE;
    struct SequenceChannel *seqChannel = layer->seqChannel;
    struct Portamento *portamento;
    struct AudioBankSound *sound;
    struct Instrument *instrument;
    struct Drum *drum;
    f32 tuning;
    s32 vel;
    f32 freqScale;
    f32 sp24;
    f32 temp_f12;
    UNUSED s32 pad[2];
    struct SequencePlayer *seqPlayer = seqChannel->seqPlayer;
    u8 cmd = cmd1;
    f32 temp_f2;

    s32 temp = layer->instOrWave;
    if (temp == 0xff) {
        if (!seqChannel->hasInstrument) {
            return -1;
        }
        temp = seqChannel->instOrWave;
    }
    if (temp == 0) { // drum
        // cmd is reused for the drum semitone
        cmd += seqChannel->transposition + layer->transposition;

        drum = get_drum(seqChannel->bankId, cmd);
        if (drum == NULL) {
            layer->stopSomething = TRUE;
            layer->delayUnused = layer->delay;
            return -1;
        } else {
            layer->adsr.envelope = drum->envelope;
            layer->adsr.releaseRate = drum->releaseRate;
            if (!layer->ignoreDrumPan) {
                layer->pan = drum->pan;
            }
            layer->sound = &drum->sound;
            layer->freqScale = layer->sound->tuning;
        }
    } else { // instrument
        // cmd is reused for the instrument semitone
        cmd += seqPlayer->transposition + seqChannel->transposition + layer->transposition;

        if (cmd >= 0x80) {
            layer->stopSomething = TRUE;
            return -1;
        } else {
            if (layer->instOrWave == 0xff) {
                instrument = seqChannel->instrument;
            } else {
                instrument = layer->instrument;
            }

            if (layer->portamento.mode != 0) {
                if (layer->portamentoTargetNote < cmd) {
                    vel = cmd;
                } else {
                    vel = layer->portamentoTargetNote;
                }

                if (instrument != NULL) {
                    sound = instrument_get_audio_bank_sound(instrument, vel);
                    sameSound = (sound == layer->sound);
                    layer->sound = sound;
                    tuning = sound->tuning;
                } else {
                    layer->sound = NULL;
                    tuning = 1.0f;
                }

                temp_f2 = gNoteFrequencies[cmd] * tuning;
                temp_f12 = gNoteFrequencies[layer->portamentoTargetNote] * tuning;

                portamento = &layer->portamento;
                switch (PORTAMENTO_MODE(layer->portamento)) {
                    case PORTAMENTO_MODE_1:
                    case PORTAMENTO_MODE_3:
                    case PORTAMENTO_MODE_5:
                        sp24 = temp_f2;
                        freqScale = temp_f12;
                        break;

                    case PORTAMENTO_MODE_2:
                    case PORTAMENTO_MODE_4:
                        freqScale = temp_f2;
                        sp24 = temp_f12;
                        break;

                    default:
                        freqScale = temp_f2;
                        sp24 = temp_f2;
                        break;
                }

                portamento->extent = sp24 / freqScale - 1.0f;

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
                    layer->portamentoTargetNote = cmd;
                }
            } else if (instrument != NULL) {
                sound = instrument_get_audio_bank_sound(instrument, cmd);
                sameSound = (sound == layer->sound);
                layer->sound = sound;
                layer->freqScale = gNoteFrequencies[cmd] * sound->tuning;
            } else {
                layer->sound = NULL;
                layer->freqScale = gNoteFrequencies[cmd];
            }
        }
    }
    layer->delayUnused = layer->delay;
    layer->freqScale *= layer->freqScaleMultiplier;
    return sameSound;
}

s32 seq_channel_layer_process_script_part3(struct SequenceChannelLayer *layer, s32 cmd) {
    struct M64ScriptState *state = &layer->scriptState;
    u16 sp3A;
    s32 vel;
    struct SequenceChannel *seqChannel = layer->seqChannel;
    struct SequencePlayer *seqPlayer = seqChannel->seqPlayer;

    if (cmd == 0xc0) { // layer_delay
        layer->delay = m64_read_compressed_u16(state);
        layer->stopSomething = TRUE;
        return -1;
    }

    layer->stopSomething = FALSE;

    if (seqChannel->largeNotes == TRUE) {
        switch (cmd & 0xc0) {
            case 0x00: // layer_note0 (play percentage, velocity, duration)
                sp3A = m64_read_compressed_u16(state);
                vel = *(state->pc++);
                layer->noteDuration = *(state->pc++);
                layer->playPercentage = sp3A;
                break;

            case 0x40: // layer_note1 (play percentage, velocity)
                sp3A = m64_read_compressed_u16(state);
                vel = *(state->pc++);
                layer->noteDuration = 0;
                layer->playPercentage = sp3A;
                break;

            case 0x80: // layer_note2 (velocity, duration; uses last play percentage)
                sp3A = layer->playPercentage;
                vel = *(state->pc++);
                layer->noteDuration = *(state->pc++);
                break;
        }
        if (vel >= 0x80 || vel < 0) {
            vel = 0x7f;
        }
        layer->velocitySquare = ((f32) vel * (f32) vel) / (f32) (0x7f * 0x7f);
        // the remaining bits are used for the semitone
        cmd -= (cmd & 0xc0);
    } else {
        switch (cmd & 0xc0) {
            case 0x00: // play note, type 0 (play percentage)
                sp3A = m64_read_compressed_u16(state);
                layer->playPercentage = sp3A;
                break;

            case 0x40: // play note, type 1 (uses default play percentage)
                sp3A = layer->shortNoteDefaultPlayPercentage;
                break;

            case 0x80: // play note, type 2 (uses last play percentage)
                sp3A = layer->playPercentage;
                break;
        }

        // the remaining bits are used for the semitone
        cmd -= cmd & 0xc0;
    }

    layer->delay = sp3A;
    layer->duration = layer->noteDuration * sp3A >> 8;
    if ((seqPlayer->muted && (seqChannel->muteBehavior & 0x50) != 0)
        || seqChannel->stopSomething2) {
        layer->stopSomething = TRUE;
        return -1;
    }

    return cmd;
}
#endif

u8 get_instrument(struct SequenceChannel *seqChannel, u8 instId, struct Instrument **instOut, struct AdsrSettings *adsr) {
    struct Instrument *inst;
#if defined(VERSION_EU) || defined(VERSION_SH)
    inst = get_instrument_inner(seqChannel->bankId, instId);
    if (inst == NULL) {
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
#if defined(VERSION_EU) || defined(VERSION_SH)
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

void sequence_channel_process_script(struct SequenceChannel *seqChannel) {
    struct M64ScriptState *state;
    struct SequencePlayer *seqPlayer;
    u8 cmd;
    s8 temp;
    u8 loBits;
    u16 sp5A;
    s32 sp38;
    s8 value;
    s32 i;
    u8 *seqData;

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
#if !defined(VERSION_EU) && !defined(VERSION_SH)
            if (cmd == 0xff) { // chan_end
                if (state->depth == 0) {
                    sequence_channel_disable(seqChannel);
                    break;
                }
                state->depth--, state->pc = state->stack[state->depth];
            }
            if (cmd == 0xfe) { // chan_delay1
                break;
            }
            if (cmd == 0xfd) { // chan_delay
                seqChannel->delay = m64_read_compressed_u16(state);
                break;
            }
            if (cmd == 0xf3) { // chan_hang
                seqChannel->stopScript = TRUE;
                break;
            }
#endif

#ifdef VERSION_SH
            if (cmd >= 0xb0)
#else
            if (cmd > 0xc0)
#endif
            {
                switch (cmd) {
                    case 0xff: // chan_end
#if defined(VERSION_EU) || defined(VERSION_SH)
                        if (state->depth == 0) {
                            sequence_channel_disable(seqChannel);
                            goto out;
                        } else {
                            state->pc = state->stack[--state->depth];
                        }
#endif
                        break;

#if defined(VERSION_EU) || defined(VERSION_SH)
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
                        if (0 && state->depth >= 4) {
                            eu_stubbed_printf_0("Audio:Track :Call Macro Level Over Error!\n");
                        }
                        sp5A = m64_read_s16(state);
#if defined(VERSION_EU) || defined(VERSION_SH)
                        state->stack[state->depth++] = state->pc;
#else
                        state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                        state->pc = seqPlayer->seqData + sp5A;
                        break;

                    case 0xf8: // chan_loop; loop start, N iterations (or 256 if N = 0)
                        if (0 && state->depth >= 4) {
                            eu_stubbed_printf_0("Audio:Track :Loops Macro Level Over Error!\n");
                        }
                        state->remLoopIters[state->depth] = m64_read_u8(state);
#if defined(VERSION_EU) || defined(VERSION_SH)
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

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xf4: // chan_jump_rel
                    case 0xf3: // chan_beqz_rel
                    case 0xf2: // chan_bltz_rel
                        temp = m64_read_u8(state);
                        if (cmd == 0xf3 && value != 0)
                            break;
                        if (cmd == 0xf2 && value >= 0)
                            break;
                        state->pc += temp;
                        break;
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xf1: // chan_reservenotes
#else
                    case 0xf2: // chan_reservenotes
#endif
                        note_pool_clear(&seqChannel->notePool);
                        note_pool_fill(&seqChannel->notePool, m64_read_u8(state));
                        break;

#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_EU) || defined(VERSION_SH)
                            seqData = (*seqChannel->dynTable)[value];
                            sp38 = (u16)((seqData[0] << 8) + seqData[1]);
                            seqChannel->dynTable = (void *) (seqPlayer->seqData + sp38);
#else
                            sp5A = (u16)((((*seqChannel->dynTable)[value])[0] << 8) + (((*seqChannel->dynTable)[value])[1]));
                            seqChannel->dynTable = (void *) (seqPlayer->seqData + sp5A);
#endif
                        }
                        break;

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xeb: // chan_setbankandinstr
                        cmd = m64_read_u8(state);
                        // Switch to the cmd's (0-indexed) bank in this sequence's
                        // bank set. Note that in the binary format (not in the JSON!)
                        // the banks are listed backwards, so we counts from the back.
                        // (gAlBankSets[offset] is number of banks)
                        sp38 = ((u16 *) gAlBankSets)[seqPlayer->seqId];
                        loBits = *(sp38 + gAlBankSets);
                        cmd = gAlBankSets[(s32)sp38 + loBits - cmd];

#ifdef VERSION_SH
                        if (get_bank_or_seq(1, 2, cmd) != NULL)
#else
                        if (get_bank_or_seq(&gBankLoadedPool, 2, cmd) != NULL)
#endif
                        {
                            seqChannel->bankId = cmd;
                        } else {
                            eu_stubbed_printf_1("SUB:ERR:BANK %d NOT CACHED.\n", cmd);
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
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->changes.as_bitfields.volume = TRUE;
#endif
                        break;

                    case 0xe0: // chan_setvolscale
                        seqChannel->volumeScale = FLOAT_CAST(m64_read_u8(state)) / US_FLOAT(128.0);
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->changes.as_bitfields.volume = TRUE;
#endif
                        break;

                    case 0xde: // chan_freqscale; pitch bend using raw frequency multiplier N/2^15 (N is u16)
                        sp5A = m64_read_s16(state);
                        seqChannel->freqScale = FLOAT_CAST(sp5A) / US_FLOAT(32768.0);
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->changes.as_bitfields.freqScale = TRUE;
#endif
                        break;

                    case 0xd3: // chan_pitchbend; pitch bend by <= 1 octave in either direction (-127..127)
                        // (m64_read_u8(state) is really s8 here)
#ifdef VERSION_SH
                        cmd = m64_read_u8(state) + 128;
#else
                        cmd = m64_read_u8(state) + 127;
#endif
                        seqChannel->freqScale = gPitchBendFrequencyScale[cmd];
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->changes.as_bitfields.freqScale = TRUE;
#endif
                        break;

#ifdef VERSION_SH
                    case 0xee:
                        cmd = m64_read_u8(state) + 0x80;
                        seqChannel->freqScale = unk_sh_data_1[cmd];
                        seqChannel->changes.as_bitfields.freqScale = TRUE;
                        break;
#endif

                    case 0xdd: // chan_setpan
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->newPan = m64_read_u8(state);
                        seqChannel->changes.as_bitfields.pan = TRUE;
#else
                        seqChannel->pan = FLOAT_CAST(m64_read_u8(state)) / US_FLOAT(128.0);
#endif
                        break;

                    case 0xdc: // chan_setpanmix; set proportion of pan to come from channel (0..128)
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->panChannelWeight = m64_read_u8(state);
                        seqChannel->changes.as_bitfields.pan = TRUE;
#else
                        seqChannel->panChannelWeight = FLOAT_CAST(m64_read_u8(state)) / US_FLOAT(128.0);
#endif
                        break;

                    case 0xdb: // chan_transpose; set transposition in semitones
                        temp = *state->pc++;
                        seqChannel->transposition = temp;
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

#if defined(VERSION_JP) || defined(VERSION_US)
                    case 0xd6: // chan_setupdatesperframe_unimplemented
                        cmd = m64_read_u8(state);
                        if (cmd == 0) {
                            cmd = gAudioUpdatesPerFrame;
                        }
                        seqChannel->updatesPerFrameUnused = cmd;
                        break;
#endif

                    case 0xd4: // chan_setreverb
                        seqChannel->reverbVol = m64_read_u8(state);
                        break;

                    case 0xc6: // chan_setbank; switch bank within set
                        cmd = m64_read_u8(state);
                        // Switch to the temp's (0-indexed) bank in this sequence's
                        // bank set. Note that in the binary format (not in the JSON!)
                        // the banks are listed backwards, so we counts from the back.
                        // (gAlBankSets[offset] is number of banks)
#if defined(VERSION_EU) || defined(VERSION_SH)
                        sp38 = ((u16 *) gAlBankSets)[seqPlayer->seqId];
                        loBits = *(sp38 + gAlBankSets);
                        cmd = gAlBankSets[(s32)sp38 + loBits - cmd];
#else
                        sp5A = ((u16 *) gAlBankSets)[seqPlayer->seqId];
                        loBits = *(sp5A + gAlBankSets);
                        cmd = gAlBankSets[sp5A + loBits - cmd];
#endif
#ifdef VERSION_SH
                        if (get_bank_or_seq(1, 2, cmd) != NULL)
#else
                        if (get_bank_or_seq(&gBankLoadedPool, 2, cmd) != NULL)
#endif
                        {
                            seqChannel->bankId = cmd;
                        } else {
                            eu_stubbed_printf_1("SUB:ERR:BANK %d NOT CACHED.\n", cmd);
                        }
                        break;

                    case 0xc7: // chan_writeseq; write to sequence data (!)
                        {
#if !defined(VERSION_EU) && !defined(VERSION_SH)
                            u8 *seqData;
#endif
                            cmd = m64_read_u8(state);
                            sp5A = m64_read_s16(state);
                            seqData = seqPlayer->seqData + sp5A;
                            *seqData = (u8)value + cmd;
                        }
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

#ifdef VERSION_SH
                    case 0xcd:
                        sequence_channel_disable(seqPlayer->channels[m64_read_u8(state)]);
                        break;
#endif

                    case 0xca: // chan_setmutebhv
                        seqChannel->muteBehavior = m64_read_u8(state);
#ifdef VERSION_SH
                        seqChannel->changes.as_bitfields.volume = TRUE;
#endif
                        break;

                    case 0xcb: // chan_readseq
                        sp38 = (u16)m64_read_s16(state) + value;
                        value = seqPlayer->seqData[sp38];
                        break;

#ifdef VERSION_SH
                    case 0xce:
                        seqChannel->unkC8 = m64_read_s16(state);
                        break;

                    case 0xcf:
                        sp5A = m64_read_s16(state);
                        seqData = seqPlayer->seqData + sp5A;
                        seqData[0] = (seqChannel->unkC8 >> 8) & 0xffff;
                        seqData[1] = (seqChannel->unkC8) & 0xffff;
                        break;
#endif

                    case 0xd0: // chan_stereoheadseteffects
                        seqChannel->stereoHeadsetEffects = m64_read_u8(state);
                        break;

                    case 0xd1: // chan_setnoteallocationpolicy
                        seqChannel->noteAllocPolicy = m64_read_u8(state);
                        break;

                    case 0xd2: // chan_setsustain
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqChannel->adsr.sustain = m64_read_u8(state);
#else
                        seqChannel->adsr.sustain = m64_read_u8(state) << 8;
#endif
                        break;
#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xe5:
                        seqChannel->reverbIndex = m64_read_u8(state);
                        break;
#endif
                    case 0xe4: // chan_dyncall
                        if (value != -1) {
#if defined(VERSION_EU) || defined(VERSION_SH)
                            if (state->depth >= 4) {
                                eu_stubbed_printf_0("Audio:Track: CTBLCALL Macro Level Over Error!\n");
                            }
#endif
                            seqData = (*seqChannel->dynTable)[value];
#if defined(VERSION_EU) || defined(VERSION_SH)
                            state->stack[state->depth++] = state->pc;
                            sp38 = (u16)((seqData[0] << 8) + seqData[1]);
                            state->pc = seqPlayer->seqData + sp38;

                            if (0 && sp38 >= gSeqFileHeader->seqArray[seqPlayer->seqId].len) {
                                eu_stubbed_printf_3("Err :Sub %x ,address %x:Undefined SubTrack Function %x", seqChannel, state->pc, sp38);
                            }
#else
                            state->depth++, state->stack[state->depth - 1] = state->pc;
                            sp5A = ((seqData[0] << 8) + seqData[1]);
                            state->pc = seqPlayer->seqData + sp5A;
#endif
                        }
                        break;

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xe6:
                        seqChannel->bookOffset = m64_read_u8(state);
                        break;

                    case 0xe7:
                        sp5A = m64_read_s16(state);
                        seqData = seqPlayer->seqData + sp5A;
                        seqChannel->muteBehavior = *seqData++;
                        seqChannel->noteAllocPolicy = *seqData++;
                        seqChannel->notePriority = *seqData++;
                        seqChannel->transposition = (s8) *seqData++;
                        seqChannel->newPan = *seqData++;
                        seqChannel->panChannelWeight = *seqData++;
                        seqChannel->reverbVol = *seqData++;
                        seqChannel->reverbIndex = *seqData++;
                        seqChannel->changes.as_bitfields.pan = TRUE;
                        break;

                    case 0xe8:
                        seqChannel->muteBehavior = m64_read_u8(state);
                        seqChannel->noteAllocPolicy = m64_read_u8(state);
                        seqChannel->notePriority = m64_read_u8(state);
                        seqChannel->transposition = (s8) m64_read_u8(state);
                        seqChannel->newPan = m64_read_u8(state);
                        seqChannel->panChannelWeight = m64_read_u8(state);
                        seqChannel->reverbVol = m64_read_u8(state);
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

                    case 0xe9: // chan_setnotepriority
#ifdef VERSION_SH
                        cmd = m64_read_u8(state);
                        if ((cmd & 0xf) != 0) {
                            seqChannel->notePriority = cmd & 0xf;
                        }
                        cmd = cmd >> 4;
                        if (cmd != 0) {
                            seqChannel->unkSH06 = cmd;
                        }
#else
                        seqChannel->notePriority = m64_read_u8(state);
#endif
                        break;
#endif
#ifdef VERSION_SH
                    case 0xed:
                        seqChannel->synthesisVolume = m64_read_u8(state);
                        break;

                    case 0xef:
                        m64_read_s16(state);
                        m64_read_u8(state);
                        break;

                    case 0xb0:
                        sp5A = m64_read_s16(state);
                        seqData = seqPlayer->seqData + sp5A;
                        seqChannel->filter = (s16 *) (seqData);
                        break;

                    case 0xb1:
                        seqChannel->filter = NULL;
                        break;

                    case 0xb3:
                        if (seqChannel->filter != NULL) {
                            cmd = m64_read_u8(state);
                            if (cmd == 0) {
                                seqChannel->filter = NULL;
                            } else {
                                loBits = (cmd >> 4) & 0xf;
                                cmd &= 0xf;
                                fill_filter(seqChannel->filter, loBits, cmd);
                            }
                        }
                        break;

                    case 0xb2:
                        i = (value * 2);
                        sp5A = m64_read_s16(state);
                        sp38 = sp5A + i;
                        seqChannel->unkC8 = *(u16 *) (seqPlayer->seqData + sp38);
                        break;

                    case 0xb4:
                        seqChannel->dynTable = (void *) (seqPlayer->seqData + seqChannel->unkC8);
                        break;

                    case 0xb5:
                        seqChannel->unkC8 = *(u16 *) (*seqChannel->dynTable)[value];
                        break;

                    case 0xb6:
                        value = (*seqChannel->dynTable)[0][value];
                        break;
#endif
                }
            } else {
#ifdef VERSION_SH
                if (cmd >= 0x80) {
                    loBits = cmd & 7;
                    switch (cmd & 0xf8) {
                        case 0x80:
                            if (seqChannel->layers[loBits] != NULL) {
                                value = seqChannel->layers[loBits]->finished;
                            } else {
                                value = -1;
                            }
                            break;

                        case 0x88:
                            sp5A = m64_read_s16(state);
                            if (seq_channel_set_layer(seqChannel, loBits) == 0) {
                                if (1) {}
                                seqChannel->layers[loBits]->scriptState.pc = seqPlayer->seqData + sp5A;
                            }
                            break;

                        case 0x90:
                            seq_channel_layer_free(seqChannel, loBits);
                            break;

                        case 0x98:
                            if (value != -1 && seq_channel_set_layer(seqChannel, loBits) != -1) {
                                seqData = (*seqChannel->dynTable)[value];
                                sp5A = ((seqData[0] << 8) + seqData[1]);
                                seqChannel->layers[loBits]->scriptState.pc = seqPlayer->seqData + sp5A;
                            }
                            break;
                    }
                } else {
#endif
                loBits = cmd & 0xf;

                switch (cmd & 0xf0) {
#ifdef VERSION_SH
                    case 0x00:
                        seqChannel->delay = loBits;
                        goto out;

                    case 0x10:
                        seqChannel->soundScriptIO[loBits] = -1;
                        if (func_sh_802f47c8(seqChannel->bankId, (u8)value, &seqChannel->soundScriptIO[loBits]) == -1) {
                        }
                        break;
#else
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
#endif

                    // sh: 0x70
                    case 0x70: // chan_iowriteval; write data back to audio lib
                        seqChannel->soundScriptIO[loBits] = value;
                        break;

#ifdef VERSION_SH
                    case 0x60:
#else
                    case 0x80: // chan_ioreadval; read data from audio lib
#endif
                        value = seqChannel->soundScriptIO[loBits];
                        if (loBits < 4) {
                            seqChannel->soundScriptIO[loBits] = -1;
                        }
                        break;

                    // sh: 0x50
                    case 0x50: // chan_ioreadvalsub; subtract with read data from audio lib
                        value -= seqChannel->soundScriptIO[loBits];
                        break;

#ifndef VERSION_SH
#ifdef VERSION_EU
                    // sh: 0x00
                    case 0x60: // chan_delayshort
                        seqChannel->delay = loBits;
                        goto out;
#endif

                    case 0x90: // chan_setlayer
                        sp5A = m64_read_s16(state);
                        if (seq_channel_set_layer(seqChannel, loBits) == 0) {
#ifdef VERSION_EU
                            if (1) {}
#endif
                            seqChannel->layers[loBits]->scriptState.pc = seqPlayer->seqData + sp5A;
                        }
                        break;

                    case 0xa0: // chan_freelayer
                        seq_channel_layer_free(seqChannel, loBits);
                        break;

                    case 0xb0: // chan_dynsetlayer
                        if (value != -1 && seq_channel_set_layer(seqChannel, loBits) != -1) {
                            seqData = (*seqChannel->dynTable)[value];
                            sp5A = ((seqData[0] << 8) + seqData[1]);
                            seqChannel->layers[loBits]->scriptState.pc = seqPlayer->seqData + sp5A;
                        }
                        break;

#ifndef VERSION_EU
                    case 0x60: // chan_setnotepriority (arg must be >= 2)
                        seqChannel->notePriority = loBits;
                        break;
#endif
#endif

#ifdef VERSION_SH
                    case 0x20:
#else
                    case 0x10: // chan_startchannel
#endif
                        sp5A = m64_read_s16(state);
                        sequence_channel_enable(seqPlayer, loBits, seqPlayer->seqData + sp5A);
                        break;

#ifndef VERSION_SH
                    case 0x20: // chan_disablechannel
                        sequence_channel_disable(seqPlayer->channels[loBits]);
                        break;
#endif

                    case 0x30: // chan_iowriteval2; write data back to audio lib for another channel
                        cmd = m64_read_u8(state);
                        seqPlayer->channels[loBits]->soundScriptIO[cmd] = value;
                        break;

                    case 0x40: // chan_ioreadval2; read data from audio lib from another channel
                        cmd = m64_read_u8(state);
                        value = seqPlayer->channels[loBits]->soundScriptIO[cmd];
                        break;
                }
#ifdef VERSION_SH
                }
#endif
            }
        }
    }
#if defined(VERSION_EU) || defined(VERSION_SH)
    out:
#endif

    for (i = 0; i < LAYERS_MAX; i++) {
        if (seqChannel->layers[i] != 0) {
            seq_channel_layer_process_script(seqChannel->layers[i]);
        }
    }
}

void sequence_player_process_sequence(struct SequencePlayer *seqPlayer) {
    u8 cmd;
#ifdef VERSION_SH
    UNUSED u32 pad;
#endif
    u8 loBits;
    u8 temp;
    s32 value;
    s32 i;
    u16 u16v;
    u8 *seqData;
    struct M64ScriptState *state;
#if defined(VERSION_EU) || defined(VERSION_SH)
    s32 temp32;
#endif

    if (seqPlayer->enabled == FALSE) {
        return;
    }

#ifndef VERSION_SH
    if (seqPlayer->bankDmaInProgress == TRUE) {
#ifdef VERSION_EU
        if (osRecvMesg(&seqPlayer->bankDmaMesgQueue, NULL, 0) == -1) {
            return;
        }
        if (seqPlayer->bankDmaRemaining == 0) {
            seqPlayer->bankDmaInProgress = FALSE;
            patch_audio_bank(
                (struct AudioBank *) (gCtlEntries[seqPlayer->loadingBankId].instruments - 1),
                gAlTbl->seqArray[seqPlayer->loadingBankId].offset,
                gCtlEntries[seqPlayer->loadingBankId].numInstruments,
                gCtlEntries[seqPlayer->loadingBankId].numDrums);
            gCtlEntries[seqPlayer->loadingBankId].drums =
                ((struct AudioBank *) (gCtlEntries[seqPlayer->loadingBankId].instruments - 1))->drums;
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
#endif

    // If discarded, bail out.
    if (IS_SEQ_LOAD_COMPLETE(seqPlayer->seqId) == FALSE
        || (
#ifdef VERSION_SH
        seqPlayer->defaultBank[0] != 0xff &&
#endif
        IS_BANK_LOAD_COMPLETE(seqPlayer->defaultBank[0]) == FALSE)) {
        eu_stubbed_printf_1("Disappear Sequence or Bank %d\n", seqPlayer->seqId);
        sequence_player_disable(seqPlayer);
        return;
    }

    // Remove possible SOUND_LOAD_STATUS_DISCARDABLE marks.
#ifdef VERSION_SH
    if (gSeqLoadStatus[seqPlayer->seqId] != 5)
#endif
        gSeqLoadStatus[seqPlayer->seqId] = SOUND_LOAD_STATUS_COMPLETE;

#ifdef VERSION_SH
    if (gBankLoadStatus[seqPlayer->defaultBank[0]] != 5)
#endif
        gBankLoadStatus[seqPlayer->defaultBank[0]] = SOUND_LOAD_STATUS_COMPLETE;

    if (seqPlayer->muted && (seqPlayer->muteBehavior & MUTE_BEHAVIOR_STOP_SCRIPT) != 0) {
        return;
    }

    // Check if we surpass the number of ticks needed for a tatum, else stop.
    seqPlayer->tempoAcc += seqPlayer->tempo;
#ifdef VERSION_SH
    seqPlayer->tempoAcc += seqPlayer->tempoAdd;
#endif
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
#if defined(VERSION_EU) || defined(VERSION_SH)
        seqPlayer->recalculateVolume = 1;
#endif
        for (;;) {
            cmd = m64_read_u8(state);
            if (cmd == 0xff) { // seq_end
                if (state->depth == 0) {
                    sequence_player_disable(seqPlayer);
                    break;
                }
#if defined(VERSION_EU) || defined(VERSION_SH)
                state->pc = state->stack[--state->depth];
#else
                state->depth--, state->pc = state->stack[state->depth];
#endif
            }

            if (cmd == 0xfd) { // seq_delay
                seqPlayer->delay = m64_read_compressed_u16(state);
                break;
            }

            if (cmd == 0xfe) { // seq_delay1
                seqPlayer->delay = 1;
                break;
            }

            if (cmd >= 0xc0) {
                switch (cmd) {
                    case 0xff: // seq_end
                        break;

                    case 0xfc: // seq_call
                        u16v = m64_read_s16(state);
                        if (0 && state->depth >= 4) {
                            eu_stubbed_printf_0("Macro Level Over Error!\n");
                        }
#if defined(VERSION_EU) || defined(VERSION_SH)
                        state->stack[state->depth++] = state->pc;
#else
                        state->depth++, state->stack[state->depth - 1] = state->pc;
#endif
                        state->pc = seqPlayer->seqData + u16v;
                        break;

                    case 0xf8: // seq_loop; loop start, N iterations (or 256 if N = 0)
                        if (0 && state->depth >= 4) {
                            eu_stubbed_printf_0("Macro Level Over Error!\n");
                        }
                        state->remLoopIters[state->depth] = m64_read_u8(state);
#if defined(VERSION_EU) || defined(VERSION_SH)
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

#if defined(VERSION_EU) || defined(VERSION_SH)
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
                        state->pc += (s8) temp;
                        break;
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xf1: // seq_reservenotes
#else
                    case 0xf2: // seq_reservenotes
#endif
                        note_pool_clear(&seqPlayer->notePool);
                        note_pool_fill(&seqPlayer->notePool, m64_read_u8(state));
                        break;

#if defined(VERSION_EU) || defined(VERSION_SH)
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
#ifndef VERSION_SH
                    case 0xdc: // seq_addtempo (bpm)
#endif
#ifdef VERSION_SH
                        seqPlayer->tempo = m64_read_u8(state) * TEMPO_SCALE;
#else
                        temp = m64_read_u8(state);
                        if (cmd == 0xdd) {
                            seqPlayer->tempo = temp * TEMPO_SCALE;
                        } else {
                            seqPlayer->tempo += (s8) temp * TEMPO_SCALE;
                        }
#endif

                        if (seqPlayer->tempo > gTempoInternalToExternal) {
                            seqPlayer->tempo = gTempoInternalToExternal;
                        }

                        //if (cmd) {}

                        if ((s16) seqPlayer->tempo <= 0) {
                            seqPlayer->tempo = 1;
                        }
                        break;

#ifdef VERSION_SH
                    case 0xdc: // seq_addtempo (bpm)
                        seqPlayer->tempoAdd = (s8) m64_read_u8(state) * TEMPO_SCALE;
                        break;
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xda:
                        cmd = m64_read_u8(state);
                        u16v = m64_read_s16(state);
                        switch (cmd) {
                            case SEQUENCE_PLAYER_STATE_0:
                            case SEQUENCE_PLAYER_STATE_FADE_OUT:
                                if (seqPlayer->state != SEQUENCE_PLAYER_STATE_2) {
                                    seqPlayer->fadeTimerUnkEu = u16v;
                                    seqPlayer->state = cmd;
                                }
                                break;
                            case SEQUENCE_PLAYER_STATE_2:
                                seqPlayer->fadeRemainingFrames = u16v;
                                seqPlayer->state = cmd;
                                seqPlayer->fadeVelocity =
                                    (0.0f - seqPlayer->fadeVolume) / (s32)(u16v & 0xFFFFu);
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
                                seqPlayer->fadeRemainingFrames = seqPlayer->fadeTimerUnkEu;
                                if (seqPlayer->fadeTimerUnkEu != 0) {
                                    seqPlayer->fadeVelocity = (temp32 / 127.0f - seqPlayer->fadeVolume) / FLOAT_CAST(seqPlayer->fadeRemainingFrames);
                                } else {
                                    seqPlayer->fadeVolume = temp32 / 127.0f;
                                }
                        }
                        break;
#else
                    case 0xdb: // seq_setvol
                        cmd = m64_read_u8(state);
                        switch (seqPlayer->state) {
                            case SEQUENCE_PLAYER_STATE_2:
                                if (seqPlayer->fadeRemainingFrames != 0) {
                                    f32 targetVolume = FLOAT_CAST(cmd) / US_FLOAT(127.0);
                                    seqPlayer->fadeVelocity = (targetVolume - seqPlayer->fadeVolume)
                                                              / FLOAT_CAST(seqPlayer->fadeRemainingFrames);
                                    break;
                                }
                                // fallthrough
                            case SEQUENCE_PLAYER_STATE_0:
                                seqPlayer->fadeVolume = FLOAT_CAST(cmd) / US_FLOAT(127.0);
                                break;
                            case SEQUENCE_PLAYER_STATE_FADE_OUT:
                            case SEQUENCE_PLAYER_STATE_4:
                                seqPlayer->volume = FLOAT_CAST(cmd) / US_FLOAT(127.0);
                                break;
                        }
                        break;

                    case 0xda: // seq_changevol
                        temp = m64_read_u8(state);
                        seqPlayer->fadeVolume =
                            seqPlayer->fadeVolume + (f32)(s8) temp / US_FLOAT(127.0);
                        break;
#endif

#if defined(VERSION_EU) || defined(VERSION_SH)
                    case 0xd9:
                        temp = m64_read_u8(state);
                        seqPlayer->fadeVolumeScale = (s8) temp / 127.0f;
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
                        seqPlayer->muteVolumeScale = (f32)(s8) temp / US_FLOAT(127.0);
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
                        seqData = seqPlayer->seqData + u16v;
                        if (cmd == 0xd2) {
                            seqPlayer->shortNoteVelocityTable = seqData;
                        } else {
                            seqPlayer->shortNoteDurationTable = seqData;
                        }
                        break;

                    case 0xd0: // seq_setnoteallocationpolicy
                        seqPlayer->noteAllocPolicy = m64_read_u8(state);
                        break;

                    case 0xcc: // seq_setval
                        value = m64_read_u8(state);
                        break;

                    case 0xc9: // seq_bitand
#if defined(VERSION_EU) || defined(VERSION_SH)
                        value &= m64_read_u8(state);
#else
                        value = m64_read_u8(state) & value;
#endif
                        break;

                    case 0xc8: // seq_subtract
                        value = value - m64_read_u8(state);
                        break;

#ifdef VERSION_SH
                    case 0xc7:
                        cmd = m64_read_u8(state);
                        u16v = m64_read_s16(state);
                        seqData = seqPlayer->seqData + u16v;
                        *seqData = (u8)value + cmd;
                        break;

                    case 0xc6:
                        seqPlayer->unkSh = TRUE;
                        return;
#endif

                    default:
                        eu_stubbed_printf_1("Group:Undefine upper C0h command (%x)\n", cmd);
                        break;
                }
            } else {
                loBits = cmd & 0xf;
                switch (cmd & 0xf0) {
                    case 0x00: // seq_testchdisabled
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_EU) || defined(VERSION_SH)
                        value -= seqPlayer->seqVariationEu[0];
#else
                        value -= seqPlayer->seqVariation;
#endif
                        break;
                    case 0x60:
                        break;
                    case 0x70: // seq_setvariation
#if defined(VERSION_EU) || defined(VERSION_SH)
                        seqPlayer->seqVariationEu[0] = value;
#else
                        seqPlayer->seqVariation = value;
#endif
                        break;
                    case 0x80: // seq_getvariation
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if !defined(VERSION_EU) && !defined(VERSION_SH)
                    case 0xd8: // (this makes no sense)
                        break;
                    case 0xd9:
                        break;
#endif

                    default:
                        eu_stubbed_printf_0("Group:Undefined Command\n");
                        break;
                }
            }
        }
    }

    for (i = 0; i < CHANNELS_MAX; i++) {
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_EU) || defined(VERSION_SH)
            sequence_player_process_sequence(&gSequencePlayers[i]);
            sequence_player_process_sound(&gSequencePlayers[i]);
#else
            sequence_player_process_sequence(gSequencePlayers + i);
            sequence_player_process_sound(gSequencePlayers + i);
#endif
        }
    }
#if defined(VERSION_JP) || defined(VERSION_US)
    reclaim_notes();
#endif
    process_notes();
}

void init_sequence_player(u32 player) {
    struct SequencePlayer *seqPlayer = &gSequencePlayers[player];
#if defined(VERSION_EU) || defined(VERSION_SH)
    sequence_player_disable(seqPlayer);
#endif
#ifdef VERSION_SH
    seqPlayer->unkSh = FALSE;
#else
    seqPlayer->muted = FALSE;
#endif
    seqPlayer->delay = 0;
#if defined(VERSION_EU) || defined(VERSION_SH)
    seqPlayer->state = 1;
#else
    seqPlayer->state = SEQUENCE_PLAYER_STATE_0;
#endif
    seqPlayer->fadeRemainingFrames = 0;
#if defined(VERSION_EU) || defined(VERSION_SH)
    seqPlayer->fadeTimerUnkEu = 0;
#endif
    seqPlayer->tempoAcc = 0;
    seqPlayer->tempo = 120 * TEMPO_SCALE; // 120 BPM
#ifdef VERSION_SH
    seqPlayer->tempoAdd = 0;
#endif
    seqPlayer->transposition = 0;
#ifndef VERSION_SH
    seqPlayer->muteBehavior = MUTE_BEHAVIOR_STOP_SCRIPT | MUTE_BEHAVIOR_STOP_NOTES | MUTE_BEHAVIOR_SOFTEN;
#endif
    seqPlayer->noteAllocPolicy = 0;
    seqPlayer->shortNoteVelocityTable = gDefaultShortNoteVelocityTable;
    seqPlayer->shortNoteDurationTable = gDefaultShortNoteDurationTable;
    seqPlayer->fadeVolume = 1.0f;
#if defined(VERSION_EU) || defined(VERSION_SH)
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
#if defined(VERSION_JP) || defined(VERSION_US)
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

#if defined(VERSION_EU) || defined(VERSION_SH)
        gSequencePlayers[i].seqVariationEu[0] = -1;
#else
        gSequencePlayers[i].seqVariation = -1;
#endif
#ifdef VERSION_SH
        gSequencePlayers[i].muteBehavior = MUTE_BEHAVIOR_STOP_SCRIPT | MUTE_BEHAVIOR_STOP_NOTES | MUTE_BEHAVIOR_SOFTEN;
        gSequencePlayers[i].enabled = FALSE;
        gSequencePlayers[i].muted = FALSE;
#endif
        gSequencePlayers[i].bankDmaInProgress = FALSE;
        gSequencePlayers[i].seqDmaInProgress = FALSE;
        init_note_lists(&gSequencePlayers[i].notePool);
        init_sequence_player(i);
    }
}

