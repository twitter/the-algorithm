// 0x07011DE8
static Movtex wf_movtex_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   10),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-1023, 1024),
    MOV_TEX_4_BOX_TRIS(-1023, 4096),
    MOV_TEX_4_BOX_TRIS( 3226, 4096),
    MOV_TEX_4_BOX_TRIS( 3226, 1024),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0x78),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x7011E08
const struct MovtexQuadCollection wf_movtex_water[] = {
    {0, wf_movtex_water_data},
    {-1, NULL},
};
