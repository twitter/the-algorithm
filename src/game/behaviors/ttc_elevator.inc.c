
/**
 * Behavior for bhvTTCElevator.
 */

/**
 * The speed on each setting.
 */
static s8 sTTCElevatorSpeeds[] = {
    /* TTC_SPEED_SLOW    */ 6,
    /* TTC_SPEED_FAST    */ 10,
    /* TTC_SPEED_RANDOM  */ 6,
    /* TTC_SPEED_STOPPED */ 0,
};

/**
 * Init function for bhvTTCElevator.
 */
void bhv_ttc_elevator_init(void) {
    // If behParam is nonzero, then move 100 * behParam units. Otherwise default
    // to 500
    f32 peakOffset =
        ((o->oBehParams >> 16) & 0xFFFF) != 0 ? 100.0f * ((o->oBehParams >> 16) & 0xFFFF) : 500.0f;

    o->oTTCElevatorPeakY = o->oPosY + peakOffset;
}

/**
 * Update function for bhvTTCElevator.
 */
void bhv_ttc_elevator_update(void) {
    o->oVelY = sTTCElevatorSpeeds[gTTCSpeedSetting] * o->oTTCElevatorDir;

    if (gTTCSpeedSetting == TTC_SPEED_RANDOM) {
        // Occasionally stop for 5 frames then change direction
        if (o->oTimer > o->oTTCElevatorMoveTime) {
            o->oTTCElevatorDir = random_sign();
            o->oTTCElevatorMoveTime = random_mod_offset(30, 30, 6);
            o->oTimer = 0;
        } else if (o->oTimer < 5) {
            o->oVelY = 0.0f;
        }
    }

    // This is basically equivalent to posY += velY
    cur_obj_move_using_fvel_and_gravity();

    // Flip directions if the elevator tries to run away
    if (clamp_f32(&o->oPosY, o->oHomeY, o->oTTCElevatorPeakY)) {
        o->oTTCElevatorDir = -o->oTTCElevatorDir;
    }
}
