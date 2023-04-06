
/**
 * Behavior for bhvCameraLakitu. This includes both the intro lakitu and the
 * lakitu visible in the mirror room.
 * TODO: Processing order relative to bhvCloud
 */

/**
 * Init function for camera lakitu.
 * If this is the intro lakitu, despawn unless this is the start of the game.
 * Spawn cloud if not the intro lakitu.
 */
void bhv_camera_lakitu_init(void) {
    if (o->oBehParams2ndByte != CAMERA_LAKITU_BP_FOLLOW_CAMERA) {
        // Despawn unless this is the very beginning of the game
        if (gShouldNotPlayCastleMusic != TRUE) {
            obj_mark_for_deletion(o);
        }
    } else {
        spawn_object_relative_with_scale(CLOUD_BP_LAKITU_CLOUD, 0, 0, 0, 2.0f, o, MODEL_MIST, bhvCloud);
    }
}

/**
 * Wait for mario to stand on the bridge, then interrupt his action and enter
 * the spawn cloud action.
 */
static void camera_lakitu_intro_act_trigger_cutscene(void) {
    //! These bounds are slightly smaller than the actual bridge bounds, allowing
    //  the RTA speedrunning method of lakitu skip
    if (gMarioObject->oPosX > -544.0f && gMarioObject->oPosX < 545.0f && gMarioObject->oPosY > 800.0f
        && gMarioObject->oPosZ > -2000.0f && gMarioObject->oPosZ < -177.0f
        && gMarioObject->oPosZ < -177.0f) // always double check your conditions
    {
        if (set_mario_npc_dialog(2) == 1) {
            o->oAction = CAMERA_LAKITU_INTRO_ACT_SPAWN_CLOUD;
        }
    }
}

/**
 * Warp up into the air and spawn cloud, then enter the TODO action.
 */
static void camera_lakitu_intro_act_spawn_cloud(void) {
    if (set_mario_npc_dialog(2) == 2) {
        o->oAction = CAMERA_LAKITU_INTRO_ACT_UNK2;

        o->oPosX = 1800.0f;
        o->oPosY = 2400.0f;
        o->oPosZ = -2400.0f;

        o->oMoveAnglePitch = 0x4000;
        o->oCameraLakituSpeed = 60.0f;
        o->oCameraLakituCircleRadius = 1000.0f;

        spawn_object_relative_with_scale(CLOUD_BP_LAKITU_CLOUD, 0, 0, 0, 2.0f, o, MODEL_MIST, bhvCloud);
    }
}

/**
 * Circle down to mario, show the dialog, then fly away.
 */
static void camera_lakitu_intro_act_show_dialog(void) {
    s16 targetMovePitch;
    s16 targetMoveYaw;

    cur_obj_play_sound_1(SOUND_AIR_LAKITU_FLY);

    // Face toward mario
    o->oFaceAnglePitch = obj_turn_pitch_toward_mario(120.0f, 0);
    o->oFaceAngleYaw = o->oAngleToMario;

    // After finishing dialog, fly away and despawn
    if (o->oCameraLakituFinishedDialog) {
        approach_f32_ptr(&o->oCameraLakituSpeed, 60.0f, 3.0f);
        if (o->oDistanceToMario > 6000.0f) {
            obj_mark_for_deletion(o);
        }

        targetMovePitch = -0x3000;
        targetMoveYaw = -0x6000;
    } else {
        if (o->oCameraLakituSpeed != 0.0f) {
            if (o->oDistanceToMario > 5000.0f) {
                targetMovePitch = o->oMoveAnglePitch;
                targetMoveYaw = o->oAngleToMario;
            } else {
                // Stay moving in a circle around mario
                s16 turnAmount = 0x4000
                                 - atan2s(o->oCameraLakituCircleRadius,
                                          o->oDistanceToMario - o->oCameraLakituCircleRadius);
                if ((s16)(o->oMoveAngleYaw - o->oAngleToMario) < 0) {
                    turnAmount = -turnAmount;
                }

                targetMoveYaw = o->oAngleToMario + turnAmount;
                targetMovePitch = o->oFaceAnglePitch;

                approach_f32_ptr(&o->oCameraLakituCircleRadius, 200.0f, 50.0f);
                if (o->oDistanceToMario < 1000.0f) {
#ifndef VERSION_JP
                    if (!o->oCameraLakituUnk104) {
                        play_music(SEQ_PLAYER_LEVEL, SEQUENCE_ARGS(15, SEQ_EVENT_CUTSCENE_LAKITU), 0);
                        o->oCameraLakituUnk104 = TRUE;
                    }
#endif

                    // Once within 1000 units, slow down
                    approach_f32_ptr(&o->oCameraLakituSpeed, 20.0f, 1.0f);
                    if (o->oDistanceToMario < 500.0f
                        && abs_angle_diff(gMarioObject->oFaceAngleYaw, o->oFaceAngleYaw) > 0x7000) {
                        // Once within 500 units and facing toward mario, come
                        // to a stop
                        approach_f32_ptr(&o->oCameraLakituSpeed, 0.0f, 5.0f);
                    }
                }
            }
        } else if (cur_obj_update_dialog_with_cutscene(2, DIALOG_UNK2_FLAG_0, CUTSCENE_DIALOG, DIALOG_034) != 0) {
            o->oCameraLakituFinishedDialog = TRUE;
        }
    }

    o->oCameraLakituPitchVel = approach_s16_symmetric(o->oCameraLakituPitchVel, 0x7D0, 0x190);
    obj_move_pitch_approach(targetMovePitch, o->oCameraLakituPitchVel);

    o->oCameraLakituYawVel = approach_s16_symmetric(o->oCameraLakituYawVel, 0x7D0, 0x64);
    cur_obj_rotate_yaw_toward(targetMoveYaw, o->oCameraLakituYawVel);

    // vel y is explicitly computed, so gravity doesn't apply
    obj_compute_vel_from_move_pitch(o->oCameraLakituSpeed);
    cur_obj_move_using_fvel_and_gravity();
}

/**
 * Update function for camera lakitu.
 */
void bhv_camera_lakitu_update(void) {
    if (!(o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM)) {
        obj_update_blinking(&o->oCameraLakituBlinkTimer, 20, 40, 4);

        if (o->oBehParams2ndByte != CAMERA_LAKITU_BP_FOLLOW_CAMERA) {
            switch (o->oAction) {
                case CAMERA_LAKITU_INTRO_ACT_TRIGGER_CUTSCENE:
                    camera_lakitu_intro_act_trigger_cutscene();
                    break;
                case CAMERA_LAKITU_INTRO_ACT_SPAWN_CLOUD:
                    camera_lakitu_intro_act_spawn_cloud();
                    break;
                case CAMERA_LAKITU_INTRO_ACT_UNK2:
                    camera_lakitu_intro_act_show_dialog();
                    break;
            }
        } else {
            f32 val0C = (f32) 0x875C3D / 0x800 - gLakituState.curPos[0];
            if (gLakituState.curPos[0] < 1700.0f || val0C < 0.0f) {
                cur_obj_hide();
            } else {
                cur_obj_unhide();

                o->oPosX = gLakituState.curPos[0];
                o->oPosY = gLakituState.curPos[1];
                o->oPosZ = gLakituState.curPos[2];

                o->oHomeX = gLakituState.curFocus[0];
                o->oHomeZ = gLakituState.curFocus[2];

                o->oFaceAngleYaw = -cur_obj_angle_to_home();
                o->oFaceAnglePitch = atan2s(cur_obj_lateral_dist_to_home(),
                                            o->oPosY - gLakituState.curFocus[1]);

                o->oPosX = (f32) 0x875C3D / 0x800 + val0C;
            }
        }
    }
}
