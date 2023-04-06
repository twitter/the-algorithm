// 0x0700BD70 - 0x0700BD88
static const Lights1 ssl_seg7_lights_0700BD70 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700BD88 - 0x0700BE48
static const Vtx ssl_seg7_vertex_0700BD88[] = {
    {{{   512,   -255,    512}, 0, {   480,    990}, {0x59, 0x59, 0x00, 0xff}}},
    {{{   512,   -255,   -511}, 0, {  4566,    990}, {0x59, 0x59, 0x00, 0xff}}},
    {{{     0,    256,      0}, 0, {  2522,  -1900}, {0x59, 0x59, 0x00, 0xff}}},
    {{{   512,   -255,   -511}, 0, {   480,    990}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{  -511,   -255,   -511}, 0, {  4568,    990}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{     0,    256,      0}, 0, {  2524,  -1900}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{  -511,   -255,   -511}, 0, {   478,    990}, {0xa7, 0x59, 0x00, 0xff}}},
    {{{  -511,   -255,    512}, 0, {  4566,    990}, {0xa7, 0x59, 0x00, 0xff}}},
    {{{     0,    256,      0}, 0, {  2522,  -1900}, {0xa7, 0x59, 0x00, 0xff}}},
    {{{  -511,   -255,    512}, 0, {   480,    990}, {0x00, 0x59, 0x59, 0xff}}},
    {{{   512,   -255,    512}, 0, {  4566,    990}, {0x00, 0x59, 0x59, 0xff}}},
    {{{     0,    256,      0}, 0, {  2522,  -1900}, {0x00, 0x59, 0x59, 0xff}}},
};

// 0x0700BE48 - 0x0700BE88
static const Vtx ssl_seg7_vertex_0700BE48[] = {
    {{{  -511,   -255,    512}, 0, {  6100,  -7186}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   512,   -255,   -511}, 0, { 10188,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   512,   -255,    512}, 0, {  6100,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -511,   -255,   -511}, 0, { 10188,  -7186}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0700BE88 - 0x0700BEE0
static const Gfx ssl_seg7_dl_0700BE88[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ssl_seg7_lights_0700BD70.l, 1),
    gsSPLight(&ssl_seg7_lights_0700BD70.a, 2),
    gsSPVertex(ssl_seg7_vertex_0700BD88, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BEE0 - 0x0700BF18
static const Gfx ssl_seg7_dl_0700BEE0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_0900A800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_0700BE48, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BF18 - 0x0700BF90
const Gfx ssl_seg7_dl_0700BF18[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_0700BE88),
    gsSPDisplayList(ssl_seg7_dl_0700BEE0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
