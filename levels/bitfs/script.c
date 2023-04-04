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
#include "levels/bitfs/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_BITFS_PLATFORM_ON_TRACK,       /*pos*/ -5733, -3071,    0, /*angle*/ 0, 0, 0,   /*behParam*/ 0x07330000, /*beh*/ bhvPlatformOnTrack),
    OBJECT(/*model*/ MODEL_BITFS_TILTING_SQUARE_PLATFORM, /*pos*/ -1945, -3225, -715, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBitfsTiltingInvertedPyramid),
    OBJECT(/*model*/ MODEL_BITFS_TILTING_SQUARE_PLATFORM, /*pos*/ -2866, -3225, -715, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBitfsTiltingInvertedPyramid),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_PLATFORMS,       /*pos*/ -1381,  3487,   96, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBitfsSinkingPlatforms),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_PLATFORMS,       /*pos*/  1126, -3065,  307, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBitfsSinkingPlatforms),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_PLATFORMS,       /*pos*/ -3225,  3487,   96, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBitfsSinkingPlatforms),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_CAGE_PLATFORM,   /*pos*/  6605, -3071,  266, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBitfsSinkingCagePlatform),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_CAGE_PLATFORM,   /*pos*/  1843,  3584,   96, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00010000, /*beh*/ bhvBitfsSinkingCagePlatform),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_CAGE_PLATFORM,   /*pos*/   614,  3584,   96, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00010000, /*beh*/ bhvBitfsSinkingCagePlatform),
    OBJECT(/*model*/ MODEL_BITFS_SINKING_CAGE_PLATFORM,   /*pos*/  3072,  3584,   96, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00010000, /*beh*/ bhvBitfsSinkingCagePlatform),
    OBJECT(/*model*/ MODEL_BITFS_ELEVATOR,                /*pos*/  2867, -1279,  307, /*angle*/ 0, 0, 0,   /*behParam*/ 0x029F0000, /*beh*/ bhvActivatedBackAndForthPlatform),
    OBJECT(/*model*/ MODEL_BITFS_STRETCHING_PLATFORMS,    /*pos*/ -5836,   410,  300, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvSquishablePlatform),
    OBJECT(/*model*/ MODEL_BITFS_SEESAW_PLATFORM,         /*pos*/  4454, -2226,  266, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00040000, /*beh*/ bhvSeesawPlatform),
    OBJECT(/*model*/ MODEL_BITFS_SEESAW_PLATFORM,         /*pos*/  5786, -2380,  266, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00040000, /*beh*/ bhvSeesawPlatform),
    OBJECT(/*model*/ MODEL_BITFS_MOVING_SQUARE_PLATFORM,  /*pos*/ -3890,   102,  617, /*angle*/ 0, 90, 0,  /*behParam*/ 0x010C0000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITFS_MOVING_SQUARE_PLATFORM,  /*pos*/ -3276,   102,    2, /*angle*/ 0, 270, 0, /*behParam*/ 0x010C0000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITFS_SLIDING_PLATFORM,        /*pos*/  2103,   198,  312, /*angle*/ 0, 0, 0,   /*behParam*/ 0x019F0000, /*beh*/ bhvSlidingPlatform2),
    OBJECT(/*model*/ MODEL_BITFS_TUMBLING_PLATFORM,       /*pos*/  4979,  4250,   96, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00030000, /*beh*/ bhvWfTumblingBridge),
    OBJECT(/*model*/ MODEL_NONE,                          /*pos*/  3890, -2043,  266, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00520000, /*beh*/ bhvPoleGrabbing),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_NONE, /*pos*/ -3226, 3584, -822, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    OBJECT(/*model*/ MODEL_NONE, /*pos*/ -1382, 3584, -822, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    OBJECT(/*model*/ MODEL_NONE, /*pos*/  1229,  307, -412, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlamethrower),
    RETURN(),
};

static const LevelScript script_func_local_3[] = {
    OBJECT(/*model*/ MODEL_NONE, /*pos*/  1200, 5700,  160, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBowserCourseRedCoinStar),
    RETURN(),
};

const LevelScript level_bitfs_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _bitfs_segment_7SegmentRomStart, _bitfs_segment_7SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _sky_mio0SegmentRomStart, _sky_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0A, _bitfs_skybox_mio0SegmentRomStart, _bitfs_skybox_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0B, _effect_mio0SegmentRomStart, _effect_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group2_mio0SegmentRomStart, _group2_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group2_geoSegmentRomStart, _group2_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group17_mio0SegmentRomStart, _group17_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group17_geoSegmentRomStart, _group17_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart, _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_3),
    JUMP_LINK(script_func_global_18),
    JUMP_LINK(script_func_global_1),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_03,             bitfs_geo_0004B0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_04,             bitfs_geo_0004C8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_05,             bitfs_geo_0004E0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_06,             bitfs_geo_0004F8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_07,             bitfs_geo_000510),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_08,             bitfs_geo_000528),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_09,             bitfs_geo_000540),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0A,             bitfs_geo_000558),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0B,             bitfs_geo_000570),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0C,             bitfs_geo_000588),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0D,             bitfs_geo_0005A0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0E,             bitfs_geo_0005B8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_0F,             bitfs_geo_0005D0),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_10,             bitfs_geo_0005E8),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_11,             bitfs_geo_000600),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_12,             bitfs_geo_000618),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_13,             bitfs_geo_000630),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_14,             bitfs_geo_000648),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_15,             bitfs_geo_000660),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_PLATFORM_ON_TRACK,       bitfs_geo_000758),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_TILTING_SQUARE_PLATFORM, bitfs_geo_0006C0),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_SINKING_PLATFORMS,       bitfs_geo_000770),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_BLUE_POLE,               bitfs_geo_0006A8),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_SINKING_CAGE_PLATFORM,   bitfs_geo_000690),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_ELEVATOR,                bitfs_geo_000678),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_STRETCHING_PLATFORMS,    bitfs_geo_000708),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_SEESAW_PLATFORM,         bitfs_geo_000788),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_MOVING_SQUARE_PLATFORM,  bitfs_geo_000728),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_SLIDING_PLATFORM,        bitfs_geo_000740),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_TUMBLING_PLATFORM_PART,  bitfs_geo_0006D8),
    LOAD_MODEL_FROM_GEO(MODEL_BITFS_TUMBLING_PLATFORM,       bitfs_geo_0006F0),

    AREA(/*index*/ 1, bitfs_geo_0007A0),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ -7577, -1764,  0, /*angle*/ 0, 90, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvAirborneWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/  6735,  3681, 99, /*angle*/ 0, 0, 0,  /*behParam*/ 0x140B0000, /*beh*/ bhvWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/  5886,  5000, 99, /*angle*/ 0, 90, 0, /*behParam*/ 0x000C0000, /*beh*/ bhvDeathWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_BITFS, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0B, /*destLevel*/ LEVEL_BOWSER_2, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0C, /*destLevel*/ LEVEL_BITFS, /*destArea*/ 0x01, /*destNode*/ 0x0C, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x03, /*destNode*/ 0x68, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        JUMP_LINK(script_func_local_3),
        TERRAIN(/*terrainData*/ bitfs_seg7_collision_level),
        MACRO_OBJECTS(/*objList*/ bitfs_seg7_macro_objs),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0000, /*seq*/ SEQ_LEVEL_KOOPA_ROAD),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_STONE),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 90, /*pos*/ -7577, -2764, 0),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
