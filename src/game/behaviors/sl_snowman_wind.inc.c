// sl_snowman_wind.c.inc

void bhv_sl_snowman_wind_loop(void) {
    UNUSED s32 unusedVar = 0;
    s16 marioAngleFromWindSource;
    Vec3f tempPos;
    
    if (o->oTimer == 0)
        o->oSLSnowmanWindOriginalYaw = o->oMoveAngleYaw;
    
    // Waiting for Mario to approach.
    if (o->oSubAction == SL_SNOWMAN_WIND_ACT_IDLE) {
        o->oDistanceToMario = 0;
        
        // Check if Mario is within 1000 units of the center of the bridge, and ready to speak.
        vec3f_copy_2(tempPos, &o->oPosX);
        obj_set_pos(o, 1100, 3328, 1164); // Position is in the middle of the ice bridge
        if (cur_obj_can_mario_activate_textbox(1000.0f, 30.0f, 0x7FFF))
            o->oSubAction++;
        vec3f_copy_2(&o->oPosX, tempPos);
        
    // Mario has come close, begin dialog.
    } else if (o->oSubAction == SL_SNOWMAN_WIND_ACT_TALKING) {
        if (cur_obj_update_dialog(2, 2, DIALOG_153, 0))
            o->oSubAction++;
        
    // Blowing, spawn wind particles (SL_SNOWMAN_WIND_ACT_BLOWING)
    } else if (o->oDistanceToMario < 1500.0f && absf(gMarioObject->oPosY - o->oHomeY) < 500.0f) {
        // Point towards Mario, but only within 0x1500 angle units of the original angle.
        if ((marioAngleFromWindSource = o->oAngleToMario - o->oSLSnowmanWindOriginalYaw) > 0) {
            if (marioAngleFromWindSource < 0x1500)
                o->oMoveAngleYaw = o->oAngleToMario;
            else
                o->oMoveAngleYaw = o->oSLSnowmanWindOriginalYaw + 0x1500;
        } else {
            if (marioAngleFromWindSource > -0x1500)
                o->oMoveAngleYaw = o->oAngleToMario;
            else
                o->oMoveAngleYaw = o->oSLSnowmanWindOriginalYaw - 0x1500;
        }
        // Spawn wind and play wind sound
        cur_obj_spawn_strong_wind_particles(12, 3.0f, 0, 0, 0);
        cur_obj_play_sound_1(SOUND_AIR_BLOW_WIND);
    }
}
