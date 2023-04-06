// door.c.inc

struct DoorAction
{
    u32 flag;
    s32 action;
};

struct DoorAction D_8032F300[] = { { 0x40000, 3 }, { 0x80000, 4 }, { 0x10000, 1 }, { 0x20000, 2 }, { -1, 0 }, };

s32 D_8032F328[] = { SOUND_GENERAL_OPEN_WOOD_DOOR, SOUND_GENERAL_OPEN_IRON_DOOR };

s32 D_8032F330[] = { SOUND_GENERAL_CLOSE_WOOD_DOOR, SOUND_GENERAL_CLOSE_IRON_DOOR };

void door_animation_and_reset(s32 sp18) {
    cur_obj_init_animation_with_sound(sp18);
    if (cur_obj_check_if_near_animation_end())
        o->oAction = 0;
}

void set_door_camera_event(void) {
    if (segmented_to_virtual(bhvDoor) == o->behavior)
        gPlayerCameraState->cameraEvent = CAM_EVENT_DOOR;
    else
        gPlayerCameraState->cameraEvent = CAM_EVENT_DOOR_WARP;
    gPlayerCameraState->usedObj = o;
}

void play_door_open_noise(void) {
    s32 sp1C = cur_obj_has_model(MODEL_HMC_METAL_DOOR);
    if (o->oTimer == 0) {
        cur_obj_play_sound_2(D_8032F328[sp1C]);
        gTimeStopState |= TIME_STOP_MARIO_OPENED_DOOR;
    }
    if (o->oTimer == 70) {
        cur_obj_play_sound_2(D_8032F330[sp1C]);
    }
}

void play_warp_door_open_noise(void) {
    s32 sp1C = cur_obj_has_model(MODEL_HMC_METAL_DOOR);
    if (o->oTimer == 30)
        cur_obj_play_sound_2(D_8032F330[sp1C]);
}

void bhv_door_loop(void) {
    s32 sp1C = 0;
    
    while (D_8032F300[sp1C].flag != (u32)~0) {
        if (cur_obj_clear_interact_status_flag(D_8032F300[sp1C].flag)) {
            set_door_camera_event();
            cur_obj_change_action(D_8032F300[sp1C].action);
        }
        sp1C++;
    }

    switch (o->oAction) {
        case 0:
            cur_obj_init_animation_with_sound(0);
            break;
        case 1:
            door_animation_and_reset(1);
            play_door_open_noise();
            break;
        case 2:
            door_animation_and_reset(2);
            play_door_open_noise();
            break;
        case 3:
            door_animation_and_reset(3);
            play_warp_door_open_noise();
            break;
        case 4:
            door_animation_and_reset(4);
            play_warp_door_open_noise();
            break;
    }
    if (o->oAction == 0)
        load_object_collision_model();
    bhv_star_door_loop_2();
}

void bhv_door_init(void) {
    f32 x = o->oPosX;
    f32 z = o->oPosZ;
    struct Surface *floor;
    find_floor(x, o->oPosY, z, &floor);
    if (floor != NULL) {
        o->oDoorUnkF8 = floor->room;
    }

    x = o->oPosX + sins(o->oMoveAngleYaw) * 200.0f;
    z = o->oPosZ + coss(o->oMoveAngleYaw) * 200.0f;
    find_floor(x, o->oPosY, z, &floor);
    if (floor != NULL) {
        o->oDoorUnkFC = floor->room;
    }

    x = o->oPosX + sins(o->oMoveAngleYaw) * -200.0f;
    z = o->oPosZ + coss(o->oMoveAngleYaw) * -200.0f;
    find_floor(x, o->oPosY, z, &floor);
    if (floor != NULL) {
        o->oDoorUnk100 = floor->room;
    }

    if (o->oDoorUnkF8 > 0 && o->oDoorUnkF8 < 60) {
        gDoorAdjacentRooms[o->oDoorUnkF8][0] = o->oDoorUnkFC;
        gDoorAdjacentRooms[o->oDoorUnkF8][1] = o->oDoorUnk100;
    }
}

void bhv_star_door_loop_2(void) {
    s32 sp4 = 0;
    if (gMarioCurrentRoom != 0) {
        if (o->oDoorUnkF8 == gMarioCurrentRoom)
            sp4 = 1;
        else if (gMarioCurrentRoom == o->oDoorUnkFC)
            sp4 = 1;
        else if (gMarioCurrentRoom == o->oDoorUnk100)
            sp4 = 1;
        else if (gDoorAdjacentRooms[gMarioCurrentRoom][0] == o->oDoorUnkFC)
            sp4 = 1;
        else if (gDoorAdjacentRooms[gMarioCurrentRoom][0] == o->oDoorUnk100)
            sp4 = 1;
        else if (gDoorAdjacentRooms[gMarioCurrentRoom][1] == o->oDoorUnkFC)
            sp4 = 1;
        else if (gDoorAdjacentRooms[gMarioCurrentRoom][1] == o->oDoorUnk100)
            sp4 = 1;
    } else
        sp4 = 1;
    if (sp4 == 1) {
        o->header.gfx.node.flags |= GRAPH_RENDER_ACTIVE;
        D_8035FEE4++;
    }
    if (sp4 == 0) {
        o->header.gfx.node.flags &= ~GRAPH_RENDER_ACTIVE;
    }
    o->oDoorUnk88 = sp4;
}
