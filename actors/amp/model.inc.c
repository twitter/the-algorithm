// Amp

ALIGNED8 static const Texture dAmpElectricityTexture[] = {
#include "actors/amp/amp_electricity.rgba16.inc.c"
};

ALIGNED8 static const Texture dAmpEyesTexture[] = {
#include "actors/amp/amp_eyes.rgba16.inc.c"
};

ALIGNED8 static const Texture dAmpBodyTexture[] = {
#include "actors/amp/amp_body.rgba16.inc.c"
};

ALIGNED8 static const Texture dAmpMouthTexture[] = {
#include "actors/amp/amp_mouth.rgba16.inc.c"
};

static const Vtx dAmpElectricityVertices[] = {
    {{{   224,      0,    -89}, 0, {     0,    480}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   187,    149,      0}, 0, {   223,   1078}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,      0,     90}, 0, {   479,    478}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   187,   -148,      0}, 0, {   224,   -122}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   224,      0,    -89}, 0, {     0,    478}, {0xff, 0xff, 0xff, 0xff}}},
};

const Gfx dAmpElectricitySubDl[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dAmpElectricityTexture),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 16 * 32 - 1, CALC_DXT(16, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dAmpElectricityVertices, 5, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  2,  3,  4, 0x0),
    gsSPEndDisplayList(),
};

const Gfx dAmpElectricityDl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 4, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 4, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (16 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dAmpElectricitySubDl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING | G_CULL_BACK),
    gsSPEndDisplayList(),
};

static const Vtx dAmpEyeVertices[] = {
    {{{    68,     72,    158}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -27,    -71,    164}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    68,    -71,    158}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -27,     72,    164}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

const Gfx dAmpEyeSubDl[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dAmpEyesTexture),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dAmpEyeVertices, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

const Gfx dAmpEyeDl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dAmpEyeSubDl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

static const Vtx dAmpMouthVertices[] = {
    {{{   -29,     72,    164}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -124,    -71,    121}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -29,    -71,    164}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{  -124,     72,    121}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

const Gfx dAmpMouthSubDl[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dAmpMouthTexture),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dAmpMouthVertices, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

const Gfx dAmpMouthDl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dAmpMouthSubDl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

static const Vtx dAmpBodyVertices[] = {
    {{{   -39,    -39,      0}, 0, {     0,    990}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,     40,      0}, 0, {   990,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   -39,     40,      0}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    40,    -39,      0}, 0, {   990,    990}, {0xff, 0xff, 0xff, 0xff}}},
};

const Gfx dAmpBodySubDl[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, dAmpBodyTexture),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPVertex(dAmpBodyVertices, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSPEndDisplayList(),
};

const Gfx dAmpBodyDl[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPClearGeometryMode(G_LIGHTING),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPDisplayList(dAmpBodySubDl),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_LIGHTING),
    gsSPEndDisplayList(),
};

/**
 * Everything beyond this point is unused, and seems to be an attempt at a 3D modelled
 * amp. The model and attempt are overall slightly buggy, with misread lights and a slightly
 * broken model.
 */

UNUSED static const Lights1 dAmpUnused3DLights = gdSPDefLights1(
    0x33, 0x3f, 0x00,
    0xcf, 0xff, 0x00, 0x28, 0x28, 0x28
);

UNUSED static const Vtx dAmpUnused3DVtx01[] = {
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx02[] = {
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx03[] = {
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0x00}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx04[] = {
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0x00}}},
    {{{   240,    160,      0}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0x00}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0x1e, 0x00, 0x00}}},
    {{{   280,      0,     35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
    {{{   240,   -160,      0}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
    {{{   280,      0,    -35}, 0, {     0,      0}, {0x7b, 0xe2, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx05[] = {
    {{{  -184,    -54,    -54}, 0, {     0,      0}, {0x8b, 0xde, 0xde, 0x00}}},
    {{{  -184,    -76,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,   -100,   -100}, 0, {     0,      0}, {0xa6, 0xc1, 0xc1, 0xff}}},
    {{{  -141,   -141,      0}, 0, {     0,      0}, {0xa6, 0xa7, 0x00, 0xff}}},
    {{{   -76,   -130,   -130}, 0, {     0,      0}, {0xd0, 0xae, 0xae, 0xff}}},
    {{{   -76,   -184,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{     0,   -141,   -141}, 0, {     0,      0}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{     0,   -200,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{    76,   -130,   -130}, 0, {     0,      0}, {0x30, 0xae, 0xae, 0xff}}},
    {{{    76,   -184,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{   141,   -100,   -100}, 0, {     0,      0}, {0x5a, 0xc1, 0xc1, 0xff}}},
    {{{   141,   -141,      0}, 0, {     0,      0}, {0x5a, 0xa7, 0x00, 0xff}}},
    {{{   184,    -54,    -54}, 0, {     0,      0}, {0x75, 0xde, 0xde, 0xff}}},
    {{{   184,    -76,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx06[] = {
    {{{  -184,      0,    -76}, 0, {     0,      0}, {0x8b, 0x00, 0xd0, 0xff}}},
    {{{  -184,    -54,    -54}, 0, {     0,      0}, {0x8b, 0xde, 0xde, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,      0,   -141}, 0, {     0,      0}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{  -141,   -100,   -100}, 0, {     0,      0}, {0xa6, 0xc1, 0xc1, 0xff}}},
    {{{   -76,      0,   -184}, 0, {     0,      0}, {0xd0, 0x00, 0x8b, 0xff}}},
    {{{   -76,   -130,   -130}, 0, {     0,      0}, {0xd0, 0xae, 0xae, 0xff}}},
    {{{     0,      0,   -200}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{     0,   -141,   -141}, 0, {     0,      0}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{    76,      0,   -184}, 0, {     0,      0}, {0x30, 0x00, 0x8b, 0xff}}},
    {{{    76,   -130,   -130}, 0, {     0,      0}, {0x30, 0xae, 0xae, 0xff}}},
    {{{   141,      0,   -141}, 0, {     0,      0}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   141,   -100,   -100}, 0, {     0,      0}, {0x5a, 0xc1, 0xc1, 0xff}}},
    {{{   184,      0,    -76}, 0, {     0,      0}, {0x75, 0x00, 0xd0, 0xff}}},
    {{{   184,    -54,    -54}, 0, {     0,      0}, {0x75, 0xde, 0xde, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx07[] = {
    {{{  -184,     54,    -54}, 0, {     0,      0}, {0x8b, 0x22, 0xde, 0xff}}},
    {{{  -184,      0,    -76}, 0, {     0,      0}, {0x8b, 0x00, 0xd0, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,    100,   -100}, 0, {     0,      0}, {0xa6, 0x3f, 0xc1, 0xff}}},
    {{{  -141,      0,   -141}, 0, {     0,      0}, {0xa6, 0x00, 0xa7, 0xff}}},
    {{{   -76,    130,   -130}, 0, {     0,      0}, {0xd0, 0x52, 0xae, 0xff}}},
    {{{   -76,      0,   -184}, 0, {     0,      0}, {0xd0, 0x00, 0x8b, 0xff}}},
    {{{     0,    141,   -141}, 0, {     0,      0}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{     0,      0,   -200}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{    76,    130,   -130}, 0, {     0,      0}, {0x30, 0x52, 0xae, 0xff}}},
    {{{    76,      0,   -184}, 0, {     0,      0}, {0x30, 0x00, 0x8b, 0xff}}},
    {{{   141,    100,   -100}, 0, {     0,      0}, {0x5a, 0x3f, 0xc1, 0xff}}},
    {{{   141,      0,   -141}, 0, {     0,      0}, {0x5a, 0x00, 0xa7, 0xff}}},
    {{{   184,     54,    -54}, 0, {     0,      0}, {0x75, 0x22, 0xde, 0xff}}},
    {{{   184,      0,    -76}, 0, {     0,      0}, {0x75, 0x00, 0xd0, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx08[] = {
    {{{  -184,     76,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0xff}}},
    {{{  -184,     54,    -54}, 0, {     0,      0}, {0x8b, 0x22, 0xde, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,    141,      0}, 0, {     0,      0}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{  -141,    100,   -100}, 0, {     0,      0}, {0xa6, 0x3f, 0xc1, 0xff}}},
    {{{   -76,    184,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{   -76,    130,   -130}, 0, {     0,      0}, {0xd0, 0x52, 0xae, 0xff}}},
    {{{     0,    200,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{     0,    141,   -141}, 0, {     0,      0}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{    76,    184,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{    76,    130,   -130}, 0, {     0,      0}, {0x30, 0x52, 0xae, 0xff}}},
    {{{   141,    141,      0}, 0, {     0,      0}, {0x5a, 0x59, 0x00, 0xff}}},
    {{{   141,    100,   -100}, 0, {     0,      0}, {0x5a, 0x3f, 0xc1, 0xff}}},
    {{{   184,     76,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{   184,     54,    -54}, 0, {     0,      0}, {0x75, 0x22, 0xde, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx09[] = {
    {{{  -184,     54,     54}, 0, {     0,      0}, {0x8b, 0x22, 0x22, 0xff}}},
    {{{  -184,     76,      0}, 0, {     0,      0}, {0x8b, 0x30, 0x00, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,    100,    100}, 0, {     0,      0}, {0xa6, 0x3f, 0x3f, 0xff}}},
    {{{  -141,    141,      0}, 0, {     0,      0}, {0xa6, 0x59, 0x00, 0xff}}},
    {{{   -76,    130,    130}, 0, {     0,      0}, {0xd0, 0x52, 0x52, 0xff}}},
    {{{   -76,    184,      0}, 0, {     0,      0}, {0xd0, 0x75, 0x00, 0xff}}},
    {{{     0,    141,    141}, 0, {     0,      0}, {0x00, 0x59, 0x59, 0xff}}},
    {{{     0,    200,      0}, 0, {     0,      0}, {0x00, 0x7f, 0x00, 0xff}}},
    {{{    76,    130,    130}, 0, {     0,      0}, {0x30, 0x52, 0x52, 0xff}}},
    {{{    76,    184,      0}, 0, {     0,      0}, {0x30, 0x75, 0x00, 0xff}}},
    {{{   141,    100,    100}, 0, {     0,      0}, {0x5a, 0x3f, 0x3f, 0xff}}},
    {{{   141,    141,      0}, 0, {     0,      0}, {0x5a, 0x59, 0x00, 0xff}}},
    {{{   184,     54,     54}, 0, {     0,      0}, {0x75, 0x22, 0x22, 0xff}}},
    {{{   184,     76,      0}, 0, {     0,      0}, {0x75, 0x30, 0x00, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx10[] = {
    {{{  -184,      0,     76}, 0, {     0,      0}, {0x8b, 0x00, 0x30, 0xff}}},
    {{{  -184,     54,     54}, 0, {     0,      0}, {0x8b, 0x22, 0x22, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,      0,    141}, 0, {     0,      0}, {0xa6, 0x00, 0x59, 0xff}}},
    {{{  -141,    100,    100}, 0, {     0,      0}, {0xa6, 0x3f, 0x3f, 0xff}}},
    {{{   -76,      0,    184}, 0, {     0,      0}, {0xd0, 0x00, 0x75, 0xff}}},
    {{{   -76,    130,    130}, 0, {     0,      0}, {0xd0, 0x52, 0x52, 0xff}}},
    {{{     0,      0,    200}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,    141,    141}, 0, {     0,      0}, {0x00, 0x59, 0x59, 0xff}}},
    {{{    76,      0,    184}, 0, {     0,      0}, {0x30, 0x00, 0x75, 0xff}}},
    {{{    76,    130,    130}, 0, {     0,      0}, {0x30, 0x52, 0x52, 0xff}}},
    {{{   141,      0,    141}, 0, {     0,      0}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   141,    100,    100}, 0, {     0,      0}, {0x5a, 0x3f, 0x3f, 0xff}}},
    {{{   184,      0,     76}, 0, {     0,      0}, {0x75, 0x00, 0x30, 0xff}}},
    {{{   184,     54,     54}, 0, {     0,      0}, {0x75, 0x22, 0x22, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx11[] = {
    {{{  -184,    -54,     54}, 0, {     0,      0}, {0x8b, 0xde, 0x22, 0xff}}},
    {{{  -184,      0,     76}, 0, {     0,      0}, {0x8b, 0x00, 0x30, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,   -100,    100}, 0, {     0,      0}, {0xa6, 0xc1, 0x3f, 0xff}}},
    {{{  -141,      0,    141}, 0, {     0,      0}, {0xa6, 0x00, 0x59, 0xff}}},
    {{{   -76,   -130,    130}, 0, {     0,      0}, {0xd0, 0xae, 0x52, 0xff}}},
    {{{   -76,      0,    184}, 0, {     0,      0}, {0xd0, 0x00, 0x75, 0xff}}},
    {{{     0,   -141,    141}, 0, {     0,      0}, {0x00, 0xa7, 0x59, 0xff}}},
    {{{     0,      0,    200}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    76,   -130,    130}, 0, {     0,      0}, {0x30, 0xae, 0x52, 0xff}}},
    {{{    76,      0,    184}, 0, {     0,      0}, {0x30, 0x00, 0x75, 0xff}}},
    {{{   141,   -100,    100}, 0, {     0,      0}, {0x5a, 0xc1, 0x3f, 0xff}}},
    {{{   141,      0,    141}, 0, {     0,      0}, {0x5a, 0x00, 0x59, 0xff}}},
    {{{   184,    -54,     54}, 0, {     0,      0}, {0x75, 0xde, 0x22, 0xff}}},
    {{{   184,      0,     76}, 0, {     0,      0}, {0x75, 0x00, 0x30, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx12[] = {
    {{{  -184,    -76,      0}, 0, {     0,      0}, {0x8b, 0xd0, 0x00, 0xff}}},
    {{{  -184,    -54,     54}, 0, {     0,      0}, {0x8b, 0xde, 0x22, 0x00}}},
    {{{  -200,      0,      0}, 0, {     0,      0}, {0x81, 0x00, 0x00, 0x00}}},
    {{{  -141,   -141,      0}, 0, {     0,      0}, {0xa6, 0xa7, 0x00, 0xff}}},
    {{{  -141,   -100,    100}, 0, {     0,      0}, {0xa6, 0xc1, 0x3f, 0xff}}},
    {{{   -76,   -184,      0}, 0, {     0,      0}, {0xd0, 0x8b, 0x00, 0xff}}},
    {{{   -76,   -130,    130}, 0, {     0,      0}, {0xd0, 0xae, 0x52, 0xff}}},
    {{{     0,   -200,      0}, 0, {     0,      0}, {0x00, 0x81, 0x00, 0xff}}},
    {{{     0,   -141,    141}, 0, {     0,      0}, {0x00, 0xa7, 0x59, 0xff}}},
    {{{    76,   -184,      0}, 0, {     0,      0}, {0x30, 0x8b, 0x00, 0xff}}},
    {{{    76,   -130,    130}, 0, {     0,      0}, {0x30, 0xae, 0x52, 0xff}}},
    {{{   141,   -141,      0}, 0, {     0,      0}, {0x5a, 0xa7, 0x00, 0xff}}},
    {{{   141,   -100,    100}, 0, {     0,      0}, {0x5a, 0xc1, 0x3f, 0xff}}},
    {{{   184,    -76,      0}, 0, {     0,      0}, {0x75, 0xd0, 0x00, 0xff}}},
    {{{   184,    -54,     54}, 0, {     0,      0}, {0x75, 0xde, 0x22, 0xff}}},
    {{{   200,      0,      0}, 0, {     0,      0}, {0x7f, 0x00, 0x00, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx13[] = {
    {{{   -37,     90,    205}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0x00}}},
    {{{  -129,     90,    163}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0x00}}},
    {{{  -129,    -90,    163}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0x00}}},
    {{{   -37,    -90,    205}, 0, {     0,      0}, {0xcc, 0x00, 0x73, 0xff}}},
};

UNUSED static const Vtx dAmpUnused3DVtx14[] = {
    {{{   112,     -7,    182}, 0, {     0,      0}, {0x4c, 0xd8, 0x5c, 0x00}}},
    {{{    66,   -139,    162}, 0, {     0,      0}, {0x4c, 0xd8, 0x5c, 0x00}}},
    {{{   175,    -77,     98}, 0, {     0,      0}, {0x4c, 0xd8, 0x5c, 0x00}}},
};

UNUSED static const Vtx dAmpUnused3DVtx15[] = {
    {{{    63,     90,    198}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0x00}}},
    {{{   -35,     90,    205}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0x00}}},
    {{{   -35,    -90,    205}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0x00}}},
    {{{    63,    -90,    198}, 0, {     0,      0}, {0x08, 0x00, 0x7e, 0xff}}},
};

UNUSED const Gfx dAmpUnused3DElectricDl1[] = {
    gsSPLight(&dAmpUnused3DLights.l, 1),
    gsSPLight(&dAmpUnused3DLights.a, 2),
    gsSPVertex(dAmpUnused3DVtx01, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DElectricDl2[] = {
    gsSPLight(&dAmpUnused3DLights.l, 1),
    gsSPLight(&dAmpUnused3DLights.a, 2),
    gsSPVertex(dAmpUnused3DVtx02, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DElectricDl3[] = {
    gsSPLight(&dAmpUnused3DLights.l, 1),
    gsSPLight(&dAmpUnused3DLights.a, 2),
    gsSPVertex(dAmpUnused3DVtx03, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DElectricDl4[] = {
    gsSPLight(&dAmpUnused3DLights.l, 1),
    gsSPLight(&dAmpUnused3DLights.a, 2),
    gsSPVertex(dAmpUnused3DVtx04, 6, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DModelDl[] = {
    //! Vertex interpreted as light
    gsSPLight((const u8*)dAmpUnused3DVtx01 + 0x8, 1),
    gsSPLight((const u8*)dAmpUnused3DVtx01, 2),
    gsSPVertex(dAmpUnused3DVtx05, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx06, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx07, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx08, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx09, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx10, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx11, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPVertex(dAmpUnused3DVtx12, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  0,  3, 0x0),
    gsSP2Triangles( 1,  3,  4, 0x0,  4,  3,  5, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  6,  5,  7, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  8,  7,  9, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 10,  9, 11, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 12, 11, 13, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 14, 13, 15, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DElectricDl5[] = {
    //! Vertex interpreted as light
    gsSPLight((const u8*)dAmpUnused3DVtx01 + 0x8, 1),
    gsSPLight((const u8*)dAmpUnused3DVtx01, 2),
    gsSPVertex(dAmpUnused3DVtx13, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DElectricDl6[] = {
    //! Vertex interpreted as light
    gsSPLight((const u8*)dAmpUnused3DVtx01 + 0x8, 1),
    gsSPLight((const u8*)dAmpUnused3DVtx01, 2),
    gsSPVertex(dAmpUnused3DVtx14, 3, 0),
    gsSP1Triangle( 0,  1,  2, 0x0),
    gsSPEndDisplayList(),
};

UNUSED const Gfx dAmpUnused3DElectricDl7[] = {
    //! Vertex interpreted as light
    gsSPLight((const u8*)dAmpUnused3DVtx01 + 0x8, 1),
    gsSPLight((const u8*)dAmpUnused3DVtx01, 2),
    gsSPVertex(dAmpUnused3DVtx15, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};
