// Heart

// 0x0800D7A0
static const Vtx heart_seg8_vertex_0800D7A0[] = {
    {{{   -50,    -50,      0}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xb4}}},
    {{{    50,    -50,      0}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xb4}}},
    {{{    50,     50,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xb4}}},
    {{{   -50,     50,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xb4}}},
};

// 0x0800D7E0
ALIGNED8 static const Texture heart_seg8_texture_0800D7E0[] = {
#include "actors/heart/spinning_heart.rgba16.inc.c"
};

// 0x0800DFE0 - 0x0800E078
const Gfx heart_seg8_dl_0800DFE0[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsDPLoadTextureBlock(heart_seg8_texture_0800D7E0, G_IM_FMT_RGBA, G_IM_SIZ_16b, 32, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(heart_seg8_vertex_0800D7A0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
