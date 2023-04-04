#include <ultra64.h>
#include "sm64.h"

#include "make_const_nonconst.h"

// 0x0A000000 - 0x0A000100
static const Vtx title_screen_bg_vertex_0A000000[] = {
    {{{     0,     60,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     60,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     80,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     80,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     40,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     40,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     60,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     60,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     20,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     20,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     40,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     40,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,      0,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,      0,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     20,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     20,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x0A000100 - 0x0A000118
const Gfx title_screen_bg_dl_0A000100[] = {
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsDPSetRenderMode(G_RM_AA_OPA_SURF, G_RM_AA_OPA_SURF2),
    gsSPEndDisplayList(),
};

// 0x0A000118 - 0x0A000130
const Gfx title_screen_bg_dl_0A000118[] = {
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsSPVertex(title_screen_bg_vertex_0A000000, 16, 0),
    gsSPEndDisplayList(),
};

// 0x0A000130 - 0x0A000148
const Gfx title_screen_bg_dl_0A000130[] = {
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPEndDisplayList(),
};

// 0x0A000148 - 0x0A000160
const Gfx title_screen_bg_dl_0A000148[] = {
    gsSP2Triangles( 4,  5,  6, 0x0,  4,  6,  7, 0x0),
    gsSPEndDisplayList(),
};

// 0x0A000160 - 0x0A000178
const Gfx title_screen_bg_dl_0A000160[] = {
    gsSP2Triangles( 8,  9, 10, 0x0,  8, 10, 11, 0x0),
    gsSPEndDisplayList(),
};

// 0x0A000178 - 0x0A000190
const Gfx title_screen_bg_dl_0A000178[] = {
    gsSP2Triangles(12, 13, 14, 0x0, 12, 14, 15, 0x0),
    gsSPEndDisplayList(),
};

// 0x0A000190 - 0x0A0001C0
const Gfx title_screen_bg_dl_0A000190[] = {
    gsDPPipeSync(),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsSPSetGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsSPEndDisplayList(),
};

// 0x0A0001C0
ALIGNED8 static const Texture title_texture_0A0001C0[] = {
#include "textures/title_screen_bg/title_screen_bg.001C0.rgba16.inc.c"
};

// 0x0A000E40
ALIGNED8 static const Texture title_texture_0A000E40[] = {
#include "textures/title_screen_bg/title_screen_bg.00E40.rgba16.inc.c"
};

// 0x0A001AC0
ALIGNED8 static const Texture title_texture_0A001AC0[] = {
#include "textures/title_screen_bg/title_screen_bg.01AC0.rgba16.inc.c"
};

// 0x0A002740
ALIGNED8 static const Texture title_texture_0A002740[] = {
#include "textures/title_screen_bg/title_screen_bg.02740.rgba16.inc.c"
};

// 0x0A0033C0
ALIGNED8 static const Texture title_texture_0A0033C0[] = {
#include "textures/title_screen_bg/title_screen_bg.033C0.rgba16.inc.c"
};

// 0x0A004040
ALIGNED8 static const Texture title_texture_0A004040[] = {
#include "textures/title_screen_bg/title_screen_bg.04040.rgba16.inc.c"
};

// 0x0A004CC0
ALIGNED8 static const Texture title_texture_0A004CC0[] = {
#include "textures/title_screen_bg/title_screen_bg.04CC0.rgba16.inc.c"
};

// 0x0A005940
ALIGNED8 static const Texture title_texture_0A005940[] = {
#include "textures/title_screen_bg/title_screen_bg.05940.rgba16.inc.c"
};

// 0x0A0065C0
const Texture *const mario_title_texture_table[] = {
    title_texture_0A0001C0, title_texture_0A000E40, title_texture_0A001AC0, title_texture_0A002740,
};

// 0x0A0065D0
const Texture *const game_over_texture_table[] = {
    title_texture_0A0033C0, title_texture_0A004040, title_texture_0A004CC0, title_texture_0A005940,
};

UNUSED static const u64 title_screen_bg_unused_0 = 0;

#ifdef VERSION_SH
const Gfx title_screen_bg_dl_0A0065E8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_COPY),
    gsDPSetTexturePersp(G_TP_NONE),
    gsDPSetTextureFilter(G_TF_POINT),
    gsDPSetRenderMode(G_RM_NOOP, G_RM_NOOP2),
    gsSPEndDisplayList(),
};

const Gfx title_screen_bg_dl_0A006618[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetTexturePersp(G_TP_PERSP),
    gsDPSetTextureFilter(G_TF_BILERP),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsSPEndDisplayList(),
};

ALIGNED8 static const u8 title_texture_rumble_pak[] = {
#include "textures/title_screen_bg/title_screen_bg.06648.rgba16.inc.c"
};

const Gfx title_screen_bg_dl_0A007548[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_COPY),
    gsDPSetTexturePersp(G_TP_NONE),
    gsDPSetTextureFilter(G_TF_POINT),
    gsDPSetRenderMode(G_RM_NOOP, G_RM_NOOP2),
    gsDPSetScissor(G_SC_NON_INTERLACE, 0, 0, 319, 239),
    gsDPLoadTextureTile(title_texture_rumble_pak, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 0, 0, 0, 79, 23, 0, G_TX_NOMIRROR | G_TX_CLAMP, G_TX_NOMIRROR | G_TX_CLAMP, 7, 5, G_TX_NOLOD, G_TX_NOLOD),
    gsSPTextureRectangle(220 << 2, 200 << 2, 299 << 2, 223 << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetTexturePersp(G_TP_PERSP),
    gsDPSetTextureFilter(G_TF_BILERP),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsSPEndDisplayList(),
};
#endif
