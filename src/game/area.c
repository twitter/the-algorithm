#include <ultra64.h>

#include "area.h"
#include "sm64.h"
#include "behavior_data.h"
#include "game_init.h"
#include "object_list_processor.h"
#include "engine/surface_load.h"
#include "ingame_menu.h"
#include "screen_transition.h"
#include "mario.h"
#include "mario_actions_cutscene.h"
#include "print.h"
#include "hud.h"
#include "audio/external.h"
#include "area.h"
#include "rendering_graph_node.h"
#include "level_update.h"
#include "engine/geo_layout.h"
#include "save_file.h"
#include "level_table.h"

struct SpawnInfo gPlayerSpawnInfos[1];
struct GraphNode *D_8033A160[0x100];
struct Area gAreaData[8];

struct WarpTransition gWarpTransition;

s16 gCurrCourseNum;
s16 gCurrActNum;
s16 gCurrAreaIndex;
s16 gSavedCourseNum;
s16 gPauseScreenMode;
s16 gSaveOptSelectIndex;

struct SpawnInfo *gMarioSpawnInfo = &gPlayerSpawnInfos[0];
struct GraphNode **gLoadedGraphNodes = D_8033A160;
struct Area *gAreas = gAreaData;
struct Area *gCurrentArea = NULL;
struct CreditsEntry *gCurrCreditsEntry = NULL;
Vp *D_8032CE74 = NULL;
Vp *D_8032CE78 = NULL;
s16 gWarpTransDelay = 0;
u32 gFBSetColor = 0;
u32 gWarpTransFBSetColor = 0;
u8 gWarpTransRed = 0;
u8 gWarpTransGreen = 0;
u8 gWarpTransBlue = 0;
s16 gCurrSaveFileNum = 1;
s16 gCurrLevelNum = LEVEL_MIN;

/* 
 * The following two tables are used in get_mario_spawn_type() to determine spawn type
 * from warp behavior.
 * When looping through sWarpBhvSpawnTable, if the behavior function in the table matches
 * the spawn behavior executed, the index of that behavior is used with sSpawnTypeFromWarpBhv
*/

// D_8032CE9C
const BehaviorScript *sWarpBhvSpawnTable[] = {
    bhvDoorWarp, bhvStar,       bhvExitPodiumWarp, bhvWarp,
    bhvWarpPipe, bhvFadingWarp, bhvWarps60,        bhvWarps64,
    bhvWarps68,  bhvWarps6C,    bhvDeathWarp,      bhvWarps74,
    bhvWarps78,  bhvWarps94,    bhvWarps7C,        bhvPaintingDeathWarp,
    bhvWarps88,  bhvWarps84,    bhvWarps8C,        bhvWarps90,
};

// D_8032CEEC
u8 sSpawnTypeFromWarpBhv[] = {
    MARIO_SPAWN_UNKNOWN_01, MARIO_SPAWN_UNKNOWN_02, MARIO_SPAWN_UNKNOWN_03, MARIO_SPAWN_UNKNOWN_03,
    MARIO_SPAWN_UNKNOWN_03, MARIO_SPAWN_UNKNOWN_04, MARIO_SPAWN_UNKNOWN_10, MARIO_SPAWN_UNKNOWN_12,
    MARIO_SPAWN_UNKNOWN_13, MARIO_SPAWN_UNKNOWN_14, MARIO_SPAWN_DEATH,      MARIO_SPAWN_UNKNOWN_16,
    MARIO_SPAWN_UNKNOWN_17, MARIO_SPAWN_UNKNOWN_11, MARIO_SPAWN_UNKNOWN_20, MARIO_SPAWN_PAINTING_DEATH,
    MARIO_SPAWN_UNKNOWN_22, MARIO_SPAWN_UNKNOWN_23, MARIO_SPAWN_UNKNOWN_24, MARIO_SPAWN_UNKNOWN_25,
};

Vp D_8032CF00 = { {
    { 640, 480, 511, 0 },
    { 640, 480, 511, 0 },
} };

#ifdef VERSION_EU
const char *gNoControllerMsg[] = {
    "NO CONTROLLER",
    "MANETTE DEBRANCHEE",
    "CONTROLLER FEHLT",
};
#endif

void override_viewport_and_clip(Vp *a, Vp *b, u8 c, u8 d, u8 e) {
    u16 sp6 = ((c >> 3) << 11) | ((d >> 3) << 6) | ((e >> 3) << 1) | 1;

    gFBSetColor = (sp6 << 16) | sp6;
    D_8032CE74 = a;
    D_8032CE78 = b;
}

void set_warp_transition_rgb(u8 red, u8 green, u8 blue) {
    u16 warpTransitionRGBA16 = ((red >> 3) << 11) | ((green >> 3) << 6) | ((blue >> 3) << 1) | 1;

    gWarpTransFBSetColor = (warpTransitionRGBA16 << 16) | warpTransitionRGBA16;
    gWarpTransRed = red;
    gWarpTransGreen = green;
    gWarpTransBlue = blue;
}

void print_intro_text(void) {
#ifdef VERSION_EU
    int language = eu_get_language();
#endif
    if ((gGlobalTimer & 0x1F) < 20) {
        if (gControllerBits == 0) {
#ifdef VERSION_EU
            print_text_centered(160, 20, gNoControllerMsg[language]);
#else
            print_text_centered(160, 20, "NO CONTROLLER");
#endif
        } else {
#ifdef VERSION_EU
            print_text(20, 20, "START");
#else
            print_text_centered(60, 38, "PRESS");
            print_text_centered(60, 20, "START");
#endif
        }
    }
}

u32 get_mario_spawn_type(struct Object *o) {
    s32 i;
    const BehaviorScript *behavior = virtual_to_segmented(0x13, o->behavior);

    for (i = 0; i < 20; i++) {
        if (sWarpBhvSpawnTable[i] == behavior) {
            return sSpawnTypeFromWarpBhv[i];
        }
    }
    return 0;
}

struct ObjectWarpNode *area_get_warp_node(u8 id) {
    struct ObjectWarpNode *node = NULL;

    for (node = gCurrentArea->warpNodes; node != NULL; node = node->next) {
        if (node->node.id == id) {
            break;
        }
    }
    return node;
}

struct ObjectWarpNode *area_get_warp_node_from_params(struct Object *o) {
    u8 sp1F = (o->oBehParams & 0x00FF0000) >> 16;

    return area_get_warp_node(sp1F);
}

void load_obj_warp_nodes(void) {
    struct ObjectWarpNode *sp24;
    struct Object *sp20 = (struct Object *) gObjParentGraphNode.children;

    do {
        struct Object *sp1C = sp20;

        if (sp1C->activeFlags && get_mario_spawn_type(sp1C) != 0) {
            sp24 = area_get_warp_node_from_params(sp1C);
            if (sp24 != NULL) {
                sp24->object = sp1C;
            }
        }
    } while ((sp20 = (struct Object *) sp20->header.gfx.node.next)
             != (struct Object *) gObjParentGraphNode.children);
}

void clear_areas(void) {
    s32 i;

    gCurrentArea = NULL;
    gWarpTransition.isActive = FALSE;
    gWarpTransition.pauseRendering = FALSE;
    gMarioSpawnInfo->areaIndex = -1;

    for (i = 0; i < 8; i++) {
        gAreaData[i].index = i;
        gAreaData[i].flags = 0;
        gAreaData[i].terrainType = 0;
        gAreaData[i].unk04 = NULL;
        gAreaData[i].terrainData = NULL;
        gAreaData[i].surfaceRooms = NULL;
        gAreaData[i].macroObjects = NULL;
        gAreaData[i].warpNodes = NULL;
        gAreaData[i].paintingWarpNodes = NULL;
        gAreaData[i].instantWarps = NULL;
        gAreaData[i].objectSpawnInfos = NULL;
        gAreaData[i].camera = NULL;
        gAreaData[i].unused28 = NULL;
        gAreaData[i].whirlpools[0] = NULL;
        gAreaData[i].whirlpools[1] = NULL;
        gAreaData[i].dialog[0] = 255;
        gAreaData[i].dialog[1] = 255;
        gAreaData[i].musicParam = 0;
        gAreaData[i].musicParam2 = 0;
    }
}

void clear_area_graph_nodes(void) {
    s32 i;

    if (gCurrentArea != NULL) {
        geo_call_global_function_nodes(gCurrentArea->unk04, GEO_CONTEXT_AREA_UNLOAD);
        gCurrentArea = NULL;
        gWarpTransition.isActive = FALSE;
    }

    for (i = 0; i < 8; i++) {
        if (gAreaData[i].unk04 != NULL) {
            geo_call_global_function_nodes(gAreaData[i].unk04, GEO_CONTEXT_AREA_INIT);
            gAreaData[i].unk04 = NULL;
        }
    }
}

void load_area(s32 index) {
    if (gCurrentArea == NULL && gAreaData[index].unk04 != NULL) {
        gCurrentArea = &gAreaData[index];
        gCurrAreaIndex = gCurrentArea->index;

        if (gCurrentArea->terrainData != NULL) {
            load_area_terrain(index, gCurrentArea->terrainData, gCurrentArea->surfaceRooms,
                              gCurrentArea->macroObjects);
        }

        if (gCurrentArea->objectSpawnInfos != NULL) {
            spawn_objects_from_info(0, gCurrentArea->objectSpawnInfos);
        }

        load_obj_warp_nodes();
        geo_call_global_function_nodes(gCurrentArea->unk04, GEO_CONTEXT_AREA_LOAD);
    }
}

void unload_area(void) {
    if (gCurrentArea != NULL) {
        unload_objects_from_area(0, gCurrentArea->index);
        geo_call_global_function_nodes(gCurrentArea->unk04, GEO_CONTEXT_AREA_UNLOAD);

        gCurrentArea->flags = 0;
        gCurrentArea = NULL;
        gWarpTransition.isActive = FALSE;
    }
}

void load_mario_area(void) {
    func_80320890();
    load_area(gMarioSpawnInfo->areaIndex);

    if (gCurrentArea->index == gMarioSpawnInfo->areaIndex) {
        gCurrentArea->flags |= 0x01;
        spawn_objects_from_info(0, gMarioSpawnInfo);
    }
}

void unload_mario_area(void) {
    if (gCurrentArea != NULL && (gCurrentArea->flags & 0x01)) {
        unload_objects_from_area(0, gMarioSpawnInfo->activeAreaIndex);

        gCurrentArea->flags &= ~0x01;
        if (gCurrentArea->flags == 0) {
            unload_area();
        }
    }
}

void change_area(s32 index) {
    s32 areaFlags = gCurrentArea->flags;

    if (gCurrAreaIndex != index) {
        unload_area();
        load_area(index);

        gCurrentArea->flags = areaFlags;
        gMarioObject->oActiveParticleFlags = 0;
    }

    if (areaFlags & 0x01) {
        gMarioObject->header.gfx.unk18 = index, gMarioSpawnInfo->areaIndex = index;
    }
}

void area_update_objects(void) {
    gAreaUpdateCounter++;
    update_objects(0);
}

/*
 * Sets up the information needed to play a warp transition, including the
 * transition type, time in frames, and the RGB color that will fill the screen.
 */
void play_transition(s16 transType, s16 time, u8 red, u8 green, u8 blue) {
    gWarpTransition.isActive = TRUE;
    gWarpTransition.type = transType;
    gWarpTransition.time = time;
    gWarpTransition.pauseRendering = FALSE;

    // The lowest bit of transType determines if the transition is fading in or out.
    if (transType & 1) {
        set_warp_transition_rgb(red, green, blue);
    } else {
        red = gWarpTransRed, green = gWarpTransGreen, blue = gWarpTransBlue;
    }

    if (transType < 8) { // if transition is RGB
        gWarpTransition.data.red = red;
        gWarpTransition.data.green = green;
        gWarpTransition.data.blue = blue;
    } else { // if transition is textured
        gWarpTransition.data.red = red;
        gWarpTransition.data.green = green;
        gWarpTransition.data.blue = blue;

        // Both the start and end textured transition are always located in the middle of the screen.
        // If you really wanted to, you could place the start at one corner and the end at
        // the opposite corner. This will make the transition image look like it is moving
        // across the screen.
        gWarpTransition.data.startTexX = SCREEN_WIDTH / 2;
        gWarpTransition.data.startTexY = SCREEN_HEIGHT / 2;
        gWarpTransition.data.endTexX = SCREEN_WIDTH / 2;
        gWarpTransition.data.endTexY = SCREEN_HEIGHT / 2;

        gWarpTransition.data.texTimer = 0;

        if (transType & 1) // Is the image fading in?
        {
            gWarpTransition.data.startTexRadius = SCREEN_WIDTH;
            if (transType >= 0x0F) {
                gWarpTransition.data.endTexRadius = 16;
            } else {
                gWarpTransition.data.endTexRadius = 0;
            }
        } else // The image is fading out. (Reverses start & end circles)
        {
            if (transType >= 0x0E) {
                gWarpTransition.data.startTexRadius = 16;
            } else {
                gWarpTransition.data.startTexRadius = 0;
            }
            gWarpTransition.data.endTexRadius = SCREEN_WIDTH;
        }
    }
}

/*
 * Sets up the information needed to play a warp transition, including the
 * transition type, time in frames, and the RGB color that will fill the screen.
 * The transition will play only after a number of frames specified by 'delay'
 */
void play_transition_after_delay(s16 transType, s16 time, u8 red, u8 green, u8 blue, s16 delay) {
    gWarpTransDelay = delay; // Number of frames to delay playing the transition.
    play_transition(transType, time, red, green, blue);
}

void render_game(void) {
    if (gCurrentArea != NULL && !gWarpTransition.pauseRendering) {
        geo_process_root(gCurrentArea->unk04, D_8032CE74, D_8032CE78, gFBSetColor);

        gSPViewport(gDisplayListHead++, VIRTUAL_TO_PHYSICAL(&D_8032CF00));

        gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, BORDER_HEIGHT, SCREEN_WIDTH,
                      SCREEN_HEIGHT - BORDER_HEIGHT);
        render_hud();

        gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        render_text_labels();
        do_cutscene_handler();
        print_displaying_credits_entry();
        gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, BORDER_HEIGHT, SCREEN_WIDTH,
                      SCREEN_HEIGHT - BORDER_HEIGHT);
        gPauseScreenMode = render_menus_and_dialogs();

        if (gPauseScreenMode != 0) {
            gSaveOptSelectIndex = gPauseScreenMode;
        }
        
        if (D_8032CE78 != NULL) {
            make_viewport_clip_rect(D_8032CE78);
        } else
            gDPSetScissor(gDisplayListHead++, G_SC_NON_INTERLACE, 0, BORDER_HEIGHT, SCREEN_WIDTH,
                          SCREEN_HEIGHT - BORDER_HEIGHT);

        if (gWarpTransition.isActive) {
            if (gWarpTransDelay == 0) {
                gWarpTransition.isActive = !render_screen_transition(0, gWarpTransition.type, gWarpTransition.time,
                                                          &gWarpTransition.data);
                if (!gWarpTransition.isActive) {
                    if (gWarpTransition.type & 1) {
                        gWarpTransition.pauseRendering = TRUE;
                    } else {
                        set_warp_transition_rgb(0, 0, 0);
                    }
                }
            } else {
                gWarpTransDelay--;
            }
        }
    } else {
        render_text_labels();
        if (D_8032CE78 != 0) {
            clear_viewport(D_8032CE78, gWarpTransFBSetColor);
        } else {
            clear_frame_buffer(gWarpTransFBSetColor);
        }
    }

    D_8032CE74 = NULL;
    D_8032CE78 = 0;
}
