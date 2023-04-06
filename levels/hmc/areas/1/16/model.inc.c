// 0x07014CB8 - 0x07014CD0
static const Lights1 hmc_seg7_lights_07014CB8 = gdSPDefLights1(
    0x23, 0x2b, 0x14,
    0x8e, 0xac, 0x52, 0x28, 0x28, 0x28
);

// 0x07014CD0 - 0x07014DD0
static const Vtx hmc_seg7_vertex_07014CD0[] = {
    {{{  3165,   -409,  -6338}, 0, {   990,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3113,   -869,  -6338}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3165,   -869,  -6338}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3482,   -665,  -6338}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3533,   -665,  -6338}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3533,   -409,  -6338}, 0, {   990,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3482,   -409,  -6338}, 0, {     0,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3328,   -921,  -6338}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3431,   -921,  -6338}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3431,   -409,  -6338}, 0, {   990,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3328,   -409,  -6338}, 0, {     0,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3277,   -409,  -6338}, 0, {   990,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3226,   -818,  -6338}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3277,   -818,  -6338}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3226,   -409,  -6338}, 0, {     0,    422}, {0x00, 0x00, 0x7f, 0x80}}},
    {{{  3113,   -409,  -6338}, 0, {     0,    422}, {0x00, 0x00, 0x7f, 0x80}}},
};

// 0x07014DD0 - 0x07014E48
static const Gfx hmc_seg7_dl_07014DD0[] = {
    gsDPSetTextureImage(G_IM_FMT_IA, G_IM_SIZ_16b, 1, cave_0900C000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&hmc_seg7_lights_07014CB8.l, 1),
    gsSPLight(&hmc_seg7_lights_07014CB8.a, 2),
    gsSPVertex(hmc_seg7_vertex_07014CD0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 14, 12, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07014E48 - 0x07014EB8
const Gfx hmc_seg7_dl_07014E48[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATEIA, G_CC_MODULATEIA),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(hmc_seg7_dl_07014DD0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
