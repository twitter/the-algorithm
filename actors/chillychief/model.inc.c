// Chilly Chief (Chill Bully)

// 0x06000000
static const Lights1 chilly_chief_seg6_lights_06000000 = gdSPDefLights1(
    0x00, 0x38, 0x00,
    0x00, 0xe3, 0x00, 0x28, 0x28, 0x28
);

// 0x06000018
static const Lights1 chilly_chief_seg6_lights_06000018 = gdSPDefLights1(
    0x00, 0x3f, 0x00,
    0x00, 0xff, 0x00, 0x28, 0x28, 0x28
);

// 0x06000030
static const Lights1 chilly_chief_seg6_lights_06000030 = gdSPDefLights1(
    0x3f, 0x29, 0x00,
    0xff, 0xa5, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chillychief_lights_unused = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x06000060
ALIGNED8 static const Texture chilly_chief_seg6_texture_06000060[] = {
#include "actors/chillychief/chill_bully_left_side.rgba16.inc.c"
};

// 0x06001060
ALIGNED8 static const Texture chilly_chief_seg6_texture_06001060[] = {
#include "actors/chillychief/chill_bully_right_side.rgba16.inc.c"
};

// 0x06002060
ALIGNED8 static const Texture chilly_chief_seg6_texture_06002060[] = {
#include "actors/chillychief/chill_bully_eye.rgba16.inc.c"
};

// 0x06002860
static const Vtx chilly_chief_seg6_vertex_06002860[] = {
    {{{    27,    -35,      0}, 0, {     0,      0}, {0x0c, 0x82, 0x04, 0x00}}},
    {{{    56,    -16,    -27}, 0, {     0,      0}, {0x16, 0xae, 0xa3, 0x00}}},
    {{{    72,    -16,      0}, 0, {     0,      0}, {0x59, 0xa7, 0x04, 0x00}}},
    {{{    56,     28,    -46}, 0, {     0,      0}, {0x65, 0xdf, 0xbd, 0xff}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x65, 0xdf, 0xbd, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xd7, 0xc5, 0x98, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd0, 0xde, 0x90, 0xff}}},
    {{{    56,     28,    -46}, 0, {     0,      0}, {0xd0, 0xde, 0x90, 0xff}}},
    {{{    56,    -16,     22}, 0, {     0,      0}, {0x16, 0xb4, 0x62, 0xff}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x5f, 0xde, 0x4b, 0xff}}},
    {{{    55,     28,     41}, 0, {     0,      0}, {0x5f, 0xde, 0x4b, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xdc, 0xcb, 0x6c, 0xff}}},
    {{{    55,     28,     41}, 0, {     0,      0}, {0xd5, 0xdf, 0x72, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd5, 0xdf, 0x72, 0xff}}},
};

// 0x06002940
static const Vtx chilly_chief_seg6_vertex_06002940[] = {
    {{{    90,     28,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
    {{{    56,     28,    -46}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    55,     28,     41}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
};

// 0x06002980
static const Vtx chilly_chief_seg6_vertex_06002980[] = {
    {{{    72,    -16,      0}, 0, {     0,      0}, {0x59, 0xa7, 0xfc, 0x00}}},
    {{{    56,    -16,     27}, 0, {     0,      0}, {0x16, 0xae, 0x5d, 0x00}}},
    {{{    27,    -35,      0}, 0, {     0,      0}, {0x0c, 0x82, 0xfc, 0x00}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x65, 0xdf, 0x43, 0xff}}},
    {{{    56,     28,     46}, 0, {     0,      0}, {0x65, 0xdf, 0x43, 0xff}}},
    {{{    56,     28,     46}, 0, {     0,      0}, {0xd0, 0xde, 0x70, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd0, 0xde, 0x70, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xd7, 0xc5, 0x68, 0xff}}},
    {{{    56,    -16,    -22}, 0, {     0,      0}, {0x16, 0xb4, 0x9e, 0xff}}},
    {{{    55,     28,    -41}, 0, {     0,      0}, {0x5f, 0xde, 0xb5, 0xff}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0x5f, 0xde, 0xb5, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xd5, 0xdf, 0x8e, 0xff}}},
    {{{    55,     28,    -41}, 0, {     0,      0}, {0xd5, 0xdf, 0x8e, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,      0}, {0xdc, 0xcb, 0x94, 0xff}}},
};

// 0x06002A60
static const Vtx chilly_chief_seg6_vertex_06002A60[] = {
    {{{    55,     28,    -41}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
    {{{   -37,     27,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    56,     28,     46}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0x00}}},
    {{{    90,     28,      0}, 0, {     0,      0}, {0xff, 0x7f, 0x00, 0xff}}},
};

// 0x06002AA0
static const Vtx chilly_chief_seg6_vertex_06002AA0[] = {
    {{{   132,    -29,      0}, 0, {     0,      0}, {0x10, 0xc2, 0x93, 0x00}}},
    {{{   131,     31,    -35}, 0, {     0,      0}, {0x10, 0xc2, 0x93, 0x00}}},
    {{{   250,      2,      0}, 0, {     0,      0}, {0x10, 0xc2, 0x93, 0x00}}},
    {{{   131,     31,     35}, 0, {     0,      0}, {0x10, 0xc2, 0x6d, 0xff}}},
    {{{   132,    -29,      0}, 0, {     0,      0}, {0x10, 0xc2, 0x6d, 0xff}}},
    {{{   250,      2,      0}, 0, {     0,      0}, {0x10, 0xc2, 0x6d, 0xff}}},
    {{{   131,     31,    -35}, 0, {     0,      0}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{   131,     31,     35}, 0, {     0,      0}, {0x1e, 0x7b, 0x00, 0xff}}},
    {{{   250,      2,      0}, 0, {     0,      0}, {0x1e, 0x7b, 0x00, 0xff}}},
};

// 0x06002B30 - 0x06002BC8
const Gfx chilly_chief_seg6_dl_06002B30[] = {
    gsSPLight(&chilly_chief_seg6_lights_06000000.l, 1),
    gsSPLight(&chilly_chief_seg6_lights_06000000.a, 2),
    gsSPVertex(chilly_chief_seg6_vertex_06002860, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  1,  5,  6, 0x0),
    gsSP2Triangles( 1,  6,  7, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  2,  8, 0x0,  8,  2,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11,  8, 12, 0x0),
    gsSP2Triangles(11, 12, 13, 0x0,  0,  8, 11, 0x0),
    gsSPVertex(chilly_chief_seg6_vertex_06002940, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002BC8 - 0x06002C60
const Gfx chilly_chief_seg6_dl_06002BC8[] = {
    gsSPLight(&chilly_chief_seg6_lights_06000018.l, 1),
    gsSPLight(&chilly_chief_seg6_lights_06000018.a, 2),
    gsSPVertex(chilly_chief_seg6_vertex_06002980, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 3,  1,  0, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 5,  7,  1, 0x0,  1,  7,  2, 0x0),
    gsSP2Triangles( 8,  0,  2, 0x0,  9, 10,  0, 0x0),
    gsSP2Triangles( 9,  0,  8, 0x0, 11, 12,  8, 0x0),
    gsSP2Triangles(11,  8, 13, 0x0, 13,  8,  2, 0x0),
    gsSPVertex(chilly_chief_seg6_vertex_06002A60, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002C60 - 0x06002C98
const Gfx chilly_chief_seg6_dl_06002C60[] = {
    gsSPLight(&chilly_chief_seg6_lights_06000030.l, 1),
    gsSPLight(&chilly_chief_seg6_lights_06000030.a, 2),
    gsSPVertex(chilly_chief_seg6_vertex_06002AA0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002C98
static const Vtx chilly_chief_seg6_vertex_06002C98[] = {
    {{{     0,     68,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -66,    -66,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -66,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -66,     68,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06002CD8
static const Vtx chilly_chief_seg6_vertex_06002CD8[] = {
    {{{    68,     68,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -66,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    68,    -66,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     68,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06002D18 - 0x06002D50
const Gfx chilly_chief_seg6_dl_06002D18[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chilly_chief_seg6_texture_06000060),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chilly_chief_seg6_vertex_06002C98, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002D50 - 0x06002D88
const Gfx chilly_chief_seg6_dl_06002D50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chilly_chief_seg6_texture_06001060),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chilly_chief_seg6_vertex_06002CD8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002D88 - 0x06002E00
const Gfx chilly_chief_seg6_dl_06002D88[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chilly_chief_seg6_dl_06002D18),
    gsSPDisplayList(chilly_chief_seg6_dl_06002D50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06002E00
static const Vtx chilly_chief_seg6_vertex_06002E00[] = {
    {{{     0,    136,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -133,   -133,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -133,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -133,    136,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06002E40
static const Vtx chilly_chief_seg6_vertex_06002E40[] = {
    {{{   136,    136,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -133,      0}, 0, {     0,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   136,   -133,      0}, 0, {   992,   2016}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    136,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06002E80 - 0x06002EB8
const Gfx chilly_chief_seg6_dl_06002E80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chilly_chief_seg6_texture_06000060),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chilly_chief_seg6_vertex_06002E00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002EB8 - 0x06002EF0
const Gfx chilly_chief_seg6_dl_06002EB8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chilly_chief_seg6_texture_06001060),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chilly_chief_seg6_vertex_06002E40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06002EF0 - 0x06002F68
const Gfx chilly_chief_seg6_dl_06002EF0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chilly_chief_seg6_dl_06002E80),
    gsSPDisplayList(chilly_chief_seg6_dl_06002EB8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06002F68
static const Vtx chilly_chief_seg6_vertex_06002F68[] = {
    {{{   -36,    152,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,    136,     68}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    56,    124,     68}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,    140,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    56,    124,    -64}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -40,    136,    -64}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06002FC8 - 0x06003010
const Gfx chilly_chief_seg6_dl_06002FC8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chilly_chief_seg6_texture_06002060),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chilly_chief_seg6_vertex_06002F68, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  5,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x06003010 - 0x06003080
const Gfx chilly_chief_seg6_dl_06003010[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chilly_chief_seg6_dl_06002FC8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
