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
#include "levels/hmc/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 4936, -357, -4146, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 5018, -460, -5559, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 1997,  666,  -235, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 1762, -460, -2610, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 4178, -255, -3737, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 2233, -460,   256, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 5510, -255, -3429, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 4690, -357,  -767, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 3462, -255, -1125, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 1762,  666,     0, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 1762, -460,   256, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 6482,  461,  3226, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 1075,  461,  6543, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 6912,  461,  6543, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 6912,  461,  3697, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 6482,  461,  7014, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME, /*pos*/ 3817,  717,  1034, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_NONE,      /*pos*/  799, 1024,  4434, /*angle*/ 0, 0, 0, /*behParam*/ 0x00B80000, /*beh*/ bhvPoleGrabbing),
    OBJECT(/*model*/ MODEL_NONE,      /*pos*/  889, 1024,  3277, /*angle*/ 0, 0, 0, /*behParam*/ 0x00B80000, /*beh*/ bhvPoleGrabbing),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_HMC_METAL_PLATFORM,    /*pos*/  1100,   950,  6350, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvControllablePlatform),
    OBJECT(/*model*/ MODEL_HMC_ELEVATOR_PLATFORM, /*pos*/ -3243,  1434,  1392, /*angle*/ 0,  27, 0, /*behParam*/ 0x00000000, /*beh*/ bhvHmcElevatorPlatform),
    OBJECT(/*model*/ MODEL_HMC_ELEVATOR_PLATFORM, /*pos*/ -2816,  2253, -2509, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvHmcElevatorPlatform),
    OBJECT(/*model*/ MODEL_HMC_ELEVATOR_PLATFORM, /*pos*/  -973,  1741, -7347, /*angle*/ 0,   0, 0, /*behParam*/ 0x00020000, /*beh*/ bhvHmcElevatorPlatform),
    OBJECT(/*model*/ MODEL_HMC_ELEVATOR_PLATFORM, /*pos*/ -3533,  1741, -7040, /*angle*/ 0,   0, 0, /*behParam*/ 0x00030000, /*beh*/ bhvHmcElevatorPlatform),
    OBJECT(/*model*/ MODEL_NONE,                  /*pos*/   614, -4690,  2330, /*angle*/ 0, 270, 0, /*behParam*/ 0x00010000, /*beh*/ bhvOpenableGrill),
    OBJECT(/*model*/ MODEL_PURPLE_SWITCH,         /*pos*/  -307, -4997,  2483, /*angle*/ 0, 270, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFloorSwitchGrills),
    OBJECT(/*model*/ MODEL_CHECKERBOARD_PLATFORM, /*pos*/  1270,  2000,  4000, /*angle*/ 0, 270, 0, /*behParam*/ 0x09A40000, /*beh*/ bhvPlatformOnTrack),
    RETURN(),
};

static const LevelScript script_func_local_3[] = {
    OBJECT(/*model*/ MODEL_DORRIE, /*pos*/ -3533, -4969,  3558, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvDorrie),
    OBJECT(/*model*/ MODEL_NONE,   /*pos*/ -6093,  3075, -7807, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBigBoulderGenerator),
    OBJECT(/*model*/ MODEL_NONE,   /*pos*/  -500,  1600,  3500, /*angle*/ 0, 0, 0, /*behParam*/ 0x00040000, /*beh*/ bhvFlamethrower),
    OBJECT(/*model*/ MODEL_NONE,   /*pos*/  -500,  1600,  3800, /*angle*/ 0, 0, 0, /*behParam*/ 0x00040000, /*beh*/ bhvFlamethrower),
    RETURN(),
};

static const LevelScript script_func_local_4[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/ -3600, -4000,  3600, /*angle*/ 0, 0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE, /*pos*/  4000,   300,  5000, /*angle*/ 0, 0, 0, /*behParam*/ 0x01000000, /*beh*/ bhvHiddenRedCoinStar, /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/  6200, -4400,  2300, /*angle*/ 0, 0, 0, /*behParam*/ 0x02000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/ -2100,  2100, -7550, /*angle*/ 0, 0, 0, /*behParam*/ 0x03000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/ -6500,  2700, -1600, /*angle*/ 0, 0, 0, /*behParam*/ 0x04000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/ -5000,  3050, -6700, /*angle*/ 0, 0, 0, /*behParam*/ 0x05000000, /*beh*/ bhvStar,                 /*acts*/ ALL_ACTS),
    RETURN(),
};

const LevelScript level_hmc_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _hmc_segment_7SegmentRomStart, _hmc_segment_7SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _cave_mio0SegmentRomStart, _cave_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group6_mio0SegmentRomStart, _group6_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group6_geoSegmentRomStart, _group6_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group17_mio0SegmentRomStart, _group17_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group17_geoSegmentRomStart, _group17_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_1),
    JUMP_LINK(script_func_global_7),
    JUMP_LINK(script_func_global_18),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_WOODEN_DOOR,          wooden_door_geo),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_METAL_DOOR,           metal_door_geo),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_HAZY_MAZE_DOOR,       hazy_maze_door_geo),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_METAL_PLATFORM,       hmc_geo_0005A0),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_METAL_ARROW_PLATFORM, hmc_geo_0005B8),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_ELEVATOR_PLATFORM,    hmc_geo_0005D0),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_ROLLING_ROCK,         hmc_geo_000548),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_ROCK_PIECE,           hmc_geo_000570),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_ROCK_SMALL_PIECE,     hmc_geo_000588),
    LOAD_MODEL_FROM_GEO(MODEL_HMC_RED_GRILLS,           hmc_geo_000530),

    AREA(/*index*/ 1, hmc_geo_000B90),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ -7152,  3161, 7181, /*angle*/ 0, 135, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvSpinAirborneWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/  3351, -4690, 4773, /*angle*/ 0,   0, 0, /*behParam*/ 0x340B0000, /*beh*/ bhvWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_HMC, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0B, /*destLevel*/ LEVEL_COTMC, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x03, /*destNode*/ 0x34, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x03, /*destNode*/ 0x66, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        JUMP_LINK(script_func_local_3),
        JUMP_LINK(script_func_local_4),
        TERRAIN(/*terrainData*/ hmc_seg7_collision_level),
        MACRO_OBJECTS(/*objList*/ hmc_seg7_macro_objs),
        ROOMS(/*surfaceRooms*/ hmc_seg7_rooms),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0004, /*seq*/ SEQ_LEVEL_UNDERGROUND),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_STONE),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 135, /*pos*/ -7152, 2161, 7181),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
