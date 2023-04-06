// sparkle_spawn_star.c.inc

struct ObjectHitbox sSparkleSpawnStarHitbox = {
    /* interactType: */ INTERACT_STAR_OR_KEY,
    /* downOffset: */ 0,
    /* damageOrCoinValue: */ 0,
    /* health: */ 0,
    /* numLootCoins: */ 0,
    /* radius: */ 80,
    /* height: */ 50,
    /* hurtboxRadius: */ 0,
    /* hurtboxHeight: */ 0,
};

void bhv_spawned_star_init(void) {
    s32 sp24;
    if (!(o->oInteractionSubtype & INT_SUBTYPE_NO_EXIT))
        o->oBehParams = o->parentObj->oBehParams;
    sp24 = (o->oBehParams >> 24) & 0xFF;
    if (bit_shift_left(sp24) & save_file_get_star_flags(gCurrSaveFileNum - 1, gCurrCourseNum - 1))
        cur_obj_set_model(MODEL_TRANSPARENT_STAR);
    cur_obj_play_sound_2(SOUND_GENERAL2_STAR_APPEARS);
}

void set_sparkle_spawn_star_hitbox(void) {
    obj_set_hitbox(o, &sSparkleSpawnStarHitbox);
    if (o->oInteractStatus & INT_STATUS_INTERACTED) {
        mark_obj_for_deletion(o);
        o->oInteractStatus = 0;
    }
}

void set_home_to_mario(void) {
    f32 sp1C;
    f32 sp18;
    o->oHomeX = gMarioObject->oPosX;
    o->oHomeZ = gMarioObject->oPosZ;
    o->oHomeY = gMarioObject->oPosY;
    o->oHomeY += 250.0f;
    o->oPosY = o->oHomeY;
    sp1C = o->oHomeX - o->oPosX;
    sp18 = o->oHomeZ - o->oPosZ;
    o->oForwardVel = sqrtf(sp1C * sp1C + sp18 * sp18) / 23.0f;
}

void set_y_home_to_pos(void) {
    o->oForwardVel = 0;
    o->oHomeY = o->oPosY;
}

void slow_star_rotation(void) {
    if (o->oAngleVelYaw > 0x400)
        o->oAngleVelYaw -= 0x40;
}

void bhv_spawned_star_loop(void) {
    if (o->oAction == 0) {
        if (o->oTimer == 0) {
            cutscene_object(CUTSCENE_STAR_SPAWN, o);
            set_time_stop_flags(TIME_STOP_ENABLED | TIME_STOP_MARIO_AND_DOORS);
            o->activeFlags |= 0x20;
            o->oAngleVelYaw = 0x800;
            if (o->oBehParams2ndByte == 0)
                set_home_to_mario();
            else
                set_y_home_to_pos();
            o->oMoveAngleYaw = cur_obj_angle_to_home();
            o->oVelY = 50.0f;
            o->oGravity = -4.0f;
            spawn_mist_particles();
        }
        cur_obj_play_sound_1(SOUND_ENV_STAR);
        spawn_object(o, MODEL_NONE, bhvSparkleSpawn);
        if (o->oVelY < 0 && o->oPosY < o->oHomeY) {
            o->oAction++;
            o->oForwardVel = 0;
            o->oVelY = 20.0f;
            o->oGravity = -1.0f;
            if (o->oInteractionSubtype & INT_SUBTYPE_NO_EXIT)
#ifdef VERSION_JP
                play_power_star_jingle(FALSE);
#else
                play_power_star_jingle(TRUE);
#endif
            else
                play_power_star_jingle(TRUE);
        }
    } else if (o->oAction == 1) {
        if (o->oVelY < -4.0f)
            o->oVelY = -4.0f;
        if (o->oVelY < 0 && o->oPosY < o->oHomeY) {
            gObjCutsceneDone = TRUE;
            o->oVelY = 0;
            o->oGravity = 0;
            o->oAction++;
        }
        spawn_object(o, MODEL_NONE, bhvSparkleSpawn);
    } else if (o->oAction == 2) {
        if (gCamera->cutscene == 0 && gRecentCutscene == 0) {
            clear_time_stop_flags(TIME_STOP_ENABLED | TIME_STOP_MARIO_AND_DOORS);
            o->activeFlags &= ~0x20;
            o->oAction++;
        }
    } else {
        set_sparkle_spawn_star_hitbox();
        slow_star_rotation();
    }
    cur_obj_move_using_fvel_and_gravity();
    o->oFaceAngleYaw += o->oAngleVelYaw;
    o->oInteractStatus = 0;
}

void bhv_spawn_star_no_level_exit(u32 sp20) {
    struct Object *sp1C = spawn_object(o, MODEL_STAR, bhvSpawnedStarNoLevelExit);
    sp1C->oBehParams = sp20 << 24;
    sp1C->oInteractionSubtype = INT_SUBTYPE_NO_EXIT;
    obj_set_angle(sp1C, 0, 0, 0);
}
