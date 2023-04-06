// square_platform_cycle.c.inc

s32 square_plat_set_yaw_until_timer(u16 yaw, s32 a) {
    o->oMoveAngleYaw = yaw;
    if (a < o->oTimer)
        return 1;
    else
        return 0;
}

void bhv_squarish_path_moving_loop(void) {
    o->oForwardVel = 10.0f;
    switch (o->oAction) {
        case 0:
            o->oAction = (o->oBehParams2ndByte & 3) + 1;
            break;
        case 1:
            if (square_plat_set_yaw_until_timer(0, 60))
                o->oAction++;
            break;
        case 2:
            if (square_plat_set_yaw_until_timer(0x4000, 60))
                o->oAction++;
            break;
        case 3:
            if (square_plat_set_yaw_until_timer(0x8000, 60))
                o->oAction++;
            break;
        case 4:
            if (square_plat_set_yaw_until_timer(0xc000, 60))
                o->oAction = 1;
            break;
        default:
            break;
    }
    cur_obj_move_using_fvel_and_gravity();
    load_object_collision_model();
}
