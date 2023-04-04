// 0x07005E48 - 0x07005E60
static const Lights1 wf_seg7_lights_07005E48 = gdSPDefLights1(
    0x66, 0x66, 0x66,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07005E60 - 0x07005EA0
static const Vtx wf_seg7_vertex_07005E60[] = {
    {{{   255,   -127,   -255}, 0, {  2012,   2010}, {0x38, 0x71, 0x00, 0xff}}},
    {{{  -256,    128,    256}, 0, {     0,   -274}, {0x38, 0x71, 0x00, 0xff}}},
    {{{   255,   -127,    256}, 0, {     0,   2010}, {0x38, 0x71, 0x00, 0xff}}},
    {{{  -256,    128,   -255}, 0, {  2012,   -274}, {0x38, 0x71, 0x00, 0xff}}},
};

// 0x07005EA0 - 0x07005EE8
static const Gfx wf_seg7_dl_07005EA0[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, grass_09009800),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPLight(&wf_seg7_lights_07005E48.l, 1),
    gsSPLight(&wf_seg7_lights_07005E48.a, 2),
    gsSPVertex(wf_seg7_vertex_07005E60, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

// 0x07005EE8 - 0x07005F58
const Gfx wf_seg7_dl_07005EE8[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(wf_seg7_dl_07005EA0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};
