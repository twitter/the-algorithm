
/**
 * Behavior for bhvFlyGuy.
 */

/**
 * Hitbox for fly guy.
 */
static struct ObjectHitbox sFlyGuyHitbox = {
    /* interactType:      */ INTERACT_BOUNCE_TOP,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 2,
    /* health:            */ 0,
    /* numLootCoins:      */ 2,
    /* radius:            */ 70,
    /* height:            */ 60,
    /* hurtboxRadius:     */ 40,
    /* hurtboxHeight:     */ 50,
};

/**
 * Unused jitter amounts.
 */
static s16 sFlyGuyJitterAmounts[] = { 0x1000, -0x2000, 0x2000 };

/**
 * Return to regular size. When mario is close enough or home is far enough,
 * turn toward mario/home and enter the approach mario action.
 */
static void fly_guy_act_idle(void) {
    o->oForwardVel = 0.0f;

    if (approach_f32_ptr(&o->header.gfx.scale[0], 1.5f, 0.02f)) {
        // If we are >2000 units from home or Mario is <2000 units from us
        if (o->oDistanceToMario >= 25000.0f || o->oDistanceToMario < 2000.0f) {
            // Turn toward home or Mario
            obj_face_yaw_approach(o->oAngleToMario, 0x300);

            if (cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x300)) {
                o->oAction = FLY_GUY_ACT_APPROACH_MARIO;
            }
        } else {
            // Randomly enter the approach mario action - but this doesn't
            // really do anything since we come right back to idle
            if (o->oFlyGuyIdleTimer >= 3 || o->oFlyGuyIdleTimer == (random_u16() & 1) + 2) {
                o->oFlyGuyIdleTimer = 0;
                o->oAction = FLY_GUY_ACT_APPROACH_MARIO;
            } else {
                o->oFlyGuyUnusedJitter = o->oMoveAngleYaw + sFlyGuyJitterAmounts[o->oFlyGuyIdleTimer];
                o->oFlyGuyIdleTimer++;
            }
        }
    }
}

/**
 * Turn toward mario or home, and when positioned nicely, either lunge or shoot
 * fire. If mario is far away, stop and return to the idle action.
 */
static void fly_guy_act_approach_mario(void) {
    // If we are >2000 units from home or Mario is <2000 units from us
    if (o->oDistanceToMario >= 25000.0f || o->oDistanceToMario < 2000.0f) {
        obj_forward_vel_approach(10.0f, 0.5f);

        // Turn toward home or Mario
        obj_face_yaw_approach(o->oAngleToMario, 0x400);
        cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x200);

        // If facing toward mario and we are either near mario laterally or
        // far above him
        if (abs_angle_diff(o->oAngleToMario, o->oFaceAngleYaw) < 0x2000
            && (o->oPosY - gMarioObject->oPosY > 400.0f || o->oDistanceToMario < 400.0f)) {
            // Either shoot fire or lunge
            if (o->oBehParams2ndByte != 0 && random_u16() % 2) {
                o->oAction = FLY_GUY_ACT_SHOOT_FIRE;
                o->oFlyGuyScaleVel = 0.06f;
            } else {
                o->oAction = FLY_GUY_ACT_LUNGE;
                o->oFlyGuyLungeTargetPitch = obj_turn_pitch_toward_mario(-200.0f, 0);

                o->oForwardVel = 25.0f * coss(o->oFlyGuyLungeTargetPitch);
                o->oVelY = 25.0f * -sins(o->oFlyGuyLungeTargetPitch);
                o->oFlyGuyLungeYDecel = -o->oVelY / 30.0f;
            }
        }
    } else if (obj_forward_vel_approach(0.0f, 0.2f)) {
        o->oAction = FLY_GUY_ACT_IDLE;
    }
}

/**
 * Lunge downward at mario, then twirl back up. Enter the approach mario action
 * afterward.
 */
static void fly_guy_act_lunge(void) {
    if (o->oVelY < 0.0f) {
        // Lunge downward

        o->oVelY += o->oFlyGuyLungeYDecel;

        cur_obj_rotate_yaw_toward(o->oFaceAngleYaw, 0x800);
        obj_face_pitch_approach(o->oFlyGuyLungeTargetPitch, 0x400);

        // Possible values: {-0x1000, 0x0000, 0x1000}
        o->oFlyGuyTargetRoll = 0x1000 * (s16)(random_float() * 3.0f) - 0x1000;
        o->oTimer = 0;
    } else {
        // Twirl back upward

        obj_face_pitch_approach(0, 0x100);
        obj_face_roll_approach(o->oFlyGuyTargetRoll, 300);

        // Twirl in a spiral with curvature proportional to oFaceAngleRoll
        o->oMoveAngleYaw -= o->oFaceAngleRoll / 4;
        obj_face_yaw_approach(o->oMoveAngleYaw, 0x800);

        // Continue moving upward until at least 200 units above mario
        if (o->oPosY < gMarioObject->oPosY + 200.0f) {
            obj_y_vel_approach(20.0f, 0.5f);
        } else if (obj_y_vel_approach(0.0f, 0.5f)) {
            // Wait until roll is zero
            if (o->oFaceAngleRoll == 0) {
                o->oAction = FLY_GUY_ACT_APPROACH_MARIO;
            }

            o->oFlyGuyTargetRoll = 0;
        }
    }
}

/**
 * Turn toward mario, then shoot fire. Then enter the idle action.
 */
static void fly_guy_act_shoot_fire(void) {
    o->oForwardVel = 0.0f;

    if (obj_face_yaw_approach(o->oAngleToMario, 0x800)) {
        s32 scaleStatus;

        o->oMoveAngleYaw = o->oFaceAngleYaw;

        // Increase scale by 0.06, 0.05, ..., -0.03. Then wait ~8 frames, then
        // starting moving scale by 0.05 each frame toward 1.1. The first time
        // it becomes below 1.2 during this latter portion, shoot fire.
        scaleStatus = obj_grow_then_shrink(&o->oFlyGuyScaleVel, 1.2f, 1.1f);

        if (scaleStatus != 0) {
            if (scaleStatus < 0) {
                // We have reached scale 1.1
                o->oAction = FLY_GUY_ACT_IDLE;
            } else {
                // We have reached below scale 1.2 in the shrinking portion
                s16 fireMovePitch = obj_turn_pitch_toward_mario(0.0f, 0);

                cur_obj_play_sound_2(SOUND_OBJ_FLAME_BLOWN);
                clamp_s16(&fireMovePitch, 0x800, 0x3000);

                obj_spit_fire(
                    /*relativePos*/ 0, 38, 20,
                    /*scale      */ 2.5f,
                    /*model      */ MODEL_RED_FLAME_SHADOW,
                    /*startSpeed */ 25.0f,
                    /*endSpeed   */ 20.0f,
                    /*movePitch  */ fireMovePitch);
            }
        }
    } else {
        //! By triggering this repeatedly, we can keep obj_grow_then_shrink
        // in the "grow" phase. But because oFlyGuyScaleVel continues decreasing
        // past -0.03, the fly guy shrinks more than he is supposed to. We can
        // arbitrarily decrease the fly guy's scale in this way.
        o->oTimer = 0;
    }
}

/**
 * Update function for fly guy.
 */
void bhv_fly_guy_update(void) {
    // PARTIAL_UPDATE (appears in non-roomed levels)

    if (!(o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM)) {
        o->oDeathSound = SOUND_OBJ_KOOPA_FLYGUY_DEATH;

        cur_obj_scale(o->header.gfx.scale[0]);
        treat_far_home_as_mario(2000.0f);
        cur_obj_update_floor_and_walls();

        if (o->oMoveFlags & OBJ_MOVE_HIT_WALL) {
            o->oMoveAngleYaw = cur_obj_reflect_move_angle_off_wall();
        } else if (o->oMoveFlags & OBJ_MOVE_MASK_IN_WATER) {
            o->oVelY = 6.0f;
        }

        // Oscillate up and down
        o->oFlyGuyOscTimer++;
        o->oPosY += coss(0x400 * o->oFlyGuyOscTimer) * 1.5f;

        switch (o->oAction) {
            case FLY_GUY_ACT_IDLE:
                fly_guy_act_idle();
                break;
            case FLY_GUY_ACT_APPROACH_MARIO:
                fly_guy_act_approach_mario();
                break;
            case FLY_GUY_ACT_LUNGE:
                fly_guy_act_lunge();
                break;
            case FLY_GUY_ACT_SHOOT_FIRE:
                fly_guy_act_shoot_fire();
                break;
        }

        cur_obj_move_standard(78);
        obj_check_attacks(&sFlyGuyHitbox, o->oAction);
    }
}
