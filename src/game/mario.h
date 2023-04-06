#ifndef _MARIO_H
#define _MARIO_H

#include "types.h"

extern u32 gAudioRandom;

extern struct Object *gMarioObject;
extern struct Object *gLuigiObject;

extern s32 is_anim_at_end(struct MarioState *m);
extern s32 is_anim_past_end(struct MarioState *m);
extern s16 set_mario_animation(struct MarioState *m, s32 targetAnimID);
extern s16 set_mario_anim_with_accel(struct MarioState *m, s32 targetAnimID, s32 accel);
extern void set_anim_to_frame(struct MarioState *m, s16 animFrame);
extern s32 is_anim_past_frame(struct MarioState *m, s16 animFrame);
extern s16 find_mario_anim_flags_and_translation(struct Object *o, s32 yaw, Vec3s translation);
extern void update_mario_pos_for_anim(struct MarioState *m);
extern s16 return_mario_anim_y_translation(struct MarioState *m);
extern void play_sound_if_no_flag(struct MarioState *m, u32 soundBits, u32 flags);
extern void play_mario_jump_sound(struct MarioState *m);
extern void adjust_sound_for_speed(struct MarioState *m);
extern void play_sound_and_spawn_particles(struct MarioState *m, u32 soundBits, u32 waveParticleType);
extern void play_mario_action_sound(struct MarioState *m, u32 soundBits, u32 waveParticleType);
extern void play_mario_landing_sound(struct MarioState *m, u32 soundBits);
extern void play_mario_landing_sound_once(struct MarioState *m, u32 soundBits);
extern void play_mario_heavy_landing_sound(struct MarioState *m, u32 soundBits);
extern void play_mario_heavy_landing_sound_once(struct MarioState *m, u32 soundBits);
extern void play_mario_sound(struct MarioState *m, s32 primarySoundBits, s32 scondarySoundBits);
extern void mario_set_forward_vel(struct MarioState *m, f32 speed);
extern s32 mario_get_floor_class(struct MarioState *m);
extern u32 mario_get_terrain_sound_addend(struct MarioState *m);
extern struct Surface *resolve_and_return_wall_collisions(Vec3f pos, f32 offset, f32 radius);
extern f32 vec3f_find_ceil(Vec3f pos, f32 height, struct Surface **ceil);
extern s32 mario_facing_downhill(struct MarioState *m, s32 turnYaw);
extern u32 mario_floor_is_slippery(struct MarioState *m);
extern s32 mario_floor_is_slope(struct MarioState *m);
extern s32 mario_floor_is_steep(struct MarioState *m);
extern f32 find_floor_height_relative_polar(struct MarioState *m, s16 angleFromMario, f32 distFromMario);
extern s16 find_floor_slope(struct MarioState *m, s16 yawOffset);
extern void update_mario_sound_and_camera(struct MarioState *m);
extern void set_steep_jump_action(struct MarioState *m);
extern u32 set_mario_action(struct MarioState *, u32 action, u32 actionArg);
extern s32 set_jump_from_landing(struct MarioState *m);
extern s32 set_jumping_action(struct MarioState *m, u32 action, u32 actionArg);
extern s32 drop_and_set_mario_action(struct MarioState *m, u32 action, u32 actionArg);
extern s32 hurt_and_set_mario_action(struct MarioState *m, u32 action, u32 actionArg, s16 hurtCounter);
extern s32 check_common_action_exits(struct MarioState *m);
extern s32 check_common_hold_action_exits(struct MarioState *m);
extern s32 transition_submerged_to_walking(struct MarioState *m);
extern s32 set_water_plunge_action(struct MarioState *m);
extern s32 execute_mario_action(struct Object *o);
extern void init_mario(void);
extern void init_mario_from_save_file(void);

#endif /* _MARIO_H */
