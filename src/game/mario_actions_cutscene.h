#ifndef MARIO_ACTIONS_CUTSCENE_H
#define MARIO_ACTIONS_CUTSCENE_H

#include <PR/ultratypes.h>

#include "macros.h"
#include "types.h"

// set_mario_npc_dialog
// actionArg
#define MARIO_DIALOG_STOP       0
#define MARIO_DIALOG_LOOK_FRONT 1 // no head turn
#define MARIO_DIALOG_LOOK_UP    2
#define MARIO_DIALOG_LOOK_DOWN  3
// dialogState
#define MARIO_DIALOG_STATUS_NONE  0
#define MARIO_DIALOG_STATUS_START 1
#define MARIO_DIALOG_STATUS_SPEAK 2

void print_displaying_credits_entry(void);
void bhv_end_peach_loop(void);
void bhv_end_toad_loop(void);
s32 geo_switch_peach_eyes(s32 run, struct GraphNode *node, UNUSED s32 a2);
s32 mario_ready_to_speak(void);
s32 set_mario_npc_dialog(s32 actionArg);
s32 mario_execute_cutscene_action(struct MarioState *m);

#endif // MARIO_ACTIONS_CUTSCENE_H
