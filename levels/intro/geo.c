#include <ultra64.h>
#include "sm64.h"
#include "geo_commands.h"

#include "game/level_geo.h"
#include "game/geo_misc.h"
#include "game/camera.h"
#include "game/moving_texture.h"
#include "game/screen_transition.h"
#include "game/paintings.h"
#include "menu/debug_level_select.h"
#include "menu/intro_geo.h"

#include "make_const_nonconst.h"

#include "levels/intro/header.h"

// 0x0E0002D0
const GeoLayout intro_geo_0002D0[] = {
   GEO_NODE_SCREEN_AREA(0, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH/2, SCREEN_HEIGHT/2),
   GEO_OPEN_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_NODE_ORTHO(100),
         GEO_OPEN_NODE(),
            GEO_BACKGROUND_COLOR(0x0001),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(1),
      GEO_OPEN_NODE(),
         GEO_CAMERA_FRUSTUM(45, 128, 16384),
         GEO_OPEN_NODE(),
            GEO_CAMERA(0, 0, 0, 3200, 0, 0, 0, 0x00000000),
            GEO_OPEN_NODE(),
               GEO_ASM(0, geo_intro_super_mario_64_logo),
            GEO_CLOSE_NODE(),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_ASM(0, geo_intro_tm_copyright),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E00035C
const GeoLayout intro_geo_mario_head_regular[] = {
   GEO_NODE_SCREEN_AREA(0, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH/2, SCREEN_HEIGHT/2),
   GEO_OPEN_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_NODE_ORTHO(100),
         GEO_OPEN_NODE(),
            GEO_ASM(0, geo_intro_regular_backdrop),
#ifdef VERSION_SH
            GEO_ASM(0, geo_intro_face_easter_egg),
#endif
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(1),
      GEO_OPEN_NODE(),
         GEO_CAMERA_FRUSTUM(45, 128, 16384),
         GEO_OPEN_NODE(),
            GEO_ASM(2, geo_draw_mario_head_goddard),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
#ifdef VERSION_SH
   GEO_ZBUFFER(0),
   GEO_OPEN_NODE(),
      GEO_ASM(0, geo_intro_rumble_pak_graphic),
   GEO_CLOSE_NODE(),
#endif
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E0003B8
const GeoLayout intro_geo_mario_head_dizzy[] = {
   GEO_NODE_SCREEN_AREA(0, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH/2, SCREEN_HEIGHT/2),
   GEO_OPEN_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_NODE_ORTHO(100),
         GEO_OPEN_NODE(),
            GEO_ASM(0, geo_intro_gameover_backdrop),
#ifdef VERSION_SH
            GEO_ASM(0, geo_intro_face_easter_egg),
#endif
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(1),
      GEO_OPEN_NODE(),
         GEO_CAMERA_FRUSTUM(45, 128, 16384),
         GEO_OPEN_NODE(),
            GEO_ASM(3, geo_draw_mario_head_goddard),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
#ifdef VERSION_SH
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_ASM(1, geo_intro_rumble_pak_graphic),
      GEO_CLOSE_NODE(),
#endif
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000414
const GeoLayout intro_geo_000414[] = {
   GEO_NODE_SCREEN_AREA(0, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH/2, SCREEN_HEIGHT/2),
   GEO_OPEN_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_NODE_ORTHO(100),
         GEO_OPEN_NODE(),
            GEO_ASM(0, geo_intro_regular_backdrop),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(1),
      GEO_OPEN_NODE(),
         GEO_CAMERA_FRUSTUM(45, 128, 16384),
         GEO_OPEN_NODE(),
            GEO_CAMERA(0, 0, 0, 1200, 0, 0, 0, 0x00000000),
            GEO_OPEN_NODE(),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE, -230, 300, 0, debug_level_select_dl_07000858),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE, -120, 300, 0, debug_level_select_dl_07001100),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,  -20, 300, 0, debug_level_select_dl_07001BA0),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,  100, 300, 0, debug_level_select_dl_070025F0),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,  250, 300, 0, debug_level_select_dl_07003258),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE, -310, 100, 0, debug_level_select_dl_07003DB8),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,  -90, 100, 0, debug_level_select_dl_070048C8),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,   60, 100, 0, debug_level_select_dl_07005558),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,  180, 100, 0, debug_level_select_dl_070059F8),
               GEO_TRANSLATE_NODE_WITH_DL(LAYER_OPAQUE,  300, 100, 0, debug_level_select_dl_070063B0),
            GEO_CLOSE_NODE(),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

