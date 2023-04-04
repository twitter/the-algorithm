// Checkerboard Platform

// 0x0800C828
static const Lights1 checkerboard_platform_seg8_lights_0800C828 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0800C840
ALIGNED8 static const Texture checkerboard_platform_seg8_texture_0800C840[] = {
#include "actors/checkerboard_platform/checkerboard_platform_side.rgba16.inc.c"
};

// 0x0800CC40
ALIGNED8 static const Texture checkerboard_platform_seg8_texture_0800CC40[] = {
#include "actors/checkerboard_platform/checkerboard_platform.rgba16.inc.c"
};

// 0x0800D440
static const Vtx checkerboard_platform_seg8_vertex_0800D440[] = {
    {{{  -255,    -25,    154}, 0, {  1504,    480}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,     26,    154}, 0, {  1504,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,     26,   -153}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -255,    -25,   -153}, 0, {     0,    480}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   256,    -25,   -153}, 0, {  1504,    480}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,     26,    154}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,    -25,    154}, 0, {     0,    480}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   256,     26,   -153}, 0, {  1504,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0800D4C0
static const Vtx checkerboard_platform_seg8_vertex_0800D4C0[] = {
    {{{   256,    -25,    154}, 0, {  2528,    992}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,    -25,   -153}, 0, {     0,   -512}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,    -25,   -153}, 0, {  2528,   -512}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   256,     26,   -153}, 0, {  2528,   -512}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,     26,    154}, 0, {     0,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,     26,    154}, 0, {  2528,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,     26,   -153}, 0, {     0,   -512}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,    -25,   -153}, 0, {  2528,    480}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -255,     26,   -153}, 0, {  2528,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,     26,   -153}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,    -25,   -153}, 0, {     0,    480}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   256,    -25,    154}, 0, {  2528,    480}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,     26,    154}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,    -25,    154}, 0, {     0,    480}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   256,     26,    154}, 0, {  2528,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -255,    -25,    154}, 0, {     0,    992}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0800D5C0 - 0x0800D618
const Gfx checkerboard_platform_seg8_dl_0800D5C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, checkerboard_platform_seg8_texture_0800C840),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&checkerboard_platform_seg8_lights_0800C828.l, 1),
    gsSPLight(&checkerboard_platform_seg8_lights_0800C828.a, 2),
    gsSPVertex(checkerboard_platform_seg8_vertex_0800D440, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800D618 - 0x0800D680
const Gfx checkerboard_platform_seg8_dl_0800D618[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, checkerboard_platform_seg8_texture_0800CC40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(checkerboard_platform_seg8_vertex_0800D4C0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800D680 - 0x0800D710
const Gfx checkerboard_platform_seg8_dl_0800D680[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(checkerboard_platform_seg8_dl_0800D5C0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(checkerboard_platform_seg8_dl_0800D618),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

