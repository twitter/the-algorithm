// star_door.c.inc

void star_door_update_pos(void) {
    o->oVelX = (o->oUnkBC) * coss(o->oMoveAngleYaw);
    o->oVelZ = (o->oUnkBC) * -sins(o->oMoveAngleYaw);
    o->oPosX += o->oVelX;
    o->oPosZ += o->oVelZ;
}

void bhv_star_door_loop(void) {
    UNUSED u8 pad[4];
    struct Object *sp18;
    sp18 = cur_obj_nearest_object_with_behavior(bhvStarDoor);
    switch (o->oAction) {
        case 0:
            cur_obj_become_tangible();
            if (0x30000 & o->oInteractStatus)
                o->oAction = 1;
            if (sp18 != NULL && sp18->oAction != 0)
                o->oAction = 1;
            break;
        case 1:
            if (o->oTimer == 0 && (s16)(o->oMoveAngleYaw) >= 0) {
                cur_obj_play_sound_2(SOUND_GENERAL_STAR_DOOR_OPEN);
#ifdef VERSION_SH
                queue_rumble_data(35, 30);
#endif
            }
            cur_obj_become_intangible();
            o->oUnkBC = -8.0f;
            star_door_update_pos();
            if (o->oTimer >= 16)
                o->oAction++;
            break;
        case 2:
            if (o->oTimer >= 31)
                o->oAction++;
            break;
        case 3:
            if (o->oTimer == 0 && (s16)(o->oMoveAngleYaw) >= 0) {
                cur_obj_play_sound_2(SOUND_GENERAL_STAR_DOOR_CLOSE);
#ifdef VERSION_SH
                queue_rumble_data(35, 30);
#endif
            }
            o->oUnkBC = 8.0f;
            star_door_update_pos();
            if (o->oTimer >= 16)
                o->oAction++;
            break;
        case 4:
            o->oInteractStatus = 0;
            o->oAction = 0;
            break;
    }
}
