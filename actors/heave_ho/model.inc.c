// Heave Ho

// Unreferenced light group
static const Lights1 heave_ho_lights_unused1 = gdSPDefLights1(
    0x2c, 0x2c, 0x2c,
    0xb2, 0xb2, 0xb2, 0x28, 0x28, 0x28
);

// Unreferenced light group
static const Lights1 heave_ho_lights_unused2 = gdSPDefLights1(
    0x3f, 0x38, 0x00,
    0xff, 0xe3, 0x00, 0x28, 0x28, 0x28
);

// Unreferenced light group
static const Lights1 heave_ho_lights_unused3 = gdSPDefLights1(
    0x3f, 0x00, 0x00,
    0xff, 0x00, 0x00, 0x28, 0x28, 0x28
);

// 0x0500E968
static const Lights1 heave_ho_seg5_lights_0500E968 = gdSPDefLights1(
    0x14, 0x14, 0x15,
    0x50, 0x50, 0x54, 0x28, 0x28, 0x28
);

// 0x0500E980
static const Lights1 heave_ho_seg5_lights_0500E980 = gdSPDefLights1(
    0x73, 0x3f, 0x32,
    0xff, 0xe0, 0x85, 0x28, 0x28, 0x28
);

// Unreferenced light group
static const Lights1 heave_ho_lights_unused4 = gdSPDefLights1(
    0x3b, 0x38, 0x21,
    0xec, 0xe3, 0x84, 0x28, 0x28, 0x28
);

// Unreferenced light group
static const Lights1 heave_ho_lights_unused5 = gdSPDefLights1(
    0x32, 0x32, 0x32,
    0xc8, 0xc8, 0xc8, 0x28, 0x28, 0x28
);

// 0x0500E9C8
ALIGNED8 static const Texture heave_ho_seg5_texture_0500E9C8[] = {
#include "actors/heave_ho/heave-ho_face.rgba16.inc.c"
};

// 0x0500F1C8
ALIGNED8 static const Texture heave_ho_seg5_texture_0500F1C8[] = {
#include "actors/heave_ho/heave-ho_platform.rgba16.inc.c"
};

// 0x0500F9C8
ALIGNED8 static const Texture heave_ho_seg5_texture_0500F9C8[] = {
#include "actors/heave_ho/heave-ho_logo.rgba16.inc.c"
};

// 0x050109C8
ALIGNED8 static const Texture heave_ho_seg5_texture_050109C8[] = {
#include "actors/heave_ho/heave-ho_arm_ornament.rgba16.inc.c"
};

// 0x050111C8
ALIGNED8 static const Texture heave_ho_seg5_texture_050111C8[] = {
#include "actors/heave_ho/heave-ho_roller.rgba16.inc.c"
};

// 0x050113C8
ALIGNED8 static const Texture heave_ho_seg5_texture_050113C8[] = {
#include "actors/heave_ho/heave-ho_turnkey.rgba16.inc.c"
};

// 0x05011BC8
static const Lights1 heave_ho_seg5_lights_05011BC8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05011BE0
static const Lights1 heave_ho_seg5_lights_05011BE0 = gdSPDefLights1(
    0x40, 0x00, 0x0d,
    0x81, 0x01, 0x1a, 0x28, 0x28, 0x28
);

// 0x05011BF8
static const Vtx heave_ho_seg5_vertex_05011BF8[] = {
    {{{   -23,    108,    -47}, 0, {    96,   -150}, {0xd4, 0x70, 0xda, 0xff}}},
    {{{  -107,    -11,    -95}, 0, {  -286,   1018}, {0xa6, 0xbd, 0xc6, 0xff}}},
    {{{  -107,    -11,     96}, 0, {  1246,   1018}, {0x90, 0x28, 0x2b, 0xff}}},
    {{{   -23,    108,     48}, 0, {   862,   -150}, {0xd9, 0x6a, 0x39, 0xff}}},
};

// 0x05011C38
static const Vtx heave_ho_seg5_vertex_05011C38[] = {
    {{{   -23,    108,    -47}, 0, {   862,   -822}, {0xd4, 0x70, 0xda, 0xff}}},
    {{{   -23,    108,     48}, 0, {    96,   -822}, {0xd9, 0x6a, 0x39, 0xff}}},
    {{{   132,     86,     72}, 0, {   -94,    236}, {0x30, 0x6e, 0x27, 0xff}}},
    {{{   132,     86,    -71}, 0, {  1054,    236}, {0x2b, 0x67, 0xc4, 0xff}}},
    {{{   233,     -9,     72}, 0, {   -94,   1170}, {0x51, 0xba, 0x43, 0xff}}},
    {{{   233,     -9,    -71}, 0, {  1054,   1170}, {0x68, 0x31, 0xcc, 0xff}}},
};

// 0x05011C98
static const Vtx heave_ho_seg5_vertex_05011C98[] = {
    {{{   132,     86,    -71}, 0, {  1736,   -108}, {0x2b, 0x67, 0xc4, 0xff}}},
    {{{    24,    -11,   -155}, 0, {    36,   1382}, {0x00, 0xbf, 0x93, 0xff}}},
    {{{   -23,    108,    -47}, 0, {  -452,   -292}, {0xd4, 0x70, 0xda, 0xff}}},
    {{{   233,     -9,    -71}, 0, {  3024,   1182}, {0x68, 0x31, 0xcc, 0xff}}},
    {{{  -107,    -11,    -95}, 0, { -1844,   1486}, {0xa6, 0xbd, 0xc6, 0xff}}},
    {{{   -23,    108,     48}, 0, {  -452,   -292}, {0xd9, 0x6a, 0x39, 0xff}}},
    {{{    24,    -11,    156}, 0, {    36,   1382}, {0x00, 0xbf, 0x6d, 0xff}}},
    {{{   132,     86,     72}, 0, {  1736,   -108}, {0x30, 0x6e, 0x27, 0xff}}},
    {{{  -107,    -11,     96}, 0, { -1844,   1486}, {0x90, 0x28, 0x2b, 0xff}}},
    {{{   233,     -9,     72}, 0, {  3024,   1182}, {0x51, 0xba, 0x43, 0xff}}},
};

// 0x05011D38
static const Vtx heave_ho_seg5_vertex_05011D38[] = {
    {{{  -107,    -11,    -95}, 0, {     0,      0}, {0xa6, 0xbd, 0xc6, 0xff}}},
    {{{    24,    -11,    156}, 0, {     0,      0}, {0x00, 0xbf, 0x6d, 0xff}}},
    {{{  -107,    -11,     96}, 0, {     0,      0}, {0x90, 0x28, 0x2b, 0xff}}},
    {{{    24,    -11,   -155}, 0, {     0,      0}, {0x00, 0xbf, 0x93, 0xff}}},
    {{{   233,     -9,    -71}, 0, {     0,      0}, {0x68, 0x31, 0xcc, 0xff}}},
    {{{   233,     -9,     72}, 0, {     0,      0}, {0x51, 0xba, 0x43, 0xff}}},
};

// 0x05011D98 - 0x05011DE0
const Gfx heave_ho_seg5_dl_05011D98[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_0500E9C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&heave_ho_seg5_lights_05011BC8.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05011BC8.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05011BF8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05011DE0 - 0x05011E28
const Gfx heave_ho_seg5_dl_05011DE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_050109C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(heave_ho_seg5_vertex_05011C38, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  4,  5, 0x0,  2,  5,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x05011E28 - 0x05011E80
const Gfx heave_ho_seg5_dl_05011E28[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_0500F9C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(heave_ho_seg5_vertex_05011C98, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  5,  6,  7, 0x0),
    gsSP2Triangles( 8,  6,  5, 0x0,  6,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05011E80 - 0x05011EC0
const Gfx heave_ho_seg5_dl_05011E80[] = {
    gsSPLight(&heave_ho_seg5_lights_05011BE0.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05011BE0.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05011D38, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 3,  4,  5, 0x0,  3,  5,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x05011EC0 - 0x05011F50
const Gfx heave_ho_seg5_dl_05011EC0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_05011D98),
    gsSPDisplayList(heave_ho_seg5_dl_05011DE0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_05011E28),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(heave_ho_seg5_dl_05011E80),
    gsSPEndDisplayList(),
};

// 0x05011F50
static const Lights1 heave_ho_seg5_lights_05011F50 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05011F68
static const Lights1 heave_ho_seg5_lights_05011F68 = gdSPDefLights1(
    0x4c, 0x00, 0x16,
    0x99, 0x00, 0x2d, 0x28, 0x28, 0x28
);

// 0x05011F80
static const Vtx heave_ho_seg5_vertex_05011F80[] = {
    {{{    -1,     -2,    245}, 0, {  -604,    434}, {0xd6, 0xf8, 0x77, 0xff}}},
    {{{   -37,     20,    206}, 0, {   990,   1372}, {0xb4, 0x5f, 0xdf, 0xff}}},
    {{{   -28,    -33,    206}, 0, {   990,   -368}, {0xdb, 0x8c, 0x21, 0xff}}},
    {{{   -37,     20,    206}, 0, {   198,    552}, {0xb4, 0x5f, 0xdf, 0xff}}},
    {{{   218,     73,    204}, 0, {   774,    552}, {0x30, 0x75, 0xfc, 0xff}}},
    {{{   228,      3,    206}, 0, {   764,    338}, {0x6f, 0xc5, 0xf2, 0xff}}},
    {{{   -28,    -33,    206}, 0, {   194,    386}, {0xdb, 0x8c, 0x21, 0xff}}},
    {{{   166,     53,    250}, 0, {  1112,    914}, {0x19, 0x3d, 0x6c, 0xff}}},
    {{{   174,      9,    250}, 0, {  1060,     34}, {0x1e, 0xc0, 0x69, 0xff}}},
    {{{   228,      3,    206}, 0, {   -98,   -190}, {0x6f, 0xc5, 0xf2, 0xff}}},
    {{{   218,     73,    204}, 0, {   -98,   1190}, {0x30, 0x75, 0xfc, 0xff}}},
};

// 0x05012030
static const Vtx heave_ho_seg5_vertex_05012030[] = {
    {{{   166,     53,    250}, 0, {     0,      0}, {0x19, 0x3d, 0x6c, 0xff}}},
    {{{   218,     73,    204}, 0, {     0,      0}, {0x30, 0x75, 0xfc, 0xff}}},
    {{{   -37,     20,    206}, 0, {     0,      0}, {0xb4, 0x5f, 0xdf, 0xff}}},
    {{{    -1,     -2,    245}, 0, {     0,      0}, {0xd6, 0xf8, 0x77, 0xff}}},
    {{{   174,      9,    250}, 0, {     0,      0}, {0x1e, 0xc0, 0x69, 0xff}}},
    {{{   -28,    -33,    206}, 0, {     0,      0}, {0xdb, 0x8c, 0x21, 0xff}}},
    {{{   228,      3,    206}, 0, {     0,      0}, {0x6f, 0xc5, 0xf2, 0xff}}},
};

// 0x050120A0 - 0x05012100
const Gfx heave_ho_seg5_dl_050120A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_050109C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&heave_ho_seg5_lights_05011F50.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05011F50.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05011F80, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 9, 10,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05012100 - 0x05012148
const Gfx heave_ho_seg5_dl_05012100[] = {
    gsSPLight(&heave_ho_seg5_lights_05011F68.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05011F68.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05012030, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  0, 0x0),
    gsSP2Triangles( 4,  0,  3, 0x0,  3,  5,  4, 0x0),
    gsSP1Triangle( 6,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x05012148 - 0x050121B0
const Gfx heave_ho_seg5_dl_05012148[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_050120A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(heave_ho_seg5_dl_05012100),
    gsSPEndDisplayList(),
};

// 0x050121B0
static const Lights1 heave_ho_seg5_lights_050121B0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x050121C8
static const Lights1 heave_ho_seg5_lights_050121C8 = gdSPDefLights1(
    0x4c, 0x00, 0x16,
    0x99, 0x00, 0x2d, 0x28, 0x28, 0x28
);

// 0x050121E0
static const Vtx heave_ho_seg5_vertex_050121E0[] = {
    {{{   -28,    -33,   -205}, 0, {   990,   -368}, {0xdb, 0x8c, 0xdf, 0xff}}},
    {{{   -37,     20,   -205}, 0, {   990,   1372}, {0xb4, 0x5f, 0x21, 0xff}}},
    {{{    -1,     -2,   -244}, 0, {  -604,    434}, {0xd6, 0xf8, 0x89, 0xff}}},
    {{{   228,      3,   -205}, 0, {   764,    338}, {0x6f, 0xc5, 0x0e, 0xff}}},
    {{{   218,     73,   -203}, 0, {   774,    552}, {0x30, 0x75, 0x04, 0xff}}},
    {{{   -37,     20,   -205}, 0, {   198,    552}, {0xb4, 0x5f, 0x21, 0xff}}},
    {{{   -28,    -33,   -205}, 0, {   194,    386}, {0xdb, 0x8c, 0xdf, 0xff}}},
    {{{   228,      3,   -205}, 0, {   -98,   -190}, {0x6f, 0xc5, 0x0e, 0xff}}},
    {{{   174,      9,   -249}, 0, {  1060,     34}, {0x1e, 0xc0, 0x97, 0xff}}},
    {{{   166,     53,   -249}, 0, {  1112,    914}, {0x19, 0x3d, 0x94, 0xff}}},
    {{{   218,     73,   -203}, 0, {   -98,   1190}, {0x30, 0x75, 0x04, 0xff}}},
};

// 0x05012290
static const Vtx heave_ho_seg5_vertex_05012290[] = {
    {{{   -37,     20,   -205}, 0, {     0,      0}, {0xb4, 0x5f, 0x21, 0xff}}},
    {{{   218,     73,   -203}, 0, {     0,      0}, {0x30, 0x75, 0x04, 0xff}}},
    {{{   166,     53,   -249}, 0, {     0,      0}, {0x19, 0x3d, 0x94, 0xff}}},
    {{{   -28,    -33,   -205}, 0, {     0,      0}, {0xdb, 0x8c, 0xdf, 0xff}}},
    {{{   174,      9,   -249}, 0, {     0,      0}, {0x1e, 0xc0, 0x97, 0xff}}},
    {{{   228,      3,   -205}, 0, {     0,      0}, {0x6f, 0xc5, 0x0e, 0xff}}},
    {{{    -1,     -2,   -244}, 0, {     0,      0}, {0xd6, 0xf8, 0x89, 0xff}}},
};

// 0x05012300 - 0x05012360
const Gfx heave_ho_seg5_dl_05012300[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_050109C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&heave_ho_seg5_lights_050121B0.l, 1),
    gsSPLight(&heave_ho_seg5_lights_050121B0.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_050121E0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 5,  6,  3, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 9, 10,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x05012360 - 0x050123A8
const Gfx heave_ho_seg5_dl_05012360[] = {
    gsSPLight(&heave_ho_seg5_lights_050121C8.l, 1),
    gsSPLight(&heave_ho_seg5_lights_050121C8.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05012290, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  2,  4, 0x0,  4,  3,  6, 0x0),
    gsSP1Triangle( 2,  6,  0, 0x0),
    gsSPEndDisplayList(),
};

// 0x050123A8 - 0x05012410
const Gfx heave_ho_seg5_dl_050123A8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_05012300),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(heave_ho_seg5_dl_05012360),
    gsSPEndDisplayList(),
};

// 0x05012410
static const Lights1 heave_ho_seg5_lights_05012410 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05012428
static const Lights1 heave_ho_seg5_lights_05012428 = gdSPDefLights1(
    0x5d, 0x6d, 0x65,
    0xbb, 0xda, 0xcb, 0x28, 0x28, 0x28
);

// 0x05012440
static const Vtx heave_ho_seg5_vertex_05012440[] = {
    {{{   438,     63,   -205}, 0, {     0,    990}, {0x10, 0x83, 0x00, 0xff}}},
    {{{   438,     63,    206}, 0, {   990,    990}, {0x10, 0x83, 0x00, 0xff}}},
    {{{   146,     25,    206}, 0, {   990,      0}, {0x10, 0x83, 0x00, 0xff}}},
    {{{   146,     25,   -205}, 0, {     0,      0}, {0x10, 0x83, 0x00, 0xff}}},
};

// 0x05012480
static const Vtx heave_ho_seg5_vertex_05012480[] = {
    {{{   188,     61,    206}, 0, {     0,      0}, {0xae, 0x60, 0x00, 0xff}}},
    {{{   188,     61,   -205}, 0, {     0,      0}, {0xae, 0x60, 0x00, 0xff}}},
    {{{   146,     25,   -205}, 0, {     0,      0}, {0xae, 0x60, 0x00, 0xff}}},
    {{{   188,     61,   -205}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   438,     63,   -205}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   146,     25,   -205}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   188,     61,    206}, 0, {     0,      0}, {0xff, 0x7e, 0x00, 0xff}}},
    {{{   438,     63,   -205}, 0, {     0,      0}, {0xff, 0x7e, 0x00, 0xff}}},
    {{{   188,     61,   -205}, 0, {     0,      0}, {0xff, 0x7e, 0x00, 0xff}}},
    {{{   438,     63,    206}, 0, {     0,      0}, {0xff, 0x7e, 0x00, 0xff}}},
    {{{   146,     25,    206}, 0, {     0,      0}, {0xae, 0x60, 0x00, 0xff}}},
    {{{   438,     63,    206}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   188,     61,    206}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   146,     25,    206}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x05012560 - 0x050125A8
const Gfx heave_ho_seg5_dl_05012560[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_0500F1C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&heave_ho_seg5_lights_05012410.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05012410.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05012440, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x050125A8 - 0x050125F8
const Gfx heave_ho_seg5_dl_050125A8[] = {
    gsSPLight(&heave_ho_seg5_lights_05012428.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05012428.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05012480, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles( 0,  2, 10, 0x0, 11, 12, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x050125F8 - 0x05012670
const Gfx heave_ho_seg5_dl_050125F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_05012560),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPDisplayList(heave_ho_seg5_dl_050125A8),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x05012670
static const Vtx heave_ho_seg5_vertex_05012670[] = {
    {{{   -11,     12,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,    -11,      0}, 0, {   479,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    12,     12,      0}, 0, {   479,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -11,    -11,      0}, 0, {     0,    479}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x050126B0 - 0x050126E8
const Gfx heave_ho_seg5_dl_050126B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_050111C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(heave_ho_seg5_vertex_05012670, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x050126E8 - 0x05012758
const Gfx heave_ho_seg5_dl_050126E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_050126B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

// 0x05012758
static const Lights1 heave_ho_seg5_lights_05012758 = gdSPDefLights1(
    0x93, 0x73, 0x7c,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x05012770
static const Vtx heave_ho_seg5_vertex_05012770[] = {
    {{{    30,    155,      0}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    30,   -153,      0}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   214,    155,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   214,   -153,      0}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x050127B0 - 0x050127F8
const Gfx heave_ho_seg5_dl_050127B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, heave_ho_seg5_texture_050113C8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&heave_ho_seg5_lights_05012758.l, 1),
    gsSPLight(&heave_ho_seg5_lights_05012758.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05012770, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  1,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x050127F8 - 0x05012868
const Gfx heave_ho_seg5_dl_050127F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGBA, G_CC_MODULATERGBA),
    gsSPClearGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(heave_ho_seg5_dl_050127B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_CULL_BACK | G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x05012868
static const Vtx heave_ho_seg5_vertex_05012868[] = {
    {{{   -13,     44,     13}, 0, {     0,      0}, {0xde, 0x75, 0x22, 0x00}}},
    {{{     0,     44,     18}, 0, {     0,      0}, {0x00, 0x75, 0x30, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -24,     33,     24}, 0, {     0,      0}, {0xc1, 0x5a, 0x3f, 0xff}}},
    {{{     0,     33,     33}, 0, {     0,      0}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{   -31,     18,     31}, 0, {     0,      0}, {0xae, 0x30, 0x52, 0xff}}},
    {{{     0,     18,     44}, 0, {     0,      0}, {0x00, 0x30, 0x75, 0xff}}},
    {{{   -33,      0,     33}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{     0,      0,     48}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -31,    -18,     31}, 0, {     0,      0}, {0xae, 0xd0, 0x52, 0xff}}},
    {{{     0,    -18,     44}, 0, {     0,      0}, {0x00, 0xd0, 0x75, 0xff}}},
    {{{   -24,    -33,     24}, 0, {     0,      0}, {0xc1, 0xa6, 0x3f, 0xff}}},
    {{{     0,    -33,     33}, 0, {     0,      0}, {0x00, 0xa6, 0x59, 0xff}}},
    {{{   -13,    -44,     13}, 0, {     0,      0}, {0xde, 0x8b, 0x22, 0xff}}},
    {{{     0,    -44,     18}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012968
static const Vtx heave_ho_seg5_vertex_05012968[] = {
    {{{   -18,     44,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{   -13,     44,     13}, 0, {     0,      0}, {0xde, 0x75, 0x22, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -33,     33,      0}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{   -24,     33,     24}, 0, {     0,      0}, {0xc1, 0x5a, 0x3f, 0xff}}},
    {{{   -44,     18,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{   -31,     18,     31}, 0, {     0,      0}, {0xae, 0x30, 0x52, 0xff}}},
    {{{   -48,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -33,      0,     33}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   -44,    -18,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{   -31,    -18,     31}, 0, {     0,      0}, {0xae, 0xd0, 0x52, 0xff}}},
    {{{   -33,    -33,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -24,    -33,     24}, 0, {     0,      0}, {0xc1, 0xa6, 0x3f, 0xff}}},
    {{{   -18,    -44,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{   -13,    -44,     13}, 0, {     0,      0}, {0xde, 0x8b, 0x22, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012A68
static const Vtx heave_ho_seg5_vertex_05012A68[] = {
    {{{   -13,     44,    -13}, 0, {     0,      0}, {0xde, 0x75, 0xde, 0xff}}},
    {{{   -18,     44,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -24,     33,    -24}, 0, {     0,      0}, {0xc1, 0x5a, 0xc1, 0xff}}},
    {{{   -33,     33,      0}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{   -31,     18,    -31}, 0, {     0,      0}, {0xae, 0x30, 0xae, 0xff}}},
    {{{   -44,     18,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{   -33,      0,    -33}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   -48,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -31,    -18,    -31}, 0, {     0,      0}, {0xae, 0xd0, 0xae, 0xff}}},
    {{{   -44,    -18,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{   -24,    -33,    -24}, 0, {     0,      0}, {0xc1, 0xa6, 0xc1, 0xff}}},
    {{{   -33,    -33,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -13,    -44,    -13}, 0, {     0,      0}, {0xde, 0x8b, 0xde, 0xff}}},
    {{{   -18,    -44,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012B68
static const Vtx heave_ho_seg5_vertex_05012B68[] = {
    {{{     0,     44,    -18}, 0, {     0,      0}, {0x00, 0x75, 0xd0, 0xff}}},
    {{{   -13,     44,    -13}, 0, {     0,      0}, {0xde, 0x75, 0xde, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,     33,    -33}, 0, {     0,      0}, {0x00, 0x5a, 0xa7, 0xff}}},
    {{{   -24,     33,    -24}, 0, {     0,      0}, {0xc1, 0x5a, 0xc1, 0xff}}},
    {{{     0,     18,    -44}, 0, {     0,      0}, {0x00, 0x30, 0x8b, 0xff}}},
    {{{   -31,     18,    -31}, 0, {     0,      0}, {0xae, 0x30, 0xae, 0xff}}},
    {{{     0,      0,    -48}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -33,      0,    -33}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{     0,    -18,    -44}, 0, {     0,      0}, {0x00, 0xd0, 0x8b, 0xff}}},
    {{{   -31,    -18,    -31}, 0, {     0,      0}, {0xae, 0xd0, 0xae, 0xff}}},
    {{{     0,    -33,    -33}, 0, {     0,      0}, {0x00, 0xa6, 0xa7, 0xff}}},
    {{{   -24,    -33,    -24}, 0, {     0,      0}, {0xc1, 0xa6, 0xc1, 0xff}}},
    {{{     0,    -44,    -18}, 0, {     0,      0}, {0x00, 0x8b, 0xd0, 0xff}}},
    {{{   -13,    -44,    -13}, 0, {     0,      0}, {0xde, 0x8b, 0xde, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012C68
static const Vtx heave_ho_seg5_vertex_05012C68[] = {
    {{{    13,     44,    -13}, 0, {     0,      0}, {0x22, 0x75, 0xde, 0xff}}},
    {{{     0,     44,    -18}, 0, {     0,      0}, {0x00, 0x75, 0xd0, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    24,     33,    -24}, 0, {     0,      0}, {0x3f, 0x5a, 0xc1, 0xff}}},
    {{{     0,     33,    -33}, 0, {     0,      0}, {0x00, 0x5a, 0xa7, 0xff}}},
    {{{    31,     18,    -31}, 0, {     0,      0}, {0x52, 0x30, 0xae, 0xff}}},
    {{{     0,     18,    -44}, 0, {     0,      0}, {0x00, 0x30, 0x8b, 0xff}}},
    {{{    33,      0,    -33}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{     0,      0,    -48}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    31,    -18,    -31}, 0, {     0,      0}, {0x52, 0xd0, 0xae, 0xff}}},
    {{{     0,    -18,    -44}, 0, {     0,      0}, {0x00, 0xd0, 0x8b, 0xff}}},
    {{{    24,    -33,    -24}, 0, {     0,      0}, {0x3f, 0xa6, 0xc1, 0xff}}},
    {{{     0,    -33,    -33}, 0, {     0,      0}, {0x00, 0xa6, 0xa7, 0xff}}},
    {{{    13,    -44,    -13}, 0, {     0,      0}, {0x22, 0x8b, 0xde, 0xff}}},
    {{{     0,    -44,    -18}, 0, {     0,      0}, {0x00, 0x8b, 0xd0, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012D68
static const Vtx heave_ho_seg5_vertex_05012D68[] = {
    {{{    18,     44,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{    13,     44,    -13}, 0, {     0,      0}, {0x22, 0x75, 0xde, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    33,     33,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{    24,     33,    -24}, 0, {     0,      0}, {0x3f, 0x5a, 0xc1, 0xff}}},
    {{{    44,     18,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{    31,     18,    -31}, 0, {     0,      0}, {0x52, 0x30, 0xae, 0xff}}},
    {{{    48,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    33,      0,    -33}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{    44,    -18,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{    31,    -18,    -31}, 0, {     0,      0}, {0x52, 0xd0, 0xae, 0xff}}},
    {{{    33,    -33,      0}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    24,    -33,    -24}, 0, {     0,      0}, {0x3f, 0xa6, 0xc1, 0xff}}},
    {{{    18,    -44,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    13,    -44,    -13}, 0, {     0,      0}, {0x22, 0x8b, 0xde, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012E68
static const Vtx heave_ho_seg5_vertex_05012E68[] = {
    {{{    13,     44,     13}, 0, {     0,      0}, {0x22, 0x75, 0x22, 0xff}}},
    {{{    18,     44,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    24,     33,     24}, 0, {     0,      0}, {0x3f, 0x5a, 0x3f, 0xff}}},
    {{{    33,     33,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{    31,     18,     31}, 0, {     0,      0}, {0x52, 0x30, 0x52, 0xff}}},
    {{{    44,     18,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{    33,      0,     33}, 0, {     0,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{    48,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    31,    -18,     31}, 0, {     0,      0}, {0x52, 0xd0, 0x52, 0xff}}},
    {{{    44,    -18,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{    24,    -33,     24}, 0, {     0,      0}, {0x3f, 0xa6, 0x3f, 0xff}}},
    {{{    33,    -33,      0}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    13,    -44,     13}, 0, {     0,      0}, {0x22, 0x8b, 0x22, 0xff}}},
    {{{    18,    -44,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05012F68
static const Vtx heave_ho_seg5_vertex_05012F68[] = {
    {{{     0,     44,     18}, 0, {     0,      0}, {0x00, 0x75, 0x30, 0xff}}},
    {{{    13,     44,     13}, 0, {     0,      0}, {0x22, 0x75, 0x22, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,     33,     33}, 0, {     0,      0}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{    24,     33,     24}, 0, {     0,      0}, {0x3f, 0x5a, 0x3f, 0xff}}},
    {{{     0,     18,     44}, 0, {     0,      0}, {0x00, 0x30, 0x75, 0xff}}},
    {{{    31,     18,     31}, 0, {     0,      0}, {0x52, 0x30, 0x52, 0xff}}},
    {{{     0,      0,     48}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    33,      0,     33}, 0, {     0,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{     0,    -18,     44}, 0, {     0,      0}, {0x00, 0xd0, 0x75, 0xff}}},
    {{{    31,    -18,     31}, 0, {     0,      0}, {0x52, 0xd0, 0x52, 0xff}}},
    {{{     0,    -33,     33}, 0, {     0,      0}, {0x00, 0xa6, 0x59, 0xff}}},
    {{{    24,    -33,     24}, 0, {     0,      0}, {0x3f, 0xa6, 0x3f, 0xff}}},
    {{{     0,    -44,     18}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{    13,    -44,     13}, 0, {     0,      0}, {0x22, 0x8b, 0x22, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013068
static const Vtx heave_ho_seg5_vertex_05013068[] = {
    {{{   223,    -25,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0x00}}},
    {{{   223,      0,    -25}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0x00}}},
    {{{   223,     25,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0x00}}},
    {{{   223,     25,      0}, 0, {     0,      0}, {0x01, 0x7f, 0x00, 0xff}}},
    {{{     0,     18,    -18}, 0, {     0,      0}, {0xff, 0x59, 0xa7, 0xff}}},
    {{{     0,     18,     18}, 0, {     0,      0}, {0xff, 0x59, 0x59, 0xff}}},
    {{{   223,      0,    -25}, 0, {     0,      0}, {0x01, 0x00, 0x81, 0xff}}},
    {{{   223,      0,     25}, 0, {     0,      0}, {0x01, 0x00, 0x7f, 0xff}}},
    {{{     0,    -18,     18}, 0, {     0,      0}, {0xff, 0xa7, 0x59, 0xff}}},
    {{{   223,    -25,      0}, 0, {     0,      0}, {0x01, 0x81, 0x00, 0xff}}},
    {{{     0,    -18,    -18}, 0, {     0,      0}, {0xff, 0xa7, 0xa7, 0xff}}},
    {{{   223,      0,     25}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x05013128
static const Vtx heave_ho_seg5_vertex_05013128[] = {
    {{{   -13,     44,     13}, 0, {     0,      0}, {0xde, 0x75, 0x22, 0x00}}},
    {{{     0,     44,     18}, 0, {     0,      0}, {0x00, 0x75, 0x30, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -24,     33,     24}, 0, {     0,      0}, {0xc1, 0x5a, 0x3f, 0xff}}},
    {{{     0,     33,     33}, 0, {     0,      0}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{   -31,     18,     31}, 0, {     0,      0}, {0xae, 0x30, 0x52, 0xff}}},
    {{{     0,     18,     44}, 0, {     0,      0}, {0x00, 0x30, 0x75, 0xff}}},
    {{{   -33,      0,     33}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{     0,      0,     48}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -31,    -18,     31}, 0, {     0,      0}, {0xae, 0xd0, 0x52, 0xff}}},
    {{{     0,    -18,     44}, 0, {     0,      0}, {0x00, 0xd0, 0x75, 0xff}}},
    {{{   -24,    -33,     24}, 0, {     0,      0}, {0xc1, 0xa6, 0x3f, 0xff}}},
    {{{     0,    -33,     33}, 0, {     0,      0}, {0x00, 0xa6, 0x59, 0xff}}},
    {{{   -13,    -44,     13}, 0, {     0,      0}, {0xde, 0x8b, 0x22, 0xff}}},
    {{{     0,    -44,     18}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013228
static const Vtx heave_ho_seg5_vertex_05013228[] = {
    {{{   -18,     44,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{   -13,     44,     13}, 0, {     0,      0}, {0xde, 0x75, 0x22, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -33,     33,      0}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{   -24,     33,     24}, 0, {     0,      0}, {0xc1, 0x5a, 0x3f, 0xff}}},
    {{{   -44,     18,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{   -31,     18,     31}, 0, {     0,      0}, {0xae, 0x30, 0x52, 0xff}}},
    {{{   -48,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -33,      0,     33}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   -44,    -18,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{   -31,    -18,     31}, 0, {     0,      0}, {0xae, 0xd0, 0x52, 0xff}}},
    {{{   -33,    -33,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -24,    -33,     24}, 0, {     0,      0}, {0xc1, 0xa6, 0x3f, 0xff}}},
    {{{   -18,    -44,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{   -13,    -44,     13}, 0, {     0,      0}, {0xde, 0x8b, 0x22, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013328
static const Vtx heave_ho_seg5_vertex_05013328[] = {
    {{{   -13,     44,    -13}, 0, {     0,      0}, {0xde, 0x75, 0xde, 0xff}}},
    {{{   -18,     44,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -24,     33,    -24}, 0, {     0,      0}, {0xc1, 0x5a, 0xc1, 0xff}}},
    {{{   -33,     33,      0}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{   -31,     18,    -31}, 0, {     0,      0}, {0xae, 0x30, 0xae, 0xff}}},
    {{{   -44,     18,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{   -33,      0,    -33}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   -48,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -31,    -18,    -31}, 0, {     0,      0}, {0xae, 0xd0, 0xae, 0xff}}},
    {{{   -44,    -18,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{   -24,    -33,    -24}, 0, {     0,      0}, {0xc1, 0xa6, 0xc1, 0xff}}},
    {{{   -33,    -33,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -13,    -44,    -13}, 0, {     0,      0}, {0xde, 0x8b, 0xde, 0xff}}},
    {{{   -18,    -44,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013428
static const Vtx heave_ho_seg5_vertex_05013428[] = {
    {{{     0,     44,    -18}, 0, {     0,      0}, {0x00, 0x75, 0xd0, 0xff}}},
    {{{   -13,     44,    -13}, 0, {     0,      0}, {0xde, 0x75, 0xde, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,     33,    -33}, 0, {     0,      0}, {0x00, 0x5a, 0xa7, 0xff}}},
    {{{   -24,     33,    -24}, 0, {     0,      0}, {0xc1, 0x5a, 0xc1, 0xff}}},
    {{{     0,     18,    -44}, 0, {     0,      0}, {0x00, 0x30, 0x8b, 0xff}}},
    {{{   -31,     18,    -31}, 0, {     0,      0}, {0xae, 0x30, 0xae, 0xff}}},
    {{{     0,      0,    -48}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -33,      0,    -33}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{     0,    -18,    -44}, 0, {     0,      0}, {0x00, 0xd0, 0x8b, 0xff}}},
    {{{   -31,    -18,    -31}, 0, {     0,      0}, {0xae, 0xd0, 0xae, 0xff}}},
    {{{     0,    -33,    -33}, 0, {     0,      0}, {0x00, 0xa6, 0xa7, 0xff}}},
    {{{   -24,    -33,    -24}, 0, {     0,      0}, {0xc1, 0xa6, 0xc1, 0xff}}},
    {{{     0,    -44,    -18}, 0, {     0,      0}, {0x00, 0x8b, 0xd0, 0xff}}},
    {{{   -13,    -44,    -13}, 0, {     0,      0}, {0xde, 0x8b, 0xde, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013528
static const Vtx heave_ho_seg5_vertex_05013528[] = {
    {{{    13,     44,    -13}, 0, {     0,      0}, {0x22, 0x75, 0xde, 0xff}}},
    {{{     0,     44,    -18}, 0, {     0,      0}, {0x00, 0x75, 0xd0, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    24,     33,    -24}, 0, {     0,      0}, {0x3f, 0x5a, 0xc1, 0xff}}},
    {{{     0,     33,    -33}, 0, {     0,      0}, {0x00, 0x5a, 0xa7, 0xff}}},
    {{{    31,     18,    -31}, 0, {     0,      0}, {0x52, 0x30, 0xae, 0xff}}},
    {{{     0,     18,    -44}, 0, {     0,      0}, {0x00, 0x30, 0x8b, 0xff}}},
    {{{    33,      0,    -33}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{     0,      0,    -48}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    31,    -18,    -31}, 0, {     0,      0}, {0x52, 0xd0, 0xae, 0xff}}},
    {{{     0,    -18,    -44}, 0, {     0,      0}, {0x00, 0xd0, 0x8b, 0xff}}},
    {{{    24,    -33,    -24}, 0, {     0,      0}, {0x3f, 0xa6, 0xc1, 0xff}}},
    {{{     0,    -33,    -33}, 0, {     0,      0}, {0x00, 0xa6, 0xa7, 0xff}}},
    {{{    13,    -44,    -13}, 0, {     0,      0}, {0x22, 0x8b, 0xde, 0xff}}},
    {{{     0,    -44,    -18}, 0, {     0,      0}, {0x00, 0x8b, 0xd0, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013628
static const Vtx heave_ho_seg5_vertex_05013628[] = {
    {{{    18,     44,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{    13,     44,    -13}, 0, {     0,      0}, {0x22, 0x75, 0xde, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    33,     33,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{    24,     33,    -24}, 0, {     0,      0}, {0x3f, 0x5a, 0xc1, 0xff}}},
    {{{    44,     18,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{    31,     18,    -31}, 0, {     0,      0}, {0x52, 0x30, 0xae, 0xff}}},
    {{{    48,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    33,      0,    -33}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{    44,    -18,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{    31,    -18,    -31}, 0, {     0,      0}, {0x52, 0xd0, 0xae, 0xff}}},
    {{{    33,    -33,      0}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    24,    -33,    -24}, 0, {     0,      0}, {0x3f, 0xa6, 0xc1, 0xff}}},
    {{{    18,    -44,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    13,    -44,    -13}, 0, {     0,      0}, {0x22, 0x8b, 0xde, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013728
static const Vtx heave_ho_seg5_vertex_05013728[] = {
    {{{    13,     44,     13}, 0, {     0,      0}, {0x22, 0x75, 0x22, 0xff}}},
    {{{    18,     44,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    24,     33,     24}, 0, {     0,      0}, {0x3f, 0x5a, 0x3f, 0xff}}},
    {{{    33,     33,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{    31,     18,     31}, 0, {     0,      0}, {0x52, 0x30, 0x52, 0xff}}},
    {{{    44,     18,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{    33,      0,     33}, 0, {     0,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{    48,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    31,    -18,     31}, 0, {     0,      0}, {0x52, 0xd0, 0x52, 0xff}}},
    {{{    44,    -18,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{    24,    -33,     24}, 0, {     0,      0}, {0x3f, 0xa6, 0x3f, 0xff}}},
    {{{    33,    -33,      0}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    13,    -44,     13}, 0, {     0,      0}, {0x22, 0x8b, 0x22, 0xff}}},
    {{{    18,    -44,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013828
static const Vtx heave_ho_seg5_vertex_05013828[] = {
    {{{     0,     44,     18}, 0, {     0,      0}, {0x00, 0x75, 0x30, 0xff}}},
    {{{    13,     44,     13}, 0, {     0,      0}, {0x22, 0x75, 0x22, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,     33,     33}, 0, {     0,      0}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{    24,     33,     24}, 0, {     0,      0}, {0x3f, 0x5a, 0x3f, 0xff}}},
    {{{     0,     18,     44}, 0, {     0,      0}, {0x00, 0x30, 0x75, 0xff}}},
    {{{    31,     18,     31}, 0, {     0,      0}, {0x52, 0x30, 0x52, 0xff}}},
    {{{     0,      0,     48}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    33,      0,     33}, 0, {     0,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{     0,    -18,     44}, 0, {     0,      0}, {0x00, 0xd0, 0x75, 0xff}}},
    {{{    31,    -18,     31}, 0, {     0,      0}, {0x52, 0xd0, 0x52, 0xff}}},
    {{{     0,    -33,     33}, 0, {     0,      0}, {0x00, 0xa6, 0x59, 0xff}}},
    {{{    24,    -33,     24}, 0, {     0,      0}, {0x3f, 0xa6, 0x3f, 0xff}}},
    {{{     0,    -44,     18}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{    13,    -44,     13}, 0, {     0,      0}, {0x22, 0x8b, 0x22, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013928
static const Vtx heave_ho_seg5_vertex_05013928[] = {
    {{{   -13,     44,     13}, 0, {     0,      0}, {0xde, 0x75, 0x22, 0x00}}},
    {{{     0,     44,     18}, 0, {     0,      0}, {0x00, 0x75, 0x30, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -24,     33,     24}, 0, {     0,      0}, {0xc1, 0x5a, 0x3f, 0xff}}},
    {{{     0,     33,     33}, 0, {     0,      0}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{   -31,     18,     31}, 0, {     0,      0}, {0xae, 0x30, 0x52, 0xff}}},
    {{{     0,     18,     44}, 0, {     0,      0}, {0x00, 0x30, 0x75, 0xff}}},
    {{{   -33,      0,     33}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{     0,      0,     48}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -31,    -18,     31}, 0, {     0,      0}, {0xae, 0xd0, 0x52, 0xff}}},
    {{{     0,    -18,     44}, 0, {     0,      0}, {0x00, 0xd0, 0x75, 0xff}}},
    {{{   -24,    -33,     24}, 0, {     0,      0}, {0xc1, 0xa6, 0x3f, 0xff}}},
    {{{     0,    -33,     33}, 0, {     0,      0}, {0x00, 0xa6, 0x59, 0xff}}},
    {{{   -13,    -44,     13}, 0, {     0,      0}, {0xde, 0x8b, 0x22, 0xff}}},
    {{{     0,    -44,     18}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013A28
static const Vtx heave_ho_seg5_vertex_05013A28[] = {
    {{{   -18,     44,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{   -13,     44,     13}, 0, {     0,      0}, {0xde, 0x75, 0x22, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -33,     33,      0}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{   -24,     33,     24}, 0, {     0,      0}, {0xc1, 0x5a, 0x3f, 0xff}}},
    {{{   -44,     18,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{   -31,     18,     31}, 0, {     0,      0}, {0xae, 0x30, 0x52, 0xff}}},
    {{{   -48,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -33,      0,     33}, 0, {     0,      0}, {0xa7, 0x00, 0x59, 0xff}}},
    {{{   -44,    -18,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{   -31,    -18,     31}, 0, {     0,      0}, {0xae, 0xd0, 0x52, 0xff}}},
    {{{   -33,    -33,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -24,    -33,     24}, 0, {     0,      0}, {0xc1, 0xa6, 0x3f, 0xff}}},
    {{{   -18,    -44,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{   -13,    -44,     13}, 0, {     0,      0}, {0xde, 0x8b, 0x22, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013B28
static const Vtx heave_ho_seg5_vertex_05013B28[] = {
    {{{   -13,     44,    -13}, 0, {     0,      0}, {0xde, 0x75, 0xde, 0xff}}},
    {{{   -18,     44,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{   -24,     33,    -24}, 0, {     0,      0}, {0xc1, 0x5a, 0xc1, 0xff}}},
    {{{   -33,     33,      0}, 0, {     0,      0}, {0xa7, 0x5a, 0x00, 0xff}}},
    {{{   -31,     18,    -31}, 0, {     0,      0}, {0xae, 0x30, 0xae, 0xff}}},
    {{{   -44,     18,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{   -33,      0,    -33}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   -48,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -31,    -18,    -31}, 0, {     0,      0}, {0xae, 0xd0, 0xae, 0xff}}},
    {{{   -44,    -18,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{   -24,    -33,    -24}, 0, {     0,      0}, {0xc1, 0xa6, 0xc1, 0xff}}},
    {{{   -33,    -33,      0}, 0, {     0,      0}, {0xa7, 0xa6, 0x00, 0xff}}},
    {{{   -13,    -44,    -13}, 0, {     0,      0}, {0xde, 0x8b, 0xde, 0xff}}},
    {{{   -18,    -44,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013C28
static const Vtx heave_ho_seg5_vertex_05013C28[] = {
    {{{     0,     44,    -18}, 0, {     0,      0}, {0x00, 0x75, 0xd0, 0xff}}},
    {{{   -13,     44,    -13}, 0, {     0,      0}, {0xde, 0x75, 0xde, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,     33,    -33}, 0, {     0,      0}, {0x00, 0x5a, 0xa7, 0xff}}},
    {{{   -24,     33,    -24}, 0, {     0,      0}, {0xc1, 0x5a, 0xc1, 0xff}}},
    {{{     0,     18,    -44}, 0, {     0,      0}, {0x00, 0x30, 0x8b, 0xff}}},
    {{{   -31,     18,    -31}, 0, {     0,      0}, {0xae, 0x30, 0xae, 0xff}}},
    {{{     0,      0,    -48}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -33,      0,    -33}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{     0,    -18,    -44}, 0, {     0,      0}, {0x00, 0xd0, 0x8b, 0xff}}},
    {{{   -31,    -18,    -31}, 0, {     0,      0}, {0xae, 0xd0, 0xae, 0xff}}},
    {{{     0,    -33,    -33}, 0, {     0,      0}, {0x00, 0xa6, 0xa7, 0xff}}},
    {{{   -24,    -33,    -24}, 0, {     0,      0}, {0xc1, 0xa6, 0xc1, 0xff}}},
    {{{     0,    -44,    -18}, 0, {     0,      0}, {0x00, 0x8b, 0xd0, 0xff}}},
    {{{   -13,    -44,    -13}, 0, {     0,      0}, {0xde, 0x8b, 0xde, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013D28
static const Vtx heave_ho_seg5_vertex_05013D28[] = {
    {{{    13,     44,    -13}, 0, {     0,      0}, {0x22, 0x75, 0xde, 0xff}}},
    {{{     0,     44,    -18}, 0, {     0,      0}, {0x00, 0x75, 0xd0, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    24,     33,    -24}, 0, {     0,      0}, {0x3f, 0x5a, 0xc1, 0xff}}},
    {{{     0,     33,    -33}, 0, {     0,      0}, {0x00, 0x5a, 0xa7, 0xff}}},
    {{{    31,     18,    -31}, 0, {     0,      0}, {0x52, 0x30, 0xae, 0xff}}},
    {{{     0,     18,    -44}, 0, {     0,      0}, {0x00, 0x30, 0x8b, 0xff}}},
    {{{    33,      0,    -33}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{     0,      0,    -48}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    31,    -18,    -31}, 0, {     0,      0}, {0x52, 0xd0, 0xae, 0xff}}},
    {{{     0,    -18,    -44}, 0, {     0,      0}, {0x00, 0xd0, 0x8b, 0xff}}},
    {{{    24,    -33,    -24}, 0, {     0,      0}, {0x3f, 0xa6, 0xc1, 0xff}}},
    {{{     0,    -33,    -33}, 0, {     0,      0}, {0x00, 0xa6, 0xa7, 0xff}}},
    {{{    13,    -44,    -13}, 0, {     0,      0}, {0x22, 0x8b, 0xde, 0xff}}},
    {{{     0,    -44,    -18}, 0, {     0,      0}, {0x00, 0x8b, 0xd0, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013E28
static const Vtx heave_ho_seg5_vertex_05013E28[] = {
    {{{    18,     44,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{    13,     44,    -13}, 0, {     0,      0}, {0x22, 0x75, 0xde, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    33,     33,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{    24,     33,    -24}, 0, {     0,      0}, {0x3f, 0x5a, 0xc1, 0xff}}},
    {{{    44,     18,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{    31,     18,    -31}, 0, {     0,      0}, {0x52, 0x30, 0xae, 0xff}}},
    {{{    48,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    33,      0,    -33}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{    44,    -18,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{    31,    -18,    -31}, 0, {     0,      0}, {0x52, 0xd0, 0xae, 0xff}}},
    {{{    33,    -33,      0}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    24,    -33,    -24}, 0, {     0,      0}, {0x3f, 0xa6, 0xc1, 0xff}}},
    {{{    18,    -44,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    13,    -44,    -13}, 0, {     0,      0}, {0x22, 0x8b, 0xde, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05013F28
static const Vtx heave_ho_seg5_vertex_05013F28[] = {
    {{{    13,     44,     13}, 0, {     0,      0}, {0x22, 0x75, 0x22, 0xff}}},
    {{{    18,     44,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{    24,     33,     24}, 0, {     0,      0}, {0x3f, 0x5a, 0x3f, 0xff}}},
    {{{    33,     33,      0}, 0, {     0,      0}, {0x59, 0x5a, 0x00, 0xff}}},
    {{{    31,     18,     31}, 0, {     0,      0}, {0x52, 0x30, 0x52, 0xff}}},
    {{{    44,     18,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{    33,      0,     33}, 0, {     0,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{    48,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    31,    -18,     31}, 0, {     0,      0}, {0x52, 0xd0, 0x52, 0xff}}},
    {{{    44,    -18,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{    24,    -33,     24}, 0, {     0,      0}, {0x3f, 0xa6, 0x3f, 0xff}}},
    {{{    33,    -33,      0}, 0, {     0,      0}, {0x59, 0xa6, 0x00, 0xff}}},
    {{{    13,    -44,     13}, 0, {     0,      0}, {0x22, 0x8b, 0x22, 0xff}}},
    {{{    18,    -44,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05014028
static const Vtx heave_ho_seg5_vertex_05014028[] = {
    {{{     0,     44,     18}, 0, {     0,      0}, {0x00, 0x75, 0x30, 0xff}}},
    {{{    13,     44,     13}, 0, {     0,      0}, {0x22, 0x75, 0x22, 0x00}}},
    {{{     0,     48,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0x00}}},
    {{{     0,     33,     33}, 0, {     0,      0}, {0x00, 0x5a, 0x59, 0xff}}},
    {{{    24,     33,     24}, 0, {     0,      0}, {0x3f, 0x5a, 0x3f, 0xff}}},
    {{{     0,     18,     44}, 0, {     0,      0}, {0x00, 0x30, 0x75, 0xff}}},
    {{{    31,     18,     31}, 0, {     0,      0}, {0x52, 0x30, 0x52, 0xff}}},
    {{{     0,      0,     48}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    33,      0,     33}, 0, {     0,      0}, {0x59, 0x00, 0x59, 0xff}}},
    {{{     0,    -18,     44}, 0, {     0,      0}, {0x00, 0xd0, 0x75, 0xff}}},
    {{{    31,    -18,     31}, 0, {     0,      0}, {0x52, 0xd0, 0x52, 0xff}}},
    {{{     0,    -33,     33}, 0, {     0,      0}, {0x00, 0xa6, 0x59, 0xff}}},
    {{{    24,    -33,     24}, 0, {     0,      0}, {0x3f, 0xa6, 0x3f, 0xff}}},
    {{{     0,    -44,     18}, 0, {     0,      0}, {0x00, 0x8b, 0x30, 0xff}}},
    {{{    13,    -44,     13}, 0, {     0,      0}, {0x22, 0x8b, 0x22, 0xff}}},
    {{{     0,    -48,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x05014128
static const Vtx heave_ho_seg5_vertex_05014128[] = {
    {{{   204,    -17,    220}, 0, {     0,      0}, {0x3e, 0x6e, 0x00, 0x00}}},
    {{{   204,    -29,   -219}, 0, {     0,      0}, {0x7f, 0x00, 0xfd, 0x00}}},
    {{{   180,    -17,   -219}, 0, {     0,      0}, {0xc2, 0x6e, 0x00, 0x00}}},
    {{{   204,    -41,    220}, 0, {     0,      0}, {0x3e, 0x92, 0x00, 0xff}}},
    {{{   180,    -41,   -219}, 0, {     0,      0}, {0xc2, 0x92, 0x00, 0xff}}},
    {{{   180,    -29,    220}, 0, {     0,      0}, {0x81, 0x00, 0x03, 0xff}}},
};

// 0x05014188 - 0x05014560
const Gfx heave_ho_seg5_dl_05014188[] = {
    gsSPLight(&heave_ho_seg5_lights_0500E968.l, 1),
    gsSPLight(&heave_ho_seg5_lights_0500E968.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05012868, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012968, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012A68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012B68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012C68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012D68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012E68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05012F68, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x05014560 - 0x050145D0
const Gfx heave_ho_seg5_dl_05014560[] = {
    gsSPLight(&heave_ho_seg5_lights_0500E980.l, 1),
    gsSPLight(&heave_ho_seg5_lights_0500E980.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05013068, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  3,  6, 0x0,  5,  7,  3, 0x0),
    gsSP2Triangles( 8,  9,  7, 0x0, 10,  6,  9, 0x0),
    gsSP2Triangles( 2, 11,  0, 0x0,  7,  5,  8, 0x0),
    gsSP2Triangles( 9,  8, 10, 0x0,  6, 10,  4, 0x0),
    gsSPEndDisplayList(),
};

// 0x050145D0 - 0x050149A8
const Gfx heave_ho_seg5_dl_050145D0[] = {
    gsSPLight(&heave_ho_seg5_lights_0500E968.l, 1),
    gsSPLight(&heave_ho_seg5_lights_0500E968.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05013128, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013228, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013328, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013428, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013528, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013628, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013728, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013828, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x050149A8 - 0x05014D80
const Gfx heave_ho_seg5_dl_050149A8[] = {
    gsSPLight(&heave_ho_seg5_lights_0500E968.l, 1),
    gsSPLight(&heave_ho_seg5_lights_0500E968.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05013928, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013A28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013B28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013C28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013D28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013E28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05013F28, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(heave_ho_seg5_vertex_05014028, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x05014D80 - 0x05014DD0
const Gfx heave_ho_seg5_dl_05014D80[] = {
    gsSPLight(&heave_ho_seg5_lights_0500E980.l, 1),
    gsSPLight(&heave_ho_seg5_lights_0500E980.a, 2),
    gsSPVertex(heave_ho_seg5_vertex_05014128, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  4, 0x0),
    gsSP2Triangles( 4,  3,  5, 0x0,  0,  2,  5, 0x0),
    gsSP2Triangles( 0,  3,  1, 0x0,  2,  4,  5, 0x0),
    gsSPEndDisplayList(),
};
