#include <ultra64.h>
#include "sm64.h"
#include "behavior_data.h"
#include "model_ids.h"
#include "seq_ids.h"
#include "segment_symbols.h"
#include "level_commands.h"

#include "game/level_update.h"

#include "levels/scripts.h"

#include "actors/common1.h"

#include "make_const_nonconst.h"
#include "levels/ttc/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_NONE,   /*pos*/ -1080,  -840,  1573, /*angle*/ 0,   0, 0, /*behParam*/ 0x00560000, /*beh*/ bhvPoleGrabbing),
    OBJECT(/*model*/ MODEL_THWOMP, /*pos*/  1919,  6191,  1919, /*angle*/ 0, 225, 0, /*behParam*/ 0x00000000, /*beh*/ bhvThwomp2),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR,   /*pos*/ -1450, -1130, -1050, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR,   /*pos*/ -1850,   300,  -950, /*angle*/ 0,   0, 0, /*behParam*/ 0x01000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR,   /*pos*/ -1300, -2250, -1300, /*angle*/ 0,   0, 0, /*behParam*/ 0x02000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR,   /*pos*/  2200,  7300,  2210, /*angle*/ 0,   0, 0, /*behParam*/ 0x03000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR,   /*pos*/ -1050,  2400,  -790, /*angle*/ 0,   0, 0, /*behParam*/ 0x04000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE,   /*pos*/  1815, -3200,   800, /*angle*/ 0,   0, 0, /*behParam*/ 0x05000000, /*beh*/ bhvHiddenRedCoinStar, /*acts*/ ALL_ACTS),
    RETURN(),
};

const LevelScript level_ttc_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _ttc_segment_7SegmentRomStart, _ttc_segment_7SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _machine_mio0SegmentRomStart, _machine_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group1_mio0SegmentRomStart, _group1_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group1_geoSegmentRomStart,  _group1_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_1),
    JUMP_LINK(script_func_global_2),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_ROTATING_CUBE,     ttc_geo_000240),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_ROTATING_PRISM,    ttc_geo_000258),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_PENDULUM,          ttc_geo_000270),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_LARGE_TREADMILL,   ttc_geo_000288),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_SMALL_TREADMILL,   ttc_geo_0002A8),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_PUSH_BLOCK,        ttc_geo_0002C8),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_ROTATING_HEXAGON,  ttc_geo_0002E0),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_ROTATING_TRIANGLE, ttc_geo_0002F8),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_PIT_BLOCK,         ttc_geo_000310),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_PIT_BLOCK_UNUSED,  ttc_geo_000328),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_ELEVATOR_PLATFORM, ttc_geo_000340),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_CLOCK_HAND,        ttc_geo_000358),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_SPINNER,           ttc_geo_000370),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_SMALL_GEAR,        ttc_geo_000388),
    LOAD_MODEL_FROM_GEO(MODEL_TTC_LARGE_GEAR,        ttc_geo_0003A0),

    AREA(/*index*/ 1, ttc_geo_0003B8),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ 1417, -3822, -548, /*angle*/ 0, 316, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvSpinAirborneWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_TTC, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x35, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x67, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        TERRAIN(/*terrainData*/ ttc_seg7_collision_level),
        MACRO_OBJECTS(/*objList*/ ttc_seg7_macro_objs),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0001, /*seq*/ SEQ_LEVEL_SLIDE),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_STONE),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 316, /*pos*/ 1417, -4822, -548),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
