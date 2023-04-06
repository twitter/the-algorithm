// lll_volcano_flames.c.inc

void bhv_volcano_flames_loop(void) {
    cur_obj_update_floor_height();
    cur_obj_compute_vel_xz();
    o->oPosX += o->oVelX;
    o->oPosZ += o->oVelZ;
    cur_obj_move_y(-4.0f, -0.7f, 2.0f);
    if (o->oMoveFlags & 0x33)
        obj_mark_for_deletion(o);
}
