#ifndef _MARIO_STEP_H
#define _MARIO_STEP_H

#include "types.h"

extern struct Surface gWaterSurfacePseudoFloor;

extern f32 get_additive_y_vel_for_jumps(void);
extern void stub_mario_step_1(struct MarioState *);
extern void stub_mario_step_2(void);

extern void mario_bonk_reflection(struct MarioState *, u32);
extern u32 mario_update_quicksand(struct MarioState *, f32);
extern u32 mario_push_off_steep_floor(struct MarioState *, u32, u32);
extern u32 mario_update_moving_sand(struct MarioState *);
extern u32 mario_update_windy_ground(struct MarioState *);
extern void stop_and_set_height_to_floor(struct MarioState *);
extern s32 stationary_ground_step(struct MarioState *);
extern s32 perform_ground_step(struct MarioState *);
extern s32 perform_air_step(struct MarioState *, u32);

#endif /* _MARIO_STEP_H */
