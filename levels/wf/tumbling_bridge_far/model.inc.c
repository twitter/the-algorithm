// 0x0700E160 - 0x0700E178
static const Lights1 wf_seg7_lights_0700E160 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700E178 - 0x0700E278
static const Vtx wf_seg7_vertex_0700E178[] = {
    {{{  -127,     64,   -575}, 0, {     0,    480}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,    -63,   -575}, 0, {     0,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,    -63,    576}, 0, {  9166,    990}, {0x81, 0x00, 0x00, 0xff}}},
    {{{  -127,     64,    576}, 0, {     0,    480}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -127,    -63,    576}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   215,    -63,    576}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   215,     64,    576}, 0, {   990,    478}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   215,     64,   -575}, 0, {     0,   1498}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -127,     64,   -575}, 0, {   990,   1498}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -127,     64,    576}, 0, {   990,  -3098}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   215,     64,      0}, 0, {     0,   -798}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   215,     64,    576}, 0, {     0,  -3098}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  -127,     64,    576}, 0, {  9166,    480}, {0x81, 0x00, 0x00, 0xff}}},
    {{{   215,     64,   -575}, 0, {     0,    478}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   215,    -63,   -575}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,    -63,   -575}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700E278 - 0x0700E328
static const Vtx wf_seg7_vertex_0700E278[] = {
    {{{   215,     64,   -575}, 0, {     0,    478}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,    -63,   -575}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -127,     64,   -575}, 0, {   990,    478}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   215,    -63,    576}, 0, {     0,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -127,    -63,    576}, 0, {   990,  -3098}, {0x00, 0x81, 0x00, 0xff}}},
    {{{  -127,    -63,   -575}, 0, {   990,   1498}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   215,    -63,   -575}, 0, {     0,   1498}, {0x00, 0x81, 0x00, 0xff}}},
    {{{   215,     64,    576}, 0, {     0,    478}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   215,    -63,    576}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   215,    -63,   -575}, 0, {  9166,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   215,     64,   -575}, 0, {  9166,    480}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0700E328 - 0x0700E3D0
static const Gfx wf_seg7_dl_0700E328[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09004000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700E160.l, 1),
    gsSPLight(&wf_seg7_lights_0700E160.a, 2),
    gsSPVertex(wf_seg7_vertex_0700E178, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles( 9, 10,  7, 0x0,  9, 11, 10, 0x0),
    gsSP2Triangles( 0,  2, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(wf_seg7_vertex_0700E278, 11, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP1Triangle( 7,  9, 10, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700E3D0 - 0x0700E440
const Gfx wf_seg7_dl_0700E3D0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700E328),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
