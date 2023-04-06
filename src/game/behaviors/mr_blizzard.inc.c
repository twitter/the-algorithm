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

void mr_blizzard_spawn_white_particles(s8 count, s8 offsetY, s8 forwardVelBase, s8 velYBase, s8 sizeBase) {
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

void bhv_mr_blizzard_init(void) {
    if (o->oBehParams2ndByte == 1) {
        o->oAction = 7;
        o->oMrBlizzardUnk10C = 24.0f;
        o->oMrBlizzardUnk1AC = o->oMoveAngleYaw;
    } else {
        if (o->oBehParams2ndByte != 0) {
            if (save_file_get_flags() & SAVE_FLAG_CAP_ON_MR_BLIZZARD) {
                o->oAnimState = 1;
            }
        }

        o->oMrBlizzardUnk10C = -200.0f;
        o->oMrBlizzardUnkF8 = NULL;
    }
}

static void mr_blizzard_act_0(void) {
    if (o->oMrBlizzardUnkF8 == NULL && cur_obj_init_anim_check_frame(0, 5)) {
        o->oMrBlizzardUnkF8 = spawn_object_relative(0, -70, (s32)(o->oMrBlizzardUnk10C + 153.0f), 0, o,
                                                    MODEL_WHITE_PARTICLE, bhvMrBlizzardSnowball);
    } else if (cur_obj_check_anim_frame(10)) {
        o->prevObj = o->oMrBlizzardUnkF8;
    } else if (cur_obj_check_if_near_animation_end()) {
        if (o->oMrBlizzardUnk10C < 0.0f) {
            o->oAction = 1;
        } else {
            o->oAction = 3;
        }
    }
}

static void mr_blizzard_act_1(void) {
    if (o->oDistanceToMario < 1000.0f) {
        cur_obj_play_sound_2(SOUND_OBJ_SNOW_SAND2);
        o->oAction = 2;
        o->oMoveAngleYaw = o->oAngleToMario;
        o->oMrBlizzardUnkFC = 42.0f;

        mr_blizzard_spawn_white_particles(8, -10, 15, 20, 10);
        cur_obj_unhide();
        cur_obj_become_tangible();
    } else {
        cur_obj_hide();
    }
}

static void mr_blizzard_act_2(void) {
    if (o->oMrBlizzardUnk100 != 0) {
        o->oMrBlizzardUnk100 -= 1;
    } else if ((o->oMrBlizzardUnk10C += o->oMrBlizzardUnkFC) > 24.0f) {
        o->oPosY += o->oMrBlizzardUnk10C - 24.0f;
        o->oMrBlizzardUnk10C = 24.0f;

        mr_blizzard_spawn_white_particles(8, -20, 20, 15, 10);

        o->oAction = 3;
        o->oVelY = o->oMrBlizzardUnkFC;
    } else if ((o->oMrBlizzardUnkFC -= 10.0f) < 0.0f) {
        o->oMrBlizzardUnkFC = 47.0f;
        o->oMrBlizzardUnk100 = 5;
    }
}

static void mr_blizzard_act_3(void) {
    s16 val06;
    f32 val00;

    if (o->oMoveFlags & 0x00000003) {
        cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x600);

        val06 = o->oAngleToMario - o->oMoveAngleYaw;
        if (val06 != 0) {
            if (val06 < 0) {
                o->oMrBlizzardUnk108 -= 8.0f;
            } else {
                o->oMrBlizzardUnk108 += 8.0f;
            }

            o->oMrBlizzardUnk104 += o->oMrBlizzardUnk108;
        } else if (o->oMrBlizzardUnk104 != 0.0f) {
            val00 = o->oMrBlizzardUnk104;
            if (o->oMrBlizzardUnk104 < 0.0f) {
                approach_f32_ptr(&o->oMrBlizzardUnk108, 1000.0f, 80.0f);
            } else {
                approach_f32_ptr(&o->oMrBlizzardUnk108, -1000.0f, 80.0f);
            }

            o->oMrBlizzardUnk104 += o->oMrBlizzardUnk108;
            if (val00 * o->oMrBlizzardUnk104 < 0.0f) {
                o->oMrBlizzardUnk104 = o->oMrBlizzardUnk108 = 0.0f;
            }
        }

        if (o->oMrBlizzardUnk104 != 0.0f) {
            if (absi(o->oFaceAngleRoll) > 0x3000) {
                o->oAction = 6;
                o->prevObj = o->oMrBlizzardUnkF8 = NULL;
                cur_obj_become_intangible();
            }
        } else if (o->oDistanceToMario > 1500.0f) {
            o->oAction = 5;
            o->oMrBlizzardUnk108 = 300.0f;
            o->prevObj = o->oMrBlizzardUnkF8 = NULL;
        } else if (o->oTimer > 60 && abs_angle_diff(o->oAngleToMario, o->oMoveAngleYaw) < 0x800) {
            o->oAction = 4;
        }
    }
}

static void mr_blizzard_act_6(void) {
    struct Object *val04;

    if (clamp_f32(&o->oMrBlizzardUnk104, -0x4000, 0x4000)) {
        if (o->oMrBlizzardUnk108 != 0.0f) {
            cur_obj_play_sound_2(SOUND_OBJ_SNOW_SAND1);
            if (o->oAnimState) {
                save_file_clear_flags(SAVE_FLAG_CAP_ON_MR_BLIZZARD);

                val04 = spawn_object_relative(0, 5, 105, 0, o, MODEL_MARIOS_CAP, bhvNormalCap);
                if (val04 != NULL) {
                    val04->oMoveAngleYaw =
                        o->oFaceAngleYaw + (o->oFaceAngleRoll < 0 ? 0x4000 : -0x4000);
                    val04->oForwardVel = 10.0f;
                }

                o->oAnimState = 0;
            }

            o->oMrBlizzardUnk108 = 0.0f;
        }
    } else {
        if (o->oMrBlizzardUnk104 < 0) {
            o->oMrBlizzardUnk108 -= 40.0f;
        } else {
            o->oMrBlizzardUnk108 += 40.0f;
        }

        o->oMrBlizzardUnk104 += o->oMrBlizzardUnk108;
    }

    if (o->oTimer >= 30) {
        if (o->oTimer == 30) {
            cur_obj_play_sound_2(SOUND_OBJ_ENEMY_DEFEAT_SHRINK);
        }

        if (o->oMrBlizzardUnkF4 != 0.0f) {
            if ((o->oMrBlizzardUnkF4 -= 0.03f) <= 0.0f) {
                o->oMrBlizzardUnkF4 = 0.0f;
                if (!(o->oBehParams & 0x0000FF00)) {
                    obj_spawn_loot_yellow_coins(o, o->oNumLootCoins, 20.0f);
                    set_object_respawn_info_bits(o, 1);
                }
            }
        } else if (o->oDistanceToMario > 1000.0f) {
            cur_obj_init_animation_with_sound(1);

            o->oAction = 0;
            o->oMrBlizzardUnkF4 = 1.0f;
            o->oMrBlizzardUnk10C = -200.0f;
            o->oFaceAngleRoll = 0;
            o->oMrBlizzardUnk104 = o->oMrBlizzardUnk108 = 0.0f;
        }
    }
}

static void mr_blizzard_act_4(void) {
    if (cur_obj_init_anim_check_frame(1, 7)) {
        cur_obj_play_sound_2(SOUND_OBJ2_SCUTTLEBUG_ALERT);
        o->prevObj = o->oMrBlizzardUnkF8 = NULL;
    } else if (cur_obj_check_if_near_animation_end()) {
        o->oAction = 0;
    }
}

static void mr_blizzard_act_5(void) {
    o->oMrBlizzardUnk104 += o->oMrBlizzardUnk108;

    if (o->oMrBlizzardUnk104 < 0.0f) {
        o->oMrBlizzardUnk108 += 150.0f;
    } else {
        o->oMrBlizzardUnk108 -= 150.0f;
    }

    if (approach_f32_ptr(&o->oMrBlizzardUnk10C, -200.0f, 4.0f)) {
        o->oAction = 0;
        cur_obj_init_animation_with_sound(1);
    }
}

static void mr_blizzard_act_7(void) {
    if (o->oMrBlizzardUnk100 != 0) {
        cur_obj_rotate_yaw_toward(o->oMrBlizzardUnk1AC, 3400);

        if (--o->oMrBlizzardUnk100 == 0) {
            cur_obj_play_sound_2(SOUND_OBJ_MR_BLIZZARD_ALERT);

            if (o->oMrBlizzardUnk110 > 700) {
                o->oMrBlizzardUnk1AC += 0x8000;
                o->oVelY = 25.0f;
                o->oMrBlizzardUnk100 = 30;
                o->oMrBlizzardUnk110 = 0;
            } else {
                o->oForwardVel = 10.0f;
                o->oVelY = 50.0f;
                o->oMoveFlags = 0;
            }
        }
    } else if (o->oMoveFlags & 0x00000003) {
        cur_obj_play_sound_2(SOUND_OBJ_SNOW_SAND1);
        if (o->oMrBlizzardUnk110 != 0) {
            o->oMrBlizzardUnk110 = (s32) cur_obj_lateral_dist_to_home();
        } else {
            o->oMrBlizzardUnk110 = 700;
        }

        o->oForwardVel = 0.0f;
        o->oMrBlizzardUnk100 = 15;
    }
}

void bhv_mr_blizzard_update(void) {
    cur_obj_update_floor_and_walls();

    switch (o->oAction) {
        case 0:
            mr_blizzard_act_0();
            break;
        case 1:
            mr_blizzard_act_1();
            break;
        case 2:
            mr_blizzard_act_2();
            break;
        case 3:
            mr_blizzard_act_3();
            break;
        case 4:
            mr_blizzard_act_4();
            break;
        case 5:
            mr_blizzard_act_5();
            break;
        case 6:
            mr_blizzard_act_6();
            break;
        case 7:
            mr_blizzard_act_7();
            break;
    }

    o->oFaceAngleRoll = o->oMrBlizzardUnk104;
    o->oGraphYOffset = o->oMrBlizzardUnk10C + absf(20.0f * sins(o->oFaceAngleRoll))
                       - 40.0f * (1.0f - o->oMrBlizzardUnkF4);

    cur_obj_scale(o->oMrBlizzardUnkF4);
    cur_obj_move_standard(78);
    obj_check_attacks(&sMrBlizzardHitbox, o->oAction);
}

static void mr_blizzard_snowball_act_0(void) {
    cur_obj_move_using_fvel_and_gravity();
    if (o->parentObj->prevObj == o) {
        o->oAction = 1;
        o->oParentRelativePosX = 190.0f;
        o->oParentRelativePosY = o->oParentRelativePosZ = -38.0f;
    }
}

static void mr_blizzard_snowball_act_1(void) {
    f32 val04;

    if (o->parentObj->prevObj == NULL) {
        if (o->parentObj->oAction == 4) {
            val04 = o->oDistanceToMario;
            if (val04 > 800.0f) {
                val04 = 800.0f;
            }

            o->oMoveAngleYaw = (s32)(o->parentObj->oMoveAngleYaw + 4000 - val04 * 4.0f);
            o->oForwardVel = 40.0f;
            o->oVelY = -20.0f + val04 * 0.075f;
        }

        o->oAction = 2;
        o->oMoveFlags = 0;
    }
}

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

static void mr_blizzard_snowball_act_2(void) {
    cur_obj_update_floor_and_walls();
    obj_check_attacks(&sMrBlizzardSnowballHitbox, -1);

    if (o->oAction == -1 || o->oMoveFlags & 0x0000000B) {
        mr_blizzard_spawn_white_particles(6, 0, 5, 10, 3);
        create_sound_spawner(SOUND_GENERAL_MOVING_IN_SAND);
        obj_mark_for_deletion(o);
    }

    cur_obj_move_standard(78);
}

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
