#ifndef _MARIO_MISC_H
#define _MARIO_MISC_H

#include "types.h"

extern struct GraphNodeObject gMirrorMario;
extern struct MarioBodyState gBodyStates[2];

extern Gfx *geo_draw_mario_head_goddard(s32 a, struct GraphNode *b, Mat4 *c);
extern void bhv_toad_message_loop(void);
extern void bhv_toad_message_init(void);
extern void bhv_unlock_door_star_init(void);
extern void bhv_unlock_door_star_loop(void);
extern Gfx *geo_mirror_mario_set_alpha(s32 a, struct GraphNode *b, UNUSED Mat4 *c);
extern Gfx *geo_switch_mario_stand_run(s32 run, struct GraphNode *node, UNUSED Mat4 *c);
extern Gfx *geo_switch_mario_eyes(s32 run, struct GraphNode *node, UNUSED Mat4 *c);
extern Gfx *geo_mario_tilt_torso(s32 a, struct GraphNode *b, UNUSED Mat4 *c);
extern Gfx *geo_mario_head_rotation(s32 a, struct GraphNode *b, UNUSED Mat4 *c);
extern Gfx *geo_switch_mario_hand(s32 run, struct GraphNode *node, UNUSED Mat4 *c);
extern Gfx *geo_mario_hand_foot_scaler(s32 a, struct GraphNode *b, UNUSED Mat4 *c);
extern Gfx *geo_switch_mario_cap_effect(s32 run, struct GraphNode *node, UNUSED Mat4 *c);
extern Gfx *geo_switch_mario_cap_on_off(s32 run, struct GraphNode *node, UNUSED Mat4 *c);
extern Gfx *geo_mario_rotate_wing_cap_wings(s32 a, struct GraphNode *b, UNUSED Mat4 *c);
extern Gfx *geo_switch_mario_hand_grab_pos(s32 callContext, struct GraphNode *b, Mat4 *c);
extern Gfx *geo_render_mirror_mario(s32 a, struct GraphNode *b, UNUSED Mat4 *c);
extern Gfx *geo_mirror_mario_backface_culling(s32 a, struct GraphNode *b, UNUSED Mat4 *c);

#endif /* _MARIO_MISC_H */
