// Eyerok

// Unreferenced light group
UNUSED static const Lights1 eyerok_lights_unused = gdSPDefLights1(
    0x3f, 0x2a, 0x16,
    0xff, 0xa9, 0x5b, 0x28, 0x28, 0x28
);

// 0x05008D40
ALIGNED8 static const u8 eyerok_seg5_texture_05008D40[] = {
#include "actors/eyerok/eyerok_bricks.rgba16.inc.c"
};

// 0x05009540
ALIGNED8 static const u8 eyerok_seg5_texture_05009540[] = {
#include "actors/eyerok/eyerok_eye_open.rgba16.inc.c"
};

// 0x05009D40
ALIGNED8 static const u8 eyerok_seg5_texture_05009D40[] = {
#include "actors/eyerok/eyerok_eye_mostly_open.rgba16.inc.c"
};

// 0x0500A540
ALIGNED8 static const u8 eyerok_seg5_texture_0500A540[] = {
#include "actors/eyerok/eyerok_eye_mostly_closed.rgba16.inc.c"
};

// 0x0500AD40
ALIGNED8 static const u8 eyerok_seg5_texture_0500AD40[] = {
#include "actors/eyerok/eyerok_eye_closed.rgba16.inc.c"
};

// 0x0500B540
static const Lights1 eyerok_seg5_lights_0500B540 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500B558
static const Vtx eyerok_seg5_vertex_0500B558[] = {
    {{{   348,    201,   -202}, 0, {     0,    -16}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   348,      0,   -202}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,      0,   -202}, 0, {  1702,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   348,      0,      0}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   348,    201,      0}, 0, {     0,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    201,      0}, 0, {  1702,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,      0,      0}, 0, {  1702,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   348,    201,   -202}, 0, {     0,    -16}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   348,      0,      0}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   348,      0,   -202}, 0, {   990,    -16}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   348,    201,      0}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   348,    201,   -202}, 0, {     0,    -16}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    201,   -202}, 0, {  1702,    -16}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   348,    201,      0}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    201,      0}, 0, {  1702,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0500B648
static const Vtx eyerok_seg5_vertex_0500B648[] = {
    {{{   348,      0,   -202}, 0, {     0,    -16}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   348,      0,      0}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {  1702,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,    201,   -202}, 0, {  1702,    -16}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   348,    201,   -202}, 0, {     0,    -16}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,      0,   -202}, 0, {  1702,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,      0,   -202}, 0, {  1702,    -16}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0500B6B8 - 0x0500B748
const Gfx eyerok_seg5_dl_0500B6B8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500B540.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500B540.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500B558, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500B648, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B748 - 0x0500B7B8
const Gfx eyerok_seg5_dl_0500B748[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500B6B8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500B7B8
static const Lights1 eyerok_seg5_lights_0500B7B8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500B7D0
static const Vtx eyerok_seg5_vertex_0500B7D0[] = {
    {{{   339,    201,   -201}, 0, {  1708,    -16}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   339,      0,   -202}, 0, {  1708,    990}, {0x00, 0x00, 0x82, 0xff}}},
    {{{     0,      0,   -202}, 0, {  3406,    990}, {0x00, 0x00, 0x82, 0xff}}},
    {{{     0,      0,      0}, 0, {  3406,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   339,      0,      0}, 0, {  1708,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    201,      0}, 0, {  3406,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   339,    201,      0}, 0, {  1708,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    201,   -201}, 0, {   -16,    -16}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,      0,   -202}, 0, {   990,    -16}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,    201,      0}, 0, {   -16,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   339,    201,   -201}, 0, {  1708,    -16}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    201,   -201}, 0, {  3406,    -16}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   339,    201,      0}, 0, {  1708,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    201,      0}, 0, {  3406,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0500B8C0
static const Vtx eyerok_seg5_vertex_0500B8C0[] = {
    {{{   339,      0,   -202}, 0, {  1708,    -16}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   339,      0,      0}, 0, {  1708,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {  3406,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,    201,   -201}, 0, {  3406,    -16}, {0x00, 0x00, 0x82, 0xff}}},
    {{{   339,    201,   -201}, 0, {  1708,    -16}, {0x00, 0x00, 0x82, 0xff}}},
    {{{     0,      0,   -202}, 0, {  3406,    990}, {0x00, 0x00, 0x82, 0xff}}},
    {{{     0,      0,   -202}, 0, {  3406,    -16}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0500B930 - 0x0500B9C0
const Gfx eyerok_seg5_dl_0500B930[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500B7B8.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500B7B8.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500B7D0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500B8C0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500B9C0 - 0x0500BA30
const Gfx eyerok_seg5_dl_0500B9C0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500B930),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500BA30
static const Lights1 eyerok_seg5_lights_0500BA30 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BA48
static const Vtx eyerok_seg5_vertex_0500BA48[] = {
    {{{  -200,    200,   -100}, 0, { -1020,   -512}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -200,   -202,   -100}, 0, {   990,   -512}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -200,    200,    200}, 0, { -1020,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -200,   -202,   -100}, 0, {  5728,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -200,    200,   -100}, 0, {  5728,  -1022}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   202,   -202,   -100}, 0, {  3716,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   202,    200,   -100}, 0, {  3716,  -1022}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   202,   -202,    200}, 0, {  3716,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   202,    200,    200}, 0, {  3716,  -1022}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -200,   -202,    200}, 0, {  5728,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -200,    200,    200}, 0, {  5728,  -1022}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -200,   -202,   -100}, 0, {  5728,   -512}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   202,   -202,   -100}, 0, {  3716,   -512}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -200,   -202,    200}, 0, {  5728,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   202,   -202,    200}, 0, {  3716,    990}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0500BB38
static const Vtx eyerok_seg5_vertex_0500BB38[] = {
    {{{   202,    200,   -100}, 0, {     0,   -512}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   202,    200,    200}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   202,   -202,    200}, 0, {  1980,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{  -200,   -202,   -100}, 0, {   990,   -512}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -200,   -202,    200}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -200,    200,    200}, 0, { -1020,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   202,    200,   -100}, 0, {  3716,   -512}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -200,    200,   -100}, 0, {  5728,   -512}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   202,    200,    200}, 0, {  3716,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -200,    200,    200}, 0, {  5728,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   202,   -202,   -100}, 0, {  1980,   -512}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0500BBE8 - 0x0500BC88
const Gfx eyerok_seg5_dl_0500BBE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500BA30.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500BA30.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500BA48, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500BB38, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  9,  8, 0x0),
    gsSP1Triangle(10,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500BC88 - 0x0500BCF8
const Gfx eyerok_seg5_dl_0500BC88[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500BBE8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500BCF8
static const Lights1 eyerok_seg5_lights_0500BCF8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BD10
static const Vtx eyerok_seg5_vertex_0500BD10[] = {
    {{{   403,      0,    101}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   403,      0,   -100}, 0, {   -16,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   403,    201,    101}, 0, {   990,    -16}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,    201,    101}, 0, {   -34,    974}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   403,    201,    101}, 0, {  1978,    974}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    201,   -100}, 0, {   -34,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   403,    201,   -100}, 0, {  1978,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   403,      0,   -100}, 0, {  1978,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   403,      0,    101}, 0, {  1978,    974}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,    101}, 0, {   -34,    974}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,   -100}, 0, {   -34,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   403,      0,   -100}, 0, {  1982,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,      0,   -100}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   403,    201,   -100}, 0, {  1982,    -16}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,    201,   -100}, 0, {     0,    -16}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0500BE00
static const Vtx eyerok_seg5_vertex_0500BE00[] = {
    {{{   403,      0,    101}, 0, {  1982,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   403,    201,    101}, 0, {  1982,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    201,    101}, 0, {     0,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   403,      0,   -100}, 0, {   -16,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   403,    201,   -100}, 0, {   -16,    -16}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   403,    201,    101}, 0, {   990,    -16}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{     0,      0,    101}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0500BE70 - 0x0500BF00
const Gfx eyerok_seg5_dl_0500BE70[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500BCF8.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500BCF8.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500BD10, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10,  7,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500BE00, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500BF00 - 0x0500BF70
const Gfx eyerok_seg5_dl_0500BF00[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500BE70),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500BF70
static const Lights1 eyerok_seg5_lights_0500BF70 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500BF88
static const Vtx eyerok_seg5_vertex_0500BF88[] = {
    {{{     1,      0,    101}, 0, {   -16,  -1024}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   405,      0,    101}, 0, {   -16,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     1,    201,    101}, 0, {   990,  -1024}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     1,      0,   -100}, 0, {   -16,  -1024}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   405,      0,   -100}, 0, {   -16,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     1,      0,    101}, 0, {   990,  -1024}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   405,      0,    101}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     1,    201,    101}, 0, {   990,  -1024}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   405,    201,    101}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     1,    201,   -100}, 0, {   -16,  -1024}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   405,    201,   -100}, 0, {   -16,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   405,      0,   -100}, 0, {   -16,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     1,      0,   -100}, 0, {   -16,  -1024}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   405,    201,   -100}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     1,    201,   -100}, 0, {   990,  -1024}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   405,    201,    101}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0500C088 - 0x0500C100
const Gfx eyerok_seg5_dl_0500C088[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500BF70.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500BF70.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500BF88, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(12, 14, 13, 0x0,  1, 15,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C100 - 0x0500C170
const Gfx eyerok_seg5_dl_0500C100[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500C088),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500C170
static const Lights1 eyerok_seg5_lights_0500C170 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C188
static const Vtx eyerok_seg5_vertex_0500C188[] = {
    {{{   403,      0,   -100}, 0, {   986,    -16}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   403,      0,    101}, 0, {   986,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,    101}, 0, { -1024,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,    201,    101}, 0, { -1024,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   403,    201,    101}, 0, {   988,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    201,   -100}, 0, { -1024,    -16}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   403,    201,   -100}, 0, {   988,    -16}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      0,   -100}, 0, {   990,    -16}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,      0,    101}, 0, {   -16,    -16}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,    201,   -100}, 0, {   990,  -1022}, {0x81, 0x00, 0x00, 0xff}}},
    {{{     0,    201,    101}, 0, {   -16,  -1022}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   403,      0,   -100}, 0, {   990,    -16}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,      0,   -100}, 0, { -1022,    -16}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   403,    201,   -100}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,    201,   -100}, 0, { -1022,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0500C278
static const Vtx eyerok_seg5_vertex_0500C278[] = {
    {{{   403,      0,    101}, 0, {     0,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   403,    201,    101}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    201,    101}, 0, {  1982,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,      0,   -100}, 0, { -1024,    -16}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   403,      0,   -100}, 0, {   986,    -16}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,    101}, 0, { -1024,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,    101}, 0, {  1982,    -16}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0500C2E8 - 0x0500C378
const Gfx eyerok_seg5_dl_0500C2E8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500C170.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500C170.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500C188, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500C278, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 6,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C378 - 0x0500C3E8
const Gfx eyerok_seg5_dl_0500C378[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500C2E8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500C3E8
static const Lights1 eyerok_seg5_lights_0500C3E8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C400
static const Vtx eyerok_seg5_vertex_0500C400[] = {
    {{{   464,     12,   -523}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -3,     -2,   -523}, 0, {  2306,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   330,    409,   -523}, 0, {   576,   1966}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -3,     -2,   -523}, 0, {  2304,  -3034}, {0x03, 0x82, 0x00, 0xff}}},
    {{{   464,     12,   -523}, 0, {     0,  -3034}, {0x03, 0x82, 0x00, 0xff}}},
    {{{    -3,     -2,    282}, 0, {  2304,    990}, {0x03, 0x82, 0x00, 0xff}}},
    {{{   464,     12,    282}, 0, {     0,    990}, {0x03, 0x82, 0x00, 0xff}}},
    {{{   -16,    398,    282}, 0, {  2368,    990}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{   330,    409,    282}, 0, {   640,    990}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{   -16,    398,   -523}, 0, {  2368,  -3034}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{   330,    409,   -523}, 0, {   640,  -3034}, {0xfc, 0x7e, 0x00, 0xff}}},
    {{{    -3,     -2,   -523}, 0, { -3032,    -50}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{    -3,     -2,    282}, 0, {   990,    -50}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{   -16,    398,   -523}, 0, { -3032,   1948}, {0x82, 0xfc, 0x00, 0xff}}},
    {{{   -16,    398,    282}, 0, {   990,   1948}, {0x82, 0xfc, 0x00, 0xff}}},
};

// 0x0500C4F0
static const Vtx eyerok_seg5_vertex_0500C4F0[] = {
    {{{   464,     12,    282}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   330,    409,    282}, 0, {   576,   1966}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -16,    398,    282}, 0, {  2306,   1966}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -3,     -2,   -523}, 0, {  2306,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -16,    398,   -523}, 0, {  2306,   1966}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   330,    409,   -523}, 0, {   576,   1966}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   464,     12,    282}, 0, {   990,      0}, {0x78, 0x28, 0x00, 0xff}}},
    {{{   464,     12,   -523}, 0, { -3032,      0}, {0x78, 0x28, 0x00, 0xff}}},
    {{{   330,    409,    282}, 0, {   990,   1946}, {0x78, 0x28, 0x00, 0xff}}},
    {{{   330,    409,   -523}, 0, { -3032,   1946}, {0x78, 0x28, 0x00, 0xff}}},
    {{{    -3,     -2,    282}, 0, {  2306,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0500C5A0 - 0x0500C640
const Gfx eyerok_seg5_dl_0500C5A0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500C3E8.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500C3E8.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500C400, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 4,  6,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500C4F0, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  7,  9,  8, 0x0),
    gsSP1Triangle(10,  0,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C640 - 0x0500C6B0
const Gfx eyerok_seg5_dl_0500C640[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500C5A0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500C6B0
static const Lights1 eyerok_seg5_lights_0500C6B0 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0500C6C8
static const Vtx eyerok_seg5_vertex_0500C6C8[] = {
    {{{   273,    357,    281}, 0, {  2306,   1978}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -89,    181,    281}, 0, {  4318,   1978}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    86,   -181,    281}, 0, {  4318,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    86,   -181,   -522}, 0, {  4316,  -3022}, {0x37, 0x8e, 0x00, 0xff}}},
    {{{   449,     -5,    281}, 0, {  2304,    990}, {0x37, 0x8e, 0x00, 0xff}}},
    {{{    86,   -181,    281}, 0, {  4316,    990}, {0x37, 0x8e, 0x00, 0xff}}},
    {{{   449,     -5,   -522}, 0, {  2304,  -3022}, {0x37, 0x8e, 0x00, 0xff}}},
    {{{   -89,    181,    281}, 0, {  4380,    990}, {0xc9, 0x72, 0x00, 0xff}}},
    {{{   273,    357,   -522}, 0, {  2370,  -3022}, {0xc9, 0x72, 0x00, 0xff}}},
    {{{   -89,    181,   -522}, 0, {  4380,  -3022}, {0xc9, 0x72, 0x00, 0xff}}},
    {{{   273,    357,    281}, 0, {  2370,    990}, {0xc9, 0x72, 0x00, 0xff}}},
    {{{   -89,    181,    281}, 0, {     0,    990}, {0x8e, 0xc9, 0x00, 0xff}}},
    {{{    86,   -181,   -522}, 0, {  3980,  -1022}, {0x8e, 0xc9, 0x00, 0xff}}},
    {{{    86,   -181,    281}, 0, {     0,  -1022}, {0x8e, 0xc9, 0x00, 0xff}}},
    {{{   -89,    181,   -522}, 0, {  3980,    990}, {0x8e, 0xc9, 0x00, 0xff}}},
};

// 0x0500C7B8
static const Vtx eyerok_seg5_vertex_0500C7B8[] = {
    {{{   -89,    181,   -522}, 0, {  4318,   1978}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   273,    357,   -522}, 0, {  2306,   1978}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   449,     -5,   -522}, 0, {  2306,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   273,    357,    281}, 0, {  2306,   1978}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    86,   -181,    281}, 0, {  4318,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   449,     -5,    281}, 0, {  2306,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   273,    357,   -522}, 0, { -3020,    990}, {0x72, 0x37, 0x00, 0xff}}},
    {{{   449,     -5,    281}, 0, {   990,  -1056}, {0x72, 0x37, 0x00, 0xff}}},
    {{{   449,     -5,   -522}, 0, { -3020,  -1056}, {0x72, 0x37, 0x00, 0xff}}},
    {{{   273,    357,    281}, 0, {   990,    990}, {0x72, 0x37, 0x00, 0xff}}},
    {{{    86,   -181,   -522}, 0, {  4318,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0500C868 - 0x0500C908
const Gfx eyerok_seg5_dl_0500C868[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05008D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&eyerok_seg5_lights_0500C6B0.l, 1),
    gsSPLight(&eyerok_seg5_lights_0500C6B0.a, 2),
    gsSPVertex(eyerok_seg5_vertex_0500C6C8, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(eyerok_seg5_vertex_0500C7B8, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP1Triangle( 0,  2, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0500C908 - 0x0500C978
const Gfx eyerok_seg5_dl_0500C908[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(eyerok_seg5_dl_0500C868),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x0500C978
static const Vtx eyerok_seg5_vertex_0500C978[] = {
    {{{   212,    365,     63}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   212,    365,   -255}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -73,    190,   -255}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -73,    190,     63}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0500C9B8 - 0x0500CA50
const Gfx eyerok_seg5_dl_0500C9B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05009540),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(eyerok_seg5_vertex_0500C978, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0500CA50 - 0x0500CAE8
const Gfx eyerok_seg5_dl_0500CA50[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_05009D40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(eyerok_seg5_vertex_0500C978, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0500CAE8 - 0x0500CB80
const Gfx eyerok_seg5_dl_0500CAE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_0500A540),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(eyerok_seg5_vertex_0500C978, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0500CB80 - 0x0500CC18
const Gfx eyerok_seg5_dl_0500CB80[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, eyerok_seg5_texture_0500AD40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(eyerok_seg5_vertex_0500C978, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

// 0x0500CC18 - 0x0500CC30
const Gfx eyerok_seg5_dl_0500CC18[] = {
    gsSPGeometryMode(G_CULL_BACK, G_CULL_FRONT),
    gsSPEndDisplayList(),
};

// 0x0500CC30 - 0x0500CC48
const Gfx eyerok_seg5_dl_0500CC30[] = {
    gsSPGeometryMode(G_CULL_FRONT, G_CULL_BACK),
    gsSPEndDisplayList(),
};
