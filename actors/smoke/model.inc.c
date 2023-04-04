// Smoke

// 0x05007280
static const Vtx smoke_seg5_vertex_05007280[] = {
    {{{    26,     26,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xb4}}},
    {{{   -25,     26,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xb4}}},
    {{{   -25,    -25,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xb4}}},
    {{{    26,    -25,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xb4}}},
};

// 0x050072C0
ALIGNED8 static const Texture smoke_seg5_texture_050072C0[] = {
#include "actors/smoke/smoke.ia16.inc.c"
};

// 0x05007AC0 - 0x05007AF8
const Gfx smoke_seg5_dl_05007AC0[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, smoke_seg5_texture_050072C0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(smoke_seg5_vertex_05007280, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05007AF8 - 0x05007B68
const Gfx smoke_seg5_dl_05007AF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(smoke_seg5_dl_05007AC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
