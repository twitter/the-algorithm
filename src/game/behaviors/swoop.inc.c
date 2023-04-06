
/**
 * Behavior for bhvSwoop.
 * Has a native room.
 */

/**
 * Hitbox for swoop.
 */
static struct ObjectHitbox sSwoopHitbox = {
    /* interactType:      */ INTERACT_HIT_FROM_BELOW,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 0,
    /* numLootCoins:      */ 1,
    /* radius:            */ 100,
    /* height:            */ 80,
    /* hurtboxRadius:     */ 70,
    /* hurtboxHeight:     */ 70,
};

/**
 * If necessary, grow to full size. Wait for mario to enter range, then turn
 * toward him and enter the move action.
 */
static void swoop_act_idle(void) {
    cur_obj_init_animation_with_sound(1);

    if (approach_f32_ptr(&o->header.gfx.scale[0], 1.0f, 0.05f) && o->oDistanceToMario < 1500.0f) {
        if (cur_obj_rotate_yaw_toward(o->oAngleToMario, 800)) {
            cur_obj_play_sound_2(SOUND_OBJ2_SWOOP);
            o->oAction = SWOOP_ACT_MOVE;
            o->oVelY = -12.0f;
        }
    }

    o->oFaceAngleRoll = 0x8000;
}

/**
 * Swoop downward toward mario. Stop moving downward and speed up after reaching
 * him. Return to home once mario is far away.
 */
static void swoop_act_move(void) {
    cur_obj_init_animation_with_accel_and_sound(0, 2.0f);
    if (cur_obj_check_if_near_animation_end()) {
        cur_obj_play_sound_2(SOUND_OBJ_UNKNOWN6);
    }

    if (o->oForwardVel == 0.0f) {
        // If we haven't started moving yet, begin swooping
        if (obj_face_roll_approach(0, 2500)) {
            o->oForwardVel = 10.0f;
            o->oVelY = -10.0f;
        }
    } else if (cur_obj_mario_far_away()) {
        // If mario far away, reset
        o->oAction = SWOOP_ACT_IDLE;
        cur_obj_set_pos_to_home();
        o->header.gfx.scale[0] = o->oForwardVel = o->oVelY = 0.0f;
        o->oFaceAngleRoll = 0;
    } else {
        if (o->oSwoopBonkCountdown != 0) {
            o->oSwoopBonkCountdown -= 1;
        } else if (o->oVelY != 0.0f) {
            // If we're not done swooping, turn toward mario. When between
            // 0 and 200 units above mario, increase speed and stop swooping
            o->oSwoopTargetYaw = o->oAngleToMario;
            if (o->oPosY < gMarioObject->oPosY + 200.0f) {
                if (obj_y_vel_approach(0.0f, 0.5f)) {
                    o->oForwardVel *= 2.0f;
                }
            } else {
                obj_y_vel_approach(-10.0f, 0.5f);
            }
        } else if (o->oMoveFlags & OBJ_MOVE_HIT_WALL) {
            // Bounce off a wall and don't bounce again for 30 frames.
            o->oSwoopTargetYaw = cur_obj_reflect_move_angle_off_wall();
            o->oSwoopBonkCountdown = 30;
        }

        // Tilt upward when approaching mario
        if ((o->oSwoopTargetPitch = obj_get_pitch_from_vel()) == 0) {
            o->oSwoopTargetPitch += o->oForwardVel * 500;
        }
        obj_move_pitch_approach(o->oSwoopTargetPitch, 140);

        // Jitter yaw a bit
        cur_obj_rotate_yaw_toward(o->oSwoopTargetYaw + (s32)(3000 * coss(4000 * gGlobalTimer)), 1200);
        obj_roll_to_match_yaw_turn(o->oSwoopTargetYaw, 0x3000, 500);

        // Jitter roll a bit
        o->oFaceAngleRoll += (s32)(1000 * coss(20000 * gGlobalTimer));
    }
}

/**
 * Update function for swoop.
 */
void bhv_swoop_update(void) {
    // No partial update (only appears in roomed levels)

    if (!(o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM)) {
        o->oDeathSound = SOUND_OBJ_SWOOP_DEATH;

        cur_obj_update_floor_and_walls();

        switch (o->oAction) {
            case SWOOP_ACT_IDLE:
                swoop_act_idle();
                break;
            case SWOOP_ACT_MOVE:
                swoop_act_move();
                break;
        }

        cur_obj_scale(o->header.gfx.scale[0]);
        cur_obj_move_standard(78);

        obj_check_attacks(&sSwoopHitbox, o->oAction);
    }
}
