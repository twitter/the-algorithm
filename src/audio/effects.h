#ifndef AUDIO_EFFECTS_H
#define AUDIO_EFFECTS_H

#include <PR/ultratypes.h>

#include "internal.h"
#include "platform_info.h"

#define ADSR_STATE_DISABLED 0
#define ADSR_STATE_INITIAL 1
#define ADSR_STATE_START_LOOP 2
#define ADSR_STATE_LOOP 3
#define ADSR_STATE_FADE 4
#define ADSR_STATE_HANG 5
#define ADSR_STATE_DECAY 6
#define ADSR_STATE_RELEASE 7
#define ADSR_STATE_SUSTAIN 8

#define ADSR_ACTION_RELEASE 0x10
#define ADSR_ACTION_DECAY 0x20
#define ADSR_ACTION_HANG 0x40

#define ADSR_DISABLE 0
#define ADSR_HANG -1
#define ADSR_GOTO -2
#define ADSR_RESTART -3

// Envelopes are always stored as big endian, to match sequence files which are
// byte blobs and can embed envelopes. Hence this byteswapping macro.
#if IS_BIG_ENDIAN
#define BSWAP16(x) (x)
#else
#define BSWAP16(x) (((x) & 0xff) << 8 | (((x) >> 8) & 0xff))
#endif

void sequence_player_process_sound(struct SequencePlayer *seqPlayer);
void note_vibrato_update(struct Note *note);
void note_vibrato_init(struct Note *note);
void adsr_init(struct AdsrState *adsr, struct AdsrEnvelope *envelope, s16 *volOut);
#if defined(VERSION_EU) || defined(VERSION_SH)
f32 adsr_update(struct AdsrState *adsr);
#else
s32 adsr_update(struct AdsrState *adsr);
#endif

#endif // AUDIO_EFFECTS_H
