// lll_sinking_rectangle.c.inc

void sinking_rectangular_plat_actions(f32 a0, s32 a1) {
    switch (o->oAction) {
        case 0:
            o->oAction++;
            break;
        case 1:
            o->oPosY -= sins(o->oLllWoodPieceUnkF4) * a0;
            o->oLllWoodPieceUnkF4 += a1;
            break;
        case 2:
            break;
            break; // Two breaks needed?
    }
}

void bhv_lll_sinking_rectangular_platform_loop(void) {
    f32 sp1C = 0.4f;
    s32 sp18 = 0x100;
    if (o->oMoveAngleYaw != 0)
        sinking_rectangular_plat_actions(sp1C, sp18);
    else {
        o->oFaceAnglePitch = sins(o->oLllWoodPieceUnkF4) * 512.0f;
        o->oLllWoodPieceUnkF4 += 0x100;
    }
}

void bhv_lll_sinking_square_platforms_loop(void) {
    f32 sp1C = 0.5f;
    s32 sp18 = 0x100;
    sinking_rectangular_plat_actions(sp1C, sp18);
}
