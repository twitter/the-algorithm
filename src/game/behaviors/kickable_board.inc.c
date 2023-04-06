// kickable_board.c.inc

s32 check_mario_attacking(UNUSED s32 sp18) {
    if (obj_check_if_collided_with_object(o, gMarioObject)) {
        if (abs_angle_diff(o->oMoveAngleYaw, gMarioObject->oMoveAngleYaw) > 0x6000) {
            if (gMarioStates->action == ACT_SLIDE_KICK)
                return 1;
            if (gMarioStates->action == ACT_PUNCHING)
                return 1;
            if (gMarioStates->action == ACT_MOVE_PUNCHING)
                return 1;
            if (gMarioStates->action == ACT_SLIDE_KICK_SLIDE)
                return 1;
            if (gMarioStates->action == ACT_JUMP_KICK)
                return 2;
            if (gMarioStates->action == ACT_WALL_KICK_AIR)
                return 2;
        }
    }
    return 0;
}

void init_kickable_board_rock(void) {
    o->oKickableBoardF8 = 1600;
    o->oKickableBoardF4 = 0;
}

void bhv_kickable_board_loop(void) {
    s32 sp24;
    switch (o->oAction) {
        case 0:
            o->oFaceAnglePitch = 0;
            if (check_mario_attacking(0)) {
                init_kickable_board_rock();
                o->oAction++;
            }
            load_object_collision_model();
            break;
        case 1:
            o->oFaceAnglePitch = 0;
            load_object_collision_model();
            o->oFaceAnglePitch = -sins(o->oKickableBoardF4) * o->oKickableBoardF8;
            if (o->oTimer > 30 && (sp24 = check_mario_attacking(0))) {
                if (gMarioObject->oPosY > o->oPosY + 160.0f && sp24 == 2) {
                    o->oAction++;
                    cur_obj_play_sound_2(SOUND_GENERAL_BUTTON_PRESS_2);
                } else
                    o->oTimer = 0;
            }
            if (o->oTimer != 0) {
                o->oKickableBoardF8 -= 8;
                if (o->oKickableBoardF8 < 0)
                    o->oAction = 0;
            } else
                init_kickable_board_rock();
            if (!(o->oKickableBoardF4 & 0x7FFF))
                cur_obj_play_sound_2(SOUND_GENERAL_BUTTON_PRESS_2);
            o->oKickableBoardF4 += 0x400;
            break;
        case 2:
            cur_obj_become_intangible();
            cur_obj_set_model(MODEL_WF_KICKABLE_BOARD_FELLED);
            o->oAngleVelPitch -= 0x80;
            o->oFaceAnglePitch += o->oAngleVelPitch;
            if (o->oFaceAnglePitch < -0x4000) {
                o->oFaceAnglePitch = -0x4000;
                o->oAngleVelPitch = 0;
                o->oAction++;
                cur_obj_shake_screen(SHAKE_POS_SMALL);
                cur_obj_play_sound_2(SOUND_GENERAL_UNKNOWN4);
            }
            load_object_collision_model();
            break;
        case 3:
            load_object_collision_model();
            break;
    }
    o->header.gfx.throwMatrix = NULL;
}
