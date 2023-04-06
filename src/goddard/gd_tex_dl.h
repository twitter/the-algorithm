#ifndef GD_TEXTURE_DL_H
#define GD_TEXTURE_DL_H

#include <ultra64.h>

/**
 * @file gd_tex_dl.h
 * 
 * Data arrays that were probably in u16 C arrays in `renderer`,
 * but it is easier to leave these as ALIGNED8 u8 arrays in C.
 */

extern Gfx *redStarDlArray[];
extern Gfx *silverStarDlArray[];
extern Gfx *redSparkleDlArray[];
extern Gfx *silverSparkleDlArray[];
extern Gfx marioHeadDl801B5170[];
extern Gfx marioHeadDl801B5188[];
extern Gfx marioHeadDl801B5100[];
extern f32 D_801B520C;
extern u16 textureHandOpen[];
extern u16 textureHandClosed[];
extern Gfx marioHeadDl801B52D8[];

#endif /* GD_TEXTURE_DL_H */
