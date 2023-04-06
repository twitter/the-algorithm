// lll_hexagonal_ring.c.inc

void hexagonal_ring_spawn_flames(void) {
    struct Object *sp1C;
    f32 size;
    sp1C = spawn_object(o, MODEL_RED_FLAME, bhvVolcanoFlames);
    sp1C->oPosY += 550.0f;
    sp1C->oMoveAngleYaw = random_u16() << 0x10 >> 0x10;
    sp1C->oForwardVel = random_float() * 40.0f + 20.0f;
    sp1C->oVelY = random_float() * 50.0f + 10.0f;
    size = random_float() * 6.0 + 3.0;
    obj_scale_xyz(sp1C, size, size, size);
    if (random_float() < 0.1)
        cur_obj_play_sound_2(SOUND_GENERAL_VOLCANO_EXPLOSION);
}

void bhv_lll_rotating_hexagonal_ring_loop(void) {
    UNUSED s32 unused;
    o->oCollisionDistance = 4000.0f;
    o->oDrawingDistance = 8000.0f;
    switch (o->oAction) {
        case 0:
            if (gMarioObject->platform == o)
                o->oAction++;
            o->oAngleVelYaw = 0x100;
            break;
        case 1:
            o->oAngleVelYaw = 256.0f - sins(o->oTimer << 7) * 256.0f;
            if (o->oTimer > 128)
                o->oAction++;
            break;
        case 2:
            if (gMarioObject->platform != o)
                o->oAction++;
            if (o->oTimer > 128)
                o->oAction++;
            o->oAngleVelYaw = 0;
            hexagonal_ring_spawn_flames();
            break;
        case 3:
            o->oAngleVelYaw = sins(o->oTimer << 7) * 256.0f;
            if (o->oTimer > 128)
                o->oAction = 0;
            break;
        case 4:
            o->oAction = 0;
            break;
    }
    o->oAngleVelYaw = -o->oAngleVelYaw;
    o->oMoveAngleYaw += o->oAngleVelYaw;
}
