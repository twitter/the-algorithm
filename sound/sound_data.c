unsigned char gSoundDataADSR[] = {
#include "sound/sound_data.ctl.inc.c"
};

unsigned char gSoundDataRaw[] = {
#include "sound/sound_data.tbl.inc.c"
};

unsigned char gMusicData[] = {
#include "sound/sequences.bin.inc.c"
};

#ifndef VERSION_SH
unsigned char gBankSetsData[] = {
#include "sound/bank_sets.inc.c"
};
#endif
