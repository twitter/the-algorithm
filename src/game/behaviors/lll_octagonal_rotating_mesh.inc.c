// lll_octagonal_rotating_mesh.c.inc

s16 D_8032F860[] = { 2,  30,      0x4000, 0, 1,   220, 900, 30, 1,  30, 0,   -30, 2,
                     30, -0x4000, 0,      1, 220, 900, 30,  1,  30, 0,  -30, 3,   0 };
s16 D_8032F894[] = { 4,  0,      0, 0, 1,   475, 900, 30, 1,  30, 0,   -30, 2,
                     30, 0x8000, 0, 1, 475, 900, 30,  1,  30, 0,  -30, 3,   0 };
s16 *D_8032F8C8[] = { D_8032F860, D_8032F894 };


s32 lll_octagonal_mesh_move(s16 *a0, s32 a1) {
    switch (a0[a1]) {
        case 4:
            o->oMoveAngleYaw = a0[a1 + 2];
            o->oForwardVel = a0[a1 + 3] / 100.0f;
            if (cur_obj_is_mario_on_platform()) {
                a1 += 4;
                o->oTimer = 0;
            }
            break;
        case 2:
            o->oMoveAngleYaw = a0[a1 + 2];
            o->oForwardVel = a0[a1 + 3] / 100.0f;
            if (o->oTimer > a0[a1 + 1]) {
                a1 += 4;
                o->oTimer = 0;
            }
            break;
        case 1:
            approach_f32_signed(&o->oForwardVel, a0[a1 + 2] / 100.0f, a0[a1 + 3] / 100.0f);
            if (o->oTimer > a0[a1 + 1]) {
                a1 += 4;
                o->oTimer = 0;
            }
            break;
        case 3:
            o->oForwardVel = 0.0f;
            a1 = 0;
            break;
    }
    return a1;
}

s32 lll_octagonal_mesh_find_y_offset(s32 *a0, f32 *a1, s32 a2, s32 a3) {
    if (cur_obj_is_mario_on_platform()) {
        if (a0[0] < 0x4000)
            a0[0] += a2;
        else
            a0[0] = 0x4000;
    } else {
        if (a0[0] > 0)
            a0[0] -= a2;
        else
            a0[0] = 0;
    }
    a1[0] = sins(a0[0]) * a3;
    if (a0[0] == 0 || a0[0] == 0x4000)
        return 1;
    else
        return 0;
}

void bhv_lll_moving_octagonal_mesh_platform_loop(void) {
    if (o->oAction == 0) {
        o->oHorizontalMovementUnkF8 = 0;
        o->oAction++;
    } else
        o->oHorizontalMovementUnkF8 =
            lll_octagonal_mesh_move(D_8032F8C8[o->oBehParams2ndByte], o->oHorizontalMovementUnkF8);
    print_debug_top_down_objectinfo("number %d\n", o->oHorizontalMovementUnkF8);
    cur_obj_move_using_fvel_and_gravity();
    if (lll_octagonal_mesh_find_y_offset(&o->oHorizontalMovementUnk104, &o->oHorizontalMovementUnk108, 0x400, -80)) {
        o->oHorizontalMovementUnkF4 += 0x800;
        o->oHorizontalMovementUnk100 -= sins(o->oHorizontalMovementUnkF4) * 2;
    }
    o->oPosY = o->oHorizontalMovementUnk100 + o->oHomeY + o->oHorizontalMovementUnk108;
}
