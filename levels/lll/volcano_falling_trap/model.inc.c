// 0x07025C90 - 0x07025D70
static const Vtx lll_seg7_vertex_07025C90[] = {
    {{{  -306,    614,      0}, 0, {     0,    864}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,   -101,      0}, 0, {     0,   3184}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,   -101,    102}, 0, {   300,   3184}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,    614,    102}, 0, {   300,    864}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,   -101,      0}, 0, {  3086,   3184}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,    614,    102}, 0, {  2754,    864}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,   -101,    102}, 0, {  2754,   3184}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,    614,      0}, 0, {  3086,    864}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,   -101,      0}, 0, {  5078,   3184}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -306,    614,      0}, 0, {  5078,    864}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   307,    614,      0}, 0, {  3086,    864}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   307,   -101,      0}, 0, {  3086,   3184}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   307,    614,    102}, 0, {  3086,    830}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -306,    614,    102}, 0, {  5078,    830}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025D70 - 0x07025E10
static const Vtx lll_seg7_vertex_07025D70[] = {
    {{{   307,   -101,    102}, 0, { -2584,  -4120}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,    614,    102}, 0, { -2584,   3032}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,   -101,    700}, 0, { -2584,  -4120}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,    614,    102}, 0, {  3544,   3032}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,   -101,    102}, 0, {  3546,  -4120}, {0x77, 0x77, 0x77, 0xff}}},
    {{{  -306,   -101,    700}, 0, {  3544,  -4120}, {0x77, 0x77, 0x77, 0xff}}},
    {{{   307,   -101,    700}, 0, { -2584,  -4120}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -306,    614,    102}, 0, {  3544,   3032}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -306,   -101,    700}, 0, {  3544,  -4120}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   307,    614,    102}, 0, { -2584,   3032}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025E10 - 0x07025E78
static const Gfx lll_seg7_dl_07025E10[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, lll_seg7_texture_0700DC00),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07025C90, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSP2Triangles( 9, 12, 10, 0x0,  9, 13, 12, 0x0),
    gsSPEndDisplayList(),
};

// 0x07025E78 - 0x07025EC0
static const Gfx lll_seg7_dl_07025E78[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, fire_0900A000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(lll_seg7_vertex_07025D70, 10, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07025EC0 - 0x07025F80
const Gfx lll_seg7_dl_07025EC0[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogFactor(0x0855, 0xF8AB), // This isn't gsSPFogPosition since there is no valid min/max pair that corresponds to 0x0855F8AB
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(lll_seg7_dl_07025E10),
    gsSPDisplayList(lll_seg7_dl_07025E78),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
