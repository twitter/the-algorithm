// Cannon Barrel

// 0x08005878
static const Lights1 cannon_barrel_seg8_lights_08005878 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x08005890
static const Lights1 cannon_barrel_seg8_lights_08005890 = gdSPDefLights1(
    0x00, 0x00, 0x0f,
    0x00, 0x00, 0x32, 0x28, 0x28, 0x28
);

// 0x080058A8
ALIGNED8 static const Texture cannon_barrel_seg8_texture_080058A8[] = {
#include "actors/cannon_barrel/cannon_barrel.rgba16.inc.c"
};

// 0x080060A8
static const Vtx cannon_barrel_seg8_vertex_080060A8[] = {
    {{{   -40,    236,     41}, 0, {   176,    748}, {0x45, 0x5d, 0xcd, 0xff}}},
    {{{   -56,    236,      0}, 0, {    64,    480}, {0x66, 0x49, 0x0d, 0xff}}},
    {{{   -81,    236,      0}, 0, {   -98,    480}, {0xb8, 0x68, 0x09, 0xff}}},
    {{{   -57,    236,     58}, 0, {    60,    864}, {0xcc, 0x5c, 0x44, 0xff}}},
    {{{   -40,    236,    -40}, 0, {   176,    210}, {0x2b, 0x68, 0x3a, 0xff}}},
    {{{   -57,    236,    -57}, 0, {    60,     94}, {0xae, 0x49, 0xc2, 0xff}}},
    {{{     0,    236,    -81}, 0, {   444,    -66}, {0xf4, 0x5c, 0xab, 0xff}}},
    {{{     0,    236,    -56}, 0, {   444,     98}, {0xf6, 0x5c, 0x56, 0xff}}},
    {{{    41,    236,    -40}, 0, {   714,    210}, {0xbc, 0x5d, 0x33, 0xff}}},
    {{{    58,    236,    -57}, 0, {   830,     94}, {0x34, 0x5c, 0xbb, 0xff}}},
    {{{    57,    236,      0}, 0, {   826,    480}, {0xaa, 0x5c, 0xf6, 0xff}}},
    {{{    82,    236,      0}, 0, {   990,    480}, {0x55, 0x5c, 0xf4, 0xff}}},
    {{{    58,    236,     58}, 0, {   830,    864}, {0x3a, 0x68, 0x2b, 0xff}}},
    {{{    41,    236,     41}, 0, {   714,    748}, {0xc3, 0x4a, 0xae, 0xff}}},
    {{{     0,    236,     57}, 0, {   444,    860}, {0x09, 0x67, 0xb8, 0xff}}},
    {{{     0,    236,     82}, 0, {   444,   1022}, {0x0d, 0x49, 0x66, 0xff}}},
};

// 0x080061A8
static const Vtx cannon_barrel_seg8_vertex_080061A8[] = {
    {{{    41,    236,     41}, 0, {     0,      0}, {0xc3, 0x4a, 0xae, 0xff}}},
    {{{    41,    -19,     41}, 0, {     0,      0}, {0xae, 0x4a, 0xc3, 0xff}}},
    {{{     0,    -19,     57}, 0, {     0,      0}, {0xf3, 0x49, 0x9a, 0xff}}},
    {{{   -40,    236,    -40}, 0, {     0,      0}, {0x2b, 0x68, 0x3a, 0xff}}},
    {{{   -40,    -19,    -40}, 0, {     0,      0}, {0x2a, 0x73, 0x20, 0xff}}},
    {{{     0,    -19,    -56}, 0, {     0,      0}, {0x10, 0x2a, 0x76, 0xff}}},
    {{{   -56,    236,      0}, 0, {     0,      0}, {0x66, 0x49, 0x0d, 0xff}}},
    {{{   -56,    -19,      0}, 0, {     0,      0}, {0x76, 0x2a, 0xf1, 0xff}}},
    {{{    41,    -19,    -40}, 0, {     0,      0}, {0xc3, 0x4a, 0x52, 0xff}}},
    {{{   -40,    -19,     41}, 0, {     0,      0}, {0x3e, 0x4a, 0xae, 0xff}}},
    {{{    57,    -19,      0}, 0, {     0,      0}, {0x9a, 0x49, 0x0d, 0xff}}},
    {{{   -40,    236,     41}, 0, {     0,      0}, {0x45, 0x5d, 0xcd, 0xff}}},
    {{{     0,    236,     57}, 0, {     0,      0}, {0x09, 0x67, 0xb8, 0xff}}},
    {{{    57,    236,      0}, 0, {     0,      0}, {0xaa, 0x5c, 0xf6, 0xff}}},
    {{{    41,    236,    -40}, 0, {     0,      0}, {0xbc, 0x5d, 0x33, 0xff}}},
    {{{     0,    236,    -56}, 0, {     0,      0}, {0xf6, 0x5c, 0x56, 0xff}}},
};

// 0x080062A8
static const Vtx cannon_barrel_seg8_vertex_080062A8[] = {
    {{{    82,    -19,      0}, 0, {     0,      0}, {0x74, 0xce, 0x0b, 0xff}}},
    {{{    58,    -19,     58}, 0, {     0,      0}, {0x49, 0xce, 0x5a, 0xff}}},
    {{{     0,    -71,      0}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{     0,    -19,     82}, 0, {     0,      0}, {0xf4, 0xce, 0x74, 0xff}}},
    {{{    58,    236,     58}, 0, {     0,      0}, {0x3a, 0x68, 0x2b, 0xff}}},
    {{{     0,    236,     82}, 0, {     0,      0}, {0x0d, 0x49, 0x66, 0xff}}},
    {{{    82,    236,      0}, 0, {     0,      0}, {0x55, 0x5c, 0xf4, 0xff}}},
    {{{   -57,    236,     58}, 0, {     0,      0}, {0xcc, 0x5c, 0x44, 0xff}}},
    {{{   -57,    -19,     58}, 0, {     0,      0}, {0xa6, 0xcf, 0x49, 0xff}}},
    {{{    58,    -19,    -57}, 0, {     0,      0}, {0x5a, 0xcf, 0xb6, 0xff}}},
    {{{    58,    236,    -57}, 0, {     0,      0}, {0x34, 0x5c, 0xbb, 0xff}}},
    {{{     0,    -19,    -81}, 0, {     0,      0}, {0x0b, 0xcf, 0x8c, 0xff}}},
    {{{     0,    236,    -81}, 0, {     0,      0}, {0xf4, 0x5c, 0xab, 0xff}}},
    {{{   -57,    -19,    -57}, 0, {     0,      0}, {0xb6, 0xcf, 0xa6, 0xff}}},
    {{{   -81,    236,      0}, 0, {     0,      0}, {0xb8, 0x68, 0x09, 0xff}}},
    {{{   -57,    236,    -57}, 0, {     0,      0}, {0xae, 0x49, 0xc2, 0xff}}},
};

// 0x080063A8
static const Vtx cannon_barrel_seg8_vertex_080063A8[] = {
    {{{   -81,    -19,      0}, 0, {     0,      0}, {0x8c, 0xcf, 0xf4, 0xff}}},
    {{{   -57,    -19,    -57}, 0, {     0,      0}, {0xb6, 0xcf, 0xa6, 0xff}}},
    {{{     0,    -71,      0}, 0, {     0,      0}, {0x00, 0x82, 0x00, 0xff}}},
    {{{   -57,    236,    -57}, 0, {     0,      0}, {0xae, 0x49, 0xc2, 0xff}}},
    {{{   -81,    236,      0}, 0, {     0,      0}, {0xb8, 0x68, 0x09, 0xff}}},
    {{{   -57,    -19,     58}, 0, {     0,      0}, {0xa6, 0xcf, 0x49, 0xff}}},
};

// 0x08006408 - 0x080064C0
const Gfx cannon_barrel_seg8_dl_08006408[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cannon_barrel_seg8_texture_080058A8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&cannon_barrel_seg8_lights_08005878.l, 1),
    gsSPLight(&cannon_barrel_seg8_lights_08005878.a, 2),
    gsSPVertex(cannon_barrel_seg8_vertex_080060A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  4,  7,  6, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  7,  9,  6, 0x0),
    gsSP2Triangles( 8, 10, 11, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(10, 12, 11, 0x0, 10, 13, 12, 0x0),
    gsSP2Triangles(14, 15, 12, 0x0, 14,  3, 15, 0x0),
    gsSP2Triangles(14,  0,  3, 0x0, 14, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x080064C0 - 0x08006660
const Gfx cannon_barrel_seg8_dl_080064C0[] = {
    gsSPLight(&cannon_barrel_seg8_lights_08005890.l, 1),
    gsSPLight(&cannon_barrel_seg8_lights_08005890.a, 2),
    gsSPVertex(cannon_barrel_seg8_vertex_080061A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  4, 0x0,  6,  4,  3, 0x0),
    gsSP2Triangles( 4,  8,  5, 0x0,  4,  7,  9, 0x0),
    gsSP2Triangles( 4,  9,  2, 0x0,  4,  2,  1, 0x0),
    gsSP2Triangles( 4,  1, 10, 0x0,  4, 10,  8, 0x0),
    gsSP2Triangles(11,  9,  7, 0x0, 11,  7,  6, 0x0),
    gsSP2Triangles(12,  2,  9, 0x0, 12,  9, 11, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 10,  1, 0x0),
    gsSP2Triangles(13,  1,  0, 0x0, 14,  8, 10, 0x0),
    gsSP2Triangles(14, 10, 13, 0x0, 15,  8, 14, 0x0),
    gsSP2Triangles(15,  5,  8, 0x0,  3,  5, 15, 0x0),
    gsSPVertex(cannon_barrel_seg8_vertex_080062A8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  4,  5, 0x0,  1,  5,  3, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  0,  6,  4, 0x0),
    gsSP2Triangles( 3,  5,  7, 0x0,  3,  8,  2, 0x0),
    gsSP2Triangles( 3,  7,  8, 0x0,  9,  0,  2, 0x0),
    gsSP2Triangles( 9,  6,  0, 0x0,  9, 10,  6, 0x0),
    gsSP2Triangles(11,  9,  2, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(11, 12, 10, 0x0, 13, 11,  2, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0,  8,  7, 14, 0x0),
    gsSP1Triangle(13, 15, 12, 0x0),
    gsSPVertex(cannon_barrel_seg8_vertex_080063A8, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  5,  0,  2, 0x0),
    gsSP1Triangle( 5,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x08006660 - 0x080066C8
const Gfx cannon_barrel_seg8_dl_08006660[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(cannon_barrel_seg8_dl_08006408),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(cannon_barrel_seg8_dl_080064C0),
    gsSPEndDisplayList(),
};
