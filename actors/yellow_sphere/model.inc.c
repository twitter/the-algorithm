// Yellow Sphere (used in a lot of things)

// 0x0601EB48
static const Vtx yellow_sphere_seg6_vertex_0601EB48[] = {
    {{{   -76,    -76,      0}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    77,    -76,      0}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    77,     77,      0}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -76,     77,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0601EB88
ALIGNED8 static const Texture yellow_sphere_seg6_texture_0601EB88[] = {
#include "actors/yellow_sphere/yellow_sphere.rgba16.inc.c"
};

// 0x0601F388 - 0x0601F3C0
const Gfx yellow_sphere_seg6_dl_0601F388[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yellow_sphere_seg6_texture_0601EB88),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(yellow_sphere_seg6_vertex_0601EB48, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0601F3C0 - 0x0601F430
const Gfx yellow_sphere_seg6_dl_0601F3C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(yellow_sphere_seg6_dl_0601F388),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
