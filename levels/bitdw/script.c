#include <ultra64.h>
#include "sm64.h"
#include "behavior_data.h"
#include "model_ids.h"
#include "seq_ids.h"
#include "dialog_ids.h"
#include "segment_symbols.h"
#include "level_commands.h"

#include "game/level_update.h"

#include "levels/scripts.h"

#include "actors/common1.h"

#include "make_const_nonconst.h"
#include "levels/bitdw/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_BITDW_SQUARE_PLATFORM,   /*pos*/ -1966, -3154,  3586, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvSquarishPathMoving),
    OBJECT(/*model*/ MODEL_BITDW_SQUARE_PLATFORM,   /*pos*/ -1352, -3154,  4200, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00020000, /*beh*/ bhvSquarishPathMoving),
    OBJECT(/*model*/ MODEL_BITDW_SQUARE_PLATFORM,   /*pos*/ -2963,  1017, -2464, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvSquarishPathMoving),
    OBJECT(/*model*/ MODEL_BITDW_SQUARE_PLATFORM,   /*pos*/ -2349,  1017, -1849, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00020000, /*beh*/ bhvSquarishPathMoving),
    OBJECT(/*model*/ MODEL_BITDW_SQUARE_PLATFORM,   /*pos*/ -2349,  1017, -1235, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvSquarishPathMoving),
    OBJECT(/*model*/ MODEL_BITDW_SQUARE_PLATFORM,   /*pos*/ -1735,  1017,  -621, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00020000, /*beh*/ bhvSquarishPathMoving),
    OBJECT(/*model*/ MODEL_BITDW_SEESAW_PLATFORM,   /*pos*/  1491,  1273,   512, /*angle*/ 0, 90, 0, /*behParam*/ 0x00000000, /*beh*/ bhvSeesawPlatform),
    OBJECT(/*model*/ MODEL_BITDW_SEESAW_PLATFORM,   /*pos*/  -147,   894,   512, /*angle*/ 0, 90, 0, /*behParam*/ 0x00000000, /*beh*/ bhvSeesawPlatform),
    OBJECT(/*model*/ MODEL_BITDW_SLIDING_PLATFORM,  /*pos*/ -5728,   819, -2151, /*angle*/ 0, 0, 0,  /*behParam*/ 0x03CE0000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITDW_FERRIS_WHEEL_AXLE, /*pos*/  -204, -1924,  3381, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00010000, /*beh*/ bhvFerrisWheelAxle),
    OBJECT(/*model*/ MODEL_BITDW_STAIRCASE,         /*pos*/  5279,  1740,    -6, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00010000, /*beh*/ bhvAnimatesOnFloorSwitchPress),
    OBJECT(/*model*/ MODEL_PURPLE_SWITCH,           /*pos*/  3922,  1740,    -7, /*angle*/ 0, 0, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvFloorSwitchAnimatesObject),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_NONE, /*pos*/ -3092, -2795, 2842, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    OBJECT(/*model*/ MODEL_NONE, /*pos*/  2463, -2386, 2844, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    RETURN(),
};

static const LevelScript script_func_local_3[] = {
    OBJECT(/*model*/ MODEL_NONE, /*pos*/  7180,  3000,    0, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBowserCourseRedCoinStar),
    RETURN(),
};

const LevelScript level_bitdw_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _bitdw_segment_7SegmentRomStart, _bitdw_segment_7SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0A, _bidw_skybox_mio0SegmentRomStart, _bidw_skybox_mio0SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _sky_mio0SegmentRomStart, _sky_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group11_mio0SegmentRomStart, _group11_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group11_geoSegmentRomStart,  _group11_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group17_mio0SegmentRomStart, _group17_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group17_geoSegmentRomStart,  _group17_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_12),
    JUMP_LINK(script_func_global_18),
    JUMP_LINK(script_func_global_1),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_03,       geo_bitdw_0003C0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_04,       geo_bitdw_0003D8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_05,       geo_bitdw_0003F0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_06,       geo_bitdw_000408),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_07,       geo_bitdw_000420),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_08,       geo_bitdw_000438),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_09,       geo_bitdw_000450),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0A,       geo_bitdw_000468),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0B,       geo_bitdw_000480),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0C,       geo_bitdw_000498),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0D,       geo_bitdw_0004B0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0E,       geo_bitdw_0004C8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0F,       geo_bitdw_0004E0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_10,       geo_bitdw_0004F8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_11,       geo_bitdw_000510),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_WARP_PIPE,         warp_pipe_geo),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_SQUARE_PLATFORM,   geo_bitdw_000558),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_SEESAW_PLATFORM,   geo_bitdw_000540),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_SLIDING_PLATFORM,  geo_bitdw_000528),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_FERRIS_WHEEL_AXLE, geo_bitdw_000570),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_BLUE_PLATFORM,     geo_bitdw_000588),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_STAIRCASE_FRAME4,  geo_bitdw_0005A0),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_STAIRCASE_FRAME3,  geo_bitdw_0005B8),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_STAIRCASE_FRAME2,  geo_bitdw_0005D0),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_STAIRCASE_FRAME1,  geo_bitdw_0005E8),
    LOAD_MODEL_FROM_GEO(MODEL_BITDW_STAIRCASE,         geo_bitdw_000600),

    AREA(/*index*/ 1, geo_bitdw_000618),
        OBJECT(/*model*/ MODEL_NONE,            /*pos*/ -7443, -2153, 3886, /*angle*/ 0, 90, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvAirborneWarp),
        OBJECT(/*model*/ MODEL_BITDW_WARP_PIPE, /*pos*/  6816,  2860,   -7, /*angle*/ 0, 0, 0,  /*behParam*/ 0x000B0000, /*beh*/ bhvWarpPipe),
        OBJECT(/*model*/ MODEL_NONE,            /*pos*/  5910,  3500,   -7, /*angle*/ 0, 90, 0, /*behParam*/ 0x000C0000, /*beh*/ bhvDeathWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_BITDW, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0B, /*destLevel*/ LEVEL_BOWSER_1, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0C, /*destLevel*/ LEVEL_BITDW, /*destArea*/ 0x01, /*destNode*/ 0x0C, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x01, /*destNode*/ 0x25, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        JUMP_LINK(script_func_local_3),
        TERRAIN(/*terrainData*/ bitdw_seg7_collision_level),
        MACRO_OBJECTS(/*objList*/ bitdw_seg7_macro_objs),
        SHOW_DIALOG(/*index*/ 0x00, DIALOG_090),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0000, /*seq*/ SEQ_LEVEL_KOOPA_ROAD),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_STONE),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 90, /*pos*/ -7443, -3153, 3886),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
