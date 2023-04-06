// 0x0701A080 - 0x0701A120
static const Vtx lll_seg7_vertex_0701A080[] = {
    {{{     0,      0,      0}, 0, {   480,    478}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0xff}}},
    {{{   307,    307,   -306}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0xff}}},
    {{{   307,    307,    307}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0xff}}},
    {{{     0,      0,      0}, 0, {   480,    478}, {0xc8, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,      0}, {0xc8, 0x00, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {     0,    990}, {0xc8, 0x00, 0x00, 0xff}}},
    {{{   307,    307,   -306}, 0, {   990,    990}, {0xc8, 0x00, 0x00, 0xff}}},
    {{{   307,    307,    307}, 0, {   990,      0}, {0xc8, 0x00, 0x00, 0xff}}},
};

// 0x0701A120 - 0x0701A160
static const Vtx lll_seg7_vertex_0701A120[] = {
    {{{   307,    307,    307}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,    307,   -306}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,    307,    307}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   307,    307,   -306}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0701A160 - 0x0701A1A8
static const Gfx lll_seg7_dl_0701A160[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_0701A080, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  4, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  5,  8,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701A1A8 - 0x0701A1F0
static const Gfx lll_seg7_dl_0701A1A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&lll_seg7_lights_0700FC00.l, 1),
    gsSPLight(&lll_seg7_lights_0700FC00.a, 2),
    gsSPVertex(lll_seg7_vertex_0701A120, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701A1F0 - 0x0701A270
const Gfx lll_seg7_dl_0701A1F0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_0701A160),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPDisplayList(lll_seg7_dl_0701A1A8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
