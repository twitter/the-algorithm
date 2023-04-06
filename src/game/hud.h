#ifndef HUD_H
#define HUD_H

#include "types.h"

enum PowerMeterAnimation {
    POWER_METER_HIDDEN,
    POWER_METER_EMPHASIZED,
    POWER_METER_DEEMPHASIZING,
    POWER_METER_HIDING,
    POWER_METER_VISIBLE
};

enum CameraHUDLut {
    GLYPH_CAM_CAMERA,
    GLYPH_CAM_MARIO_HEAD,
    GLYPH_CAM_LAKITU_HEAD,
    GLYPH_CAM_FIXED,
    GLYPH_CAM_ARROW_UP,
    GLYPH_CAM_ARROW_DOWN
};

// Segment 3
extern u8 *power_meter_health_segments_lut[];
extern Gfx* dl_power_meter_base;
extern Gfx* dl_power_meter_health_segments_begin;
extern Gfx* dl_power_meter_health_segments_end;

// Functions
extern void set_hud_camera_status(s16 status);
extern void render_hud(void);

#endif /* HUD_H */
