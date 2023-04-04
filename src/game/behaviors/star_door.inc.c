// star_door.inc.c

void star_door_update_pos(void) {
    o->oVelX = (o->oLeftVel) * coss(o->oMoveAngleYaw);
    o->oVelZ = (o->oLeftVel) * -sins(o->oMoveAngleYaw);
    o->oPosX += o->oVelX;
    o->oPosZ += o->oVelZ;
}

void bhv_star_door_loop(void) {
    UNUSED u8 filler[4];
    struct Object *sp18 = cur_obj_nearest_object_with_behavior(bhvStarDoor);

    switch (o->oAction) {
        case 0:
            cur_obj_become_tangible();
            if (o->oInteractStatus & (INT_STATUS_UNK16 | INT_STATUS_UNK17)) {
                o->oAction = 1;
            }
            if (sp18 != NULL && sp18->oAction != 0) {
                o->oAction = 1;
            }
            break;

        case 1:
            if (o->oTimer == 0 && (s16) o->oMoveAngleYaw >= 0) {
                cur_obj_play_sound_2(SOUND_GENERAL_STAR_DOOR_OPEN);
#if ENABLE_RUMBLE
                queue_rumble_data(35, 30);
#endif
            }
            cur_obj_become_intangible();
            o->oLeftVel = -8.0f;
            star_door_update_pos();
            if (o->oTimer > 15) {
                o->oAction++;
            }
            break;

        case 2:
            if (o->oTimer > 30) {
                o->oAction++;
            }
            break;

        case 3:
            if (o->oTimer == 0 && (s16) o->oMoveAngleYaw >= 0) {
                cur_obj_play_sound_2(SOUND_GENERAL_STAR_DOOR_CLOSE);
#if ENABLE_RUMBLE
                queue_rumble_data(35, 30);
#endif
            }
            o->oLeftVel = 8.0f;
            star_door_update_pos();
            if (o->oTimer > 15) {
                o->oAction++;
            }
            break;

        case 4:
            o->oInteractStatus = 0;
            o->oAction = 0;
            break;
    }
}
