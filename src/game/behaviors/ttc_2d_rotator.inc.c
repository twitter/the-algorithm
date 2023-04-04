
/**
 * Behavior for bhvTTC2DRotator.
 * This includes the hand (in TTC, not the clock in the castle), as well as the
 * purely visual 2D cogs in TTC.
 */

/**
 * Speeds for the hand and the 2D cog, respectively. Negative because clockwise.
 */
static s16 sTTC2DRotatorSpeeds[] = {
    -0x444,
    -0xCCC,
};

/**
 * The time between each increment to target yaw. On random setting, this is
 * only used for the first turn, after which it is chosen randomly.
 * These values are for the hand and the 2D cog, respectively.
 */
static s16 sTTC2DRotatorTimeBetweenTurns[][4] = {
    {
        /* TTC_SPEED_SLOW    */ 40,
        /* TTC_SPEED_FAST    */ 10,
        /* TTC_SPEED_RANDOM  */ 10,
        /* TTC_SPEED_STOPPED */ 0,
    },
    {
        /* TTC_SPEED_SLOW    */ 20,
        /* TTC_SPEED_FAST    */ 5,
        /* TTC_SPEED_RANDOM  */ 5,
        /* TTC_SPEED_STOPPED */ 0,
    },
};

/**
 * Init function for bhvTTC2DRotator.
 */
void bhv_ttc_2d_rotator_init(void) {
    o->oTTC2DRotatorMinTimeUntilNextTurn =
        sTTC2DRotatorTimeBetweenTurns[o->oBehParams2ndByte][gTTCSpeedSetting];
    o->oTTC2DRotatorIncrement = o->oTTC2DRotatorSpeed = sTTC2DRotatorSpeeds[o->oBehParams2ndByte];
}

/**
 * Update function for bhvTTC2DRotator.
 * Rotate to target yaw, and make sure we've waited long enough since the last
 * turn, then increment the target yaw and possibly change direction.
 */
void bhv_ttc_2d_rotator_update(void) {
    s32 startYaw = o->oFaceAngleYaw;

    if (o->oTTC2DRotatorRandomDirTimer != 0) {
        o->oTTC2DRotatorRandomDirTimer--;
    }

    // Wait until rotated to target yaw
    if (o->oTTC2DRotatorMinTimeUntilNextTurn != 0
        && obj_face_yaw_approach(o->oTTC2DRotatorTargetYaw, 0xC8)) {
        // and until MinTimeUntilNextTurn has passed.
        if (o->oTimer > o->oTTC2DRotatorMinTimeUntilNextTurn) {
            // Increment target yaw
            o->oTTC2DRotatorTargetYaw += o->oTTC2DRotatorIncrement;
            o->oTimer = 0;

            if (gTTCSpeedSetting == TTC_SPEED_RANDOM) {
                // If ready for a change in direction, then pick a new
                // direction
                if (o->oTTC2DRotatorRandomDirTimer == 0) {
                    if (random_u16() & 0x03) {
                        o->oTTC2DRotatorIncrement = o->oTTC2DRotatorSpeed;
                        o->oTTC2DRotatorRandomDirTimer = random_mod_offset(90, 60, 4);
                    } else {
                        o->oTTC2DRotatorIncrement = -o->oTTC2DRotatorSpeed;
                        o->oTTC2DRotatorRandomDirTimer = random_mod_offset(30, 30, 3);
                    }
                }

                o->oTTC2DRotatorMinTimeUntilNextTurn = random_mod_offset(10, 20, 3);
            }
        }
    }

    o->oAngleVelYaw = o->oFaceAngleYaw - startYaw;
    if (o->oBehParams2ndByte == TTC_2D_ROTATOR_BP_HAND) {
        load_object_collision_model();
    }
}
