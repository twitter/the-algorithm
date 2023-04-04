// 0x0700EBB8 - 0x0700EBD0
static const Lights1 wf_seg7_lights_0700EBB8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700EBD0 - 0x0700ECC0
static const Vtx wf_seg7_vertex_0700EBD0[] = {
    {{{   505,      0,     85}, 0, {  -562,    992}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -214,      0,   -204}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   215,      0,   -204}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -214,    102,   -204}, 0, {     0,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  -214,      0,   -204}, 0, {     0,    478}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  -504,      0,     85}, 0, {  2012,    478}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  -504,    102,     85}, 0, {  2012,      0}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{  -504,    102,     85}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -504,      0,     85}, 0, {     0,    478}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   505,      0,     85}, 0, {  2012,    478}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   505,    102,     85}, 0, {  2012,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   215,    102,   -204}, 0, {  1502,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -214,    102,   -204}, 0, {   480,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -504,    102,     85}, 0, {   -52,    992}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   505,    102,     85}, 0, {  2034,    992}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0700ECC0 - 0x0700ED30
static const Vtx wf_seg7_vertex_0700ECC0[] = {
    {{{   505,    102,     85}, 0, {     0,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{   215,      0,   -204}, 0, {  2012,    478}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{   215,    102,   -204}, 0, {  2012,      0}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{   505,      0,     85}, 0, {  -562,    992}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -504,      0,     85}, 0, {  1522,    992}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -214,      0,   -204}, 0, {   990,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   505,      0,     85}, 0, {     0,    478}, {0x59, 0x00, 0xa7, 0xff}}},
};

// 0x0700ED30 - 0x0700EDC0
static const Gfx wf_seg7_dl_0700ED30[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09007000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700EBB8.l, 1),
    gsSPLight(&wf_seg7_lights_0700EBB8.a, 2),
    gsSPVertex(wf_seg7_vertex_0700EBD0, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(11, 13, 14, 0x0),
    gsSPVertex(wf_seg7_vertex_0700ECC0, 7, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP1Triangle( 0,  6,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700EDC0 - 0x0700EE30
const Gfx wf_seg7_dl_0700EDC0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700ED30),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
