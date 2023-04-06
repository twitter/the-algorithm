// 0x07012C08 - 0x07012C20
static const Lights1 wdw_seg7_lights_07012C08 = gdSPDefLights1(
    0x99, 0x99, 0x99,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07012C20 - 0x07012C60
static const Vtx wdw_seg7_vertex_07012C20[] = {
    {{{    63,    127,    -60}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -64,    127,     67}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    63,    127,     67}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -64,    127,    -60}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07012C60 - 0x07012D50
static const Vtx wdw_seg7_vertex_07012C60[] = {
    {{{    63,    127,    -60}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -64,      0,    -60}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -64,    127,    -60}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    63,    127,     67}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    63,      0,     67}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    63,      0,    -60}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{    63,    127,    -60}, 0, {   990,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   -64,    127,     67}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -64,      0,     67}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    63,      0,     67}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    63,    127,     67}, 0, {   990,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -64,    127,    -60}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -64,      0,    -60}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -64,      0,     67}, 0, {   990,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   -64,    127,     67}, 0, {   990,      0}, {0x81, 0x00, 0x00, 0xff}}},
};

// 0x07012D50 - 0x07012DC0
static const Vtx wdw_seg7_vertex_07012D50[] = {
    {{{    63,      0,     67}, 0, {     0,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -64,      0,     67}, 0, {   990,    990}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   -64,      0,    -60}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    63,    127,    -60}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    63,      0,    -60}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -64,      0,    -60}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    63,      0,    -60}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x07012DC0 - 0x07012E08
static const Gfx wdw_seg7_dl_07012DC0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wdw_seg7_texture_07001800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wdw_seg7_lights_07012C08.l, 1),
    gsSPLight(&wdw_seg7_lights_07012C08.a, 2),
    gsSPVertex(wdw_seg7_vertex_07012C20, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012E08 - 0x07012E88
static const Gfx wdw_seg7_dl_07012E08[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wdw_seg7_vertex_07012C60, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(wdw_seg7_vertex_07012D50, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  2,  6, 0x0),
    gsSPEndDisplayList(),
};

// 0x07012E88 - 0x07012F18
const Gfx wdw_seg7_dl_07012E88[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_07012DC0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wdw_seg7_dl_07012E08),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
