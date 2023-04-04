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
#include "levels/ccm/header.h"

static const LevelScript script_func_local_1[] = {
    OBJECT(/*model*/ MODEL_CCM_ROPEWAY_LIFT, /*pos*/ 531, -4430, 6426,    /*angle*/ 0,   0, 0, /*behParam*/ 0x07120000, /*beh*/ bhvPlatformOnTrack),
    RETURN(),
};

static const LevelScript script_func_local_2[] = {
    OBJECT(/*model*/ MODEL_PENGUIN,          /*pos*/  2650, -3735,  3970, /*angle*/ 0,   0, 0, /*behParam*/ 0x00010000, /*beh*/ bhvSmallPenguin),
    OBJECT(/*model*/ MODEL_PENGUIN,          /*pos*/  -555,  3470, -1000, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvSmallPenguin),
    OBJECT(/*model*/ MODEL_MR_BLIZZARD,      /*pos*/ -2376, -1589,  4256, /*angle*/ 0, 252, 0, /*behParam*/ 0x00010000, /*beh*/ bhvMrBlizzard),
    OBJECT(/*model*/ MODEL_MR_BLIZZARD,      /*pos*/  -394, -1589,  4878, /*angle*/ 0,  74, 0, /*behParam*/ 0x00010000, /*beh*/ bhvMrBlizzard),
    OBJECT_WITH_ACTS(/*model*/ MODEL_CCM_SNOWMAN_BASE, /*pos*/  2560,  2662, -1122, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvSnowmansBottom, /*acts*/ ACT_5),
    RETURN(),
};

static const LevelScript script_func_local_3[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE,             /*pos*/  2665, -4607,  4525, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvCcmTouchedStarSpawn, /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_PENGUIN,          /*pos*/  3450, -4700,  4550, /*angle*/ 0,   0, 0, /*behParam*/ 0x01000000, /*beh*/ bhvTuxiesMother,          /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_NONE,             /*pos*/  4200,  -927,   400, /*angle*/ 0,   0, 0, /*behParam*/ 0x03000000, /*beh*/ bhvHiddenRedCoinStar,   /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_CCM_SNOWMAN_HEAD, /*pos*/ -4230, -1169,  1813, /*angle*/ 0, 270, 0, /*behParam*/ 0x04000000, /*beh*/ bhvSnowmansHead,          /*acts*/ ALL_ACTS),
    OBJECT_WITH_ACTS(/*model*/ MODEL_STAR,             /*pos*/ -2000, -2200, -3000, /*angle*/ 0,   0, 0, /*behParam*/ 0x05000000, /*beh*/ bhvStar,                   /*acts*/ ALL_ACTS),
    RETURN(),
};

static const LevelScript script_func_local_4[] = {
    OBJECT_WITH_ACTS(/*model*/ MODEL_PENGUIN, /*pos*/ -4952,  6656, -6075, /*angle*/ 0, 270, 0, /*behParam*/ 0x02000000, /*beh*/ bhvRacingPenguin,   /*acts*/ ACT_2 | ACT_3 | ACT_4 | ACT_5 | ACT_6),
    OBJECT(/*model*/ MODEL_NONE,    /*pos*/ -6500, -5836, -6400, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvPenguinRaceFinishLine),
    OBJECT(/*model*/ MODEL_NONE,    /*pos*/ -6393,  -716,  7503, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvPenguinRaceShortcutCheck),
#ifndef VERSION_JP
    OBJECT(/*model*/ MODEL_NONE,    /*pos*/ -4943,  1321,   667, /*angle*/ 0,   0, 0, /*behParam*/ 0x00000000, /*beh*/ bhvPlaysMusicTrackWhenTouched),
#endif
    RETURN(),
};

const LevelScript level_ccm_entry[] = {
    INIT_LEVEL(),
    LOAD_MIO0(        /*seg*/ 0x07, _ccm_segment_7SegmentRomStart, _ccm_segment_7SegmentRomEnd),
    LOAD_MIO0_TEXTURE(/*seg*/ 0x09, _snow_mio0SegmentRomStart, _snow_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0B, _effect_mio0SegmentRomStart, _effect_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x0A, _ccm_skybox_mio0SegmentRomStart, _ccm_skybox_mio0SegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x05, _group7_mio0SegmentRomStart, _group7_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0C, _group7_geoSegmentRomStart,  _group7_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x06, _group16_mio0SegmentRomStart, _group16_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0D, _group16_geoSegmentRomStart,  _group16_geoSegmentRomEnd),
    LOAD_MIO0(        /*seg*/ 0x08, _common0_mio0SegmentRomStart, _common0_mio0SegmentRomEnd),
    LOAD_RAW(         /*seg*/ 0x0F, _common0_geoSegmentRomStart,  _common0_geoSegmentRomEnd),
    ALLOC_LEVEL_POOL(),
    MARIO(/*model*/ MODEL_MARIO, /*behParam*/ 0x00000001, /*beh*/ bhvMario),
    JUMP_LINK(script_func_global_1),
    JUMP_LINK(script_func_global_8),
    JUMP_LINK(script_func_global_17),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_03, ccm_geo_00042C),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_04, ccm_geo_00045C),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_05, ccm_geo_000494),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_06, ccm_geo_0004BC),
    LOAD_MODEL_FROM_GEO(MODEL_LEVEL_GEOMETRY_07, ccm_geo_0004E4),
    LOAD_MODEL_FROM_GEO(MODEL_CCM_CABIN_DOOR,    cabin_door_geo),
    LOAD_MODEL_FROM_GEO(MODEL_CCM_SNOW_TREE,     snow_tree_geo),
    LOAD_MODEL_FROM_GEO(MODEL_CCM_ROPEWAY_LIFT,  ccm_geo_0003D0),
    LOAD_MODEL_FROM_GEO(MODEL_CCM_SNOWMAN_BASE,  ccm_geo_0003F0),
    LOAD_MODEL_FROM_GEO(MODEL_CCM_SNOWMAN_HEAD,  ccm_geo_00040C),

    AREA(/*index*/ 1, ccm_geo_00051C),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ -1512,  3560, -2305, /*angle*/ 0,  140, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvSpinAirborneWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/  -181,  2918, -1486, /*angle*/ 0,    0, 0, /*behParam*/ 0x0F1E0000, /*beh*/ bhvWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ -1847,  2815,  -321, /*angle*/ 0, -158, 0, /*behParam*/ 0x001F0000, /*beh*/ bhvFadingWarp),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/  3349, -4694,  -183, /*angle*/ 0,  -34, 0, /*behParam*/ 0x00200000, /*beh*/ bhvFadingWarp),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x01, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x14, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x02, /*destNode*/ 0x14, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x1E, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x02, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x1F, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x01, /*destNode*/ 0x20, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x20, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x01, /*destNode*/ 0x1F, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x01, /*destNode*/ 0x33, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x01, /*destNode*/ 0x65, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_1),
        JUMP_LINK(script_func_local_2),
        JUMP_LINK(script_func_local_3),
        TERRAIN(/*terrainData*/ ccm_seg7_area_1_collision),
        MACRO_OBJECTS(/*objList*/ ccm_seg7_area_1_macro_objs),
        SHOW_DIALOG(/*index*/ 0x00, DIALOG_048),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0000, /*seq*/ SEQ_LEVEL_SNOW),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_SNOW),
    END_AREA(),

    AREA(/*index*/ 2, ccm_geo_0005E8),
        OBJECT(/*model*/ MODEL_NONE, /*pos*/ -5836, 7465, -6143, /*angle*/ 0, 90, 0, /*behParam*/ 0x000A0000, /*beh*/ bhvAirborneWarp),
        WARP_NODE(/*id*/ 0x14, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x01, /*destNode*/ 0x14, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0x0A, /*destLevel*/ LEVEL_CCM, /*destArea*/ 0x02, /*destNode*/ 0x0A, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF0, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x01, /*destNode*/ 0x33, /*flags*/ WARP_NO_CHECKPOINT),
        WARP_NODE(/*id*/ 0xF1, /*destLevel*/ LEVEL_CASTLE, /*destArea*/ 0x01, /*destNode*/ 0x65, /*flags*/ WARP_NO_CHECKPOINT),
        JUMP_LINK(script_func_local_4),
        TERRAIN(/*terrainData*/ ccm_seg7_area_2_collision),
        MACRO_OBJECTS(/*objList*/ ccm_seg7_area_2_macro_objs),
        SET_BACKGROUND_MUSIC(/*settingsPreset*/ 0x0001, /*seq*/ SEQ_LEVEL_SLIDE),
        TERRAIN_TYPE(/*terrainType*/ TERRAIN_SLIDE),
    END_AREA(),

    FREE_LEVEL_POOL(),
    MARIO_POS(/*area*/ 1, /*yaw*/ 140, /*pos*/ -1512, 2560, -2305),
    CALL(/*arg*/ 0, /*func*/ lvl_init_or_update),
    CALL_LOOP(/*arg*/ 1, /*func*/ lvl_init_or_update),
    CLEAR_LEVEL(),
    SLEEP_BEFORE_EXIT(/*frames*/ 1),
    EXIT(),
};
