#include "libultra_internal.h"
#include "libaudio_internal.h"

#define PATCH(SRC, BASE, TYPE) SRC = (TYPE)((uintptr_t) SRC + (uintptr_t) BASE)

void alSeqFileNew(ALSeqFile *f, u8 *base) {
    int i;
    for (i = 0; i < f->seqCount; i++) {
        PATCH(f->seqArray[i].offset, base, u8 *);
    }
}

static void _bnkfPatchBank(ALInstrument *inst, ALBankFile *f, u8 *table) {
    int i;
    ALSound *sound;
    ALWaveTable *wavetable;
    u8 *table2;

    if (inst->flags) {
        return;
    }

    inst->flags = 1;

    for (i = 0; i < inst->soundCount; i++) {
        PATCH(inst->soundArray[i], f, ALSound *);
        sound = inst->soundArray[i];
        if (sound->flags) {
            continue;
        }

        table2 = table;

        sound->flags = 1;
        PATCH(sound->envelope, f, ALEnvelope *);
        PATCH(sound->keyMap, f, ALKeyMap *);
        PATCH(sound->wavetable, f, ALWaveTable *);
        wavetable = sound->wavetable;
        if (wavetable->flags) {
            continue;
        }

        wavetable->flags = 1;
        PATCH(wavetable->base, table2, u8 *);
        if (wavetable->type == 0) {
            PATCH(wavetable->waveInfo.adpcmWave.book, f, ALADPCMBook *);
            if (wavetable->waveInfo.adpcmWave.loop != NULL) {
                PATCH(wavetable->waveInfo.adpcmWave.loop, f, ALADPCMloop *);
            }
        } else if (wavetable->type == 1) {
            if (wavetable->waveInfo.rawWave.loop != NULL) {
                PATCH(wavetable->waveInfo.rawWave.loop, f, ALRawLoop *);
            }
        }
    }
}

// Force adding another jr $ra.  Has to be called or it doesn't get put in the
// right place.
static void unused(void) {
}

void alBnkfNew(ALBankFile *f, u8 *table) {
    ALBank *bank;
    int i;
    int j;
    unused();
    if (f->revision != AL_BANK_VERSION) {
        return;
    }

    for (i = 0; i < f->bankCount; i++) {
        PATCH(f->bankArray[i], f, ALBank *);
        if (f->bankArray[i] == NULL) {
            continue;
        }

        bank = f->bankArray[i];
        if (bank->flags == 0) {
            bank->flags = 1;
            if (bank->percussion != NULL) {
                PATCH(bank->percussion, f, ALInstrument *);
                _bnkfPatchBank(bank->percussion, f, table);
            }
            for (j = 0; j < bank->instCount; j++) {
                PATCH(bank->instArray[j], f, ALInstrument *);
                if (bank->instArray[j] != NULL) {
                    _bnkfPatchBank(bank->instArray[j], f, table);
                }
            }
        }
    }
}
