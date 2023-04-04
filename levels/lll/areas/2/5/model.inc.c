// 0x07025AC0 - 0x07025B80
static const Vtx lll_seg7_vertex_07025AC0[] = {
    {{{ -2732,   2391,  -1140}, 0, {   479,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2773,   2309,  -1069}, 0, {     0,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2732,   2309,  -1140}, 0, {   479,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2773,   2391,  -1069}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2446,   2391,  -1636}, 0, {   479,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2487,   2309,  -1565}, 0, {     0,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2446,   2309,  -1636}, 0, {   479,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -2487,   2391,  -1565}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2937,    518,    -30}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2937,    436,     51}, 0, {   479,    479}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2937,    518,     51}, 0, {   479,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  2937,    436,    -30}, 0, {     0,    479}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025B80 - 0x07025BD8
static const Gfx lll_seg7_dl_07025B80[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700D000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 16 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07025AC0, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 11,  9, 0x0),
    gsSPEndDisplayList(),
};

// 0x07025BD8 - 0x07025C90
const Gfx lll_seg7_dl_07025BD8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogFactor(0x0855, 0xF8AB), // This isn't gsSPFogPosition since there is no valid min/max pair that corresponds to 0x0855F8AB
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 4, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (16 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07025B80),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
