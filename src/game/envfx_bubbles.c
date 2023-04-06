#include <ultra64.h>

#include "sm64.h"
#include "game_init.h"
#include "memory.h"
#include "ingame_menu.h"
#include "envfx_snow.h"
#include "envfx_bubbles.h"
#include "engine/surface_collision.h"
#include "engine/math_util.h"
#include "engine/behavior_script.h"
#include "audio/external.h"
#include "obj_behaviors.h"

/**
 * This file implements environment effects that are not snow:
 * Flowers (unused), lava bubbles and jetsream/whirlpool bubbles.
 * Refer to 'envfx_snow.c' for more info about environment effects.
 * Note that the term 'bubbles' is used as a collective name for
 * effects in this file even though flowers aren't bubbles. For the
 * sake of concise naming, flowers fall under bubbles.
 */

s16 gEnvFxBubbleConfig[10];
static Gfx *sGfxCursor; // points to end of display list for bubble particles
static s32 sBubbleParticleCount;
static s32 sBubbleParticleMaxCount;

UNUSED s32 D_80330690 = 0;
UNUSED s32 D_80330694 = 0;

/// Template for a bubble particle triangle
Vtx_t gBubbleTempVtx[3] = {
    { { 0, 0, 0 }, 0, { 1544, 964 }, { 0xFF, 0xFF, 0xFF, 0xFF } },
    { { 0, 0, 0 }, 0, { 522, -568 }, { 0xFF, 0xFF, 0xFF, 0xFF } },
    { { 0, 0, 0 }, 0, { -498, 964 }, { 0xFF, 0xFF, 0xFF, 0xFF } },
};

extern void *flower_bubbles_textures_ptr_0B002008;
extern void *lava_bubble_ptr_0B006020;
extern void *bubble_ptr_0B006848;
extern void *tiny_bubble_dl_0B006AB0;
extern void *tiny_bubble_dl_0B006D38;
extern void *tiny_bubble_dl_0B006D68;

/**
 * Check whether the particle with the given index is
 * laterally within distance of point (x, z). Used to
 * kill flower and bubble particles.
 */
s32 particle_is_laterally_close(s32 index, s32 x, s32 z, s32 distance) {
    s32 xPos = (gEnvFxBuffer + index)->xPos;
    s32 zPos = (gEnvFxBuffer + index)->zPos;

    if (sqr(xPos - x) + sqr(zPos - z) > sqr(distance)) {
        return 0;
    }

    return 1;
}

/**
 * Generate a uniform random number in range [-2000, -1000[ or [1000, 2000[
 * Used to position flower particles
 */
s32 random_flower_offset() {
    s32 result = random_float() * 2000.0f - 1000.0f;
    if (result < 0) {
        result -= 1000;
    } else {
        result += 1000;
    }

    return result;
}

/**
 * Update flower particles. Flowers are scattered randomly in front of the
 * camera, and can land on any ground
 */
void envfx_update_flower(Vec3s centerPos) {
    s32 i;
    struct FloorGeometry *floorGeo; // unused
    s32 timer = gGlobalTimer;

    s16 centerX = centerPos[0];
    UNUSED s16 centerY = centerPos[1];
    s16 centerZ = centerPos[2];

    for (i = 0; i < sBubbleParticleMaxCount; i++) {
        (gEnvFxBuffer + i)->isAlive = particle_is_laterally_close(i, centerX, centerZ, 3000);
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            (gEnvFxBuffer + i)->xPos = random_flower_offset() + centerX;
            (gEnvFxBuffer + i)->zPos = random_flower_offset() + centerZ;
            (gEnvFxBuffer + i)->yPos = find_floor_height_and_data((gEnvFxBuffer + i)->xPos, 10000.0f,
                                                                  (gEnvFxBuffer + i)->zPos, &floorGeo);
            (gEnvFxBuffer + i)->isAlive = 1;
            (gEnvFxBuffer + i)->animFrame = random_float() * 5.0f;
        } else if ((timer & 0x03) == 0) {
            (gEnvFxBuffer + i)->animFrame += 1;
            if ((gEnvFxBuffer + i)->animFrame > 5) {
                (gEnvFxBuffer + i)->animFrame = 0;
            }
        }
    }
}

/**
 * Update the position of a lava bubble to be somewhere around centerPos
 * Uses find_floor to find the height of lava, if no floor or a non-lava
 * floor is found the bubble y is set to -10000, which is why you can see
 * occasional lava bubbles far below the course in Lethal Lava Land.
 * In the second Bowser fight arena, the visual lava is above the lava
 * floor so lava-bubbles are not normally visible, only if you bring the
 * camera below the lava plane.
 */
void envfx_set_lava_bubble_position(s32 index, Vec3s centerPos) {
    struct Surface *surface;
    s16 floorY;
    s16 centerX, centerY, centerZ;

    centerX = centerPos[0];
    centerY = centerPos[1];
    centerZ = centerPos[2];

    (gEnvFxBuffer + index)->xPos = random_float() * 6000.0f - 3000.0f + centerX;
    (gEnvFxBuffer + index)->zPos = random_float() * 6000.0f - 3000.0f + centerZ;

    if ((gEnvFxBuffer + index)->xPos > 8000) {
        (gEnvFxBuffer + index)->xPos = 16000 - (gEnvFxBuffer + index)->xPos;
    }
    if ((gEnvFxBuffer + index)->xPos < -8000) {
        (gEnvFxBuffer + index)->xPos = -16000 - (gEnvFxBuffer + index)->xPos;
    }

    if ((gEnvFxBuffer + index)->zPos > 8000) {
        (gEnvFxBuffer + index)->zPos = 16000 - (gEnvFxBuffer + index)->zPos;
    }
    if ((gEnvFxBuffer + index)->zPos < -8000) {
        (gEnvFxBuffer + index)->zPos = -16000 - (gEnvFxBuffer + index)->zPos;
    }

    floorY =
        find_floor((gEnvFxBuffer + index)->xPos, centerY + 500, (gEnvFxBuffer + index)->zPos, &surface);
    if (surface == NULL) {
        (gEnvFxBuffer + index)->yPos = -10000;
        return;
    }

    if (surface->type == SURFACE_BURNING) {
        (gEnvFxBuffer + index)->yPos = floorY;
    } else {
        (gEnvFxBuffer + index)->yPos = -10000;
    }
}

/**
 * Update lava bubble animation and give the bubble a new position if the
 * animation is over.
 */
void envfx_update_lava(Vec3s centerPos) {
    s32 i;
    s32 timer = gGlobalTimer;
    s8 chance;
    UNUSED s16 centerX, centerY, centerZ;

    centerX = centerPos[0];
    centerY = centerPos[1];
    centerZ = centerPos[2];

    for (i = 0; i < sBubbleParticleMaxCount; i++) {
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            envfx_set_lava_bubble_position(i, centerPos);
            (gEnvFxBuffer + i)->isAlive = 1;
        } else if ((timer & 0x01) == 0) {
            (gEnvFxBuffer + i)->animFrame += 1;
            if ((gEnvFxBuffer + i)->animFrame > 8) {
                (gEnvFxBuffer + i)->isAlive = 0;
                (gEnvFxBuffer + i)->animFrame = 0;
            }
        }
    }

    if ((chance = (s32)(random_float() * 16.0f)) == 8) {
        play_sound(SOUND_GENERAL_QUIET_BUBBLE2, gDefaultSoundArgs);
    }
}

/**
 * Rotate the input x, y and z around the rotation origin of the whirlpool
 * according to the pitch and yaw of the whirlpool.
 */
void envfx_rotate_around_whirlpool(s32 *x, s32 *y, s32 *z) {
    s32 vecX = *x - gEnvFxBubbleConfig[ENVFX_STATE_DEST_X];
    s32 vecY = *y - gEnvFxBubbleConfig[ENVFX_STATE_DEST_Y];
    s32 vecZ = *z - gEnvFxBubbleConfig[ENVFX_STATE_DEST_Z];
    f32 cosPitch = coss(gEnvFxBubbleConfig[ENVFX_STATE_PITCH]);
    f32 sinPitch = sins(gEnvFxBubbleConfig[ENVFX_STATE_PITCH]);
    f32 cosMYaw = coss(-gEnvFxBubbleConfig[ENVFX_STATE_YAW]);
    f32 sinMYaw = sins(-gEnvFxBubbleConfig[ENVFX_STATE_YAW]);

    f32 rotatedX = vecX * cosMYaw - sinMYaw * cosPitch * vecY - sinPitch * sinMYaw * vecZ;
    f32 rotatedY = vecX * sinMYaw + cosPitch * cosMYaw * vecY - sinPitch * cosMYaw * vecZ;
    f32 rotatedZ = vecY * sinPitch + cosPitch * vecZ;

    *x = gEnvFxBubbleConfig[ENVFX_STATE_DEST_X] + (s32) rotatedX;
    *y = gEnvFxBubbleConfig[ENVFX_STATE_DEST_Y] + (s32) rotatedY;
    *z = gEnvFxBubbleConfig[ENVFX_STATE_DEST_Z] + (s32) rotatedZ;
}

/**
 * Check whether a whirlpool bubble is alive. A bubble respawns when it is too
 * low or close to the center.
 */
s32 envfx_is_whirlpool_bubble_alive(s32 index) {
    s32 UNUSED sp4;

    if ((gEnvFxBuffer + index)->bubbleY < gEnvFxBubbleConfig[ENVFX_STATE_DEST_Y] - 100) {
        return 0;
    }

    if ((gEnvFxBuffer + index)->angleAndDist[1] < 10) {
        return 0;
    }

    return 1;
}

/**
 * Update whirlpool particles. Whirlpool particles start high and far from
 * the center and get sucked into the sink in a spiraling motion.
 */
void envfx_update_whirlpool(void) {
    s32 i;

    for (i = 0; i < sBubbleParticleMaxCount; i++) {
        (gEnvFxBuffer + i)->isAlive = envfx_is_whirlpool_bubble_alive(i);
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            (gEnvFxBuffer + i)->angleAndDist[1] = random_float() * 1000.0f;
            (gEnvFxBuffer + i)->angleAndDist[0] = random_float() * 65536.0f;
            (gEnvFxBuffer + i)->xPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_X]
                + sins((gEnvFxBuffer + i)->angleAndDist[0]) * (gEnvFxBuffer + i)->angleAndDist[1];
            (gEnvFxBuffer + i)->zPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_Z]
                + coss((gEnvFxBuffer + i)->angleAndDist[0]) * (gEnvFxBuffer + i)->angleAndDist[1];
            (gEnvFxBuffer + i)->bubbleY =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_Y] + (random_float() * 100.0f - 50.0f);
            (gEnvFxBuffer + i)->yPos = (i + gEnvFxBuffer)->bubbleY;
            (gEnvFxBuffer + i)->unusedBubbleVar = 0;
            (gEnvFxBuffer + i)->isAlive = 1;

            envfx_rotate_around_whirlpool(&(gEnvFxBuffer + i)->xPos, &(gEnvFxBuffer + i)->yPos,
                                          &(gEnvFxBuffer + i)->zPos);
        } else {
            (gEnvFxBuffer + i)->angleAndDist[1] -= 40;
            (gEnvFxBuffer + i)->angleAndDist[0] +=
                (s16)(3000 - (gEnvFxBuffer + i)->angleAndDist[1] * 2) + 0x400;
            (gEnvFxBuffer + i)->xPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_X]
                + sins((gEnvFxBuffer + i)->angleAndDist[0]) * (gEnvFxBuffer + i)->angleAndDist[1];
            (gEnvFxBuffer + i)->zPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_Z]
                + coss((gEnvFxBuffer + i)->angleAndDist[0]) * (gEnvFxBuffer + i)->angleAndDist[1];
            (gEnvFxBuffer + i)->bubbleY -= 40 - ((s16)(gEnvFxBuffer + i)->angleAndDist[1] / 100);
            (gEnvFxBuffer + i)->yPos = (i + gEnvFxBuffer)->bubbleY;

            envfx_rotate_around_whirlpool(&(gEnvFxBuffer + i)->xPos, &(gEnvFxBuffer + i)->yPos,
                                          &(gEnvFxBuffer + i)->zPos);
        }
    }
}

/**
 * Check whether a jetstream bubble should respawn. Happens if it is laterally
 * 1000 units away from the source or 1500 units above it.
 */
s32 envfx_is_jestream_bubble_alive(s32 index) {
    UNUSED s32 unk;

    if (!particle_is_laterally_close(index, gEnvFxBubbleConfig[ENVFX_STATE_SRC_X],
                                     gEnvFxBubbleConfig[ENVFX_STATE_SRC_Z], 1000)
        || gEnvFxBubbleConfig[ENVFX_STATE_SRC_Y] + 1500 < (gEnvFxBuffer + index)->yPos) {
        return 0;
    }

    return 1;
}

/**
 * Update the positions of jestream bubble particles.
 * They move up and outwards.
 */
void envfx_update_jetstream(void) {
    s32 i;

    for (i = 0; i < sBubbleParticleMaxCount; i++) {
        (gEnvFxBuffer + i)->isAlive = envfx_is_jestream_bubble_alive(i);
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            (gEnvFxBuffer + i)->angleAndDist[1] = random_float() * 300.0f;
            (gEnvFxBuffer + i)->angleAndDist[0] = random_u16();
            (gEnvFxBuffer + i)->xPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_X]
                + sins((gEnvFxBuffer + i)->angleAndDist[0]) * (gEnvFxBuffer + i)->angleAndDist[1];
            (gEnvFxBuffer + i)->zPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_Z]
                + coss((gEnvFxBuffer + i)->angleAndDist[0]) * (gEnvFxBuffer + i)->angleAndDist[1];
            (gEnvFxBuffer + i)->yPos =
                gEnvFxBubbleConfig[ENVFX_STATE_SRC_Y] + (random_float() * 400.0f - 200.0f);
        } else {
            (gEnvFxBuffer + i)->angleAndDist[1] += 10;
            (gEnvFxBuffer + i)->xPos += sins((gEnvFxBuffer + i)->angleAndDist[0]) * 10.0f;
            (gEnvFxBuffer + i)->zPos += coss((gEnvFxBuffer + i)->angleAndDist[0]) * 10.0f;
            (gEnvFxBuffer + i)->yPos -= ((gEnvFxBuffer + i)->angleAndDist[1] / 30) - 50;
        }
    }
}

/**
 * Initialize bubble (or flower) effect by allocating a buffer to store
 * the state of each particle and setting the initial and max count.
 * Analogous to init_snow_particles, but for bubbles.
 */
s32 envfx_init_bubble(s32 mode) {
    s32 i;

    switch (mode) {
        case ENVFX_MODE_NONE:
            return 0;

        case ENVFX_FLOWERS:
            sBubbleParticleCount = 30;
            sBubbleParticleMaxCount = 30;
            break;

        case ENVFX_LAVA_BUBBLES:
            sBubbleParticleCount = 15;
            sBubbleParticleMaxCount = 15;
            break;

        case ENVFX_WHIRLPOOL_BUBBLES:
            sBubbleParticleCount = 60;
            break;

        case ENVFX_JETSTREAM_BUBBLES:
            sBubbleParticleCount = 60;
            break;
    }

    gEnvFxBuffer = mem_pool_alloc(gEffectsMemoryPool, sBubbleParticleCount * sizeof(struct EnvFxParticle));
    if (!gEnvFxBuffer) {
        return 0;
    }

    bzero(gEnvFxBuffer, sBubbleParticleCount * sizeof(struct EnvFxParticle));
    bzero(gEnvFxBubbleConfig, sizeof(gEnvFxBubbleConfig));

    if (mode == ENVFX_LAVA_BUBBLES) {
        //! Dead code
        if (0) {
        }

        for (i = 0; i < sBubbleParticleCount; i++) {
            (gEnvFxBuffer + i)->animFrame = random_float() * 7.0f;
        }

        if (0) {
        }
    }

    gEnvFxMode = mode;
    return 1;
}

/**
 * Update particles depending on mode.
 * Also sets the given vertices to the correct shape for each mode,
 * though they are not being rotated yet.
 */
void envfx_bubbles_update_switch(s32 mode, Vec3s camTo, Vec3s vertex1, Vec3s vertex2, Vec3s vertex3) {
    switch (mode) {
        case ENVFX_FLOWERS:
            envfx_update_flower(camTo);
            vertex1[0] = 50;  vertex1[1] = 0;  vertex1[2] = 0;
            vertex2[0] = 0;   vertex2[1] = 75; vertex2[2] = 0;
            vertex3[0] = -50; vertex3[1] = 0;  vertex3[2] = 0;
            break;

        case ENVFX_LAVA_BUBBLES:
            envfx_update_lava(camTo);
            vertex1[0] = 100;  vertex1[1] = 0;   vertex1[2] = 0;
            vertex2[0] = 0;    vertex2[1] = 150; vertex2[2] = 0;
            vertex3[0] = -100; vertex3[1] = 0;   vertex3[2] = 0;
            break;

        case ENVFX_WHIRLPOOL_BUBBLES:
            envfx_update_whirlpool();
            vertex1[0] = 40;  vertex1[1] = 0;  vertex1[2] = 0;
            vertex2[0] = 0;   vertex2[1] = 60; vertex2[2] = 0;
            vertex3[0] = -40; vertex3[1] = 0;  vertex3[2] = 0;
            break;

        case ENVFX_JETSTREAM_BUBBLES:
            envfx_update_jetstream();
            vertex1[0] = 40;  vertex1[1] = 0;  vertex1[2] = 0;
            vertex2[0] = 0;   vertex2[1] = 60; vertex2[2] = 0;
            vertex3[0] = -40; vertex3[1] = 0;  vertex3[2] = 0;
            break;
    }
}

/**
 * Append 15 vertices to 'gfx', which is enough for 5 bubbles starting at
 * 'index'. The 3 input vertices represent the roated triangle around (0,0,0)
 * that will be translated to bubble positions to draw the bubble image
 *
 * TODO: (Scrub C)
 */
void append_bubble_vertex_buffer(Gfx *gfx, s32 index, Vec3s vertex1, Vec3s vertex2, Vec3s vertex3,
                                 Vtx *template) {
    s32 i = 0;
    Vtx *vertBuf = alloc_display_list(15 * sizeof(Vtx));
#ifdef VERSION_EU
    Vtx *p;
#endif

    if (vertBuf == NULL) {
        return;
    }

    for (i = 0; i < 15; i += 3) {
        vertBuf[i] = template[0];
#ifdef VERSION_EU
        p = vertBuf;
        p += i;
        p[0].v.ob[0] = gEnvFxBuffer[index + i / 3].xPos + vertex1[0];
        p[0].v.ob[1] = gEnvFxBuffer[index + i / 3].yPos + vertex1[1];
        p[0].v.ob[2] = gEnvFxBuffer[index + i / 3].zPos + vertex1[2];
#else
        vertBuf[i].v.ob[0] = gEnvFxBuffer[index + i / 3].xPos + vertex1[0];
        vertBuf[i].v.ob[1] = gEnvFxBuffer[index + i / 3].yPos + vertex1[1];
        vertBuf[i].v.ob[2] = gEnvFxBuffer[index + i / 3].zPos + vertex1[2];
#endif

        vertBuf[i + 1] = template[1];
#ifdef VERSION_EU
        p = vertBuf;
        p += i;
        p[1].v.ob[0] = gEnvFxBuffer[index + i / 3].xPos + vertex2[0];
        p[1].v.ob[1] = gEnvFxBuffer[index + i / 3].yPos + vertex2[1];
        p[1].v.ob[2] = gEnvFxBuffer[index + i / 3].zPos + vertex2[2];
#else
        vertBuf[i + 1].v.ob[0] = gEnvFxBuffer[index + i / 3].xPos + vertex2[0];
        vertBuf[i + 1].v.ob[1] = gEnvFxBuffer[index + i / 3].yPos + vertex2[1];
        vertBuf[i + 1].v.ob[2] = gEnvFxBuffer[index + i / 3].zPos + vertex2[2];
#endif

        vertBuf[i + 2] = template[2];
#ifdef VERSION_EU
        p = vertBuf;
        p += i;
        p[2].v.ob[0] = gEnvFxBuffer[index + i / 3].xPos + vertex3[0];
        p[2].v.ob[1] = gEnvFxBuffer[index + i / 3].yPos + vertex3[1];
        p[2].v.ob[2] = gEnvFxBuffer[index + i / 3].zPos + vertex3[2];
#else
        vertBuf[i + 2].v.ob[0] = gEnvFxBuffer[index + i / 3].xPos + vertex3[0];
        vertBuf[i + 2].v.ob[1] = gEnvFxBuffer[index + i / 3].yPos + vertex3[1];
        vertBuf[i + 2].v.ob[2] = gEnvFxBuffer[index + i / 3].zPos + vertex3[2];
#endif
    }

    gSPVertex(gfx, VIRTUAL_TO_PHYSICAL(vertBuf), 15, 0);
}

/**
 * Appends to the enfvx display list a command setting the appropriate texture
 * for a specific particle. The display list is not passed as parameter but uses
 * the global sGfxCursor instead.
 */
void envfx_set_bubble_texture(s32 mode, s16 index) {
    void **imageArr;
    s16 frame = (gEnvFxBuffer + index)->animFrame;

    switch (mode) {
        case ENVFX_FLOWERS:
            imageArr = segmented_to_virtual(&flower_bubbles_textures_ptr_0B002008);
            frame = (gEnvFxBuffer + index)->animFrame;
            break;

        case ENVFX_LAVA_BUBBLES:
            imageArr = segmented_to_virtual(&lava_bubble_ptr_0B006020);
            frame = (gEnvFxBuffer + index)->animFrame;
            break;

        case ENVFX_WHIRLPOOL_BUBBLES:
        case ENVFX_JETSTREAM_BUBBLES:
            imageArr = segmented_to_virtual(&bubble_ptr_0B006848);
            frame = 0;
            break;
    }

    gDPSetTextureImage(sGfxCursor++, G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, *(imageArr + frame));
    gSPDisplayList(sGfxCursor++, &tiny_bubble_dl_0B006D68);
}

/**
 * Updates the bubble particle positions, then generates and returns a display
 * list drawing them.
 */
Gfx *envfx_update_bubble_particles(s32 mode, UNUSED Vec3s marioPos, Vec3s camFrom, Vec3s camTo) {
    s32 i;
    s16 radius, pitch, yaw;

    Vec3s vertex1;
    Vec3s vertex2;
    Vec3s vertex3;

    Gfx *gfxStart;

    gfxStart = alloc_display_list(((sBubbleParticleMaxCount / 5) * 10 + sBubbleParticleMaxCount + 3)
                                  * sizeof(Gfx));
    if (gfxStart == NULL) {
        return NULL;
    }

    sGfxCursor = gfxStart;

    orbit_from_positions(camTo, camFrom, &radius, &pitch, &yaw);
    envfx_bubbles_update_switch(mode, camTo, vertex1, vertex2, vertex3);
    rotate_triangle_vertices(vertex1, vertex2, vertex3, pitch, yaw);

    gSPDisplayList(sGfxCursor++, &tiny_bubble_dl_0B006D38);

    for (i = 0; i < sBubbleParticleMaxCount; i += 5) {
        gDPPipeSync(sGfxCursor++);
        envfx_set_bubble_texture(mode, i);
        append_bubble_vertex_buffer(sGfxCursor++, i, vertex1, vertex2, vertex3, (Vtx *) gBubbleTempVtx);
        gSP1Triangle(sGfxCursor++, 0, 1, 2, 0);
        gSP1Triangle(sGfxCursor++, 3, 4, 5, 0);
        gSP1Triangle(sGfxCursor++, 6, 7, 8, 0);
        gSP1Triangle(sGfxCursor++, 9, 10, 11, 0);
        gSP1Triangle(sGfxCursor++, 12, 13, 14, 0);
    }

    gSPDisplayList(sGfxCursor++, &tiny_bubble_dl_0B006AB0);
    gSPEndDisplayList(sGfxCursor++);

    return gfxStart;
}

/**
 * Set the maximum particle count from the gEnvFxBubbleConfig variable,
 * which is set by the whirlpool or jetstream behavior.
 */
void envfx_set_max_bubble_particles(s32 mode) {
    switch (mode) {
        case ENVFX_WHIRLPOOL_BUBBLES:
            sBubbleParticleMaxCount = gEnvFxBubbleConfig[ENVFX_STATE_PARTICLECOUNT];
            break;
        case ENVFX_JETSTREAM_BUBBLES:
            sBubbleParticleMaxCount = gEnvFxBubbleConfig[ENVFX_STATE_PARTICLECOUNT];
            break;
    }
}

/**
 * Update bubble-like environment effects. Assumes the mode is larger than 10,
 * lower modes are snow effects which are updated in a different function.
 * Returns a display list drawing the particles.
 */
Gfx *envfx_update_bubbles(s32 mode, Vec3s marioPos, Vec3s camTo, Vec3s camFrom) {
    Gfx *gfx;

    if (gEnvFxMode == 0 && !envfx_init_bubble(mode)) {
        return NULL;
    }

    envfx_set_max_bubble_particles(mode);

    if (sBubbleParticleMaxCount == 0) {
        return NULL;
    }

    switch (mode) {
        case ENVFX_FLOWERS:
            gfx = envfx_update_bubble_particles(ENVFX_FLOWERS, marioPos, camFrom, camTo);
            break;

        case ENVFX_LAVA_BUBBLES:
            gfx = envfx_update_bubble_particles(ENVFX_LAVA_BUBBLES, marioPos, camFrom, camTo);
            break;

        case ENVFX_WHIRLPOOL_BUBBLES:
            gfx = envfx_update_bubble_particles(ENVFX_WHIRLPOOL_BUBBLES, marioPos, camFrom, camTo);
            break;

        case ENVFX_JETSTREAM_BUBBLES:
            gfx = envfx_update_bubble_particles(ENVFX_JETSTREAM_BUBBLES, marioPos, camFrom, camTo);
            break;

        default:
            return NULL;
    }

    return gfx;
}
