// 0x07006E4C - 0x07006E7C
static Movtex castle_courtyard_movtex_star_statue_water_data[] = {
    MOV_TEX_INIT_LOAD(   1),
    MOV_TEX_ROT_SPEED(   5),
    MOV_TEX_ROT_SCALE(   3),
    MOV_TEX_4_BOX_TRIS(-656, -2405),
    MOV_TEX_4_BOX_TRIS(-656, -1074),
    MOV_TEX_4_BOX_TRIS( 674, -1074),
    MOV_TEX_4_BOX_TRIS( 674, -2405),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

const struct MovtexQuadCollection castle_courtyard_movtex_star_statue_water[] = {
    {0, castle_courtyard_movtex_star_statue_water_data},
    {-1, NULL},
};
