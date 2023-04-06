// 0x0701F700 - 0x0701F7C0
static const Vtx hmc_seg7_vertex_0701F700[] = {
    {{{ -1689,  -4177,   -526}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -2201,  -4689,   -526}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -1689,  -4689,   -526}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -2201,  -4177,   -526}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -4863,  -4177,   -526}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -5375,  -4689,   -526}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -4863,  -4689,   -526}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -5375,  -4177,   -526}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -2774,   -101,  -6271}, 0, {   990,    990}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -2774,    410,  -6783}, 0, {     0,      0}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -2774,   -101,  -6783}, 0, {     0,    990}, {0xff, 0x00, 0x00, 0xb4}}},
    {{{ -2774,    410,  -6271}, 0, {   990,      0}, {0xff, 0x00, 0x00, 0xb4}}},
};

// 0x0701F7C0 - 0x0701F818
static const Gfx hmc_seg7_dl_0701F7C0[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, cave_0900C000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(hmc_seg7_vertex_0701F700, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701F818 - 0x0701F888
const Gfx hmc_seg7_dl_0701F818[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(hmc_seg7_dl_0701F7C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
