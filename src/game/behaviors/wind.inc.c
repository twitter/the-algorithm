// wind.c.inc

void spawn_wind_particles(s16 pitch, s16 yaw) {
    s32 i;
    for (i = 0; i < 3; i++) {
        struct Object *wind = spawn_object(o, MODEL_MIST, bhvWind);
        wind->oMoveAngleYaw = yaw;
        wind->oMoveAnglePitch = pitch;
    }
}

void bhv_wind_loop(void) {
    s16 sp2E = 500;
    f32 sp28 = 1.0f;
    if (o->oTimer == 0) {
        o->oOpacity = 100;
        if (o->oMoveAnglePitch == 0) {
            obj_translate_xz_random(o, 900.0f);
            o->oPosX += sins(o->oMoveAngleYaw + 0x8000) * sp2E; // NOP as Pitch is 0
            o->oPosY += 80.0f + random_f32_around_zero(200.0f);
            o->oPosZ += coss(o->oMoveAngleYaw + 0x8000) * sp2E; // -coss(a) * sp2E
            o->oMoveAngleYaw += random_f32_around_zero(4000.0f);
            o->oForwardVel = random_float() * 70.0f + 50.0f;
        } else {
            obj_translate_xz_random(o, 600.0f);
            o->oPosY -= sp2E - 200; // 300
            o->oVelY = random_float() * 30.0f + 50.0f;
            o->oMoveAngleYaw = random_u16();
            o->oForwardVel = 10.0f;
        }
        obj_set_billboard(o);
        cur_obj_scale(sp28);
    }
    if (o->oTimer > 8)
        obj_mark_for_deletion(o);
    o->oFaceAnglePitch += 4000.0f + 2000.0f * random_float();
    o->oFaceAngleYaw += 4000.0f + 2000.0f * random_float();
    cur_obj_move_using_fvel_and_gravity();
}
