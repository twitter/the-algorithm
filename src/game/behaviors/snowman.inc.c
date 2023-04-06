// snowman.c.inc

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
    struct Object *sp34;

    o->oHomeX = o->oPosX;
    o->oHomeY = o->oPosY;
    o->oHomeZ = o->oPosZ;

    o->oGravity = 10.0f;
    o->oFriction = 0.999f;
    o->oBuoyancy = 2.0f;

    o->oVelY = 0;
    o->oForwardVel = 0;
    o->oSnowmansBottomUnkF4 = 0.4f;

    sp34 = cur_obj_nearest_object_with_behavior(bhvSnowmansHead);
    if (sp34 != NULL) {
        o->parentObj = sp34;
    }
    spawn_object_abs_with_rot(o, 0, MODEL_NONE, bhvSnowmansBodyCheckpoint, -402, 461, -2898, 0, 0, 0);
}

void set_rolling_sphere_hitbox(void) {
    obj_set_hitbox(o, &sRollingSphereHitbox);

    if ((o->oInteractStatus & INT_STATUS_INTERACTED) != 0) {
        o->oInteractStatus = 0;
    }
}

void adjust_rolling_face_pitch(f32 f12) {
    o->oFaceAnglePitch += (s16)(o->oForwardVel * (100.0f / f12));
    o->oSnowmansBottomUnkF4 += o->oForwardVel * 1e-4;

    if (o->oSnowmansBottomUnkF4 > 1.0)
        o->oSnowmansBottomUnkF4 = 1.0f;
}

void snowmans_bottom_act_1(void) {
    UNUSED s16 sp26;
    s32 sp20;
    UNUSED s16 sp1E;

    o->oPathedStartWaypoint = segmented_to_virtual(&ccm_seg7_trajectory_snowman);
    sp26 = object_step_without_floor_orient();
    sp20 = cur_obj_follow_path(sp20);
    o->oSnowmansBottomUnkF8 = o->oPathedTargetYaw;
    o->oMoveAngleYaw = approach_s16_symmetric(o->oMoveAngleYaw, o->oSnowmansBottomUnkF8, 0x400);

    if (o->oForwardVel > 70.0)
        o->oForwardVel = 70.0f;

    if (sp20 == -1) {
        sp1E = (u16) o->oAngleToMario - (u16) o->oMoveAngleYaw;
        if (obj_check_if_facing_toward_angle(o->oMoveAngleYaw, o->oAngleToMario, 0x2000) == 1 && o->oSnowmansBottomUnk1AC == 1) {
            o->oSnowmansBottomUnkF8 = o->oAngleToMario;
        } else {
            o->oSnowmansBottomUnkF8 = o->oMoveAngleYaw;
        }
        o->oAction = 2;
    }
}

void snowmans_bottom_act_2(void) {
    UNUSED s16 sp26;

    sp26 = object_step_without_floor_orient();
    if (o->oForwardVel > 70.0)
        o->oForwardVel = 70.0f;

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
        o->activeFlags = 0;
    }
}

void snowmans_bottom_act_3(void) {
    UNUSED s16 sp1E;

    sp1E = object_step_without_floor_orient();
    if ((sp1E & 0x09) == 0x09) {
        o->oAction = 4;
        cur_obj_become_intangible();
    }

    if ((sp1E & 0x01) != 0) {
        spawn_mist_particles_variable(0, 0, 70.0f);
        o->oPosX = -4230.0f;
        o->oPosZ = 1813.0f;
        o->oForwardVel = 0.0f;
    }
}

void bhv_snowmans_bottom_loop(void) {
    s16 sp1E;

    switch (o->oAction) {
        case 0:
            if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 400) == 1
                && set_mario_npc_dialog(1) == 2) {
                sp1E = cutscene_object_with_dialog(CUTSCENE_DIALOG, o, DIALOG_110);
                if (sp1E) {
                    o->oForwardVel = 10.0f;
                    o->oAction = 1;
                    set_mario_npc_dialog(0);
                }
            }
            break;

        case 1:
            snowmans_bottom_act_1();
            adjust_rolling_face_pitch(o->oSnowmansBottomUnkF4);
            cur_obj_play_sound_1(SOUND_ENV_UNKNOWN2);
            break;

        case 2:
            snowmans_bottom_act_2();
            adjust_rolling_face_pitch(o->oSnowmansBottomUnkF4);
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
    cur_obj_scale(o->oSnowmansBottomUnkF4);
    o->oGraphYOffset = o->oSnowmansBottomUnkF4 * 180.0f;
}

void bhv_snowmans_head_init(void) {
    u8 sp37;
    s8 sp36;

    sp37 = save_file_get_star_flags(gCurrSaveFileNum - 1, gCurrCourseNum - 1);
    sp36 = (o->oBehParams >> 24) & 0xFF;

    cur_obj_scale(0.7f);

    o->oGravity = 5.0f;
    o->oFriction = 0.999f;
    o->oBuoyancy = 2.0f;

    if ((sp37 & (1 << sp36)) && gCurrActNum != sp36 + 1) {
        spawn_object_abs_with_rot(o, 0, MODEL_CCM_SNOWMAN_BASE, bhvBigSnowmanWhole, -4230, -1344, 1813,
                                  0, 0, 0);
        o->oPosX = -4230.0f;
        o->oPosY = -994.0f;
        o->oPosZ = 1813.0f;
        o->oAction = 1;
    }
}

void bhv_snowmans_head_loop(void) {
    UNUSED s16 sp1E;
    s16 sp1C;

    switch (o->oAction) {
        case 0:
            if (trigger_obj_dialog_when_facing(&o->oSnowmansHeadUnkF4, DIALOG_109, 400.0f, 1))
                o->oAction = 1;
            break;

        case 1:
            break;

        case 2:
            sp1C = object_step_without_floor_orient();
            if (sp1C & 0x08)
                o->oAction = 3;
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
            if (trigger_obj_dialog_when_facing(&o->oSnowmansHeadUnkF4, DIALOG_111, 700.0f, 2)) {
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
        o->activeFlags = 0;
    }

    if (o->parentObj->activeFlags == 0)
        o->activeFlags = 0;
}
