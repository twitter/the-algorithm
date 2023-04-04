
/**
 * @file intro_lakitu.inc.c
 * This file implements lakitu's behvaior during the intro cutscene.
 * It's also used during the ending cutscene.
 */

/**
 * Add the camera's position to `offset`, rotate the point to be relative to the camera's focus, then
 * set lakitu's location.
 */
void intro_lakitu_set_offset_from_camera(struct Object *obj, Vec3f offset) {
    f32 dist;
    Vec3s focusAngles;
    s16 offsetPitch, offsetYaw;

    vec3f_add(offset, gCamera->pos);
    vec3f_get_dist_and_angle(gCamera->pos, gCamera->focus,
                             &dist, &focusAngles[0], &focusAngles[1]);
    vec3f_get_dist_and_angle(gCamera->pos, offset, &dist, &offsetPitch, &offsetYaw);
    vec3f_set_dist_and_angle(gCamera->pos, offset, dist,
                             focusAngles[0] + offsetPitch, focusAngles[1] + offsetYaw);
    vec3f_to_object_pos(obj, offset);
}

void intro_lakitu_set_focus(struct Object *obj, Vec3f newFocus) {
    UNUSED u8 filler1[12];
    Vec3f origin;
    f32 dist;
    s16 pitch, yaw;
    UNUSED u8 filler2[4];

    // newFocus is an offset from lakitu's origin, not a point in the world.
    vec3f_set(origin, 0.0f, 0.0f, 0.0f);
    vec3f_get_dist_and_angle(origin, newFocus, &dist, &pitch, &yaw);

    obj->oFaceAnglePitch = pitch;
    obj->oFaceAngleYaw = yaw;
}

/**
 * Move lakitu along the spline `offset`, relative to the camera, and face him towards the corresponding
 * location along the spline `focus`.
 */
s32 intro_lakitu_set_pos_and_focus(struct Object *obj, struct CutsceneSplinePoint offset[],
                                   struct CutsceneSplinePoint focus[]) {
    Vec3f newOffset, newFocus;
    s32 splineFinished = 0;
    s16 splineSegment = obj->oIntroLakituSplineSegment;

    if ((move_point_along_spline(newFocus, offset, &splineSegment,
                                 &obj->oIntroLakituSplineSegmentProgress) == 1)
        || (move_point_along_spline(newOffset, focus, &splineSegment,
                                    &obj->oIntroLakituSplineSegmentProgress) == 1)) {
        splineFinished++;
    }

    obj->oIntroLakituSplineSegment = splineSegment;

    intro_lakitu_set_offset_from_camera(obj, newOffset);
    intro_lakitu_set_focus(obj, newFocus);

    return splineFinished;
}

#ifdef VERSION_EU
#define TIMER1 599
#define TIMER2 74
#else
#define TIMER1 720
#define TIMER2 98
#endif

void bhv_intro_lakitu_loop(void) {
    Vec3f sp64, sp58, sp4C;
    UNUSED u8 filler[16];

    switch (o->oAction) {
        case 0:
            cur_obj_disable_rendering();

            o->oIntroLakituSplineSegment = 0.0f;
            o->oIntroLakituSplineSegmentProgress = 0.0f;
            o->oIntroLakituCloud =
                spawn_object_relative_with_scale(1, 0, 0, 0, 2.0f, o, MODEL_MIST, bhvCloud);

            if (gCamera->cutscene == CUTSCENE_END_WAVING) {
                o->oAction = 100;
            } else {
                o->oAction++;
            }
            break;

        case 1:
            cur_obj_enable_rendering();

            if ((gCutsceneTimer > 350) && (gCutsceneTimer < 458)) {
                o->oPosX = gCamera->pos[0];
                o->oPosY = gCamera->pos[1] + 500.0f;
                o->oPosZ = gCamera->pos[2];
            }

            if (gCutsceneTimer > 52) {
                cur_obj_play_sound_1(SOUND_AIR_LAKITU_FLY_HIGHPRIO);
            }

            if (intro_lakitu_set_pos_and_focus(o, gIntroLakituStartToPipeOffsetFromCamera,
                                               gIntroLakituStartToPipeFocus) == 1) {
                o->oAction++;
            }

            switch (o->oTimer) {
#if defined(VERSION_US) || defined(VERSION_SH)
                case 534:
                    cur_obj_play_sound_2(SOUND_ACTION_FLYING_FAST);
                    break;
                case 581:
                    cur_obj_play_sound_2(SOUND_ACTION_INTRO_UNK45E);
                    break;
#endif
                case 73:
                    o->oAnimState++;
                    break;
                case 74:
                    o->oAnimState--;
                    break;
                case 82:
                    o->oAnimState++;
                    break;
                case 84:
                    o->oAnimState--;
                    break;
            }
#ifdef VERSION_EU
            if (o->oTimer == 446) {
                cur_obj_play_sound_2(SOUND_ACTION_FLYING_FAST);
            }
            if (o->oTimer == 485) {
                cur_obj_play_sound_2(SOUND_ACTION_INTRO_UNK45E);
            }
#endif
            break;

        case 2:
            if (gCutsceneTimer > TIMER1) {
                o->oAction++;

                o->oIntroLakituUnk100 = 1400.0f;
                o->oIntroLakituUnk104 = -4096.0f;
                o->oIntroLakituUnk108 = 2048.0f;
                o->oIntroLakituUnk10C = -200.0f;

                o->oMoveAngleYaw = 0x8000;
                o->oFaceAngleYaw = o->oMoveAngleYaw + 0x4000;
                o->oMoveAnglePitch = 0x800;
            }

            cur_obj_play_sound_1(SOUND_AIR_LAKITU_FLY_HIGHPRIO);
            break;

        case 3:
            cur_obj_play_sound_1(SOUND_AIR_LAKITU_FLY_HIGHPRIO);

            vec3f_set(sp58, -1128.0f, 560.0f, 4664.0f);
            o->oMoveAngleYaw += 0x200;
            o->oIntroLakituUnk100 = approach_f32_asymptotic(o->oIntroLakituUnk100, 100.0f, 0.03f);
            o->oFaceAnglePitch = atan2s(200.0f, o->oPosY - 400.0f);
            o->oFaceAngleYaw = approach_s16_asymptotic(o->oFaceAngleYaw, o->oMoveAngleYaw + 0x8000, 4);
            vec3f_set_dist_and_angle(sp58, sp4C, o->oIntroLakituUnk100, 0, o->oMoveAngleYaw);
            sp4C[1] += 150.0f * coss((s16) o->oIntroLakituUnk104);
            o->oIntroLakituUnk104 += o->oIntroLakituUnk108;
            o->oIntroLakituUnk108 = approach_f32_asymptotic(o->oIntroLakituUnk108, 512.0f, 0.05f);
            sp4C[0] += o->oIntroLakituUnk10C;
            o->oIntroLakituUnk10C = approach_f32_asymptotic(o->oIntroLakituUnk10C, 0.0f, 0.05f);
            vec3f_to_object_pos(o, sp4C);

            if (o->oTimer == 31) {
                o->oPosY -= 158.0f;
                // Spawn white ground particles
                spawn_mist_from_global();
                o->oPosY += 158.0f;
            }

            if (o->oTimer == TIMER2) {
                obj_mark_for_deletion(o);
                obj_mark_for_deletion(o->oIntroLakituCloud);
            }

#ifndef VERSION_JP
            if (o->oTimer == 14) {
                cur_obj_play_sound_2(SOUND_ACTION_INTRO_UNK45F);
            }
#endif
            break;

        case 100:
            cur_obj_enable_rendering();

            vec3f_set(sp64, -100.0f, 100.0f, 300.0f);
            offset_rotated(sp4C, gCamera->pos, sp64, sMarioCamState->faceAngle);
            vec3f_to_object_pos(o, sp4C);

            o->oMoveAnglePitch = 0x1000;
            o->oMoveAngleYaw = 0x9000;
            o->oFaceAnglePitch = o->oMoveAnglePitch / 2;
            o->oFaceAngleYaw = o->oMoveAngleYaw;

            o->oAction++;
            break;

        case 101:
            object_pos_to_vec3f(sp4C, o);

            if (o->oTimer > 60) {
                o->oForwardVel = approach_f32_asymptotic(o->oForwardVel, -10.0f, 0.05f);
                o->oMoveAngleYaw += 0x78;
                o->oMoveAnglePitch += 0x40;
                o->oFaceAngleYaw = camera_approach_s16_symmetric(
                                       o->oFaceAngleYaw, calculate_yaw(sp4C, gCamera->pos), 0x200);
            }

            if (o->oTimer > 105) {
                o->oAction++;
                o->oMoveAnglePitch = 0xE00;
            }

            o->oFaceAnglePitch = 0;

            cur_obj_set_pos_via_transform();
            break;

        case 102:
            object_pos_to_vec3f(sp4C, o);

            o->oForwardVel = approach_f32_asymptotic(o->oForwardVel, 60.0f, 0.05f);
            o->oFaceAngleYaw = camera_approach_s16_symmetric(
                                   o->oFaceAngleYaw, calculate_yaw(sp4C, gCamera->pos), 0x200);

            if (o->oTimer < 62) {
                o->oMoveAngleYaw = approach_s16_asymptotic(o->oMoveAngleYaw, 0x1800, 0x1E);
            }

            o->oMoveAnglePitch = camera_approach_s16_symmetric(o->oMoveAnglePitch, -0x2000, 0x5A);
            o->oFaceAnglePitch = 0;

            cur_obj_set_pos_via_transform();
            break;
    }
}

#undef TIMER1
#undef TIMER2
