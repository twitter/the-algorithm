// castle_flag.inc.c

void bhv_castle_flag_init(void) {
    o->header.gfx.unk38.animFrame = random_float() * 28.0f;
}
