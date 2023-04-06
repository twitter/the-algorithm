// tox_box.c.inc

s8 D_8032F8F0[] = { 4, 1, 4, 1, 6, 1, 6, 1, 5, 1, 5, 1, 6, 1, 6, 1, 5, 1, 2, 4, 1, 4, 1, 4, 1, 2,
                    5, 1, 5, 1, 7, 1, 7, 1, 4, 1, 4, 1, 7, 1, 7, 1, 5, 1, 5, 1, 5, 1, 2, 4, 1, -1 };
s8 D_8032F924[] = { 4, 1, 4, 1, 7, 1, 7, 1, 7, 1, 2, 6, 1, 6, 1, 6, 1, 5,
                    1, 5, 1, 6, 1, 5, 1, 5, 1, 2, 4, 1, 4, 1, 7, 1, -1 };
s8 D_8032F948[] = { 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 2, 5, 1, 5, 1, 5, 1, 5,
                    1, 5, 1, 7, 1, 2, 6, 1, 6, 1, 5, 1, 2, 4, 1, 7, 1, -1 };
s8 *D_8032F96C[] = { D_8032F8F0, D_8032F924, D_8032F948 };

void tox_box_shake_screen(void) {
    if (o->oDistanceToMario < 3000.0f)
        cur_obj_shake_screen(SHAKE_POS_SMALL);
}

void tox_box_move(f32 a0, f32 a1, s16 a2, s16 a3) // 0x18 0x1c 0x22 0x26
{
    o->oPosY = 99.41124 * sins((f32)(o->oTimer + 1) / 8 * 0x8000) + o->oHomeY + 3.0f;
    o->oForwardVel = a0;
    o->oUnkC0 = a1;
    o->oFaceAnglePitch += a2;
    if ((s16) o->oFaceAnglePitch < 0)
        a3 = -a3;
    o->oFaceAngleRoll += a3;
    cur_obj_set_pos_via_transform();
    if (o->oTimer == 7) {
        o->oAction = cur_obj_progress_direction_table();
#ifndef VERSION_JP
        cur_obj_play_sound_2(SOUND_GENERAL_UNK46);
#else
        cur_obj_play_sound_2(SOUND_GENERAL_UNK46_LOWPRIO);
#endif
    }
}

void tox_box_act_4(void) {
    tox_box_move(64.0f, 0.0f, 0x800, 0);
}
void tox_box_act_5(void) {
    tox_box_move(-64.0f, 0.0f, -0x800, 0);
}
void tox_box_act_6(void) {
    tox_box_move(0.0f, -64.0f, 0, 0x800);
}
void tox_box_act_7(void) {
    tox_box_move(0.0f, 64.0f, 0, -0x800);
}

void tox_box_act_1(void) {
    o->oForwardVel = 0.0f;
    if (o->oTimer == 0)
        tox_box_shake_screen();
    o->oPosY = o->oHomeY + 3.0f;
    if (o->oTimer == 20)
        o->oAction = cur_obj_progress_direction_table();
}

void tox_box_act_2(void) {
    if (o->oTimer == 20)
        o->oAction = cur_obj_progress_direction_table();
}

void tox_box_act_3(void) {
    if (o->oTimer == 20)
        o->oAction = cur_obj_progress_direction_table();
}

void tox_box_act_0(void) {
    s8 *sp1C = D_8032F96C[o->oBehParams2ndByte];
    o->oAction = cur_obj_set_direction_table(sp1C);
}

void (*sToxBoxActions[])(void) = { tox_box_act_0, tox_box_act_1, tox_box_act_2, tox_box_act_3,
                                   tox_box_act_4, tox_box_act_5, tox_box_act_6, tox_box_act_7 };

void bhv_tox_box_loop(void) {
    cur_obj_call_action_function(sToxBoxActions);
    load_object_collision_model();
}
