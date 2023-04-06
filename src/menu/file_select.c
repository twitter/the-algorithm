#include <ultra64.h>

#include "sm64.h"
#include "audio/external.h"
#include "game/game_init.h"
#include "game/ingame_menu.h"
#include "game/object_helpers.h"
#include "game/area.h"
#include "game/save_file.h"
#include "game/spawn_object.h"
#include "game/object_list_processor.h"
#include "game/segment2.h"
#include "game/segment7.h"
#include "game/print.h"
#include "engine/behavior_script.h"
#include "engine/graph_node.h"
#include "engine/math_util.h"
#include "behavior_data.h"
#include "text_strings.h"
#include "file_select.h"
#include "dialog_ids.h"

#include "eu_translation.h"
#ifdef VERSION_EU
#undef LANGUAGE_FUNCTION
#define LANGUAGE_FUNCTION sLanguageMode
#endif

/**
 * @file file_select.c
 * This file implements how the file select and it's menus render and function.
 * That includes button IDs rendered as object models, strings, hand cursor,
 * special menu messages and phases, button states and button clicked checks.
 */

#ifdef VERSION_US
// The current sound mode is automatically centered on US due to
// the large length difference between options.
// sSoundTextY unused (EU supports its existence).
static s16 sSoundTextX;
static s16 sSoundTextY;
#endif

//! @Bug (UB Array Access) For PAL, more buttons were added than the array was extended.
//! This causes no currently known issues on console (as the other variables are not changed
//! while this is used) but can cause issues with other compilers.
#ifdef VERSION_EU
    #ifdef AVOID_UB
        #define NUM_BUTTONS 36
    #else
        #define NUM_BUTTONS 34
    #endif
#else
#define NUM_BUTTONS 32
#endif

// Amount of main menu buttons defined in the code called by spawn_object_rel_with_rot.
// See file_select.h for the names in MenuButtonTypes.
static struct Object *sMainMenuButtons[NUM_BUTTONS];

#ifdef VERSION_EU
// The current sound mode is automatically centered on US due to
// the large length difference between options.
// sSoundTextY is unused
static s16 sSoundTextX;
static s16 sSoundTextY;
#endif

// Used to defined yes/no fade colors after a file is selected in the erase menu.
// sYesNoColor[0]: YES | sYesNoColor[1]: NO
static u8 sYesNoColor[2];

// Unused variable that is written to define centered X value for some strings.
#ifdef VERSION_EU
static s16 sCenteredX;
#endif

// The button that is selected when it is clicked.
static s8 sSelectedButtonID = MENU_BUTTON_NONE;

// Whether we are on the main menu or one of the submenus.
static s8 sCurrentMenuLevel = MENU_LAYER_MAIN;

// Used for text opacifying. If it is below 250, it is constantly incremented.
static u8 sTextBaseAlpha = 0;

// 2D position of the cursor on the screen.
// sCursorPos[0]: X | sCursorPos[1]: Y
static f32 sCursorPos[] = {0, 0};

// Determines which graphic to use for the cursor.
static s16 sCursorClickingTimer = 0;

// Equal to sCursorPos if the cursor gets clicked, {-10000, -10000} otherwise.
static s16 sClickPos[] = {-10000, -10000};

// Used for determining which file has been selected during copying and erasing.
static s8 sSelectedFileIndex = -1;

// Whether to fade out text or not.
static s8 sFadeOutText = FALSE;

// The message currently being displayed at the top of a menu.
static s8 sStatusMessageID = 0;

// Used for text fading. The alpha value of text is calculated as
// sTextBaseAlpha - sTextFadeAlpha.
static u8 sTextFadeAlpha = 0;

// File select timer that keeps counting until it reaches 1000.
// Used to prevent buttons from being clickable as soon as a menu loads.
// Gets reset when you click an empty save, existing saves in copy and erase menus
// and when you click yes/no in the erase confirmation prompt.
static s16 sMainMenuTimer = 0;

// Sound mode menu buttonID, has different values compared to gSoundMode in audio.
// 0: gSoundMode = 0 (Stereo) | 1: gSoundMode = 3 (Mono) | 2: gSoundMode = 1 (Headset)
static s8 sSoundMode = 0;

// Active language for PAL arrays, values defined similar to sSoundMode
// 0: English | 1: French | 2: German
#ifdef VERSION_EU
static s8 sLanguageMode = LANGUAGE_ENGLISH;
#endif

// Tracks which button will be pressed in the erase confirmation prompt (yes/no).
static s8 sEraseYesNoHoverState = MENU_ERASE_HOVER_NONE;

// Used for the copy menu, defines if the game as all 4 save slots with data.
// if TRUE, it doesn't allow copying more files.
static s8 sAllFilesExist = FALSE;

// Defines the value of the save slot selected in the menu.
// Mario A: 1 | Mario B: 2 | Mario C: 3 | Mario D: 4
static s8 sSelectedFileNum = 0;

// Which coin score mode to use when scoring files. 0 for local
// coin high score, 1 for high score across all files.
static s8 sScoreFileCoinScoreMode = 0;

// In PAL, if no save file exists, open the language menu so the user can find it.
#ifdef VERSION_EU
static s8 sOpenLangSettings = FALSE;
#endif

#ifndef VERSION_EU
static unsigned char textReturn[] = { TEXT_RETURN };
#else
static unsigned char textReturn[][8] = {{ TEXT_RETURN }, { TEXT_RETURN_FR }, { TEXT_RETURN_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textViewScore[] = { TEXT_CHECK_SCORE };
#else
static unsigned char textViewScore[][12] = {{ TEXT_CHECK_SCORE }, {TEXT_CHECK_SCORE_FR}, {TEXT_CHECK_SCORE_DE}};
#endif

#ifndef VERSION_EU
static unsigned char textCopyFileButton[] = { TEXT_COPY_FILE_BUTTON };
#else
static unsigned char textCopyFileButton[][15] = {{ TEXT_COPY_FILE }, { TEXT_COPY_FILE_FR }, { TEXT_COPY_FILE_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textEraseFileButton[] = { TEXT_ERASE_FILE_BUTTON };
#else
static unsigned char textEraseFileButton[][16] = { {TEXT_ERASE_FILE}, {TEXT_ERASE_FILE_FR}, {TEXT_ERASE_FILE_DE} };
#endif

#ifndef VERSION_EU
static unsigned char textSoundModes[][8] = { { TEXT_STEREO }, { TEXT_MONO }, { TEXT_HEADSET } };
#endif

static unsigned char textMarioA[] = { TEXT_FILE_MARIO_A };
static unsigned char textMarioB[] = { TEXT_FILE_MARIO_B };
static unsigned char textMarioC[] = { TEXT_FILE_MARIO_C };
static unsigned char textMarioD[] = { TEXT_FILE_MARIO_D };

#ifndef VERSION_EU
static unsigned char textNew[] = { TEXT_NEW };
static unsigned char starIcon[] = { GLYPH_STAR, GLYPH_SPACE };
static unsigned char xIcon[] = { GLYPH_MULTIPLY, GLYPH_SPACE };
#endif

#ifndef VERSION_EU
static unsigned char textSelectFile[] = { TEXT_SELECT_FILE };
#else
static unsigned char textSelectFile[][17] = {{ TEXT_SELECT_FILE }, { TEXT_SELECT_FILE_FR }, { TEXT_SELECT_FILE_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textScore[] = { TEXT_SCORE };
#else
static unsigned char textScore[][9] = {{ TEXT_SCORE }, { TEXT_SCORE_FR }, { TEXT_SCORE_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textCopy[] = { TEXT_COPY };
#else
static unsigned char textCopy[][9] = {{ TEXT_COPY }, { TEXT_COPY_FR }, { TEXT_COPY_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textErase[] = { TEXT_ERASE };
#else
static unsigned char textErase[][8] = {{ TEXT_ERASE }, { TEXT_ERASE_FR }, { TEXT_ERASE_DE }};
#endif

#ifdef VERSION_EU
static unsigned char textOption[][9] = {{ TEXT_OPTION }, { TEXT_OPTION_FR }, { TEXT_OPTION_DE } };
#endif

#ifndef VERSION_EU
static unsigned char textCheckFile[] = { TEXT_CHECK_FILE };
#else
static unsigned char textCheckFile[][18] = {{ TEXT_CHECK_FILE }, { TEXT_CHECK_FILE_FR }, { TEXT_CHECK_FILE_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textNoSavedDataExists[] = { TEXT_NO_SAVED_DATA_EXISTS };
#else
static unsigned char textNoSavedDataExists[][30] = {{ TEXT_NO_SAVED_DATA_EXISTS }, { TEXT_NO_SAVED_DATA_EXISTS_FR }, { TEXT_NO_SAVED_DATA_EXISTS_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textCopyFile[] = { TEXT_COPY_FILE };
#else
static unsigned char textCopyFile[][16] = {{ TEXT_COPY_FILE_BUTTON }, { TEXT_COPY_FILE_BUTTON_FR }, { TEXT_COPY_FILE_BUTTON_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textCopyItToWhere[] = { TEXT_COPY_IT_TO_WHERE };
#else
static unsigned char textCopyItToWhere[][18] = {{ TEXT_COPY_IT_TO_WHERE }, { TEXT_COPY_IT_TO_WHERE_FR }, { TEXT_COPY_IT_TO_WHERE_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textNoSavedDataExistsCopy[] = { TEXT_NO_SAVED_DATA_EXISTS };
#endif

#ifndef VERSION_EU
static unsigned char textCopyCompleted[] = { TEXT_COPYING_COMPLETED };
#else
static unsigned char textCopyCompleted[][18] = {{ TEXT_COPYING_COMPLETED }, { TEXT_COPYING_COMPLETED_FR }, { TEXT_COPYING_COMPLETED_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textSavedDataExists[] = { TEXT_SAVED_DATA_EXISTS };
#else
static unsigned char textSavedDataExists[][20] = {{ TEXT_SAVED_DATA_EXISTS }, { TEXT_SAVED_DATA_EXISTS_FR }, { TEXT_SAVED_DATA_EXISTS_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textNoFileToCopyFrom[] = { TEXT_NO_FILE_TO_COPY_FROM };
#else
static unsigned char textNoFileToCopyFrom[][21] = {{ TEXT_NO_FILE_TO_COPY_FROM }, { TEXT_NO_FILE_TO_COPY_FROM_FR }, { TEXT_NO_FILE_TO_COPY_FROM_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textYes[] = { TEXT_YES };
#else
static unsigned char textYes[][4] = {{ TEXT_YES }, { TEXT_YES_FR }, { TEXT_YES_DE }};
#endif

#ifndef VERSION_EU
static unsigned char textNo[] = { TEXT_NO };
#else
static unsigned char textNo[][5] = {{ TEXT_NO }, { TEXT_NO_FR }, { TEXT_NO_DE }};
#endif

#ifdef VERSION_EU
// In EU, Erase File and Sound Select strings are outside it's print string function
static unsigned char textEraseFile[][17] = {
    { TEXT_ERASE_FILE_BUTTON }, { TEXT_ERASE_FILE_BUTTON_FR }, { TEXT_ERASE_FILE_BUTTON_DE }
};
static unsigned char textSure[][8] = {{ TEXT_SURE }, { TEXT_SURE_FR }, { TEXT_SURE_DE }};
static unsigned char textMarioAJustErased[][20] = {
    { TEXT_FILE_MARIO_A_JUST_ERASED }, { TEXT_FILE_MARIO_A_JUST_ERASED_FR }, { TEXT_FILE_MARIO_A_JUST_ERASED_DE }
};

static unsigned char textSoundSelect[][13] = {
    { TEXT_SOUND_SELECT }, { TEXT_SOUND_SELECT_FR }, { TEXT_SOUND_SELECT_DE }
};

static unsigned char textLanguageSelect[][17] = {
    { TEXT_LANGUAGE_SELECT }, { TEXT_LANGUAGE_SELECT_FR }, { TEXT_LANGUAGE_SELECT_DE }
};

static unsigned char textSoundModes[][10] = {
    { TEXT_STEREO }, { TEXT_MONO }, { TEXT_HEADSET },
    { TEXT_STEREO_FR }, { TEXT_MONO_FR }, { TEXT_HEADSET_FR },
    { TEXT_STEREO_DE }, { TEXT_MONO_DE }, { TEXT_HEADSET_DE }
};

static unsigned char textLanguage[][9] = {{ TEXT_ENGLISH }, { TEXT_FRENCH }, { TEXT_GERMAN }};

static unsigned char textMario[] = { TEXT_MARIO };
static unsigned char textHiScore[][15] = {{ TEXT_HI_SCORE }, { TEXT_HI_SCORE_FR }, { TEXT_HI_SCORE_DE }};
static unsigned char textMyScore[][10] = {{ TEXT_MY_SCORE }, { TEXT_MY_SCORE_FR }, { TEXT_MY_SCORE_DE }};

static unsigned char textNew[][5] = {{ TEXT_NEW }, { TEXT_NEW_FR }, { TEXT_NEW_DE }};
static unsigned char starIcon[] = { GLYPH_STAR, GLYPH_SPACE };
static unsigned char xIcon[] = { GLYPH_MULTIPLY, GLYPH_SPACE };
#endif

/**
 * Yellow Background Menu Initial Action
 * Rotates the background at 180 grades and it's scale.
 * Although the scale is properly applied in the loop function.
 */
void beh_yellow_background_menu_init(void) {
    gCurrentObject->oFaceAngleYaw = 0x8000;
    gCurrentObject->oMenuButtonScale = 9.0f;
}

/**
 * Yellow Background Menu Loop Action
 * Properly scales the background in the main menu.
 */
void beh_yellow_background_menu_loop(void) {
    cur_obj_scale(9.0f);
}

/**
 * Check if a button was clicked.
 * depth = 200.0 for main menu, 22.0 for submenus.
 */
s32 check_clicked_button(s16 x, s16 y, f32 depth) {
    f32 a = 52.4213;
    f32 newX = ((f32) x * 160.0) / (a * depth);
    f32 newY = ((f32) y * 120.0) / (a * 3 / 4 * depth);
    s16 maxX = newX + 25.0f;
    s16 minX = newX - 25.0f;
    s16 maxY = newY + 21.0f;
    s16 minY = newY - 21.0f;

    if (sClickPos[0] < maxX && minX < sClickPos[0] && sClickPos[1] < maxY && minY < sClickPos[1]) {
        return TRUE;
    }
    return FALSE;
}

/**
 * Grow from main menu, used by selecting files and menus.
 */
static void bhv_menu_button_growing_from_main_menu(struct Object *button) {
    if (button->oMenuButtonTimer < 16) {
        button->oFaceAngleYaw += 0x800;
    }
    if (button->oMenuButtonTimer < 8) {
        button->oFaceAnglePitch += 0x800;
    }
    if (button->oMenuButtonTimer >= 8 && button->oMenuButtonTimer < 16) {
        button->oFaceAnglePitch -= 0x800;
    }
    button->oParentRelativePosX -= button->oMenuButtonOrigPosX / 16.0;
    button->oParentRelativePosY -= button->oMenuButtonOrigPosY / 16.0;
    if (button->oPosZ < button->oMenuButtonOrigPosZ + 17800.0) {
        button->oParentRelativePosZ += 1112.5;
    }
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 16) {
        button->oParentRelativePosX = 0.0f;
        button->oParentRelativePosY = 0.0f;
        button->oMenuButtonState = MENU_BUTTON_STATE_FULLSCREEN;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * Shrink back to main menu, used to return back while inside menus.
 */
static void bhv_menu_button_shrinking_to_main_menu(struct Object *button) {
    if (button->oMenuButtonTimer < 16) {
        button->oFaceAngleYaw -= 0x800;
    }
    if (button->oMenuButtonTimer < 8) {
        button->oFaceAnglePitch -= 0x800;
    }
    if (button->oMenuButtonTimer >= 8 && button->oMenuButtonTimer < 16) {
        button->oFaceAnglePitch += 0x800;
    }
    button->oParentRelativePosX += button->oMenuButtonOrigPosX / 16.0;
    button->oParentRelativePosY += button->oMenuButtonOrigPosY / 16.0;
    if (button->oPosZ > button->oMenuButtonOrigPosZ) {
        button->oParentRelativePosZ -= 1112.5;
    }
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 16) {
        button->oParentRelativePosX = button->oMenuButtonOrigPosX;
        button->oParentRelativePosY = button->oMenuButtonOrigPosY;
        button->oMenuButtonState = MENU_BUTTON_STATE_DEFAULT;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * Grow from submenu, used by selecting a file in the score menu.
 */
static void bhv_menu_button_growing_from_submenu(struct Object *button) {
    if (button->oMenuButtonTimer < 16) {
        button->oFaceAngleYaw += 0x800;
    }
    if (button->oMenuButtonTimer < 8) {
        button->oFaceAnglePitch += 0x800;
    }
    if (button->oMenuButtonTimer >= 8 && button->oMenuButtonTimer < 16) {
        button->oFaceAnglePitch -= 0x800;
    }
    button->oParentRelativePosX -= button->oMenuButtonOrigPosX / 16.0;
    button->oParentRelativePosY -= button->oMenuButtonOrigPosY / 16.0;
    button->oParentRelativePosZ -= 116.25;
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 16) {
        button->oParentRelativePosX = 0.0f;
        button->oParentRelativePosY = 0.0f;
        button->oMenuButtonState = MENU_BUTTON_STATE_FULLSCREEN;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * Shrink back to submenu, used to return back while inside a score save menu.
 */
static void bhv_menu_button_shrinking_to_submenu(struct Object *button) {
    if (button->oMenuButtonTimer < 16) {
        button->oFaceAngleYaw -= 0x800;
    }
    if (button->oMenuButtonTimer < 8) {
        button->oFaceAnglePitch -= 0x800;
    }
    if (button->oMenuButtonTimer >= 8 && button->oMenuButtonTimer < 16) {
        button->oFaceAnglePitch += 0x800;
    }
    button->oParentRelativePosX += button->oMenuButtonOrigPosX / 16.0;
    button->oParentRelativePosY += button->oMenuButtonOrigPosY / 16.0;
    if (button->oPosZ > button->oMenuButtonOrigPosZ) {
        button->oParentRelativePosZ += 116.25;
    }
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 16) {
        button->oParentRelativePosX = button->oMenuButtonOrigPosX;
        button->oParentRelativePosY = button->oMenuButtonOrigPosY;
        button->oMenuButtonState = MENU_BUTTON_STATE_DEFAULT;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * A small increase and decrease in size.
 * Used by failed copy/erase/score operations and sound mode select.
 */
static void bhv_menu_button_zoom_in_out(struct Object *button) {
    if (sCurrentMenuLevel == MENU_LAYER_MAIN) {
        if (button->oMenuButtonTimer < 4) {
            button->oParentRelativePosZ -= 20.0f;
        }
        if (button->oMenuButtonTimer >= 4) {
            button->oParentRelativePosZ += 20.0f;
        }
    } else {
        if (button->oMenuButtonTimer < 4) {
            button->oParentRelativePosZ += 20.0f;
        }
        if (button->oMenuButtonTimer >= 4) {
            button->oParentRelativePosZ -= 20.0f;
        }
    }
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 8) {
        button->oMenuButtonState = MENU_BUTTON_STATE_DEFAULT;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * A small temporary increase in size.
 * Used while selecting a target copy/erase file or yes/no erase confirmation prompt.
 */
static void bhv_menu_button_zoom_in(struct Object *button) {
    button->oMenuButtonScale += 0.0022;
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 10) {
        button->oMenuButtonState = MENU_BUTTON_STATE_DEFAULT;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * A small temporary decrease in size.
 * Used after selecting a target copy/erase file or
 * yes/no erase confirmation prompt to undo the zoom in.
 */
static void bhv_menu_button_zoom_out(struct Object *button) {
    button->oMenuButtonScale -= 0.0022;
    button->oMenuButtonTimer++;
    if (button->oMenuButtonTimer == 10) {
        button->oMenuButtonState = MENU_BUTTON_STATE_DEFAULT;
        button->oMenuButtonTimer = 0;
    }
}

/**
 * Menu Buttons Menu Initial Action
 * Aligns menu buttons so they can stay in their original
 * positions when you choose a button.
 */
void bhv_menu_button_init(void) {
    gCurrentObject->oMenuButtonOrigPosX = gCurrentObject->oParentRelativePosX;
    gCurrentObject->oMenuButtonOrigPosY = gCurrentObject->oParentRelativePosY;
}

/**
 * Menu Buttons Menu Loop Action
 * Handles the functions of the button states and
 * object scale for each button.
 */
void bhv_menu_button_loop(void) {
    switch (gCurrentObject->oMenuButtonState) {
        case MENU_BUTTON_STATE_DEFAULT: // Button state
            gCurrentObject->oMenuButtonOrigPosZ = gCurrentObject->oPosZ;
            break;
        case MENU_BUTTON_STATE_GROWING: // Switching from button to menu state
            if (sCurrentMenuLevel == MENU_LAYER_MAIN) {
                bhv_menu_button_growing_from_main_menu(gCurrentObject);
            }
            if (sCurrentMenuLevel == MENU_LAYER_SUBMENU) {
                bhv_menu_button_growing_from_submenu(gCurrentObject); // Only used for score files
            }
            sTextBaseAlpha = 0;
            sCursorClickingTimer = 4;
            break;
        case MENU_BUTTON_STATE_FULLSCREEN: // Menu state
            break;
        case MENU_BUTTON_STATE_SHRINKING: // Switching from menu to button state
            if (sCurrentMenuLevel == MENU_LAYER_MAIN) {
                bhv_menu_button_shrinking_to_main_menu(gCurrentObject);
            }
            if (sCurrentMenuLevel == MENU_LAYER_SUBMENU) {
                bhv_menu_button_shrinking_to_submenu(gCurrentObject); // Only used for score files
            }
            sTextBaseAlpha = 0;
            sCursorClickingTimer = 4;
            break;
        case MENU_BUTTON_STATE_ZOOM_IN_OUT:
            bhv_menu_button_zoom_in_out(gCurrentObject);
            sCursorClickingTimer = 4;
            break;
        case MENU_BUTTON_STATE_ZOOM_IN:
            bhv_menu_button_zoom_in(gCurrentObject);
            sCursorClickingTimer = 4;
            break;
        case MENU_BUTTON_STATE_ZOOM_OUT:
            bhv_menu_button_zoom_out(gCurrentObject);
            sCursorClickingTimer = 4;
            break;
    }
    cur_obj_scale(gCurrentObject->oMenuButtonScale);
}

/**
 * Handles how to exit the score file menu using button states.
 */
void exit_score_file_to_score_menu(struct Object *scoreFileButton, s8 scoreButtonID) {
    // Begin exit
    if (scoreFileButton->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN
        && sCursorClickingTimer == 2) {
        play_sound(SOUND_MENU_CAMERA_ZOOM_OUT, gDefaultSoundArgs);
        scoreFileButton->oMenuButtonState = MENU_BUTTON_STATE_SHRINKING;
    }
    // End exit
    if (scoreFileButton->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT) {
        sSelectedButtonID = scoreButtonID;
        if (sCurrentMenuLevel == MENU_LAYER_SUBMENU) {
            sCurrentMenuLevel = MENU_LAYER_MAIN;
        }
    }
}

/**
 * Render buttons for the score menu.
 * Also check if the save file exists to render a different Mario button.
 */
void render_score_menu_buttons(struct Object *scoreButton) {
    // File A
    if (save_file_exists(SAVE_FILE_A) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_A] =
            spawn_object_rel_with_rot(scoreButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      711, 311, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_A] =
            spawn_object_rel_with_rot(scoreButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, 711,
                                      311, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_SCORE_FILE_A]->oMenuButtonScale = 0.11111111f;
    // File B
    if (save_file_exists(SAVE_FILE_B) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_B] =
            spawn_object_rel_with_rot(scoreButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      -166, 311, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_B] =
            spawn_object_rel_with_rot(scoreButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton,
                                      -166, 311, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_SCORE_FILE_B]->oMenuButtonScale = 0.11111111f;
    // File C
    if (save_file_exists(SAVE_FILE_C) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_C] = spawn_object_rel_with_rot(
            scoreButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton, 711, 0, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_C] = spawn_object_rel_with_rot(
            scoreButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, 711, 0, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_SCORE_FILE_C]->oMenuButtonScale = 0.11111111f;
    // File D
    if (save_file_exists(SAVE_FILE_D) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_D] =
            spawn_object_rel_with_rot(scoreButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      -166, 0, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_SCORE_FILE_D] = spawn_object_rel_with_rot(
            scoreButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, -166, 0, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_SCORE_FILE_D]->oMenuButtonScale = 0.11111111f;
    // Return to main menu button
    sMainMenuButtons[MENU_BUTTON_SCORE_RETURN] = spawn_object_rel_with_rot(
        scoreButton, MODEL_MAIN_MENU_YELLOW_FILE_BUTTON, bhvMenuButton, 711, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_SCORE_RETURN]->oMenuButtonScale = 0.11111111f;
    // Switch to copy menu button
    sMainMenuButtons[MENU_BUTTON_SCORE_COPY_FILE] = spawn_object_rel_with_rot(
        scoreButton, MODEL_MAIN_MENU_BLUE_COPY_BUTTON, bhvMenuButton, 0, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_SCORE_COPY_FILE]->oMenuButtonScale = 0.11111111f;
    // Switch to erase menu button
    sMainMenuButtons[MENU_BUTTON_SCORE_ERASE_FILE] = spawn_object_rel_with_rot(
        scoreButton, MODEL_MAIN_MENU_RED_ERASE_BUTTON, bhvMenuButton, -711, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_SCORE_ERASE_FILE]->oMenuButtonScale = 0.11111111f;
}

#ifdef VERSION_EU
    #define SCORE_TIMER 46
#else
    #define SCORE_TIMER 31
#endif
/**
 * In the score menu, checks if a button was clicked to play a sound, button state and other functions.
 */
void check_score_menu_clicked_buttons(struct Object *scoreButton) {
    if (scoreButton->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        s32 buttonID;
        // Configure score menu button group
        for (buttonID = MENU_BUTTON_SCORE_MIN; buttonID < MENU_BUTTON_SCORE_MAX; buttonID++) {
            s16 buttonX = sMainMenuButtons[buttonID]->oPosX;
            s16 buttonY = sMainMenuButtons[buttonID]->oPosY;

            if (check_clicked_button(buttonX, buttonY, 22.0f) == TRUE && sMainMenuTimer >= SCORE_TIMER) {
                // If menu button clicked, select it
                if (buttonID == MENU_BUTTON_SCORE_RETURN || buttonID == MENU_BUTTON_SCORE_COPY_FILE
                    || buttonID == MENU_BUTTON_SCORE_ERASE_FILE) {
                    play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                    sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
                    sSelectedButtonID = buttonID;
                }
                else { // Check if a save file is clicked
                    if (sMainMenuTimer >= SCORE_TIMER) {
                        // If clicked in a existing save file, select it too see it's score
                        if (save_file_exists(buttonID - MENU_BUTTON_SCORE_MIN) == TRUE) {
                            play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
                            sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
                            sSelectedButtonID = buttonID;
                        }
                        else {
                            // If clicked in a non-existing save file, play buzz sound
                            play_sound(SOUND_MENU_CAMERA_BUZZ, gDefaultSoundArgs);
                            sMainMenuButtons[buttonID]->oMenuButtonState =
                                MENU_BUTTON_STATE_ZOOM_IN_OUT;
                            if (sMainMenuTimer >= SCORE_TIMER) {
                                sFadeOutText = TRUE;
                                sMainMenuTimer = 0;
                            }
                        }
                    }
                }
                sCurrentMenuLevel = MENU_LAYER_SUBMENU;
                break;
            }
        }
    }
}
#undef SCORE_TIMER

/**
 * Render buttons for the copy menu.
 * Also check if the save file exists to render a different Mario button.
 */
void render_copy_menu_buttons(struct Object *copyButton) {
    // File A
    if (save_file_exists(SAVE_FILE_A) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_A] =
            spawn_object_rel_with_rot(copyButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton, 711,
                                      311, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_A] = spawn_object_rel_with_rot(
            copyButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, 711, 311, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_COPY_FILE_A]->oMenuButtonScale = 0.11111111f;
    // File B
    if (save_file_exists(SAVE_FILE_B) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_B] =
            spawn_object_rel_with_rot(copyButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      -166, 311, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_B] =
            spawn_object_rel_with_rot(copyButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, -166,
                                      311, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_COPY_FILE_B]->oMenuButtonScale = 0.11111111f;
    // File C
    if (save_file_exists(SAVE_FILE_C) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_C] = spawn_object_rel_with_rot(
            copyButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton, 711, 0, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_C] = spawn_object_rel_with_rot(
            copyButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, 711, 0, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_COPY_FILE_C]->oMenuButtonScale = 0.11111111f;
    // File D
    if (save_file_exists(SAVE_FILE_D) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_D] = spawn_object_rel_with_rot(
            copyButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton, -166, 0, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_COPY_FILE_D] = spawn_object_rel_with_rot(
            copyButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, -166, 0, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_COPY_FILE_D]->oMenuButtonScale = 0.11111111f;
    // Return to main menu button
    sMainMenuButtons[MENU_BUTTON_COPY_RETURN] = spawn_object_rel_with_rot(
        copyButton, MODEL_MAIN_MENU_YELLOW_FILE_BUTTON, bhvMenuButton, 711, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_COPY_RETURN]->oMenuButtonScale = 0.11111111f;
    // Switch to scire menu button
    sMainMenuButtons[MENU_BUTTON_COPY_CHECK_SCORE] = spawn_object_rel_with_rot(
        copyButton, MODEL_MAIN_MENU_GREEN_SCORE_BUTTON, bhvMenuButton, 0, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_COPY_CHECK_SCORE]->oMenuButtonScale = 0.11111111f;
    // Switch to erase menu button
    sMainMenuButtons[MENU_BUTTON_COPY_ERASE_FILE] = spawn_object_rel_with_rot(
        copyButton, MODEL_MAIN_MENU_RED_ERASE_BUTTON, bhvMenuButton, -711, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_COPY_ERASE_FILE]->oMenuButtonScale = 0.11111111f;
}

#ifdef VERSION_EU
    #define BUZZ_TIMER 36
#else
    #define BUZZ_TIMER 21
#endif

/**
 * Copy Menu phase actions that handles what to do when a file button is clicked.
 */
void copy_action_file_button(struct Object *copyButton, s32 copyFileButtonID) {
    switch (copyButton->oMenuButtonActionPhase) {
        case COPY_PHASE_MAIN: // Copy Menu Main Phase
            if (sAllFilesExist == TRUE) { // Don't enable copy if all save files exists
                return;
            }
            if (save_file_exists(copyFileButtonID - MENU_BUTTON_COPY_MIN) == TRUE) {
                // If clicked in a existing save file, ask where it wants to copy
                play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                sMainMenuButtons[copyFileButtonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN;
                sSelectedFileIndex = copyFileButtonID - MENU_BUTTON_COPY_MIN;
                copyButton->oMenuButtonActionPhase = COPY_PHASE_COPY_WHERE;
                sFadeOutText = TRUE;
                sMainMenuTimer = 0;
            } else {
                // If clicked in a non-existing save file, play buzz sound
                play_sound(SOUND_MENU_CAMERA_BUZZ, gDefaultSoundArgs);
                sMainMenuButtons[copyFileButtonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
                if (sMainMenuTimer >= BUZZ_TIMER) {
                    sFadeOutText = TRUE;
                    sMainMenuTimer = 0;
                }
            }
            break;
        case COPY_PHASE_COPY_WHERE: // Copy Menu "COPY IT TO WHERE?" Phase (after a file is selected)
            sMainMenuButtons[copyFileButtonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
            if (save_file_exists(copyFileButtonID - MENU_BUTTON_COPY_MIN) == FALSE) {
                // If clicked in a non-existing save file, copy the file
                play_sound(SOUND_MENU_STAR_SOUND, gDefaultSoundArgs);
                copyButton->oMenuButtonActionPhase = COPY_PHASE_COPY_COMPLETE;
                sFadeOutText = TRUE;
                sMainMenuTimer = 0;
                save_file_copy(sSelectedFileIndex, copyFileButtonID - MENU_BUTTON_COPY_MIN);
                sMainMenuButtons[copyFileButtonID]->header.gfx.sharedChild =
                    gLoadedGraphNodes[MODEL_MAIN_MENU_MARIO_SAVE_BUTTON_FADE];
                sMainMenuButtons[copyFileButtonID - MENU_BUTTON_COPY_MIN]->header.gfx.sharedChild =
                    gLoadedGraphNodes[MODEL_MAIN_MENU_MARIO_SAVE_BUTTON_FADE];
            } else {
                // If clicked in a existing save file, play buzz sound
                if (MENU_BUTTON_COPY_FILE_A + sSelectedFileIndex == copyFileButtonID) {
                    play_sound(SOUND_MENU_CAMERA_BUZZ, gDefaultSoundArgs);
                    sMainMenuButtons[MENU_BUTTON_COPY_FILE_A + sSelectedFileIndex]->oMenuButtonState =
                        MENU_BUTTON_STATE_ZOOM_OUT;
                    copyButton->oMenuButtonActionPhase = COPY_PHASE_MAIN;
                    sFadeOutText = TRUE;
                    return;
                }
                if (sMainMenuTimer >= BUZZ_TIMER) {
                    sFadeOutText = TRUE;
                    sMainMenuTimer = 0;
                }
            }
            break;
    }
}

#ifdef VERSION_EU
    #define ACTION_TIMER      41
    #define MAIN_RETURN_TIMER 36
#else
    #define ACTION_TIMER      31
    #define MAIN_RETURN_TIMER 31
#endif

/**
 * In the copy menu, checks if a button was clicked to play a sound, button state and other functions.
 */
void check_copy_menu_clicked_buttons(struct Object *copyButton) {
    if (copyButton->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        s32 buttonID;
        // Configure copy menu button group
        for (buttonID = MENU_BUTTON_COPY_MIN; buttonID < MENU_BUTTON_COPY_MAX; buttonID++) {
            s16 buttonX = sMainMenuButtons[buttonID]->oPosX;
            s16 buttonY = sMainMenuButtons[buttonID]->oPosY;

            if (check_clicked_button(buttonX, buttonY, 22.0f) == TRUE) {
                // If menu button clicked, select it
                if (buttonID == MENU_BUTTON_COPY_RETURN || buttonID == MENU_BUTTON_COPY_CHECK_SCORE
                    || buttonID == MENU_BUTTON_COPY_ERASE_FILE) {
                    if (copyButton->oMenuButtonActionPhase == COPY_PHASE_MAIN) {
                        play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                        sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
                        sSelectedButtonID = buttonID;
                    }
                }
                else {
                    // Check if a file button is clicked to play a copy action
                    if (sMainMenuButtons[buttonID]->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT
                        && sMainMenuTimer >= ACTION_TIMER) {
                        copy_action_file_button(copyButton, buttonID);
                    }
                }
                sCurrentMenuLevel = MENU_LAYER_SUBMENU;
                break;
            }
        }

        // After copy is complete, return to main copy phase
        if (copyButton->oMenuButtonActionPhase == COPY_PHASE_COPY_COMPLETE
            && sMainMenuTimer >= MAIN_RETURN_TIMER) {
            copyButton->oMenuButtonActionPhase = COPY_PHASE_MAIN;
            sMainMenuButtons[MENU_BUTTON_COPY_MIN + sSelectedFileIndex]->oMenuButtonState =
                MENU_BUTTON_STATE_ZOOM_OUT;
        }
    }
}

/**
 * Render buttons for the erase menu.
 * Also check if the save file exists to render a different Mario button.
 */
void render_erase_menu_buttons(struct Object *eraseButton) {
    // File A
    if (save_file_exists(SAVE_FILE_A) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_A] =
            spawn_object_rel_with_rot(eraseButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      711, 311, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_A] =
            spawn_object_rel_with_rot(eraseButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, 711,
                                      311, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_ERASE_FILE_A]->oMenuButtonScale = 0.11111111f;
    // File B
    if (save_file_exists(SAVE_FILE_B) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_B] =
            spawn_object_rel_with_rot(eraseButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      -166, 311, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_B] =
            spawn_object_rel_with_rot(eraseButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton,
                                      -166, 311, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_ERASE_FILE_B]->oMenuButtonScale = 0.11111111f;
    // File C
    if (save_file_exists(SAVE_FILE_C) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_C] = spawn_object_rel_with_rot(
            eraseButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton, 711, 0, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_C] = spawn_object_rel_with_rot(
            eraseButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, 711, 0, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_ERASE_FILE_C]->oMenuButtonScale = 0.11111111f;
    // File D
    if (save_file_exists(SAVE_FILE_D) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_D] =
            spawn_object_rel_with_rot(eraseButton, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON, bhvMenuButton,
                                      -166, 0, -100, 0, -0x8000, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_ERASE_FILE_D] = spawn_object_rel_with_rot(
            eraseButton, MODEL_MAIN_MENU_MARIO_NEW_BUTTON, bhvMenuButton, -166, 0, -100, 0, -0x8000, 0);
    }
    sMainMenuButtons[MENU_BUTTON_ERASE_FILE_D]->oMenuButtonScale = 0.11111111f;
    // Return to main menu button
    sMainMenuButtons[MENU_BUTTON_ERASE_RETURN] = spawn_object_rel_with_rot(
        eraseButton, MODEL_MAIN_MENU_YELLOW_FILE_BUTTON, bhvMenuButton, 711, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_ERASE_RETURN]->oMenuButtonScale = 0.11111111f;
    // Switch to score menu button
    sMainMenuButtons[MENU_BUTTON_ERASE_CHECK_SCORE] = spawn_object_rel_with_rot(
        eraseButton, MODEL_MAIN_MENU_GREEN_SCORE_BUTTON, bhvMenuButton, 0, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_ERASE_CHECK_SCORE]->oMenuButtonScale = 0.11111111f;
    // Switch to copy menu button
    sMainMenuButtons[MENU_BUTTON_ERASE_COPY_FILE] = spawn_object_rel_with_rot(
        eraseButton, MODEL_MAIN_MENU_BLUE_COPY_BUTTON, bhvMenuButton, -711, -388, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_ERASE_COPY_FILE]->oMenuButtonScale = 0.11111111f;
}

/**
 * Erase Menu phase actions that handles what to do when a file button is clicked.
 */
void erase_action_file_button(struct Object *eraseButton, s32 eraseFileButtonID) {
    switch (eraseButton->oMenuButtonActionPhase) {
        case ERASE_PHASE_MAIN: // Erase Menu Main Phase
            if (save_file_exists(eraseFileButtonID - MENU_BUTTON_ERASE_MIN) == TRUE) {
                // If clicked in a existing save file, ask if it wants to delete it
                play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                sMainMenuButtons[eraseFileButtonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN;
                sSelectedFileIndex = eraseFileButtonID - MENU_BUTTON_ERASE_MIN;
                eraseButton->oMenuButtonActionPhase = ERASE_PHASE_PROMPT;
                sFadeOutText = TRUE;
                sMainMenuTimer = 0;
            } else {
                // If clicked in a non-existing save file, play buzz sound
                play_sound(SOUND_MENU_CAMERA_BUZZ, gDefaultSoundArgs);
                sMainMenuButtons[eraseFileButtonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;

                if (sMainMenuTimer >= BUZZ_TIMER) {
                    sFadeOutText = TRUE;
                    sMainMenuTimer = 0;
                }
            }
            break;
        case ERASE_PHASE_PROMPT: // Erase Menu "SURE? YES NO" Phase (after a file is selected)
            if (MENU_BUTTON_ERASE_MIN + sSelectedFileIndex == eraseFileButtonID) {
                // If clicked in a existing save file, play click sound and zoom out button
                // Note: The prompt functions are actually called when the ERASE_MSG_PROMPT
                // message is displayed with print_erase_menu_prompt
                play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                sMainMenuButtons[MENU_BUTTON_ERASE_MIN + sSelectedFileIndex]->oMenuButtonState =
                    MENU_BUTTON_STATE_ZOOM_OUT;
                eraseButton->oMenuButtonActionPhase = ERASE_PHASE_MAIN;
                sFadeOutText = TRUE;
            }
            break;
    }
}
#undef BUZZ_TIMER

/**
 * In the erase menu, checks if a button was clicked to play a sound, button state and other functions.
 */
void check_erase_menu_clicked_buttons(struct Object *eraseButton) {
    if (eraseButton->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        s32 buttonID;
        // Configure erase menu button group
        for (buttonID = MENU_BUTTON_ERASE_MIN; buttonID < MENU_BUTTON_ERASE_MAX; buttonID++) {
            s16 buttonX = sMainMenuButtons[buttonID]->oPosX;
            s16 buttonY = sMainMenuButtons[buttonID]->oPosY;

            if (check_clicked_button(buttonX, buttonY, 22.0f) == TRUE) {
                // If menu button clicked, select it
                if (buttonID == MENU_BUTTON_ERASE_RETURN || buttonID == MENU_BUTTON_ERASE_CHECK_SCORE
                    || buttonID == MENU_BUTTON_ERASE_COPY_FILE) {
                    if (eraseButton->oMenuButtonActionPhase == ERASE_PHASE_MAIN) {
                        play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                        sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
                        sSelectedButtonID = buttonID;
                    }
                }
                else {
                    // Check if a file button is clicked to play an erase action
                    if (sMainMenuTimer >= ACTION_TIMER) {
                        erase_action_file_button(eraseButton, buttonID);
                    }
                }
                sCurrentMenuLevel = MENU_LAYER_SUBMENU;
                break;
            }
        }
        // After erase is complete, return to main erase phase
        if (eraseButton->oMenuButtonActionPhase == ERASE_PHASE_MARIO_ERASED
            && sMainMenuTimer >= MAIN_RETURN_TIMER) {
            eraseButton->oMenuButtonActionPhase = ERASE_PHASE_MAIN;
            sMainMenuButtons[MENU_BUTTON_ERASE_MIN + sSelectedFileIndex]->oMenuButtonState =
                MENU_BUTTON_STATE_ZOOM_OUT;
        }
    }
}
#undef ACTION_TIMER
#undef MAIN_RETURN_TIMER

#ifdef VERSION_EU
    #define SOUND_BUTTON_Y 388
#else
    #define SOUND_BUTTON_Y 0
#endif

/**
 * Render buttons for the sound mode menu.
 */
void render_sound_mode_menu_buttons(struct Object *soundModeButton) {
    // Stereo option button
    sMainMenuButtons[MENU_BUTTON_STEREO] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_GENERIC_BUTTON, bhvMenuButton, 533, SOUND_BUTTON_Y, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_STEREO]->oMenuButtonScale = 0.11111111f;
    // Mono option button
    sMainMenuButtons[MENU_BUTTON_MONO] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_GENERIC_BUTTON, bhvMenuButton, 0, SOUND_BUTTON_Y, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_MONO]->oMenuButtonScale = 0.11111111f;
    // Headset option button
    sMainMenuButtons[MENU_BUTTON_HEADSET] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_GENERIC_BUTTON, bhvMenuButton, -533, SOUND_BUTTON_Y, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_HEADSET]->oMenuButtonScale = 0.11111111f;

#ifdef VERSION_EU
    // English option button
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_ENGLISH] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_GENERIC_BUTTON, bhvMenuButton, 533, -111, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_ENGLISH]->oMenuButtonScale = 0.11111111f;
    // French option button
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_FRENCH] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_GENERIC_BUTTON, bhvMenuButton, 0, -111, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_FRENCH]->oMenuButtonScale = 0.11111111f;
    // German option button
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_GERMAN] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_GENERIC_BUTTON, bhvMenuButton, -533, -111, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_GERMAN]->oMenuButtonScale = 0.11111111f;

    // Return button
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_RETURN] = spawn_object_rel_with_rot(
        soundModeButton, MODEL_MAIN_MENU_YELLOW_FILE_BUTTON, bhvMenuButton, 0, -533, -100, 0, -0x8000, 0);
    sMainMenuButtons[MENU_BUTTON_LANGUAGE_RETURN]->oMenuButtonScale = 0.11111111f;
#else
    // Zoom in current selection
    sMainMenuButtons[MENU_BUTTON_OPTION_MIN + sSoundMode]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN;
#endif
}
#undef SOUND_BUTTON_Y

/**
 * In the sound mode menu, checks if a button was clicked to change sound mode & button state.
 */
void check_sound_mode_menu_clicked_buttons(struct Object *soundModeButton) {
    if (soundModeButton->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        s32 buttonID;
        // Configure sound mode menu button group
        for (buttonID = MENU_BUTTON_OPTION_MIN; buttonID < MENU_BUTTON_OPTION_MAX; buttonID++) {
            s16 buttonX = sMainMenuButtons[buttonID]->oPosX;
            s16 buttonY = sMainMenuButtons[buttonID]->oPosY;

            if (check_clicked_button(buttonX, buttonY, 22.0f) == TRUE) {
                // If sound mode button clicked, select it and define sound mode
                // The check will always be true because of the group configured above (In JP & US)
                if (buttonID == MENU_BUTTON_STEREO || buttonID == MENU_BUTTON_MONO
                    || buttonID == MENU_BUTTON_HEADSET) {
                    if (soundModeButton->oMenuButtonActionPhase == SOUND_MODE_PHASE_MAIN) {
                        play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                        sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
#ifndef VERSION_EU
                        // Sound menu buttons don't return to Main Menu in EU
                        // because they don't have a case in bhv_menu_button_manager_loop
                        sSelectedButtonID = buttonID;
#endif
                        sSoundMode = buttonID - MENU_BUTTON_OPTION_MIN;
                        save_file_set_sound_mode(sSoundMode);
                    }
                }
#ifdef VERSION_EU
                // If language mode button clicked, select it and change language
                if (buttonID == MENU_BUTTON_LANGUAGE_ENGLISH || buttonID == MENU_BUTTON_LANGUAGE_FRENCH
                         || buttonID == MENU_BUTTON_LANGUAGE_GERMAN) {
                    if (soundModeButton->oMenuButtonActionPhase == SOUND_MODE_PHASE_MAIN) {
                        play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                        sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
                        sLanguageMode = buttonID - MENU_BUTTON_LANGUAGE_MIN;
                        eu_set_language(sLanguageMode);
                    }
                }
                // If neither of the buttons above are pressed, return to main menu
                if (buttonID == MENU_BUTTON_LANGUAGE_RETURN) {
                    play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
                    sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_ZOOM_IN_OUT;
                    sSelectedButtonID = buttonID;
                }
#endif
                sCurrentMenuLevel = MENU_LAYER_SUBMENU;

                break;
            }
        }
    }
}

/**
 * Loads a save file selected after it goes into a full screen state
 * retuning sSelectedFileNum to a save value defined in fileNum.
 */
void load_main_menu_save_file(struct Object *fileButton, s32 fileNum) {
    if (fileButton->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        sSelectedFileNum = fileNum;
    }
}

/**
 * Returns from the previous menu back to the main menu using
 * the return button (or sound mode) as source button.
 */
void return_to_main_menu(s16 prevMenuButtonID, struct Object *sourceButton) {
    s32 buttonID;
    // If the source button is in default state and the previous menu in full screen,
    // play zoom out sound and shrink previous menu
    if (sourceButton->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT
        && sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        play_sound(SOUND_MENU_CAMERA_ZOOM_OUT, gDefaultSoundArgs);
        sMainMenuButtons[prevMenuButtonID]->oMenuButtonState = MENU_BUTTON_STATE_SHRINKING;
        sCurrentMenuLevel = MENU_LAYER_MAIN;
    }
    // If the previous button is in default state, return back to the main menu
    if (sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT) {
        sSelectedButtonID = MENU_BUTTON_NONE;
        // Hide buttons of corresponding button menu groups
        if (prevMenuButtonID == MENU_BUTTON_SCORE) {
            for (buttonID = MENU_BUTTON_SCORE_MIN; buttonID < MENU_BUTTON_SCORE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_COPY) {
            for (buttonID = MENU_BUTTON_COPY_MIN; buttonID < MENU_BUTTON_COPY_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_ERASE) {
            for (buttonID = MENU_BUTTON_ERASE_MIN; buttonID < MENU_BUTTON_ERASE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_SOUND_MODE) {
            for (buttonID = MENU_BUTTON_OPTION_MIN; buttonID < MENU_BUTTON_OPTION_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
    }
}

/**
 * Loads score menu from the previous menu using "CHECK SCORE" as source button.
 */
void load_score_menu_from_submenu(s16 prevMenuButtonID, struct Object *sourceButton) {
    s32 buttonID;
    // If the source button is in default state and the previous menu in full screen,
    // play zoom out sound and shrink previous menu
    if (sourceButton->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT
        && sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        play_sound(SOUND_MENU_CAMERA_ZOOM_OUT, gDefaultSoundArgs);
        sMainMenuButtons[prevMenuButtonID]->oMenuButtonState = MENU_BUTTON_STATE_SHRINKING;
        sCurrentMenuLevel = MENU_LAYER_MAIN;
    }
    // If the previous button is in default state
    if (sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT) {
        // Hide buttons of corresponding button menu groups
        if (prevMenuButtonID == MENU_BUTTON_SCORE) //! Not possible, this is checking if the score menu
                                                   //! was opened from the score menu!
        {
            for (buttonID = MENU_BUTTON_SCORE_MIN; buttonID < MENU_BUTTON_SCORE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_COPY) {
            for (buttonID = MENU_BUTTON_COPY_MIN; buttonID < MENU_BUTTON_COPY_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_ERASE) {
            for (buttonID = MENU_BUTTON_ERASE_MIN; buttonID < MENU_BUTTON_ERASE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        // Play zoom in sound, select score menu and render it's buttons
        sSelectedButtonID = MENU_BUTTON_SCORE;
        play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
        sMainMenuButtons[MENU_BUTTON_SCORE]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
        render_score_menu_buttons(sMainMenuButtons[MENU_BUTTON_SCORE]);
    }
}

/**
 * Loads copy menu from the previous menu using "COPY FILE" as source button.
 */
void load_copy_menu_from_submenu(s16 prevMenuButtonID, struct Object *sourceButton) {
    s32 buttonID;
    // If the source button is in default state and the previous menu in full screen,
    // play zoom out sound and shrink previous menu
    if (sourceButton->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT
        && sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        play_sound(SOUND_MENU_CAMERA_ZOOM_OUT, gDefaultSoundArgs);
        sMainMenuButtons[prevMenuButtonID]->oMenuButtonState = MENU_BUTTON_STATE_SHRINKING;
        sCurrentMenuLevel = MENU_LAYER_MAIN;
    }
    // If the previous button is in default state
    if (sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT) {
        // Hide buttons of corresponding button menu groups
        if (prevMenuButtonID == MENU_BUTTON_SCORE) {
            for (buttonID = MENU_BUTTON_SCORE_MIN; buttonID < MENU_BUTTON_SCORE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_COPY) //! Not possible, this is checking if the copy menu
                                                  //! was opened from the copy menu!
        {
            for (buttonID = MENU_BUTTON_COPY_MIN; buttonID < MENU_BUTTON_COPY_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_ERASE) {
            for (buttonID = MENU_BUTTON_ERASE_MIN; buttonID < MENU_BUTTON_ERASE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        // Play zoom in sound, select copy menu and render it's buttons
        sSelectedButtonID = MENU_BUTTON_COPY;
        play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
        sMainMenuButtons[MENU_BUTTON_COPY]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
        render_copy_menu_buttons(sMainMenuButtons[MENU_BUTTON_COPY]);
    }
}

/**
 * Loads erase menu from the previous menu using "ERASE FILE" as source button.
 */
void load_erase_menu_from_submenu(s16 prevMenuButtonID, struct Object *sourceButton) {
    s32 buttonID;
    // If the source button is in default state and the previous menu in full screen,
    // play zoom out sound and shrink previous menu
    if (sourceButton->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT
        && sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_FULLSCREEN) {
        play_sound(SOUND_MENU_CAMERA_ZOOM_OUT, gDefaultSoundArgs);
        sMainMenuButtons[prevMenuButtonID]->oMenuButtonState = MENU_BUTTON_STATE_SHRINKING;
        sCurrentMenuLevel = MENU_LAYER_MAIN;
    }
    // If the previous button is in default state
    if (sMainMenuButtons[prevMenuButtonID]->oMenuButtonState == MENU_BUTTON_STATE_DEFAULT) {
        // Hide buttons of corresponding button menu groups
        if (prevMenuButtonID == MENU_BUTTON_SCORE) {
            for (buttonID = MENU_BUTTON_SCORE_MIN; buttonID < MENU_BUTTON_SCORE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_COPY) {
            for (buttonID = MENU_BUTTON_COPY_MIN; buttonID < MENU_BUTTON_COPY_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        if (prevMenuButtonID == MENU_BUTTON_ERASE) //! Not possible, this is checking if the erase menu
                                                   //! was opened from the erase menu!
        {
            for (buttonID = MENU_BUTTON_ERASE_MIN; buttonID < MENU_BUTTON_ERASE_MAX; buttonID++) {
                mark_obj_for_deletion(sMainMenuButtons[buttonID]);
            }
        }
        // Play zoom in sound, select erase menu and render it's buttons
        sSelectedButtonID = MENU_BUTTON_ERASE;
        play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
        sMainMenuButtons[MENU_BUTTON_ERASE]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
        render_erase_menu_buttons(sMainMenuButtons[MENU_BUTTON_ERASE]);
    }
}


/**
 * Menu Buttons Menu Manager Initial Action
 * Creates models of the buttons in the menu. For the Mario buttons it
 * checks if a save file exists to render an specific button model for it.
 * Unlike buttons on submenus, these are never hidden or recreated.
 */
void bhv_menu_button_manager_init(void) {
    // File A
    if (save_file_exists(SAVE_FILE_A) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_A] =
            spawn_object_rel_with_rot(gCurrentObject, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON_FADE,
                                      bhvMenuButton, -6400, 2800, 0, 0, 0, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_A] =
            spawn_object_rel_with_rot(gCurrentObject, MODEL_MAIN_MENU_MARIO_NEW_BUTTON_FADE,
                                      bhvMenuButton, -6400, 2800, 0, 0, 0, 0);
    }
    sMainMenuButtons[MENU_BUTTON_PLAY_FILE_A]->oMenuButtonScale = 1.0f;
    // File B
    if (save_file_exists(SAVE_FILE_B) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_B] =
            spawn_object_rel_with_rot(gCurrentObject, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON_FADE,
                                      bhvMenuButton, 1500, 2800, 0, 0, 0, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_B] =
            spawn_object_rel_with_rot(gCurrentObject, MODEL_MAIN_MENU_MARIO_NEW_BUTTON_FADE,
                                      bhvMenuButton, 1500, 2800, 0, 0, 0, 0);
    }
    sMainMenuButtons[MENU_BUTTON_PLAY_FILE_B]->oMenuButtonScale = 1.0f;
    // File C
    if (save_file_exists(SAVE_FILE_C) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_C] =
            spawn_object_rel_with_rot(gCurrentObject, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON_FADE,
                                      bhvMenuButton, -6400, 0, 0, 0, 0, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_C] = spawn_object_rel_with_rot(
            gCurrentObject, MODEL_MAIN_MENU_MARIO_NEW_BUTTON_FADE, bhvMenuButton, -6400, 0, 0, 0, 0, 0);
    }
    sMainMenuButtons[MENU_BUTTON_PLAY_FILE_C]->oMenuButtonScale = 1.0f;
    // File D
    if (save_file_exists(SAVE_FILE_D) == TRUE) {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_D] = spawn_object_rel_with_rot(
            gCurrentObject, MODEL_MAIN_MENU_MARIO_SAVE_BUTTON_FADE, bhvMenuButton, 1500, 0, 0, 0, 0, 0);
    } else {
        sMainMenuButtons[MENU_BUTTON_PLAY_FILE_D] = spawn_object_rel_with_rot(
            gCurrentObject, MODEL_MAIN_MENU_MARIO_NEW_BUTTON_FADE, bhvMenuButton, 1500, 0, 0, 0, 0, 0);
    }
    sMainMenuButtons[MENU_BUTTON_PLAY_FILE_D]->oMenuButtonScale = 1.0f;
    // Score menu button
    sMainMenuButtons[MENU_BUTTON_SCORE] = spawn_object_rel_with_rot(
        gCurrentObject, MODEL_MAIN_MENU_GREEN_SCORE_BUTTON, bhvMenuButton, -6400, -3500, 0, 0, 0, 0);
    sMainMenuButtons[MENU_BUTTON_SCORE]->oMenuButtonScale = 1.0f;
    // Copy menu button
    sMainMenuButtons[MENU_BUTTON_COPY] = spawn_object_rel_with_rot(
        gCurrentObject, MODEL_MAIN_MENU_BLUE_COPY_BUTTON, bhvMenuButton, -2134, -3500, 0, 0, 0, 0);
    sMainMenuButtons[MENU_BUTTON_COPY]->oMenuButtonScale = 1.0f;
    // Erase menu button
    sMainMenuButtons[MENU_BUTTON_ERASE] = spawn_object_rel_with_rot(
        gCurrentObject, MODEL_MAIN_MENU_RED_ERASE_BUTTON, bhvMenuButton, 2134, -3500, 0, 0, 0, 0);
    sMainMenuButtons[MENU_BUTTON_ERASE]->oMenuButtonScale = 1.0f;
    // Sound mode menu button (Option Mode in EU)
    sMainMenuButtons[MENU_BUTTON_SOUND_MODE] = spawn_object_rel_with_rot(
        gCurrentObject, MODEL_MAIN_MENU_PURPLE_SOUND_BUTTON, bhvMenuButton, 6400, -3500, 0, 0, 0, 0);
    sMainMenuButtons[MENU_BUTTON_SOUND_MODE]->oMenuButtonScale = 1.0f;

    sTextBaseAlpha = 0;
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define SAVE_FILE_SOUND SOUND_MENU_STAR_SOUND
#else
    #define SAVE_FILE_SOUND SOUND_MENU_STAR_SOUND_OKEY_DOKEY
#endif

/**
 * In the main menu, check if a button was clicked to play it's button growing state.
 * Also play a sound and/or render buttons depending of the button ID selected.
 */
void check_main_menu_clicked_buttons(void) {
#ifdef VERSION_EU
    if (sMainMenuTimer >= 5) {
#endif
        // Sound mode menu is handled separately because the button ID for it
        // is not grouped with the IDs of the other submenus.
        if (check_clicked_button(sMainMenuButtons[MENU_BUTTON_SOUND_MODE]->oPosX,
                                sMainMenuButtons[MENU_BUTTON_SOUND_MODE]->oPosY, 200.0f) == TRUE) {
            sMainMenuButtons[MENU_BUTTON_SOUND_MODE]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
            sSelectedButtonID = MENU_BUTTON_SOUND_MODE;
        } else {
            // Main Menu buttons
            s8 buttonID;
            // Configure Main Menu button group
            for (buttonID = MENU_BUTTON_MAIN_MIN; buttonID < MENU_BUTTON_MAIN_MAX; buttonID++) {
                s16 buttonX = sMainMenuButtons[buttonID]->oPosX;
                s16 buttonY = sMainMenuButtons[buttonID]->oPosY;

                if (check_clicked_button(buttonX, buttonY, 200.0f) == TRUE) {
                    // If menu button clicked, select it
                    sMainMenuButtons[buttonID]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
                    sSelectedButtonID = buttonID;
                    break;
                }
            }
        }
#ifdef VERSION_EU
        // Open Options Menu if sOpenLangSettings is TRUE (It's TRUE when there's no saves)
        if (sOpenLangSettings == TRUE) {
            sMainMenuButtons[MENU_BUTTON_SOUND_MODE]->oMenuButtonState = MENU_BUTTON_STATE_GROWING;
            sSelectedButtonID = MENU_BUTTON_SOUND_MODE;
            sOpenLangSettings = FALSE;
        }
#endif

        // Play sound of the save file clicked
        switch (sSelectedButtonID) {
            case MENU_BUTTON_PLAY_FILE_A:
                play_sound(SAVE_FILE_SOUND, gDefaultSoundArgs);
                break;
            case MENU_BUTTON_PLAY_FILE_B:
                play_sound(SAVE_FILE_SOUND, gDefaultSoundArgs);
                break;
            case MENU_BUTTON_PLAY_FILE_C:
                play_sound(SAVE_FILE_SOUND, gDefaultSoundArgs);
                break;
            case MENU_BUTTON_PLAY_FILE_D:
                play_sound(SAVE_FILE_SOUND, gDefaultSoundArgs);
                break;
            // Play sound of the button clicked and render buttons of that menu.
            case MENU_BUTTON_SCORE:
                play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
                render_score_menu_buttons(sMainMenuButtons[MENU_BUTTON_SCORE]);
                break;
            case MENU_BUTTON_COPY:
                play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
                render_copy_menu_buttons(sMainMenuButtons[MENU_BUTTON_COPY]);
                break;
            case MENU_BUTTON_ERASE:
                play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
                render_erase_menu_buttons(sMainMenuButtons[MENU_BUTTON_ERASE]);
                break;
            case MENU_BUTTON_SOUND_MODE:
                play_sound(SOUND_MENU_CAMERA_ZOOM_IN, gDefaultSoundArgs);
                render_sound_mode_menu_buttons(sMainMenuButtons[MENU_BUTTON_SOUND_MODE]);
                break;
        }
#ifdef VERSION_EU
    }
#endif
}
#undef SAVE_FILE_SOUND

/**
 * Menu Buttons Menu Manager Loop Action
 * Calls a menu function depending of the button chosen.
 * sSelectedButtonID is MENU_BUTTON_NONE when the file select
 * is loaded, and that checks what buttonID is clicked in the main menu.
 */
void bhv_menu_button_manager_loop(void) {
    switch (sSelectedButtonID) {
        case MENU_BUTTON_NONE:
            check_main_menu_clicked_buttons();
            break;
        case MENU_BUTTON_PLAY_FILE_A:
            load_main_menu_save_file(sMainMenuButtons[MENU_BUTTON_PLAY_FILE_A], 1);
            break;
        case MENU_BUTTON_PLAY_FILE_B:
            load_main_menu_save_file(sMainMenuButtons[MENU_BUTTON_PLAY_FILE_B], 2);
            break;
        case MENU_BUTTON_PLAY_FILE_C:
            load_main_menu_save_file(sMainMenuButtons[MENU_BUTTON_PLAY_FILE_C], 3);
            break;
        case MENU_BUTTON_PLAY_FILE_D:
            load_main_menu_save_file(sMainMenuButtons[MENU_BUTTON_PLAY_FILE_D], 4);
            break;
        case MENU_BUTTON_SCORE:
            check_score_menu_clicked_buttons(sMainMenuButtons[MENU_BUTTON_SCORE]);
            break;
        case MENU_BUTTON_COPY:
            check_copy_menu_clicked_buttons(sMainMenuButtons[MENU_BUTTON_COPY]);
            break;
        case MENU_BUTTON_ERASE:
            check_erase_menu_clicked_buttons(sMainMenuButtons[MENU_BUTTON_ERASE]);
            break;

        case MENU_BUTTON_SCORE_FILE_A:
            exit_score_file_to_score_menu(sMainMenuButtons[MENU_BUTTON_SCORE_FILE_A], MENU_BUTTON_SCORE);
            break;
        case MENU_BUTTON_SCORE_FILE_B:
            exit_score_file_to_score_menu(sMainMenuButtons[MENU_BUTTON_SCORE_FILE_B], MENU_BUTTON_SCORE);
            break;
        case MENU_BUTTON_SCORE_FILE_C:
            exit_score_file_to_score_menu(sMainMenuButtons[MENU_BUTTON_SCORE_FILE_C], MENU_BUTTON_SCORE);
            break;
        case MENU_BUTTON_SCORE_FILE_D:
            exit_score_file_to_score_menu(sMainMenuButtons[MENU_BUTTON_SCORE_FILE_D], MENU_BUTTON_SCORE);
            break;
        case MENU_BUTTON_SCORE_RETURN:
            return_to_main_menu(MENU_BUTTON_SCORE, sMainMenuButtons[MENU_BUTTON_SCORE_RETURN]);
            break;
        case MENU_BUTTON_SCORE_COPY_FILE:
            load_copy_menu_from_submenu(MENU_BUTTON_SCORE,
                                        sMainMenuButtons[MENU_BUTTON_SCORE_COPY_FILE]);
            break;
        case MENU_BUTTON_SCORE_ERASE_FILE:
            load_erase_menu_from_submenu(MENU_BUTTON_SCORE,
                                         sMainMenuButtons[MENU_BUTTON_SCORE_ERASE_FILE]);
            break;

        case MENU_BUTTON_COPY_FILE_A:
            break;
        case MENU_BUTTON_COPY_FILE_B:
            break;
        case MENU_BUTTON_COPY_FILE_C:
            break;
        case MENU_BUTTON_COPY_FILE_D:
            break;
        case MENU_BUTTON_COPY_RETURN:
            return_to_main_menu(MENU_BUTTON_COPY, sMainMenuButtons[MENU_BUTTON_COPY_RETURN]);
            break;
        case MENU_BUTTON_COPY_CHECK_SCORE:
            load_score_menu_from_submenu(MENU_BUTTON_COPY,
                                         sMainMenuButtons[MENU_BUTTON_COPY_CHECK_SCORE]);
            break;
        case MENU_BUTTON_COPY_ERASE_FILE:
            load_erase_menu_from_submenu(MENU_BUTTON_COPY,
                                         sMainMenuButtons[MENU_BUTTON_COPY_ERASE_FILE]);
            break;

        case MENU_BUTTON_ERASE_FILE_A:
            break;
        case MENU_BUTTON_ERASE_FILE_B:
            break;
        case MENU_BUTTON_ERASE_FILE_C:
            break;
        case MENU_BUTTON_ERASE_FILE_D:
            break;
        case MENU_BUTTON_ERASE_RETURN:
            return_to_main_menu(MENU_BUTTON_ERASE, sMainMenuButtons[MENU_BUTTON_ERASE_RETURN]);
            break;
        case MENU_BUTTON_ERASE_CHECK_SCORE:
            load_score_menu_from_submenu(MENU_BUTTON_ERASE,
                                         sMainMenuButtons[MENU_BUTTON_ERASE_CHECK_SCORE]);
            break;
        case MENU_BUTTON_ERASE_COPY_FILE:
            load_copy_menu_from_submenu(MENU_BUTTON_ERASE,
                                        sMainMenuButtons[MENU_BUTTON_ERASE_COPY_FILE]);
            break;

        case MENU_BUTTON_SOUND_MODE:
            check_sound_mode_menu_clicked_buttons(sMainMenuButtons[MENU_BUTTON_SOUND_MODE]);
            break;

        // STEREO, MONO and HEADSET buttons are undefined so they can be selected without
        // exiting the Options menu, as a result they added a return button
#ifdef VERSION_EU
        case MENU_BUTTON_LANGUAGE_RETURN:
            return_to_main_menu(MENU_BUTTON_SOUND_MODE, sMainMenuButtons[MENU_BUTTON_LANGUAGE_RETURN]);
            break;
#else
        case MENU_BUTTON_STEREO:
            return_to_main_menu(MENU_BUTTON_SOUND_MODE, sMainMenuButtons[MENU_BUTTON_STEREO]);
            break;
        case MENU_BUTTON_MONO:
            return_to_main_menu(MENU_BUTTON_SOUND_MODE, sMainMenuButtons[MENU_BUTTON_MONO]);
            break;
        case MENU_BUTTON_HEADSET:
            return_to_main_menu(MENU_BUTTON_SOUND_MODE, sMainMenuButtons[MENU_BUTTON_HEADSET]);
            break;
#endif
    }

    sClickPos[0] = -10000;
    sClickPos[1] = -10000;
}

/**
 * Cursor function that handles button inputs.
 * If the cursor is clicked, sClickPos uses the same value as sCursorPos.
 */
void handle_cursor_button_input(void) {
    // If scoring a file, pressing A just changes the coin score mode.
    if (sSelectedButtonID == MENU_BUTTON_SCORE_FILE_A || sSelectedButtonID == MENU_BUTTON_SCORE_FILE_B
        || sSelectedButtonID == MENU_BUTTON_SCORE_FILE_C
        || sSelectedButtonID == MENU_BUTTON_SCORE_FILE_D) {
        if (gPlayer3Controller->buttonPressed
#ifdef VERSION_EU
            & (B_BUTTON | START_BUTTON | Z_TRIG)) {
#else
            & (B_BUTTON | START_BUTTON)) {
#endif
            sClickPos[0] = sCursorPos[0];
            sClickPos[1] = sCursorPos[1];
            sCursorClickingTimer = 1;
        } else if (gPlayer3Controller->buttonPressed & A_BUTTON) {
            sScoreFileCoinScoreMode = 1 - sScoreFileCoinScoreMode;
            play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
        }
    } else { // If cursor is clicked
        if (gPlayer3Controller->buttonPressed
#ifdef VERSION_EU
            & (A_BUTTON | B_BUTTON | START_BUTTON | Z_TRIG)) {
#else
            & (A_BUTTON | B_BUTTON | START_BUTTON)) {
#endif
            sClickPos[0] = sCursorPos[0];
            sClickPos[1] = sCursorPos[1];
            sCursorClickingTimer = 1;
        }
    }
}

/**
 * Cursor function that handles analog stick input and button presses with a function near the end.
 */
void handle_controller_cursor_input(void) {
    s16 rawStickX = gPlayer3Controller->rawStickX;
    s16 rawStickY = gPlayer3Controller->rawStickY;

    // Handle deadzone
    if (rawStickY > -2 && rawStickY < 2) {
        rawStickY = 0;
    }
    if (rawStickX > -2 && rawStickX < 2) {
        rawStickX = 0;
    }

    // Move cursor
    sCursorPos[0] += rawStickX / 8;
    sCursorPos[1] += rawStickY / 8;

    // Stop cursor from going offscreen
    if (sCursorPos[0] > 132.0f) {
        sCursorPos[0] = 132.0f;
    }
    if (sCursorPos[0] < -132.0f) {
        sCursorPos[0] = -132.0f;
    }

    if (sCursorPos[1] > 90.0f) {
        sCursorPos[1] = 90.0f;
    }
    if (sCursorPos[1] < -90.0f) {
        sCursorPos[1] = -90.0f;
    }

    if (sCursorClickingTimer == 0) {
        handle_cursor_button_input();
    }
}

/**
 * Prints the cursor (Mario Hand, different to the one in the Mario screen)
 * and loads it's controller inputs in handle_controller_cursor_input
 * to be usable on the file select.
 */
void print_menu_cursor(void) {
    handle_controller_cursor_input();
    create_dl_translation_matrix(MENU_MTX_PUSH, sCursorPos[0] + 160.0f - 5.0, sCursorPos[1] + 120.0f - 25.0, 0.0f);
    // Get the right graphic to use for the cursor.
    if (sCursorClickingTimer == 0)
        // Idle
        gSPDisplayList(gDisplayListHead++, dl_menu_idle_hand);
    if (sCursorClickingTimer != 0)
        // Grabbing
        gSPDisplayList(gDisplayListHead++, dl_menu_grabbing_hand);
    gSPPopMatrix(gDisplayListHead++, G_MTX_MODELVIEW);
    if (sCursorClickingTimer != 0) {
        sCursorClickingTimer++; // This is a very strange way to implement a timer? It counts up and
                                // then resets to 0 instead of just counting down to 0.
        if (sCursorClickingTimer == 5) {
            sCursorClickingTimer = 0;
        }
    }
}

/**
 * Prints a hud string depending of the hud table list defined with text fade properties.
 */
void print_hud_lut_string_fade(s8 hudLUT, s16 x, s16 y, const unsigned char *text) {
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha - sTextFadeAlpha);
    print_hud_lut_string(hudLUT, x, y, text);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);
}

/**
 * Prints a generic white string with text fade properties.
 */
void print_generic_string_fade(s16 x, s16 y, const unsigned char *text) {
    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha - sTextFadeAlpha);
    print_generic_string(x, y, text);
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);
}

/**
 * Updates text fade at the top of a menu.
 */
s32 update_text_fade_out(void) {
    if (sFadeOutText == TRUE) {
        sTextFadeAlpha += 50;
        if (sTextFadeAlpha == 250) {
            sFadeOutText = FALSE;
            return TRUE;
        }
    } else {
        if (sTextFadeAlpha > 0) {
            sTextFadeAlpha -= 50;
        }
    }
    return FALSE;
}

/**
 * Prints the amount of stars of a save file.
 * If a save doesn't exist, print "NEW" instead.
 */
void print_save_file_star_count(s8 fileIndex, s16 x, s16 y) {
    u8 starCountText[4];
    s8 offset = 0;
    s16 starCount;

    if (save_file_exists(fileIndex) == TRUE) {
        starCount = save_file_get_total_star_count(fileIndex, 0, 24);
        // Print star icon
        print_hud_lut_string(HUD_LUT_GLOBAL, x, y, starIcon);
        // If star count is less than 100, print x icon and move
        // the star count text one digit to the right.
        if (starCount < 100) {
            print_hud_lut_string(HUD_LUT_GLOBAL, x + 16, y, xIcon);
            offset = 16;
        }
        // Print star count
        int_to_str(starCount, starCountText);
        print_hud_lut_string(HUD_LUT_GLOBAL, x + offset + 16, y, starCountText);
    } else {
        // Print "new" text
        print_hud_lut_string(HUD_LUT_GLOBAL, x, y, LANGUAGE_ARRAY(textNew));
    }
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define SELECT_FILE_X 96
    #define SCORE_X 50
    #define COPY_X 115
    #define ERASE_X 180
    #define SOUNDMODE_X1 235
    #define SAVEFILE_X1 92
    #define SAVEFILE_X2 209
    #define MARIOTEXT_X1 92
    #define MARIOTEXT_X2 207
#elif VERSION_US
    #define SELECT_FILE_X 93
    #define SCORE_X 52
    #define COPY_X 117
    #define ERASE_X 177
    #define SOUNDMODE_X1 sSoundTextX
    #define SAVEFILE_X1 92
    #define SAVEFILE_X2 209
    #define MARIOTEXT_X1 92
    #define MARIOTEXT_X2 207
#elif VERSION_EU
    #define SELECT_FILE_X 93
    #define SCORE_X 52
    #define COPY_X 117
    #define ERASE_X 177
    #define SOUNDMODE_X1 sSoundTextX
    #define SAVEFILE_X1 97
    #define SAVEFILE_X2 204
    #define MARIOTEXT_X1 97
    #define MARIOTEXT_X2 204
#endif

/**
 * Prints main menu strings that shows on the yellow background menu screen.
 *
 * In EU this function acts like "print_save_file_strings" because
 * print_main_lang_strings is first called to render the strings for the 4 buttons.
 * Same rule applies for score, copy and erase strings.
 */
void print_main_menu_strings(void) {
    // Print "SELECT FILE" text
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
#ifndef VERSION_EU
    print_hud_lut_string(HUD_LUT_DIFF, SELECT_FILE_X, 35, textSelectFile);
#endif
    // Print file star counts
    print_save_file_star_count(SAVE_FILE_A, SAVEFILE_X1, 78);
    print_save_file_star_count(SAVE_FILE_B, SAVEFILE_X2, 78);
    print_save_file_star_count(SAVE_FILE_C, SAVEFILE_X1, 118);
    print_save_file_star_count(SAVE_FILE_D, SAVEFILE_X2, 118);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);
#ifndef VERSION_EU
   // Print menu names
    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_generic_string(SCORE_X, 39, textScore);
    print_generic_string(COPY_X, 39, textCopy);
    print_generic_string(ERASE_X, 39, textErase);
#if !defined(VERSION_JP) && !defined(VERSION_SH)
    sSoundTextX = get_str_x_pos_from_center(254, textSoundModes[sSoundMode], 10.0f);
#endif
    print_generic_string(SOUNDMODE_X1, 39, textSoundModes[sSoundMode]);
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);
#endif
    // Print file names
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_menu_generic_string(MARIOTEXT_X1, 65, textMarioA);
    print_menu_generic_string(MARIOTEXT_X2, 65, textMarioB);
    print_menu_generic_string(MARIOTEXT_X1, 105, textMarioC);
    print_menu_generic_string(MARIOTEXT_X2, 105, textMarioD);
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_end);
}

#ifdef VERSION_EU
/**
 * Prints the first part main menu strings that shows on the yellow background menu screen.
 * Has the strings for the 4 buttons below the save buttons that get changed depending of the language.
 * Calls print_main_menu_strings to print the remaining strings.
 */
void print_main_lang_strings(void) {
    s16 centeredX;

    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    centeredX = get_str_x_pos_from_center_scale(160, textSelectFile[sLanguageMode], 12.0f);
    sCenteredX = centeredX;
    print_hud_lut_string(HUD_LUT_GLOBAL, centeredX, 35, textSelectFile[sLanguageMode]);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);

    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    centeredX = get_str_x_pos_from_center(76, textScore[sLanguageMode], 10.0f);
    sCenteredX = centeredX;
    print_generic_string(centeredX, 39, textScore[sLanguageMode]);
    centeredX = get_str_x_pos_from_center(131, textCopy[sLanguageMode], 10.0f);
    sCenteredX = centeredX;
    print_generic_string(centeredX, 39, textCopy[sLanguageMode]);
    centeredX = get_str_x_pos_from_center(189, textErase[sLanguageMode], 10.0f);
    sCenteredX = centeredX;
    print_generic_string(centeredX, 39, textErase[sLanguageMode]);
    centeredX = get_str_x_pos_from_center(245, textOption[sLanguageMode], 10.0f);
    sCenteredX = centeredX;
    print_generic_string(centeredX, 39, textOption[sLanguageMode]);
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);

    print_main_menu_strings();
}
#endif

#ifdef VERSION_EU
#define CHECK_FILE_X checkFileX
#define NOSAVE_DATA_X1 noSaveDataX
#elif VERSION_JP
#define CHECK_FILE_X 90
#define NOSAVE_DATA_X1 90
#else
#define CHECK_FILE_X 95
#define NOSAVE_DATA_X1 99
#endif

/**
 * Defines IDs for the top message of the score menu and displays it if the ID is called in messageID.
 */
void score_menu_display_message(s8 messageID) {
#ifdef VERSION_EU
    s16 checkFileX, noSaveDataX;
#endif

    switch (messageID) {
        case SCORE_MSG_CHECK_FILE:
#ifdef VERSION_EU
            checkFileX = get_str_x_pos_from_center_scale(160, LANGUAGE_ARRAY(textCheckFile), 12.0f);
#endif
            print_hud_lut_string_fade(HUD_LUT_DIFF, CHECK_FILE_X, 35, LANGUAGE_ARRAY(textCheckFile));
            break;
        case SCORE_MSG_NOSAVE_DATA:
#ifdef VERSION_EU
            noSaveDataX = get_str_x_pos_from_center(160, LANGUAGE_ARRAY(textNoSavedDataExists), 10.0f);
#endif
            print_generic_string_fade(NOSAVE_DATA_X1, 190, LANGUAGE_ARRAY(textNoSavedDataExists));
            break;
    }
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define RETURN_X     45
    #define COPYFILE_X1  128
    #define ERASEFILE_X1 228
#elif VERSION_EU
    #define RETURN_X     centeredX
    #define COPYFILE_X1  centeredX
    #define ERASEFILE_X1 centeredX
#else
    #define RETURN_X     44
    #define COPYFILE_X1  135
    #define ERASEFILE_X1 231
#endif

#ifdef VERSION_EU
    #define FADEOUT_TIMER 35
#else
    #define FADEOUT_TIMER 20
#endif

/**
 * Prints score menu strings that shows on the green background menu screen.
 */
void print_score_menu_strings(void) {
#ifdef VERSION_EU
    s16 centeredX;
#endif

    // Update and print the message at the top of the menu.
    if (sMainMenuTimer == FADEOUT_TIMER) {
        sFadeOutText = TRUE;
    }
    if (update_text_fade_out() == TRUE) {
        if (sStatusMessageID == SCORE_MSG_CHECK_FILE) {
            sStatusMessageID = SCORE_MSG_NOSAVE_DATA;
        } else {
            sStatusMessageID = SCORE_MSG_CHECK_FILE;
        }
    }
    // Print messageID called above
    score_menu_display_message(sStatusMessageID);

#ifndef VERSION_EU
    // Print file star counts
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_save_file_star_count(SAVE_FILE_A, 90, 76);
    print_save_file_star_count(SAVE_FILE_B, 211, 76);
    print_save_file_star_count(SAVE_FILE_C, 90, 119);
    print_save_file_star_count(SAVE_FILE_D, 211, 119);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);
#endif

    // Print menu names
    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(69, textReturn[sLanguageMode], 10.0f);
#endif
    print_generic_string(RETURN_X, 35, LANGUAGE_ARRAY(textReturn));
#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(159, textCopyFileButton[sLanguageMode], 10.0f);
#endif
    print_generic_string(COPYFILE_X1, 35, LANGUAGE_ARRAY(textCopyFileButton));
#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(249, textEraseFileButton[sLanguageMode], 10.0f);
#endif
    print_generic_string(ERASEFILE_X1, 35, LANGUAGE_ARRAY(textEraseFileButton));
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);

#ifdef VERSION_EU
    print_main_menu_strings();
#else
    // Print file names
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_menu_generic_string(89, 62, textMarioA);
    print_menu_generic_string(211, 62, textMarioB);
    print_menu_generic_string(89, 105, textMarioC);
    print_menu_generic_string(211, 105, textMarioD);
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_end);
#endif
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define NOFILE_COPY_X  90
    #define COPY_FILE_X    90
    #define COPYIT_WHERE_X 90
    #define NOSAVE_DATA_X2 90
    #define COPYCOMPLETE_X 90
    #define SAVE_EXISTS_X1 90
#elif VERSION_EU
    #define NOFILE_COPY_X  centeredX
    #define COPY_FILE_X    centeredX
    #define COPYIT_WHERE_X centeredX
    #define NOSAVE_DATA_X2 centeredX
    #define COPYCOMPLETE_X centeredX
    #define SAVE_EXISTS_X1 centeredX
#else
    #define NOFILE_COPY_X  119
    #define COPY_FILE_X    104
    #define COPYIT_WHERE_X 109
    #define NOSAVE_DATA_X2 101
    #define COPYCOMPLETE_X 110
    #define SAVE_EXISTS_X1 110
#endif

/**
 * Defines IDs for the top message of the copy menu and displays it if the ID is called in messageID.
 */
void copy_menu_display_message(s8 messageID) {
#ifdef VERSION_EU
    s16 centeredX;
#endif

    switch (messageID) {
        case COPY_MSG_MAIN_TEXT:
            if (sAllFilesExist == TRUE) {
#ifdef VERSION_EU
                centeredX = get_str_x_pos_from_center(160, textNoFileToCopyFrom[sLanguageMode], 10.0f);
#endif
                print_generic_string_fade(NOFILE_COPY_X, 190, LANGUAGE_ARRAY(textNoFileToCopyFrom));
            } else {
#ifdef VERSION_EU
                centeredX = get_str_x_pos_from_center_scale(160, textCopyFile[sLanguageMode], 12.0f);
#endif
                print_hud_lut_string_fade(HUD_LUT_DIFF, COPY_FILE_X, 35, LANGUAGE_ARRAY(textCopyFile));
            }
            break;
        case COPY_MSG_COPY_WHERE:
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textCopyItToWhere[sLanguageMode], 10.0f);
#endif
            print_generic_string_fade(COPYIT_WHERE_X, 190, LANGUAGE_ARRAY(textCopyItToWhere));
            break;
        case COPY_MSG_NOSAVE_EXISTS:
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textNoSavedDataExists[sLanguageMode], 10.0f);
            print_generic_string_fade(NOSAVE_DATA_X2, 190, textNoSavedDataExists[sLanguageMode]);
#else
            print_generic_string_fade(NOSAVE_DATA_X2, 190, textNoSavedDataExistsCopy);
#endif
            break;
        case COPY_MSG_COPY_COMPLETE:
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textCopyCompleted[sLanguageMode], 10.0f);
#endif
            print_generic_string_fade(COPYCOMPLETE_X, 190, LANGUAGE_ARRAY(textCopyCompleted));
            break;
        case COPY_MSG_SAVE_EXISTS:
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textSavedDataExists[sLanguageMode], 10.0f);
#endif
            print_generic_string_fade(SAVE_EXISTS_X1, 190, LANGUAGE_ARRAY(textSavedDataExists));
            break;
    }
}

/**
 * Updates messageIDs of the copy menu depending of the copy phase value defined.
 */
void copy_menu_update_message(void) {
    switch (sMainMenuButtons[MENU_BUTTON_COPY]->oMenuButtonActionPhase) {
        case COPY_PHASE_MAIN:
            if (sMainMenuTimer == FADEOUT_TIMER) {
                sFadeOutText = TRUE;
            }
            if (update_text_fade_out() == TRUE) {
                if (sStatusMessageID == COPY_MSG_MAIN_TEXT) {
                    sStatusMessageID = COPY_MSG_NOSAVE_EXISTS;
                } else {
                    sStatusMessageID = COPY_MSG_MAIN_TEXT;
                }
            }
            break;
        case COPY_PHASE_COPY_WHERE:
            if (sMainMenuTimer == FADEOUT_TIMER
                && sStatusMessageID == COPY_MSG_SAVE_EXISTS) {
                sFadeOutText = TRUE;
            }
            if (update_text_fade_out() == TRUE) {
                if (sStatusMessageID != COPY_MSG_COPY_WHERE) {
                    sStatusMessageID = COPY_MSG_COPY_WHERE;
                } else {
                    sStatusMessageID = COPY_MSG_SAVE_EXISTS;
                }
            }
            break;
        case COPY_PHASE_COPY_COMPLETE:
            if (sMainMenuTimer == FADEOUT_TIMER) {
                sFadeOutText = TRUE;
            }
            if (update_text_fade_out() == TRUE) {
                if (sStatusMessageID != COPY_MSG_COPY_COMPLETE) {
                    sStatusMessageID = COPY_MSG_COPY_COMPLETE;
                } else {
                    sStatusMessageID = COPY_MSG_MAIN_TEXT;
                }
            }
            break;
    }
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define VIEWSCORE_X1 133
    #define ERASEFILE_X2 220
#elif VERSION_EU
    #define VIEWSCORE_X1 centeredX
    #define ERASEFILE_X2 centeredX
#else
    #define VIEWSCORE_X1 128
    #define ERASEFILE_X2 230
#endif

/**
 * Prints copy menu strings that shows on the blue background menu screen.
 */
void print_copy_menu_strings(void) {
#ifdef VERSION_EU
    s16 centeredX;
#endif

    // Update and print the message at the top of the menu.
    copy_menu_update_message();
    // Print messageID called inside a copy_menu_update_message case
    copy_menu_display_message(sStatusMessageID);
#ifndef VERSION_EU
    // Print file star counts
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_save_file_star_count(SAVE_FILE_A, 90, 76);
    print_save_file_star_count(SAVE_FILE_B, 211, 76);
    print_save_file_star_count(SAVE_FILE_C, 90, 119);
    print_save_file_star_count(SAVE_FILE_D, 211, 119);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);
#endif
    // Print menu names
    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(69, textReturn[sLanguageMode], 10.0f);
#endif
    print_generic_string(RETURN_X, 35, LANGUAGE_ARRAY(textReturn));
#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(159, textViewScore[sLanguageMode], 10.0f);
#endif
    print_generic_string(VIEWSCORE_X1, 35, LANGUAGE_ARRAY(textViewScore));
#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(249, textEraseFileButton[sLanguageMode], 10.0f);
#endif
    print_generic_string(ERASEFILE_X2, 35, LANGUAGE_ARRAY(textEraseFileButton));
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);
#ifdef VERSION_EU
    print_main_menu_strings();
#else
    // Print file names
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_menu_generic_string(89, 62, textMarioA);
    print_menu_generic_string(211, 62, textMarioB);
    print_menu_generic_string(89, 105, textMarioC);
    print_menu_generic_string(211, 105, textMarioD);
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_end);
#endif
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define CURSOR_X 160.0f
    #define MENU_ERASE_YES_MIN_X 0x91
    #define MENU_ERASE_YES_MAX_X 0xA4
#else
    #define CURSOR_X (x + 0x46)
    #define MENU_ERASE_YES_MIN_X 0x8C
    #define MENU_ERASE_YES_MAX_X 0xA9
#endif

#define MENU_ERASE_YES_NO_MIN_Y 0xBF
#define MENU_ERASE_YES_NO_MAX_Y 0xD2
#define MENU_ERASE_NO_MIN_X 0xBD
#define MENU_ERASE_NO_MAX_X 0xDA

/**
 * Prints the "YES NO" prompt and checks if one of the prompts are hovered to do it's functions.
 */
void print_erase_menu_prompt(s16 x, s16 y) {
    s16 colorFade = gGlobalTimer << 12;

    s16 cursorX = sCursorPos[0] + CURSOR_X;
    s16 cursorY = sCursorPos[1] + 120.0f;

    if (cursorX < MENU_ERASE_YES_MAX_X && cursorX >= MENU_ERASE_YES_MIN_X &&
        cursorY < MENU_ERASE_YES_NO_MAX_Y && cursorY >= MENU_ERASE_YES_NO_MIN_Y) {
        // Fade "YES" string color but keep "NO" gray
        sYesNoColor[0] = sins(colorFade) * 50.0f + 205.0f;
        sYesNoColor[1] = 150;
        sEraseYesNoHoverState = MENU_ERASE_HOVER_YES;
    } else if (cursorX < MENU_ERASE_NO_MAX_X && cursorX >= MENU_ERASE_NO_MIN_X
        && cursorY < MENU_ERASE_YES_NO_MAX_Y && cursorY >= MENU_ERASE_YES_NO_MIN_Y) {
        // Fade "NO" string color but keep "YES" gray
        sYesNoColor[0] = 150;
        sYesNoColor[1] = sins(colorFade) * 50.0f + 205.0f;
        sEraseYesNoHoverState = MENU_ERASE_HOVER_NO;
    } else {
        // Don't fade both strings and keep them gray
        sYesNoColor[0] = 150;
        sYesNoColor[1] = 150;
        sEraseYesNoHoverState = MENU_ERASE_HOVER_NONE;
    }
    // If the cursor is clicked...
    if (sCursorClickingTimer == 2) {
        // ..and is hovering "YES", delete file
        if (sEraseYesNoHoverState == MENU_ERASE_HOVER_YES) {
            play_sound(SOUND_MARIO_WAAAOOOW, gDefaultSoundArgs);
            sMainMenuButtons[MENU_BUTTON_ERASE]->oMenuButtonActionPhase = ERASE_PHASE_MARIO_ERASED;
            sFadeOutText = TRUE;
            sMainMenuTimer = 0;
            save_file_erase(sSelectedFileIndex);
            sMainMenuButtons[MENU_BUTTON_ERASE_MIN + sSelectedFileIndex]->header.gfx.sharedChild =
                gLoadedGraphNodes[MODEL_MAIN_MENU_MARIO_NEW_BUTTON_FADE];
            sMainMenuButtons[sSelectedFileIndex]->header.gfx.sharedChild =
                gLoadedGraphNodes[MODEL_MAIN_MENU_MARIO_NEW_BUTTON_FADE];
            sEraseYesNoHoverState = MENU_ERASE_HOVER_NONE;
            // ..and is hovering "NO", return back to main phase
        } else if (sEraseYesNoHoverState == MENU_ERASE_HOVER_NO) {
            play_sound(SOUND_MENU_CLICK_FILE_SELECT, gDefaultSoundArgs);
            sMainMenuButtons[MENU_BUTTON_ERASE_MIN + sSelectedFileIndex]->oMenuButtonState =
                MENU_BUTTON_STATE_ZOOM_OUT;
            sMainMenuButtons[MENU_BUTTON_ERASE]->oMenuButtonActionPhase = ERASE_PHASE_MAIN;
            sFadeOutText = TRUE;
            sMainMenuTimer = 0;
            sEraseYesNoHoverState = MENU_ERASE_HOVER_NONE;
        }
    }

    // Print "YES NO" strings
    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, sYesNoColor[0], sYesNoColor[0], sYesNoColor[0], sTextBaseAlpha);
    print_generic_string(x + 56, y, LANGUAGE_ARRAY(textYes));
    gDPSetEnvColor(gDisplayListHead++, sYesNoColor[1], sYesNoColor[1], sYesNoColor[1], sTextBaseAlpha);
    print_generic_string(x + 98, y, LANGUAGE_ARRAY(textNo));
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);
}

// MARIO_ERASED_VAR is the value there the letter "A" is, it works like this:
//   US and EU   ---    JP
// M a r i o   A --- マ リ オ Ａ
// 0 1 2 3 4 5 6 --- 0 1 2 3
#if defined(VERSION_JP) || defined(VERSION_SH)
    #define ERASE_FILE_X     96
    #define NOSAVE_DATA_X3   90
    #define MARIO_ERASED_VAR 3
    #define MARIO_ERASED_X   90
    #define SAVE_EXISTS_X2   90
#elif VERSION_EU
    #define ERASE_FILE_X     centeredX
    #define NOSAVE_DATA_X3   centeredX
    #define MARIO_ERASED_VAR 6
    #define MARIO_ERASED_X   centeredX
    #define SAVE_EXISTS_X2   centeredX
#else
    #define ERASE_FILE_X     98
    #define NOSAVE_DATA_X3   100
    #define MARIO_ERASED_VAR 6
    #define MARIO_ERASED_X   100
    #define SAVE_EXISTS_X2   100
#endif

/**
 * Defines IDs for the top message of the erase menu and displays it if the ID is called in messageID.
 */
void erase_menu_display_message(s8 messageID) {
#ifdef VERSION_EU
    s16 centeredX;
#endif

#ifndef VERSION_EU
    unsigned char textEraseFile[] = { TEXT_ERASE_FILE };
    unsigned char textSure[] = { TEXT_SURE };
    unsigned char textNoSavedDataExists[] = { TEXT_NO_SAVED_DATA_EXISTS };
    unsigned char textMarioAJustErased[] = { TEXT_FILE_MARIO_A_JUST_ERASED };
    unsigned char textSavedDataExists[] = { TEXT_SAVED_DATA_EXISTS };
#endif

    switch (messageID) {
        case ERASE_MSG_MAIN_TEXT:
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center_scale(160, textEraseFile[sLanguageMode], 12.0f);
#endif
            print_hud_lut_string_fade(HUD_LUT_DIFF, ERASE_FILE_X, 35, LANGUAGE_ARRAY(textEraseFile));
            break;
        case ERASE_MSG_PROMPT:
            print_generic_string_fade(90, 190, LANGUAGE_ARRAY(textSure));
            print_erase_menu_prompt(90, 190); // YES NO, has functions for it too
            break;
        case ERASE_MSG_NOSAVE_EXISTS:
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textNoSavedDataExists[sLanguageMode], 10.0f);
#endif
            print_generic_string_fade(NOSAVE_DATA_X3, 190, LANGUAGE_ARRAY(textNoSavedDataExists));
            break;
        case ERASE_MSG_MARIO_ERASED:
            LANGUAGE_ARRAY(textMarioAJustErased)[MARIO_ERASED_VAR] = sSelectedFileIndex + 10;
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textMarioAJustErased[sLanguageMode], 10.0f);
#endif
            print_generic_string_fade(MARIO_ERASED_X, 190, LANGUAGE_ARRAY(textMarioAJustErased));
            break;
        case ERASE_MSG_SAVE_EXISTS: // unused
#ifdef VERSION_EU
            centeredX = get_str_x_pos_from_center(160, textSavedDataExists[sLanguageMode], 10.0f);
#endif
            print_generic_string_fade(SAVE_EXISTS_X2, 190, LANGUAGE_ARRAY(textSavedDataExists));
            break;
    }
}

/**
 * Updates messageIDs of the erase menu depending of the erase phase value defined.
 */
void erase_menu_update_message(void) {
    switch (sMainMenuButtons[MENU_BUTTON_ERASE]->oMenuButtonActionPhase) {
        case ERASE_PHASE_MAIN:
            if (sMainMenuTimer == FADEOUT_TIMER
                && sStatusMessageID == ERASE_MSG_NOSAVE_EXISTS) {
                sFadeOutText = TRUE;
            }
            if (update_text_fade_out() == TRUE) {
                if (sStatusMessageID == ERASE_MSG_MAIN_TEXT) {
                    sStatusMessageID = ERASE_MSG_NOSAVE_EXISTS;
                } else {
                    sStatusMessageID = ERASE_MSG_MAIN_TEXT;
                }
            }
            break;
        case ERASE_PHASE_PROMPT:
            if (update_text_fade_out() == TRUE) {
                if (sStatusMessageID != ERASE_MSG_PROMPT) {
                    sStatusMessageID = ERASE_MSG_PROMPT;
                }
                sCursorPos[0] = 43.0f;
                sCursorPos[1] = 80.0f;
            }
            break;
        case ERASE_PHASE_MARIO_ERASED:
            if (sMainMenuTimer == FADEOUT_TIMER) {
                sFadeOutText = TRUE;
            }
            if (update_text_fade_out() == TRUE) {
                if (sStatusMessageID != ERASE_MSG_MARIO_ERASED) {
                    sStatusMessageID = ERASE_MSG_MARIO_ERASED;
                } else {
                    sStatusMessageID = ERASE_MSG_MAIN_TEXT;
                }
            }
            break;
    }
}

#if defined(VERSION_JP) || defined(VERSION_SH)
#define VIEWSCORE_X2 133
#define COPYFILE_X2 223
#else
#define VIEWSCORE_X2 127
#define COPYFILE_X2 233
#endif

/**
 * Prints erase menu strings that shows on the red background menu screen.
 */
void print_erase_menu_strings(void) {
#ifdef VERSION_EU
    s16 centeredX;
#endif

    // Update and print the message at the top of the menu.
    erase_menu_update_message();

    // Print messageID called inside a erase_menu_update_message case
    erase_menu_display_message(sStatusMessageID);

#ifndef VERSION_EU
    // Print file star counts
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_save_file_star_count(SAVE_FILE_A, 90, 76);
    print_save_file_star_count(SAVE_FILE_B, 211, 76);
    print_save_file_star_count(SAVE_FILE_C, 90, 119);
    print_save_file_star_count(SAVE_FILE_D, 211, 119);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);
#endif

    // Print menu names
    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);

#ifdef VERSION_EU
    centeredX = get_str_x_pos_from_center(69, textReturn[sLanguageMode], 10.0f);
    print_generic_string(centeredX, 35, textReturn[sLanguageMode]);
    centeredX = get_str_x_pos_from_center(159, textViewScore[sLanguageMode], 10.0f);
    print_generic_string(centeredX, 35, textViewScore[sLanguageMode]);
    centeredX = get_str_x_pos_from_center(249, textCopyFileButton[sLanguageMode], 10.0f);
    print_generic_string(centeredX, 35, textCopyFileButton[sLanguageMode]);
#else
    print_generic_string(RETURN_X, 35, textReturn);
    print_generic_string(VIEWSCORE_X2, 35, textViewScore);
    print_generic_string(COPYFILE_X2, 35, textCopyFileButton);
#endif
    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);

#ifdef VERSION_EU
    print_main_menu_strings();
#else
    // Print file names
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_menu_generic_string(89, 62, textMarioA);
    print_menu_generic_string(211, 62, textMarioB);
    print_menu_generic_string(89, 105, textMarioC);
    print_menu_generic_string(211, 105, textMarioD);
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_end);
#endif
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define SOUND_HUD_X 96
#elif VERSION_US
    #define SOUND_HUD_X 88
#endif

/**
 * Prints sound mode menu strings that shows on the purple background menu screen.
 *
 * In EU, this function acts like "print_option_mode_menu_strings" because of languages.
 */
void print_sound_mode_menu_strings(void) {
    s32 mode;

#ifdef VERSION_US
    s16 textX;
#elif VERSION_EU
    s32 textX;
#endif

#ifndef VERSION_EU
    unsigned char textSoundSelect[] = { TEXT_SOUND_SELECT };
#endif

    // Print "SOUND SELECT" text
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);

#ifdef VERSION_EU
    print_hud_lut_string(HUD_LUT_DIFF, 47, 32, textSoundSelect[sLanguageMode]);
    print_hud_lut_string(HUD_LUT_DIFF, 47, 101, textLanguageSelect[sLanguageMode]);
#else
    print_hud_lut_string(HUD_LUT_DIFF, SOUND_HUD_X, 35, textSoundSelect);
#endif

    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);

    gSPDisplayList(gDisplayListHead++, dl_ia_text_begin);

#ifdef VERSION_EU // In EU their X position get increased each string
    // Print sound mode names
    for (mode = 0, textX = 90; mode < 3; textX += 70, mode++) {
        if (mode == sSoundMode) {
            gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
        } else {
            gDPSetEnvColor(gDisplayListHead++, 0, 0, 0, sTextBaseAlpha);
        }
        print_generic_string(
            get_str_x_pos_from_center(textX, textSoundModes[sLanguageMode * 3 + mode], 10.0f),
            141, textSoundModes[sLanguageMode * 3 + mode]);
    }

    // In EU, print language mode names
    for (mode = 0, textX = 90; mode < 3; textX += 70, mode++) {
        if (mode == sLanguageMode) {
            gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
        } else {
            gDPSetEnvColor(gDisplayListHead++, 0, 0, 0, sTextBaseAlpha);
        }
        print_generic_string(
            get_str_x_pos_from_center(textX, textLanguage[mode], 10.0f),
            72, textLanguage[mode]);
    }
#else
    // Print sound mode names
    for (mode = 0; mode < 3; mode++) {
        if (mode == sSoundMode) {
            gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
        } else {
            gDPSetEnvColor(gDisplayListHead++, 0, 0, 0, sTextBaseAlpha);
        }
        #ifdef VERSION_US
            // Mode names are centered correctly on US
            textX = get_str_x_pos_from_center(mode * 74 + 87, textSoundModes[mode], 10.0f);
            print_generic_string(textX, 87, textSoundModes[mode]);
        #elif VERSION_JP
            print_generic_string(mode * 74 + 67, 87, textSoundModes[mode]);
        #endif
    }
#endif

#ifdef VERSION_EU
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_generic_string(182, 29, textReturn[sLanguageMode]);
#endif

    gSPDisplayList(gDisplayListHead++, dl_ia_text_end);
}


unsigned char textStarX[] = { TEXT_STAR_X };

/**
 * Prints castle secret stars collected in a score menu save file.
 */
void print_score_file_castle_secret_stars(s8 fileIndex, s16 x, s16 y) {
    unsigned char secretStarsText[20];
    // Print "[star] x"
    print_menu_generic_string(x, y, textStarX);
    // Print number of castle secret stars
    int_to_str(save_file_get_total_star_count(fileIndex, 15, 24), secretStarsText);
#ifdef VERSION_EU
    print_menu_generic_string(x + 20, y, secretStarsText);
#else
    print_menu_generic_string(x + 16, y, secretStarsText);
#endif
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define HISCORE_COIN_ICON_X  0
    #define HISCORE_COIN_TEXT_X  16
    #define HISCORE_COIN_NAMES_X 45
#else
    #define HISCORE_COIN_ICON_X  18
    #define HISCORE_COIN_TEXT_X  34
    #define HISCORE_COIN_NAMES_X 60
#endif

/**
 * Prints course coins collected in a score menu save file.
 */
void print_score_file_course_coin_score(s8 fileIndex, s16 courseIndex, s16 x, s16 y) {
    unsigned char coinScoreText[20];
    u8 stars = save_file_get_star_flags(fileIndex, courseIndex);
    unsigned char textCoinX[] = { TEXT_COIN_X };
    unsigned char textStar[] = { TEXT_STAR };
#if defined(VERSION_JP) || defined(VERSION_SH)
    #define LENGTH 5
#else
    #define LENGTH 8
#endif
    unsigned char fileNames[][LENGTH] = {
        { TEXT_4DASHES }, // huh?
        { TEXT_SCORE_MARIO_A }, { TEXT_SCORE_MARIO_B }, { TEXT_SCORE_MARIO_C }, { TEXT_SCORE_MARIO_D },
    };
#undef LENGTH
    // MYSCORE
    if (sScoreFileCoinScoreMode == 0) {
        // Print "[coin] x"
        print_menu_generic_string(x + 25, y, textCoinX);
        // Print coin score
        int_to_str(save_file_get_course_coin_score(fileIndex, courseIndex), coinScoreText);
        print_menu_generic_string(x + 41, y, coinScoreText);
        // If collected, print 100 coin star
        if (stars & (1 << 6)) {
            print_menu_generic_string(x + 70, y, textStar);
        }
    }
    // HISCORE
    else {
        // Print "[coin] x"
        print_menu_generic_string(x + HISCORE_COIN_ICON_X, y, textCoinX);
        // Print coin highscore
        int_to_str((u16) save_file_get_max_coin_score(courseIndex) & 0xFFFF, coinScoreText);
        print_menu_generic_string(x + HISCORE_COIN_TEXT_X, y, coinScoreText);
        // Print coin highscore file
        print_menu_generic_string(x + HISCORE_COIN_NAMES_X, y,
                         fileNames[(save_file_get_max_coin_score(courseIndex) >> 16) & 0xFFFF]);
    }
}

/**
 * Prints stars collected in a score menu save file.
 */
void print_score_file_star_score(s8 fileIndex, s16 courseIndex, s16 x, s16 y) {
    s16 i = 0;
    unsigned char starScoreText[19];
    u8 stars = save_file_get_star_flags(fileIndex, courseIndex);
    s8 starCount = save_file_get_course_star_count(fileIndex, courseIndex);
    // Don't count 100 coin star
    if (stars & (1 << 6)) {
        starCount--;
    }
    // Add 1 star character for every star collected
    for (i = 0; i < starCount; i++) {
        starScoreText[i] = DIALOG_CHAR_STAR_FILLED;
    }
    // Terminating byte
    starScoreText[i] = DIALOG_CHAR_TERMINATOR;
    print_menu_generic_string(x, y, starScoreText);
}

#if defined(VERSION_JP) || defined(VERSION_SH)
    #define MARIO_X 28
    #define FILE_LETTER_X 86
    #define LEVEL_NAME_X 23
    #define SECRET_STARS_X 152
    #define MYSCORE_X 237
    #define HISCORE_X 237
#else
    #define MARIO_X 25
    #define FILE_LETTER_X 95
    #define LEVEL_NAME_X 29
    #define SECRET_STARS_X 171
    #define MYSCORE_X 238
    #define HISCORE_X 231
#endif

#ifdef VERSION_EU
#include "game/segment7.h"
#endif

/**
 * Prints save file score strings that shows when a save file is chosen inside the score menu.
 */
 void print_save_file_scores(s8 fileIndex) {
#ifndef VERSION_EU
    unsigned char textMario[] = { TEXT_MARIO };
#endif
#ifndef VERSION_US
    unsigned char textFileLetter[] = { TEXT_ZERO };
#endif
#if defined(VERSION_JP) || defined(VERSION_SH)
    void **levelNameTable = segmented_to_virtual(seg2_course_name_table);
#endif
#ifndef VERSION_EU
    unsigned char textHiScore[] = { TEXT_HI_SCORE };
    unsigned char textMyScore[] = { TEXT_MY_SCORE };
    #ifdef VERSION_US
        unsigned char textFileLetter[] = { TEXT_ZERO };
        void **levelNameTable = segmented_to_virtual(seg2_course_name_table);
    #endif
#else
    void **levelNameTable;
    switch (sLanguageMode) {
        case LANGUAGE_ENGLISH:
            levelNameTable = segmented_to_virtual(eu_course_strings_en_table);
            break;
        case LANGUAGE_FRENCH:
            levelNameTable = segmented_to_virtual(eu_course_strings_fr_table);
            break;
        case LANGUAGE_GERMAN:
            levelNameTable = segmented_to_virtual(eu_course_strings_de_table);
            break;
    }
#endif

    textFileLetter[0] = fileIndex + ASCII_TO_DIALOG('A'); // get letter of file selected

    // Print file name at top
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);
    print_hud_lut_string(HUD_LUT_DIFF, MARIO_X, 15, textMario);
    print_hud_lut_string(HUD_LUT_GLOBAL, FILE_LETTER_X, 15, textFileLetter);

    // Print save file star count at top
    print_save_file_star_count(fileIndex, 124, 15);
    gSPDisplayList(gDisplayListHead++, dl_rgba16_text_end);
    // Print course scores
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_begin);
    gDPSetEnvColor(gDisplayListHead++, 255, 255, 255, sTextBaseAlpha);

//! Huge print list, for loops exist for a reason!
//  PADCHAR is used to difference an x position value between
//  JP and US when the course number is only one digit.
#if defined(VERSION_JP) || defined(VERSION_SH)
    #define PADCHAR 0
    #define PRINT_COURSE_SCORES(courseIndex, pad)                                                               \
        print_menu_generic_string(23 + (pad * 3), 23 + 12 * courseIndex, segmented_to_virtual(levelNameTable[courseIndex - 1]));  \
        print_score_file_star_score(fileIndex, courseIndex - 1, 152, 23 + 12 * courseIndex);                        \
        print_score_file_course_coin_score(fileIndex, courseIndex - 1, 213, 23 + 12 * courseIndex);
#else
    #define PADCHAR 1
    #define PRINT_COURSE_SCORES(courseIndex, pad)                                                               \
        print_menu_generic_string(23 + (pad * 3), 23 + 12 * courseIndex, segmented_to_virtual(levelNameTable[courseIndex - 1]));  \
        print_score_file_star_score(fileIndex, courseIndex - 1, 171, 23 + 12 * courseIndex);                        \
        print_score_file_course_coin_score(fileIndex, courseIndex - 1, 213, 23 + 12 * courseIndex);
#endif
    // Course values are indexed, from Bob-omb Battlefield to Rainbow Ride
    PRINT_COURSE_SCORES(COURSE_BOB, PADCHAR) // BOB
    PRINT_COURSE_SCORES(COURSE_WF, PADCHAR) // WF
    PRINT_COURSE_SCORES(COURSE_JRB, PADCHAR) // JRB
    PRINT_COURSE_SCORES(COURSE_CCM, PADCHAR) // CCM
    PRINT_COURSE_SCORES(COURSE_BBH, PADCHAR) // BBH
    PRINT_COURSE_SCORES(COURSE_HMC, PADCHAR) // HMC
    PRINT_COURSE_SCORES(COURSE_LLL, PADCHAR) // LLL
    PRINT_COURSE_SCORES(COURSE_SSL, PADCHAR) // SSL
    PRINT_COURSE_SCORES(COURSE_DDD, PADCHAR) // DDD
    PRINT_COURSE_SCORES(COURSE_SL, 0)  // SL
    PRINT_COURSE_SCORES(COURSE_WDW, 0) // WDW
    PRINT_COURSE_SCORES(COURSE_TTM, 0) // TTM
    PRINT_COURSE_SCORES(COURSE_THI, 0) // THI
    PRINT_COURSE_SCORES(COURSE_TTC, 0) // TTC
    PRINT_COURSE_SCORES(COURSE_RR, 0) // RR
#undef PRINT_COURSE_SCORES
#undef PADCHAR

    // Print level name
    print_menu_generic_string(LEVEL_NAME_X, 215, segmented_to_virtual(levelNameTable[25]));
    // Print castle secret stars
    print_score_file_castle_secret_stars(fileIndex, SECRET_STARS_X, 215);

    // Print current coin score mode
    if (sScoreFileCoinScoreMode == 0) {
#ifdef VERSION_EU
        print_menu_generic_string(get_str_x_pos_from_center(257, textMyScore[sLanguageMode], 10.0f),
            24, textMyScore[sLanguageMode]);
#else
        print_menu_generic_string(MYSCORE_X, 24, textMyScore);
#endif
    } else {
#ifdef VERSION_EU
        print_menu_generic_string(get_str_x_pos_from_center(257, textHiScore[sLanguageMode], 10.0f),
            24,textHiScore[sLanguageMode]);
#else
        print_menu_generic_string(HISCORE_X, 24, textHiScore);
#endif
    }
    gSPDisplayList(gDisplayListHead++, dl_menu_ia8_text_end);
}

/**
 * Prints file select strings depending on the menu selected.
 * Also checks if all saves exists and defines text and main menu timers.
 */
static void print_file_select_strings(void) {
    UNUSED s32 unused1;
    UNUSED s32 unused2;

    create_dl_ortho_matrix();
    switch (sSelectedButtonID) {
        case MENU_BUTTON_NONE:
#ifdef VERSION_EU
            // Ultimately calls print_main_menu_strings, but prints main language strings first.
            print_main_lang_strings();
#else
            print_main_menu_strings();
#endif
            break;
        case MENU_BUTTON_SCORE:
            print_score_menu_strings();
            sScoreFileCoinScoreMode = 0;
            break;
        case MENU_BUTTON_COPY:
            print_copy_menu_strings();
            break;
        case MENU_BUTTON_ERASE:
            print_erase_menu_strings();
            break;
        case MENU_BUTTON_SCORE_FILE_A:
            print_save_file_scores(SAVE_FILE_A);
            break;
        case MENU_BUTTON_SCORE_FILE_B:
            print_save_file_scores(SAVE_FILE_B);
            break;
        case MENU_BUTTON_SCORE_FILE_C:
            print_save_file_scores(SAVE_FILE_C);
            break;
        case MENU_BUTTON_SCORE_FILE_D:
            print_save_file_scores(SAVE_FILE_D);
            break;
        case MENU_BUTTON_SOUND_MODE:
            print_sound_mode_menu_strings();
            break;
    }
    // If all 4 save file exists, define true to sAllFilesExist to prevent more copies in copy menu
    if (save_file_exists(SAVE_FILE_A) == TRUE && save_file_exists(SAVE_FILE_B) == TRUE &&
        save_file_exists(SAVE_FILE_C) == TRUE && save_file_exists(SAVE_FILE_D) == TRUE) {
        sAllFilesExist = TRUE;
    } else {
        sAllFilesExist = FALSE;
    }
    // Timers for menu alpha text and the main menu itself
    if (sTextBaseAlpha < 250) {
        sTextBaseAlpha += 10;
    }
    if (sMainMenuTimer < 1000) {
        sMainMenuTimer += 1;
    }
}

/**
 * Geo function that prints file select strings and the cursor.
 */
Gfx *geo_file_select_strings_and_menu_cursor(s32 callContext, UNUSED struct GraphNode *node, UNUSED f32 mtx[4][4]) {
    if (callContext == GEO_CONTEXT_RENDER) {
        print_file_select_strings();
        print_menu_cursor();
    }
    return NULL;
}

/**
 * Initiates file select values after Mario Screen.
 * Relocates cursor position of the last save if the game goes back to the Mario Screen
 * either completing a course choosing "SAVE & QUIT" or having a game over.
 */
s32 lvl_init_menu_values_and_cursor_pos(UNUSED s32 arg, UNUSED s32 unused) {
#ifdef VERSION_EU
    s8 fileNum;
#endif
    sSelectedButtonID = MENU_BUTTON_NONE;
    sCurrentMenuLevel = MENU_LAYER_MAIN;
    sTextBaseAlpha = 0;
    // Place the cursor over the save file that was being played.
    // gCurrSaveFileNum is 1 by default when the game boots, as such
    // the cursor will point on Mario A save file.
    switch (gCurrSaveFileNum) {
        case 1: // File A
            sCursorPos[0] = -94.0f;
            sCursorPos[1] = 46.0f;
            break;
        case 2: // File B
            sCursorPos[0] = 24.0f;
            sCursorPos[1] = 46.0f;
            break;
        case 3: // File C
            sCursorPos[0] = -94.0f;
            sCursorPos[1] = 5.0f;
            break;
        case 4: // File D
            sCursorPos[0] = 24.0f;
            sCursorPos[1] = 5.0f;
            break;
    }
    sClickPos[0] = -10000;
    sClickPos[1] = -10000;
    sCursorClickingTimer = 0;
    sSelectedFileNum = 0;
    sSelectedFileIndex = MENU_BUTTON_NONE;
    sFadeOutText = FALSE;
    sStatusMessageID = 0;
    sTextFadeAlpha = 0;
    sMainMenuTimer = 0;
    sEraseYesNoHoverState = MENU_ERASE_HOVER_NONE;
    sSoundMode = save_file_get_sound_mode();
#ifdef VERSION_EU
    sLanguageMode = eu_get_language();

    for (fileNum = 0; fileNum < 4; fileNum++) {
        if (save_file_exists(fileNum) == TRUE) {
            sOpenLangSettings = FALSE;
            break;
        } else {
            sOpenLangSettings = TRUE;
        }
    }
#endif
    //! no return value
#ifdef AVOID_UB
    return 0;
#endif
}

/**
 * Updates file select menu button objects so they can be interacted.
 * When a save file is selected, it returns fileNum value
 * defined in load_main_menu_save_file.
 */
s32 lvl_update_obj_and_load_file_selected(UNUSED s32 arg, UNUSED s32 unused) {
    area_update_objects();
    return sSelectedFileNum;
}
