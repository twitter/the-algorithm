// Fwoosh

// 0x050157C8
static const Vtx fwoosh_seg5_vertex_050157C8[] = {
    {{{   -19,    -19,      0}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    20,    -19,      0}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    20,     20,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -19,     20,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x05015808
ALIGNED8 static const Texture fwoosh_seg5_texture_05015808[] = {
#include "actors/fwoosh/fwoosh_face.ia16.inc.c"
};

// 0x05016008 - 0x05016040
const Gfx fwoosh_seg5_dl_05016008[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, fwoosh_seg5_texture_05015808),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(fwoosh_seg5_vertex_050157C8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05016040 - 0x050160B0
const Gfx fwoosh_seg5_dl_05016040[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(fwoosh_seg5_dl_05016008),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
