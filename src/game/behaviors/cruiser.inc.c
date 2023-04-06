// cruiser.c.inc

void bhv_rr_cruiser_wing_init(void) {
    o->oRRCruiserWingUnkF4 = o->oFaceAngleYaw;
    o->oRRCruiserWingUnkF8 = o->oFaceAnglePitch;
}

void bhv_rr_cruiser_wing_loop(void) {
    if (o->oBehParams2ndByte == 0) {
        o->oFaceAngleYaw = o->oRRCruiserWingUnkF4 + sins(o->oTimer * 0x400) * 8192.0f;
        o->oFaceAnglePitch = o->oRRCruiserWingUnkF8 + coss(o->oTimer * 0x400) * 2048.0f;
    } else {
        o->oFaceAngleYaw = o->oRRCruiserWingUnkF4 - sins(o->oTimer * 0x400) * 8192.0f;
        o->oFaceAnglePitch = o->oRRCruiserWingUnkF8 + coss(o->oTimer * 0x400) * 2048.0f;
    }
#ifndef VERSION_JP
    if (o->oTimer == 64) {
        cur_obj_play_sound_2(SOUND_GENERAL_BOAT_ROCK);
        o->oTimer = 0;
    }
#endif
}
