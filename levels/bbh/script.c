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
#include "levels/bbh/header.h"


static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_RED_FLAME,                  /*pos*/  2089,  1331, -1125, /*angle*/ 0, 270, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME,                  /*pos*/  1331,  1075, -1330, /*angle*/ 0, 90, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME,                  /*pos*/  2089,  1331,  -511, /*angle*/ 0, 270, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME,                  /*pos*/  -511,   358, -1330, /*angle*/ 0, 90, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME,                  /*pos*/  1126,   358,  2212, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    OBJECT(/*model*/ MODEL_RED_FLAME,                  /*pos*/   205,   358,  2212, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvFlame),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_BBH_TILTING_FLOOR_PLATFORM, /*pos*/  2866,   820,  1897, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBbhTiltingTrapPlatform),
    OBJECT(/*model*/ MODEL_BBH_TUMBLING_PLATFORM,      /*pos*/  2961,     0,  -768, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBbhTumblingBridge),
    OBJECT(/*model*/ MODEL_BBH_MOVING_BOOKSHELF,       /*pos*/ -1994,   819,   213, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvHauntedBookshelf),
    OBJECT(/*model*/ MODEL_BBH_MESH_ELEVATOR,          /*pos*/ -2985,  -205,  5400, /*angle*/ 0, -45, 0, /*behParam*/ 0x00000000, /*beh*/ bhvMeshElevator),
    OBJECT(/*model*/ MODEL_BBH_MERRY_GO_ROUND,         /*pos*/  -205, -2560,   205, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvMerryGoRound),
    OBJECT(/*model*/ MODEL_NONE,                       /*pos*/  2200,   819,  -800, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvCoffinSpawner),
    RETURN(),
};

static const LevelScript script_func_local_3[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/  1000,    50,  1000, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvGhostHuntBigBoo,       /*acts*/ ACT_1),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/    20,   100,  -908, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvGhostHuntBoo,                 /*acts*/ ACT_1),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/  3150,   100,   398, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvGhostHuntBoo,                 /*acts*/ ACT_1),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/ -2000,   150,  -800, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvGhostHuntBoo,                 /*acts*/ ACT_1),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/  2851,   100,  2289, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvGhostHuntBoo,                 /*acts*/ ACT_1),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/ -1551,   100, -1018, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvGhostHuntBoo,                 /*acts*/ ACT_1),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BBH_STAIRCASE_STEP,         /*pos*/   973,     0,   517, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvHiddenStaircaseStep, /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BBH_STAIRCASE_STEP,         /*pos*/   973,  -206,   717, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvHiddenStaircaseStep, /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BBH_STAIRCASE_STEP,         /*pos*/   973,  -412,   917, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvHiddenStaircaseStep, /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/    20,   100,  -908, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBoo,                 /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/  3150,   100,   398, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBoo,                 /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/ -2000,   150,  -800, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBoo,                 /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/  2851,   100,  2289, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBoo,                 /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/ -1551,   100, -1018, /*angle*/ 0, 0, 0,   /*behParam*/ 0x00000000, /*beh*/ bhvBoo,                 /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE,                       /*pos*/   990, -2146,  -908, /*angle*/ 0, -45, 0, /*behParam*/ 0x00030000, /*beh*/ bhvFlamethrower,          /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE,                       /*pos*/ -1100, -2372,  1100, /*angle*/ 0, 135, 0, /*behParam*/ 0x01000000, /*beh*/ bhvMerryGoRoundBooManager,         /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/  1030,  1922,  2546, /*angle*/ 0, -90, 0, /*behParam*/ 0x04000000, /*beh*/ bhvBalconyBigBoo,        /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_BOO,                        /*pos*/   581,  1850,  -206, /*angle*/ 0, -90, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBoo,                 /*acts*/ ALL_ACTS),
    OBJECT(/*model*/ MODEL_MAD_PIANO,                  /*pos*/ -1300,     0,  2310, /*angle*/ 0, 243, 0, /*behParam*/ 0x00000000, /*beh*/ bhvMadPiano),
    OBJECT(/*model*/ MODEL_HAUNTED_CHAIR,              /*pos*/ -1530,     0,  2200, /*angle*/ 0, 66, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvHauntedChair),
    OBJECT(/*model*/ MODEL_NONE,                       /*pos*/ -1330,   890,   200, /*angle*/ 0, 90, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvBookendSpawn),
    OBJECT(/*model*/ MODEL_NONE,                       /*pos*/  -818,   890,  -200, /*angle*/ 0, 270, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBookendSpawn),
    OBJECT(/*model*/ MODEL_NONE,                       /*pos*/ -1330,   890,  -622, /*angle*/ 0, 90, 0,  /*behParam*/ 0x00000000, /*beh*/ bhvBookendSpawn),
    OBJECT(/*model*/ MODEL_NONE,                       /*pos*/  -818,   890,  -686, /*angle*/ 0, 270, 0, /*behParam*/ 0x00000000, /*beh*/ bhvBookendSpawn),
    OBJECT(/*model*/ MODEL_NONE,                       /*pos*/ -1950,   880,     8, /*angle*/ 0, 180, 0, /*behParam*/ 0x00000000, /*beh*/ bhvHauntedBookshelfManager),
    OBJECT(/*model*/ MODEL_BOOKEND,                    /*pos*/  2680,  1045,   876, /*angle*/ 0, 166, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlyingBookend),
    OBJECT(/*model*/ MODEL_BOOKEND,                    /*pos*/  3075,  1045,   995, /*angle*/ 0, 166, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlyingBookend),
    OBJECT(/*model*/ MODEL_BOOKEND,                    /*pos*/ -1411,   218,   922, /*angle*/ 0, 180, 0, /*behParam*/ 0x00000000, /*beh*/ bhvFlyingBookend),
    RETURN(),
};

static const LevelScript script_func_local_4[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR, /*pos*/ -2030, 1350,  1940, /*angle*/ 0, 0, 0,  /*behParam*/ 0x02000000, /*beh*/ bhvStar,                    /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE, /*pos*/  -204, 1100,  1576, /*angle*/ 0, 0, 0,  /*behParam*/ 0x03000000, /*beh*/ bhvHiddenRedCoinStar,    /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE, /*pos*/   923, 1741,  -332, /*angle*/ 0, 18, 0, /*behParam*/ 0x05010000, /*beh*/ bhvMrI,                    /*acts*/ ALL_ACTS),
    RETURN(),
};

const LevelScript level_bbh_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _bbh_segment_7SegmentRomStart, _bbh_segment_7SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0A, _bbh_skybox_mio0SegmentRomStart, _bbh_skybox_mio0SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _spooky_mio0SegmentRomStart, _spooky_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group9_mio0SegmentRomStart, _group9_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group9_geoSegmentRomStart,  _group9_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group17_mio0SegmentRomStart, _group17_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group17_geoSegmentRomStart, _group17_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_1),
    JUMP_LINK(script_func_global_10),
    JUMP_LINK(script_func_global_18),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_HAUNTED_DOOR,           haunted_door_geo),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_STAIRCASE_STEP,         geo_bbh_0005B0),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_TILTING_FLOOR_PLATFORM, geo_bbh_0005C8),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_TUMBLING_PLATFORM,      geo_bbh_0005E0),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_TUMBLING_PLATFORM_PART, geo_bbh_0005F8),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_MOVING_BOOKSHELF,       geo_bbh_000610),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_MESH_ELEVATOR,          geo_bbh_000628),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_MERRY_GO_ROUND,         geo_bbh_000640),
    LOAD_MODEL_FROM_GEO(MODEL_BBH_WOODEN_TOMB,            geo_bbh_000658),

    AREA(/*index*/ 1, geo_bbh_000F00),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        JUMP_LINK(script_func_local_3),
        JUMP_LINK(script_func_local_4),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ 666, 796, 5350, /*angle*/ 0, 180, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvSpinAirborneWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_BBH, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE_COURTYARD, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE_COURTYARD, /*destArea*/ 0x01, /*destNode*/ 0x0B, /*flags*/ WARP_NO_CHECKPOINT),
        TERRAIN(/*terrainData*/ bbh_seg7_collision_level),
        MACRO_OBJECTS(/*objList*/ bbh_seg7_macro_objs),
        ROOMS(/*surfaceRooms*/ bbh_seg7_rooms),
        SHOW_DIALOG(/*index*/ 0x00, DIALOG_098),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0006, /*seq*/ SEQ_LEVEL_SPOOKY),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_SPOOKY),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 180, /*pos*/ 666, -204, 5350),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
