// 0x07013DB8 - 0x07013E38
static const Vtx hmc_seg7_vertex_07013DB8[] = {
    {{{  5671,   -306,    819}, 0, {  6100,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5671,   -204,    205}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5671,   -306,    205}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5671,   -204,    819}, 0, {  6100,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5466,   -511,    819}, 0, {  6100,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5466,   -409,    205}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5466,   -511,    205}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  5466,   -409,    819}, 0, {  6100,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07013E38 - 0x07013E80
static const Gfx hmc_seg7_dl_07013E38[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cave_09003000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(hmc_seg7_vertex_07013DB8, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07013E80 - 0x07013F38
const Gfx hmc_seg7_dl_07013E80[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(hmc_seg7_dl_07013E38),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};
