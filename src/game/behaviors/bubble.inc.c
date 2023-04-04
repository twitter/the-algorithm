// bubble.inc.c

void bhv_object_bubble_init(void) {
    o->oPosX += random_float() * 30.0f;
    o->oPosY += random_float() * 30.0f;
    o->oPosZ += random_float() * 30.0f;
}

void bhv_object_bubble_loop(void) {
    struct Object *bubbleSplash;
    f32 waterY = find_water_level(o->oPosX, o->oPosZ);
    f32 bubbleY = o->oPosY;

    if (bubbleY > waterY) {
        if (gFreeObjectList.next != NULL) {
            bubbleSplash = spawn_object_at_origin(o, 0, MODEL_SMALL_WATER_SPLASH, bhvBubbleSplash);
            bubbleSplash->oPosX = o->oPosX;
            bubbleSplash->oPosY = bubbleY + 5.0f;
            bubbleSplash->oPosZ = o->oPosZ;
        }

        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}
