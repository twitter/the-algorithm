#include <PR/ultratypes.h>

#include "sm64.h"
#include "area.h"
#include "audio/external.h"
#include "behavior_actions.h"
#include "behavior_data.h"
#include "camera.h"
#include "dialog_ids.h"
#include "engine/behavior_script.h"
#include "engine/graph_node.h"
#include "engine/math_util.h"
#include "envfx_snow.h"
#include "game_init.h"
#include "goddard/renderer.h"
#include "interaction.h"
#include "level_update.h"
#include "mario_actions_cutscene.h"
#include "mario_misc.h"
#include "memory.h"
#include "object_helpers.h"
#include "object_list_processor.h"
#include "rendering_graph_node.h"
#include "save_file.h"
#include "skybox.h"
#include "sound_init.h"

#define TOAD_STAR_1_REQUIREMENT 12
#define TOAD_STAR_2_REQUIREMENT 25
#define TOAD_STAR_3_REQUIREMENT 35

#define TOAD_STAR_1_DIALOG DIALOG_082
#define TOAD_STAR_2_DIALOG DIALOG_076
#define TOAD_STAR_3_DIALOG DIALOG_083

#define TOAD_STAR_1_DIALOG_AFTER DIALOG_154
#define TOAD_STAR_2_DIALOG_AFTER DIALOG_155
#define TOAD_STAR_3_DIALOG_AFTER DIALOG_156

enum ToadMessageStates {
    TOAD_MESSAGE_FADED,
    TOAD_MESSAGE_OPAQUE,
    TOAD_MESSAGE_OPACIFYING,
    TOAD_MESSAGE_FADING,
    TOAD_MESSAGE_TALKING
};

enum UnlockDoorStarStates {
    UNLOCK_DOOR_STAR_RISING,
    UNLOCK_DOOR_STAR_WAITING,
    UNLOCK_DOOR_STAR_SPAWNING_PARTICLES,
    UNLOCK_DOOR_STAR_DONE
};

/**
 * The eye texture on succesive frames of Mario's blink animation.
 * He intentionally blinks twice each time.
 */
static s8 gMarioBlinkAnimation[7] = { 1, 2, 1, 0, 1, 2, 1 };

/**
 * The scale values per frame for Mario's foot/hand for his attack animation
 * There are 3 scale animations in groups of 6 frames.
 * The first animation starts at frame index 3 and goes down, the others start at frame index 5.
 * The values get divided by 10 before assigning, so e.g. 12 gives a scale factor 1.2.
 * All combined, this means e.g. the first animation scales Mario's fist by {2.4, 1.6, 1.2, 1.0} on
 * successive frames.
 */
static s8 gMarioAttackScaleAnimation[3 * 6] = {
    10, 12, 16, 24, 10, 10, 10, 14, 20, 30, 10, 10, 10, 16, 20, 26, 26, 20,
};

struct MarioBodyState gBodyStates[2]; // 2nd is never accessed in practice, most likely Luigi related
struct GraphNodeObject gMirrorMario;  // copy of Mario's geo node for drawing mirror Mario

// This whole file is weirdly organized. It has to be the same file due
// to rodata boundaries and function aligns, which means the programmer
// treated this like a "misc" file for vaguely Mario related things
// (message NPC related things, the Mario head geo, and Mario geo
// functions)

/**
 * Geo node script that draws Mario's head on the title screen.
 */
Gfx *geo_draw_mario_head_goddard(s32 callContext, struct GraphNode *node, Mat4 *c) {
    Gfx *gfx = NULL;
    s16 sfx = 0;
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;
    UNUSED Mat4 *transform = c;

    if (callContext == GEO_CONTEXT_RENDER) {
        if (gPlayer1Controller->controllerData != NULL && !gWarpTransition.isActive) {
            gd_copy_p1_contpad(gPlayer1Controller->controllerData);
        }
        gfx = (Gfx *) PHYSICAL_TO_VIRTUAL(gdm_gettestdl(asGenerated->parameter));
        gGoddardVblankCallback = gd_vblank;
        sfx = gd_sfx_to_play();
        play_menu_sounds(sfx);
    }
    return gfx;
}

static void toad_message_faded(void) {
    if (gCurrentObject->oDistanceToMario > 700.0f) {
        gCurrentObject->oToadMessageRecentlyTalked = FALSE;
    }
    if (!gCurrentObject->oToadMessageRecentlyTalked && gCurrentObject->oDistanceToMario < 600.0f) {
        gCurrentObject->oToadMessageState = TOAD_MESSAGE_OPACIFYING;
    }
}

static void toad_message_opaque(void) {
    if (gCurrentObject->oDistanceToMario > 700.0f) {
        gCurrentObject->oToadMessageState = TOAD_MESSAGE_FADING;
    } else if (!gCurrentObject->oToadMessageRecentlyTalked) {
        gCurrentObject->oInteractionSubtype = INT_SUBTYPE_NPC;
        if (gCurrentObject->oInteractStatus & INT_STATUS_INTERACTED) {
            gCurrentObject->oInteractStatus = 0;
            gCurrentObject->oToadMessageState = TOAD_MESSAGE_TALKING;
            play_toads_jingle();
        }
    }
}

static void toad_message_talking(void) {
    if (cur_obj_update_dialog_with_cutscene(MARIO_DIALOG_LOOK_DOWN, 
        DIALOG_FLAG_TURN_TO_MARIO, CUTSCENE_DIALOG, gCurrentObject->oToadMessageDialogId)) {
        gCurrentObject->oToadMessageRecentlyTalked = TRUE;
        gCurrentObject->oToadMessageState = TOAD_MESSAGE_FADING;
        switch (gCurrentObject->oToadMessageDialogId) {
            case TOAD_STAR_1_DIALOG:
                gCurrentObject->oToadMessageDialogId = TOAD_STAR_1_DIALOG_AFTER;
                bhv_spawn_star_no_level_exit(0);
                break;
            case TOAD_STAR_2_DIALOG:
                gCurrentObject->oToadMessageDialogId = TOAD_STAR_2_DIALOG_AFTER;
                bhv_spawn_star_no_level_exit(1);
                break;
            case TOAD_STAR_3_DIALOG:
                gCurrentObject->oToadMessageDialogId = TOAD_STAR_3_DIALOG_AFTER;
                bhv_spawn_star_no_level_exit(2);
                break;
        }
    }
}

static void toad_message_opacifying(void) {
    if ((gCurrentObject->oOpacity += 6) == 255) {
        gCurrentObject->oToadMessageState = TOAD_MESSAGE_OPAQUE;
    }
}

static void toad_message_fading(void) {
    if ((gCurrentObject->oOpacity -= 6) == 81) {
        gCurrentObject->oToadMessageState = TOAD_MESSAGE_FADED;
    }
}

void bhv_toad_message_loop(void) {
    if (gCurrentObject->header.gfx.node.flags & GRAPH_RENDER_ACTIVE) {
        gCurrentObject->oInteractionSubtype = 0;
        switch (gCurrentObject->oToadMessageState) {
            case TOAD_MESSAGE_FADED:
                toad_message_faded();
                break;
            case TOAD_MESSAGE_OPAQUE:
                toad_message_opaque();
                break;
            case TOAD_MESSAGE_OPACIFYING:
                toad_message_opacifying();
                break;
            case TOAD_MESSAGE_FADING:
                toad_message_fading();
                break;
            case TOAD_MESSAGE_TALKING:
                toad_message_talking();
                break;
        }
    }
}

void bhv_toad_message_init(void) {
    s32 saveFlags = save_file_get_flags();
    s32 starCount = save_file_get_total_star_count(gCurrSaveFileNum - 1, COURSE_MIN - 1, COURSE_MAX - 1);
    s32 dialogId = (gCurrentObject->oBehParams >> 24) & 0xFF;
    s32 enoughStars = TRUE;

    switch (dialogId) {
        case TOAD_STAR_1_DIALOG:
            enoughStars = (starCount >= TOAD_STAR_1_REQUIREMENT);
            if (saveFlags & SAVE_FLAG_COLLECTED_TOAD_STAR_1) {
                dialogId = TOAD_STAR_1_DIALOG_AFTER;
            }
            break;
        case TOAD_STAR_2_DIALOG:
            enoughStars = (starCount >= TOAD_STAR_2_REQUIREMENT);
            if (saveFlags & SAVE_FLAG_COLLECTED_TOAD_STAR_2) {
                dialogId = TOAD_STAR_2_DIALOG_AFTER;
            }
            break;
        case TOAD_STAR_3_DIALOG:
            enoughStars = (starCount >= TOAD_STAR_3_REQUIREMENT);
            if (saveFlags & SAVE_FLAG_COLLECTED_TOAD_STAR_3) {
                dialogId = TOAD_STAR_3_DIALOG_AFTER;
            }
            break;
    }
    if (enoughStars) {
        gCurrentObject->oToadMessageDialogId = dialogId;
        gCurrentObject->oToadMessageRecentlyTalked = FALSE;
        gCurrentObject->oToadMessageState = TOAD_MESSAGE_FADED;
        gCurrentObject->oOpacity = 81;
    } else {
        obj_mark_for_deletion(gCurrentObject);
    }
}

static void star_door_unlock_spawn_particles(s16 angleOffset) {
    struct Object *sparkleParticle = spawn_object(gCurrentObject, 0, bhvSparkleSpawn);

    sparkleParticle->oPosX +=
        100.0f * sins((gCurrentObject->oUnlockDoorStarTimer * 0x2800) + angleOffset);
    sparkleParticle->oPosZ +=
        100.0f * coss((gCurrentObject->oUnlockDoorStarTimer * 0x2800) + angleOffset);
    // Particles are spawned lower each frame
    sparkleParticle->oPosY -= gCurrentObject->oUnlockDoorStarTimer * 10.0f;
}

void bhv_unlock_door_star_init(void) {
    gCurrentObject->oUnlockDoorStarState = UNLOCK_DOOR_STAR_RISING;
    gCurrentObject->oUnlockDoorStarTimer = 0;
    gCurrentObject->oUnlockDoorStarYawVel = 0x1000;
    gCurrentObject->oPosX += 30.0f * sins(gMarioState->faceAngle[1] - 0x4000);
    gCurrentObject->oPosY += 160.0f;
    gCurrentObject->oPosZ += 30.0f * coss(gMarioState->faceAngle[1] - 0x4000);
    gCurrentObject->oMoveAngleYaw = 0x7800;
    obj_scale(gCurrentObject, 0.5f);
}

void bhv_unlock_door_star_loop(void) {
    UNUSED u8 filler1[4];
    s16 prevYaw = gCurrentObject->oMoveAngleYaw;
    UNUSED u8 filler2[4];

    // Speed up the star every frame
    if (gCurrentObject->oUnlockDoorStarYawVel < 0x2400) {
        gCurrentObject->oUnlockDoorStarYawVel += 0x60;
    }
    switch (gCurrentObject->oUnlockDoorStarState) {
        case UNLOCK_DOOR_STAR_RISING:
            gCurrentObject->oPosY += 3.4f; // Raise the star up in the air
            gCurrentObject->oMoveAngleYaw +=
                gCurrentObject->oUnlockDoorStarYawVel; // Apply yaw velocity
            obj_scale(gCurrentObject, gCurrentObject->oUnlockDoorStarTimer / 50.0f
                                             + 0.5f); // Scale the star to be bigger
            if (++gCurrentObject->oUnlockDoorStarTimer == 30) {
                gCurrentObject->oUnlockDoorStarTimer = 0;
                gCurrentObject->oUnlockDoorStarState++; // Sets state to UNLOCK_DOOR_STAR_WAITING
            }
            break;
        case UNLOCK_DOOR_STAR_WAITING:
            gCurrentObject->oMoveAngleYaw +=
                gCurrentObject->oUnlockDoorStarYawVel; // Apply yaw velocity
            if (++gCurrentObject->oUnlockDoorStarTimer == 30) {
                play_sound(SOUND_MENU_STAR_SOUND,
                           gCurrentObject->header.gfx.cameraToObject); // Play final sound
                cur_obj_hide();                                            // Hide the object
                gCurrentObject->oUnlockDoorStarTimer = 0;
                gCurrentObject
                    ->oUnlockDoorStarState++; // Sets state to UNLOCK_DOOR_STAR_SPAWNING_PARTICLES
            }
            break;
        case UNLOCK_DOOR_STAR_SPAWNING_PARTICLES:
            // Spawn two particles, opposite sides of the star.
            star_door_unlock_spawn_particles(0);
            star_door_unlock_spawn_particles(0x8000);
            if (gCurrentObject->oUnlockDoorStarTimer++ == 20) {
                gCurrentObject->oUnlockDoorStarTimer = 0;
                gCurrentObject->oUnlockDoorStarState++; // Sets state to UNLOCK_DOOR_STAR_DONE
            }
            break;
        case UNLOCK_DOOR_STAR_DONE: // The object stays loaded for an additional 50 frames so that the
                                    // sound doesn't immediately stop.
            if (gCurrentObject->oUnlockDoorStarTimer++ == 50) {
                obj_mark_for_deletion(gCurrentObject);
            }
            break;
    }
    // Checks if the angle has cycled back to 0.
    // This means that the code will execute when the star completes a full revolution.
    if (prevYaw > (s16) gCurrentObject->oMoveAngleYaw) {
        play_sound(
            SOUND_GENERAL_SHORT_STAR,
            gCurrentObject->header.gfx.cameraToObject); // Play a sound every time the star spins once
    }
}

/**
 * Generate a display list that sets the correct blend mode and color for mirror Mario.
 */
static Gfx *make_gfx_mario_alpha(struct GraphNodeGenerated *node, s16 alpha) {
    Gfx *gfx;
    Gfx *gfxHead = NULL;

    if (alpha == 255) {
        node->fnNode.node.flags = (node->fnNode.node.flags & 0xFF) | (LAYER_OPAQUE << 8);
        gfxHead = alloc_display_list(2 * sizeof(*gfxHead));
        gfx = gfxHead;
    } else {
        node->fnNode.node.flags = (node->fnNode.node.flags & 0xFF) | (LAYER_TRANSPARENT << 8);
        gfxHead = alloc_display_list(3 * sizeof(*gfxHead));
        gfx = gfxHead;
        gDPSetAlphaCompare(gfx++, G_AC_DITHER);
    }
    gDPSetEnvColor(gfx++, 255, 255, 255, alpha);
    gSPEndDisplayList(gfx);
    return gfxHead;
}

/**
 * Sets the correct blend mode and color for mirror Mario.
 */
Gfx *geo_mirror_mario_set_alpha(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    UNUSED u8 filler1[4];
    Gfx *gfx = NULL;
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;
    struct MarioBodyState *bodyState = &gBodyStates[asGenerated->parameter];
    s16 alpha;
    UNUSED u8 filler2[4];

    if (callContext == GEO_CONTEXT_RENDER) {
        alpha = (bodyState->modelState & 0x100) ? (bodyState->modelState & 0xFF) : 255;
        gfx = make_gfx_mario_alpha(asGenerated, alpha);
    }
    return gfx;
}

/**
 * Determines if Mario is standing or running for the level of detail of his model.
 * If Mario is standing still, he is always high poly. If he is running,
 * his level of detail depends on the distance to the camera.
 */
Gfx *geo_switch_mario_stand_run(s32 callContext, struct GraphNode *node, UNUSED Mat4 *mtx) {
    struct GraphNodeSwitchCase *switchCase = (struct GraphNodeSwitchCase *) node;
    struct MarioBodyState *bodyState = &gBodyStates[switchCase->numCases];

    if (callContext == GEO_CONTEXT_RENDER) {
        // assign result. 0 if moving, 1 if stationary.
        switchCase->selectedCase = ((bodyState->action & ACT_FLAG_STATIONARY) == 0);
    }
    return NULL;
}

/**
 * Geo node script that makes Mario blink
 */
Gfx *geo_switch_mario_eyes(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNodeSwitchCase *switchCase = (struct GraphNodeSwitchCase *) node;
    struct MarioBodyState *bodyState = &gBodyStates[switchCase->numCases];
    s16 blinkFrame;

    if (callContext == GEO_CONTEXT_RENDER) {
        if (bodyState->eyeState == 0) {
            blinkFrame = ((switchCase->numCases * 32 + gAreaUpdateCounter) >> 1) & 0x1F;
            if (blinkFrame < 7) {
                switchCase->selectedCase = gMarioBlinkAnimation[blinkFrame];
            } else {
                switchCase->selectedCase = 0;
            }
        } else {
            switchCase->selectedCase = bodyState->eyeState - 1;
        }
    }
    return NULL;
}

/**
 * Makes Mario's upper body tilt depending on the rotation stored in his bodyState
 */
Gfx *geo_mario_tilt_torso(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;
    struct MarioBodyState *bodyState = &gBodyStates[asGenerated->parameter];
    s32 action = bodyState->action;

    if (callContext == GEO_CONTEXT_RENDER) {
        struct GraphNodeRotation *rotNode = (struct GraphNodeRotation *) node->next;

        if (action != ACT_BUTT_SLIDE && action != ACT_HOLD_BUTT_SLIDE && action != ACT_WALKING
            && action != ACT_RIDING_SHELL_GROUND) {
            vec3s_copy(bodyState->torsoAngle, gVec3sZero);
        }
        rotNode->rotation[0] = bodyState->torsoAngle[1];
        rotNode->rotation[1] = bodyState->torsoAngle[2];
        rotNode->rotation[2] = bodyState->torsoAngle[0];
    }
    return NULL;
}

/**
 * Makes Mario's head rotate with the camera angle when in C-up mode
 */
Gfx *geo_mario_head_rotation(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;
    struct MarioBodyState *bodyState = &gBodyStates[asGenerated->parameter];
    s32 action = bodyState->action;

    if (callContext == GEO_CONTEXT_RENDER) {
        struct GraphNodeRotation *rotNode = (struct GraphNodeRotation *) node->next;
        struct Camera *camera = gCurGraphNodeCamera->config.camera;

        if (camera->mode == CAMERA_MODE_C_UP) {
            rotNode->rotation[0] = gPlayerCameraState->headRotation[1];
            rotNode->rotation[2] = gPlayerCameraState->headRotation[0];
        } else if (action & ACT_FLAG_WATER_OR_TEXT) {
            rotNode->rotation[0] = bodyState->headAngle[1];
            rotNode->rotation[1] = bodyState->headAngle[2];
            rotNode->rotation[2] = bodyState->headAngle[0];
        } else {
            vec3s_set(bodyState->headAngle, 0, 0, 0);
            vec3s_set(rotNode->rotation, 0, 0, 0);
        }
    }
    return NULL;
}

/**
 * Switch between hand models.
 * Possible options are described in the MarioHandGSCId enum.
 */
Gfx *geo_switch_mario_hand(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNodeSwitchCase *switchCase = (struct GraphNodeSwitchCase *) node;
    struct MarioBodyState *bodyState = &gBodyStates[0];

    if (callContext == GEO_CONTEXT_RENDER) {
        if (bodyState->handState == MARIO_HAND_FISTS) {
            // switch between fists (0) and open (1)
            switchCase->selectedCase = ((bodyState->action & ACT_FLAG_SWIMMING_OR_FLYING) != 0);
        } else {
            if (switchCase->numCases == 0) {
                switchCase->selectedCase =
                    (bodyState->handState < 5) ? bodyState->handState : MARIO_HAND_OPEN;
            } else {
                switchCase->selectedCase =
                    (bodyState->handState < 2) ? bodyState->handState : MARIO_HAND_FISTS;
            }
        }
    }
    return NULL;
}

/**
 * Increase Mario's hand / foot size when he punches / kicks.
 * Since animation geo nodes only support rotation, this scaling animation
 * was scripted separately. The node with this script should be placed before
 * a scaling node containing the hand / foot geo layout.
 * ! Since the animation gets updated in GEO_CONTEXT_RENDER, drawing Mario multiple times
 * (such as in the mirror room) results in a faster and desynced punch / kick animation.
 */
Gfx *geo_mario_hand_foot_scaler(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    static s16 sMarioAttackAnimCounter = 0;
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;
    struct GraphNodeScale *scaleNode = (struct GraphNodeScale *) node->next;
    struct MarioBodyState *bodyState = &gBodyStates[0];

    if (callContext == GEO_CONTEXT_RENDER) {
        scaleNode->scale = 1.0f;
        if (asGenerated->parameter == bodyState->punchState >> 6) {
            if (sMarioAttackAnimCounter != gAreaUpdateCounter && (bodyState->punchState & 0x3F) > 0) {
                bodyState->punchState -= 1;
                sMarioAttackAnimCounter = gAreaUpdateCounter;
            }
            scaleNode->scale =
                gMarioAttackScaleAnimation[asGenerated->parameter * 6 + (bodyState->punchState & 0x3F)]
                / 10.0f;
        }
    }
    return NULL;
}

/**
 * Switch between normal cap, wing cap, vanish cap and metal cap.
 */
Gfx *geo_switch_mario_cap_effect(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNodeSwitchCase *switchCase = (struct GraphNodeSwitchCase *) node;
    struct MarioBodyState *bodyState = &gBodyStates[switchCase->numCases];

    if (callContext == GEO_CONTEXT_RENDER) {
        switchCase->selectedCase = bodyState->modelState >> 8;
    }
    return NULL;
}

/**
 * Determine whether Mario's head is drawn with or without a cap on.
 * Also sets the visibility of the wing cap wings on or off.
 */
Gfx *geo_switch_mario_cap_on_off(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNode *next = node->next;
    struct GraphNodeSwitchCase *switchCase = (struct GraphNodeSwitchCase *) node;
    struct MarioBodyState *bodyState = &gBodyStates[switchCase->numCases];

    if (callContext == GEO_CONTEXT_RENDER) {
        switchCase->selectedCase = bodyState->capState & 1;
        while (next != node) {
            if (next->type == GRAPH_NODE_TYPE_TRANSLATION_ROTATION) {
                if (bodyState->capState & 2) {
                    next->flags |= GRAPH_RENDER_ACTIVE;
                } else {
                    next->flags &= ~GRAPH_RENDER_ACTIVE;
                }
            }
            next = next->next;
        }
    }
    return NULL;
}

/**
 * Geo node script that makes the wings on Mario's wing cap flap.
 * Should be placed before a rotation node.
 */
Gfx *geo_mario_rotate_wing_cap_wings(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    s16 rotX;
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;

    if (callContext == GEO_CONTEXT_RENDER) {
        struct GraphNodeRotation *rotNode = (struct GraphNodeRotation *) node->next;

        if (!gBodyStates[asGenerated->parameter >> 1].wingFlutter) {
            rotX = (coss((gAreaUpdateCounter & 0xF) << 12) + 1.0f) * 4096.0f;
        } else {
            rotX = (coss((gAreaUpdateCounter & 7) << 13) + 1.0f) * 6144.0f;
        }
        if (!(asGenerated->parameter & 1)) {
            rotNode->rotation[0] = -rotX;
        } else {
            rotNode->rotation[0] = rotX;
        }
    }
    return NULL;
}

/**
 * Geo node that updates the held object node and the HOLP.
 */
Gfx *geo_switch_mario_hand_grab_pos(s32 callContext, struct GraphNode *b, Mat4 *mtx) {
    struct GraphNodeHeldObject *asHeldObj = (struct GraphNodeHeldObject *) b;
    Mat4 *curTransform = mtx;
    struct MarioState *marioState = &gMarioStates[asHeldObj->playerIndex];

    if (callContext == GEO_CONTEXT_RENDER) {
        asHeldObj->objNode = NULL;
        if (marioState->heldObj != NULL) {
            asHeldObj->objNode = marioState->heldObj;
            switch (marioState->marioBodyState->grabPos) {
                case GRAB_POS_LIGHT_OBJ:
                    if (marioState->action & ACT_FLAG_THROWING) {
                        vec3s_set(asHeldObj->translation, 50, 0, 0);
                    } else {
                        vec3s_set(asHeldObj->translation, 50, 0, 110);
                    }
                    break;
                case GRAB_POS_HEAVY_OBJ:
                    vec3s_set(asHeldObj->translation, 145, -173, 180);
                    break;
                case GRAB_POS_BOWSER:
                    vec3s_set(asHeldObj->translation, 80, -270, 1260);
                    break;
            }
        }
    } else if (callContext == GEO_CONTEXT_HELD_OBJ) {
        // ! The HOLP is set here, which is why it only updates when the held object is drawn.
        // This is why it won't update during a pause buffered hitstun or when the camera is very far
        // away.
        get_pos_from_transform_mtx(marioState->marioBodyState->heldObjLastPosition, *curTransform,
                                   *gCurGraphNodeCamera->matrixPtr);
    }
    return NULL;
}

// X position of the mirror
#define MIRROR_X 4331.53

/**
 * Geo node that creates a clone of Mario's geo node and updates it to becomes
 * a mirror image of the player.
 */
Gfx *geo_render_mirror_mario(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    f32 mirroredX;
    struct Object *mario = gMarioStates[0].marioObj;

    switch (callContext) {
        case GEO_CONTEXT_CREATE:
            init_graph_node_object(NULL, &gMirrorMario, NULL, gVec3fZero, gVec3sZero, gVec3fOne);
            break;
        case GEO_CONTEXT_AREA_LOAD:
            geo_add_child(node, &gMirrorMario.node);
            break;
        case GEO_CONTEXT_AREA_UNLOAD:
            geo_remove_child(&gMirrorMario.node);
            break;
        case GEO_CONTEXT_RENDER:
            if (mario->header.gfx.pos[0] > 1700.0f) {
                // TODO: Is this a geo layout copy or a graph node copy?
                gMirrorMario.sharedChild = mario->header.gfx.sharedChild;
                gMirrorMario.areaIndex = mario->header.gfx.areaIndex;
                vec3s_copy(gMirrorMario.angle, mario->header.gfx.angle);
                vec3f_copy(gMirrorMario.pos, mario->header.gfx.pos);
                vec3f_copy(gMirrorMario.scale, mario->header.gfx.scale);

                gMirrorMario.animInfo = mario->header.gfx.animInfo;
                mirroredX = MIRROR_X - gMirrorMario.pos[0];
                gMirrorMario.pos[0] = mirroredX + MIRROR_X;
                gMirrorMario.angle[1] = -gMirrorMario.angle[1];
                gMirrorMario.scale[0] *= -1.0f;
                ((struct GraphNode *) &gMirrorMario)->flags |= GRAPH_RENDER_ACTIVE;
            } else {
                ((struct GraphNode *) &gMirrorMario)->flags &= ~GRAPH_RENDER_ACTIVE;
            }
            break;
    }
    return NULL;
}

/**
 * Since Mirror Mario has an x scale of -1, the mesh becomes inside out.
 * This node corrects that by changing the culling mode accordingly.
 */
Gfx *geo_mirror_mario_backface_culling(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct GraphNodeGenerated *asGenerated = (struct GraphNodeGenerated *) node;
    Gfx *gfx = NULL;

    if (callContext == GEO_CONTEXT_RENDER && gCurGraphNodeObject == &gMirrorMario) {
        gfx = alloc_display_list(3 * sizeof(*gfx));

        if (asGenerated->parameter == 0) {
            gSPClearGeometryMode(&gfx[0], G_CULL_BACK);
            gSPSetGeometryMode(&gfx[1], G_CULL_FRONT);
            gSPEndDisplayList(&gfx[2]);
        } else {
            gSPClearGeometryMode(&gfx[0], G_CULL_FRONT);
            gSPSetGeometryMode(&gfx[1], G_CULL_BACK);
            gSPEndDisplayList(&gfx[2]);
        }
        asGenerated->fnNode.node.flags = (asGenerated->fnNode.node.flags & 0xFF) | (LAYER_OPAQUE << 8);
    }
    return gfx;
}
