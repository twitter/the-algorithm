// grill_door.c.inc

extern u8 bob_seg7_collision_gate[];
extern u8 hmc_seg7_collision_0702B65C[];
struct Struct8032FCE8 D_8032FCE8[] = { { 320, MODEL_BOB_BARS_GRILLS, bob_seg7_collision_gate },
                                       { 410, MODEL_HMC_RED_GRILLS, hmc_seg7_collision_0702B65C } };

void bhv_openable_cage_door_loop(void) {
    if (gCurrentObject->oAction == 0) {
        if (gCurrentObject->parentObj->oOpenableGrillUnk88 != 0)
            gCurrentObject->oAction++;
    } else if (gCurrentObject->oAction == 1) {
        if (gCurrentObject->oTimer < 64)
            gCurrentObject->oMoveAngleYaw -= gCurrentObject->oBehParams2ndByte * 0x100;
        else
            gCurrentObject->oAction++;
    }
}

void bhv_openable_grill_loop(void) {
    struct Object *sp3C;
    s32 sp38;
    switch (o->oAction) {
        case 0:
            sp38 = o->oBehParams2ndByte;
            sp3C = spawn_object_relative(-1, D_8032FCE8[sp38].unk0, 0, 0, o, D_8032FCE8[sp38].unk1,
                                         bhvOpenableCageDoor);
            sp3C->oMoveAngleYaw += 0x8000;
            obj_set_collision_data(sp3C, D_8032FCE8[sp38].unk2);
            sp3C = spawn_object_relative(1, -D_8032FCE8[sp38].unk0, 0, 0, o, D_8032FCE8[sp38].unk1,
                                         bhvOpenableCageDoor);
            obj_set_collision_data(sp3C, D_8032FCE8[sp38].unk2);
            o->oAction++;
            break;
        case 1:
            if ((o->oOpenableGrillUnkF4 = cur_obj_nearest_object_with_behavior(bhvFloorSwitchGrills))
                != NULL)
                o->oAction++;
            break;
        case 2:
            sp3C = o->oOpenableGrillUnkF4;
            if (sp3C->oAction == 2) {
                o->oOpenableGrillUnk88 = 2;
                cur_obj_play_sound_2(SOUND_GENERAL_CAGE_OPEN);
                o->oAction++;
                if (o->oBehParams2ndByte != 0)
                    play_puzzle_jingle();
            }
            break;
        case 3:
            break;
    }
}
