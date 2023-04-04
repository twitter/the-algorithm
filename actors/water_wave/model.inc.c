// Water Waves

// 0x04025318
static const Vtx water_wave_seg4_vertex_04025318[] = {
    {{{   -64,      0,     64}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xc8}}},
    {{{    64,      0,     64}, 0, {   992,      0}, {0xff, 0xff, 0xff, 0xc8}}},
    {{{    64,      0,    -64}, 0, {   992,    992}, {0xff, 0xff, 0xff, 0xc8}}},
    {{{   -64,      0,    -64}, 0, {     0,    992}, {0xff, 0xff, 0xff, 0xc8}}},
};

// 0x04025358
ALIGNED8 static const Texture water_wave_seg4_texture_04025358[] = {
#include "actors/water_wave/water_wave_0.ia16.inc.c"
};

// 0x04025B58
ALIGNED8 static const Texture water_wave_seg4_texture_04025B58[] = {
#include "actors/water_wave/water_wave_1.ia16.inc.c"
};

// 0x04026358
ALIGNED8 static const Texture water_wave_seg4_texture_04026358[] = {
#include "actors/water_wave/water_wave_2.ia16.inc.c"
};

// 0x04026B58
ALIGNED8 static const Texture water_wave_seg4_texture_04026B58[] = {
#include "actors/water_wave/water_wave_3.ia16.inc.c"
};

// 0x04027358 - 0x040273A0
const Gfx water_wave_seg4_dl_04027358[] = {
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x040273A0 - 0x040273D8
const Gfx water_wave_seg4_dl_040273A0[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsDPPipeSync(),
    gsSPTexture(0x0001, 0x0001, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x040273D8 - 0x040273F0
const Gfx water_wave_seg4_dl_040273D8[] = {
    gsSPDisplayList(water_wave_seg4_dl_04027358),
    gsSPVertex(water_wave_seg4_vertex_04025318, 4, 0),
    gsSPBranchList(water_wave_seg4_dl_040273A0),
};

// 0x040273F0 - 0x04027408
const Gfx water_wave_seg4_dl_040273F0[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, water_wave_seg4_texture_04025358),
    gsSPBranchList(water_wave_seg4_dl_040273D8),
};

// 0x04027408 - 0x04027420
const Gfx water_wave_seg4_dl_04027408[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, water_wave_seg4_texture_04025B58),
    gsSPBranchList(water_wave_seg4_dl_040273D8),
};

// 0x04027420 - 0x04027438
const Gfx water_wave_seg4_dl_04027420[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, water_wave_seg4_texture_04026358),
    gsSPBranchList(water_wave_seg4_dl_040273D8),
};

// 0x04027438 - 0x04027450
const Gfx water_wave_seg4_dl_04027438[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, water_wave_seg4_texture_04026B58),
    gsSPBranchList(water_wave_seg4_dl_040273D8),
};
