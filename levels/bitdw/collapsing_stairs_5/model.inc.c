// 0x0700D208 - 0x0700D308
static const Vtx bitdw_seg7_vertex_0700D208[] = {
    {{{  -716,    256,    307}, 0, {  1948,      0}, {0x65, 0xff, 0xb2, 0xff}}},
    {{{ -1023,      0,   -306}, 0, {    32,    926}, {0x65, 0xff, 0xb2, 0xff}}},
    {{{ -1023,      0,    307}, 0, {  1948,    926}, {0x65, 0xff, 0xb2, 0xff}}},
    {{{  -716,    256,   -306}, 0, {    32,      0}, {0x65, 0xff, 0xb2, 0xff}}},
    {{{  -409,    512,   -306}, 0, {    32,      0}, {0x65, 0xff, 0xcc, 0xff}}},
    {{{  -716,    256,   -306}, 0, {    32,    926}, {0x65, 0xff, 0xcc, 0xff}}},
    {{{  -716,    256,    307}, 0, {  1948,    926}, {0x65, 0xff, 0xcc, 0xff}}},
    {{{  -409,    512,    307}, 0, {  1948,      0}, {0x65, 0xff, 0xcc, 0xff}}},
    {{{  -101,    768,    307}, 0, {  1948,      0}, {0x65, 0xff, 0xe5, 0xff}}},
    {{{  -409,    512,   -306}, 0, {    32,    926}, {0x65, 0xff, 0xe5, 0xff}}},
    {{{  -409,    512,    307}, 0, {  1948,    926}, {0x65, 0xff, 0xe5, 0xff}}},
    {{{  -101,    768,   -306}, 0, {    32,      0}, {0x65, 0xff, 0xe5, 0xff}}},
    {{{   205,   1024,    307}, 0, {  1948,      0}, {0x65, 0xe5, 0xff, 0xff}}},
    {{{   205,   1024,   -306}, 0, {    32,      0}, {0x65, 0xe5, 0xff, 0xff}}},
    {{{  -101,    768,   -306}, 0, {    32,    926}, {0x65, 0xe5, 0xff, 0xff}}},
    {{{  -101,    768,    307}, 0, {  1948,    926}, {0x65, 0xe5, 0xff, 0xff}}},
};

// 0x0700D308 - 0x0700D348
static const Vtx bitdw_seg7_vertex_0700D308[] = {
    {{{ -1023,      0,    307}, 0, {     0,      0}, {0xb7, 0xcd, 0xba, 0xff}}},
    {{{ -1023,      0,   -306}, 0, { -1052,    990}, {0xb7, 0xcd, 0xba, 0xff}}},
    {{{ -1637,      0,   -306}, 0, { -2074,      0}, {0xb7, 0xcd, 0xba, 0xff}}},
    {{{ -1637,      0,    307}, 0, { -1052,  -1054}, {0xb7, 0xcd, 0xba, 0xff}}},
};

// 0x0700D348 - 0x0700D3B0
static const Gfx bitdw_seg7_dl_0700D348[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_0700D208, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D3B0 - 0x0700D3E8
static const Gfx bitdw_seg7_dl_0700D3B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bitdw_seg7_vertex_0700D308, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D3E8 - 0x0700D460
const Gfx bitdw_seg7_dl_0700D3E8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bitdw_seg7_dl_0700D348),
    gsSPDisplayList(bitdw_seg7_dl_0700D3B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
