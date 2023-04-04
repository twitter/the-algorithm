// cannon.inc.c

void bhv_cannon_closed_init(void) {
    if (save_file_is_cannon_unlocked() == TRUE) {
        // If the cannon is open, spawn a cannon and despawn the object.
        struct Object *cannon = spawn_object(o, MODEL_CANNON_BASE, bhvCannon);

        cannon->oBehParams2ndByte = o->oBehParams2ndByte;
        cannon->oPosX = o->oHomeX;
        cannon->oPosY = o->oHomeY;
        cannon->oPosZ = o->oHomeZ;

        o->oAction = CANNON_TRAP_DOOR_ACT_OPEN;
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}

void cannon_door_act_opening(void) {
    if (o->oTimer == 0) {
        cur_obj_play_sound_2(SOUND_GENERAL_CANNON_UP);
    }

    if (o->oTimer < 30) {
        o->oVelY = -0.5f;
        o->oPosY += o->oVelY;
        o->oVelX = 0.0f;
    } else {
        if (o->oTimer == 80) {
            bhv_cannon_closed_init();
            return;
        }

        o->oVelX = 4.0f;
        o->oVelY = 0.0f;
        o->oPosX += o->oVelX;
    }
}

void bhv_cannon_closed_loop(void) {
    switch (o->oAction) {
        case CANNON_TRAP_DOOR_ACT_CLOSED:
            o->oVelX = 0.0f;
            o->oVelY = 0.0f;
            o->oDrawingDistance = 4000.0f;

            if (save_file_is_cannon_unlocked() == TRUE) {
                o->oAction = CANNON_TRAP_DOOR_ACT_CAM_ZOOM;
            }
            break;

        case CANNON_TRAP_DOOR_ACT_CAM_ZOOM:
            if (o->oTimer == 60) {
                o->oAction = CANNON_TRAP_DOOR_ACT_OPENING;
            }
            o->oDrawingDistance = 20000.0f;
            break;

        case CANNON_TRAP_DOOR_ACT_OPENING:
            cannon_door_act_opening();
            break;
    }
}
