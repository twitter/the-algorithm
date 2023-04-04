// Thwomp

// 0x050098E8
static const Lights1 thwomp_seg5_lights_050098E8 = gdSPDefLights1(
    0x4c, 0x4c, 0x4c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05009900
ALIGNED8 static const Texture thwomp_seg5_texture_05009900[] = {
#include "actors/thwomp/thwomp_face.rgba16.inc.c"
};

// 0x0500A900
ALIGNED8 static const Texture thwomp_seg5_texture_0500A900[] = {
#include "actors/thwomp/thwomp_surface.rgba16.inc.c"
};

// 0x0500B100
static const Vtx thwomp_seg5_vertex_0500B100[] = {
    {{{   -97,     52,   -141}, 0, {  3308,    418}, {0xce, 0xcb, 0x99, 0xff}}},
    {{{  -141,     52,    -72}, 0, {  2910,    418}, {0x93, 0xca, 0xdf, 0xff}}},
    {{{  -156,    252,    -78}, 0, {  2904,   1584}, {0x89, 0x1a, 0xde, 0xff}}},
    {{{  -105,    252,   -156}, 0, {  3316,   1584}, {0xd0, 0x46, 0xa3, 0xff}}},
    {{{   106,    252,   -156}, 0, {  4286,   1584}, {0x29, 0x39, 0x97, 0xff}}},
    {{{    98,     52,   -141}, 0, {  4292,    418}, {0x45, 0xd0, 0xa2, 0xff}}},
    {{{   157,    252,    -78}, 0, {  4698,   1584}, {0x73, 0x22, 0xd7, 0xff}}},
    {{{   142,     52,    -72}, 0, {  4692,    418}, {0x70, 0xc9, 0xea, 0xff}}},
    {{{     0,    302,   -101}, 0, {   460,    -56}, {0x00, 0x70, 0xc6, 0xff}}},
    {{{    93,    330,      0}, 0, {  1000,    540}, {0x2c, 0x76, 0x00, 0xff}}},
    {{{   106,    252,   -156}, 0, {  1080,   -374}, {0x29, 0x39, 0x97, 0xff}}},
    {{{   -92,    330,      0}, 0, {   -80,    540}, {0xd4, 0x76, 0x00, 0xff}}},
    {{{     0,    302,    102}, 0, {   460,   1134}, {0x00, 0x70, 0x3a, 0xff}}},
    {{{   106,    252,    157}, 0, {  1080,   1452}, {0x30, 0x46, 0x5d, 0xff}}},
    {{{  -105,    252,   -156}, 0, {  -160,   -374}, {0xd0, 0x46, 0xa3, 0xff}}},
};

// 0x0500B1F0
static const Vtx thwomp_seg5_vertex_0500B1F0[] = {
    {{{  -156,    252,     79}, 0, {  -452,   1000}, {0x8d, 0x22, 0x29, 0xff}}},
    {{{  -105,    252,    157}, 0, {  -160,   1452}, {0xd7, 0x39, 0x69, 0xff}}},
    {{{   -92,    330,      0}, 0, {   -80,    540}, {0xd4, 0x76, 0x00, 0xff}}},
    {{{  -156,    252,    -78}, 0, {  -452,     78}, {0x89, 0x1a, 0xde, 0xff}}},
    {{{  -105,    252,   -156}, 0, {  -160,   -374}, {0xd0, 0x46, 0xa3, 0xff}}},
    {{{     0,    302,    102}, 0, {   460,   1134}, {0x00, 0x70, 0x3a, 0xff}}},
    {{{   106,    252,    157}, 0, {  1080,   1452}, {0x30, 0x46, 0x5d, 0xff}}},
    {{{   -97,     52,   -141}, 0, {  -188,   1444}, {0xce, 0xcb, 0x99, 0xff}}},
    {{{    83,      0,    -82}, 0, {  1042,   1040}, {0x1e, 0x8c, 0xd7, 0xff}}},
    {{{   -82,      0,    -82}, 0, {   -82,   1040}, {0xc9, 0x92, 0xe2, 0xff}}},
    {{{    98,     52,   -141}, 0, {  1148,   1444}, {0x45, 0xd0, 0xa2, 0xff}}},
    {{{    83,      0,     83}, 0, {  1042,    -84}, {0x37, 0x92, 0x1e, 0xff}}},
    {{{   -82,      0,     83}, 0, {   -82,    -84}, {0xe2, 0x8c, 0x29, 0xff}}},
    {{{   142,     52,    -72}, 0, {  1446,    976}, {0x70, 0xc9, 0xea, 0xff}}},
    {{{    98,     52,    142}, 0, {  1148,   -488}, {0x32, 0xcb, 0x67, 0xff}}},
    {{{   -97,     52,    142}, 0, {  -188,   -488}, {0xbb, 0xd0, 0x5e, 0xff}}},
};

// 0x0500B2F0
static const Vtx thwomp_seg5_vertex_0500B2F0[] = {
    {{{   -82,      0,    -82}, 0, {   -82,   1040}, {0xc9, 0x92, 0xe2, 0xff}}},
    {{{   -82,      0,     83}, 0, {   -82,    -84}, {0xe2, 0x8c, 0x29, 0xff}}},
    {{{  -141,     52,     73}, 0, {  -486,    -18}, {0x90, 0xc9, 0x16, 0xff}}},
    {{{   -97,     52,    142}, 0, {  -188,   -488}, {0xbb, 0xd0, 0x5e, 0xff}}},
    {{{   -97,     52,   -141}, 0, {  -188,   1444}, {0xce, 0xcb, 0x99, 0xff}}},
    {{{  -141,     52,    -72}, 0, {  -486,    976}, {0x93, 0xca, 0xdf, 0xff}}},
    {{{    83,      0,     83}, 0, {  1042,    -84}, {0x37, 0x92, 0x1e, 0xff}}},
    {{{   142,     52,    -72}, 0, {  1446,    976}, {0x70, 0xc9, 0xea, 0xff}}},
    {{{   142,     52,     73}, 0, {  1446,    -18}, {0x6d, 0xca, 0x21, 0xff}}},
    {{{    98,     52,    142}, 0, {  1148,   -488}, {0x32, 0xcb, 0x67, 0xff}}},
    {{{   142,     52,    -72}, 0, {  4692,    418}, {0x70, 0xc9, 0xea, 0xff}}},
    {{{   157,    252,    -78}, 0, {  4698,   1584}, {0x73, 0x22, 0xd7, 0xff}}},
    {{{   157,    252,     79}, 0, {  5458,   1584}, {0x77, 0x1a, 0x22, 0xff}}},
    {{{   142,     52,     73}, 0, {  5464,    418}, {0x6d, 0xca, 0x21, 0xff}}},
};

// 0x0500B3D0
static const Vtx thwomp_seg5_vertex_0500B3D0[] = {
    {{{   106,    252,    157}, 0, {   760,   1584}, {0x30, 0x46, 0x5d, 0xff}}},
    {{{    98,     52,    142}, 0, {   754,    418}, {0x32, 0xcb, 0x67, 0xff}}},
    {{{   157,    252,     79}, 0, {   348,   1584}, {0x77, 0x1a, 0x22, 0xff}}},
    {{{   -97,     52,    142}, 0, {  1738,    418}, {0xbb, 0xd0, 0x5e, 0xff}}},
    {{{  -156,    252,     79}, 0, {  2142,   1584}, {0x8d, 0x22, 0x29, 0xff}}},
    {{{  -141,     52,     73}, 0, {  2136,    418}, {0x90, 0xc9, 0x16, 0xff}}},
    {{{  -156,    252,    -78}, 0, {  2904,   1584}, {0x89, 0x1a, 0xde, 0xff}}},
    {{{  -141,     52,    -72}, 0, {  2910,    418}, {0x93, 0xca, 0xdf, 0xff}}},
    {{{  -105,    252,    157}, 0, {  1730,   1584}, {0xd7, 0x39, 0x69, 0xff}}},
    {{{   106,    252,   -156}, 0, {   -24,    628}, {0x29, 0x39, 0x97, 0xff}}},
    {{{  -105,    252,   -156}, 0, {   984,    628}, {0xd0, 0x46, 0xa3, 0xff}}},
    {{{     0,    302,   -101}, 0, {   480,    370}, {0x00, 0x70, 0xc6, 0xff}}},
    {{{   157,    252,     79}, 0, {  1374,   1000}, {0x77, 0x1a, 0x22, 0xff}}},
    {{{    93,    330,      0}, 0, {  1000,    540}, {0x2c, 0x76, 0x00, 0xff}}},
    {{{   106,    252,    157}, 0, {  1080,   1452}, {0x30, 0x46, 0x5d, 0xff}}},
    {{{   157,    252,    -78}, 0, {  1374,     78}, {0x73, 0x22, 0xd7, 0xff}}},
};

// 0x0500B4D0
static const Vtx thwomp_seg5_vertex_0500B4D0[] = {
    {{{    98,     52,    142}, 0, {   754,    418}, {0x32, 0xcb, 0x67, 0xff}}},
    {{{   142,     52,     73}, 0, {   354,    418}, {0x6d, 0xca, 0x21, 0xff}}},
    {{{   157,    252,     79}, 0, {   348,   1584}, {0x77, 0x1a, 0x22, 0xff}}},
    {{{   106,    252,   -156}, 0, {  1414,    324}, {0x29, 0x39, 0x97, 0xff}}},
    {{{    93,    330,      0}, 0, {   472,    -20}, {0x2c, 0x76, 0x00, 0xff}}},
    {{{   157,    252,    -78}, 0, {   946,    494}, {0x73, 0x22, 0xd7, 0xff}}},
};

// 0x0500B530
static const Vtx thwomp_seg5_vertex_0500B530[] = {
    {{{    98,     52,    142}, 0, {   968,   1976}, {0x32, 0xcb, 0x67, 0xff}}},
    {{{   106,    252,    157}, 0, {  1010,      0}, {0x30, 0x46, 0x5d, 0xff}}},
    {{{  -105,    252,    157}, 0, {   -54,    -12}, {0xd7, 0x39, 0x69, 0xff}}},
    {{{   -97,     52,    142}, 0, {   -16,   1964}, {0xbb, 0xd0, 0x5e, 0xff}}},
};

// 0x0500B570 - 0x0500B718
const Gfx thwomp_seg5_dl_0500B570[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, thwomp_seg5_texture_0500A900),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&thwomp_seg5_lights_050098E8.l, 1),
    gsSPLight(&thwomp_seg5_lights_050098E8.a, 2),
    gsSPVertex(thwomp_seg5_vertex_0500B100, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 0,  3,  4, 0x0,  0,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13,  9, 0x0,  9, 11, 12, 0x0),
    gsSP1Triangle( 8, 14, 11, 0x0),
    gsSPVertex(thwomp_seg5_vertex_0500B1F0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  4, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  2,  1,  5, 0x0),
    gsSP2Triangles( 1,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0,  8, 11, 12, 0x0),
    gsSP2Triangles( 8, 12,  9, 0x0, 11,  8, 13, 0x0),
    gsSP2Triangles(13,  8, 10, 0x0, 14, 12, 11, 0x0),
    gsSP1Triangle(14, 15, 12, 0x0),
    gsSPVertex(thwomp_seg5_vertex_0500B2F0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  0,  5, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  6,  8, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12, 13, 0x0),
    gsSPVertex(thwomp_seg5_vertex_0500B3D0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 3,  8,  4, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 15, 13, 12, 0x0),
    gsSPVertex(thwomp_seg5_vertex_0500B4D0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B718 - 0x0500B750
const Gfx thwomp_seg5_dl_0500B718[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, thwomp_seg5_texture_05009900),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(thwomp_seg5_vertex_0500B530, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B750 - 0x0500B7D0
const Gfx thwomp_seg5_dl_0500B750[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thwomp_seg5_dl_0500B570),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(thwomp_seg5_dl_0500B718),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
