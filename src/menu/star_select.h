#ifndef STAR_SELECT_H
#define STAR_SELECT_H

#include <PR/ultratypes.h>
#include <PR/gbi.h>

#include "types.h"

enum StarSelectorTypes {
    STAR_SELECTOR_NOT_SELECTED,
    STAR_SELECTOR_SELECTED,
    STAR_SELECTOR_100_COINS
};

#ifdef AVOID_UB
Gfx *geo_act_selector_strings(s16 callContext, UNUSED struct GraphNode *node, UNUSED void *context);
#else
Gfx *geo_act_selector_strings(s16 callContext, UNUSED struct GraphNode *node);
#endif
s32 lvl_init_act_selector_values_and_stars(UNUSED s32 arg, UNUSED s32 unused);
s32 lvl_update_obj_and_load_act_button_actions(UNUSED s32 arg, UNUSED s32 unused);

#endif // STAR_SELECT_H
