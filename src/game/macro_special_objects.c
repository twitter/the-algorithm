#include <PR/ultratypes.h>

#include "sm64.h"
#include "object_helpers.h"
#include "macro_special_objects.h"
#include "object_list_processor.h"

#include "behavior_data.h"

#include "macro_presets.h"

#include "special_presets.h"

/*
 * Converts the rotation value supplied by macro objects into one
 * that can be used by in-game objects.
 */
s16 convert_rotation(s16 inRotation) {
    u16 rotation = ((u16)(inRotation & 0xFF));
    rotation <<= 8;

    if (rotation == 0x3F00) {
        rotation = 0x4000;
    }

    if (rotation == 0x7F00) {
        rotation = 0x8000;
    }

    if (rotation == 0xBF00) {
        rotation = 0xC000;
    }

    if (rotation == 0xFF00) {
        rotation = 0x0000;
    }

    return (s16) rotation;
}

/*
 * Spawns an object at an absolute location with rotation around the y-axis and
 * parameters filling up the upper 2 bytes of newObj->oBehParams.
 * The object will not spawn if 'behavior' is NULL.
 */
void spawn_macro_abs_yrot_2params(s32 model, const BehaviorScript *behavior, s16 x, s16 y, s16 z, s16 ry, s16 params) {
    if (behavior != NULL) {
        struct Object *newObj = spawn_object_abs_with_rot(
            &gMacroObjectDefaultParent, 0, model, behavior, x, y, z, 0, convert_rotation(ry), 0);
        newObj->oBehParams = ((u32) params) << 16;
    }
}

/*
 * Spawns an object at an absolute location with rotation around the y-axis and
 * a single parameter filling up the upper byte of newObj->oBehParams.
 * The object will not spawn if 'behavior' is NULL.
 */
void spawn_macro_abs_yrot_param1(s32 model, const BehaviorScript *behavior, s16 x, s16 y, s16 z, s16 ry, s16 param) {
    if (behavior != NULL) {
        struct Object *newObj = spawn_object_abs_with_rot(
            &gMacroObjectDefaultParent, 0, model, behavior, x, y, z, 0, convert_rotation(ry), 0);
        newObj->oBehParams = ((u32) param) << 24;
    }
}

/*
 * Spawns an object at an absolute location with currently 3 unknown variables that get converted to
 * floats. Oddly enough, this function doesn't care if 'behavior' is NULL or not.
 */
void spawn_macro_abs_special(s32 model, const BehaviorScript *behavior, s16 x, s16 y, s16 z, s16 unkA, s16 unkB,
                             s16 unkC) {
    struct Object *newObj =
        spawn_object_abs_with_rot(&gMacroObjectDefaultParent, 0, model, behavior, x, y, z, 0, 0, 0);

    // Are all three of these values unused?
    newObj->oMacroUnk108 = (f32) unkA;
    newObj->oMacroUnk10C = (f32) unkB;
    newObj->oMacroUnk110 = (f32) unkC;
}

UNUSED static void spawn_macro_coin_unknown(const BehaviorScript *behavior, s16 a1[]) {
    struct Object *sp3C;
    s16 model;

    model = bhvYellowCoin == behavior ? MODEL_YELLOW_COIN : MODEL_NONE;

    sp3C = spawn_object_abs_with_rot(&gMacroObjectDefaultParent, 0, model, behavior,
                                     a1[1], a1[2], a1[3], 0, convert_rotation(a1[0]), 0);

    sp3C->oUnk1A8 = a1[4];
    sp3C->oBehParams = (a1[4] & 0xFF) >> 16;
}

struct LoadedPreset {
    /*0x00*/ const BehaviorScript *behavior;
    /*0x04*/ s16 param; // huh? why does the below function swap these.. just use the struct..
    /*0x06*/ s16 model;
};

#define MACRO_OBJ_Y_ROT 0
#define MACRO_OBJ_X 1
#define MACRO_OBJ_Y 2
#define MACRO_OBJ_Z 3
#define MACRO_OBJ_PARAMS 4

void spawn_macro_objects(s16 areaIndex, s16 *macroObjList) {
    UNUSED u8 filler[4];
    s32 presetID;

    s16 macroObject[5]; // see the 5 #define statements above
    struct Object *newObj;
    struct LoadedPreset preset;

    gMacroObjectDefaultParent.header.gfx.areaIndex = areaIndex;
    gMacroObjectDefaultParent.header.gfx.activeAreaIndex = areaIndex;

    while (TRUE) {
        if (*macroObjList == -1) { // An encountered value of -1 means the list has ended.
            break;
        }

        presetID = (*macroObjList & 0x1FF) - 31; // Preset identifier for MacroObjectPresets array

        if (presetID < 0) {
            break;
        }

        // Set macro object properties from the list
        macroObject[MACRO_OBJ_Y_ROT] = ((*macroObjList++ >> 9) & 0x7F) << 1; // Y-Rotation
        macroObject[MACRO_OBJ_X] = *macroObjList++;                          // X position
        macroObject[MACRO_OBJ_Y] = *macroObjList++;                          // Y position
        macroObject[MACRO_OBJ_Z] = *macroObjList++;                          // Z position
        macroObject[MACRO_OBJ_PARAMS] = *macroObjList++;                     // Behavior params

        // Get the preset values from the MacroObjectPresets list.
        preset.model = MacroObjectPresets[presetID].model;
        preset.behavior = MacroObjectPresets[presetID].behavior;
        preset.param = MacroObjectPresets[presetID].param;

        if (preset.param != 0) {
            macroObject[MACRO_OBJ_PARAMS] =
                (macroObject[MACRO_OBJ_PARAMS] & 0xFF00) + (preset.param & 0x00FF);
        }

        // If object has been killed, prevent it from respawning
        if (((macroObject[MACRO_OBJ_PARAMS] >> 8) & RESPAWN_INFO_DONT_RESPAWN)
            != RESPAWN_INFO_DONT_RESPAWN) {
            // Spawn the new macro object.
            newObj = spawn_object_abs_with_rot(
                         &gMacroObjectDefaultParent, // Parent object
                         0,                          // Unused
                         preset.model,               // Model ID
                         preset.behavior,            // Behavior address
                         macroObject[MACRO_OBJ_X],   // X-position
                         macroObject[MACRO_OBJ_Y],   // Y-position
                         macroObject[MACRO_OBJ_Z],   // Z-position
                         0,                          // X-rotation
                         convert_rotation(macroObject[MACRO_OBJ_Y_ROT]), // Y-rotation
                         0                                               // Z-rotation
                     );

            newObj->oUnk1A8 = macroObject[MACRO_OBJ_PARAMS];
            newObj->oBehParams = ((macroObject[MACRO_OBJ_PARAMS] & 0x00FF) << 16)
                                 + (macroObject[MACRO_OBJ_PARAMS] & 0xFF00);
            newObj->oBehParams2ndByte = macroObject[MACRO_OBJ_PARAMS] & 0x00FF;
            newObj->respawnInfoType = RESPAWN_INFO_TYPE_16;
            newObj->respawnInfo = macroObjList - 1;
            newObj->parentObj = newObj;
        }
    }
}

void spawn_macro_objects_hardcoded(s16 areaIndex, s16 *macroObjList) {
    UNUSED u8 filler1[8];

    // This version of macroObjList has the preset and Y-Rotation separated,
    // and lacks behavior params. Might be an early version of the macro object list?
    s16 macroObjX;
    s16 macroObjY;
    s16 macroObjZ;
    s16 macroObjPreset;
    s16 macroObjRY; // Y Rotation

    UNUSED u8 filler2[10];

    gMacroObjectDefaultParent.header.gfx.areaIndex = areaIndex;
    gMacroObjectDefaultParent.header.gfx.activeAreaIndex = areaIndex;

    while (TRUE) {
        macroObjPreset = *macroObjList++;

        if (macroObjPreset < 0) {
            break;
        }

        macroObjX = *macroObjList++;
        macroObjY = *macroObjList++;
        macroObjZ = *macroObjList++;
        macroObjRY = *macroObjList++;

        // Spawn objects based on hardcoded presets, and most seem to be for Big Boo's Haunt.
        // However, BBH doesn't use this function so this might just be an early test?
        switch (macroObjPreset) {
            case 0:
                spawn_macro_abs_yrot_2params(MODEL_NONE, bhvBooStaircase, macroObjX, macroObjY,
                                             macroObjZ, macroObjRY, 0);
                break;
            case 1:
                spawn_macro_abs_yrot_2params(MODEL_BBH_TILTING_FLOOR_PLATFORM,
                                             bhvBbhTiltingTrapPlatform, macroObjX, macroObjY, macroObjZ,
                                             macroObjRY, 0);
                break;
            case 2:
                spawn_macro_abs_yrot_2params(MODEL_BBH_TUMBLING_PLATFORM, bhvBbhTumblingBridge,
                                             macroObjX, macroObjY, macroObjZ, macroObjRY, 0);
                break;
            case 3:
                spawn_macro_abs_yrot_2params(MODEL_BBH_MOVING_BOOKSHELF, bhvHauntedBookshelf, macroObjX,
                                             macroObjY, macroObjZ, macroObjRY, 0);
                break;
            case 4:
                spawn_macro_abs_yrot_2params(MODEL_BBH_MESH_ELEVATOR, bhvMeshElevator, macroObjX,
                                             macroObjY, macroObjZ, macroObjRY, 0);
                break;
            case 20:
                spawn_macro_abs_yrot_2params(MODEL_YELLOW_COIN, bhvYellowCoin, macroObjX, macroObjY,
                                             macroObjZ, macroObjRY, 0);
                break;
            case 21:
                spawn_macro_abs_yrot_2params(MODEL_YELLOW_COIN, bhvYellowCoin, macroObjX, macroObjY,
                                             macroObjZ, macroObjRY, 0);
                break;
            default:
                break;
        }
    }
}

void spawn_special_objects(s16 areaIndex, s16 **specialObjList) {
    s32 numOfSpecialObjects;
    s32 i;
    s32 offset;
    s16 x;
    s16 y;
    s16 z;
    s16 extraParams[4];
    u8 model;
    u8 type;
    u8 presetID;
    u8 defaultParam;
    const BehaviorScript *behavior;

    numOfSpecialObjects = **specialObjList;
    (*specialObjList)++;

    gMacroObjectDefaultParent.header.gfx.areaIndex = areaIndex;
    gMacroObjectDefaultParent.header.gfx.activeAreaIndex = areaIndex;

    for (i = 0; i < numOfSpecialObjects; i++) {
        presetID = (u8) **specialObjList;
        (*specialObjList)++;
        x = **specialObjList;
        (*specialObjList)++;
        y = **specialObjList;
        (*specialObjList)++;
        z = **specialObjList;
        (*specialObjList)++;

        offset = 0;
        while (TRUE) {
            if (SpecialObjectPresets[offset].preset_id == presetID) {
                break;
            }

            if (SpecialObjectPresets[offset].preset_id == 0xFF) {
            }

            offset++;
        }

        model = SpecialObjectPresets[offset].model;
        behavior = SpecialObjectPresets[offset].behavior;
        type = SpecialObjectPresets[offset].type;
        defaultParam = SpecialObjectPresets[offset].defParam;

        switch (type) {
            case SPTYPE_NO_YROT_OR_PARAMS:
                spawn_macro_abs_yrot_2params(model, behavior, x, y, z, 0, 0);
                break;
            case SPTYPE_YROT_NO_PARAMS:
                extraParams[0] = **specialObjList; // Y-rotation
                (*specialObjList)++;
                spawn_macro_abs_yrot_2params(model, behavior, x, y, z, extraParams[0], 0);
                break;
            case SPTYPE_PARAMS_AND_YROT:
                extraParams[0] = **specialObjList; // Y-rotation
                (*specialObjList)++;
                extraParams[1] = **specialObjList; // Params
                (*specialObjList)++;
                spawn_macro_abs_yrot_2params(model, behavior, x, y, z, extraParams[0], extraParams[1]);
                break;
            case SPTYPE_UNKNOWN:
                extraParams[0] =
                    **specialObjList; // Unknown, gets put into obj->oMacroUnk108 as a float
                (*specialObjList)++;
                extraParams[1] =
                    **specialObjList; // Unknown, gets put into obj->oMacroUnk10C as a float
                (*specialObjList)++;
                extraParams[2] =
                    **specialObjList; // Unknown, gets put into obj->oMacroUnk110 as a float
                (*specialObjList)++;
                spawn_macro_abs_special(model, behavior, x, y, z, extraParams[0], extraParams[1],
                                        extraParams[2]);
                break;
            case SPTYPE_DEF_PARAM_AND_YROT:
                extraParams[0] = **specialObjList; // Y-rotation
                (*specialObjList)++;
                spawn_macro_abs_yrot_param1(model, behavior, x, y, z, extraParams[0], defaultParam);
                break;
            default:
                break;
        }
    }
}

#ifdef NO_SEGMENTED_MEMORY
u32 get_special_objects_size(s16 *data) {
    s16 *startPos = data;
    s32 numOfSpecialObjects;
    s32 i;
    u8 presetID;
    s32 offset;

    numOfSpecialObjects = *data++;

    for (i = 0; i < numOfSpecialObjects; i++) {
        presetID = (u8) *data++;
        data += 3;
        offset = 0;

        while (TRUE) {
            if (SpecialObjectPresets[offset].preset_id == presetID) {
                break;
            }
            offset++;
        }

        switch (SpecialObjectPresets[offset].type) {
            case SPTYPE_NO_YROT_OR_PARAMS:
                break;
            case SPTYPE_YROT_NO_PARAMS:
                data++;
                break;
            case SPTYPE_PARAMS_AND_YROT:
                data += 2;
                break;
            case SPTYPE_UNKNOWN:
                data += 3;
                break;
            case SPTYPE_DEF_PARAM_AND_YROT:
                data++;
                break;
            default:
                break;
        }
    }

    return data - startPos;
}
#endif
