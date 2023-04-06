// ground_particles.c.inc

void bhv_pound_white_puffs_init(void) {
    clear_particle_flags(0x8000);
    spawn_mist_from_global();
}

void spawn_mist_from_global(void) {
    cur_obj_spawn_particles(&D_8032F3CC);
}

void bhv_ground_sand_init(void) {
    clear_particle_flags(0x4000);
    cur_obj_spawn_particles(&D_8032F3E0);
}

void spawn_smoke_with_velocity(void) {
    struct Object *smoke = spawn_object_with_scale(o, MODEL_SMOKE, bhvWhitePuffSmoke2, 1.0f);
    smoke->oForwardVel = D_8032F3F4[0];
    smoke->oVelY = D_8032F3F4[1];
    smoke->oGravity = D_8032F3F4[2];
    obj_translate_xyz_random(smoke, D_8032F3F4[3]);
}

// TODO Fix name
void clear_particle_flags(u32 flags) {
    o->parentObj->oActiveParticleFlags &= flags ^ -1; // Clear the flags given (could just be ~flags)
}

void bhv_ground_snow_init(void) {
    clear_particle_flags(1 << 16);
    cur_obj_spawn_particles(&D_8032F3FC);
}
