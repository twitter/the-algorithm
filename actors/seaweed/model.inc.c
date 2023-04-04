// Seaweed

// 0x06007DF8
static const Lights1 seaweed_seg6_lights_06007DF8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x06007E10
ALIGNED8 static const Texture seaweed_seg6_texture_06007E10[] = {
#include "actors/seaweed/seaweed_tip.rgba16.inc.c"
};

// 0x06008610
ALIGNED8 static const Texture seaweed_seg6_texture_06008610[] = {
#include "actors/seaweed/seaweed_upper_center.rgba16.inc.c"
};

// 0x06008E10
ALIGNED8 static const Texture seaweed_seg6_texture_06008E10[] = {
#include "actors/seaweed/seaweed_lower_center.rgba16.inc.c"
};

// 0x06009610
ALIGNED8 static const Texture seaweed_seg6_texture_06009610[] = {
#include "actors/seaweed/seaweed_base.rgba16.inc.c"
};

// 0x06009E10
static const Vtx seaweed_seg6_vertex_06009E10[] = {
    {{{   232,      2,    -76}, 0, {   -30,    -12}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -1,      2,     77}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   232,      2,     77}, 0, {   992,    -12}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -1,      2,    -76}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x06009E50 - 0x06009E98
const Gfx seaweed_seg6_dl_06009E50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, seaweed_seg6_texture_06007E10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&seaweed_seg6_lights_06007DF8.l, 1),
    gsSPLight(&seaweed_seg6_lights_06007DF8.a, 2),
    gsSPVertex(seaweed_seg6_vertex_06009E10, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009E98 - 0x06009F08
const Gfx seaweed_seg6_dl_06009E98[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(seaweed_seg6_dl_06009E50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x06009F08
static const Vtx seaweed_seg6_vertex_06009F08[] = {
    {{{   311,      2,    -76}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -1,      2,    -76}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -1,      2,     77}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   311,      2,     77}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x06009F48 - 0x06009F90
const Gfx seaweed_seg6_dl_06009F48[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, seaweed_seg6_texture_06008610),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&seaweed_seg6_lights_06007DF8.l, 1),
    gsSPLight(&seaweed_seg6_lights_06007DF8.a, 2),
    gsSPVertex(seaweed_seg6_vertex_06009F08, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x06009F90 - 0x0600A000
const Gfx seaweed_seg6_dl_06009F90[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(seaweed_seg6_dl_06009F48),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0600A000
static const Vtx seaweed_seg6_vertex_0600A000[] = {
    {{{   314,      2,    -76}, 0, {     0,    -20}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -1,      2,     77}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   314,      2,     77}, 0, {   990,    -20}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -1,      2,    -76}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0600A040 - 0x0600A088
const Gfx seaweed_seg6_dl_0600A040[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, seaweed_seg6_texture_06008E10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&seaweed_seg6_lights_06007DF8.l, 1),
    gsSPLight(&seaweed_seg6_lights_06007DF8.a, 2),
    gsSPVertex(seaweed_seg6_vertex_0600A000, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600A088 - 0x0600A0F8
const Gfx seaweed_seg6_dl_0600A088[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(seaweed_seg6_dl_0600A040),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0600A0F8
static const Vtx seaweed_seg6_vertex_0600A0F8[] = {
    {{{   236,      2,    -76}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      2,     77}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   236,      2,     77}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      2,    -76}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0600A138 - 0x0600A180
const Gfx seaweed_seg6_dl_0600A138[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, seaweed_seg6_texture_06009610),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&seaweed_seg6_lights_06007DF8.l, 1),
    gsSPLight(&seaweed_seg6_lights_06007DF8.a, 2),
    gsSPVertex(seaweed_seg6_vertex_0600A0F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0600A180 - 0x0600A1F0
const Gfx seaweed_seg6_dl_0600A180[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(seaweed_seg6_dl_0600A138),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
