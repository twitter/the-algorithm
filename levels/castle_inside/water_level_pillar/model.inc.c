// 0x07068908 - 0x07068920
static const Lights1 inside_castle_seg7_lights_07068908 = gdSPDefLights1(
    0x5f, 0x5f, 0x5f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07068920 - 0x07068960
static const Vtx inside_castle_seg7_vertex_07068920[] = {
    {{{  -153,      0,    154}, 0, {  5078,   4564}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   154,      0,    154}, 0, {  6612,   4564}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   154,      0,   -153}, 0, {  6612,   3032}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -153,      0,   -153}, 0, {  5078,   3032}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07068960 - 0x07068A60
static const Vtx inside_castle_seg7_vertex_07068960[] = {
    {{{  -153,      0,    154}, 0, { -5140,  -3098}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -153,   -409,    154}, 0, { -5140,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   154,   -409,    154}, 0, { -2074,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -153,      0,   -153}, 0, {  2012,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -153,   -409,    154}, 0, {  5078,   4054}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -153,      0,    154}, 0, {  5078,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -153,   -409,   -153}, 0, {  2012,   4054}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   154,      0,   -153}, 0, { -2074,  -3098}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -153,   -409,   -153}, 0, { -5140,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -153,      0,   -153}, 0, { -5140,  -3098}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   154,   -409,   -153}, 0, { -2074,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   154,      0,    154}, 0, {  5078,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   154,   -409,   -153}, 0, {  2012,   4054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   154,      0,   -153}, 0, {  2012,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   154,   -409,    154}, 0, {  5078,   4054}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   154,      0,    154}, 0, { -2074,  -3098}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07068A60 - 0x07068AA8
static const Gfx inside_castle_seg7_dl_07068A60[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_07068908.l, 1),
    gsSPLight(&inside_castle_seg7_lights_07068908.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_07068920, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x07068AA8 - 0x07068B10
static const Gfx inside_castle_seg7_dl_07068AA8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_09003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(inside_castle_seg7_vertex_07068960, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0,  2, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x07068B10 - 0x07068B88
const Gfx inside_castle_seg7_dl_07068B10[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(inside_castle_seg7_dl_07068A60),
    gsSPDisplayList(inside_castle_seg7_dl_07068AA8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
