// mr_blizzard.inc.c

// Mr. Blizzard hitbox
struct ObjectHitbox sMrBlizzardHitbox = {
    /* interactType:      */ INTERACT_MR_BLIZZARD,
    /* downOffset:        */ 24,
    /* damageOrCoinValue: */ 2,
    /* health:            */ 99,
    /* numLootCoins:      */ 3,
    /* radius:            */ 65,
    /* height:            */ 170,
    /* hurtboxRadius:     */ 65,
    /* hurtboxHeight:     */ 170,
};

// Mr. Blizzard particle spawner.
void mr_blizzard_spawn_white_particles(s8 count, s8 offsetY, s8 forwardVelBase, s8 velYBase,
                                       s8 sizeBase) {
    static struct SpawnParticlesInfo D_80331A00 = {
        /* behParam:        */ 0,
        /* count:           */ 6,
        /* model:           */ MODEL_WHITE_PARTICLE,
        /* offsetY:         */ 0,
        /* forwardVelBase:  */ 5,
        /* forwardVelRange: */ 5,
        /* velYBase:        */ 10,
        /* velYRange:       */ 10,
        /* gravity:         */ -3,
        /* dragStrength:    */ 0,
        /* sizeBase:        */ 3.0f,
        /* sizeRange:       */ 5.0f,
    };

    D_80331A00.count = count;
    D_80331A00.offsetY = offsetY;
    D_80331A00.forwardVelBase = forwardVelBase;
    D_80331A00.velYBase = velYBase;
    D_80331A00.sizeBase = sizeBase;

    cur_obj_spawn_particles(&D_80331A00);
}

/**
 * Mr. Blizzard initialization function.
 */
void bhv_mr_blizzard_init(void) {
    if (o->oBehParams2ndByte == MR_BLIZZARD_STYPE_JUMPING) {
        // Jumping Mr. Blizzard.
        o->oAction = MR_BLIZZARD_ACT_JUMP;
        o->oMrBlizzardGraphYOffset = 24.0f;
        o->oMrBlizzardTargetMoveYaw = o->oMoveAngleYaw;
    } else {
        // Cap wearing Mr. Blizzard from SL.
        if ((o->oBehParams2ndByte != MR_BLIZZARD_STYPE_NO_CAP)
            && (save_file_get_flags() & SAVE_FLAG_CAP_ON_MR_BLIZZARD)) {
            o->oAnimState = 1;
        }

        // Mr. Blizzard starts under the floor holding nothing.
        o->oMrBlizzardGraphYOffset = -200.0f;
        o->oMrBlizzardHeldObj = NULL;
    }
}

/**
 * Handler for spawning Mr. Blizzard's snowball.
 */
static void mr_blizzard_act_spawn_snowball(void) {
    // If Mr. Blizzard is not holding a snowball, and the animation reaches 5 frames
    // spawn the Mr. Blizzard snowball.
    if (o->oMrBlizzardHeldObj == NULL && cur_obj_init_anim_check_frame(0, 5)) {
        o->oMrBlizzardHeldObj =
            spawn_object_relative(0, -70, (s32)(o->oMrBlizzardGraphYOffset + 153.0f), 0, o,
                                  MODEL_WHITE_PARTICLE, bhvMrBlizzardSnowball);
    } else if (cur_obj_check_anim_frame(10)) {
        o->prevObj = o->oMrBlizzardHeldObj;
    } else if (cur_obj_check_if_near_animation_end()) {
        // If Mr. Blizzard's graphical position is below the ground, move to hide and unhide action.
        // Otherwise, move to rotate action.
        if (o->oMrBlizzardGraphYOffset < 0.0f) {
            o->oAction = MR_BLIZZARD_ACT_HIDE_UNHIDE;
        } else {
            o->oAction = MR_BLIZZARD_ACT_ROTATE;
        }
    }
}

/**
 * Handler for Mario entering or exiting Mr. Blizzard's range.
 */
static void mr_blizzard_act_hide_unhide(void) {
    if (o->oDistanceToMario < 1000.0f) {
        // If Mario is in range, move to rising action, make Mr. Blizzard visible,
        // make Mr. Blizzard tangible, and initialize GraphYVel.
        cur_obj_play_sound_2(SOUND_OBJ_SNOW_SAND2);
        o->oAction = MR_BLIZZARD_ACT_RISE_FROM_GROUND;
        o->oMoveAngleYaw = o->oAngleToMario;
        o->oMrBlizzardGraphYVel = 42.0f;

        mr_blizzard_spawn_white_particles(8, -10, 15, 20, 10);
        cur_obj_unhide();
        cur_obj_become_tangible();
    } else {
        // If Mario is not in range, make Mr. Blizzard invisible.
        cur_obj_hide();
    }
}

/**
 * Handler for Mr. Blizzard popping up out of the ground.
 */
static void mr_blizzard_act_rise_from_ground(void) {
    // If the timer is not 0, decrement by 1 until it reaches 0.
    if (o->oMrBlizzardTimer != 0) {
        o->oMrBlizzardTimer--;
    } else if ((o->oMrBlizzardGraphYOffset += o->oMrBlizzardGraphYVel) > 24.0f) {
        // Increments GraphYOffset by GraphYVel until it is greater than 24,
        // moving Mr. Blizzard's graphical position upward each frame.
        // Then, Mr. Blizzard's Y-position is increased by the value of
        // GraphYOffset minus 24, GraphYOffset is
        // set to 24, VelY is set to GraphYVel and action is moved to rotate.
        o->oPosY += o->oMrBlizzardGraphYOffset - 24.0f;
        o->oMrBlizzardGraphYOffset = 24.0f;

        mr_blizzard_spawn_white_particles(8, -20, 20, 15, 10);

        o->oAction = MR_BLIZZARD_ACT_ROTATE;
        o->oVelY = o->oMrBlizzardGraphYVel;
    } else if ((o->oMrBlizzardGraphYVel -= 10.0f) < 0.0f) {
        // Decrement GraphYOffset until it is less than 0.
        // When it is less than 0, set it to 47 and set timer to 5.
        o->oMrBlizzardGraphYVel = 47.0f;
        o->oMrBlizzardTimer = 5;
    }
}

/**
 * Handler for Mr. Blizzard's rotation.
 */
static void mr_blizzard_act_rotate(void) {
    // While Mr. Blizzard is on the ground, rotate toward Mario at
    // 8.4375 degrees/frame.
    if (o->oMoveFlags & OBJ_MOVE_MASK_ON_GROUND) {
        s16 angleDiff;
        cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x600);

        // Modify the ChangeInDizziness based on Mario's angle to Mr. Blizzard.
        angleDiff = o->oAngleToMario - o->oMoveAngleYaw;
        if (angleDiff != 0) {
            if (angleDiff < 0) {
                o->oMrBlizzardChangeInDizziness -= 8.0f;
            } else {
                o->oMrBlizzardChangeInDizziness += 8.0f;
            }

            // Incremement Dizziness by value of ChangeInDizziness
            o->oMrBlizzardDizziness += o->oMrBlizzardChangeInDizziness;
        } else if (o->oMrBlizzardDizziness != 0.0f) {
            f32 prevDizziness = o->oMrBlizzardDizziness;
            // Slowly move Dizziness back to 0 by making ChangeInDizziness positive if Dizziness
            // is negative, and making ChangeInDizziness negative if Dizziness is positive.
            if (o->oMrBlizzardDizziness < 0.0f) {
                approach_f32_ptr(&o->oMrBlizzardChangeInDizziness, 1000.0f, 80.0f);
            } else {
                approach_f32_ptr(&o->oMrBlizzardChangeInDizziness, -1000.0f, 80.0f);
            }

            o->oMrBlizzardDizziness += o->oMrBlizzardChangeInDizziness;
            // If prevDizziness has a different sign than Dizziness,
            // set Dizziness and ChangeInDizziness to 0.
            if (prevDizziness * o->oMrBlizzardDizziness < 0.0f) {
                o->oMrBlizzardDizziness = o->oMrBlizzardChangeInDizziness = 0.0f;
            }
        }
        // If Dizziness is not 0 and Mr. Blizzard's FaceRollAngle has a magnitude greater than
        // 67.5 degrees move to death action, delete the snowball, and make Mr. Blizzard intangible.
        if (o->oMrBlizzardDizziness != 0.0f) {
            if (absi(o->oFaceAngleRoll) > 0x3000) {
                o->oAction = MR_BLIZZARD_ACT_DEATH;
                o->prevObj = o->oMrBlizzardHeldObj = NULL;
                cur_obj_become_intangible();
            }
        }
        // If Mario gets too far away, move to burrow action and delete the snowball.
        else if (o->oDistanceToMario > 1500.0f) {
            o->oAction = MR_BLIZZARD_ACT_BURROW;
            o->oMrBlizzardChangeInDizziness = 300.0f;
            o->prevObj = o->oMrBlizzardHeldObj = NULL;
        }
        // After 60 frames, if Mario is within 11.25 degrees of Mr. Blizzard, throw snowball action.
        else if (o->oTimer > 60 && abs_angle_diff(o->oAngleToMario, o->oMoveAngleYaw) < 0x800) {
            o->oAction = MR_BLIZZARD_ACT_THROW_SNOWBALL;
        }
    }
}

/**
 * Handler for Mr. Blizzard's death.
 */
static void mr_blizzard_act_death(void) {
    if (clamp_f32(&o->oMrBlizzardDizziness, -0x4000, 0x4000)) {
        if (o->oMrBlizzardChangeInDizziness != 0.0f) {
            cur_obj_play_sound_2(SOUND_OBJ_SNOW_SAND1);
            // If Mr. Blizzard is wearing Mario's cap, clear
            // the save flag and spawn Mario's cap.
            if (o->oAnimState != 0) {
                struct Object *cap;
                save_file_clear_flags(SAVE_FLAG_CAP_ON_MR_BLIZZARD);

                cap = spawn_object_relative(0, 5, 105, 0, o, MODEL_MARIOS_CAP, bhvNormalCap);
                if (cap != NULL) {
                    cap->oMoveAngleYaw = o->oFaceAngleYaw + (o->oFaceAngleRoll < 0 ? 0x4000 : -0x4000);
                    cap->oForwardVel = 10.0f;
                }

                // Mr. Blizzard no longer spawns with Mario's cap on.
                o->oAnimState = 0;
            }

            o->oMrBlizzardChangeInDizziness = 0.0f;
        }
    } else {
        if (o->oMrBlizzardDizziness < 0) {
            o->oMrBlizzardChangeInDizziness -= 40.0f;
        } else {
            o->oMrBlizzardChangeInDizziness += 40.0f;
        }

        o->oMrBlizzardDizziness += o->oMrBlizzardChangeInDizziness;
    }

    // After 30 frames, play the defeat sound once and scale Mr. Blizzard down to 0
    // at .03 units per frame. Spawn coins and set the coins to not respawn.
    if (o->oTimer >= 30) {
        if (o->oTimer == 30) {
            cur_obj_play_sound_2(SOUND_OBJ_ENEMY_DEFEAT_SHRINK);
        }

        if (o->oMrBlizzardScale != 0.0f) {
            if ((o->oMrBlizzardScale -= 0.03f) <= 0.0f) {
                o->oMrBlizzardScale = 0.0f;
                if (!(o->oBehParams & 0x0000FF00)) {
                    obj_spawn_loot_yellow_coins(o, o->oNumLootCoins, 20.0f);
                    set_object_respawn_info_bits(o, 1);
                }
            }
        }
        // Reset Mr. Blizzard if Mario leaves its radius.
        else if (o->oDistanceToMario > 1000.0f) {
            cur_obj_init_animation_with_sound(1);

            o->oAction = MR_BLIZZARD_ACT_SPAWN_SNOWBALL;
            o->oMrBlizzardScale = 1.0f;
            o->oMrBlizzardGraphYOffset = -200.0f;
            o->oFaceAngleRoll = 0;
            o->oMrBlizzardDizziness = o->oMrBlizzardChangeInDizziness = 0.0f;
        }
    }
}

/**
 * Handler for snowball throw.
 */
static void mr_blizzard_act_throw_snowball(void) {
    // Play a sound and set HeldObj to NULL. Then set action to 0.
    if (cur_obj_init_anim_check_frame(1, 7)) {
        cur_obj_play_sound_2(SOUND_OBJ2_SCUTTLEBUG_ALERT);
        o->prevObj = o->oMrBlizzardHeldObj = NULL;
    } else if (cur_obj_check_if_near_animation_end()) {
        o->oAction = MR_BLIZZARD_ACT_SPAWN_SNOWBALL;
    }
}

/**
 * Mr. Blizzard's going back into the ground function.
 */
static void mr_blizzard_act_burrow(void) {
    // Reset Dizziness by increasing ChangeInDizziness if
    // dizziness is negative and decreasing it if Dizziness
    o->oMrBlizzardDizziness += o->oMrBlizzardChangeInDizziness;

    if (o->oMrBlizzardDizziness < 0.0f) {
        o->oMrBlizzardChangeInDizziness += 150.0f;
    } else {
        o->oMrBlizzardChangeInDizziness -= 150.0f;
    }
    // Put Mr. Blizzard's graphical position back below ground
    // then move to action 0.
    if (approach_f32_ptr(&o->oMrBlizzardGraphYOffset, -200.0f, 4.0f)) {
        o->oAction = MR_BLIZZARD_ACT_SPAWN_SNOWBALL;
        cur_obj_init_animation_with_sound(1);
    }
}

/**
 * Jumping Mr. Blizzard handler function.
 */
static void mr_blizzard_act_jump(void) {
    if (o->oMrBlizzardTimer != 0) {
        cur_obj_rotate_yaw_toward(o->oMrBlizzardTargetMoveYaw, 3400);

        if (--o->oMrBlizzardTimer == 0) {
            cur_obj_play_sound_2(SOUND_OBJ_MR_BLIZZARD_ALERT);

            // If Mr. Blizzard is more than 700 units from its home, change its target yaw
            // by 180 degrees, jump in the air, set distance from home to 0.
            if (o->oMrBlizzardDistFromHome > 700) {
                o->oMrBlizzardTargetMoveYaw += 0x8000;
                o->oVelY = 25.0f;
                o->oMrBlizzardTimer = 30;
                o->oMrBlizzardDistFromHome = 0;
            }
            // Jump forward.
            else {
                o->oForwardVel = 10.0f;
                o->oVelY = 50.0f;
                o->oMoveFlags = 0;
            }
        }
    } else if (o->oMoveFlags & OBJ_MOVE_MASK_ON_GROUND) {
        // When Mr. Blizzard lands, play the landing sound, stop Mr. Blizzard, and
        // set its timer to 15. If Mr. Blizzard's DistFromHome is not 0,
        // set DistFromHome to its current distance from its home.
        // Otherwise, set DistFromHome to 700.
        cur_obj_play_sound_2(SOUND_OBJ_SNOW_SAND1);
        if (o->oMrBlizzardDistFromHome != 0) {
            o->oMrBlizzardDistFromHome = (s32) cur_obj_lateral_dist_to_home();
        } else {
            o->oMrBlizzardDistFromHome = 700;
        }

        o->oForwardVel = 0.0f;
        o->oMrBlizzardTimer = 15;
    }
}

/**
 * Mr. Blizzard update function.
 */
void bhv_mr_blizzard_update(void) {
    cur_obj_update_floor_and_walls();

    // Behavior loop
    switch (o->oAction) {
        case MR_BLIZZARD_ACT_SPAWN_SNOWBALL:
            mr_blizzard_act_spawn_snowball();
            break;
        case MR_BLIZZARD_ACT_HIDE_UNHIDE:
            mr_blizzard_act_hide_unhide();
            break;
        case MR_BLIZZARD_ACT_RISE_FROM_GROUND:
            mr_blizzard_act_rise_from_ground();
            break;
        case MR_BLIZZARD_ACT_ROTATE:
            mr_blizzard_act_rotate();
            break;
        case MR_BLIZZARD_ACT_THROW_SNOWBALL:
            mr_blizzard_act_throw_snowball();
            break;
        case MR_BLIZZARD_ACT_BURROW:
            mr_blizzard_act_burrow();
            break;
        case MR_BLIZZARD_ACT_DEATH:
            mr_blizzard_act_death();
            break;
        case MR_BLIZZARD_ACT_JUMP:
            mr_blizzard_act_jump();
            break;
    }

    // Set roll angle equal to dizziness, making Mr. Blizzard
    // slowly fall over.
    o->oFaceAngleRoll = o->oMrBlizzardDizziness;
    // Mr. Blizzard's graphical position changes by changing the Y offset.
    o->oGraphYOffset = o->oMrBlizzardGraphYOffset + absf(20.0f * sins(o->oFaceAngleRoll))
                       - 40.0f * (1.0f - o->oMrBlizzardScale);

    cur_obj_scale(o->oMrBlizzardScale);
    cur_obj_move_standard(78);
    obj_check_attacks(&sMrBlizzardHitbox, o->oAction);
}

/**
 * Snowball initial takeoff position handler.
 */
static void mr_blizzard_snowball_act_0(void) {
    cur_obj_move_using_fvel_and_gravity();
    if (o->parentObj->prevObj == o) {
        o->oAction = 1;
        o->oParentRelativePosX = 190.0f;
        o->oParentRelativePosY = o->oParentRelativePosZ = -38.0f;
    }
}

/**
 * Snowball launching action.
 */
static void mr_blizzard_snowball_act_1(void) {
    if (o->parentObj->prevObj == NULL) {
        if (o->parentObj->oAction == MR_BLIZZARD_ACT_THROW_SNOWBALL) {
            f32 marioDist = o->oDistanceToMario;
            if (marioDist > 800.0f) {
                marioDist = 800.0f;
            }

            // Launch the snowball relative to Mario's distance from the snowball.
            o->oMoveAngleYaw = (s32)(o->parentObj->oMoveAngleYaw + 4000 - marioDist * 4.0f);
            o->oForwardVel = 40.0f;
            o->oVelY = -20.0f + marioDist * 0.075f;
        }

        o->oAction = 2;
        o->oMoveFlags = 0;
    }
}

// Snowball hitbox.
struct ObjectHitbox sMrBlizzardSnowballHitbox = {
    /* interactType:      */ INTERACT_MR_BLIZZARD,
    /* downOffset:        */ 12,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 99,
    /* numLootCoins:      */ 0,
    /* radius:            */ 30,
    /* height:            */ 30,
    /* hurtboxRadius:     */ 25,
    /* hurtboxHeight:     */ 25,
};

/**
 * Snowball collision function.
 */
static void mr_blizzard_snowball_act_2(void) {
    // Set snowball to interact with walls, floors, and Mario.
    cur_obj_update_floor_and_walls();
    obj_check_attacks(&sMrBlizzardSnowballHitbox, -1);

    // If snowball collides with the ground, delete snowball.
    if (o->oAction == -1 || o->oMoveFlags & (OBJ_MOVE_MASK_ON_GROUND | OBJ_MOVE_ENTERED_WATER)) {
        mr_blizzard_spawn_white_particles(6, 0, 5, 10, 3);
        create_sound_spawner(SOUND_GENERAL_MOVING_IN_SAND);
        obj_mark_for_deletion(o);
    }

    cur_obj_move_standard(78);
}

/**
 * Snowball behavior loop.
 */
void bhv_mr_blizzard_snowball(void) {
    switch (o->oAction) {
        case 0:
            mr_blizzard_snowball_act_0();
            break;
        case 1:
            mr_blizzard_snowball_act_1();
            break;
        case 2:
            mr_blizzard_snowball_act_2();
            break;
    }
}
