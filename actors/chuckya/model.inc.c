// Chuckya

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused1 = gdSPDefLights1(
    0x26, 0x00, 0x27,
    0x9a, 0x00, 0x9c, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused2 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused3 = gdSPDefLights1(
    0x3f, 0x00, 0x00,
    0xff, 0x00, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused4 = gdSPDefLights1(
    0x0e, 0x0e, 0x0e,
    0x39, 0x39, 0x39, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused5 = gdSPDefLights1(
    0x39, 0x20, 0x00,
    0xe4, 0x83, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused6 = gdSPDefLights1(
    0x3f, 0x27, 0x00,
    0xff, 0x9e, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
UNUSED static const Lights1 chuckya_lights_unused7 = gdSPDefLights1(
    0x15, 0x14, 0x16,
    0x57, 0x53, 0x58, 0x28, 0x28, 0x28
);

// 0x08006778
ALIGNED8 static const Texture chuckya_seg8_texture_08006778[] = {
#include "actors/chuckya/chuckya_eyes.rgba16.inc.c"
};

// 0x08007778
ALIGNED8 static const Texture chuckya_seg8_texture_08007778[] = {
#include "actors/chuckya/chuckya_hand_antenna.rgba16.inc.c"
};

// 0x08007F78
ALIGNED8 static const Texture chuckya_seg8_texture_08007F78[] = {
#include "actors/chuckya/chuckya_body_arm_left_side.rgba16.inc.c"
};

// 0x08008F78
ALIGNED8 static const Texture chuckya_seg8_texture_08008F78[] = {
#include "actors/chuckya/chuckya_body_arm_right_side.rgba16.inc.c"
};

// 0x08009F78
static const Vtx chuckya_seg8_vertex_08009F78[] = {
    {{{     0,     56,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -56,    -56,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -56,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -56,     56,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x08009FB8
static const Vtx chuckya_seg8_vertex_08009FB8[] = {
    {{{    56,     56,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -56,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    56,    -56,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     56,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x08009FF8 - 0x0800A030
const Gfx chuckya_seg8_dl_08009FF8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08007F78),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_08009F78, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A030 - 0x0800A068
const Gfx chuckya_seg8_dl_0800A030[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08008F78),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_08009FB8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A068 - 0x0800A0E0
const Gfx chuckya_seg8_dl_0800A068[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_08009FF8),
    gsSPDisplayList(chuckya_seg8_dl_0800A030),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0800A0E0
static const Vtx chuckya_seg8_vertex_0800A0E0[] = {
    {{{     0,     13,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -13,    -13,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -13,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -13,     13,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A120
static const Vtx chuckya_seg8_vertex_0800A120[] = {
    {{{    13,     13,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -13,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    13,    -13,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     13,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A160 - 0x0800A198
const Gfx chuckya_seg8_dl_0800A160[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08007F78),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A0E0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A198 - 0x0800A1D0
const Gfx chuckya_seg8_dl_0800A198[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08008F78),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A120, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A1D0 - 0x0800A248
const Gfx chuckya_seg8_dl_0800A1D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_0800A160),
    gsSPDisplayList(chuckya_seg8_dl_0800A198),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0800A248
static const Vtx chuckya_seg8_vertex_0800A248[] = {
    {{{     0,     13,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -13,    -13,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -13,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -13,     13,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A288
static const Vtx chuckya_seg8_vertex_0800A288[] = {
    {{{    13,     13,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    -13,      0}, 0, {     0,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    13,    -13,      0}, 0, {   990,   2012}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     13,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A2C8 - 0x0800A300
const Gfx chuckya_seg8_dl_0800A2C8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08007F78),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A248, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A300 - 0x0800A338
const Gfx chuckya_seg8_dl_0800A300[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08008F78),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A288, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A338 - 0x0800A3B0
const Gfx chuckya_seg8_dl_0800A338[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_0800A2C8),
    gsSPDisplayList(chuckya_seg8_dl_0800A300),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0800A3B0
static const Vtx chuckya_seg8_vertex_0800A3B0[] = {
    {{{    23,     23,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -22,     23,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -22,    -22,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    23,    -22,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A3F0 - 0x0800A428
const Gfx chuckya_seg8_dl_0800A3F0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08007778),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A3B0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A428 - 0x0800A498
const Gfx chuckya_seg8_dl_0800A428[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_0800A3F0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0800A498
static const Vtx chuckya_seg8_vertex_0800A498[] = {
    {{{    23,     23,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -22,     23,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -22,    -22,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    23,    -22,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A4D8 - 0x0800A510
const Gfx chuckya_seg8_dl_0800A4D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08007778),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A498, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A510 - 0x0800A580
const Gfx chuckya_seg8_dl_0800A510[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_0800A4D8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0800A580
static const Vtx chuckya_seg8_vertex_0800A580[] = {
    {{{    25,     25,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -25,     25,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -25,    -25,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    25,    -25,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0800A5C0 - 0x0800A5F8
const Gfx chuckya_seg8_dl_0800A5C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08007778),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(chuckya_seg8_vertex_0800A580, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A5F8 - 0x0800A668
const Gfx chuckya_seg8_dl_0800A5F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_0800A5C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x0800A668
static const Lights1 chuckya_seg8_lights_0800A668 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0800A680
static const Vtx chuckya_seg8_vertex_0800A680[] = {
    {{{    33,    237,      0}, 0, {   480,   1016}, {0x40, 0x65, 0x28, 0xff}}},
    {{{    27,    198,    107}, 0, {  1012,    932}, {0x40, 0x65, 0x28, 0xff}}},
    {{{   196,    134,      0}, 0, {   480,    -66}, {0x40, 0x65, 0x28, 0xff}}},
    {{{   163,    112,    107}, 0, {  1012,     22}, {0x40, 0x65, 0x28, 0xff}}},
    {{{   196,    134,      0}, 0, {   480,    -66}, {0x40, 0x65, 0xd7, 0xff}}},
    {{{    27,    198,   -106}, 0, {   -52,    928}, {0x40, 0x65, 0xd7, 0xff}}},
    {{{    33,    237,      0}, 0, {   480,   1016}, {0x40, 0x65, 0xd7, 0xff}}},
    {{{   163,    112,   -106}, 0, {   -52,     20}, {0x40, 0x65, 0xd7, 0xff}}},
};

// 0x0800A700 - 0x0800A758
const Gfx chuckya_seg8_dl_0800A700[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, chuckya_seg8_texture_08006778),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&chuckya_seg8_lights_0800A668.l, 1),
    gsSPLight(&chuckya_seg8_lights_0800A668.a, 2),
    gsSPVertex(chuckya_seg8_vertex_0800A680, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A758 - 0x0800A7C8
const Gfx chuckya_seg8_dl_0800A758[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(chuckya_seg8_dl_0800A700),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0800A7C8
static const Lights1 chuckya_seg8_lights_0800A7C8 = gdSPDefLights1(
    0x29, 0x29, 0x29,
    0x89, 0x89, 0x8a, 0x28, 0x28, 0x28
);

// 0x0800A7E0
static const Vtx chuckya_seg8_vertex_0800A7E0[] = {
    {{{    87,     41,    -41}, 0, {     0,      0}, {0xb8, 0x49, 0xb7, 0xff}}},
    {{{    87,      0,    -58}, 0, {     0,      0}, {0xb8, 0x00, 0x98, 0xff}}},
    {{{     3,     -1,      0}, 0, {     0,      0}, {0x82, 0x00, 0x00, 0xff}}},
    {{{    87,     58,      0}, 0, {     0,      0}, {0xb8, 0x67, 0x00, 0xff}}},
    {{{    87,    -42,    -41}, 0, {     0,      0}, {0xb8, 0xb6, 0xb7, 0xff}}},
    {{{    87,    -42,     42}, 0, {     0,      0}, {0xb8, 0xb6, 0x49, 0xff}}},
    {{{    87,      0,     59}, 0, {     0,      0}, {0xb8, 0x00, 0x67, 0xff}}},
    {{{    87,     41,     42}, 0, {     0,      0}, {0xb7, 0x49, 0x48, 0xff}}},
    {{{    88,    -59,      0}, 0, {     0,      0}, {0xb9, 0x98, 0x00, 0xff}}},
};

// 0x0800A870 - 0x0800A8D0
const Gfx chuckya_seg8_dl_0800A870[] = {
    gsSPLight(&chuckya_seg8_lights_0800A7C8.l, 1),
    gsSPLight(&chuckya_seg8_lights_0800A7C8.a, 2),
    gsSPVertex(chuckya_seg8_vertex_0800A7E0, 9, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  0,  2, 0x0),
    gsSP2Triangles( 1,  4,  2, 0x0,  5,  6,  2, 0x0),
    gsSP2Triangles( 6,  7,  2, 0x0,  8,  5,  2, 0x0),
    gsSP2Triangles( 7,  3,  2, 0x0,  4,  8,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A8D0 - 0x0800A8F0
const Gfx chuckya_seg8_dl_0800A8D0[] = {
    gsDPPipeSync(),
    gsSPDisplayList(chuckya_seg8_dl_0800A870),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x0800A8F0
static const Lights1 chuckya_seg8_lights_0800A8F0 = gdSPDefLights1(
    0x4c, 0x4c, 0x00,
    0xff, 0xff, 0x00, 0x28, 0x28, 0x28
);

// 0x0800A908
static const Vtx chuckya_seg8_vertex_0800A908[] = {
    {{{    -9,      2,      0}, 0, {     0,      0}, {0x82, 0xfd, 0x00, 0xff}}},
    {{{    79,     26,      0}, 0, {     0,      0}, {0xdf, 0x7a, 0x00, 0xff}}},
    {{{    79,      1,    -33}, 0, {     0,      0}, {0xd4, 0xff, 0x8a, 0xff}}},
    {{{    80,    -23,      0}, 0, {     0,      0}, {0xde, 0x86, 0x00, 0xff}}},
    {{{    79,      1,     33}, 0, {     0,      0}, {0xd4, 0xff, 0x76, 0xff}}},
};

// 0x0800A958 - 0x0800A998
const Gfx chuckya_seg8_dl_0800A958[] = {
    gsSPLight(&chuckya_seg8_lights_0800A8F0.l, 1),
    gsSPLight(&chuckya_seg8_lights_0800A8F0.a, 2),
    gsSPVertex(chuckya_seg8_vertex_0800A908, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 0,  2,  3, 0x0,  0,  4,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800A998 - 0x0800A9B8
const Gfx chuckya_seg8_dl_0800A998[] = {
    gsDPPipeSync(),
    gsSPDisplayList(chuckya_seg8_dl_0800A958),
    gsDPPipeSync(),
    gsSPEndDisplayList(),
};

// 0x0800A9B8
static const Lights1 chuckya_seg8_lights_0800A9B8 = gdSPDefLights1(
    0x0c, 0x0c, 0x0c,
    0x32, 0x32, 0x32, 0x28, 0x28, 0x28
);

// 0x0800A9D0
static const Vtx chuckya_seg8_vertex_0800A9D0[] = {
    {{{   -99,   -129,    130}, 0, {     0,      0}, {0x24, 0xa9, 0x54, 0xff}}},
    {{{  -135,   -173,    100}, 0, {     0,      0}, {0x24, 0xa9, 0x54, 0xff}}},
    {{{   -55,   -236,      0}, 0, {     0,      0}, {0x24, 0xa9, 0x54, 0xff}}},
    {{{  -135,   -173,    100}, 0, {     0,      0}, {0xa3, 0x11, 0x54, 0xff}}},
    {{{  -202,    -49,      0}, 0, {     0,      0}, {0xa3, 0x11, 0x54, 0xff}}},
    {{{  -213,   -113,      1}, 0, {     0,      0}, {0xa3, 0x11, 0x54, 0xff}}},
    {{{  -135,   -173,    100}, 0, {     0,      0}, {0xa4, 0x12, 0x54, 0xff}}},
    {{{   -99,   -129,    130}, 0, {     0,      0}, {0xa4, 0x12, 0x54, 0xff}}},
    {{{  -202,    -49,      0}, 0, {     0,      0}, {0xa4, 0x12, 0x54, 0xff}}},
    {{{  -135,   -173,    100}, 0, {     0,      0}, {0xb2, 0x9d, 0x00, 0xff}}},
    {{{  -132,   -175,   -100}, 0, {     0,      0}, {0xb2, 0x9d, 0x00, 0xff}}},
    {{{   -55,   -236,      0}, 0, {     0,      0}, {0xb2, 0x9d, 0x00, 0xff}}},
    {{{  -135,   -173,    100}, 0, {     0,      0}, {0xb3, 0x9c, 0x00, 0xff}}},
    {{{  -213,   -113,      1}, 0, {     0,      0}, {0xb3, 0x9c, 0x00, 0xff}}},
    {{{  -132,   -175,   -100}, 0, {     0,      0}, {0xb3, 0x9c, 0x00, 0xff}}},
};

// 0x0800AAC0
static const Vtx chuckya_seg8_vertex_0800AAC0[] = {
    {{{  -213,   -113,      1}, 0, {     0,      0}, {0xa3, 0x0e, 0xac, 0xff}}},
    {{{  -202,    -49,      0}, 0, {     0,      0}, {0xa3, 0x0e, 0xac, 0xff}}},
    {{{   -98,   -130,   -130}, 0, {     0,      0}, {0xa3, 0x0e, 0xac, 0xff}}},
    {{{  -132,   -175,   -100}, 0, {     0,      0}, {0xa3, 0x0e, 0xac, 0xff}}},
    {{{   -99,   -129,    130}, 0, {     0,      0}, {0x25, 0xa9, 0x54, 0xff}}},
    {{{   -55,   -236,      0}, 0, {     0,      0}, {0x25, 0xa9, 0x54, 0xff}}},
    {{{     3,   -210,      1}, 0, {     0,      0}, {0x25, 0xa9, 0x54, 0xff}}},
    {{{  -132,   -175,   -100}, 0, {     0,      0}, {0x28, 0xaa, 0xad, 0xff}}},
    {{{     3,   -210,      1}, 0, {     0,      0}, {0x28, 0xaa, 0xad, 0xff}}},
    {{{   -55,   -236,      0}, 0, {     0,      0}, {0x28, 0xaa, 0xad, 0xff}}},
    {{{   -98,   -130,   -130}, 0, {     0,      0}, {0x28, 0xaa, 0xad, 0xff}}},
};

// 0x0800AB70 - 0x0800ABE8
const Gfx chuckya_seg8_dl_0800AB70[] = {
    gsSPLight(&chuckya_seg8_lights_0800A9B8.l, 1),
    gsSPLight(&chuckya_seg8_lights_0800A9B8.a, 2),
    gsSPVertex(chuckya_seg8_vertex_0800A9D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP1Triangle(12, 13, 14, 0x0),
    gsSPVertex(chuckya_seg8_vertex_0800AAC0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 7, 10,  8, 0x0),
    gsSPEndDisplayList(),
};

// 0x0800ABE8 - 0x0800AC18
const Gfx chuckya_seg8_dl_0800ABE8[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsSPDisplayList(chuckya_seg8_dl_0800AB70),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
