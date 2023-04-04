// 0x070192B8 - 0x07019328
static const Vtx hmc_seg7_vertex_070192B8[] = {
    {{{ -6041,   2247,  -7797}, 0, {   990,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -6348,   2094,  -7797}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -6041,   2145,  -7797}, 0, {   990,    820}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -5733,   2196,  -7797}, 0, {  2012,    650}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -6041,   2401,  -7797}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -6041,   1991,  -7797}, 0, {   990,   1328}, {0xff, 0xff, 0xff, 0xff}}},
    {{{ -6348,   2298,  -7797}, 0, {     0,    308}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07019328 - 0x07019368
static const Gfx hmc_seg7_dl_07019328[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cave_09009800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(hmc_seg7_vertex_070192B8, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  6,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07019368 - 0x07019430
const Gfx hmc_seg7_dl_07019368[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_XLU_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogPosition(960, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetEnvColor(255, 255, 255, 100),
    gsDPSetCombineMode(G_CC_MODULATERGBFADE, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(hmc_seg7_dl_07019328),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_XLU_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPEndDisplayList(),
};
