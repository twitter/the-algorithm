/**
 * Behavior for bhvBird. These are the birds in the castle grounds
 * that fly away and scatter when Mario comes near them. There are
 * 2 types of birds; spawner birds and spawned birds. Spawner birds
 * are loaded by the level, and are inactive until Mario comes within
 * 2000 units of them, when they spawn 6 spawned birds and start flying.
 * Spawned birds are only spawned by a spawner bird, and start flying
 * immediately after spawning.
 */

/**
 * If the object is a spawned bird, start flying; if it's a spawner bird,
 * spawn spawned birds if Mario comes within 2000 units of it.
 */
static void bird_act_inactive(void) {
    // Start flying if the object is a spawned bird or if it's a spawner bird
    // and Mario is within 2000 units.
    if (o->oBehParams2ndByte == BIRD_BP_SPAWNED || o->oDistanceToMario < 2000.0f) {
        // If the object is a spawner bird, play the sound of birds flying away,
        // and spawn 6 spawned birds (which will start flying on the next frame).
        if (o->oBehParams2ndByte != BIRD_BP_SPAWNED) {
            s32 i;

            cur_obj_play_sound_2(SOUND_GENERAL_BIRDS_FLY_AWAY);

            for (i = 0; i < 6; i++) {
                spawn_object(o, MODEL_BIRDS, bhvBird);
            }

            // The spawner bird's home acts as its target location.
            o->oHomeX = -20.0f;
            o->oHomeZ = -3990.0f;
        }

        // Start flying
        o->oAction = BIRD_ACT_FLY;

        // Start with a random yaw, and a random pitch from 1000 to 5000.
        // Positive pitch is downwards.
        o->oMoveAnglePitch = 5000 - (s32)(4000.0f * random_float());
        o->oMoveAngleYaw = random_u16();

        o->oBirdSpeed = 40.0f;

        cur_obj_unhide();
    }
}

/**
 * Make the bird fly.
 * The bird flies laterally towards a target; (-20, -3990) if it's a spawner bird,
 * and the parent spawner bird if it's a spawned bird.
 */
static void bird_act_fly(void) {
    UNUSED s32 unused;
    f32 distance;

    // Compute forward velocity and vertical velocity from oBirdSpeed and pitch
    obj_compute_vel_from_move_pitch(o->oBirdSpeed);

    // If the bird's parent is higher than 8000 units, despawn the bird.
    // A spawned bird's parent is its spawner bird. A spawner bird's parent
    // is itself. In other words, when a group of birds has its spawner bird
    // fly past Y=8000, they will all despawn simultaneously. Otherwise, fly.
    if (o->parentObj->oPosY > 8000.0f) {
        obj_mark_for_deletion(o);
    } else {
        // If the bird is a spawner bird, fly towards its home; otherwise,
        // fly towards the bird's spawner bird.
        if (o->oBehParams2ndByte != BIRD_BP_SPAWNED) {
            distance = cur_obj_lateral_dist_to_home();

            // The spawner bird will start with its downwards (positive) pitch
            // and will continuously decrease its pitch (i.e. make itself face more upwards)
            // until it reaches its home, at which point it will face directly up.
            // This is done by making its target pitch the arctangent of its distance
            // to its home and its position - 10,000 (which is always negative).
            o->oBirdTargetPitch = atan2s(distance, o->oPosY - 10000.0f);
            o->oBirdTargetYaw = cur_obj_angle_to_home();
        } else {
            distance = lateral_dist_between_objects(o, o->parentObj);

            // The bird's target pitch will face directly to its spawner bird.
            o->oBirdTargetPitch = atan2s(distance, o->oPosY - o->parentObj->oPosY);
            o->oBirdTargetYaw = obj_angle_to_object(o, o->parentObj);

            // The bird goes faster the farther it is from its spawner bird so it can catch up.
            o->oBirdSpeed = 0.04f * dist_between_objects(o, o->parentObj) + 20.0f;
        }

        // Approach to match the bird's target yaw and pitch.
        obj_move_pitch_approach(o->oBirdTargetPitch, 140);
        cur_obj_rotate_yaw_toward(o->oBirdTargetYaw, 800);
        obj_roll_to_match_yaw_turn(o->oBirdTargetYaw, 0x3000, 600);
    }

    // The bird has no gravity, so this function only
    // moves the bird using its forward velocity.
    // Even if it did have gravity, it would only act as
    // a constant added to its Y position every frame since
    // its Y velocity is reset every frame by
    // obj_compute_vel_from_move_pitch.
    cur_obj_move_using_fvel_and_gravity();
}

/**
 * Update function for bhvBird.
 */
void bhv_bird_update(void) {
    switch (o->oAction) {
        case BIRD_ACT_INACTIVE:
            bird_act_inactive();
            break;
        case BIRD_ACT_FLY:
            bird_act_fly();
            break;
    }
}
