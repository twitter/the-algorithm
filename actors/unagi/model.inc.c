// Unagi (Eel)

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused1 = gdSPDefLights1(
    0x2c, 0x25, 0x00,
    0xb2, 0x94, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused2 = gdSPDefLights1(
    0x35, 0x00, 0x00,
    0xd5, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused3 = gdSPDefLights1(
    0x37, 0x00, 0x00,
    0xdd, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused4 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused5 = gdSPDefLights1(
    0x00, 0x00, 0x00,
    0x00, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused6 = gdSPDefLights1(
    0x34, 0x00, 0x00,
    0xd2, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused7 = gdSPDefLights1(
    0x34, 0x00, 0x00,
    0xd3, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 unagi_lights_unused8 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500AF20
ALIGNED8 static const Texture unagi_seg5_texture_0500AF20[] = {
#include "actors/unagi/unagi_body.rgba16.inc.c"
};

// 0x0500B720
ALIGNED8 static const Texture unagi_seg5_texture_0500B720[] = {
#include "actors/unagi/unagi_eye.rgba16.inc.c"
};

// 0x0500B920
ALIGNED8 static const Texture unagi_seg5_texture_0500B920[] = {
#include "actors/unagi/unagi_head_base.rgba16.inc.c"
};

// 0x0500C120
ALIGNED8 static const Texture unagi_seg5_texture_0500C120[] = {
#include "actors/unagi/unagi_tooth.rgba16.inc.c"
};

// 0x0500C320
ALIGNED8 static const Texture unagi_seg5_texture_0500C320[] = {
#include "actors/unagi/unagi_mouth.rgba16.inc.c"
};

// 0x0500C3A0
ALIGNED8 static const Texture unagi_seg5_texture_0500C3A0[] = {
#include "actors/unagi/unagi_tail.rgba16.inc.c"
};

// 0x0500CBA0
static const Vtx unagi_seg5_vertex_0500CBA0[] = {
    {{{   179,     42,    160}, 0, {   -25,    134}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -164}, 0, {   224,    223}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   179,     42,   -150}, 0, {   213,    134}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   429,     24,    -89}, 0, {   166,     12}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   429,     24,     90}, 0, {    29,     12}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   521,     42,      0}, 0, {    98,    -33}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    170}, 0, {     0,    223}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500CC10
static const Vtx unagi_seg5_vertex_0500CC10[] = {
    {{{     0,      0,    170}, 0, {   670,    620}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   106,    210,     85}, 0, {   518,   1064}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -59,    157,     53}, 0, {   754,    954}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   368,     99,      0}, 0, {   148,    830}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   429,     24,    -89}, 0, {    60,    670}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   179,     42,   -150}, 0, {   416,    710}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -164}, 0, {   670,    620}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   106,    210,    -84}, 0, {   518,   1064}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   265,    166,      0}, 0, {   294,    974}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -59,    157,    -51}, 0, {   754,    954}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   521,     42,      0}, 0, {   -68,    710}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   429,     24,     90}, 0, {    60,    670}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   179,     42,    160}, 0, {   416,    710}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500CCE0 - 0x0500CD30
const Gfx unagi_seg5_dl_0500CCE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500C320),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 8 * 8 - 1, CALC_DXT(8, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500CBA0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  3,  5,  4, 0x0),
    gsSP1Triangle( 6,  1,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500CD30 - 0x0500CDD0
const Gfx unagi_seg5_dl_0500CD30[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500B920),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500CC10, 13, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  7,  8,  5, 0x0),
    gsSP2Triangles( 5,  8,  3, 0x0,  9,  7,  6, 0x0),
    gsSP2Triangles( 8,  7,  1, 0x0,  1,  7,  9, 0x0),
    gsSP2Triangles( 4,  3, 10, 0x0, 10,  3, 11, 0x0),
    gsSP2Triangles(12, 11,  3, 0x0,  8, 12,  3, 0x0),
    gsSP2Triangles( 1,  9,  2, 0x0, 12,  8,  1, 0x0),
    gsSP1Triangle( 1,  0, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500CDD0 - 0x0500CEA8
const Gfx unagi_seg5_dl_0500CDD0[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 2, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 3, G_TX_NOLOD, G_TX_CLAMP, 3, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (8 - 1) << G_TEXTURE_IMAGE_FRAC, (8 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500CCE0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500CD30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500CEA8
static const Vtx unagi_seg5_vertex_0500CEA8[] = {
    {{{   528,      0,   -117}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   531,    208,    -60}, 0, {   -36,   1008}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   646,      0,      0}, 0, {  -258,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   528,      0,    123}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   451,   -175,      0}, 0, {   116,    -22}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -107}, 0, {   990,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    118}, 0, {   990,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -159,      0}, 0, {   990,     22}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -98,      0,      0}, 0, {  1182,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    188,      0}, 0, {   990,    960}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   531,    208,     61}, 0, {   -36,   1008}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500CF58
static const Vtx unagi_seg5_vertex_0500CF58[] = {
    {{{   531,    208,     61}, 0, {     0,      0}, {0x89, 0x19, 0x2e, 0xff}}},
    {{{   531,    208,    -60}, 0, {     0,      0}, {0x89, 0x19, 0x2e, 0xff}}},
    {{{     0,    188,      0}, 0, {     0,      0}, {0x89, 0x19, 0x2e, 0xff}}},
};

// 0x0500CF88 - 0x0500D038
const Gfx unagi_seg5_dl_0500CF88[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500AF20),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500CEA8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSP2Triangles( 5,  0,  4, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 4,  0,  2, 0x0,  7,  6,  8, 0x0),
    gsSP2Triangles( 5,  7,  8, 0x0,  1,  0,  5, 0x0),
    gsSP2Triangles( 1,  5,  9, 0x0,  9,  5,  8, 0x0),
    gsSP2Triangles( 6,  9,  8, 0x0, 10,  9,  6, 0x0),
    gsSP2Triangles( 6,  3, 10, 0x0, 10,  3,  2, 0x0),
    gsSP1Triangle( 2,  1, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D038 - 0x0500D050
const Gfx unagi_seg5_dl_0500D038[] = {
    gsSPVertex(unagi_seg5_vertex_0500CF58, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D050 - 0x0500D120
const Gfx unagi_seg5_dl_0500D050[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500CF88),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_PASS2),
    gsSPDisplayList(unagi_seg5_dl_0500D038),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500D120
static const Vtx unagi_seg5_vertex_0500D120[] = {
    {{{  -357,      0,      0}, 0, {  -242,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -253,      0,    118}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -253,    160,      0}, 0, {     0,     22}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   245,      0,     99}, 0, {   994,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   245,    151,      0}, 0, {   994,     46}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   245,      0,    -89}, 0, {   994,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -253,      0,   -107}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   316,      0,      0}, 0, {  1138,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -253,   -187,      0}, 0, {     0,    960}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   245,   -173,      0}, 0, {   994,    920}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500D1C0 - 0x0500D268
const Gfx unagi_seg5_dl_0500D1C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500AF20),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500D120, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 4,  3,  7, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 8,  5,  9, 0x0,  8,  6,  5, 0x0),
    gsSP2Triangles( 9,  5,  7, 0x0,  0,  8,  1, 0x0),
    gsSP2Triangles( 1,  8,  3, 0x0,  8,  9,  3, 0x0),
    gsSP2Triangles( 6,  8,  0, 0x0,  3,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D268 - 0x0500D320
const Gfx unagi_seg5_dl_0500D268[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500D1C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500D320
static const Vtx unagi_seg5_vertex_0500D320[] = {
    {{{   -93,      0,      0}, 0, {  -224,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,     99}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    151,      0}, 0, {     0,     46}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   495,      0,     90}, 0, {   992,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   495,    146,      0}, 0, {   992,     60}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   495,      0,    -79}, 0, {   992,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    -89}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   575,      0,      0}, 0, {  1158,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -173,      0}, 0, {     0,    920}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   495,   -159,      0}, 0, {   992,    882}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500D3C0 - 0x0500D468
const Gfx unagi_seg5_dl_0500D3C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500AF20),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500D320, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 2,  1,  3, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 4,  3,  7, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 8,  5,  9, 0x0,  8,  6,  5, 0x0),
    gsSP2Triangles( 9,  5,  7, 0x0,  0,  8,  1, 0x0),
    gsSP2Triangles( 1,  8,  3, 0x0,  8,  9,  3, 0x0),
    gsSP2Triangles( 6,  8,  0, 0x0,  3,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D468 - 0x0500D520
const Gfx unagi_seg5_dl_0500D468[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500D3C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500D520
static const Vtx unagi_seg5_vertex_0500D520[] = {
    {{{   -79,      0,      0}, 0, {  -202,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,     90}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    146,      0}, 0, {     0,     60}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   476,      0,     57}, 0, {   990,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -159,      0}, 0, {     0,    882}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    -79}, 0, {     0,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   476,      0,    -56}, 0, {   990,    452}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   476,   -126,      0}, 0, {   990,    794}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   476,    132,      0}, 0, {   990,     98}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   528,      0,      0}, 0, {  1102,    452}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500D5C0 - 0x0500D668
const Gfx unagi_seg5_dl_0500D5C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500AF20),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500D520, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 1,  4,  3, 0x0,  0,  4,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  3, 0x0),
    gsSP2Triangles( 4,  6,  7, 0x0,  5,  4,  0, 0x0),
    gsSP2Triangles( 3,  8,  2, 0x0,  3,  7,  9, 0x0),
    gsSP2Triangles( 8,  3,  9, 0x0,  2,  8,  6, 0x0),
    gsSP2Triangles( 6,  8,  9, 0x0,  7,  6,  9, 0x0),
    gsSP2Triangles( 6,  5,  2, 0x0,  2,  5,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D668 - 0x0500D720
const Gfx unagi_seg5_dl_0500D668[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500D5C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500D720
static const Vtx unagi_seg5_vertex_0500D720[] = {
    {{{   327,    -97,      0}, 0, {   676,    896}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   471,      0,      0}, 0, {   988,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,     57}, 0, {     0,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   328,    102,      0}, 0, {   678,     44}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    132,      0}, 0, {     0,    -82}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -56,      0,      0}, 0, {  -152,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,   -126,      0}, 0, {     0,   1018}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    -56}, 0, {     0,    478}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500D7A0 - 0x0500D828
const Gfx unagi_seg5_dl_0500D7A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500C3A0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500D720, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  2, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 6,  2,  5, 0x0,  2,  6,  0, 0x0),
    gsSP2Triangles( 4,  7,  5, 0x0,  7,  4,  3, 0x0),
    gsSP2Triangles( 7,  6,  5, 0x0,  7,  1,  0, 0x0),
    gsSP2Triangles( 3,  1,  7, 0x0,  0,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500D828 - 0x0500D8E0
const Gfx unagi_seg5_dl_0500D828[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500D7A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500D8E0
static const Vtx unagi_seg5_vertex_0500D8E0[] = {
    {{{   226,     66,   -164}, 0, {     0,    223}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   221,    236,     61}, 0, {   138,    142}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,     66,    170}, 0, {   219,    223}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,    -95}, 0, {    21,      1}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   405,    108,   -150}, 0, {   -21,    135}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,     96}, 0, {   167,      1}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   768,     55,      0}, 0, {    94,    -42}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   405,    108,    160}, 0, {   216,    135}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,     66,    170}, 0, {   224,    223}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   221,    236,    -60}, 0, {    46,    142}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500D980
static const Vtx unagi_seg5_vertex_0500D980[] = {
    {{{   679,     53,     96}, 0, {    26,    592}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   405,    108,    160}, 0, {   414,    710}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   506,    -68,    121}, 0, {   272,    332}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   700,    -50,     39}, 0, {    -4,    370}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,   -180,     55}, 0, {   672,     92}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   470,   -137,      0}, 0, {   322,    186}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   599,    -87,      0}, 0, {   140,    292}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,     66,    170}, 0, {   668,    620}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   506,    -68,   -116}, 0, {   272,    332}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   700,    -50,    -38}, 0, {    -4,    370}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   768,     55,      0}, 0, {   -98,    596}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,    -95}, 0, {    26,    592}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,   -180,    -54}, 0, {   672,     92}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,     66,   -164}, 0, {   668,    620}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -112}, 0, {   990,    478}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500DA70
static const Vtx unagi_seg5_vertex_0500DA70[] = {
    {{{     3,    208,    -60}, 0, {   986,    922}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   221,    236,    -60}, 0, {   676,    980}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,     66,   -164}, 0, {   668,    620}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,   -112}, 0, {   990,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -112,      0,      0}, 0, {  1150,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,   -180,    -54}, 0, {   672,     92}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -68,   -121,      0}, 0, {  1088,    220}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     3,    208,     61}, 0, {   986,    922}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   506,    -68,   -116}, 0, {   272,    332}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   405,    108,   -150}, 0, {   414,    710}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,    -95}, 0, {    26,    592}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,    118}, 0, {   990,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   226,     66,    170}, 0, {   668,    620}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   221,    236,     61}, 0, {   676,    980}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,   -180,     55}, 0, {   672,     92}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500DB60 - 0x0500DBC0
const Gfx unagi_seg5_dl_0500DB60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500C320),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 8 * 8 - 1, CALC_DXT(8, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500D8E0, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  5,  4,  7, 0x0),
    gsSP2Triangles( 0,  7,  4, 0x0,  0,  8,  7, 0x0),
    gsSP1Triangle( 0,  9,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500DBC0 - 0x0500DD08
const Gfx unagi_seg5_dl_0500DBC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500B920),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500D980, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  6,  3, 0x0),
    gsSP2Triangles( 5,  6,  2, 0x0,  7,  4,  2, 0x0),
    gsSP2Triangles( 1,  7,  2, 0x0,  8,  6,  5, 0x0),
    gsSP2Triangles( 9,  6,  8, 0x0,  6,  9,  3, 0x0),
    gsSP2Triangles( 3,  9, 10, 0x0, 10,  0,  3, 0x0),
    gsSP2Triangles( 9, 11, 10, 0x0,  8, 12, 13, 0x0),
    gsSP2Triangles(14, 13, 12, 0x0,  5,  4, 12, 0x0),
    gsSP2Triangles( 5, 12,  8, 0x0,  8, 11,  9, 0x0),
    gsSPVertex(unagi_seg5_vertex_0500DA70, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  0, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  6,  4,  3, 0x0),
    gsSP2Triangles( 2,  3,  0, 0x0,  1,  0,  7, 0x0),
    gsSP2Triangles( 0,  4,  7, 0x0,  8,  9, 10, 0x0),
    gsSP2Triangles( 8,  2,  9, 0x0,  7,  4, 11, 0x0),
    gsSP2Triangles(11,  4,  6, 0x0,  7, 11, 12, 0x0),
    gsSP2Triangles( 7, 13,  1, 0x0, 12, 13,  7, 0x0),
    gsSP2Triangles(14,  6,  5, 0x0, 14, 12, 11, 0x0),
    gsSP1Triangle(11,  6, 14, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500DD08 - 0x0500DDE0
const Gfx unagi_seg5_dl_0500DD08[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 2, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 3, G_TX_NOLOD, G_TX_CLAMP, 3, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (8 - 1) << G_TEXTURE_IMAGE_FRAC, (8 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500DB60),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500DBC0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500DDE0
static const Vtx unagi_seg5_vertex_0500DDE0[] = {
    {{{   506,    -68,   -120}, 0, {  1519,    199}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,    -99}, 0, {   224,    609}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   700,    -50,    -42}, 0, {   224,   -160}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   768,     55,     -3}, 0, {  -519,    232}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   768,     55,      4}, 0, {  -520,    232}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,    100}, 0, {   223,    609}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   700,    -50,     43}, 0, {   224,   -160}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   679,     53,    100}, 0, {   224,    609}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   506,    -68,    125}, 0, {  1526,    211}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500DE70 - 0x0500DEB8
const Gfx unagi_seg5_dl_0500DE70[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500B720),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500DDE0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  7,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500DEB8 - 0x0500DF70
const Gfx unagi_seg5_dl_0500DEB8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500DE70),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0500DF70
static const Vtx unagi_seg5_vertex_0500DF70[] = {
    {{{   673,    102,    -92}, 0, {  1493,    521}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   758,    123,      0}, 0, {   -43,    646}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   760,     50,      0}, 0, {   -43,    -51}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   671,     48,    -92}, 0, {  1520,     12}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   398,    103,    157}, 0, {  5385,    384}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   671,     48,     93}, 0, {    -7,    -64}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   673,    102,     93}, 0, {  -149,    567}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   398,    103,   -147}, 0, {  3179,    441}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   673,    102,    -93}, 0, {   -51,    545}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   671,     48,    -92}, 0, {   -31,    -64}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   673,    102,     93}, 0, {  1493,    521}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   671,     48,     93}, 0, {  1520,     12}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500E030 - 0x0500E088
const Gfx unagi_seg5_dl_0500E030[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500C120),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500DF70, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11,  2, 0x0,  2,  1, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500E088 - 0x0500E140
const Gfx unagi_seg5_dl_0500E088[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500E030),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0500E140
static const Vtx unagi_seg5_vertex_0500E140[] = {
    {{{   164,     46,    153}, 0, { -4789,    510}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   424,    -17,     85}, 0, {   558,    496}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   426,     27,     84}, 0, {   558,     27}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   164,     46,   -143}, 0, { -4747,    504}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   426,     27,    -83}, 0, {   558,     27}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   424,    -17,    -84}, 0, {   558,    496}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   426,     27,     84}, 0, {  -931,    132}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   424,    -17,     85}, 0, {  -971,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   519,    -31,      0}, 0, {   491,    610}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   514,     43,      0}, 0, {   491,    -22}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   426,     27,    -83}, 0, {  -931,    132}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   424,    -17,    -84}, 0, {  -971,    479}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500E200 - 0x0500E258
const Gfx unagi_seg5_dl_0500E200[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, unagi_seg5_texture_0500C120),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(unagi_seg5_vertex_0500E140, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  9,  6, 0x0),
    gsSP2Triangles(10,  9,  8, 0x0,  8, 11, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500E258 - 0x0500E310
const Gfx unagi_seg5_dl_0500E258[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(unagi_seg5_dl_0500E200),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
