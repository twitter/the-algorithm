
/**
 * Behavior for Bowser and it's actions (Tail, Flame, Body)
 */

// Bowser's Tail

/**
 * Checks whenever the Bowser and his tail should be intangible or not
 * By default it starts tangible
 */
void bowser_tail_anchor_act_default(void) {
    struct Object *bowser = o->parentObj;
    cur_obj_become_tangible();
    cur_obj_scale(1.0f);

    if (bowser->oAction == BOWSER_ACT_TILT_LAVA_PLATFORM) {
        // Bowser cannot be touched when he tilts BitFS platform
        bowser->oIntangibleTimer = -1;
    } else if (obj_check_if_collided_with_object(o, gMarioObject)) {
        // When Mario collides his tail, it now gets
        // intangible so he can grab it through
        bowser->oIntangibleTimer = 0;
        o->oAction = BOWSER_ACT_TAIL_TOUCHED_MARIO;
    } else {
        bowser->oIntangibleTimer = -1;
    }
}

/**
 * While Bowser get's thrown, wait 30 frames then
 * return to the default tail action check
 */
void bowser_tail_anchor_thrown(void) {
    if (o->oTimer > 30) {
        o->oAction = BOWSER_ACT_TAIL_DEFAULT;
    }
}

/**
 * Makes the tail intangible so Mario can grab it
 */
void bowser_tail_anchor_act_touched_mario(void) {
    // Return to main action when Bowser tilts BitFS platform
    if (o->parentObj->oAction == BOWSER_ACT_TILT_LAVA_PLATFORM) {
        o->parentObj->oIntangibleTimer = -1;
        o->oAction = BOWSER_ACT_TAIL_DEFAULT;
    }
    cur_obj_become_intangible();
}

void (*sBowserTailAnchorActions[])(void) = {
    bowser_tail_anchor_act_default,
    bowser_tail_anchor_thrown,
    bowser_tail_anchor_act_touched_mario,
};

/**
 * Bowser's tail main loop
 */
void bhv_bowser_tail_anchor_loop(void) {
    // Call its actions
    cur_obj_call_action_function(sBowserTailAnchorActions);
    // Position the tail
    o->oParentRelativePosX = 90.0f;

    // Make it intangible while Bowser is dead
    if (o->parentObj->oAction == BOWSER_ACT_DEAD) {
        o->parentObj->oIntangibleTimer = -1;
    }

    o->oInteractStatus = 0;
}

// Bowser's Flame

/**
 * Bowser's Flame spawn main loop
 */
void bhv_bowser_flame_spawn_loop(void) {
    struct Object *bowser = o->parentObj;
    s32 animFrame;
    f32 posX;
    f32 posZ;
    f32 cossYaw = coss(bowser->oMoveAngleYaw);
    f32 sinsYaw = sins(bowser->oMoveAngleYaw);
    s16 *data = segmented_to_virtual(dBowserFlamesOrientationValues);

    // Check for Bowser breathing animation
    if (bowser->oSoundStateID == BOWSER_ANIM_BREATH) {

        // Start counting anim frames then reset it when it ends
        animFrame = bowser->header.gfx.animInfo.animFrame + 1.0f;
        if (bowser->header.gfx.animInfo.curAnim->loopEnd == animFrame) {
            animFrame = 0;
        }

        // Bowser is breathing, play sound and adjust flame position
        // each animFrame based off the orientantion data
        if (animFrame > 45 && animFrame < 85) {
            cur_obj_play_sound_1(SOUND_AIR_BOWSER_SPIT_FIRE);
            posX = data[5 * animFrame];
            posZ = data[5 * animFrame + 2];
            o->oPosX = bowser->oPosX + (posZ * sinsYaw + posX * cossYaw);
            o->oPosY = bowser->oPosY + data[5 * animFrame + 1];
            o->oPosZ = bowser->oPosZ + (posZ * cossYaw - posX * sinsYaw);
            o->oMoveAnglePitch = data[5 * animFrame + 4] + 0xC00;
            o->oMoveAngleYaw = data[5 * animFrame + 3] + (s16) bowser->oMoveAngleYaw;
            // Spawns the flames on a non-odd animFrame value
            if (!(animFrame & 1)) {
                spawn_object(o, MODEL_RED_FLAME, bhvFlameMovingForwardGrowing);
            }
        }
    }
}

/**
 * Bowser's Body main loop
 */
void bhv_bowser_body_anchor_loop(void) {
    // Copy position and angles from Bowser itself
    obj_copy_pos_and_angle(o, o->parentObj);
    // If Bowser is dead, set interaction type to text
    // so that he can be ready to speak his dialog
    if (o->parentObj->oAction == BOWSER_ACT_DEAD) {
#if BUGFIX_BOWSER_COLLIDE_BITS_DEAD
        // Clear interaction type at the last sub action in BitS
        // Fixes collision coliding after defeating him
        if (o->parentObj->oSubAction == BOWSER_SUB_ACT_DEAD_FINAL_END_OVER) {
            o->oInteractType = 0;
        } else {
            o->oInteractType = INTERACT_TEXT;
        }
#else
        o->oInteractType = INTERACT_TEXT;
#endif
    } else {
        // Do damage if Mario touches Bowser
        o->oInteractType = INTERACT_DAMAGE;
        // Make body intangible while is transparent
        // in BitFS (Teleporting)
        if (o->parentObj->oOpacity < 100) {
            cur_obj_become_intangible();
        } else {
            cur_obj_become_tangible();
        }
    }
    // Make body intangible while Bowser is getting grabbed
    if (o->parentObj->oHeldState != HELD_FREE) {
        cur_obj_become_intangible();
    }
    o->oInteractStatus = 0;
}

/**
 * Bowser's shockwave attack, spawns only in BitS
 */
s32 bowser_spawn_shockwave(void) {
    if (o->oBehParams2ndByte == BOWSER_BP_BITS) {
        struct Object *wave = spawn_object(o, MODEL_BOWSER_WAVE, bhvBowserShockWave);
        wave->oPosY = o->oFloorHeight;
        return TRUE;
    }
    return FALSE;
}

/**
 * Misc effects that Bowser plays when he lands with drastic actions
 * Plays step sound, spawns particles and changes camera event
 */
void bowser_bounce_effects(s32 *timer) {
    if (o->oMoveFlags & OBJ_MOVE_LANDED) {
        (*timer)++;
        if (*timer < 4) {
            cur_obj_start_cam_event(o, CAM_EVENT_BOWSER_THROW_BOUNCE);
            spawn_mist_particles_variable(0, 0, 60.0f);
            cur_obj_play_sound_2(SOUND_OBJ_BOWSER_WALK);
        }
    }
}

/**
 * Makes Bowser look up and walk on an specific animation frame
 * Returns TRUE if the animation is almost over
 */
s32 bowser_set_anim_look_up_and_walk(void) {
    cur_obj_init_animation_with_sound(BOWSER_ANIM_LOOK_UP_START_WALK);
    if (cur_obj_check_anim_frame(21)) {
        o->oForwardVel = 3.0f;
    }
    if (cur_obj_check_if_near_animation_end()) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Makes Bowser do a slow gait (or slow walk)
 * Returns TRUE if the animation is almost over
 */
s32 bowser_set_anim_slow_gait(void) {
    o->oForwardVel = 3.0f;
    cur_obj_init_animation_with_sound(BOWSER_ANIM_SLOW_GAIT);
    if (cur_obj_check_if_near_animation_end()) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Makes Bowser look down and stop on an specific animation frame
 * Returns TRUE if the animation is almost over
 */
s32 bowser_set_anim_look_down_stop_walk(void) {
    cur_obj_init_animation_with_sound(BOWSER_ANIM_LOOK_DOWN_STOP_WALK);
    if (cur_obj_check_anim_frame(20)) {
        o->oForwardVel = 0.0f;
    }
    if (cur_obj_check_if_near_animation_end()) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Set Bowser an action depending of the CamAct value
 * CamAct changes value on the cutscene itself (cutscene_bowser_arena)
 */
void bowser_init_camera_actions(void) {
    if (o->oBowserCamAct == BOWSER_CAM_ACT_IDLE) {
        o->oAction = BOWSER_ACT_WAIT;
    } else if (o->oBowserCamAct == BOWSER_CAM_ACT_WALK) {
        o->oAction = BOWSER_ACT_INTRO_WALK;
    // Start with a big jump in BitFS to do a platform tilt
    } else if (o->oBehParams2ndByte == BOWSER_BP_BITFS) {
        o->oAction = BOWSER_ACT_BIG_JUMP;
    } else {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Bowser's idle action that plays when he is initialized
 * or the CamAct gets in idle mode
 */
void bowser_act_wait(void) {
    o->oForwardVel = 0.0f;
    cur_obj_init_animation_with_sound(BOWSER_ANIM_IDLE);
    bowser_init_camera_actions();
}

/**
 * Bowser's cutscene walk that last a few seconds to introduce itself
 * Do subactions until the animation ends, then go to next subaction
 */
void bowser_act_intro_walk(void) {
    // First look up and walk
    if (o->oSubAction == 0) {
        if (bowser_set_anim_look_up_and_walk()) {
            o->oSubAction++;
        }
    // Then slowly walk
    } else if (o->oSubAction == 1) {
        if (bowser_set_anim_slow_gait()) {
            o->oSubAction++;
        }
    // And finally stop, and set to wait mode
    } else if (bowser_set_anim_look_down_stop_walk()) {
        if (o->oBowserCamAct == BOWSER_CAM_ACT_WALK) {
            o->oBowserCamAct = BOWSER_CAM_ACT_IDLE;
        }
        bowser_init_camera_actions();
    }
}

/**
 * List of actions to debug Bowser
 */
s8 sBowserDebugActions[] = {
    BOWSER_ACT_CHARGE_MARIO,
    BOWSER_ACT_SPIT_FIRE_INTO_SKY,
    BOWSER_ACT_SPIT_FIRE_ONTO_FLOOR,
    BOWSER_ACT_HIT_MINE,
    BOWSER_ACT_BIG_JUMP,
    BOWSER_ACT_WALK_TO_MARIO,
    BOWSER_ACT_BREATH_FIRE,
    BOWSER_ACT_DEAD,
    BOWSER_ACT_DANCE,
    BOWSER_ACT_TELEPORT,
    BOWSER_ACT_QUICK_JUMP,
    BOWSER_ACT_TILT_LAVA_PLATFORM,
    BOWSER_ACT_DANCE,
    BOWSER_ACT_DANCE,
    BOWSER_ACT_DANCE,
    BOWSER_ACT_DANCE,
};

/**
 * Debug function that allows to change Bowser's actions (most of them)
 */
UNUSED static void bowser_debug_actions(void) {
    if (gDebugInfo[DEBUG_PAGE_ENEMYINFO][1] != 0) {
        o->oAction = sBowserDebugActions[gDebugInfo[DEBUG_PAGE_ENEMYINFO][2] & 0x0F];
        gDebugInfo[DEBUG_PAGE_ENEMYINFO][1] = 0;
    }
}

/**
 * Set actions (and attacks) for Bowser in "Bowser in the Dark World"
 */
void bowser_bitdw_actions(void) {
    // Generate random float
    f32 rand = random_float();
    // Set attacks when Bowser Reacts
    if (!o->oBowserIsReacting) {
        if (o->oBowserStatus & BOWSER_STATUS_ANGLE_MARIO) {
            if (o->oDistanceToMario < 1500.0f) { // nearby
                o->oAction = BOWSER_ACT_BREATH_FIRE;
            } else { // far away
                o->oAction = BOWSER_ACT_QUICK_JUMP;
            }
        } else {
            // Keep walking
            o->oAction = BOWSER_ACT_WALK_TO_MARIO;
        }
        o->oBowserIsReacting++;
    } else {
        o->oBowserIsReacting = FALSE;
        // Set starting Bowser level actions, randomly he can also start
        // dancing after the introduction
#ifndef VERSION_JP
        if (gCurrDemoInput == NULL) { // demo check because entry exits post JP
#endif
            if (rand < 0.1) {
                o->oAction = BOWSER_ACT_DANCE; // 10% chance
            } else {
                o->oAction = BOWSER_ACT_WALK_TO_MARIO; // common
            }
#ifndef VERSION_JP
        } else {
            o->oAction = BOWSER_ACT_WALK_TO_MARIO;
        }
#endif
    }
}

/**
 * Set actions (and attacks) for Bowser in "Bowser in the Fire Sea"
 */
void bowser_bitfs_actions(void) {
    // Generate random float
    f32 rand = random_float();
    // Set attacks when Bowser Reacts
    if (!o->oBowserIsReacting) {
        if (o->oBowserStatus & BOWSER_STATUS_ANGLE_MARIO) {
            if (o->oDistanceToMario < 1300.0f) {  // nearby
                if (rand < 0.5) { // 50% chance
                    o->oAction = BOWSER_ACT_TELEPORT;
                } else {
                    o->oAction = BOWSER_ACT_SPIT_FIRE_ONTO_FLOOR;
                }
            } else { // far away
                o->oAction = BOWSER_ACT_CHARGE_MARIO;
                if (500.0f < o->oBowserDistToCenter && o->oBowserDistToCenter < 1500.0f
                    && rand < 0.5) { // 50% chance
                    o->oAction = BOWSER_ACT_BIG_JUMP;
                }
            }
        } else {
            // Keep walking
            o->oAction = BOWSER_ACT_WALK_TO_MARIO;
        }
        o->oBowserIsReacting++;
    } else {
        // Keep walking
        o->oBowserIsReacting = FALSE;
        o->oAction = BOWSER_ACT_WALK_TO_MARIO;
    }
}

/**
 * List of actions (and attacks) for "Bowser in the Sky"
 */
void bowser_bits_action_list(void) {
    f32 rand = random_float();
    if (o->oBowserStatus & BOWSER_STATUS_ANGLE_MARIO) {
        if (o->oDistanceToMario < 1000.0f) { // nearby
            if (rand < 0.4) {
                o->oAction = BOWSER_ACT_SPIT_FIRE_ONTO_FLOOR; // 40% chance
            } else if (rand < 0.8) {
                o->oAction = BOWSER_ACT_SPIT_FIRE_INTO_SKY; // 80% chance
            } else {
                o->oAction = BOWSER_ACT_BREATH_FIRE;
            }
        } else { // far away
            if (rand < 0.5) {
                o->oAction = BOWSER_ACT_BIG_JUMP; // 50% chance
            } else {
                o->oAction = BOWSER_ACT_CHARGE_MARIO;
            }
        }
    } else {
        // Keep walking
        o->oAction = BOWSER_ACT_WALK_TO_MARIO;
    }
}

/**
 * Sets big jump action, not much to say
 * Never gets called since oBowserBitsJustJump is always FALSE
 */
void bowser_set_act_big_jump(void) {
    o->oAction = BOWSER_ACT_BIG_JUMP;
}

/**
 * Set actions (and attacks) for Bowser in "Bowser in the Sky"
 */
void bowser_bits_actions(void) {
    switch (o->oBowserIsReacting) {
        case FALSE:
            // oBowserBitsJustJump never changes value,
            // so its always FALSE, maybe a debug define
            if (o->oBowserBitsJustJump == FALSE) {
                bowser_bits_action_list();
            } else {
                bowser_set_act_big_jump();
            }
            o->oBowserIsReacting = TRUE;
            break;

        case TRUE:
            o->oBowserIsReacting = FALSE;
            o->oAction = BOWSER_ACT_WALK_TO_MARIO;
            break;
    }
}

/**
 * Reset Bowser position and speed if he wasn't able to land properly on stage
 */
#if BUGFIX_BOWSER_FALLEN_OFF_STAGE
void bowser_reset_fallen_off_stage(void) {
    if (o->oVelY < 0.0f && o->oPosY < (o->oHomeY - 300.0f)) {
        o->oPosX = o->oPosZ = 0.0f;
        o->oPosY = o->oHomeY + 2000.0f;
        o->oVelY = 0.0f;
        o->oForwardVel = 0.0f;
    }
}
#endif

/**
 * Unused, makes Bowser be in idle and after it returns to default action
 */
void bowser_act_idle(void) {
    if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_IDLE)) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Default Bowser act that doesn't last very long
 */
void bowser_act_default(void) {
    // Set eye state
    o->oBowserEyesShut = FALSE;
    // Set idle animation
    cur_obj_init_animation_with_sound(BOWSER_ANIM_IDLE);
    // Stop him still
    o->oAngleVelYaw = 0;
    o->oForwardVel = 0.0f;
    o->oVelY = 0.0f;
    // Set level specific actions
    if (o->oBehParams2ndByte == BOWSER_BP_BITDW) {
        bowser_bitdw_actions();
    } else if (o->oBehParams2ndByte == BOWSER_BP_BITFS) {
        bowser_bitfs_actions();
    } else { // BOWSER_BP_BITS
        bowser_bits_actions();
    }
}

/**
 * Makes Bowser play breath animation and sound effect
 * The actual breath attack is in bhv_bowser_flame_spawn_loop
 * called as a child obj behavior in Bowser
 */
void bowser_act_breath_fire(void) {
    o->oForwardVel = 0.0f;
    if (o->oTimer == 0) {
        cur_obj_play_sound_2(SOUND_OBJ_BOWSER_INHALING);
    }
    // Init animation and return to default act after it ends
    if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_BREATH)) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Makes Bowser walk towards Mario
 */
void bowser_act_walk_to_mario(void) {
    UNUSED s32 facing; // is Bowser facing Mario?
    s16 turnSpeed;
    s16 angleFromMario = abs_angle_diff(o->oMoveAngleYaw, o->oAngleToMario);

    // Set turning speed depending of the health
    // Also special case for BitFS
    if (o->oBehParams2ndByte == BOWSER_BP_BITFS) {
        turnSpeed = 0x400;
    } else { // BOWSER_BP_BITDW or BOWSER_BP_BITS
        if (o->oHealth >= 3) {
            turnSpeed = 0x400;
        } else if (o->oHealth == 2) {
            turnSpeed = 0x300;
        } else { // 1 health
            turnSpeed = 0x200;
        }
    }

    facing = cur_obj_rotate_yaw_toward(o->oAngleToMario, turnSpeed);

    if (o->oSubAction == 0) {
        o->oBowserTimer = 0;
        // Start walking
        if (bowser_set_anim_look_up_and_walk()) {
            o->oSubAction++;
        }
    } else if (o->oSubAction == 1) {
        // Keep walking slowly
        if (bowser_set_anim_slow_gait()) {
            o->oBowserTimer++;
            // Reset fire sky status
            if (o->oBowserStatus & BOWSER_STATUS_FIRE_SKY) {
                if (o->oBowserTimer > 4) {
                    o->oBowserStatus &= ~BOWSER_STATUS_FIRE_SKY;
                }
            // Do subaction below if angles is less than 0x2000
            } else if (angleFromMario < 0x2000) {
                o->oSubAction++;
            }
        }
    // Stop walking and set to default action
    } else if (bowser_set_anim_look_down_stop_walk()) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Makes Bowser teleport while invisible
 */
void bowser_act_teleport(void) {
    switch (o->oSubAction) {
        // Set opacity target to invisible and become intangible
        case BOWSER_SUB_ACT_TELEPORT_START:
            cur_obj_become_intangible();
            o->oBowserTargetOpacity = 0;
            o->oBowserTimer = 30; // set timer value
            // Play sound effect
            if (o->oTimer == 0) {
                cur_obj_play_sound_2(SOUND_OBJ2_BOWSER_TELEPORT);
            }
            // Bowser is invisible, move angle to face Mario
            if (o->oOpacity == 0) {
                o->oSubAction++;
                o->oMoveAngleYaw = o->oAngleToMario;
            }
            break;

        case BOWSER_SUB_ACT_TELEPORT_MOVE:
            // reduce timer and set velocity teleport while at it
            if (o->oBowserTimer--) {
                o->oForwardVel = 100.0f;
            } else {
                o->oSubAction = BOWSER_SUB_ACT_TELEPORT_STOP;
                o->oMoveAngleYaw = o->oAngleToMario; // update angle
            }

            if (abs_angle_diff(o->oMoveAngleYaw, o->oAngleToMario) > 0x4000) {
                if (o->oDistanceToMario > 500.0f) {
                    o->oSubAction = BOWSER_SUB_ACT_TELEPORT_STOP;
                    o->oMoveAngleYaw = o->oAngleToMario; // update angle
                    cur_obj_play_sound_2(SOUND_OBJ2_BOWSER_TELEPORT);
                }
            }
            break;

        // Set opacity target to visible and become tangible
        case BOWSER_SUB_ACT_TELEPORT_STOP:
            o->oForwardVel = 0.0f; // reset velocity
            o->oBowserTargetOpacity = 255;
            // Set to default action once visible
            if (o->oOpacity == 255) {
                o->oAction = BOWSER_ACT_DEFAULT;
            }
            cur_obj_become_tangible();
            break;
    }
}

/**
 * Makes Bowser do a fire split into the sky
 */
void bowser_act_spit_fire_into_sky(void) {
    s32 animFrame;
    // Play animation
    cur_obj_init_animation_with_sound(BOWSER_ANIM_BREATH_UP);
    // Set frames
    animFrame = o->header.gfx.animInfo.animFrame;
    // Spawn flames in the middle of the animation
    if (animFrame > 24 && animFrame < 36) {
        cur_obj_play_sound_1(SOUND_AIR_BOWSER_SPIT_FIRE);
        if (animFrame == 35) { // Spawns Blue flames at this frame
            spawn_object_relative(1, 0, 400, 100, o, MODEL_RED_FLAME, bhvBlueBowserFlame);
        } else { // Spawns Red flames
            spawn_object_relative(0, 0, 400, 100, o, MODEL_RED_FLAME, bhvBlueBowserFlame);
        }
    }
    // Return to default act once the animation is over
    if (cur_obj_check_if_near_animation_end()) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
    // Set fire sky status
    o->oBowserStatus |= BOWSER_STATUS_FIRE_SKY;
}

/**
 * Flips Bowser back on stage if he hits a mine with more than 1 health
 */
void bowser_act_hit_mine(void) {
    // Similar vel values from bowser_fly_back_dead
    if (o->oTimer == 0) {
        o->oForwardVel = -400.0f;
        o->oVelY = 100.0f;
        o->oMoveAngleYaw = o->oBowserAngleToCenter + 0x8000;
        o->oBowserEyesShut = TRUE; // close eyes
    }
    // Play flip animation
    if (o->oSubAction == BOWSER_SUB_ACT_HIT_MINE_START) {
        cur_obj_init_animation_with_sound(BOWSER_ANIM_FLIP);
        o->oSubAction++;
        o->oBowserTimer = 0;
    // Play flip animation again, extend it and play bounce effects
    } else if (o->oSubAction == BOWSER_SUB_ACT_HIT_MINE_FALL) {
        cur_obj_init_animation_with_sound(BOWSER_ANIM_FLIP);
        cur_obj_extend_animation_if_at_end();
        bowser_bounce_effects(&o->oBowserTimer);
        // Reset vel and stand up
        if (o->oBowserTimer > 2) {
            cur_obj_init_animation_with_sound(BOWSER_ANIM_STAND_UP_FROM_FLIP);
            o->oVelY = 0.0f;
            o->oForwardVel = 0.0f;
            o->oSubAction++;
        }
    // Play these actions once he is stand up
    } else if (o->oSubAction == BOWSER_SUB_ACT_HIT_MINE_STOP) {
        if (cur_obj_check_if_near_animation_end()) {
            // Makes Bowser dance at one health (in BitS)
            if (o->oHealth == 1) {
                o->oAction = BOWSER_ACT_DANCE;
            } else {
                o->oAction = BOWSER_ACT_DEFAULT;
            }
            o->oBowserEyesShut = FALSE; // open eyes
        }
    } else {
    }
}

/**
 * Makes Bowser do his jump start animation
 * Returns TRUE on the middle of the jump
 */
s32 bowser_set_anim_jump(void) {
    cur_obj_init_animation_with_sound(BOWSER_ANIM_JUMP_START);
    if (cur_obj_check_anim_frame(11)) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Reset speed, play jump stop animation and do attacks in BitDW
 * Returns TRUE when Bowser lands
 */
s32 bowser_land(void) {
    if (o->oMoveFlags & OBJ_MOVE_LANDED) {
        o->oForwardVel = 0.0f;
        o->oVelY = 0.0f;
        spawn_mist_particles_variable(0, 0, 60.0f);
        cur_obj_init_animation_with_sound(BOWSER_ANIM_JUMP_STOP);
        o->header.gfx.animInfo.animFrame = 0;
        cur_obj_start_cam_event(o, CAM_EVENT_BOWSER_JUMP);
        // Set status attacks in BitDW since the other levels
        // have different attacks defined
        if (o->oBehParams2ndByte == BOWSER_BP_BITDW) {
            if (o->oDistanceToMario < 850.0f) {
                gMarioObject->oInteractStatus |= INT_STATUS_MARIO_KNOCKBACK_DMG;
            } else {
                gMarioObject->oInteractStatus |= INT_STATUS_MARIO_STUNNED;
            }
        }
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Makes Bowser do a second hop speed only in BitS
 */
void bowser_short_second_hop(void) {
    if (o->oBehParams2ndByte == BOWSER_BP_BITS && o->oBowserStatus & BOWSER_STATUS_BIG_JUMP) {
        if (o->oBowserDistToCenter > 1000.0f) {
            o->oForwardVel = 60.0f;
        }
    }
}

/**
 * Makes Bowser do a big jump
 */
void bowser_act_big_jump(void) {
    UNUSED u8 filler[4];

    if (o->oSubAction == 0) {
        // Set jump animation
        if (bowser_set_anim_jump()) {
            // Set vel depending of the stage and status
            if (o->oBehParams2ndByte == BOWSER_BP_BITS && o->oBowserStatus & BOWSER_STATUS_BIG_JUMP) {
                o->oVelY = 70.0f;
            } else {
                o->oVelY = 80.0f;
            }
            o->oBowserTimer = 0;
            bowser_short_second_hop();
            o->oSubAction++;
        }
    } else if (o->oSubAction == 1) {
#if BUGFIX_BOWSER_FALLEN_OFF_STAGE
        // Reset Bowser back on stage in BitS if he doesn't land properly
        if (o->oBehParams2ndByte == BOWSER_BP_BITS && o->oBowserStatus & BOWSER_STATUS_BIG_JUMP) {
            bowser_reset_fallen_off_stage();
        }
#endif
        // Land on stage, reset status jump and velocity
        if (bowser_land()) {
            o->oBowserStatus &= ~BOWSER_STATUS_BIG_JUMP;
            o->oForwardVel = 0.0f;
            o->oSubAction++;
            // Spawn shockwave (BitS only) if is not on a platform
            bowser_spawn_shockwave();
            // Tilt platform in BitFS
            if (o->oBehParams2ndByte == BOWSER_BP_BITFS) {
                o->oAction = BOWSER_ACT_TILT_LAVA_PLATFORM;
            }
        } else {
        }
    // Set to default action when the animation is over
    } else if (cur_obj_check_if_near_animation_end()) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Fixed values for the quick jump action
 */
s16 sBowserVelYAir[] = { 60 };
s16 sBowserFVelAir[] = { 50 };

/**
 * Makes Bowser do a "quick" jump in BitDW
 */
void bowser_act_quick_jump(void) {
    f32 velY = sBowserVelYAir[0];
    f32 fVel = sBowserFVelAir[0];

    if (o->oSubAction == 0) {
        // Set fixed val positions while jumping
        if (bowser_set_anim_jump()) {
            o->oVelY = velY;
            o->oForwardVel = fVel;
            o->oBowserTimer = 0;
            o->oSubAction++;
        }
    // Lands then quickly returns to default action
    } else if (o->oSubAction == 1) {
        if (bowser_land()) {
            o->oSubAction++;
        }
    } else if (cur_obj_check_if_near_animation_end()) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Makes Bowser moving around if he is on an edge floor
 */
void bowser_act_hit_edge(void) {
    // Reset speed and timer
    o->oForwardVel = 0.0f;
    if (o->oTimer == 0) {
        o->oBowserTimer = 0;
    }

    switch (o->oSubAction) {
        case 0:
            // Move on the edge
            cur_obj_init_animation_with_sound(BOWSER_ANIM_EDGE_MOVE);
            if (cur_obj_check_if_near_animation_end()) {
                o->oBowserTimer++;
            }
            if (o->oBowserTimer > 0) {
                o->oSubAction++;
            }
            break;

        case 1:
            // Stop moving on the edge
            cur_obj_init_animation_with_sound(BOWSER_ANIM_EDGE_STOP);
            // Turn around once the animation ends
            if (cur_obj_check_if_near_animation_end()) {
                o->oAction = BOWSER_ACT_TURN_FROM_EDGE;
            }
            break;
    }
}

/**
 * Makes Bowser do a fire split attack
 */
void bowser_act_spit_fire_onto_floor(void) {
    // Set fixed rand value if Mario is low health
    if (gHudDisplay.wedges < 4) {
        o->oBowserRandSplitFloor = 3;
    } else {
        o->oBowserRandSplitFloor = random_float() * 3.0f + 1.0f;
    }

    // Play animation and split fire at a specific frame
    cur_obj_init_animation_with_sound(BOWSER_ANIM_BREATH_QUICK);
    if (cur_obj_check_anim_frame(5)) {
        obj_spit_fire(0, 200, 180, 7.0f, MODEL_RED_FLAME, 30.0f, 10.0f, 0x1000);
    }
    // Use subaction as a timer when the animation is over
    if (cur_obj_check_if_near_animation_end()) {
        o->oSubAction++;
    }
    // Return to default act once we get past rand value
    if (o->oSubAction >= o->oBowserRandSplitFloor) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Turns around Bowser from an specific yaw angle
 * Returns TRUE once the timer is bigger than the time set
 */
s32 bowser_turn_on_timer(s32 time, s16 yaw) {
    if (o->oSubAction == 0) {
        if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_LOOK_UP_START_WALK)) {
            o->oSubAction++;
        }
    } else if (o->oSubAction == 1) {
        if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_LOOK_DOWN_STOP_WALK)) {
            o->oSubAction++;
        }
    } else {
        cur_obj_init_animation_with_sound(BOWSER_ANIM_IDLE);
    }

    o->oForwardVel = 0.0f;
    o->oMoveAngleYaw += yaw;

    if (o->oTimer >= time) {
        return TRUE;
    } else {
        return FALSE;
    }
}

/**
 * Makes Bowser turn around after hitting the edge
 */
void bowser_act_turn_from_edge(void) {
    if (bowser_turn_on_timer(63, 0x200)) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Makes Bowser charge (run) to Mario
 */
void bowser_act_charge_mario(void) {
    s32 time;
    // Reset Speed to prepare charge
    if (o->oTimer == 0) {
        o->oForwardVel = 0.0f;
    }

    switch (o->oSubAction) {
        case BOWSER_SUB_ACT_CHARGE_START:
            // Start running
            o->oBowserTimer = 0;
            if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_RUN_START)) {
                o->oSubAction = BOWSER_SUB_ACT_CHARGE_RUN;
            }
            break;

        case BOWSER_SUB_ACT_CHARGE_RUN:
            // Set speed to run
            o->oForwardVel = 50.0f;
            if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_RUN)) {
                o->oBowserTimer++;
                // Split if 6 timer frames has passed
                if (o->oBowserTimer >= 6) {
                    o->oSubAction = BOWSER_SUB_ACT_CHARGE_SLIP;
                }
                // Slip if Mario has a differentiable angle and 2 timer frames has passed
                if (o->oBowserTimer >= 2) {
                    if (abs_angle_diff(o->oAngleToMario, o->oMoveAngleYaw) > 0x2000) {
                        o->oSubAction = BOWSER_SUB_ACT_CHARGE_SLIP;
                    }
                }
            }
            // Rotate to Mario
            cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x200);
            break;

        case BOWSER_SUB_ACT_CHARGE_SLIP:
            // Spawn smoke puff while slipping
            o->oBowserTimer = 0;
            cur_obj_init_animation_with_sound(BOWSER_ANIM_RUN_SLIP);
            spawn_object_relative_with_scale(0, 100, -50, 0, 3.0f, o, MODEL_SMOKE, bhvWhitePuffSmoke2);
            spawn_object_relative_with_scale(0, -100, -50, 0, 3.0f, o, MODEL_SMOKE,
                                             bhvWhitePuffSmoke2);
            // End Charge once Bowser stops running
            if (approach_f32_signed(&o->oForwardVel, 0, -1.0f)) {
                o->oSubAction = BOWSER_SUB_ACT_CHARGE_END;
            }
            cur_obj_extend_animation_if_at_end();
            break;

        case BOWSER_SUB_ACT_CHARGE_END:
            // Stop running
            o->oForwardVel = 0.0f;
            cur_obj_init_animation_with_sound(BOWSER_ANIM_RUN_STOP);
            if (cur_obj_check_if_near_animation_end()) {
                // Set time delay to go to default action
                if (o->oBehParams2ndByte == BOWSER_BP_BITS) {
                    time = 10;
                } else {
                    time = 30;
                }
                if (o->oBowserTimer > time) {
                    o->oAction = BOWSER_ACT_DEFAULT;
                }
                o->oBowserTimer++;
            }
            cur_obj_extend_animation_if_at_end();
            break;
    }

    // Bowser is close to falling so set hit edge action
    if (o->oMoveFlags & OBJ_MOVE_HIT_EDGE) {
        o->oAction = BOWSER_ACT_HIT_EDGE;
    }
}

/**
 * Checks if Bowser hits a mine from a distance, returns TRUE if so
 */
s32 bowser_check_hit_mine(void) {
    struct Object *mine;
    f32 dist;

    mine = cur_obj_find_nearest_object_with_behavior(bhvBowserBomb, &dist);
    if (mine != NULL && dist < 800.0f) {
        mine->oInteractStatus |= INT_STATUS_HIT_MINE;
        return TRUE;
    }

    return FALSE;
}

/**
 * Bowser's thrown act that gets called after Mario releases him
 */
void bowser_act_thrown(void) {
    UNUSED u8 filler[4];

    // Keep Bowser's timer at 0 unless he lands
    if (o->oTimer < 2) {
        o->oBowserTimer = 0;
    }
    if (o->oSubAction == 0) {
        // Play shake animations and do bounce effects
        cur_obj_init_animation_with_sound(BOWSER_ANIM_SHAKING);
        bowser_bounce_effects(&o->oBowserTimer);
        // Reset speed when he moves on ground
        if (o->oMoveFlags & OBJ_MOVE_ON_GROUND) {
            o->oForwardVel = 0.0f;
            o->oSubAction++; // stops this current subaction
        }
    // Stand up and after play, set to default act
    } else if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_STAND_UP)) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
    // Hit mine check, reduce health and set specific action depending of it
    if (bowser_check_hit_mine()) {
        o->oHealth--;
        if (o->oHealth <= 0) {
            o->oAction = BOWSER_ACT_DEAD;
        } else {
            o->oAction = BOWSER_ACT_HIT_MINE;
        }
    }
}

/**
 * Set Bowser invisible and stops him (after falling)
 */
void bowser_set_goal_invisible(void) {
    o->oBowserTargetOpacity = 0;
    if (o->oOpacity == 0) {
        o->oForwardVel = 0.0f;
        o->oVelY = 0.0f;
        o->oPosY = o->oHomeY - 1000.0f;
    }
}

/**
 * Makes Bowser jump back on stage after falling
 */
void bowser_act_jump_onto_stage(void) {
    s32 onDynamicFloor;
    UNUSED u8 filler[4];
    struct Surface *floor = o->oFloor;

    // Set dynamic floor check (Object platforms)
    if (floor != NULL && floor->flags & SURFACE_FLAG_DYNAMIC) {
        onDynamicFloor = TRUE;
    } else {
        onDynamicFloor = FALSE;
    }
    // Set status Jump
    o->oBowserStatus |= BOWSER_STATUS_BIG_JUMP;

    switch (o->oSubAction) {
        // Stops Bowser and makes him invisible
        case BOWSER_SUB_ACT_JUMP_ON_STAGE_IDLE:
            if (o->oTimer == 0) {
                o->oFaceAnglePitch = 0;
                o->oFaceAngleRoll = 0;
            } //? missing else
            o->oFaceAnglePitch += 0x800;
            o->oFaceAngleRoll += 0x800;
            if (!(o->oFaceAnglePitch & 0xFFFF)) {
                o->oSubAction++;
            }
            bowser_set_goal_invisible();
            break;

        // Start jump animation and make him visible after an animation frame
        case BOWSER_SUB_ACT_JUMP_ON_STAGE_START:
            cur_obj_init_animation_with_sound(BOWSER_ANIM_JUMP_START);
            if (cur_obj_check_anim_frame(11)) {
                o->oMoveAngleYaw = o->oBowserAngleToCenter;
                o->oVelY = 150.0f;
                o->oBowserTargetOpacity = 255;
                o->oBowserTimer = 0;
                o->oSubAction++;
            } else {
                bowser_set_goal_invisible();
            }
            break;

        // Approach him back on stage
        case BOWSER_SUB_ACT_JUMP_ON_STAGE_LAND:
            if (o->oPosY > o->oHomeY) {
                o->oDragStrength = 0.0f;
                if (o->oBowserDistToCenter < 2500.0f) {
                    if (absf(o->oFloorHeight - o->oHomeY) < 100.0f) {
                        approach_f32_signed(&o->oForwardVel, 0, -5.0f);
                    } else {
                        cur_obj_forward_vel_approach_upward(150.0f, 2.0f);
                    }
                } else {
                    cur_obj_forward_vel_approach_upward(150.0f, 2.0f);
                }
            }
            // Land on stage
            if (bowser_land()) {
                o->oDragStrength = 10.0f;
                o->oSubAction++;
                // Spawn shockwave (BitS only) if is not on a platform
                if (onDynamicFloor == FALSE) {
                    bowser_spawn_shockwave();
                // If is on a dynamic floor in BitS, then jump
                // because of the falling platform
                } else if (o->oBehParams2ndByte == BOWSER_BP_BITS) {
                    o->oAction = BOWSER_ACT_BIG_JUMP;
                }
                // If is on a dynamic floor in BitFS, then tilt platform
                if (o->oBehParams2ndByte == BOWSER_BP_BITFS) {
                    o->oAction = BOWSER_ACT_TILT_LAVA_PLATFORM;
                }
            }
            // Reset him back on stage if he still didn't landed yet
            // Post-JP made this check as a separate function
#if BUGFIX_BOWSER_FALLEN_OFF_STAGE
            bowser_reset_fallen_off_stage();
#else
            if (o->oVelY < 0.0f && o->oPosY < o->oHomeY - 300.0f) {
                o->oPosZ = 0.0f, o->oPosX = o->oPosZ;
                o->oPosY = o->oHomeY + 2000.0f;
                o->oVelY = 0.0f;
            }
#endif
            break;

        // Bowser landed, so reset action after he's done jumping
        case BOWSER_SUB_ACT_JUMP_ON_STAGE_STOP:
            if (cur_obj_check_if_near_animation_end()) {
                o->oAction = BOWSER_ACT_DEFAULT;
                o->oBowserStatus &= ~BOWSER_STATUS_BIG_JUMP;
                cur_obj_extend_animation_if_at_end();
            }
            break;
    }

    print_debug_bottom_up("sp %d", o->oForwardVel);
}

/**
 * The frames of the Bowser's timer on which to play a "stomp" sound
 */
s8 sBowserDanceStepNoises[] = { 24, 42, 60, -1 };

/**
 * Makes Bowser's dance as a "taunt"
 */
void bowser_act_dance(void) {
    // Play a stomp sound effect on certain frames
    if (is_item_in_array(o->oTimer, sBowserDanceStepNoises)) {
        cur_obj_play_sound_2(SOUND_OBJ_BOWSER_WALK);
    }
    // Play dance animation and after that return to default action
    if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_DANCE)) {
        o->oAction = BOWSER_ACT_DEFAULT;
    }
}

/**
 * Spawn collectable that Bowser spawns after despawning
 * Spawns a Key in BitDW/BitFS or Grand Star in BitS
 */
void bowser_spawn_collectable(void) {
    if (o->oBehParams2ndByte == BOWSER_BP_BITS) {
        gSecondCameraFocus = spawn_object(o, MODEL_STAR, bhvGrandStar);
    } else {
        gSecondCameraFocus = spawn_object(o, MODEL_BOWSER_KEY, bhvBowserKey);
        cur_obj_play_sound_2(SOUND_GENERAL2_BOWSER_KEY);
    }
    gSecondCameraFocus->oAngleVelYaw = o->oAngleVelYaw;
}

/**
 * Makes Bowser fly back on stage defeated
 */
void bowser_fly_back_dead(void) {
    cur_obj_init_animation_with_sound(BOWSER_ANIM_FLIP_DOWN);
    // More knockback in BitS
    if (o->oBehParams2ndByte == BOWSER_BP_BITS) {
        o->oForwardVel = -400.0f;
    } else {
        o->oForwardVel = -200.0f;
    }
    o->oVelY = 100.0f;
    o->oMoveAngleYaw = o->oBowserAngleToCenter + 0x8000;
    o->oBowserTimer = 0;
    o->oSubAction++; // BOWSER_SUB_ACT_DEAD_BOUNCE
}

/**
 * Plays bounce effects after landing upside down
 */
void bowser_dead_bounce(void) {
    o->oBowserEyesShut = TRUE; // close eyes
    bowser_bounce_effects(&o->oBowserTimer);
    if (o->oMoveFlags & OBJ_MOVE_LANDED) {
        cur_obj_play_sound_2(SOUND_OBJ_BOWSER_WALK);
    }
    if (o->oMoveFlags & OBJ_MOVE_ON_GROUND) {
        o->oForwardVel = 0.0f;
        o->oSubAction++; // BOWSER_SUB_ACT_DEAD_WAIT
    }
}

/**
 * Wait for Mario to get close while Bowser is defeated
 * Returns TRUE if he is close enough
 */
s32 bowser_dead_wait_for_mario(void) {
    s32 ret = FALSE;
    cur_obj_become_intangible();
    if (cur_obj_init_animation_and_check_if_near_end(BOWSER_ANIM_LAY_DOWN) && o->oDistanceToMario < 700.0f
        && abs_angle_diff(gMarioObject->oMoveAngleYaw, o->oAngleToMario) > 0x6000) {
        ret = TRUE;
    }
    cur_obj_extend_animation_if_at_end();
    o->oBowserTimer = 0;
    return ret;
}

/**
 * Makes Bowser twirl up by changing his scale
 * Returns TRUE once done
 */
s32 bowser_dead_twirl_up(void) {
    s32 ret = FALSE;

    // Set angle rotation once he has low X scale value
    if (o->header.gfx.scale[0] < 0.8) {
        o->oAngleVelYaw += 0x80;
    }
    // Slowly scale down his X and Z value
    if (o->header.gfx.scale[0] > 0.2) {
        o->header.gfx.scale[0] = o->header.gfx.scale[0] - 0.02;
        o->header.gfx.scale[2] = o->header.gfx.scale[2] - 0.02;
    } else {
        // Now scale down his Y value (and send Bowser up)
        o->header.gfx.scale[1] = o->header.gfx.scale[1] - 0.01;
        o->oVelY = 20.0f;
        o->oGravity = 0.0f;
    }
    // At half Y scale value, he is high enough, so we are done
    if (o->header.gfx.scale[1] < 0.5) {
        ret = TRUE;
    }
    // Copy angle rotation to moving rotation
    o->oMoveAngleYaw += o->oAngleVelYaw;
    // Slowly fade out
    if (o->oOpacity >= 3) {
        o->oOpacity -= 2;
    }

    return ret;
}

/**
 * Hides Bowser after his death sequence is done
 */
void bowser_dead_hide(void) {
    cur_obj_scale(0);
    o->oForwardVel = 0;
    o->oVelY = 0;
    o->oGravity = 0;
}

/**
 * Dialog values that are set on each stage Bowser's is defeated
 */
s16 sBowserDefeatedDialogText[3] = { DIALOG_119, DIALOG_120, DIALOG_121 };

/**
 * Bowser's dead sequence that plays in BitDW/BitFS
 * Returns TRUE once done
 */
s32 bowser_dead_default_stage_ending(void) {
    s32 ret = FALSE;

    if (o->oBowserTimer < 2) {
        // Lower music volume
        if (o->oBowserTimer == 0) {
            seq_player_lower_volume(SEQ_PLAYER_LEVEL, 60, 40);
            o->oBowserTimer++;
        }
        // Play Bowser defeated dialog
        if (cur_obj_update_dialog(MARIO_DIALOG_LOOK_UP,
            (DIALOG_FLAG_TEXT_DEFAULT | DIALOG_FLAG_TIME_STOP_ENABLED),
            sBowserDefeatedDialogText[o->oBehParams2ndByte], 0)) {
            // Dialog is done, fade out music and play explode sound effect
            o->oBowserTimer++;
            cur_obj_play_sound_2(SOUND_GENERAL2_BOWSER_EXPLODE);
            seq_player_unlower_volume(SEQ_PLAYER_LEVEL, 60);
            seq_player_fade_out(SEQ_PLAYER_LEVEL, 1);
        }
    // Hide Bowser and spawn collectable once done twirling
    } else if (bowser_dead_twirl_up()) {
        bowser_dead_hide();
        spawn_triangle_break_particles(20, MODEL_YELLOW_COIN, 1.0f, 0);
        bowser_spawn_collectable();
        set_mario_npc_dialog(MARIO_DIALOG_STOP);
        ret = TRUE;
    }

    return ret;
}

/**
 * Bowser's dead sequence that plays in BitS
 * Returns TRUE once done
 */
s32 bowser_dead_final_stage_ending(void) {
    UNUSED u8 filler[4];
    s32 ret = FALSE;
    s32 dialogID;

    if (o->oBowserTimer < 2) {
        // Set dialog whenever you have 120 stars or not
        if (gHudDisplay.stars < 120) {
            dialogID = DIALOG_121;
        } else {
            dialogID = DIALOG_163;
        }
        // Lower music volume
        if (o->oBowserTimer == 0) {
            seq_player_lower_volume(SEQ_PLAYER_LEVEL, 60, 40);
            o->oBowserTimer++;
        }
        // Play Bowser defeated dialog
        if (cur_obj_update_dialog(MARIO_DIALOG_LOOK_UP,
            (DIALOG_FLAG_TEXT_DEFAULT | DIALOG_FLAG_TIME_STOP_ENABLED), dialogID, 0)) {
            // Dialog is done, fade out music and spawn grand star
            cur_obj_set_model(MODEL_BOWSER_NO_SHADOW);
            seq_player_unlower_volume(SEQ_PLAYER_LEVEL, 60);
            seq_player_fade_out(SEQ_PLAYER_LEVEL, 1);
            bowser_spawn_collectable();
            o->oBowserTimer++;
        }
    // Slowly fade him out
    } else if (o->oOpacity > 4) {
        o->oOpacity -= 4;
    } else {
        // And at last, hide him
        bowser_dead_hide();
        ret = TRUE;
    }

    return ret;
}

/**
 * Bowser's dead action, plays when he has no health left
 * This action is divided in subaction functions
 */
void bowser_act_dead(void) {
    switch (o->oSubAction) {
        case BOWSER_SUB_ACT_DEAD_FLY_BACK:
            bowser_fly_back_dead();
            break;

        case BOWSER_SUB_ACT_DEAD_BOUNCE:
            bowser_dead_bounce();
            break;

        case BOWSER_SUB_ACT_DEAD_WAIT:
            // Check if Mario is close to Bowser
            if (bowser_dead_wait_for_mario()) {
                o->oBowserTimer = 0;
                // Set different (final) subaction in BitS
                // Non-BitS Bowser uses default subaction and sets dithering
                if (o->oBehParams2ndByte == BOWSER_BP_BITS) {
                    o->oSubAction = BOWSER_SUB_ACT_DEAD_FINAL_END;
                } else {
                    o->activeFlags |= ACTIVE_FLAG_DITHERED_ALPHA;
                    o->oSubAction++; // BOWSER_SUB_ACT_DEAD_DEFAULT_END
                }
            }
            break;

        case BOWSER_SUB_ACT_DEAD_DEFAULT_END:
            if (bowser_dead_default_stage_ending()) {
                o->oSubAction++; // BOWSER_SUB_ACT_DEAD_DEFAULT_END_OVER
            }
            break;

        case BOWSER_SUB_ACT_DEAD_DEFAULT_END_OVER:
            break;

        case BOWSER_SUB_ACT_DEAD_FINAL_END:
            if (bowser_dead_final_stage_ending()) {
                o->oSubAction++; // BOWSER_SUB_ACT_DEAD_FINAL_END_OVER
            }
            break;

        case BOWSER_SUB_ACT_DEAD_FINAL_END_OVER:
            break;
    }
}

/**
 * Sets values for the BitFS platform to tilt
 */
void bowser_tilt_platform(struct Object *platform, s16 angSpeed) {
    s16 angle = o->oBowserAngleToCenter + 0x8000;
    platform->oAngleVelPitch = coss(angle) * angSpeed;
    platform->oAngleVelRoll = -sins(angle) * angSpeed;
}

/**
 * Struct for the BitFS tilt platform
 */
struct BowserTiltPlatformInfo {
    // Flag value to make sure platform moves smoothly
    //  0 = Don't move
    //  1 = Move angle behind Bowser
    // -1 = Move angle in front of Bowser
    s16 flag;
    // Sets platform's tilt angle speed (pattern: positive then negative)
    s16 angSpeed;
    // Sets how much time the platform can tilt, increases each move
    s16 time;
};

/**
 * Data for the BitFS tilt Platform
 */
struct BowserTiltPlatformInfo sBowsertiltPlatformData[] = {
    {  1,   10,  40 },
    {  0,    0,  74 },
    { -1,  -10, 114 },
    {  1,  -20, 134 },
    { -1,   20, 154 },
    {  1,   40, 164 },
    { -1,  -40, 174 },
    {  1,  -80, 179 },
    { -1,   80, 184 },
    {  1,  160, 186 },
    { -1, -160, 186 },
    {  1,    0,   0 },
};

/**
 * Makes the platform in BitFS tilt from left to right
 */
void bowser_act_tilt_lava_platform(void) {
    // Set platform object
    struct Object *platform = cur_obj_nearest_object_with_behavior(bhvTiltingBowserLavaPlatform);
    UNUSED s16 angle = o->oBowserAngleToCenter + 0x8000;
    s16 angSpeed;
    UNUSED u8 filler[4];

    // If there's not platform, return to default action
    if (platform == NULL) {
        o->oAction = BOWSER_ACT_DEFAULT;
    } else {
        s32 i = 0;
        s32 isNotTilting = TRUE;
        // Active platform tilting if the timer is not 0
        while (sBowsertiltPlatformData[i].time != 0) {
            // Move if the time values is more than the timer
            if (o->oTimer < sBowsertiltPlatformData[i].time) {
                // Set angle speed
                angSpeed = sBowsertiltPlatformData[i].angSpeed;
                // Move angle behind Bowser
                if (sBowsertiltPlatformData[i].flag > 0) {
                    angSpeed = (sBowsertiltPlatformData[i].time - o->oTimer - 1) * angSpeed;
                } else { // Move angle in front of Bowser
                    angSpeed = (o->oTimer - sBowsertiltPlatformData[i - 1].time) * angSpeed;
                }
                // Set angle values to the platform
                bowser_tilt_platform(platform, angSpeed);
                // Play sound effect
                if (angSpeed != 0) {
                    play_sound(SOUND_ENV_MOVING_BIG_PLATFORM, platform->header.gfx.cameraToObject);
                }
                isNotTilting = FALSE;
                break;
            }
            i++;
        }
        // If Bowser is done tilting, reset platform angles
        // and set Bowser to default action
        if (isNotTilting) {
            o->oAction = BOWSER_ACT_DEFAULT;
            platform->oAngleVelPitch = 0;
            platform->oAngleVelRoll = 0;
            platform->oFaceAnglePitch = 0;
            platform->oFaceAngleRoll = 0;
        }
    }
    // Extend "idle" animation
    cur_obj_extend_animation_if_at_end();
}

/**
 * Check if Bowser is offstage from a large distance or landed on a lethal floor
 */
s32 bowser_check_fallen_off_stage(void) {
    if (o->oAction != BOWSER_ACT_JUMP_ONTO_STAGE && o->oAction != BOWSER_ACT_TILT_LAVA_PLATFORM) {
        if (o->oPosY < o->oHomeY - 1000.0f) {
            return TRUE;
        }
        if (o->oMoveFlags & OBJ_MOVE_LANDED) {
            // Check for Fire Sea
            if (o->oFloorType == SURFACE_BURNING) {
                return TRUE;
            }
            // Check for Dark World - Sky
            if (o->oFloorType == SURFACE_DEATH_PLANE) {
                return TRUE;
            }
        }
    }
    return FALSE;
}

/**
 * Set Bowser's actions
 */
void (*sBowserActions[])(void) = {
    bowser_act_default,
    bowser_act_thrown,
    bowser_act_jump_onto_stage,
    bowser_act_dance,
    bowser_act_dead,
    bowser_act_wait,
    bowser_act_intro_walk,
    bowser_act_charge_mario,
    bowser_act_spit_fire_into_sky,
    bowser_act_spit_fire_onto_floor,
    bowser_act_hit_edge,
    bowser_act_turn_from_edge,
    bowser_act_hit_mine,
    bowser_act_big_jump,
    bowser_act_walk_to_mario,
    bowser_act_breath_fire,
    bowser_act_teleport,
    bowser_act_quick_jump,
    bowser_act_idle,
    bowser_act_tilt_lava_platform,
};

/**
 * Set Bowser's sound animations
 */
struct SoundState sBowserSoundStates[] = {
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 1, 0, -1, SOUND_OBJ_BOWSER_WALK },
    { 1, 0, -1, SOUND_OBJ2_BOWSER_ROAR },
    { 1, 0, -1, SOUND_OBJ2_BOWSER_ROAR },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 1, 20, 40, SOUND_OBJ_BOWSER_WALK },
    { 1, 20, -1, SOUND_OBJ_BOWSER_WALK },
    { 1, 20, 40, SOUND_OBJ_BOWSER_WALK },
    { 1, 0, -1, SOUND_OBJ_BOWSER_TAIL_PICKUP },
    { 1, 0, -1, SOUND_OBJ_BOWSER_DEFEATED },
    { 1, 8, -1, SOUND_OBJ_BOWSER_WALK },
    { 1, 8, 17, SOUND_OBJ_BOWSER_WALK },
    { 1, 8, -10, SOUND_OBJ_BOWSER_WALK },
    { 0, 0, 0, NO_SOUND },
    { 1, 5, -1, SOUND_OBJ_FLAME_BLOWN },
    { 0, 0, 0, NO_SOUND },
    { 0, 0, 0, NO_SOUND },
    { 1, 0, -1, SOUND_OBJ_BOWSER_TAIL_PICKUP },
    { 1, 0, -1, SOUND_OBJ2_BOWSER_ROAR },
};

/**
 * Set whenever Bowser should have rainbow light or not on each stage
 */
s8 sBowserRainbowLight[] = { FALSE, FALSE, TRUE };

/**
 * Set how much health Bowser has on each stage
 */
s8 sBowserHealth[] = { 1, 1, 3 };

/**
 * Update Bowser's actions when he's hands free
 */
void bowser_free_update(void) {
    struct Surface *floor;
    struct Object *platform;
    UNUSED f32 floorHeight;

    // Platform displacement check (for BitFS)
    if ((platform = o->platform) != NULL) {
        apply_platform_displacement(FALSE, platform);
    }
    // Reset grabbed status
    o->oBowserGrabbedStatus = BOWSER_GRAB_STATUS_NONE;
    // Update positions and actions (default action)
    cur_obj_update_floor_and_walls();
    cur_obj_call_action_function(sBowserActions);
    cur_obj_move_standard(-78);
    // Jump on stage if Bowser has fallen off
    if (bowser_check_fallen_off_stage()) {
        o->oAction = BOWSER_ACT_JUMP_ONTO_STAGE;
    }
    // Check floor height and platform
    floorHeight = find_floor(o->oPosX, o->oPosY, o->oPosZ, &floor);
    if ((floor != NULL) && (floor->object != NULL)) {
        o->platform = floor->object;
    } else {
        o->platform = NULL;
    }
    // Sound states for Bowser Animations
    exec_anim_sound_state(sBowserSoundStates);
}

/**
 * Update Bowser's actions when he's getting held
 */
void bowser_held_update(void) {
    // Reset fire sky status and make him intangible
    o->oBowserStatus &= ~BOWSER_STATUS_FIRE_SKY;
    cur_obj_become_intangible();

    switch (o->oBowserGrabbedStatus) {
        // Play pickup sound, start grabbed animation, and set throw action
        // Throw action won't be played until he's actually released
        case BOWSER_GRAB_STATUS_NONE:
            cur_obj_play_sound_2(SOUND_OBJ_BOWSER_TAIL_PICKUP);
            cur_obj_unrender_set_action_and_anim(BOWSER_ANIM_GRABBED, BOWSER_ACT_THROWN);
            o->oBowserGrabbedStatus++;
            break;
        // After the grabbed animation ends, play shaking animation in a loop
        case BOWSER_GRAB_STATUS_GRABBED:
            if (cur_obj_check_if_near_animation_end()) {
                cur_obj_init_animation_with_sound(BOWSER_ANIM_SHAKING);
                o->oBowserGrabbedStatus++;
            }
            break;
        case BOWSER_GRAB_STATUS_HOLDING:
            break;
    }

    // Reset move flags
    o->oMoveFlags = 0;
    // Copy angle values from Mario
    o->oBowserHeldAnglePitch = gMarioObject->oMoveAnglePitch;
    o->oBowserHeldAngleVelYaw = gMarioObject->oAngleVelYaw;
    o->oMoveAngleYaw = gMarioObject->oMoveAngleYaw;
}

/**
 * Update Bowser's actions when he's thrown and dropped
 */
void bowser_thrown_dropped_update(void) {
    f32 swingSpd;

    // Reset grabbed status
    o->oBowserGrabbedStatus = BOWSER_GRAB_STATUS_NONE;
    // Set throw action and vel values
    cur_obj_get_thrown_or_placed(1.0f, 1.0f, BOWSER_ACT_THROWN);
    // Set swing speed based of angle
    swingSpd = o->oBowserHeldAngleVelYaw / 3000.0 * 70.0f;
    // If less than 0, reduce speed
    if (swingSpd < 0.0f) {
        swingSpd = -swingSpd;
    }
    // If more than 90, increase speed
    if (swingSpd > 90.0f) {
        swingSpd *= 2.5;
    }
    // Set distance speed when throwing
    o->oForwardVel = coss(o->oBowserHeldAnglePitch) * swingSpd;
    o->oVelY = -sins(o->oBowserHeldAnglePitch) * swingSpd;
    cur_obj_become_intangible();

    // Reset timer and subactions
    o->prevObj->oAction = BOWSER_ACT_TAIL_THROWN; // prevObj is Bowser's Tail
    o->prevObj->oTimer = 0;
    o->prevObj->oSubAction = 0; //! Tail doesn't have sub actions

    o->oTimer = 0;
    o->oSubAction = 0;
}

/**
 * Bowser's main loop
 */
void bhv_bowser_loop(void) {
    s16 angleToMario;  // AngleToMario from Bowser's perspective
    s16 angleToCenter; // AngleToCenter from Bowser's perspective

    // Set distance/angle values
    o->oBowserDistToCenter = sqrtf(o->oPosX * o->oPosX + o->oPosZ * o->oPosZ);
    o->oBowserAngleToCenter = atan2s(0.0f - o->oPosZ, 0.0f - o->oPosX);
    angleToMario = abs_angle_diff(o->oMoveAngleYaw, o->oAngleToMario);
    angleToCenter = abs_angle_diff(o->oMoveAngleYaw, o->oBowserAngleToCenter);

    // Reset Status
    o->oBowserStatus &= ~0xFF;

    // Set bitflag status for distance/angle values
    // Only the first one is used
    if (angleToMario < 0x2000) {
        o->oBowserStatus |= BOWSER_STATUS_ANGLE_MARIO;
    }
    if (angleToCenter < 0x3800) {
        o->oBowserStatus |= BOWSER_STATUS_ANGLE_CENTER; // unused
    }
    if (o->oBowserDistToCenter < 1000.0f) {
        o->oBowserStatus |= BOWSER_STATUS_DIST_CENTER; // unused
    }
    if (o->oDistanceToMario < 850.0f) {
        o->oBowserStatus |= BOWSER_STATUS_DIST_MARIO; // unused
    }

    // Update Held state actions
    switch (o->oHeldState) {
        case HELD_FREE:
            bowser_free_update();
            break;
        case HELD_HELD:
            bowser_held_update();
            break;
        case HELD_THROWN:
            bowser_thrown_dropped_update();
            break;
        case HELD_DROPPED:
            bowser_thrown_dropped_update();
            break;
    }
    // Adjust model to the floor
    cur_obj_align_gfx_with_floor();

    // Adjust opacity (when not dead)
    // Mostly for the teleport action in BitFS
    if (o->oAction != BOWSER_ACT_DEAD) {
        if (o->oBowserTargetOpacity != o->oOpacity) {
            // increase opacity when oBowserTargetOpacity is 255
            if (o->oBowserTargetOpacity > o->oOpacity) {
                o->oOpacity += 20;
                if (o->oOpacity > 255) {
                    o->oOpacity = 255;
                }
            // reduce opacity when oBowserTargetOpacity is 0
            } else {
                o->oOpacity -= 20;
                if (o->oOpacity < 0) {
                    o->oOpacity = 0;
                }
            }
        }
    }
}

/**
 * Bowser's initial values and actions
 */
void bhv_bowser_init(void) {
    s32 level;
    // Set "reaction" value
    // It goes true when Bowser is a non-walking state
    o->oBowserIsReacting = TRUE;
    // Set no transparency opacity
    o->oOpacity = 255;
    o->oBowserTargetOpacity = 255;
    // Set Bowser B-param depending of the stage
    if (gCurrLevelNum == LEVEL_BOWSER_2) {
        level = BOWSER_BP_BITFS;
    } else if (gCurrLevelNum == LEVEL_BOWSER_3) {
        level = BOWSER_BP_BITS;
    } else { // LEVEL_BOWSER_1
        level = BOWSER_BP_BITDW;
    }
    o->oBehParams2ndByte = level;
    // Set health and rainbow light depending of the level
    o->oBowserRainbowLight = sBowserRainbowLight[level];
    o->oHealth = sBowserHealth[level];
    // Start camera event, this event is not defined so maybe
    // the "start arena" cutscene was originally called this way
    cur_obj_start_cam_event(o, CAM_EVENT_BOWSER_INIT);
    o->oAction = BOWSER_ACT_WAIT;
    // Set eyes status
    o->oBowserEyesTimer = 0;
    o->oBowserEyesShut = FALSE;
}

Gfx *geo_update_body_rot_from_parent(s32 callContext, UNUSED struct GraphNode *node, Mat4 mtx) {
    if (callContext == GEO_CONTEXT_RENDER) {
        Mat4 mtx2;
        struct Object *obj = (struct Object *) gCurGraphNodeObject;
        if (obj->prevObj != NULL) {
            create_transformation_from_matrices(mtx2, mtx, *gCurGraphNodeCamera->matrixPtr);
            obj_update_pos_from_parent_transformation(mtx2, obj->prevObj);
            obj_set_gfx_pos_from_pos(obj->prevObj);
        }
    }

    return NULL;
}

/**
 * Bowser's eyes Geo-Switch-Case IDs, defined from Mario's POV
 */
enum BowserEyesGSCId {
    /*0x00*/ BOWSER_EYES_OPEN,
    /*0x01*/ BOWSER_EYES_HALF_CLOSED,
    /*0x02*/ BOWSER_EYES_CLOSED,
    /*0x03*/ BOWSER_EYES_LEFT,
    /*0x04*/ BOWSER_EYES_FAR_LEFT,
    /*0x05*/ BOWSER_EYES_RIGHT,
    /*0x06*/ BOWSER_EYES_FAR_RIGHT,
    /*0x07*/ BOWSER_EYES_DERP, // unused
    /*0x08*/ BOWSER_EYES_CROSS, // unused
    /*0x08*/ BOWSER_EYES_RESET // set eyes back to open
};

/**
 * Controls Bowser's eye open stage, including blinking and look directions
 */
void bowser_open_eye_switch(struct Object *obj, struct GraphNodeSwitchCase *switchCase) {
    s32 eyeCase;
    s16 angleFromMario;

    angleFromMario = abs_angle_diff(obj->oMoveAngleYaw, obj->oAngleToMario);
    eyeCase = switchCase->selectedCase;

    switch (eyeCase) {
        case BOWSER_EYES_OPEN:
            // Mario is in Bowser's field of view
            if (angleFromMario > 0x2000) {
                if (obj->oAngleVelYaw > 0) {
                    switchCase->selectedCase = BOWSER_EYES_RIGHT;
                }
                if (obj->oAngleVelYaw < 0) {
                    switchCase->selectedCase = BOWSER_EYES_LEFT;
                }
            }
            // Half close, start blinking
            if (obj->oBowserEyesTimer > 50) {
                switchCase->selectedCase = BOWSER_EYES_HALF_CLOSED;
            }
            break;

        case BOWSER_EYES_HALF_CLOSED:
            // Close, blinking
            if (obj->oBowserEyesTimer > 2) {
                switchCase->selectedCase = BOWSER_EYES_CLOSED;
            }
            break;

        case BOWSER_EYES_CLOSED:
            // Reset blinking
            if (obj->oBowserEyesTimer > 2) {
                switchCase->selectedCase = BOWSER_EYES_RESET;
            }
            break;

        case BOWSER_EYES_RESET:
            // Open, no longer blinking
            if (obj->oBowserEyesTimer > 2) {
                switchCase->selectedCase = BOWSER_EYES_OPEN;
            }
            break;

        case BOWSER_EYES_RIGHT:
            // Look more on the right if angle didn't change
            // Otherwise, look at the center (open)
            if (obj->oBowserEyesTimer > 2) {
                switchCase->selectedCase = BOWSER_EYES_FAR_RIGHT;
                if (obj->oAngleVelYaw <= 0) {
                    switchCase->selectedCase = BOWSER_EYES_OPEN;
                }
            }
            break;

        case BOWSER_EYES_FAR_RIGHT:
            // Look close right if angle was drastically changed
            if (obj->oAngleVelYaw <= 0) {
                switchCase->selectedCase = BOWSER_EYES_RIGHT;
            }
            break;

        case BOWSER_EYES_LEFT:
            // Look more on the left if angle didn't change
            // Otherwise, look at the center (open)
            if (obj->oBowserEyesTimer > 2) {
                switchCase->selectedCase = BOWSER_EYES_FAR_LEFT;
                if (obj->oAngleVelYaw >= 0) {
                    switchCase->selectedCase = BOWSER_EYES_OPEN;
                }
            }
            break;

        case BOWSER_EYES_FAR_LEFT:
            // Look close left if angle was drastically changed
            if (obj->oAngleVelYaw >= 0) {
                switchCase->selectedCase = BOWSER_EYES_LEFT;
            }
            break;

        default:
            switchCase->selectedCase = BOWSER_EYES_OPEN;
    }

    // Reset timer if eye case has changed
    if (switchCase->selectedCase != eyeCase) {
        obj->oBowserEyesTimer = -1;
    }
}

/**
 * Geo switch for controlling the state of Bowser's eye direction and open/closed
 * state. Checks whether oBowserEyesShut is TRUE and closes eyes if so and processes
 * direction otherwise.
 */
Gfx *geo_switch_bowser_eyes(s32 callContext, struct GraphNode *node, UNUSED Mat4 *mtx) {
    UNUSED s16 eyeShut;
    UNUSED u8 filler[4];
    struct Object *obj = (struct Object *) gCurGraphNodeObject;
    struct GraphNodeSwitchCase *switchCase = (struct GraphNodeSwitchCase *) node;

    if (callContext == GEO_CONTEXT_RENDER) {
        if (gCurGraphNodeHeldObject != NULL) {
            obj = gCurGraphNodeHeldObject->objNode;
        }

        switch (eyeShut = obj->oBowserEyesShut) {
            case FALSE: // eyes open, handle eye looking direction
                bowser_open_eye_switch(obj, switchCase);
                break;
            case TRUE: // eyes closed, blinking
                switchCase->selectedCase = BOWSER_EYES_CLOSED;
                break;
        }

        obj->oBowserEyesTimer++;
    }

    return NULL;
}

/**
 * Geo switch that sets Bowser's Rainbow coloring (in BitS)
 */
Gfx *geo_bits_bowser_coloring(s32 callContext, struct GraphNode *node, UNUSED s32 context) {
    Gfx *gfxHead = NULL;
    Gfx *gfx;

    if (callContext == GEO_CONTEXT_RENDER) {
        struct Object *obj = (struct Object *) gCurGraphNodeObject;
        struct GraphNodeGenerated *graphNode = (struct GraphNodeGenerated *) node;

        if (gCurGraphNodeHeldObject != NULL) {
            obj = gCurGraphNodeHeldObject->objNode;
        }

        // Set layers if object is transparent or not
        if (obj->oOpacity == 255) {
            graphNode->fnNode.node.flags = (graphNode->fnNode.node.flags & 0xFF) | (LAYER_OPAQUE << 8);
        } else {
            graphNode->fnNode.node.flags = (graphNode->fnNode.node.flags & 0xFF) | (LAYER_TRANSPARENT << 8);
        }

        gfx = gfxHead = alloc_display_list(2 * sizeof(Gfx));

        // If TRUE, clear lighting to give rainbow color
        if (obj->oBowserRainbowLight) {
            gSPClearGeometryMode(gfx++, G_LIGHTING);
        }

        gSPEndDisplayList(gfx);
    }

    return gfxHead;
}
