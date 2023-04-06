#ifndef _SPECIAL_PRESETS_H
#define _SPECIAL_PRESETS_H

#include "special_preset_names.h"
#include "behavior_data.h"
#include "model_ids.h"

// Special Preset types
#define SPTYPE_NO_YROT_OR_PARAMS  0 // object is 8-bytes long, no y-rotation or any behavior params
#define SPTYPE_YROT_NO_PARAMS     1 // object is 10-bytes long, has y-rotation but no params
#define SPTYPE_PARAMS_AND_YROT    2 // object is 12-bytes long, has y-rotation and params
#define SPTYPE_UNKNOWN            3 // object is 14-bytes long, has 3 extra shorts that get converted to floats.
#define SPTYPE_DEF_PARAM_AND_YROT 4 // object is 10-bytes long, has y-rotation and uses the default param

struct SpecialPreset
{
    /*00*/ u8  preset_id;
    /*01*/ u8  type;      // Determines whether object is 8, 10, 12 or 14 bytes long.
    /*02*/ u8  defParam;  // Default parameter, only used when type is SPTYPE_DEF_PARAM_AND_YROT
    /*03*/ u8  model;     
    /*04*/ const BehaviorScript *behavior;
};

// Some Models ID's are missing their names because they are probably unused

static struct SpecialPreset SpecialObjectPresets[] = 
{
    {0x00, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_NONE, NULL},
    {0x01, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_YELLOW_COIN, bhvYellowCoin},
    {0x02, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_YELLOW_COIN, bhvYellowCoin},
    {0x03, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_UNKNOWN_B8, bhvStaticObject},
    {0x04, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_BOO, bhvCourtyardBooTriplet},
    {0x05, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_UNKNOWN_AC, bhvCastleFloorTrap},
    {0x06, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_LLL_MOVING_OCTAGONAL_MESH_PLATFORM, bhvLllMovingOctagonalMeshPlatform},
    {0x07, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_CCM_SNOWMAN_HEAD, bhvSnowBall},
    {0x08, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LLL_DRAWBRIDGE_PART, bhvLllDrawbridgeSpawner},
    {0x09, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x0A, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_LLL_ROTATING_BLOCK_FIRE_BARS, bhvLllRotatingBlockWithFireBars},
    {0x0B, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvLllFloatingWoodBridge},
    {0x0C, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvLllTumblingBridge},
    {0x0D, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_LLL_ROTATING_HEXAGONAL_RING , bhvLllRotatingHexagonalRing},
    {0x0E, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LLL_SINKING_RECTANGULAR_PLATFORM, bhvLllSinkingRectangularPlatform},
    {0x0F, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_LLL_SINKING_SQUARE_PLATFORMS, bhvLllSinkingSquarePlatforms},
    {0x10, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_LLL_TILTING_SQUARE_PLATFORM, bhvLllTiltingInvertedPyramid},
    {0x11, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvLllBowserPuzzle},
    {0x12, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvMrI},
    {0x13, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_BULLY, bhvSmallBully},
    {0x14, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_BULLY_BOSS, bhvBigBully},
    {0x15, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x16, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x17, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x18, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x19, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x1A, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_YELLOW_COIN, bhvMovingBlueCoin},
    {0x1B, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_TREASURE_CHEST_BASE, bhvBetaChestBottom},
    {0x1C, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_WATER_RING, bhvJetStreamRingSpawner},
    {0x1D, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_WATER_MINE, bhvBowserBomb},
    {0x1E, SPTYPE_UNKNOWN           , 0x00, MODEL_NONE, bhvStaticObject},
    {0x1F, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvStaticObject},
    {0x20, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_BUTTERFLY, bhvButterfly},
    {0x21, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_BOWSER, bhvBowser},
    {0x22, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_WF_ROTATING_WOODEN_PLATFORM, bhvWfRotatingWoodenPlatform},
    {0x23, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_WF_SMALL_BOMP, bhvSmallBomp},
    {0x24, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_WF_SLIDING_PLATFORM, bhvWfSlidingPlatform},
    {0x25, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvTowerPlatformGroup},
    {0x26, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, bhvRotatingCounterClockwise},
    {0x27, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_WF_TUMBLING_BRIDGE, bhvWfTumblingBridge},
    {0x28, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_WF_LARGE_BOMP, bhvLargeBomp},
    {0x65, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_03, bhvStaticObject},
    {0x66, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_04, bhvStaticObject},
    {0x67, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_05, bhvStaticObject},
    {0x68, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_06, bhvStaticObject},
    {0x69, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_07, bhvStaticObject},
    {0x6A, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_08, bhvStaticObject},
    {0x6B, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_09, bhvStaticObject},
    {0x6C, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_0A, bhvStaticObject},
    {0x6D, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_0B, bhvStaticObject},
    {0x6E, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_0C, bhvStaticObject},
    {0x6F, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_0D, bhvStaticObject},
    {0x70, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_0E, bhvStaticObject},
    {0x71, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_0F, bhvStaticObject},
    {0x72, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_10, bhvStaticObject},
    {0x73, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_11, bhvStaticObject},
    {0x74, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_12, bhvStaticObject},
    {0x75, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_13, bhvStaticObject},
    {0x76, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_14, bhvStaticObject},
    {0x77, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_15, bhvStaticObject},
    {0x78, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_LEVEL_GEOMETRY_16, bhvStaticObject},
    {0x79, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_BOB_BUBBLY_TREE, bhvTree},
    {0x7A, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_COURTYARD_SPIKY_TREE, bhvTree},
    {0x7B, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_CCM_SNOW_TREE, bhvTree},
    {0x7C, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_UNKNOWN_TREE_1A, bhvTree},
    {0x7D, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_SSL_PALM_TREE, bhvTree},
    {0x89, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_CASTLE_CASTLE_DOOR_UNUSED, bhvDoor},
    {0x7E, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_CASTLE_WOODEN_DOOR_UNUSED, bhvDoor},
    {0x7F, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_UNKNOWN_DOOR_1E, bhvDoor},
    {0x80, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_HMC_METAL_DOOR, bhvDoor},
    {0x81, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_HMC_HAZY_MAZE_DOOR, bhvDoor},
    {0x82, SPTYPE_YROT_NO_PARAMS    , 0x00, MODEL_UNKNOWN_DOOR_21, bhvDoor},
    {0x8A, SPTYPE_DEF_PARAM_AND_YROT, 0x00, MODEL_CASTLE_DOOR_0_STARS, bhvDoor},
    {0x8B, SPTYPE_DEF_PARAM_AND_YROT, 0x01, MODEL_CASTLE_DOOR_1_STAR, bhvDoor},
    {0x8C, SPTYPE_DEF_PARAM_AND_YROT, 0x03, MODEL_CASTLE_DOOR_3_STARS, bhvDoor},
    {0x8D, SPTYPE_DEF_PARAM_AND_YROT, 0x00, MODEL_CASTLE_KEY_DOOR, bhvDoor},
    {0x88, SPTYPE_PARAMS_AND_YROT   , 0x00, MODEL_CASTLE_CASTLE_DOOR, bhvDoorWarp},
    {0x83, SPTYPE_PARAMS_AND_YROT   , 0x00, MODEL_CASTLE_WOODEN_DOOR, bhvDoorWarp},
    {0x84, SPTYPE_PARAMS_AND_YROT   , 0x00, MODEL_UNKNOWN_DOOR_28, bhvDoorWarp},
    {0x85, SPTYPE_PARAMS_AND_YROT   , 0x00, MODEL_CASTLE_METAL_DOOR, bhvDoorWarp},
    {0x86, SPTYPE_PARAMS_AND_YROT   , 0x00, MODEL_UNKNOWN_DOOR_2A, bhvDoorWarp},
    {0x87, SPTYPE_PARAMS_AND_YROT   , 0x00, MODEL_UNKNOWN_DOOR_2B, bhvDoorWarp},
    {0xFF, SPTYPE_NO_YROT_OR_PARAMS , 0x00, MODEL_NONE, NULL}
};

#endif // _SPECIAL_PRESETS_H
