struct ObjectHitbox sEyerokHitbox = {
    /* interactType:      */ INTERACT_BOUNCE_TOP,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 0,
    /* health:            */ 4,
    /* numLootCoins:      */ 0,
    /* radius:            */ 150,
    /* height:            */ 100,
    /* hurtboxRadius:     */ 1,
    /* hurtboxHeight:     */ 1,
};

s8 D_80331BA4[] = { 0, 1, 3, 2, 1, 0 };

static s32 eyerok_check_mario_relative_z(s32 arg0) {
    if (gMarioObject->oPosZ - o->oHomeZ < arg0) {
        return TRUE;
    } else {
        return FALSE;
    }
}

static void eyerok_spawn_hand(s16 side, s32 model, const BehaviorScript *behavior) {
    struct Object *hand;

    hand = spawn_object_relative_with_scale(side, -500 * side, 0, 300, 1.5f, o, model, behavior);
    if (hand != NULL) {
        hand->oFaceAngleYaw -= 0x4000 * side;
    }
}

static void eyerok_boss_act_sleep(void) {
    if (o->oTimer == 0) {
        eyerok_spawn_hand(-1, MODEL_EYEROK_LEFT_HAND, bhvEyerokHand);
        eyerok_spawn_hand(1, MODEL_EYEROK_RIGHT_HAND, bhvEyerokHand);
    } else if (o->oDistanceToMario < 500.0f) {
        cur_obj_play_sound_2(SOUND_OBJ_EYEROK_EXPLODE);
        o->oAction = EYEROK_BOSS_ACT_WAKE_UP;
    }
}

static void eyerok_boss_act_wake_up(void) {
    if (o->oEyerokBossNumHands == 2) {
        if (o->oTimer > 5) {
            if (o->oSubAction == 0) {
                func_8031FFB4(SEQ_PLAYER_LEVEL, 60, 40);
                o->oSubAction += 1;
            }

            if (o->oEyerokBossUnk110 == 0.0f && mario_ready_to_speak() != 0) {
                o->oAction = EYEROK_BOSS_ACT_SHOW_INTRO_TEXT;
            } else if (o->oTimer > 150) {
                if (approach_f32_ptr(&o->oEyerokBossUnk110, 0.0f, 10.0f)) {
                    o->oTimer = 0;
                }
            } else if (o->oTimer > 90) {
                approach_f32_ptr(&o->oEyerokBossUnk110, 300.f, 10.0f);
            }
        }
    } else {
        o->oTimer = 0;
    }
}

static void eyerok_boss_act_show_intro_text(void) {
    if (cur_obj_update_dialog_with_cutscene(2, 0, CUTSCENE_DIALOG, DIALOG_117)) {
        o->oAction = EYEROK_BOSS_ACT_FIGHT;
    }
}

static void eyerok_boss_act_fight(void) {
    if (o->oEyerokBossNumHands == 0) {
        o->oAction = EYEROK_BOSS_ACT_DIE;
    } else if (o->oEyerokBossUnk1AC == 0 && o->oEyerokBossActiveHand == 0) {
        if (o->oEyerokBossUnk104 != 0) {
            if (approach_f32_ptr(&o->oEyerokBossUnk110, 1.0f, 0.02f)) {
                if (o->oEyerokBossUnk104 < 0) {
                    if (eyerok_check_mario_relative_z(400) == 0 && ++o->oEyerokBossUnk104 == 0) {
                        o->oEyerokBossUnk104 = 1;
                    }
                } else {
                    o->oEyerokBossUnk104 -= 1;
                }

                if (o->oEyerokBossUnk104 != 0 && o->oEyerokBossUnk104 != 1) {
                    o->oEyerokBossUnkFC += 1;
                    if ((o->oEyerokBossActiveHand = o->oEyerokBossUnkFC & 0x1) == 0) {
                        o->oEyerokBossActiveHand = -1;
                    }
                }
            }
        } else {
            o->oEyerokBossUnkFC += 1;

            if (eyerok_check_mario_relative_z(400)) {
                o->oEyerokBossUnk104 = -8;
                o->oEyerokBossUnk110 = 1.0f;
                o->oEyerokBossUnk108 = 0.0f;
            } else if (o->oEyerokBossNumHands == 2 && o->oEyerokBossUnkFC % 6 == 0) {
                o->oEyerokBossUnk104 = 8;
                o->oEyerokBossUnk110 = 0.0f;

                if ((o->oEyerokBossUnkFC = random_u16() & 0x1) != 0) {
                    o->oEyerokBossUnk108 = -1.0f;
                } else {
                    o->oEyerokBossUnk108 = 1.0f;
                }

                o->oEyerokBossUnk10C = gMarioObject->oPosZ;
                clamp_f32(&o->oEyerokBossUnk10C, o->oPosZ + 400.0f, o->oPosZ + 1600.0f);
            } else if ((o->oEyerokBossActiveHand = o->oEyerokBossUnkFC & 0x1) == 0) {
                o->oEyerokBossActiveHand = -1;
            }
        }
    }
}

static void eyerok_boss_act_die(void) {
    if (o->oTimer == 60) {
        if (cur_obj_update_dialog_with_cutscene(2, 0, CUTSCENE_DIALOG, DIALOG_118)) {
            spawn_default_star(0.0f, -900.0f, -3700.0f);
        } else {
            o->oTimer -= 1;
        }
    } else if (o->oTimer > 120) {
        stop_background_music(SEQUENCE_ARGS(4, SEQ_EVENT_BOSS));
        obj_mark_for_deletion(o);
    }
}

void bhv_eyerok_boss_loop(void) {
    switch (o->oAction) {
        case EYEROK_BOSS_ACT_SLEEP:
            eyerok_boss_act_sleep();
            break;
        case EYEROK_BOSS_ACT_WAKE_UP:
            eyerok_boss_act_wake_up();
            break;
        case EYEROK_BOSS_ACT_SHOW_INTRO_TEXT:
            eyerok_boss_act_show_intro_text();
            break;
        case EYEROK_BOSS_ACT_FIGHT:
            eyerok_boss_act_fight();
            break;
        case EYEROK_BOSS_ACT_DIE:
            eyerok_boss_act_die();
            break;
    }
}

static s32 eyerok_hand_check_attacked(void) {
    if (o->oEyerokReceivedAttack != 0 && abs_angle_diff(o->oAngleToMario, o->oFaceAngleYaw) < 0x3000) {
        cur_obj_play_sound_2(SOUND_OBJ2_EYEROK_SOUND_SHORT);

        if (--o->oHealth >= 2) {
            o->oAction = EYEROK_HAND_ACT_ATTACKED;
            o->oVelY = 30.0f;
        } else {
            o->parentObj->oEyerokBossNumHands -= 1;
            o->oAction = EYEROK_HAND_ACT_DIE;
            o->oVelY = 50.0f;
        }

        o->oForwardVel *= 0.2f;
        o->oMoveAngleYaw = o->oFaceAngleYaw + 0x8000;
        o->oMoveFlags = 0;
        o->oGravity = -4.0f;
        o->oAnimState = 3;

        return TRUE;
    } else {
        return FALSE;
    }
}

static void eyerok_hand_pound_ground(void) {
    cur_obj_play_sound_2(SOUND_OBJ_POUNDING_LOUD);
    set_camera_shake_from_point(SHAKE_POS_SMALL, o->oPosX, o->oPosY, o->oPosZ);
    spawn_mist_from_global();
}

static void eyerok_hand_act_sleep(void) {
    if (o->parentObj->oAction != EYEROK_BOSS_ACT_SLEEP
        && ++o->oEyerokHandWakeUpTimer > -3 * o->oBehParams2ndByte) {
        if (cur_obj_check_if_near_animation_end()) {
            o->parentObj->oEyerokBossNumHands += 1;
            o->oAction = EYEROK_HAND_ACT_IDLE;
            o->collisionData = segmented_to_virtual(&ssl_seg7_collision_07028274);
        } else {
            approach_f32_ptr(&o->oPosX, o->oHomeX, 15.0f);
            o->oPosY = o->oHomeY
                       + (200 * o->oBehParams2ndByte + 400)
                             * sins((s16)(absf(o->oPosX - o->oHomeX) / 724.0f * 0x8000));
            obj_face_yaw_approach(o->oMoveAngleYaw, 400);
        }
    } else {
        if (o->oBehParams2ndByte < 0) {
            o->collisionData = segmented_to_virtual(&ssl_seg7_collision_070284B0);
        } else {
            o->collisionData = segmented_to_virtual(&ssl_seg7_collision_07028370);
        }

        cur_obj_reverse_animation();
        o->oPosX = o->oHomeX + 724.0f * o->oBehParams2ndByte;
    }
}

static void eyerok_hand_act_idle(void) {
    cur_obj_init_animation_with_sound(2);

    if (o->parentObj->oAction == EYEROK_BOSS_ACT_FIGHT) {
        if (o->parentObj->oEyerokBossUnk104 != 0) {
            if (o->parentObj->oEyerokBossUnk104 != 1) {
                o->oAction = EYEROK_HAND_ACT_BEGIN_DOUBLE_POUND;
                o->oGravity = 0.0f;
            }
        } else if (o->parentObj->oEyerokBossUnk1AC == 0 && o->parentObj->oEyerokBossActiveHand != 0) {
            if (o->parentObj->oEyerokBossActiveHand == o->oBehParams2ndByte) {
                if (eyerok_check_mario_relative_z(400) != 0 || random_u16() % 2 != 0) {
                    o->oAction = EYEROK_HAND_ACT_TARGET_MARIO;
                    o->oMoveAngleYaw = o->oAngleToMario;
                    o->oGravity = 0.0f;
                } else {
                    o->oAction = EYEROK_HAND_ACT_FIST_PUSH;
                    if (o->parentObj->oPosX - gMarioObject->oPosX < 0.0f) {
                        o->oMoveAngleYaw = -0x800;
                    } else {
                        o->oMoveAngleYaw = 0x800;
                    }

                    o->oMoveAngleYaw += o->oAngleToMario;
                    o->oGravity = -4.0f;
                }
            } else {
                o->oAction = EYEROK_HAND_ACT_OPEN;
            }
        }
    } else {
        o->oPosY = o->oHomeY + o->parentObj->oEyerokBossUnk110;
    }
}

static void eyerok_hand_act_open(void) {
    s16 sp1E;

    o->parentObj->oEyerokBossUnk1AC = o->oBehParams2ndByte;

    if (cur_obj_init_anim_and_check_if_end(4)) {
        o->oAction = EYEROK_HAND_ACT_SHOW_EYE;
        o->oEyerokHandUnkFC = 2;
        o->oEyerokHandUnk100 = 60;

        o->collisionData = segmented_to_virtual(ssl_seg7_collision_070282F8);

        if (o->parentObj->oEyerokBossNumHands != 2) {
            sp1E = o->oAngleToMario;
            clamp_s16(&sp1E, -0x3000, 0x3000);
            o->oMoveAngleYaw = sp1E;
            o->oForwardVel = 50.0f;
            ;
        } else {
            o->oMoveAngleYaw = 0;
        }
    }
}

static void eyerok_hand_act_show_eye(void) {
    UNUSED s16 val06;

    cur_obj_init_animation_with_sound(5);
    cur_obj_play_sound_at_anim_range(0, 0, SOUND_OBJ_EYEROK_SHOW_EYE);

    if (!eyerok_hand_check_attacked()) {
        if (o->parentObj->oEyerokBossActiveHand == 0) {
            if (o->oAnimState < 3) {
                o->oAnimState += 1;
            } else if (cur_obj_check_if_near_animation_end()) {
                val06 = (s16)(o->oAngleToMario - o->oFaceAngleYaw) * o->oBehParams2ndByte;
                o->oAction = EYEROK_HAND_ACT_CLOSE;
            }
        } else {
            if (o->oEyerokHandUnk100--) {
                if (o->oEyerokHandUnkFC != 0) {
                    o->oEyerokHandUnkFC -= 1;
                }
                o->oAnimState = D_80331BA4[o->oEyerokHandUnkFC];
            } else {
                o->oEyerokHandUnkFC = 5;
                o->oEyerokHandUnk100 = random_linear_offset(20, 50);
            }

            if (o->parentObj->oEyerokBossNumHands != 2) {
                obj_face_yaw_approach(o->oMoveAngleYaw, 0x800);
                if (o->oTimer > 10
                    && (o->oPosZ - gMarioObject->oPosZ > 0.0f || (o->oMoveFlags & 0x00000400))) {
                    o->parentObj->oEyerokBossActiveHand = 0;
                    o->oForwardVel = 0.0f;
                }
            }
        }
    }
}

static void eyerok_hand_act_close(void) {
    if (cur_obj_init_anim_check_frame(7, 1)) {
        o->collisionData = segmented_to_virtual(ssl_seg7_collision_07028274);

        if (o->parentObj->oEyerokBossNumHands != 2) {
            o->oAction = EYEROK_HAND_ACT_RETREAT;
            o->parentObj->oEyerokBossActiveHand = o->oBehParams2ndByte;
        } else if (o->parentObj->oEyerokBossActiveHand == 0) {
            o->oAction = EYEROK_HAND_ACT_IDLE;
            o->parentObj->oEyerokBossUnk1AC = 0;
        }
    }
}

static void eyerok_hand_act_attacked(void) {
    if (cur_obj_init_anim_and_check_if_end(3)) {
        o->oAction = EYEROK_HAND_ACT_RECOVER;
        o->collisionData = segmented_to_virtual(ssl_seg7_collision_07028274);
    }

    if (o->oMoveFlags & 0x00000003) {
        o->oForwardVel = 0.0f;
    }
}

static void eyerok_hand_act_recover(void) {
    if (cur_obj_init_anim_and_check_if_end(0)) {
        o->oAction = EYEROK_HAND_ACT_BECOME_ACTIVE;
    }
}

static void eyerok_hand_act_become_active(void) {
    if (o->parentObj->oEyerokBossActiveHand == 0 || o->parentObj->oEyerokBossNumHands != 2) {
        o->oAction = EYEROK_HAND_ACT_RETREAT;
        o->parentObj->oEyerokBossActiveHand = o->oBehParams2ndByte;
    }
}

static void eyerok_hand_act_die(void) {
    if (cur_obj_init_anim_and_check_if_end(1)) {
        o->parentObj->oEyerokBossUnk1AC = 0;
        obj_explode_and_spawn_coins(150.0f, 1);
        create_sound_spawner(SOUND_OBJ2_EYEROK_SOUND_LONG);
    }

    if (o->oMoveFlags & 0x00000003) {
        cur_obj_play_sound_2(SOUND_OBJ_POUNDING_LOUD);
        o->oForwardVel = 0.0f;
    }
}

static void eyerok_hand_act_retreat(void) {
    f32 distToHome = cur_obj_lateral_dist_to_home();
    s16 angleToHome = cur_obj_angle_to_home();

    if ((distToHome -= 40.0f) < 0.0f) {
        distToHome = 0.0f;
    }

    o->oPosX = o->oHomeX - distToHome * sins(angleToHome);
    o->oPosZ = o->oHomeZ - distToHome * coss(angleToHome);

    obj_face_yaw_approach(0, 400);

    if (approach_f32_ptr(&o->oPosY, o->oHomeY, 20.0f) && distToHome == 0.0f && o->oFaceAngleYaw == 0) {
        o->oAction = EYEROK_HAND_ACT_IDLE;
        o->parentObj->oEyerokBossActiveHand -= o->oBehParams2ndByte;

        if (o->parentObj->oEyerokBossUnk1AC == o->oBehParams2ndByte) {
            o->parentObj->oEyerokBossUnk1AC = 0;
        }
    }
}

static void eyerok_hand_act_target_mario(void) {
    if (eyerok_check_mario_relative_z(400) != 0 || o->oPosZ - gMarioObject->oPosZ > 0.0f
        || o->oPosZ - o->parentObj->oPosZ > 1700.0f || absf(o->oPosX - o->parentObj->oPosX) > 900.0f
        || (o->oMoveFlags & 0x00000200)) {
        o->oForwardVel = 0.0f;
        if (approach_f32_ptr(&o->oPosY, o->oHomeY + 300.0f, 20.0f)) {
            o->oAction = EYEROK_HAND_ACT_SMASH;
        }
    } else {
        obj_forward_vel_approach(50.0f, 5.0f);
        approach_f32_ptr(&o->oPosY, o->oHomeY + 300.0f, 20.0f);
        cur_obj_rotate_yaw_toward(o->oAngleToMario, 4000);
    }
}

static void eyerok_hand_act_smash(void) {
    s16 sp1E;

    if (o->oTimer > 20) {
        if (o->oMoveFlags & 0x00000003) {
            if (o->oGravity < -4.0f) {
                eyerok_hand_pound_ground();
                o->oGravity = -4.0f;
            } else {
                sp1E = abs_angle_diff(o->oFaceAngleYaw, o->oAngleToMario);
                if (o->oDistanceToMario < 300.0f && sp1E > 0x2000 && sp1E < 0x6000) {
                    o->oAction = EYEROK_HAND_ACT_FIST_SWEEP;
                    if ((s16)(o->oFaceAngleYaw - o->oAngleToMario) < 0) {
                        o->oMoveAngleYaw = 0x4000;
                    } else {
                        o->oMoveAngleYaw = -0x4000;
                    }
                } else {
                    o->oAction = EYEROK_HAND_ACT_RETREAT;
                }
            }
        } else {
            o->oGravity = -20.0f;
        }
    }
}

static void eyerok_hand_act_fist_push(void) {
    if (o->oTimer > 5 && (o->oPosZ - gMarioObject->oPosZ > 0.0f || (o->oMoveFlags & 0x00000400))) {
        o->oAction = EYEROK_HAND_ACT_FIST_SWEEP;
        o->oForwardVel = 0.0f;

        if (o->oPosX - gMarioObject->oPosX < 0.0f) {
            o->oMoveAngleYaw = 0x4000;
        } else {
            o->oMoveAngleYaw = -0x4000;
        }
    } else {
        o->oForwardVel = 50.0f;
    }
}

static void eyerok_hand_act_fist_sweep(void) {
    if (o->oPosZ - o->parentObj->oPosZ < 1000.0f || (o->oMoveFlags & 0x400)) {
        o->oAction = EYEROK_HAND_ACT_RETREAT;
        o->oForwardVel = 0.0f;
    } else {
        obj_forward_vel_approach(5.0f, 0.02f);
        o->oForwardVel *= 1.08f;
        o->oTimer = 0;
    }
}

static void eyerok_hand_act_begin_double_pound(void) {
    f32 sp4;

    if (o->parentObj->oEyerokBossUnk104 < 0
        || o->parentObj->oEyerokBossActiveHand == o->oBehParams2ndByte) {
        o->oAction = EYEROK_HAND_ACT_DOUBLE_POUND;
        o->oMoveAngleYaw = (s32)(o->oFaceAngleYaw - 0x4000 * o->parentObj->oEyerokBossUnk108);
    } else {
        sp4 = o->parentObj->oPosX + 400.0f * o->parentObj->oEyerokBossUnk108
              - 180.0f * o->oBehParams2ndByte;

        o->oPosX = o->oHomeX + (sp4 - o->oHomeX) * o->parentObj->oEyerokBossUnk110;
        o->oPosY = o->oHomeY + 300.0f * o->parentObj->oEyerokBossUnk110;
        o->oPosZ =
            o->oHomeZ + (o->parentObj->oEyerokBossUnk10C - o->oHomeZ) * o->parentObj->oEyerokBossUnk110;
    }
}

static void eyerok_hand_act_double_pound(void) {
    if (o->parentObj->oEyerokBossNumHands != 2) {
        o->parentObj->oEyerokBossActiveHand = o->oBehParams2ndByte;
    }

    if (o->parentObj->oEyerokBossUnk104 == 1) {
        o->oAction = EYEROK_HAND_ACT_RETREAT;
        o->parentObj->oEyerokBossUnk1AC = o->oBehParams2ndByte;
    } else if (o->parentObj->oEyerokBossActiveHand == o->oBehParams2ndByte) {
        if (o->oMoveFlags & 0x00000003) {
            if (o->oGravity < -15.0f) {
                o->parentObj->oEyerokBossActiveHand = 0;
                eyerok_hand_pound_ground();
                o->oForwardVel = 0.0f;
                o->oGravity = -15.0f;
            } else {
                o->oForwardVel = 30.0f * absf(o->parentObj->oEyerokBossUnk108);
                o->oVelY = 100.0f;
                o->oMoveFlags = 0;
            }
        } else if (o->oVelY <= 0.0f) {
            o->oGravity = -20.0f;
        }
    }
}

void bhv_eyerok_hand_loop(void) {
    o->header.gfx.scale[0] = 1.5f;

    if (o->oAction == EYEROK_HAND_ACT_SLEEP) {
        eyerok_hand_act_sleep();
    } else {
        cur_obj_update_floor_and_walls();

        switch (o->oAction) {
            case EYEROK_HAND_ACT_IDLE:
                eyerok_hand_act_idle();
                break;
            case EYEROK_HAND_ACT_OPEN:
                eyerok_hand_act_open();
                break;
            case EYEROK_HAND_ACT_SHOW_EYE:
                eyerok_hand_act_show_eye();
                break;
            case EYEROK_HAND_ACT_CLOSE:
                eyerok_hand_act_close();
                break;
            case EYEROK_HAND_ACT_RETREAT:
                eyerok_hand_act_retreat();
                break;
            case EYEROK_HAND_ACT_TARGET_MARIO:
                eyerok_hand_act_target_mario();
                break;
            case EYEROK_HAND_ACT_SMASH:
                eyerok_hand_act_smash();
                break;
            case EYEROK_HAND_ACT_FIST_PUSH:
                eyerok_hand_act_fist_push();
                break;
            case EYEROK_HAND_ACT_FIST_SWEEP:
                eyerok_hand_act_fist_sweep();
                break;
            case EYEROK_HAND_ACT_BEGIN_DOUBLE_POUND:
                eyerok_hand_act_begin_double_pound();
                break;
            case EYEROK_HAND_ACT_DOUBLE_POUND:
                eyerok_hand_act_double_pound();
                break;
            case EYEROK_HAND_ACT_ATTACKED:
                eyerok_hand_act_attacked();
                break;
            case EYEROK_HAND_ACT_RECOVER:
                eyerok_hand_act_recover();
                break;
            case EYEROK_HAND_ACT_BECOME_ACTIVE:
                eyerok_hand_act_become_active();
                break;
            case EYEROK_HAND_ACT_DIE:
                eyerok_hand_act_die();
                break;
        }

        o->oEyerokReceivedAttack = obj_check_attacks(&sEyerokHitbox, o->oAction);
        cur_obj_move_standard(-78);
    }

    load_object_collision_model();
    o->header.gfx.scale[0] = 1.5f * o->oBehParams2ndByte;
}
