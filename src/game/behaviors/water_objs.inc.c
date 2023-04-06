// water_objs.c.inc
// TODO: Better name, please

void bhv_water_air_bubble_init(void) {
    cur_obj_scale(4.0f);
}

// Fields 0xF4 & 0xF8 seem to be angles for bubble and cannon

void bhv_water_air_bubble_loop(void) {
    s32 i;
    o->header.gfx.scale[0] = sins(o->oWaterObjUnkF4) * 0.5 + 4.0;
    o->header.gfx.scale[1] = -sins(o->oWaterObjUnkF4) * 0.5 + 4.0;
    o->oWaterObjUnkF4 += 0x400;
    if (o->oTimer < 30) {
        cur_obj_become_intangible();
        o->oPosY += 3.0f;
    } else {
        cur_obj_become_tangible();
        cur_obj_forward_vel_approach_upward(2.0f, 10.0f);
        o->oMoveAngleYaw = obj_angle_to_object(o, gMarioObject);
        cur_obj_move_using_fvel_and_gravity();
    }
    o->oPosX += random_float() * 4.0f - 2.0f;
    o->oPosZ += random_float() * 4.0f - 2.0f;
    if (o->oInteractStatus & INT_STATUS_INTERACTED || o->oTimer > 200) {
        cur_obj_play_sound_2(SOUND_GENERAL_QUIET_BUBBLE);
        obj_mark_for_deletion(o);
        for (i = 0; i < 30; i++)
            spawn_object(o, MODEL_BUBBLE, bhvBubbleMaybe);
    }
    if (find_water_level(o->oPosX, o->oPosZ) < o->oPosY)
        obj_mark_for_deletion(o);
    o->oInteractStatus = 0;
}

void bhv_bubble_wave_init(void) {
    o->oWaterObjUnkFC  = 0x800 + (s32)(random_float() * 2048.0f);
    o->oWaterObjUnk100 = 0x800 + (s32)(random_float() * 2048.0f);
    cur_obj_play_sound_2(SOUND_GENERAL_QUIET_BUBBLE);
}

void scale_bubble_random(void) {
    cur_obj_scale(random_float() + 1.0);
}

void bhv_bubble_maybe_loop(void) {
    o->oPosY += random_float() * 3.0f + 6.0f;
    o->oPosX += random_float() * 10.0f - 5.0f;
    o->oPosZ += random_float() * 10.0f - 5.0f;
    o->header.gfx.scale[0] = sins(o->oWaterObjUnkF4) * 0.2 + 1.0;
    o->oWaterObjUnkF4 += o->oWaterObjUnkFC;
    o->header.gfx.scale[1] = sins(o->oWaterObjUnkF8) * 0.2 + 1.0;
    o->oWaterObjUnkF8 += o->oWaterObjUnk100;
}

void bhv_small_water_wave_loop(void) {
    f32 sp1C = find_water_level(o->oPosX, o->oPosZ);
    o->header.gfx.scale[0] = sins(o->oWaterObjUnkF4) * 0.2 + 1.0;
    o->oWaterObjUnkF4 += o->oWaterObjUnkFC;
    o->header.gfx.scale[1] = sins(o->oWaterObjUnkF8) * 0.2 + 1.0;
    o->oWaterObjUnkF8 += o->oWaterObjUnk100;
    if (o->oPosY > sp1C) {
        o->activeFlags = 0;
        o->oPosY += 5.0f;
        if (gFreeObjectList.next != NULL)
            spawn_object(o, MODEL_SMALL_WATER_SPLASH, bhvObjectWaterSplash);
    }
    if (o->oInteractStatus & INT_STATUS_INTERACTED)
        obj_mark_for_deletion(o);
}

void scale_bubble_sin(void) {
    o->header.gfx.scale[0] = sins(o->oWaterObjUnkF4) * 0.5 + 2.0;
    o->oWaterObjUnkF4 += o->oWaterObjUnkFC;
    o->header.gfx.scale[1] = sins(o->oWaterObjUnkF8) * 0.5 + 2.0;
    o->oWaterObjUnkF8 += o->oWaterObjUnk100;
}

void bhv_particle_init(void) {
    obj_scale_xyz(o, 2.0f, 2.0f, 1.0f);
    o->oWaterObjUnkFC = 0x800 + (s32)(random_float() * 2048.0f);
    o->oWaterObjUnk100 = 0x800 + (s32)(random_float() * 2048.0f);
    obj_translate_xyz_random(o, 100.0f);
}

void bhv_particle_loop() {
    f32 sp24 = find_water_level(o->oPosX, o->oPosZ);
    o->oPosY += 5.0f;
    obj_translate_xz_random(o, 4.0f);
    scale_bubble_sin();
    if (o->oPosY > sp24 && o->oTimer) {
        obj_mark_for_deletion(o);
        try_to_spawn_object(5, 0, o, MODEL_SMALL_WATER_SPLASH, bhvObjectWaterSplash);
    }
}

void bhv_small_bubbles_loop(void) {
    o->oPosY += 5.0f;
    obj_translate_xz_random(o, 4.0f);
    scale_bubble_sin();
}

void bhv_fish_group_loop(void) {
    if (gMarioCurrentRoom == 15 || gMarioCurrentRoom == 7)
        if (gGlobalTimer & 1)
            spawn_object(o, MODEL_WHITE_PARTICLE_SMALL, bhvSmallParticleBubbles);
}

void bhv_water_waves_init(void) {
    s32 sp1C;
    for (sp1C = 0; sp1C < 3; sp1C++)
        spawn_object(o, MODEL_WHITE_PARTICLE_SMALL, bhvSmallParticle);
}
