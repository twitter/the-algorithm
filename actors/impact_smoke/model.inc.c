// Impact Smoke

// TODO: These 64x64 textures are referenced as two different texture addresses in the DLs

// 0x0605AA28
ALIGNED8 static const u8 impact_smoke_seg6_texture_0605AA28[] = {
#include "actors/impact_smoke/impact_smoke_0.ia16.inc.c"
};

// 0x0605CA28
ALIGNED8 static const u8 impact_smoke_seg6_texture_0605CA28[] = {
#include "actors/impact_smoke/impact_smoke_1.ia16.inc.c"
};

// 0x0605EA28
ALIGNED8 static const u8 impact_smoke_seg6_texture_0605EA28[] = {
#include "actors/impact_smoke/impact_smoke_2.ia16.inc.c"
};

// 0x06060A28
ALIGNED8 static const u8 impact_smoke_seg6_texture_06060A28[] = {
#include "actors/impact_smoke/impact_smoke_3.ia16.inc.c"
};

// 0x06062A28
static const Vtx impact_smoke_seg6_vertex_06062A28[] = {
    {{{  -150,    150,      0}, 0, {     0,    992}, {0x28, 0x19, 0x14, 0xff}}},
    {{{   150,    150,      0}, 0, {  2016,    992}, {0x28, 0x19, 0x14, 0xff}}},
    {{{   150,    300,      0}, 0, {  2016,      0}, {0x28, 0x19, 0x14, 0xff}}},
    {{{  -150,    300,      0}, 0, {     0,      0}, {0x28, 0x19, 0x14, 0xff}}},
    {{{  -150,      0,      0}, 0, {     0,    992}, {0x28, 0x19, 0x14, 0xff}}},
    {{{   150,      0,      0}, 0, {  2016,    992}, {0x28, 0x19, 0x14, 0xff}}},
    {{{   150,    150,      0}, 0, {  2016,      0}, {0x28, 0x19, 0x14, 0xff}}},
    {{{  -150,    150,      0}, 0, {     0,      0}, {0x28, 0x19, 0x14, 0xff}}},
};

// 0x06062AA8 - 0x06062AD8
const Gfx impact_smoke_seg6_dl_06062AA8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEFADE, G_CC_MODULATEFADE),
    gsSPGeometryMode(G_LIGHTING, G_SHADING_SMOOTH),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPEndDisplayList(),
};

// 0x06062AD8 - 0x06062AF0
const Gfx impact_smoke_seg6_dl_06062AD8[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06062AF0 - 0x06062B08
const Gfx impact_smoke_seg6_dl_06062AF0[] = {
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x06062B08 - 0x06062B38
const Gfx impact_smoke_seg6_dl_06062B08[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x06062B38 - 0x06062BD8
const Gfx impact_smoke_seg6_dl_06062B38[] = {
    gsSPDisplayList(impact_smoke_seg6_dl_06062AA8),
    gsDPLoadTextureBlock(impact_smoke_seg6_texture_0605AA28, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(impact_smoke_seg6_vertex_06062A28, 8, 0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AD8),
    gsDPLoadTextureBlock((u8*)impact_smoke_seg6_texture_0605AA28 + 0x1000, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AF0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062B08),
    gsSPEndDisplayList(),
};

// 0x06062BD8 - 0x06062C78
const Gfx impact_smoke_seg6_dl_06062BD8[] = {
    gsSPDisplayList(impact_smoke_seg6_dl_06062AA8),
    gsDPLoadTextureBlock(impact_smoke_seg6_texture_0605CA28, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(impact_smoke_seg6_vertex_06062A28, 8, 0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AD8),
    gsDPLoadTextureBlock((u8*)impact_smoke_seg6_texture_0605CA28 + 0x1000, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AF0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062B08),
    gsSPEndDisplayList(),
};

// 0x06062C78 - 0x06062D18
const Gfx impact_smoke_seg6_dl_06062C78[] = {
    gsSPDisplayList(impact_smoke_seg6_dl_06062AA8),
    gsDPLoadTextureBlock(impact_smoke_seg6_texture_0605EA28, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(impact_smoke_seg6_vertex_06062A28, 8, 0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AD8),
    gsDPLoadTextureBlock((u8*)impact_smoke_seg6_texture_0605EA28 + 0x1000, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AF0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062B08),
    gsSPEndDisplayList(),
};

// 0x06062D18 - 0x06062DB8
const Gfx impact_smoke_seg6_dl_06062D18[] = {
    gsSPDisplayList(impact_smoke_seg6_dl_06062AA8),
    gsDPLoadTextureBlock(impact_smoke_seg6_texture_06060A28, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(impact_smoke_seg6_vertex_06062A28, 8, 0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AD8),
    gsDPLoadTextureBlock((u8*)impact_smoke_seg6_texture_06060A28 + 0x1000, G_IM_FMT_IA, G_IM_SIZ_16b, 64, 32, 0, G_TX_CLAMP, G_TX_CLAMP, 6, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPDisplayList(impact_smoke_seg6_dl_06062AF0),
    gsSPDisplayList(impact_smoke_seg6_dl_06062B08),
    gsSPEndDisplayList(),
};
