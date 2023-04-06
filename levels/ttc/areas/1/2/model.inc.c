// 0x0700AED8 - 0x0700AEF0
static const Lights1 ttc_seg7_lights_0700AED8 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700AEF0 - 0x0700AFE0
static const Vtx ttc_seg7_vertex_0700AEF0[] = {
    {{{  -861,  -8703,   2081}, 0, {  5078,  -5012}, {0x20, 0x00, 0x86, 0x00}}},
    {{{  -861,  -8191,   2081}, 0, {  5078,  -7186}, {0x40, 0x00, 0x93, 0x32}}},
    {{{   862,  -8191,   2081}, 0, {   -30,  -7186}, {0xe0, 0x00, 0x86, 0x32}}},
    {{{  2081,  -8703,    862}, 0, {  5078,  -5012}, {0x86, 0x00, 0xe0, 0x00}}},
    {{{  2081,  -8191,   -861}, 0, {   -30,  -7186}, {0x86, 0x00, 0x20, 0x32}}},
    {{{  2081,  -8703,   -861}, 0, {   -30,  -5012}, {0x93, 0x00, 0x40, 0x00}}},
    {{{  2081,  -8191,    862}, 0, {  5078,  -7186}, {0x93, 0x00, 0xc0, 0x32}}},
    {{{   862,  -8703,   2081}, 0, {  5078,  -5012}, {0xc0, 0x00, 0x93, 0x00}}},
    {{{  2081,  -8191,    862}, 0, {   -30,  -7186}, {0x93, 0x00, 0xc0, 0x32}}},
    {{{  2081,  -8703,    862}, 0, {   -30,  -5012}, {0x86, 0x00, 0xe0, 0x00}}},
    {{{   862,  -8191,   2081}, 0, {  5078,  -7186}, {0xe0, 0x00, 0x86, 0x32}}},
    {{{ -2080,  -8703,    862}, 0, {  5078,  -5012}, {0x6d, 0x00, 0xc0, 0x00}}},
    {{{ -2080,  -8191,    862}, 0, {  5078,  -7186}, {0x7a, 0x00, 0xe0, 0x32}}},
    {{{  -861,  -8191,   2081}, 0, {   -30,  -7186}, {0x40, 0x00, 0x93, 0x32}}},
    {{{  -861,  -8703,   2081}, 0, {   -30,  -5012}, {0x20, 0x00, 0x86, 0x00}}},
};

// 0x0700AFE0 - 0x0700B0D0
static const Vtx ttc_seg7_vertex_0700AFE0[] = {
    {{{  -861,  -8703,  -2080}, 0, {  5078,  -5012}, {0x40, 0x00, 0x6d, 0x00}}},
    {{{  -861,  -8191,  -2080}, 0, {  5078,  -7186}, {0x20, 0x00, 0x7a, 0x32}}},
    {{{ -2080,  -8191,   -861}, 0, {   -30,  -7186}, {0x6d, 0x00, 0x40, 0x32}}},
    {{{  -861,  -8703,   2081}, 0, {  5078,  -5012}, {0x20, 0x00, 0x86, 0x00}}},
    {{{   862,  -8191,   2081}, 0, {   -30,  -7186}, {0xe0, 0x00, 0x86, 0x32}}},
    {{{   862,  -8703,   2081}, 0, {   -30,  -5012}, {0xc0, 0x00, 0x93, 0x00}}},
    {{{   862,  -8703,  -2080}, 0, {  5078,  -5012}, {0xe0, 0x00, 0x7a, 0x00}}},
    {{{   862,  -8191,  -2080}, 0, {  5078,  -7186}, {0xc0, 0x00, 0x6d, 0x32}}},
    {{{  -861,  -8191,  -2080}, 0, {   -30,  -7186}, {0x20, 0x00, 0x7a, 0x32}}},
    {{{  -861,  -8703,  -2080}, 0, {   -30,  -5012}, {0x40, 0x00, 0x6d, 0x00}}},
    {{{  2081,  -8703,   -861}, 0, {  5078,  -5012}, {0x93, 0x00, 0x40, 0x00}}},
    {{{   862,  -8191,  -2080}, 0, {   -30,  -7186}, {0xc0, 0x00, 0x6d, 0x32}}},
    {{{   862,  -8703,  -2080}, 0, {   -30,  -5012}, {0xe0, 0x00, 0x7a, 0x00}}},
    {{{  2081,  -8191,   -861}, 0, {  5078,  -7186}, {0x86, 0x00, 0x20, 0x32}}},
    {{{ -2080,  -8703,   -861}, 0, {   -30,  -5012}, {0x7a, 0x00, 0x20, 0x00}}},
};

// 0x0700B0D0 - 0x0700B110
static const Vtx ttc_seg7_vertex_0700B0D0[] = {
    {{{ -2080,  -8703,   -861}, 0, {  5078,  -5012}, {0x7a, 0x00, 0x20, 0x00}}},
    {{{ -2080,  -8191,    862}, 0, {     0,  -7186}, {0x7a, 0x00, 0xe0, 0x32}}},
    {{{ -2080,  -8703,    862}, 0, {     0,  -5012}, {0x6d, 0x00, 0xc0, 0x00}}},
    {{{ -2080,  -8191,   -861}, 0, {  5078,  -7186}, {0x6d, 0x00, 0x40, 0x32}}},
};

// 0x0700B110 - 0x0700B1D8
static const Gfx ttc_seg7_dl_0700B110[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, machine_09002800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ttc_seg7_lights_0700AED8.l, 1),
    gsSPLight(&ttc_seg7_lights_0700AED8.a, 2),
    gsSPVertex(ttc_seg7_vertex_0700AEF0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7, 10,  8, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(ttc_seg7_vertex_0700AFE0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 10, 13, 11, 0x0),
    gsSP1Triangle( 0,  2, 14, 0x0),
    gsSPVertex(ttc_seg7_vertex_0700B0D0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700B1D8 - 0x0700B238
const Gfx ttc_seg7_dl_0700B1D8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ttc_seg7_dl_0700B110),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
