#include <PR/ultratypes.h>

#include "sm64.h"
#include "area.h"
#include "engine/graph_node.h"
#include "engine/surface_collision.h"
#include "game_init.h"
#include "geo_misc.h"
#include "levels/castle_inside/header.h"
#include "levels/hmc/header.h"
#include "levels/ttm/header.h"
#include "mario.h"
#include "memory.h"
#include "moving_texture.h"
#include "object_list_processor.h"
#include "paintings.h"
#include "save_file.h"
#include "segment2.h"

/**
 * @file paintings.c
 *
 * Implements the rippling painting effect. Paintings are GraphNodes that exist without being connected
 * to any particular object.
 *
 * Paintings are defined in level data. Look at levels/castle_inside/painting.inc.c for examples.
 *
 * The ripple effect uses data that is split into several parts:
 *      The mesh positions are generated from a base mesh. See seg2_painting_triangle_mesh near the
 *          bottom of bin/segment2.c
 *
 *      The lighting for the ripple is also generated from a base table, seg2_painting_mesh_neighbor_tris
 *          in bin/segment2.c
 *
 *      Each painting's texture uses yet another table to map its texture to the mesh.
 *          These maps are in level data, see levels/castle_inside/painting.inc.c for example.
 *
 *      Finally, each painting has two display lists, normal and rippling, which are defined in the same
 *      level data file as the Painting itself. See levels/castle_inside/painting.inc.c.
 *
 *
 * Painting state machine:
 * Paintings spawn in the PAINTING_IDLE state
 *      From IDLE, paintings can change to PAINTING_RIPPLE or PAINTING_ENTERED
 *        - This state checks for ENTERED because if Mario waits long enough, a PROXIMITY painting could
 *          reset to IDLE
 *
 * Paintings in the PAINTING_RIPPLE state are passively rippling.
 *      For RIPPLE_TRIGGER_PROXIMITY paintings, this means Mario bumped the wall in front of the
 *          painting.
 *
 *      Paintings that use RIPPLE_TRIGGER_CONTINUOUS try to transition to this state as soon as possible,
 *          usually when Mario enters the room.
 *
 *      A PROXIMITY painting will automatically reset to IDLE if its ripple magnitude becomes small
 *          enough.
 *
 * Paintings in the PAINTING_ENTERED state have been entered by Mario.
 *      A CONTINUOUS painting will automatically reset to RIPPLE if its ripple magnitude becomes small
 *          enough.
 */

/**
 * Triggers a passive ripple on the left side of the painting.
 */
#define RIPPLE_LEFT 0x20

/**
 * Triggers a passive ripple in the middle the painting.
 */
#define RIPPLE_MIDDLE 0x10

/**
 * Triggers a passive ripple on the right side of the painting.
 */
#define RIPPLE_RIGHT 0x8

/**
 * Triggers an entry ripple on the left side of the painting.
 */
#define ENTER_LEFT 0x4

/**
 * Triggers an entry ripple in the middle of the painting.
 */
#define ENTER_MIDDLE 0x2

/**
 * Triggers an entry ripple on the right side of the painting.
 */
#define ENTER_RIGHT 0x1

/**
 * Use the 1/4th part of the painting that is nearest to Mario's current floor.
 */
#define NEAREST_4TH 30

/**
 * Use Mario's relative x position.
 * @see painting_mario_x
 */
#define MARIO_X 40

/**
 * Use the x center of the painting.
 */
#define MIDDLE_X 50

/**
 * Use Mario's relative y position.
 * @see painting_mario_y
 */
#define MARIO_Y 60

/**
 * Use Mario's relative z position.
 * @see painting_mario_z
 */
#define MARIO_Z 70

/**
 * Use the y center of the painting.
 */
#define MIDDLE_Y 80

/**
 * Does nothing to the timer.
 * Why -56 instead of false? Who knows.
 */
#define DONT_RESET -56

/**
 * Reset the timer to 0.
 */
#define RESET_TIMER 100

/// A copy of the type of floor Mario is standing on.
s16 gPaintingMarioFloorType;
// A copy of Mario's position
f32 gPaintingMarioXPos;
f32 gPaintingMarioYPos;
f32 gPaintingMarioZPos;

/**
 * When a painting is rippling, this mesh is generated each frame using the Painting's parameters.
 *
 * This mesh only contains the vertex positions and normals.
 * Paintings use an additional array to map textures to the mesh.
 */
struct PaintingMeshVertex *gPaintingMesh;

/**
 * The painting's surface normals, used to approximate each of the vertex normals (for gouraud shading).
 */
Vec3f *gPaintingTriNorms;

/**
 * The painting that is currently rippling. Only one painting can be rippling at once.
 */
struct Painting *gRipplingPainting;

/**
 * Whether the DDD painting is moved forward, should being moving backwards, or has already moved backwards.
 */
s8 gDddPaintingStatus;

struct Painting *sHmcPaintings[] = {
    &cotmc_painting,
    NULL,
};

struct Painting *sInsideCastlePaintings[] = {
    &bob_painting, &ccm_painting, &wf_painting,  &jrb_painting,      &lll_painting,
    &ssl_painting, &hmc_painting, &ddd_painting, &wdw_painting,      &thi_tiny_painting,
    &ttm_painting, &ttc_painting, &sl_painting,  &thi_huge_painting, NULL,
};

struct Painting *sTtmPaintings[] = {
    &ttm_slide_painting,
    NULL,
};

struct Painting **sPaintingGroups[] = {
    sHmcPaintings,
    sInsideCastlePaintings,
    sTtmPaintings,
};

s16 gPaintingUpdateCounter = 1;
s16 gLastPaintingUpdateCounter = 0;

/**
 * Stop paintings in paintingGroup from rippling if their id is different from *idptr.
 */
void stop_other_paintings(s16 *idptr, struct Painting *paintingGroup[]) {
    s16 index;
    s16 id = *idptr;

    index = 0;
    while (paintingGroup[index] != NULL) {
        struct Painting *painting = segmented_to_virtual(paintingGroup[index]);

        // stop all rippling except for the selected painting
        if (painting->id != id) {
            painting->state = 0;
        }
        index++;
    }
}

/**
 * @return Mario's y position inside the painting (bounded).
 */
f32 painting_mario_y(struct Painting *painting) {
    //! Unnecessary use of double constants
    // Add 50 to make the ripple closer to Mario's center of mass.
    f32 relY = gPaintingMarioYPos - painting->posY + 50.0;

    if (relY < 0.0) {
        relY = 0.0;
    } else if (relY > painting->size) {
        relY = painting->size;
    }
    return relY;
}

/**
 * @return Mario's z position inside the painting (bounded).
 */
f32 painting_mario_z(struct Painting *painting) {
    f32 relZ = painting->posZ - gPaintingMarioZPos;

    if (relZ < 0.0) {
        relZ = 0.0;
    } else if (relZ > painting->size) {
        relZ = painting->size;
    }
    return relZ;
}

/**
 * @return The y origin for the ripple, based on ySource.
 *         For floor paintings, the z-axis is treated as y.
 */
f32 painting_ripple_y(struct Painting *painting, s8 ySource) {
    switch (ySource) {
        case MARIO_Y:
            return painting_mario_y(painting); // normal wall paintings
            break;
        case MARIO_Z:
            return painting_mario_z(painting); // floor paintings use X and Z
            break;
        case MIDDLE_Y:
            return painting->size / 2.0; // some concentric ripples don't care about Mario
            break;
    }
#ifdef AVOID_UB
    return 0.0f;
#endif
}

/**
 * Return the quarter of the painting that is closest to the floor Mario entered.
 */
f32 painting_nearest_4th(struct Painting *painting) {
    f32 firstQuarter = painting->size / 4.0;       // 1/4 of the way across the painting
    f32 secondQuarter = painting->size / 2.0;      // 1/2 of the way across the painting
    f32 thirdQuarter = painting->size * 3.0 / 4.0; // 3/4 of the way across the painting

    if (painting->floorEntered & RIPPLE_LEFT) {
        return firstQuarter;
    } else if (painting->floorEntered & RIPPLE_MIDDLE) {
        return secondQuarter;
    } else if (painting->floorEntered & RIPPLE_RIGHT) {
        return thirdQuarter;

    // Same as ripple floors.
    } else if (painting->floorEntered & ENTER_LEFT) {
        return firstQuarter;
    } else if (painting->floorEntered & ENTER_MIDDLE) {
        return secondQuarter;
    } else if (painting->floorEntered & ENTER_RIGHT) {
        return thirdQuarter;
    }
#ifdef AVOID_UB
    return 0.0f;
#endif
}

/**
 * @return Mario's x position inside the painting (bounded).
 */
f32 painting_mario_x(struct Painting *painting) {
    f32 relX = gPaintingMarioXPos - painting->posX;

    if (relX < 0.0) {
        relX = 0.0;
    } else if (relX > painting->size) {
        relX = painting->size;
    }
    return relX;
}

/**
 * @return The x origin for the ripple, based on xSource.
 */
f32 painting_ripple_x(struct Painting *painting, s8 xSource) {
    switch (xSource) {
        case NEAREST_4TH: // normal wall paintings
            return painting_nearest_4th(painting);
            break;
        case MARIO_X: // horizontally placed paintings use X and Z
            return painting_mario_x(painting);
            break;
        case MIDDLE_X: // concentric rippling may not care about Mario
            return painting->size / 2.0;
            break;
    }
#ifdef AVOID_UB
    return 0.0f;
#endif
}

/**
 * Set the painting's state, causing it to start a passive ripple or a ripple from Mario entering.
 *
 * @param state The state to enter
 * @param painting,paintingGroup identifies the painting that is changing state
 * @param xSource,ySource what to use for the x and y origin of the ripple
 * @param resetTimer if 100, set the timer to 0
 */
void painting_state(s8 state, struct Painting *painting, struct Painting *paintingGroup[],
                    s8 xSource, s8 ySource, s8 resetTimer) {
    // make sure no other paintings are rippling
    stop_other_paintings(&painting->id, paintingGroup);

    // use a different set of variables depending on the state
    switch (state) {
        case PAINTING_RIPPLE:
            painting->currRippleMag    = painting->passiveRippleMag;
            painting->rippleDecay      = painting->passiveRippleDecay;
            painting->currRippleRate   = painting->passiveRippleRate;
            painting->dispersionFactor = painting->passiveDispersionFactor;
            break;

        case PAINTING_ENTERED:
            painting->currRippleMag    = painting->entryRippleMag;
            painting->rippleDecay      = painting->entryRippleDecay;
            painting->currRippleRate   = painting->entryRippleRate;
            painting->dispersionFactor = painting->entryDispersionFactor;
            break;
    }
    painting->state = state;
    painting->rippleX = painting_ripple_x(painting, xSource);
    painting->rippleY = painting_ripple_y(painting, ySource);
    gPaintingMarioYEntry = gPaintingMarioYPos;

    // Because true or false would be too simple...
    if (resetTimer == RESET_TIMER) {
        painting->rippleTimer = 0.0f;
    }
    gRipplingPainting = painting;
}

/**
 * Idle update function for wall paintings that use RIPPLE_TRIGGER_PROXIMITY.
 */
void wall_painting_proximity_idle(struct Painting *painting, struct Painting *paintingGroup[]) {
    // Check for Mario triggering a ripple
    if (painting->floorEntered & RIPPLE_LEFT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_MIDDLE) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_RIGHT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);

    // Check for Mario entering
    } else if (painting->floorEntered & ENTER_LEFT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & ENTER_MIDDLE) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & ENTER_RIGHT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    }
}

/**
 * Rippling update function for wall paintings that use RIPPLE_TRIGGER_PROXIMITY.
 */
void wall_painting_proximity_rippling(struct Painting *painting, struct Painting *paintingGroup[]) {
    if (painting->floorEntered & ENTER_LEFT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & ENTER_MIDDLE) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & ENTER_RIGHT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    }
}

/**
 * Idle update function for wall paintings that use RIPPLE_TRIGGER_CONTINUOUS.
 */
void wall_painting_continuous_idle(struct Painting *painting, struct Painting *paintingGroup[]) {
    // Check for Mario triggering a ripple
    if (painting->floorEntered & RIPPLE_LEFT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MIDDLE_X, MIDDLE_Y, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_MIDDLE) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MIDDLE_X, MIDDLE_Y, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_RIGHT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MIDDLE_X, MIDDLE_Y, RESET_TIMER);

    // Check for Mario entering
    } else if (painting->floorEntered & ENTER_LEFT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & ENTER_MIDDLE) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    } else if (painting->floorEntered & ENTER_RIGHT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, RESET_TIMER);
    }
}

/**
 * Rippling update function for wall paintings that use RIPPLE_TRIGGER_CONTINUOUS.
 */
void wall_painting_continuous_rippling(struct Painting *painting, struct Painting *paintingGroup[]) {
    if (painting->floorEntered & ENTER_LEFT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, DONT_RESET);
    } else if (painting->floorEntered & ENTER_MIDDLE) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, DONT_RESET);
    } else if (painting->floorEntered & ENTER_RIGHT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, NEAREST_4TH, MARIO_Y, DONT_RESET);
    }
}

/**
 * Idle update function for floor paintings that use RIPPLE_TRIGGER_PROXIMITY.
 *
 * No floor paintings use RIPPLE_TRIGGER_PROXIMITY in the game.
 */
void floor_painting_proximity_idle(struct Painting *painting, struct Painting *paintingGroup[]) {
    // Check for Mario triggering a ripple
    if (painting->floorEntered & RIPPLE_LEFT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_MIDDLE) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_RIGHT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);

    // Only check for Mario entering if he jumped below the surface
    } else if (painting->marioWentUnder) {
        if (painting->currFloor & ENTER_LEFT) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
        } else if (painting->currFloor & ENTER_MIDDLE) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
        } else if (painting->currFloor & ENTER_RIGHT) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
        }
    }
}

/**
 * Rippling update function for floor paintings that use RIPPLE_TRIGGER_PROXIMITY.
 *
 * No floor paintings use RIPPLE_TRIGGER_PROXIMITY in the game.
 */
void floor_painting_proximity_rippling(struct Painting *painting, struct Painting *paintingGroup[]) {
    if (painting->marioWentUnder) {
        if (painting->currFloor & ENTER_LEFT) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
        } else if (painting->currFloor & ENTER_MIDDLE) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
        } else if (painting->currFloor & ENTER_RIGHT) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
        }
    }
}

/**
 * Idle update function for floor paintings that use RIPPLE_TRIGGER_CONTINUOUS.
 *
 * Both floor paintings (HMC and CotMC) are hidden behind a door, which hides the ripple's start up.
 * The floor just inside the doorway is RIPPLE_LEFT, so the painting starts rippling as soon as Mario
 * enters the room.
 */
void floor_painting_continuous_idle(struct Painting *painting, struct Painting *paintingGroup[]) {
    // Check for Mario triggering a ripple
    if (painting->floorEntered & RIPPLE_LEFT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MIDDLE_X, MIDDLE_Y, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_MIDDLE) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MIDDLE_X, MIDDLE_Y, RESET_TIMER);
    } else if (painting->floorEntered & RIPPLE_RIGHT) {
        painting_state(PAINTING_RIPPLE, painting, paintingGroup, MIDDLE_X, MIDDLE_Y, RESET_TIMER);

    // Check for Mario entering
    } else if (painting->currFloor & ENTER_LEFT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
    } else if (painting->currFloor & ENTER_MIDDLE) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
    } else if (painting->currFloor & ENTER_RIGHT) {
        painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, RESET_TIMER);
    }
}

/**
 * Rippling update function for floor paintings that use RIPPLE_TRIGGER_CONTINUOUS.
 */
void floor_painting_continuous_rippling(struct Painting *painting, struct Painting *paintingGroup[]) {
    if (painting->marioWentUnder) {
        if (painting->currFloor & ENTER_LEFT) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, DONT_RESET);
        } else if (painting->currFloor & ENTER_MIDDLE) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, DONT_RESET);
        } else if (painting->currFloor & ENTER_RIGHT) {
            painting_state(PAINTING_ENTERED, painting, paintingGroup, MARIO_X, MARIO_Z, DONT_RESET);
        }
    }
}

/**
 * Check for Mario entering one of the special floors associated with the painting.
 */
void painting_update_floors(struct Painting *painting) {
    s16 paintingId = painting->id;
    s8 rippleLeft = 0;
    s8 rippleMiddle = 0;
    s8 rippleRight = 0;
    s8 enterLeft = 0;
    s8 enterMiddle = 0;
    s8 enterRight = 0;

    /* The area in front of every painting in the game (except HMC and CotMC, which   *\
    |* act a little differently) is made up of 3 special floor triangles with special *|
    |* (unique) surface types. This code checks which surface Mario is currently on   *|
    \* and sets a bitfield accordingly.                                               */

    // check if Mario's current floor is one of the special floors
    if (gPaintingMarioFloorType == paintingId * 3 + SURFACE_PAINTING_WOBBLE_A6) {
        rippleLeft = RIPPLE_LEFT;
    }
    if (gPaintingMarioFloorType == paintingId * 3 + SURFACE_PAINTING_WOBBLE_A7) {
        rippleMiddle = RIPPLE_MIDDLE;
    }
    if (gPaintingMarioFloorType == paintingId * 3 + SURFACE_PAINTING_WOBBLE_A8) {
        rippleRight = RIPPLE_RIGHT;
    }
    if (gPaintingMarioFloorType == paintingId * 3 + SURFACE_PAINTING_WARP_D3) {
        enterLeft = ENTER_LEFT;
    }
    if (gPaintingMarioFloorType == paintingId * 3 + SURFACE_PAINTING_WARP_D4) {
        enterMiddle = ENTER_MIDDLE;
    }
    if (gPaintingMarioFloorType == paintingId * 3 + SURFACE_PAINTING_WARP_D5) {
        enterRight = ENTER_RIGHT;
    }

    painting->lastFloor = painting->currFloor;
    // at most 1 of these will be nonzero;
    painting->currFloor = rippleLeft + rippleMiddle + rippleRight + enterLeft + enterMiddle + enterRight;

    // floorEntered is true iff currFloor is true and lastFloor is false
    // (Mario just entered the floor on this frame)
    painting->floorEntered = (painting->lastFloor ^ painting->currFloor) & painting->currFloor;

    painting->marioWasUnder = painting->marioIsUnder;
    // Check if Mario has fallen below the painting (used for floor paintings)
    if (gPaintingMarioYPos < painting->posY) {
        painting->marioIsUnder = TRUE;
    } else {
        painting->marioIsUnder = FALSE;
    }

    // Mario "went under" if he was not under last frame, but is under now
    painting->marioWentUnder = (painting->marioWasUnder ^ painting->marioIsUnder) & painting->marioIsUnder;
}

/**
 * Update the ripple's timer and magnitude, making it propagate outwards.
 *
 * Automatically changes the painting back to IDLE state (or RIPPLE for continuous paintings) if the
 * ripple's magnitude becomes small enough.
 */
void painting_update_ripple_state(struct Painting *painting) {
    if (gPaintingUpdateCounter != gLastPaintingUpdateCounter) {
        painting->currRippleMag *= painting->rippleDecay;

        //! After ~6.47 days, paintings with RIPPLE_TRIGGER_CONTINUOUS will increment this to
        //! 16777216 (1 << 24), at which point it will freeze (due to floating-point
        //! imprecision?) and the painting will stop rippling. This happens to HMC, DDD, and
        //! CotMC.
        painting->rippleTimer += 1.0;
    }
    if (painting->rippleTrigger == RIPPLE_TRIGGER_PROXIMITY) {
        // if the painting is barely rippling, make it stop rippling
        if (painting->currRippleMag <= 1.0) {
            painting->state = PAINTING_IDLE;
            gRipplingPainting = NULL;
        }
    } else if (painting->rippleTrigger == RIPPLE_TRIGGER_CONTINUOUS) {

        // if the painting is doing the entry ripple but the ripples are as small as those from the
        // passive ripple, make it do a passive ripple
        // If Mario goes below the surface but doesn't warp, the painting will eventually reset.
        if (painting->state == PAINTING_ENTERED && painting->currRippleMag <= painting->passiveRippleMag) {

            painting->state = PAINTING_RIPPLE;
            painting->currRippleMag = painting->passiveRippleMag;
            painting->rippleDecay = painting->passiveRippleDecay;
            painting->currRippleRate = painting->passiveRippleRate;
            painting->dispersionFactor = painting->passiveDispersionFactor;
        }
    }
}

/**
 * @return the ripple function at posX, posY
 * note that posX and posY correspond to a point on the face of the painting, not actual axes
 */
s16 calculate_ripple_at_point(struct Painting *painting, f32 posX, f32 posY) {
    /// Controls the peaks of the ripple.
    f32 rippleMag = painting->currRippleMag;
    /// Controls the ripple's frequency
    f32 rippleRate = painting->currRippleRate;
    /// Controls how fast the ripple spreads
    f32 dispersionFactor = painting->dispersionFactor;
    /// How far the ripple has spread
    f32 rippleTimer = painting->rippleTimer;
    /// x and y ripple origin
    f32 rippleX = painting->rippleX;
    f32 rippleY = painting->rippleY;

    f32 distanceToOrigin;
    f32 rippleDistance;

    posX *= painting->size / PAINTING_SIZE;
    posY *= painting->size / PAINTING_SIZE;
    distanceToOrigin = sqrtf((posX - rippleX) * (posX - rippleX) + (posY - rippleY) * (posY - rippleY));
    // A larger dispersionFactor makes the ripple spread slower
    rippleDistance = distanceToOrigin / dispersionFactor;
    if (rippleTimer < rippleDistance) {
        // if the ripple hasn't reached the point yet, make the point magnitude 0
        return 0;
    } else {
        // use a cosine wave to make the ripple go up and down,
        // scaled by the painting's ripple magnitude
        f32 rippleZ = rippleMag * cosf(rippleRate * (2 * M_PI) * (rippleTimer - rippleDistance));

        // round it to an int and return it
        return round_float(rippleZ);
    }
}

/**
 * If movable, return the ripple function at (posX, posY)
 * else return 0
 */
s16 ripple_if_movable(struct Painting *painting, s16 movable, s16 posX, s16 posY) {
    s16 rippleZ = 0;

    if (movable) {
        rippleZ = calculate_ripple_at_point(painting, posX, posY);
    }
    return rippleZ;
}

/**
 * Allocates and generates a mesh for the rippling painting effect by modifying the passed in `mesh`
 * based on the painting's current ripple state.
 *
 * The `mesh` table describes the location of mesh vertices, whether they move when rippling, and what
 * triangles they belong to.
 *
 * The static mesh passed in is organized into two lists. This function only uses the first list,
 * painting_calculate_triangle_normals below uses the second one.
 *
 * The first list describes the vertices in this format:
 *      numVertices
 *      v0 x, v0 y, movable
 *      ...
 *      vN x, vN y, movable
 *      Where x and y are from 0 to PAINTING_SIZE, movable is 0 or 1.
 *
 * The mesh used in game, seg2_painting_triangle_mesh, is in bin/segment2.c.
 */
void painting_generate_mesh(struct Painting *painting, s16 *mesh, s16 numTris) {
    s16 i;

    gPaintingMesh = mem_pool_alloc(gEffectsMemoryPool, numTris * sizeof(struct PaintingMeshVertex));
    if (gPaintingMesh == NULL) {
    }
    // accesses are off by 1 since the first entry is the number of vertices
    for (i = 0; i < numTris; i++) {
        gPaintingMesh[i].pos[0] = mesh[i * 3 + 1];
        gPaintingMesh[i].pos[1] = mesh[i * 3 + 2];
        // The "z coordinate" of each vertex in the mesh is either 1 or 0. Instead of being an
        // actual coordinate, it just determines whether the vertex moves
        gPaintingMesh[i].pos[2] = ripple_if_movable(painting, mesh[i * 3 + 3],
                                                    gPaintingMesh[i].pos[0], gPaintingMesh[i].pos[1]);
    }
}

/**
 * Calculate the surface normals of each triangle in the generated ripple mesh.
 *
 * The static mesh passed in is organized into two lists. This function uses the second list,
 * painting_generate_mesh above uses the first one.
 *
 * The second list in `mesh` describes the mesh's triangles in this format:
 *      numTris
 *      tri0 v0, tri0 v1, tri0 v2
 *      ...
 *      triN v0, triN v1, triN v2
 *      Where each v0, v1, v2 is an index into the first list in `mesh`.
 *
 * The mesh used in game, seg2_painting_triangle_mesh, is in bin/segment2.c.
 */
void painting_calculate_triangle_normals(s16 *mesh, s16 numVtx, s16 numTris) {
    s16 i;

    gPaintingTriNorms = mem_pool_alloc(gEffectsMemoryPool, numTris * sizeof(Vec3f));
    if (gPaintingTriNorms == NULL) {
    }
    for (i = 0; i < numTris; i++) {
        s16 tri = numVtx * 3 + i * 3 + 2; // Add 2 because of the 2 length entries preceding the list
        s16 v0 = mesh[tri];
        s16 v1 = mesh[tri + 1];
        s16 v2 = mesh[tri + 2];

        f32 x0 = gPaintingMesh[v0].pos[0];
        f32 y0 = gPaintingMesh[v0].pos[1];
        f32 z0 = gPaintingMesh[v0].pos[2];

        f32 x1 = gPaintingMesh[v1].pos[0];
        f32 y1 = gPaintingMesh[v1].pos[1];
        f32 z1 = gPaintingMesh[v1].pos[2];

        f32 x2 = gPaintingMesh[v2].pos[0];
        f32 y2 = gPaintingMesh[v2].pos[1];
        f32 z2 = gPaintingMesh[v2].pos[2];

        // Cross product to find each triangle's normal vector
        gPaintingTriNorms[i][0] = (y1 - y0) * (z2 - z1) - (z1 - z0) * (y2 - y1);
        gPaintingTriNorms[i][1] = (z1 - z0) * (x2 - x1) - (x1 - x0) * (z2 - z1);
        gPaintingTriNorms[i][2] = (x1 - x0) * (y2 - y1) - (y1 - y0) * (x2 - x1);
    }
}

/**
 * Rounds a floating-point component of a normal vector to an s8 by multiplying it by 127 or 128 and
 * rounding away from 0.
 */
s8 normalize_component(f32 comp) {
    s8 rounded;

    if (comp > 0.0) {
        rounded = comp * 127.0 + 0.5; // round up
    } else if (comp < 0.0) {
        rounded = comp * 128.0 - 0.5; // round down
    } else {
        rounded = 0;                  // don't round 0
    }
    return rounded;
}

/**
 * Approximates the painting mesh's vertex normals by averaging the normals of all triangles sharing a
 * vertex. Used for Gouraud lighting.
 *
 * After each triangle's surface normal is calculated, the `neighborTris` table describes which triangles
 * each vertex should use when calculating the average normal vector.
 *
 * The table is a list of entries in this format:
 *      numNeighbors, tri0, tri1, ..., triN
 *
 *      Where each 'tri' is an index into gPaintingTriNorms.
 *      Entry i in `neighborTris` corresponds to the vertex at gPaintingMesh[i]
 *
 * The table used in game, seg2_painting_mesh_neighbor_tris, is in bin/segment2.c.
 */
void painting_average_vertex_normals(s16 *neighborTris, s16 numVtx) {
    UNUSED s16 unused;
    s16 tri;
    s16 i;
    s16 j;
    s16 neighbors;
    s16 entry = 0;

    for (i = 0; i < numVtx; i++) {
        f32 nx = 0.0f;
        f32 ny = 0.0f;
        f32 nz = 0.0f;
        f32 nlen;

        // The first number of each entry is the number of adjacent tris
        neighbors = neighborTris[entry];
        for (j = 0; j < neighbors; j++) {
            tri = neighborTris[entry + j + 1];
            nx += gPaintingTriNorms[tri][0];
            ny += gPaintingTriNorms[tri][1];
            nz += gPaintingTriNorms[tri][2];
        }
        // Move to the next vertex's entry
        entry += neighbors + 1;

        // average the surface normals from each neighboring tri
        nx /= neighbors;
        ny /= neighbors;
        nz /= neighbors;
        nlen = sqrtf(nx * nx + ny * ny + nz * nz);

        if (nlen == 0.0) {
            gPaintingMesh[i].norm[0] = 0;
            gPaintingMesh[i].norm[1] = 0;
            gPaintingMesh[i].norm[2] = 0;
        } else {
            gPaintingMesh[i].norm[0] = normalize_component(nx / nlen);
            gPaintingMesh[i].norm[1] = normalize_component(ny / nlen);
            gPaintingMesh[i].norm[2] = normalize_component(nz / nlen);
        }
    }
}

/**
 * Creates a display list that draws the rippling painting, with 'img' mapped to the painting's mesh,
 * using 'textureMap'.
 *
 * If the textureMap doesn't describe the whole mesh, then multiple calls are needed to draw the whole
 * painting.
 */
Gfx *render_painting(u8 *img, s16 tWidth, s16 tHeight, s16 *textureMap, s16 mapVerts, s16 mapTris, u8 alpha) {
    s16 group;
    s16 map;
    s16 triGroup;
    s16 mapping;
    s16 meshVtx;
    s16 tx;
    s16 ty;

    // We can fit 15 (16 / 3) vertices in the RSP's vertex buffer.
    // Group triangles by 5, with one remainder group.
    s16 triGroups = mapTris / 5;
    s16 remGroupTris = mapTris % 5;
    s16 numVtx = mapTris * 3;

    s16 commands = triGroups * 2 + remGroupTris + 7;
    Vtx *verts = alloc_display_list(numVtx * sizeof(Vtx));
    Gfx *dlist = alloc_display_list(commands * sizeof(Gfx));
    Gfx *gfx = dlist;

    if (verts == NULL || dlist == NULL) {
    }

    gLoadBlockTexture(gfx++, tWidth, tHeight, G_IM_FMT_RGBA, img);

    // Draw the groups of 5 first
    for (group = 0; group < triGroups; group++) {

        // The triangle groups are the second part of the texture map.
        // Each group is a list of 15 mappings
        triGroup = mapVerts * 3 + group * 15 + 2;
        for (map = 0; map < 15; map++) {
            // The mapping is just an index into the earlier part of the textureMap
            // Some mappings are repeated, for example, when multiple triangles share a vertex
            mapping = textureMap[triGroup + map];

            // The first entry is the ID of the vertex in the mesh
            meshVtx = textureMap[mapping * 3 + 1];

            // The next two are the texture coordinates for that vertex
            tx = textureMap[mapping * 3 + 2];
            ty = textureMap[mapping * 3 + 3];

            // Map the texture and place it in the verts array
            make_vertex(verts, group * 15 + map, gPaintingMesh[meshVtx].pos[0], gPaintingMesh[meshVtx].pos[1],
                        gPaintingMesh[meshVtx].pos[2], tx, ty, gPaintingMesh[meshVtx].norm[0],
                        gPaintingMesh[meshVtx].norm[1], gPaintingMesh[meshVtx].norm[2], alpha);
        }

        // Load the vertices and draw the 5 triangles
        gSPVertex(gfx++, VIRTUAL_TO_PHYSICAL(verts + group * 15), 15, 0);
        gSPDisplayList(gfx++, dl_paintings_draw_ripples);
    }

    // One group left with < 5 triangles
    triGroup = mapVerts * 3 + triGroups * 15 + 2;
    // Map the texture to the triangles
    for (map = 0; map < remGroupTris * 3; map++) {
        mapping = textureMap[triGroup + map];
        meshVtx = textureMap[mapping * 3 + 1];
        tx = textureMap[mapping * 3 + 2];
        ty = textureMap[mapping * 3 + 3];
        make_vertex(verts, triGroups * 15 + map, gPaintingMesh[meshVtx].pos[0], gPaintingMesh[meshVtx].pos[1],
                    gPaintingMesh[meshVtx].pos[2], tx, ty, gPaintingMesh[meshVtx].norm[0],
                    gPaintingMesh[meshVtx].norm[1], gPaintingMesh[meshVtx].norm[2], alpha);
    }

    // Draw the triangles individually
    gSPVertex(gfx++, VIRTUAL_TO_PHYSICAL(verts + triGroups * 15), remGroupTris * 3, 0);
    for (group = 0; group < remGroupTris; group++) {
        gSP1Triangle(gfx++, group * 3, group * 3 + 1, group * 3 + 2, 0);
    }

    gSPEndDisplayList(gfx);
    return dlist;
}

/**
 * Orient the painting mesh for rendering.
 */
Gfx *painting_model_view_transform(struct Painting *painting) {
    f32 sizeRatio = painting->size / PAINTING_SIZE;
    Mtx *rotX = alloc_display_list(sizeof(Mtx));
    Mtx *rotY = alloc_display_list(sizeof(Mtx));
    Mtx *translate = alloc_display_list(sizeof(Mtx));
    Mtx *scale = alloc_display_list(sizeof(Mtx));
    Gfx *dlist = alloc_display_list(5 * sizeof(Gfx));
    Gfx *gfx = dlist;

    if (rotX == NULL || rotY == NULL || translate == NULL || dlist == NULL) {
    }

    guTranslate(translate, painting->posX, painting->posY, painting->posZ);
    guRotate(rotX, painting->pitch, 1.0f, 0.0f, 0.0f);
    guRotate(rotY, painting->yaw, 0.0f, 1.0f, 0.0f);
    guScale(scale, sizeRatio, sizeRatio, sizeRatio);

    gSPMatrix(gfx++, translate, G_MTX_MODELVIEW | G_MTX_MUL | G_MTX_PUSH);
    gSPMatrix(gfx++, rotX,      G_MTX_MODELVIEW | G_MTX_MUL | G_MTX_NOPUSH);
    gSPMatrix(gfx++, rotY,      G_MTX_MODELVIEW | G_MTX_MUL | G_MTX_NOPUSH);
    gSPMatrix(gfx++, scale,     G_MTX_MODELVIEW | G_MTX_MUL | G_MTX_NOPUSH);
    gSPEndDisplayList(gfx);

    return dlist;
}

/**
 * Ripple a painting that has 1 or more images that need to be mapped
 */
Gfx *painting_ripple_image(struct Painting *painting) {
    s16 meshVerts;
    s16 meshTris;
    s16 i;
    s16 *textureMap;
    s16 imageCount = painting->imageCount;
    s16 tWidth = painting->textureWidth;
    s16 tHeight = painting->textureHeight;
    s16 **textureMaps = segmented_to_virtual(painting->textureMaps);
    u8 **textures = segmented_to_virtual(painting->textureArray);
    Gfx *dlist = alloc_display_list((imageCount + 6) * sizeof(Gfx));
    Gfx *gfx = dlist;

    if (dlist == NULL) {
        return dlist;
    }

    gSPDisplayList(gfx++, painting_model_view_transform(painting));
    gSPDisplayList(gfx++, dl_paintings_rippling_begin);
    gSPDisplayList(gfx++, painting->rippleDisplayList);

    // Map each image to the mesh's vertices
    for (i = 0; i < imageCount; i++) {
        textureMap = segmented_to_virtual(textureMaps[i]);
        meshVerts = textureMap[0];
        meshTris = textureMap[meshVerts * 3 + 1];
        gSPDisplayList(gfx++, render_painting(textures[i], tWidth, tHeight, textureMap, meshVerts, meshTris, painting->alpha));
    }

    // Update the ripple, may automatically reset the painting's state.
    painting_update_ripple_state(painting);

    gSPPopMatrix(gfx++, G_MTX_MODELVIEW);
    gSPDisplayList(gfx++, dl_paintings_rippling_end);
    gSPEndDisplayList(gfx);
    return dlist;
}

/**
 * Ripple a painting that has 1 "environment map" texture.
 */
Gfx *painting_ripple_env_mapped(struct Painting *painting) {
    s16 meshVerts;
    s16 meshTris;
    s16 *textureMap;
    s16 tWidth = painting->textureWidth;
    s16 tHeight = painting->textureHeight;
    s16 **textureMaps = segmented_to_virtual(painting->textureMaps);
    u8 **tArray = segmented_to_virtual(painting->textureArray);
    Gfx *dlist = alloc_display_list(7 * sizeof(Gfx));
    Gfx *gfx = dlist;

    if (dlist == NULL) {
        return dlist;
    }

    gSPDisplayList(gfx++, painting_model_view_transform(painting));
    gSPDisplayList(gfx++, dl_paintings_env_mapped_begin);
    gSPDisplayList(gfx++, painting->rippleDisplayList);

    // Map the image to the mesh's vertices
    textureMap = segmented_to_virtual(textureMaps[0]);
    meshVerts = textureMap[0];
    meshTris = textureMap[meshVerts * 3 + 1];
    gSPDisplayList(gfx++, render_painting(tArray[0], tWidth, tHeight, textureMap, meshVerts, meshTris, painting->alpha));

    // Update the ripple, may automatically reset the painting's state.
    painting_update_ripple_state(painting);

    gSPPopMatrix(gfx++, G_MTX_MODELVIEW);
    gSPDisplayList(gfx++, dl_paintings_env_mapped_end);
    gSPEndDisplayList(gfx);
    return dlist;
}

/**
 * Generates a mesh, calculates vertex normals for lighting, and renders a rippling painting.
 * The mesh and vertex normals are regenerated and freed every frame.
 */
Gfx *display_painting_rippling(struct Painting *painting) {
    s16 *mesh = segmented_to_virtual(seg2_painting_triangle_mesh);
    s16 *neighborTris = segmented_to_virtual(seg2_painting_mesh_neighbor_tris);
    s16 numVtx = mesh[0];
    s16 numTris = mesh[numVtx * 3 + 1];
    Gfx *dlist;

    // Generate the mesh and its lighting data
    painting_generate_mesh(painting, mesh, numVtx);
    painting_calculate_triangle_normals(mesh, numVtx, numTris);
    painting_average_vertex_normals(neighborTris, numVtx);

    // Map the painting's texture depending on the painting's texture type.
    switch (painting->textureType) {
        case PAINTING_IMAGE:
            dlist = painting_ripple_image(painting);
            break;
        case PAINTING_ENV_MAP:
            dlist = painting_ripple_env_mapped(painting);
            break;
    }

    // The mesh data is freed every frame.
    mem_pool_free(gEffectsMemoryPool, gPaintingMesh);
    mem_pool_free(gEffectsMemoryPool, gPaintingTriNorms);
    return dlist;
}

/**
 * Render a normal painting.
 */
Gfx *display_painting_not_rippling(struct Painting *painting) {
    Gfx *dlist = alloc_display_list(4 * sizeof(Gfx));
    Gfx *gfx = dlist;

    if (dlist == NULL) {
        return dlist;
    }
    gSPDisplayList(gfx++, painting_model_view_transform(painting));
    gSPDisplayList(gfx++, painting->normalDisplayList);
    gSPPopMatrix(gfx++, G_MTX_MODELVIEW);
    gSPEndDisplayList(gfx);
    return dlist;
}

/**
 * Clear Mario-related state and clear gRipplingPainting.
 */
void reset_painting(struct Painting *painting) {
    painting->lastFloor = 0;
    painting->currFloor = 0;
    painting->floorEntered = 0;
    painting->marioWasUnder = 0;
    painting->marioIsUnder = 0;
    painting->marioWentUnder = 0;

    gRipplingPainting = NULL;

#ifdef NO_SEGMENTED_MEMORY
    // Make sure all variables are reset correctly.
    // With segmented memory the segments that contain the relevant
    // Painting structs are reloaded from ROM upon level load.
    painting->state = PAINTING_IDLE;
    painting->currRippleMag = 0.0f;
    painting->rippleDecay = 1.0f;
    painting->currRippleRate = 0.0f;
    painting->dispersionFactor = 0.0f;
    painting->rippleTimer = 0.0f;
    painting->rippleX = 0.0f;
    painting->rippleY = 0.0f;
    if (painting == &ddd_painting) {
        // Move DDD painting to initial position, in case the animation
        // that moves the painting stops during level unload.
        painting->posX = 3456.0f;
    }
#endif
}

/**
 * Controls the x coordinate of the DDD painting.
 *
 * Before Mario gets the "Board Bowser's Sub" star in DDD, the painting spawns at frontPos.
 *
 * If Mario just got the star, the painting's x coordinate moves to backPos at a rate of `speed` units.
 *
 * When the painting reaches backPos, a save flag is set so that the painting will spawn at backPos
 * whenever it loads.
 *
 * This function also sets gDddPaintingStatus, which controls the warp:
 *  0 (0b00): set x coordinate to frontPos
 *  2 (0b10): set x coordinate to backPos
 *  3 (0b11): same as 2. Bit 0 is ignored
 */
void move_ddd_painting(struct Painting *painting, f32 frontPos, f32 backPos, f32 speed) {
    // Obtain the DDD star flags
    u32 dddFlags = save_file_get_star_flags(gCurrSaveFileNum - 1, COURSE_NUM_TO_INDEX(COURSE_DDD));
    // Get the other save file flags
    u32 saveFileFlags = save_file_get_flags();
    // Find out whether Board Bowser's Sub was collected
    u32 bowsersSubBeaten = dddFlags & BOARD_BOWSERS_SUB;
    // Check whether DDD has already moved back
    u32 dddBack = saveFileFlags & SAVE_FLAG_DDD_MOVED_BACK;

    if (!bowsersSubBeaten && !dddBack) {
        // If we haven't collected the star or moved the painting, put the painting at the front
        painting->posX = frontPos;
        gDddPaintingStatus = 0;
    } else if (bowsersSubBeaten && !dddBack) {
        // If we've collected the star but not moved the painting back,
        // Each frame, move the painting by a certain speed towards the back area.
        painting->posX += speed;
        gDddPaintingStatus = BOWSERS_SUB_BEATEN;
        if (painting->posX >= backPos) {
            painting->posX = backPos;
            // Tell the save file that we've moved DDD back.
            save_file_set_flags(SAVE_FLAG_DDD_MOVED_BACK);
        }
    } else if (bowsersSubBeaten && dddBack) {
        // If the painting has already moved back, place it in the back position.
        painting->posX = backPos;
        gDddPaintingStatus = BOWSERS_SUB_BEATEN | DDD_BACK;
    }
}

/**
 * Set the painting's node's layer based on its alpha
 */
void set_painting_layer(struct GraphNodeGenerated *gen, struct Painting *painting) {
    switch (painting->alpha) {
        case 0xFF: // Opaque
            gen->fnNode.node.flags = (gen->fnNode.node.flags & 0xFF) | (LAYER_OPAQUE << 8);
            break;
        default:
            gen->fnNode.node.flags = (gen->fnNode.node.flags & 0xFF) | (LAYER_TRANSPARENT << 8);
            break;
    }
}

/**
 * Display either a normal painting or a rippling one depending on the painting's ripple status
 */
Gfx *display_painting(struct Painting *painting) {
    switch (painting->state) {
        case PAINTING_IDLE:
            return display_painting_not_rippling(painting);
            break;
        default:
            return display_painting_rippling(painting);
            break;
    }
}

/**
 * Update function for wall paintings.
 * Calls a different update function depending on the painting's ripple trigger and current state.
 */
void wall_painting_update(struct Painting *painting, struct Painting *paintingGroup[]) {
    if (painting->rippleTrigger == RIPPLE_TRIGGER_PROXIMITY) {
        switch (painting->state) {
            case PAINTING_IDLE:
                wall_painting_proximity_idle(painting, paintingGroup);
                break;
            case PAINTING_RIPPLE:
                wall_painting_proximity_rippling(painting, paintingGroup);
                break;
        }
    } else if (painting->rippleTrigger == RIPPLE_TRIGGER_CONTINUOUS) {
        switch (painting->state) {
            case PAINTING_IDLE:
                wall_painting_continuous_idle(painting, paintingGroup);
                break;
            case PAINTING_RIPPLE:
                wall_painting_continuous_rippling(painting, paintingGroup);
                break;
        }
    }
}

/**
 * Update function for floor paintings (HMC and CotMC)
 * Calls a different update function depending on the painting's ripple trigger and current state.
 *
 * No floor paintings use RIPPLE_TRIGGER_PROXIMITY in the game.
 */
void floor_painting_update(struct Painting *painting, struct Painting *paintingGroup[]) {
    if (painting->rippleTrigger == RIPPLE_TRIGGER_PROXIMITY) {
        switch (painting->state) {
            case PAINTING_IDLE:
                floor_painting_proximity_idle(painting, paintingGroup);
                break;
            case PAINTING_RIPPLE:
                floor_painting_proximity_rippling(painting, paintingGroup);
                break;
        }
    } else if (painting->rippleTrigger == RIPPLE_TRIGGER_CONTINUOUS) {
        switch (painting->state) {
            case PAINTING_IDLE:
                floor_painting_continuous_idle(painting, paintingGroup);
                break;
            case PAINTING_RIPPLE:
                floor_painting_continuous_rippling(painting, paintingGroup);
                break;
        }
    }
}

/**
 * Render and update the painting whose id and group matches the values in the GraphNode's parameter.
 * Use PAINTING_ID(id, group) to set the right parameter in a level's geo layout.
 */
Gfx *geo_painting_draw(s32 callContext, struct GraphNode *node, UNUSED void *context) {
    struct GraphNodeGenerated *gen = (struct GraphNodeGenerated *) node;
    s32 group = (gen->parameter >> 8) & 0xFF;
    s32 id = gen->parameter & 0xFF;
    Gfx *paintingDlist = NULL;
    struct Painting **paintingGroup = sPaintingGroups[group];
    struct Painting *painting = segmented_to_virtual(paintingGroup[id]);

    if (callContext != GEO_CONTEXT_RENDER) {
        reset_painting(painting);
    } else if (callContext == GEO_CONTEXT_RENDER) {

        // Update the ddd painting before drawing
        if (group == 1 && id == PAINTING_ID_DDD) {
            move_ddd_painting(painting, 3456.0f, 5529.6f, 20.0f);
        }

        // Determine if the painting is transparent
        set_painting_layer(gen, painting);

        // Draw before updating
        paintingDlist = display_painting(painting);

        // Update the painting
        painting_update_floors(painting);
        switch ((s16) painting->pitch) {
            // only paintings with 0 pitch are treated as walls
            case 0:
                wall_painting_update(painting, paintingGroup);
                break;
            default:
                floor_painting_update(painting, paintingGroup);
                break;
        }
    }
    return paintingDlist;
}

/**
 * Update the painting system's local copy of Mario's current floor and position.
 */
Gfx *geo_painting_update(s32 callContext, UNUSED struct GraphNode *node, UNUSED Mat4 c) {
    struct Surface *surface;

    // Reset the update counter
    if (callContext != GEO_CONTEXT_RENDER) {
        gLastPaintingUpdateCounter = gAreaUpdateCounter - 1;
        gPaintingUpdateCounter = gAreaUpdateCounter;
    } else {
        gLastPaintingUpdateCounter = gPaintingUpdateCounter;
        gPaintingUpdateCounter = gAreaUpdateCounter;

        // Store Mario's floor and position
        find_floor(gMarioObject->oPosX, gMarioObject->oPosY, gMarioObject->oPosZ, &surface);
        gPaintingMarioFloorType = surface->type;
        gPaintingMarioXPos = gMarioObject->oPosX;
        gPaintingMarioYPos = gMarioObject->oPosY;
        gPaintingMarioZPos = gMarioObject->oPosZ;
    }
    return NULL;
}
