#ifndef _MARIO_ACTIONS_CUTSCENE_H
#define _MARIO_ACTIONS_CUTSCENE_H

#include "types.h"

extern void print_displaying_credits_entry(void);
extern void bhv_end_peach_loop(void);
extern void bhv_end_toad_loop(void);
extern s32 geo_switch_peach_eyes(s32 run, struct GraphNode *node, UNUSED s32 a2);
extern s32 mario_ready_to_speak(void);
extern s32 set_mario_npc_dialog(s32);
extern s32 mario_execute_cutscene_action(struct MarioState *);

#endif /* _MARIO_ACTIONS_CUTSCENE_H */
