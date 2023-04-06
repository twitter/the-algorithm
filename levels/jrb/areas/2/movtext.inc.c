// 0x0701137C
static Movtex jrb_movtex_sinked_boat_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   20),
    MOV_TEX_ROT_SCALE(    6),
    MOV_TEX_4_BOX_TRIS(-4095, -4095),
    MOV_TEX_4_BOX_TRIS(-4095,  4096),
    MOV_TEX_4_BOX_TRIS( 4096,  4096),
    MOV_TEX_4_BOX_TRIS( 4096, -4095),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0xB4),
    MOV_TEX_DEFINE(  TEXTURE_JRB_WATER),
    MOV_TEX_END(),
};

// 0x0701139C
const struct MovtexQuadCollection jrb_movtex_sinked_boat_water[] = {
    {0, jrb_movtex_sinked_boat_water_data},
    {-1, NULL},
};
