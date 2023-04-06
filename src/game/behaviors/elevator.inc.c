// elevator.c.inc

void elevator_starting_shake(void) {
    cur_obj_play_sound_2(SOUND_GENERAL_QUIET_POUND1);
    cur_obj_shake_screen(SHAKE_POS_SMALL);
}

void elevator_act_0(void) {
    o->oVelY = 0;
    if (o->oElevatorUnk100 == 2) {
        if (gMarioObject->platform == o) {
            if (o->oPosY > o->oElevatorUnkFC)
                o->oAction = 2;
            else
                o->oAction = 1;
        }
    } else if (gMarioObject->oPosY > o->oElevatorUnkFC || o->oElevatorUnk100 == 1) {
        o->oPosY = o->oElevatorUnkF8;
        if (gMarioObject->platform == o)
            o->oAction = 2;
    } else {
        o->oPosY = o->oElevatorUnkF4;
        if (gMarioObject->platform == o)
            o->oAction = 1;
    }
}

void elevator_act_1(void) {
    cur_obj_play_sound_1(SOUND_ENV_ELEVATOR1);
    if (o->oTimer == 0 && cur_obj_is_mario_on_platform())
        elevator_starting_shake();
    approach_f32_signed(&o->oVelY, 10.0f, 2.0f);
    o->oPosY += o->oVelY;
    if (o->oPosY > o->oElevatorUnkF8) {
        o->oPosY = o->oElevatorUnkF8;
        if (o->oElevatorUnk100 == 2 || o->oElevatorUnk100 == 1)
            o->oAction = 3;
        else if (gMarioObject->oPosY < o->oElevatorUnkFC)
            o->oAction = 2;
        else
            o->oAction = 3;
    }
}

void elevator_act_2() // Pretty similar code to action 1
{
    cur_obj_play_sound_1(SOUND_ENV_ELEVATOR1);
    if (o->oTimer == 0 && cur_obj_is_mario_on_platform())
        elevator_starting_shake();
    approach_f32_signed(&o->oVelY, -10.0f, -2.0f);
    o->oPosY += o->oVelY;
    if (o->oPosY < o->oElevatorUnkF4) {
        o->oPosY = o->oElevatorUnkF4;
        if (o->oElevatorUnk100 == 1)
            o->oAction = 4;
        else if (o->oElevatorUnk100 == 2)
            o->oAction = 3;
        else if (gMarioObject->oPosY > o->oElevatorUnkFC)
            o->oAction = 1;
        else
            o->oAction = 3;
    }
}

void elevator_act_4() {
    o->oVelY = 0;
    if (o->oTimer == 0) {
        cur_obj_shake_screen(SHAKE_POS_SMALL);
        cur_obj_play_sound_2(SOUND_GENERAL_METAL_POUND);
    }
    if (!mario_is_in_air_action() && !cur_obj_is_mario_on_platform())
        o->oAction = 1;
}

void elevator_act_3() // nearly identical to action 2
{
    o->oVelY = 0;
    if (o->oTimer == 0) {
        cur_obj_shake_screen(SHAKE_POS_SMALL);
        cur_obj_play_sound_2(SOUND_GENERAL_METAL_POUND);
    }
    if (!mario_is_in_air_action() && !cur_obj_is_mario_on_platform())
        o->oAction = 0;
}

void bhv_elevator_init(void) {
    s32 sp1C = D_8032F38C[o->oBehParams2ndByte * 3 + 2];
    if (sp1C == 0) {
        o->oElevatorUnkF4 = D_8032F38C[o->oBehParams2ndByte * 3];
        o->oElevatorUnkF8 = o->oHomeY;
        o->oElevatorUnkFC = (o->oElevatorUnkF4 + o->oElevatorUnkF8) / 2;
        o->oElevatorUnk100 = cur_obj_has_behavior(bhvRrElevatorPlatform);
    } else {
        o->oElevatorUnkF4 = D_8032F38C[o->oBehParams2ndByte * 3];
        o->oElevatorUnkF8 = D_8032F38C[o->oBehParams2ndByte * 3 + 1];
        o->oElevatorUnkFC = (o->oElevatorUnkF4 + o->oElevatorUnkF8) / 2;
        o->oElevatorUnk100 = 2;
    }
}

void (*sElevatorActions[])(void) = { elevator_act_0, elevator_act_1, elevator_act_2, elevator_act_3,
                                     elevator_act_4 };

struct SpawnParticlesInfo D_8032F3CC = { 3, 20, MODEL_MIST, 20, 10, 5, 0, 0, 0, 30, 30.0f, 1.5f };

struct SpawnParticlesInfo D_8032F3E0 = { 0, 5, MODEL_SAND_DUST, 0, 0, 20, 20, 0, 252, 30, 5.0f, 2.0f };

s16 D_8032F3F4[] = { 2, -8, 1, 4 };

struct SpawnParticlesInfo D_8032F3FC = { 0,    5,   MODEL_WHITE_PARTICLE_DL, 0, 0, 20, 20, 0, 252, 30,
                                         2.0f, 2.0f };

void bhv_elevator_loop(void) {
    cur_obj_call_action_function(sElevatorActions);
}
