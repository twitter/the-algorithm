#ifndef _DEBUG_H
#define _DEBUG_H

#include "types.h"

enum DebugPage {
    DEBUG_PAGE_OBJECTINFO,       // 0: objectinfo
    DEBUG_PAGE_CHECKSURFACEINFO, // 1: checkinfo/surfaceinfo
    DEBUG_PAGE_MAPINFO,          // 2: mapinfo
    DEBUG_PAGE_STAGEINFO,        // 3: stageinfo
    DEBUG_PAGE_EFFECTINFO,       // 4: effectinfo
    DEBUG_PAGE_ENEMYINFO         // 5: enemyinfo
};

extern s64 get_current_clock(void);
extern s64 get_clock_difference(s64 cycles);
extern void set_text_array_x_y(s32 xOffset, s32 yOffset);
extern void print_debug_top_down_objectinfo(const char *str, s32 number);
extern void print_debug_top_down_mapinfo(const char * str, s32 number);
extern void print_debug_bottom_up(const char*,s32);
extern void debug_unknown_level_select_check(void);
extern void reset_debug_objectinfo(void);
extern void stub_debug_5(void);
extern void try_print_debug_mario_object_info(void);
extern void try_do_mario_debug_object_spawn(void);
extern void try_print_debug_mario_level_info(void);

#endif /* _DEBUG_H */
