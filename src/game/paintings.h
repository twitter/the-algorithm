#ifndef PAINTINGS_H
#define PAINTINGS_H

#include "types.h"

/**
 * @file paintings.h
 */

/// Use to properly set a GraphNodeGenerated's parameter to point to the right painting
#define PAINTING_ID(id, grp) id | (grp << 8)

/// The default painting side length
#define PAINTING_SIZE 614.0

#define PAINTING_ID_DDD 7

#define BOARD_BOWSERS_SUB 1

#define BOWSERS_SUB_BEATEN 0x2
#define DDD_BACK 0x1

#define PAINTING_IDLE 0
#define PAINTING_RIPPLE 1
#define PAINTING_ENTERED 2

#define RIPPLE_TRIGGER_PROXIMITY 10
#define RIPPLE_TRIGGER_CONTINUOUS 20

/// Painting that uses 1 or more images as a texture
#define PAINTING_IMAGE 0
/// Painting that has one texture used for an environment map effect
#define PAINTING_ENV_MAP 1

struct Painting
{
    s16 id;
    /// How many images should be drawn when the painting is rippling.
    s8 imageCount;
    /// Either PAINTING_IMAGE or PAINTING_ENV_MAP
    s8 textureType;

    /// The floor mario was on last frame
    s8 lastFloor;
    /// The floor mario is currently on
    s8 currFloor;
    /// The floor mario just entered
    s8 floorEntered;

    /// The painting's state, see top of paintings.c
    s8 state;

    /// The painting's rotation
    f32 pitch;
    f32 yaw;

    /// The painting's position
    f32 posX;
    f32 posY;
    f32 posZ;

    /// Controls how high the peaks of the ripple are.
    f32 currRippleMag;
    f32 passiveRippleMag;
    f32 entryRippleMag;

    /// Multiplier that controls how fast the ripple regresses to the IDLE state.
    f32 rippleDecay;
    f32 passiveRippleDecay;
    f32 entryRippleDecay;

    /// Controls the ripple's frequency
    f32 currRippleRate;
    f32 passiveRippleRate;
    f32 entryRippleRate;

    /// The rate at which the magnitude of the ripple decreases as you move farther from the central
    /// point of the ripple
    f32 dispersionFactor;
    f32 passiveDispersionFactor;
    f32 entryDispersionFactor;

    /// How far the ripple has spread
    f32 rippleTimer;

    /// The x and y origin of the ripple
    f32 rippleX;
    f32 rippleY;

    /// Display list used when the painting is normal.
    const Gfx *normalDisplayList;
    /// Data used to map the texture to the mesh
    const s16 *const *textureMaps;

    // Texture data
    const u8 *const *textureArray;
    s16 textureWidth;
    s16 textureHeight;

    /// Display list used when the painting is rippling.
    const Gfx *rippleDisplayList;
    /// Controls when a passive ripple starts. RIPPLE_TRIGGER_CONTINUOUS or RIPPLE_TRIGGER_PROXIMITY.
    s8 rippleTrigger;

    /// The painting's transparency. Determines what layer the painting is in.
    u8 alpha;

    /// True if mario was under the painting's y coordinate last frame
    s8 marioWasUnder;
    /// True if mario is currently under the painting's y coordinate
    s8 marioIsUnder;
    /// True if mario just went under the painting's y coordinate on this frame
    s8 marioWentUnder;

    /// Uniformly scales the painting to a multiple of PAINTING_SIZE.
    /// By default a painting is 614.0 x 614.0
    f32 size;
};

extern struct Painting cotmc_painting;

extern struct Painting bob_painting;
extern struct Painting ccm_painting;
extern struct Painting wf_painting;
extern struct Painting jrb_painting;
extern struct Painting lll_painting;
extern struct Painting ssl_painting;
extern struct Painting hmc_painting;
extern struct Painting ddd_painting;
extern struct Painting wdw_painting;
extern struct Painting thi_tiny_painting;
extern struct Painting ttm_painting;
extern struct Painting ttc_painting;
extern struct Painting sl_painting;
extern struct Painting thi_huge_painting;

extern struct Painting ttm_slide_painting;

extern u16 gAreaUpdateCounter;
extern f32 gPaintingMarioYEntry;

// defined in file
extern s16 gPaintingMarioFloorType;
extern f32 gPaintingMarioXPos, gPaintingMarioYPos, gPaintingMarioZPos;

/**
 * Contains the position and normal of a vertex in the painting's generated mesh.
 */
struct PaintingMeshVertex {
    /*0x00*/ s16 pos[3];
    /*0x06*/ s8 norm[3];
};

extern struct PaintingMeshVertex *gPaintingMesh;
extern Vec3f *gPaintingTriNorms;
extern struct Painting *gRipplingPainting;
extern s8 gDddPaintingStatus;

extern Gfx *geo_painting_draw(s32 run, struct GraphNode *node, void *context);
extern Gfx *geo_painting_update(s32 run, struct GraphNode *node, f32 c[4][4]);

#endif /* PAINTINGS_H */
