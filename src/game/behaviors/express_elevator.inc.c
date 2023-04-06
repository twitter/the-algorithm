// express_elevator.c.inc

void bhv_wdw_express_elevator_loop(void) {
    o->oVelY = 0.0f;
    if (o->oAction == 0) {
        if (cur_obj_is_mario_on_platform())
            o->oAction++;
    } else if (o->oAction == 1) {
        o->oVelY = -20.0f;
        o->oPosY += o->oVelY;
        cur_obj_play_sound_1(SOUND_ENV_ELEVATOR4);
        if (o->oTimer > 132)
            o->oAction++;
    } else if (o->oAction == 2) {
        if (o->oTimer > 110)
            o->oAction++;
    } else if (o->oAction == 3) {
        o->oVelY = 10.0f;
        o->oPosY += o->oVelY;
        cur_obj_play_sound_1(SOUND_ENV_ELEVATOR4);
        if (o->oPosY >= o->oHomeY) {
            o->oPosY = o->oHomeY;
            o->oAction++;
        }
    } else if (!cur_obj_is_mario_on_platform())
        o->oAction = 0;
}
