// pole_base.inc.c

void bhv_pole_base_loop(void) {
    if (o->oPosY - 10.0f < gMarioObject->oPosY
        && gMarioObject->oPosY < o->oPosY + o->hitboxHeight + 30.0f)
        if (o->oTimer > 10)
            if (!(gMarioStates->action & MARIO_PUNCHING))
                cur_obj_push_mario_away(70.0f);
}
