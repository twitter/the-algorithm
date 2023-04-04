// Bubble

// 0x0401CD20
static const Vtx bubble_seg4_vertex_0401CD20[] = {
    {{{   -16,      0,      0}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    16,      0,      0}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    16,     32,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -16,     32,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0401CD60
ALIGNED8 static const Texture bubble_seg4_texture_0401CD60[] = {
#include "actors/bubble/bubble.rgba16.inc.c"
};

// 0x0401D560
ALIGNED8 static const Texture bubble_seg4_texture_0401D560[] = {
#include "actors/bubble/mr_i_bubble.rgba16.inc.c"
};

// 0x0401DD60 - 0x0401DDE0
const Gfx bubble_seg4_dl_0401DD60[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPLoadTextureBlock(bubble_seg4_texture_0401CD60, G_IM_FMT_RGBA, G_IM_SIZ_16b, 32, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(bubble_seg4_vertex_0401CD20, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x0401DDE0 - 0x0401DE60
const Gfx bubble_seg4_dl_0401DDE0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPLoadTextureBlock(bubble_seg4_texture_0401D560, G_IM_FMT_RGBA, G_IM_SIZ_16b, 32, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(bubble_seg4_vertex_0401CD20, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
