/**
 * Behavior for bhvAlphaBooKey and bhvBetaBooKey.
 * They were apparently intended to be a key that would be contained in boos
 * and would fall out, like coins do. There is a model, MODEL_BETA_BOO_KEY, that
 * is loaded in script_func_global_10, which contains boo-themed models used in
 * BBH and the castle courtyard. It is used in a macro preset with bhvAlphaBooKey,
 * which is also grouped near other boo/BBH-related macros. This is evidence that
 * bhvAlphaBooKey was supposed to be a key. bhvBetaBooKey has code similar to
 * bhvAlphaBooKey's for rotation and collection, and functions correctly when
 * spawned as a child of a boo (it checks the death status of the boo to know when
 * to drop, so this is almost definitely what was intended). It appears that
 * bhvAlphaBooKey was abandoned for reasons unknown and replaced with bhvBetaBooKey.
 */

/**
 * Update function for bhvAlphaBooKey.
 * It rotates the key, and deletes it when collected.
 * The code in this function is similar to that found in
 * bhvBetaBooKey code, which implies that these are 2 versions
 * of the same object. It is a less developed version of
 * bhvBetaBooKey, hence the "alpha" moniker.
 */
void bhv_alpha_boo_key_loop(void) {
    // Rotate the key
    o->oFaceAngleRoll += 0x200;
    o->oFaceAngleYaw += 0x200;

    if (obj_check_if_collided_with_object(o, gMarioObject)) {
        // This line makes the object inside the key's parent boo drop.
        // Was this intended to make the boo die when the key is collected?
        // Boos don't read from oBooDeathStatus, they only set it to let the
        // objects inside them know when to drop.
        // Due to this line, the key will cause the game to crash if collected
        // when its parent object is NULL.
        // Another theory is that the boo key was intended to be spawned by a
        // spawner that used object field 0x00 for something else. This
        // is elaborated on more in beta_boo_key_dropped_loop.
        o->parentObj->oBooDeathStatus = BOO_DEATH_STATUS_DYING;

        // Delete the object and spawn sparkles
        obj_mark_for_deletion(o);
        spawn_object(o, MODEL_SPARKLES, bhvGoldenCoinSparkles);
    }
}

// For some reason, the action functions for the beta boo key
// are written in reverse order.

/**
 * Continue to make the key fall, and handle collection.
 */
static void beta_boo_key_dropped_loop(void) {
    // Apply standard physics to the key
    cur_obj_update_floor_and_walls();
    cur_obj_move_standard(78);

    // Slowly increase the Y offset to make the model aligned correctly.
    // This is spread out over 13 frames so that it's not noticable.
    if (o->oGraphYOffset < 26.0f) {
        o->oGraphYOffset += 2.0f;
    }

    // Transition from rotating in both the yaw and the roll axes
    // to just in the yaw axis. This is done by truncating the key's roll
    // to the nearest multiple of 0x800, then continuously adding 0x800
    // until it reaches a multiple of 0x10000, at which point &-ing with
    // 0xFFFF returns 0 and the key stops rotating in the roll direction.
    if (o->oFaceAngleRoll & 0xFFFF) {
        o->oFaceAngleRoll &= 0xF800;
        o->oFaceAngleRoll += 0x800;
    }

    // Once the key stops bouncing, stop its horizontal movement on the ground.
    if (o->oMoveFlags & OBJ_MOVE_ON_GROUND) {
        o->oVelX = 0.0f;
        o->oVelZ = 0.0f;
    }

    // Rotate the key
    o->oFaceAngleYaw += 0x800;

    // If the key hits the floor or 90 frames have elapsed since it was dropped,
    // become tangible and handle collision.
    if (o->oTimer > 90 || o->oMoveFlags & OBJ_MOVE_LANDED) {
        cur_obj_become_tangible();

        if (obj_check_if_collided_with_object(o, gMarioObject)) {
            // This interaction status is 0x01, the first interaction status flag.
            // It was only used for Hoot in the final game, but it seems it could've
            // done something else or held some special meaning in beta.
            // Earlier, in beta_boo_key_drop (called when the parent boo is killed),
            // o->parentObj is set to the parent boo's parentObj. This means that
            // here, the parentObj is actually the parent of the old parent boo.
            // One theory about this code is that there was a boo spawner, which
            // spawned "false" boos and one "true" boo with the key, and the player
            // was intended to find the one with the key to progress.
            o->parentObj->oInteractStatus = INT_STATUS_HOOT_GRABBED_BY_MARIO;

            // Delete the object and spawn sparkles
            obj_mark_for_deletion(o);
            spawn_object(o, MODEL_SPARKLES, bhvGoldenCoinSparkles);
        }
    }
}

/**
 * Drop the key. This function is run once, the frame after the boo dies;
 * It immediately sets the action to BETA_BOO_KEY_ACT_DROPPED.
 */
static void beta_boo_key_drop(void) {
    s16 velocityDirection;
    f32 velocityMagnitude;

    // Update the key to be inside the boo
    struct Object *parent = o->parentObj;
    obj_copy_pos(o, parent);

    // This if statement to only run this code on the first frame
    // is redundant, since it instantly sets the action to BETA_BOO_KEY_ACT_DROPPED
    // which stops this function from running again.
    if (o->oTimer == 0) {
        // Separate from the parent boo
        o->parentObj = parent->parentObj;

        o->oAction = BETA_BOO_KEY_ACT_DROPPED;

        // Make the key move laterally away from Mario at 3 units/frame
        // (as if he transferred kinetic energy to it)
        velocityDirection = gMarioObject->oMoveAngleYaw;
        velocityMagnitude = 3.0f;

        o->oVelX = sins(velocityDirection) * velocityMagnitude;
        o->oVelZ = coss(velocityDirection) * velocityMagnitude;

        // Give it an initial Y velocity of 40 units/frame
        o->oVelY = 40.0f;
    }

    // Rotate the key
    o->oFaceAngleYaw += 0x200;
    o->oFaceAngleRoll += 0x200;
}

/**
 * Update the key to be inside its parent boo, and handle the boo dying.
 */
static void beta_boo_key_inside_boo_loop(void) {
    // Update the key to be inside the boo at all times
    struct Object *parent = o->parentObj;
    obj_copy_pos(o, parent);

    // Use a Y offset of 40 to make the key model aligned correctly.
    // (Why didn't they use oGraphYOffset?)
    o->oPosY += 40.0f;

    // If the boo is dying/dead, set the action to BETA_BOO_KEY_ACT_DROPPING.
    if (parent->oBooDeathStatus != BOO_DEATH_STATUS_ALIVE) {
        o->oAction = BETA_BOO_KEY_ACT_DROPPING;
    }

    // Rotate the key
    o->oFaceAngleRoll += 0x200;
    o->oFaceAngleYaw += 0x200;
}

static void (*sBetaBooKeyActions[])(void) = { beta_boo_key_inside_boo_loop, beta_boo_key_drop,
                                              beta_boo_key_dropped_loop };

/**
 * Update function for bhvBetaBooKey.
 */
void bhv_beta_boo_key_loop(void) {
    cur_obj_call_action_function(sBetaBooKeyActions);
}
