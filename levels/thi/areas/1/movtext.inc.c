// 0x0700E2BC
static Movtex thi_movtex_area1_short_side_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   10),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-8191, -8191),
    MOV_TEX_4_BOX_TRIS(-8191,  6246),
    MOV_TEX_4_BOX_TRIS(-5119,  6246),
    MOV_TEX_4_BOX_TRIS(-5119, -8191),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700E2DC
static Movtex thi_movtex_area1_large_side_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   10),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-4607,  4506),
    MOV_TEX_4_BOX_TRIS(-4607,  8192),
    MOV_TEX_4_BOX_TRIS( 6963,  8192),
    MOV_TEX_4_BOX_TRIS( 6963,  4506),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700E2FC
static Movtex thi_movtex_area1_mountain_top_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   10),
    MOV_TEX_ROT_SCALE(   10),
    MOV_TEX_4_BOX_TRIS(-1023, -2555),
    MOV_TEX_4_BOX_TRIS(-1023,  -507),
    MOV_TEX_4_BOX_TRIS( 1024,  -507),
    MOV_TEX_4_BOX_TRIS( 1024, -2555),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x0700E31C
const struct MovtexQuadCollection thi_movtex_area1_water[] = {
    {0, thi_movtex_area1_short_side_water_data},
    {1, thi_movtex_area1_large_side_water_data},
    {2, thi_movtex_area1_mountain_top_water_data},
    {-1, NULL},
};
