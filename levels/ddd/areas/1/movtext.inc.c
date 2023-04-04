// 0x0700FC78
static Movtex ddd_movtex_area1_water_data[] = {
    MOV_TEX_INIT_LOAD(    2),
    MOV_TEX_ROT_SPEED(   20), // area 1 general water
    MOV_TEX_ROT_SCALE(   20),
    MOV_TEX_4_BOX_TRIS(-7167, -4095),
    MOV_TEX_4_BOX_TRIS(-7167,  4096),
    MOV_TEX_4_BOX_TRIS( 1024,  4096),
    MOV_TEX_4_BOX_TRIS( 1024, -4095),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0xA0),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
    MOV_TEX_ROT_SPEED(    0), // entrance to area 2 water
    MOV_TEX_ROT_SCALE(    5),
    MOV_TEX_4_BOX_TRIS( 2048,  -768),
    MOV_TEX_4_BOX_TRIS( 2048,   768),
    MOV_TEX_4_BOX_TRIS( 6144,   768),
    MOV_TEX_4_BOX_TRIS( 6144,  -768),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0xA0),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700FCB4
const struct MovtexQuadCollection ddd_movtex_area1_water[] = {
    {0, ddd_movtex_area1_water_data},
    {-1, NULL},
};
