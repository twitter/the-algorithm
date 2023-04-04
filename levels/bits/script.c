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
#include "levels/bits/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_BITS_SLIDING_PLATFORM,       /*pos*/ -2370, -4525,     0, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00100000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITS_TWIN_SLIDING_PLATFORMS, /*pos*/ -2611,  3544,  -904, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00CF0000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITS_TWIN_SLIDING_PLATFORMS, /*pos*/ -4700,  3544,  -904, /*angle*/ 0, 180, 0, /*behParam*/ 0x008F0000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITS_OCTAGONAL_PLATFORM,     /*pos*/  4139, -1740, -1831, /*angle*/ 0, 0, 0,   /*behParam*/ 0x02000000, /*beh*/ bhvOctagonalPlatformRotating),
    OBJECT(/*model*/ MODEL_BITS_OCTAGONAL_PLATFORM,     /*pos*/ -6459,  1732,  -904, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvOctagonalPlatformRotating),
    OBJECT(/*model*/ MODEL_BITS_OCTAGONAL_PLATFORM,     /*pos*/ -4770,  1732,  -904, /*angle*/ 0, 0, 0,   /*behParam*/ 0x02000000, /*beh*/ bhvOctagonalPlatformRotating),
    OBJECT(/*model*/ MODEL_BITS_FERRIS_WHEEL_AXLE,      /*pos*/ -1748, -1330, -1094, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFerrisWheelAxle),
    OBJECT(/*model*/ MODEL_BITS_FERRIS_WHEEL_AXLE,      /*pos*/  2275,  5628, -1315, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFerrisWheelAxle),
    OBJECT(/*model*/ MODEL_BITS_FERRIS_WHEEL_AXLE,      /*pos*/  3114,  4701, -1320, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFerrisWheelAxle),
    OBJECT(/*model*/ MODEL_BITS_ARROW_PLATFORM,         /*pos*/  2793,  2325,  -904, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00610000, /*beh*/ bhvActivatedBackAndForthPlatform),
    OBJECT(/*model*/ MODEL_BITS_SEESAW_PLATFORM,        /*pos*/    27, -1555,  -713, /*angle*/ 0, 90, 0,  /*behParam*/ 0x00010000, /*beh*/ bhvSeesawPlatform),
    OBJECT(/*model*/ MODEL_BITS_TILTING_W_PLATFORM,     /*pos*/  -306, -4300,     0, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00020000, /*beh*/ bhvSeesawPlatform),
    OBJECT(/*model*/ MODEL_BITS_STAIRCASE,              /*pos*/  1769,  -234,  -899, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvAnimatesOnFloorSwitchPress),
    OBJECT(/*model*/ MODEL_PURPLE_SWITCH,               /*pos*/  -279,  -234,  -900, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFloorSwitchAnimatesObject),
    OBJECT(/*model*/ MODEL_NONE,                        /*pos*/ -6460,  2039,  -905, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00CF0000, /*beh*/ bhvPoleGrabbing),
    OBJECT(/*model*/ MODEL_NONE,                        /*pos*/ -3326,  3227,  -905, /*angle*/ 0, 0, 0,   /*behParam*/ 0x004D0000, /*beh*/ bhvPoleGrabbing),
    OBJECT(/*model*/ MODEL_NONE,                        /*pos*/  5518,  3184, -4019, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    OBJECT(/*model*/ MODEL_NONE,                        /*pos*/  6465,  3731, -1915, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    OBJECT(/*model*/ MODEL_NONE,                        /*pos*/  5915,  3718, -4019, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_NONE, /*pos*/ 350, 6800, -6800, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBowserCourseRedCoinStar),
    RETURN(),
};

const LevelScript level_bits_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _bits_segment_7SegmentRomStart, _bits_segment_7SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0A, _bits_skybox_mio0SegmentRomStart, _bits_skybox_mio0SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _sky_mio0SegmentRomStart, _sky_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group14_mio0SegmentRomStart, _group14_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group14_geoSegmentRomStart,  _group14_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_15),
    JUMP_LINK(script_func_global_1),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_03,           bits_geo_000430),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_04,           bits_geo_000448),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_05,           bits_geo_000460),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_06,           bits_geo_000478),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_07,           bits_geo_000490),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_08,           bits_geo_0004A8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_09,           bits_geo_0004C0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0A,           bits_geo_0004D8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0B,           bits_geo_0004F0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0C,           bits_geo_000508),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0D,           bits_geo_000520),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0E,           bits_geo_000538),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0F,           bits_geo_000550),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_10,           bits_geo_000568),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_11,           bits_geo_000580),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_12,           bits_geo_000598),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_13,           bits_geo_0005B0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_14,           bits_geo_0005C8),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_SLIDING_PLATFORM,       bits_geo_0005E0),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_TWIN_SLIDING_PLATFORMS, bits_geo_0005F8),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_OCTAGONAL_PLATFORM,     bits_geo_000610),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_BLUE_PLATFORM,          bits_geo_000628),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_FERRIS_WHEEL_AXLE,      bits_geo_000640),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_ARROW_PLATFORM,         bits_geo_000658),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_SEESAW_PLATFORM,        bits_geo_000670),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_TILTING_W_PLATFORM,     bits_geo_000688),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_STAIRCASE,              bits_geo_0006A0),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_STAIRCASE_FRAME1,       bits_geo_0006B8),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_STAIRCASE_FRAME2,       bits_geo_0006D0),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_STAIRCASE_FRAME3,       bits_geo_0006E8),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_STAIRCASE_FRAME4,       bits_geo_000700),
    LOAD_MODEL_FROM_GEO(MODEL_BITS_WARP_PIPE,              warp_pipe_geo),

    AREA(/*index*/ 1, bits_geo_000718),
        OBJECT(/*model*/ MODEL_NONE,           /*pos*/ -7039, -3812,     4, /*angle*/ 0, 90, 0,  /*behParam*/ 0x000A0000, /*beh*/ bhvAirborneWarp),
        OBJECT(/*model*/ MODEL_BITS_WARP_PIPE, /*pos*/   351,  6652, -6030, /*angle*/ 0, 0, 0,   /*behParam*/ 0x000B0000, /*beh*/ bhvWarpPipe),
        OBJECT(/*model*/ MODEL_NONE,           /*pos*/   351,  6800, -3900, /*angle*/ 0, 180, 0, /*behParam*/ 0x000C0000, /*beh*/ bhvDeathWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_BITS, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0B, /*destLevel*/ LEVEL_BOWSER_3, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0C, /*destLevel*/ LEVEL_BITS, /*destArea*/ 0x01, /*destNode*/ 0x0C, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x6B, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        TERRAIN(/*terrainData*/ bits_seg7_collision_level),
        MACRO_OBJECTS(/*objList*/ bits_seg7_macro_objs),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0000, /*seq*/ SEQ_LEVEL_KOOPA_ROAD),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_STONE),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 90, /*pos*/ -7039, -4812, 4),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
