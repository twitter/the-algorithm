// Amp

// 0x08000F18
ALIGNED8 static const u8 amp_seg8_texture_08000F18[] = {
#include "actors/amp/amp_electricity.rgba16.inc.c"
};

// 0x08001318
ALIGNED8 static const u8 amp_seg8_texture_08001318[] = {
#include "actors/amp/amp_eyes.rgba16.inc.c"
};

// 0x08001B18
ALIGNED8 static const u8 amp_seg8_texture_08001B18[] = {
#include "actors/amp/amp_body.rgba16.inc.c"
};

// 0x08002318
ALIGNED8 static const u8 amp_seg8_texture_08002318[] = {
#include "actors/amp/amp_mouth.rgba16.inc.c"
};

// 0x08002B18
static const Vtx amp_seg8_vertex_08002B18[] = {
    {{{   224,      0,    -89}, 0, {     0,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   187,    149,      0}, 0, {   223,   1078}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,      0,     90}, 0, {   479,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   187,   -148,      0}, 0, {   224,   -122}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,      0,    -89}, 0, {     0,    478}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x08002B68 - 0x08002BA0
const Gfx amp_seg8_dl_08002B68[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, amp_seg8_texture_08000F18),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(amp_seg8_vertex_08002B18, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x08002BA0 - 0x08002C10
const Gfx amp_seg8_dl_08002BA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(amp_seg8_dl_08002B68),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x08002C10
static const Vtx amp_seg8_vertex_08002C10[] = {
    {{{    68,     72,    158}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -27,    -71,    164}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    68,    -71,    158}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -27,     72,    164}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x08002C50 - 0x08002C88
const Gfx amp_seg8_dl_08002C50[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, amp_seg8_texture_08001318),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(amp_seg8_vertex_08002C10, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x08002C88 - 0x08002CF8
const Gfx amp_seg8_dl_08002C88[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(amp_seg8_dl_08002C50),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x08002CF8
static const Vtx amp_seg8_vertex_08002CF8[] = {
    {{{   -29,     72,    164}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -124,    -71,    121}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -29,    -71,    164}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -124,     72,    121}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x08002D38 - 0x08002D70
const Gfx amp_seg8_dl_08002D38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, amp_seg8_texture_08002318),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(amp_seg8_vertex_08002CF8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x08002D70 - 0x08002DE0
const Gfx amp_seg8_dl_08002D70[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(amp_seg8_dl_08002D38),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x08002DE0
static const Vtx amp_seg8_vertex_08002DE0[] = {
    {{{   -39,    -39,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,     40,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -39,     40,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,    -39,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x08002E20 - 0x08002E58
const Gfx amp_seg8_dl_08002E20[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, amp_seg8_texture_08001B18),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(amp_seg8_vertex_08002DE0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x08002E58 - 0x08002EC8
const Gfx amp_seg8_dl_08002E58[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(amp_seg8_dl_08002E20),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x08002EC8
static const Lights1 amp_seg8_lights_08002EC8 = gdSPDefLights1(
    0x33, 0x3f, 0x00,
    0xcf, 0xff, 0x00, 0x28, 0x28, 0x28
);

// //! Another malformed entry: Vertex interpreted as light
// 0x08002EE0
static const Vtx amp_seg8_vertex_08002EE0[] = {
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
};

// 0x08002F40
static const Vtx amp_seg8_vertex_08002F40[] = {
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
};

// 0x08002FA0
static const Vtx amp_seg8_vertex_08002FA0[] = {
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
};

// 0x08003000
static const Vtx amp_seg8_vertex_08003000[] = {
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0x00}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0x00}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0x00}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
};

// 0x08003060
static const Vtx amp_seg8_vertex_08003060[] = {
    {{{  -184,    -54,    -54}, 0, {     0,      0}, {0x8b, 0xde, 0xde, 0x00}}},
    {{{  -184,    -76,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,   -100,   -100}, 0, {     0,      0}, {0xa6, 0xc1, 0xc1, 0xff}}},
    {{{  -141,   -141,      0}, 0, {     0,      0}, {0xa6, 0xa7, 0x00, 0xff}}},
    {{{   -76,   -130,   -130}, 0, {     0,      0}, {0xd0, 0xae, 0xae, 0xff}}},
    {{{   -76,   -184,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{     0,   -141,   -141}, 0, {     0,      0}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{     0,   -200,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    76,   -130,   -130}, 0, {     0,      0}, {0x30, 0xae, 0xae, 0xff}}},
    {{{    76,   -184,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{   141,   -100,   -100}, 0, {     0,      0}, {0x5a, 0xc1, 0xc1, 0xff}}},
    {{{   141,   -141,      0}, 0, {     0,      0}, {0x5a, 0xa7, 0x00, 0xff}}},
    {{{   184,    -54,    -54}, 0, {     0,      0}, {0x75, 0xde, 0xde, 0xff}}},
    {{{   184,    -76,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003160
static const Vtx amp_seg8_vertex_08003160[] = {
    {{{  -184,      0,    -76}, 0, {     0,      0}, {0x8b, 0x00, 0xd0, 0xff}}},
    {{{  -184,    -54,    -54}, 0, {     0,      0}, {0x8b, 0xde, 0xde, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,      0,   -141}, 0, {     0,      0}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{  -141,   -100,   -100}, 0, {     0,      0}, {0xa6, 0xc1, 0xc1, 0xff}}},
    {{{   -76,      0,   -184}, 0, {     0,      0}, {0xd0, 0x00, 0x8b, 0xff}}},
    {{{   -76,   -130,   -130}, 0, {     0,      0}, {0xd0, 0xae, 0xae, 0xff}}},
    {{{     0,      0,   -200}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,   -141,   -141}, 0, {     0,      0}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{    76,      0,   -184}, 0, {     0,      0}, {0x30, 0x00, 0x8b, 0xff}}},
    {{{    76,   -130,   -130}, 0, {     0,      0}, {0x30, 0xae, 0xae, 0xff}}},
    {{{   141,      0,   -141}, 0, {     0,      0}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   141,   -100,   -100}, 0, {     0,      0}, {0x5a, 0xc1, 0xc1, 0xff}}},
    {{{   184,      0,    -76}, 0, {     0,      0}, {0x75, 0x00, 0xd0, 0xff}}},
    {{{   184,    -54,    -54}, 0, {     0,      0}, {0x75, 0xde, 0xde, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003260
static const Vtx amp_seg8_vertex_08003260[] = {
    {{{  -184,     54,    -54}, 0, {     0,      0}, {0x8b, 0x22, 0xde, 0xff}}},
    {{{  -184,      0,    -76}, 0, {     0,      0}, {0x8b, 0x00, 0xd0, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,    100,   -100}, 0, {     0,      0}, {0xa6, 0x3f, 0xc1, 0xff}}},
    {{{  -141,      0,   -141}, 0, {     0,      0}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{   -76,    130,   -130}, 0, {     0,      0}, {0xd0, 0x52, 0xae, 0xff}}},
    {{{   -76,      0,   -184}, 0, {     0,      0}, {0xd0, 0x00, 0x8b, 0xff}}},
    {{{     0,    141,   -141}, 0, {     0,      0}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{     0,      0,   -200}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    76,    130,   -130}, 0, {     0,      0}, {0x30, 0x52, 0xae, 0xff}}},
    {{{    76,      0,   -184}, 0, {     0,      0}, {0x30, 0x00, 0x8b, 0xff}}},
    {{{   141,    100,   -100}, 0, {     0,      0}, {0x5a, 0x3f, 0xc1, 0xff}}},
    {{{   141,      0,   -141}, 0, {     0,      0}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   184,     54,    -54}, 0, {     0,      0}, {0x75, 0x22, 0xde, 0xff}}},
    {{{   184,      0,    -76}, 0, {     0,      0}, {0x75, 0x00, 0xd0, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003360
static const Vtx amp_seg8_vertex_08003360[] = {
    {{{  -184,     76,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{  -184,     54,    -54}, 0, {     0,      0}, {0x8b, 0x22, 0xde, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,    141,      0}, 0, {     0,      0}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{  -141,    100,   -100}, 0, {     0,      0}, {0xa6, 0x3f, 0xc1, 0xff}}},
    {{{   -76,    184,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{   -76,    130,   -130}, 0, {     0,      0}, {0xd0, 0x52, 0xae, 0xff}}},
    {{{     0,    200,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    141,   -141}, 0, {     0,      0}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{    76,    184,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{    76,    130,   -130}, 0, {     0,      0}, {0x30, 0x52, 0xae, 0xff}}},
    {{{   141,    141,      0}, 0, {     0,      0}, {0x5a, 0x59, 0x00, 0xff}}},
    {{{   141,    100,   -100}, 0, {     0,      0}, {0x5a, 0x3f, 0xc1, 0xff}}},
    {{{   184,     76,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{   184,     54,    -54}, 0, {     0,      0}, {0x75, 0x22, 0xde, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003460
static const Vtx amp_seg8_vertex_08003460[] = {
    {{{  -184,     54,     54}, 0, {     0,      0}, {0x8b, 0x22, 0x22, 0xff}}},
    {{{  -184,     76,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,    100,    100}, 0, {     0,      0}, {0xa6, 0x3f, 0x3f, 0xff}}},
    {{{  -141,    141,      0}, 0, {     0,      0}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{   -76,    130,    130}, 0, {     0,      0}, {0xd0, 0x52, 0x52, 0xff}}},
    {{{   -76,    184,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{     0,    141,    141}, 0, {     0,      0}, {0x00, 0x59, 0x59, 0xff}}},
    {{{     0,    200,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    76,    130,    130}, 0, {     0,      0}, {0x30, 0x52, 0x52, 0xff}}},
    {{{    76,    184,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{   141,    100,    100}, 0, {     0,      0}, {0x5a, 0x3f, 0x3f, 0xff}}},
    {{{   141,    141,      0}, 0, {     0,      0}, {0x5a, 0x59, 0x00, 0xff}}},
    {{{   184,     54,     54}, 0, {     0,      0}, {0x75, 0x22, 0x22, 0xff}}},
    {{{   184,     76,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003560
static const Vtx amp_seg8_vertex_08003560[] = {
    {{{  -184,      0,     76}, 0, {     0,      0}, {0x8b, 0x00, 0x30, 0xff}}},
    {{{  -184,     54,     54}, 0, {     0,      0}, {0x8b, 0x22, 0x22, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,      0,    141}, 0, {     0,      0}, {0xa6, 0x00, 0x59, 0xff}}},
    {{{  -141,    100,    100}, 0, {     0,      0}, {0xa6, 0x3f, 0x3f, 0xff}}},
    {{{   -76,      0,    184}, 0, {     0,      0}, {0xd0, 0x00, 0x75, 0xff}}},
    {{{   -76,    130,    130}, 0, {     0,      0}, {0xd0, 0x52, 0x52, 0xff}}},
    {{{     0,      0,    200}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    141,    141}, 0, {     0,      0}, {0x00, 0x59, 0x59, 0xff}}},
    {{{    76,      0,    184}, 0, {     0,      0}, {0x30, 0x00, 0x75, 0xff}}},
    {{{    76,    130,    130}, 0, {     0,      0}, {0x30, 0x52, 0x52, 0xff}}},
    {{{   141,      0,    141}, 0, {     0,      0}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   141,    100,    100}, 0, {     0,      0}, {0x5a, 0x3f, 0x3f, 0xff}}},
    {{{   184,      0,     76}, 0, {     0,      0}, {0x75, 0x00, 0x30, 0xff}}},
    {{{   184,     54,     54}, 0, {     0,      0}, {0x75, 0x22, 0x22, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003660
static const Vtx amp_seg8_vertex_08003660[] = {
    {{{  -184,    -54,     54}, 0, {     0,      0}, {0x8b, 0xde, 0x22, 0xff}}},
    {{{  -184,      0,     76}, 0, {     0,      0}, {0x8b, 0x00, 0x30, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,   -100,    100}, 0, {     0,      0}, {0xa6, 0xc1, 0x3f, 0xff}}},
    {{{  -141,      0,    141}, 0, {     0,      0}, {0xa6, 0x00, 0x59, 0xff}}},
    {{{   -76,   -130,    130}, 0, {     0,      0}, {0xd0, 0xae, 0x52, 0xff}}},
    {{{   -76,      0,    184}, 0, {     0,      0}, {0xd0, 0x00, 0x75, 0xff}}},
    {{{     0,   -141,    141}, 0, {     0,      0}, {0x00, 0xa7, 0x59, 0xff}}},
    {{{     0,      0,    200}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    76,   -130,    130}, 0, {     0,      0}, {0x30, 0xae, 0x52, 0xff}}},
    {{{    76,      0,    184}, 0, {     0,      0}, {0x30, 0x00, 0x75, 0xff}}},
    {{{   141,   -100,    100}, 0, {     0,      0}, {0x5a, 0xc1, 0x3f, 0xff}}},
    {{{   141,      0,    141}, 0, {     0,      0}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   184,    -54,     54}, 0, {     0,      0}, {0x75, 0xde, 0x22, 0xff}}},
    {{{   184,      0,     76}, 0, {     0,      0}, {0x75, 0x00, 0x30, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003760
static const Vtx amp_seg8_vertex_08003760[] = {
    {{{  -184,    -76,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{  -184,    -54,     54}, 0, {     0,      0}, {0x8b, 0xde, 0x22, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,   -141,      0}, 0, {     0,      0}, {0xa6, 0xa7, 0x00, 0xff}}},
    {{{  -141,   -100,    100}, 0, {     0,      0}, {0xa6, 0xc1, 0x3f, 0xff}}},
    {{{   -76,   -184,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{   -76,   -130,    130}, 0, {     0,      0}, {0xd0, 0xae, 0x52, 0xff}}},
    {{{     0,   -200,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,   -141,    141}, 0, {     0,      0}, {0x00, 0xa7, 0x59, 0xff}}},
    {{{    76,   -184,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    76,   -130,    130}, 0, {     0,      0}, {0x30, 0xae, 0x52, 0xff}}},
    {{{   141,   -141,      0}, 0, {     0,      0}, {0x5a, 0xa7, 0x00, 0xff}}},
    {{{   141,   -100,    100}, 0, {     0,      0}, {0x5a, 0xc1, 0x3f, 0xff}}},
    {{{   184,    -76,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{   184,    -54,     54}, 0, {     0,      0}, {0x75, 0xde, 0x22, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x08003860
static const Vtx amp_seg8_vertex_08003860[] = {
    {{{   -37,     90,    205}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0x00}}},
    {{{  -129,     90,    163}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0x00}}},
    {{{  -129,    -90,    163}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0x00}}},
    {{{   -37,    -90,    205}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0xff}}},
};

// 0x080038A0
static const Vtx amp_seg8_vertex_080038A0[] = {
    {{{   112,     -7,    182}, 0, {     0,      0}, {0x4c, 0xd8, 0x5c, 0x00}}},
    {{{    66,   -139,    162}, 0, {     0,      0}, {0x4c, 0xd8, 0x5c, 0x00}}},
    {{{   175,    -77,     98}, 0, {     0,      0}, {0x4c, 0xd8, 0x5c, 0x00}}},
};

// 0x080038D0
static const Vtx amp_seg8_vertex_080038D0[] = {
    {{{    63,     90,    198}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0x00}}},
    {{{   -35,     90,    205}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0x00}}},
    {{{   -35,    -90,    205}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0x00}}},
    {{{    63,    -90,    198}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0xff}}},
};

// 0x08003910 - 0x08003940
const Gfx amp_seg8_dl_08003910[] = {
    gsSPLight(&amp_seg8_lights_08002EC8.l, 1),
    gsSPLight(&amp_seg8_lights_08002EC8.a, 2),
    gsSPVertex(amp_seg8_vertex_08002EE0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x08003940 - 0x08003970
const Gfx amp_seg8_dl_08003940[] = {
    gsSPLight(&amp_seg8_lights_08002EC8.l, 1),
    gsSPLight(&amp_seg8_lights_08002EC8.a, 2),
    gsSPVertex(amp_seg8_vertex_08002F40, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x08003970 - 0x080039A0
const Gfx amp_seg8_dl_08003970[] = {
    gsSPLight(&amp_seg8_lights_08002EC8.l, 1),
    gsSPLight(&amp_seg8_lights_08002EC8.a, 2),
    gsSPVertex(amp_seg8_vertex_08002FA0, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x080039A0 - 0x080039D0
const Gfx amp_seg8_dl_080039A0[] = {
    gsSPLight(&amp_seg8_lights_08002EC8.l, 1),
    gsSPLight(&amp_seg8_lights_08002EC8.a, 2),
    gsSPVertex(amp_seg8_vertex_08003000, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x080039D0 - 0x08003DA8
const Gfx amp_seg8_dl_080039D0[] = {
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0 + 0x8, 1),
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0, 2),
    gsSPVertex(amp_seg8_vertex_08003060, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003160, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003260, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003360, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003460, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003560, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003660, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(amp_seg8_vertex_08003760, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x08003DA8 - 0x08003DD8
const Gfx amp_seg8_dl_08003DA8[] = {
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0 + 0x8, 1),
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0, 2),
    gsSPVertex(amp_seg8_vertex_08003860, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x08003DD8 - 0x08003E00
const Gfx amp_seg8_dl_08003DD8[] = {
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0 + 0x8, 1),
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0, 2),
    gsSPVertex(amp_seg8_vertex_080038A0, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x08003E00 - 0x08003E30
const Gfx amp_seg8_dl_08003E00[] = {
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0 + 0x8, 1),
    gsSPLight((const u8*)amp_seg8_vertex_08002EE0, 2),
    gsSPVertex(amp_seg8_vertex_080038D0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};
