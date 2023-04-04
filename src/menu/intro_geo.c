#include <PR/ultratypes.h>

#include "game/memory.h"
#include "game/segment2.h"
#include "game/segment7.h"
#include "intro_geo.h"
#include "sm64.h"
#include "textures.h"
#include "types.h"
#include "buffers/framebuffers.h"
#include "game/game_init.h"
#include "audio/external.h"

// frame counts for the zoom in, hold, and zoom out of title model
#define INTRO_STEPS_ZOOM_IN 20
#define INTRO_STEPS_HOLD_1 75
#define INTRO_STEPS_ZOOM_OUT 91

// background types
#define INTRO_BACKGROUND_SUPER_MARIO 0
#define INTRO_BACKGROUND_GAME_OVER 1

struct GraphNodeMore {
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *todo;
    /*0x18*/ u32 unk18;
};

// intro geo bss
#ifdef VERSION_SH
static u16 *sFramebuffers[3];
#endif
static s32 sGameOverFrameCounter;
static s32 sGameOverTableIndex;
static s16 sIntroFrameCounter;
static s32 sTmCopyrightAlpha;

/**
 * Geo callback to render the "Super Mario 64" logo on the title screen
 */
Gfx *geo_intro_super_mario_64_logo(s32 state, struct GraphNode *node, UNUSED void *context) {
    struct GraphNode *graphNode = node;
    Gfx *dl = NULL;
    Gfx *dlIter = NULL;
    Mtx *scaleMat;
    f32 *scaleTable1 = segmented_to_virtual(intro_seg7_table_0700C790);
    f32 *scaleTable2 = segmented_to_virtual(intro_seg7_table_0700C880);
    f32 scaleX;
    f32 scaleY;
    f32 scaleZ;

    if (state != 1) {
        sIntroFrameCounter = 0;
    } else if (state == 1) {
        graphNode->flags = (graphNode->flags & 0xFF) | (LAYER_OPAQUE << 8);
        scaleMat = alloc_display_list(sizeof(*scaleMat));
        dl = alloc_display_list(4 * sizeof(*dl));
        dlIter = dl;

        // determine scale based on the frame counter
        if (sIntroFrameCounter >= 0 && sIntroFrameCounter < INTRO_STEPS_ZOOM_IN) {
            // zooming in
            scaleX = scaleTable1[sIntroFrameCounter * 3];
            scaleY = scaleTable1[sIntroFrameCounter * 3 + 1];
            scaleZ = scaleTable1[sIntroFrameCounter * 3 + 2];
        } else if (sIntroFrameCounter >= INTRO_STEPS_ZOOM_IN && sIntroFrameCounter < INTRO_STEPS_HOLD_1) {
            // holding
            scaleX = 1.0f;
            scaleY = 1.0f;
            scaleZ = 1.0f;
        } else if (sIntroFrameCounter >= INTRO_STEPS_HOLD_1 && sIntroFrameCounter < INTRO_STEPS_ZOOM_OUT) {
            // zooming out
            scaleX = scaleTable2[(sIntroFrameCounter - INTRO_STEPS_HOLD_1) * 3];
            scaleY = scaleTable2[(sIntroFrameCounter - INTRO_STEPS_HOLD_1) * 3 + 1];
            scaleZ = scaleTable2[(sIntroFrameCounter - INTRO_STEPS_HOLD_1) * 3 + 2];
        } else {
            // disappeared
            scaleX = 0.0f;
            scaleY = 0.0f;
            scaleZ = 0.0f;
        }
        guScale(scaleMat, scaleX, scaleY, scaleZ);

        gSPMatrix(dlIter++, scaleMat, G_MTX_MODELVIEW | G_MTX_MUL | G_MTX_PUSH);
        gSPDisplayList(dlIter++, &intro_seg7_dl_0700B3A0);  // draw model
        gSPPopMatrix(dlIter++, G_MTX_MODELVIEW);
        gSPEndDisplayList(dlIter);

        sIntroFrameCounter++;
    }
    return dl;
}

/**
 * Geo callback to render TM and Copyright on the title screen
 */
Gfx *geo_intro_tm_copyright(s32 state, struct GraphNode *node, UNUSED void *context) {
    struct GraphNode *graphNode = node;
    Gfx *dl = NULL;
    Gfx *dlIter = NULL;

    if (state != 1) {  // reset
        sTmCopyrightAlpha = 0;
    } else if (state == 1) {  // draw
        dl = alloc_display_list(5 * sizeof(*dl));
        dlIter = dl;
        gSPDisplayList(dlIter++, dl_proj_mtx_fullscreen);
        gDPSetEnvColor(dlIter++, 255, 255, 255, sTmCopyrightAlpha);
        switch (sTmCopyrightAlpha) {
            case 255: // opaque
                graphNode->flags = (graphNode->flags & 0xFF) | (LAYER_OPAQUE << 8);
                gDPSetRenderMode(dlIter++, G_RM_AA_OPA_SURF, G_RM_AA_OPA_SURF2);
                break;
            default: // blend
                graphNode->flags = (graphNode->flags & 0xFF) | (LAYER_TRANSPARENT << 8);
                gDPSetRenderMode(dlIter++, G_RM_AA_XLU_SURF, G_RM_AA_XLU_SURF2);
                break;
        }
        gSPDisplayList(dlIter++, &intro_seg7_dl_0700C6A0);  // draw model
        gSPEndDisplayList(dlIter);

        // Once the "Super Mario 64" logo has just about zoomed fully, fade in the "TM" and copyright text
        if (sIntroFrameCounter >= 19) {
            sTmCopyrightAlpha += 26;
            if (sTmCopyrightAlpha > 255) {
                sTmCopyrightAlpha = 255;
            }
        }
    }
    return dl;
}

/**
 * Generates a display list for a single background tile
 *
 * @param index            which tile to render (value from 0 to 11)
 * @param backgroundTable  array describing which image to use for each tile (0 denotes a "Super Mario 64" image, and 1 denotes a "Game Over" image)
 */
static Gfx *intro_backdrop_one_image(s32 index, s8 *backgroundTable) {
    // intro screen background display lists for each of four 80x20 textures
    static const Gfx *introBackgroundDlRows[] = { title_screen_bg_dl_0A000130, title_screen_bg_dl_0A000148,
                                                  title_screen_bg_dl_0A000160, title_screen_bg_dl_0A000178 };

    // intro screen background texture X offsets
    static float xCoords[] = {
        0, 80, 160, 240,
        0, 80, 160, 240,
        0, 80, 160, 240,
    };

    // intro screen background texture Y offsets
    static float yCoords[] = {
        160, 160, 160, 160,
        80,  80,  80,  80,
        0,   0,   0,   0,
    };

    // table that points to either the "Super Mario 64" or "Game Over" tables
    static const u8 *const *textureTables[] = { mario_title_texture_table, game_over_texture_table };

    Mtx *mtx = alloc_display_list(sizeof(*mtx));
    Gfx *displayList = alloc_display_list(36 * sizeof(*displayList));
    Gfx *displayListIter = displayList;
    const u8 *const *vIntroBgTable = segmented_to_virtual(textureTables[backgroundTable[index]]);
    s32 i;

    guTranslate(mtx, xCoords[index], yCoords[index], 0.0f);
    gSPMatrix(displayListIter++, mtx, G_MTX_MODELVIEW | G_MTX_LOAD | G_MTX_PUSH);
    gSPDisplayList(displayListIter++, &title_screen_bg_dl_0A000118);
    for (i = 0; i < 4; ++i) {
        gDPLoadTextureBlock(displayListIter++, vIntroBgTable[i], G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0,
                            G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD)
        gSPDisplayList(displayListIter++, introBackgroundDlRows[i]);
    }
    gSPPopMatrix(displayListIter++, G_MTX_MODELVIEW);
    gSPEndDisplayList(displayListIter);
    return displayList;
}

static s8 introBackgroundIndexTable[] = {
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
};

// only one table of indexes listed
static s8 *introBackgroundTables[] = { introBackgroundIndexTable };

/**
 * Geo callback to render the intro background tiles
 */
Gfx *geo_intro_regular_backdrop(s32 state, struct GraphNode *node, UNUSED void *context) {
    struct GraphNodeMore *graphNode = (struct GraphNodeMore *) node;
    s32 index = graphNode->unk18 & 0xff; // TODO: word at offset 0x18 of struct GraphNode (always ends up being 0)
    s8 *backgroundTable = introBackgroundTables[index];
    Gfx *dl = NULL;
    Gfx *dlIter = NULL;
    s32 i;

    if (state == 1) {  // draw
        dl = alloc_display_list(16 * sizeof(*dl));
        dlIter = dl;
        graphNode->node.flags = (graphNode->node.flags & 0xFF) | (LAYER_OPAQUE << 8);
        gSPDisplayList(dlIter++, &dl_proj_mtx_fullscreen);
        gSPDisplayList(dlIter++, &title_screen_bg_dl_0A000100);
        for (i = 0; i < 12; ++i) {
            gSPDisplayList(dlIter++, intro_backdrop_one_image(i, backgroundTable));
        }
        gSPDisplayList(dlIter++, &title_screen_bg_dl_0A000190);
        gSPEndDisplayList(dlIter);
    }
    return dl;
}

static s8 gameOverBackgroundTable[] = {
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
};

/**
 * Geo callback to render the Game Over background tiles
 */
Gfx *geo_intro_gameover_backdrop(s32 state, struct GraphNode *node, UNUSED void *context) {
    struct GraphNode *graphNode = node;
    Gfx *dl = NULL;
    Gfx *dlIter = NULL;
    s32 j;
    s32 i;

    if (state != 1) {  // reset
        sGameOverFrameCounter = 0;
        sGameOverTableIndex = -2;
        for (i = 0; i < ARRAY_COUNT(gameOverBackgroundTable); ++i)
            gameOverBackgroundTable[i] = INTRO_BACKGROUND_GAME_OVER;
    } else {  // draw
        dl = alloc_display_list(16 * sizeof(*dl));
        dlIter = dl;
        if (sGameOverTableIndex == -2) {
            if (sGameOverFrameCounter == 180) {
                sGameOverTableIndex++;
                sGameOverFrameCounter = 0;
            }
        } else {
            // transition tile from "Game Over" to "Super Mario 64"
            if (sGameOverTableIndex != 11 && !(sGameOverFrameCounter & 0x1)) {
                // order of tiles that are flipped from "Game Over" to "Super Mario 64"
                static s8 flipOrder[] = { 0, 1, 2, 3, 7, 11, 10, 9, 8, 4, 5, 6 };

                sGameOverTableIndex++;
                gameOverBackgroundTable[flipOrder[sGameOverTableIndex]] =
                    INTRO_BACKGROUND_SUPER_MARIO;
            }
        }
        if (sGameOverTableIndex != 11) {
            sGameOverFrameCounter++;
        }
        graphNode->flags = (graphNode->flags & 0xFF) | (LAYER_OPAQUE << 8);

        // draw all the tiles
        gSPDisplayList(dlIter++, &dl_proj_mtx_fullscreen);
        gSPDisplayList(dlIter++, &title_screen_bg_dl_0A000100);
        for (j = 0; j < ARRAY_COUNT(gameOverBackgroundTable); ++j)
            gSPDisplayList(dlIter++, intro_backdrop_one_image(j, gameOverBackgroundTable));
        gSPDisplayList(dlIter++, &title_screen_bg_dl_0A000190);
        gSPEndDisplayList(dlIter);
    }
    return dl;
}

#ifdef VERSION_SH

extern Gfx title_screen_bg_dl_0A0065E8[];
extern Gfx title_screen_bg_dl_0A006618[];
extern Gfx title_screen_bg_dl_0A007548[];

// Data
s8 sFaceVisible[] = {
    1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 0, 0, 0, 0, 1, 1,
    1, 1, 0, 0, 0, 0, 1, 1,
    1, 1, 1, 1, 1, 1, 1, 1,
    1, 1, 1, 1, 1, 1, 1, 1,
};

s8 sFaceToggleOrder[] = {
     0,  1,  2,  3,  4,  5,  6,  7,
    15, 23, 31, 39, 47, 46, 45, 44,
    43, 42, 41, 40, 32, 24, 16,  8,
     9, 10, 11, 12, 13, 14, 22, 30,
    38, 37, 36, 35, 34, 33, 25, 17,
};

s8 sFaceCounter = 0;

void intro_gen_face_texrect(Gfx **dlIter) {
    s32 x;
    s32 y;

    for (y = 0; y < 6; y++) {
        for (x = 0; x < 8; x++) {
            if (sFaceVisible[y*8 + x] != 0) {
                gSPTextureRectangle((*dlIter)++, (x * 40) << 2, (y * 40) << 2, (x * 40 + 39) << 2, (y * 40 + 39) << 2, 0,
                                    0, 0, 4 << 10, 1 << 10);
            }
        }
    }
}

Gfx *intro_draw_face(u16 *image, s32 imageW, s32 imageH) {
    Gfx *dl;
    Gfx *dlIter;

    dl = alloc_display_list(110 * sizeof(Gfx));

    if (dl == NULL) {
        return dl;
    } else {
        dlIter = dl;
    }

    gSPDisplayList(dlIter++, title_screen_bg_dl_0A0065E8);

    gDPLoadTextureBlock(dlIter++, VIRTUAL_TO_PHYSICAL(image), G_IM_FMT_RGBA, G_IM_SIZ_16b, imageW, imageH, 0, G_TX_CLAMP | G_TX_NOMIRROR, G_TX_CLAMP | G_TX_NOMIRROR, 6, 6, G_TX_NOLOD, G_TX_NOLOD);

    intro_gen_face_texrect(&dlIter);

    gSPDisplayList(dlIter++, title_screen_bg_dl_0A006618);

    gSPEndDisplayList(dlIter++);

    return dl;
}

u16 *intro_sample_framebuffer(s32 imageW, s32 imageH, s32 sampleW, s32 sampleH) {
    u16 *fb;
    u16 *image;
    s32 pixel;
    f32 size;
    f32 r, g, b;
    s32 iy, ix, sy, sx;

    s32 xOffset = 120;
    s32 yOffset = 80;

    fb = sFramebuffers[sRenderingFramebuffer];
    image = alloc_display_list(imageW * imageH * sizeof(u16));

    if (image == NULL) {
        return image;
    }

    for (iy = 0; iy < imageH; iy++) {
        for (ix = 0; ix < imageW; ix++) {
            r = 0;
            g = 0;
            b = 0;

            for (sy = 0; sy < sampleH; sy++) {
                for (sx = 0; sx < sampleW; sx++) {
                    u16 fbr, fbg, fbb;
                    f32 f1, f2, f3;
                    pixel = 320 * (sampleH * iy + sy + yOffset) + (sampleW * ix + xOffset) + sx;

                    fbr = fb[pixel];
                    fbg = fb[pixel];
                    fbb = fb[pixel];

                    f1 = ((fbr >> 0xB) & 0x1F);
                    f2 = ((fbg >> 0x6) & 0x1F);
                    f3 = ((fbb >> 0x1) & 0x1F);

                    r += f1;
                    g += f2;
                    b += f3;
                }
            }

            size = sampleW * sampleH;
            image[imageH * iy + ix] = ((((u16) (r / size + 0.5) << 0xB) & 0xF800) & 0xffff) +
                                      ((((u16) (g / size + 0.5) << 0x6) &  0x7C0) & 0xffff) +
                                      ((((u16) (b / size + 0.5) << 0x1) &   0x3E) & 0xffff) + 1;
        }
    }

    return image;
}

Gfx *geo_intro_face_easter_egg(s32 state, struct GraphNode *node, UNUSED void *context) {
    struct GraphNodeGenerated *genNode = (struct GraphNodeGenerated *)node;
    u16 *image;
    Gfx *dl = NULL;
    s32 i;

    if (state != 1) {
        sFramebuffers[0] = gFramebuffer0;
        sFramebuffers[1] = gFramebuffer1;
        sFramebuffers[2] = gFramebuffer2;

        for (i = 0; i < 48; i++) {
            sFaceVisible[i] = 0;
        }

    } else if (state == 1) {
        if (sFaceCounter == 0) {
            if (gPlayer1Controller->buttonPressed & Z_TRIG) {
                play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
                sFaceVisible[0] ^= 1;
                sFaceCounter++;
            }
        } else {
            sFaceVisible[sFaceToggleOrder[sFaceCounter++]] ^= 1;
            if (sFaceCounter >= 40) {
                sFaceCounter = 0;
            }
        }

        // Draw while the first or last face is visible.
        if (sFaceVisible[0] == 1 || sFaceVisible[17] == 1) {
            image = intro_sample_framebuffer(40, 40, 2, 2);
            if (image != NULL) {
                genNode->fnNode.node.flags = (genNode->fnNode.node.flags & 0xFF) | (LAYER_OPAQUE << 8);
                dl = intro_draw_face(image, 40, 40);
            }
        }
    }

    return dl;
}

Gfx *geo_intro_rumble_pak_graphic(s32 state, struct GraphNode *node, UNUSED void *context) {
    struct GraphNodeGenerated *genNode = (struct GraphNodeGenerated *)node;
    Gfx *dlIter;
    Gfx *dl;
    s32 introContext;
    s8 backgroundTileSix;
#ifdef AVOID_UB
    dl = NULL;
    backgroundTileSix = 0;
#endif

    if (state != 1) {
        dl = NULL;
    } else if (state == 1) {
        genNode->fnNode.node.flags = (genNode->fnNode.node.flags & 0xFF) | (LAYER_OPAQUE << 8);
        introContext = genNode->parameter & 0xFF;
        if (introContext == 0) {
            backgroundTileSix = introBackgroundIndexTable[6];
        } else if (introContext == 1) {
            backgroundTileSix = gameOverBackgroundTable[6];
        }
        if (backgroundTileSix == INTRO_BACKGROUND_SUPER_MARIO) {
            dl = alloc_display_list(3 * sizeof(*dl));
            if (dl != NULL) {
                dlIter = dl;
                gSPDisplayList(dlIter++, &title_screen_bg_dl_0A007548);
                gSPEndDisplayList(dlIter);
            }
        } else {
            dl = NULL;
        }
    }
    return dl;
}

#endif
