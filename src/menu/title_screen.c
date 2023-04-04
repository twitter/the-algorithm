#include <PR/ultratypes.h>

#include "audio/external.h"
#include "engine/math_util.h"
#include "game/area.h"
#include "game/game_init.h"
#include "game/level_update.h"
#include "game/main.h"
#include "game/memory.h"
#include "game/print.h"
#include "game/save_file.h"
#include "game/sound_init.h"
#include "game/rumble_init.h"
#include "level_table.h"
#include "seq_ids.h"
#include "sm64.h"
#include "title_screen.h"

/**
 * @file title_screen.c
 * This file implements how title screen functions.
 * That includes playing demo sequences, introduction screens
 * and a level select used for testing purposes.
 */

#define STUB_LEVEL(textname, _1, _2, _3, _4, _5, _6, _7, _8) textname,
#define DEFINE_LEVEL(textname, _1, _2, _3, _4, _5, _6, _7, _8, _9, _10) textname,

static char sLevelSelectStageNames[64][16] = {
    #include "levels/level_defines.h"
};
#undef STUB_LEVEL
#undef DEFINE_LEVEL

static u16 sDemoCountdown = 0;
#ifndef VERSION_JP
static s16 sPlayMarioGreeting = TRUE;
static s16 sPlayMarioGameOver = TRUE;
#endif

#define PRESS_START_DEMO_TIMER 800

/**
 * Run the demo timer on the PRESS START screen after a number of frames.
 * This function returns the level ID from the first byte of a demo file.
 * It also returns the level ID from intro_regular (file select or level select menu)
 */
s32 run_level_id_or_demo(s32 level) {
    gCurrDemoInput = NULL;

    if (level == LEVEL_NONE) {
        if (!gPlayer1Controller->buttonDown && !gPlayer1Controller->stickMag) {
            // start the demo. 800 frames has passed while
            // player is idle on PRESS START screen.
            if ((++sDemoCountdown) == PRESS_START_DEMO_TIMER) {

                // start the Mario demo animation for the demo list.
                load_patchable_table(&gDemoInputsBuf, gDemoInputListID);

                // if the next demo sequence ID is the count limit, reset it back to
                // the first sequence.
                if (++gDemoInputListID == gDemoInputsBuf.dmaTable->count) {
                    gDemoInputListID = 0;
                }

                // add 1 (+4) to the pointer to skip the first 4 bytes
                // Use the first 4 bytes to store level ID,
                // then use the rest of the values for inputs
                gCurrDemoInput = ((struct DemoInput *) gDemoInputsBuf.bufTarget) + 1;
                level = (s8)((struct DemoInput *) gDemoInputsBuf.bufTarget)->timer;
                gCurrSaveFileNum = 1;
                gCurrActNum = 1;
            }
        } else { // activity was detected, so reset the demo countdown.
            sDemoCountdown = 0;
        }
    }
    return level;
}

/**
 * Level select intro function, updates the selected stage
 * count if an input was received. signals the stage to be started
 * or the level select to be exited if start or the quit combo is pressed.
 */
s16 intro_level_select(void) {
    s32 stageChanged = FALSE;

    // perform the ID updates per each button press.
    // runs into a loop so after a button is pressed
    // stageChanged goes back to FALSE
    if (gPlayer1Controller->buttonPressed & A_BUTTON) {
        ++gCurrLevelNum, stageChanged = TRUE;
    }
    if (gPlayer1Controller->buttonPressed & B_BUTTON) {
        --gCurrLevelNum, stageChanged = TRUE;
    }
    if (gPlayer1Controller->buttonPressed & U_JPAD) {
        --gCurrLevelNum, stageChanged = TRUE;
    }
    if (gPlayer1Controller->buttonPressed & D_JPAD) {
        ++gCurrLevelNum, stageChanged = TRUE;
    }
    if (gPlayer1Controller->buttonPressed & L_JPAD) {
        gCurrLevelNum -= 10, stageChanged = TRUE;
    }
    if (gPlayer1Controller->buttonPressed & R_JPAD) {
        gCurrLevelNum += 10, stageChanged = TRUE;
    }

    // if the stage was changed, play the sound for changing a stage.
    if (stageChanged) {
        play_sound(SOUND_GENERAL_LEVEL_SELECT_CHANGE, gGlobalSoundSource);
    }

    if (gCurrLevelNum > LEVEL_MAX) {
        gCurrLevelNum = LEVEL_MIN; // exceeded max. set to min.
    }

    if (gCurrLevelNum < LEVEL_MIN) {
        gCurrLevelNum = LEVEL_MAX; // exceeded min. set to max.
    }

    // Use file 4 and last act as a test
    gCurrSaveFileNum = 4;
    gCurrActNum = 6;

    print_text_centered(160, 80, "SELECT STAGE");
    print_text_centered(160, 30, "PRESS START BUTTON");
    print_text_fmt_int(40, 60, "%2d", gCurrLevelNum);
    print_text(80, 60, sLevelSelectStageNames[gCurrLevelNum - 1]); // print stage name

#define QUIT_LEVEL_SELECT_COMBO (Z_TRIG | START_BUTTON | L_CBUTTONS | R_CBUTTONS)

    // start being pressed signals the stage to be started. that is, unless...
    if (gPlayer1Controller->buttonPressed & START_BUTTON) {
        // ... the level select quit combo is being pressed, which uses START. If this
        // is the case, quit the menu instead.
        if (gPlayer1Controller->buttonDown == QUIT_LEVEL_SELECT_COMBO) {
            gDebugLevelSelect = FALSE;
            return -1;
        }
        play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
        return gCurrLevelNum;
    }
    return 0;
}

/**
 * Regular intro function that handles Mario's greeting voice and game start.
 */
s32 intro_regular(void) {
    s32 level = LEVEL_NONE;

#ifndef VERSION_JP
    // When the game stars, gGlobalTimer is less than 129 frames,
    // so Mario greets the player. After that, he will always say
    // "press start to play" when it goes back to the title screen
    // (using SAVE AND QUIT)
    if (sPlayMarioGreeting == TRUE) {
        if (gGlobalTimer < 129) {
            play_sound(SOUND_MARIO_HELLO, gGlobalSoundSource);
        } else {
            play_sound(SOUND_MARIO_PRESS_START_TO_PLAY, gGlobalSoundSource);
        }
        sPlayMarioGreeting = FALSE;
    }
#endif
    print_intro_text();

    if (gPlayer1Controller->buttonPressed & START_BUTTON) {
        play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
#if ENABLE_RUMBLE
        queue_rumble_data(60, 70);
        func_sh_8024C89C(1);
#endif
        // calls level ID 100 (or 101 adding level select bool value)
        // defined in level_intro_mario_head_regular JUMP_IF commands
        // 100 is File Select - 101 is Level Select
        level = 100 + gDebugLevelSelect;
#ifndef VERSION_JP
        sPlayMarioGreeting = TRUE;
#endif
    }
    return run_level_id_or_demo(level);
}

/**
 * Game over intro function that handles Mario's game over voice and game start.
 */
s32 intro_game_over(void) {
    s32 level = LEVEL_NONE;

#ifndef VERSION_JP
    if (sPlayMarioGameOver == TRUE) {
        play_sound(SOUND_MARIO_GAME_OVER, gGlobalSoundSource);
        sPlayMarioGameOver = FALSE;
    }
#endif

    print_intro_text();

    if (gPlayer1Controller->buttonPressed & START_BUTTON) {
        play_sound(SOUND_MENU_STAR_SOUND, gGlobalSoundSource);
#if ENABLE_RUMBLE
        queue_rumble_data(60, 70);
        func_sh_8024C89C(1);
#endif
        // same criteria as intro_regular
        level = 100 + gDebugLevelSelect;
#ifndef VERSION_JP
        sPlayMarioGameOver = TRUE;
#endif
    }
    return run_level_id_or_demo(level);
}

/**
 * Plays the casual "It's a me mario" when the game stars.
 */
s32 intro_play_its_a_me_mario(void) {
    set_background_music(0, SEQ_SOUND_PLAYER, 0);
    play_sound(SOUND_MENU_COIN_ITS_A_ME_MARIO, gGlobalSoundSource);
    return 1;
}

/**
 * Update intro functions to handle title screen actions.
 * Returns a level ID after their criteria is met.
 */
s32 lvl_intro_update(s16 arg, UNUSED s32 unusedArg) {
    s32 retVar;

    switch (arg) {
        case LVL_INTRO_PLAY_ITS_A_ME_MARIO:
            retVar = intro_play_its_a_me_mario();
            break;
        case LVL_INTRO_REGULAR:
            retVar = intro_regular();
            break;
        case LVL_INTRO_GAME_OVER:
            retVar = intro_game_over();
            break;
        case LVL_INTRO_LEVEL_SELECT:
            retVar = intro_level_select();
            break;
    }
    return retVar;
}
