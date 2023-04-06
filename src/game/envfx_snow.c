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
 * This file contains the function that handles 'environment effects',
 * which are particle effects related to the level type that, unlike
 * object-based particle effects, are rendered more efficiently by manually
 * generating display lists instead of drawing each particle separately.
 * This file implements snow effects, while in 'envfx_bubbles.c' the
 * implementation for flowers (unused), lava bubbles and jetstream bubbles
 * can be found.
 * The main entry point for envfx is at the bottom of this file, which is
 * called from geo_envfx_main in level_geo.c
 */

// Might be duplicate
struct SnowFlakeVertex {
    s16 x;
    s16 y;
    s16 z;
};

struct EnvFxParticle *gEnvFxBuffer;
Vec3i gSnowCylinderLastPos;
s16 gSnowParticleCount;
s16 gSnowParticleMaxCount;

/* DATA */
s8 gEnvFxMode = 0;
UNUSED s32 D_80330644 = 0;

/// Template for a snow particle triangle
Vtx gSnowTempVtx[3] = { { { { -5, 5, 0 }, 0, { 0, 0 }, { 0x7F, 0x7F, 0x7F, 0xFF } } },
                        { { { -5, -5, 0 }, 0, { 0, 960 }, { 0x7F, 0x7F, 0x7F, 0xFF } } },
                        { { { 5, 5, 0 }, 0, { 960, 0 }, { 0x7F, 0x7F, 0x7F, 0xFF } } } };

// Change these to make snowflakes smaller or bigger
struct SnowFlakeVertex gSnowFlakeVertex1 = { -5, 5, 0 };
struct SnowFlakeVertex gSnowFlakeVertex2 = { -5, -5, 0 };
struct SnowFlakeVertex gSnowFlakeVertex3 = { 5, 5, 0 };

extern void *tiny_bubble_dl_0B006AB0;
extern void *tiny_bubble_dl_0B006A50;
extern void *tiny_bubble_dl_0B006CD8;

/**
 * Initialize snow particles by allocating a buffer for storing their state
 * and setting a start amount.
 */
s32 envfx_init_snow(s32 mode) {
    switch (mode) {
        case ENVFX_MODE_NONE:
            return 0;

        case ENVFX_SNOW_NORMAL:
            gSnowParticleMaxCount = 140;
            gSnowParticleCount = 5;
            break;

        case ENVFX_SNOW_WATER:
            gSnowParticleMaxCount = 30;
            gSnowParticleCount = 30;
            break;

        case ENVFX_SNOW_BLIZZARD:
            gSnowParticleMaxCount = 140;
            gSnowParticleCount = 140;
            break;
    }

    gEnvFxBuffer = mem_pool_alloc(gEffectsMemoryPool, gSnowParticleMaxCount * sizeof(struct EnvFxParticle));
    if (!gEnvFxBuffer) {
        return 0;
    }

    bzero(gEnvFxBuffer, gSnowParticleMaxCount * sizeof(struct EnvFxParticle));

    gEnvFxMode = mode;
    return 1;
}

/**
 * Update the amount of snow particles on screen.
 * Normal snow starts with few flakes and slowly increases to the maximum.
 * For water snow, this is dependent on how deep underwater you are.
 * Blizzard snows starts at the maximum amount and doesn't change.
 */
void envfx_update_snowflake_count(s32 mode, Vec3s marioPos) {
    s32 timer = gGlobalTimer;
    f32 waterLevel;
    switch (mode) {
        case ENVFX_SNOW_NORMAL:
            if (gSnowParticleMaxCount > gSnowParticleCount) {
                if ((timer & 0x3F) == 0) {
                    gSnowParticleCount += 5;
                }
            }
            break;

        case ENVFX_SNOW_WATER:
            waterLevel = find_water_level(marioPos[0], marioPos[2]);

            gSnowParticleCount =
                (((s32)((waterLevel - 400.f - (f32) marioPos[1]) * 1.0e-3) << 0x10) >> 0x10) * 5;

            if (gSnowParticleCount < 0) {
                gSnowParticleCount = 0;
            }

            if (gSnowParticleCount > gSnowParticleMaxCount) {
                gSnowParticleCount = gSnowParticleMaxCount;
            }

            break;

        case ENVFX_SNOW_BLIZZARD:
            break;
    }
}

/**
 * Deallocate the buffer storing snow particles and set the environment effect
 * to none.
 */
void envfx_cleanup_snow(void *snowParticleArray) {
    if (gEnvFxMode) {
        if (snowParticleArray) {
            mem_pool_free(gEffectsMemoryPool, snowParticleArray);
        }
        gEnvFxMode = ENVFX_MODE_NONE;
    }
}

/**
 * Given two points, return the vector from one to the other represented
 * as euler angles and a length
 */
void orbit_from_positions(Vec3s from, Vec3s to, s16 *radius, s16 *pitch, s16 *yaw) {
    f32 dx = to[0] - from[0];
    f32 dy = to[1] - from[1];
    f32 dz = to[2] - from[2];

    *radius = (s16) sqrtf(dx * dx + dy * dy + dz * dz);
    *pitch = atan2s(sqrtf(dx * dx + dz * dz), dy);
    *yaw = atan2s(dz, dx);
}

/**
 * Calculate the 'result' vector as the position of the 'origin' vector
 * with a vector added represented by radius, pitch and yaw.
 */
void pos_from_orbit(Vec3s origin, Vec3s result, s16 radius, s16 pitch, s16 yaw) {
    result[0] = origin[0] + radius * coss(pitch) * sins(yaw);
    result[1] = origin[1] + radius * sins(pitch);
    result[2] = origin[2] + radius * coss(pitch) * coss(yaw);
}

/**
 * Check whether the snowflake with the given index is inside view, where
 * 'view' is a cylinder of radius 300 and height 400 centered at the input
 * x, y and z.
 */
s32 envfx_is_snowflake_alive(s32 index, s32 snowCylinderX, s32 snowCylinderY, s32 snowCylinderZ) {
    s32 x = (gEnvFxBuffer + index)->xPos;
    s32 y = (gEnvFxBuffer + index)->yPos;
    s32 z = (gEnvFxBuffer + index)->zPos;

    if (sqr(x - snowCylinderX) + sqr(z - snowCylinderZ) > sqr(300)) {
        return 0;
    }

    if ((y < snowCylinderY - 201) || (snowCylinderY + 201 < y)) {
        return 0;
    }

    return 1;
}

/**
 * Update the position of each snowflake. Snowflakes wiggle by having a
 * random value added to their position each frame. If snowflakes get out
 * of view (where view = a small cylinder in front of the camera) their
 * position is reset to somewhere in view.
 * Since the cylinder of snow is so close to the camera, snow flakes would
 * move out of view very quickly when the camera moves. To mitigate this,
 * a portion of the difference between the previous and current snowCylinder
 * position is added to snowflakes to keep them in view for longer. That's
 * why the snow looks a bit off in 3d, it's a lot closer than you'd think
 * but appears to be further by means of hacky position updates. This might
 * have been done because larger, further away snowflakes are occluded easily
 * by level geometry, wasting many particles.
 */
void envfx_update_snow_normal(s32 snowCylinderX, s32 snowCylinderY, s32 snowCylinderZ) {
    s32 i;
    s32 deltaX = snowCylinderX - gSnowCylinderLastPos[0];
    s32 deltaY = snowCylinderY - gSnowCylinderLastPos[1];
    s32 deltaZ = snowCylinderZ - gSnowCylinderLastPos[2];

    for (i = 0; i < gSnowParticleCount; i++) {
        (gEnvFxBuffer + i)->isAlive =
            envfx_is_snowflake_alive(i, snowCylinderX, snowCylinderY, snowCylinderZ);
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            (gEnvFxBuffer + i)->xPos =
                400.0f * random_float() - 200.0f + snowCylinderX + (s16)(deltaX * 2);
            (gEnvFxBuffer + i)->zPos =
                400.0f * random_float() - 200.0f + snowCylinderZ + (s16)(deltaZ * 2);
            (gEnvFxBuffer + i)->yPos = 200.0f * random_float() + snowCylinderY;
            (gEnvFxBuffer + i)->isAlive = 1;
        } else {
            (gEnvFxBuffer + i)->xPos += random_float() * 2 - 1.0f + (s16)(deltaX / 1.2);
            (gEnvFxBuffer + i)->yPos -= 2 -(s16)(deltaY * 0.8);
            (gEnvFxBuffer + i)->zPos += random_float() * 2 - 1.0f + (s16)(deltaZ / 1.2);
        }
    }

    gSnowCylinderLastPos[0] = snowCylinderX;
    gSnowCylinderLastPos[1] = snowCylinderY;
    gSnowCylinderLastPos[2] = snowCylinderZ;
}

/**
 * Unused function. Basically a copy-paste of envfx_update_snow_normal,
 * but an extra 20 units is added to each snowflake x and snowflakes can
 * respawn in y-range [-200, 200] instead of [0, 200] relative to snowCylinderY
 * They also fall a bit faster (with vertical speed -5 instead of -2).
 */
void envfx_update_snow_blizzard(s32 snowCylinderX, s32 snowCylinderY, s32 snowCylinderZ) {
    s32 i;
    s32 deltaX = snowCylinderX - gSnowCylinderLastPos[0];
    s32 deltaY = snowCylinderY - gSnowCylinderLastPos[1];
    s32 deltaZ = snowCylinderZ - gSnowCylinderLastPos[2];

    for (i = 0; i < gSnowParticleCount; i++) {
        (gEnvFxBuffer + i)->isAlive =
            envfx_is_snowflake_alive(i, snowCylinderX, snowCylinderY, snowCylinderZ);
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            (gEnvFxBuffer + i)->xPos =
                400.0f * random_float() - 200.0f + snowCylinderX + (s16)(deltaX * 2);
            (gEnvFxBuffer + i)->zPos =
                400.0f * random_float() - 200.0f + snowCylinderZ + (s16)(deltaZ * 2);
            (gEnvFxBuffer + i)->yPos = 400.0f * random_float() - 200.0f + snowCylinderY;
            (gEnvFxBuffer + i)->isAlive = 1;
        } else {
            (gEnvFxBuffer + i)->xPos += random_float() * 2 - 1.0f + (s16)(deltaX / 1.2) + 20.0f;
            (gEnvFxBuffer + i)->yPos -= 5 -(s16)(deltaY * 0.8);
            (gEnvFxBuffer + i)->zPos += random_float() * 2 - 1.0f + (s16)(deltaZ / 1.2);
        }
    }

    gSnowCylinderLastPos[0] = snowCylinderX;
    gSnowCylinderLastPos[1] = snowCylinderY;
    gSnowCylinderLastPos[2] = snowCylinderZ;
}

/*! Unused function. Checks whether a position is laterally within 3000 units
 *  to the point (x: 3380, z: -520). Considering there is an unused blizzard
 *  snow mode, this could have been used to check whether Mario is in a
 *  'blizzard area'. In Cool Cool Mountain and Snowman's Land the area lies
 *  near the starting point and doesn't seem meaningfull. Notably, the point is
 *  close to the entrance of SL, so maybe there were plans for an extra hint to
 *  find it. The radius of 3000 units is quite large for that though, covering
 *  more than half of the mirror room.
 */
static s32 is_in_mystery_snow_area(s32 x, UNUSED s32 y, s32 z) {
    if (sqr(x - 3380) + sqr(z + 520) < sqr(3000)) {
        return 1;
    }
    return 0;
}

/**
 * Update the position of underwater snow particles. Since they are stationary,
 * they merely jump back into view when they are out of view.
 */
void envfx_update_snow_water(s32 snowCylinderX, s32 snowCylinderY, s32 snowCylinderZ) {
    s32 i;

    for (i = 0; i < gSnowParticleCount; i++) {
        (gEnvFxBuffer + i)->isAlive =
            envfx_is_snowflake_alive(i, snowCylinderX, snowCylinderY, snowCylinderZ);
        if ((gEnvFxBuffer + i)->isAlive == 0) {
            (gEnvFxBuffer + i)->xPos = 400.0f * random_float() - 200.0f + snowCylinderX;
            (gEnvFxBuffer + i)->zPos = 400.0f * random_float() - 200.0f + snowCylinderZ;
            (gEnvFxBuffer + i)->yPos = 400.0f * random_float() - 200.0f + snowCylinderY;
            (gEnvFxBuffer + i)->isAlive = 1;
        }
    }
}

/**
 * Rotates the input vertices according to the give pitch and yaw. This
 * is needed for billboarding of particles.
 */
void rotate_triangle_vertices(Vec3s vertex1, Vec3s vertex2, Vec3s vertex3, s16 pitch, s16 yaw) {
    f32 cosPitch = coss(pitch);
    f32 sinPitch = sins(pitch);
    f32 cosMYaw = coss(-yaw);
    f32 sinMYaw = sins(-yaw);

    Vec3f v1, v2, v3;

    v1[0] = vertex1[0];
    v1[1] = vertex1[1];
    v1[2] = vertex1[2];

    v2[0] = vertex2[0];
    v2[1] = vertex2[1];
    v2[2] = vertex2[2];

    v3[0] = vertex3[0];
    v3[1] = vertex3[1];
    v3[2] = vertex3[2];

    vertex1[0] = v1[0] * cosMYaw + v1[1] * (sinPitch * sinMYaw) + v1[2] * (-sinMYaw * cosPitch);
    vertex1[1] = v1[1] * cosPitch + v1[2] * sinPitch;
    vertex1[2] = v1[0] * sinMYaw + v1[1] * (-sinPitch * cosMYaw) + v1[2] * (cosPitch * cosMYaw);

    vertex2[0] = v2[0] * cosMYaw + v2[1] * (sinPitch * sinMYaw) + v2[2] * (-sinMYaw * cosPitch);
    vertex2[1] = v2[1] * cosPitch + v2[2] * sinPitch;
    vertex2[2] = v2[0] * sinMYaw + v2[1] * (-sinPitch * cosMYaw) + v2[2] * (cosPitch * cosMYaw);

    vertex3[0] = v3[0] * cosMYaw + v3[1] * (sinPitch * sinMYaw) + v3[2] * (-sinMYaw * cosPitch);
    vertex3[1] = v3[1] * cosPitch + v3[2] * sinPitch;
    vertex3[2] = v3[0] * sinMYaw + v3[1] * (-sinPitch * cosMYaw) + v3[2] * (cosPitch * cosMYaw);
}

/**
 * Append 15 vertices to 'gfx', which is enough for 5 snowflakes starting at
 * 'index' in the buffer. The 3 input vertices represent the roated triangle
 * around (0,0,0) that will be translated to snowflake positions to draw the
 * snowflake image.
 *
 * TODO: (Scrub C)
 */
void append_snowflake_vertex_buffer(Gfx *gfx, s32 index, Vec3s vertex1, Vec3s vertex2, Vec3s vertex3) {
    s32 i = 0;
    Vtx *vertBuf = (Vtx *) alloc_display_list(15 * sizeof(Vtx));
#ifdef VERSION_EU
    Vtx *p;
#endif

    if (vertBuf == NULL) {
        return;
    }

    for (i = 0; i < 15; i += 3) {
        vertBuf[i] = gSnowTempVtx[0];
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

        vertBuf[i + 1] = gSnowTempVtx[1];
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

        vertBuf[i + 2] = gSnowTempVtx[2];
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
 * Updates positions of snow particles and returns a pointer to a display list
 * drawing all snowflakes.
 */
Gfx *envfx_update_snow(s32 snowMode, Vec3s marioPos, Vec3s camFrom, Vec3s camTo) {
    s32 i;
    s16 radius, pitch, yaw;
    Vec3s snowCylinderPos;
    struct SnowFlakeVertex vertex1, vertex2, vertex3;
    Gfx *gfxStart;
    Gfx *gfx;

    vertex1 = gSnowFlakeVertex1;
    vertex2 = gSnowFlakeVertex2;
    vertex3 = gSnowFlakeVertex3;

    gfxStart = (Gfx *) alloc_display_list((gSnowParticleCount * 6 + 3) * sizeof(Gfx));
    gfx = gfxStart;

    if (gfxStart == NULL) {
        return NULL;
    }

    envfx_update_snowflake_count(snowMode, marioPos);

    // Note: to and from are inverted here, so the resulting vector goes towards the camera
    orbit_from_positions(camTo, camFrom, &radius, &pitch, &yaw);

    switch (snowMode) {
        case ENVFX_SNOW_NORMAL:
            // ensure the snow cylinder is no further than 250 units in front
            // of the camera, and no closer than 1 unit.
            if (radius > 250) {
                radius -= 250;
            } else {
                radius = 1;
            }

            pos_from_orbit(camTo, snowCylinderPos, radius, pitch, yaw);
            envfx_update_snow_normal(snowCylinderPos[0], snowCylinderPos[1], snowCylinderPos[2]);
            break;

        case ENVFX_SNOW_WATER:
            if (radius > 500) {
                radius -= 500;
            } else {
                radius = 1;
            }

            pos_from_orbit(camTo, snowCylinderPos, radius, pitch, yaw);
            envfx_update_snow_water(snowCylinderPos[0], snowCylinderPos[1], snowCylinderPos[2]);
            break;
        case ENVFX_SNOW_BLIZZARD:
            if (radius > 250) {
                radius -= 250;
            } else {
                radius = 1;
            }

            pos_from_orbit(camTo, snowCylinderPos, radius, pitch, yaw);
            envfx_update_snow_blizzard(snowCylinderPos[0], snowCylinderPos[1], snowCylinderPos[2]);
            break;
    }

    rotate_triangle_vertices((s16 *) &vertex1, (s16 *) &vertex2, (s16 *) &vertex3, pitch, yaw);

    if (snowMode == ENVFX_SNOW_NORMAL || snowMode == ENVFX_SNOW_BLIZZARD) {
        gSPDisplayList(gfx++, &tiny_bubble_dl_0B006A50); // snowflake with gray edge
    } else if (snowMode == ENVFX_SNOW_WATER) {
        gSPDisplayList(gfx++, &tiny_bubble_dl_0B006CD8); // snowflake with blue edge
    }

    for (i = 0; i < gSnowParticleCount; i += 5) {
        append_snowflake_vertex_buffer(gfx++, i, (s16 *) &vertex1, (s16 *) &vertex2, (s16 *) &vertex3);

        gSP1Triangle(gfx++, 0, 1, 2, 0);
        gSP1Triangle(gfx++, 3, 4, 5, 0);
        gSP1Triangle(gfx++, 6, 7, 8, 0);
        gSP1Triangle(gfx++, 9, 10, 11, 0);
        gSP1Triangle(gfx++, 12, 13, 14, 0);
    }

    gSPDisplayList(gfx++, &tiny_bubble_dl_0B006AB0) gSPEndDisplayList(gfx++);

    return gfxStart;
}

/**
 * Updates the environment effects (snow, flowers, bubbles)
 * and returns a display list drawing them.
 */
Gfx *envfx_update_particles(s32 mode, Vec3s marioPos, Vec3s camTo, Vec3s camFrom) {
    Gfx *gfx;

    if (get_dialog_id() != -1) {
        return NULL;
    }

    if (gEnvFxMode != 0 && mode != gEnvFxMode) {
        mode = 0;
    }

    if (mode >= ENVFX_BUBBLE_START) {
        gfx = envfx_update_bubbles(mode, marioPos, camTo, camFrom);
        return gfx;
    }

    if (gEnvFxMode == 0 && envfx_init_snow(mode) == 0) {
        return NULL;
    }

    switch (mode) {
        case ENVFX_MODE_NONE:
            envfx_cleanup_snow(gEnvFxBuffer);
            return NULL;

        case ENVFX_SNOW_NORMAL:
            gfx = envfx_update_snow(1, marioPos, camFrom, camTo);
            break;

        case ENVFX_SNOW_WATER:
            gfx = envfx_update_snow(2, marioPos, camFrom, camTo);
            break;

        case ENVFX_SNOW_BLIZZARD:
            gfx = envfx_update_snow(3, marioPos, camFrom, camTo);
            break;

        default:
            return NULL;
    }

    return gfx;
}
