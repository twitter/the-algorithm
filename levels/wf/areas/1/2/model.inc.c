// 0x070051C8 - 0x070051E0
static const Lights1 wf_seg7_lights_070051C8 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x070051E0 - 0x07005260
static const Vtx wf_seg7_vertex_070051E0[] = {
    {{{   781,   2560,   1728}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   576,   2560,   1728}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   576,   2560,   1933}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{   781,   2560,   1933}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1925,   2560,   -204}, 0, {   990,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1720,   2560,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1925,   2560,      0}, 0, {     0,    990}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{  1720,   2560,   -204}, 0, {   990,      0}, {0x00, 0x7f, 0x00, 0xff}}},
};

// 0x07005260 - 0x070052B8
static const Gfx wf_seg7_dl_07005260[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, wf_seg7_texture_07000800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_070051C8.l, 1),
    gsSPLight(&wf_seg7_lights_070051C8.a, 2),
    gsSPVertex(wf_seg7_vertex_070051E0, 8, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  7,  5, 0x0),
    gsSPEndDisplayList(),
};

// 0x070052B8 - 0x07005328
const Gfx wf_seg7_dl_070052B8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_07005260),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
