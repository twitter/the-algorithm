#ifndef EU_TRANSLATION_H
#define EU_TRANSLATION_H

#include "types.h"

// PAL changes most text to arrays for each language. This define allows these
// differences to be combined.
#ifdef VERSION_EU
    #define LANGUAGE_ARRAY(cmd) cmd[LANGUAGE_FUNCTION]
#else
    #define LANGUAGE_ARRAY(cmd) cmd
#endif

// EU translations are contained in three segment 0x19 compressed data blocks
extern u8 _translation_en_mio0SegmentRomStart[];
extern u8 _translation_en_mio0SegmentRomEnd[];
extern u8 _translation_fr_mio0SegmentRomStart[];
extern u8 _translation_fr_mio0SegmentRomEnd[];
extern u8 _translation_de_mio0SegmentRomStart[];
extern u8 _translation_de_mio0SegmentRomEnd[];

extern void *dialog_table_eu_en[];
extern void *course_name_table_eu_en[];
extern void *act_name_table_eu_en[];

extern void *dialog_table_eu_fr[];
extern void *course_name_table_eu_fr[];
extern void *act_name_table_eu_fr[];

extern void *dialog_table_eu_de[];
extern void *course_name_table_eu_de[];
extern void *act_name_table_eu_de[];

#endif /* EU_TRANSLATION_H */
