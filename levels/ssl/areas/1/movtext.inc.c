// 0x07012758 - 0x07012778
static Movtex ssl_movtex_puddle_water_data[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   10),
    MOV_TEX_ROT_SCALE(    3),
    MOV_TEX_4_BOX_TRIS(-6911, -7167),
    MOV_TEX_4_BOX_TRIS(-6911, -4607),
    MOV_TEX_4_BOX_TRIS(-4223, -4607),
    MOV_TEX_4_BOX_TRIS(-4223, -7167),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER),
    MOV_TEX_END(),
};

// 0x07012778
const struct MovtexQuadCollection ssl_movtex_puddle_water[] = {
    {0, ssl_movtex_puddle_water_data},
    {-1, NULL},
};

// 0x07012788 - 0x070127A8
static Movtex ssl_movtex_toxbox_quicksand_large_mist[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   40),
    MOV_TEX_ROT_SCALE(    2),
    MOV_TEX_4_BOX_TRIS( 1024, -7065),
    MOV_TEX_4_BOX_TRIS( 1024,  -716),
    MOV_TEX_4_BOX_TRIS( 7578,  -716),
    MOV_TEX_4_BOX_TRIS( 7578, -7065),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER), // vertex shaded to another color
    MOV_TEX_END(),
};

// 0x070127A8 - 0x070127C8
static Movtex ssl_movtex_toxbox_quicksand_short_mist[] = {
    MOV_TEX_INIT_LOAD(    1),
    MOV_TEX_ROT_SPEED(   40),
    MOV_TEX_ROT_SCALE(    2),
    MOV_TEX_4_BOX_TRIS(-3993, -7065),
    MOV_TEX_4_BOX_TRIS(-3993, -4197),
    MOV_TEX_4_BOX_TRIS( 1024, -4197),
    MOV_TEX_4_BOX_TRIS( 1024, -7065),
    MOV_TEX_ROT(     ROTATE_COUNTER_CLOCKWISE),
    MOV_TEX_ALPHA(    0x96),
    MOV_TEX_DEFINE(  TEXTURE_WATER), // vertex shaded to another color
    MOV_TEX_END(),
};

// 0x070127C8 - 0x070127E0
const struct MovtexQuadCollection ssl_movtex_toxbox_quicksand_mist[] = {
    {51, ssl_movtex_toxbox_quicksand_large_mist},
    {52, ssl_movtex_toxbox_quicksand_short_mist},
    {-1, NULL},
};

// 0x070127E0
const Gfx ssl_dl_quicksand_begin[] = {
    gsSPBranchList(ssl_dl_quicksand_pit_begin),
};

// 0x070127E8
const Gfx ssl_dl_quicksand_end[] = {
    gsSPBranchList(ssl_dl_quicksand_pit_end),
};

// 0x070127F0 - 0x070128B8
Movtex ssl_movtex_tris_pyramid_quicksand[] = {
    MOV_TEX_SPD(       20),
    MOV_TEX_ROT_TRIS(-4096, -256,  1024,  23, 123, -15, 0, 0),
    MOV_TEX_ROT_TRIS(    0, -256,  1024, -15, 123, -23, 0, 2),
    MOV_TEX_ROT_TRIS(    0, -256, -3072, -23, 123,  15, 0, 4),
    MOV_TEX_ROT_TRIS(-4096, -256, -3072,  17, 123,  23, 0, 2),
    MOV_TEX_ROT_TRIS(-4096,    0,  2048,  17, 123, -21, 1, 0),
    MOV_TEX_ROT_TRIS(    0,    0,  2048,  -6, 126, -12, 1, 2),
    MOV_TEX_ROT_TRIS( 1024,    0,  1024, -14, 126,   2, 1, 2),
    MOV_TEX_ROT_TRIS( 1024,    0, -3072,  -6, 125, -16, 1, 4),
    MOV_TEX_ROT_TRIS(    0,    0, -4096,   7, 125, -15, 1, 4),
    MOV_TEX_ROT_TRIS(-4096,    0, -4096,  10, 125, -12, 1, 2),
    MOV_TEX_ROT_TRIS(-5120,    0, -3072,  14, 126,   4, 1, 2),
    MOV_TEX_ROT_TRIS(-5120,    0,  1024,  12, 126,  -6, 1, 0),
    MOV_TEX_ROT_END(),
};

// 0x070128B8 - 0x07012900
const Gfx ssl_dl_pyramid_quicksand[] = {
    gsSP2Triangles( 0,  4,  1, 0x0,  1,  4,  5, 0x0),
    gsSP2Triangles( 1,  6,  2, 0x0,  2,  6,  7, 0x0),
    gsSP2Triangles( 2,  8,  3, 0x0,  3,  8,  9, 0x0),
    gsSP2Triangles( 0,  3, 10, 0x0,  0, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012900 - 0x07012A08
Movtex ssl_movtex_tris_pyramid_corners_quicksand[] = {
    MOV_TEX_SPD(       20),
    MOV_TEX_ROT_TRIS(-4096, -256,  1024,  23, 123, -15, 0, 1),
    MOV_TEX_ROT_TRIS(    0, -256,  1024, -15, 123, -23, 0, 3),
    MOV_TEX_ROT_TRIS(    0, -256, -3072, -23, 123,  15, 0, 5),
    MOV_TEX_ROT_TRIS(-4096, -256, -3072,  17, 123,  23, 0, 3),
    MOV_TEX_ROT_TRIS(-4096,    0,  2048,  17, 123, -21, 1, 0),
    MOV_TEX_ROT_TRIS(    0,    0,  2048,  -6, 126, -12, 1, 2),
    MOV_TEX_ROT_TRIS( 1024,    0,  1024, -14, 126,   2, 1, 2),
    MOV_TEX_ROT_TRIS( 1024,    0, -3072,  -6, 125, -16, 1, 4),
    MOV_TEX_ROT_TRIS(    0,    0, -4096,   7, 125, -15, 1, 4),
    MOV_TEX_ROT_TRIS(-4096,    0, -4096,  10, 125, -12, 1, 2),
    MOV_TEX_ROT_TRIS(-5120,    0, -3072,  14, 126,   4, 1, 2),
    MOV_TEX_ROT_TRIS(-5120,    0,  1024,  12, 126,  -6, 1, 0),
    MOV_TEX_ROT_TRIS(-4608,    0,  1536,  15, 125, -14, 1, 1),
    MOV_TEX_ROT_TRIS(  512,    0,  1536, -10, 126,  -5, 1, 3),
    MOV_TEX_ROT_TRIS(  512,    0, -3584,   1, 125, -15, 1, 5),
    MOV_TEX_ROT_TRIS(-4608,    0, -3584,  12, 126,  -4, 1, 3),
    MOV_TEX_ROT_END(),
};

// 0x07012A08 - 0x07012A50
const Gfx ssl_dl_pyramid_corners_quicksand[] = {
    gsSP2Triangles( 0, 11, 12, 0x0,  0, 12,  4, 0x0),
    gsSP2Triangles( 1,  5, 13, 0x0,  1, 13,  6, 0x0),
    gsSP2Triangles( 2,  7, 14, 0x0,  2, 14,  8, 0x0),
    gsSP2Triangles( 3,  9, 15, 0x0,  3, 15, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012A50 - 0x07012B48
Movtex ssl_movtex_tris_sides_quicksand[] = {
    MOV_TEX_SPD(       20),
    MOV_TEX_ROT_TRIS(-8192, -512,  8704,   0, 113,  56, 0,  0),
    MOV_TEX_ROT_TRIS(-8192, -256,  8192,   0, 113,  56, 1,  0),
    MOV_TEX_ROT_TRIS(-8192,    0,  7680,   0, 106,  69, 2,  0),
    MOV_TEX_ROT_TRIS(    0, -512,  8704,   0, 113,  56, 0,  3),
    MOV_TEX_ROT_TRIS(    0, -256,  8192,   0, 113,  56, 1,  3),
    MOV_TEX_ROT_TRIS(    0,    0,  7680,   2, 122,  34, 2,  3),
    MOV_TEX_ROT_TRIS( 8704, -512,  8704,  29, 119,  29, 0,  6),
    MOV_TEX_ROT_TRIS( 8192, -256,  8192,  29, 119,  29, 1,  6),
    MOV_TEX_ROT_TRIS( 7680,    0,  7680,   6, 126,   6, 2,  6),
    MOV_TEX_ROT_TRIS( 8704, -512,     0,  56, 113,   0, 0,  9),
    MOV_TEX_ROT_TRIS( 8192, -256,     0,  56, 113,   0, 1,  9),
    MOV_TEX_ROT_TRIS( 7680,    0,     0,  20, 125,   0, 2,  9),
    MOV_TEX_ROT_TRIS( 8704, -512, -8192,  56, 113,   0, 0, 12),
    MOV_TEX_ROT_TRIS( 8192, -256, -8192,  56, 113,   0, 1, 12),
    MOV_TEX_ROT_TRIS( 7680,    0, -8192,  43, 119,   0, 2, 12),
    MOV_TEX_ROT_END(),
};

// 0x07012B48 - 0x07012BD0
const Gfx ssl_dl_sides_quicksand[] = {
    gsSP2Triangles( 2,  1,  5, 0x0,  1,  4,  5, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 4,  7,  5, 0x0,  5,  7,  8, 0x0),
    gsSP2Triangles( 4,  3,  7, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 8,  7, 11, 0x0, 11,  7, 10, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles(11, 10, 13, 0x0, 11, 13, 14, 0x0),
    gsSP2Triangles(10,  9, 13, 0x0, 13,  9, 12, 0x0),
    gsSPEndDisplayList(),
};
