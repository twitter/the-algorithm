// 0x07012580 - 0x07012680
static const Vtx bbh_seg7_vertex_07012580[] = {
    {{{ -1049,   1178,   1075}, 0, {   542,    536}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{  -869,   1382,   1075}, 0, {   990,    990}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1049,   1382,   1075}, 0, {   542,    990}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1279,    922,   1075}, 0, {     0,      0}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1100,    922,   1075}, 0, {   416,      0}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1100,   1126,   1075}, 0, {   416,    422}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1279,   1126,   1075}, 0, {     0,    422}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1049,    922,   1075}, 0, {   542,      0}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{  -869,    922,   1075}, 0, {   990,      0}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{  -869,   1126,   1075}, 0, {   990,    422}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1049,   1126,   1075}, 0, {   542,    422}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1279,   1178,   1075}, 0, {     0,    536}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1100,   1382,   1075}, 0, {   416,    990}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1279,   1382,   1075}, 0, {     0,    990}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{ -1100,   1178,   1075}, 0, {   416,    536}, {0x09, 0xb3, 0xff, 0x80}}},
    {{{  -869,   1178,   1075}, 0, {   990,    536}, {0x09, 0xb3, 0xff, 0x80}}},
};

// 0x07012680 - 0x070126E8
static const Gfx bbh_seg7_dl_07012680[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, spooky_0900B000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_07012580, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070126E8 - 0x07012758
const Gfx bbh_seg7_dl_070126E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_07012680),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
