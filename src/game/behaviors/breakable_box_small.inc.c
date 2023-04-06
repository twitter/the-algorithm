// breakable_box.c.inc

struct ObjectHitbox sBreakableBoxSmallHitbox = {
    /* interactType:      */ INTERACT_GRABBABLE,
    /* downOffset:        */ 20,
    /* damageOrCoinValue: */ 0,
    /* health:            */ 1,
    /* numLootCoins:      */ 0,
    /* radius:            */ 150,
    /* height:            */ 250,
    /* hurtboxRadius:     */ 150,
    /* hurtboxHeight:     */ 250,
};

void bhv_breakable_box_small_init(void) {
    o->oGravity = 2.5f;
    o->oFriction = 0.99f;
    o->oBuoyancy = 1.4f;
    cur_obj_scale(0.4f);
    obj_set_hitbox(o, &sBreakableBoxSmallHitbox);
    o->oAnimState = 1;
    o->activeFlags |= 0x200;
}

void small_breakable_box_spawn_dust(void) {
    struct Object *sp24 = spawn_object(o, MODEL_SMOKE, bhvSmoke);
    sp24->oPosX += (s32)(random_float() * 80.0f) - 40;
    sp24->oPosZ += (s32)(random_float() * 80.0f) - 40;
}

void small_breakable_box_act_move(void) {
    s16 sp1E = object_step();

    obj_attack_collided_from_other_object(o);
    if (sp1E == 1)
        cur_obj_play_sound_2(SOUND_GENERAL_BOX_LANDING_2);
    if (sp1E & 1) {
        if (o->oForwardVel > 20.0f) {
            cur_obj_play_sound_2(SOUND_ENV_SLIDING);
            small_breakable_box_spawn_dust();
        }
    }

    if (sp1E & 2) {
        spawn_mist_particles();
        spawn_triangle_break_particles(20, 138, 0.7f, 3);
        obj_spawn_yellow_coins(o, 3);
        create_sound_spawner(SOUND_GENERAL_BREAK_BOX);
        o->activeFlags = 0;
    }

    obj_check_floor_death(sp1E, sObjFloor);
}

void breakable_box_small_released_loop(void) {
    o->oBreakableBoxSmallFramesSinceReleased++;

    // Begin flashing
    if (o->oBreakableBoxSmallFramesSinceReleased > 810) {
        if (o->oBreakableBoxSmallFramesSinceReleased & 1)
            o->header.gfx.node.flags |= GRAPH_RENDER_INVISIBLE;
        else
            o->header.gfx.node.flags &= ~GRAPH_RENDER_INVISIBLE;
    }

    // Despawn, and create a corkbox respawner
    if (o->oBreakableBoxSmallFramesSinceReleased > 900) {
        create_respawner(MODEL_BREAKABLE_BOX_SMALL, bhvBreakableBoxSmall, 3000);
        o->activeFlags = 0;
    }
}

void breakable_box_small_idle_loop(void) {
    switch (o->oAction) {
        case 0:
            small_breakable_box_act_move();
            break;

        case 100:
            obj_lava_death();
            break;

        case 101:
            o->activeFlags = 0;
            create_respawner(MODEL_BREAKABLE_BOX_SMALL, bhvBreakableBoxSmall, 3000);
            break;
    }

    if (o->oBreakableBoxSmallReleased == 1)
        breakable_box_small_released_loop();
}

void breakable_box_small_get_dropped(void) {
    cur_obj_become_tangible();
    cur_obj_enable_rendering();
    cur_obj_get_dropped();
    o->header.gfx.node.flags &= ~GRAPH_RENDER_INVISIBLE;
    o->oHeldState = 0;
    o->oBreakableBoxSmallReleased = 1;
    o->oBreakableBoxSmallFramesSinceReleased = 0;
}

void breakable_box_small_get_thrown(void) {
    cur_obj_become_tangible();
    cur_obj_enable_rendering_2();
    cur_obj_enable_rendering();
    o->header.gfx.node.flags &= ~GRAPH_RENDER_INVISIBLE;
    o->oHeldState = 0;
    o->oFlags &= ~0x08;
    o->oForwardVel = 40.0f;
    o->oVelY = 20.0f;
    o->oBreakableBoxSmallReleased = 1;
    o->oBreakableBoxSmallFramesSinceReleased = 0;
    o->activeFlags &= ~0x200;
}

void bhv_breakable_box_small_loop(void) {
    switch (o->oHeldState) {
        case 0:
            breakable_box_small_idle_loop();
            break;

        case 1:
            cur_obj_disable_rendering();
            cur_obj_become_intangible();
            break;

        case 2:
            breakable_box_small_get_thrown();
            break;

        case 3:
            breakable_box_small_get_dropped();
            break;
    }

    o->oInteractStatus = 0;
}
