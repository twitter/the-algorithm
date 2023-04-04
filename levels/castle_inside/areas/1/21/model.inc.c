// 0x07037C58 - 0x07037C70
static const Lights1 inside_castle_seg7_lights_07037C58 = gdSPDefLights1(
    0x37, 0x37, 0x37,
    0xdd, 0xdd, 0xdd, 0x28, 0x28, 0x28
);

// 0x07037C70 - 0x07037D70
static const Vtx inside_castle_seg7_vertex_07037C70[] = {
    {{{  3422,    768,   1208}, 0, {   860,    578}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  4146,    358,    484}, 0, {  1346,    778}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3422,    358,   1208}, 0, {   778,    706}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3422,    768,  -1667}, 0, {   860,    578}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3422,    358,  -1667}, 0, {   778,    706}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  4146,    358,   -943}, 0, {  1346,    778}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  4146,    768,   -943}, 0, {  1428,    648}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3174,    768,  -1791}, 0, {   666,    554}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  2355,    768,  -1791}, 0, {    22,    474}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  2355,    358,  -1791}, 0, {   -58,    604}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3174,    358,  -1791}, 0, {   584,    682}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  2355,    768,   1331}, 0, {    22,    474}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3174,    358,   1331}, 0, {   584,    682}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  2355,    358,   1331}, 0, {   -58,    604}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  3174,    768,   1331}, 0, {   666,    554}, {0x37, 0x37, 0x37, 0xff}}},
    {{{  4146,    768,    484}, 0, {  1428,    648}, {0x37, 0x37, 0x37, 0xff}}},
};

// 0x07037D70 - 0x07037DE8
static const Gfx inside_castle_seg7_dl_07037D70[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, inside_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&inside_castle_seg7_lights_07037C58.l, 1),
    gsSPLight(&inside_castle_seg7_lights_07037C58.a, 2),
    gsSPVertex(inside_castle_seg7_vertex_07037C70, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  3,  5, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07037DE8 - 0x07037E38
const Gfx inside_castle_seg7_dl_07037DE8[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetEnvColor(255, 255, 255, 100),
    gsDPSetCombineMode(G_CC_SHADEFADEA, G_CC_SHADEFADEA),
    gsSPDisplayList(inside_castle_seg7_dl_07037D70),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
