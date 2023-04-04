#include <ultra64.h>
#include "sm64.h"
#include "behavior_data.h"
#include "model_ids.h"
#include "seq_ids.h"
#include "segment_symbols.h"
#include "level_commands.h"

#include "game/area.h"
#include "game/level_update.h"

#include "levels/scripts.h"

#include "actors/common1.h"

#include "make_const_nonconst.h"
#include "levels/ending/header.h"

const LevelScript level_ending_entry[] = {
    /*0*/ INIT_LEVEL(),
    /*1*/ LOAD_MIO0(/*seg*/ 0x07, _ending_segment_7SegmentRomStart, _ending_segment_7SegmentRomEnd),
    /*4*/ ALLOC_LEVEL_POOL(),

    /*5*/ AREA(/*index*/ 1, ending_geo_000050),
    /*7*/ END_AREA(),

    /*8*/ FREE_LEVEL_POOL(),
    /*9*/ SLEEP(/*frames*/ 60),
    /*10*/ BLACKOUT(/*active*/ FALSE),
    /*11*/ LOAD_AREA(/*area*/ 1),
    /*12*/ TRANSITION(/*transType*/ WARP_TRANSITION_FADE_FROM_COLOR, /*time*/ 75, /*color*/ 0x00, 0x00, 0x00),
    /*14*/ SLEEP(/*frames*/ 120),
    /*15*/ CALL(/*arg*/ 0, /*func*/ lvl_play_the_end_screen_sound),
    // L1:
    /*17*/ SLEEP(/*frames*/ 1),
    /*18*/ JUMP(level_ending_entry + 17),
};
