// boulder.c.inc

void bhv_big_boulder_init(void) {
    o->oHomeX = o->oPosX;
    o->oHomeY = o->oPosY;
    o->oHomeZ = o->oPosZ;

    o->oGravity = 8.0f;
    o->oFriction = 0.999f;
    o->oBuoyancy = 2.0f;
}

void boulder_act_1(void) {
    s16 sp1E;

    sp1E = object_step_without_floor_orient();
    if ((sp1E & 0x09) == 0x01 && o->oVelY > 10.0f) {
        cur_obj_play_sound_2(SOUND_GENERAL_GRINDEL_ROLL);
        spawn_mist_particles();
    }

    if (o->oForwardVel > 70.0)
        o->oForwardVel = 70.0f;

    if (o->oPosY < -1000.0f)
        o->activeFlags = 0;
}

void bhv_big_boulder_loop(void) {
    cur_obj_scale(1.5f);
    o->oGraphYOffset = 270.0f;
    switch (o->oAction) {
        case 0:
            o->oForwardVel = 40.0f;
            o->oAction = 1;
            break;

        case 1:
            boulder_act_1();
            adjust_rolling_face_pitch(1.5f);
            cur_obj_play_sound_1(SOUND_ENV_UNKNOWN2);
            break;
    }

    set_rolling_sphere_hitbox();
}

void bhv_big_boulder_generator_loop(void) {
    struct Object *sp1C;
    if (o->oTimer >= 256) {
        o->oTimer = 0;
    }

    if (!current_mario_room_check(4) || is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 1500))
        return;

    if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 6000)) {
        if ((o->oTimer & 0x3F) == 0) {
            sp1C = spawn_object(o, MODEL_HMC_ROLLING_ROCK, bhvBigBoulder);
            sp1C->oMoveAngleYaw = random_float() * 4096.0f;
        }
    } else {
        if ((o->oTimer & 0x7F) == 0) {
            sp1C = spawn_object(o, MODEL_HMC_ROLLING_ROCK, bhvBigBoulder);
            sp1C->oMoveAngleYaw = random_float() * 4096.0f;
        }
    }
}
