
void bhv_horizontal_grindel_init(void) {
    o->oHorizontalGrindelTargetYaw = o->oMoveAngleYaw;
}

void bhv_horizontal_grindel_update(void) {
    if (o->oMoveFlags & 0x00000003) {
        if (!o->oHorizontalGrindelOnGround) {
            cur_obj_play_sound_2(SOUND_OBJ_THWOMP);
            o->oHorizontalGrindelOnGround = TRUE;
            set_camera_shake_from_point(SHAKE_POS_SMALL, o->oPosX, o->oPosY, o->oPosZ);

            o->oHorizontalGrindelDistToHome = cur_obj_lateral_dist_to_home();
            o->oForwardVel = 0.0f;
            o->oTimer = 0;
        }

        if (cur_obj_rotate_yaw_toward(o->oHorizontalGrindelTargetYaw, 0x400)) {
            if (o->oTimer > 60) {
                if (o->oHorizontalGrindelDistToHome > 300.0f) {
                    o->oHorizontalGrindelTargetYaw += 0x8000;
                    o->oHorizontalGrindelDistToHome = 0.0f;
                } else {
                    cur_obj_play_sound_2(SOUND_OBJ_KING_BOBOMB_JUMP);
                    o->oForwardVel = 11.0f;
                    o->oVelY = 70.0f;
                    o->oGravity = -4.0f;
                    o->oMoveFlags = 0;
                }
            }
        } else {
            o->oTimer = 0;
        }
    } else {
        o->oHorizontalGrindelOnGround = FALSE;
        if (o->oVelY < 0.0f) {
            o->oGravity = -16.0f;
        }
    }

    o->oFaceAngleYaw = o->oMoveAngleYaw + 0x4000;
    cur_obj_move_standard(78);
}
