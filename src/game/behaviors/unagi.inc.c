// unagi.inc.c

struct ObjectHitbox sUnagiHitbox = {
    /* interactType:      */ INTERACT_CLAM_OR_BUBBA,
    /* downOffset:        */ 50,
    /* damageOrCoinValue: */ 3,
    /* health:            */ 99,
    /* numLootCoins:      */ 0,
    /* radius:            */ 150,
    /* height:            */ 150,
    /* hurtboxRadius:     */ 150,
    /* hurtboxHeight:     */ 150,
};

void bhv_unagi_init(void) {
    if (o->oBehParams2ndByte != 1) {
        o->oPathedStartWaypoint = segmented_to_virtual(jrb_seg7_trajectory_unagi_1);
        if (o->oBehParams2ndByte == 0) {
            o->oFaceAnglePitch = -7600;
        } else {
            o->oAction = 1;
        }
    } else {
        o->oPathedStartWaypoint = segmented_to_virtual(jrb_seg7_trajectory_unagi_2);
        o->oAction = 3;
        o->oAnimState = 1;

        o->oUnagiUnk1B0 = o->oMoveAngleYaw;
    }

    o->oPathedPrevWaypoint = o->oPathedStartWaypoint;
}

void unagi_act_0(void) {
    if (o->oDistanceToMario > 4500.0f && o->oSubAction != 0) {
        o->oAction = 1;
        o->oPosX = o->oPathedStartWaypoint->pos[0];
        o->oPosY = o->oPathedStartWaypoint->pos[1];
        o->oPosZ = o->oPathedStartWaypoint->pos[2];
    } else if (o->oUnagiUnk1AC < 700.0f) {
        o->oSubAction = 1;
    }
}

void unagi_act_1_4(s32 arg0) {
    if (o->oSoundStateID == 3) {
        if (cur_obj_check_anim_frame(30)) {
            o->oForwardVel = 40.0f;
        }
    } else {
        if (cur_obj_check_if_at_animation_end()) {
            if (o->oAction != arg0 && (o->oPathedPrevWaypointFlags & 0xFF) >= 7) {
                cur_obj_init_animation_with_sound(3);
            } else {
                cur_obj_init_animation_with_sound(2);
            }
        }
    }

    if (cur_obj_check_anim_frame(6)) {
        cur_obj_play_sound_2(SOUND_GENERAL_MOVING_WATER);
    }

    if (cur_obj_follow_path(0) == -1) {
        o->oAction = arg0;
    }

    o->oMoveAnglePitch = o->oFaceAnglePitch =
        approach_s16_symmetric(o->oMoveAnglePitch, o->oPathedTargetPitch, 50);

    cur_obj_rotate_yaw_toward(o->oPathedTargetYaw, 120);
    obj_roll_to_match_yaw_turn(o->oPathedTargetYaw, 0x2000, 100);

    obj_forward_vel_approach(10.0f, 0.2f);
    cur_obj_set_pos_via_transform();
}

void unagi_act_2(void) {
    o->oPathedPrevWaypoint = o->oPathedStartWaypoint;
    o->oPathedPrevWaypointFlags = 0;

    cur_obj_set_pos_to_home();

    o->oMoveAnglePitch = o->oFaceAnglePitch = 0;
    o->oMoveAngleYaw = o->oFaceAngleYaw = o->oUnagiUnk1B0;
    o->oFaceAngleRoll = 0;

    o->oForwardVel = o->oVelX = o->oVelZ = o->oUnagiUnkF8 = 0.0f;

    o->oUnagiUnkF4 = -800.0f;

    o->oAction = 3;
}

void unagi_act_3(void) {
    if (o->oUnagiUnkF4 < 0.0f) {
        cur_obj_init_animation_with_sound(6);

        if ((o->oUnagiUnkF4 += 10.0f) > 0.0f) {
            o->oUnagiUnkF4 = 0.0f;
        }
    } else {
        if (o->oUnagiUnkF4 == 0.0f) {
            cur_obj_init_animation_with_sound(6);

            if (o->oTimer > 60 && o->oUnagiUnk1AC < 1000.0f) {
                cur_obj_play_sound_2(SOUND_OBJ_EEL_2);
                o->oUnagiUnkF8 = o->oUnagiUnk110 = 30.0f;
            } else {
                o->oUnagiUnk110 = 0.0f;
            }
        } else if (o->oUnagiUnk110 > 0.0f) {
            if (cur_obj_init_anim_and_check_if_end(5)) {
                o->oUnagiUnk110 = 0.0f;
            }
        } else if (o->oUnagiUnk110 == 0.0f) {
            cur_obj_init_animation_with_sound(0);
            if (cur_obj_check_if_at_animation_end()) {
                if (o->oUnagiUnk1AC < 1000.0f) {
                    o->oAction = 4;
                    o->oForwardVel = o->oUnagiUnkF8;
                    cur_obj_init_animation_with_sound(1);
                } else {
                    o->oUnagiUnk110 = -50.0f;
                    cur_obj_init_animation_with_sound(4);
                }
            }
        }

        approach_f32_ptr(&o->oUnagiUnkF8, o->oUnagiUnk110, 4.0f);

        if ((o->oUnagiUnkF4 += o->oUnagiUnkF8) < 0.0f) {
            o->oUnagiUnkF4 = o->oUnagiUnkF8 = 0.0f;
            o->oTimer = 0;
        }
    }

    o->oPosX = o->oHomeX + o->oUnagiUnkF4 * sins(o->oMoveAngleYaw);
    o->oPosZ = o->oHomeZ + o->oUnagiUnkF4 * coss(o->oMoveAngleYaw);
}

void bhv_unagi_loop(void) {
    s32 val04;

    if (o->oUnagiUnk1B2 == 0) {
        o->oUnagiUnk1AC = 99999.0f;
        if (o->oDistanceToMario < 3000.0f) {
            for (val04 = -4; val04 < 4; val04++) {
                spawn_object_relative(val04, 0, 0, 0, o, MODEL_NONE, bhvUnagiSubobject);
            }
            o->oUnagiUnk1B2 = 1;
        }
    } else if (o->oDistanceToMario > 4000.0f) {
        o->oUnagiUnk1B2 = 0;
    }

    switch (o->oAction) {
        case 0:
            unagi_act_0();
            break;
        case 1:
            unagi_act_1_4(o->oAction);
            break;
        case 2:
            unagi_act_2();
        case 3:
            unagi_act_3();
            break;
        case 4:
            unagi_act_1_4(2);
            break;
    }
}

void bhv_unagi_subobject_loop(void) {
    f32 val04;

    if (o->parentObj->oUnagiUnk1B2 == 0) {
        obj_mark_for_deletion(o);
    } else {
        val04 = 300.0f * o->oBehParams2ndByte;

        o->oPosY = o->parentObj->oPosY - val04 * sins(o->parentObj->oFaceAnglePitch) * 1.13f;

        val04 = coss(o->parentObj->oFaceAnglePitch / 2) * val04;

        o->oPosX = o->parentObj->oPosX + val04 * sins(o->parentObj->oFaceAngleYaw);
        o->oPosZ = o->parentObj->oPosZ + val04 * coss(o->parentObj->oFaceAngleYaw);

        if (o->oBehParams2ndByte == -4) {
            if (o->parentObj->oAnimState != 0 && o->oDistanceToMario < 150.0f) {
                o->oBehParams = o->parentObj->oBehParams;
                spawn_default_star(6833.0f, -3654.0f, 2230.0f);
                o->parentObj->oAnimState = 0;
            }
        } else {
            obj_check_attacks(&sUnagiHitbox, o->oAction);
            if (o->oBehParams2ndByte == 3) {
                o->parentObj->oUnagiUnk1AC = o->oDistanceToMario;
            }
        }
    }
}
