// 0x0700E33C
static Movtex thi_movtex_area2_short_side_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    3),
    MOV_TEX_ROT_SCALE(    3),
    MOV_TEX_4_BOX_TRIS(-2457, -2457),
    MOV_TEX_4_BOX_TRIS(-2457,  1874),
    MOV_TEX_4_BOX_TRIS(-1535,  1874),
    MOV_TEX_4_BOX_TRIS(-1535, -2457),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700E35C
static Movtex thi_movtex_area2_large_side_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    3),
    MOV_TEX_ROT_SCALE(    3),
    MOV_TEX_4_BOX_TRIS(-1381,  1352),
    MOV_TEX_4_BOX_TRIS(-1381,  2458),
    MOV_TEX_4_BOX_TRIS( 2089,  2458),
    MOV_TEX_4_BOX_TRIS( 2089,  1352),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700E37C
static Movtex thi_movtex_area2_mountain_top_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    3),
    MOV_TEX_ROT_SCALE(    3),
    MOV_TEX_4_BOX_TRIS( -306,  -766),
    MOV_TEX_4_BOX_TRIS( -306,  -152),
    MOV_TEX_4_BOX_TRIS(  307,  -152),
    MOV_TEX_4_BOX_TRIS(  307,  -766),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700E39C
const struct MovtexQuadCollection thi_movtex_area2_water[] = {
    {0, thi_movtex_area2_short_side_water_data},
    {1, thi_movtex_area2_large_side_water_data},
    {2, thi_movtex_area2_mountain_top_water_data},
    {-1, NULL},
};
