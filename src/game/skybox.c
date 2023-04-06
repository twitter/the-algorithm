#include <ultra64.h>

#include "sm64.h"
#include "engine/math_util.h"
#include "memory.h"
#include "area.h"
#include "save_file.h"
#include "segment2.h"
#include "level_update.h"
#include "geo_misc.h"


/**
 * @file skybox.c
 *
 * Implements the skybox background.
 *
 * It's not exactly a sky"box": it's more of a sky tilemap. It renders a 3x3 grid of 32x32 pieces of the
 * whole skybox image, starting from the top left based on the camera's rotation. A skybox image has 64
 * unique 32x32 tiles, with the first two columns duplicated for a total of 80.
 *
 * The tiles are mapped to world space such that 2 full tiles fit on the screen, for a total of
 * 8 tiles around the full 360 degrees. Each tile takes up 45 degrees of the camera's field of view, and
 * the code draws 3 tiles or 135 degrees of the skybox in a frame. But only 2 tiles, or 90 degrees, can
 * fit on-screen at a time.
 *
 * @bug FOV is handled strangely by the code. It is used to scale and rotate the skybox, when really it
 * should probably only be used to calculate the distance drawn from the center of the looked-at tile.
 * But since the game always sets it to 90 degrees, the skybox always scales and rotates the same,
 * regardless of the camera's actual FOV. So even if the camera's FOV is 10 degrees the game draws a
 * full 90 degrees of the skybox, which makes the sky look really far away.
 *
 * @bug Skyboxes unnecessarily repeat the first 2 columns when they could just wrap the col index.
 * Although, the wasted space is only about 128 bytes for each image.
 */

/**
 * Describes the position, tiles, and orientation of the skybox image.
 *
 * Describes the scaled x and y offset into the tilemap, based on the yaw and pitch.  Computes the
 * upperLeftTile index into the skybox's tile list using scaledX and scaledY. See get_top_left_tile_idx.
 *
 * The skybox is always drawn behind everything, because in the level's geo script, the skybox is drawn
 * first, in a display list with the Z buffer disabled
 */
struct Skybox {
    /// The camera's yaw, from 0 to 65536, which maps to 0 to 360 degrees
    u16 yaw;
    /// The camera's pitch, which is bounded by +-16384, which maps to -90 to 90 degrees
    s16 pitch;
    /// The skybox's X position in world space
    s32 scaledX;
    /// The skybox's Y position in world space
    s32 scaledY;

    /// The index of the upper-left tile in the 3x3 grid that gets drawn
    s32 upperLeftTile;
};

struct Skybox sSkyBoxInfo[2];

typedef const u8 *const SkyboxTexture[80];

extern SkyboxTexture bbh_skybox_ptrlist;
extern SkyboxTexture bidw_skybox_ptrlist;
extern SkyboxTexture bitfs_skybox_ptrlist;
extern SkyboxTexture bits_skybox_ptrlist;
extern SkyboxTexture ccm_skybox_ptrlist;
extern SkyboxTexture cloud_floor_skybox_ptrlist;
extern SkyboxTexture clouds_skybox_ptrlist;
extern SkyboxTexture ssl_skybox_ptrlist;
extern SkyboxTexture water_skybox_ptrlist;
extern SkyboxTexture wdw_skybox_ptrlist;

SkyboxTexture *sSkyboxTextures[10] = {
    &water_skybox_ptrlist,
    &bitfs_skybox_ptrlist,
    &wdw_skybox_ptrlist,
    &cloud_floor_skybox_ptrlist,
    &ccm_skybox_ptrlist,
    &ssl_skybox_ptrlist,
    &bbh_skybox_ptrlist,
    &bidw_skybox_ptrlist,
    &clouds_skybox_ptrlist,
    &bits_skybox_ptrlist,
};

/**
 * The skybox color mask.
 * The final color of each pixel is computed from the bitwise AND of the color and the texture.
 */
u8 sSkyboxColors[][3] = {
    { 0x50, 0x64, 0x5A },
    { 0xFF, 0xFF, 0xFF },
};

/**
 * Constant used to scale the skybox horizontally to a multiple of the screen's width
 */
#define SKYBOX_WIDTH (4 * SCREEN_WIDTH)
/**
 * Constant used to scale the skybox vertically to a multiple of the screen's height
 */
#define SKYBOX_HEIGHT (4 * SCREEN_HEIGHT)

/**
 * The tile's width in world space.
 * By default, two full tiles can fit in the screen.
 */
#define SKYBOX_TILE_WIDTH (SCREEN_WIDTH / 2)
/**
 * The tile's height in world space.
 * By default, two full tiles can fit in the screen.
 */
#define SKYBOX_TILE_HEIGHT (SCREEN_HEIGHT / 2)

/**
 * The horizontal length of the skybox tilemap in tiles.
 */
#define SKYBOX_COLS (10)
/**
 * The vertical length of the skybox tilemap in tiles.
 */
#define SKYBOX_ROWS (8)


/**
 * Convert the camera's yaw into an x position into the scaled skybox image.
 *
 * fov is always 90 degrees, set in draw_skybox_facing_camera.
 *
 * The calculation performed is equivalent to (360 / fov) * (yaw / 65536) * SCREEN_WIDTH
 * in other words: (the number of fov-sized parts of the circle there are) *
 *                 (how far is the camera rotated from 0, scaled 0 to 1)   *
 *                 (the screen width)
 */
int calculate_skybox_scaled_x(s8 player, f32 fov) {
    f32 yaw = sSkyBoxInfo[player].yaw;

    //! double literals are used instead of floats
    f32 yawScaled = SCREEN_WIDTH * 360.0 * yaw / (fov * 65536.0);
    // Round the scaled yaw. Since yaw is a u16, it doesn't need to check for < 0
    s32 scaledX = yawScaled + 0.5;

    if (scaledX > SKYBOX_WIDTH) {
        scaledX -= scaledX / SKYBOX_WIDTH * SKYBOX_WIDTH;
    }
    return SKYBOX_WIDTH - scaledX;
}

/**
 * Convert the camera's pitch into a y position in the scaled skybox image.
 *
 * fov may have been used in an earlier version, but the developers changed the function to always use
 * 90 degrees.
 */
int calculate_skybox_scaled_y(s8 player, UNUSED f32 fov) {
    // Convert pitch to degrees. Pitch is bounded between -90 (looking down) and 90 (looking up).
    f32 pitchInDegrees = (f32) sSkyBoxInfo[player].pitch * 360.0 / 65535.0;

    // Scale by 360 / fov
    f32 degreesToScale = 360.0f * pitchInDegrees / 90.0;
    s32 roundedY = round_float(degreesToScale);

    // Since pitch can be negative, and the tile grid starts 1 octant above the camera's focus, add
    // 5 octants to the y position
    s32 scaledY = roundedY + 5 * SKYBOX_TILE_HEIGHT;

    if (scaledY > SKYBOX_HEIGHT) {
        scaledY = SKYBOX_HEIGHT;
    }
    if (scaledY < SCREEN_HEIGHT) {
        scaledY = SCREEN_HEIGHT;
    }
    return scaledY;
}

/**
 * Converts the upper left xPos and yPos to the index of the upper left tile in the skybox.
 */
static int get_top_left_tile_idx(s8 player) {
    s32 tileCol = sSkyBoxInfo[player].scaledX / SKYBOX_TILE_WIDTH;
    s32 tileRow = (SKYBOX_HEIGHT - sSkyBoxInfo[player].scaledY) / SKYBOX_TILE_HEIGHT;

    return tileRow * SKYBOX_COLS + tileCol;
}

/**
 * Generates vertices for the skybox tile.
 *
 * @param tileIndex The index into the 32x32 sections of the whole skybox image. The index is converted
 *                  into an x and y by modulus and division by SKYBOX_COLS. x and y are then scaled by
 *                  SKYBOX_TILE_WIDTH to get a point in world space.
 */
Vtx *make_skybox_rect(s32 tileIndex, s8 colorIndex) {
    Vtx *verts = alloc_display_list(4 * sizeof(*verts));
    s16 x = tileIndex % SKYBOX_COLS * SKYBOX_TILE_WIDTH;
    s16 y = SKYBOX_HEIGHT - tileIndex / SKYBOX_COLS * SKYBOX_TILE_HEIGHT;

    if (verts != NULL) {
        make_vertex(verts, 0, x, y, -1, 0, 0, sSkyboxColors[colorIndex][0], sSkyboxColors[colorIndex][1],
                    sSkyboxColors[colorIndex][2], 255);
        make_vertex(verts, 1, x, y - SKYBOX_TILE_HEIGHT, -1, 0, 31 << 5, sSkyboxColors[colorIndex][0], sSkyboxColors[colorIndex][1],
                    sSkyboxColors[colorIndex][2], 255);
        make_vertex(verts, 2, x + SKYBOX_TILE_WIDTH, y - SKYBOX_TILE_HEIGHT, -1, 31 << 5, 31 << 5, sSkyboxColors[colorIndex][0],
                    sSkyboxColors[colorIndex][1], sSkyboxColors[colorIndex][2], 255);
        make_vertex(verts, 3, x + SKYBOX_TILE_WIDTH, y, -1, 31 << 5, 0, sSkyboxColors[colorIndex][0], sSkyboxColors[colorIndex][1],
                    sSkyboxColors[colorIndex][2], 255);
    } else {
    }
    return verts;
}

/**
 * Draws a 3x3 grid of 32x32 sections of the original skybox image.
 * The row and column are converted into an index into the skybox's tile list, which is then drawn in
 * world space so that the tiles will rotate with the camera.
 */
void draw_skybox_tile_grid(Gfx **dlist, s8 background, s8 player, s8 colorIndex) {
    s32 row;
    s32 col;

    for (row = 0; row < 3; row++) {
        for (col = 0; col < 3; col++) {
            s32 tileIndex = sSkyBoxInfo[player].upperLeftTile + row * SKYBOX_COLS + col;
            const u8 *const texture =
                (*(SkyboxTexture *) segmented_to_virtual(sSkyboxTextures[background]))[tileIndex];
            Vtx *vertices = make_skybox_rect(tileIndex, colorIndex);

            gLoadBlockTexture((*dlist)++, 32, 32, G_IM_FMT_RGBA, texture);
            gSPVertex((*dlist)++, VIRTUAL_TO_PHYSICAL(vertices), 4, 0);
            gSPDisplayList((*dlist)++, dl_draw_quad_verts_0123);
        }
    }
}

void *create_skybox_ortho_matrix(s8 player) {
    f32 left = sSkyBoxInfo[player].scaledX;
    f32 right = sSkyBoxInfo[player].scaledX + SCREEN_WIDTH;
    f32 bottom = sSkyBoxInfo[player].scaledY - SCREEN_HEIGHT;
    f32 top = sSkyBoxInfo[player].scaledY;
    Mtx *mtx = alloc_display_list(sizeof(*mtx));

    if (mtx != NULL) {
        guOrtho(mtx, left, right, bottom, top, 0.0f, 3.0f, 1.0f);
    } else {
    }

    return mtx;
}

/**
 * Creates the skybox's display list, then draws the 3x3 grid of tiles.
 */
Gfx *init_skybox_display_list(s8 player, s8 background, s8 colorIndex) {
    s32 dlCommandCount = 5 + 9 * 7; // 5 for the start and end, plus 9 skybox tiles
    void *skybox = alloc_display_list(dlCommandCount * sizeof(Gfx));
    Gfx *dlist = skybox;

    if (skybox == NULL) {
        return NULL;
    } else {
        Mtx *ortho = create_skybox_ortho_matrix(player);

        gSPDisplayList(dlist++, dl_skybox_begin);
        gSPMatrix(dlist++, VIRTUAL_TO_PHYSICAL(ortho), G_MTX_PROJECTION | G_MTX_MUL | G_MTX_NOPUSH);
        gSPDisplayList(dlist++, dl_skybox_tile_tex_settings);
        draw_skybox_tile_grid(&dlist, background, player, colorIndex);
        gSPDisplayList(dlist++, dl_skybox_end);
        gSPEndDisplayList(dlist);
    }
    return skybox;
}

/**
 * Draw a skybox facing the direction from pos to foc.
 *
 * @param player Unused, determines which orientation info struct to update
 * @param background The skybox image to use
 * @param fov Unused. It SHOULD control how much the skybox is scaled, but the way it's coded it just
 *            controls how fast the skybox rotates. The given value is replaced with 90 right before the
 *            dl is created
 * @param posX,posY,posZ The camera's position
 * @param focX,focY,focZ The camera's focus.
 */
Gfx *create_skybox_facing_camera(s8 player, s8 background, f32 fov,
                                    f32 posX, f32 posY, f32 posZ,
                                    f32 focX, f32 focY, f32 focZ) {
    f32 cameraFaceX = focX - posX;
    f32 cameraFaceY = focY - posY;
    f32 cameraFaceZ = focZ - posZ;
    s8 colorIndex = 1;

    // If the first star is collected in JRB, make the sky darker and slightly green
    if (background == 8 && !(save_file_get_star_flags(gCurrSaveFileNum - 1, COURSE_JRB - 1) & 1)) {
        colorIndex = 0;
    }

    //! fov is always set to 90.0f. If this line is removed, then the game crashes because fov is 0 on
    //! the first frame, which causes a floating point divide by 0
    fov = 90.0f;
    sSkyBoxInfo[player].yaw = atan2s(cameraFaceZ, cameraFaceX);
    sSkyBoxInfo[player].pitch = atan2s(sqrtf(cameraFaceX * cameraFaceX + cameraFaceZ * cameraFaceZ), cameraFaceY);
    sSkyBoxInfo[player].scaledX = calculate_skybox_scaled_x(player, fov);
    sSkyBoxInfo[player].scaledY = calculate_skybox_scaled_y(player, fov);
    sSkyBoxInfo[player].upperLeftTile = get_top_left_tile_idx(player);

    return init_skybox_display_list(player, background, colorIndex);
}
