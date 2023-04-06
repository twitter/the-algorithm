/**
 * Behavior file for bhvSnufit and bhvSnufitBalls.
 * Snufits are present in HMC and CotMC, and are the fly guy
 * like enemies that shoot bullets. The balls are the little pellets
 * the snufit shoots at Mario.
 */

struct ObjectHitbox sSnufitHitbox = {
    /* interactType:      */ INTERACT_HIT_FROM_BELOW,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 2,
    /* health:            */ 0,
    /* numLootCoins:      */ 2,
    /* radius:            */ 100,
    /* height:            */ 60,
    /* hurtboxRadius:     */ 70,
    /* hurtboxHeight:     */ 50,
};

struct ObjectHitbox sSnufitBulletHitbox = {
    /* interactType:      */ INTERACT_SNUFIT_BULLET,
    /* downOffset:        */ 50,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 100,
    /* height:            */ 50,
    /* hurtboxRadius:     */ 100,
    /* hurtboxHeight:     */ 50,
};

/**
 * This geo function shifts snufit's mask when it shrinks down, 
 * since the parts move independently.
 */
Gfx *geo_snufit_move_mask(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct Object *obj;
    struct GraphNodeTranslationRotation *transNode;

    if (callContext == GEO_CONTEXT_RENDER) {
        obj = (struct Object *) gCurGraphNodeObject;
        transNode = (struct GraphNodeTranslationRotation *) node->next;

        transNode->translation[0] = obj->oSnufitXOffset;
        transNode->translation[1] = obj->oSnufitYOffset;
        transNode->translation[2] = obj->oSnufitZOffset;
    }

    return NULL;
}

/**
 * This function scales the body of snufit, which needs done seperately from its mask.
 */
Gfx *geo_snufit_scale_body(s32 callContext, struct GraphNode *node, UNUSED Mat4 *c) {
    struct Object *obj;
    struct GraphNodeScale *scaleNode;

    if (callContext == GEO_CONTEXT_RENDER) {
        obj = (struct Object *) gCurGraphNodeObject;
        scaleNode = (struct GraphNodeScale *) node->next;

        scaleNode->scale = obj->oSnufitBodyScale / 1000.0f;
    }

    return NULL;
}

/**
 * Snufit's idle action. It rotates in a circle until Mario is near,
 * then prepares to shoot after a period.
 */
void snufit_act_idle(void) {
    s32 marioDist;

    // This line would could cause a crash in certain PU situations,
    // if the game would not have already crashed.
    marioDist = (s32)(o->oDistanceToMario / 10.0f);
    if (o->oTimer > marioDist && o->oDistanceToMario < 800.0f) {
        
        // Controls an alternating scaling factor in a cos.
        o->oSnufitBodyScalePeriod
            = approach_s16_symmetric(o->oSnufitBodyScalePeriod, 0, 1500);
        o->oSnufitBodyBaseScale
            = approach_s16_symmetric(o->oSnufitBodyBaseScale, 600, 15);

        if ((s16) o->oSnufitBodyScalePeriod == 0 && o->oSnufitBodyBaseScale == 600) {
            o->oAction = SNUFIT_ACT_SHOOT;
            o->oSnufitBullets = 0;
        }
    } else {
        o->oSnufitCircularPeriod += 400;
    }
}

/**
 * Controls the literal shooting action, spawning three bhvSnufitBalls.
 */
void snufit_act_shoot(void) {
    o->oSnufitBodyScalePeriod
        = approach_s16_symmetric(o->oSnufitBodyScalePeriod, -0x8000, 3000);
    o->oSnufitBodyBaseScale
        = approach_s16_symmetric(o->oSnufitBodyBaseScale, 167, 20);

    if ((u16) o->oSnufitBodyScalePeriod == 0x8000 && o->oSnufitBodyBaseScale == 167) {
        o->oAction = SNUFIT_ACT_IDLE;
    } else if (o->oSnufitBullets < 3 && o->oTimer >= 3) {
        o->oSnufitBullets += 1;
        cur_obj_play_sound_2(SOUND_OBJ_SNUFIT_SHOOT);
        spawn_object_relative(0, 0, -20, 40, o, MODEL_BOWLING_BALL, bhvSnufitBalls);
        o->oSnufitRecoil = -30;
        o->oTimer = 0;
    }
}

/**
 * Primary loop behavior for snufit. Controls some generic movement
 * and the action brain of the object.
 */
void bhv_snufit_loop(void) {
    // Only update if Mario is in the current room.
    if (!(o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM)) {
        o->oDeathSound = SOUND_OBJ_SNUFIT_SKEETER_DEATH;
        
        // Face Mario if he is within range.
        if (o->oDistanceToMario < 800.0f) {
            obj_turn_pitch_toward_mario(120.0f, 2000);

            if ((s16) o->oMoveAnglePitch > 0x2000) {
                o->oMoveAnglePitch = 0x2000;
            } else if ((s16) o->oMoveAnglePitch < -0x2000) {
                o->oMoveAnglePitch = -0x2000;
            }

            cur_obj_rotate_yaw_toward(o->oAngleToMario, 2000);
        } else {
            obj_move_pitch_approach(0, 0x200);
            o->oMoveAngleYaw += 200;
        }

        o->oFaceAnglePitch = o->oMoveAnglePitch;

        switch (o->oAction) {
            case SNUFIT_ACT_IDLE:
                snufit_act_idle();
                break;
            case SNUFIT_ACT_SHOOT:
                snufit_act_shoot();
                break;
        }

        // Snufit orbits in a circular motion depending on an internal timer
        // and vertically off the global timer. The vertical position can be
        // manipulated using pauses since it uses the global timer.
        o->oPosX = o->oHomeX + 100.0f * coss(o->oSnufitCircularPeriod);
        o->oPosY = o->oHomeY + 8.0f * coss(4000 * gGlobalTimer);
        o->oPosZ = o->oHomeZ + 100.0f * sins(o->oSnufitCircularPeriod);

        o->oSnufitYOffset = -0x20;
        o->oSnufitZOffset = o->oSnufitRecoil + 180;
        o->oSnufitBodyScale
            = (s16)(o->oSnufitBodyBaseScale + 666
            + o->oSnufitBodyBaseScale * coss(o->oSnufitBodyScalePeriod));

        if (o->oSnufitBodyScale > 1000) {
            o->oSnufitScale = (o->oSnufitBodyScale - 1000) / 1000.0f + 1.0f;
            o->oSnufitBodyScale = 1000;
        } else {
            o->oSnufitScale = 1.0f;
        }

        cur_obj_scale(o->oSnufitScale);
        obj_check_attacks(&sSnufitHitbox, o->oAction);
    }
}

/**
 * Snufit bullets live to run into stuff and die when they do.
 */
void bhv_snufit_balls_loop(void) {
    // If far from Mario or in a different room, despawn.
    if ((o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM)
        || (o->oTimer != 0 && o->oDistanceToMario > 1500.0f)) {
        obj_mark_for_deletion(o);
    }

    // Gravity =/= 0 after it has hit Mario while metal.
    if (o->oGravity == 0.0f) {
        cur_obj_update_floor_and_walls();

        obj_compute_vel_from_move_pitch(40.0f);
        if (obj_check_attacks(&sSnufitBulletHitbox, 1)) {
            // We hit Mario while he is metal!
            // Bounce off, and fall until the first check is true.
            o->oMoveAngleYaw += 0x8000;
            o->oForwardVel *= 0.05f;
            o->oVelY = 30.0f;
            o->oGravity = -4.0f;

            cur_obj_become_intangible();
        } else if (o->oAction == 1 
               || (o->oMoveFlags & (OBJ_MOVE_MASK_ON_GROUND | OBJ_MOVE_HIT_WALL))) {
            // The Snufit shot Mario and has fulfilled its lonely existance.
            //! The above check could theoretically be avoided by finding a geometric
            //! situation that does not trigger those flags (Water?). If found,
            //! this would be a route to hang the game via too many snufit bullets.
            o->oDeathSound = -1;
            obj_die_if_health_non_positive();
        }

        cur_obj_move_standard(78);
    } else {
        cur_obj_move_using_fvel_and_gravity();
    }
}
