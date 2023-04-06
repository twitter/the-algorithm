// sl_walking_penguin.c.inc

struct SLWalkingPenguinStep {
    s32 stepLength;
    s32 anim;
    f32 speed;
    f32 animSpeed;
};

// The penguin follows a preset list of movements while walking forward.
struct SLWalkingPenguinStep sSLWalkingPenguinErraticSteps[] = {
    { 60, PENGUIN_ANIM_WALK, 6.0f,  1.0f }, // Walk forwards for 2 seconds
    { 30, PENGUIN_ANIM_IDLE, 0.0f,  1.0f }, // Stop for 1 second
    { 30, PENGUIN_ANIM_WALK, 12.0f, 2.0f }, // Walk forwards quickly for 1 second
    { 30, PENGUIN_ANIM_IDLE, 0.0f,  1.0f }, // Stop for 1 second
    { 30, PENGUIN_ANIM_WALK, -6.0f, 1.0f }, // Walk backwards for 1 second
    { 30, PENGUIN_ANIM_IDLE, 0.0f,  1.0f }, // Stop for 1 second
    { -1, 0, 0.0f,  0.0f } }; // Repeat


static s32 sl_walking_penguin_turn(void) {
    // Stay still and use walking animation for the turn.
    o->oForwardVel = 0.0f;
    cur_obj_init_animation_with_accel_and_sound(PENGUIN_ANIM_WALK, 1.0f);
    
    // Turn around.
    o->oAngleVelYaw = 0x400;
    o->oMoveAngleYaw += o->oAngleVelYaw;
    
    if (o->oTimer == 31)
        return TRUE; // Finished turning
    else
        return FALSE;
}

void bhv_sl_walking_penguin_loop(void) {
    f32 adjustedXPos, adjustedZPos;
    f32 perpendicularOffset = 100.0f;
    
    o->oAngleVelYaw = 0;
    cur_obj_update_floor_and_walls();
    
    switch (o->oAction) {
        // Walk erratically across the ice bridge using preset steps.
        case SL_WALKING_PENGUIN_ACT_MOVING_FORWARDS:
            if (o->oTimer == 0) {
                // Initiate variables
                o->oSLWalkingPenguinCurStep = 0;
                o->oSLWalkingPenguinCurStepTimer = 0;
            }
            
            if (o->oSLWalkingPenguinCurStepTimer < sSLWalkingPenguinErraticSteps[o->oSLWalkingPenguinCurStep].stepLength)
                o->oSLWalkingPenguinCurStepTimer++;
            else {
                // Move to next step
                o->oSLWalkingPenguinCurStepTimer = 0;
                o->oSLWalkingPenguinCurStep++;
                if (sSLWalkingPenguinErraticSteps[o->oSLWalkingPenguinCurStep].stepLength < 0)
                    // Reached the end of the list, go back to the start
                    o->oSLWalkingPenguinCurStep = 0;
            }
            
            if (o->oPosX < 300.0f)
                o->oAction++; // If reached the end of the bridge, turn around and head back.
            else {
                // Move and animate the penguin
                o->oForwardVel = sSLWalkingPenguinErraticSteps[o->oSLWalkingPenguinCurStep].speed;
                
                cur_obj_init_animation_with_accel_and_sound(
                    sSLWalkingPenguinErraticSteps[o->oSLWalkingPenguinCurStep].anim,
                    sSLWalkingPenguinErraticSteps[o->oSLWalkingPenguinCurStep].animSpeed
                );
            }
            break;
            
        // At the end, turn around and prepare to head back across the bridge.
        case SL_WALKING_PENGUIN_ACT_TURNING_BACK:
            if (sl_walking_penguin_turn())
                o->oAction++; // Finished turning
            break;
            
        // Walk back across the bridge at a constant speed.
        case SL_WALKING_PENGUIN_ACT_RETURNING:
            // Move and animate the penguin
            o->oForwardVel = 12.0f;
            cur_obj_init_animation_with_accel_and_sound(PENGUIN_ANIM_WALK, 2.0f);
            
            if (o->oPosX > 1700.0f)
                o->oAction++; // If reached the start of the bridge, turn around.
            break;
            
        // At the start, turn around and prepare to walk erratically across the bridge.
        case SL_WALKING_PENGUIN_ACT_TURNING_FORWARDS:
            if (sl_walking_penguin_turn())
                o->oAction = SL_WALKING_PENGUIN_ACT_MOVING_FORWARDS; // Finished turning
            break;
    }
    
    cur_obj_move_standard(-78);
    if (!cur_obj_hide_if_mario_far_away_y(1000.0f))
        play_penguin_walking_sound(PENGUIN_WALK_BIG);
    
    // Adjust the position to get a point better lined up with the visual model, for stopping the wind.
    // The new point is 60 units behind the penguin and 100 units perpedicularly, away from the snowman.
    
    adjustedXPos = o->oPosX + sins(0xDBB0) * 60.0f; // 0xDBB0 = -51 degrees, the angle the penguin is facing
    adjustedZPos = o->oPosZ + coss(0xDBB0) * 60.0f;
    adjustedXPos += perpendicularOffset * sins(0x1BB0); // 0x1BB0 = 39 degrees, perpendicular to the penguin
    adjustedZPos += perpendicularOffset * coss(0x1BB0);
    o->oSLWalkingPenguinWindCollisionXPos = adjustedXPos;
    o->oSLWalkingPenguinWindCollisionZPos = adjustedZPos;
    
    print_debug_bottom_up("x %d", o->oPosX);
    print_debug_bottom_up("z %d", o->oPosZ);
}
