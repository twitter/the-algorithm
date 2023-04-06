// 0x07004A18 - 0x07004A30
static const Lights1 jrb_seg7_lights_07004A18 = gdSPDefLights1(
    0x1e, 0x27, 0x3a,
    0x79, 0x9f, 0xeb, 0x28, 0x28, 0x28
);

// 0x07004A30 - 0x07004A48
static const Lights1 jrb_seg7_lights_07004A30 = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07004A48 - 0x07004AC8
static const Vtx jrb_seg7_vertex_07004A48[] = {
    {{{ -1944,    512,   7211}, 0, { 19276,      0}, {0x75, 0x00, 0x2f, 0xff}}},
    {{{ -1675,    512,   6547}, 0, { 15876,      0}, {0x7c, 0x00, 0xe6, 0xff}}},
    {{{ -1675,   1024,   6547}, 0, { 15876,   2522}, {0x7c, 0x00, 0xe6, 0xff}}},
    {{{ -1944,   1024,   7211}, 0, { 19276,   2522}, {0x75, 0x00, 0x2f, 0xff}}},
    {{{ -2073,    512,   6165}, 0, { 12960,      0}, {0x2b, 0x00, 0x89, 0xff}}},
    {{{ -2073,   1024,   6165}, 0, { 12960,   2522}, {0xec, 0x00, 0x83, 0xff}}},
    {{{ -3017,    512,   6778}, 0, {  7700,      0}, {0xbb, 0x00, 0x96, 0xff}}},
    {{{ -3017,   1024,   6778}, 0, {  7700,   2522}, {0xbb, 0x00, 0x96, 0xff}}},
};

// 0x07004AC8 - 0x07004B88
static const Vtx jrb_seg7_vertex_07004AC8[] = {
    {{{ -2771,      0,   1676}, 0, {  1672,    554}, {0xf3, 0x00, 0x7e, 0xff}}},
    {{{  -705,      0,   2401}, 0, {  1672,   -520}, {0xd6, 0x00, 0x77, 0xff}}},
    {{{  -705,    512,   2401}, 0, {  1840,   -520}, {0xd6, 0x00, 0x77, 0xff}}},
    {{{ -4161,    512,   6315}, 0, {  1840,    886}, {0x68, 0x00, 0xb8, 0xff}}},
    {{{ -5256,   1024,   4733}, 0, {  2012,   1544}, {0x75, 0x00, 0xd1, 0xff}}},
    {{{ -4161,   1024,   6315}, 0, {  2012,    886}, {0x68, 0x00, 0xb8, 0xff}}},
    {{{ -5256,    512,   4733}, 0, {  1840,   1544}, {0x7d, 0x00, 0xed, 0xff}}},
    {{{ -5170,    614,   3362}, 0, {  1876,   1608}, {0x7a, 0x00, 0x22, 0xff}}},
    {{{ -5170,      0,   3362}, 0, {  1672,   1608}, {0x6f, 0x00, 0x3d, 0xff}}},
    {{{ -4133,    614,   2187}, 0, {  1876,   1186}, {0x50, 0x00, 0x62, 0xff}}},
    {{{ -4133,      0,   2187}, 0, {  1672,   1186}, {0x3f, 0x00, 0x6e, 0xff}}},
    {{{ -2771,    614,   1676}, 0, {  1876,    554}, {0x10, 0x00, 0x7d, 0xff}}},
};

// 0x07004B88 - 0x07004BF0
static const Gfx jrb_seg7_dl_07004B88[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_09001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&jrb_seg7_lights_07004A18.l, 1),
    gsSPLight(&jrb_seg7_lights_07004A18.a, 2),
    gsSPVertex(jrb_seg7_vertex_07004A48, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 2,  1,  4, 0x0,  2,  4,  5, 0x0),
    gsSP2Triangles( 5,  4,  6, 0x0,  5,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x07004BF0 - 0x07004C78
static const Gfx jrb_seg7_dl_07004BF0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, water_09004800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&jrb_seg7_lights_07004A30.l, 1),
    gsSPLight(&jrb_seg7_lights_07004A30.a, 2),
    gsSPVertex(jrb_seg7_vertex_07004AC8, 12, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  6,  7,  4, 0x0),
    gsSP2Triangles( 6,  8,  7, 0x0,  8,  9,  7, 0x0),
    gsSP2Triangles( 8, 10,  9, 0x0, 10, 11,  9, 0x0),
    gsSP2Triangles(10,  0, 11, 0x0,  0,  2, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x07004C78 - 0x07004D40
const Gfx jrb_seg7_dl_07004C78[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(5, 80, 75, 255),
    gsSPFogPosition(900, 1000),
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(jrb_seg7_dl_07004B88),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(jrb_seg7_dl_07004BF0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};
