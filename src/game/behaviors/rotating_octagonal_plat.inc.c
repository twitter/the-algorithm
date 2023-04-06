// rotating_octagonal_plat.inc.c

void *D_80331A44[] = {
    bits_seg7_collision_0701AA84,
    rr_seg7_collision_07029508,
};

s16 D_80331A4C[] = { 300, -300, 600, -600 };

void bhv_rotating_octagonal_plat_init(void) {
    o->collisionData = segmented_to_virtual(D_80331A44[(u8)(o->oBehParams >> 16)]);
    o->oAngleVelYaw = D_80331A4C[(u8)(o->oBehParams >> 24)];
}

void bhv_rotating_octagonal_plat_loop(void) {
    o->oFaceAngleYaw += o->oAngleVelYaw;
}
