/**
 * Main loop of the hour and minute hands of the Tick Tock Clock painting.
 */
void bhv_rotating_clock_arm_loop(void) {
    struct Surface *marioSurface;
    u16 rollAngle = o->oFaceAngleRoll;
    o->oFloorHeight =
        find_floor(gMarioObject->oPosX, gMarioObject->oPosY, gMarioObject->oPosZ, &marioSurface);

    // Seems to make sure Mario is on a default surface & 4 frames pass before
    //   allowing him to change the Tick Tock Clock speed setting.
    // Probably a safety check for when you leave the level through the painting
    //   to make sure the setting isn't accidentally locked in as you fly out.
    if (o->oAction == 0) {
        if (marioSurface->type == SURFACE_DEFAULT)
            if (o->oTimer >= 4)
                o->oAction++;
    } else if (o->oAction == 1) {
        // If Mario is touching the Tick Tock Clock painting...
        if (marioSurface != NULL
            && (marioSurface->type == SURFACE_TTC_PAINTING_1
                || marioSurface->type == SURFACE_TTC_PAINTING_2
                || marioSurface->type == SURFACE_TTC_PAINTING_3)) {
            // And this is the minute hand...
            if (cur_obj_has_behavior(bhvClockMinuteHand)) {
                // Set Tick Tick Clock's speed based on the angle of the hand.
                // The angle actually counting down from 0xFFFF to 0 so
                //   11 o'clock is a small value and 1 o'clock is a large value.
                if (rollAngle < 0xAAA) // > 345 degrees from 12 o'clock.
                    gTTCSpeedSetting = TTC_SPEED_STOPPED;
                else if (rollAngle < 0x6aa4) // 210..345 degrees from 12 o'clock.
                    gTTCSpeedSetting = TTC_SPEED_FAST;
                else if (rollAngle < 0x954C) // 150..210 degrees from 12 o'clock.
                    gTTCSpeedSetting = TTC_SPEED_RANDOM;
                else if (rollAngle < 0xf546) // 15..150 degrees from 12 o'clock.
                    gTTCSpeedSetting = TTC_SPEED_SLOW;
                else // < 15 degrees from 12 o'clock.
                    gTTCSpeedSetting = TTC_SPEED_STOPPED;
            }

            // Increment the action to stop animating the hands.
            o->oAction++;
        } else {
        }
    }

    // Only rotate the hands until Mario enters the painting.
    if (o->oAction < 2)
        cur_obj_rotate_face_angle_using_vel();
}
