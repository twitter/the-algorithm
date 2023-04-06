#ifndef OBJECT_HELPERS_H
#define OBJECT_HELPERS_H

#include "types.h"

// used for chain chomp and wiggler
struct ChainSegment
{
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
struct WaterDropletParams
{
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

// TODO: Field names
struct SpawnParticlesInfo
{
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

// extern ? D_80336610;
extern struct GraphNode **gLoadedGraphNodes;
// extern ? sLevelsWithRooms;
// extern ? sMrIParticleActions;
// extern ? D_8032F124;
// extern ? sMrIActions;
// extern ? D_8032F134;
// extern const char* D_803366B0;
// extern ? sKingBobombActions;
// extern s16 gMarioShotFromCannon;
// extern ? sOpenedCannonActions;
// extern ? D_803366B4;
// extern ? D_803366BC;
// extern ? D_803366C4;
// extern ? sChuckyaActions;
// extern ? D_803366C8;
// extern ? D_8032F260;
// extern ? D_8032F273;
// extern ? D_8032F271;
// extern ? D_8032F270;
// extern ? D_8032F284;
// extern ? D_8032F294;
// extern ? sCoinInsideBooActions;
// extern s16 D_8035FEE4;
// extern ? sGrindelThwompActions;
// extern ? sTumblingBridgeParams;
// extern ? sTumblingBridgeActions;
// extern ? sElevatorActions;
// extern ? D_8032F3CC;
// extern ? D_8032F3E0;
// extern ? D_8032F3F4;
// extern ? D_8032F3FC;
// extern ? sUkikiCageActions;
// extern ? D_8032F420;
// extern ? D_8032F430;
// extern ? D_8032F440;
// extern ? D_8032F450;
// extern ? sHeaveHoActions;
// extern ? D_8032F498;
// extern ? sJumpingBoxActions;
// extern ? D_8032F4B0;
// extern ? D_8035FF10;
// extern ? sBetaBooKeyActions;
// extern ? D_8032F4CC;
// extern ? sBulletBillActions;
// extern ? sBowserTailAnchorActions;
extern void* D_060576FC;
// extern ? D_8032F50C;
// extern ? D_8032F510;
// extern ? sBowserActions;
// extern ? D_8032F5B8;
// extern ? sFallingBowserPlatformActions;
// extern ? D_8032F738;
// extern ? D_8032F728;
// extern ? D_8032F754;
extern s8 gDddPaintingStatus;
// extern ? D_8035FEE8;
// extern ? sCageUkikiPath;
// extern ? sUkikiActions;
// extern ? sUkikiSoundStates;
// extern ? D_803366D8;
// extern ? D_803366E4;
// extern ? D_803366F0;
// extern ? sRotatingCwFireBarsActions;
// extern ? D_8032F8E0;
// extern ? sToxBoxActions;
// extern ? sPiranhaPlantActions;
// extern ? D_8032FB4C;
// extern ? sBowserPuzzlePieceActions;
// extern ? sTuxiesMotherActions;
// extern ? sSmallPenguinActions;
// extern ? D_0301C2B0;
// extern ? D_0600E264;
// extern ? sFishActions;
// extern ? sFishGroupActions;
// extern ? sBirdChirpChirpActions;
// extern ? sCheepCheepActions;
// extern ? D_8032FC30;
// extern ? sExclamationBoxContents;
// extern ? sExclamationBoxActions;
// extern ? D_8032FCD8;
// extern ? D_8032FCE8;
// extern ? D_803366FC;
// extern ? D_8032FCF8;
// extern ? sTweesterActions;
// extern ? sCourtyardBooTripletPositions;
// extern ? sBooActions;
// extern ? D_8032FD14;
// extern ? sBooGivingStarActions;
// extern ? sBooWithCageActions;
// extern ? D_8032FD74;
// extern ? sWhompActions;
// extern ? D_8032FDAC;
// extern ? D_8032FDF4;
// extern ? D_8032FE3C;
// extern ? D_8032FE4C;
// extern ? D_80336704;
// extern ? D_8033670C;

extern Gfx *geo_update_projectile_pos_from_parent(s32 run, UNUSED struct GraphNode *node, f32 mtx[4][4]);
extern Gfx *geo_update_layer_transparency(s32 run, struct GraphNode *node, UNUSED void *context);
#ifdef AVOID_UB
extern Gfx *geo_switch_anim_state(s32 run, struct GraphNode *node, void *context);
extern Gfx *geo_switch_area(s32 run, struct GraphNode *node, void *context);
#else
extern Gfx *geo_switch_anim_state(s32 run, struct GraphNode *node);
extern Gfx *geo_switch_area(s32 run, struct GraphNode *node);
#endif
extern void obj_update_pos_from_parent_transformation(Mat4, struct Object *);
void obj_apply_scale_to_matrix(struct Object *, Mat4, Mat4);
extern void create_transformation_from_matrices(Mat4,Mat4,Mat4);
void obj_set_held_state(struct Object *, const BehaviorScript *);
extern f32 lateral_dist_between_objects(struct Object *, struct Object *);
extern f32 dist_between_objects(struct Object *, struct Object *);
extern void cur_obj_forward_vel_approach_upward(f32,f32);
extern s32 approach_f32_signed(f32*,f32,f32);
extern f32 approach_f32_symmetric(f32,f32,f32);
extern s16 approach_s16_symmetric(s16 arg0, s16 arg1, s16 arg2);
extern s32 cur_obj_rotate_yaw_toward(s16,s16);
extern s16 obj_angle_to_object(struct Object *, struct Object *);
extern s16 obj_turn_toward_object(struct Object *, struct Object *, s16, s16);
extern void obj_set_parent_relative_pos(struct Object*,s16,s16,s16);
extern void obj_set_pos(struct Object*,s16,s16,s16);
extern void obj_set_angle(struct Object*,s16,s16,s16);
extern struct Object *spawn_object_abs_with_rot(struct Object *, s16, u32, const BehaviorScript *, s16, s16, s16, s16, s16, s16);
extern struct Object *spawn_object_rel_with_rot(struct Object *sp20, u32 sp24, const BehaviorScript *sp28, s16 sp2E, s16 sp32, s16 sp36, s16 sp3A, s16 sp3E, s16 sp42);
// extern ? spawn_obj_with_transform_flags(?);
extern struct Object *spawn_water_droplet(struct Object *, struct WaterDropletParams *);
extern struct Object *spawn_object_at_origin(struct Object *, s32, u32, const BehaviorScript *);
extern struct Object *spawn_object(struct Object *, s32, const BehaviorScript *);
extern struct Object* try_to_spawn_object(s16,f32,struct Object*,s32,const BehaviorScript *);
extern struct Object* spawn_object_with_scale(struct Object*,s32,const BehaviorScript *,f32);
// extern ? obj_build_relative_transform(?);
extern struct Object* spawn_object_relative(s16, s16, s16, s16, struct Object *, s32, const BehaviorScript *);
extern struct Object* spawn_object_relative_with_scale(s16,s16,s16,s16,f32,struct Object *,s32,const BehaviorScript *);
// extern ? cur_obj_move_using_vel(?);
extern void obj_copy_graph_y_offset(struct Object*,struct Object*);
extern void obj_copy_pos_and_angle(struct Object *, struct Object *);
extern void obj_copy_pos(struct Object*,struct Object*);
// extern ? obj_copy_angle(?);
extern void obj_set_gfx_pos_from_pos(struct Object*);
// extern ? spawn_obj_with_transform_flags(?);
extern void linear_mtxf_mul_vec3f(f32 [4][4], Vec3f, Vec3f);
extern void linear_mtxf_transpose_mul_vec3f(f32 [4][4], Vec3f, Vec3f);
// extern ? obj_apply_scale_to_transform(?);
void obj_copy_scale(struct Object *toObj, struct Object *fromObj);
extern void obj_scale_xyz(struct Object* obj, f32 xScale, f32 yScale, f32 zScale);
extern void obj_scale(struct Object *, f32);
extern void cur_obj_scale(f32);
extern void cur_obj_init_animation_with_sound(s32);
extern void cur_obj_init_animation_with_accel_and_sound(s32, f32);
extern void cur_obj_init_animation(s32 arg0);
void obj_init_animation_with_sound(struct Object *a0, struct Animation **a1, s32 a2);
// extern ? obj_enable_rendering_and_become_tangible(?);
extern void cur_obj_enable_rendering(void);
// extern ? obj_disable_rendering_and_become_intangible(?);
extern void cur_obj_disable_rendering(void);
extern void cur_obj_unhide(void);
extern void cur_obj_hide(void);
extern void cur_obj_set_pos_relative(struct Object *MarioObj, f32, f32, f32);
// extern ? cur_obj_set_pos_relative_to_parent(?);
extern void cur_obj_enable_rendering_2(void);
// extern ? obj_unused_init_on_floor(?);
extern void obj_set_face_angle_to_move_angle(struct Object *);
u32 get_object_list_from_behavior(const BehaviorScript *behavior);
extern struct Object *cur_obj_nearest_object_with_behavior(const BehaviorScript *);
f32 cur_obj_dist_to_nearest_object_with_behavior(const BehaviorScript*);
extern struct Object *cur_obj_find_nearest_object_with_behavior(const BehaviorScript *, f32 *);
extern struct Object *find_unimportant_object(void);
// extern ? count_unimportant_objects(?);
extern s32 count_objects_with_behavior(const BehaviorScript *behavior);
struct Object* cur_obj_find_nearby_held_actor(const BehaviorScript *,f32);
// extern ? obj_reset_timer_and_subaction(?);
void cur_obj_change_action(s32);
void cur_obj_set_vel_from_mario_vel(f32,f32);
BAD_RETURN(s16) cur_obj_reverse_animation(void);
extern BAD_RETURN(s32) cur_obj_extend_animation_if_at_end(void);
extern s32 cur_obj_check_if_near_animation_end(void);
extern s32 cur_obj_check_if_at_animation_end(void);
extern s32 cur_obj_check_anim_frame(s32);
extern s32 cur_obj_check_anim_frame_in_range(s32, s32);
// extern ? cur_obj_check_frame_prior_current_frame(?);
s32 mario_is_in_air_action(void);
s32 mario_is_dive_sliding(void);
void cur_obj_set_y_vel_and_animation(f32,s32);
void cur_obj_unrender_and_reset_state(s32,s32);
// extern ? obj_move_after_thrown_or_dropped(?);
void cur_obj_get_thrown_or_placed(f32,f32,s32);
extern void cur_obj_get_dropped(void);
extern void cur_obj_set_model(s32);
// extern ? mario_set_flag(?);
s32 cur_obj_clear_interact_status_flag(s32);
extern void obj_mark_for_deletion(struct Object *);
void cur_obj_disable(void);
extern void cur_obj_become_intangible(void);
extern void cur_obj_become_tangible(void);
extern void obj_become_tangible(struct Object*);
void cur_obj_update_floor_height(void);
struct Surface* cur_obj_update_floor_height_and_get_floor(void);
// extern ? apply_drag_to_value(?);
void cur_obj_apply_drag_xz(f32);
// extern ? cur_obj_move_xz(?);
// extern ? cur_obj_move_update_underwater_flags(?);
// extern ? cur_obj_move_update_ground_air_flags(?);
// extern ? cur_obj_move_y_and_get_water_level(?);
void cur_obj_move_y(f32,f32,f32);
// extern ? clear_move_flag(?);
// extern ? cur_obj_unused_resolve_wall_collisions(?);
extern s16 abs_angle_diff(s16, s16);
extern void cur_obj_move_xz_using_fvel_and_yaw(void);
extern void cur_obj_move_y_with_terminal_vel(void);
void cur_obj_compute_vel_xz(void);
f32 increment_velocity_toward_range(f32,f32,f32,f32);
extern s32 obj_check_if_collided_with_object(struct Object *, struct Object *);
void cur_obj_set_behavior(const BehaviorScript *);
void obj_set_behavior(struct Object *, const BehaviorScript *);
extern s32 cur_obj_has_behavior(const BehaviorScript *);
s32 obj_has_behavior(struct Object *, const BehaviorScript *);
f32 cur_obj_lateral_dist_from_mario_to_home(void);
extern f32 cur_obj_lateral_dist_to_home(void);
// extern ? obj_outside_home_square(?);
// extern ? obj_outside_home_rectangle(?);
extern void cur_obj_set_pos_to_home(void);
void cur_obj_set_pos_to_home_and_stop(void);
extern void cur_obj_shake_y(f32);
void cur_obj_start_cam_event(struct Object *obj, s32 cameraEvent);
// extern ? set_mario_interact_hoot_if_in_range(?);
void obj_set_billboard(struct Object *a0);
void cur_obj_set_hitbox_radius_and_height(f32,f32);
void cur_obj_set_hurtbox_radius_and_height(f32,f32);
// extern ? obj_spawn_loot_coins(?);
// extern ? obj_spawn_loot_blue_coins(?);
extern void obj_spawn_loot_yellow_coins(struct Object *, s32, f32);
void cur_obj_spawn_loot_coin_at_mario_pos(void);
// extern ? obj_abs_y_dist_to_home(?);
// extern ? cur_obj_advance_looping_anim(?);
// extern ? obj_detect_steep_floor(?);
s32 cur_obj_resolve_wall_collisions(void);
// extern ? obj_update_floor(?);
// extern ? obj_update_floor_and_resolve_wall_collisions(?);
extern void cur_obj_update_floor_and_walls(void);
extern void cur_obj_move_standard(s16);
// extern ? obj_within_12k_bounds(?);
void cur_obj_move_using_vel_and_gravity(void);
void cur_obj_move_using_fvel_and_gravity(void);
// extern ? set_object_pos_relative(?);
s16 cur_obj_angle_to_home(void);
void obj_set_gfx_pos_at_obj_pos(struct Object*,struct Object*);
extern void obj_translate_local(struct Object*,s16,s16);
extern void obj_build_transform_from_pos_and_angle(struct Object *, s16, s16);
extern void obj_set_throw_matrix_from_transform(struct Object *);
extern void obj_build_transform_relative_to_parent(struct Object *);
// extern ? obj_create_transform_from_self(?);
// extern ? obj_rotate_move_angle_using_vel(?);
void  cur_obj_rotate_face_angle_using_vel(void);
// extern ? obj_set_face_angle_to_move_angle(?);
extern s32 cur_obj_follow_path(UNUSED s32);
extern void chain_segment_init(struct ChainSegment *);
extern f32 random_f32_around_zero(f32);
void obj_scale_random(struct Object*,f32,f32);
extern void obj_translate_xyz_random(struct Object *, f32);
extern void obj_translate_xz_random(struct Object *, f32);
// extern ? obj_build_vel_from_transform(?);
void cur_obj_set_pos_via_transform(void);
void cur_obj_spawn_particles(struct SpawnParticlesInfo *sp28);
extern s16 cur_obj_reflect_move_angle_off_wall(void);

#define WAYPOINT_FLAGS_END -1
#define WAYPOINT_FLAGS_INITIALIZED 0x8000
#define WAYPOINT_MASK_00FF 0x00FF
#define WAYPOINT_FLAGS_PLATFORM_ON_TRACK_PAUSE 3

#define PATH_NONE 0
#define PATH_REACHED_END -1
#define PATH_REACHED_WAYPOINT 1

struct GraphNode_802A45E4 {
    /*0x00*/ s8 filler0[0x18 - 0x00];
    /*0x18*/ s16 unk18;
    /*0x1A*/ s16 unk1A;
    /*0x1C*/ s16 unk1C;
    /*0x1E*/ s16 unk1E;
    /*0x20*/ s16 unk20;
    /*0x22*/ s16 unk22;
};

extern void obj_set_hitbox(struct Object* obj, struct ObjectHitbox *arg1);
s32 signum_positive(s32);
extern f32 absf(f32);
extern s32 absi(s32 a0);
s32 cur_obj_wait_then_blink(s32 a0, s32 a1);
s32 cur_obj_is_mario_ground_pounding_platform(void);
extern void spawn_mist_particles(void);
extern void spawn_mist_particles_with_sound(u32 sp18);
void cur_obj_push_mario_away(f32);
void cur_obj_push_mario_away_from_cylinder(f32 sp20, f32 sp24);
// extern ? bhv_dust_smoke_loop(?);
s32 cur_obj_set_direction_table(s8*);
s32 cur_obj_progress_direction_table(void);
// extern ? stub_obj_helpers_3(?);
extern void cur_obj_scale_over_time(s32,s32,f32,f32);
void cur_obj_set_pos_to_home_with_debug(void);
extern s32 cur_obj_is_mario_on_platform(void);
// extern ? obj_shake_y_until(?);
s32 cur_obj_move_up_and_down(s32);
void cur_obj_call_action_function(void(*[])(void));
// extern ? spawn_star_with_no_lvl_exit(?);
// extern ? spawn_base_star_with_no_lvl_exit(?);
s32 bit_shift_left(s32);
s32 cur_obj_mario_far_away(void);
s32 is_mario_moving_fast_or_in_air(s32);
s32 is_item_in_array(s8,s8*);
extern void bhv_init_room(void); // 802A3978
extern void cur_obj_enable_rendering_if_mario_in_room(void);
s32 cur_obj_set_hitbox_and_die_if_attacked(struct ObjectHitbox*,s32,s32);
void obj_explode_and_spawn_coins(f32 sp18, s32 sp1C);
void obj_set_collision_data(struct Object*, const void*);
void cur_obj_if_hit_wall_bounce_away(void);
s32 cur_obj_hide_if_mario_far_away_y(f32);
extern Gfx *geo_offset_klepto_held_object(s32 run, struct GraphNode *node, UNUSED f32 mtx[4][4]);
// extern ? geo_offset_klepto_debug(?);
s32 obj_is_hidden(struct Object*);
extern void enable_time_stop(void);
extern void disable_time_stop(void);
void set_time_stop_flags(s32);
void clear_time_stop_flags(s32);
s32 cur_obj_can_mario_activate_textbox(f32,f32,s32);
extern s32 cur_obj_can_mario_activate_textbox_2(f32 sp18, f32 sp1C);
// extern ? obj_end_dialog(?);
s32 cur_obj_update_dialog(s32 arg0, s32 dialogFlags, s32 dialogID, s32 unused);
s32 cur_obj_update_dialog_with_cutscene(s32 arg0, s32 dialogFlags, s32 cutsceneTable, s32 dialogID);
s32 cur_obj_has_model(u16);
extern void cur_obj_align_gfx_with_floor(void);
// extern ? mario_is_within_rectangle(?);
void cur_obj_shake_screen(s32 shake);
extern s32 obj_attack_collided_from_other_object(struct Object *obj);
s32 cur_obj_was_attacked_or_ground_pounded(void);
void obj_copy_behavior_params(struct Object*,struct Object*);
void cur_obj_init_animation_and_anim_frame(s32,s32);
s32 cur_obj_init_animation_and_check_if_near_end(s32);
void cur_obj_init_animation_and_extend_if_at_end(s32);
s32 cur_obj_check_grabbed_mario(void);
s32 player_performed_grab_escape_action(void);
// extern ? cur_obj_unused_play_footstep_sound(?);
// extern ? enable_time_stop_including_mario(?);
extern void disable_time_stop_including_mario(void);
s32 cur_obj_check_interacted(void);
void cur_obj_spawn_loot_blue_coin(void);

#ifndef VERSION_JP
void cur_obj_spawn_star_at_y_offset(f32 f12, f32 f14, f32 a2, f32 a3);
#endif

#endif /* OBJECT_HELPERS_H */
