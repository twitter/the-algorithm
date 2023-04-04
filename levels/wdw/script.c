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
#include "levels/wdw/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_WDW_SQUARE_FLOATING_PLATFORM,      /*pos*/  3390,    0,   384, /*angle*/ 0, 180, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWdwSquareFloatingPlatform),
    OBJECT(/*model*/ MODEL_WDW_SQUARE_FLOATING_PLATFORM,      /*pos*/  -767,  384,  3584, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWdwSquareFloatingPlatform),
    OBJECT(/*model*/ MODEL_WDW_SQUARE_FLOATING_PLATFORM,      /*pos*/  -767,  384,  1536, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWdwSquareFloatingPlatform),
    OBJECT(/*model*/ MODEL_WDW_SQUARE_FLOATING_PLATFORM,      /*pos*/  -767, 2304, -1279, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWdwSquareFloatingPlatform),
    OBJECT(/*model*/ MODEL_WDW_ARROW_LIFT,                    /*pos*/  -578, 2177,  3009, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvArrowLift),
    OBJECT(/*model*/ MODEL_WDW_ARROW_LIFT,                    /*pos*/ -1474, 2177,  3393, /*angle*/ 0, 270, 0, /*behParam*/ 0x00000000, /*beh*/ bhvArrowLift),
    OBJECT(/*model*/ MODEL_WDW_ARROW_LIFT,                    /*pos*/ -1602, 2177,  3009, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvArrowLift),
    OBJECT(/*model*/ MODEL_WDW_ARROW_LIFT,                    /*pos*/ -1090, 2177,  3521, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvArrowLift),
    OBJECT(/*model*/ MODEL_WDW_ARROW_LIFT,                    /*pos*/  -962, 2177,  3137, /*angle*/ 0,  90, 0, /*behParam*/ 0x00000000, /*beh*/ bhvArrowLift),
    OBJECT(/*model*/ MODEL_NONE,                              /*pos*/     0,    0,     0, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvInitializeChangingWaterLevel),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND,           /*pos*/  1920, 2560, -3583, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND,           /*pos*/  3328,  256,  2918, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND,           /*pos*/  2048, 1792,  2176, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND,           /*pos*/   640, 1024,  3712, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND,           /*pos*/  1810,   40, -3118, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_PURPLE_SWITCH,                     /*pos*/  3360, 1280,  3420, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFloorSwitchHiddenObjects),
    OBJECT(/*model*/ MODEL_WDW_HIDDEN_PLATFORM,               /*pos*/  2239, 1126,  3391, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvHiddenObject),
    OBJECT(/*model*/ MODEL_WDW_HIDDEN_PLATFORM,               /*pos*/  1215, 1357,  2751, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvHiddenObject),
    OBJECT(/*model*/ MODEL_WDW_HIDDEN_PLATFORM,               /*pos*/  1215, 1229,  3391, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvHiddenObject),
    OBJECT(/*model*/ MODEL_WDW_HIDDEN_PLATFORM,               /*pos*/  1599, 1101,  3391, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvHiddenObject),
    OBJECT(/*model*/ MODEL_WDW_HIDDEN_PLATFORM,               /*pos*/  2879, 1152,  3391, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvHiddenObject),
    OBJECT(/*model*/ MODEL_WDW_EXPRESS_ELEVATOR,              /*pos*/  1024, 3277, -2112, /*angle*/ 0,   0, 0, /*behParam*/ 0x00320000, /*beh*/ bhvWdwExpressElevatorPlatform),
    OBJECT(/*model*/ MODEL_WDW_EXPRESS_ELEVATOR,              /*pos*/  1024, 3277, -1663, /*angle*/ 0,   0, 0, /*behParam*/ 0x00320000, /*beh*/ bhvWdwExpressElevator),
    OBJECT(/*model*/ MODEL_WDW_RECTANGULAR_FLOATING_PLATFORM, /*pos*/  -767, 1152,   128, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWdwRectangularFloatingPlatform),
    OBJECT(/*model*/ MODEL_WDW_RECTANGULAR_FLOATING_PLATFORM, /*pos*/  -767, 2304, -2687, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWdwRectangularFloatingPlatform),
    OBJECT(/*model*/ MODEL_WDW_ROTATING_PLATFORM,             /*pos*/   734, 3840,    84, /*angle*/ 0,   0, 0, /*behParam*/ 0x46010000, /*beh*/ bhvRotatingPlatform),
    OBJECT(/*model*/ MODEL_SKEETER,                           /*pos*/  2956,  288,  -468, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvSkeeter),
    OBJECT(/*model*/ MODEL_SKEETER,                           /*pos*/   184,  384,   621, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvSkeeter),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_NONE,                    /*pos*/     0,     0,     0, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvInitializeChangingWaterLevel),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND, /*pos*/ -3583, -2508, -2047, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_WDW_WATER_LEVEL_DIAMOND, /*pos*/  -767,  -127,  1792, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvWaterLevelDiamond),
    OBJECT(/*model*/ MODEL_NONE,                    /*pos*/  -768,  -665,  3584, /*angle*/ 0, 0, 0, /*behParam*/ 0x005C0000, /*beh*/ bhvPoleGrabbing),
    RETURN(),
};

static const LevelScript script_func_local_3[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE, /*pos*/ 3360,  1580,  2660, /*angle*/ 0, 0, 0, /*behParam*/ 0x02000000, /*beh*/ bhvHiddenStar,          /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/  890,  3400, -2040, /*angle*/ 0, 0, 0, /*behParam*/ 0x03000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    RETURN(),
};

static const LevelScript script_func_local_4[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE, /*pos*/ -770, -1600,  3600, /*angle*/ 0, 0, 0, /*behParam*/ 0x04000000, /*beh*/ bhvHiddenRedCoinStar, /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/ 2180,  -840,  3720, /*angle*/ 0, 0, 0, /*behParam*/ 0x05000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    RETURN(),
};

const LevelScript level_wdw_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _wdw_segment_7SegmentRomStart, _wdw_segment_7SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _grass_mio0SegmentRomStart, _grass_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0A, _wdw_skybox_mio0SegmentRomStart, _wdw_skybox_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group1_mio0SegmentRomStart, _group1_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group1_geoSegmentRomStart,  _group1_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group13_mio0SegmentRomStart, _group13_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group13_geoSegmentRomStart,  _group13_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_1),
    JUMP_LINK(script_func_global_2),
    JUMP_LINK(script_func_global_14),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_BUBBLY_TREE,                   bubbly_tree_geo),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_SQUARE_FLOATING_PLATFORM,      wdw_geo_000580),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_ARROW_LIFT,                    wdw_geo_000598),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_WATER_LEVEL_DIAMOND,           wdw_geo_0005C0),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_HIDDEN_PLATFORM,               wdw_geo_0005E8),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_EXPRESS_ELEVATOR,              wdw_geo_000610),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_RECTANGULAR_FLOATING_PLATFORM, wdw_geo_000628),
    LOAD_MODEL_FROM_GEO(MODEL_WDW_ROTATING_PLATFORM,             wdw_geo_000640),

    AREA(/*index*/ 1, wdw_geo_000658),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/  3395, 3580,  384, /*angle*/ 0, 180, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvSpinAirborneWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/   818,    0, 3634, /*angle*/ 0,  45, 0, /*behParam*/ 0x000B0000, /*beh*/ bhvFadingWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ -2865, 3328, 3065, /*angle*/ 0,   0, 0, /*behParam*/ 0x000C0000, /*beh*/ bhvFadingWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_WDW, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0B, /*destLevel*/ LEVEL_WDW, /*destArea*/ 0x01, /*destNode*/ 0x0C, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0C, /*destLevel*/ LEVEL_WDW, /*destArea*/ 0x01, /*destNode*/ 0x0B, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_3),
        JUMP_LINK(script_func_local_1),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x32, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x64, /*flags*/ WARP_NO_CHECKPOINT),
        INSTANT_WARP(/*index*/ 1, /*destArea*/ 2, /*displace*/ 0, 0, 0),
        TERRAIN(/*terrainData*/ wdw_seg7_area_1_collision),
        MACRO_OBJECTS(/*objList*/ wdw_seg7_area_1_macro_objs),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0003, /*seq*/ SEQ_LEVEL_UNDERGROUND),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_STONE),
    END_AREA(),

    AREA(/*index*/ 2, wdw_geo_000724),
        JUMP_LINK(script_func_local_4),
        JUMP_LINK(script_func_local_2),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x32, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x02, /*destNode*/ 0x64, /*flags*/ WARP_NO_CHECKPOINT),
        INSTANT_WARP(/*index*/ 0, /*destArea*/ 1, /*displace*/ 0, 0, 0),
        TERRAIN(/*terrainData*/ wdw_seg7_area_2_collision),
        MACRO_OBJECTS(/*objList*/ wdw_seg7_area_2_macro_objs),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0003, /*seq*/ SEQ_LEVEL_UNDERGROUND),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_WATER),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 180, /*pos*/ 3395, 2580, 384),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
