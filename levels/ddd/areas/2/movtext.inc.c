// 0x0700FCC4
static Movtex ddd_movtex_area2_water_data[] = {
    MOV_TEX_INIT_LOAD(    2),
    MOV_TEX_ROT_SPEED(   20), // area 2 general water
    MOV_TEX_ROT_SPEED(   20),
    MOV_TEX_4_BOX_TRIS(    0, -5119),
    MOV_TEX_4_BOX_TRIS(    0,  7168),
    MOV_TEX_4_BOX_TRIS( 8192,  7168),
    MOV_TEX_4_BOX_TRIS( 8192, -5119),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0xA0),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
    MOV_TEX_ROT_SPEED(    0), // entrance to area 1 water
    MOV_TEX_ROT_SPEED(    5),
    MOV_TEX_4_BOX_TRIS(-6144,  -768),
    MOV_TEX_4_BOX_TRIS(-6144,   768),
    MOV_TEX_4_BOX_TRIS(-2048,   768),
    MOV_TEX_4_BOX_TRIS(-2048,  -768),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0xA0),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700FD00
const struct MovtexQuadCollection ddd_movtex_area2_water[] = {
    {0, ddd_movtex_area2_water_data},
    {-1, NULL},
};
