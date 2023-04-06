// checkerboard_platform.c.inc

struct Struct8032F754 D_8032F754[] = { { 145, { 0.7f, 1.5f, 0.7f }, 7.0f },
                                       { 235, { 1.2f, 2.0f, 1.2f }, 11.6f } };

void bhv_checkerboard_elevator_group_init(void) {
    s32 sp3C;
    s32 sp38;
    s32 sp34;
    s32 i;
    struct Object *sp2C;
    if (o->oBehParams2ndByte == 0)
        o->oBehParams2ndByte = 65;
    sp3C = o->oBehParams2ndByte * 10;
    sp34 = (o->oBehParams >> 24) & 0XFF;
    for (i = 0; i < 2; i++) {
        if (i == 0)
            sp38 = -D_8032F754[sp34].unk0;
        else
            sp38 = D_8032F754[sp34].unk0;

        sp2C = spawn_object_relative(i, 0, i * sp3C, sp38, o, MODEL_CHECKERBOARD_PLATFORM,
                                     bhvCheckerboardPlatformSub);
        sp2C->oCheckerBoardPlatformUnk1AC = D_8032F754[sp34].unk2;
        vec3f_copy_2(sp2C->header.gfx.scale, D_8032F754[sp34].unk1);
    }
}

void checkerboard_plat_act_move_y(UNUSED s32 unused, f32 vel, s32 a2) {
    o->oMoveAnglePitch = 0;
    o->oAngleVelPitch = 0;
    o->oForwardVel = 0.0f;
    o->oVelY = vel;
    if (o->oTimer > a2)
        o->oAction++;
}

void checkerboard_plat_act_rotate(s32 a0, s16 a1) {
    o->oVelY = 0.0f;
    o->oAngleVelPitch = a1;
    if (o->oTimer + 1 == 0x8000 / absi(a1))
        o->oAction = a0;
    o->oCheckerBoardPlatformUnkF8 = a0;
}

void bhv_checkerboard_platform_init(void) {
    o->oCheckerBoardPlatformUnkFC = o->parentObj->oBehParams2ndByte;
}

void bhv_checkerboard_platform_loop(void) {
    f32 sp24 = o->oCheckerBoardPlatformUnk1AC;
    o->oCheckerBoardPlatformUnkF8 = 0;
    if (o->oDistanceToMario < 1000.0f)
        cur_obj_play_sound_1(SOUND_ENV_ELEVATOR4);
    switch (o->oAction) {
        case 0:
            if (o->oBehParams2ndByte == 0)
                o->oAction = 1;
            else
                o->oAction = 3;
            break;
        case 1:
            checkerboard_plat_act_move_y(2, 10.0f, o->oCheckerBoardPlatformUnkFC);
            break;
        case 2:
            checkerboard_plat_act_rotate(3, 512);
            break;
        case 3:
            checkerboard_plat_act_move_y(4, -10.0f, o->oCheckerBoardPlatformUnkFC);
            break;
        case 4:
            checkerboard_plat_act_rotate(1, -512);
            break;
    }
    o->oMoveAnglePitch += absi(o->oAngleVelPitch);
    o->oFaceAnglePitch += absi(o->oAngleVelPitch);
    o->oFaceAngleYaw = o->oMoveAngleYaw;
    if (o->oMoveAnglePitch != 0) {
        o->oForwardVel = signum_positive(o->oAngleVelPitch) * sins(o->oMoveAnglePitch) * sp24;
        o->oVelY = signum_positive(o->oAngleVelPitch) * coss(o->oMoveAnglePitch) * sp24;
    }
    if (o->oCheckerBoardPlatformUnkF8 == 1) {
        o->oAngleVelPitch = 0;
        o->oFaceAnglePitch &= ~0x7FFF;
        cur_obj_move_using_fvel_and_gravity();
    } else
        cur_obj_move_using_fvel_and_gravity();
    load_object_collision_model();
}
