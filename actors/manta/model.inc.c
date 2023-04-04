// Manta Ray

// 0x05001758
static const Lights1 manta_seg5_lights_05001758 = gdSPDefLights1(
    0x31, 0x3b, 0x3b,
    0xc6, 0xee, 0xed, 0x28, 0x28, 0x28
);

// 0x05001770
static const Lights1 manta_seg5_lights_05001770 = gdSPDefLights1(
    0x00, 0x16, 0x18,
    0x03, 0x5b, 0x63, 0x28, 0x28, 0x28
);

// Unreferenced light group
static const Lights1 manta_lights_unused = gdSPDefLights1(
    0x3f, 0x3f, 0x35,
    0xff, 0xff, 0xd7, 0x28, 0x28, 0x28
);

// 0x050017A0
ALIGNED8 static const Texture manta_seg5_texture_050017A0[] = {
#include "actors/manta/manta_fin_corner.rgba16.inc.c"
};

// 0x05001FA0
ALIGNED8 static const Texture manta_seg5_texture_05001FA0[] = {
#include "actors/manta/manta_gills.rgba16.inc.c"
};

// 0x05002FA0
ALIGNED8 static const Texture manta_seg5_texture_05002FA0[] = {
#include "actors/manta/manta_eye.rgba16.inc.c"
};

// 0x050037A0
ALIGNED8 static const Texture manta_seg5_texture_050037A0[] = {
#include "actors/manta/manta_fin_edge.rgba16.inc.c"
};

// 0x050047A0
static const Vtx manta_seg5_vertex_050047A0[] = {
    {{{    60,    -41,     -9}, 0, {   112,    436}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,      3,    -29}, 0, {   718,    778}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,      2,      2}, 0, {   836,    436}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,    -25,    -55}, 0, {   224,   1082}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,    -26,    -37}, 0, {   274,    802}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -59,    -29}, 0, {  -108,   1004}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,     15,    -44}, 0, {   766,   1082}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,      3,    -29}, 0, {   670,    802}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,     30,      3}, 0, {  1114,    962}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,    -26,    -37}, 0, {   208,    756}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,    -41,     -9}, 0, {    96,    416}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -59,    -29}, 0, {  -270,    708}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,    -26,    -37}, 0, {   224,    778}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,      2,      2}, 0, {   868,    416}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,      3,    -29}, 0, {   750,    756}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,     30,      3}, 0, {  1304,    626}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x050048A0
static const Vtx manta_seg5_vertex_050048A0[] = {
    {{{   -23,    -42,      8}, 0, {   394,     38}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -48,    -24,    -36}, 0, {   286,    704}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -59,    -29}, 0, {   554,    584}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,    -25,    -55}, 0, {   622,    960}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -60,     27,    -28}, 0, {   232,    594}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -26,      9,     23}, 0, {   380,   -172}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,     15,    -44}, 0, {   622,    794}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,    -41,     -9}, 0, {   770,    280}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    33,    -47,     23}, 0, {   648,   -192}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,     30,      3}, 0, {   554,     98}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    33,     -6,     34}, 0, {   648,   -358}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,      2,      2}, 0, {   770,    102}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05004960 - 0x050049C8
const Gfx manta_seg5_dl_05004960[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_05002FA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050047A0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 7,  6,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 0, 12,  1, 0x0, 13, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x050049C8 - 0x05004A70
const Gfx manta_seg5_dl_050049C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050048A0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 1,  0,  5, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 1,  5,  4, 0x0,  3,  4,  6, 0x0),
    gsSP2Triangles( 7,  8,  2, 0x0,  8,  0,  2, 0x0),
    gsSP2Triangles( 6,  4,  9, 0x0,  4,  5,  9, 0x0),
    gsSP2Triangles( 5, 10,  9, 0x0, 10, 11,  9, 0x0),
    gsSP2Triangles( 0, 10,  5, 0x0,  0,  8, 10, 0x0),
    gsSP2Triangles( 8,  7, 11, 0x0,  8, 11, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004A70 - 0x05004AE8
const Gfx manta_seg5_dl_05004A70[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05004960),
    gsSPDisplayList(manta_seg5_dl_050049C8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05004AE8
static const Vtx manta_seg5_vertex_05004AE8[] = {
    {{{    60,     -1,      2}, 0, {   836,    436}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,     -2,    -29}, 0, {   718,    778}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     42,     -9}, 0, {   112,    436}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,     60,    -29}, 0, {  -108,   1004}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,     27,    -37}, 0, {   274,    802}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,     26,    -55}, 0, {   224,   1082}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,    -14,    -44}, 0, {   766,   1082}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,     -2,    -29}, 0, {   670,    802}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -29,      3}, 0, {  1114,    962}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,     60,    -29}, 0, {  -270,    708}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     42,     -9}, 0, {    96,    416}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,     27,    -37}, 0, {   208,    756}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,     27,    -37}, 0, {   224,    778}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -29,      3}, 0, {  1304,    626}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    51,     -2,    -29}, 0, {   750,    756}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     -1,      2}, 0, {   868,    416}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05004BE8
static const Vtx manta_seg5_vertex_05004BE8[] = {
    {{{    12,     60,    -29}, 0, {   554,    584}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    33,     48,     23}, 0, {   648,   -192}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     42,     -9}, 0, {   770,    280}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -23,     43,      8}, 0, {   394,     38}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -48,     25,    -36}, 0, {   286,    704}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,     26,    -55}, 0, {   622,    960}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    27,    -14,    -44}, 0, {   622,    794}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -60,    -26,    -28}, 0, {   232,    594}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -26,     -8,     23}, 0, {   380,   -172}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -29,      3}, 0, {   554,     98}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    33,      7,     34}, 0, {   648,   -358}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     -1,      2}, 0, {   770,    102}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05004CA8 - 0x05004D10
const Gfx manta_seg5_dl_05004CA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_05002FA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05004AE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  5,  4, 0x0,  7,  6,  4, 0x0),
    gsSP2Triangles( 8,  6,  7, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 1, 12,  2, 0x0, 13, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004D10 - 0x05004DB8
const Gfx manta_seg5_dl_05004D10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05004BE8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  0,  5,  4, 0x0),
    gsSP2Triangles( 6,  7,  5, 0x0,  7,  4,  5, 0x0),
    gsSP2Triangles( 8,  3,  4, 0x0,  7,  8,  4, 0x0),
    gsSP2Triangles( 9,  7,  6, 0x0,  9,  8,  7, 0x0),
    gsSP2Triangles( 9, 10,  8, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 8, 10,  3, 0x0, 10,  1,  3, 0x0),
    gsSP2Triangles(11,  2,  1, 0x0, 10, 11,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004DB8 - 0x05004E30
const Gfx manta_seg5_dl_05004DB8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05004CA8),
    gsSPDisplayList(manta_seg5_dl_05004D10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05004E30
static const Vtx manta_seg5_vertex_05004E30[] = {
    {{{    97,      0,     52}, 0, {   412,    534}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   135,      0,      0}, 0, {   432,    770}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   274,      0,      0}, 0, {  1244,    550}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05004E60 - 0x05004E90
const Gfx manta_seg5_dl_05004E60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05004E30, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05004E90 - 0x05004F00
const Gfx manta_seg5_dl_05004E90[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05004E60),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x05004F00
static const Vtx manta_seg5_vertex_05004F00[] = {
    {{{   172,    -21,     42}, 0, {   862,   -544}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   184,    -11,      8}, 0, {   918,    -60}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   102,     18,      2}, 0, {   544,    108}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    92,    -11,      2}, 0, {   500,    116}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    92,     -1,    -23}, 0, {   500,    496}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   102,     -1,     32}, 0, {   544,   -330}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,    -31,     32}, 0, {     8,   -216}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,    -31,    -31}, 0, {     8,    718}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,     18,     32}, 0, {     8,   -216}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,     18,    -31}, 0, {     8,    718}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05004FA0 - 0x05005038
const Gfx manta_seg5_dl_05004FA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05004F00, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0,  2,  1,  4, 0x0),
    gsSP2Triangles( 3,  0,  5, 0x0,  2,  5,  0, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 3,  6,  7, 0x0,  8,  5,  2, 0x0),
    gsSP2Triangles( 5,  8,  6, 0x0,  9,  2,  4, 0x0),
    gsSP2Triangles( 4,  7,  9, 0x0,  2,  9,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005038 - 0x050050A8
const Gfx manta_seg5_dl_05005038[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05004FA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x050050A8
static const Vtx manta_seg5_vertex_050050A8[] = {
    {{{   -89,    -89,      0}, 0, {   376,    742}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -119,    -39}, 0, {     0,    626}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -159,      0}, 0, {     0,     40}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -119,    -39}, 0, {   990,    626}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,    -89,    -19}, 0, {   780,    948}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,   -123,      0}, 0, {   780,    656}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -159,      0}, 0, {   990,     40}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005118
static const Vtx manta_seg5_vertex_05005118[] = {
    {{{   220,    -89,     20}, 0, {   812,    282}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,   -123,      0}, 0, {   812,    242}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   244,    -63,      0}, 0, {   840,    314}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -119,     30}, 0, {   552,    248}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -159,      0}, 0, {   552,    200}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -89,    -89,      0}, 0, {   448,    282}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -89,    140,      0}, 0, {   448,    556}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    180,     30}, 0, {   552,    604}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    350,      0}, 0, {   552,    806}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,    228,      0}, 0, {   812,    660}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x050051B8
static const Vtx manta_seg5_vertex_050051B8[] = {
    {{{   244,    -63,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   220,   -123,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   220,    -89,    -19}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -119,    -39}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    180,    -39}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   220,    228,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -89,    -89,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -89,    140,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    350,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x05005248 - 0x05005288
const Gfx manta_seg5_dl_05005248[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050050A8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  3,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005288 - 0x05005308
const Gfx manta_seg5_dl_05005288[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05005118, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 1,  0,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 5,  3,  6, 0x0,  3,  7,  6, 0x0),
    gsSP2Triangles( 7,  3,  0, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0,  0,  9,  7, 0x0),
    gsSP1Triangle( 2,  9,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005308 - 0x05005358
const Gfx manta_seg5_dl_05005308[] = {
    gsSPVertex(manta_seg5_vertex_050051B8, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 0,  2,  5, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  7,  4,  6, 0x0),
    gsSP2Triangles( 8,  4,  7, 0x0,  5,  4,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005358 - 0x050053F0
const Gfx manta_seg5_dl_05005358[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005248),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005288),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_05005308),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x050053F0
static const Vtx manta_seg5_vertex_050053F0[] = {
    {{{     0,    -89,     20}, 0, {   804,    296}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    228,      0}, 0, {   804,    674}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -31,    -63,      0}, 0, {   768,    326}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -123,      0}, 0, {   804,    254}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,    -91,     10}, 0, {  1016,    292}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,   -111,      0}, 0, {  1016,    268}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,    120,      0}, 0, {  1016,    544}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   186,    -79,      0}, 0, {  1024,    306}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005470
static const Vtx manta_seg5_vertex_05005470[] = {
    {{{     0,    -89,    -19}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,    120,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,    -91,     -9}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -123,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,   -111,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -31,    -63,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    228,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   186,    -79,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x050054F0 - 0x05005558
const Gfx manta_seg5_dl_050054F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050053F0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  5,  4,  3, 0x0),
    gsSP2Triangles( 0,  4,  6, 0x0,  0,  6,  1, 0x0),
    gsSP2Triangles( 4,  5,  7, 0x0,  4,  7,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005558 - 0x050055A8
const Gfx manta_seg5_dl_05005558[] = {
    gsSPVertex(manta_seg5_vertex_05005470, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 5,  6,  0, 0x0,  6,  1,  0, 0x0),
    gsSP2Triangles( 0,  2,  4, 0x0,  0,  3,  5, 0x0),
    gsSP2Triangles( 7,  2,  1, 0x0,  7,  4,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x050055A8 - 0x05005620
const Gfx manta_seg5_dl_050055A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_050054F0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_05005558),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05005620
static const Vtx manta_seg5_vertex_05005620[] = {
    {{{   167,   -105,      0}, 0, {  1220,    280}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -89,     10}, 0, {  1024,    298}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -109,      0}, 0, {  1024,    274}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    122,      0}, 0, {  1024,    550}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,    -17,      0}, 0, {  1236,    384}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    -7,    -77,      0}, 0, {  1012,    312}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005680
static const Vtx manta_seg5_vertex_05005680[] = {
    {{{     0,    -89,     -9}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,    -17,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   167,   -105,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -109,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    -7,    -77,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    122,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x050056E0 - 0x05005730
const Gfx manta_seg5_dl_050056E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05005620, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  4, 0x0),
    gsSP2Triangles( 4,  1,  0, 0x0,  1,  3,  5, 0x0),
    gsSP1Triangle( 5,  2,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005730 - 0x05005768
const Gfx manta_seg5_dl_05005730[] = {
    gsSPVertex(manta_seg5_vertex_05005680, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 3,  0,  2, 0x0,  4,  5,  0, 0x0),
    gsSP1Triangle( 5,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005768 - 0x050057E0
const Gfx manta_seg5_dl_05005768[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_050056E0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_05005730),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x050057E0
static const Vtx manta_seg5_vertex_050057E0[] = {
    {{{   102,      2,     32}, 0, {   544,   -330}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    92,     12,      2}, 0, {   500,    116}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,     32,     32}, 0, {     8,   -216}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    92,      2,    -23}, 0, {   500,    496}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,     32,    -31}, 0, {     8,    718}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   184,     12,      8}, 0, {   918,    -60}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   172,     22,     42}, 0, {   862,   -544}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   102,    -17,      2}, 0, {   544,    108}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,    -17,     32}, 0, {     8,   -216}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,    -17,    -31}, 0, {     8,    718}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005880 - 0x05005918
const Gfx manta_seg5_dl_05005880[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050057E0, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 5,  3,  1, 0x0,  1,  6,  5, 0x0),
    gsSP2Triangles( 0,  6,  1, 0x0,  4,  2,  1, 0x0),
    gsSP2Triangles( 7,  5,  6, 0x0,  6,  0,  7, 0x0),
    gsSP2Triangles( 3,  5,  7, 0x0,  7,  0,  8, 0x0),
    gsSP2Triangles( 2,  8,  0, 0x0,  3,  7,  9, 0x0),
    gsSP2Triangles( 9,  4,  3, 0x0,  8,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005918 - 0x05005988
const Gfx manta_seg5_dl_05005918[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005880),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05005988
static const Vtx manta_seg5_vertex_05005988[] = {
    {{{     0,    160,      0}, 0, {     0,     40}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    120,    -39}, 0, {     0,    626}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -89,     90,      0}, 0, {   376,    742}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,    124,      0}, 0, {   780,    656}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,     90,    -19}, 0, {   780,    948}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    120,    -39}, 0, {   990,    626}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    160,      0}, 0, {   990,     40}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x050059F8
static const Vtx manta_seg5_vertex_050059F8[] = {
    {{{   244,     64,      0}, 0, {   840,    314}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,    124,      0}, 0, {   812,    242}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,     90,     20}, 0, {   812,    282}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -89,   -139,      0}, 0, {   448,    556}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    120,     30}, 0, {   552,    248}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -89,     90,      0}, 0, {   448,    282}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    160,      0}, 0, {   552,    200}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -179,     30}, 0, {   552,    604}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -349,      0}, 0, {   552,    806}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,   -227,      0}, 0, {   812,    660}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005A98
static const Vtx manta_seg5_vertex_05005A98[] = {
    {{{   220,   -227,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   220,     90,    -19}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   244,     64,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -349,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -179,    -39}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    120,    -39}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -89,     90,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -89,   -139,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   220,    124,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x05005B28 - 0x05005B68
const Gfx manta_seg5_dl_05005B28[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05005988, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 3,  5,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005B68 - 0x05005BE8
const Gfx manta_seg5_dl_05005B68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050059F8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  6,  4,  2, 0x0),
    gsSP2Triangles( 3,  7,  4, 0x0,  2,  4,  7, 0x0),
    gsSP2Triangles( 6,  2,  1, 0x0,  8,  7,  3, 0x0),
    gsSP2Triangles( 7,  8,  9, 0x0,  7,  9,  2, 0x0),
    gsSP1Triangle( 2,  9,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005BE8 - 0x05005C38
const Gfx manta_seg5_dl_05005BE8[] = {
    gsSPVertex(manta_seg5_vertex_05005A98, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 0,  4,  1, 0x0,  4,  5,  1, 0x0),
    gsSP2Triangles( 6,  5,  4, 0x0,  6,  4,  7, 0x0),
    gsSP2Triangles( 7,  4,  3, 0x0,  1,  8,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005C38 - 0x05005CD0
const Gfx manta_seg5_dl_05005C38[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005B28),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005B68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_05005BE8),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05005CD0
static const Vtx manta_seg5_vertex_05005CD0[] = {
    {{{   180,   -119,      0}, 0, {  1016,    538}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,     92,     10}, 0, {  1016,    286}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     90,     20}, 0, {   804,    290}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -31,     64,      0}, 0, {   764,    320}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -227,      0}, 0, {   804,    668}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    124,      0}, 0, {   804,    248}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,    112,      0}, 0, {  1016,    264}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   186,     80,      0}, 0, {  1024,    302}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005D50
static const Vtx manta_seg5_vertex_05005D50[] = {
    {{{   180,     92,     -9}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,   -119,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,     90,    -19}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   186,     80,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,    112,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -227,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    124,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -31,     64,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x05005DD0 - 0x05005E38
const Gfx manta_seg5_dl_05005DD0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05005CD0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 2,  5,  3, 0x0,  5,  2,  1, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  5,  1,  6, 0x0),
    gsSP2Triangles( 7,  6,  1, 0x0,  0,  7,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005E38 - 0x05005E88
const Gfx manta_seg5_dl_05005E38[] = {
    gsSPVertex(manta_seg5_vertex_05005D50, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  4,  0,  2, 0x0),
    gsSP2Triangles( 2,  1,  5, 0x0,  4,  2,  6, 0x0),
    gsSP2Triangles( 2,  5,  7, 0x0,  7,  6,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x05005E88 - 0x05005F00
const Gfx manta_seg5_dl_05005E88[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005DD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_05005E38),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05005F00
static const Vtx manta_seg5_vertex_05005F00[] = {
    {{{     0,     90,     10}, 0, {  1024,    298}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    110,      0}, 0, {  1024,    274}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    -7,     78,      0}, 0, {  1012,    312}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   167,    106,      0}, 0, {  1220,    280}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   180,     18,      0}, 0, {  1236,    384}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -121,      0}, 0, {  1024,    550}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05005F60
static const Vtx manta_seg5_vertex_05005F60[] = {
    {{{     0,     90,     -9}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   180,     18,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,   -121,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   167,    106,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{     0,    110,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    -7,     78,      0}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x05005FC0 - 0x05006010
const Gfx manta_seg5_dl_05005FC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05005F00, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  4, 0x0),
    gsSP2Triangles( 1,  0,  3, 0x0,  4,  0,  5, 0x0),
    gsSP1Triangle( 2,  5,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006010 - 0x05006048
const Gfx manta_seg5_dl_05006010[] = {
    gsSPVertex(manta_seg5_vertex_05005F60, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  1,  0, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  0,  2,  5, 0x0),
    gsSP1Triangle( 5,  4,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006048 - 0x050060C0
const Gfx manta_seg5_dl_05006048[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05005FC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_05006010),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x050060C0
static const Vtx manta_seg5_vertex_050060C0[] = {
    {{{   600,    220,      6}, 0, {     0,     36}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,    220,    -33}, 0, {     0,    718}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   532,    288,      6}, 0, {   178,    388}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   730,      0,    -43}, 0, {   480,    888}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   740,    120,     26}, 0, {   758,   -304}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   770,      0,     16}, 0, {   480,   -134}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   670,    130,    -53}, 0, {   780,   1056}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   740,   -119,     26}, 0, {   200,   -304}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   670,   -129,    -53}, 0, {   178,   1056}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,    220,    -33}, 0, {   990,    718}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   600,    220,      6}, 0, {   990,     36}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   600,   -219,      6}, 0, {     0,     36}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,   -219,    -33}, 0, {     0,    718}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   532,   -287,      6}, 0, {   178,    388}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x050061A0
static const Vtx manta_seg5_vertex_050061A0[] = {
    {{{   260,   -219,    -33}, 0, {     0,   2324}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   220,      0,    -73}, 0, {  1092,   2532}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   504,      0,    -91}, 0, {  1092,   1048}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,   -219,    -33}, 0, {     0,    756}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   260,    220,    -33}, 0, {     0,   2324}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,    220,    -33}, 0, {     0,    756}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,   -219,    -33}, 0, {   -10,     48}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   504,      0,    -91}, 0, {  1212,    864}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   670,   -129,    -53}, 0, {   490,     16}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   670,   -129,    -53}, 0, {   532,      8}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   504,      0,    -91}, 0, {   990,    912}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   730,      0,    -43}, 0, {   990,    -72}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   670,    130,    -53}, 0, {   490,     16}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,    220,    -33}, 0, {   -10,     48}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   670,    130,    -53}, 0, {   532,      8}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05006290
static const Vtx manta_seg5_vertex_05006290[] = {
    {{{   740,    120,     26}, 0, {   144,     40}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,    220,     36}, 0, {    28,    254}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   500,      0,     56}, 0, {   288,    324}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   532,   -287,      6}, 0, {   628,    286}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   600,   -219,      6}, 0, {   548,    206}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   560,   -219,     36}, 0, {   548,    254}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   260,   -219,     36}, 0, {   548,    610}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   300,   -287,      6}, 0, {   628,    562}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   740,   -119,     26}, 0, {   428,     40}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   260,    220,     36}, 0, {    28,    610}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    20,   -109,      6}, 0, {   416,    894}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    90,   -219,      6}, 0, {   548,    812}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   770,      0,     16}, 0, {   288,      4}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   600,    220,      6}, 0, {    28,    206}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05006370
static const Vtx manta_seg5_vertex_05006370[] = {
    {{{   560,    220,     36}, 0, {   536,    250}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   600,    220,      6}, 0, {   536,    202}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   532,    288,      6}, 0, {   616,    284}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   260,    220,     36}, 0, {    28,    610}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    20,    120,     16}, 0, {   144,    894}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    20,   -109,      6}, 0, {   416,    894}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -39,      0,      6}, 0, {   288,    966}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    90,    220,      6}, 0, {    28,    812}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    90,    220,      6}, 0, {   536,    810}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   260,    220,     36}, 0, {   536,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   300,    288,      6}, 0, {   616,    560}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05006420
static const Vtx manta_seg5_vertex_05006420[] = {
    {{{   300,    288,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   260,    220,    -33}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    90,    220,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    80,      0,    -40}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    90,   -219,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    20,   -109,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   260,   -219,    -33}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   300,   -287,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   532,   -287,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   560,   -219,    -33}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   220,      0,    -73}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   532,    288,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   560,    220,    -33}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{   -39,      0,      6}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
    {{{    20,    120,     16}, 0, {     0,      0}, {0xc6, 0xee, 0xed, 0xff}}},
};

// 0x05006510 - 0x05006588
const Gfx manta_seg5_dl_05006510[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050017A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050060C0, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  3,  5,  7, 0x0),
    gsSP2Triangles( 7,  8,  3, 0x0,  6,  9, 10, 0x0),
    gsSP2Triangles( 4,  6, 10, 0x0, 11, 12,  8, 0x0),
    gsSP2Triangles( 8,  7, 11, 0x0, 13, 12, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006588 - 0x050065F0
const Gfx manta_seg5_dl_05006588[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_05001FA0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_050061A0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12,  7, 13, 0x0, 11, 10, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x050065F0 - 0x050066D0
const Gfx manta_seg5_dl_050065F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, manta_seg5_texture_050037A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(manta_seg5_vertex_05006290, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  2,  5,  8, 0x0),
    gsSP2Triangles( 8,  5,  4, 0x0,  6,  5,  2, 0x0),
    gsSP2Triangles( 5,  7,  3, 0x0,  6,  2,  9, 0x0),
    gsSP2Triangles(10,  6,  9, 0x0,  7,  6, 11, 0x0),
    gsSP2Triangles(10, 11,  6, 0x0,  8, 12,  0, 0x0),
    gsSP2Triangles( 0,  2,  8, 0x0, 13,  1,  0, 0x0),
    gsSP1Triangle( 2,  1,  9, 0x0),
    gsSPVertex(manta_seg5_vertex_05006370, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  3,  7,  4, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9,  0, 0x0),
    gsSP1Triangle( 2, 10,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x050066D0 - 0x05006750
const Gfx manta_seg5_dl_050066D0[] = {
    gsSPVertex(manta_seg5_vertex_05006420, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  7,  6, 0x0,  8,  6,  9, 0x0),
    gsSP2Triangles( 3, 10,  6, 0x0, 11, 12,  1, 0x0),
    gsSP2Triangles(11,  1,  0, 0x0,  1, 10,  3, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  3, 13, 14, 0x0),
    gsSP2Triangles( 2,  3, 14, 0x0,  5, 13,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006750 - 0x05006808
const Gfx manta_seg5_dl_05006750[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05006510),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_05006588),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(manta_seg5_dl_050065F0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(manta_seg5_dl_050066D0),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05006808
static const Vtx manta_seg5_vertex_05006808[] = {
    {{{     0,     -5,     -3}, 0, {     0,      0}, {0xf3, 0x98, 0x46, 0x00}}},
    {{{     0,      0,      4}, 0, {     0,      0}, {0xf6, 0x00, 0x7e, 0x00}}},
    {{{    -4,      0,      2}, 0, {     0,      0}, {0xa9, 0x00, 0x5b, 0x00}}},
    {{{   238,      0,      0}, 0, {     0,      0}, {0x01, 0x66, 0x4a, 0x00}}},
    {{{     0,      5,     -3}, 0, {     0,      0}, {0xf3, 0x68, 0x46, 0x00}}},
    {{{   238,      0,      0}, 0, {     0,      0}, {0x01, 0x9a, 0x4a, 0x00}}},
};

// 0x05006868
static const Vtx manta_seg5_vertex_05006868[] = {
    {{{    -4,      0,      2}, 0, {     0,      0}, {0xa9, 0x00, 0x5b, 0x00}}},
    {{{     0,      5,     -3}, 0, {     0,      0}, {0xf3, 0x68, 0x46, 0x00}}},
    {{{     0,     -5,     -3}, 0, {     0,      0}, {0xf3, 0x98, 0x46, 0x00}}},
    {{{     0,     -5,     -3}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
    {{{     0,      5,     -3}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
    {{{   238,      0,      0}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
};

// 0x050068C8
static const Vtx manta_seg5_vertex_050068C8[] = {
    {{{     0,     -8,     -5}, 0, {     0,      0}, {0xf1, 0x94, 0x3f, 0x00}}},
    {{{     0,      0,      7}, 0, {     0,      0}, {0xf4, 0x00, 0x7e, 0x00}}},
    {{{    -7,      0,      0}, 0, {     0,      0}, {0x84, 0x00, 0x18, 0x00}}},
    {{{   236,     -5,     -3}, 0, {     0,      0}, {0x13, 0x8e, 0xcd, 0x00}}},
    {{{   240,      0,      0}, 0, {     0,      0}, {0x7b, 0x00, 0x1b, 0x00}}},
    {{{   236,      0,      4}, 0, {     0,      0}, {0x0f, 0x00, 0x7e, 0x00}}},
    {{{   236,      5,     -3}, 0, {     0,      0}, {0x13, 0x72, 0xcd, 0x00}}},
    {{{     0,      8,     -5}, 0, {     0,      0}, {0xf1, 0x6c, 0x3f, 0x00}}},
};

// 0x05006948
static const Vtx manta_seg5_vertex_05006948[] = {
    {{{    -7,      0,      0}, 0, {     0,      0}, {0x84, 0x00, 0x18, 0x00}}},
    {{{     0,      8,     -5}, 0, {     0,      0}, {0xf1, 0x6c, 0x3f, 0x00}}},
    {{{     0,     -8,     -5}, 0, {     0,      0}, {0xf1, 0x94, 0x3f, 0x00}}},
    {{{   236,      5,     -3}, 0, {     0,      0}, {0x13, 0x72, 0xcd, 0x00}}},
    {{{   240,      0,      0}, 0, {     0,      0}, {0x7b, 0x00, 0x1b, 0x00}}},
    {{{   236,     -5,     -3}, 0, {     0,      0}, {0x13, 0x8e, 0xcd, 0x00}}},
    {{{     0,     -8,     -5}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
    {{{     0,      8,     -5}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
};

// 0x050069C8
static const Vtx manta_seg5_vertex_050069C8[] = {
    {{{     0,    -11,     -7}, 0, {     0,      0}, {0x95, 0xbf, 0xee, 0x00}}},
    {{{     0,      0,     10}, 0, {     0,      0}, {0x9d, 0x00, 0x4f, 0x00}}},
    {{{    -8,      0,      0}, 0, {     0,      0}, {0x83, 0x00, 0x13, 0x00}}},
    {{{   168,     -8,     -5}, 0, {     0,      0}, {0x13, 0x8f, 0xcc, 0x00}}},
    {{{   175,      0,      0}, 0, {     0,      0}, {0x7c, 0x00, 0x18, 0x00}}},
    {{{   168,      0,      7}, 0, {     0,      0}, {0x10, 0x00, 0x7d, 0x00}}},
    {{{   168,      8,     -5}, 0, {     0,      0}, {0x13, 0x71, 0xcc, 0x00}}},
    {{{     0,     11,     -7}, 0, {     0,      0}, {0x95, 0x41, 0xee, 0x00}}},
    {{{     0,      0,     10}, 0, {     0,      0}, {0x01, 0x97, 0x46, 0x00}}},
    {{{     0,    -11,     -7}, 0, {     0,      0}, {0x01, 0x97, 0x46, 0x00}}},
    {{{     0,     11,     -7}, 0, {     0,      0}, {0x01, 0x69, 0x46, 0x00}}},
    {{{     0,      0,     10}, 0, {     0,      0}, {0x01, 0x69, 0x46, 0x00}}},
};

// 0x05006A88
static const Vtx manta_seg5_vertex_05006A88[] = {
    {{{    -8,      0,      0}, 0, {     0,      0}, {0x83, 0x00, 0x13, 0x00}}},
    {{{     0,     11,     -7}, 0, {     0,      0}, {0x95, 0x41, 0xee, 0x00}}},
    {{{     0,    -11,     -7}, 0, {     0,      0}, {0x95, 0xbf, 0xee, 0x00}}},
    {{{   168,      8,     -5}, 0, {     0,      0}, {0x13, 0x71, 0xcc, 0x00}}},
    {{{   175,      0,      0}, 0, {     0,      0}, {0x7c, 0x00, 0x18, 0x00}}},
    {{{   168,     -8,     -5}, 0, {     0,      0}, {0x13, 0x8f, 0xcc, 0x00}}},
    {{{     0,    -11,     -7}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
    {{{     0,     11,     -7}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0x00}}},
};

// 0x05006B08 - 0x05006B70
const Gfx manta_seg5_dl_05006B08[] = {
    gsSPLight(&manta_seg5_lights_05001770.l, 1),
    gsSPLight(&manta_seg5_lights_05001770.a, 2),
    gsSPVertex(manta_seg5_vertex_05006808, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  1,  0,  5, 0x0),
    gsSPLight(&manta_seg5_lights_05001758.l, 1),
    gsSPLight(&manta_seg5_lights_05001758.a, 2),
    gsSPVertex(manta_seg5_vertex_05006868, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006B70 - 0x05006C08
const Gfx manta_seg5_dl_05006B70[] = {
    gsSPLight(&manta_seg5_lights_05001770.l, 1),
    gsSPLight(&manta_seg5_lights_05001770.a, 2),
    gsSPVertex(manta_seg5_vertex_050068C8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  1,  7,  2, 0x0),
    gsSP2Triangles( 5,  1,  0, 0x0,  5,  0,  3, 0x0),
    gsSP2Triangles( 6,  7,  1, 0x0,  6,  1,  5, 0x0),
    gsSPLight(&manta_seg5_lights_05001758.l, 1),
    gsSPLight(&manta_seg5_lights_05001758.a, 2),
    gsSPVertex(manta_seg5_vertex_05006948, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  7,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05006C08 - 0x05006CA0
const Gfx manta_seg5_dl_05006C08[] = {
    gsSPLight(&manta_seg5_lights_05001770.l, 1),
    gsSPLight(&manta_seg5_lights_05001770.a, 2),
    gsSPVertex(manta_seg5_vertex_050069C8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  1,  7,  2, 0x0),
    gsSP2Triangles( 5,  8,  9, 0x0,  5,  9,  3, 0x0),
    gsSP2Triangles( 6, 10, 11, 0x0,  6, 11,  5, 0x0),
    gsSPLight(&manta_seg5_lights_05001758.l, 1),
    gsSPLight(&manta_seg5_lights_05001758.a, 2),
    gsSPVertex(manta_seg5_vertex_05006A88, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  7,  3, 0x0),
    gsSPEndDisplayList(),
};
