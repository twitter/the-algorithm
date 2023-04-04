// sliding_platform_2.inc.c

static Collision const *sSlidingPlatform2CollisionData[] = {
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
    s32 collisionDataIndex = ((u16)(o->oBehParams >> 16) & 0x0380) >> 7;

    o->collisionData = segmented_to_virtual(sSlidingPlatform2CollisionData[collisionDataIndex]);
    o->oBackAndForthPlatformPathLength = 50.0f * ((u16)(o->oBehParams >> 16) & 0x003F);

    if (collisionDataIndex < 5 || collisionDataIndex > 6) {
        o->oBackAndForthPlatformVel = 15.0f;
        if ((u16)(o->oBehParams >> 16) & 0x0040) {
            o->oMoveAngleYaw += 0x8000;
        }
    } else {
        o->oBackAndForthPlatformVel = 10.0f;
        if ((u16)(o->oBehParams >> 16) & 0x0040) {
            o->oBackAndForthPlatformDirection = -1.0f;
        } else {
            o->oBackAndForthPlatformDirection = 1.0f;
        }
    }
}

void bhv_sliding_plat_2_loop(void) {
    if (o->oTimer > 10) {
        o->oBackAndForthPlatformDistance += o->oBackAndForthPlatformVel;
        if (clamp_f32(&o->oBackAndForthPlatformDistance, -o->oBackAndForthPlatformPathLength, 0.0f)) {
            o->oBackAndForthPlatformVel = -o->oBackAndForthPlatformVel;
            o->oTimer = 0;
        }
    }

    obj_perform_position_op(0);

    if (o->oBackAndForthPlatformDirection != 0.0f) {
        o->oPosY = o->oHomeY + o->oBackAndForthPlatformDistance * o->oBackAndForthPlatformDirection;
    } else {
        obj_set_dist_from_home(o->oBackAndForthPlatformDistance);
    }

    obj_perform_position_op(1);
}
