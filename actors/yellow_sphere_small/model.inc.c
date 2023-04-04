// Yellow Sphere (used in a lot of things)

// 0x05000000
static const Vtx yellow_sphere_seg5_vertex_05000000[] = {
    {{{   -49,    -49,      0}, 0, {     0,    992}, {0xb5, 0x20, 0x40, 0xff}}},
    {{{    50,    -49,      0}, 0, {   992,    992}, {0xb5, 0x20, 0x40, 0xff}}},
    {{{    50,     50,      0}, 0, {   992,      0}, {0xb5, 0x20, 0x40, 0xff}}},
    {{{   -49,     50,      0}, 0, {     0,      0}, {0xb5, 0x20, 0x40, 0xff}}},
};

// 0x05000040
ALIGNED8 static const Texture yellow_sphere_seg5_texture_05000040[] = {
#include "actors/yellow_sphere_small/small_yellow_sphere.rgba16.inc.c"
};

// 0x05000840 - 0x05000888
const Gfx yellow_sphere_seg5_dl_05000840[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x05000888 - 0x050008C8
const Gfx yellow_sphere_seg5_dl_05000888[] = {
    gsSPVertex(yellow_sphere_seg5_vertex_05000000, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x050008C8 - 0x050008F8
const Gfx yellow_sphere_seg5_dl_050008C8[] = {
    gsSPDisplayList(yellow_sphere_seg5_dl_05000840),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, yellow_sphere_seg5_texture_05000040),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(yellow_sphere_seg5_dl_05000888),
    gsSPEndDisplayList(),
};
