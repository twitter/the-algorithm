// water_bomb_cannon.inc.c

void bhv_bubble_cannon_barrel_loop(void) {
    struct Object *val04;

    if (o->parentObj->oAction == 2) {
        obj_mark_for_deletion(o);
    } else {
        o->oMoveAngleYaw = o->parentObj->oFaceAngleYaw;
        o->oMoveAnglePitch = o->parentObj->oMoveAnglePitch + 0x4000;
        o->oFaceAnglePitch = o->parentObj->oMoveAnglePitch;

        if ((o->oCannonBarrelBubblesUnkF4 += o->oForwardVel) > 0.0f) {
            cur_obj_set_pos_via_transform();
            obj_forward_vel_approach(-5.0f, 18.0f);
        } else {
            o->oCannonBarrelBubblesUnkF4 = 0.0f;
            obj_copy_pos(o, o->parentObj);

            // check this
            if (o->parentObj->oWaterCannonUnkF4 != 0) {
                if (o->oForwardVel == 0.0f) {
                    o->oForwardVel = 35.0f;

                    val04 = spawn_object(o, MODEL_WATER_BOMB, bhvWaterBomb);
                    if (val04 != NULL) {
                        val04->oForwardVel = -100.0f;
                        val04->header.gfx.scale[1] = 1.7f;
                    }

                    set_camera_shake_from_point(SHAKE_POS_MEDIUM, o->oPosX, o->oPosY, o->oPosZ);
                }
            } else {
                o->oForwardVel = 0.0f;
            }
        }
    }
}

void water_bomb_cannon_act_0(void) {
    if (o->oDistanceToMario < 2000.0f) {
        spawn_object(o, MODEL_CANNON_BARREL, bhvCannonBarrelBubbles);
        cur_obj_unhide();

        o->oAction = 1;
        o->oMoveAnglePitch = o->oWaterCannonUnkFC = 0x1C00;
    }
}

void water_bomb_cannon_act_1(void) {
    if (o->oDistanceToMario > 2500.0f) {
        o->oAction = 2;
    } else if (o->oBehParams2ndByte == 0) {
        if (o->oWaterCannonUnkF4 != 0) {
            o->oWaterCannonUnkF4 -= 1;
        } else {
            obj_move_pitch_approach(o->oWaterCannonUnkFC, 0x80);
            obj_face_yaw_approach(o->oWaterCannonUnk100, 0x100);

            if ((s16) o->oFaceAngleYaw == (s16) o->oWaterCannonUnk100) {
                if (o->oWaterCannonUnkF8 != 0) {
                    o->oWaterCannonUnkF8 -= 1;
                } else {
                    cur_obj_play_sound_2(SOUND_OBJ_CANNON4);
                    o->oWaterCannonUnkF4 = 70;
                    o->oWaterCannonUnkFC = 0x1000 + 0x400 * (random_u16() & 0x3);
                    o->oWaterCannonUnk100 = -0x2000 + o->oMoveAngleYaw + 0x1000 * (random_u16() % 5);
                    o->oWaterCannonUnkF8 = 60;
                }
            }
        }
    }
}

void water_bomb_cannon_act_2(void) {
    cur_obj_hide();
    o->oAction = 0;
}

void bhv_water_bomb_cannon_loop(void) {
    cur_obj_push_mario_away_from_cylinder(220.0f, 300.0f);

    switch (o->oAction) {
        case 0:
            water_bomb_cannon_act_0();
            break;
        case 1:
            water_bomb_cannon_act_1();
            break;
        case 2:
            water_bomb_cannon_act_2();
            break;
    }
}
