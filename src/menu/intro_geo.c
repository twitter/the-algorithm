#include "ultra64.h"
#include "sm64.h"
#include "prevent_bss_reordering.h"
#include "types.h"
#include "game/memory.h"
#include "game/segment2.h"
#include "game/segment7.h"
#include "intro_geo.h"

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

// title screen segment A
extern Gfx title_screen_bg_dl_0A000118[];
extern Gfx title_screen_bg_dl_0A000100[];
extern Gfx title_screen_bg_dl_0A000130[];
extern Gfx title_screen_bg_dl_0A000148[];
extern Gfx title_screen_bg_dl_0A000160[];
extern Gfx title_screen_bg_dl_0A000178[];
extern Gfx title_screen_bg_dl_0A000190[];
extern const u8 *const mario_title_texture_table[];
extern const u8 *const game_over_texture_table[];

// intro geo bss
s32 gGameOverFrameCounter;
s32 gGameOverTableIndex;
s16 gTitleZoomCounter;
s32 gTitleFadeCounter;

// intro screen background display lists for each of four 80x20 textures
Gfx *introBackgroundDlRows[] = { title_screen_bg_dl_0A000130, title_screen_bg_dl_0A000148,
                                 title_screen_bg_dl_0A000160, title_screen_bg_dl_0A000178 };

// intro screen background texture X offsets
float introBackgroundOffsetX[] = {
    0.0, 80.0, 160.0, 240.0, 0.0, 80.0, 160.0, 240.0, 0.0, 80.0, 160.0, 240.0,
};

// intro screen background texture Y offsets
float introBackgroundOffsetY[] = {
    160.0, 160.0, 160.0, 160.0, 80.0, 80.0, 80.0, 80.0, 0.0, 0.0, 0.0, 0.0,
};

// table that points to either the "Super Mario 64" or "Game Over" tables
const u8 *const *introBackgroundTextureType[] = { mario_title_texture_table, game_over_texture_table };

s8 introBackgroundIndexTable[] = {
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
    INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO, INTRO_BACKGROUND_SUPER_MARIO,
};

// only one table of indexes listed
s8 *introBackgroundTables[] = { introBackgroundIndexTable };

s8 gameOverBackgroundTable[] = {
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
    INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER, INTRO_BACKGROUND_GAME_OVER,
};

// order of tiles that are flipped from "Game Over" to "Super Mario 64"
s8 gameOverBackgroundFlipOrder[] = { 0x00, 0x01, 0x02, 0x03, 0x07, 0x0B,
                                     0x0a, 0x09, 0x08, 0x04, 0x05, 0x06 };

Gfx *geo_title_screen(s32 sp50, struct GraphNode *sp54, UNUSED void *context) {
    struct GraphNode *graphNode; // sp4c
    Gfx *displayList;            // sp48
    Gfx *displayListIter;        // sp44
    Mtx *scaleMat;               // sp40
    f32 *scaleTable1;            // sp3c
    f32 *scaleTable2;            // sp38
    f32 scaleX;                  // sp34
    f32 scaleY;                  // sp30
    f32 scaleZ;                  // sp2c
    graphNode = sp54;
    displayList = NULL;
    displayListIter = NULL;
    scaleTable1 = segmented_to_virtual(intro_seg7_table_0700C790);
    scaleTable2 = segmented_to_virtual(intro_seg7_table_0700C880);
    if (sp50 != 1) {
        gTitleZoomCounter = 0;
    } else if (sp50 == 1) {
        graphNode->flags = (graphNode->flags & 0xFF) | 0x100;
        scaleMat = alloc_display_list(sizeof(*scaleMat));
        displayList = alloc_display_list(4 * sizeof(*displayList));
        displayListIter = displayList;
        if (gTitleZoomCounter >= 0 && gTitleZoomCounter < INTRO_STEPS_ZOOM_IN) {
            scaleX = scaleTable1[gTitleZoomCounter * 3];
            scaleY = scaleTable1[gTitleZoomCounter * 3 + 1];
            scaleZ = scaleTable1[gTitleZoomCounter * 3 + 2];
        } else if (gTitleZoomCounter >= INTRO_STEPS_ZOOM_IN && gTitleZoomCounter < INTRO_STEPS_HOLD_1) {
            scaleX = 1.0f;
            scaleY = 1.0f;
            scaleZ = 1.0f;
        } else if (gTitleZoomCounter >= INTRO_STEPS_HOLD_1
                   && gTitleZoomCounter < INTRO_STEPS_ZOOM_OUT) {
            scaleX = scaleTable2[(gTitleZoomCounter - INTRO_STEPS_HOLD_1) * 3];
            scaleY = scaleTable2[(gTitleZoomCounter - INTRO_STEPS_HOLD_1) * 3 + 1];
            scaleZ = scaleTable2[(gTitleZoomCounter - INTRO_STEPS_HOLD_1) * 3 + 2];
        } else {
            scaleX = 0.0f;
            scaleY = 0.0f;
            scaleZ = 0.0f;
        }
        guScale(scaleMat, scaleX, scaleY, scaleZ);
        gSPMatrix(displayListIter++, scaleMat, G_MTX_MODELVIEW | G_MTX_MUL | G_MTX_PUSH);
        gSPDisplayList(displayListIter++, &intro_seg7_dl_0700B3A0);
        gSPPopMatrix(displayListIter++, G_MTX_MODELVIEW);
        gSPEndDisplayList(displayListIter);
        gTitleZoomCounter++;
    }
    return displayList;
}

Gfx *geo_fade_transition(s32 sp40, struct GraphNode *sp44, UNUSED void *context) {
    struct GraphNode *graphNode = sp44; // sp3c
    Gfx *displayList = NULL;            // sp38
    Gfx *displayListIter = NULL;        // sp34
    if (sp40 != 1) {
        gTitleFadeCounter = 0; // D_801B985C
    } else if (sp40 == 1) {
        displayList = alloc_display_list(5 * sizeof(*displayList));
        displayListIter = displayList;
        gSPDisplayList(displayListIter++, dl_proj_mtx_fullscreen);
        gDPSetEnvColor(displayListIter++, 255, 255, 255, gTitleFadeCounter);
        if (gTitleFadeCounter == 255) {
            if (0) {
            }
            graphNode->flags = (graphNode->flags & 0xFF) | 0x100;
            gDPSetRenderMode(displayListIter++, G_RM_AA_OPA_SURF, G_RM_AA_OPA_SURF2);
        } else {
            graphNode->flags = (graphNode->flags & 0xFF) | 0x500;
            gDPSetRenderMode(displayListIter++, G_RM_AA_XLU_SURF, G_RM_AA_XLU_SURF2);
            if (0) {
            }
        }
        gSPDisplayList(displayListIter++, &intro_seg7_dl_0700C6A0);
        gSPEndDisplayList(displayListIter);
        if (gTitleZoomCounter >= 0x13) {
            gTitleFadeCounter += 0x1a;
            if (gTitleFadeCounter >= 0x100) {
                gTitleFadeCounter = 0xFF;
            }
        }
    }
    return displayList;
}

Gfx *intro_backdrop_one_image(s32 index, s8 *backgroundTable) {
    Mtx *mtx;                         // sp5c
    Gfx *displayList;                 // sp58
    Gfx *displayListIter;             // sp54
    const u8 *const *vIntroBgTable;   // sp50
    s32 i;                            // sp4c
    mtx = alloc_display_list(sizeof(*mtx));
    displayList = alloc_display_list(36 * sizeof(*displayList));
    displayListIter = displayList;
    vIntroBgTable = segmented_to_virtual(introBackgroundTextureType[backgroundTable[index]]);
    guTranslate(mtx, introBackgroundOffsetX[index], introBackgroundOffsetY[index], 0.0f);
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

Gfx *geo_intro_backdrop(s32 sp48, struct GraphNode *sp4c, UNUSED void *context) {
    struct GraphNodeMore *graphNode; // sp44
    s32 index;                       // sp40
    s8 *backgroundTable;             // sp3c
    Gfx *displayList;                // sp38
    Gfx *displayListIter;            // sp34
    s32 i;                           // sp30
    graphNode = (struct GraphNodeMore *) sp4c;
    index = graphNode->unk18 & 0xff; // TODO: word at offset 0x18 of struct GraphNode
    backgroundTable = introBackgroundTables[index];
    displayList = NULL;
    displayListIter = NULL;
    if (sp48 == 1) {
        displayList = alloc_display_list(16 * sizeof(*displayList));
        displayListIter = displayList;
        graphNode->node.flags = (graphNode->node.flags & 0xFF) | 0x100;
        gSPDisplayList(displayListIter++, &dl_proj_mtx_fullscreen);
        gSPDisplayList(displayListIter++, &title_screen_bg_dl_0A000100);
        for (i = 0; i < 12; ++i) {
            gSPDisplayList(displayListIter++, intro_backdrop_one_image(i, backgroundTable));
        }
        gSPDisplayList(displayListIter++, &title_screen_bg_dl_0A000190);
        gSPEndDisplayList(displayListIter);
    }
    return displayList;
}

Gfx *geo_game_over_tile(s32 sp40, struct GraphNode *sp44, UNUSED void *context) {
    struct GraphNode *graphNode; // sp3c
    Gfx *displayList;            // sp38
    Gfx *displayListIter;        // sp34
    s32 j;                       // sp30
    s32 i;                       // sp2c
    graphNode = sp44;
    displayList = NULL;
    displayListIter = NULL;
    if (sp40 != 1) {
        gGameOverFrameCounter = 0;
        gGameOverTableIndex = -2;
        for (i = 0; i < (s32) sizeof(gameOverBackgroundTable); ++i) {
            gameOverBackgroundTable[i] = INTRO_BACKGROUND_GAME_OVER;
        }
    } else {
        displayList = alloc_display_list(16 * sizeof(*displayList));
        displayListIter = displayList;
        if (gGameOverTableIndex == -2) {
            if (gGameOverFrameCounter == 180) {
                gGameOverTableIndex++;
                gGameOverFrameCounter = 0;
            }
        } else {
            // transition tile from "Game Over" to "Super Mario 64"
            if (gGameOverTableIndex != 11 && !(gGameOverFrameCounter & 0x1)) {
                gGameOverTableIndex++;
                gameOverBackgroundTable[gameOverBackgroundFlipOrder[gGameOverTableIndex]] =
                    INTRO_BACKGROUND_SUPER_MARIO;
            }
        }
        if (gGameOverTableIndex != 11) {
            gGameOverFrameCounter++;
        }
        graphNode->flags = (graphNode->flags & 0xFF) | 0x100;
        gSPDisplayList(displayListIter++, &dl_proj_mtx_fullscreen);
        gSPDisplayList(displayListIter++, &title_screen_bg_dl_0A000100);
        for (j = 0; j < (s32) sizeof(gameOverBackgroundTable); ++j) {
            gSPDisplayList(displayListIter++, intro_backdrop_one_image(j, gameOverBackgroundTable));
        }
        gSPDisplayList(displayListIter++, &title_screen_bg_dl_0A000190);
        gSPEndDisplayList(displayListIter);
    }
    return displayList;
}
