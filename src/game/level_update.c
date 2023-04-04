#include <ultra64.h>

#include "sm64.h"
#include "seq_ids.h"
#include "dialog_ids.h"
#include "audio/external.h"
#include "level_update.h"
#include "game_init.h"
#include "level_update.h"
#include "main.h"
#include "engine/math_util.h"
#include "engine/graph_node.h"
#include "area.h"
#include "save_file.h"
#include "sound_init.h"
#include "mario.h"
#include "camera.h"
#include "object_list_processor.h"
#include "ingame_menu.h"
#include "obj_behaviors.h"
#include "save_file.h"
#include "debug_course.h"
#ifdef VERSION_EU
#include "memory.h"
#include "eu_translation.h"
#include "segment_symbols.h"
#endif
#include "level_table.h"
#include "course_table.h"
#include "rumble_init.h"

#define PLAY_MODE_NORMAL 0
#define PLAY_MODE_PAUSED 2
#define PLAY_MODE_CHANGE_AREA 3
#define PLAY_MODE_CHANGE_LEVEL 4
#define PLAY_MODE_FRAME_ADVANCE 5

#define WARP_TYPE_NOT_WARPING 0
#define WARP_TYPE_CHANGE_LEVEL 1
#define WARP_TYPE_CHANGE_AREA 2
#define WARP_TYPE_SAME_AREA 3

#define WARP_NODE_F0 0xF0
#define WARP_NODE_DEATH 0xF1
#define WARP_NODE_F2 0xF2
#define WARP_NODE_WARP_FLOOR 0xF3
#define WARP_NODE_CREDITS_START 0xF8
#define WARP_NODE_CREDITS_NEXT 0xF9
#define WARP_NODE_CREDITS_END 0xFA

#define WARP_NODE_CREDITS_MIN 0xF8

// TODO: Make these ifdefs better
const char *credits01[] = { "1GAME DIRECTOR", "SHIGERU MIYAMOTO" };
const char *credits02[] = { "2ASSISTANT DIRECTORS", "YOSHIAKI KOIZUMI", "TAKASHI TEZUKA" };
const char *credits03[] = { "2SYSTEM PROGRAMMERS", "YASUNARI NISHIDA", "YOSHINORI TANIMOTO" };
const char *credits04[] = { "3PROGRAMMERS", "HAJIME YAJIMA", "DAIKI IWAMOTO", "TOSHIO IWAWAKI" };

#if defined(VERSION_JP) || defined(VERSION_SH)

const char *credits05[] = { "1CAMERA PROGRAMMER", "TAKUMI KAWAGOE" };
const char *credits06[] = { "1MARIO FACE PROGRAMMER", "GILES GODDARD" };
const char *credits07[] = { "2COURSE DIRECTORS", "YOICHI YAMADA", "YASUHISA YAMAMURA" };
const char *credits08[] = { "2COURSE DESIGNERS", "KENTA USUI", "NAOKI MORI" };
const char *credits09[] = { "3COURSE DESIGNERS", "YOSHIKI HARUHANA", "MAKOTO MIYANAGA", "KATSUHIKO KANNO" };
const char *credits10[] = { "1SOUND COMPOSER", "KOJI KONDO" };

#ifdef VERSION_JP
const char *credits11[] = { "1SOUND EFFECTS", "YOJI INAGAKI" };
const char *credits12[] = { "1SOUND PROGRAMMER", "HIDEAKI SHIMIZU" };
const char *credits13[] = { "23D ANIMATORS", "YOSHIAKI KOIZUMI", "SATORU TAKIZAWA" };
const char *credits14[] = { "1CG DESIGNER", "MASANAO ARIMOTO" };
const char *credits15[] = { "3TECHNICAL SUPPORT", "TAKAO SAWANO", "HIROHITO YOSHIMOTO", "HIROTO YADA" };
const char *credits16[] = { "1TECHNICAL SUPPORT", "SGI. 64PROJECT STAFF" };
const char *credits17[] = { "2PROGRESS MANAGEMENT", "KIMIYOSHI FUKUI", "KEIZO KATO" };
#else // VERSION_SH
// Shindou combines sound effects and sound programmer in order to make room for Mario voice and Peach voice
const char *credits11[] = { "4SOUND EFFECTS", "SOUND PROGRAMMER", "YOJI INAGAKI", "HIDEAKI SHIMIZU" };
const char *credits12[] = { "23D ANIMATORS", "YOSHIAKI KOIZUMI", "SATORU TAKIZAWA" };
const char *credits13[] = { "1CG DESIGNER", "MASANAO ARIMOTO" };
const char *credits14[] = { "3TECHNICAL SUPPORT", "TAKAO SAWANO", "HIROHITO YOSHIMOTO", "HIROTO YADA" };
const char *credits15[] = { "1TECHNICAL SUPPORT", "SGI. 64PROJECT STAFF" };
const char *credits16[] = { "2PROGRESS MANAGEMENT", "KIMIYOSHI FUKUI", "KEIZO KATO" };
#endif

#else // VERSION_US || VERSION_EU

// US and EU combine camera programmer and Mario face programmer...
const char *credits05[] = { "4CAMERA PROGRAMMER", "MARIO FACE PROGRAMMER", "TAKUMI KAWAGOE", "GILES GODDARD" };
const char *credits06[] = { "2COURSE DIRECTORS", "YOICHI YAMADA", "YASUHISA YAMAMURA" };
const char *credits07[] = { "2COURSE DESIGNERS", "KENTA USUI", "NAOKI MORI" };
const char *credits08[] = { "3COURSE DESIGNERS", "YOSHIKI HARUHANA", "MAKOTO MIYANAGA", "KATSUHIKO KANNO" };

#ifdef VERSION_US
const char *credits09[] = { "1SOUND COMPOSER", "KOJI KONDO" };
// ...as well as sound effects and sound programmer in order to make room for screen text writer, Mario voice, and Peach voice
const char *credits10[] = { "4SOUND EFFECTS", "SOUND PROGRAMMER", "YOJI INAGAKI", "HIDEAKI SHIMIZU" }; 
const char *credits11[] = { "23-D ANIMATORS", "YOSHIAKI KOIZUMI", "SATORU TAKIZAWA" };
const char *credits12[] = { "1ADDITIONAL GRAPHICS", "MASANAO ARIMOTO" };
const char *credits13[] = { "3TECHNICAL SUPPORT", "TAKAO SAWANO", "HIROHITO YOSHIMOTO", "HIROTO YADA" };
const char *credits14[] = { "1TECHNICAL SUPPORT", "SGI N64 PROJECT STAFF" };
const char *credits15[] = { "2PROGRESS MANAGEMENT", "KIMIYOSHI FUKUI", "KEIZO KATO" };
const char *credits16[] = { "5SCREEN TEXT WRITER", "TRANSLATION", "LESLIE SWAN", "MINA AKINO", "HIRO YAMADA" }; 
#else // VERSION_EU
// ...as well as sound composer, sound effects, and sound programmer, and...
const char *credits09[] = { "7SOUND COMPOSER", "SOUND EFFECTS", "SOUND PROGRAMMER", "KOJI KONDO", "YOJI INAGAKI", "HIDEAKI SHIMIZU" };
// ...3D animators and additional graphics in order to make room for screen text writer(s), Mario voice, and Peach voice
const char *credits10[] = { "63-D ANIMATORS", "ADDITIONAL GRAPHICS", "YOSHIAKI KOIZUMI", "SATORU TAKIZAWA", "MASANAO ARIMOTO" };
const char *credits11[] = { "3TECHNICAL SUPPORT", "TAKAO SAWANO", "HIROHITO YOSHIMOTO", "HIROTO YADA" };
const char *credits12[] = { "1TECHNICAL SUPPORT", "SGI N64 PROJECT STAFF" };
const char *credits13[] = { "2PROGRESS MANAGEMENT", "KIMIYOSHI FUKUI", "KEIZO KATO" };
const char *credits14[] = { "5SCREEN TEXT WRITER", "ENGLISH TRANSLATION", "LESLIE SWAN", "MINA AKINO", "HIRO YAMADA" };
const char *credits15[] = { "4SCREEN TEXT WRITER", "FRENCH TRANSLATION", "JULIEN BARDAKOFF", "KENJI HARAGUCHI" };
const char *credits16[] = { "4SCREEN TEXT WRITER", "GERMAN TRANSLATION", "THOMAS GOERG", "THOMAS SPINDLER" };
#endif

#endif

#ifndef VERSION_JP
const char *credits17[] = { "4MARIO VOICE", "PEACH VOICE", "CHARLES MARTINET", "LESLIE SWAN" };
#endif

#if defined(VERSION_JP) || defined(VERSION_SH)
const char *credits18[] = { "3SPECIAL THANKS TO", "JYOHO KAIHATUBU", "ALL NINTENDO", "MARIO CLUB STAFF" };
#elif defined(VERSION_US)
const char *credits18[] = { "3SPECIAL THANKS TO", "EAD STAFF", "ALL NINTENDO PERSONNEL", "MARIO CLUB STAFF" };
#else // VERSION_EU
const char *credits18[] = { "3SPECIAL THANKS TO", "EAD STAFF", "ALL NINTENDO PERSONNEL", "SUPER MARIO CLUB STAFF" };
#endif

const char *credits19[] = { "1PRODUCER", "SHIGERU MIYAMOTO" };
const char *credits20[] = { "1EXECUTIVE PRODUCER", "HIROSHI YAMAUCHI" };


struct CreditsEntry sCreditsSequence[] = {
    { LEVEL_CASTLE_GROUNDS, 1, 1, -128, { 0, 8000, 0 }, NULL },
    { LEVEL_BOB, 1, 1, 117, { 713, 3918, -3889 }, credits01 },
    { LEVEL_WF, 1, 50, 46, { 347, 5376, 326 }, credits02 },
    { LEVEL_JRB, 1, 18, 22, { 3800, -4840, 2727 }, credits03 },
    { LEVEL_CCM, 2, 34, 25, { -5464, 6656, -6575 }, credits04 },
    { LEVEL_BBH, 1, 1, 60, { 257, 1922, 2580 }, credits05 },
    { LEVEL_HMC, 1, -15, 123, { -6469, 1616, -6054 }, credits06 },
    { LEVEL_THI, 3, 17, -32, { 508, 1024, 1942 }, credits07 },
    { LEVEL_LLL, 2, 33, 124, { -73, 82, -1467 }, credits08 },
    { LEVEL_SSL, 1, 65, 98, { -5906, 1024, -2576 }, credits09 },
    { LEVEL_DDD, 1, 50, 47, { -4884, -4607, -272 }, credits10 },
    { LEVEL_SL, 1, 17, -34, { 1925, 3328, 563 }, credits11 },
    { LEVEL_WDW, 1, 33, 105, { -537, 1850, 1818 }, credits12 },
    { LEVEL_TTM, 1, 2, -33, { 2613, 313, 1074 }, credits13 },
    { LEVEL_THI, 1, 51, 54, { -2609, 512, 856 }, credits14 },
    { LEVEL_TTC, 1, 17, -72, { -1304, -71, -967 }, credits15 },
    { LEVEL_RR, 1, 33, 64, { 1565, 1024, -148 }, credits16 },
    { LEVEL_SA, 1, 1, 24, { -1050, -1330, -1559 }, credits17 },
    { LEVEL_COTMC, 1, 49, -16, { -254, 415, -6045 }, credits18 },
    { LEVEL_DDD, 2, -111, -64, { 3948, 1185, -104 }, credits19 },
    { LEVEL_CCM, 1, 33, 31, { 3169, -4607, 5240 }, credits20 },
    { LEVEL_CASTLE_GROUNDS, 1, 1, -128, { 0, 906, -1200 }, NULL },
    { LEVEL_NONE, 0, 1, 0, { 0, 0, 0 }, NULL },
};

struct MarioState gMarioStates[1];
struct HudDisplay gHudDisplay;
s16 sCurrPlayMode;
u16 D_80339ECA;
s16 sTransitionTimer;
void (*sTransitionUpdate)(s16 *);
struct WarpDest sWarpDest;
s16 D_80339EE0;
s16 sDelayedWarpOp;
s16 sDelayedWarpTimer;
s16 sSourceWarpNodeId;
s32 sDelayedWarpArg;
#if defined(VERSION_EU) || defined(VERSION_SH)
s16 unusedEULevelUpdateBss1;
#endif
s8 sTimerRunning;
s8 gNeverEnteredCastle;

struct MarioState *gMarioState = &gMarioStates[0];
u8 unused1[4] = { 0 };
s8 sWarpCheckpointActive = FALSE;
u8 unused2[4];
u8 unused3[2];

u16 level_control_timer(s32 timerOp) {
    switch (timerOp) {
        case TIMER_CONTROL_SHOW:
            gHudDisplay.flags |= HUD_DISPLAY_FLAG_TIMER;
            sTimerRunning = FALSE;
            gHudDisplay.timer = 0;
            break;

        case TIMER_CONTROL_START:
            sTimerRunning = TRUE;
            break;

        case TIMER_CONTROL_STOP:
            sTimerRunning = FALSE;
            break;

        case TIMER_CONTROL_HIDE:
            gHudDisplay.flags &= ~HUD_DISPLAY_FLAG_TIMER;
            sTimerRunning = FALSE;
            gHudDisplay.timer = 0;
            break;
    }

    return gHudDisplay.timer;
}

u32 pressed_pause(void) {
    u32 dialogActive = get_dialog_id() >= 0;
    u32 intangible = (gMarioState->action & ACT_FLAG_INTANGIBLE) != 0;

    if (!intangible && !dialogActive && !gWarpTransition.isActive && sDelayedWarpOp == WARP_OP_NONE
        && (gPlayer1Controller->buttonPressed & START_BUTTON)) {
        return TRUE;
    }

    return FALSE;
}

void set_play_mode(s16 playMode) {
    sCurrPlayMode = playMode;
    D_80339ECA = 0;
}

void warp_special(s32 arg) {
    sCurrPlayMode = PLAY_MODE_CHANGE_LEVEL;
    D_80339ECA = 0;
    D_80339EE0 = arg;
}

void fade_into_special_warp(u32 arg, u32 color) {
    if (color != 0) {
        color = 0xFF;
    }

    fadeout_music(190);
    play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x10, color, color, color);
    level_set_transition(30, NULL);

    warp_special(arg);
}

void stub_level_update_1(void) {
}

void load_level_init_text(u32 arg) {
    s32 gotAchievement;
    u32 dialogID = gCurrentArea->dialog[arg];

    switch (dialogID) {
        case DIALOG_129:
            gotAchievement = save_file_get_flags() & SAVE_FLAG_HAVE_VANISH_CAP;
            break;

        case DIALOG_130:
            gotAchievement = save_file_get_flags() & SAVE_FLAG_HAVE_METAL_CAP;
            break;

        case DIALOG_131:
            gotAchievement = save_file_get_flags() & SAVE_FLAG_HAVE_WING_CAP;
            break;

        case (u8)DIALOG_NONE: // 255, cast value to u8 to match (-1)
            gotAchievement = TRUE;
            break;

        default:
            gotAchievement =
                save_file_get_star_flags(gCurrSaveFileNum - 1, COURSE_NUM_TO_INDEX(gCurrCourseNum));
            break;
    }

    if (!gotAchievement) {
        level_set_transition(-1, NULL);
        create_dialog_box(dialogID);
    }
}

void init_door_warp(struct SpawnInfo *spawnInfo, u32 arg1) {
    if (arg1 & 0x00000002) {
        spawnInfo->startAngle[1] += 0x8000;
    }

    spawnInfo->startPos[0] += 300.0f * sins(spawnInfo->startAngle[1]);
    spawnInfo->startPos[2] += 300.0f * coss(spawnInfo->startAngle[1]);
}

void set_mario_initial_cap_powerup(struct MarioState *m) {
    u32 capCourseIndex = gCurrCourseNum - COURSE_CAP_COURSES;

    switch (capCourseIndex) {
        case COURSE_COTMC - COURSE_CAP_COURSES:
            m->flags |= MARIO_METAL_CAP | MARIO_CAP_ON_HEAD;
            m->capTimer = 600;
            break;

        case COURSE_TOTWC - COURSE_CAP_COURSES:
            m->flags |= MARIO_WING_CAP | MARIO_CAP_ON_HEAD;
            m->capTimer = 1200;
            break;

        case COURSE_VCUTM - COURSE_CAP_COURSES:
            m->flags |= MARIO_VANISH_CAP | MARIO_CAP_ON_HEAD;
            m->capTimer = 600;
            break;
    }
}

void set_mario_initial_action(struct MarioState *m, u32 spawnType, u32 actionArg) {
    switch (spawnType) {
        case MARIO_SPAWN_DOOR_WARP:
            set_mario_action(m, ACT_WARP_DOOR_SPAWN, actionArg);
            break;
        case MARIO_SPAWN_UNKNOWN_02:
            set_mario_action(m, ACT_IDLE, 0);
            break;
        case MARIO_SPAWN_UNKNOWN_03:
            set_mario_action(m, ACT_EMERGE_FROM_PIPE, 0);
            break;
        case MARIO_SPAWN_TELEPORT:
            set_mario_action(m, ACT_TELEPORT_FADE_IN, 0);
            break;
        case MARIO_SPAWN_INSTANT_ACTIVE:
            set_mario_action(m, ACT_IDLE, 0);
            break;
        case MARIO_SPAWN_AIRBORNE:
            set_mario_action(m, ACT_SPAWN_NO_SPIN_AIRBORNE, 0);
            break;
        case MARIO_SPAWN_HARD_AIR_KNOCKBACK:
            set_mario_action(m, ACT_HARD_BACKWARD_AIR_KB, 0);
            break;
        case MARIO_SPAWN_SPIN_AIRBORNE_CIRCLE:
            set_mario_action(m, ACT_SPAWN_SPIN_AIRBORNE, 0);
            break;
        case MARIO_SPAWN_DEATH:
            set_mario_action(m, ACT_FALLING_DEATH_EXIT, 0);
            break;
        case MARIO_SPAWN_SPIN_AIRBORNE:
            set_mario_action(m, ACT_SPAWN_SPIN_AIRBORNE, 0);
            break;
        case MARIO_SPAWN_FLYING:
            set_mario_action(m, ACT_FLYING, 2);
            break;
        case MARIO_SPAWN_SWIMMING:
            set_mario_action(m, ACT_WATER_IDLE, 1);
            break;
        case MARIO_SPAWN_PAINTING_STAR_COLLECT:
            set_mario_action(m, ACT_EXIT_AIRBORNE, 0);
            break;
        case MARIO_SPAWN_PAINTING_DEATH:
            set_mario_action(m, ACT_DEATH_EXIT, 0);
            break;
        case MARIO_SPAWN_AIRBORNE_STAR_COLLECT:
            set_mario_action(m, ACT_FALLING_EXIT_AIRBORNE, 0);
            break;
        case MARIO_SPAWN_AIRBORNE_DEATH:
            set_mario_action(m, ACT_UNUSED_DEATH_EXIT, 0);
            break;
        case MARIO_SPAWN_LAUNCH_STAR_COLLECT:
            set_mario_action(m, ACT_SPECIAL_EXIT_AIRBORNE, 0);
            break;
        case MARIO_SPAWN_LAUNCH_DEATH:
            set_mario_action(m, ACT_SPECIAL_DEATH_EXIT, 0);
            break;
    }

    set_mario_initial_cap_powerup(m);
}

void init_mario_after_warp(void) {
    struct ObjectWarpNode *spawnNode = area_get_warp_node(sWarpDest.nodeId);
    u32 marioSpawnType = get_mario_spawn_type(spawnNode->object);

    if (gMarioState->action != ACT_UNINITIALIZED) {
        gPlayerSpawnInfos[0].startPos[0] = (s16) spawnNode->object->oPosX;
        gPlayerSpawnInfos[0].startPos[1] = (s16) spawnNode->object->oPosY;
        gPlayerSpawnInfos[0].startPos[2] = (s16) spawnNode->object->oPosZ;

        gPlayerSpawnInfos[0].startAngle[0] = 0;
        gPlayerSpawnInfos[0].startAngle[1] = spawnNode->object->oMoveAngleYaw;
        gPlayerSpawnInfos[0].startAngle[2] = 0;

        if (marioSpawnType == MARIO_SPAWN_DOOR_WARP) {
            init_door_warp(&gPlayerSpawnInfos[0], sWarpDest.arg);
        }

        if (sWarpDest.type == WARP_TYPE_CHANGE_LEVEL || sWarpDest.type == WARP_TYPE_CHANGE_AREA) {
            gPlayerSpawnInfos[0].areaIndex = sWarpDest.areaIdx;
            load_mario_area();
        }

        init_mario();
        set_mario_initial_action(gMarioState, marioSpawnType, sWarpDest.arg);

        gMarioState->interactObj = spawnNode->object;
        gMarioState->usedObj = spawnNode->object;
    }

    reset_camera(gCurrentArea->camera);
    sWarpDest.type = WARP_TYPE_NOT_WARPING;
    sDelayedWarpOp = WARP_OP_NONE;

    switch (marioSpawnType) {
        case MARIO_SPAWN_UNKNOWN_03:
            play_transition(WARP_TRANSITION_FADE_FROM_STAR, 0x10, 0x00, 0x00, 0x00);
            break;
        case MARIO_SPAWN_DOOR_WARP:
            play_transition(WARP_TRANSITION_FADE_FROM_CIRCLE, 0x10, 0x00, 0x00, 0x00);
            break;
        case MARIO_SPAWN_TELEPORT:
            play_transition(WARP_TRANSITION_FADE_FROM_COLOR, 0x14, 0xFF, 0xFF, 0xFF);
            break;
        case MARIO_SPAWN_SPIN_AIRBORNE:
            play_transition(WARP_TRANSITION_FADE_FROM_COLOR, 0x1A, 0xFF, 0xFF, 0xFF);
            break;
        case MARIO_SPAWN_SPIN_AIRBORNE_CIRCLE:
            play_transition(WARP_TRANSITION_FADE_FROM_CIRCLE, 0x10, 0x00, 0x00, 0x00);
            break;
        case MARIO_SPAWN_UNKNOWN_27:
            play_transition(WARP_TRANSITION_FADE_FROM_COLOR, 0x10, 0x00, 0x00, 0x00);
            break;
        default:
            play_transition(WARP_TRANSITION_FADE_FROM_STAR, 0x10, 0x00, 0x00, 0x00);
            break;
    }

    if (gCurrDemoInput == NULL) {
        set_background_music(gCurrentArea->musicParam, gCurrentArea->musicParam2, 0);

        if (gMarioState->flags & MARIO_METAL_CAP) {
            play_cap_music(SEQUENCE_ARGS(4, SEQ_EVENT_METAL_CAP));
        }

        if (gMarioState->flags & (MARIO_VANISH_CAP | MARIO_WING_CAP)) {
            play_cap_music(SEQUENCE_ARGS(4, SEQ_EVENT_POWERUP));
        }

#if BUGFIX_KOOPA_RACE_MUSIC
        if (gCurrLevelNum == LEVEL_BOB
            && get_current_background_music() != SEQUENCE_ARGS(4, SEQ_LEVEL_SLIDE) && sTimerRunning) {
            play_music(SEQ_PLAYER_LEVEL, SEQUENCE_ARGS(4, SEQ_LEVEL_SLIDE), 0);
        }
#endif

        if (sWarpDest.levelNum == LEVEL_CASTLE && sWarpDest.areaIdx == 1
#ifndef VERSION_JP
            && (sWarpDest.nodeId == 31 || sWarpDest.nodeId == 32)
#else
            && sWarpDest.nodeId == 31
#endif
        ) {
            play_sound(SOUND_MENU_MARIO_CASTLE_WARP, gGlobalSoundSource);
        }

#ifndef VERSION_JP
        if (sWarpDest.levelNum == LEVEL_CASTLE_GROUNDS && sWarpDest.areaIdx == 1
            && (sWarpDest.nodeId == 7 || sWarpDest.nodeId == 10 || sWarpDest.nodeId == 20
                || sWarpDest.nodeId == 30)) {
            play_sound(SOUND_MENU_MARIO_CASTLE_WARP, gGlobalSoundSource);
        }
#endif
    }
}

// used for warps inside one level
void warp_area(void) {
    if (sWarpDest.type != WARP_TYPE_NOT_WARPING) {
        if (sWarpDest.type == WARP_TYPE_CHANGE_AREA) {
            level_control_timer(TIMER_CONTROL_HIDE);
            unload_mario_area();
            load_area(sWarpDest.areaIdx);
        }

        init_mario_after_warp();
    }
}

// used for warps between levels
void warp_level(void) {
    gCurrLevelNum = sWarpDest.levelNum;

    level_control_timer(TIMER_CONTROL_HIDE);

    load_area(sWarpDest.areaIdx);
    init_mario_after_warp();
}

void warp_credits(void) {
    s32 marioAction;

    switch (sWarpDest.nodeId) {
        case WARP_NODE_CREDITS_START:
            marioAction = ACT_END_PEACH_CUTSCENE;
            break;

        case WARP_NODE_CREDITS_NEXT:
            marioAction = ACT_CREDITS_CUTSCENE;
            break;

        case WARP_NODE_CREDITS_END:
            marioAction = ACT_END_WAVING_CUTSCENE;
            break;
    }

    gCurrLevelNum = sWarpDest.levelNum;

    load_area(sWarpDest.areaIdx);

    vec3s_set(gPlayerSpawnInfos[0].startPos, gCurrCreditsEntry->marioPos[0],
              gCurrCreditsEntry->marioPos[1], gCurrCreditsEntry->marioPos[2]);

    vec3s_set(gPlayerSpawnInfos[0].startAngle, 0, 0x100 * gCurrCreditsEntry->marioAngle, 0);

    gPlayerSpawnInfos[0].areaIndex = sWarpDest.areaIdx;

    load_mario_area();
    init_mario();

    set_mario_action(gMarioState, marioAction, 0);

    reset_camera(gCurrentArea->camera);

    sWarpDest.type = WARP_TYPE_NOT_WARPING;
    sDelayedWarpOp = WARP_OP_NONE;

    play_transition(WARP_TRANSITION_FADE_FROM_COLOR, 0x14, 0x00, 0x00, 0x00);

    if (gCurrCreditsEntry == NULL || gCurrCreditsEntry == sCreditsSequence) {
        set_background_music(gCurrentArea->musicParam, gCurrentArea->musicParam2, 0);
    }
}

void check_instant_warp(void) {
    s16 cameraAngle;
    struct Surface *floor;

    if (gCurrLevelNum == LEVEL_CASTLE
        && save_file_get_total_star_count(gCurrSaveFileNum - 1, COURSE_MIN - 1, COURSE_MAX - 1) >= 70) {
        return;
    }

    if ((floor = gMarioState->floor) != NULL) {
        s32 index = floor->type - SURFACE_INSTANT_WARP_1B;
        if (index >= INSTANT_WARP_INDEX_START && index < INSTANT_WARP_INDEX_STOP
            && gCurrentArea->instantWarps != NULL) {
            struct InstantWarp *warp = &gCurrentArea->instantWarps[index];

            if (warp->id != 0) {
                gMarioState->pos[0] += warp->displacement[0];
                gMarioState->pos[1] += warp->displacement[1];
                gMarioState->pos[2] += warp->displacement[2];

                gMarioState->marioObj->oPosX = gMarioState->pos[0];
                gMarioState->marioObj->oPosY = gMarioState->pos[1];
                gMarioState->marioObj->oPosZ = gMarioState->pos[2];

                cameraAngle = gMarioState->area->camera->yaw;

                change_area(warp->area);
                gMarioState->area = gCurrentArea;

                warp_camera(warp->displacement[0], warp->displacement[1], warp->displacement[2]);

                gMarioState->area->camera->yaw = cameraAngle;
            }
        }
    }
}

s16 music_changed_through_warp(s16 arg) {
    struct ObjectWarpNode *warpNode = area_get_warp_node(arg);
    s16 levelNum = warpNode->node.destLevel & 0x7F;

#if BUGFIX_KOOPA_RACE_MUSIC

    s16 destArea = warpNode->node.destArea;
    s16 val4 = TRUE;
    s16 sp2C;

    if (levelNum == LEVEL_BOB && levelNum == gCurrLevelNum && destArea == gCurrAreaIndex) {
        sp2C = get_current_background_music();
        if (sp2C == SEQUENCE_ARGS(4, SEQ_EVENT_POWERUP | SEQ_VARIATION)
            || sp2C == SEQUENCE_ARGS(4, SEQ_EVENT_POWERUP)) {
            val4 = FALSE;
        }
    } else {
        u16 val8 = gAreas[destArea].musicParam;
        u16 val6 = gAreas[destArea].musicParam2;

        val4 = levelNum == gCurrLevelNum && val8 == gCurrentArea->musicParam
               && val6 == gCurrentArea->musicParam2;

        if (get_current_background_music() != val6) {
            val4 = FALSE;
        }
    }
    return val4;

#else

    u16 val8 = gAreas[warpNode->node.destArea].musicParam;
    u16 val6 = gAreas[warpNode->node.destArea].musicParam2;

    s16 val4 = levelNum == gCurrLevelNum && val8 == gCurrentArea->musicParam
               && val6 == gCurrentArea->musicParam2;

    if (get_current_background_music() != val6) {
        val4 = FALSE;
    }
    return val4;

#endif
}

/**
 * Set the current warp type and destination level/area/node.
 */
void initiate_warp(s16 destLevel, s16 destArea, s16 destWarpNode, s32 arg3) {
    if (destWarpNode >= WARP_NODE_CREDITS_MIN) {
        sWarpDest.type = WARP_TYPE_CHANGE_LEVEL;
    } else if (destLevel != gCurrLevelNum) {
        sWarpDest.type = WARP_TYPE_CHANGE_LEVEL;
    } else if (destArea != gCurrentArea->index) {
        sWarpDest.type = WARP_TYPE_CHANGE_AREA;
    } else {
        sWarpDest.type = WARP_TYPE_SAME_AREA;
    }

    sWarpDest.levelNum = destLevel;
    sWarpDest.areaIdx = destArea;
    sWarpDest.nodeId = destWarpNode;
    sWarpDest.arg = arg3;
}

// From Surface 0xD3 to 0xFC
#define PAINTING_WARP_INDEX_START 0x00 // Value greater than or equal to Surface 0xD3
#define PAINTING_WARP_INDEX_FA 0x2A    // THI Huge Painting index left
#define PAINTING_WARP_INDEX_END 0x2D   // Value less than Surface 0xFD

/**
 * Check if Mario is above and close to a painting warp floor, and return the
 * corresponding warp node.
 */
struct WarpNode *get_painting_warp_node(void) {
    struct WarpNode *warpNode = NULL;
    s32 paintingIndex = gMarioState->floor->type - SURFACE_PAINTING_WARP_D3;

    if (paintingIndex >= PAINTING_WARP_INDEX_START && paintingIndex < PAINTING_WARP_INDEX_END) {
        if (paintingIndex < PAINTING_WARP_INDEX_FA
            || gMarioState->pos[1] - gMarioState->floorHeight < 80.0f) {
            warpNode = &gCurrentArea->paintingWarpNodes[paintingIndex];
        }
    }

    return warpNode;
}

/**
 * Check is Mario has entered a painting, and if so, initiate a warp.
 */
void initiate_painting_warp(void) {
    if (gCurrentArea->paintingWarpNodes != NULL && gMarioState->floor != NULL) {
        struct WarpNode warpNode;
        struct WarpNode *pWarpNode = get_painting_warp_node();

        if (pWarpNode != NULL) {
            if (gMarioState->action & ACT_FLAG_INTANGIBLE) {
                play_painting_eject_sound();
            } else if (pWarpNode->id != 0) {
                warpNode = *pWarpNode;

                if (!(warpNode.destLevel & 0x80)) {
                    sWarpCheckpointActive = check_warp_checkpoint(&warpNode);
                }

                initiate_warp(warpNode.destLevel & 0x7F, warpNode.destArea, warpNode.destNode, 0);
                check_if_should_set_warp_checkpoint(&warpNode);

                play_transition_after_delay(WARP_TRANSITION_FADE_INTO_COLOR, 30, 255, 255, 255, 45);
                level_set_transition(74, basic_update);

                set_mario_action(gMarioState, ACT_DISAPPEARED, 0);

                gMarioState->marioObj->header.gfx.node.flags &= ~GRAPH_RENDER_ACTIVE;

                play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
                fadeout_music(398);
#if ENABLE_RUMBLE
                queue_rumble_data(80, 70);
                func_sh_8024C89C(1);
#endif
            }
        }
    }
}

/**
 * If there is not already a delayed warp, schedule one. The source node is
 * based on the warp operation and sometimes Mario's used object.
 * Return the time left until the delayed warp is initiated.
 */
s16 level_trigger_warp(struct MarioState *m, s32 warpOp) {
    s32 val04 = TRUE;

    if (sDelayedWarpOp == WARP_OP_NONE) {
        m->invincTimer = -1;
        sDelayedWarpArg = 0;
        sDelayedWarpOp = warpOp;

        switch (warpOp) {
            case WARP_OP_DEMO_NEXT:
            case WARP_OP_DEMO_END: sDelayedWarpTimer = 20; // Must be one line to match on -O2
                sSourceWarpNodeId = WARP_NODE_F0;
                gSavedCourseNum = COURSE_NONE;
                val04 = FALSE;
                play_transition(WARP_TRANSITION_FADE_INTO_STAR, 0x14, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_CREDITS_END:
                sDelayedWarpTimer = 60;
                sSourceWarpNodeId = WARP_NODE_F0;
                val04 = FALSE;
                gSavedCourseNum = COURSE_NONE;
                play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x3C, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_STAR_EXIT:
                sDelayedWarpTimer = 32;
                sSourceWarpNodeId = WARP_NODE_F0;
                gSavedCourseNum = COURSE_NONE;
                play_transition(WARP_TRANSITION_FADE_INTO_MARIO, 0x20, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_DEATH:
                if (m->numLives == 0) {
                    sDelayedWarpOp = WARP_OP_GAME_OVER;
                }
                sDelayedWarpTimer = 48;
                sSourceWarpNodeId = WARP_NODE_DEATH;
                play_transition(WARP_TRANSITION_FADE_INTO_BOWSER, 0x30, 0x00, 0x00, 0x00);
                play_sound(SOUND_MENU_BOWSER_LAUGH, gGlobalSoundSource);
                break;

            case WARP_OP_WARP_FLOOR:
                sSourceWarpNodeId = WARP_NODE_WARP_FLOOR;
                if (area_get_warp_node(sSourceWarpNodeId) == NULL) {
                    if (m->numLives == 0) {
                        sDelayedWarpOp = WARP_OP_GAME_OVER;
                    } else {
                        sSourceWarpNodeId = WARP_NODE_DEATH;
                    }
                }
                sDelayedWarpTimer = 20;
                play_transition(WARP_TRANSITION_FADE_INTO_CIRCLE, 0x14, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_UNKNOWN_01: // enter totwc
                sDelayedWarpTimer = 30;
                sSourceWarpNodeId = WARP_NODE_F2;
                play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x1E, 0xFF, 0xFF, 0xFF);
#ifndef VERSION_JP
                play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
#endif
                break;

            case WARP_OP_UNKNOWN_02: // bbh enter
                sDelayedWarpTimer = 30;
                sSourceWarpNodeId = (m->usedObj->oBehParams & 0x00FF0000) >> 16;
                play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x1E, 0xFF, 0xFF, 0xFF);
                break;

            case WARP_OP_TELEPORT:
                sDelayedWarpTimer = 20;
                sSourceWarpNodeId = (m->usedObj->oBehParams & 0x00FF0000) >> 16;
                val04 = !music_changed_through_warp(sSourceWarpNodeId);
                play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x14, 0xFF, 0xFF, 0xFF);
                break;

            case WARP_OP_WARP_DOOR:
                sDelayedWarpTimer = 20;
                sDelayedWarpArg = m->actionArg;
                sSourceWarpNodeId = (m->usedObj->oBehParams & 0x00FF0000) >> 16;
                val04 = !music_changed_through_warp(sSourceWarpNodeId);
                play_transition(WARP_TRANSITION_FADE_INTO_CIRCLE, 0x14, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_WARP_OBJECT:
                sDelayedWarpTimer = 20;
                sSourceWarpNodeId = (m->usedObj->oBehParams & 0x00FF0000) >> 16;
                val04 = !music_changed_through_warp(sSourceWarpNodeId);
                play_transition(WARP_TRANSITION_FADE_INTO_STAR, 0x14, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_CREDITS_START:
                sDelayedWarpTimer = 30;
                play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x1E, 0x00, 0x00, 0x00);
                break;

            case WARP_OP_CREDITS_NEXT:
                if (gCurrCreditsEntry == &sCreditsSequence[0]) {
                    sDelayedWarpTimer = 60;
                    play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x3C, 0x00, 0x00, 0x00);
                } else {
                    sDelayedWarpTimer = 20;
                    play_transition(WARP_TRANSITION_FADE_INTO_COLOR, 0x14, 0x00, 0x00, 0x00);
                }
                val04 = FALSE;
                break;
        }

        if (val04 && gCurrDemoInput == NULL) {
            fadeout_music((3 * sDelayedWarpTimer / 2) * 8 - 2);
        }
    }

    return sDelayedWarpTimer;
}

/**
 * If a delayed warp is ready, initiate it.
 */
void initiate_delayed_warp(void) {
    struct ObjectWarpNode *warpNode;
    s32 destWarpNode;

    if (sDelayedWarpOp != WARP_OP_NONE && --sDelayedWarpTimer == 0) {
        reset_dialog_render_state();

        if (gDebugLevelSelect && (sDelayedWarpOp & WARP_OP_TRIGGERS_LEVEL_SELECT)) {
            warp_special(-9);
        } else if (gCurrDemoInput != NULL) {
            if (sDelayedWarpOp == WARP_OP_DEMO_END) {
                warp_special(-8);
            } else {
                warp_special(-2);
            }
        } else {
            switch (sDelayedWarpOp) {
                case WARP_OP_GAME_OVER:
                    save_file_reload();
                    warp_special(-3);
                    break;

                case WARP_OP_CREDITS_END:
                    warp_special(-1);
                    sound_banks_enable(SEQ_PLAYER_SFX,
                                       SOUND_BANKS_ALL & ~SOUND_BANKS_DISABLED_AFTER_CREDITS);
                    break;

                case WARP_OP_DEMO_NEXT:
                    warp_special(-2);
                    break;

                case WARP_OP_CREDITS_START:
                    gCurrCreditsEntry = &sCreditsSequence[0];
                    initiate_warp(gCurrCreditsEntry->levelNum, gCurrCreditsEntry->areaIndex,
                                  WARP_NODE_CREDITS_START, 0);
                    break;

                case WARP_OP_CREDITS_NEXT:
                    sound_banks_disable(SEQ_PLAYER_SFX, SOUND_BANKS_ALL);

                    gCurrCreditsEntry++;
                    gCurrActNum = gCurrCreditsEntry->unk02 & 0x07;
                    if ((gCurrCreditsEntry + 1)->levelNum == LEVEL_NONE) {
                        destWarpNode = WARP_NODE_CREDITS_END;
                    } else {
                        destWarpNode = WARP_NODE_CREDITS_NEXT;
                    }

                    initiate_warp(gCurrCreditsEntry->levelNum, gCurrCreditsEntry->areaIndex,
                                  destWarpNode, 0);
                    break;

                default:
                    warpNode = area_get_warp_node(sSourceWarpNodeId);

                    initiate_warp(warpNode->node.destLevel & 0x7F, warpNode->node.destArea,
                                  warpNode->node.destNode, sDelayedWarpArg);

                    check_if_should_set_warp_checkpoint(&warpNode->node);
                    if (sWarpDest.type != WARP_TYPE_CHANGE_LEVEL) {
                        level_set_transition(2, NULL);
                    }
                    break;
            }
        }
    }
}

void update_hud_values(void) {
    if (gCurrCreditsEntry == NULL) {
        s16 numHealthWedges = gMarioState->health > 0 ? gMarioState->health >> 8 : 0;

        if (gCurrCourseNum >= COURSE_MIN) {
            gHudDisplay.flags |= HUD_DISPLAY_FLAG_COIN_COUNT;
        } else {
            gHudDisplay.flags &= ~HUD_DISPLAY_FLAG_COIN_COUNT;
        }

        if (gHudDisplay.coins < gMarioState->numCoins) {
            if (gGlobalTimer & 1) {
                u32 coinSound;
                if (gMarioState->action & (ACT_FLAG_SWIMMING | ACT_FLAG_METAL_WATER)) {
                    coinSound = SOUND_GENERAL_COIN_WATER;
                } else {
                    coinSound = SOUND_GENERAL_COIN;
                }

                gHudDisplay.coins++;
                play_sound(coinSound, gMarioState->marioObj->header.gfx.cameraToObject);
            }
        }

        if (gMarioState->numLives > 100) {
            gMarioState->numLives = 100;
        }

#if BUGFIX_MAX_LIVES
        if (gMarioState->numCoins > 999) {
            gMarioState->numCoins = 999;
        }

        if (gHudDisplay.coins > 999) {
            gHudDisplay.coins = 999;
        }
#else
        if (gMarioState->numCoins > 999) {
            gMarioState->numLives = (s8) 999; //! Wrong variable
        }
#endif

        gHudDisplay.stars = gMarioState->numStars;
        gHudDisplay.lives = gMarioState->numLives;
        gHudDisplay.keys = gMarioState->numKeys;

        if (numHealthWedges > gHudDisplay.wedges) {
            play_sound(SOUND_MENU_POWER_METER, gGlobalSoundSource);
        }
        gHudDisplay.wedges = numHealthWedges;

        if (gMarioState->hurtCounter > 0) {
            gHudDisplay.flags |= HUD_DISPLAY_FLAG_EMPHASIZE_POWER;
        } else {
            gHudDisplay.flags &= ~HUD_DISPLAY_FLAG_EMPHASIZE_POWER;
        }
    }
}

/**
 * Update objects, HUD, and camera. This update function excludes things like
 * endless staircase, warps, pausing, etc. This is used when entering a painting,
 * presumably to allow painting and camera updating while avoiding triggering the
 * warp twice.
 */
void basic_update(UNUSED s16 *arg) {
    area_update_objects();
    update_hud_values();

    if (gCurrentArea != NULL) {
        update_camera(gCurrentArea->camera);
    }
}

s32 play_mode_normal(void) {
    if (gCurrDemoInput != NULL) {
        print_intro_text();
        if (gPlayer1Controller->buttonPressed & END_DEMO) {
            level_trigger_warp(gMarioState,
                               gCurrLevelNum == LEVEL_PSS ? WARP_OP_DEMO_END : WARP_OP_DEMO_NEXT);
        } else if (!gWarpTransition.isActive && sDelayedWarpOp == WARP_OP_NONE
                   && (gPlayer1Controller->buttonPressed & START_BUTTON)) {
            level_trigger_warp(gMarioState, WARP_OP_DEMO_NEXT);
        }
    }

    warp_area();
    check_instant_warp();

    if (sTimerRunning && gHudDisplay.timer < 17999) {
        gHudDisplay.timer++;
    }

    area_update_objects();
    update_hud_values();

    if (gCurrentArea != NULL) {
        update_camera(gCurrentArea->camera);
    }

    initiate_painting_warp();
    initiate_delayed_warp();

    // If either initiate_painting_warp or initiate_delayed_warp initiated a
    // warp, change play mode accordingly.
    if (sCurrPlayMode == PLAY_MODE_NORMAL) {
        if (sWarpDest.type == WARP_TYPE_CHANGE_LEVEL) {
            set_play_mode(PLAY_MODE_CHANGE_LEVEL);
        } else if (sTransitionTimer != 0) {
            set_play_mode(PLAY_MODE_CHANGE_AREA);
        } else if (pressed_pause()) {
            lower_background_noise(1);
#if ENABLE_RUMBLE
            cancel_rumble();
#endif
            gCameraMovementFlags |= CAM_MOVE_PAUSE_SCREEN;
            set_play_mode(PLAY_MODE_PAUSED);
        }
    }

    return 0;
}

s32 play_mode_paused(void) {
    if (gMenuOptSelectIndex == MENU_OPT_NONE) {
        set_menu_mode(MENU_MODE_RENDER_PAUSE_SCREEN);
    } else if (gMenuOptSelectIndex == MENU_OPT_DEFAULT) {
        raise_background_noise(1);
        gCameraMovementFlags &= ~CAM_MOVE_PAUSE_SCREEN;
        set_play_mode(PLAY_MODE_NORMAL);
    } else { // MENU_OPT_EXIT_COURSE
        if (gDebugLevelSelect) {
            fade_into_special_warp(-9, 1);
        } else {
            initiate_warp(LEVEL_CASTLE, 1, 0x1F, 0);
            fade_into_special_warp(0, 0);
            gSavedCourseNum = COURSE_NONE;
        }

        gCameraMovementFlags &= ~CAM_MOVE_PAUSE_SCREEN;
    }

    return 0;
}

/**
 * Debug mode that lets you frame advance by pressing D-pad down. Unfortunately
 * it uses the pause camera, making it basically unusable in most levels.
 */
s32 play_mode_frame_advance(void) {
    if (gPlayer1Controller->buttonPressed & D_JPAD) {
        gCameraMovementFlags &= ~CAM_MOVE_PAUSE_SCREEN;
        play_mode_normal();
    } else if (gPlayer1Controller->buttonPressed & START_BUTTON) {
        gCameraMovementFlags &= ~CAM_MOVE_PAUSE_SCREEN;
        raise_background_noise(1);
        set_play_mode(PLAY_MODE_NORMAL);
    } else {
        gCameraMovementFlags |= CAM_MOVE_PAUSE_SCREEN;
    }

    return 0;
}

/**
 * Set the transition, which is a period of time after the warp is initiated
 * but before it actually occurs. If updateFunction is not NULL, it will be
 * called each frame during the transition.
 */
void level_set_transition(s16 length, void (*updateFunction)(s16 *)) {
    sTransitionTimer = length;
    sTransitionUpdate = updateFunction;
}

/**
 * Play the transition and then return to normal play mode.
 */
s32 play_mode_change_area(void) {
    //! This maybe was supposed to be sTransitionTimer == -1? sTransitionUpdate
    // is never set to -1.
    if (sTransitionUpdate == (void (*)(s16 *)) -1) {
        update_camera(gCurrentArea->camera);
    } else if (sTransitionUpdate != NULL) {
        sTransitionUpdate(&sTransitionTimer);
    }

    if (sTransitionTimer > 0) {
        sTransitionTimer--;
    }

    if (sTransitionTimer == 0) {
        sTransitionUpdate = NULL;
        set_play_mode(PLAY_MODE_NORMAL);
    }

    return 0;
}

/**
 * Play the transition and then return to normal play mode.
 */
s32 play_mode_change_level(void) {
    if (sTransitionUpdate != NULL) {
        sTransitionUpdate(&sTransitionTimer);
    }

    if (--sTransitionTimer == -1) {
        gHudDisplay.flags = HUD_DISPLAY_NONE;
        sTransitionTimer = 0;
        sTransitionUpdate = NULL;

        if (sWarpDest.type != WARP_TYPE_NOT_WARPING) {
            return sWarpDest.levelNum;
        } else {
            return D_80339EE0;
        }
    }

    return 0;
}

/**
 * Unused play mode. Doesn't call transition update and doesn't reset transition at the end.
 */
UNUSED static s32 play_mode_unused(void) {
    if (--sTransitionTimer == -1) {
        gHudDisplay.flags = HUD_DISPLAY_NONE;

        if (sWarpDest.type != WARP_TYPE_NOT_WARPING) {
            return sWarpDest.levelNum;
        } else {
            return D_80339EE0;
        }
    }

    return 0;
}

s32 update_level(void) {
    s32 changeLevel;

    switch (sCurrPlayMode) {
        case PLAY_MODE_NORMAL:
            changeLevel = play_mode_normal();
            break;
        case PLAY_MODE_PAUSED:
            changeLevel = play_mode_paused();
            break;
        case PLAY_MODE_CHANGE_AREA:
            changeLevel = play_mode_change_area();
            break;
        case PLAY_MODE_CHANGE_LEVEL:
            changeLevel = play_mode_change_level();
            break;
        case PLAY_MODE_FRAME_ADVANCE:
            changeLevel = play_mode_frame_advance();
            break;
    }

    if (changeLevel) {
        reset_volume();
        enable_background_sound();
    }

    return changeLevel;
}

s32 init_level(void) {
    s32 val4 = FALSE;

    set_play_mode(PLAY_MODE_NORMAL);

    sDelayedWarpOp = WARP_OP_NONE;
    sTransitionTimer = 0;
    D_80339EE0 = 0;

    if (gCurrCreditsEntry == NULL) {
        gHudDisplay.flags = HUD_DISPLAY_DEFAULT;
    } else {
        gHudDisplay.flags = HUD_DISPLAY_NONE;
    }

    sTimerRunning = FALSE;

    if (sWarpDest.type != WARP_TYPE_NOT_WARPING) {
        if (sWarpDest.nodeId >= WARP_NODE_CREDITS_MIN) {
            warp_credits();
        } else {
            warp_level();
        }
    } else {
        if (gPlayerSpawnInfos[0].areaIndex >= 0) {
            load_mario_area();
            init_mario();
        }

        if (gCurrentArea != NULL) {
            reset_camera(gCurrentArea->camera);

            if (gCurrDemoInput != NULL) {
                set_mario_action(gMarioState, ACT_IDLE, 0);
            } else if (!gDebugLevelSelect) {
                if (gMarioState->action != ACT_UNINITIALIZED) {
                    if (save_file_exists(gCurrSaveFileNum - 1)) {
                        set_mario_action(gMarioState, ACT_IDLE, 0);
                    } else {
                        set_mario_action(gMarioState, ACT_INTRO_CUTSCENE, 0);
                        val4 = TRUE;
                    }
                }
            }
        }

        if (val4) {
            play_transition(WARP_TRANSITION_FADE_FROM_COLOR, 0x5A, 0xFF, 0xFF, 0xFF);
        } else {
            play_transition(WARP_TRANSITION_FADE_FROM_STAR, 0x10, 0xFF, 0xFF, 0xFF);
        }

        if (gCurrDemoInput == NULL) {
            set_background_music(gCurrentArea->musicParam, gCurrentArea->musicParam2, 0);
        }
    }
#if ENABLE_RUMBLE
    if (gCurrDemoInput == NULL) {
        cancel_rumble();
    }
#endif

    if (gMarioState->action == ACT_INTRO_CUTSCENE) {
        sound_banks_disable(SEQ_PLAYER_SFX, SOUND_BANKS_DISABLED_DURING_INTRO_CUTSCENE);
    }

    return 1;
}

/**
 * Initialize the current level if initOrUpdate is 0, or update the level if it is 1.
 */
s32 lvl_init_or_update(s16 initOrUpdate, UNUSED s32 unused) {
    s32 result = 0;

    switch (initOrUpdate) {
        case 0:
            result = init_level();
            break;
        case 1:
            result = update_level();
            break;
    }

    return result;
}

s32 lvl_init_from_save_file(UNUSED s16 arg0, s32 levelNum) {
#ifdef VERSION_EU
    s16 language = eu_get_language();
    switch (language) {
        case LANGUAGE_ENGLISH:
            load_segment_decompress(0x19, _translation_en_mio0SegmentRomStart,
                                    _translation_en_mio0SegmentRomEnd);
            break;
        case LANGUAGE_FRENCH:
            load_segment_decompress(0x19, _translation_fr_mio0SegmentRomStart,
                                    _translation_fr_mio0SegmentRomEnd);
            break;
        case LANGUAGE_GERMAN:
            load_segment_decompress(0x19, _translation_de_mio0SegmentRomStart,
                                    _translation_de_mio0SegmentRomEnd);
            break;
    }
#endif
    sWarpDest.type = WARP_TYPE_NOT_WARPING;
    sDelayedWarpOp = WARP_OP_NONE;
    gNeverEnteredCastle = !save_file_exists(gCurrSaveFileNum - 1);

    gCurrLevelNum = levelNum;
    gCurrCourseNum = COURSE_NONE;
    gSavedCourseNum = COURSE_NONE;
    gCurrCreditsEntry = NULL;
    gSpecialTripleJump = FALSE;

    init_mario_from_save_file();
    disable_warp_checkpoint();
    save_file_move_cap_to_default_location();
    select_mario_cam_mode();
    set_yoshi_as_not_dead();

    return levelNum;
}

s32 lvl_set_current_level(UNUSED s16 arg0, s32 levelNum) {
    s32 warpCheckpointActive = sWarpCheckpointActive;

    sWarpCheckpointActive = FALSE;
    gCurrLevelNum = levelNum;
    gCurrCourseNum = gLevelToCourseNumTable[levelNum - 1];

    if (gCurrDemoInput != NULL || gCurrCreditsEntry != NULL || gCurrCourseNum == COURSE_NONE) {
        return 0;
    }

    if (gCurrLevelNum != LEVEL_BOWSER_1 && gCurrLevelNum != LEVEL_BOWSER_2
        && gCurrLevelNum != LEVEL_BOWSER_3) {
        gMarioState->numCoins = 0;
        gHudDisplay.coins = 0;
        gCurrCourseStarFlags =
            save_file_get_star_flags(gCurrSaveFileNum - 1, COURSE_NUM_TO_INDEX(gCurrCourseNum));
    }

    if (gSavedCourseNum != gCurrCourseNum) {
        gSavedCourseNum = gCurrCourseNum;
        nop_change_course();
        disable_warp_checkpoint();
    }

    if (gCurrCourseNum > COURSE_STAGES_MAX || warpCheckpointActive) {
        return 0;
    }

    if (gDebugLevelSelect && !gShowProfiler) {
        return 0;
    }

    return 1;
}

/**
 * Play the "thank you so much for to playing my game" sound.
 */
s32 lvl_play_the_end_screen_sound(UNUSED s16 arg0, UNUSED s32 arg1) {
    play_sound(SOUND_MENU_THANK_YOU_PLAYING_MY_GAME, gGlobalSoundSource);
    return 1;
}
