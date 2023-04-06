#ifndef STAR_SELECT_H
#define STAR_SELECT_H

enum StarSelectorTypes
{
    STAR_SELECTOR_NOT_SELECTED,
    STAR_SELECTOR_SELECTED,
    STAR_SELECTOR_100_COINS
};

#ifdef AVOID_UB
extern Gfx *geo_act_selector_strings(s16 callContext, struct GraphNode *node, void *context);
#else
extern Gfx *geo_act_selector_strings(s16 callContext, struct GraphNode *node);
#endif
extern s32 lvl_init_act_selector_values_and_stars(s32 arg, s32 unused);
extern s32 lvl_update_obj_and_load_act_button_actions(s32 arg, s32 unused);

#endif /* STAR_SELECT_H */
