// explosion.c.inc

void bhv_explosion_init(void) {
    create_sound_spawner(SOUND_GENERAL2_BOBOMB_EXPLOSION);
    set_environmental_camera_shake(SHAKE_ENV_EXPLOSION);

    o->oOpacity = 255;
}

void bhv_explosion_loop(void) {
    s32 i;

    if (o->oTimer == 9) {
        if (find_water_level(o->oPosX, o->oPosZ) > o->oPosY) {
            for (i = 0; i < 40; i++)
                spawn_object(o, MODEL_WHITE_PARTICLE_SMALL, bhvBobombExplosionBubble);
        } else
            spawn_object(o, MODEL_SMOKE, bhvBobombBullyDeathSmoke);

        o->activeFlags = 0;
    }

    o->oOpacity -= 14;

    cur_obj_scale((f32) o->oTimer / 9.0f + 1.0);
}
