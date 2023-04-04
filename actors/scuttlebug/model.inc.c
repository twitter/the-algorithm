// Scuttlebug

// Unreferenced light group
UNUSED static const Lights1 scuttlebug_lights_unused1 = gdSPDefLights1(
    0x2c, 0x00, 0x00,
    0xb2, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 scuttlebug_lights_unused2 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x06010108
ALIGNED8 static const Texture scuttlebug_seg6_texture_06010108[] = {
#include "actors/scuttlebug/scuttlebug_eye.rgba16.inc.c"
};

// 0x06010908
ALIGNED8 static const Texture scuttlebug_seg6_texture_06010908[] = {
#include "actors/scuttlebug/scuttlebug_left_side.rgba16.inc.c"
};

// 0x06011908
ALIGNED8 static const Texture scuttlebug_seg6_texture_06011908[] = {
#include "actors/scuttlebug/scuttlebug_right_side.rgba16.inc.c"
};

// 0x06012908
ALIGNED8 static const Texture scuttlebug_seg6_texture_06012908[] = {
#include "actors/scuttlebug/scuttlebug_iris.rgba16.inc.c"
};

// 0x06013108
ALIGNED8 static const Texture scuttlebug_seg6_texture_06013108[] = {
#include "actors/scuttlebug/scuttlebug_leg.rgba16.inc.c"
};

// 0x06013908
static const Vtx scuttlebug_seg6_vertex_06013908[] = {
    {{{   -44,    -44,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -44,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     44,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -44,     44,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06013948
static const Vtx scuttlebug_seg6_vertex_06013948[] = {
    {{{     0,    -44,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    44,     44,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     44,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    44,    -44,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06013988 - 0x060139C0
const Gfx scuttlebug_seg6_dl_06013988[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06010908),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06013908, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x060139C0 - 0x060139F8
const Gfx scuttlebug_seg6_dl_060139C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06011908),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06013948, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x060139F8 - 0x06013A70
const Gfx scuttlebug_seg6_dl_060139F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06013988),
    gsSPDisplayList(scuttlebug_seg6_dl_060139C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06013A70
static const Vtx scuttlebug_seg6_vertex_06013A70[] = {
    {{{    15,     15,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,     15,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,    -15,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    15,    -15,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06013AB0 - 0x06013AE8
const Gfx scuttlebug_seg6_dl_06013AB0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06010108),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06013A70, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06013AE8 - 0x06013B58
const Gfx scuttlebug_seg6_dl_06013AE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06013AB0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06013B58
static const Vtx scuttlebug_seg6_vertex_06013B58[] = {
    {{{    15,     15,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,     15,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -15,    -15,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    15,    -15,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06013B98 - 0x06013BD0
const Gfx scuttlebug_seg6_dl_06013B98[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06010108),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06013B58, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06013BD0 - 0x06013C40
const Gfx scuttlebug_seg6_dl_06013BD0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06013B98),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06013C40
static const Vtx scuttlebug_seg6_vertex_06013C40[] = {
    {{{    60,    -30,     31}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,    -30,    -30}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     32,    -30}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,     32,     31}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06013C80 - 0x06013CB8
const Gfx scuttlebug_seg6_dl_06013C80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06012908),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06013C40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06013CB8 - 0x06013D28
const Gfx scuttlebug_seg6_dl_06013CB8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06013C80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06013D28
static const Vtx scuttlebug_seg6_vertex_06013D28[] = {
    {{{    60,    -30,     31}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    60,    -30,    -30}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    59,     31,    -30}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    59,     31,     31}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06013D68 - 0x06013DA0
const Gfx scuttlebug_seg6_dl_06013D68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06012908),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06013D28, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06013DA0 - 0x06013E10
const Gfx scuttlebug_seg6_dl_06013DA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06013D68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06013E10
static const Lights1 scuttlebug_seg6_lights_06013E10 = gdSPDefLights1(
    0x33, 0x22, 0x01,
    0xcf, 0x89, 0x06, 0x28, 0x28, 0x28
);

// 0x06013E28
static const Vtx scuttlebug_seg6_vertex_06013E28[] = {
    {{{  -111,     85,    293}, 0, {     0,      0}, {0x7e, 0x06, 0x0b, 0xff}}},
    {{{  -120,    127,    192}, 0, {     0,      0}, {0x34, 0x64, 0xc8, 0xff}}},
    {{{  -120,    103,    301}, 0, {     0,      0}, {0xc9, 0x62, 0x3a, 0xff}}},
    {{{  -128,    -84,    293}, 0, {     0,      0}, {0x82, 0xfa, 0x0b, 0xff}}},
    {{{  -120,   -126,    192}, 0, {     0,      0}, {0xc7, 0x9e, 0xc8, 0xff}}},
    {{{  -120,   -102,    301}, 0, {     0,      0}, {0x32, 0x9c, 0x3b, 0xff}}},
    {{{  -103,    -88,    199}, 0, {     0,      0}, {0x7e, 0xf9, 0xf3, 0xff}}},
    {{{  -111,    -84,    293}, 0, {     0,      0}, {0x7d, 0x10, 0x0c, 0xff}}},
    {{{  -120,    -29,    351}, 0, {     0,      0}, {0xf4, 0x3d, 0x6e, 0xff}}},
    {{{  -136,    -88,    199}, 0, {     0,      0}, {0x83, 0x10, 0xf4, 0xff}}},
    {{{  -120,    -58,    167}, 0, {     0,      0}, {0xfe, 0x1f, 0x85, 0xff}}},
    {{{  -120,    -49,    205}, 0, {     0,      0}, {0x2f, 0x75, 0x04, 0xff}}},
    {{{  -120,    -67,    284}, 0, {     0,      0}, {0xc9, 0x72, 0xf9, 0xff}}},
    {{{  -120,     68,    284}, 0, {     0,      0}, {0x32, 0x8c, 0xf8, 0xff}}},
    {{{  -103,     89,    199}, 0, {     0,      0}, {0x7c, 0xee, 0xf3, 0xff}}},
    {{{  -120,     30,    351}, 0, {     0,      0}, {0xf4, 0xc3, 0x6e, 0xff}}},
};

// 0x06013F28
static const Vtx scuttlebug_seg6_vertex_06013F28[] = {
    {{{  -103,     89,    199}, 0, {     0,      0}, {0x7c, 0xee, 0xf3, 0xff}}},
    {{{  -120,     59,    167}, 0, {     0,      0}, {0xfe, 0xe1, 0x85, 0xff}}},
    {{{  -120,    127,    192}, 0, {     0,      0}, {0x34, 0x64, 0xc8, 0xff}}},
    {{{  -136,     89,    199}, 0, {     0,      0}, {0x82, 0x06, 0xf4, 0xff}}},
    {{{  -120,    103,    301}, 0, {     0,      0}, {0xc9, 0x62, 0x3a, 0xff}}},
    {{{  -111,     85,    293}, 0, {     0,      0}, {0x7e, 0x06, 0x0b, 0xff}}},
    {{{  -120,     30,    351}, 0, {     0,      0}, {0xf4, 0xc3, 0x6e, 0xff}}},
    {{{  -128,     85,    293}, 0, {     0,      0}, {0x83, 0xf1, 0x0b, 0xff}}},
    {{{  -120,     50,    205}, 0, {     0,      0}, {0xcd, 0x8c, 0x03, 0xff}}},
    {{{  -120,     68,    284}, 0, {     0,      0}, {0x32, 0x8c, 0xf8, 0xff}}},
};

// 0x06013FC8 - 0x060140F0
const Gfx scuttlebug_seg6_dl_06013FC8[] = {
    gsSPLight(&scuttlebug_seg6_lights_06013E10.l, 1),
    gsSPLight(&scuttlebug_seg6_lights_06013E10.a, 2),
    gsSPVertex(scuttlebug_seg6_vertex_06013E28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 8,  5,  7, 0x0,  3,  5,  8, 0x0),
    gsSP2Triangles( 9, 10,  4, 0x0,  3,  9,  4, 0x0),
    gsSP2Triangles(10,  6,  4, 0x0, 10, 11,  6, 0x0),
    gsSP2Triangles(11,  7,  6, 0x0, 12,  7, 11, 0x0),
    gsSP2Triangles(12,  8,  7, 0x0, 12,  9,  3, 0x0),
    gsSP2Triangles( 3,  8, 12, 0x0, 11, 10,  9, 0x0),
    gsSP2Triangles(11,  9, 12, 0x0, 13, 14,  0, 0x0),
    gsSP2Triangles( 0, 14,  1, 0x0, 15, 13,  0, 0x0),
    gsSPVertex(scuttlebug_seg6_vertex_06013F28, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 4,  2,  3, 0x0,  5,  4,  6, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  4,  3,  7, 0x0),
    gsSP2Triangles( 1,  8,  3, 0x0,  8,  7,  3, 0x0),
    gsSP2Triangles( 8,  1,  0, 0x0,  8,  0,  9, 0x0),
    gsSP2Triangles( 9,  7,  8, 0x0,  7,  9,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x060140F0 - 0x06014110
const Gfx scuttlebug_seg6_dl_060140F0[] = {
    gsDPPipeSync(),
    gsSPDisplayList(scuttlebug_seg6_dl_06013FC8),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x06014110
static const Vtx scuttlebug_seg6_vertex_06014110[] = {
    {{{   -21,     37,      0}, 0, {  -584,  -4238}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   191,     18,      0}, 0, {    28,    892}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   193,    -19,      0}, 0, {   988,    958}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -16,    -25,      0}, 0, {   982,  -4136}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06014150 - 0x06014188
const Gfx scuttlebug_seg6_dl_06014150[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06013108),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_06014110, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06014188 - 0x060141F8
const Gfx scuttlebug_seg6_dl_06014188[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06014150),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x060141F8
static const Vtx scuttlebug_seg6_vertex_060141F8[] = {
    {{{    -9,     39,      4}, 0, {  -560,  -4258}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   199,     20,      2}, 0, {    38,    948}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   201,    -17,     -2}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    -5,    -22,     -3}, 0, {   990,  -4192}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x06014238 - 0x06014270
const Gfx scuttlebug_seg6_dl_06014238[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, scuttlebug_seg6_texture_06013108),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(scuttlebug_seg6_vertex_060141F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x06014270 - 0x060142E0
const Gfx scuttlebug_seg6_dl_06014270[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(scuttlebug_seg6_dl_06014238),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x060142E0
static const Lights1 scuttlebug_seg6_lights_060142E0 = gdSPDefLights1(
    0x26, 0x05, 0x05,
    0x99, 0x16, 0x16, 0x28, 0x28, 0x28
);

// 0x060142F8
static const Vtx scuttlebug_seg6_vertex_060142F8[] = {
    {{{     0,     -1,      0}, 0, {     0,      0}, {0x82, 0x02, 0x00, 0xff}}},
    {{{    57,   -106,     -3}, 0, {     0,      0}, {0xcf, 0x8c, 0xfd, 0xff}}},
    {{{    57,     48,     83}, 0, {     0,      0}, {0xd0, 0x37, 0x67, 0xff}}},
    {{{    57,     54,    -81}, 0, {     0,      0}, {0xcf, 0x3d, 0x9d, 0xff}}},
};

// 0x06014338 - 0x06014378
const Gfx scuttlebug_seg6_dl_06014338[] = {
    gsSPLight(&scuttlebug_seg6_lights_060142E0.l, 1),
    gsSPLight(&scuttlebug_seg6_lights_060142E0.a, 2),
    gsSPVertex(scuttlebug_seg6_vertex_060142F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  2,  1, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  1,  0,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06014378 - 0x06014398
const Gfx scuttlebug_seg6_dl_06014378[] = {
    gsDPPipeSync(),
    gsSPDisplayList(scuttlebug_seg6_dl_06014338),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};
