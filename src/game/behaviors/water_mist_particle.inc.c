// water_mist_particle.inc.c
// TODO: Is this really "mist"?

void bhv_water_mist_spawn_loop(void) {
    clear_particle_flags(ACTIVE_PARTICLE_BREATH);
    spawn_object(o, MODEL_MIST, bhvWaterMist);
}

void bhv_water_mist_loop(void) {
    f32 scale;

    if (o->oTimer == 0) {
        o->oMoveAngleYaw = gMarioObject->oMoveAngleYaw;
        obj_translate_xz_random(o, 10.0f);
    }

    cur_obj_move_using_fvel_and_gravity();
    o->oOpacity -= 42;
    scale = (254 - o->oOpacity) / 254.0 * 1.0 + 0.5; // seen this before
    cur_obj_scale(scale);

    if (o->oOpacity < 2) {
        obj_mark_for_deletion(o);
    }
}
