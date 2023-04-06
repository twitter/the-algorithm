#ifndef _MARIO_ACTIONS_OBJECT_H
#define _MARIO_ACTIONS_OBJECT_H

struct MarioState;

s32 mario_update_punch_sequence(struct MarioState *m);
s32 mario_execute_object_action(struct MarioState *m);

#endif /* _MARIO_ACTIONS_OBJECT_H */
