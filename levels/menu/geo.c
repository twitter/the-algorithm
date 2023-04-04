#include <ultra64.h>
#include "sm64.h"
#include "geo_commands.h"

#include "game/level_geo.h"
#include "game/geo_misc.h"
#include "game/camera.h"
#include "game/moving_texture.h"
#include "game/screen_transition.h"
#include "game/paintings.h"
#include "menu/file_select.h"
#include "menu/star_select.h"

#include "make_const_nonconst.h"

#include "levels/menu/header.h"

// 0x0E0001D0
const GeoLayout geo_menu_mario_save_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_mario_save_button_base),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_save_button_back),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000200
const GeoLayout geo_menu_mario_save_button_fade[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_mario_save_button_base),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_save_button_fade_back),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000230
const GeoLayout geo_menu_mario_new_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_mario_new_button_base),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_save_button_back),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000260
const GeoLayout geo_menu_mario_new_button_fade[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_mario_new_button_base),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_save_button_fade_back),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000290
const GeoLayout geo_menu_erase_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_erase_button),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E0002B8
const GeoLayout geo_menu_copy_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_copy_button),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E0002E0
const GeoLayout geo_menu_file_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_file_button),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000308
const GeoLayout geo_menu_score_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_score_button),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000330
const GeoLayout geo_menu_sound_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_sound_button),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000358
const GeoLayout geo_menu_generic_button[] = {
   GEO_NODE_START(),
   GEO_OPEN_NODE(),
      GEO_SCALE(0x00, 524288),
      GEO_OPEN_NODE(),
         GEO_DISPLAY_LIST(LAYER_OPAQUE, dl_menu_generic_button),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000380
const GeoLayout geo_menu_file_select_strings_and_menu_cursor[] = {
   GEO_NODE_SCREEN_AREA(10, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH/2, SCREEN_HEIGHT/2),
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
         GEO_CAMERA_FRUSTUM(45, 100, 25000),
         GEO_OPEN_NODE(),
            GEO_CAMERA(0, 0, 0, 1000, 0, 0, 0, 0x00000000),
            GEO_OPEN_NODE(),
               GEO_RENDER_OBJ(),
            GEO_CLOSE_NODE(),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_ASM(0, geo_file_select_strings_and_menu_cursor),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};

// 0x0E000408
const GeoLayout geo_menu_act_selector_strings[] = {
   GEO_NODE_SCREEN_AREA(10, SCREEN_WIDTH/2, SCREEN_HEIGHT/2, SCREEN_WIDTH/2, SCREEN_HEIGHT/2),
   GEO_OPEN_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_NODE_ORTHO(100),
         GEO_OPEN_NODE(),
            GEO_BACKGROUND_COLOR(0xFFFF),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(1),
      GEO_OPEN_NODE(),
         GEO_CAMERA_FRUSTUM(45, 100, 25000),
         GEO_OPEN_NODE(),
            GEO_CAMERA(0, 0, 0, 1000, 0, 0, 0, 0x00000000),
            GEO_OPEN_NODE(),
               GEO_RENDER_OBJ(),
            GEO_CLOSE_NODE(),
         GEO_CLOSE_NODE(),
      GEO_CLOSE_NODE(),
      GEO_ZBUFFER(0),
      GEO_OPEN_NODE(),
         GEO_ASM(0, geo_act_selector_strings),
      GEO_CLOSE_NODE(),
   GEO_CLOSE_NODE(),
   GEO_END(),
};
