// 0x07009E30 - 0x07009E48
static const Lights1 wf_seg7_lights_07009E30 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07009E48 - 0x07009F48
static const Vtx wf_seg7_vertex_07009E48[] = {
    {{{    15,      0,      0}, 0, {  1502,    990}, {0x6f, 0x00, 0x3c, 0xff}}},
    {{{     8,   2100,     13}, 0, {   990, -19972}, {0x6f, 0x00, 0x3c, 0xff}}},
    {{{     8,      0,     13}, 0, {   990,    990}, {0x6f, 0x00, 0x3c, 0xff}}},
    {{{    -7,   2100,    -12}, 0, {   156,   1020}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   -14,   2100,      0}, 0, {   768,   1020}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    -7,   2100,     13}, 0, {  1076,    490}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     8,   2100,     13}, 0, {   768,    -40}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    15,   2100,      0}, 0, {   156,    -40}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     8,   2100,    -12}, 0, {  -150,    490}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     8,      0,     13}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -7,   2100,     13}, 0, {   480, -19972}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -7,      0,     13}, 0, {   480,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -7,      0,     13}, 0, {   480,    990}, {0x91, 0x00, 0x3c, 0xff}}},
    {{{   -14,   2100,      0}, 0, {     0, -19972}, {0x91, 0x00, 0x3c, 0xff}}},
    {{{   -14,      0,      0}, 0, {     0,    990}, {0x91, 0x00, 0x3c, 0xff}}},
    {{{    -7,   2100,     13}, 0, {   480, -19972}, {0x91, 0x00, 0x3c, 0xff}}},
};

// 0x07009F48 - 0x0700A048
static const Vtx wf_seg7_vertex_07009F48[] = {
    {{{     8,      0,     13}, 0, {   990,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     8,   2100,     13}, 0, {   990, -19972}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    -7,   2100,     13}, 0, {   480, -19972}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   -14,      0,      0}, 0, {  3034,    990}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{   -14,   2100,      0}, 0, {  3034, -19972}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{    -7,   2100,    -12}, 0, {  2524, -19972}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{    15,      0,      0}, 0, {  1502,    990}, {0x6f, 0x00, 0x3c, 0xff}}},
    {{{    15,   2100,      0}, 0, {  1502, -19972}, {0x6f, 0x00, 0x3c, 0xff}}},
    {{{     8,   2100,     13}, 0, {   990, -19972}, {0x6f, 0x00, 0x3c, 0xff}}},
    {{{     8,      0,    -12}, 0, {  2012,    990}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{    15,   2100,      0}, 0, {  1502, -19972}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{    15,      0,      0}, 0, {  1502,    990}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{     8,   2100,    -12}, 0, {  2012, -19972}, {0x6d, 0x00, 0xc1, 0xff}}},
    {{{    -7,      0,    -12}, 0, {  2524,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     8,   2100,    -12}, 0, {  2012, -19972}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     8,      0,    -12}, 0, {  2012,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x0700A048 - 0x0700A0A8
static const Vtx wf_seg7_vertex_0700A048[] = {
    {{{    -7,      0,    -12}, 0, {  2524,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    -7,   2100,    -12}, 0, {  2524, -19972}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     8,   2100,    -12}, 0, {  2012, -19972}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   -14,      0,      0}, 0, {  3034,    990}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{    -7,   2100,    -12}, 0, {  2524, -19972}, {0x93, 0x00, 0xc1, 0xff}}},
    {{{    -7,      0,    -12}, 0, {  2524,    990}, {0x93, 0x00, 0xc1, 0xff}}},
};

// 0x0700A0A8 - 0x0700A170
static const Gfx wf_seg7_dl_0700A0A8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09007800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_07009E30.l, 1),
    gsSPLight(&wf_seg7_lights_07009E30.a, 2),
    gsSPVertex(wf_seg7_vertex_07009E48, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 3,  5,  6, 0x0,  3,  6,  7, 0x0),
    gsSP2Triangles( 3,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 12, 15, 13, 0x0),
    gsSPVertex(wf_seg7_vertex_07009F48, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9, 10, 11, 0x0),
    gsSP2Triangles( 9, 12, 10, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(wf_seg7_vertex_0700A048, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700A170 - 0x0700A1E0
const Gfx wf_seg7_dl_0700A170[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700A0A8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
