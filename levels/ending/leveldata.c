#include <ultra64.h>
#include "sm64.h"
#include "surface_terrains.h"
#include "moving_texture_macros.h"
#include "level_misc_macros.h"
#include "macro_preset_names.h"
#include "special_preset_names.h"
#include "textures.h"

#include "make_const_nonconst.h"

#ifdef VERSION_EU
#include "levels/ending/cake_eu.inc.c"

// 0x07023000 - 0x07023FFF
ALIGNED8 static const u8 cake_end_texture_eu_35[] = {
#include "levels/ending/eu_023000.rgba16.inc.c"
};

// 0x07024000 - 0x07024FFF
ALIGNED8 static const u8 cake_end_texture_eu_36[] = {
#include "levels/ending/eu_024000.rgba16.inc.c"
};

// 0x07025000 - 0x07025FFF
ALIGNED8 static const u8 cake_end_texture_eu_37[] = {
#include "levels/ending/eu_025000.rgba16.inc.c"
};

// 0x07026000 - 0x07026FFF
ALIGNED8 static const u8 cake_end_texture_eu_38[] = {
#include "levels/ending/eu_026000.rgba16.inc.c"
};

// 0x07027000 - 0x07027FFF
ALIGNED8 static const u8 cake_end_texture_eu_39[] = {
#include "levels/ending/eu_027000.rgba16.inc.c"
};

// 0x07028000 - 0x07028FFF
ALIGNED8 static const u8 cake_end_texture_eu_40[] = {
#include "levels/ending/eu_028000.rgba16.inc.c"
};

// 0x07029000 - 0x070296D8
const Gfx dl_cake_end_screen[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_COPY),
    gsDPSetTexturePersp(G_TP_NONE),
    gsDPSetRenderMode(G_RM_NOOP, G_RM_NOOP2),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 0, 0, G_TX_LOADTILE, 0, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD, G_TX_WRAP | G_TX_NOMIRROR, G_TX_NOMASK, G_TX_NOLOD),
    gsDPTileSync(),
    gsDPSetTile(G_IM_FMT_RGBA, G_IM_SIZ_16b, 16, 0, G_TX_RENDERTILE, 0, G_TX_CLAMP, 5, G_TX_NOLOD, G_TX_CLAMP, 6, G_TX_NOLOD),
    gsDPSetTileSize(0, 0, 0, (64 - 1) << G_TEXTURE_IMAGE_FRAC, (32 - 1) << G_TEXTURE_IMAGE_FRAC),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_0),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 8 << 2, (0 + 63) << 2, (8 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_1),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 8 << 2, (64 + 63) << 2, (8 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_2),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 8 << 2, (128 + 63) << 2, (8 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_3),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 8 << 2, (192 + 63) << 2, (8 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_4),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 8 << 2, (256 + 63) << 2, (8 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_5),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 40 << 2, (0 + 63) << 2, (40 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_6),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 40 << 2, (64 + 63) << 2, (40 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_7),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 40 << 2, (128 + 63) << 2, (40 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_8),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 40 << 2, (192 + 63) << 2, (40 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_9),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 40 << 2, (256 + 63) << 2, (40 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_10),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 72 << 2, (0 + 63) << 2, (72 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_11),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 72 << 2, (64 + 63) << 2, (72 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_12),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 72 << 2, (128 + 63) << 2, (72 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_13),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 72 << 2, (192 + 63) << 2, (72 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_14),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 72 << 2, (256 + 63) << 2, (72 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_15),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 104 << 2, (0 + 63) << 2, (104 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_16),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 104 << 2, (64 + 63) << 2, (104 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_17),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 104 << 2, (128 + 63) << 2, (104 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_18),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 104 << 2, (192 + 63) << 2, (104 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_19),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 104 << 2, (256 + 63) << 2, (104 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_20),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 136 << 2, (0 + 63) << 2, (136 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_21),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 136 << 2, (64 + 63) << 2, (136 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_22),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 136 << 2, (128 + 63) << 2, (136 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_23),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 136 << 2, (192 + 63) << 2, (136 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_24),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 136 << 2, (256 + 63) << 2, (136 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_25),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 168 << 2, (0 + 63) << 2, (168 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_26),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 168 << 2, (64 + 63) << 2, (168 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_27),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 168 << 2, (128 + 63) << 2, (168 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_28),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 168 << 2, (192 + 63) << 2, (168 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_29),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 168 << 2, (256 + 63) << 2, (168 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_30),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(0 << 2, 200 << 2, (0 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_31),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(64 << 2, 200 << 2, (64 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_32),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 200 << 2, (128 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_33),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(192 << 2, 200 << 2, (192 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_34),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(256 << 2, 200 << 2, (256 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsSPEndDisplayList(),
};

// 0x070296D8 - 0x070296F8
static const Gfx dl_cake_end_eu_070296D8[] = {
    gsDPPipeSync(),
    gsDPSetCycleType(G_CYC_1CYCLE),
    gsDPSetTexturePersp(G_TP_PERSP),
    gsSPEndDisplayList(),
};

// 0x070296F8 - 0x07029768
const Gfx dl_cake_end_screen_eu_070296F8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_38),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 200 << 2, (128 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_35),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 125 << 2, (128 + 63) << 2, (125 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsSPDisplayList(dl_cake_end_eu_070296D8),
    gsSPEndDisplayList(),
};

// 0x07029768 - 0x070297D8
const Gfx dl_cake_end_screen_eu_07029768[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_39),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 200 << 2, (128 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_36),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 125 << 2, (128 + 63) << 2, (125 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsSPDisplayList(dl_cake_end_eu_070296D8),
    gsSPEndDisplayList(),
};

// 0x070297D8 - 0x07029848
const Gfx dl_cake_end_screen_eu_070297D8[] = {
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_40),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 200 << 2, (128 + 63) << 2, (200 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsDPSetTextureImage(G_IM_FMT_RGBA, G_IM_SIZ_16b, 1, cake_end_texture_eu_37),
    gsDPLoadSync(),
    gsDPLoadBlock(G_TX_LOADTILE, 0, 0, 64 * 32 - 1, CALC_DXT(64, G_IM_SIZ_16b_BYTES)),
    gsSPTextureRectangle(128 << 2, 125 << 2, (128 + 63) << 2, (125 + 31) << 2, G_TX_RENDERTILE, 0, 0, 4 << 10, 1 << 10),
    gsSPDisplayList(dl_cake_end_eu_070296D8),
    gsSPEndDisplayList(),
};

// VERSION_EU
#else

#include "levels/ending/cake.inc.c"

// 0x07025800 - 0x07025840
static const Vtx cake_end_vertex_07025800[] = {
    {{{     0,    220,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    220,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    240,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    240,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025840 - 0x07025880
static const Vtx cake_end_vertex_07025840[] = {
    {{{    80,    220,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    220,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    240,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    240,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025880 - 0x070258C0
static const Vtx cake_end_vertex_07025880[] = {
    {{{   160,    220,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    220,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    240,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    240,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070258C0 - 0x07025900
static const Vtx cake_end_vertex_070258C0[] = {
    {{{   240,    220,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    220,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    240,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    240,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025900 - 0x07025940
static const Vtx cake_end_vertex_07025900[] = {
    {{{     0,    200,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    200,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    220,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    220,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025940 - 0x07025980
static const Vtx cake_end_vertex_07025940[] = {
    {{{    80,    200,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    200,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    220,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    220,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025980 - 0x070259C0
static const Vtx cake_end_vertex_07025980[] = {
    {{{   160,    200,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    200,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    220,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    220,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070259C0 - 0x07025A00
static const Vtx cake_end_vertex_070259C0[] = {
    {{{   240,    200,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    200,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    220,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    220,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025A00 - 0x07025A40
static const Vtx cake_end_vertex_07025A00[] = {
    {{{     0,    180,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    180,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    200,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    200,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025A40 - 0x07025A80
static const Vtx cake_end_vertex_07025A40[] = {
    {{{    80,    180,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    180,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    200,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    200,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025A80 - 0x07025AC0
static const Vtx cake_end_vertex_07025A80[] = {
    {{{   160,    180,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    180,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    200,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    200,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025AC0 - 0x07025B00
static const Vtx cake_end_vertex_07025AC0[] = {
    {{{   240,    180,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    180,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    200,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    200,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025B00 - 0x07025B40
static const Vtx cake_end_vertex_07025B00[] = {
    {{{     0,    160,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    160,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    180,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    180,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025B40 - 0x07025B80
static const Vtx cake_end_vertex_07025B40[] = {
    {{{    80,    160,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    160,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    180,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    180,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025B80 - 0x07025BC0
static const Vtx cake_end_vertex_07025B80[] = {
    {{{   160,    160,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    160,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    180,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    180,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025BC0 - 0x07025C00
static const Vtx cake_end_vertex_07025BC0[] = {
    {{{   240,    160,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    160,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    180,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    180,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025C00 - 0x07025C40
static const Vtx cake_end_vertex_07025C00[] = {
    {{{     0,    140,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    140,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    160,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    160,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025C40 - 0x07025C80
static const Vtx cake_end_vertex_07025C40[] = {
    {{{    80,    140,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    140,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    160,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    160,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025C80 - 0x07025CC0
static const Vtx cake_end_vertex_07025C80[] = {
    {{{   160,    140,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    140,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    160,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    160,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025CC0 - 0x07025D00
static const Vtx cake_end_vertex_07025CC0[] = {
    {{{   240,    140,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    140,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    160,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    160,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025D00 - 0x07025D40
static const Vtx cake_end_vertex_07025D00[] = {
    {{{     0,    120,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    120,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    140,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    140,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025D40 - 0x07025D80
static const Vtx cake_end_vertex_07025D40[] = {
    {{{    80,    120,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    120,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    140,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    140,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025D80 - 0x07025DC0
static const Vtx cake_end_vertex_07025D80[] = {
    {{{   160,    120,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    120,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    140,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    140,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025DC0 - 0x07025E00
static const Vtx cake_end_vertex_07025DC0[] = {
    {{{   240,    120,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    120,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    140,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    140,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025E00 - 0x07025E40
static const Vtx cake_end_vertex_07025E00[] = {
    {{{     0,    100,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    100,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    120,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    120,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025E40 - 0x07025E80
static const Vtx cake_end_vertex_07025E40[] = {
    {{{    80,    100,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    100,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    120,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    120,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025E80 - 0x07025EC0
static const Vtx cake_end_vertex_07025E80[] = {
    {{{   160,    100,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    100,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    120,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    120,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025EC0 - 0x07025F00
static const Vtx cake_end_vertex_07025EC0[] = {
    {{{   240,    100,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    100,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    120,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    120,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025F00 - 0x07025F40
static const Vtx cake_end_vertex_07025F00[] = {
    {{{     0,     80,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     80,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    100,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,    100,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025F40 - 0x07025F80
static const Vtx cake_end_vertex_07025F40[] = {
    {{{    80,     80,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     80,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    100,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,    100,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025F80 - 0x07025FC0
static const Vtx cake_end_vertex_07025F80[] = {
    {{{   160,     80,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     80,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    100,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,    100,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07025FC0 - 0x07026000
static const Vtx cake_end_vertex_07025FC0[] = {
    {{{   240,     80,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     80,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,    100,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,    100,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026000 - 0x07026040
static const Vtx cake_end_vertex_07026000[] = {
    {{{     0,     60,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     60,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     80,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     80,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026040 - 0x07026080
static const Vtx cake_end_vertex_07026040[] = {
    {{{    80,     60,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     60,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     80,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     80,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026080 - 0x070260C0
static const Vtx cake_end_vertex_07026080[] = {
    {{{   160,     60,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     60,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     80,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     80,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070260C0 - 0x07026100
static const Vtx cake_end_vertex_070260C0[] = {
    {{{   240,     60,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     60,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     80,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     80,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026100 - 0x07026140
static const Vtx cake_end_vertex_07026100[] = {
    {{{     0,     40,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     40,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     60,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     60,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026140 - 0x07026180
static const Vtx cake_end_vertex_07026140[] = {
    {{{    80,     40,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     40,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     60,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     60,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026180 - 0x070261C0
static const Vtx cake_end_vertex_07026180[] = {
    {{{   160,     40,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     40,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     60,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     60,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070261C0 - 0x07026200
static const Vtx cake_end_vertex_070261C0[] = {
    {{{   240,     40,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     40,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     60,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     60,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026200 - 0x07026240
static const Vtx cake_end_vertex_07026200[] = {
    {{{     0,     20,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     20,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     40,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     40,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026240 - 0x07026280
static const Vtx cake_end_vertex_07026240[] = {
    {{{    80,     20,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     20,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     40,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     40,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026280 - 0x070262C0
static const Vtx cake_end_vertex_07026280[] = {
    {{{   160,     20,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     20,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     40,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     40,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070262C0 - 0x07026300
static const Vtx cake_end_vertex_070262C0[] = {
    {{{   240,     20,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     20,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     40,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     40,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026300 - 0x07026340
static const Vtx cake_end_vertex_07026300[] = {
    {{{     0,      0,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,      0,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     20,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{     0,     20,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026340 - 0x07026380
static const Vtx cake_end_vertex_07026340[] = {
    {{{    80,      0,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,      0,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     20,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{    80,     20,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026380 - 0x070263C0
static const Vtx cake_end_vertex_07026380[] = {
    {{{   160,      0,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,      0,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     20,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   160,     20,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x070263C0 - 0x07026400
static const Vtx cake_end_vertex_070263C0[] = {
    {{{   240,      0,     -1}, 0, {     0,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,      0,     -1}, 0, {  2528,    608}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   320,     20,     -1}, 0, {  2528,      0}, {0xff, 0xff, 0xff, 0xff}}},
    {{{   240,     20,     -1}, 0, {     0,      0}, {0xff, 0xff, 0xff, 0xff}}},
};

// 0x07026400 - 0x07027350
const Gfx dl_cake_end_screen[] = {
    gsDPPipeSync(),
    gsDPSetCombineMode(G_CC_DECALRGB, G_CC_DECALRGB),
    gsDPSetRenderMode(G_RM_AA_OPA_SURF, G_RM_AA_OPA_SURF2),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_ON),

    gsDPLoadTextureBlock(cake_end_texture_0, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025800, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_1, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025840, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_2, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025880, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_3, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_070258C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_4, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025900, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_5, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025940, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_6, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025980, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_7, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_070259C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_8, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025A00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_9, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025A40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_10, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025A80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_11, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025AC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_12, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025B00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_13, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025B40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_14, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025B80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_15, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025BC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_16, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025C00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_17, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025C40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_18, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025C80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_19, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025CC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_20, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025D00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_21, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025D40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_22, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025D80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_23, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025DC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_24, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025E00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_25, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025E40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_26, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025E80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_27, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025EC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_28, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025F00, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_29, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025F40, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_30, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025F80, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_31, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07025FC0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_32, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026000, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_33, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026040, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_34, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026080, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_35, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_070260C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_36, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026100, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_37, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026140, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_38, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026180, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_39, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_070261C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_40, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026200, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_41, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026240, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_42, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026280, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_43, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_070262C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_44, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026300, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_45, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026340, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_46, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_07026380, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPLoadTextureBlock(cake_end_texture_47, G_IM_FMT_RGBA, G_IM_SIZ_16b, 80, 20, 0, G_TX_CLAMP, G_TX_CLAMP, 7, 6, G_TX_NOLOD, G_TX_NOLOD),
    gsSPVertex(cake_end_vertex_070263C0, 4, 0),
    gsSP2Triangles( 0,  1,  2, 0x0,  0,  2,  3, 0x0),

    gsDPPipeSync(),
    gsSPTexture(0xFFFF, 0xFFFF, 0, G_TX_RENDERTILE, G_OFF),
    gsSPSetGeometryMode(G_LIGHTING),
    gsDPSetCombineMode(G_CC_SHADE, G_CC_SHADE),
    gsDPSetRenderMode(G_RM_AA_ZB_OPA_SURF, G_RM_AA_ZB_OPA_SURF2),
    gsSPEndDisplayList(),
};
#endif
