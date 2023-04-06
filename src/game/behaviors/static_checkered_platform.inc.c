// static_checkered_platform.c.inc

void bhv_static_checkered_platform_loop(void) {
    if (gDebugInfo[5][0] == 1) {
        obj_set_angle(o, 0, 0, 0);
        o->oAngleVelPitch = 0;
        o->oAngleVelYaw = 0;
        o->oAngleVelRoll = 0;
    }
    if (gDebugInfo[5][0] == 2) {
        o->oFaceAnglePitch = gDebugInfo[5][1] << 12;
        o->oFaceAngleYaw = gDebugInfo[5][2] << 12;
        o->oFaceAngleRoll = gDebugInfo[5][3] << 12;
    }
    o->oAngleVelPitch = gDebugInfo[5][4];
    o->oAngleVelYaw = gDebugInfo[5][5];
    o->oAngleVelRoll = gDebugInfo[5][6];
    if (gDebugInfo[5][0] == 3) {
        o->oFaceAnglePitch += o->oAngleVelPitch;
        o->oFaceAngleYaw += o->oAngleVelYaw;
        o->oFaceAngleRoll += o->oAngleVelRoll;
    }
}
