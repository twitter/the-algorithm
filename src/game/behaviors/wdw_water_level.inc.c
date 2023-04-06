// wdw_water_level.c.inc

// called when WDW is loaded.
void bhv_init_changing_water_level_loop(void) {
    if (gCurrentObject->oAction == 0) {
        if (gEnvironmentRegions != NULL)
            gCurrentObject->oAction++;
    } else if (gCurrentObject->oTimer < 10)
        *gEnvironmentLevels = gEnvironmentRegions[6];
    else {
        gEnvironmentRegions[6] = *gEnvironmentLevels + sins(o->oWaterLevelTriggerUnkF4) * 20.0f;
        gCurrentObject->oWaterLevelTriggerUnkF4 += 0x200;
    }
}

void bhv_water_level_diamond_loop(void) {
    if (gEnvironmentRegions != NULL) {
        switch (o->oAction) {
            case WATER_LEVEL_DIAMOND_ACT_INIT:
                o->oFaceAngleYaw = 0;
                o->oWaterLevelTriggerTargetWaterLevel = (s32) o->oPosY;
                if (o->oTimer > 10)
                    o->oAction++; // Sets to WATER_LEVEL_DIAMOND_ACT_IDLE
                break;
            case WATER_LEVEL_DIAMOND_ACT_IDLE:
                if (obj_check_if_collided_with_object(o, gMarioObject)) {
                    if (gWDWWaterLevelChanging == 0) {
                        o->oAction++; // Sets to WATER_LEVEL_DIAMOND_ACT_CHANGE_WATER_LEVEL
                        gWDWWaterLevelChanging = 1;
                    }
                }
                break;
            case WATER_LEVEL_DIAMOND_ACT_CHANGE_WATER_LEVEL:
                o->oAngleVelYaw = 0;
                *gEnvironmentLevels = (s32) approach_f32_symmetric(
                    (f32) *gEnvironmentLevels, (f32) o->oWaterLevelTriggerTargetWaterLevel, 10.0f);
                if (*gEnvironmentLevels == o->oWaterLevelTriggerTargetWaterLevel) {
                    if ((s16) o->oFaceAngleYaw == 0)
                        o->oAction++; // Sets to WATER_LEVEL_DIAMOND_ACT_IDLE_SPINNING
                    else
                        o->oAngleVelYaw = 0x800;
                } else {
                    if (o->oTimer == 0)
                        cur_obj_play_sound_2(SOUND_GENERAL_WATER_LEVEL_TRIG);
                    else {
                        if (*gEnvironmentLevels > o->oWaterLevelTriggerTargetWaterLevel)
                            cur_obj_play_sound_1(SOUND_ENV_WATER_DRAIN);
                        else
                            cur_obj_play_sound_1(SOUND_ENV_WATER_DRAIN); // same as above
                    }
                    o->oAngleVelYaw = 0x800;
#ifdef VERSION_SH
                    reset_rumble_timers_2(2);
#endif
                }
                break;
            case WATER_LEVEL_DIAMOND_ACT_IDLE_SPINNING:
                if (!obj_check_if_collided_with_object(o, gMarioObject)) {
                    gWDWWaterLevelChanging = 0;
                    o->oAction = WATER_LEVEL_DIAMOND_ACT_IDLE;
                    o->oAngleVelYaw = 0;
                }
                break;
        }
        o->oFaceAngleYaw += o->oAngleVelYaw;
    }
}
