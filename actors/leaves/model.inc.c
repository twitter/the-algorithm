// Leaves

// 0x0301CBA0
static const Vtx leaves_seg3_vertex_0301CBA0[] = {
    {{{   -10,    -10,      0}, 0, {     0,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    10,    -10,      0}, 0, {   480,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    10,     10,      0}, 0, {   480,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -10,     10,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0301CBE0
ALIGNED8 static const Texture leaves_seg3_texture_0301CBE0[] = {
#include "actors/leaves/leaf.rgba16.inc.c"
};

// 0x0301CDE0 - 0x0301CE70
const Gfx leaves_seg3_dl_0301CDE0[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPLoadTextureBlock(leaves_seg3_texture_0301CBE0, G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 16, 0, G_TX_CLAMP, G_TX_CLAMP, 4, 4, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(leaves_seg3_vertex_0301CBA0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
