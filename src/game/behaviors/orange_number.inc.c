// orange_number.inc.c

void bhv_orange_number_init(void) {
    o->oAnimState = o->oBehParams2ndByte;
    o->oVelY = 26.0f;
}

void bhv_orange_number_loop(void) {
    o->oPosY += o->oVelY;
    o->oVelY -= 2.0f;

    if (o->oVelY < -21.0f) {
        o->oVelY = 14.0;
    }

    if (o->oTimer == 35) {
        struct Object *sp1C = spawn_object(o, MODEL_SPARKLES, bhvGoldenCoinSparkles);
        sp1C->oPosY -= 30.0f;
        o->activeFlags = ACTIVE_FLAG_DEACTIVATED;
    }
}
