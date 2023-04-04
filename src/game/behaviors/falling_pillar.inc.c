
/**
 * Behavior for the falling pillars inside the underwater cave area of
 * Jolly Roger Bay.
 *
 * Also includes behavior for the invisible hitboxes they spawn.
 */

static struct ObjectHitbox sFallingPillarHitbox = {
    /* interactType:      */ INTERACT_DAMAGE,
    /* downOffset:        */ 150,
    /* damageOrCoinValue: */ 3,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 150,
    /* height:            */ 300,
    /* hurtboxRadius:     */ 0,
    /* hurtboxHeight:     */ 0,
};

/**
 * Initiates various physics params for the pillar.
 */
void bhv_falling_pillar_init(void) {
    o->oGravity = 0.5f;
    o->oFriction = 0.91f;
    o->oBuoyancy = 1.3f;
}

/**
 * Spawns 4 hitboxes with Y coordinates offset.
 */
void bhv_falling_pillar_spawn_hitboxes(void) {
    s32 i;

    for (i = 0; i < 4; i++) {
        spawn_object_relative(i, 0, i * 400 + 300, 0, o, MODEL_NONE, bhvFallingPillarHitbox);
    }
}

/**
 * Computes the angle from current pillar position to 500 units in front of
 * Mario.
 */
s16 bhv_falling_pillar_calculate_angle_in_front_of_mario(void) {
    // Calculate target to be 500 units in front of Mario in
    // the direction he is facing (angle[1] is yaw).
    f32 targetX = sins(gMarioObject->header.gfx.angle[1]) * 500.0f + gMarioObject->header.gfx.pos[0];
    f32 targetZ = coss(gMarioObject->header.gfx.angle[1]) * 500.0f + gMarioObject->header.gfx.pos[2];

    // Calculate the angle to the target from the pillar's current location.
    return atan2s(targetZ - o->oPosZ, targetX - o->oPosX);
}

/**
 * Falling pillar main logic loop.
 */
void bhv_falling_pillar_loop(void) {
    s16 angleInFrontOfMario;

    switch (o->oAction) {
        case FALLING_PILLAR_ACT_IDLE:
            // When Mario is within 1300 units of distance...
            if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 1300)) {
                // Begin slightly moving towards Mario.
                o->oMoveAngleYaw = o->oAngleToMario;
                o->oForwardVel = 1.0f;

                // Spawn the invisible hitboxes.
                bhv_falling_pillar_spawn_hitboxes();

                // Start turning towards Mario.
                o->oAction = FALLING_PILLAR_ACT_TURNING;

                // Play the detaching sound.
                cur_obj_play_sound_2(SOUND_GENERAL_POUND_ROCK);
            }
            break;

        case FALLING_PILLAR_ACT_TURNING:
            object_step_without_floor_orient();

            // Calculate angle in front of Mario and turn towards it.
            angleInFrontOfMario = bhv_falling_pillar_calculate_angle_in_front_of_mario();
            o->oFaceAngleYaw = approach_s16_symmetric(o->oFaceAngleYaw, angleInFrontOfMario, 0x400);

            // After 10 ticks, start falling.
            if (o->oTimer > 10) {
                o->oAction = FALLING_PILLAR_ACT_FALLING;
            }
            break;

        case FALLING_PILLAR_ACT_FALLING:
            object_step_without_floor_orient();

            // Start falling slowly, with increasing acceleration each frame.
            o->oFallingPillarPitchAcceleration += 4.0f;
            o->oAngleVelPitch += o->oFallingPillarPitchAcceleration;
            o->oFaceAnglePitch += o->oAngleVelPitch;

            // Once the pillar has turned nearly 90 degrees (after ~22 frames),
            if (o->oFaceAnglePitch > 0x3900) {
                // Move 500 units in the direction of falling.
                o->oPosX += sins(o->oFaceAngleYaw) * 500.0f;
                o->oPosZ += coss(o->oFaceAngleYaw) * 500.0f;

                // Make the camera shake and spawn dust clouds.
                set_camera_shake_from_point(SHAKE_POS_MEDIUM, o->oPosX, o->oPosY, o->oPosZ);
                spawn_mist_particles_variable(0, 0, 92.0f);

                // Go invisible.
                o->activeFlags = ACTIVE_FLAG_DEACTIVATED;

                // Play the hitting the ground sound.
                create_sound_spawner(SOUND_GENERAL_BIG_POUND);
            }
            break;
    }
}

/**
 * Main loop for the invisible hitboxes.
 */
void bhv_falling_pillar_hitbox_loop(void) {
    // Get the state of the pillar.
    s32 pitch = o->parentObj->oFaceAnglePitch;
    s32 yaw = o->parentObj->oFaceAngleYaw;
    f32 x = o->parentObj->oPosX;
    f32 y = o->parentObj->oPosY;
    f32 z = o->parentObj->oPosZ;
    f32 yOffset = o->oBehParams2ndByte * 400 + 300;

    // Update position of hitboxes so they fall with the pillar.
    o->oPosX = sins(pitch) * sins(yaw) * yOffset + x;
    o->oPosY = coss(pitch) * yOffset + y;
    o->oPosZ = sins(pitch) * coss(yaw) * yOffset + z;

    // Give these a hitbox so they can collide with Mario.
    obj_set_hitbox(o, &sFallingPillarHitbox);

    // When the pillar goes inactive, the hitboxes also go inactive.
    if (o->parentObj->activeFlags == ACTIVE_FLAG_DEACTIVATED) {
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}
