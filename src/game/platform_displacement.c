#include <ultra64.h>

#include "sm64.h"
#include "platform_displacement.h"
#include "engine/math_util.h"
#include "object_helpers.h"
#include "mario.h"
#include "engine/behavior_script.h"
#include "level_update.h"
#include "engine/surface_collision.h"
#include "object_list_processor.h"

u16 D_8032FEC0 = 0;

u32 unused_8032FEC4[4] = { 0 };

struct Object *gMarioPlatform = NULL;

/**
 * Determine if mario is standing on a platform object, meaning that he is
 * within 4 units of the floor. Set his referenced platform object accordingly.
 */
void update_mario_platform(void) {
    struct Surface *floor;
    UNUSED u32 unused;
    f32 marioX;
    f32 marioY;
    f32 marioZ;
    f32 floorHeight;
    u32 awayFromFloor;

    if (gMarioObject == NULL) {
        return;
    }

    //! If mario moves onto a rotating platform in a PU, the find_floor call
    //  will detect the platform and he will end up receiving a large amount
    //  of displacement since he is considered to be far from the platform's
    //  axis of rotation.

    marioX = gMarioObject->oPosX;
    marioY = gMarioObject->oPosY;
    marioZ = gMarioObject->oPosZ;
    floorHeight = find_floor(marioX, marioY, marioZ, &floor);

    if (absf(marioY - floorHeight) < 4.0f) {
        awayFromFloor = 0;
    } else {
        awayFromFloor = 1;
    }

    switch (awayFromFloor) {
        case 1:
            gMarioPlatform = NULL;
            gMarioObject->platform = NULL;
            break;

        case 0:
            if (floor != NULL && floor->object != NULL) {
                gMarioPlatform = floor->object;
                gMarioObject->platform = floor->object;
            } else {
                gMarioPlatform = NULL;
                gMarioObject->platform = NULL;
            }
            break;
    }
}

/**
 * Get mario's position and store it in x, y, and z.
 */
void get_mario_pos(f32 *x, f32 *y, f32 *z) {
    *x = gMarioStates[0].pos[0];
    *y = gMarioStates[0].pos[1];
    *z = gMarioStates[0].pos[2];
}

/**
 * Set mario's position.
 */
void set_mario_pos(f32 x, f32 y, f32 z) {
    gMarioStates[0].pos[0] = x;
    gMarioStates[0].pos[1] = y;
    gMarioStates[0].pos[2] = z;
}

/**
 * Apply one frame of platform rotation to mario or an object using the given
 * platform. If isMario is 0, use gCurrentObject.
 */
void apply_platform_displacement(u32 isMario, struct Object *platform) {
    f32 x;
    f32 y;
    f32 z;
    f32 platformPosX;
    f32 platformPosY;
    f32 platformPosZ;
    Vec3f currentObjectOffset;
    Vec3f relativeOffset;
    Vec3f newObjectOffset;
    Vec3s rotation;
    UNUSED s16 unused1;
    UNUSED s16 unused2;
    UNUSED s16 unused3;
    f32 displaceMatrix[4][4];

    rotation[0] = platform->oAngleVelPitch;
    rotation[1] = platform->oAngleVelYaw;
    rotation[2] = platform->oAngleVelRoll;

    if (isMario) {
        D_8032FEC0 = 0;
        get_mario_pos(&x, &y, &z);
    } else {
        x = gCurrentObject->oPosX;
        y = gCurrentObject->oPosY;
        z = gCurrentObject->oPosZ;
    }

    x += platform->oVelX;
    z += platform->oVelZ;

    if (rotation[0] != 0 || rotation[1] != 0 || rotation[2] != 0) {
        unused1 = rotation[0];
        unused2 = rotation[2];
        unused3 = platform->oFaceAngleYaw;

        if (isMario) {
            gMarioStates[0].faceAngle[1] += rotation[1];
        }

        platformPosX = platform->oPosX;
        platformPosY = platform->oPosY;
        platformPosZ = platform->oPosZ;

        currentObjectOffset[0] = x - platformPosX;
        currentObjectOffset[1] = y - platformPosY;
        currentObjectOffset[2] = z - platformPosZ;

        rotation[0] = platform->oFaceAnglePitch - platform->oAngleVelPitch;
        rotation[1] = platform->oFaceAngleYaw - platform->oAngleVelYaw;
        rotation[2] = platform->oFaceAngleRoll - platform->oAngleVelRoll;

        mtxf_rotate_zxy_and_translate(displaceMatrix, currentObjectOffset, rotation);
        linear_mtxf_transpose_mul_vec3f(displaceMatrix, relativeOffset, currentObjectOffset);

        rotation[0] = platform->oFaceAnglePitch;
        rotation[1] = platform->oFaceAngleYaw;
        rotation[2] = platform->oFaceAngleRoll;

        mtxf_rotate_zxy_and_translate(displaceMatrix, currentObjectOffset, rotation);
        linear_mtxf_mul_vec3f(displaceMatrix, newObjectOffset, relativeOffset);

        x = platformPosX + newObjectOffset[0];
        y = platformPosY + newObjectOffset[1];
        z = platformPosZ + newObjectOffset[2];
    }

    if (isMario) {
        set_mario_pos(x, y, z);
    } else {
        gCurrentObject->oPosX = x;
        gCurrentObject->oPosY = y;
        gCurrentObject->oPosZ = z;
    }
}

/**
 * If mario's platform is not null, apply platform displacement.
 */
void apply_mario_platform_displacement(void) {
    struct Object *platform;

    platform = gMarioPlatform;
    if (!(gTimeStopState & TIME_STOP_ACTIVE) && gMarioObject != NULL && platform != NULL) {
        apply_platform_displacement(1, platform);
    }
}

#ifndef VERSION_JP
/**
 * Set mario's platform to NULL.
 */
void clear_mario_platform(void) {
    gMarioPlatform = NULL;
}
#endif
