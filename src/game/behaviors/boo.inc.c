// boo.c.inc

static struct ObjectHitbox sBooGivingStarHitbox = {
    /* interactType: */      0,
    /* downOffset: */        0,
    /* damageOrCoinValue: */ 3,
    /* health: */            3,
    /* numLootCoins: */      0,
    /* radius: */            140,
    /* height: */            80,
    /* hurtboxRadius: */     40,
    /* hurtboxHeight: */     60,
};

// Relative positions
static s16 sCourtyardBooTripletPositions[][3] = {
    {0, 50, 0},
    {210, 110, 210},
    {-210, 70, -210}
};

static void boo_stop(void) {
    o->oForwardVel = 0.0f;
    o->oVelY = 0.0f;
    o->oGravity = 0.0f;
}

void bhv_boo_init(void) {
    o->oBooInitialMoveYaw = o->oMoveAngleYaw;
}

static s32 boo_should_be_stopped(void) {
    if (cur_obj_has_behavior(bhvMerryGoRoundBigBoo) || cur_obj_has_behavior(bhvMerryGoRoundBoo)) {
        if (gMarioOnMerryGoRound == FALSE) {
            return TRUE;
        } else {
            return FALSE;
        }
    } else {
        if (o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM) {
            return TRUE;
        }

        if (o->oRoom == 10) {
            if (gTimeStopState & TIME_STOP_MARIO_OPENED_DOOR) {
                return TRUE;
            }
        }
    }

    return FALSE;
}

static s32 boo_should_be_active(void) {
    f32 activationRadius;

    if (cur_obj_has_behavior(bhvBalconyBigBoo)) {
        activationRadius = 5000.0f;
    } else {
        activationRadius = 1500.0f;
    }

    if (cur_obj_has_behavior(bhvMerryGoRoundBigBoo) || cur_obj_has_behavior(bhvMerryGoRoundBoo)) {
        if (gMarioOnMerryGoRound == TRUE) {
            return TRUE;
        } else {
            return FALSE;
        }
    } else if (o->oRoom == -1) {
        if (o->oDistanceToMario < activationRadius) {
            return TRUE;
        }
    } else if (!boo_should_be_stopped()) {
        if (
            o->oDistanceToMario < activationRadius &&
            (o->oRoom == gMarioCurrentRoom || gMarioCurrentRoom == 0)
        ) {
            return TRUE;
        }
    }

    return FALSE;
}

void bhv_courtyard_boo_triplet_init(void) {
    s32 i;
    struct Object *boo;

    if (gHudDisplay.stars < 12) {
        obj_mark_for_deletion(o);
    } else {
        for (i = 0; i < 3; i++) {
            boo = spawn_object_relative(
                0x01,
                sCourtyardBooTripletPositions[i][0],
                sCourtyardBooTripletPositions[i][1],
                sCourtyardBooTripletPositions[i][2],
                o,
                MODEL_BOO,
                bhvGhostHuntBoo
            );

            boo->oMoveAngleYaw = random_u16();
        }
    }
}

static void boo_approach_target_opacity_and_update_scale(void) {
    f32 scale;

    if (o->oBooTargetOpacity != o->oOpacity) {
        if (o->oBooTargetOpacity > o->oOpacity) {
            o->oOpacity += 20;

            if (o->oBooTargetOpacity < o->oOpacity) {
                o->oOpacity = o->oBooTargetOpacity;
            }
        } else {
            o->oOpacity -= 20;

            if (o->oBooTargetOpacity > o->oOpacity) {
                o->oOpacity = o->oBooTargetOpacity;
            }
        }
    }

    scale = (o->oOpacity/255.0f * 0.4 + 0.6) * o->oBooBaseScale;
    obj_scale(o, scale); // why no cur_obj_scale? was cur_obj_scale written later?
}

static void boo_oscillate(s32 ignoreOpacity) {
    o->oFaceAnglePitch = sins(o->oBooOscillationTimer) * 0x400;

    if (o->oOpacity == 0xFF || ignoreOpacity == TRUE) {
        o->header.gfx.scale[0] = sins(o->oBooOscillationTimer) * 0.08 + o->oBooBaseScale;
        o->header.gfx.scale[1] = -sins(o->oBooOscillationTimer) * 0.08 + o->oBooBaseScale;
        o->header.gfx.scale[2] = o->header.gfx.scale[0];
        o->oGravity = sins(o->oBooOscillationTimer) * o->oBooBaseScale;
        o->oBooOscillationTimer += 0x400;
    }
}

static s32 boo_vanish_or_appear(void) {
    s16 relativeAngleToMario = abs_angle_diff(o->oAngleToMario, o->oMoveAngleYaw);
    s16 relativeMarioFaceAngle = abs_angle_diff(o->oMoveAngleYaw, gMarioObject->oFaceAngleYaw);
    // magic?
    s16 relativeAngleToMarioThreshhold = 0x1568;
    s16 relativeMarioFaceAngleThreshhold = 0x6b58;
    s32 doneAppearing = FALSE;

    o->oVelY = 0.0f;

    if (
        relativeAngleToMario > relativeAngleToMarioThreshhold ||
        relativeMarioFaceAngle < relativeMarioFaceAngleThreshhold
    ) {
        if (o->oOpacity == 40) {
            o->oBooTargetOpacity = 255;
            cur_obj_play_sound_2(SOUND_OBJ_BOO_LAUGH_LONG);
        }

        if (o->oOpacity > 180) {
            doneAppearing = TRUE;
        }
    } else if (o->oOpacity == 255) {
        o->oBooTargetOpacity = 40;
    }

    return doneAppearing;
}

static void boo_set_move_yaw_for_during_hit(s32 hurt) {
    cur_obj_become_intangible();

    o->oFlags &= ~OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW;
    o->oBooMoveYawBeforeHit = (f32) o->oMoveAngleYaw;

    if (hurt != FALSE) {
        o->oBooMoveYawDuringHit = gMarioObject->oMoveAngleYaw;
    } else if (coss((s16)o->oMoveAngleYaw - (s16)o->oAngleToMario) < 0.0f) {
        o->oBooMoveYawDuringHit = o->oMoveAngleYaw;
    } else {
        o->oBooMoveYawDuringHit = (s16)(o->oMoveAngleYaw + 0x8000);
    }
}

static void boo_move_during_hit(s32 roll, f32 fVel) {
    // Boos seem to have been supposed to oscillate up then down then back again
    // when hit. However it seems the programmers forgot to scale the cosine,
    // so the Y velocity goes from 1 to -1 and back to 1 over 32 frames.
    // This is such a small change that the Y position only changes by 5 units.
    // It's completely unnoticable in-game.
    s32 oscillationVel = o->oTimer * 0x800 + 0x800;
    
    o->oForwardVel = fVel;
    o->oVelY = coss(oscillationVel);
    o->oMoveAngleYaw = o->oBooMoveYawDuringHit;

    if (roll != FALSE) {
        o->oFaceAngleYaw  += D_8032F0CC[o->oTimer];
        o->oFaceAngleRoll += D_8032F0CC[o->oTimer];
    }
}

static void big_boo_shake_after_hit(void) {
    // Oscillate yaw
    s32 oscillationVel = o->oTimer * 0x2000 - 0x3E000;
    o->oFaceAngleYaw += coss(oscillationVel) * 0x400;
}

static void boo_reset_after_hit(void) {
    o->oMoveAngleYaw = o->oBooMoveYawBeforeHit;
    o->oFlags |= OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW;
    o->oInteractStatus = 0;
}

// called iff boo/big boo/cage boo is in action 2, which only occurs if it was non-attack-ly interacted with/bounced on?
static s32 boo_update_after_bounced_on(f32 a0) {
    boo_stop();

    if (o->oTimer == 0) {
        boo_set_move_yaw_for_during_hit(FALSE);
    }

    if (o->oTimer < 32) {
        boo_move_during_hit(FALSE, D_8032F0CC[o->oTimer]/5000.0f * a0);
    } else {
        cur_obj_become_tangible();
        boo_reset_after_hit();
        o->oAction = 1;
        return TRUE;
    }

    return FALSE;
}

// called iff big boo nonlethally hit
static s32 big_boo_update_during_nonlethal_hit(f32 a0) {
    boo_stop();
    
    if (o->oTimer == 0) {
        boo_set_move_yaw_for_during_hit(TRUE);
    }
    
    if (o->oTimer < 32) {
        boo_move_during_hit(TRUE, D_8032F0CC[o->oTimer]/5000.0f * a0);
    } else if (o->oTimer < 48) {
        big_boo_shake_after_hit();
    } else {
        cur_obj_become_tangible();
        boo_reset_after_hit();
        
        o->oAction = 1;
        
        return TRUE;
    }
    
    return FALSE;
}

// called every frame once mario lethally hits the boo until the boo is deleted,
// returns whether death is complete
static s32 boo_update_during_death(void) {
    struct Object *parentBigBoo;

    if (o->oTimer == 0) {
        o->oForwardVel = 40.0f;
        o->oMoveAngleYaw = gMarioObject->oMoveAngleYaw;
        o->oBooDeathStatus = BOO_DEATH_STATUS_DYING;
        o->oFlags &= ~OBJ_FLAG_SET_FACE_YAW_TO_MOVE_YAW;
    } else {
        if (o->oTimer == 5) {
            o->oBooTargetOpacity = 0;
        }

        if (o->oTimer > 30 || o->oMoveFlags & 0x200) {
            spawn_mist_particles();
            o->oBooDeathStatus = BOO_DEATH_STATUS_DEAD;

            if (o->oBooParentBigBoo != NULL) {
                parentBigBoo = o->oBooParentBigBoo;
                
#ifndef VERSION_JP
                if (!cur_obj_has_behavior(bhvBoo)) {
                    parentBigBoo->oBigBooNumMinionBoosKilled++;
                }
#else
                parentBigBoo->oBigBooNumMinionBoosKilled++;
#endif
            }
            
            return TRUE;
        }
    }

    o->oVelY = 5.0f;
    o->oFaceAngleRoll += 0x800;
    o->oFaceAngleYaw += 0x800;

    return FALSE;
}

static s32 obj_has_attack_type(u32 attackType) {
    if ((o->oInteractStatus & INT_STATUS_ATTACK_MASK) == attackType) {
        return TRUE;
    } else {
        return FALSE;
    }
}

static s32 boo_get_attack_status(void) {
    s32 attackStatus = BOO_NOT_ATTACKED;
    
    if (o->oInteractStatus & INT_STATUS_INTERACTED) {
        if ((o->oInteractStatus & INT_STATUS_WAS_ATTACKED) && obj_has_attack_type(ATTACK_FROM_ABOVE) == FALSE) {
            cur_obj_become_intangible();
            
            o->oInteractStatus = 0;
            
            cur_obj_play_sound_2(SOUND_OBJ_BOO_LAUGH_SHORT);
            
            attackStatus = BOO_ATTACKED;
        } else {
            cur_obj_play_sound_2(SOUND_OBJ_BOO_BOUNCE_TOP);
            
            o->oInteractStatus = 0;
            
            attackStatus = BOO_BOUNCED_ON;
        }
    }
    
    return attackStatus;
}

// boo idle/chasing movement?
static void boo_chase_mario(f32 a0, s16 a1, f32 a2) {
    f32 sp1C;
    s16 sp1A;
    
    if (boo_vanish_or_appear()) {
        o->oInteractType = 0x8000;
        
        if (cur_obj_lateral_dist_from_mario_to_home() > 1500.0f) {
            sp1A = cur_obj_angle_to_home();
        } else {
            sp1A = o->oAngleToMario;
        }
        
        cur_obj_rotate_yaw_toward(sp1A, a1);
        o->oVelY = 0.0f;
        
        if (mario_is_in_air_action() == 0) {
            sp1C = o->oPosY - gMarioObject->oPosY;
            if (a0 < sp1C && sp1C < 500.0f) {
                o->oVelY = increment_velocity_toward_range(o->oPosY, gMarioObject->oPosY + 50.0f, 10.f, 2.0f);
            }
        }
        
        cur_obj_set_vel_from_mario_vel(10.0f - o->oBooNegatedAggressiveness, a2);
        
        if (o->oForwardVel != 0.0f) {
            boo_oscillate(FALSE);
        }
    } else {
        o->oInteractType = 0;
        // why is boo_stop not used here
        o->oForwardVel = 0.0f;
        o->oVelY = 0.0f;
        o->oGravity = 0.0f;
    }
}

static void boo_act_0(void) {
    o->activeFlags |= ACTIVE_FLAG_MOVE_THROUGH_GRATE;
    
    if (o->oBehParams2ndByte == 2) {
        o->oRoom = 10;
    }
    
    cur_obj_set_pos_to_home();
    o->oMoveAngleYaw = o->oBooInitialMoveYaw;
    boo_stop();
    
    o->oBooParentBigBoo = cur_obj_nearest_object_with_behavior(bhvGhostHuntBigBoo);
    o->oBooBaseScale = 1.0f;
    o->oBooTargetOpacity = 0xFF;
    
    if (boo_should_be_active()) {
        // Condition is met if the object is bhvBalconyBigBoo or bhvMerryGoRoundBoo
        if (o->oBehParams2ndByte == 2) {
            o->oBooParentBigBoo = NULL;
            o->oAction = 5;
        } else {
            o->oAction = 1;
        }
    }
}

static void boo_act_5(void) {
    if (o->oTimer < 30) {
        o->oVelY = 0.0f;
        o->oForwardVel = 13.0f;
        boo_oscillate(FALSE);
        o->oWallHitboxRadius = 0.0f;
    } else {
        o->oAction = 1;
        o->oWallHitboxRadius = 30.0f;
    }
}

static void boo_act_1(void) {
    s32 attackStatus;
    
    if (o->oTimer == 0) {
        o->oBooNegatedAggressiveness = -random_float() * 5.0f;
        o->oBooTurningSpeed = (s32)(random_float() * 128.0f);
    }
    
    boo_chase_mario(-100.0f, o->oBooTurningSpeed + 0x180, 0.5f);
    attackStatus = boo_get_attack_status();
    
    if (boo_should_be_stopped()) {
        o->oAction = 0;
    }
    
    if (attackStatus == BOO_BOUNCED_ON) {
        o->oAction = 2;
    }
    
    if (attackStatus == BOO_ATTACKED) {
        o->oAction = 3;
    }
    
    if (attackStatus == BOO_ATTACKED) {
        create_sound_spawner(SOUND_OBJ_DYING_ENEMY1);
    }
}

static void boo_act_2(void) {
    if (boo_update_after_bounced_on(20.0f)) {
        o->oAction = 1;
    }
}

static void boo_act_3(void) {
    if (boo_update_during_death()) {
        if (o->oBehParams2ndByte != 0) {
            obj_mark_for_deletion(o);
        } else {
            o->oAction = 4;
            cur_obj_disable();
        }
    }
}

// Called when a Go on a Ghost Hunt boo dies
static void boo_act_4(void) {
    s32 dialogID;

    // If there are no remaining "minion" boos, show the dialog of the Big Boo
    if (cur_obj_nearest_object_with_behavior(bhvGhostHuntBoo) == NULL) {
        dialogID = DIALOG_108;
    } else {
        dialogID = DIALOG_107;
    }

    if (cur_obj_update_dialog(2, 2, dialogID, 0)) {
        create_sound_spawner(SOUND_OBJ_DYING_ENEMY1);
        obj_mark_for_deletion(o);
        
        if (dialogID == DIALOG_108) { // If the Big Boo should spawn, play the jingle
            play_puzzle_jingle();
        }
    }
}

static void (*sBooActions[])(void) = {
    boo_act_0,
    boo_act_1,
    boo_act_2,
    boo_act_3,
    boo_act_4,
    boo_act_5
};

void bhv_boo_loop(void) {
    //PARTIAL_UPDATE
    
    cur_obj_update_floor_and_walls();
    cur_obj_call_action_function(sBooActions);
    cur_obj_move_standard(78);
    boo_approach_target_opacity_and_update_scale();
    
    if (obj_has_behavior(o->parentObj, bhvMerryGoRoundBooManager)) {
        if (o->activeFlags == 0) {
            o->parentObj->oMerryGoRoundBooManagerNumBoosKilled++;
        }
    }
    
    o->oInteractStatus = 0;
}

static void big_boo_act_0(void) {
    if (cur_obj_has_behavior(bhvBalconyBigBoo)) {
        obj_set_secondary_camera_focus();
        // number of killed boos set > 5 so that boo always loads
        // redundant? this is also done in behavior_data.s
        o->oBigBooNumMinionBoosKilled = 10;
    }
    
    o->oBooParentBigBoo = NULL;
    
#ifndef VERSION_JP
    if (boo_should_be_active() && gDebugInfo[5][0] + 5 <= o->oBigBooNumMinionBoosKilled) {
#else
    if (boo_should_be_active() && o->oBigBooNumMinionBoosKilled >= 5) {
#endif
        o->oAction = 1;
        
        cur_obj_set_pos_to_home();
        o->oMoveAngleYaw = o->oBooInitialMoveYaw;
        
        cur_obj_unhide();
        
        o->oBooTargetOpacity = 0xFF;
        o->oBooBaseScale = 3.0f;
        o->oHealth = 3;
        
        cur_obj_scale(3.0f);
        cur_obj_become_tangible();
    } else {
        cur_obj_hide();
        cur_obj_become_intangible();
        boo_stop();
    }
}

static void big_boo_act_1(void) {
    s32 attackStatus;
    s16 sp22;
    f32 sp1C;
    
    if (o->oHealth == 3) {
        sp22 = 0x180; sp1C = 0.5f;
    } else if (o->oHealth == 2) {
        sp22 = 0x240; sp1C = 0.6f;
    } else {
        sp22 = 0x300; sp1C = 0.8f;
    }
    
    boo_chase_mario(-100.0f, sp22, sp1C);
    
    attackStatus = boo_get_attack_status();
    
    // redundant; this check is in boo_should_be_stopped
    if (cur_obj_has_behavior(bhvMerryGoRoundBigBoo)) {
        if (gMarioOnMerryGoRound == FALSE) {
            o->oAction = 0;
        }
    } else if (boo_should_be_stopped()) {
        o->oAction = 0;
    }
    
    if (attackStatus == BOO_BOUNCED_ON) {
        o->oAction = 2;
    }
    
    if (attackStatus == BOO_ATTACKED) {
        o->oAction = 3;
    }
    
    if (attackStatus == 1) {
        create_sound_spawner(SOUND_OBJ_THWOMP);
    }
}

static void big_boo_act_2(void) {
    if (boo_update_after_bounced_on(20.0f)) {
        o->oAction = 1;
    }
}

static void big_boo_spawn_ghost_hunt_star(void) {
    spawn_default_star(980.0f, 1100.0f, 250.0f);
}

static void big_boo_spawn_balcony_star(void) {
    spawn_default_star(700.0f, 3200.0f, 1900.0f);
}

static void big_boo_spawn_merry_go_round_star(void) {
    struct Object *merryGoRound;

    spawn_default_star(-1600.0f, -2100.0f, 205.0f);

    merryGoRound = cur_obj_nearest_object_with_behavior(bhvMerryGoRound);

    if (merryGoRound != NULL) {
        merryGoRound->oMerryGoRoundStopped = TRUE;
    }
}

static void big_boo_act_3(void) {
    if (o->oTimer == 0) {
        o->oHealth--;
    }
    
    if (o->oHealth == 0) {
        if (boo_update_during_death()) {
            cur_obj_disable();
            
            o->oAction = 4;
            
            obj_set_angle(o, 0, 0, 0);
            
            if (o->oBehParams2ndByte == 0) {
                big_boo_spawn_ghost_hunt_star();
            } else if (o->oBehParams2ndByte == 1) {
                big_boo_spawn_merry_go_round_star();
            } else {
                big_boo_spawn_balcony_star();
            }
        }
    } else {
        if (o->oTimer == 0) {
            spawn_mist_particles();
            o->oBooBaseScale -= 0.5;
        }
        
        if (big_boo_update_during_nonlethal_hit(40.0f)) {
            o->oAction = 1;
        }
    }
}

static void big_boo_act_4(void) {
#ifndef VERSION_JP
    boo_stop();
#endif
    
    if (o->oBehParams2ndByte == 0) {
        obj_set_pos(o, 973, 0, 626);
        
        if (o->oTimer > 60 && o->oDistanceToMario < 600.0f) {
            obj_set_pos(o,  973, 0, 717);
            
            spawn_object_relative(0, 0, 0,    0, o, MODEL_BBH_STAIRCASE_STEP, bhvBooBossSpawnedBridge);
            spawn_object_relative(1, 0, 0, -200, o, MODEL_BBH_STAIRCASE_STEP, bhvBooBossSpawnedBridge);
            spawn_object_relative(2, 0, 0,  200, o, MODEL_BBH_STAIRCASE_STEP, bhvBooBossSpawnedBridge);
            
            obj_mark_for_deletion(o);
        }
    } else {
        obj_mark_for_deletion(o);
    }
}

static void (*sBooGivingStarActions[])(void) = {
    big_boo_act_0,
    big_boo_act_1,
    big_boo_act_2,
    big_boo_act_3,
    big_boo_act_4
};

void bhv_big_boo_loop(void) {
    //PARTIAL_UPDATE
    
    obj_set_hitbox(o, &sBooGivingStarHitbox);
    
    o->oGraphYOffset = o->oBooBaseScale * 60.0f;
    
    cur_obj_update_floor_and_walls();
    cur_obj_call_action_function(sBooGivingStarActions);
    cur_obj_move_standard(78);
    
    boo_approach_target_opacity_and_update_scale();
    o->oInteractStatus = 0;
}

static void boo_with_cage_act_0(void) {
    o->oBooParentBigBoo = NULL;
    o->oBooTargetOpacity = 0xFF;
    o->oBooBaseScale = 2.0f;
    
    cur_obj_scale(2.0f);
    cur_obj_become_tangible();
    
    if (boo_should_be_active()) {
        o->oAction = 1;
    }
}

static void boo_with_cage_act_1(void) {
    s32 attackStatus;

    boo_chase_mario(100.0f, 512, 0.5f);
    
    attackStatus = boo_get_attack_status();
    
    if (boo_should_be_stopped()) {
        o->oAction = 0;
    }
    
    if (attackStatus == BOO_BOUNCED_ON) {
        o->oAction = 2;
    }
    
    if (attackStatus == BOO_ATTACKED) {
        o->oAction = 3;
    }
}

static void boo_with_cage_act_2(void) {
    if (boo_update_after_bounced_on(20.0f)) {
        o->oAction = 1;
    }
}

static void boo_with_cage_act_3(void) {
    if (boo_update_during_death()) {
        obj_mark_for_deletion(o);
    }
}

void bhv_boo_with_cage_init(void) {
    struct Object* cage;
    
    if (gHudDisplay.stars < 12) {
        obj_mark_for_deletion(o);
    } else {
        cage = spawn_object(o, MODEL_HAUNTED_CAGE, bhvBooCage);
        cage->oBehParams = o->oBehParams;
    }
}

static void (*sBooWithCageActions[])(void) = {
    boo_with_cage_act_0,
    boo_with_cage_act_1,
    boo_with_cage_act_2,
    boo_with_cage_act_3
};

void bhv_boo_with_cage_loop(void)
{
    //PARTIAL_UPDATE
    
    cur_obj_update_floor_and_walls();
    cur_obj_call_action_function(sBooWithCageActions);
    cur_obj_move_standard(78);

    boo_approach_target_opacity_and_update_scale();
    o->oInteractStatus = 0;
}

void bhv_merry_go_round_boo_manager_loop(void) {
    switch (o->oAction) {
        case 0:
            if (o->oDistanceToMario < 1000.0f) {
                if (o->oMerryGoRoundBooManagerNumBoosKilled < 5) {
                    if (o->oMerryGoRoundBooManagerNumBoosSpawned != 5) {
                        if (o->oMerryGoRoundBooManagerNumBoosSpawned - o->oMerryGoRoundBooManagerNumBoosKilled < 2) {
                            spawn_object(o, MODEL_BOO, bhvMerryGoRoundBoo);
                            o->oMerryGoRoundBooManagerNumBoosSpawned++;
                        }
                    }
                    
                    o->oAction++;
                }
                
                if (o->oMerryGoRoundBooManagerNumBoosKilled > 4) {
                    struct Object *boo = spawn_object(o, MODEL_BOO, bhvMerryGoRoundBigBoo);
                    obj_copy_behavior_params(boo, o);
                    
                    o->oAction = 2;
                    
#ifndef VERSION_JP
                    play_puzzle_jingle();
#else
                    play_sound(SOUND_GENERAL2_RIGHT_ANSWER, gDefaultSoundArgs);
#endif
                }
            }
            
            break;
        case 1:
            if (o->oTimer > 60) {
                o->oAction = 0;
            }
            
            break;
        case 2:
            break;
    }
}

void obj_set_secondary_camera_focus(void) {
    gSecondCameraFocus = o;
}

void bhv_animated_texture_loop(void) {
    cur_obj_set_pos_to_home_with_debug();
}

void bhv_boo_in_castle_loop(void) {
    s16 targetAngle;
    
    o->oBooBaseScale = 2.0f;
    
    if (o->oAction == 0) {
        cur_obj_hide();
        
        if (gHudDisplay.stars < 12) {
            obj_mark_for_deletion(o);
        }
        
        if (gMarioCurrentRoom == 1) {
            o->oAction++;
        }
    } else if (o->oAction == 1) {
        cur_obj_unhide();
        
        o->oOpacity = 180;
        
        if (o->oTimer == 0) {
            cur_obj_scale(o->oBooBaseScale);
        }
        
        if (o->oDistanceToMario < 1000.0f) {
            o->oAction++;
            cur_obj_play_sound_2(SOUND_OBJ_BOO_LAUGH_LONG);
        }
        
        o->oForwardVel = 0.0f;
        targetAngle = o->oAngleToMario;
    } else {
        cur_obj_forward_vel_approach_upward(32.0f, 1.0f);
        
        o->oHomeX = -1000.0f;
        o->oHomeZ = -9000.0f;
        
        targetAngle = cur_obj_angle_to_home();
        
        if (o->oPosZ < -5000.0f) {
            if (o->oOpacity > 0) {
                o->oOpacity -= 20;
            } else {
                o->oOpacity = 0;
            }
        }
        
        if (o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM) {
            o->oAction = 1;
        }
    }
    
    o->oVelY = 0.0f;
    
    targetAngle = cur_obj_angle_to_home();
    
    cur_obj_rotate_yaw_toward(targetAngle, 0x5A8);
    boo_oscillate(TRUE);
    cur_obj_move_using_fvel_and_gravity();
}

void bhv_boo_boss_spawned_bridge_loop(void) {
    f32 targetY;
    
    switch (o->oBehParams2ndByte) {
        case 1:
            targetY = 0.0f;
            break;
        case 0:
            targetY = -206.0f;
            break;
        case 2:
            targetY = -413.0f;
            break;
    }
    
    switch(o->oAction) {
        case 0:
            o->oPosY = o->oHomeY - 620.0f;
            o->oAction++;
            // fallthrough
        case 1:
            o->oPosY += 8.0f;
            cur_obj_play_sound_1(SOUND_ENV_ELEVATOR2);
            
            if (o->oPosY > targetY) {
                o->oPosY = targetY;
                o->oAction++;
            }
            
            break;
        case 2:
            if (o->oTimer == 0) {
                cur_obj_play_sound_2(SOUND_GENERAL_UNKNOWN4_LOWPRIO);
            }
            
            if (cur_obj_move_up_and_down(o->oTimer)) {
                o->oAction++;
            }
            
            break;
        case 3:
            if (o->oTimer == 0 && o->oBehParams2ndByte == 1) {
                play_puzzle_jingle();
            }
            
            break;
    }
}
