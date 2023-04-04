// castle_flag.inc.c

void bhv_castle_flag_init(void) {
    o->header.gfx.animInfo.animFrame = random_float() * 28.0f;
}
