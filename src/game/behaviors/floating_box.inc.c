// floating_box.c.inc

void bhv_jrb_floating_box_loop(void) {
    o->oPosY = o->oHomeY + sins(o->oTimer * 0x400) * 10.0f;
}
