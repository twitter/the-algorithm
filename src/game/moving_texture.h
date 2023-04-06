#ifndef _MOVING_TEXTURE_H
#define _MOVING_TEXTURE_H

#include "types.h"

#define ROTATE_CLOCKWISE         0
#define ROTATE_COUNTER_CLOCKWISE 1

/// Ids for textures used for moving textures
enum MovtexRectTextureId
{
    TEXTURE_WATER,
    TEXTURE_MIST,
    TEXTURE_JRB_WATER,
    TEXTURE_UNK_WATER,
    TEXTURE_LAVA,
    TEX_QUICKSAND_SSL,
    TEX_PYRAMID_SAND_SSL,
    TEX_YELLOW_TRI_TTC
};

/**
 * Contains an id and an array of MovtexQuad structs.
 */
struct MovtexQuadCollection {
    /// identifier for geo nodes to refer to this MovtexQuad collection
    s16 id;
    /// points to a short 'n' followed by an array of n MovtexQuad structs
    Movtex *quadArraySegmented;
};

extern f32 gPaintingMarioYEntry;

// Moving texture mesh ids have for bits 8-16 a course identifier.
// This corresponds to the numbers used in debug level select, except they are
// re-interpreted as hexadecimal numbers. TTM is course 36, so the id is 0x36
// and the first water quad collection in TTM has id 0x3601.
#define MOVTEX_AREA_BBH              (0x04 << 8)
#define MOVTEX_AREA_CCM              (0x05 << 8)
#define MOVTEX_AREA_INSIDE_CASTLE    (0x06 << 8)
#define MOVTEX_AREA_HMC              (0x07 << 8)
#define MOVTEX_AREA_SSL              (0x08 << 8)
#define MOVTEX_AREA_SL               (0x10 << 8)
#define MOVTEX_AREA_WDW              (0x11 << 8)
#define MOVTEX_AREA_JRB              (0x12 << 8)
#define MOVTEX_AREA_THI              (0x13 << 8)
#define MOVTEX_AREA_TTC              (0x14 << 8)
#define MOVTEX_AREA_CASTLE_GROUNDS   (0x16 << 8)
#define MOVTEX_AREA_BITFS            (0x19 << 8)
#define MOVTEX_AREA_LLL              (0x22 << 8)
#define MOVTEX_AREA_DDD              (0x23 << 8)
#define MOVTEX_AREA_WF               (0x24 << 8)
#define MOVTEX_AREA_CASTLE_COURTYARD (0x26 << 8)
#define MOVTEX_AREA_COTMC            (0x28 << 8)
#define MOVTEX_AREA_TTM              (0x36 << 8)

// Quad collections
#define BBH_MOVTEX_MERRY_GO_ROUND_WATER_ENTRANCE    (   0 | MOVTEX_AREA_BBH)
#define BBH_MOVTEX_MERRY_GO_ROUND_WATER_SIDE        (   1 | MOVTEX_AREA_BBH)
#define CCM_MOVTEX_PENGUIN_PUDDLE_WATER             (   1 | MOVTEX_AREA_CCM)
#define INSIDE_CASTLE_MOVTEX_GREEN_ROOM_WATER       (   0 | MOVTEX_AREA_INSIDE_CASTLE)
#define INSIDE_CASTLE_MOVTEX_MOAT_WATER             (0x12 | MOVTEX_AREA_INSIDE_CASTLE)
#define HMC_MOVTEX_DORRIE_POOL_WATER                (   1 | MOVTEX_AREA_HMC)
#define HMC_MOVTEX_TOXIC_MAZE_MIST                  (   2 | MOVTEX_AREA_HMC)
#define SSL_MOVTEX_PUDDLE_WATER                     (   1 | MOVTEX_AREA_SSL)
#define SSL_MOVTEX_TOXBOX_QUICKSAND_MIST            (0x51 | MOVTEX_AREA_SSL)
#define SL_MOVTEX_WATER                             (   1 | MOVTEX_AREA_SL)
#define WDW_MOVTEX_AREA1_WATER                      (   1 | MOVTEX_AREA_WDW)
#define WDW_MOVTEX_AREA2_WATER                      (   2 | MOVTEX_AREA_WDW)
#define JRB_MOVTEX_WATER                            (   1 | MOVTEX_AREA_JRB)
#define JRB_MOVTEX_INTIAL_MIST                      (   5 | MOVTEX_AREA_JRB)
#define JRB_MOVTEX_SINKED_BOAT_WATER                (   2 | MOVTEX_AREA_JRB)
#define THI_MOVTEX_AREA1_WATER                      (   1 | MOVTEX_AREA_THI)
#define THI_MOVTEX_AREA2_WATER                      (   2 | MOVTEX_AREA_THI)
#define CASTLE_GROUNDS_MOVTEX_WATER                 (   1 | MOVTEX_AREA_CASTLE_GROUNDS)
#define LLL_MOVTEX_VOLCANO_FLOOR_LAVA               (   2 | MOVTEX_AREA_LLL)
#define DDD_MOVTEX_AREA1_WATER                      (   1 | MOVTEX_AREA_DDD)
#define DDD_MOVTEX_AREA2_WATER                      (   2 | MOVTEX_AREA_DDD)
#define WF_MOVTEX_WATER                             (   1 | MOVTEX_AREA_WF)
#define CASTLE_COURTYARD_MOVTEX_STAR_STATUE_WATER   (   1 | MOVTEX_AREA_CASTLE_COURTYARD)
#define TTM_MOVTEX_PUDDLE                           (   1 | MOVTEX_AREA_TTM)

// Non-colored, unique movtex meshes (drawn in level geo)
#define MOVTEX_PYRAMID_SAND_PATHWAY_FRONT (1 | MOVTEX_AREA_SSL)
#define MOVTEX_PYRAMID_SAND_PATHWAY_FLOOR (2 | MOVTEX_AREA_SSL)
#define MOVTEX_PYRAMID_SAND_PATHWAY_SIDE  (3 | MOVTEX_AREA_SSL)
#define MOVTEX_CASTLE_WATERFALL           (1 | MOVTEX_AREA_CASTLE_GROUNDS)
#define MOVTEX_BITFS_LAVA_FIRST           (1 | MOVTEX_AREA_BITFS)
#define MOVTEX_BITFS_LAVA_SECOND          (2 | MOVTEX_AREA_BITFS)
#define MOVTEX_BITFS_LAVA_FLOOR           (3 | MOVTEX_AREA_BITFS)
#define MOVTEX_LLL_LAVA_FLOOR             (1 | MOVTEX_AREA_LLL)
#define MOVTEX_VOLCANO_LAVA_FALL          (2 | MOVTEX_AREA_LLL)
#define MOVTEX_COTMC_WATER                (1 | MOVTEX_AREA_COTMC)
#define MOVTEX_TTM_BEGIN_WATERFALL        (1 | MOVTEX_AREA_TTM)
#define MOVTEX_TTM_END_WATERFALL          (2 | MOVTEX_AREA_TTM)
#define MOVTEX_TTM_BEGIN_PUDDLE_WATERFALL (3 | MOVTEX_AREA_TTM)
#define MOVTEX_TTM_END_PUDDLE_WATERFALL   (4 | MOVTEX_AREA_TTM)
#define MOVTEX_TTM_PUDDLE_WATERFALL       (5 | MOVTEX_AREA_TTM)

// Colored, unique movtex meshes (drawn in level geo)
#define MOVTEX_SSL_PYRAMID_SIDE    (1 | MOVTEX_AREA_SSL)
#define MOVTEX_SSL_PYRAMID_CORNER  (2 | MOVTEX_AREA_SSL)
#define MOVTEX_SSL_COURSE_EDGE     (3 | MOVTEX_AREA_SSL)

// Shared movtex meshes (drawn in object geo)
#define MOVTEX_SSL_SAND_PIT_OUTSIDE  (1 | MOVTEX_AREA_SSL)
#define MOVTEX_SSL_SAND_PIT_PYRAMID  (2 | MOVTEX_AREA_SSL)
#define MOVTEX_TREADMILL_BIG         (0 | MOVTEX_AREA_TTC)
#define MOVTEX_TREADMILL_SMALL       (1 | MOVTEX_AREA_TTC)

extern Gfx *geo_wdw_set_initial_water_level(s32 callContext, struct GraphNode *node, f32 mtx[4][4]);
extern Gfx *geo_movtex_pause_control(s32 callContext, struct GraphNode *node, f32 mtx[4][4]);
extern Gfx *geo_movtex_draw_water_regions(s32 callContext, struct GraphNode *node, f32 mtx[4][4]);
extern Gfx *geo_movtex_draw_nocolor(s32 callContext, struct GraphNode *node, f32 mtx[4][4]);
extern Gfx *geo_movtex_draw_colored(s32 callContext, struct GraphNode *node, f32 mtx[4][4]);
extern Gfx *geo_movtex_draw_colored_no_update(s32 callContext, struct GraphNode *node,
                                              f32 mtx[4][4]);
extern Gfx *geo_movtex_draw_colored_2_no_update(s32 callContext, struct GraphNode *node,
                                         f32 mtx[4][4]);
extern Gfx *geo_movtex_update_horizontal(s32 callContext, struct GraphNode *node, f32 mtx[4][4]);

#endif /* _MOVING_TEXTURE_H */
