/**
 * Behavior for bhvCoffinSpawner and bhvCoffin.
 * The coffins are spawned by a singular spawner,
 * with half being able to stand up.
 * Coffins unload after leaving the room.
 */

/**
 * Struct with s16 values for a horizontal position.
 */
struct LateralPosition {
    s16 x;
    s16 z;
};

/**
 * Array of positions for all coffins relative to their spawner.
 */
struct LateralPosition coffinRelativePos[] = {
    { 412, -150 }, { 762, -150 }, { 1112, -150 },
    { 412,  150 }, { 762,  150 }, { 1112,  150 },
};

/**
 * Loop behavior for the object that spawns the six coffins in BBH.
 * Loads the coffins when in the room, they unload themselves.
 */
void bhv_coffin_spawner_loop(void) {
    struct Object *coffin;
    s32 i;
    s16 relativeZ;

    if (o->oAction == COFFIN_SPAWNER_ACT_COFFINS_UNLOADED) {
        if (!(o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM)) {
            for (i = 0; i < 6; i++) {
                relativeZ = coffinRelativePos[i].z;

                // Behavior param of 0 for all even i, 1 for all odd
                coffin = spawn_object_relative(i & 1, coffinRelativePos[i].x, 0, relativeZ, o,
                                              MODEL_BBH_WOODEN_TOMB, bhvCoffin);

                // Never true, game would enter a while(1) before it could.
                // Possible a remnant of days this didn't happen.
                if (coffin != NULL) {
                    // Rotate the coffin 180 degrees if its on the other side of the room.
                    if (relativeZ > 0) {
                        coffin->oFaceAngleYaw = 0x8000;
                    }
                }
            }

            o->oAction += 1;
        }
    } else if (o->activeFlags & ACTIVE_FLAG_IN_DIFFERENT_ROOM) {
        o->oAction = COFFIN_SPAWNER_ACT_COFFINS_UNLOADED;
    }
}

/**
 * The main action for the coffins. Coffins with COFFIN_BP_STATIC skip the behavior, while
 * the other coffins will enter a standing action when Mario is near.
 * Also controls laying the coffin down after it has stood up.
 */
void coffin_act_idle(void) {
    f32 yawCos;
    f32 yawSin;
    f32 dx;
    f32 dz;
    f32 distForwards;
    f32 distSideways;

    if (o->oBehParams2ndByte != COFFIN_BP_STATIC) {
        // Lay down if standing
        if (o->oFaceAnglePitch != 0) {
            o->oAngleVelPitch = approach_s16_symmetric(o->oAngleVelPitch, -2000, 200);

            // If the coffin landed...
            if (obj_face_pitch_approach(0, -o->oAngleVelPitch)) {
                cur_obj_play_sound_2(SOUND_GENERAL_ELEVATOR_MOVE_2);

                // This bit changes the coffin's position,
                // spawns dust there, then resets the position.
                obj_perform_position_op(POS_OP_SAVE_POSITION);
                o->oMoveAngleYaw = o->oFaceAngleYaw - 0x4000;
                obj_set_dist_from_home(200.0f);
                spawn_mist_from_global();
                obj_perform_position_op(POS_OP_RESTORE_POSITION);
            }

            o->oTimer = 0;
        } else {
            // Yaw never changes and is aligned, so yawCos = 1 or -1, yawSin = 0
            yawCos = coss(o->oFaceAngleYaw);
            yawSin = sins(o->oFaceAngleYaw);

            dx = gMarioObject->oPosX - o->oPosX;
            dz = gMarioObject->oPosZ - o->oPosZ;

            distForwards = dx * yawCos + dz * yawSin;
            distSideways = dz * yawCos - dx * yawSin;

            // This checks a box around the coffin and if it has been a bit since it stood up.
            // It also checks in the case Mario is squished, so he doesn't get permanently squished.
            if (o->oTimer > 60
                && (o->oDistanceToMario > 100.0f || gMarioState->action == ACT_SQUISHED)) {
                if (gMarioObject->oPosY - o->oPosY < 200.0f && absf(distForwards) < 140.0f) {
                    if (distSideways < 150.0f && distSideways > -450.0f) {
                        cur_obj_play_sound_2(SOUND_GENERAL_BUTTON_PRESS_2_LOWPRIO);
                        o->oAction = COFFIN_ACT_STAND_UP;
                    }
                }
            }

            o->oAngleVelPitch = 0;
        }
    }
}

/**
 * Stand up the coffin and keep it standing until the timer hits 60.
 */
void coffin_act_stand_up(void) {
    // Stand up
    if (o->oFaceAnglePitch != 0x4000) {
        o->oAngleVelPitch = approach_s16_symmetric(o->oAngleVelPitch, 1000, 200);
        obj_face_pitch_approach(0x4000, o->oAngleVelPitch);
    } else {
        // Stay standing
        if (o->oTimer > 60) {
            o->oAction = COFFIN_ACT_IDLE;
            o->oFaceAngleRoll = 0;
        } else if (o->oTimer > 30) {
            if (gGlobalTimer % 4 == 0) {
                cur_obj_play_sound_2(SOUND_GENERAL_ELEVATOR_MOVE_2);
            }
            // Shake the coffin while its standing
            o->oFaceAngleRoll = 400 * (gGlobalTimer % 2) - 200;
        }

        o->oAngleVelPitch = 0;
    }
}

/**
 * Main behavior for each coffin. Unloads the coffin if the spawner enters
 * that action.
 */
void bhv_coffin_loop(void) {
    // Gotta save those 6 object slots
    if (o->parentObj->oAction == COFFIN_SPAWNER_ACT_COFFINS_UNLOADED) {
        obj_mark_for_deletion(o);
    } else {
        // Scale the coffin vertically? Must have thought it was too short?
        o->header.gfx.scale[1] = 1.1f;

        switch (o->oAction) {
            case COFFIN_ACT_IDLE:
                coffin_act_idle();
                break;
            case COFFIN_ACT_STAND_UP:
                coffin_act_stand_up();
                break;
        }

        load_object_collision_model();
    }
}
