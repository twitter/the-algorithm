// 0x07021E50 - 0x07021E68
static const Lights1 ssl_seg7_lights_07021E50 = gdSPDefLights1(
    0x7f, 0x7f, 0x7f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07021E68 - 0x07021EE8
static const Vtx ssl_seg7_vertex_07021E68[] = {
    {{{  -224,      0,   -224}, 0, {   990,    990}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{   224,      0,    224}, 0, {     0,      0}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{  -224,      0,    224}, 0, {     0,    990}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{   224,      0,   -224}, 0, {   990,      0}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{  -224,    450,    224}, 0, {     0,    990}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{   224,    450,   -224}, 0, {   990,      0}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{  -224,    450,   -224}, 0, {   990,    990}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{   224,    450,    224}, 0, {     0,      0}, {0x67, 0x33, 0x33, 0xff}}},
};

// 0x07021EE8 - 0x07021FE8
static const Vtx ssl_seg7_vertex_07021EE8[] = {
    {{{   224,    450,   -224}, 0, {     0,      0}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{  -224,      0,   -224}, 0, {   990,   2012}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{  -224,    450,   -224}, 0, {   990,      0}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{  -224,    450,    224}, 0, {     0,      0}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{   224,      0,    224}, 0, {   990,   2012}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{   224,    450,    224}, 0, {   990,      0}, {0x67, 0x33, 0x33, 0xff}}},
    {{{  -224,      0,    224}, 0, {     0,   2012}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{  -224,    450,   -224}, 0, {     0,      0}, {0x99, 0x33, 0xcd, 0xff}}},
    {{{  -224,      0,   -224}, 0, {     0,   2012}, {0xd6, 0xac, 0xac, 0xff}}},
    {{{  -224,      0,    224}, 0, {   990,   2012}, {0x99, 0xcd, 0x33, 0xff}}},
    {{{  -224,    450,    224}, 0, {   990,      0}, {0xd6, 0x54, 0x54, 0xff}}},
    {{{   224,    450,    224}, 0, {     0,      0}, {0x67, 0x33, 0x33, 0xff}}},
    {{{   224,      0,    224}, 0, {     0,   2012}, {0x2a, 0xac, 0x54, 0xff}}},
    {{{   224,      0,   -224}, 0, {   990,   2012}, {0x67, 0xcd, 0xcd, 0xff}}},
    {{{   224,    450,   -224}, 0, {   990,      0}, {0x2a, 0x54, 0xac, 0xff}}},
    {{{   224,      0,   -224}, 0, {     0,   2012}, {0x67, 0xcd, 0xcd, 0xff}}},
};

// 0x07021FE8 - 0x07022040
static const Gfx ssl_seg7_dl_07021FE8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, generic_09002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&ssl_seg7_lights_07021E50.l, 1),
    gsSPLight(&ssl_seg7_lights_07021E50.a, 2),
    gsSPVertex(ssl_seg7_vertex_07021E68, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x07022040 - 0x070220A8
static const Gfx ssl_seg7_dl_07022040[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ssl_seg7_texture_07002800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_07021EE8, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x070220A8 - 0x07022170
const Gfx ssl_seg7_dl_070220A8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogFactor(0x0E49, 0xF2B7), // This isn't gsSPFogPosition since there is no valid min/max pair that corresponds to 0x0E49F2B7
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_PASS2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_07021FE8),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_07022040),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x07022170 - 0x070221B0
static const Vtx ssl_seg7_vertex_07022170[] = {
    {{{  -229,    382,   -157}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -229,     68,   -157}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -229,     68,    157}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -229,    382,    157}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070221B0 - 0x070221E8
static const Gfx ssl_seg7_dl_070221B0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, ssl_seg7_texture_07003800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(ssl_seg7_vertex_07022170, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x070221E8 - 0x070222A0
const Gfx ssl_seg7_dl_070221E8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_2CYCLE),
    gsDPSetRenderMode(G_RM_FOG_SHADE_A, G_RM_AA_ZB_TEX_EDGE2),
    gsDPSetDepthSource(G_ZS_PIXEL),
    gsDPSetFogColor(0, 0, 0, 255),
    gsSPFogFactor(0x0E49, 0xF2B7), // This isn't gsSPFogPosition since there is no valid min/max pair that corresponds to 0x0E49F2B7
    gsSPSetGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_PASS2),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(ssl_seg7_dl_070221B0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetRenderMode(G_RM_AA_ZB_TEX_EDGE, G_RM_NOOP2),
    gsSPClearGeometryMode(G_FOG),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};
