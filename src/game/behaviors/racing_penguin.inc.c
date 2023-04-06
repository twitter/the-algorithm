struct RacingPenguinData {
    s16 text;
    f32 radius;
    f32 height;
};

static struct RacingPenguinData sRacingPenguinData[] = {
    { DIALOG_055, 200.0f, 200.0f },
    { DIALOG_164, 350.0f, 250.0f },
};

void bhv_racing_penguin_init(void) {
    if (gMarioState->numStars == 120) {
        cur_obj_scale(8.0f);
        o->header.gfx.scale[1] = 5.0f;
        o->oBehParams2ndByte = 1;
    }
}

static void racing_penguin_act_wait_for_mario(void) {
    if (o->oTimer > o->oRacingPenguinInitTextCooldown && o->oPosY - gMarioObject->oPosY <= 0.0f
        && cur_obj_can_mario_activate_textbox_2(400.0f, 400.0f)) {
        o->oAction = RACING_PENGUIN_ACT_SHOW_INIT_TEXT;
    }
}

static void racing_penguin_act_show_init_text(void) {
    s32 response;
    struct Object *child;

    response = obj_update_race_proposition_dialog(sRacingPenguinData[o->oBehParams2ndByte].text);
    if (response == 1) {
        child = cur_obj_nearest_object_with_behavior(bhvPenguinRaceFinishLine);
        child->parentObj = o;

        child = cur_obj_nearest_object_with_behavior(bhvPenguinRaceShortcutCheck);
        child->parentObj = o;

        o->oPathedStartWaypoint = o->oPathedPrevWaypoint =
            segmented_to_virtual(ccm_seg7_trajectory_penguin_race);
        o->oPathedPrevWaypointFlags = 0;

        o->oAction = RACING_PENGUIN_ACT_PREPARE_FOR_RACE;
        o->oVelY = 60.0f;
        ;
    } else if (response == 2) {
        o->oAction = RACING_PENGUIN_ACT_WAIT_FOR_MARIO;
        o->oRacingPenguinInitTextCooldown = 60;
    }
}

static void racing_penguin_act_prepare_for_race(void) {
    if (obj_begin_race(TRUE)) {
        o->oAction = RACING_PENGUIN_ACT_RACE;
        o->oForwardVel = 20.0f;
    }

    cur_obj_rotate_yaw_toward(0x4000, 2500);
}

static void racing_penguin_act_race(void) {
    f32 targetSpeed;
    f32 minSpeed;

    if (cur_obj_follow_path(0) == PATH_REACHED_END) {
        o->oRacingPenguinReachedBottom = TRUE;
        o->oAction = RACING_PENGUIN_ACT_FINISH_RACE;
    } else {
        targetSpeed = o->oPosY - gMarioObject->oPosY;
        minSpeed = 70.0f;

        cur_obj_play_sound_1(SOUND_AIR_ROUGH_SLIDE);

        if (targetSpeed < 100.0f || (o->oPathedPrevWaypointFlags & WAYPOINT_MASK_00FF) >= 35) {
            if ((o->oPathedPrevWaypointFlags & WAYPOINT_MASK_00FF) >= 35) {
                minSpeed = 60.0f;
            }

            approach_f32_ptr(&o->oRacingPenguinWeightedNewTargetSpeed, -500.0f, 100.0f);
        } else {
            approach_f32_ptr(&o->oRacingPenguinWeightedNewTargetSpeed, 1000.0f, 30.0f);
        }

        targetSpeed = 0.1f * (o->oRacingPenguinWeightedNewTargetSpeed + targetSpeed);
        clamp_f32(&targetSpeed, minSpeed, 150.0f);
        obj_forward_vel_approach(targetSpeed, 0.4f);

        cur_obj_init_animation_with_sound(1);
        cur_obj_rotate_yaw_toward(o->oPathedTargetYaw, (s32)(15.0f * o->oForwardVel));

        if (cur_obj_check_if_at_animation_end() && (o->oMoveFlags & 0x00000003)) {
            spawn_object_relative_with_scale(0, 0, -100, 0, 4.0f, o, MODEL_SMOKE, bhvWhitePuffSmoke2);
        }
    }

    if (mario_is_in_air_action()) {
        if (o->oTimer > 60) {
            o->oRacingPenguinMarioCheated = TRUE;
        }
    } else {
        o->oTimer = 0;
    }
}

static void racing_penguin_act_finish_race(void) {
    if (o->oForwardVel != 0.0f) {
        if (o->oTimer > 5 && (o->oMoveFlags & 0x00000200)) {
            cur_obj_play_sound_2(SOUND_OBJ_POUNDING_LOUD);
            set_camera_shake_from_point(SHAKE_POS_SMALL, o->oPosX, o->oPosY, o->oPosZ);
            o->oForwardVel = 0.0f;
        }
    } else if (cur_obj_init_anim_and_check_if_end(2) != 0) {
        o->oAction = RACING_PENGUIN_ACT_SHOW_FINAL_TEXT;
    }
}

static void racing_penguin_act_show_final_text(void) {
    s32 textResult;

    if (o->oRacingPenguinFinalTextbox == 0) {
        if (cur_obj_rotate_yaw_toward(0, 200)) {
            cur_obj_init_animation_with_sound(3);
            o->oForwardVel = 0.0f;

            if (cur_obj_can_mario_activate_textbox_2(400.0f, 400.0f)) {
                if (o->oRacingPenguinMarioWon) {
                    if (o->oRacingPenguinMarioCheated) {
                        o->oRacingPenguinFinalTextbox = DIALOG_132;
                        o->oRacingPenguinMarioWon = FALSE;
                    } else {
                        o->oRacingPenguinFinalTextbox = DIALOG_056;
                    }
                } else {
                    o->oRacingPenguinFinalTextbox = DIALOG_037;
                }
            }
        } else {
            cur_obj_init_animation_with_sound(0);

#ifndef VERSION_JP
            play_penguin_walking_sound(1);
#endif

            o->oForwardVel = 4.0f;
        }
    } else if (o->oRacingPenguinFinalTextbox > 0) {
        if ((textResult = cur_obj_update_dialog_with_cutscene(2, 1, CUTSCENE_DIALOG, o->oRacingPenguinFinalTextbox)) != 0) {
            o->oRacingPenguinFinalTextbox = -1;
            o->oTimer = 0;
        }
    } else if (o->oRacingPenguinMarioWon) {
#ifdef VERSION_JP
        spawn_default_star(-7339.0f, -5700.0f, -6774.0f);
#else
        cur_obj_spawn_star_at_y_offset(-7339.0f, -5700.0f, -6774.0f, 200.0f);
#endif
        o->oRacingPenguinMarioWon = FALSE;
    }
}

void bhv_racing_penguin_update(void) {
    cur_obj_update_floor_and_walls();

    switch (o->oAction) {
        case RACING_PENGUIN_ACT_WAIT_FOR_MARIO:
            racing_penguin_act_wait_for_mario();
            break;
        case RACING_PENGUIN_ACT_SHOW_INIT_TEXT:
            racing_penguin_act_show_init_text();
            break;
        case RACING_PENGUIN_ACT_PREPARE_FOR_RACE:
            racing_penguin_act_prepare_for_race();
            break;
        case RACING_PENGUIN_ACT_RACE:
            racing_penguin_act_race();
            break;
        case RACING_PENGUIN_ACT_FINISH_RACE:
            racing_penguin_act_finish_race();
            break;
        case RACING_PENGUIN_ACT_SHOW_FINAL_TEXT:
            racing_penguin_act_show_final_text();
            break;
    }

    cur_obj_move_standard(78);
    cur_obj_align_gfx_with_floor();
    cur_obj_push_mario_away_from_cylinder(sRacingPenguinData[o->oBehParams2ndByte].radius,
                                      sRacingPenguinData[o->oBehParams2ndByte].height);
}

void bhv_penguin_race_finish_line_update(void) {
    if (o->parentObj->oRacingPenguinReachedBottom
        || (o->oDistanceToMario < 1000.0f && gMarioObject->oPosZ - o->oPosZ < 0.0f)) {
        if (!o->parentObj->oRacingPenguinReachedBottom) {
            o->parentObj->oRacingPenguinMarioWon = TRUE;
        }
    }
}

void bhv_penguin_race_shortcut_check_update(void) {
    if (o->oDistanceToMario < 500.0f) {
        o->parentObj->oRacingPenguinMarioCheated = TRUE;
    }
}
