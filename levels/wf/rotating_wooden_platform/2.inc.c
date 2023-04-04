// 0x0700E7D0 - 0x0700E7E8
static const Lights1 wf_seg7_lights_0700E7D0 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700E7E8 - 0x0700E8E8
static const Vtx wf_seg7_vertex_0700E7E8[] = {
    {{{   177,      0,   -101}, 0, {   922,    224}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   177,      0,    102}, 0, {   922,    734}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,      0,    102}, 0, {  2396,    734}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   768,      0,   -101}, 0, {  2396,    224}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -176,      0,    102}, 0, {  3940,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,   -204}, 0, {  3232,   -236}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   177,      0,   -101}, 0, {  2522,    172}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   177,      0,    102}, 0, {  2522,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,    205}, 0, {  3232,   1396}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -176,      0,   -101}, 0, {  3940,    172}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   768,      0,   -101}, 0, {   166,    172}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   768,      0,    102}, 0, {   166,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   177,      0,    102}, 0, {  2524,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,      0,   -204}, 0, {   478,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -176,      0,    102}, 0, {    36,    734}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,      0,    205}, 0, {   480,    990}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700E8E8 - 0x0700E918
static const Vtx wf_seg7_vertex_0700E8E8[] = {
    {{{     0,      0,   -204}, 0, {   478,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -176,      0,   -101}, 0, {    36,    224}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -176,      0,    102}, 0, {    36,    734}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700E918 - 0x0700E9B8
static const Gfx wf_seg7_dl_0700E918[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09006800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700E7D0.l, 1),
    gsSPLight(&wf_seg7_lights_0700E7D0.a, 2),
    gsSPVertex(wf_seg7_vertex_0700E7E8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 4,  7,  8, 0x0,  4,  9,  5, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 12,  6, 0x0),
    gsSP2Triangles(13,  1,  0, 0x0, 13, 14, 15, 0x0),
    gsSP1Triangle(13, 15,  1, 0x0),
    gsSPVertex(wf_seg7_vertex_0700E8E8, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700E9B8 - 0x0700EA28
const Gfx wf_seg7_dl_0700E9B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700E918),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
