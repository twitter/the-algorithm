// sliding_platform_2.inc.c

void *D_80331A24[] = {
    bits_seg7_collision_0701A9A0,
    bits_seg7_collision_0701AA0C,
    bitfs_seg7_collision_07015714,
    bitfs_seg7_collision_07015768,
    rr_seg7_collision_070295F8,
    rr_seg7_collision_0702967C,
    NULL,
    bitdw_seg7_collision_0700F688,
};

void bhv_sliding_plat_2_init(void) {
    s32 val04;

    val04 = ((u16)(o->oBehParams >> 16) & 0x0380) >> 7;
    o->collisionData = segmented_to_virtual(D_80331A24[val04]);
    o->oBackAndForthPlatformUnkF8 = 50.0f * ((u16)(o->oBehParams >> 16) & 0x003F);

    if (val04 < 5 || val04 > 6) {
        o->oBackAndForthPlatformUnk100 = 15.0f;
        if ((u16)(o->oBehParams >> 16) & 0x0040) {
            o->oMoveAngleYaw += 0x8000;
        }
    } else {
        o->oBackAndForthPlatformUnk100 = 10.0f;
        if ((u16)(o->oBehParams >> 16) & 0x0040) {
            o->oBackAndForthPlatformUnkF4 = -1.0f;
        } else {
            o->oBackAndForthPlatformUnkF4 = 1.0f;
        }
    }
}

void bhv_sliding_plat_2_loop(void) {
    if (o->oTimer > 10) {
        o->oBackAndForthPlatformUnkFC += o->oBackAndForthPlatformUnk100;
        if (clamp_f32(&o->oBackAndForthPlatformUnkFC, -o->oBackAndForthPlatformUnkF8, 0.0f)) {
            o->oBackAndForthPlatformUnk100 = -o->oBackAndForthPlatformUnk100;
            o->oTimer = 0;
        }
    }

    obj_perform_position_op(0);

    if (o->oBackAndForthPlatformUnkF4 != 0.0f) {
        o->oPosY = o->oHomeY + o->oBackAndForthPlatformUnkFC * o->oBackAndForthPlatformUnkF4;
    } else {
        obj_set_dist_from_home(o->oBackAndForthPlatformUnkFC);
    }

    obj_perform_position_op(1);
}
