#ifndef GD_SFX_H
#define GD_SFX_H

#include <PR/ultratypes.h>

// Sfx for Mario Head Screen
enum GdSfx {
    GD_SFX_NONE           = 0x00,
    GD_SFX_HAND_APPEAR    = 0x01,
    GD_SFX_HAND_DISAPPEAR = 0x02,
    GD_SFX_UNUSED_COIN    = 0x04,
    GD_SFX_PINCH_FACE     = 0x08,
    GD_SFX_PINCH_FACE_2   = 0x10,
    GD_SFX_LET_GO_FACE    = 0x20,
    GD_SFX_CAM_ZOOM_IN    = 0x40,
    GD_SFX_CAM_ZOOM_OUT   = 0x80
};

// functions
void gd_reset_sfx(void);
u32 gd_new_sfx_to_play(void);
void gd_sfx_update(void);
void gd_play_sfx(enum GdSfx sfx);

#endif // GD_SFX_H
