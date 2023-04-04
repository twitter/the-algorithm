#ifndef OBJECT_HELPERS_H
#define OBJECT_HELPERS_H

#include <PR/ultratypes.h>

#include "macros.h"
#include "types.h"

// used for chain chomp and wiggler
struct ChainSegment {
    f32 posX;
    f32 posY;
    f32 posZ;
    s16 pitch;
    s16 yaw;
    s16 roll;
};

#define WATER_DROPLET_FLAG_RAND_ANGLE                0x02
#define WATER_DROPLET_FLAG_RAND_OFFSET_XZ            0x04 // Unused
#define WATER_DROPLET_FLAG_RAND_OFFSET_XYZ           0x08 // Unused
#define WATER_DROPLET_FLAG_SET_Y_TO_WATER_LEVEL      0x20
#define WATER_DROPLET_FLAG_RAND_ANGLE_INCR_PLUS_8000 0x40
#define WATER_DROPLET_FLAG_RAND_ANGLE_INCR           0x80 // Unused

// Call spawn_water_droplet with this struct to spawn an object.
struct WaterDropletParams {
    s16 flags; // Droplet spawn flags, see defines above
    s16 model;
    const BehaviorScript *behavior;
    s16 moveAngleRange; // Only used for RAND_ANGLE_INCR flags
    s16 moveRange;      // Only used for RAND_OFFSET flags
    f32 randForwardVelOffset;
    f32 randForwardVelScale;
    f32 randYVelOffset;
    f32 randYVelScale;
    f32 randSizeOffset;
    f32 randSizeScale;
};

struct struct802A1230 {
    /*0x00*/ s16 unk00;
    /*0x02*/ s16 unk02;
};

struct Struct802A272C {
    Vec3f vecF;
    Vec3s vecS;
};

struct SpawnParticlesInfo {
    /*0x00*/ s8 behParam;
    /*0x01*/ s8 count;
    /*0x02*/ u8 model;
    /*0x03*/ s8 offsetY;
    /*0x04*/ s8 forwardVelBase;
    /*0x05*/ s8 forwardVelRange;
    /*0x06*/ s8 velYBase;
    /*0x07*/ s8 velYRange;
    /*0x08*/ s8 gravity;
    /*0x09*/ s8 dragStrength;
    /*0x0C*/ f32 sizeBase;
    /*0x10*/ f32 sizeRange;
};

Gfx *geo_update_projectile_pos_from_parent(s32 callContext, UNUSED struct GraphNode *node, Mat4 mtx);
Gfx *geo_update_layer_transparency(s32 callContext, struct GraphNode *node, UNUSED void *context);
#ifdef AVOID_UB
Gfx *geo_switch_anim_state(s32 callContext, struct GraphNode *node, UNUSED void *context);
Gfx *geo_switch_area(s32 callContext, struct GraphNode *node, UNUSED void *context);
#else
Gfx *geo_switch_anim_state(s32 callContext, struct GraphNode *node);
Gfx *geo_switch_area(s32 callContext, struct GraphNode *node);
#endif
void obj_update_pos_from_parent_transformation(Mat4 a0, struct Object *a1);
void obj_apply_scale_to_matrix(struct Object *obj, Mat4 dst, Mat4 src);
void create_transformation_from_matrices(Mat4 a0, Mat4 a1, Mat4 a2);
void obj_set_held_state(struct Object *obj, const BehaviorScript *heldBehavior);
f32 lateral_dist_between_objects(struct Object *obj1, struct Object *obj2);
f32 dist_between_objects(struct Object *obj1, struct Object *obj2);
void cur_obj_forward_vel_approach_upward(f32 target, f32 increment);
s32 approach_f32_signed(f32 *value, f32 target, f32 increment);
f32 approach_f32_symmetric(f32 value, f32 target, f32 increment);
s16 approach_s16_symmetric(s16 value, s16 target, s16 increment);
s32 cur_obj_rotate_yaw_toward(s16 target, s16 increment);
s16 obj_angle_to_object(struct Object *obj1, struct Object *obj2);
s16 obj_turn_toward_object(struct Object *obj, struct Object *target, s16 angleIndex, s16 turnAmount);
void obj_set_parent_relative_pos(struct Object *obj, s16 relX, s16 relY, s16 relZ);
void obj_set_pos(struct Object *obj, s16 x, s16 y, s16 z);
void obj_set_angle(struct Object *obj, s16 pitch, s16 yaw, s16 roll);
struct Object *spawn_object_abs_with_rot(struct Object *parent, s16 uselessArg, u32 model,
                                         const BehaviorScript *behavior,
                                         s16 x, s16 y, s16 z, s16 pitch, s16 yaw, s16 roll);
struct Object *spawn_object_rel_with_rot(struct Object *parent, u32 model, const BehaviorScript *behavior,
                                         s16 xOff, s16 yOff, s16 zOff, s16 pitch, s16 yaw, UNUSED s16 roll);
struct Object *spawn_obj_with_transform_flags(struct Object *sp20, s32 model, const BehaviorScript *sp28);
struct Object *spawn_water_droplet(struct Object *parent, struct WaterDropletParams *params);
struct Object *spawn_object_at_origin(struct Object *, s32, u32, const BehaviorScript *);
struct Object *spawn_object_at_origin(struct Object *parent, UNUSED s32 unusedArg, u32 model, const BehaviorScript *behavior);
struct Object *spawn_object(struct Object *parent, s32 model, const BehaviorScript *behavior);
struct Object *try_to_spawn_object(s16 offsetY, f32 scale, struct Object *parent, s32 model, const BehaviorScript *behavior);
struct Object *spawn_object_with_scale(struct Object *parent, s32 model, const BehaviorScript *behavior, f32 scale);
struct Object *spawn_object_relative(s16 behaviorParam, s16 relativePosX, s16 relativePosY, s16 relativePosZ,
                                     struct Object *parent, s32 model, const BehaviorScript *behavior);
struct Object *spawn_object_relative_with_scale(s16 behaviorParam, s16 relativePosX, s16 relativePosY,
                                                s16 relativePosZ, f32 scale, struct Object *parent,
                                                s32 model, const BehaviorScript *behavior);
void cur_obj_move_using_vel(void);
void obj_copy_graph_y_offset(struct Object *dst, struct Object *src);
void obj_copy_pos_and_angle(struct Object *dst, struct Object *src);
void obj_copy_pos(struct Object *dst, struct Object *src);
void obj_copy_angle(struct Object *dst, struct Object *src);
void obj_set_gfx_pos_from_pos(struct Object *obj);
void linear_mtxf_mul_vec3f(Mat4 m, Vec3f dst, Vec3f v);
void linear_mtxf_transpose_mul_vec3f(Mat4 m, Vec3f dst, Vec3f v);
void obj_apply_scale_to_transform(struct Object *obj);
void obj_copy_scale(struct Object *dst, struct Object *src);
void obj_scale_xyz(struct Object *obj, f32 xScale, f32 yScale, f32 zScale);
void obj_scale(struct Object *obj, f32 scale);
void cur_obj_scale(f32 scale);
void cur_obj_init_animation_with_sound(s32 animIndex);
void cur_obj_init_animation_with_accel_and_sound(s32 animIndex, f32 accel);
void cur_obj_init_animation(s32 animIndex);
void obj_init_animation_with_sound(struct Object *obj, const struct Animation * const* animations, s32 animIndex);
void cur_obj_enable_rendering(void);
void cur_obj_disable_rendering(void);
void cur_obj_unhide(void);
void cur_obj_hide(void);
void cur_obj_set_pos_relative(struct Object *other, f32 dleft, f32 dy, f32 dforward);
void cur_obj_set_pos_relative_to_parent(f32 dleft, f32 dy, f32 dforward);
void cur_obj_enable_rendering_2(void);
void obj_set_face_angle_to_move_angle(struct Object *obj);
u32 get_object_list_from_behavior(const BehaviorScript *behavior);
struct Object *cur_obj_nearest_object_with_behavior(const BehaviorScript *behavior);
f32 cur_obj_dist_to_nearest_object_with_behavior(const BehaviorScript* behavior);
struct Object *cur_obj_find_nearest_object_with_behavior(const BehaviorScript * behavior, f32 *dist);
struct Object *find_unimportant_object(void);
s32 count_unimportant_objects(void);
s32 count_objects_with_behavior(const BehaviorScript *behavior);
struct Object *cur_obj_find_nearby_held_actor(const BehaviorScript *behavior, f32 maxDist);
void cur_obj_change_action(s32 action);
void cur_obj_set_vel_from_mario_vel(f32 f12,f32 f14);
BAD_RETURN(s16) cur_obj_reverse_animation(void);
BAD_RETURN(s32) cur_obj_extend_animation_if_at_end(void);
s32 cur_obj_check_if_near_animation_end(void);
s32 cur_obj_check_if_at_animation_end(void);
s32 cur_obj_check_anim_frame(s32 frame);
s32 cur_obj_check_anim_frame_in_range(s32 startFrame, s32 rangeLength);
s32 cur_obj_check_frame_prior_current_frame(s16 *a0);
s32 mario_is_in_air_action(void);
s32 mario_is_dive_sliding(void);
void cur_obj_set_y_vel_and_animation(f32 sp18, s32 sp1C);
void cur_obj_unrender_set_action_and_anim(s32 sp18, s32 sp1C);
void cur_obj_get_thrown_or_placed(f32 forwardVel, f32 velY, s32 thrownAction);
void cur_obj_get_dropped(void);
void cur_obj_set_model(s32 modelID);
void mario_set_flag(s32 flag);
s32 cur_obj_clear_interact_status_flag(s32 flag);
void obj_mark_for_deletion(struct Object *obj);
void cur_obj_disable(void);
void cur_obj_become_intangible(void);
void cur_obj_become_tangible(void);
void obj_become_tangible(struct Object *obj);
void cur_obj_update_floor_height(void);
struct Surface *cur_obj_update_floor_height_and_get_floor(void);
void cur_obj_apply_drag_xz(f32 dragStrength);
void cur_obj_move_y(f32 gravity, f32 bounciness, f32 buoyancy);
void cur_obj_unused_resolve_wall_collisions(f32 offsetY, f32 radius);
s16 abs_angle_diff(s16 x0, s16 x1);
void cur_obj_move_xz_using_fvel_and_yaw(void);
void cur_obj_move_y_with_terminal_vel(void);
void cur_obj_compute_vel_xz(void);
f32 increment_velocity_toward_range(f32 value, f32 center, f32 zeroThreshold, f32 increment);
s32 obj_check_if_collided_with_object(struct Object *obj1, struct Object *obj2);
void cur_obj_set_behavior(const BehaviorScript *behavior);
void obj_set_behavior(struct Object *obj, const BehaviorScript *behavior);
s32 cur_obj_has_behavior(const BehaviorScript *behavior);
s32 obj_has_behavior(struct Object *obj, const BehaviorScript *behavior);
f32 cur_obj_lateral_dist_from_mario_to_home(void);
f32 cur_obj_lateral_dist_to_home(void);
void cur_obj_set_pos_to_home(void);
void cur_obj_set_pos_to_home_and_stop(void);
void cur_obj_shake_y(f32 amount);
void cur_obj_start_cam_event(UNUSED struct Object *obj, s32 cameraEvent);
void set_mario_interact_true_if_in_range(UNUSED s32 sp0, UNUSED s32 sp4, f32 sp8);
void obj_set_billboard(struct Object *obj);
void cur_obj_set_hitbox_radius_and_height(f32 radius, f32 height);
void cur_obj_set_hurtbox_radius_and_height(f32 radius, f32 height);
void obj_spawn_loot_blue_coins(struct Object *obj, s32 numCoins, f32 sp28, s16 posJitter);
void obj_spawn_loot_yellow_coins(struct Object *obj, s32 numCoins, f32 sp28);
void cur_obj_spawn_loot_coin_at_mario_pos(void);
s32 cur_obj_advance_looping_anim(void);
s32 cur_obj_resolve_wall_collisions(void);
void cur_obj_update_floor_and_walls(void);
void cur_obj_move_standard(s16 steepSlopeAngleDegrees);
void cur_obj_move_using_vel_and_gravity(void);
void cur_obj_move_using_fvel_and_gravity(void);
s16 cur_obj_angle_to_home(void);
void obj_set_gfx_pos_at_obj_pos(struct Object *obj1, struct Object *obj2);
void obj_translate_local(struct Object *obj, s16 posIndex, s16 localTranslateIndex);
void obj_build_transform_from_pos_and_angle(struct Object *obj, s16 posIndex, s16 angleIndex);
void obj_set_throw_matrix_from_transform(struct Object *obj);
void obj_build_transform_relative_to_parent(struct Object *obj);
void obj_create_transform_from_self(struct Object *obj);
void  cur_obj_rotate_face_angle_using_vel(void);
s32 cur_obj_follow_path(UNUSED s32 unused);
void chain_segment_init(struct ChainSegment *segment);
f32 random_f32_around_zero(f32 diameter);
void obj_scale_random(struct Object *obj, f32 rangeLength, f32 minScale);
void obj_translate_xyz_random(struct Object *obj, f32 rangeLength);
void obj_translate_xz_random(struct Object *obj, f32 rangeLength);
void cur_obj_set_pos_via_transform(void);
void cur_obj_spawn_particles(struct SpawnParticlesInfo *info);
s16 cur_obj_reflect_move_angle_off_wall(void);

#define WAYPOINT_FLAGS_END -1
#define WAYPOINT_FLAGS_INITIALIZED 0x8000
#define WAYPOINT_MASK_00FF 0x00FF
#define WAYPOINT_FLAGS_PLATFORM_ON_TRACK_PAUSE 3

#define PATH_NONE 0
#define PATH_REACHED_END -1
#define PATH_REACHED_WAYPOINT 1

void obj_set_hitbox(struct Object *obj, struct ObjectHitbox *hitbox);
s32 signum_positive(s32 x);
f32 absf(f32 x);
s32 absi(s32 a0);
s32 cur_obj_wait_then_blink(s32 timeUntilBlinking, s32 numBlinks);
s32 cur_obj_is_mario_ground_pounding_platform(void);
void spawn_mist_particles(void);
void spawn_mist_particles_with_sound(u32 sp18);
void cur_obj_push_mario_away(f32 radius);
void cur_obj_push_mario_away_from_cylinder(f32 radius, f32 extentY);
s32 cur_obj_set_direction_table(s8 *a0);
s32 cur_obj_progress_direction_table(void);
void stub_obj_helpers_3(UNUSED s32 sp0, UNUSED s32 sp4);
void cur_obj_scale_over_time(s32 a0, s32 a1, f32 sp10, f32 sp14);
void cur_obj_set_pos_to_home_with_debug(void);
s32 cur_obj_is_mario_on_platform(void);
s32 jiggle_bbh_stair(s32 a0);
void cur_obj_call_action_function(void (*actionFunctions[])(void));
void spawn_base_star_with_no_lvl_exit(void);
s32 bit_shift_left(s32 a0);
s32 cur_obj_mario_far_away(void);
s32 is_mario_moving_fast_or_in_air(s32 speedThreshold);
s32 is_item_in_array(s8 item, s8 *array);
void cur_obj_enable_rendering_if_mario_in_room(void);
s32 cur_obj_set_hitbox_and_die_if_attacked(struct ObjectHitbox *hitbox, s32 deathSound, s32 noLootCoins);
void obj_explode_and_spawn_coins(f32 sp18, s32 sp1C);
void obj_set_collision_data(struct Object *obj, const void *segAddr);
void cur_obj_if_hit_wall_bounce_away(void);
s32 cur_obj_hide_if_mario_far_away_y(f32 distY);
Gfx *geo_offset_klepto_held_object(s32 callContext, struct GraphNode *node, UNUSED Mat4 mtx);
Gfx *geo_offset_klepto_debug(s32 callContext, struct GraphNode *node, UNUSED Mat4 mtx);
s32 obj_is_hidden(struct Object *obj);
void enable_time_stop(void);
void disable_time_stop(void);
void set_time_stop_flags(s32 flags);
void clear_time_stop_flags(s32 flags);
s32 cur_obj_can_mario_activate_textbox(f32 radius, f32 height, UNUSED s32 unused);
s32 cur_obj_can_mario_activate_textbox_2(f32 radius, f32 height);
s32 cur_obj_update_dialog(s32 actionArg, s32 dialogFlags, s32 dialogID, UNUSED s32 unused);
s32 cur_obj_update_dialog_with_cutscene(s32 actionArg, s32 dialogFlags, s32 cutsceneTable, s32 dialogID);
s32 cur_obj_has_model(u16 modelID);
void cur_obj_align_gfx_with_floor(void);
s32 mario_is_within_rectangle(s16 minX, s16 maxX, s16 minZ, s16 maxZ);
void cur_obj_shake_screen(s32 shake);
s32 obj_attack_collided_from_other_object(struct Object *obj);
s32 cur_obj_was_attacked_or_ground_pounded(void);
void obj_copy_behavior_params(struct Object *dst, struct Object *src);
void cur_obj_init_animation_and_anim_frame(s32 animIndex, s32 animFrame);
s32 cur_obj_init_animation_and_check_if_near_end(s32 animIndex);
void cur_obj_init_animation_and_extend_if_at_end(s32 animIndex);
s32 cur_obj_check_grabbed_mario(void);
s32 player_performed_grab_escape_action(void);
void cur_obj_unused_play_footstep_sound(s32 animFrame1, s32 animFrame2, s32 sound);
void enable_time_stop_including_mario(void);
void disable_time_stop_including_mario(void);
s32 cur_obj_check_interacted(void);
void cur_obj_spawn_loot_blue_coin(void);

#ifndef VERSION_JP
void cur_obj_spawn_star_at_y_offset(f32 targetX, f32 targetY, f32 targetZ, f32 offsetY);
#endif

#endif // OBJECT_HELPERS_H
