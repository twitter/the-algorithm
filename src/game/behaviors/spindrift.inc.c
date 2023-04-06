// spindrift.c.inc

struct ObjectHitbox sSpindriftHitbox = {
    /* interactType: */ INTERACT_BOUNCE_TOP,
    /* downOffset: */ 0,
    /* damageOrCoinValue: */ 2,
    /* health: */ 1,
    /* numLootCoins: */ 3,
    /* radius: */ 90,
    /* height: */ 80,
    /* hurtboxRadius: */ 80,
    /* hurtboxHeight: */ 70,
};

void bhv_spindrift_loop(void) {
    o->activeFlags |= 0x400;
    if (cur_obj_set_hitbox_and_die_if_attacked(&sSpindriftHitbox, SOUND_OBJ_DYING_ENEMY1, 0))
        cur_obj_change_action(1);
    cur_obj_update_floor_and_walls();
    switch (o->oAction) {
        case 0:
            approach_forward_vel(&o->oForwardVel, 4.0f, 1.0f);
            if (cur_obj_lateral_dist_from_mario_to_home() > 1000.0f)
                o->oAngleToMario = cur_obj_angle_to_home();
            else if (o->oDistanceToMario > 300.0f)
                o->oAngleToMario = obj_angle_to_object(o, gMarioObject);
            cur_obj_rotate_yaw_toward(o->oAngleToMario, 0x400);
            break;
        case 1:
            o->oFlags &= ~8;
            o->oForwardVel = -10.0f;
            if (o->oTimer > 20) {
                o->oAction = 0;
                o->oInteractStatus = 0;
                o->oFlags |= 8;
            }
            break;
    }
    cur_obj_move_standard(-60);
}
