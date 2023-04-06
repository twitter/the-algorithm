/**
 * Behavior for bhvBbhTiltingTrapPlatform.
 * This is the tilting platform trap in the upper floor of BBH
 * that drops the player into the merry-go-round area.
 */

/**
 * Update function for bhvBbhTiltingTrapPlatform.
 */
void bhv_bbh_tilting_trap_platform_loop(void) {
    UNUSED s32 unused;

    // US (and probably later) versions use oAction for the
    // if statement, while immediately setting it over here.
    // This was done so that Mario leaving or getting on the platform
    // resets oTimer to 0.
#ifndef VERSION_JP
    if (gMarioObject->platform == o) {
        o->oAction = BBH_TILTING_TRAP_PLATFORM_ACT_MARIO_ON;
    } else {
        o->oAction = BBH_TILTING_TRAP_PLATFORM_ACT_MARIO_OFF;
    }

    if (o->oAction == BBH_TILTING_TRAP_PLATFORM_ACT_MARIO_ON) {
#else
    if (gMarioObject->platform == o) {
#endif
        o->oAngleVelPitch = (s32)(o->oDistanceToMario * coss(o->oAngleToMario));
        o->oFaceAnglePitch += o->oAngleVelPitch;
    } else
#ifndef VERSION_JP
        // In the US version, if the platform has tilted more than 3000 angle units
        // in less than 16 frames since Mario got on it, and he has stepped off,
        // this code will not run, so it will continue to rotate with the same
        // angular velocity for 16 more frames. This was probably done to make
        // the platform more dangerous. This code will not work correctly
        // without the oAction changes above, since oTimer will not ever
        // reset to 0 without them.
        if ((absi(o->oFaceAnglePitch) < 3000) || (o->oTimer >= 16))
#endif
    {
        // Make the platform return to the horizontal at a speed of
        // 200 angle units/frame, and clamp it to 0 if it's within 200 units of 0.
        o->oAngleVelPitch = 0;

        if ((s16) o->oFaceAnglePitch > 0) {
            if (o->oFaceAnglePitch < 200) {
                o->oFaceAnglePitch = 0;
            } else {
                o->oAngleVelPitch = -200;
            }
        } else {
            if (o->oFaceAnglePitch > -200) {
                o->oFaceAnglePitch = 0;
            } else {
                o->oAngleVelPitch = 200;
            }
        }
    }

    // Update angle
    o->oFaceAnglePitch += o->oAngleVelPitch;
}
