// Dirt

// 0x0302BD68
static const Lights1 dirt_seg3_lights_0302BD68 = gdSPDefLights1(
    0x3f, 0x19, 0x19,
    0xff, 0x64, 0x64, 0x28, 0x28, 0x28
);

// 0x0302BD80
static const Lights1 dirt_seg3_lights_0302BD80 = gdSPDefLights1(
    0x19, 0x3f, 0x19,
    0x64, 0xff, 0x64, 0x28, 0x28, 0x28
);

// 0x0302BD98
static const Lights1 dirt_seg3_lights_0302BD98 = gdSPDefLights1(
    0x19, 0x19, 0x3f,
    0x64, 0x64, 0xff, 0x28, 0x28, 0x28
);

// 0x0302BDB0
static const Lights1 dirt_seg3_lights_0302BDB0 = gdSPDefLights1(
    0x3f, 0x3f, 0x19,
    0xff, 0xff, 0x64, 0x28, 0x28, 0x28
);

// 0x0302BDC8
static const Vtx dirt_seg3_vertex_0302BDC8[] = {
    {{{  -101,    -60,      0}, 0, {  -130,    467}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   102,    -60,      0}, 0, {   599,    467}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,     92,      0}, 0, {   234,    -81}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0302BDF8
ALIGNED8 static const Texture dirt_seg3_texture_0302BDF8[] = {
#include "actors/dirt/dirt_particle.rgba16.inc.c"
};

// 0x0302BFF8 - 0x0302C028
const Gfx dirt_seg3_dl_0302BFF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dirt_seg3_texture_0302BDF8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dirt_seg3_vertex_0302BDC8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0302C028 - 0x0302C098
const Gfx dirt_seg3_dl_0302C028[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dirt_seg3_dl_0302BFF8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C098
static const Vtx dirt_seg3_vertex_0302C098[] = {
    {{{   -10,      0,     10}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    10,      0,     10}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,      0,    -10}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
};

// 0x0302C0C8
static const Vtx dirt_seg3_vertex_0302C0C8[] = {
    {{{   -10,     10,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{    10,     10,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{     0,    -10,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
};

// 0x0302C0F8
static const Vtx dirt_seg3_vertex_0302C0F8[] = {
    {{{     0,     -8,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x00}}},
    {{{   -32,     80,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x00}}},
    {{{    32,     80,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0x00}}},
    {{{   -52,     28,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -116,     80,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -84,    -52,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    52,     28,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    84,    -52,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   116,     80,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,    160,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0302C198
static const Vtx dirt_seg3_vertex_0302C198[] = {
    {{{     0,     -8,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{   -32,     80,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{    32,     80,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{   -52,     28,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{  -116,     80,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{   -84,    -52,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{    52,     28,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{    84,    -52,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{   116,     80,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
    {{{     0,    160,      0}, 0, {     0,      0}, {0xff, 0xff, 0x00, 0xff}}},
};

// 0x0302C238 - 0x0302C298
const Gfx dirt_seg3_dl_0302C238[] = {
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C0F8, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  6,  7,  0, 0x0),
    gsSP2Triangles( 2,  8,  6, 0x0,  1,  9,  2, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  2,  6,  0, 0x0),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C298 - 0x0302C2B8
const Gfx dirt_seg3_dl_0302C298[] = {
    gsSPLight(&dirt_seg3_lights_0302BD68.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BD68.a, 2),
    gsSPDisplayList(dirt_seg3_dl_0302C238),
    gsSPEndDisplayList(),
};

// 0x0302C2B8 - 0x0302C2D8
const Gfx dirt_seg3_dl_0302C2B8[] = {
    gsSPLight(&dirt_seg3_lights_0302BD80.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BD80.a, 2),
    gsSPDisplayList(dirt_seg3_dl_0302C238),
    gsSPEndDisplayList(),
};

// 0x0302C2D8 - 0x0302C2F8
const Gfx dirt_seg3_dl_0302C2D8[] = {
    gsSPLight(&dirt_seg3_lights_0302BD98.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BD98.a, 2),
    gsSPDisplayList(dirt_seg3_dl_0302C238),
    gsSPEndDisplayList(),
};

// 0x0302C2F8 - 0x0302C318
const Gfx dirt_seg3_dl_0302C2F8[] = {
    gsSPLight(&dirt_seg3_lights_0302BDB0.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BDB0.a, 2),
    gsSPDisplayList(dirt_seg3_dl_0302C238),
    gsSPEndDisplayList(),
};

// 0x0302C318 - 0x0302C378
const Gfx dirt_seg3_dl_0302C318[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C198, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  1, 0x0),
    gsSP2Triangles( 0,  5,  3, 0x0,  6,  7,  0, 0x0),
    gsSP2Triangles( 2,  8,  6, 0x0,  1,  9,  2, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  2,  6,  0, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C378 - 0x0302C3B0
const Gfx dirt_seg3_dl_0302C378[] = {
    gsSPLight(&dirt_seg3_lights_0302BD68.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BD68.a, 2),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C098, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C3B0 - 0x0302C3E8
const Gfx dirt_seg3_dl_0302C3B0[] = {
    gsSPLight(&dirt_seg3_lights_0302BD80.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BD80.a, 2),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C098, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C3E8 - 0x0302C420
const Gfx dirt_seg3_dl_0302C3E8[] = {
    gsSPLight(&dirt_seg3_lights_0302BD98.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BD98.a, 2),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C098, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C420 - 0x0302C458
const Gfx dirt_seg3_dl_0302C420[] = {
    gsSPLight(&dirt_seg3_lights_0302BDB0.l, 1),
    gsSPLight(&dirt_seg3_lights_0302BDB0.a, 2),
    gsSPClearGeometryMode(G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C098, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPSetGeometryMode(G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0302C458 - 0x0302C480
const Gfx dirt_seg3_dl_0302C458[] = {
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPVertex(dirt_seg3_vertex_0302C0C8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
