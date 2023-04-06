// 0x0701F378 - 0x0701F390
static const Lights1 bbh_seg7_lights_0701F378 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0701F390 - 0x0701F3D0
static const Vtx bbh_seg7_vertex_0701F390[] = {
    {{{   307,   -101,    256}, 0, {   698,    260}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -306,   -101,    256}, 0, {   260,    260}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -306,   -101,   -255}, 0, {   260,    624}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   307,   -101,   -255}, 0, {   698,    624}, {0x00, 0x81, 0x00, 0xff}}},
};

// 0x0701F3D0 - 0x0701F4D0
static const Vtx bbh_seg7_vertex_0701F3D0[] = {
    {{{   307,      0,    256}, 0, {  2522,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,   -101,   -255}, 0, {     0,    988}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,      0,   -255}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   307,      0,   -255}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -306,   -101,   -255}, 0, {  3034,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -306,      0,   -255}, 0, {  3034,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   307,   -101,   -255}, 0, {     0,    992}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -306,      0,   -255}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,   -101,   -255}, 0, {     0,    988}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,   -101,    256}, 0, {  2522,    988}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,      0,    256}, 0, {  2522,      0}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -306,      0,    256}, 0, {  3034,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -306,   -101,    256}, 0, {  3034,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   307,   -101,    256}, 0, {     0,    992}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   307,      0,    256}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   307,   -101,    256}, 0, {  2522,    988}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0701F4D0 - 0x0701F510
static const Vtx bbh_seg7_vertex_0701F4D0[] = {
    {{{   307,      0,   -255}, 0, {  5080,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,      0,    256}, 0, {     0,  -5142}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   307,      0,    256}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -306,      0,   -255}, 0, {  5076,  -5142}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x0701F510 - 0x0701F558
static const Gfx bbh_seg7_dl_0701F510[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09008800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&bbh_seg7_lights_0701F378.l, 1),
    gsSPLight(&bbh_seg7_lights_0701F378.a, 2),
    gsSPVertex(bbh_seg7_vertex_0701F390, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701F558 - 0x0701F5C0
static const Gfx bbh_seg7_dl_0701F558[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09005000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 64 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0701F3D0, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  6,  4, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 7,  9, 10, 0x0, 11, 12, 13, 0x0),
    gsSP2Triangles(11, 13, 14, 0x0,  0, 15,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701F5C0 - 0x0701F5F8
static const Gfx bbh_seg7_dl_0701F5C0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, spooky_09009000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(bbh_seg7_vertex_0701F4D0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0701F5F8 - 0x0701F6A8
const Gfx bbh_seg7_dl_0701F5F8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701F510),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 6, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (64 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701F558),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(bbh_seg7_dl_0701F5C0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
