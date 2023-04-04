// 0x07016B18 - 0x07016B58
static const Vtx bits_seg7_vertex_07016B18[] = {
    {{{ -1023,      0,    307}, 0, {   480,    478}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{ -1330,      0,   -306}, 0, {   990,   2010}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{ -1330,      0,    307}, 0, {     0,    990}, {0xc8, 0xc8, 0xc8, 0xff}}},
    {{{ -1023,      0,   -306}, 0, {  1502,   1498}, {0xc8, 0xc8, 0xc8, 0xff}}},
};

// 0x07016B58 - 0x07016C58
static const Vtx bits_seg7_vertex_07016B58[] = {
    {{{  -716,    256,    307}, 0, {  1948,      0}, {0xd4, 0xff, 0x00, 0xff}}},
    {{{  -716,    256,   -306}, 0, {    32,      0}, {0xd4, 0xff, 0x00, 0xff}}},
    {{{ -1023,      0,   -306}, 0, {    32,    926}, {0xd4, 0xff, 0x00, 0xff}}},
    {{{ -1023,      0,    307}, 0, {  1948,    926}, {0xd4, 0xff, 0x00, 0xff}}},
    {{{  -409,    512,   -306}, 0, {    32,      0}, {0xaa, 0xff, 0x00, 0xff}}},
    {{{  -716,    256,   -306}, 0, {    32,    926}, {0xaa, 0xff, 0x00, 0xff}}},
    {{{  -716,    256,    307}, 0, {  1948,    926}, {0xaa, 0xff, 0x00, 0xff}}},
    {{{  -409,    512,    307}, 0, {  1948,      0}, {0xaa, 0xff, 0x00, 0xff}}},
    {{{  -101,    768,    307}, 0, {  1948,      0}, {0x7f, 0xff, 0x00, 0xff}}},
    {{{  -101,    768,   -306}, 0, {    32,      0}, {0x7f, 0xff, 0x00, 0xff}}},
    {{{  -409,    512,   -306}, 0, {    32,    926}, {0x7f, 0xff, 0x00, 0xff}}},
    {{{  -409,    512,    307}, 0, {  1948,    926}, {0x7f, 0xff, 0x00, 0xff}}},
    {{{   205,   1024,    307}, 0, {  1948,      0}, {0x2a, 0xff, 0x00, 0xff}}},
    {{{   205,   1024,   -306}, 0, {    32,      0}, {0x2a, 0xff, 0x00, 0xff}}},
    {{{  -101,    768,   -306}, 0, {    32,    926}, {0x2a, 0xff, 0x00, 0xff}}},
    {{{  -101,    768,    307}, 0, {  1948,    926}, {0x2a, 0xff, 0x00, 0xff}}},
};

// 0x07016C58 - 0x07016CD8
static const Vtx bits_seg7_vertex_07016C58[] = {
    {{{   512,   1280,    307}, 0, {  1948,      0}, {0x00, 0xff, 0x55, 0xff}}},
    {{{   205,   1024,   -306}, 0, {    32,    926}, {0x00, 0xff, 0x55, 0xff}}},
    {{{   205,   1024,    307}, 0, {  1948,    926}, {0x00, 0xff, 0x55, 0xff}}},
    {{{   512,   1280,   -306}, 0, {    32,      0}, {0x00, 0xff, 0x55, 0xff}}},
    {{{   819,   1536,    307}, 0, {  1948,      0}, {0x00, 0xff, 0x7f, 0xff}}},
    {{{   819,   1536,   -306}, 0, {    32,      0}, {0x00, 0xff, 0x7f, 0xff}}},
    {{{   512,   1280,   -306}, 0, {    32,    926}, {0x00, 0xff, 0x7f, 0xff}}},
    {{{   512,   1280,    307}, 0, {  1948,    926}, {0x00, 0xff, 0x7f, 0xff}}},
};

// 0x07016CD8 - 0x07016D10
static const Gfx bits_seg7_dl_07016CD8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07016B18, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07016D10 - 0x07016DA0
static const Gfx bits_seg7_dl_07016D10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, sky_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bits_seg7_vertex_07016B58, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPVertex(bits_seg7_vertex_07016C58, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07016DA0 - 0x07016E18
const Gfx bits_seg7_dl_07016DA0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bits_seg7_dl_07016CD8),
    gsSPDisplayList(bits_seg7_dl_07016D10),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
