/**
 * Behavior for bhvSslMovingPyramidWall.
 *
 * This controls the moving walls found within Shifting Sand Land's pyramid.
 * Each wall starts at an initial position and moves up or down at a constant
 * speed.
 */

/**
 * Initialize the moving wall to be at one of three possible initial starting
 * positions.
 */
void bhv_ssl_moving_pyramid_wall_init(void) {

    switch (o->oBehParams2ndByte) {
        case PYRAMID_WALL_BP_POSITION_HIGH:
            break;

        case PYRAMID_WALL_BP_POSITION_MIDDLE:
            o->oPosY -= 256.0f;
            o->oTimer += 50;
            break;

        case PYRAMID_WALL_BP_POSITION_LOW:
            o->oPosY -= 512.0f;
            o->oAction = PYRAMID_WALL_ACT_MOVING_UP;
            break;
    }
}

/**
 * Move up or down at a constant velocity for a period of time, then switch to
 * do the other.
 */
void bhv_ssl_moving_pyramid_wall_loop(void) {
    switch (o->oAction) {
        case PYRAMID_WALL_ACT_MOVING_DOWN:
            o->oVelY = -5.12f;
            if (o->oTimer == 100)
                o->oAction = PYRAMID_WALL_ACT_MOVING_UP;
            break;

        case PYRAMID_WALL_ACT_MOVING_UP:
            o->oVelY = 5.12f;
            if (o->oTimer == 100)
                o->oAction = PYRAMID_WALL_ACT_MOVING_DOWN;
            break;
    }

    o->oPosY += o->oVelY;
}
