// Skeeter

// Unreferenced light group
UNUSED static const Lights1 skeeter_lights_unused1 = gdSPDefLights1(
    0x3f, 0x20, 0x02,
    0xfc, 0x80, 0x08, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 skeeter_lights_unused2 = gdSPDefLights1(
    0x05, 0x05, 0x02,
    0x15, 0x15, 0x08, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 skeeter_lights_unused3 = gdSPDefLights1(
    0x03, 0x10, 0x29,
    0x0e, 0x40, 0xa4, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 skeeter_lights_unused4 = gdSPDefLights1(
    0x3b, 0x3a, 0x36,
    0xef, 0xea, 0xd9, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 skeeter_lights_unused5 = gdSPDefLights1(
    0x33, 0x0c, 0x00,
    0xcf, 0x32, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 skeeter_lights_unused6 = gdSPDefLights1(
    0x10, 0x29, 0x20,
    0x42, 0xa5, 0x81, 0x28, 0x28, 0x28
);

// 0x06000090
ALIGNED8 static const Texture skeeter_seg6_texture_06000090[] = {
#include "actors/skeeter/skeeter_eye.rgba16.inc.c"
};

// 0x06000890
ALIGNED8 static const Texture skeeter_seg6_texture_06000890[] = {
#include "actors/skeeter/skeeter_iris.rgba16.inc.c"
};

// 0x06000990
static const Vtx skeeter_seg6_vertex_06000990[] = {
    {{{   -44,     45,      0}, 0, {     0,      0}, {0x00, 0xff, 0xd4, 0xff}}},
    {{{   -44,    -44,      0}, 0, {     0,    990}, {0x00, 0xff, 0xd4, 0xff}}},
    {{{    45,    -44,      0}, 0, {   990,    990}, {0x00, 0xff, 0xd4, 0xff}}},
    {{{    45,     45,      0}, 0, {   990,      0}, {0x00, 0xff, 0xd4, 0xff}}},
};

// 0x060009D0 - 0x06000A08
const Gfx skeeter_seg6_dl_060009D0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, skeeter_seg6_texture_06000090),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(skeeter_seg6_vertex_06000990, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06000A08 - 0x06000A78
const Gfx skeeter_seg6_dl_06000A08[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(skeeter_seg6_dl_060009D0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06000A78
static const Vtx skeeter_seg6_vertex_06000A78[] = {
    {{{   -14,     15,      0}, 0, {     0,      0}, {0xff, 0x55, 0x00, 0xff}}},
    {{{   -14,    -14,      0}, 0, {     0,    990}, {0xff, 0x55, 0x00, 0xff}}},
    {{{    15,    -14,      0}, 0, {   990,    990}, {0xff, 0x55, 0x00, 0xff}}},
    {{{    15,     15,      0}, 0, {   990,      0}, {0xff, 0x55, 0x00, 0xff}}},
};

// 0x06000AB8 - 0x06000AF0
const Gfx skeeter_seg6_dl_06000AB8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, skeeter_seg6_texture_06000090),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(skeeter_seg6_vertex_06000A78, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06000AF0 - 0x06000B60
const Gfx skeeter_seg6_dl_06000AF0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(skeeter_seg6_dl_06000AB8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06000B60
static const Vtx skeeter_seg6_vertex_06000B60[] = {
    {{{   -17,     18,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -17,    -17,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    18,    -17,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    18,     18,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06000BA0 - 0x06000BD8
const Gfx skeeter_seg6_dl_06000BA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, skeeter_seg6_texture_06000090),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(skeeter_seg6_vertex_06000B60, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06000BD8 - 0x06000C48
const Gfx skeeter_seg6_dl_06000BD8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(skeeter_seg6_dl_06000BA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06000C48
static const Vtx skeeter_seg6_vertex_06000C48[] = {
    {{{    66,      8,     69}, 0, {   536,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    85,    -24,    -35}, 0, {   -99,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    63,     75,    -10}, 0, {   218,    421}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06000C78 - 0x06000CA8
const Gfx skeeter_seg6_dl_06000C78[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, skeeter_seg6_texture_06000890),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 8 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(skeeter_seg6_vertex_06000C48, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06000CA8 - 0x06000D18
const Gfx skeeter_seg6_dl_06000CA8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 3, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (8 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(skeeter_seg6_dl_06000C78),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06000D18
static const Vtx skeeter_seg6_vertex_06000D18[] = {
    {{{    63,     75,     11}, 0, {   218,    421}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    85,    -24,     36}, 0, {   536,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    66,      8,    -68}, 0, {   -99,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06000D48 - 0x06000D78
const Gfx skeeter_seg6_dl_06000D48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, skeeter_seg6_texture_06000890),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 8 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(skeeter_seg6_vertex_06000D18, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06000D78 - 0x06000DE8
const Gfx skeeter_seg6_dl_06000D78[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 3, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (8 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(skeeter_seg6_dl_06000D48),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06000DE8
static const Lights1 skeeter_seg6_lights_06000DE8 = gdSPDefLights1(
    0x7f, 0x55, 0x00,
    0xff, 0xaa, 0x00, 0x28, 0x28, 0x28
);

// 0x06000E00
static const Vtx skeeter_seg6_vertex_06000E00[] = {
    {{{    45,    -24,   -122}, 0, {     0,      0}, {0x6a, 0xe9, 0xc0, 0xff}}},
    {{{    45,    120,     76}, 0, {     0,      0}, {0x24, 0x60, 0x4a, 0xff}}},
    {{{    45,    -24,    123}, 0, {     0,      0}, {0x28, 0xd6, 0x70, 0xff}}},
    {{{    45,    120,    -75}, 0, {     0,      0}, {0xa5, 0x45, 0xcb, 0xff}}},
    {{{   -14,      0,      0}, 0, {     0,      0}, {0x82, 0xfd, 0x00, 0xff}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xb2, 0x9d, 0x00, 0xff}}},
};

// 0x06000E60 - 0x06000EC0
const Gfx skeeter_seg6_dl_06000E60[] = {
    gsSPLight(&skeeter_seg6_lights_06000DE8.l, 1),
    gsSPLight(&skeeter_seg6_lights_06000DE8.a, 2),
    gsSPVertex(skeeter_seg6_vertex_06000E00, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 3,  0,  4, 0x0,  5,  2,  4, 0x0),
    gsSP2Triangles( 0,  5,  4, 0x0,  5,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06000EC0 - 0x06000EF0
const Gfx skeeter_seg6_dl_06000EC0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(skeeter_seg6_dl_06000E60),
    gsSPEndDisplayList(),
};

// 0x06000EF0
static const Vtx skeeter_seg6_vertex_06000EF0[] = {
    {{{   -15,      0,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
};

// 0x06000FA0
static const Vtx skeeter_seg6_vertex_06000FA0[] = {
    {{{   555,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   555,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06000FE0
static const Vtx skeeter_seg6_vertex_06000FE0[] = {
    {{{   405,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   405,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001020
static const Vtx skeeter_seg6_vertex_06001020[] = {
    {{{   -15,      0,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
};

// 0x060010D0
static const Vtx skeeter_seg6_vertex_060010D0[] = {
    {{{   555,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   555,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001110
static const Vtx skeeter_seg6_vertex_06001110[] = {
    {{{   405,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   405,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001150
static const Vtx skeeter_seg6_vertex_06001150[] = {
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{   -15,      0,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
};

// 0x06001200
static const Vtx skeeter_seg6_vertex_06001200[] = {
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   555,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   555,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001240
static const Vtx skeeter_seg6_vertex_06001240[] = {
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   405,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   405,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001280
static const Vtx skeeter_seg6_vertex_06001280[] = {
    {{{   -62,    -30,     17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -60,    -18,     34}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -45,    -52,     19}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -40,    -30,     51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -20,    -66,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -14,    -37,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     6,    -70,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    13,    -38,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    33,    -63,      8}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    39,    -34,     49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    54,    -46,      1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    59,    -24,     32}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    67,    -23,     -6}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,    -10,     10}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001380
static const Vtx skeeter_seg6_vertex_06001380[] = {
    {{{   -66,    -27,     -2}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -62,    -30,     17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -52,    -45,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -45,    -52,     19}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -30,    -57,    -31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -20,    -66,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -3,    -60,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     6,    -70,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    23,    -54,    -40}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    33,    -63,      8}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    47,    -39,    -36}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    54,    -46,      1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    63,    -19,    -26}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    67,    -23,     -6}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001480
static const Vtx skeeter_seg6_vertex_06001480[] = {
    {{{   -69,     -9,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -66,    -27,     -2}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -58,    -13,    -39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -52,    -45,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -37,    -15,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -30,    -57,    -31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -11,    -15,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -3,    -60,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    16,    -12,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    23,    -54,    -40}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    41,     -7,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    47,    -39,    -36}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,     -1,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    63,    -19,    -26}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001580
static const Vtx skeeter_seg6_vertex_06001580[] = {
    {{{   -70,     10,    -10}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -69,     -9,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -59,     24,    -32}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -58,    -13,    -39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -39,     34,    -49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -37,    -15,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -13,     38,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -11,    -15,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    14,     37,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    16,    -12,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    40,     30,    -51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    41,     -7,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,     18,    -34}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,     -1,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001680
static const Vtx skeeter_seg6_vertex_06001680[] = {
    {{{   -67,     23,      6}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     10,    -10}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -54,     46,     -1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -59,     24,    -32}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -33,     63,     -8}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -39,     34,    -49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -6,     70,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -13,     38,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    20,     66,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    14,     37,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    45,     52,    -19}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    40,     30,    -51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    62,     30,    -17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,     18,    -34}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001780
static const Vtx skeeter_seg6_vertex_06001780[] = {
    {{{   -63,     19,     26}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -67,     23,      6}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -47,     39,     36}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -54,     46,     -1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -23,     54,     40}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -33,     63,     -8}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     3,     60,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -6,     70,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    30,     57,     31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    20,     66,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    52,     45,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    45,     52,    -19}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    66,     27,      2}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    62,     30,    -17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001880
static const Vtx skeeter_seg6_vertex_06001880[] = {
    {{{   -60,      1,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -63,     19,     26}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -41,      7,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -47,     39,     36}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -16,     12,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -23,     54,     40}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    11,     15,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     3,     60,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    37,     15,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    30,     57,     31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    58,     13,     39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    52,     45,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    69,      9,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    66,     27,      2}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001980
static const Vtx skeeter_seg6_vertex_06001980[] = {
    {{{   -60,    -18,     34}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -60,      1,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     -4,     12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -40,    -30,     51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -41,      7,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -14,    -37,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -16,     12,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    13,    -38,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    11,     15,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    39,    -34,     49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    37,     15,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    59,    -24,     32}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    58,     13,     39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,    -10,     10}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    69,      9,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,    -12}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001A80
static const Vtx skeeter_seg6_vertex_06001A80[] = {
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{   -15,      0,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,   -114,      0}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,   -123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    -25,    123}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,    -76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
    {{{    45,    119,     76}, 0, {     0,      0}, {0xfc, 0x80, 0x08, 0x00}}},
};

// 0x06001B30
static const Vtx skeeter_seg6_vertex_06001B30[] = {
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   555,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   555,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001B70
static const Vtx skeeter_seg6_vertex_06001B70[] = {
    {{{     0,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   405,      0,     15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   405,      0,    -15}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x06001BB0
static const Vtx skeeter_seg6_vertex_06001BB0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -59,    -19,    -35}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -62,    -31,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -39,    -30,    -51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -44,    -52,    -20}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -13,    -37,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -20,    -66,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    14,    -38,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     7,    -70,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    40,    -34,    -49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    33,    -63,     -7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,    -24,    -31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    55,    -46,      0}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,    -10,     -9}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    67,    -22,      7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001CB0
static const Vtx skeeter_seg6_vertex_06001CB0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -62,    -31,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -66,    -27,      1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -44,    -52,    -20}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -52,    -46,     17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -20,    -66,    -18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -30,    -57,     30}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     7,    -70,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -3,    -60,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    33,    -63,     -7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    23,    -54,     41}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    55,    -46,      0}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    47,    -39,     37}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    67,    -22,      7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    63,    -18,     28}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001DB0
static const Vtx skeeter_seg6_vertex_06001DB0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -66,    -27,      1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,    -10,     13}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -52,    -46,     17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -59,    -14,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -30,    -57,     30}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -38,    -16,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -3,    -60,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -13,    -15,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    23,    -54,     41}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    14,    -12,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    47,    -39,     37}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    40,     -7,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    63,    -18,     28}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,     -1,     39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001EB0
static const Vtx skeeter_seg6_vertex_06001EB0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,    -10,     13}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     10,      9}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -59,    -14,     38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -60,     24,     31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -38,    -16,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -40,     34,     49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -13,    -15,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -14,     38,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    14,    -12,     69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    13,     37,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    40,     -7,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    39,     30,     51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,     -1,     39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    59,     19,     35}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x06001FB0
static const Vtx skeeter_seg6_vertex_06001FB0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -70,     10,      9}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -67,     22,     -7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -60,     24,     31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -55,     46,      0}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -40,     34,     49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -33,     63,      7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -14,     38,     58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -7,     70,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    13,     37,     59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    20,     66,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    39,     30,     51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    44,     52,     20}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    59,     19,     35}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    62,     31,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x060020B0
static const Vtx skeeter_seg6_vertex_060020B0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -67,     22,     -7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -63,     18,    -28}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -55,     46,      0}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -47,     39,    -37}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -33,     63,      7}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -23,     54,    -41}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    -7,     70,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     3,     60,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    20,     66,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    30,     57,    -30}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    44,     52,     20}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    52,     46,    -17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    62,     31,     18}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    66,     27,     -1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x060021B0
static const Vtx skeeter_seg6_vertex_060021B0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -63,     18,    -28}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -60,      1,    -39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -47,     39,    -37}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -40,      7,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -23,     54,    -41}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -14,     12,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{     3,     60,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    13,     15,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    30,     57,    -30}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    38,     16,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    52,     46,    -17}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    59,     14,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    66,     27,     -1}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,     10,    -13}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x060022B0
static const Vtx skeeter_seg6_vertex_060022B0[] = {
    {{{   -70,     -4,    -14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -60,      1,    -39}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -59,    -19,    -35}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -40,      7,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -39,    -30,    -51}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -14,     12,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{   -13,    -37,    -59}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    13,     15,    -69}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    14,    -38,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    38,     16,    -58}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    40,    -34,    -49}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    59,     14,    -38}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    60,    -24,    -31}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,     10,    -13}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,    -10,     -9}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
    {{{    70,      4,     14}, 0, {     0,      0}, {0xef, 0xea, 0xd9, 0x00}}},
};

// 0x060023B0
static const Vtx skeeter_seg6_vertex_060023B0[] = {
    {{{   -57,     -7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      0,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,      0,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,      0,     42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,      0,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,      0,     57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -30,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,      0,     60}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,      0,     57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060024B0
static const Vtx skeeter_seg6_vertex_060024B0[] = {
    {{{    15,      0,     57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,      0,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,      0,     42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,      0,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     -7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      0,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,    -13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     -7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060025B0
static const Vtx skeeter_seg6_vertex_060025B0[] = {
    {{{   -51,    -15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -51,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -30,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060026B0
static const Vtx skeeter_seg6_vertex_060026B0[] = {
    {{{    42,    -21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,    -13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     -7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,    -15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,    -13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060027B0
static const Vtx skeeter_seg6_vertex_060027B0[] = {
    {{{   -30,    -45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -60,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -51,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,    -15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,    -13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060028B0
static const Vtx skeeter_seg6_vertex_060028B0[] = {
    {{{   -57,    -13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,    -15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -51,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -60,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060029B0
static const Vtx skeeter_seg6_vertex_060029B0[] = {
    {{{    15,    -57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,    -13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,    -15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     -7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,    -13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x06002AB0
static const Vtx skeeter_seg6_vertex_06002AB0[] = {
    {{{   -51,    -26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -30,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -51,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x06002BB0
static const Vtx skeeter_seg6_vertex_06002BB0[] = {
    {{{    42,    -36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     -7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,    -13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      0,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     -7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,      0,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,    -15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,      0,    -42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,    -21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,      0,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,    -26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,      0,    -57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x06002CB0
static const Vtx skeeter_seg6_vertex_06002CB0[] = {
    {{{   -30,    -26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,      0,    -57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,    -28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,      0,    -60}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,    -30,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,      0,    -57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,    -28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,      0,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,    -26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,      0,    -42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,    -21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,      0,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,    -15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      0,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     -7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x06002DB0
static const Vtx skeeter_seg6_vertex_06002DB0[] = {
    {{{   -57,      7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      0,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,      0,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,      0,    -42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,      0,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,      0,    -57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     30,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,      0,    -60}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,      0,    -57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x06002EB0
static const Vtx skeeter_seg6_vertex_06002EB0[] = {
    {{{    15,      0,    -57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,      0,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,      0,    -42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,      0,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      0,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x06002FB0
static const Vtx skeeter_seg6_vertex_06002FB0[] = {
    {{{   -51,     15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     51,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     30,    -51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     28,    -50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     26,    -45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060030B0
static const Vtx skeeter_seg6_vertex_060030B0[] = {
    {{{    42,     21,    -36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     15,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      7,    -13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060031B0
static const Vtx skeeter_seg6_vertex_060031B0[] = {
    {{{   -30,     45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     60,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     51,    -30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     50,    -28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     45,    -26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     36,    -21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     26,    -15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     13,     -7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060032B0
static const Vtx skeeter_seg6_vertex_060032B0[] = {
    {{{   -57,     13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     51,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     60,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060033B0
static const Vtx skeeter_seg6_vertex_060033B0[] = {
    {{{    15,     57,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     51,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     42,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     30,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     15,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,     13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060034B0
static const Vtx skeeter_seg6_vertex_060034B0[] = {
    {{{   -51,     26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     30,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     51,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     50,     28}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     45,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060035B0
static const Vtx skeeter_seg6_vertex_060035B0[] = {
    {{{    42,     36,     21}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     26,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,     13,      7}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      0,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -57,      7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,      0,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -51,     15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,      0,     42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -42,     21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,      0,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -30,     26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,      0,     57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060036B0
static const Vtx skeeter_seg6_vertex_060036B0[] = {
    {{{   -30,     26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,      0,     57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{   -15,     28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,      0,     60}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{     0,     30,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,      0,     57}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    15,     28,     50}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,      0,     51}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    30,     26,     45}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,      0,     42}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    42,     21,     36}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,      0,     30}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    51,     15,     26}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      0,     15}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    57,      7,     13}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
    {{{    60,      0,      0}, 0, {     0,      0}, {0xcf, 0x32, 0x00, 0x00}}},
};

// 0x060037B0
static const Vtx skeeter_seg6_vertex_060037B0[] = {
    {{{   162,      7,     -7}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,      7,     -7}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{     0,     -7,      7}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
    {{{   162,     -7,      7}, 0, {     0,      0}, {0x15, 0x15, 0x08, 0x00}}},
};

// 0x060037F0
static const Vtx skeeter_seg6_vertex_060037F0[] = {
    {{{    48,   -166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -166,     68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,   -127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -127,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,    -68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    -68,    166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,      0,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,      0,    180}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,     68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,     68,    166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,    127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    127,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    48,    166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    166,     68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x060038F0
static const Vtx skeeter_seg6_vertex_060038F0[] = {
    {{{    68,   -166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    48,   -166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,   -127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,   -127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   166,    -68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,    -68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   180,      0,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,      0,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   166,     68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,     68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,    127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,    127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    68,    166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    48,    166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x060039F0
static const Vtx skeeter_seg6_vertex_060039F0[] = {
    {{{    48,   -166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    68,   -166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,   -127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,   -127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,    -68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   166,    -68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,      0,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   180,      0,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,     68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   166,     68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,    127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,    127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    48,    166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    68,    166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x06003AF0
static const Vtx skeeter_seg6_vertex_06003AF0[] = {
    {{{     0,   -166,    -68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    48,   -166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -127,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,   -127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    -68,   -166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,    -68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,      0,   -180}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   127,      0,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,     68,   -166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   117,     68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    127,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    90,    127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    166,    -68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{    48,    166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x06003BF0
static const Vtx skeeter_seg6_vertex_06003BF0[] = {
    {{{   -48,   -166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -166,    -68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,   -127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -127,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,    -68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    -68,   -166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,      0,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,      0,   -180}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,     68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,     68,   -166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,    127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    127,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -48,    166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    166,    -68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x06003CF0
static const Vtx skeeter_seg6_vertex_06003CF0[] = {
    {{{   -68,   -166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -48,   -166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,   -127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,   -127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -166,    -68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,    -68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -180,      0,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,      0,   -127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -166,     68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,     68,   -117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,    127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,    127,    -90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -68,    166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -48,    166,    -48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x06003DF0
static const Vtx skeeter_seg6_vertex_06003DF0[] = {
    {{{   -48,   -166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -68,   -166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,   -127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,   -127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,    -68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -166,    -68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,      0,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -180,      0,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,     68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -166,     68,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,    127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,    127,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -48,    166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -68,    166,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x06003EF0
static const Vtx skeeter_seg6_vertex_06003EF0[] = {
    {{{     0,   -166,     68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -48,   -166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,   -127,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,   -127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    -68,    166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,    -68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,      0,    180}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -127,      0,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,     68,    166}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{  -117,     68,    117}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    127,    127}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -90,    127,     90}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    166,     68}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{   -48,    166,     48}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
    {{{     0,    180,      0}, 0, {     0,      0}, {0x42, 0xa5, 0x81, 0x00}}},
};

// 0x06003FF0 - 0x06004040
const Gfx skeeter_seg6_dl_06003FF0[] = {
    gsSPVertex(skeeter_seg6_vertex_06000EF0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  6,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06004040 - 0x06004070
const Gfx skeeter_seg6_dl_06004040[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06000FA0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004070 - 0x060040A0
const Gfx skeeter_seg6_dl_06004070[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06000FE0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x060040A0 - 0x060040F0
const Gfx skeeter_seg6_dl_060040A0[] = {
    gsSPVertex(skeeter_seg6_vertex_06001020, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  4,  5, 0x0,  0,  5,  1, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  6,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x060040F0 - 0x06004120
const Gfx skeeter_seg6_dl_060040F0[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_060010D0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004120 - 0x06004150
const Gfx skeeter_seg6_dl_06004120[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06001110, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004150 - 0x060041A0
const Gfx skeeter_seg6_dl_06004150[] = {
    gsSPVertex(skeeter_seg6_vertex_06001150, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  3,  2, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7, 10,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x060041A0 - 0x060041D0
const Gfx skeeter_seg6_dl_060041A0[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06001200, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x060041D0 - 0x06004200
const Gfx skeeter_seg6_dl_060041D0[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06001240, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004200 - 0x060045C8
const Gfx skeeter_seg6_dl_06004200[] = {
    gsSPVertex(skeeter_seg6_vertex_06001280, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001380, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001480, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001580, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001680, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001780, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001880, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001980, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x060045C8 - 0x06004618
const Gfx skeeter_seg6_dl_060045C8[] = {
    gsSPVertex(skeeter_seg6_vertex_06001A80, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  3,  2, 0x0,  1,  5,  2, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  6,  7,  8, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0,  7, 10,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x06004618 - 0x06004648
const Gfx skeeter_seg6_dl_06004618[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06001B30, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004648 - 0x06004678
const Gfx skeeter_seg6_dl_06004648[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_06001B70, 4, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSP1Triangle( 0,  2,  3, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06004678 - 0x06004A40
const Gfx skeeter_seg6_dl_06004678[] = {
    gsSPVertex(skeeter_seg6_vertex_06001BB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001CB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001DB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001EB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06001FB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060020B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060021B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060022B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 3,  2,  1, 0x0,  5,  6,  4, 0x0),
    gsSP2Triangles( 5,  4,  3, 0x0,  7,  8,  6, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  9, 10,  8, 0x0),
    gsSP2Triangles( 9,  8,  7, 0x0, 11, 12, 10, 0x0),
    gsSP2Triangles(11, 10,  9, 0x0, 13, 14, 12, 0x0),
    gsSP2Triangles(13, 12, 11, 0x0, 15, 14, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x06004A40 - 0x06005328
const Gfx skeeter_seg6_dl_06004A40[] = {
    gsSPVertex(skeeter_seg6_vertex_060023B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060024B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 10, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060025B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060026B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060027B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060028B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060029B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 10, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06002AB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06002BB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06002CB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06002DB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06002EB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 10, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06002FB0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060030B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060031B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060032B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060033B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 11, 10, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060034B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060035B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060036B0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 2,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x06005328 - 0x06005358
const Gfx skeeter_seg6_dl_06005328[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPVertex(skeeter_seg6_vertex_060037B0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06005358 - 0x06005720
const Gfx skeeter_seg6_dl_06005358[] = {
    gsSPVertex(skeeter_seg6_vertex_060037F0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060038F0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_060039F0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06003AF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06003BF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06003CF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06003DF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(skeeter_seg6_vertex_06003EF0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};
