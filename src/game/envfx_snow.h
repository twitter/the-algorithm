#ifndef ENVFX_SNOW_H
#define ENVFX_SNOW_H

#include <ultra64.h>
#include "types.h"

#define ENVFX_MODE_NONE     0  // no effects
#define ENVFX_SNOW_NORMAL   1  // CCM, SL
#define ENVFX_SNOW_WATER    2  // Secret Aquarium, Sunken Ship
#define ENVFX_SNOW_BLIZZARD 3  // unused

#define ENVFX_BUBBLE_START      10 // Separates snow effects and flower/bubble effects

#define ENVFX_FLOWERS           11 // unused
#define ENVFX_LAVA_BUBBLES      12 // LLL, BitFS, Bowser 2
#define ENVFX_WHIRLPOOL_BUBBLES 13 // DDD
#define ENVFX_JETSTREAM_BUBBLES 14 // JRB, DDD (submarine area)

struct EnvFxParticle {
    s8 isAlive;
    //s8 filler01;
    s16 animFrame; // lava bubbles and flowers have frame animations
    s32 xPos;
    s32 yPos;
    s32 zPos;
    s32 angleAndDist[2]; // for whirpools, [0] = angle from center, [1] = distance from center
    s32 unusedBubbleVar; // set to zero for bubbles when respawning, never used elsewhere
    s32 bubbleY; // for Bubbles, yPos is always set to this
    s8 filler20[56 - 0x20];
};

extern s8 gEnvFxMode;
extern UNUSED s32 D_80330644;
extern struct SnowFlakeVertex gSnowFlakeVertex1;
extern struct SnowFlakeVertex gSnowFlakeVertex2;
extern struct SnowFlakeVertex gSnowFlakeVertex3;

extern struct EnvFxParticle *gEnvFxBuffer;
extern Vec3i gSnowCylinderLastPos;
extern s16 gSnowParticleCount;
extern s16 gSnowParticleMaxCount;

Gfx *envfx_update_particles(s32 snowMode, Vec3s marioPos, Vec3s camTo, Vec3s camFrom);
void orbit_from_positions(Vec3s from, Vec3s to, s16 *radius, s16 *pitch, s16 *yaw);
void rotate_triangle_vertices(Vec3s vertex1, Vec3s vertex2, Vec3s vertex3, s16 pitch, s16 yaw);

#endif // ENVFX_SNOW_H
