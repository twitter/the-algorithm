#ifndef _SHADOW_H
#define _SHADOW_H

#include <ultra64.h>
#include "types.h"

/**
 * Shadow types. Shadows are circles, squares, or hardcoded rectangles, and
 * can be composed of either 4 or 9 vertices.
 */
enum ShadowType
{
    SHADOW_CIRCLE_9_VERTS = 0,
    SHADOW_CIRCLE_4_VERTS = 1,
    SHADOW_CIRCLE_4_VERTS_FLAT_UNUSED = 2,
    SHADOW_SQUARE_PERMANENT = 10,
    SHADOW_SQUARE_SCALABLE = 11,
    SHADOW_SQUARE_TOGGLABLE = 12,
    /**
     * This defines an offset after which rectangular shadows with custom
     * widths and heights can be defined.
     */
    SHADOW_RECTANGLE_HARDCODED_OFFSET = 50,
    SHADOW_CIRCLE_PLAYER = 99
};

/**
 * Flag for if Mario is on a flying carpet.
 */
extern s8 sMarioOnFlyingCarpet;

/**
 * The surface type below the current shadow.
 */
extern s16 sSurfaceTypeBelowShadow;

/**
 * Flag for if the current shadow is above water or lava.
 */
extern s8 gShadowAboveWaterOrLava;

/**
 * Flag for if Mario is on ice or a flying carpet.
 */
extern s8 gMarioOnIceOrCarpet;

/**
 * Given the (x, y, z) location of an object, create a shadow below that object
 * with the given initial solidity and "shadowType" (described above).
 */
extern Gfx *create_shadow_below_xyz(
    f32 xPos, f32 yPos, f32 zPos, s16 shadowScale, u8 shadowSolidity, s8 shadowType);

#endif /* _SHADOW_H */
