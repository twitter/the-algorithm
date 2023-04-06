// 0x07007298 - 0x070072B0
static const Lights1 wf_seg7_lights_07007298 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070072B0 - 0x070073A0
static const Vtx wf_seg7_vertex_070072B0[] = {
    {{{  -255,     65,    256}, 0, { -1308,    338}, {0x00, 0xe7, 0x7c, 0xff}}},
    {{{   205,    -62,    230}, 0, {   990,    990}, {0x00, 0xe7, 0x7c, 0xff}}},
    {{{   256,     65,    256}, 0, {  1246,    338}, {0x00, 0xe7, 0x7c, 0xff}}},
    {{{   256,     65,    256}, 0, {  -158,    302}, {0x75, 0xd1, 0x00, 0xff}}},
    {{{   205,    -62,   -229}, 0, {  2268,    990}, {0x75, 0xd1, 0x00, 0xff}}},
    {{{   256,     65,   -255}, 0, {  2396,    302}, {0x75, 0xd1, 0x00, 0xff}}},
    {{{   205,    -62,    230}, 0, {     0,    990}, {0x75, 0xd1, 0x00, 0xff}}},
    {{{   256,     65,   -255}, 0, { -1308,    338}, {0x00, 0xe7, 0x84, 0xff}}},
    {{{   205,    -62,   -229}, 0, { -1052,    990}, {0x00, 0xe7, 0x84, 0xff}}},
    {{{  -204,    -62,   -229}, 0, {   990,    990}, {0x00, 0xe7, 0x84, 0xff}}},
    {{{  -255,     65,   -255}, 0, {  1246,    338}, {0x00, 0xe7, 0x84, 0xff}}},
    {{{  -255,     65,   -255}, 0, { -1562,      0}, {0x8b, 0xd1, 0x00, 0xff}}},
    {{{  -204,    -62,    230}, 0, {   862,    656}, {0x8b, 0xd1, 0x00, 0xff}}},
    {{{  -255,     65,    256}, 0, {   990,      0}, {0x8b, 0xd1, 0x00, 0xff}}},
    {{{  -204,    -62,   -229}, 0, { -1436,    656}, {0x8b, 0xd1, 0x00, 0xff}}},
};

// 0x070073A0 - 0x07007410
static const Vtx wf_seg7_vertex_070073A0[] = {
    {{{   205,    -62,    230}, 0, { -1052,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -204,    -62,    230}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -204,    -62,   -229}, 0, {   990,  -1310}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -255,     65,    256}, 0, { -1308,    338}, {0x00, 0xe7, 0x7c, 0xff}}},
    {{{  -204,    -62,    230}, 0, { -1052,    990}, {0x00, 0xe7, 0x7c, 0xff}}},
    {{{   205,    -62,    230}, 0, {   990,    990}, {0x00, 0xe7, 0x7c, 0xff}}},
    {{{   205,    -62,   -229}, 0, { -1052,  -1310}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x07007410 - 0x07007450
static const Vtx wf_seg7_vertex_07007410[] = {
    {{{   256,     65,   -255}, 0, {   990,  -1566}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,     65,    256}, 0, { -1564,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   256,     65,    256}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -255,     65,   -255}, 0, { -1564,  -1566}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07007450 - 0x070074E0
static const Gfx wf_seg7_dl_07007450[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_07007298.l, 1),
    gsSPLight(&wf_seg7_lights_07007298.a, 2),
    gsSPVertex(wf_seg7_vertex_070072B0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 14, 12, 0x0),
    gsSPVertex(wf_seg7_vertex_070073A0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x070074E0 - 0x07007518
static const Gfx wf_seg7_dl_070074E0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09001000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_07007410, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07007518 - 0x07007590
const Gfx wf_seg7_dl_07007518[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_07007450),
    gsSPDisplayList(wf_seg7_dl_070074E0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
