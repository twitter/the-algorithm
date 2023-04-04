//! Copt inlining for US/JP. Here be dragons
// This version is basically identical to EU

#include <ultra64.h>
#include <macros.h>

#include "audio/heap.h"
#include "audio/data.h"
#include "audio/load.h"
#include "audio/seqplayer.h"
#include "audio/external.h"
#include "audio/effects.h"

#define PORTAMENTO_IS_SPECIAL(x) ((x).mode & 0x80)
#define PORTAMENTO_MODE(x) ((x).mode & ~0x80)
#define PORTAMENTO_MODE_1 1
#define PORTAMENTO_MODE_2 2
#define PORTAMENTO_MODE_3 3
#define PORTAMENTO_MODE_4 4
#define PORTAMENTO_MODE_5 5

#define COPT 0
#if COPT
#define M64_READ_U8(state, dst) \
    dst = m64_read_u8(state);
#else
#define M64_READ_U8(state, dst) \
{                               \
    u8 * _ptr_pc;               \
    u8  _pc;                    \
    _ptr_pc = (*state).pc;      \
    ((*state).pc)++;            \
    _pc = *_ptr_pc;             \
    dst = _pc;                  \
}
#endif


#if COPT
#define M64_READ_S16(state, dst) \
    dst = m64_read_s16(state);
#else
#define M64_READ_S16(state, dst)    \
{                                   \
    s16 _ret;                       \
    _ret = *(*state).pc << 8;       \
    ((*state).pc)++;                \
    _ret = *(*state).pc | _ret;     \
    ((*state).pc)++;                \
    dst = _ret;                     \
}
#endif
#if COPT
#define M64_READ_COMPRESSED_U16(state, dst) \
    dst = m64_read_compressed_u16(state);
#else
#define M64_READ_COMPRESSED_U16(state, dst) \
{                                           \
    u16 ret = *(state->pc++);               \
    if (ret & 0x80) {                       \
        ret = (ret << 8) & 0x7f00;          \
        ret = *(state->pc++) | ret;         \
    }                                       \
    dst = ret;                              \
}
#endif

#if COPT
#define GET_INSTRUMENT(seqChannel, instId, _instOut, _adsr, dst, l) \
    dst = get_instrument(seqChannel, instId, _instOut, _adsr);
#else
#define GET_INSTRUMENT(seqChannel, instId, _instOut, _adsr, dst, l) \
{ \
struct AdsrSettings *adsr = _adsr; \
struct Instrument **instOut = _instOut;\
    u8 _instId = instId; \
    struct Instrument *inst; \
    UNUSED u32 pad; \
        /* copt inlines instId here  */ \
    if (instId >= gCtlEntries[(*seqChannel).bankId].numInstruments) { \
        _instId = gCtlEntries[(*seqChannel).bankId].numInstruments; \
        if (_instId == 0) { \
            dst = 0; \
            goto ret ## l; \
        } \
        _instId--; \
    } \
    inst = gCtlEntries[(*seqChannel).bankId].instruments[_instId]; \
    if (inst == NULL) { \
        while (_instId != 0xff) { \
            inst = gCtlEntries[(*seqChannel).bankId].instruments[_instId]; \
            if (inst != NULL) { \
                goto gi ## l; \
            } \
            _instId--; \
        } \
        gi ## l:; \
    } \
    if (((uintptr_t) gBankLoadedPool.persistent.pool.start <= (uintptr_t) inst \
         && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.persistent.pool.start \
                                          + gBankLoadedPool.persistent.pool.size)) \
        || ((uintptr_t) gBankLoadedPool.temporary.pool.start <= (uintptr_t) inst \
            && (uintptr_t) inst <= (uintptr_t)(gBankLoadedPool.temporary.pool.start \
                                             + gBankLoadedPool.temporary.pool.size))) { \
        (*adsr).envelope = (*inst).envelope; \
        (*adsr).releaseRate = (*inst).releaseRate; \
        *instOut = inst; \
        _instId++; \
        goto ret ## l; \
    } \
    gAudioErrorFlags = _instId + 0x20000; \
    *instOut = NULL; \
    ret ## l: ; \
}
#endif

void seq_channel_layer_process_script(struct SequenceChannelLayer *layer) {
    struct SequencePlayer *seqPlayer;   // sp5C, t4
    struct SequenceChannel *seqChannel; // sp58, t5
    struct M64ScriptState *state;
    struct Portamento *portamento;
    struct AudioBankSound *sound;
    struct Instrument *instrument;
    struct Drum *drum;
    s32 temp_a0_5;
    u8 sameSound;
    u8 cmd;                             // a0 sp3E, EU s2
    u8 cmdSemitone;                     // sp3D, t0
    u16 sp3A;                           // t2, a0, a1
    f32 tuning;                         // f0
    s32 vel;                            // sp30, t3
    s32 usedSemitone;                   // a1
    f32 freqScale;                      // sp28, f0
    f32 sp24;
    f32 temp_f12;
    f32 temp_f2;

//! Copt: manually inline these functions in the scope of this routine
#ifdef __sgi
#pragma inline routine(m64_read_u8)
#pragma inline routine(m64_read_compressed_u16)
#pragma inline routine(m64_read_s16)
#pragma inline routine(get_instrument)
#endif

    sameSound = TRUE;
    if ((*layer).enabled == FALSE) {
        return;
    }

    if ((*layer).delay > 1) {
        (*layer).delay--;
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

    seqChannel = (*layer).seqChannel;
    seqPlayer = (*seqChannel).seqPlayer;
    for (;;) {
        state = &layer->scriptState;
        //M64_READ_U8(state, cmd);
        {
            u8 *_ptr_pc;
            _ptr_pc = (*state).pc++;
            cmd = *_ptr_pc;
        }

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
                M64_READ_S16(state, sp3A);
                state->depth++, state->stack[state->depth - 1] = state->pc;
                state->pc = seqPlayer->seqData + sp3A;
                break;

            case 0xf8: // layer_loop; loop start, N iterations (or 256 if N = 0)
                M64_READ_U8(state, state->remLoopIters[state->depth]);
                state->depth++, state->stack[state->depth - 1] = state->pc;
                break;

            case 0xf7: // layer_loopend
                if (--state->remLoopIters[state->depth - 1] != 0) {
                    state->pc = state->stack[state->depth - 1];
                } else {
                    state->depth--;
                }
                break;

            case 0xfb: // layer_jump
                M64_READ_S16(state, sp3A);
                state->pc = seqPlayer->seqData + sp3A;
                break;

            case 0xc1: // layer_setshortnotevelocity
            case 0xca: // layer_setpan
                temp_a0_5 = *(state->pc++);
                if (cmd == 0xc1) {
                    layer->velocitySquare = (f32)(temp_a0_5 * temp_a0_5);
                } else {
                    layer->pan = (f32) temp_a0_5 / US_FLOAT(128.0);
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
                //! copt needs a ternary:
                //layer->continuousNotes = (cmd == 0xc4) ? TRUE : FALSE;
                {
                    u8 setting;
                    if (cmd == 0xc4) {
                        setting = TRUE;
                    } else {
                        setting = FALSE;
                    }
                    layer->continuousNotes = setting;
                    seq_channel_layer_note_decay(layer);
                }
                break;

            case 0xc3: // layer_setshortnotedefaultplaypercentage
                M64_READ_COMPRESSED_U16(state, sp3A);
                layer->shortNoteDefaultPlayPercentage = sp3A;
                break;

            case 0xc6: // layer_setinstr
                M64_READ_U8(state, cmdSemitone);

                if (cmdSemitone < 127) {
                    GET_INSTRUMENT(seqChannel, cmdSemitone, &(*layer).instrument, &(*layer).adsr, cmdSemitone, 1);
                }
                break;

            case 0xc7: // layer_portamento
                M64_READ_U8(state, (*layer).portamento.mode);
                M64_READ_U8(state, cmdSemitone);

                cmdSemitone = cmdSemitone + (*seqChannel).transposition;
                cmdSemitone += (*layer).transposition;
                cmdSemitone += (*seqPlayer).transposition;

                if (cmdSemitone >= 0x80) {
                    cmdSemitone = 0;
                }
                layer->portamentoTargetNote = cmdSemitone;

                // If special, the next param is u8 instead of var
                if (PORTAMENTO_IS_SPECIAL((*layer).portamento)) {
                    layer->portamentoTime = *((state)->pc++);
                    break;
                }

                M64_READ_COMPRESSED_U16(state, sp3A);
                layer->portamentoTime = sp3A;
                break;

            case 0xc8: // layer_disableportamento
                layer->portamento.mode = 0;
                break;

            default:
                switch (cmd & 0xf0) {
                    case 0xd0: // layer_setshortnotevelocityfromtable
                        sp3A = seqPlayer->shortNoteVelocityTable[cmd & 0xf];
                        (*layer).velocitySquare = (f32)(sp3A * sp3A);
                        break;
                    case 0xe0: // layer_setshortnotedurationfromtable
                        (*layer).noteDuration = seqPlayer->shortNoteDurationTable[cmd & 0xf];
                        break;
                }
        }
    }

    if (cmd == 0xc0) { // layer_delay
        M64_READ_COMPRESSED_U16(state, layer->delay);
        layer->stopSomething = TRUE;
    } else {
        layer->stopSomething = FALSE;

        if (seqChannel->largeNotes == TRUE) {
            switch (cmd & 0xc0) {
                case 0x00: // layer_note0 (play percentage, velocity, duration)
                    M64_READ_COMPRESSED_U16(state, sp3A);
                    vel = *((*state).pc++);
                    layer->noteDuration = *((*state).pc++);
                    layer->playPercentage = sp3A;
                    goto l1090;

                case 0x40: // layer_note1 (play percentage, velocity)
                    M64_READ_COMPRESSED_U16(state, sp3A);
                    vel = *((*state).pc++);
                    layer->noteDuration = 0;
                    layer->playPercentage = sp3A;
                    goto l1090;

                case 0x80: // layer_note2 (velocity, duration; uses last play percentage)
                    sp3A = layer->playPercentage;
                    vel = *((*state).pc++);
                    layer->noteDuration = *((*state).pc++);
                    goto l1090;
            }
l1090:
            cmdSemitone = cmd - (cmd & 0xc0);
            layer->velocitySquare = vel * vel;
        } else {
            switch (cmd & 0xc0) {
                case 0x00: // play note, type 0 (play percentage)
                    M64_READ_COMPRESSED_U16(state, sp3A);
                    layer->playPercentage = sp3A;
                    goto l1138;

                case 0x40: // play note, type 1 (uses default play percentage)
                    sp3A = layer->shortNoteDefaultPlayPercentage;
                    goto l1138;

                case 0x80: // play note, type 2 (uses last play percentage)
                    sp3A = layer->playPercentage;
                    goto l1138;
            }
l1138:

            cmdSemitone = cmd - (cmd & 0xc0);
        }

        layer->delay = sp3A;
        layer->duration = layer->noteDuration * sp3A / 256;
        if ((seqPlayer->muted && (seqChannel->muteBehavior & MUTE_BEHAVIOR_STOP_NOTES) != 0)
            || seqChannel->stopSomething2
            || !seqChannel->hasInstrument
        ) {
            layer->stopSomething = TRUE;
        } else {
            if (seqChannel->instOrWave == 0) { // drum
                cmdSemitone += (*seqChannel).transposition + (*layer).transposition;
                if (cmdSemitone >= gCtlEntries[seqChannel->bankId].numDrums) {
                    cmdSemitone = gCtlEntries[seqChannel->bankId].numDrums;
                    if (cmdSemitone == 0) {
                        // this goto looks a bit like a function return...
                        layer->stopSomething = TRUE;
                        goto skip;
                    }

                    cmdSemitone--;
                }

                drum = gCtlEntries[seqChannel->bankId].drums[cmdSemitone];
                if (drum == NULL) {
                    layer->stopSomething = TRUE;
                } else {
                    layer->adsr.envelope = drum->envelope;
                    layer->adsr.releaseRate = drum->releaseRate;
                    layer->pan = FLOAT_CAST(drum->pan) / US_FLOAT(128.0);
                    layer->sound = &drum->sound;
                    layer->freqScale = layer->sound->tuning;
                }

            skip:;
            } else { // instrument
                cmdSemitone += (*seqPlayer).transposition + (*seqChannel).transposition + (*layer).transposition;
                if (cmdSemitone >= 0x80) {
                    layer->stopSomething = TRUE;
                } else {
                    instrument = layer->instrument;
                    if (instrument == NULL) {
                        instrument = seqChannel->instrument;
                    }

                    if (layer->portamento.mode != 0) {
                        //! copt needs a ternary:
                        //usedSemitone = (layer->portamentoTargetNote < cmdSemitone) ? cmdSemitone : layer->portamentoTargetNote;
                        if (layer->portamentoTargetNote < cmdSemitone) {
                            usedSemitone = cmdSemitone;
                        } else {
                            usedSemitone = layer->portamentoTargetNote;
                        }

                        if (instrument != NULL) {
                            sound = (u8) usedSemitone < instrument->normalRangeLo ? &instrument->lowNotesSound
                                  : (u8) usedSemitone <= instrument->normalRangeHi ?
                                        &instrument->normalNotesSound : &instrument->highNotesSound;

                            sameSound = (sound == (*layer).sound);
                            layer->sound = sound;
                            tuning = (*sound).tuning;
                        } else {
                            layer->sound = NULL;
                            tuning = 1.0f;
                        }

                        temp_f2 = gNoteFrequencies[cmdSemitone] * tuning;
                        temp_f12 = gNoteFrequencies[layer->portamentoTargetNote] * tuning;

                        portamento = &layer->portamento;
                        switch (PORTAMENTO_MODE(layer->portamento)) {
                            case PORTAMENTO_MODE_1:
                            case PORTAMENTO_MODE_3:
                            case PORTAMENTO_MODE_5:
                                sp24 = temp_f2;
                                freqScale = temp_f12;
                                goto l13cc;

                            case PORTAMENTO_MODE_2:
                            case PORTAMENTO_MODE_4:
                                freqScale = temp_f2;
                                sp24 = temp_f12;
                                goto l13cc;
                        }
l13cc:
                        portamento->extent = sp24 / freqScale - US_FLOAT(1.0);
                        if (PORTAMENTO_IS_SPECIAL((*layer).portamento)) {
                            portamento->speed = US_FLOAT(32512.0) * FLOAT_CAST((*seqPlayer).tempo)
                                                / ((f32)(*layer).delay * (f32) gTempoInternalToExternal
                                                   * FLOAT_CAST((*layer).portamentoTime));
                        } else {
                            portamento->speed = US_FLOAT(127.0) / FLOAT_CAST((*layer).portamentoTime);
                        }
                        portamento->cur = 0.0f;
                        layer->freqScale = freqScale;
                        if (PORTAMENTO_MODE((*layer).portamento) == PORTAMENTO_MODE_5) {
                            layer->portamentoTargetNote = cmdSemitone;
                        }
                    } else if (instrument != NULL) {
                        sound = cmdSemitone < instrument->normalRangeLo ?
                                         &instrument->lowNotesSound : cmdSemitone <= instrument->normalRangeHi ?
                                         &instrument->normalNotesSound : &instrument->highNotesSound;

                        sameSound = (sound == (*layer).sound);
                        layer->sound = sound;
                        layer->freqScale = gNoteFrequencies[cmdSemitone] * (*sound).tuning;
                    } else {
                        layer->sound = NULL;
                        layer->freqScale = gNoteFrequencies[cmdSemitone];
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

    cmdSemitone = FALSE;
    if (!layer->continuousNotes) {
        cmdSemitone = TRUE;
    } else if (layer->note == NULL || layer->status == SOUND_LOAD_STATUS_NOT_LOADED) {
        cmdSemitone = TRUE;
    } else if (sameSound == FALSE) {
        seq_channel_layer_note_decay(layer);
        cmdSemitone = TRUE;
    } else if (layer->sound == NULL) {
        init_synthetic_wave(layer->note, layer);
    }

    if (cmdSemitone != FALSE) {
        (*layer).note = alloc_note(layer);
    }

    if (layer->note != NULL && layer->note->parentLayer == layer) {
        note_vibrato_init(layer->note);
    }
}
