// Chain Ball


// 0x06020AA0
static const Vtx chain_ball_seg6_vertex_06020AA0[] = {
    {{{   -26,    -26,      0}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    26,    -26,      0}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    26,     26,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -26,     26,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// align? binid?
UNUSED static const u64 chain_ball_unused_1 = 0;

// 0x06020AE8
ALIGNED8 static const Texture chain_ball_seg6_texture_06020AE8[] = {
#include "actors/chain_ball/chain_ball.rgba16.inc.c"
};

// 0x060212E8 - 0x06021380
const Gfx chain_ball_seg6_dl_060212E8[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsDPLoadTextureBlock(chain_ball_seg6_texture_06020AE8, G_IM_FMT_RGBA, G_IM_SIZ_16b, 32, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 5, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(chain_ball_seg6_vertex_06020AA0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
