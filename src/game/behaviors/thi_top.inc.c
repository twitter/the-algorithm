// thi_top.c.inc

struct SpawnParticlesInfo D_8032F134 = {
    0, 30, MODEL_WHITE_PARTICLE_SMALL, 0, 40, 0, 20, 40, 252, 30, 20.0f, 0.0f
};

UNUSED u8 unused8032F134[] = { 10, 11, 12 };

void bhv_thi_huge_island_top_loop(void) {
    if (gTHIWaterDrained & 1) {
        if (o->oTimer == 0)
            gEnvironmentRegions[18] = 3000;
        cur_obj_hide();
    } else
        load_object_collision_model();
}

void bhv_thi_tiny_island_top_loop(void) {
    if (!(gTHIWaterDrained & 1)) {
        if (o->oAction == 0) {
            if (o->oDistanceToMario < 500.0f)
                if (gMarioStates->action == ACT_GROUND_POUND_LAND) {
                    o->oAction++;
                    cur_obj_spawn_particles(&D_8032F134);
                    spawn_triangle_break_particles(20, 138, 0.3f, 3);
                    cur_obj_play_sound_2(SOUND_GENERAL_ACTIVATE_CAP_SWITCH);
                    cur_obj_hide();
                }
        } else {
            if (o->oTimer < 50) {
                gEnvironmentRegions[18]--;
                cur_obj_play_sound_1(SOUND_ENV_WATER_DRAIN);
            } else {
                gTHIWaterDrained |= 1;
                play_puzzle_jingle();
                o->oAction += 1;
            }
        }
    } else {
        if (o->oTimer == 0)
            gEnvironmentRegions[18] = 700;
        cur_obj_hide();
    }
}
