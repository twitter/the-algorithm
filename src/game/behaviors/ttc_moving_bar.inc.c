
/**
 * Behavior for bhvTTCMovingBar.
 */

/**
 * The delay before each cycle on each setting. On random setting, this is
 * overwritten with one of the below delays after the first cycle.
 */
static s16 sTTCMovingBarDelays[] = {
    /* TTC_SPEED_SLOW    */ 55,
    /* TTC_SPEED_FAST    */ 30,
    /* TTC_SPEED_RANDOM  */ 55,
    /* TTC_SPEED_STOPPED */ 0,
};

/**
 * The possible delays before each cycle while on random setting.
 */
static s8 sTTCMovingBarRandomDelays[] = { 1, 12, 55, 100 };

/**
 * Init function for bhvTTCMovingBar.
 */
void bhv_ttc_moving_bar_init(void) {
    // If on still setting, then stick out
    if ((o->oTTCMovingBarDelay = sTTCMovingBarDelays[gTTCSpeedSetting]) == 0) {
        o->oTTCMovingBarOffset = 250.0f;
    }

    // This causes the first cycle to start at different times for different
    // bars
    o->oTTCMovingBarStoppedTimer = 10 * o->oBehParams2ndByte;

    o->oMoveAngleYaw = 0x4000 - o->oMoveAngleYaw;
}

/**
 * Wait a delay if necessary, then begin pulling back.
 */
static void ttc_moving_bar_act_wait(void) {
    if (o->oTTCMovingBarDelay != 0 && o->oTimer > o->oTTCMovingBarDelay) {
        // This is zero except on the first cycle, and is used to desync the
        // bars from each other at the very beginning
        if (o->oTTCMovingBarStoppedTimer != 0) {
            o->oTTCMovingBarStoppedTimer -= 1;
        } else {
            if (gTTCSpeedSetting == TTC_SPEED_RANDOM) {
                // Set the delay for the next cycle
                o->oTTCMovingBarDelay = sTTCMovingBarRandomDelays[random_u16() & 0x3];

                // With 50% probability, pause after pulling back
                if (random_u16() % 2 == 0) {
                    o->oTTCMovingBarStoppedTimer = random_linear_offset(20, 100);
                }
            }

            // Begin pulling back
            o->oAction = TTC_MOVING_BAR_ACT_PULL_BACK;
            o->oTTCMovingBarSpeed = -8.0f;
        }
    }
}

/**
 * Pull back, possibly stop for a bit, then begin extending.
 */
static void ttc_moving_bar_act_pull_back(void) {
    // Started with -8 speed, accelerate back to > 0
    if ((o->oTTCMovingBarSpeed += 0.73f) > 0.0f) {
        // Possibly pause after pulling back
        if (o->oTTCMovingBarStoppedTimer != 0) {
            o->oTTCMovingBarStoppedTimer -= 1;
            o->oTTCMovingBarSpeed = 0.0f;
        } else {
            // Begin extending
            o->oAction = TTC_MOVING_BAR_ACT_EXTEND;
            o->oTTCMovingBarSpeed = 29.0f;
        }
    }
}

/**
 * Reset and enter the wait action.
 */
static void ttc_moving_bar_reset(void) {
    o->oTTCMovingBarOffset = o->oTTCMovingBarSpeed = 0.0f;
    o->oAction = TTC_MOVING_BAR_ACT_WAIT;
}

/**
 * With 25% probability on random setting, stop at 0 and reset.
 * Otherwise, launch outward, oscillating and slowing down around 250, then
 * begin retracting.
 */
static void ttc_moving_bar_act_extend(void) {
    // If we passed the 250 threshold and we have decelerated enough
    if ((o->oTTCMovingBarOffset == 250.0f
         || (250.0f - o->oTTCMovingBarOffset) * (250.0f - o->oTTCMovingBarStartOffset) < 0.0f)
        && o->oTTCMovingBarSpeed > -8.0f && o->oTTCMovingBarSpeed < 8.0f) {
        // Begin retracting
        o->oAction = TTC_MOVING_BAR_ACT_RETRACT;
        o->oTTCMovingBarSpeed = 0.0f;
    } else {
        f32 accel;

        // Accelerate before reaching 250, and decelerate after reaching it
        if (o->oTTCMovingBarOffset < 250.0f) {
            accel = 6.4f;
        } else {
            accel = -6.4f;
        }

        // Strengthen deceleration
        if (o->oTTCMovingBarSpeed * accel < 0.0f) {
            accel *= 2.35f;
        }

        o->oTTCMovingBarSpeed += accel;

        // When we pass neutral on random setting, then stop immediately with
        // 25% probability (fake out)
        if (gTTCSpeedSetting == TTC_SPEED_RANDOM
            && o->oTTCMovingBarOffset * o->oTTCMovingBarStartOffset < 0.0f && random_u16() % 4 == 0) {
            ttc_moving_bar_reset();
        }
    }
}

/**
 * Wait a second, then retract back into the wall.
 */
static void ttc_moving_bar_act_retract(void) {
    // Wait a second
    if (o->oTimer > 30) {
        // Retract
        o->oTTCMovingBarSpeed = -5.0f;
        if (o->oTTCMovingBarOffset < 0.0f) {
            ttc_moving_bar_reset();
        }
    }
}

/**
 * Update function for bhvTTCMovingBar.
 */
void bhv_ttc_moving_bar_update(void) {
    o->oTTCMovingBarStartOffset = o->oTTCMovingBarOffset;
    obj_perform_position_op(POS_OP_SAVE_POSITION);

    o->oTTCMovingBarOffset += o->oTTCMovingBarSpeed;

    switch (o->oAction) {
        case TTC_MOVING_BAR_ACT_WAIT:
            ttc_moving_bar_act_wait();
            break;
        case TTC_MOVING_BAR_ACT_PULL_BACK:
            ttc_moving_bar_act_pull_back();
            break;
        case TTC_MOVING_BAR_ACT_EXTEND:
            ttc_moving_bar_act_extend();
            break;
        case TTC_MOVING_BAR_ACT_RETRACT:
            ttc_moving_bar_act_retract();
            break;
    }

    obj_set_dist_from_home(o->oTTCMovingBarOffset);
    obj_perform_position_op(POS_OP_COMPUTE_VELOCITY);
}
