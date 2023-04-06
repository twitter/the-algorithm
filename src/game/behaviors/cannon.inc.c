// cannon.c.inc

void bhv_cannon_base_unused_loop(void) {
    o->oPosY += o->oVelY;
}

void opened_cannon_act_0(void) {
    if (o->oTimer == 0) {
        o->oInteractStatus = 0;
        o->oPosX = o->oHomeX;
        o->oPosY = o->oHomeY;
        o->oPosZ = o->oHomeZ;
        o->oMoveAnglePitch = 0;
        o->oMoveAngleYaw = (s16)(o->oBehParams2ndByte << 8);
        o->oCannonUnkF4 = 0;
        o->oCannonUnk10C = 0;
        cur_obj_enable_rendering();
        cur_obj_become_tangible();
    }
    if (o->oDistanceToMario < 500.0f) {
        cur_obj_become_tangible();
        cur_obj_enable_rendering();
        if (o->oInteractStatus & INT_STATUS_INTERACTED
            && (!(o->oInteractStatus
                  & INT_STATUS_TOUCHED_BOB_OMB))) // bob-omb explodes when it gets into a cannon
        {
            o->oAction = 4;
            o->oCannonUnk10C = 1;
            o->oCannonUnkF8 = 1;
        } else
            o->oInteractStatus = 0;
    } else {
        cur_obj_become_intangible();
        cur_obj_disable_rendering();
        o->oCannonUnk10C = 0;
    }
}

void opened_cannon_act_4(void) {
    if (o->oTimer == 0)
        cur_obj_play_sound_2(SOUND_OBJ_CANNON1);
    o->oPosY += 5.0f;
    o->oPosX += (f32)((o->oTimer / 2 & 1) - 0.5) * 2;
    o->oPosZ += (f32)((o->oTimer / 2 & 1) - 0.5) * 2;
    if (o->oTimer > 67) {
        o->oPosX += (f32)((o->oTimer / 2 & 1) - 0.5) * 4;
        o->oPosZ += (f32)((o->oTimer / 2 & 1) - 0.5) * 4;
        o->oAction = 6;
    }
}

void opened_cannon_act_6(void) {
    if (o->oTimer == 0)
        cur_obj_play_sound_2(SOUND_OBJ_CANNON2);
    if (o->oTimer < 4) {
        o->oPosX += (f32)((o->oTimer / 2 & 1) - 0.5) * 4.0f;
        o->oPosZ += (f32)((o->oTimer / 2 & 1) - 0.5) * 4.0f;
    } else {
        if (o->oTimer < 6) {
        } else {
            if (o->oTimer < 22) {
                o->oMoveAngleYaw =
                    sins(o->oCannonUnkF4) * 0x4000 + ((s16)(o->oBehParams2ndByte << 8));
                o->oCannonUnkF4 += 0x400;
            } else if (o->oTimer < 26) {
            } else {
                o->oCannonUnkF4 = 0;
                o->oAction = 5;
            }
        }
    }
}

void opened_cannon_act_5(void) {
    if (o->oTimer == 0)
        cur_obj_play_sound_2(SOUND_OBJ_CANNON3);
    if (o->oTimer < 4) {
    } else {
        if (o->oTimer < 20) {
            o->oCannonUnkF4 += 0x400;
            o->oMoveAnglePitch = sins(o->oCannonUnkF4) * 0x2000;
        } else if (o->oTimer < 25) {
        } else
            o->oAction = 1;
    }
}

void opened_cannon_act_1(void) {
    UNUSED s32 unused;
    cur_obj_become_intangible();
    cur_obj_disable_rendering();
    o->oCannonUnk10C = 0;
    gMarioShotFromCannon = 1;
}

void opened_cannon_act_2(void) {
    o->oAction = 3;
}

void opened_cannon_act_3(void) {
    UNUSED s32 unused;
    if (o->oTimer > 3)
        o->oAction = 0;
}

void (*sOpenedCannonActions[])(void) = { opened_cannon_act_0, opened_cannon_act_1, opened_cannon_act_2,
                                         opened_cannon_act_3, opened_cannon_act_4, opened_cannon_act_5,
                                         opened_cannon_act_6 };

u8 unused0EA1FC[] = { 2,  0,   0, 0, 0,  0,   0, 0, 63, 128, 0, 0, 2,  0,   0, 0, 65, 32,  0, 0,
                      63, 128, 0, 0, 2,  0,   0, 0, 65, 160, 0, 0, 63, 128, 0, 0, 2,  0,   0, 0,
                      65, 160, 0, 0, 63, 128, 0, 0, 8,  0,   0, 0, 65, 32,  0, 0, 63, 128, 0, 0 };

void bhv_cannon_base_loop(void) {
    cur_obj_call_action_function(sOpenedCannonActions);
    if (o->oCannonUnkF8)
        o->oCannonUnkF8++;
    o->oInteractStatus = 0;
}

void bhv_cannon_barrel_loop(void) {
    struct Object *parent = o->parentObj;
    if (parent->header.gfx.node.flags & GRAPH_RENDER_ACTIVE) {
        cur_obj_enable_rendering();
        obj_copy_pos(o, o->parentObj);
        o->oMoveAngleYaw = o->parentObj->oMoveAngleYaw;
        o->oFaceAnglePitch = o->parentObj->oMoveAnglePitch;
    } else
        cur_obj_disable_rendering();
}
