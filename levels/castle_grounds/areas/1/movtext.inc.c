// 0x07010E40 - 0x07010E60
static Movtex castle_grounds_movtex_moat_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   20),
    MOV_TEX_ROT_SCALE(   15),
    MOV_TEX_4_BOX_TRIS(-7129, -7222),
    MOV_TEX_4_BOX_TRIS(-7129,   -58),
    MOV_TEX_4_BOX_TRIS( 8253,   -58),
    MOV_TEX_4_BOX_TRIS( 8253, -7222),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x07010E60 - 0x07010E80
static Movtex castle_grounds_movtex_lake_water_data[] = {
    MOV_TEX_INIT_LOAD(   1),
    MOV_TEX_ROT_SPEED(  15),
    MOV_TEX_ROT_SCALE(  10),
    MOV_TEX_4_BOX_TRIS(1024,  -58),
    MOV_TEX_4_BOX_TRIS(1024, 8137),
    MOV_TEX_4_BOX_TRIS(8230, 8137),
    MOV_TEX_4_BOX_TRIS(8230,  -58),
    MOV_TEX_ROT(     ROTATE_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x07010E80
const struct MovtexQuadCollection castle_grounds_movtex_water[] = {
    {0, castle_grounds_movtex_moat_water_data},
    {1, castle_grounds_movtex_lake_water_data},
    {-1, NULL},
};

Movtex castle_grounds_movtex_tris_waterfall[] = {
    MOV_TEX_SPD(    70),
    MOV_TEX_TRIS(-4469, -800, -6413, 0, 0),
    MOV_TEX_TRIS(-5525, 1171, -7026, 2, 0),
    MOV_TEX_TRIS(-6292, 2028, -7463, 4, 0),
    MOV_TEX_TRIS(-7302, 2955, -7461, 6, 0),
    MOV_TEX_TRIS(-4883, -800, -5690, 0, 3),
    MOV_TEX_TRIS(-5547, 1110, -6097, 2, 3),
    MOV_TEX_TRIS(-6732, 2587, -6770, 4, 3),
    MOV_TEX_TRIS(-7603, 3004, -7160, 6, 3),
    MOV_TEX_TRIS(-5580, -800, -4740, 0, 6),
    MOV_TEX_TRIS(-6205, 1068, -5347, 2, 6),
    MOV_TEX_TRIS(-7249, 2566, -6192, 4, 6),
    MOV_TEX_TRIS(-6895, -800, -4714, 0, 9),
    MOV_TEX_TRIS(-7201, 1083, -5071, 2, 9),
    MOV_TEX_TRIS(-7578, 2042, -5766, 4, 9),
    MOV_TEX_TRIS(-8132, 2961, -6761, 6, 9),
};

// 0x07010F30 - 0x07010FC0
const Gfx castle_grounds_dl_waterfall[] = {
    gsSP2Triangles( 0,  1,  5, 0x0,  0,  5,  4, 0x0),
    gsSP2Triangles( 1,  2,  6, 0x0,  1,  6,  5, 0x0),
    gsSP2Triangles( 2,  3,  6, 0x0,  3,  7,  6, 0x0),
    gsSP2Triangles( 4,  5,  9, 0x0,  4,  9,  8, 0x0),
    gsSP2Triangles( 5,  6,  9, 0x0,  6, 10,  9, 0x0),
    gsSP2Triangles( 6,  7, 10, 0x0,  8,  9, 12, 0x0),
    gsSP2Triangles( 8, 12, 11, 0x0,  9, 10, 13, 0x0),
    gsSP2Triangles( 9, 13, 12, 0x0, 10,  7, 14, 0x0),
    gsSP1Triangle(10, 14, 13, 0x0),
    gsSPEndDisplayList(),
};
