// coin.c.inc

// sp18 = collisionFlagsPtr

static struct ObjectHitbox sMovingYellowCoinHitbox = {
    /* interactType:      */ INTERACT_COIN,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 1,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 100,
    /* height:            */ 64,
    /* hurtboxRadius:     */ 0,
    /* hurtboxHeight:     */ 0,
};

static struct ObjectHitbox sMovingBlueCoinHitbox = {
    /* interactType:      */ INTERACT_COIN,
    /* downOffset:        */ 0,
    /* damageOrCoinValue: */ 5,
    /* health:            */ 0,
    /* numLootCoins:      */ 0,
    /* radius:            */ 100,
    /* height:            */ 64,
    /* hurtboxRadius:     */ 0,
    /* hurtboxHeight:     */ 0,
};

s32 coin_step(s16 *collisionFlagsPtr) {
    *collisionFlagsPtr = object_step();

    obj_check_floor_death(*collisionFlagsPtr, sObjFloor);

    if ((*collisionFlagsPtr & 0x1) != 0 && (*collisionFlagsPtr & 0x8) == 0) /* bit 0, bit 3 */
    {
        cur_obj_play_sound_2(SOUND_GENERAL_COIN_DROP);
        return 1;
    }

    return 0;
}

void moving_coin_flicker(void) {
    s16 collisionFlags;

    coin_step(&collisionFlags);
    obj_flicker_and_disappear(o, 0);
}

void coin_collected(void) {
    spawn_object(o, MODEL_SPARKLES, bhvGoldenCoinSparkles);
    o->activeFlags = 0;
}

void bhv_moving_yellow_coin_init(void) {
    o->oGravity = 3.0f;
    o->oFriction = 1.0f;
    o->oBuoyancy = 1.5f;

    obj_set_hitbox(o, &sMovingYellowCoinHitbox);
}

void bhv_moving_yellow_coin_loop(void) {
    s16 collisionFlags;
    switch (o->oAction) {
        case MOV_YCOIN_ACT_IDLE:
            coin_step(&collisionFlags);

            if (o->oTimer < 10)
                cur_obj_become_intangible();
            else
                cur_obj_become_tangible();

            if (o->oTimer >= 301)
                o->oAction = 1;
            break;

        case MOV_YCOIN_ACT_BLINKING:
            moving_coin_flicker();
            break;

        case MOV_YCOIN_ACT_LAVA_DEATH:
            o->activeFlags = 0;
            break;

        case MOV_YCOIN_ACT_DEATH_PLANE_DEATH:
            o->activeFlags = 0;
            break;
    }

    if ((o->oInteractStatus & INT_STATUS_INTERACTED) != 0) /* bit 15 */
    {
        coin_collected();
        o->oInteractStatus = 0;
    }
}

void bhv_moving_blue_coin_init(void) {
    o->oGravity = 5.0f;
    o->oFriction = 1.0f;
    o->oBuoyancy = 1.5f;

    obj_set_hitbox(o, &sMovingBlueCoinHitbox);
}

void bhv_moving_blue_coin_loop(void) {
#ifdef VERSION_EU
    s32 collisionFlags;
#else
    s16 collisionFlags;
#endif

    switch (o->oAction) {
        case MOV_BCOIN_ACT_STILL:
            if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 1500))
                o->oAction = 1;
            break;

        case MOV_BCOIN_ACT_MOVING:
            collisionFlags = object_step();
            if ((collisionFlags & OBJ_COL_FLAG_GROUNDED)) /* bit 0 */
            {
                o->oForwardVel += 25.0f;
                if (!(collisionFlags & OBJ_COL_FLAG_NO_Y_VEL))
                    cur_obj_play_sound_2(SOUND_GENERAL_COIN_DROP); /* bit 3 */
            } else
                o->oForwardVel *= 0.98;

            if (o->oForwardVel > 75.0)
                o->oForwardVel = 75.0f;

            obj_flicker_and_disappear(o, 600);
            break;
    }

    if ((o->oInteractStatus & INT_STATUS_INTERACTED) != 0) /* bit 15 */
    {
        coin_collected();
        o->oInteractStatus = 0;
    }
}

void bhv_blue_coin_sliding_jumping_init(void) {
    o->oGravity = 3.0;
    o->oFriction = 0.98;
    o->oBuoyancy = 1.5;

    obj_set_hitbox(o, &sMovingBlueCoinHitbox);
}

void blue_coin_sliding_away_from_mario(void) {
    s16 collisionFlags;

    o->oForwardVel = 15.0;
    o->oMoveAngleYaw = o->oAngleToMario + 0x8000;

    if (coin_step(&collisionFlags) != 0)
        o->oVelY += 18.0f;
    if ((collisionFlags & 0x2) != 0)
        o->oAction = 3; /* bit 1 */

    if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 1000) == 0)
        o->oAction = 2;
}

void blue_coin_sliding_slow_down(void) {
    s16 collisionFlags;

    coin_step(&collisionFlags);

    if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 500) == 1)
        o->oAction = 1;

    if (o->oTimer >= 151)
        o->oAction = 3;
}

void bhv_blue_coin_sliding_loop(void) {
    s16 collisionFlags;

    switch (o->oAction) {
        case 0:
            if (is_point_within_radius_of_mario(o->oPosX, o->oPosY, o->oPosZ, 500) == 1)
                o->oAction = 1;

            set_object_visibility(o, 3000);
            break;

        case 1:
            blue_coin_sliding_away_from_mario();
            break;

        case 2:
            blue_coin_sliding_slow_down();
            set_object_visibility(o, 3000);
            break;

        case 3:
            coin_step(&collisionFlags);
            if (o->oTimer >= 61)
                o->oAction = 4;
            break;

        case 4:
            moving_coin_flicker();
            break;

        case 100:
            o->activeFlags = 0;
            break;

        case 101:
            o->activeFlags = 0;
            break;
    }

    if ((o->oInteractStatus & INT_STATUS_INTERACTED) != 0) /* bit 15 */
    {
        coin_collected();
        o->oInteractStatus = 0;
    }
}

void bhv_blue_coin_jumping_loop(void) {
    s16 collisionFlags;

    switch (o->oAction) {
        case 0:
            if (o->oTimer == 0) {
                cur_obj_become_intangible();
                o->oVelY = 50.0;
            }

            object_step();

            if (o->oTimer == 15) {
                cur_obj_become_tangible();
                o->oAction = 1;
            }
            break;

        case 1:
            blue_coin_sliding_away_from_mario();
            break;

        case 2:
            blue_coin_sliding_slow_down();
            set_object_visibility(o, 3000);
            break;

        case 3:
            coin_step(&collisionFlags);
            if (o->oTimer >= 61)
                o->oAction = 4;
            break;

        case 4:
            moving_coin_flicker();
            break;
    }

    if ((o->oInteractStatus & INT_STATUS_INTERACTED) != 0) /* bit 15 */
    {
        coin_collected();
        o->oInteractStatus = 0;
    }
}
