#ifndef _INTRO_GEO_H
#define _INTRO_GEO_H

#include "engine/graph_node.h"

Gfx *geo_title_screen(s32 a0, struct GraphNode *a1, UNUSED void *context);
Gfx *geo_fade_transition(s32 a0, struct GraphNode *a1, UNUSED void *context);
Gfx *intro_backdrop_one_image(s32 index, s8 *backdrop_table);
Gfx *geo_intro_backdrop(s32 a0, struct GraphNode *a1, UNUSED void *context);
Gfx *geo_game_over_tile(s32 a0, struct GraphNode *a1, UNUSED void *context);

#endif /* _INTRO_GEO_H */
