// 0x0700BB68 - 0x0700BB80
static const Lights1 sl_seg7_lights_0700BB68 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700BB80 - 0x0700BC80
static const Vtx sl_seg7_vertex_0700BB80[] = {
    {{{  -204,    317,   1229}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    317,    819}, 0, {  2012,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -204,    317,    819}, 0, {  2012,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    317,   1229}, 0, {     0,  -1054}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   205,    317,    819}, 0, {  2012,  -1054}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   205,    307,    819}, 0, {  2012,  -1054}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    307,    819}, 0, {  2012,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    317,    819}, 0, {  2012,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -204,    307,    819}, 0, {  2012,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   205,    307,    819}, 0, {  2012,  -1054}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   205,    307,   1229}, 0, {     0,  -1054}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -204,    307,   1229}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -204,    317,   1229}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -204,    307,   1229}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   205,    307,   1229}, 0, {     0,  -1054}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   205,    317,   1229}, 0, {     0,  -1054}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x0700BC80 - 0x0700BCF8
static const Gfx sl_seg7_dl_0700BC80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, snow_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&sl_seg7_lights_0700BB68.l, 1),
    gsSPLight(&sl_seg7_lights_0700BB68.a, 2),
    gsSPVertex(sl_seg7_vertex_0700BB80, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700BCF8 - 0x0700BD68
const Gfx sl_seg7_dl_0700BCF8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(sl_seg7_dl_0700BC80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
