#include <ultra64.h>
#include "sfx.h"

static u32 sSfxToPlay;     // @ 801B9B20
static u32 sLastPlayedSfx; // @ 801B9B24

/* orig name: func_801780A0 */
void gd_reset_sfx(void) {
    sLastPlayedSfx = GD_SFX_NONE;
    sSfxToPlay = GD_SFX_NONE;
}

/* orig name: func_801780C0 */
u32 gd_new_sfx_to_play(void) {
    return ~sLastPlayedSfx & sSfxToPlay;
}

/* orig name: func_801780EC */
void gd_sfx_played(void) {
    sLastPlayedSfx = sSfxToPlay;
    sSfxToPlay = GD_SFX_NONE;
}

/* orig name: func_80178114 */
/* Add GdSfx to queue to be played. Note that only the SFX
 * with the lowest value from the enum is played on a given frame **/
void gd_play_sfx(enum GdSfx sfx) {
    sSfxToPlay |= sfx;
}
