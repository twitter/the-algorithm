#include <ultra64.h>

#include "sm64.h"
#include "behavior_actions.h"
#include "engine/behavior_script.h"
#include "camera.h"
#include "game_init.h"
#include "engine/math_util.h"
#include "object_helpers.h"
#include "mario_actions_cutscene.h"
#include "behavior_data.h"
#include "mario.h"
#include "engine/surface_collision.h"
#include "obj_behaviors_2.h"
#include "audio/external.h"
#include "seq_ids.h"
#include "dialog_ids.h"
#include "level_update.h"
#include "memory.h"
#include "platform_displacement.h"
#include "rendering_graph_node.h"
#include "engine/surface_load.h"
#include "obj_behaviors.h"
#include "object_constants.h"
#include "interaction.h"
#include "object_list_processor.h"
#include "spawn_sound.h"
#include "geo_misc.h"
#include "save_file.h"
#include "level_table.h"

extern struct Animation *wiggler_seg5_anims_0500C874[];
extern struct Animation *spiny_egg_seg5_anims_050157E4[];
extern struct ObjectNode *gObjectLists;
extern u8 jrb_seg7_trajectory_unagi_1[];
extern u8 jrb_seg7_trajectory_unagi_2[];
extern u8 dorrie_seg6_collision_0600FBB8[];
extern u8 dorrie_seg6_collision_0600F644[];
extern u8 ssl_seg7_collision_070284B0[];
extern u8 ssl_seg7_collision_07028274[];
extern u8 ssl_seg7_collision_07028370[];
extern u8 ssl_seg7_collision_070282F8[];
extern u8 ccm_seg7_trajectory_penguin_race[];
extern u8 bob_seg7_trajectory_koopa[];
extern u8 thi_seg7_trajectory_koopa[];
extern u8 rr_seg7_collision_07029038[];
extern u8 ccm_seg7_collision_070163F8[];
extern u8 checkerboard_platform_seg8_collision_0800D710[];
extern u8 bitfs_seg7_collision_070157E0[];
extern u8 rr_seg7_trajectory_0702EC3C[];
extern u8 rr_seg7_trajectory_0702ECC0[];
extern u8 ccm_seg7_trajectory_0701669C[];
extern u8 bitfs_seg7_trajectory_070159AC[];
extern u8 hmc_seg7_trajectory_0702B86C[];
extern u8 lll_seg7_trajectory_0702856C[];
extern u8 lll_seg7_trajectory_07028660[];
extern u8 rr_seg7_trajectory_0702ED9C[];
extern u8 rr_seg7_trajectory_0702EEE0[];
extern u8 bitdw_seg7_collision_0700F70C[];
extern u8 bits_seg7_collision_0701ADD8[];
extern u8 bits_seg7_collision_0701AE5C[];
extern u8 bob_seg7_collision_bridge[];
extern u8 bitfs_seg7_collision_07015928[];
extern u8 rr_seg7_collision_07029750[];
extern u8 rr_seg7_collision_07029858[];
extern u8 vcutm_seg7_collision_0700AC44[];
extern u8 bits_seg7_collision_0701ACAC[];
extern u8 bits_seg7_collision_0701AC28[];
extern u8 bitdw_seg7_collision_0700F7F0[];
extern u8 bitdw_seg7_collision_0700F898[];
extern u8 ttc_seg7_collision_07014F70[];
extern u8 ttc_seg7_collision_07015008[];
extern u8 ttc_seg7_collision_070152B4[];
extern u8 ttc_seg7_collision_070153E0[];
extern u8 ttc_seg7_collision_07015584[];
extern u8 ttc_seg7_collision_07015650[];
extern u8 ttc_seg7_collision_07015754[];
extern u8 ttc_seg7_collision_070157D8[];
extern u8 bits_seg7_collision_0701A9A0[];
extern u8 bits_seg7_collision_0701AA0C[];
extern u8 bitfs_seg7_collision_07015714[];
extern u8 bitfs_seg7_collision_07015768[];
extern u8 rr_seg7_collision_070295F8[];
extern u8 rr_seg7_collision_0702967C[];
extern u8 bitdw_seg7_collision_0700F688[];
extern u8 bits_seg7_collision_0701AA84[];
extern u8 rr_seg7_collision_07029508[];
extern u8 bits_seg7_collision_0701B734[];
extern u8 bits_seg7_collision_0701B59C[];
extern u8 bits_seg7_collision_0701B404[];
extern u8 bits_seg7_collision_0701B26C[];
extern u8 bits_seg7_collision_0701B0D4[];
extern u8 bitdw_seg7_collision_0700FD9C[];
extern u8 bitdw_seg7_collision_0700FC7C[];
extern u8 bitdw_seg7_collision_0700FB5C[];
extern u8 bitdw_seg7_collision_0700FA3C[];
extern u8 bitdw_seg7_collision_0700F91C[];
extern u8 rr_seg7_collision_0702A6B4[];
extern u8 rr_seg7_collision_0702A32C[];
extern u8 rr_seg7_collision_07029FA4[];
extern u8 rr_seg7_collision_07029C1C[];
extern u8 rr_seg7_collision_07029924[];
extern u8 bits_seg7_collision_0701AD54[];
extern u8 bitfs_seg7_collision_070157E0[];
extern u8 bitfs_seg7_collision_07015124[];
extern struct Animation *spiny_seg5_anims_05016EAC[];

#define POS_OP_SAVE_POSITION 0
#define POS_OP_COMPUTE_VELOCITY 1
#define POS_OP_RESTORE_POSITION 2

#define o gCurrentObject

/**
 * The treadmill that plays sounds and controls the others on random setting.
 */
struct Object *sMasterTreadmill;


f32 sObjSavedPosX;
f32 sObjSavedPosY;
f32 sObjSavedPosZ;

void shelled_koopa_attack_handler(s32 attackType);
void wiggler_jumped_on_attack_handler(void);
void huge_goomba_weakly_attacked(void);

static s32 obj_is_rendering_enabled(void) {
    if (o->header.gfx.node.flags & GRAPH_RENDER_ACTIVE) {
        return TRUE;
    } else {
        return FALSE;
    }
}

static s16 obj_get_pitch_from_vel(void) {
    return -atan2s(o->oForwardVel, o->oVelY);
}

/**
 * Show dialog proposing a race.
 * If the player accepts the race, then leave time stop enabled and mario in the
 * text action so that the racing object can wait before starting the race.
 * If the player declines the race, then disable time stop and allow mario to
 * move again.
 */
static s32 obj_update_race_proposition_dialog(s16 dialogID) {
    s32 dialogResponse =
        cur_obj_update_dialog_with_cutscene(2, DIALOG_UNK2_FLAG_0 | DIALOG_UNK2_LEAVE_TIME_STOP_ENABLED, CUTSCENE_RACE_DIALOG, dialogID);

    if (dialogResponse == 2) {
        set_mario_npc_dialog(0);
        disable_time_stop_including_mario();
    }

    return dialogResponse;
}

static void obj_set_dist_from_home(f32 distFromHome) {
    o->oPosX = o->oHomeX + distFromHome * coss(o->oMoveAngleYaw);
    o->oPosZ = o->oHomeZ + distFromHome * sins(o->oMoveAngleYaw);
}

static s32 obj_is_near_to_and_facing_mario(f32 maxDist, s16 maxAngleDiff) {
    if (o->oDistanceToMario < maxDist
        && abs_angle_diff(o->oMoveAngleYaw, o->oAngleToMario) < maxAngleDiff) {
        return TRUE;
    }
    return FALSE;
}

//! Although having no return value, this function
//! must be u32 to match other functions on -O2.
static BAD_RETURN(u32) obj_perform_position_op(s32 op) {
    switch (op) {
        case POS_OP_SAVE_POSITION:
            sObjSavedPosX = o->oPosX;
            sObjSavedPosY = o->oPosY;
            sObjSavedPosZ = o->oPosZ;
            break;

        case POS_OP_COMPUTE_VELOCITY:
            o->oVelX = o->oPosX - sObjSavedPosX;
            o->oVelY = o->oPosY - sObjSavedPosY;
            o->oVelZ = o->oPosZ - sObjSavedPosZ;
            break;

        case POS_OP_RESTORE_POSITION:
            o->oPosX = sObjSavedPosX;
            o->oPosY = sObjSavedPosY;
            o->oPosZ = sObjSavedPosZ;
            break;
    }
}

static void platform_on_track_update_pos_or_spawn_ball(s32 ballIndex, f32 x, f32 y, f32 z) {
    struct Object *trackBall;
    struct Waypoint *initialPrevWaypoint;
    struct Waypoint *nextWaypoint;
    struct Waypoint *prevWaypoint;
    UNUSED s32 unused;
    f32 amountToMove;
    f32 dx;
    f32 dy;
    f32 dz;
    f32 distToNextWaypoint;

    if (ballIndex == 0 || ((u16)(o->oBehParams >> 16) & 0x0080)) {
        initialPrevWaypoint = o->oPlatformOnTrackPrevWaypoint;
        nextWaypoint = initialPrevWaypoint;

        if (ballIndex != 0) {
            amountToMove = 300.0f * ballIndex;
        } else {
            obj_perform_position_op(POS_OP_SAVE_POSITION);
            o->oPlatformOnTrackPrevWaypointFlags = 0;
            amountToMove = o->oForwardVel;
        }

        do {
            prevWaypoint = nextWaypoint;

            nextWaypoint += 1;
            if (nextWaypoint->flags == WAYPOINT_FLAGS_END) {
                if (ballIndex == 0) {
                    o->oPlatformOnTrackPrevWaypointFlags = WAYPOINT_FLAGS_END;
                }

                if (((u16)(o->oBehParams >> 16) & PLATFORM_ON_TRACK_BP_RETURN_TO_START)) {
                    nextWaypoint = o->oPlatformOnTrackStartWaypoint;
                } else {
                    return;
                }
            }

            dx = nextWaypoint->pos[0] - x;
            dy = nextWaypoint->pos[1] - y;
            dz = nextWaypoint->pos[2] - z;

            distToNextWaypoint = sqrtf(dx * dx + dy * dy + dz * dz);

            // Move directly to the next waypoint, even if it's farther away
            // than amountToMove
            amountToMove -= distToNextWaypoint;
            x += dx;
            y += dy;
            z += dz;
        } while (amountToMove > 0.0f);

        // If we moved farther than amountToMove, move in the opposite direction
        // No risk of near-zero division: If distToNextWaypoint is close to
        // zero, then that means we didn't cross a waypoint this frame (since
        // otherwise distToNextWaypoint would equal the distance between two
        // waypoints, which should never be that small). But this implies that
        // amountToMove - distToNextWaypoint <= 0, and amountToMove is at least
        // 0.1 (from platform on track behavior).
        distToNextWaypoint = amountToMove / distToNextWaypoint;
        x += dx * distToNextWaypoint;
        y += dy * distToNextWaypoint;
        z += dz * distToNextWaypoint;

        if (ballIndex != 0) {
            trackBall = spawn_object_relative(o->oPlatformOnTrackBaseBallIndex + ballIndex, 0, 0, 0, o,
                                              MODEL_TRAJECTORY_MARKER_BALL, bhvTrackBall);

            if (trackBall != NULL) {
                trackBall->oPosX = x;
                trackBall->oPosY = y;
                trackBall->oPosZ = z;
            }
        } else {
            if (prevWaypoint != initialPrevWaypoint) {
                if (o->oPlatformOnTrackPrevWaypointFlags == 0) {
                    o->oPlatformOnTrackPrevWaypointFlags = initialPrevWaypoint->flags;
                }
                o->oPlatformOnTrackPrevWaypoint = prevWaypoint;
            }

            o->oPosX = x;
            o->oPosY = y;
            o->oPosZ = z;

            obj_perform_position_op(POS_OP_COMPUTE_VELOCITY);

            o->oPlatformOnTrackPitch =
                atan2s(sqrtf(o->oVelX * o->oVelX + o->oVelZ * o->oVelZ), -o->oVelY);
            o->oPlatformOnTrackYaw = atan2s(o->oVelZ, o->oVelX);
        }
    }
}

static void cur_obj_spin_all_dimensions(f32 arg0, f32 arg1) {
    f32 val24;
    f32 val20;
    f32 val1C;
    f32 c;
    f32 s;
    f32 val10;
    f32 val0C;
    f32 val08;
    f32 val04;
    f32 val00;

    if (o->oForwardVel == 0.0f) {
        val24 = val20 = val1C = 0.0f;

        if (o->oMoveFlags & OBJ_MOVE_IN_AIR) {
            val20 = 50.0f;
        } else {
            if (o->oFaceAnglePitch < 0) {
                val1C = -arg0;
            } else if (o->oFaceAnglePitch > 0) {
                val1C = arg0;
            }

            if (o->oFaceAngleRoll < 0) {
                val24 = -arg1;
            } else if (o->oFaceAngleRoll > 0) {
                val24 = arg1;
            }
        }

        c = coss(o->oFaceAnglePitch);
        s = sins(o->oFaceAnglePitch);
        val08 = val1C * c + val20 * s;
        val0C = val20 * c - val1C * s;

        c = coss(o->oFaceAngleRoll);
        s = sins(o->oFaceAngleRoll);
        val04 = val24 * c + val0C * s;
        val0C = val0C * c - val24 * s;

        c = coss(o->oFaceAngleYaw);
        s = sins(o->oFaceAngleYaw);
        val10 = val04 * c - val08 * s;
        val08 = val08 * c + val04 * s;

        val04 = val24 * c - val1C * s;
        val00 = val1C * c + val24 * s;

        o->oPosX = o->oHomeX - val04 + val10;
        o->oGraphYOffset = val20 - val0C;
        o->oPosZ = o->oHomeZ + val00 - val08;
    }
}

static void obj_rotate_yaw_and_bounce_off_walls(s16 targetYaw, s16 turnAmount) {
    if (o->oMoveFlags & OBJ_MOVE_HIT_WALL) {
        targetYaw = cur_obj_reflect_move_angle_off_wall();
    }
    cur_obj_rotate_yaw_toward(targetYaw, turnAmount);
}

static s16 obj_get_pitch_to_home(f32 latDistToHome) {
    return atan2s(latDistToHome, o->oPosY - o->oHomeY);
}

static void obj_compute_vel_from_move_pitch(f32 speed) {
    o->oForwardVel = speed * coss(o->oMoveAnglePitch);
    o->oVelY = speed * -sins(o->oMoveAnglePitch);
}

static s32 clamp_s16(s16 *value, s16 minimum, s16 maximum) {
    if (*value <= minimum) {
        *value = minimum;
    } else if (*value >= maximum) {
        *value = maximum;
    } else {
        return FALSE;
    }

    return TRUE;
}

static s32 clamp_f32(f32 *value, f32 minimum, f32 maximum) {
    if (*value <= minimum) {
        *value = minimum;
    } else if (*value >= maximum) {
        *value = maximum;
    } else {
        return FALSE;
    }

    return TRUE;
}

static void cur_obj_init_anim_extend(s32 arg0) {
    cur_obj_init_animation_with_sound(arg0);
    cur_obj_extend_animation_if_at_end();
}

static s32 cur_obj_init_anim_and_check_if_end(s32 arg0) {
    cur_obj_init_animation_with_sound(arg0);
    return cur_obj_check_if_near_animation_end();
}

static s32 cur_obj_init_anim_check_frame(s32 arg0, s32 arg1) {
    cur_obj_init_animation_with_sound(arg0);
    return cur_obj_check_anim_frame(arg1);
}

static s32 cur_obj_set_anim_if_at_end(s32 arg0) {
    if (cur_obj_check_if_at_animation_end()) {
        cur_obj_init_animation_with_sound(arg0);
        return TRUE;
    }
    return FALSE;
}

static s32 cur_obj_play_sound_at_anim_range(s8 arg0, s8 arg1, u32 sound) {
    s32 val04;

    if ((val04 = o->header.gfx.unk38.animAccel / 0x10000) <= 0) {
        val04 = 1;
    }

    if (cur_obj_check_anim_frame_in_range(arg0, val04) || cur_obj_check_anim_frame_in_range(arg1, val04)) {
        cur_obj_play_sound_2(sound);
        return TRUE;
    }

    return FALSE;
}

static s16 obj_turn_pitch_toward_mario(f32 targetOffsetY, s16 turnAmount) {
    s16 targetPitch;

    o->oPosY -= targetOffsetY;
    targetPitch = obj_turn_toward_object(o, gMarioObject, O_MOVE_ANGLE_PITCH_INDEX, turnAmount);
    o->oPosY += targetOffsetY;

    return targetPitch;
}

static s32 approach_f32_ptr(f32 *px, f32 target, f32 delta) {
    if (*px > target) {
        delta = -delta;
    }

    *px += delta;

    if ((*px - target) * delta >= 0) {
        *px = target;
        return TRUE;
    }
    return FALSE;
}

static s32 obj_forward_vel_approach(f32 target, f32 delta) {
    return approach_f32_ptr(&o->oForwardVel, target, delta);
}

static s32 obj_y_vel_approach(f32 target, f32 delta) {
    return approach_f32_ptr(&o->oVelY, target, delta);
}

static s32 obj_move_pitch_approach(s16 target, s16 delta) {
    o->oMoveAnglePitch = approach_s16_symmetric(o->oMoveAnglePitch, target, delta);

    if ((s16) o->oMoveAnglePitch == target) {
        return TRUE;
    }

    return FALSE;
}

static s32 obj_face_pitch_approach(s16 targetPitch, s16 deltaPitch) {
    o->oFaceAnglePitch = approach_s16_symmetric(o->oFaceAnglePitch, targetPitch, deltaPitch);

    if ((s16) o->oFaceAnglePitch == targetPitch) {
        return TRUE;
    }

    return FALSE;
}

static s32 obj_face_yaw_approach(s16 targetYaw, s16 deltaYaw) {
    o->oFaceAngleYaw = approach_s16_symmetric(o->oFaceAngleYaw, targetYaw, deltaYaw);

    if ((s16) o->oFaceAngleYaw == targetYaw) {
        return TRUE;
    }

    return FALSE;
}

static s32 obj_face_roll_approach(s16 targetRoll, s16 deltaRoll) {
    o->oFaceAngleRoll = approach_s16_symmetric(o->oFaceAngleRoll, targetRoll, deltaRoll);

    if ((s16) o->oFaceAngleRoll == targetRoll) {
        return TRUE;
    }

    return FALSE;
}

static s32 obj_smooth_turn(s16 *angleVel, s32 *angle, s16 targetAngle, f32 targetSpeedProportion,
                           s16 accel, s16 minSpeed, s16 maxSpeed) {
    s16 currentSpeed;
    s16 currentAngle = (s16)(*angle);

    *angleVel =
        approach_s16_symmetric(*angleVel, (targetAngle - currentAngle) * targetSpeedProportion, accel);

    currentSpeed = absi(*angleVel);
    clamp_s16(&currentSpeed, minSpeed, maxSpeed);

    *angle = approach_s16_symmetric(*angle, targetAngle, currentSpeed);
    return (s16)(*angle) == targetAngle;
}

static void obj_roll_to_match_yaw_turn(s16 targetYaw, s16 maxRoll, s16 rollSpeed) {
    s16 targetRoll = o->oMoveAngleYaw - targetYaw;
    clamp_s16(&targetRoll, -maxRoll, maxRoll);
    obj_face_roll_approach(targetRoll, rollSpeed);
}

static s16 random_linear_offset(s16 base, s16 range) {
    return base + (s16)(range * random_float());
}

static s16 random_mod_offset(s16 base, s16 step, s16 mod) {
    return base + step * (random_u16() % mod);
}

static s16 obj_random_fixed_turn(s16 delta) {
    return o->oMoveAngleYaw + (s16) random_sign() * delta;
}

/**
 * Begin by increasing the object's scale by *scaleVel, and slowly decreasing
 * scaleVel. Once the object starts to shrink, wait a bit, and then begin to
 * scale the object toward endScale. The first time it reaches below
 * shootFireScale during this time, return 1.
 * Return -1 once it's reached endScale.
 */
static s32 obj_grow_then_shrink(f32 *scaleVel, f32 shootFireScale, f32 endScale) {
    if (o->oTimer < 2) {
        o->header.gfx.scale[0] += *scaleVel;

        if ((*scaleVel -= 0.01f) > -0.03f) {
            o->oTimer = 0;
        }
    } else if (o->oTimer > 10) {
        if (approach_f32_ptr(&o->header.gfx.scale[0], endScale, 0.05f)) {
            return -1;
        } else if (*scaleVel != 0.0f && o->header.gfx.scale[0] < shootFireScale) {
            *scaleVel = 0.0f;
            return 1;
        }
    }

    return 0;
}

static s32 oscillate_toward(s32 *value, f32 *vel, s32 target, f32 velCloseToZero, f32 accel,
                            f32 slowdown) {
    s32 startValue = *value;
    *value += (s32) *vel;

    if (*value == target
        || ((*value - target) * (startValue - target) < 0 && *vel > -velCloseToZero
            && *vel < velCloseToZero)) {
        *value = target;
        *vel = 0.0f;
        return TRUE;
    } else {
        if (*value >= target) {
            accel = -accel;
        }
        if (*vel * accel < 0.0f) {
            accel *= slowdown;
        }

        *vel += accel;
    }

    return FALSE;
}

static void obj_update_blinking(s32 *blinkTimer, s16 baseCycleLength, s16 cycleLengthRange,
                                s16 blinkLength) {
    if (*blinkTimer != 0) {
        *blinkTimer -= 1;
    } else {
        *blinkTimer = random_linear_offset(baseCycleLength, cycleLengthRange);
    }

    if (*blinkTimer > blinkLength) {
        o->oAnimState = 0;
    } else {
        o->oAnimState = 1;
    }
}

static s32 obj_resolve_object_collisions(s32 *targetYaw) {
    struct Object *otherObject;
    f32 dx;
    f32 dz;
    s16 angle;
    f32 radius;
    f32 otherRadius;
    f32 relativeRadius;
    f32 newCenterX;
    f32 newCenterZ;

    if (o->numCollidedObjs != 0) {
        otherObject = o->collidedObjs[0];
        if (otherObject != gMarioObject) {
            //! If one object moves after collisions are detected and this code
            //  runs, the objects can move toward each other (transport cloning)

            dx = otherObject->oPosX - o->oPosX;
            dz = otherObject->oPosZ - o->oPosZ;
            angle = atan2s(dx, dz); //! This should be atan2s(dz, dx)

            radius = o->hitboxRadius;
            otherRadius = otherObject->hitboxRadius;
            relativeRadius = radius / (radius + otherRadius);

            newCenterX = o->oPosX + dx * relativeRadius;
            newCenterZ = o->oPosZ + dz * relativeRadius;

            o->oPosX = newCenterX - radius * coss(angle);
            o->oPosZ = newCenterZ - radius * sins(angle);

            otherObject->oPosX = newCenterX + otherRadius * coss(angle);
            otherObject->oPosZ = newCenterZ + otherRadius * sins(angle);

            if (targetYaw != NULL && abs_angle_diff(o->oMoveAngleYaw, angle) < 0x4000) {
                // Bounce off object (or it would, if the above atan2s bug
                // were fixed)
                *targetYaw = (s16)(angle - o->oMoveAngleYaw + angle + 0x8000);
                return TRUE;
            }
        }
    }

    return FALSE;
}

static s32 obj_bounce_off_walls_edges_objects(s32 *targetYaw) {
    if (o->oMoveFlags & OBJ_MOVE_HIT_WALL) {
        *targetYaw = cur_obj_reflect_move_angle_off_wall();
    } else if (o->oMoveFlags & OBJ_MOVE_HIT_EDGE) {
        *targetYaw = (s16)(o->oMoveAngleYaw + 0x8000);
    } else if (!obj_resolve_object_collisions(targetYaw)) {
        return FALSE;
    }

    return TRUE;
}

static s32 obj_resolve_collisions_and_turn(s16 targetYaw, s16 turnSpeed) {
    obj_resolve_object_collisions(NULL);

    if (cur_obj_rotate_yaw_toward(targetYaw, turnSpeed)) {
        return FALSE;
    } else {
        return TRUE;
    }
}

static void obj_die_if_health_non_positive(void) {
    if (o->oHealth <= 0) {
        if (o->oDeathSound == 0) {
            spawn_mist_particles_with_sound(SOUND_OBJ_DEFAULT_DEATH);
        } else if (o->oDeathSound > 0) {
            spawn_mist_particles_with_sound(o->oDeathSound);
        } else {
            spawn_mist_particles();
        }

        if ((s32)o->oNumLootCoins < 0) {
            spawn_object(o, MODEL_BLUE_COIN, bhvMrIBlueCoin);
        } else {
            obj_spawn_loot_yellow_coins(o, o->oNumLootCoins, 20.0f);
        }
        // This doesn't do anything
        obj_spawn_loot_yellow_coins(o, o->oNumLootCoins, 20.0f);

        if (o->oHealth < 0) {
            cur_obj_hide();
            cur_obj_become_intangible();
        } else {
            obj_mark_for_deletion(o);
        }
    }
}

static void obj_unused_die(void) {
    o->oHealth = 0;
    obj_die_if_health_non_positive();
}

static void obj_set_knockback_action(s32 attackType) {
    switch (attackType) {
        case ATTACK_KICK_OR_TRIP:
        case ATTACK_FAST_ATTACK:
            o->oAction = OBJ_ACT_VERTICAL_KNOCKBACK;
            o->oForwardVel = 20.0f;
            o->oVelY = 50.0f;
            break;

        default:
            o->oAction = OBJ_ACT_HORIZONTAL_KNOCKBACK;
            o->oForwardVel = 50.0f;
            o->oVelY = 30.0f;
            break;
    }

    o->oFlags &= ~OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW;
    o->oMoveAngleYaw = obj_angle_to_object(gMarioObject, o);
}

static void obj_set_squished_action(void) {
    cur_obj_play_sound_2(SOUND_OBJ_STOMPED);
    o->oAction = OBJ_ACT_SQUISHED;
}

static s32 obj_die_if_above_lava_and_health_non_positive(void) {
    if (o->oMoveFlags & OBJ_MOVE_UNDERWATER_ON_GROUND) {
        if (o->oGravity + o->oBuoyancy > 0.0f
            || find_water_level(o->oPosX, o->oPosZ) - o->oPosY < 150.0f) {
            return FALSE;
        }
    } else if (!(o->oMoveFlags & OBJ_MOVE_ABOVE_LAVA)) {
        if (o->oMoveFlags & OBJ_MOVE_ENTERED_WATER) {
            if (o->oWallHitboxRadius < 200.0f) {
                cur_obj_play_sound_2(SOUND_OBJ_DIVING_INTO_WATER);
            } else {
                cur_obj_play_sound_2(SOUND_OBJ_DIVING_IN_WATER);
            }
        }
        return FALSE;
    }

    obj_die_if_health_non_positive();
    return TRUE;
}

static s32 obj_handle_attacks(struct ObjectHitbox *hitbox, s32 attackedMarioAction,
                              u8 *attackHandlers) {
    s32 attackType;

    obj_set_hitbox(o, hitbox);

    //! Die immediately if above lava
    if (obj_die_if_above_lava_and_health_non_positive()) {
        return 1;
    } else if (o->oInteractStatus & INT_STATUS_INTERACTED) {
        if (o->oInteractStatus & INT_STATUS_ATTACKED_MARIO) {
            if (o->oAction != attackedMarioAction) {
                o->oAction = attackedMarioAction;
                o->oTimer = 0;
            }
        } else {
            attackType = o->oInteractStatus & INT_STATUS_ATTACK_MASK;

            switch (attackHandlers[attackType - 1]) {
                case ATTACK_HANDLER_NOP:
                    break;

                case ATTACK_HANDLER_DIE_IF_HEALTH_NON_POSITIVE:
                    obj_die_if_health_non_positive();
                    break;

                case ATTACK_HANDLER_KNOCKBACK:
                    obj_set_knockback_action(attackType);
                    break;

                case ATTACK_HANDLER_SQUISHED:
                    obj_set_squished_action();
                    break;

                case ATTACK_HANDLER_SPECIAL_KOOPA_LOSE_SHELL:
                    shelled_koopa_attack_handler(attackType);
                    break;

                case ATTACK_HANDLER_SET_SPEED_TO_ZERO:
                    obj_set_speed_to_zero();
                    break;

                case ATTACK_HANDLER_SPECIAL_WIGGLER_JUMPED_ON:
                    wiggler_jumped_on_attack_handler();
                    break;

                case ATTACK_HANDLER_SPECIAL_HUGE_GOOMBA_WEAKLY_ATTACKED:
                    huge_goomba_weakly_attacked();
                    break;

                case ATTACK_HANDLER_SQUISHED_WITH_BLUE_COIN:
                    o->oNumLootCoins = -1;
                    obj_set_squished_action();
                    break;
            }

            o->oInteractStatus = 0;
            return attackType;
        }
    }

    o->oInteractStatus = 0;
    return 0;
}

static void obj_act_knockback(UNUSED f32 baseScale) {
    cur_obj_update_floor_and_walls();

    if (o->header.gfx.unk38.curAnim != NULL) {
        cur_obj_extend_animation_if_at_end();
    }

    //! Dies immediately if above lava
    if ((o->oMoveFlags
         & (OBJ_MOVE_MASK_ON_GROUND | OBJ_MOVE_MASK_IN_WATER | OBJ_MOVE_HIT_WALL | OBJ_MOVE_ABOVE_LAVA))
        || (o->oAction == OBJ_ACT_VERTICAL_KNOCKBACK && o->oTimer >= 9)) {
        obj_die_if_health_non_positive();
    }

    cur_obj_move_standard(-78);
}

static void obj_act_squished(f32 baseScale) {
    f32 targetScaleY = baseScale * 0.3f;

    cur_obj_update_floor_and_walls();

    if (o->header.gfx.unk38.curAnim != NULL) {
        cur_obj_extend_animation_if_at_end();
    }

    if (approach_f32_ptr(&o->header.gfx.scale[1], targetScaleY, baseScale * 0.14f)) {
        o->header.gfx.scale[0] = o->header.gfx.scale[2] = baseScale * 2.0f - o->header.gfx.scale[1];

        if (o->oTimer >= 16) {
            obj_die_if_health_non_positive();
        }
    }

    o->oForwardVel = 0.0f;
    cur_obj_move_standard(-78);
}

static s32 obj_update_standard_actions(f32 scale) {
    if (o->oAction < 100) {
        return TRUE;
    } else {
        cur_obj_become_intangible();

        switch (o->oAction) {
            case OBJ_ACT_HORIZONTAL_KNOCKBACK:
            case OBJ_ACT_VERTICAL_KNOCKBACK:
                obj_act_knockback(scale);
                break;

            case OBJ_ACT_SQUISHED:
                obj_act_squished(scale);
                break;
        }

        return FALSE;
    }
}

static s32 obj_check_attacks(struct ObjectHitbox *hitbox, s32 attackedMarioAction) {
    s32 attackType;

    obj_set_hitbox(o, hitbox);

    //! Dies immediately if above lava
    if (obj_die_if_above_lava_and_health_non_positive()) {
        return 1;
    } else if (o->oInteractStatus & INT_STATUS_INTERACTED) {
        if (o->oInteractStatus & INT_STATUS_ATTACKED_MARIO) {
            if (o->oAction != attackedMarioAction) {
                o->oAction = attackedMarioAction;
                o->oTimer = 0;
            }
        } else {
            attackType = o->oInteractStatus & INT_STATUS_ATTACK_MASK;
            obj_die_if_health_non_positive();
            o->oInteractStatus = 0;
            return attackType;
        }
    }

    o->oInteractStatus = 0;
    return 0;
}

static s32 obj_move_for_one_second(s32 endAction) {
    cur_obj_update_floor_and_walls();
    cur_obj_extend_animation_if_at_end();

    if (o->oTimer > 30) {
        o->oAction = endAction;
        return TRUE;
    }

    cur_obj_move_standard(-78);
    return FALSE;
}

/**
 * If we are far from home (> threshold away), then set oAngleToMario to the
 * angle to home and oDistanceToMario to 25000.
 * If we are close to home, but Mario is far from us (> threshold away), then
 * keep oAngleToMario the same and set oDistanceToMario to 20000.
 * If we are close to both home and Mario, then keep both oAngleToMario and
 * oDistanceToMario the same.
 *
 * The point of this function is to avoid having to write extra code to get
 * the object to return to home. When mario is far away and the object is far
 * from home, it could theoretically re-use the "approach mario" logic to approach
 * its home instead.
 * However, most objects that use this function handle the far-from-home case
 * separately anyway.
 * This function causes seemingly erroneous behavior in some objects that try to
 * attack mario (e.g. fly guy shooting fire or lunging), especially when combined
 * with partial updates.
 */
static void treat_far_home_as_mario(f32 threshold) {
    f32 dx = o->oHomeX - o->oPosX;
    f32 dy = o->oHomeY - o->oPosY;
    f32 dz = o->oHomeZ - o->oPosZ;
    f32 distance = sqrtf(dx * dx + dy * dy + dz * dz);

    if (distance > threshold) {
        o->oAngleToMario = atan2s(dz, dx);
        o->oDistanceToMario = 25000.0f;
    } else {
        dx = o->oHomeX - gMarioObject->oPosX;
        dy = o->oHomeY - gMarioObject->oPosY;
        dz = o->oHomeZ - gMarioObject->oPosZ;
        distance = sqrtf(dx * dx + dy * dy + dz * dz);

        if (distance > threshold) {
            o->oDistanceToMario = 20000.0f;
        }
    }
}

#include "behaviors/koopa.inc.c" // TODO: Text arg field name
#include "behaviors/pokey.inc.c"
#include "behaviors/swoop.inc.c"
#include "behaviors/fly_guy.inc.c"
#include "behaviors/goomba.inc.c"
#include "behaviors/chain_chomp.inc.c" // TODO: chain_chomp_sub_act_lunge documentation
#include "behaviors/wiggler.inc.c"     // TODO
#include "behaviors/spiny.inc.c"
#include "behaviors/enemy_lakitu.inc.c" // TODO
#include "behaviors/cloud.inc.c"
#include "behaviors/camera_lakitu.inc.c" // TODO: 104 label, follow cam documentation
#include "behaviors/monty_mole.inc.c"    // TODO
#include "behaviors/platform_on_track.inc.c"
#include "behaviors/seesaw_platform.inc.c"
#include "behaviors/ferris_wheel.inc.c"
#include "behaviors/water_bomb.inc.c" // TODO: Shadow position
#include "behaviors/ttc_rotating_solid.inc.c"
#include "behaviors/ttc_pendulum.inc.c"
#include "behaviors/ttc_treadmill.inc.c" // TODO
#include "behaviors/ttc_moving_bar.inc.c"
#include "behaviors/ttc_cog.inc.c"
#include "behaviors/ttc_pit_block.inc.c"
#include "behaviors/ttc_elevator.inc.c"
#include "behaviors/ttc_2d_rotator.inc.c"
#include "behaviors/ttc_spinner.inc.c"
#include "behaviors/mr_blizzard.inc.c"
#include "behaviors/sliding_platform_2.inc.c"
#include "behaviors/rotating_octagonal_plat.inc.c"
#include "behaviors/animated_floor_switch.inc.c"
#include "behaviors/activated_bf_plat.inc.c"
#include "behaviors/recovery_heart.inc.c"
#include "behaviors/water_bomb_cannon.inc.c"
#include "behaviors/unagi.inc.c"
#include "behaviors/dorrie.inc.c"
#include "behaviors/haunted_chair.inc.c"
#include "behaviors/mad_piano.inc.c"
#include "behaviors/flying_bookend_switch.inc.c"

/**
 * Used by fly guy, piranha plant, and fire spitters.
 */
void obj_spit_fire(s16 relativePosX, s16 relativePosY, s16 relativePosZ, f32 scale, s32 model,
                   f32 startSpeed, f32 endSpeed, s16 movePitch) {
    struct Object *sp2C;

    sp2C = spawn_object_relative_with_scale(1, relativePosX, relativePosY, relativePosZ, scale, o,
                                            model, bhvSmallPiranhaFlame);

    if (sp2C != NULL) {
        sp2C->oSmallPiranhaFlameUnkF4 = startSpeed;
        sp2C->oSmallPiranhaFlameUnkF8 = endSpeed;
        sp2C->oSmallPiranhaFlameUnkFC = model;
        sp2C->oMoveAnglePitch = movePitch;
    }
}

#include "behaviors/fire_piranha_plant.inc.c"
#include "behaviors/fire_spitter.inc.c"
#include "behaviors/flame.inc.c"
#include "behaviors/snufit.inc.c"
#include "behaviors/horizontal_grindel.inc.c"
#include "behaviors/eyerok.inc.c"
#include "behaviors/klepto.inc.c"
#include "behaviors/bird.inc.c"
#include "behaviors/racing_penguin.inc.c"
#include "behaviors/coffin.inc.c"
#include "behaviors/clam.inc.c"
#include "behaviors/skeeter.inc.c"
#include "behaviors/swing_platform.inc.c"
#include "behaviors/donut_platform.inc.c"
#include "behaviors/ddd_pole.inc.c"
#include "behaviors/reds_star_marker.inc.c"
#include "behaviors/triplet_butterfly.inc.c"
#include "behaviors/bubba.inc.c"
