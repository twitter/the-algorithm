#ifndef MOVING_TEXTURE_MACROS_H
#define MOVING_TEXTURE_MACROS_H

#include "game/moving_texture.h"

// From gMovingTextureIdList
#define TEXTURE_WATER            0
#define TEXTURE_MIST             1
#define TEXTURE_JRB_WATER        2
#define TEXTURE_UNK_WATER        3
#define TEXTURE_LAVA             4
#define TEX_QUICKSAND_SSL        5
#define TEX_PYRAMID_SAND_SSL     6
#define TEX_YELLOW_TRI_TTC       7

// Moving Texture rotations
#define ROTATE_CLOCKWISE         0
#define ROTATE_COUNTER_CLOCKWISE 1

// Moving texture load quad tris start
#define MOV_TEX_INIT_LOAD(amount) \
    amount, 0

// Short Triangle of moving texture with only 4 triangles with x and z
#define MOV_TEX_4_BOX_TRIS(x, z) \
    x, z

// Moving texture miniTri define texture from MovingTextureList
#define MOV_TEX_DEFINE(text) \
    text

// Moving texture start with speed
#define MOV_TEX_SPD(speed) \
    speed

// Rotation speed of moving texture
#define MOV_TEX_ROT_SPEED(rotspeed) \
    rotspeed

// Rotation scale of moving texture that goes back and forth
#define MOV_TEX_ROT_SCALE(rotscale) \
    rotscale

// Rotation of moving texture
#define MOV_TEX_ROT(rot) \
    rot

// Alpha of moving texture
#define MOV_TEX_ALPHA(alpha) \
    alpha

// Triangle of moving texture
#define MOV_TEX_TRIS(x, y, z, param1, param2) \
    x, y, z, param1, param2

// 2 Triangles of moving texture
#define MOV_TEX_ROT_TRIS(x, y, z, rotx, roty, rotz, param1, param2) \
    x, y, z, rotx, roty, rotz, param1, param2

// Triangle of moving texture with light
#define MOV_TEX_LIGHT_TRIS(x, y, z, light, param1, param2) \
    x, y, z, 0, light, 0, param1, param2

// End of moving texture load
#define MOV_TEX_END() \
    0

// End of moving texture load if movTexRotTris was used, only used in SSL
#define MOV_TEX_ROT_END() \
    0, 0

#endif // MOVING_TEXTURE_MACROS_H
