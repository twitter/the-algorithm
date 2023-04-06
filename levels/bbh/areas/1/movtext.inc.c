// 0x07026DE4
static Movtex bbh_movtex_merry_go_round_water_entrance_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    0),
    MOV_TEX_ROT_SCALE(   20),
    MOV_TEX_4_BOX_TRIS(-4812,  1485),
    MOV_TEX_4_BOX_TRIS(-4812,  7270),
    MOV_TEX_4_BOX_TRIS(  640,  7270),
    MOV_TEX_4_BOX_TRIS(  640,  1485),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x07026E14
static Movtex bbh_movtex_merry_go_round_water_side_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(    0),
    MOV_TEX_ROT_SCALE(   20),
    MOV_TEX_4_BOX_TRIS( 1536, -1637),
    MOV_TEX_4_BOX_TRIS( 1536,  2662),
    MOV_TEX_4_BOX_TRIS( 3789,  2662),
    MOV_TEX_4_BOX_TRIS( 3789, -1637),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x07026E24
const struct MovtexQuadCollection bbh_movtex_merry_go_round_water_entrance[] = {
    {0, bbh_movtex_merry_go_round_water_entrance_data},
    {-1, NULL},
};

// 0x07026E34
const struct MovtexQuadCollection bbh_movtex_merry_go_round_water_side[] = {
    {1, bbh_movtex_merry_go_round_water_side_data},
    {-1, NULL},
};
