#include <ultra64.h>
#include "sm64.h"
#include "segment_symbols.h"
#include "level_commands.h"

#include "levels/intro/header.h"

#include "make_const_nonconst.h"

const LevelScript level_script_entry[] = {
    INIT_LEVEL(),
    SLEEP(/*frames*/ 2),
    BLACKOUT(/*active*/ FALSE),
    SET_REG(/*value*/ 0),
    EXECUTE(/*seg*/ 0x14, /*script*/ _introSegmentRomStart, /*scriptEnd*/ _introSegmentRomEnd, /*entry*/ level_intro_splash_screen),
    JUMP(/*target*/ level_script_entry),
};
