#ifndef _SKYBOX_H
#define _SKYBOX_H

#include "types.h"

extern Gfx *create_skybox_facing_camera(s8 a, s8 background, f32 fov, f32 posX, f32 posY, f32 posZ,
                                        f32 focX, f32 focY, f32 focZ);

#endif /* _SKYBOX_H */
