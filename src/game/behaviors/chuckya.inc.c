// chuckya.c.inc

void common_anchor_mario_behavior(f32 sp28, f32 sp2C, s32 sp30) {
    switch (o->parentObj->oChuckyaUnk88) {
        case 0:
            break;
        case 1:
            obj_set_gfx_pos_at_obj_pos(gMarioObject, o);
            break;
        case 2:
            gMarioObject->oInteractStatus |= (sp30 + INT_STATUS_MARIO_UNK2);
            gMarioStates->forwardVel = sp28;
            gMarioStates->vel[1] = sp2C;
            o->parentObj->oChuckyaUnk88 = 0;
            break;
        case 3:
            gMarioObject->oInteractStatus |=
                (INT_STATUS_MARIO_UNK2 + INT_STATUS_MARIO_UNK6); // loads 2 interactions at once?
            gMarioStates->forwardVel = 10.0f;
            gMarioStates->vel[1] = 10.0f;
            o->parentObj->oChuckyaUnk88 = 0;
            break;
    }
    o->oMoveAngleYaw = o->parentObj->oMoveAngleYaw;
    if (!o->parentObj->activeFlags)
        obj_mark_for_deletion(o);
}

void bhv_chuckya_anchor_mario_loop(void) {
    common_anchor_mario_behavior(40.0f, 40.0f, 64);
}

s32 unknown_chuckya_function(s32 sp20, f32 sp24, f32 sp28, s32 sp2C) {
    s32 sp1C = 0;
    if (o->oChuckyaUnkF8 != 4) {
        if (sp24 < cur_obj_lateral_dist_from_mario_to_home()) {
            if (cur_obj_lateral_dist_to_home() < 200.0f)
                sp1C = 0;
            else {
                sp1C = 1;
                o->oAngleToMario = cur_obj_angle_to_home();
            }
        } else if (o->oDistanceToMario > sp28) {
            if (gGlobalTimer % (s16) sp2C == 0)
                o->oAngleToMario = obj_angle_to_object(o, gMarioObject);
            sp1C = 2;
        } else
            sp1C = 3;
        if (sp20 && update_angle_from_move_flags(&o->oAngleToMario)) {
            sp1C = 4;
            o->oChuckyaUnkF8 = 4;
        }
    } else
        sp1C = 4;
    return sp1C;
}

s32 approach_forward_vel(f32 *arr, f32 spC, f32 sp10) {
    s32 sp4 = 0;
    if (arr[0] > spC) {
        arr[0] -= sp10;
        if (arr[0] < spC)
            arr[0] = spC;
    } else if (arr[0] < spC) {
        arr[0] += sp10;
        if (arr[0] > spC)
            arr[0] = spC;
    } else
        sp4 = 1;
    return sp4;
}

void chuckya_act_0(void) {
    s32 sp3C;
    UNUSED u8 pad[16];
    s32 sp28;
    if (o->oTimer == 0)
        o->oChuckyaUnkFC = 0;
    o->oAngleToMario = obj_angle_to_object(o, gMarioObject);
    switch (sp28 = o->oSubAction) {
        case 0:
            o->oForwardVel = 0;
            if (cur_obj_lateral_dist_from_mario_to_home() < 2000.0f) {
                cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x400);
                if (o->oChuckyaUnkFC > 40
                    || abs_angle_diff(o->oMoveAngleYaw, o->oAngleToMario) < 0x1000)
                    o->oSubAction = 1;
            } else
                o->oSubAction = 3;
            break;
        case 1:
            approach_forward_vel(&o->oForwardVel, 30.0f, 4.0f);
            if (abs_angle_diff(o->oMoveAngleYaw, o->oAngleToMario) > 0x4000)
                o->oSubAction = 2;
            if (cur_obj_lateral_dist_from_mario_to_home() > 2000.0f)
                o->oSubAction = 3;
            break;
        case 2:
            approach_forward_vel(&o->oForwardVel, 0, 4.0f);
            if (o->oChuckyaUnkFC > 48)
                o->oSubAction = 0;
            break;
        case 3:
            if (cur_obj_lateral_dist_to_home() < 500.0f)
                o->oForwardVel = 0;
            else {
                approach_forward_vel(&o->oForwardVel, 10.0f, 4.0f);
                o->oAngleToMario = cur_obj_angle_to_home();
                cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x800);
            }
            if (cur_obj_lateral_dist_from_mario_to_home() < 1900.0f)
                o->oSubAction = 0;
            break;
    }
    if (o->oSubAction != sp28)
        o->oChuckyaUnkFC = 0;
    else
        o->oChuckyaUnkFC++;
    cur_obj_init_animation_with_sound(4);
    if (o->oForwardVel > 1.0f)
        cur_obj_play_sound_1(SOUND_AIR_CHUCKYA_MOVE);
    print_debug_bottom_up("fg %d", sp3C);
    print_debug_bottom_up("sp %d", o->oForwardVel);
}

void chuckya_act_1(void) {
    if (o->oSubAction == 0) {
        if (cur_obj_init_animation_and_check_if_near_end(0))
            o->oSubAction++;
        o->oChuckyaUnkFC = random_float() * 30.0f + 10.0f;
        o->oChuckyaUnk100 = 0;
        o->oForwardVel = 0.0f;
    } else {
        if (o->oSubAction == 1) {
            o->oChuckyaUnk100 += player_performed_grab_escape_action();
            print_debug_bottom_up("%d", o->oChuckyaUnk100);
            if (o->oChuckyaUnk100 > 10) {
                o->oChuckyaUnk88 = 3;
                o->oAction = 3;
                o->oInteractStatus &= ~(INT_STATUS_GRABBED_MARIO);
            } else {
                cur_obj_init_animation_with_sound(1);
                o->oMoveAngleYaw += INT_STATUS_GRABBED_MARIO;
                if (o->oChuckyaUnkFC-- < 0)
                    if (check_if_moving_over_floor(50.0f, 150.0f) || o->oChuckyaUnkFC < -16) {
                        o->oSubAction++;
                        ;
                    }
            }
        } else {
            cur_obj_init_animation_with_sound(3);
            if (cur_obj_check_anim_frame(18)) {
                cur_obj_play_sound_2(SOUND_OBJ_UNKNOWN4);
                o->oChuckyaUnk88 = 2;
                o->oAction = 3;
                o->oInteractStatus &= ~(INT_STATUS_GRABBED_MARIO);
            }
        }
    }
}

void chuckya_act_3(void) {
    o->oForwardVel = 0;
    o->oVelY = 0;
    cur_obj_init_animation_with_sound(4);
    if (o->oTimer > 100)
        o->oAction = 0;
}

void chuckya_act_2(void) {
    if (o->oMoveFlags & (0x200 | 0x40 | 0x20 | 0x10 | 0x8 | 0x1)) {
        obj_mark_for_deletion(o);
        obj_spawn_loot_yellow_coins(o, 5, 20.0f);
        spawn_mist_particles_with_sound(SOUND_OBJ_CHUCKYA_DEATH);
    }
}

void (*sChuckyaActions[])(void) = { chuckya_act_0, chuckya_act_1, chuckya_act_2, chuckya_act_3 };

void chuckya_move(void) {
    cur_obj_update_floor_and_walls();
    cur_obj_call_action_function(sChuckyaActions);
    cur_obj_move_standard(-30);
    if (o->oInteractStatus & INT_STATUS_GRABBED_MARIO) {
        o->oAction = 1;
        o->oChuckyaUnk88 = 1;
        cur_obj_play_sound_2(SOUND_OBJ_UNKNOWN3);
    }
}

void bhv_chuckya_loop(void) {
    f32 sp2C = 20.0f;
    f32 sp28 = 50.0f;
    cur_obj_scale(2.0f);
    o->oInteractionSubtype |= INT_SUBTYPE_GRABS_MARIO;
    switch (o->oHeldState) {
        case HELD_FREE:
            chuckya_move();
            break;
        case HELD_HELD:
            cur_obj_unrender_and_reset_state(2, 0);
            break;
        case HELD_THROWN:
        case HELD_DROPPED:
            cur_obj_get_thrown_or_placed(sp2C, sp28, 2);
            break;
    }
    o->oInteractStatus = 0;
    print_debug_bottom_up("md %d", o->oAction);
}
