// animated_floor_switch.inc.c

struct Struct80331A54 {
    void *unk00;
    s16 unk04;
};

struct Struct80331A54 D_80331A54[][5] = {
    {
        { bits_seg7_collision_0701B734, MODEL_BITS_STAIRCASE_FRAME4 },
        { bits_seg7_collision_0701B59C, MODEL_BITS_STAIRCASE_FRAME3 },
        { bits_seg7_collision_0701B404, MODEL_BITS_STAIRCASE_FRAME2 },
        { bits_seg7_collision_0701B26C, MODEL_BITS_STAIRCASE_FRAME1 },
        { bits_seg7_collision_0701B0D4, MODEL_BITS_STAIRCASE },
    },
    {
        { bitdw_seg7_collision_0700FD9C, MODEL_BITDW_STAIRCASE },
        { bitdw_seg7_collision_0700FC7C, MODEL_BITDW_STAIRCASE_FRAME1 },
        { bitdw_seg7_collision_0700FB5C, MODEL_BITDW_STAIRCASE_FRAME2 },
        { bitdw_seg7_collision_0700FA3C, MODEL_BITDW_STAIRCASE_FRAME3 },
        { bitdw_seg7_collision_0700F91C, MODEL_BITDW_STAIRCASE_FRAME4 },
    },
    {
        { rr_seg7_collision_0702A6B4, MODEL_RR_TRICKY_TRIANGLES_FRAME4 },
        { rr_seg7_collision_0702A32C, MODEL_RR_TRICKY_TRIANGLES_FRAME3 },
        { rr_seg7_collision_07029FA4, MODEL_RR_TRICKY_TRIANGLES_FRAME2 },
        { rr_seg7_collision_07029C1C, MODEL_RR_TRICKY_TRIANGLES_FRAME1 },
        { rr_seg7_collision_07029924, MODEL_RR_TRICKY_TRIANGLES },
    },
};

s16 D_80331ACC[] = { 250, 200, 200 };

void bhv_animates_on_floor_switch_press_init(void) {
    o->parentObj = cur_obj_nearest_object_with_behavior(bhvFloorSwitchAnimatesObject);
}

void bhv_animates_on_floor_switch_press_loop(void) {
    if (o->oFloorSwitchPressAnimationUnk100 != 0) {
        if (o->parentObj->oAction != 2) {
            o->oFloorSwitchPressAnimationUnk100 = 0;
        }

        if (o->oFloorSwitchPressAnimationUnkFC != 0) {
            o->oFloorSwitchPressAnimationUnkF4 = D_80331ACC[o->oBehParams2ndByte];
        } else {
            o->oFloorSwitchPressAnimationUnkF4 = 0;
        }
    } else if (o->parentObj->oAction == 2) {
        o->oFloorSwitchPressAnimationUnkFC ^= 1;
        o->oFloorSwitchPressAnimationUnk100 = 1;
    }

    if (o->oFloorSwitchPressAnimationUnkF4 != 0) {
        if (o->oFloorSwitchPressAnimationUnkF4 < 60) {
            cur_obj_play_sound_1(SOUND_GENERAL2_SWITCH_TICK_SLOW);
        } else {
            cur_obj_play_sound_1(SOUND_GENERAL2_SWITCH_TICK_FAST);
        }

        if (--o->oFloorSwitchPressAnimationUnkF4 == 0) {
            o->oFloorSwitchPressAnimationUnkFC = 0;
        }

        if (o->oFloorSwitchPressAnimationUnkF8 < 9) {
            o->oFloorSwitchPressAnimationUnkF8 += 1;
        }
    } else if ((o->oFloorSwitchPressAnimationUnkF8 -= 2) < 0) {
        o->oFloorSwitchPressAnimationUnkF8 = 0;
        o->oFloorSwitchPressAnimationUnkFC = 1;
    }

    o->collisionData = segmented_to_virtual(
        D_80331A54[o->oBehParams2ndByte][o->oFloorSwitchPressAnimationUnkF8 / 2].unk00);

    cur_obj_set_model(D_80331A54[o->oBehParams2ndByte][o->oFloorSwitchPressAnimationUnkF8 / 2].unk04);
}
