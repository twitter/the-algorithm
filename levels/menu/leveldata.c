#include <ultra64.h>
#include "sm64.h"
#include "surface_terrains.h"
#include "moving_texture_macros.h"
#include "level_misc_macros.h"
#include "macro_preset_names.h"
#include "special_preset_names.h"
#include "textures.h"
#ifdef VERSION_EU
#include "text_strings.h"
#endif
#include "make_const_nonconst.h"

// 0x07000000 - 0x07000018
static const Lights1 lights_menu_save_button = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07000018 - 0x07000818
ALIGNED8 static const u8 texture_menu_stone[] = {
#include "levels/menu/main_menu_seg7.00018.rgba16.inc.c"
};

// 0x07000818 - 0x07001018
ALIGNED8 static const u8 texture_menu_dark_stone[] = {
#include "levels/menu/main_menu_seg7.00818.rgba16.inc.c"
};

// 0x07001018 - 0x07002018
ALIGNED8 static const u8 texture_menu_mario_save[] = {
#include "levels/menu/main_menu_seg7.01018.rgba16.inc.c"
};

// 0x07002018 - 0x07003018
ALIGNED8 static const u8 texture_menu_mario_new[] = {
#include "levels/menu/main_menu_seg7.02018.rgba16.inc.c"
};

// 0x07003018 - 0x07003118
static const Vtx vertex_menu_save_button_borders[] = {
    {{{  -163,   -122,      0}, 0, {     0,    990}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{   163,   -122,      0}, 0, {   990,    990}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{  -122,    -81,     30}, 0, {    96,    820}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{   122,    -81,     30}, 0, {   862,    820}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{  -163,   -122,      0}, 0, {     0,    990}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -122,    -81,     30}, 0, {    96,    820}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -163,    122,      0}, 0, {     0,      0}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -122,     81,     30}, 0, {    96,    138}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -122,     81,     30}, 0, {    96,    138}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{   122,     81,     30}, 0, {   862,    138}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{   163,    122,      0}, 0, {   990,      0}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{  -163,    122,      0}, 0, {     0,      0}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{   122,     81,     30}, 0, {   862,    138}, {0x4a, 0x00, 0x66, 0xff}}},
    {{{   122,    -81,     30}, 0, {   862,    820}, {0x4a, 0x00, 0x66, 0xff}}},
    {{{   163,   -122,      0}, 0, {   990,    990}, {0x4a, 0x00, 0x66, 0xff}}},
    {{{   163,    122,      0}, 0, {   990,      0}, {0x4a, 0x00, 0x66, 0xff}}},
};

// 0x07003118 - 0x07003158
static const Vtx vertex_menu_save_button_front[] = {
    {{{   122,     81,     30}, 0, {  2012,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -122,     81,     30}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   122,    -81,     30}, 0, {  2012,    990}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -122,    -81,     30}, 0, {     0,    990}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07003158 - 0x070031A0
static const Gfx dl_tex_block_menu_save_button_base[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x070031A0 - 0x07003218
static const Gfx dl_vertex_menu_save_button_borders[] = {
    gsSPLight(&lights_menu_save_button.l, 1),
    gsSPLight(&lights_menu_save_button.a, 2),
    gsSPVertex(vertex_menu_save_button_borders, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSP2Triangles( 4,  5,  6, 0x0,  5,  7,  6, 0x0),
    gsSP2Triangles( 8,  9, 10, 0x0, 11,  8, 10, 0x0),
    gsSP2Triangles(12, 13, 14, 0x0, 15, 12, 14, 0x0),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x07003218 - 0x07003258
static const Gfx dl_vertex_menu_save_button_front[] = {
    gsSPVertex(vertex_menu_save_button_front, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x07003258 - 0x07003298
static const Vtx vertex_menu_save_button_back[] = {
    {{{   163,   -122,      0}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -163,   -122,      0}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   163,    122,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -163,    122,      0}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07003298 - 0x070032E0
static const Gfx dl_tex_block_menu_save_button_back[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x070032E0 - 0x07003330
static const Gfx dl_vertex_menu_save_button_back[] = {
    gsSPLight(&lights_menu_save_button.l, 1),
    gsSPLight(&lights_menu_save_button.a, 2),
    gsSPVertex(vertex_menu_save_button_back, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x07003330 - 0x07003380
const Gfx dl_menu_mario_save_button_base[] = {
    gsSPDisplayList(dl_tex_block_menu_save_button_base),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_stone),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_vertex_menu_save_button_borders),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_mario_save),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_vertex_menu_save_button_front),
    gsSPEndDisplayList(),
};

// 0x07003380 - 0x070033D0
const Gfx dl_menu_mario_new_button_base[] = {
    gsSPDisplayList(dl_tex_block_menu_save_button_base),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_stone),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_vertex_menu_save_button_borders),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_mario_new),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_vertex_menu_save_button_front),
    gsSPEndDisplayList(),
};

// 0x070033D0 - 0x07003400
const Gfx dl_menu_save_button_back[] = {
    gsSPDisplayList(dl_tex_block_menu_save_button_back),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_dark_stone),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_vertex_menu_save_button_back),
    gsSPEndDisplayList(),
};

// 0x07003400 - 0x07003450
const Gfx dl_menu_save_button_fade_back[] = {
    gsDPPipeSync(),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsSPLight(&lights_menu_save_button.l, 1),
    gsSPLight(&lights_menu_save_button.a, 2),
    gsSPVertex(vertex_menu_save_button_back, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  1,  3,  2, 0x0),
    gsDPPipeSync(),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x07003450 - 0x07003468
static const Lights1 lights_menu_main_button = gdSPDefLights1(
    0x3f, 0x3f, 0x3f,
    0xff, 0xff, 0xff, 0x28, 0x28, 0x28
);

// 0x07003468 - 0x07003468
ALIGNED8 static const u8 texture_menu_erase[] = {
#include "levels/menu/main_menu_seg7.03468.rgba16.inc.c"
};

// 0x07003C68 - 0x07003C68
ALIGNED8 static const u8 texture_menu_copy[] = {
#include "levels/menu/main_menu_seg7.03C68.rgba16.inc.c"
};

// 0x07004468 - 0x07004468
ALIGNED8 static const u8 texture_menu_file[] = {
#include "levels/menu/main_menu_seg7.04468.rgba16.inc.c"
};

// 0x07004C68 - 0x07004C68
ALIGNED8 static const u8 texture_menu_score[] = {
#include "levels/menu/main_menu_seg7.04C68.rgba16.inc.c"
};

// 0x07005468 - 0x07005468
ALIGNED8 static const u8 texture_menu_sound[] = {
#include "levels/menu/main_menu_seg7.05468.rgba16.inc.c"
};

// 0x07005C68 - 0x07005D68
static const Vtx vertex_menu_main_button_group1[] = {
    {{{  -163,   -122,      0}, 0, {   990,      0}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -122,    -81,     30}, 0, {   862,    138}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -163,    122,      0}, 0, {   990,    990}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -143,    102,      0}, 0, {   926,    904}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{  -133,     92,     10}, 0, {   894,    862}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{  -133,    -92,     10}, 0, {   894,     96}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{  -133,     92,     10}, 0, {   894,    862}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   133,    -92,     10}, 0, {    64,     96}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -133,    -92,     10}, 0, {   894,     96}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   133,     92,     10}, 0, {    64,    862}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   133,     92,     10}, 0, {    64,    862}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{  -133,     92,     10}, 0, {   894,    862}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{  -143,    102,      0}, 0, {   926,    904}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{   143,   -102,      0}, 0, {    32,     54}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   133,     92,     10}, 0, {    64,    862}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   143,    102,      0}, 0, {    32,    904}, {0xa7, 0x00, 0xa7, 0xff}}},
};

// 0x07005D68 - 0x07005E68
static const Vtx vertex_menu_main_button_group2[] = {
    {{{   143,   -102,      0}, 0, {    32,     54}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   133,    -92,     10}, 0, {    64,     96}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   133,     92,     10}, 0, {    64,    862}, {0xa7, 0x00, 0xa7, 0xff}}},
    {{{   133,     92,     10}, 0, {    64,    862}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{  -143,    102,      0}, 0, {   926,    904}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{   143,    102,      0}, 0, {    32,    904}, {0x00, 0xa7, 0xa7, 0xff}}},
    {{{  -143,   -102,      0}, 0, {   926,     54}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{   133,    -92,     10}, 0, {    64,     96}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{   143,   -102,      0}, 0, {    32,     54}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{  -133,    -92,     10}, 0, {   894,     96}, {0x00, 0x59, 0xa7, 0xff}}},
    {{{  -143,    102,      0}, 0, {   926,    904}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{  -133,    -92,     10}, 0, {   894,     96}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{  -143,   -102,      0}, 0, {   926,     54}, {0x59, 0x00, 0xa7, 0xff}}},
    {{{   163,    122,      0}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -143,    102,      0}, 0, {   926,    904}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -163,    122,      0}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
};

// 0x07005E68 - 0x07005F48
static const Vtx vertex_menu_main_button_group3[] = {
    {{{   163,    122,      0}, 0, {     0,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   143,    102,      0}, 0, {    32,    904}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -143,    102,      0}, 0, {   926,    904}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   143,   -102,      0}, 0, {    32,     54}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   163,   -122,      0}, 0, {     0,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -163,    122,      0}, 0, {   990,    990}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -143,   -102,      0}, 0, {   926,     54}, {0x00, 0x00, 0x81, 0xff}}},
    {{{  -163,   -122,      0}, 0, {   990,      0}, {0x00, 0x00, 0x81, 0xff}}},
    {{{   163,   -122,      0}, 0, {     0,      0}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{   122,    -81,     30}, 0, {    96,    138}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{  -122,    -81,     30}, 0, {   862,    138}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{  -122,    -81,     30}, 0, {   862,    138}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -122,     81,     30}, 0, {   862,    820}, {0xb6, 0x00, 0x66, 0xff}}},
    {{{  -163,    122,      0}, 0, {   990,    990}, {0xb6, 0x00, 0x66, 0xff}}},
};

// 0x07005F48 - 0x07006038
static const Vtx vertex_menu_main_button_group4[] = {
    {{{  -122,     81,     30}, 0, {   862,    820}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -122,    -81,     30}, 0, {   862,    138}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   122,    -81,     30}, 0, {    96,    138}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{  -163,   -122,      0}, 0, {   990,      0}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{   163,   -122,      0}, 0, {     0,      0}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{  -122,    -81,     30}, 0, {   862,    138}, {0x00, 0xb6, 0x66, 0xff}}},
    {{{  -122,     81,     30}, 0, {   862,    820}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{   122,     81,     30}, 0, {    96,    820}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{   163,    122,      0}, 0, {     0,    990}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{  -163,    122,      0}, 0, {   990,    990}, {0x00, 0x4a, 0x66, 0xff}}},
    {{{   122,     81,     30}, 0, {    96,    820}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{   163,    122,      0}, 0, {     0,    990}, {0x4a, 0x00, 0x66, 0xff}}},
    {{{   122,     81,     30}, 0, {    96,    820}, {0x4a, 0x00, 0x66, 0xff}}},
    {{{   163,   -122,      0}, 0, {     0,      0}, {0x4a, 0x00, 0x66, 0xff}}},
    {{{   122,    -81,     30}, 0, {    96,    138}, {0x4a, 0x00, 0x66, 0xff}}},
};

// 0x07006038 - 0x07006150
static const Gfx dl_vertex_menu_main_button[] = {
    gsSPLight(&lights_menu_main_button.l, 1),
    gsSPLight(&lights_menu_main_button.a, 2),
    gsSPVertex(vertex_menu_main_button_group1, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(vertex_menu_main_button_group2, 16, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  6,  9,  7, 0x0),
    gsSP2Triangles(10, 11, 12, 0x0, 13, 14, 15, 0x0),
    gsSPVertex(vertex_menu_main_button_group3, 14, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  3,  1, 0x0),
    gsSP2Triangles( 0,  4,  3, 0x0,  5,  2,  6, 0x0),
    gsSP2Triangles( 5,  6,  7, 0x0,  6,  3,  4, 0x0),
    gsSP2Triangles( 6,  4,  7, 0x0,  8,  9, 10, 0x0),
    gsSP1Triangle(11, 12, 13, 0x0),
    gsSPVertex(vertex_menu_main_button_group4, 15, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  3,  4,  5, 0x0),
    gsSP2Triangles( 6,  7,  8, 0x0,  9,  6,  8, 0x0),
    gsSP2Triangles(10,  0,  2, 0x0, 11, 12, 13, 0x0),
    gsSP1Triangle(12, 14, 13, 0x0),
    gsSPEndDisplayList(),
};

// 0x07006150 - 0x07006198
static const Gfx dl_tex_block_menu_main_button[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_MODULATERGB, G_CC_MODULATERGB),
    gsSPClearGeometryMode(G_SHADING_SMOOTH),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, 5, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x07006198 - 0x070061C8
static const Gfx dl_menu_main_button[] = {
    gsSPDisplayList(dl_vertex_menu_main_button),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPSetGeometryMode(G_SHADING_SMOOTH),
    gsSPEndDisplayList(),
};

// 0x070061C8 - 0x070061F8
const Gfx dl_menu_erase_button[] = {
    gsSPDisplayList(dl_tex_block_menu_main_button),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_erase),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_menu_main_button),
    gsSPEndDisplayList(),
};

// 0x070061F8 - 0x07006228
const Gfx dl_menu_copy_button[] = {
    gsSPDisplayList(dl_tex_block_menu_main_button),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_copy),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_menu_main_button),
    gsSPEndDisplayList(),
};

// 0x07006228 - 0x07006258
const Gfx dl_menu_file_button[] = {
    gsSPDisplayList(dl_tex_block_menu_main_button),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_file),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_menu_main_button),
    gsSPEndDisplayList(),
};

// 0x07006258 - 0x07006288
const Gfx dl_menu_score_button[] = {
    gsSPDisplayList(dl_tex_block_menu_main_button),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_score),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_menu_main_button),
    gsSPEndDisplayList(),
};

// 0x07006288 - 0x070062B8
const Gfx dl_menu_sound_button[] = {
    gsSPDisplayList(dl_tex_block_menu_main_button),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_sound),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_menu_main_button),
    gsSPEndDisplayList(),
};

// 0x070062B8 - 0x070062E8
const Gfx dl_menu_generic_button[] = {
    gsSPDisplayList(dl_tex_block_menu_main_button),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_stone),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsSPDisplayList(dl_menu_main_button),
    gsSPEndDisplayList(),
};

// 0x070062E8 - 0x07006328
static const Vtx vertex_menu_hand[] = {
    {{{     0,      0,      0}, 0, {     0,   1984}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    32,      0,      0}, 0, {  1984,   1984}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{    32,     32,      0}, 0, {  1984,      0}, {0x00, 0x00, 0x7f, 0xff}}},
    {{{     0,     32,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0xff}}},
};

// 0x07006328 - 0x07006B28
ALIGNED8 static const u8 texture_menu_idle_hand[] = {
#include "levels/menu/main_menu_seg7.06328.rgba16.inc.c"
};

// 0x07006B28 - 0x07007328
ALIGNED8 static const u8 texture_menu_grabbing_hand[] = {
#include "levels/menu/main_menu_seg7.06B28.rgba16.inc.c"
};

// 0x07007328 - 0x070073A0
static const Gfx dl_menu_hand[] = {
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsDPSetRenderMode(G_RM_AA_TEX_EDGE, G_RM_AA_TEX_EDGE2),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 32 * 32 - 1, CALC_DXT(32, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 8, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (32 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPVertex(vertex_menu_hand, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsSPTexture(0x0001, 0x0001, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

// 0x070073A0 - 0x070073B8
const Gfx dl_menu_idle_hand[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_idle_hand),
    gsSPBranchList(dl_menu_hand),
};

// 0x070073B8 - 0x070073D0
const Gfx dl_menu_grabbing_hand[] = {
    gsDPPipeSync(),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_grabbing_hand),
    gsSPBranchList(dl_menu_hand),
};

// 0x070073D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_hu[] = {
#include "levels/menu/main_menu_seg7.073D0.rgba16.inc.c"
};

// 0x070075D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_small_a[] = {
#include "levels/menu/main_menu_seg7.075D0.rgba16.inc.c"
};

// 0x070077D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_i[] = {
#include "levels/menu/main_menu_seg7.077D0.rgba16.inc.c"
};

// 0x070079D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_ru[] = {
#include "levels/menu/main_menu_seg7.079D0.rgba16.inc.c"
};

// 0x07007BD0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_se[] = {
#include "levels/menu/main_menu_seg7.07BD0.rgba16.inc.c"
};

// 0x07007DD0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_re[] = {
#include "levels/menu/main_menu_seg7.07DD0.rgba16.inc.c"
};

// 0x07007FD0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_ku[] = {
#include "levels/menu/main_menu_seg7.07FD0.rgba16.inc.c"
};

// 0x070081D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_to[] = {
#include "levels/menu/main_menu_seg7.081D0.rgba16.inc.c"
};

// 0x070083D0
ALIGNED8 static const u8 texture_menu_hud_char_hiragana_wo[] = {
#include "levels/menu/main_menu_seg7.083D0.rgba16.inc.c"
};

// 0x070085D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_ko[] = {
#include "levels/menu/main_menu_seg7.085D0.rgba16.inc.c"
};

// 0x070087D0
ALIGNED8 static const u8 texture_menu_hud_char_kana_handakuten_pi[] = {
#include "levels/menu/main_menu_seg7.087D0.rgba16.inc.c"
};

// 0x070089D0
ALIGNED8 static const u8 texture_menu_hud_char_choonpu[] = {
#include "levels/menu/main_menu_seg7.089D0.rgba16.inc.c"
};

// 0x07008BD0
ALIGNED8 static const u8 texture_menu_hud_char_hiragana_su[] = {
#include "levels/menu/main_menu_seg7.08BD0.rgba16.inc.c"
};

// 0x07008DD0
ALIGNED8 static const u8 texture_menu_hud_char_hiragana_ru[] = {
#include "levels/menu/main_menu_seg7.08DD0.rgba16.inc.c"
};

// 0x07008FD0
ALIGNED8 static const u8 texture_menu_hud_char_hiragana_ke[] = {
#include "levels/menu/main_menu_seg7.08FD0.rgba16.inc.c"
};

// 0x070091D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_ma[] = {
#include "levels/menu/main_menu_seg7.091D0.rgba16.inc.c"
};

// 0x070093D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_ri[] = {
#include "levels/menu/main_menu_seg7.093D0.rgba16.inc.c"
};

// 0x070095D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_o[] = {
#include "levels/menu/main_menu_seg7.095D0.rgba16.inc.c"
};

// 0x070097D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_su[] = {
#include "levels/menu/main_menu_seg7.097D0.rgba16.inc.c"
};

// 0x070099D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_a[] = {
#include "levels/menu/main_menu_seg7.099D0.rgba16.inc.c"
};

// 0x07009BD0
ALIGNED8 static const u8 texture_menu_hud_char_hiragana_mi[] = {
#include "levels/menu/main_menu_seg7.09BD0.rgba16.inc.c"
};

// 0x07009DD0
ALIGNED8 static const u8 texture_menu_hud_char_hira_dakuten_do[] = {
#include "levels/menu/main_menu_seg7.09DD0.rgba16.inc.c"
};

// 0x07009FD0
ALIGNED8 static const u8 texture_menu_hud_char_hiragana_no[] = {
#include "levels/menu/main_menu_seg7.09FD0.rgba16.inc.c"
};

// 0x0700A1D0
ALIGNED8 static const u8 texture_menu_hud_char_question[] = {
#include "levels/menu/main_menu_seg7.0A1D0.rgba16.inc.c"
};

// 0x0700A3D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_sa[] = {
#include "levels/menu/main_menu_seg7.0A3D0.rgba16.inc.c"
};

// 0x0700A5D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_u[] = {
#include "levels/menu/main_menu_seg7.0A5D0.rgba16.inc.c"
};

// 0x0700A7D0
ALIGNED8 static const u8 texture_menu_hud_char_katakana_n[] = {
#include "levels/menu/main_menu_seg7.0A7D0.rgba16.inc.c"
};

// 0x0700A9D0
ALIGNED8 static const u8 texture_menu_hud_char_kana_dakuten_do[] = {
#include "levels/menu/main_menu_seg7.0A9D0.rgba16.inc.c"
};

// Menu HUD print table, only used in JP
// 0x0700ABD0
const u8 *const menu_hud_lut[] = {
    texture_menu_hud_char_katakana_hu, texture_menu_hud_char_katakana_small_a,         texture_menu_hud_char_katakana_i, texture_menu_hud_char_katakana_ru,
    texture_menu_hud_char_katakana_se,      texture_menu_hud_char_katakana_re,        texture_menu_hud_char_katakana_ku, texture_menu_hud_char_katakana_to,
    texture_menu_hud_char_hiragana_wo,      texture_menu_hud_char_katakana_ko, texture_menu_hud_char_kana_handakuten_pi, texture_menu_hud_char_choonpu,
    texture_menu_hud_char_hiragana_su,      texture_menu_hud_char_hiragana_ru,        texture_menu_hud_char_hiragana_ke, texture_menu_hud_char_katakana_ma,
    texture_menu_hud_char_katakana_ri,       texture_menu_hud_char_katakana_o,        texture_menu_hud_char_katakana_su, texture_menu_hud_char_katakana_a,
    texture_menu_hud_char_hiragana_mi,  texture_menu_hud_char_hira_dakuten_do,        texture_menu_hud_char_hiragana_no, texture_menu_hud_char_question,
    texture_menu_hud_char_katakana_sa,       texture_menu_hud_char_katakana_u,         texture_menu_hud_char_katakana_n, texture_menu_hud_char_kana_dakuten_do,
};

#ifdef VERSION_JP
UNUSED static const u64 menu_unused_0 = 0;

// 0x0700AC48
ALIGNED8 static const u8 texture_menu_font_char_jp_0[] = {
#include "levels/menu/main_menu_seg7.0AC48.ia8.inc.c"
};

// 0x0700AC88
ALIGNED8 static const u8 texture_menu_font_char_jp_1[] = {
#include "levels/menu/main_menu_seg7.0AC88.ia8.inc.c"
};

// 0x0700ACC8
ALIGNED8 static const u8 texture_menu_font_char_jp_2[] = {
#include "levels/menu/main_menu_seg7.0ACC8.ia8.inc.c"
};

// 0x0700AD08
ALIGNED8 static const u8 texture_menu_font_char_jp_3[] = {
#include "levels/menu/main_menu_seg7.0AD08.ia8.inc.c"
};

// 0x0700AD48
ALIGNED8 static const u8 texture_menu_font_char_jp_4[] = {
#include "levels/menu/main_menu_seg7.0AD48.ia8.inc.c"
};

// 0x0700AD88
ALIGNED8 static const u8 texture_menu_font_char_jp_5[] = {
#include "levels/menu/main_menu_seg7.0AD88.ia8.inc.c"
};

// 0x0700ADC8
ALIGNED8 static const u8 texture_menu_font_char_jp_6[] = {
#include "levels/menu/main_menu_seg7.0ADC8.ia8.inc.c"
};

// 0x0700AE08
ALIGNED8 static const u8 texture_menu_font_char_jp_7[] = {
#include "levels/menu/main_menu_seg7.0AE08.ia8.inc.c"
};

// 0x0700AE48
ALIGNED8 static const u8 texture_menu_font_char_jp_8[] = {
#include "levels/menu/main_menu_seg7.0AE48.ia8.inc.c"
};

// 0x0700AE88
ALIGNED8 static const u8 texture_menu_font_char_jp_9[] = {
#include "levels/menu/main_menu_seg7.0AE88.ia8.inc.c"
};

// 0x0700AEC8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_a[] = {
#include "levels/menu/main_menu_seg7.0AEC8.ia8.inc.c"
};

// 0x0700AF08
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_i[] = {
#include "levels/menu/main_menu_seg7.0AF08.ia8.inc.c"
};

// 0x0700AF48
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_u[] = {
#include "levels/menu/main_menu_seg7.0AF48.ia8.inc.c"
};

// 0x0700AF88
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_c[] = {
#include "levels/menu/main_menu_seg7.0AF88.ia8.inc.c"
};

// 0x0700AFC8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_o[] = {
#include "levels/menu/main_menu_seg7.0AFC8.ia8.inc.c"
};

// 0x0700B008
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ka[] = {
#include "levels/menu/main_menu_seg7.0B008.ia8.inc.c"
};

// 0x0700B048
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ki[] = {
#include "levels/menu/main_menu_seg7.0B048.ia8.inc.c"
};

// 0x0700B088
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ku[] = {
#include "levels/menu/main_menu_seg7.0B088.ia8.inc.c"
};

// 0x0700B0C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ke[] = {
#include "levels/menu/main_menu_seg7.0B0C8.ia8.inc.c"
};

// 0x0700B108
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ko[] = {
#include "levels/menu/main_menu_seg7.0B108.ia8.inc.c"
};

// 0x0700B148
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_sa[] = {
#include "levels/menu/main_menu_seg7.0B148.ia8.inc.c"
};

// 0x0700B188
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_shi[] = {
#include "levels/menu/main_menu_seg7.0B188.ia8.inc.c"
};

// 0x0700B1C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_su[] = {
#include "levels/menu/main_menu_seg7.0B1C8.ia8.inc.c"
};

// 0x0700B208
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_se[] = {
#include "levels/menu/main_menu_seg7.0B208.ia8.inc.c"
};

// 0x0700B248
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_so[] = {
#include "levels/menu/main_menu_seg7.0B248.ia8.inc.c"
};

// 0x0700B288
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ta[] = {
#include "levels/menu/main_menu_seg7.0B288.ia8.inc.c"
};

// 0x0700B2C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_chi[] = {
#include "levels/menu/main_menu_seg7.0B2C8.ia8.inc.c"
};

// 0x0700B308
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_tsu[] = {
#include "levels/menu/main_menu_seg7.0B308.ia8.inc.c"
};

// 0x0700B348
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_te[] = {
#include "levels/menu/main_menu_seg7.0B348.ia8.inc.c"
};

// 0x0700B388
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_to[] = {
#include "levels/menu/main_menu_seg7.0B388.ia8.inc.c"
};

// 0x0700B3C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_na[] = {
#include "levels/menu/main_menu_seg7.0B3C8.ia8.inc.c"
};

// 0x0700B408
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ni[] = {
#include "levels/menu/main_menu_seg7.0B408.ia8.inc.c"
};

// 0x0700B448
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_nu[] = {
#include "levels/menu/main_menu_seg7.0B448.ia8.inc.c"
};

// 0x0700B488
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ne[] = {
#include "levels/menu/main_menu_seg7.0B488.ia8.inc.c"
};

// 0x0700B4C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_no[] = {
#include "levels/menu/main_menu_seg7.0B4C8.ia8.inc.c"
};

// 0x0700B508
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ha[] = {
#include "levels/menu/main_menu_seg7.0B508.ia8.inc.c"
};

// 0x0700B548
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_hi[] = {
#include "levels/menu/main_menu_seg7.0B548.ia8.inc.c"
};

// 0x0700B588
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_hu[] = {
#include "levels/menu/main_menu_seg7.0B588.ia8.inc.c"
};

// 0x0700B5C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_he[] = {
#include "levels/menu/main_menu_seg7.0B5C8.ia8.inc.c"
};

// 0x0700B608
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ho[] = {
#include "levels/menu/main_menu_seg7.0B608.ia8.inc.c"
};

// 0x0700B648
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ma[] = {
#include "levels/menu/main_menu_seg7.0B648.ia8.inc.c"
};

// 0x0700B688
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_mi[] = {
#include "levels/menu/main_menu_seg7.0B688.ia8.inc.c"
};

// 0x0700B6C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_mu[] = {
#include "levels/menu/main_menu_seg7.0B6C8.ia8.inc.c"
};

// 0x0700B708
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_me[] = {
#include "levels/menu/main_menu_seg7.0B708.ia8.inc.c"
};

// 0x0700B748
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_mo[] = {
#include "levels/menu/main_menu_seg7.0B748.ia8.inc.c"
};

// 0x0700B788
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ya[] = {
#include "levels/menu/main_menu_seg7.0B788.ia8.inc.c"
};

// 0x0700B7C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_yu[] = {
#include "levels/menu/main_menu_seg7.0B7C8.ia8.inc.c"
};

// 0x0700B808
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_yo[] = {
#include "levels/menu/main_menu_seg7.0B808.ia8.inc.c"
};

// 0x0700B848
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ra[] = {
#include "levels/menu/main_menu_seg7.0B848.ia8.inc.c"
};

// 0x0700B888
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ri[] = {
#include "levels/menu/main_menu_seg7.0B888.ia8.inc.c"
};

// 0x0700B8C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ru[] = {
#include "levels/menu/main_menu_seg7.0B8C8.ia8.inc.c"
};

// 0x0700B908
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_re[] = {
#include "levels/menu/main_menu_seg7.0B908.ia8.inc.c"
};

// 0x0700B948
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_ro[] = {
#include "levels/menu/main_menu_seg7.0B948.ia8.inc.c"
};

// 0x0700B988
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_wa[] = {
#include "levels/menu/main_menu_seg7.0B988.ia8.inc.c"
};

// 0x0700B9C8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_wo[] = {
#include "levels/menu/main_menu_seg7.0B9C8.ia8.inc.c"
};

// 0x0700BA08
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_n[] = {
#include "levels/menu/main_menu_seg7.0BA08.ia8.inc.c"
};

// 0x0700BA48
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_a[] = {
#include "levels/menu/main_menu_seg7.0BA48.ia8.inc.c"
};

// 0x0700BA88
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_i[] = {
#include "levels/menu/main_menu_seg7.0BA88.ia8.inc.c"
};

// 0x0700BAC8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_u[] = {
#include "levels/menu/main_menu_seg7.0BAC8.ia8.inc.c"
};

// 0x0700BB08
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_e[] = {
#include "levels/menu/main_menu_seg7.0BB08.ia8.inc.c"
};

// 0x0700BB48
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_o[] = {
#include "levels/menu/main_menu_seg7.0BB48.ia8.inc.c"
};

// 0x0700BB88
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_ka[] = {
#include "levels/menu/main_menu_seg7.0BB88.ia8.inc.c"
};

// 0x0700BBC8
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_yu[] = {
#include "levels/menu/main_menu_seg7.0BBC8.ia8.inc.c"
};

// 0x0700BC08
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_yo[] = {
#include "levels/menu/main_menu_seg7.0BC08.ia8.inc.c"
};

// 0x0700BC48
ALIGNED8 static const u8 texture_menu_font_char_jp_hiragana_small_tsu[] = {
#include "levels/menu/main_menu_seg7.0BC48.ia8.inc.c"
};

// 0x0700BC88
ALIGNED8 static const u8 texture_menu_font_char_jp_handakuten[] = {
#include "levels/menu/main_menu_seg7.0BC88.ia8.inc.c"
};

// 0x0700BCC8
ALIGNED8 static const u8 texture_menu_font_char_jp_dakuten[] = {
#include "levels/menu/main_menu_seg7.0BCC8.ia8.inc.c"
};

// 0x0700BD08
ALIGNED8 static const u8 texture_menu_font_char_jp_long_vowel[] = {
#include "levels/menu/main_menu_seg7.0BD08.ia8.inc.c"
};

// 0x0700BD48
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_a[] = {
#include "levels/menu/main_menu_seg7.0BD48.ia8.inc.c"
};

// 0x0700BD88
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_i[] = {
#include "levels/menu/main_menu_seg7.0BD88.ia8.inc.c"
};

// 0x0700BDC8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_u[] = {
#include "levels/menu/main_menu_seg7.0BDC8.ia8.inc.c"
};

// 0x0700BE08
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_e[] = {
#include "levels/menu/main_menu_seg7.0BE08.ia8.inc.c"
};

// 0x0700BE48
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_o[] = {
#include "levels/menu/main_menu_seg7.0BE48.ia8.inc.c"
};

// 0x0700BE88
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ka[] = {
#include "levels/menu/main_menu_seg7.0BE88.ia8.inc.c"
};

// 0x0700BEC8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ki[] = {
#include "levels/menu/main_menu_seg7.0BEC8.ia8.inc.c"
};

// 0x0700BF08
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ku[] = {
#include "levels/menu/main_menu_seg7.0BF08.ia8.inc.c"
};

// 0x0700BF48
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ke[] = {
#include "levels/menu/main_menu_seg7.0BF48.ia8.inc.c"
};

// 0x0700BF88
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ko[] = {
#include "levels/menu/main_menu_seg7.0BF88.ia8.inc.c"
};

// 0x0700BFC8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_sa[] = {
#include "levels/menu/main_menu_seg7.0BFC8.ia8.inc.c"
};

// 0x0700C008
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_shi[] = {
#include "levels/menu/main_menu_seg7.0C008.ia8.inc.c"
};

// 0x0700C048
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_su[] = {
#include "levels/menu/main_menu_seg7.0C048.ia8.inc.c"
};

// 0x0700C088
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_se[] = {
#include "levels/menu/main_menu_seg7.0C088.ia8.inc.c"
};

// 0x0700C0C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_so[] = {
#include "levels/menu/main_menu_seg7.0C0C8.ia8.inc.c"
};

// 0x0700C108
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ta[] = {
#include "levels/menu/main_menu_seg7.0C108.ia8.inc.c"
};

// 0x0700C148
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_chi[] = {
#include "levels/menu/main_menu_seg7.0C148.ia8.inc.c"
};

// 0x0700C188
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_tsu[] = {
#include "levels/menu/main_menu_seg7.0C188.ia8.inc.c"
};

// 0x0700C1C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_te[] = {
#include "levels/menu/main_menu_seg7.0C1C8.ia8.inc.c"
};

// 0x0700C208
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_to[] = {
#include "levels/menu/main_menu_seg7.0C208.ia8.inc.c"
};

// 0x0700C248
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_na[] = {
#include "levels/menu/main_menu_seg7.0C248.ia8.inc.c"
};

// 0x0700C288
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ni[] = {
#include "levels/menu/main_menu_seg7.0C288.ia8.inc.c"
};

// 0x0700C2C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_nu[] = {
#include "levels/menu/main_menu_seg7.0C2C8.ia8.inc.c"
};

// 0x0700C308
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ne[] = {
#include "levels/menu/main_menu_seg7.0C308.ia8.inc.c"
};

// 0x0700C348
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_no[] = {
#include "levels/menu/main_menu_seg7.0C348.ia8.inc.c"
};

// 0x0700C388
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ha[] = {
#include "levels/menu/main_menu_seg7.0C388.ia8.inc.c"
};

// 0x0700C3C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_hi[] = {
#include "levels/menu/main_menu_seg7.0C3C8.ia8.inc.c"
};

// 0x0700C408
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_hu[] = {
#include "levels/menu/main_menu_seg7.0C408.ia8.inc.c"
};

// 0x0700C448
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_he[] = {
#include "levels/menu/main_menu_seg7.0C448.ia8.inc.c"
};

// 0x0700C488
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ho[] = {
#include "levels/menu/main_menu_seg7.0C488.ia8.inc.c"
};

// 0x0700C4C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ma[] = {
#include "levels/menu/main_menu_seg7.0C4C8.ia8.inc.c"
};

// 0x0700C508
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_mi[] = {
#include "levels/menu/main_menu_seg7.0C508.ia8.inc.c"
};

// 0x0700C548
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_mu[] = {
#include "levels/menu/main_menu_seg7.0C548.ia8.inc.c"
};

// 0x0700C588
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_me[] = {
#include "levels/menu/main_menu_seg7.0C588.ia8.inc.c"
};

// 0x0700C5C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_mo[] = {
#include "levels/menu/main_menu_seg7.0C5C8.ia8.inc.c"
};

// 0x0700C608
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ya[] = {
#include "levels/menu/main_menu_seg7.0C608.ia8.inc.c"
};

// 0x0700C648
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_yu[] = {
#include "levels/menu/main_menu_seg7.0C648.ia8.inc.c"
};

// 0x0700C688
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_yo[] = {
#include "levels/menu/main_menu_seg7.0C688.ia8.inc.c"
};

// 0x0700C6C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ra[] = {
#include "levels/menu/main_menu_seg7.0C6C8.ia8.inc.c"
};

// 0x0700C708
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ri[] = {
#include "levels/menu/main_menu_seg7.0C708.ia8.inc.c"
};

// 0x0700C748
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ru[] = {
#include "levels/menu/main_menu_seg7.0C748.ia8.inc.c"
};

// 0x0700C788
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_re[] = {
#include "levels/menu/main_menu_seg7.0C788.ia8.inc.c"
};

// 0x0700C7C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_ro[] = {
#include "levels/menu/main_menu_seg7.0C7C8.ia8.inc.c"
};

// 0x0700C808
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_wa[] = {
#include "levels/menu/main_menu_seg7.0C808.ia8.inc.c"
};

// 0x0700C848
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_wo[] = {
#include "levels/menu/main_menu_seg7.0C848.ia8.inc.c"
};

// 0x0700C888
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_n[] = {
#include "levels/menu/main_menu_seg7.0C888.ia8.inc.c"
};

// 0x0700C8C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_a[] = {
#include "levels/menu/main_menu_seg7.0C8C8.ia8.inc.c"
};

// 0x0700C908
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_i[] = {
#include "levels/menu/main_menu_seg7.0C908.ia8.inc.c"
};

// 0x0700C948
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_u[] = {
#include "levels/menu/main_menu_seg7.0C948.ia8.inc.c"
};

// 0x0700C988
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_e[] = {
#include "levels/menu/main_menu_seg7.0C988.ia8.inc.c"
};

// 0x0700C9C8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_o[] = {
#include "levels/menu/main_menu_seg7.0C9C8.ia8.inc.c"
};

// 0x0700CA08
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_ka[] = {
#include "levels/menu/main_menu_seg7.0CA08.ia8.inc.c"
};

// 0x0700CA48
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_yu[] = {
#include "levels/menu/main_menu_seg7.0CA48.ia8.inc.c"
};

// 0x0700CA88
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_yo[] = {
#include "levels/menu/main_menu_seg7.0CA88.ia8.inc.c"
};

// 0x0700CAC8
ALIGNED8 static const u8 texture_menu_font_char_jp_katakana_small_tsu[] = {
#include "levels/menu/main_menu_seg7.0CAC8.ia8.inc.c"
};

// 0x0700CB08
ALIGNED8 static const u8 texture_menu_font_char_jp_A[] = {
#include "levels/menu/main_menu_seg7.0CB08.ia8.inc.c"
};

// 0x0700CB48
ALIGNED8 static const u8 texture_menu_font_char_jp_B[] = {
#include "levels/menu/main_menu_seg7.0CB48.ia8.inc.c"
};

// 0x0700CB88
ALIGNED8 static const u8 texture_menu_font_char_jp_C[] = {
#include "levels/menu/main_menu_seg7.0CB88.ia8.inc.c"
};

// 0x0700CBC8
ALIGNED8 static const u8 texture_menu_font_char_jp_D[] = {
#include "levels/menu/main_menu_seg7.0CBC8.ia8.inc.c"
};

// 0x0700CC08
ALIGNED8 static const u8 texture_menu_font_char_jp_coin[] = {
#include "levels/menu/main_menu_seg7.0CC08.ia8.inc.c"
};

// 0x0700CC48
ALIGNED8 static const u8 texture_menu_font_char_jp_star_filled[] = {
#include "levels/menu/main_menu_seg7.0CC48.ia8.inc.c"
};

// 0x0700CC88
ALIGNED8 static const u8 texture_menu_font_char_jp_multiply[] = {
#include "levels/menu/main_menu_seg7.0CC88.ia8.inc.c"
};

// 0x0700CCC8
ALIGNED8 static const u8 texture_menu_font_char_jp_exclamation[] = {
#include "levels/menu/main_menu_seg7.0CCC8.ia8.inc.c"
};

#else

// 0x0700AC40
ALIGNED8 static const u8 texture_menu_font_char_0[] = {
#include "levels/menu/main_menu_seg7_us.0AC40.ia8.inc.c"
};

// 0x0700AC80
ALIGNED8 static const u8 texture_menu_font_char_1[] = {
#include "levels/menu/main_menu_seg7_us.0AC80.ia8.inc.c"
};

// 0x0700ACC0
ALIGNED8 static const u8 texture_menu_font_char_2[] = {
#include "levels/menu/main_menu_seg7_us.0ACC0.ia8.inc.c"
};

// 0x0700AD00
ALIGNED8 static const u8 texture_menu_font_char_3[] = {
#include "levels/menu/main_menu_seg7_us.0AD00.ia8.inc.c"
};

// 0x0700AD40
ALIGNED8 static const u8 texture_menu_font_char_4[] = {
#include "levels/menu/main_menu_seg7_us.0AD40.ia8.inc.c"
};

// 0x0700AD80
ALIGNED8 static const u8 texture_menu_font_char_5[] = {
#include "levels/menu/main_menu_seg7_us.0AD80.ia8.inc.c"
};

// 0x0700ADC0
ALIGNED8 static const u8 texture_menu_font_char_6[] = {
#include "levels/menu/main_menu_seg7_us.0ADC0.ia8.inc.c"
};

// 0x0700AE00
ALIGNED8 static const u8 texture_menu_font_char_7[] = {
#include "levels/menu/main_menu_seg7_us.0AE00.ia8.inc.c"
};

// 0x0700AE40
ALIGNED8 static const u8 texture_menu_font_char_8[] = {
#include "levels/menu/main_menu_seg7_us.0AE40.ia8.inc.c"
};

// 0x0700AE80
ALIGNED8 static const u8 texture_menu_font_char_9[] = {
#include "levels/menu/main_menu_seg7_us.0AE80.ia8.inc.c"
};

// 0x0700AEC0
ALIGNED8 static const u8 texture_menu_font_char_A[] = {
#include "levels/menu/main_menu_seg7_us.0AEC0.ia8.inc.c"
};

// 0x0700AF00
ALIGNED8 static const u8 texture_menu_font_char_B[] = {
#include "levels/menu/main_menu_seg7_us.0AF00.ia8.inc.c"
};

// 0x0700AF40
ALIGNED8 static const u8 texture_menu_font_char_C[] = {
#include "levels/menu/main_menu_seg7_us.0AF40.ia8.inc.c"
};

#ifdef VERSION_EU
// 0x0700AF80
ALIGNED8 static const u8 texture_menu_font_char_D[] = {
#include "levels/menu/main_menu_seg7_eu.0AF80.ia8.inc.c"
};

#else

// 0x0700AF80
ALIGNED8 static const u8 texture_menu_font_char_D[] = {
#include "levels/menu/main_menu_seg7_us.0AF80.ia8.inc.c"
};
#endif

// 0x0700AFC0
ALIGNED8 static const u8 texture_menu_font_char_E[] = {
#include "levels/menu/main_menu_seg7_us.0AFC0.ia8.inc.c"
};

// 0x0700B000
ALIGNED8 static const u8 texture_menu_font_char_F[] = {
#include "levels/menu/main_menu_seg7_us.0B000.ia8.inc.c"
};

// 0x0700B040
ALIGNED8 static const u8 texture_menu_font_char_G[] = {
#include "levels/menu/main_menu_seg7_us.0B040.ia8.inc.c"
};

// 0x0700B080
ALIGNED8 static const u8 texture_menu_font_char_H[] = {
#include "levels/menu/main_menu_seg7_us.0B080.ia8.inc.c"
};

// 0x0700B0C0
ALIGNED8 static const u8 texture_menu_font_char_I[] = {
#include "levels/menu/main_menu_seg7_us.0B0C0.ia8.inc.c"
};

// 0x0700B100
ALIGNED8 static const u8 texture_menu_font_char_J[] = {
#include "levels/menu/main_menu_seg7_us.0B100.ia8.inc.c"
};

// 0x0700B140
ALIGNED8 static const u8 texture_menu_font_char_K[] = {
#include "levels/menu/main_menu_seg7_us.0B140.ia8.inc.c"
};

// 0x0700B180
ALIGNED8 static const u8 texture_menu_font_char_L[] = {
#include "levels/menu/main_menu_seg7_us.0B180.ia8.inc.c"
};

// 0x0700B1C0
ALIGNED8 static const u8 texture_menu_font_char_M[] = {
#include "levels/menu/main_menu_seg7_us.0B1C0.ia8.inc.c"
};

// 0x0700B200
ALIGNED8 static const u8 texture_menu_font_char_N[] = {
#include "levels/menu/main_menu_seg7_us.0B200.ia8.inc.c"
};

// 0x0700B240
ALIGNED8 static const u8 texture_menu_font_char_O[] = {
#include "levels/menu/main_menu_seg7_us.0B240.ia8.inc.c"
};

// 0x0700B280
ALIGNED8 static const u8 texture_menu_font_char_P[] = {
#include "levels/menu/main_menu_seg7_us.0B280.ia8.inc.c"
};

// 0x0700B2C0
ALIGNED8 static const u8 texture_menu_font_char_Q[] = {
#include "levels/menu/main_menu_seg7_us.0B2C0.ia8.inc.c"
};

// 0x0700B300
ALIGNED8 static const u8 texture_menu_font_char_R[] = {
#include "levels/menu/main_menu_seg7_us.0B300.ia8.inc.c"
};

// 0x0700B340
ALIGNED8 static const u8 texture_menu_font_char_S[] = {
#include "levels/menu/main_menu_seg7_us.0B340.ia8.inc.c"
};

// 0x0700B380
ALIGNED8 static const u8 texture_menu_font_char_T[] = {
#include "levels/menu/main_menu_seg7_us.0B380.ia8.inc.c"
};

// 0x0700B3C0
ALIGNED8 static const u8 texture_menu_font_char_U[] = {
#include "levels/menu/main_menu_seg7_us.0B3C0.ia8.inc.c"
};

// 0x0700B400
ALIGNED8 static const u8 texture_menu_font_char_V[] = {
#include "levels/menu/main_menu_seg7_us.0B400.ia8.inc.c"
};

// 0x0700B440
ALIGNED8 static const u8 texture_menu_font_char_W[] = {
#include "levels/menu/main_menu_seg7_us.0B440.ia8.inc.c"
};

// 0x0700B480
ALIGNED8 static const u8 texture_menu_font_char_X[] = {
#include "levels/menu/main_menu_seg7_us.0B480.ia8.inc.c"
};

// 0x0700B4C0
ALIGNED8 static const u8 texture_menu_font_char_Y[] = {
#include "levels/menu/main_menu_seg7_us.0B4C0.ia8.inc.c"
};

// 0x0700B500
ALIGNED8 static const u8 texture_menu_font_char_Z[] = {
#include "levels/menu/main_menu_seg7_us.0B500.ia8.inc.c"
};

// 0x0700B540
ALIGNED8 static const u8 texture_menu_font_char_coin[] = {
#include "levels/menu/main_menu_seg7_us.0B540.ia8.inc.c"
};

// 0x0700B580
ALIGNED8 static const u8 texture_menu_font_char_multiply[] = {
#include "levels/menu/main_menu_seg7_us.0B580.ia8.inc.c"
};

// 0x0700B5C0
ALIGNED8 static const u8 texture_menu_font_char_star_filled[] = {
#include "levels/menu/main_menu_seg7_us.0B5C0.ia8.inc.c"
};

// 0x0700B600
ALIGNED8 static const u8 texture_menu_font_char_dash[] = {
#include "levels/menu/main_menu_seg7_us.0B600.ia8.inc.c"
};

#ifdef VERSION_EU
// 0x0700B640
ALIGNED8 static const u8 texture_menu_font_char_comma[] = {
#include "levels/menu/main_menu_seg7_eu.0B640.ia8.inc.c"
};

// 0x0700B680
ALIGNED8 static const u8 texture_menu_font_char_apostrophe[] = {
#include "levels/menu/main_menu_seg7_eu.0B680.ia8.inc.c"
};

#else

// 0x0700B640
ALIGNED8 static const u8 texture_menu_font_char_comma[] = {
#include "levels/menu/main_menu_seg7_us.0B640.ia8.inc.c"
};

// 0x0700B680
ALIGNED8 static const u8 texture_menu_font_char_apostrophe[] = {
#include "levels/menu/main_menu_seg7_us.0B680.ia8.inc.c"
};
#endif

// 0x0700B6C0
ALIGNED8 static const u8 texture_menu_font_char_exclamation[] = {
#include "levels/menu/main_menu_seg7_us.0B6C0.ia8.inc.c"
};

// 0x0700B700
ALIGNED8 static const u8 texture_menu_font_char_question[] = {
#include "levels/menu/main_menu_seg7_us.0B700.ia8.inc.c"
};

// 0x0700B740
ALIGNED8 static const u8 texture_menu_font_char_mface1[] = {
#include "levels/menu/main_menu_seg7_us.0B740.ia8.inc.c"
};

// 0x0700B780
ALIGNED8 static const u8 texture_menu_font_char_mface2[] = {
#include "levels/menu/main_menu_seg7_us.0B780.ia8.inc.c"
};

// 0x0700B7C0
ALIGNED8 static const u8 texture_menu_font_char_period[] = {
#include "levels/menu/main_menu_seg7_us.0B7C0.ia8.inc.c"
};

// 0x0700B800
ALIGNED8 static const u8 texture_menu_font_char_ampersand[] = {
#include "levels/menu/main_menu_seg7_us.0B800.ia8.inc.c"
};
#endif

#ifdef VERSION_EU
// 0x0700B840
ALIGNED8 static const u8 texture_menu_font_char_umlaut[] = {
#include "levels/menu/main_menu_seg7_eu.0B840.ia8.inc.c"
};

// 0x0700B880
ALIGNED8 static const u8 texture_menu_font_char_cedilla_mayus[] = {
#include "levels/menu/main_menu_seg7_eu.0B880.ia8.inc.c"
};

// 0x0700B8C0
ALIGNED8 static const u8 texture_menu_font_char_colon[] = {
#include "levels/menu/main_menu_seg7_eu.0B8C0.ia8.inc.c"
};
#endif

// Menu small font print table
// 0x0700CD08
const u8 *const menu_font_lut[] = {
#ifdef VERSION_JP
    texture_menu_font_char_jp_0, texture_menu_font_char_jp_1, texture_menu_font_char_jp_2, texture_menu_font_char_jp_3,
    texture_menu_font_char_jp_4, texture_menu_font_char_jp_5, texture_menu_font_char_jp_6, texture_menu_font_char_jp_7,
    texture_menu_font_char_jp_8, texture_menu_font_char_jp_9, texture_menu_font_char_jp_A, texture_menu_font_char_jp_B,
    texture_menu_font_char_jp_C, texture_menu_font_char_jp_D,                   0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
                              0x0,                      0x0,                      0x0,                      0x0,
    texture_menu_font_char_jp_hiragana_a, texture_menu_font_char_jp_hiragana_i, texture_menu_font_char_jp_hiragana_u, texture_menu_font_char_jp_hiragana_c,
    texture_menu_font_char_jp_hiragana_o, texture_menu_font_char_jp_hiragana_ka, texture_menu_font_char_jp_hiragana_ki, texture_menu_font_char_jp_hiragana_ku,
    texture_menu_font_char_jp_hiragana_ke, texture_menu_font_char_jp_hiragana_ko, texture_menu_font_char_jp_hiragana_sa, texture_menu_font_char_jp_hiragana_shi,
    texture_menu_font_char_jp_hiragana_su, texture_menu_font_char_jp_hiragana_se, texture_menu_font_char_jp_hiragana_so, texture_menu_font_char_jp_hiragana_ta,
    texture_menu_font_char_jp_hiragana_chi, texture_menu_font_char_jp_hiragana_tsu, texture_menu_font_char_jp_hiragana_te, texture_menu_font_char_jp_hiragana_to,
    texture_menu_font_char_jp_hiragana_na, texture_menu_font_char_jp_hiragana_ni, texture_menu_font_char_jp_hiragana_nu, texture_menu_font_char_jp_hiragana_ne,
    texture_menu_font_char_jp_hiragana_no, texture_menu_font_char_jp_hiragana_ha, texture_menu_font_char_jp_hiragana_hi, texture_menu_font_char_jp_hiragana_hu,
    texture_menu_font_char_jp_hiragana_he, texture_menu_font_char_jp_hiragana_ho, texture_menu_font_char_jp_hiragana_ma, texture_menu_font_char_jp_hiragana_mi,
    texture_menu_font_char_jp_hiragana_mu, texture_menu_font_char_jp_hiragana_me, texture_menu_font_char_jp_hiragana_mo, texture_menu_font_char_jp_hiragana_ya,
    texture_menu_font_char_jp_hiragana_yu, texture_menu_font_char_jp_hiragana_yo, texture_menu_font_char_jp_hiragana_ra, texture_menu_font_char_jp_hiragana_ri,
    texture_menu_font_char_jp_hiragana_ru, texture_menu_font_char_jp_hiragana_re, texture_menu_font_char_jp_hiragana_ro, texture_menu_font_char_jp_hiragana_wa,
    texture_menu_font_char_jp_hiragana_wo, texture_menu_font_char_jp_hiragana_n, 0x0,                                0x0,
    texture_menu_font_char_jp_katakana_a, texture_menu_font_char_jp_katakana_i, texture_menu_font_char_jp_katakana_u, texture_menu_font_char_jp_katakana_e,
    texture_menu_font_char_jp_katakana_o, texture_menu_font_char_jp_katakana_ka, texture_menu_font_char_jp_katakana_ki, texture_menu_font_char_jp_katakana_ku,
    texture_menu_font_char_jp_katakana_ke, texture_menu_font_char_jp_katakana_ko, texture_menu_font_char_jp_katakana_sa, texture_menu_font_char_jp_katakana_shi,
    texture_menu_font_char_jp_katakana_su, texture_menu_font_char_jp_katakana_se, texture_menu_font_char_jp_katakana_so, texture_menu_font_char_jp_katakana_ta,
    texture_menu_font_char_jp_katakana_chi, texture_menu_font_char_jp_katakana_tsu, texture_menu_font_char_jp_katakana_te, texture_menu_font_char_jp_katakana_to,
    texture_menu_font_char_jp_katakana_na, texture_menu_font_char_jp_katakana_ni, texture_menu_font_char_jp_katakana_nu, texture_menu_font_char_jp_katakana_ne,
    texture_menu_font_char_jp_katakana_no, texture_menu_font_char_jp_katakana_ha, texture_menu_font_char_jp_katakana_hi, texture_menu_font_char_jp_katakana_hu,
    texture_menu_font_char_jp_katakana_he, texture_menu_font_char_jp_katakana_ho, texture_menu_font_char_jp_katakana_ma, texture_menu_font_char_jp_katakana_mi,
    texture_menu_font_char_jp_katakana_mu, texture_menu_font_char_jp_katakana_me, texture_menu_font_char_jp_katakana_mo, texture_menu_font_char_jp_katakana_ya,
    texture_menu_font_char_jp_katakana_yu, texture_menu_font_char_jp_katakana_yo, texture_menu_font_char_jp_katakana_ra, texture_menu_font_char_jp_katakana_ri,
    texture_menu_font_char_jp_katakana_ru, texture_menu_font_char_jp_katakana_re, texture_menu_font_char_jp_katakana_ro, texture_menu_font_char_jp_katakana_wa,
    texture_menu_font_char_jp_katakana_wo, texture_menu_font_char_jp_katakana_n,                                   0x0, texture_menu_font_char_jp_long_vowel,
    texture_menu_font_char_jp_hiragana_small_e, texture_menu_font_char_jp_hiragana_small_tsu, texture_menu_font_char_jp_hiragana_small_ka, texture_menu_font_char_jp_hiragana_small_yu,
    texture_menu_font_char_jp_hiragana_small_yo, texture_menu_font_char_jp_hiragana_small_a, texture_menu_font_char_jp_hiragana_small_i, texture_menu_font_char_jp_hiragana_small_u,
    texture_menu_font_char_jp_hiragana_small_o,          0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    texture_menu_font_char_jp_katakana_small_e, texture_menu_font_char_jp_katakana_small_tsu, texture_menu_font_char_jp_katakana_small_ka, texture_menu_font_char_jp_katakana_small_yu,
    texture_menu_font_char_jp_katakana_small_yo, texture_menu_font_char_jp_katakana_small_a, texture_menu_font_char_jp_katakana_small_i, texture_menu_font_char_jp_katakana_small_u,
    texture_menu_font_char_jp_katakana_small_o, 0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    texture_menu_font_char_jp_dakuten, texture_menu_font_char_jp_handakuten, texture_menu_font_char_jp_exclamation, 0x0,
    0x0,                      0x0,                      0x0,                      0x0,
    0x0,                      texture_menu_font_char_jp_coin, texture_menu_font_char_jp_star_filled, texture_menu_font_char_jp_multiply,
    0x0,                      0x0,                      0x0,                      0x0,
#else
    texture_menu_font_char_0, texture_menu_font_char_1, texture_menu_font_char_2, texture_menu_font_char_3,
    texture_menu_font_char_4, texture_menu_font_char_5, texture_menu_font_char_6, texture_menu_font_char_7,
    texture_menu_font_char_8, texture_menu_font_char_9, texture_menu_font_char_A, texture_menu_font_char_B,
    texture_menu_font_char_C, texture_menu_font_char_D, texture_menu_font_char_E, texture_menu_font_char_F,
    texture_menu_font_char_G, texture_menu_font_char_H, texture_menu_font_char_I, texture_menu_font_char_J,
    texture_menu_font_char_K, texture_menu_font_char_L, texture_menu_font_char_M, texture_menu_font_char_N,
    texture_menu_font_char_O, texture_menu_font_char_P, texture_menu_font_char_Q, texture_menu_font_char_R,
    texture_menu_font_char_S, texture_menu_font_char_T, texture_menu_font_char_U, texture_menu_font_char_V,
    texture_menu_font_char_W, texture_menu_font_char_X, texture_menu_font_char_Y, texture_menu_font_char_Z,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0, texture_menu_font_char_apostrophe, texture_menu_font_char_period,
    texture_menu_font_char_mface1, texture_menu_font_char_mface2,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0, texture_menu_font_char_comma,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0, texture_menu_font_char_dash,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
#ifdef VERSION_EU
          0x0, texture_menu_font_char_ampersand, texture_menu_font_char_colon,       0x0,
          0x0, texture_menu_font_char_umlaut,       0x0,       0x0,
          0x0, texture_menu_font_char_cedilla_mayus,       0x0,       0x0,
#else
          0x0, texture_menu_font_char_ampersand,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
          0x0,       0x0,       0x0,       0x0,
#endif
          0x0,       0x0, texture_menu_font_char_exclamation,       0x0,
    texture_menu_font_char_question,       0x0,       0x0,       0x0,
          0x0, texture_menu_font_char_coin, texture_menu_font_char_star_filled, texture_menu_font_char_multiply,
          0x0,       0x0,       0x0,       0x0,
#endif
};

// 0x0700D108 - 0x0700D160
const Gfx dl_menu_ia8_text_begin[] = {
    gsDPPipeSync(),
    gsDPSetTexturePersp(G_TP_NONE),
    gsDPSetCombineMode(G_CC_FADEA, G_CC_FADEA),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsDPSetRenderMode(G_RM_AA_XLU_SURF, G_RM_AA_XLU_SURF2),
    gsDPSetTextureFilter(G_TF_POINT),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_8b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_IA, G_IM_SIZ_8b, 1, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 3, G_TX_NOLOD, G_TX_CLAMP, 3, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (8 - 1) << G_TEXTURE_IMAGE_FRAC, (8 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPEndDisplayList(),
};

// 0x0700D160 - 0x0700D1A0
const Gfx dl_menu_ia8_text_end[] = {
    gsDPPipeSync(),
    gsDPSetTexturePersp(G_TP_PERSP),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetEnvColor(255, 255, 255, 255),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetTextureFilter(G_TF_BILERP),
    gsSPEndDisplayList(),
};

UNUSED static const u64 menu_unused_1 = 0;

#ifdef VERSION_EU

// 0x0700BDA0 - 0x0700CDA0
ALIGNED8 static const u8 texture_menu_course_upper[] = {
#include "levels/menu/main_menu_seg7_eu.0BDA0.rgba16.inc.c"
};

// 0x0700CDA0 - 0x0700DDA0
ALIGNED8 static const u8 texture_menu_niveau_upper[] = {
#include "levels/menu/main_menu_seg7_eu.0CDA0.rgba16.inc.c"
};

// 0x0700DDA0 - 0x0700EDA0
ALIGNED8 static const u8 texture_menu_kurs_upper[] = {
#include "levels/menu/main_menu_seg7_eu.0DDA0.rgba16.inc.c"
};

// 0x0700EDA0 - 0x0700FDA0
ALIGNED8 static const u8 texture_menu_course_lower[] = {
#include "levels/menu/main_menu_seg7_eu.0EDA0.rgba16.inc.c"
};

#else

// 0x0700D1A8 - 0x0700E1A8
ALIGNED8 static const u8 texture_menu_course_upper[] = {
#include "levels/menu/main_menu_seg7.0D1A8.rgba16.inc.c"
};

// 0x0700E1A8 - 0x0700F1A8
ALIGNED8 static const u8 texture_menu_course_lower[] = {
#include "levels/menu/main_menu_seg7.0E1A8.rgba16.inc.c"
};
#endif

// 0x0700F1A8 - 0x0700F1E8
static const Vtx vertex_menu_course_upper[] = {
    {{{   -32,      0,      0}, 0, {     0,   1984}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{    32,      0,      0}, 0, {  4032,   1984}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{    32,     32,      0}, 0, {  4032,      0}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{   -32,     32,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0x00}}},
};

// 0x0700F1E8 - 0x0700F228
static const Vtx vertex_menu_course_lower[] = {
    {{{   -32,    -32,      0}, 0, {     0,   1984}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{    32,    -32,      0}, 0, {  4032,   1984}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{    32,      0,      0}, 0, {  4032,      0}, {0x00, 0x00, 0x7f, 0x00}}},
    {{{   -32,      0,      0}, 0, {     0,      0}, {0x00, 0x00, 0x7f, 0x00}}},
};

// 0x0700F228 - 0x0700F2F8
const Gfx dl_menu_rgba16_wood_course[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGBA, G_CC_DECALRGBA),
    gsSPTexture(0x8000, 0x8000, 0, G_TX_RENDERTILE, G_ON),
    gsDPSetRenderMode(G_RM_AA_TEX_EDGE, G_RM_AA_TEX_EDGE2),
#ifdef VERSION_EU
    gsSPEndDisplayList(),
};
const Gfx dl_menu_rgba16_wood_course_end[] = {
#else
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_course_upper),
#endif
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPVertex(vertex_menu_course_upper, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_course_lower),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsSPVertex(vertex_menu_course_lower, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsSPTexture(0x0001, 0x0001, 0, G_TX_RENDERTILE, G_OFF),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsSPEndDisplayList(),
};

#ifdef VERSION_EU
// 0x0700FEF0 - 0x0700FF00
const Gfx dl_menu_texture_course_upper[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_course_upper),
    gsSPEndDisplayList(),
};

// 0x0700FF00 - 0x0700FF10
const Gfx dl_menu_texture_niveau_upper[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_niveau_upper),
    gsSPEndDisplayList(),
};

// 0x0700FF10 - 0x0700FF20
const Gfx dl_menu_texture_kurs_upper[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, texture_menu_kurs_upper),
    gsSPEndDisplayList(),
};
#endif

// 0x0700F2F8 - 0x0700F328
const Collision main_menu_seg7_collision[] = {
    COL_INIT(),
    COL_VERTEX_INIT(0x4),
    COL_VERTEX( 8192, -1000, -8192),
    COL_VERTEX(-8192, -1000, -8192),
    COL_VERTEX(-8192, -1000,  8192),
    COL_VERTEX( 8192, -1000,  8192),
    COL_TRI_INIT(SURFACE_DEFAULT, 2),
    COL_TRI(0, 1, 2),
    COL_TRI(0, 2, 3),
    COL_TRI_STOP(),
    COL_END(),
};

#ifdef VERSION_EU

// Duplicate course name tables; the main menu needs all languages loaded at
// once since it switches language, so the copies in segment 19 aren't good
// enough.

#define COURSE_TABLE eu_course_strings_en_table
#include "text/us/define_courses.inc.c"
#undef COURSE_TABLE

#define COURSE_TABLE eu_course_strings_fr_table
#include "text/fr/define_courses.inc.c"
#undef COURSE_TABLE

#define COURSE_TABLE eu_course_strings_de_table
#include "text/de/define_courses.inc.c"
#undef COURSE_TABLE

#endif
