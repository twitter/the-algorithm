// 0x0700D780 - 0x0700D798
static const Lights1 wf_seg7_lights_0700D780 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x0700D798 - 0x0700D898
static const Vtx wf_seg7_vertex_0700D798[] = {
    {{{  -252,    261,     75}, 0, {     0,  -3138}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{   259,    210,    -96}, 0, {  1194,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{  -252,    261,    -77}, 0, {  1194,  -3138}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{   259,    210,     95}, 0, {     0,      0}, {0x0c, 0x7e, 0x00, 0xff}}},
    {{{  -252,    210,   -128}, 0, {  2522,    -34}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   259,      5,   -128}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -252,      5,   -128}, 0, {  2520,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -252,    210,   -128}, 0, {  2522,    -34}, {0x07, 0x13, 0x83, 0xff}}},
    {{{   259,    210,    -96}, 0, {   468,    -34}, {0x07, 0x13, 0x83, 0xff}}},
    {{{   259,      5,   -128}, 0, {     0,    990}, {0x07, 0x13, 0x83, 0xff}}},
    {{{  -252,    261,    -77}, 0, {  4056,    412}, {0x05, 0x59, 0xa7, 0xff}}},
    {{{   259,    210,    -96}, 0, {   478,    990}, {0x05, 0x59, 0xa7, 0xff}}},
    {{{  -252,    210,   -128}, 0, {  4056,    990}, {0x05, 0x59, 0xa7, 0xff}}},
    {{{  -252,    210,    127}, 0, { -3096,    990}, {0x05, 0x5a, 0x58, 0xff}}},
    {{{   259,    210,     95}, 0, {   480,    990}, {0x05, 0x5a, 0x58, 0xff}}},
    {{{  -252,    261,     75}, 0, { -3096,    412}, {0x05, 0x5a, 0x58, 0xff}}},
};

// 0x0700D898 - 0x0700D8F8
static const Vtx wf_seg7_vertex_0700D898[] = {
    {{{  -252,      5,    127}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   259,      5,    127}, 0, {  2012,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -252,    210,    127}, 0, {     0,    182}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   259,      5,    127}, 0, {  2012,    990}, {0x07, 0x13, 0x7d, 0xff}}},
    {{{   259,    210,     95}, 0, {  2012,    162}, {0x07, 0x13, 0x7d, 0xff}}},
    {{{  -252,    210,    127}, 0, {     0,    182}, {0x07, 0x13, 0x7d, 0xff}}},
};

// 0x0700D8F8 - 0x0700D938
static const Vtx wf_seg7_vertex_0700D8F8[] = {
    {{{   259,      5,   -128}, 0, {   990,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   259,    210,     95}, 0, {    96,    172}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   259,      5,    127}, 0, {     0,    990}, {0x7f, 0x00, 0x00, 0xff}}},
    {{{   259,    210,    -96}, 0, {   862,    172}, {0x7f, 0x00, 0x00, 0xff}}},
};

// 0x0700D938 - 0x0700D9B8
static const Gfx wf_seg7_dl_0700D938[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09008000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_0700D780.l, 1),
    gsSPLight(&wf_seg7_lights_0700D780.a, 2),
    gsSPVertex(wf_seg7_vertex_0700D798, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  7,  8,  9, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(wf_seg7_vertex_0700D898, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D9B8 - 0x0700D9F0
static const Gfx wf_seg7_dl_0700D9B8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wf_seg7_texture_07002000),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(wf_seg7_vertex_0700D8F8, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x0700D9F0 - 0x0700DA68
const Gfx wf_seg7_dl_0700D9F0[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_0700D938),
    gsSPDisplayList(wf_seg7_dl_0700D9B8),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
