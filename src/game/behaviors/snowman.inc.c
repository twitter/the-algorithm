// snowman.inc.c

static struct ObjectHitbox sRollingSphereHitbox = {
    /* interactType:      */ INTERACT_DAMAGE,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 3,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 210,
    /* height:            */ 350,
    /* hurtboxRadius:     */ 0,
    /* hurtboxHeight:     */ 0,
};

void bhv_snowmans_bottom_init(void) {
    struct Object *snowmansHead;

    o->oHomeX = o->oPosX;
    o->oHomeY = o->oPosY;
    o->oHomeZ = o->oPosZ;

    o->oGravity = 10.0f;
    o->oFriction = 0.999f;
    o->oBuoyancy = 2.0f;

    o->oVelY = 0;
    o->oForwardVel = 0;
    o->oSnowmansBottomScale = 0.4f;

    snowmansHead = cur_obj_nearest_object_with_behavior(bhvSnowmansHead);
    if (snowmansHead != NULL) {
        o->parentObj = snowmansHead;
    }
    spawn_object_abs_with_rot(o, 0, MODEL_NONE, bhvSnowmansBodyCheckpoint, -402, 461, -2898, 0, 0, 0);
}

void set_rolling_sphere_hitbox(void) {
    obj_set_hitbox(o, &sRollingSphereHitbox);

    if (o->oInteractStatus & INT_STATUS_INTERACTED) {
        o->oInteractStatus = 0;
    }
}

void adjust_rolling_face_pitch(f32 f12) {
    o->oFaceAnglePitch += (s16)(o->oForwardVel * (100.0f / f12));
    o->oSnowmansBottomScale += o->oForwardVel * 0.0001;

    if (o->oSnowmansBottomScale > 1.0) {
        o->oSnowmansBottomScale = 1.0f;
    }
}

void snowmans_bottom_act_1(void) {
    UNUSED s16 collisionFlags;
    s32 followStatus;
#ifdef AVOID_UB
    followStatus = 0;
#endif

    o->oPathedStartWaypoint = segmented_to_virtual(&ccm_seg7_trajectory_snowman);
    collisionFlags = object_step_without_floor_orient();
    followStatus = cur_obj_follow_path(followStatus);
    o->oSnowmansBottomUnkF8 = o->oPathedTargetYaw;
    o->oMoveAngleYaw = approach_s16_symmetric(o->oMoveAngleYaw, o->oSnowmansBottomUnkF8, 0x400);

    if (o->oForwardVel > 70.0) {
        o->oForwardVel = 70.0f;
    }

    if (followStatus == PATH_REACHED_END) {
        UNUSED s16 sp1E = (u16) o->oAngleToMario - (u16) o->oMoveAngleYaw;
        if (obj_check_if_facing_toward_angle(o->oMoveAngleYaw, o->oAngleToMario, 0x2000) == TRUE
            && o->oSnowmansBottomUnk1AC == 1) {
            o->oSnowmansBottomUnkF8 = o->oAngleToMario;
        } else {
            o->oSnowmansBottomUnkF8 = o->oMoveAngleYaw;
        }
        o->oAction = 2;
    }
}

void snowmans_bottom_act_2(void) {
    UNUSED s16 collisionFlags = object_step_without_floor_orient();

    if (o->oForwardVel > 70.0) {
        o->oForwardVel = 70.0f;
    }

    o->oMoveAngleYaw = approach_s16_symmetric(o->oMoveAngleYaw, o->oSnowmansBottomUnkF8, 0x400);
    if (is_point_close_to_object(o, -4230.0f, -1344.0f, 1813.0f, 300)) {
        spawn_mist_particles_variable(0, 0, 70.0f);
        o->oMoveAngleYaw = atan2s(1813.0f - o->oPosZ, -4230.0f - o->oPosX);
        o->oVelY = 80.0f;
        o->oForwardVel = 15.0f;
        o->oAction = 3;

        o->parentObj->oAction = 2;
        o->parentObj->oVelY = 100.0f;
        cur_obj_play_sound_2(SOUND_OBJ_SNOWMAN_BOUNCE);
    }

    if (o->oTimer == 200) {
        create_respawner(MODEL_CCM_SNOWMAN_BASE, bhvSnowmansBottom, 3000);
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}

void snowmans_bottom_act_3(void) {
    s16 collisionFlags = object_step_without_floor_orient();

    if ((collisionFlags & OBJ_COL_FLAGS_LANDED) == OBJ_COL_FLAGS_LANDED) {
        o->oAction = 4;
        cur_obj_become_intangible();
    }

    if (collisionFlags & OBJ_COL_FLAG_GROUNDED) {
        spawn_mist_particles_variable(0, 0, 70.0f);
        o->oPosX = -4230.0f;
        o->oPosZ = 1813.0f;
        o->oForwardVel = 0.0f;
    }
}

void bhv_snowmans_bottom_loop(void) {
    switch (o->oAction) {
        case 0:
            if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 400) == 1
                && set_mario_npc_dialog(MARIO_DIALOG_LOOK_FRONT) == MARIO_DIALOG_STATUS_SPEAK) {
                s16 response = cutscene_object_with_dialog(CUTSCENE_DIALOG, o, DIALOG_110);
                if (response != DIALOG_RESPONSE_NONE) {
                    o->oForwardVel = 10.0f;
                    o->oAction = 1;
                    set_mario_npc_dialog(MARIO_DIALOG_STOP);
                }
            }
            break;

        case 1:
            snowmans_bottom_act_1();
            adjust_rolling_face_pitch(o->oSnowmansBottomScale);
            cur_obj_play_sound_1(SOUND_ENV_UNKNOWN2);
            break;

        case 2:
            snowmans_bottom_act_2();
            adjust_rolling_face_pitch(o->oSnowmansBottomScale);
            cur_obj_play_sound_1(SOUND_ENV_UNKNOWN2);
            break;

        case 3:
            snowmans_bottom_act_3();
            break;

        case 4:
            cur_obj_push_mario_away_from_cylinder(210.0f, 550);
            break;
    }

    set_rolling_sphere_hitbox();
    set_object_visibility(o, 8000);
    cur_obj_scale(o->oSnowmansBottomScale);
    o->oGraphYOffset = o->oSnowmansBottomScale * 180.0f;
}

void bhv_snowmans_head_init(void) {
    u8 starFlags = save_file_get_star_flags(gCurrSaveFileNum - 1, COURSE_NUM_TO_INDEX(gCurrCourseNum));
    s8 sp36 = (o->oBehParams >> 24) & 0xFF;

    cur_obj_scale(0.7f);

    o->oGravity = 5.0f;
    o->oFriction = 0.999f;
    o->oBuoyancy = 2.0f;

    if ((starFlags & (1 << sp36)) && gCurrActNum != sp36 + 1) {
        spawn_object_abs_with_rot(o, 0, MODEL_CCM_SNOWMAN_BASE, bhvBigSnowmanWhole, -4230, -1344, 1813,
                                  0, 0, 0);
        o->oPosX = -4230.0f;
        o->oPosY = -994.0f;
        o->oPosZ = 1813.0f;
        o->oAction = 1;
    }
}

void bhv_snowmans_head_loop(void) {
    UNUSED s16 unused;
    s16 collisionFlags;

    switch (o->oAction) {
        case 0:
            if (trigger_obj_dialog_when_facing(&o->oSnowmansHeadDialogActive, DIALOG_109, 400.0f, MARIO_DIALOG_LOOK_FRONT)) {
                o->oAction = 1;
            }
            break;

        case 1:
            break;

        case 2:
            collisionFlags = object_step_without_floor_orient();
            if (collisionFlags & OBJ_COL_FLAG_NO_Y_VEL) {
                o->oAction = 3;
            }
            break;

        case 3:
            object_step_without_floor_orient();
            if (o->oPosY < -994.0f) {
                o->oPosY = -994.0f;
                o->oAction = 4;
                cur_obj_play_sound_2(SOUND_OBJ_SNOWMAN_EXPLODE);
                play_puzzle_jingle();
            }
            break;

        case 4:
            if (trigger_obj_dialog_when_facing(&o->oSnowmansHeadDialogActive, DIALOG_111, 700.0f, MARIO_DIALOG_LOOK_UP)) {
                spawn_mist_particles();
                spawn_default_star(-4700.0f, -1024.0f, 1890.0f);
                o->oAction = 1;
            }
            break;
    }

    cur_obj_push_mario_away_from_cylinder(180.0f, 150.0f);
}

void bhv_snowmans_body_checkpoint_loop(void) {
    if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 800)) {
        o->parentObj->oSnowmansBottomUnk1AC++;
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }

    if (o->parentObj->activeFlags == ACTIVE_FLAG_DEACTIVATED) {
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}
