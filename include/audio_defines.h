#ifndef AUDIO_DEFINES_H
#define AUDIO_DEFINES_H

// Sound Magic Definition:
// First Byte (Upper Nibble): Sound Bank (not the same as audio bank!)
// First Byte (Lower Nibble): Bitflags for audio playback?
// Second Byte: Sound ID
// Third Byte: Priority
// Fourth Byte (Upper Nibble): More bitflags
// Fourth Byte (Lower Nibble): Sound Status (this is set to SOUND_STATUS_PLAYING when passed to the audio driver.)
#define SOUND_ARG_LOAD(bank, playFlags, soundID, priority, flags2) (((u32) (bank) << 28)       | \
                 ((u32) (playFlags) << 24) | ((u32) (soundID) << 16) | ((u32) (priority) << 8) | \
                 ((u32) (flags2)  << 4)  |  SOUND_STATUS_STARTING)

#define SOUNDARGS_MASK_BANK         0xF0000000
#define SOUNDARGS_MASK_SOUNDID      0x00FF0000
#define SOUNDARGS_MASK_PRIORITY     0x0000FF00
#define SOUNDARGS_MASK_STATUS       0x0000000F

#define SOUNDARGS_SHIFT_BANK        28
#define SOUNDARGS_SHIFT_SOUNDID     16
#define SOUNDARGS_SHIFT_PRIORITY    8

/* Audio Status */
#define SOUND_STATUS_STOPPED        0
#define SOUND_STATUS_STARTING       1
#define SOUND_STATUS_PLAYING        2

/* Audio lower bitflags. TODO: Figure out what these mean and use them below. */
#define SOUND_LO_BITFLAG_UNK1       0x10 // fade in?
#define SOUND_NO_ECHO               0x20 // not in JP
#define SOUND_LO_BITFLAG_UNK8       0x80 // restart playing on each play_sound call?

/* Audio playback bitflags. TODO: Figure out what these mean and use them below. */
#define SOUND_PL_BITFLAG_UNK1       0x1000000
#define SOUND_PL_BITFLAG_UNK2       0x2000000
#define SOUND_PL_BITFLAG_UNK4       0x4000000
#define SOUND_PL_BITFLAG_UNK8       0x8000000

// silence
#define NO_SOUND                    0

/**
 * The table below defines all sounds that exist in the game, and which flags
 * they are used with. If a sound is used with multiple sets of flags (e.g.
 * different priorities), they are gives distinguishing suffixes.
 * A _2 suffix means the sound is the same despite a different sound ID.
 * Some sounds are unused by the game, and given as hexadecimal literals rather
 * than SOUND_ARG_LOAD calls.
 */

/* Terrain sounds */

/**
 * Terrain-dependent action sounds. mario_get_terrain_sound_addend computes a
 * sound terrain type between 0 and 7, depending on the terrain type of the
 * level and the floor type that Mario is standing on. That value is then added
 * to the sound ID for the TERRAIN_* sounds.
 */
#define SOUND_TERRAIN_DEFAULT   0 // e.g. air
#define SOUND_TERRAIN_GRASS     1
#define SOUND_TERRAIN_WATER     2
#define SOUND_TERRAIN_STONE     3
#define SOUND_TERRAIN_SPOOKY    4 // squeaky floor
#define SOUND_TERRAIN_SNOW      5
#define SOUND_TERRAIN_ICE       6
#define SOUND_TERRAIN_SAND      7

#define SOUND_ACTION_TERRAIN_JUMP               SOUND_ARG_LOAD(0, 4, 0x00, 0x80, 8)
#define SOUND_ACTION_TERRAIN_LANDING            SOUND_ARG_LOAD(0, 4, 0x08, 0x80, 8)
#define SOUND_ACTION_TERRAIN_STEP               SOUND_ARG_LOAD(0, 6, 0x10, 0x80, 8)
#define SOUND_ACTION_TERRAIN_BODY_HIT_GROUND    SOUND_ARG_LOAD(0, 4, 0x18, 0x80, 8)
#define SOUND_ACTION_TERRAIN_STEP_TIPTOE        SOUND_ARG_LOAD(0, 6, 0x20, 0x80, 8)
#define SOUND_ACTION_TERRAIN_STUCK_IN_GROUND    SOUND_ARG_LOAD(0, 4, 0x48, 0x80, 8)
#define SOUND_ACTION_TERRAIN_HEAVY_LANDING      SOUND_ARG_LOAD(0, 4, 0x60, 0x80, 8)

#define SOUND_ACTION_METAL_JUMP                         SOUND_ARG_LOAD(0, 4, 0x28, 0x90, 8)
#define SOUND_ACTION_METAL_LANDING                      SOUND_ARG_LOAD(0, 4, 0x29, 0x90, 8)
#define SOUND_ACTION_METAL_STEP                         SOUND_ARG_LOAD(0, 4, 0x2A, 0x90, 8)
#define SOUND_ACTION_METAL_HEAVY_LANDING                SOUND_ARG_LOAD(0, 4, 0x2B, 0x90, 8)
#define SOUND_ACTION_CLAP_HANDS_COLD                    SOUND_ARG_LOAD(0, 6, 0x2C, 0x00, 8)
#define SOUND_ACTION_HANGING_STEP                       SOUND_ARG_LOAD(0, 4, 0x2D, 0xA0, 8)
#define SOUND_ACTION_QUICKSAND_STEP                     SOUND_ARG_LOAD(0, 4, 0x2E, 0x00, 8)
#define SOUND_ACTION_METAL_STEP_TIPTOE                  SOUND_ARG_LOAD(0, 4, 0x2F, 0x90, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN430      SOUND_ARG_LOAD(0, 4, 0x30, 0xC0, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN431      SOUND_ARG_LOAD(0, 4, 0x31, 0x60, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN432      SOUND_ARG_LOAD(0, 4, 0x32, 0x80, 8)
#define SOUND_ACTION_SWIM                               SOUND_ARG_LOAD(0, 4, 0x33, 0x80, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN434      SOUND_ARG_LOAD(0, 4, 0x34, 0x80, 8)
#define SOUND_ACTION_THROW                              SOUND_ARG_LOAD(0, 4, 0x35, 0x80, 8)
#define SOUND_ACTION_KEY_SWISH                          SOUND_ARG_LOAD(0, 4, 0x36, 0x80, 8)
#define SOUND_ACTION_SPIN                               SOUND_ARG_LOAD(0, 4, 0x37, 0x80, 8)
#define SOUND_ACTION_TWIRL                              SOUND_ARG_LOAD(0, 4, 0x38, 0x80, 8) // same sound as spin
/* not verified */ #define SOUND_ACTION_CLIMB_UP_TREE   SOUND_ARG_LOAD(0, 4, 0x3A, 0x80, 8)
/* not verified */ #define SOUND_ACTION_CLIMB_DOWN_TREE 0x003B
/* not verified */ #define SOUND_ACTION_UNK3C           0x003C
/* not verified */ #define SOUND_ACTION_UNKNOWN43D      SOUND_ARG_LOAD(0, 4, 0x3D, 0x80, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN43E      SOUND_ARG_LOAD(0, 4, 0x3E, 0x80, 8)
/* not verified */ #define SOUND_ACTION_PAT_BACK        SOUND_ARG_LOAD(0, 4, 0x3F, 0x80, 8)
#define SOUND_ACTION_BRUSH_HAIR                         SOUND_ARG_LOAD(0, 4, 0x40, 0x80, 8)
/* not verified */ #define SOUND_ACTION_CLIMB_UP_POLE   SOUND_ARG_LOAD(0, 4, 0x41, 0x80, 8)
#define SOUND_ACTION_METAL_BONK                         SOUND_ARG_LOAD(0, 4, 0x42, 0x80, 8)
#define SOUND_ACTION_UNSTUCK_FROM_GROUND                SOUND_ARG_LOAD(0, 4, 0x43, 0x80, 8)
/* not verified */ #define SOUND_ACTION_HIT             SOUND_ARG_LOAD(0, 4, 0x44, 0xC0, 8)
/* not verified */ #define SOUND_ACTION_HIT_2           SOUND_ARG_LOAD(0, 4, 0x44, 0xB0, 8)
/* not verified */ #define SOUND_ACTION_HIT_3           SOUND_ARG_LOAD(0, 4, 0x44, 0xA0, 8)
#define SOUND_ACTION_BONK                               SOUND_ARG_LOAD(0, 4, 0x45, 0xA0, 8)
#define SOUND_ACTION_SHRINK_INTO_BBH                    SOUND_ARG_LOAD(0, 4, 0x46, 0xA0, 8)
#define SOUND_ACTION_SWIM_FAST                          SOUND_ARG_LOAD(0, 4, 0x47, 0xA0, 8)
#define SOUND_ACTION_METAL_JUMP_WATER                   SOUND_ARG_LOAD(0, 4, 0x50, 0x90, 8)
#define SOUND_ACTION_METAL_LAND_WATER                   SOUND_ARG_LOAD(0, 4, 0x51, 0x90, 8)
#define SOUND_ACTION_METAL_STEP_WATER                   SOUND_ARG_LOAD(0, 4, 0x52, 0x90, 8)
/* not verified */ #define SOUND_ACTION_UNK53           0x0053
/* not verified */ #define SOUND_ACTION_UNK54           0x0054
/* not verified */ #define SOUND_ACTION_UNK55           0x0055
/* not verified */ #define SOUND_ACTION_FLYING_FAST     SOUND_ARG_LOAD(0, 4, 0x56, 0x80, 8) // "swoop"?
#define SOUND_ACTION_TELEPORT                           SOUND_ARG_LOAD(0, 4, 0x57, 0xC0, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN458      SOUND_ARG_LOAD(0, 4, 0x58, 0xA0, 8)
/* not verified */ #define SOUND_ACTION_BOUNCE_OFF_OBJECT   SOUND_ARG_LOAD(0, 4, 0x59, 0xB0, 8)
/* not verified */ #define SOUND_ACTION_SIDE_FLIP_UNK   SOUND_ARG_LOAD(0, 4, 0x5A, 0x80, 8)
#define SOUND_ACTION_READ_SIGN                          SOUND_ARG_LOAD(0, 4, 0x5B, 0xFF, 8)
/* not verified */ #define SOUND_ACTION_UNKNOWN45C      SOUND_ARG_LOAD(0, 4, 0x5C, 0x80, 8)
/* not verified */ #define SOUND_ACTION_UNK5D           0x005D
/* not verified */ #define SOUND_ACTION_INTRO_UNK45E    SOUND_ARG_LOAD(0, 4, 0x5E, 0x80, 8)
/* not verified */ #define SOUND_ACTION_INTRO_UNK45F    SOUND_ARG_LOAD(0, 4, 0x5F, 0x80, 8)

/* Moving Sound Effects */

// Terrain-dependent moving sounds; a value 0-7 is added to the sound ID before
// playing. See higher up for the different terrain types.
#define SOUND_MOVING_TERRAIN_SLIDE              SOUND_ARG_LOAD(1, 4, 0x00, 0x00, 0)
#define SOUND_MOVING_TERRAIN_RIDING_SHELL       SOUND_ARG_LOAD(1, 4, 0x20, 0x00, 0)

#define SOUND_MOVING_LAVA_BURN                  SOUND_ARG_LOAD(1, 4, 0x10, 0x00, 0) // ?
#define SOUND_MOVING_SLIDE_DOWN_POLE            SOUND_ARG_LOAD(1, 4, 0x11, 0x00, 0) // ?
#define SOUND_MOVING_SLIDE_DOWN_TREE            SOUND_ARG_LOAD(1, 4, 0x12, 0x80, 0)
#define SOUND_MOVING_QUICKSAND_DEATH            SOUND_ARG_LOAD(1, 4, 0x14, 0x00, 0)
#define SOUND_MOVING_SHOCKED                    SOUND_ARG_LOAD(1, 4, 0x16, 0x00, 0)
#define SOUND_MOVING_FLYING                     SOUND_ARG_LOAD(1, 4, 0x17, 0x00, 0)
#define SOUND_MOVING_ALMOST_DROWNING            SOUND_ARG_LOAD(1, 0xC, 0x18, 0x00, 0)
#define SOUND_MOVING_AIM_CANNON                 SOUND_ARG_LOAD(1, 0xD, 0x19, 0x20, 0)
#define SOUND_MOVING_UNK1A                      0x101A // ?
#define SOUND_MOVING_RIDING_SHELL_LAVA          SOUND_ARG_LOAD(1, 4, 0x28, 0x00, 0)

/* Mario Sound Effects */
// A random number 0-2 is added to the sound ID before playing, producing Yah/Wah/Hoo
#define SOUND_MARIO_YAH_WAH_HOO                         SOUND_ARG_LOAD(2, 4, 0x00, 0x80, 8)
/* not verified */ #define SOUND_MARIO_HOOHOO           SOUND_ARG_LOAD(2, 4, 0x03, 0x80, 8)
/* not verified */ #define SOUND_MARIO_YAHOO            SOUND_ARG_LOAD(2, 4, 0x04, 0x80, 8)
/* not verified */ #define SOUND_MARIO_UH               SOUND_ARG_LOAD(2, 4, 0x05, 0x80, 8)
/* not verified */ #define SOUND_MARIO_HRMM             SOUND_ARG_LOAD(2, 4, 0x06, 0x80, 8)
/* not verified */ #define SOUND_MARIO_WAH2             SOUND_ARG_LOAD(2, 4, 0x07, 0x80, 8)
/* not verified */ #define SOUND_MARIO_WHOA             SOUND_ARG_LOAD(2, 4, 0x08, 0xC0, 8)
/* not verified */ #define SOUND_MARIO_EEUH             SOUND_ARG_LOAD(2, 4, 0x09, 0x80, 8)
/* not verified */ #define SOUND_MARIO_ATTACKED         SOUND_ARG_LOAD(2, 4, 0x0A, 0xFF, 8)
/* not verified */ #define SOUND_MARIO_OOOF             SOUND_ARG_LOAD(2, 4, 0x0B, 0x80, 8)
/* not verified */ #define SOUND_MARIO_OOOF2            SOUND_ARG_LOAD(2, 4, 0x0B, 0xD0, 8)
#define SOUND_MARIO_HERE_WE_GO                          SOUND_ARG_LOAD(2, 4, 0x0C, 0x80, 8)
/* not verified */ #define SOUND_MARIO_YAWNING          SOUND_ARG_LOAD(2, 4, 0x0D, 0x80, 8)
#define SOUND_MARIO_SNORING1                            SOUND_ARG_LOAD(2, 4, 0x0E, 0x80, 8)
#define SOUND_MARIO_SNORING2                            SOUND_ARG_LOAD(2, 4, 0x0F, 0x80, 8)
/* not verified */ #define SOUND_MARIO_WAAAOOOW         SOUND_ARG_LOAD(2, 4, 0x10, 0xC0, 8)
/* not verified */ #define SOUND_MARIO_HAHA             SOUND_ARG_LOAD(2, 4, 0x11, 0x80, 8)
/* not verified */ #define SOUND_MARIO_HAHA_2           SOUND_ARG_LOAD(2, 4, 0x11, 0xF0, 8)
/* not verified */ #define SOUND_MARIO_UH2              SOUND_ARG_LOAD(2, 4, 0x13, 0xD0, 8)
/* not verified */ #define SOUND_MARIO_UH2_2            SOUND_ARG_LOAD(2, 4, 0x13, 0x80, 8)
/* not verified */ #define SOUND_MARIO_ON_FIRE          SOUND_ARG_LOAD(2, 4, 0x14, 0xA0, 8)
/* not verified */ #define SOUND_MARIO_DYING            SOUND_ARG_LOAD(2, 4, 0x15, 0xFF, 8)
#define SOUND_MARIO_PANTING_COLD                        SOUND_ARG_LOAD(2, 4, 0x16, 0x80, 8)

// A random number 0-2 is added to the sound ID before playing
#define SOUND_MARIO_PANTING                     SOUND_ARG_LOAD(2, 4, 0x18, 0x80, 8)

#define SOUND_MARIO_COUGHING1                   SOUND_ARG_LOAD(2, 4, 0x1B, 0x80, 8)
#define SOUND_MARIO_COUGHING2                   SOUND_ARG_LOAD(2, 4, 0x1C, 0x80, 8)
#define SOUND_MARIO_COUGHING3                   SOUND_ARG_LOAD(2, 4, 0x1D, 0x80, 8)
#define SOUND_MARIO_PUNCH_YAH                   SOUND_ARG_LOAD(2, 4, 0x1E, 0x80, 8)
#define SOUND_MARIO_PUNCH_HOO                   SOUND_ARG_LOAD(2, 4, 0x1F, 0x80, 8)
#define SOUND_MARIO_MAMA_MIA                    SOUND_ARG_LOAD(2, 4, 0x20, 0x80, 8)
#define SOUND_MARIO_OKEY_DOKEY                  0x2021
#define SOUND_MARIO_GROUND_POUND_WAH            SOUND_ARG_LOAD(2, 4, 0x22, 0x80, 8)
#define SOUND_MARIO_DROWNING                    SOUND_ARG_LOAD(2, 4, 0x23, 0xF0, 8)
#define SOUND_MARIO_PUNCH_WAH                   SOUND_ARG_LOAD(2, 4, 0x24, 0x80, 8)

/* Mario Sound Effects (US/EU only) */
#define SOUND_PEACH_DEAR_MARIO                  SOUND_ARG_LOAD(2, 4, 0x28, 0xFF, 8)

// A random number 0-4 is added to the sound ID before playing, producing one of
// Yahoo! (60% chance), Waha! (20%), or Yippee! (20%).
#define SOUND_MARIO_YAHOO_WAHA_YIPPEE           SOUND_ARG_LOAD(2, 4, 0x2B, 0x80, 8)

#define SOUND_MARIO_DOH                         SOUND_ARG_LOAD(2, 4, 0x30, 0x80, 8)
#define SOUND_MARIO_GAME_OVER                   SOUND_ARG_LOAD(2, 4, 0x31, 0xFF, 8)
#define SOUND_MARIO_HELLO                       SOUND_ARG_LOAD(2, 4, 0x32, 0xFF, 8)
#define SOUND_MARIO_PRESS_START_TO_PLAY         SOUND_ARG_LOAD(2, 4, 0x33, 0xFF, 0xA)
#define SOUND_MARIO_TWIRL_BOUNCE                SOUND_ARG_LOAD(2, 4, 0x34, 0x80, 8)
#define SOUND_MARIO_SNORING3                    SOUND_ARG_LOAD(2, 4, 0x35, 0xFF, 8)
#define SOUND_MARIO_SO_LONGA_BOWSER             SOUND_ARG_LOAD(2, 4, 0x36, 0x80, 8)
#define SOUND_MARIO_IMA_TIRED                   SOUND_ARG_LOAD(2, 4, 0x37, 0x80, 8)

/* Princess Peach Sound Effects (US/EU only) */
#define SOUND_PEACH_MARIO                       SOUND_ARG_LOAD(2, 4, 0x38, 0xFF, 8)
#define SOUND_PEACH_POWER_OF_THE_STARS          SOUND_ARG_LOAD(2, 4, 0x39, 0xFF, 8)
#define SOUND_PEACH_THANKS_TO_YOU               SOUND_ARG_LOAD(2, 4, 0x3A, 0xFF, 8)
#define SOUND_PEACH_THANK_YOU_MARIO             SOUND_ARG_LOAD(2, 4, 0x3B, 0xFF, 8)
#define SOUND_PEACH_SOMETHING_SPECIAL           SOUND_ARG_LOAD(2, 4, 0x3C, 0xFF, 8)
#define SOUND_PEACH_BAKE_A_CAKE                 SOUND_ARG_LOAD(2, 4, 0x3D, 0xFF, 8)
#define SOUND_PEACH_FOR_MARIO                   SOUND_ARG_LOAD(2, 4, 0x3E, 0xFF, 8)
#define SOUND_PEACH_MARIO2                      SOUND_ARG_LOAD(2, 4, 0x3F, 0xFF, 8)

/* General Sound Effects */
#define SOUND_GENERAL_ACTIVATE_CAP_SWITCH                   SOUND_ARG_LOAD(3, 0, 0x00, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_FLAME_OUT          SOUND_ARG_LOAD(3, 0, 0x03, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_OPEN_WOOD_DOOR     SOUND_ARG_LOAD(3, 0, 0x04, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_CLOSE_WOOD_DOOR    SOUND_ARG_LOAD(3, 0, 0x05, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_OPEN_IRON_DOOR     SOUND_ARG_LOAD(3, 0, 0x06, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_CLOSE_IRON_DOOR    SOUND_ARG_LOAD(3, 0, 0x07, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_BUBBLES            0x3008
/* not verified */ #define SOUND_GENERAL_MOVING_WATER       SOUND_ARG_LOAD(3, 0, 0x09, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_SWISH_WATER        SOUND_ARG_LOAD(3, 0, 0x0A, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_QUIET_BUBBLE       SOUND_ARG_LOAD(3, 0, 0x0B, 0x00, 8)
#define SOUND_GENERAL_VOLCANO_EXPLOSION                     SOUND_ARG_LOAD(3, 0, 0x0C, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_QUIET_BUBBLE2      SOUND_ARG_LOAD(3, 0, 0x0D, 0x00, 8)
#define SOUND_GENERAL_CASTLE_TRAP_OPEN                      SOUND_ARG_LOAD(3, 0, 0x0E, 0x80, 8)
#define SOUND_GENERAL_WALL_EXPLOSION                        SOUND_ARG_LOAD(3, 0, 0x0F, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_COIN               SOUND_ARG_LOAD(3, 8, 0x11, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_COIN_WATER         SOUND_ARG_LOAD(3, 8, 0x12, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_SHORT_STAR         SOUND_ARG_LOAD(3, 0, 0x16, 0x00, 9)
/* not verified */ #define SOUND_GENERAL_BIG_CLOCK          SOUND_ARG_LOAD(3, 0, 0x17, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_LOUD_POUND         0x3018 // _TERRAIN?
/* not verified */ #define SOUND_GENERAL_LOUD_POUND2        0x3019
/* not verified */ #define SOUND_GENERAL_SHORT_POUND1       0x301A
/* not verified */ #define SOUND_GENERAL_SHORT_POUND2       0x301B
/* not verified */ #define SOUND_GENERAL_SHORT_POUND3       0x301C
/* not verified */ #define SOUND_GENERAL_SHORT_POUND4       0x301D
/* not verified */ #define SOUND_GENERAL_SHORT_POUND5       0x301E
/* not verified */ #define SOUND_GENERAL_SHORT_POUND6       0x301F
#define SOUND_GENERAL_OPEN_CHEST                            SOUND_ARG_LOAD(3, 1, 0x20, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_CLAM_SHELL1        SOUND_ARG_LOAD(3, 1, 0x22, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_BOX_LANDING        SOUND_ARG_LOAD(3, 0, 0x24, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_BOX_LANDING_2      SOUND_ARG_LOAD(3, 2, 0x24, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN1           SOUND_ARG_LOAD(3, 0, 0x25, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN1_2         SOUND_ARG_LOAD(3, 2, 0x25, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_CLAM_SHELL2        SOUND_ARG_LOAD(3, 0, 0x26, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_CLAM_SHELL3        SOUND_ARG_LOAD(3, 0, 0x27, 0x40, 8)
#ifdef VERSION_JP
#define SOUND_GENERAL_PAINTING_EJECT                        SOUND_ARG_LOAD(3, 8, 0x28, 0x00, 8)
#else
#define SOUND_GENERAL_PAINTING_EJECT                        SOUND_ARG_LOAD(3, 9, 0x28, 0x00, 8)
#endif
#define SOUND_GENERAL_LEVEL_SELECT_CHANGE                   SOUND_ARG_LOAD(3, 0, 0x2B, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_PLATFORM           SOUND_ARG_LOAD(3, 0, 0x2D, 0x80, 8)
#define SOUND_GENERAL_DONUT_PLATFORM_EXPLOSION              SOUND_ARG_LOAD(3, 0, 0x2E, 0x20, 8)
#define SOUND_GENERAL_BOWSER_BOMB_EXPLOSION                 SOUND_ARG_LOAD(3, 1, 0x2F, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_COIN_SPURT         SOUND_ARG_LOAD(3, 0, 0x30, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_COIN_SPURT_2       SOUND_ARG_LOAD(3, 8, 0x30, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_COIN_SPURT_EU      SOUND_ARG_LOAD(3, 8, 0x30, 0x20, 8)

/* not verified */ #define SOUND_GENERAL_EXPLOSION6         0x3031
/* not verified */ #define SOUND_GENERAL_UNK32              0x3032
/* not verified */ #define SOUND_GENERAL_BOAT_TILT1         SOUND_ARG_LOAD(3, 0, 0x34, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_BOAT_TILT2         SOUND_ARG_LOAD(3, 0, 0x35, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_COIN_DROP          SOUND_ARG_LOAD(3, 0, 0x36, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN3_LOWPRIO   SOUND_ARG_LOAD(3, 0, 0x37, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN3           SOUND_ARG_LOAD(3, 0, 0x37, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN3_2         SOUND_ARG_LOAD(3, 8, 0x37, 0x80, 8)
#define SOUND_GENERAL_PENDULUM_SWING                        SOUND_ARG_LOAD(3, 0, 0x38, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_CHAIN_CHOMP1       SOUND_ARG_LOAD(3, 0, 0x39, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_CHAIN_CHOMP2       SOUND_ARG_LOAD(3, 0, 0x3A, 0x00, 8)
#define SOUND_GENERAL_DOOR_TURN_KEY                         SOUND_ARG_LOAD(3, 0, 0x3B, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_MOVING_IN_SAND     SOUND_ARG_LOAD(3, 0, 0x3C, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN4_LOWPRIO   SOUND_ARG_LOAD(3, 0, 0x3D, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNKNOWN4           SOUND_ARG_LOAD(3, 0, 0x3D, 0x80, 8)
#define SOUND_GENERAL_MOVING_PLATFORM_SWITCH                SOUND_ARG_LOAD(3, 0, 0x3E, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_CAGE_OPEN          SOUND_ARG_LOAD(3, 0, 0x3F, 0xA0, 8)
/* not verified */ #define SOUND_GENERAL_QUIET_POUND1_LOWPRIO   SOUND_ARG_LOAD(3, 0, 0x40, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_QUIET_POUND1       SOUND_ARG_LOAD(3, 0, 0x40, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_BREAK_BOX          SOUND_ARG_LOAD(3, 0, 0x41, 0xC0, 8)
#define SOUND_GENERAL_DOOR_INSERT_KEY                       SOUND_ARG_LOAD(3, 0, 0x42, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_QUIET_POUND2       SOUND_ARG_LOAD(3, 0, 0x43, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_BIG_POUND          SOUND_ARG_LOAD(3, 0, 0x44, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNK45              SOUND_ARG_LOAD(3, 0, 0x45, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNK46_LOWPRIO      SOUND_ARG_LOAD(3, 0, 0x46, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_UNK46              SOUND_ARG_LOAD(3, 0, 0x46, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_CANNON_UP          SOUND_ARG_LOAD(3, 0, 0x47, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_GRINDEL_ROLL       SOUND_ARG_LOAD(3, 0, 0x48, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_EXPLOSION7         0x3049
/* not verified */ #define SOUND_GENERAL_SHAKE_COFFIN       0x304A
/* not verified */ #define SOUND_GENERAL_RACE_GUN_SHOT      SOUND_ARG_LOAD(3, 1, 0x4D, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_STAR_DOOR_OPEN     SOUND_ARG_LOAD(3, 0, 0x4E, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_STAR_DOOR_CLOSE    SOUND_ARG_LOAD(3, 0, 0x4F, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_POUND_ROCK         SOUND_ARG_LOAD(3, 0, 0x56, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_STAR_APPEARS       SOUND_ARG_LOAD(3, 0, 0x57, 0xFF, 9)
#define SOUND_GENERAL_COLLECT_1UP                           SOUND_ARG_LOAD(3, 0, 0x58, 0xFF, 8)
/* not verified */ #define SOUND_GENERAL_BUTTON_PRESS_LOWPRIO   SOUND_ARG_LOAD(3, 0, 0x5A, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_BUTTON_PRESS       SOUND_ARG_LOAD(3, 0, 0x5A, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_BUTTON_PRESS_2_LOWPRIO SOUND_ARG_LOAD(3, 1, 0x5A, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_BUTTON_PRESS_2     SOUND_ARG_LOAD(3, 1, 0x5A, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_ELEVATOR_MOVE      SOUND_ARG_LOAD(3, 0, 0x5B, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_ELEVATOR_MOVE_2    SOUND_ARG_LOAD(3, 1, 0x5B, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_SWISH_AIR          SOUND_ARG_LOAD(3, 0, 0x5C, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_SWISH_AIR_2        SOUND_ARG_LOAD(3, 1, 0x5C, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_HAUNTED_CHAIR      SOUND_ARG_LOAD(3, 0, 0x5D, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_SOFT_LANDING       SOUND_ARG_LOAD(3, 0, 0x5E, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_HAUNTED_CHAIR_MOVE SOUND_ARG_LOAD(3, 0, 0x5F, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_BOWSER_PLATFORM    SOUND_ARG_LOAD(3, 0, 0x62, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_BOWSER_PLATFORM_2  SOUND_ARG_LOAD(3, 1, 0x62, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_HEART_SPIN         SOUND_ARG_LOAD(3, 0, 0x64, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_POUND_WOOD_POST    SOUND_ARG_LOAD(3, 0, 0x65, 0xC0, 8)
/* not verified */ #define SOUND_GENERAL_WATER_LEVEL_TRIG   SOUND_ARG_LOAD(3, 0, 0x66, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_SWITCH_DOOR_OPEN   SOUND_ARG_LOAD(3, 0, 0x67, 0xA0, 8)
/* not verified */ #define SOUND_GENERAL_RED_COIN           SOUND_ARG_LOAD(3, 0, 0x68, 0x90, 8)
/* not verified */ #define SOUND_GENERAL_BIRDS_FLY_AWAY     SOUND_ARG_LOAD(3, 0, 0x69, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_METAL_POUND        SOUND_ARG_LOAD(3, 0, 0x6B, 0x80, 8)
/* not verified */ #define SOUND_GENERAL_BOING1             SOUND_ARG_LOAD(3, 0, 0x6C, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_BOING2_LOWPRIO     SOUND_ARG_LOAD(3, 0, 0x6D, 0x20, 8)
/* not verified */ #define SOUND_GENERAL_BOING2             SOUND_ARG_LOAD(3, 0, 0x6D, 0x40, 8)
/* not verified */ #define SOUND_GENERAL_YOSHI_WALK         SOUND_ARG_LOAD(3, 0, 0x6E, 0x20, 8)
/* not verified */ #define SOUND_GENERAL_ENEMY_ALERT1       SOUND_ARG_LOAD(3, 0, 0x6F, 0x30, 8)
/* not verified */ #define SOUND_GENERAL_YOSHI_TALK         SOUND_ARG_LOAD(3, 0, 0x70, 0x30, 8)
/* not verified */ #define SOUND_GENERAL_SPLATTERING        SOUND_ARG_LOAD(3, 0, 0x71, 0x30, 8)
/* not verified */ #define SOUND_GENERAL_BOING3             0x3072
/* not verified */ #define SOUND_GENERAL_GRAND_STAR         SOUND_ARG_LOAD(3, 0, 0x73, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_GRAND_STAR_JUMP    SOUND_ARG_LOAD(3, 0, 0x74, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_BOAT_ROCK          SOUND_ARG_LOAD(3, 0, 0x75, 0x00, 8)
/* not verified */ #define SOUND_GENERAL_VANISH_SFX         SOUND_ARG_LOAD(3, 0, 0x76, 0x20, 8)

/* Environment Sound Effects */
/* not verified */ #define SOUND_ENV_WATERFALL1             SOUND_ARG_LOAD(4, 0, 0x00, 0x00, 0)
/* not verified */ #define SOUND_ENV_WATERFALL2             SOUND_ARG_LOAD(4, 0, 0x01, 0x00, 0)
/* not verified */ #define SOUND_ENV_ELEVATOR1              SOUND_ARG_LOAD(4, 0, 0x02, 0x00, 0)
/* not verified */ #define SOUND_ENV_DRONING1               SOUND_ARG_LOAD(4, 1, 0x03, 0x00, 0)
/* not verified */ #define SOUND_ENV_DRONING2               SOUND_ARG_LOAD(4, 0, 0x04, 0x00, 0)
/* not verified */ #define SOUND_ENV_WIND1                  SOUND_ARG_LOAD(4, 0, 0x05, 0x00, 0)
/* not verified */ #define SOUND_ENV_MOVING_SAND_SNOW       0x4006
/* not verified */ #define SOUND_ENV_UNK07                  0x4007
/* not verified */ #define SOUND_ENV_ELEVATOR2              SOUND_ARG_LOAD(4, 0, 0x08, 0x00, 0)
/* not verified */ #define SOUND_ENV_WATER                  SOUND_ARG_LOAD(4, 0, 0x09, 0x00, 0)
/* not verified */ #define SOUND_ENV_UNKNOWN2               SOUND_ARG_LOAD(4, 0, 0x0A, 0x00, 0)
/* not verified */ #define SOUND_ENV_BOAT_ROCKING1          SOUND_ARG_LOAD(4, 0, 0x0B, 0x00, 0)
/* not verified */ #define SOUND_ENV_ELEVATOR3              SOUND_ARG_LOAD(4, 0, 0x0C, 0x00, 0)
/* not verified */ #define SOUND_ENV_ELEVATOR4              SOUND_ARG_LOAD(4, 0, 0x0D, 0x00, 0)
/* not verified */ #define SOUND_ENV_ELEVATOR4_2            SOUND_ARG_LOAD(4, 1, 0x0D, 0x00, 0)
/* not verified */ #define SOUND_ENV_MOVINGSAND             SOUND_ARG_LOAD(4, 0, 0x0E, 0x00, 0)
/* not verified */ #define SOUND_ENV_MERRY_GO_ROUND_CREAKING    SOUND_ARG_LOAD(4, 0, 0x0F, 0x40, 0)
/* not verified */ #define SOUND_ENV_WIND2                  SOUND_ARG_LOAD(4, 0, 0x10, 0x80, 0)
/* not verified */ #define SOUND_ENV_UNK12                  0x4012
/* not verified */ #define SOUND_ENV_SLIDING                SOUND_ARG_LOAD(4, 0, 0x13, 0x00, 0)
/* not verified */ #define SOUND_ENV_STAR                   SOUND_ARG_LOAD(4, 0, 0x14, 0x00, 1)
/* not verified */ #define SOUND_ENV_UNKNOWN4               SOUND_ARG_LOAD(4, 1, 0x15, 0x00, 0)
/* not verified */ #define SOUND_ENV_WATER_DRAIN            SOUND_ARG_LOAD(4, 1, 0x16, 0x00, 0)
/* not verified */ #define SOUND_ENV_METAL_BOX_PUSH         SOUND_ARG_LOAD(4, 0, 0x17, 0x80, 0)
/* not verified */ #define SOUND_ENV_SINK_QUICKSAND         SOUND_ARG_LOAD(4, 0, 0x18, 0x80, 0)

/* Object Sound Effects */
#define SOUND_OBJ_SUSHI_SHARK_WATER_SOUND                   SOUND_ARG_LOAD(5, 0, 0x00, 0x80, 8)
#define SOUND_OBJ_MRI_SHOOT                                 SOUND_ARG_LOAD(5, 0, 0x01, 0x00, 8)
#define SOUND_OBJ_BABY_PENGUIN_WALK                         SOUND_ARG_LOAD(5, 0, 0x02, 0x00, 8)
#define SOUND_OBJ_BOWSER_WALK                               SOUND_ARG_LOAD(5, 0, 0x03, 0x00, 8)
#define SOUND_OBJ_BOWSER_TAIL_PICKUP                        SOUND_ARG_LOAD(5, 0, 0x05, 0x00, 8)
#define SOUND_OBJ_BOWSER_DEFEATED                           SOUND_ARG_LOAD(5, 0, 0x06, 0x00, 8)
#define SOUND_OBJ_BOWSER_SPINNING                           SOUND_ARG_LOAD(5, 0, 0x07, 0x00, 8)
#define SOUND_OBJ_BOWSER_INHALING                           SOUND_ARG_LOAD(5, 0, 0x08, 0x00, 8)
#define SOUND_OBJ_BIG_PENGUIN_WALK                          SOUND_ARG_LOAD(5, 0, 0x09, 0x80, 8)
#define SOUND_OBJ_BOO_BOUNCE_TOP                            SOUND_ARG_LOAD(5, 0, 0x0A, 0x00, 8)
#define SOUND_OBJ_BOO_LAUGH_SHORT                           SOUND_ARG_LOAD(5, 0, 0x0B, 0x00, 8)
#define SOUND_OBJ_THWOMP                                    SOUND_ARG_LOAD(5, 0, 0x0C, 0xA0, 8)
/* not verified */ #define SOUND_OBJ_CANNON1                SOUND_ARG_LOAD(5, 0, 0x0D, 0xF0, 8)
/* not verified */ #define SOUND_OBJ_CANNON2                SOUND_ARG_LOAD(5, 0, 0x0E, 0xF0, 8)
/* not verified */ #define SOUND_OBJ_CANNON3                SOUND_ARG_LOAD(5, 0, 0x0F, 0xF0, 8)
/* not verified */ #define SOUND_OBJ_JUMP_WALK_WATER        0x5012
/* not verified */ #define SOUND_OBJ_UNKNOWN2               SOUND_ARG_LOAD(5, 0, 0x13, 0x00, 8)
#define SOUND_OBJ_MRI_DEATH                                 SOUND_ARG_LOAD(5, 0, 0x14, 0x00, 8)
/* not verified */ #define SOUND_OBJ_POUNDING1              SOUND_ARG_LOAD(5, 0, 0x15, 0x50, 8)
/* not verified */ #define SOUND_OBJ_POUNDING1_HIGHPRIO     SOUND_ARG_LOAD(5, 0, 0x15, 0x80, 8)
#define SOUND_OBJ_WHOMP_LOWPRIO                             SOUND_ARG_LOAD(5, 0, 0x16, 0x60, 8)
#define SOUND_OBJ_KING_BOBOMB                               SOUND_ARG_LOAD(5, 0, 0x16, 0x80, 8)
/* not verified */ #define SOUND_OBJ_BULLY_METAL            SOUND_ARG_LOAD(5, 0, 0x17, 0x80, 8)
/* not verified */ #define SOUND_OBJ_BULLY_EXPLODE          SOUND_ARG_LOAD(5, 0, 0x18, 0xA0, 8)
/* not verified */ #define SOUND_OBJ_BULLY_EXPLODE_2        SOUND_ARG_LOAD(5, 1, 0x18, 0xA0, 8)
/* not verified */ #define SOUND_OBJ_POUNDING_CANNON        SOUND_ARG_LOAD(5, 0, 0x1A, 0x50, 8)
/* not verified */ #define SOUND_OBJ_BULLY_WALK             SOUND_ARG_LOAD(5, 0, 0x1B, 0x30, 8)
/* not verified */ #define SOUND_OBJ_UNKNOWN3               SOUND_ARG_LOAD(5, 0, 0x1D, 0x80, 8)
/* not verified */ #define SOUND_OBJ_UNKNOWN4               SOUND_ARG_LOAD(5, 0, 0x1E, 0xA0, 8)
#define SOUND_OBJ_BABY_PENGUIN_DIVE                         SOUND_ARG_LOAD(5, 0, 0x1F, 0x40, 8)
#define SOUND_OBJ_GOOMBA_WALK                               SOUND_ARG_LOAD(5, 0, 0x20, 0x00, 8)
#define SOUND_OBJ_UKIKI_CHATTER_LONG                        SOUND_ARG_LOAD(5, 0, 0x21, 0x00, 8)
#define SOUND_OBJ_MONTY_MOLE_ATTACK                         SOUND_ARG_LOAD(5, 0, 0x22, 0x00, 8)
#define SOUND_OBJ_EVIL_LAKITU_THROW                         SOUND_ARG_LOAD(5, 0, 0x22, 0x20, 8)
/* not verified */ #define SOUND_OBJ_UNK23                  0x5023
#define SOUND_OBJ_DYING_ENEMY1                              SOUND_ARG_LOAD(5, 0, 0x24, 0x40, 8)
/* not verified */ #define SOUND_OBJ_CANNON4                SOUND_ARG_LOAD(5, 0, 0x25, 0x40, 8)
/* not verified */ #define SOUND_OBJ_DYING_ENEMY2           0x5026
#define SOUND_OBJ_BOBOMB_WALK                               SOUND_ARG_LOAD(5, 0, 0x27, 0x00, 8)
/* not verified */ #define SOUND_OBJ_SOMETHING_LANDING      SOUND_ARG_LOAD(5, 0, 0x28, 0x80, 8)
/* not verified */ #define SOUND_OBJ_DIVING_IN_WATER        SOUND_ARG_LOAD(5, 0, 0x29, 0xA0, 8)
/* not verified */ #define SOUND_OBJ_SNOW_SAND1             SOUND_ARG_LOAD(5, 0, 0x2A, 0x00, 8)
/* not verified */ #define SOUND_OBJ_SNOW_SAND2             SOUND_ARG_LOAD(5, 0, 0x2B, 0x00, 8)
#define SOUND_OBJ_DEFAULT_DEATH                             SOUND_ARG_LOAD(5, 0, 0x2C, 0x80, 8)
#define SOUND_OBJ_BIG_PENGUIN_YELL                          SOUND_ARG_LOAD(5, 0, 0x2D, 0x00, 8)
#define SOUND_OBJ_WATER_BOMB_BOUNCING                       SOUND_ARG_LOAD(5, 0, 0x2E, 0x80, 8)
#define SOUND_OBJ_GOOMBA_ALERT                              SOUND_ARG_LOAD(5, 0, 0x2F, 0x00, 8)
#define SOUND_OBJ_WIGGLER_JUMP                              SOUND_ARG_LOAD(5, 0, 0x2F, 0x60, 8)
/* not verified */ #define SOUND_OBJ_STOMPED                SOUND_ARG_LOAD(5, 0, 0x30, 0x80, 8)
/* not verified */ #define SOUND_OBJ_UNKNOWN6               SOUND_ARG_LOAD(5, 0, 0x31, 0x00, 8)
/* not verified */ #define SOUND_OBJ_DIVING_INTO_WATER      SOUND_ARG_LOAD(5, 0, 0x32, 0x40, 8)
#define SOUND_OBJ_PIRANHA_PLANT_SHRINK                      SOUND_ARG_LOAD(5, 0, 0x33, 0x40, 8)
#define SOUND_OBJ_KOOPA_THE_QUICK_WALK                      SOUND_ARG_LOAD(5, 0, 0x34, 0x20, 8)
#define SOUND_OBJ_KOOPA_WALK                                SOUND_ARG_LOAD(5, 0, 0x35, 0x00, 8)
#define SOUND_OBJ_BULLY_WALKING                             SOUND_ARG_LOAD(5, 0, 0x36, 0x60, 8)
#define SOUND_OBJ_DORRIE                                    SOUND_ARG_LOAD(5, 0, 0x37, 0x60, 8)
#define SOUND_OBJ_BOWSER_LAUGH                              SOUND_ARG_LOAD(5, 0, 0x38, 0x80, 8)
#define SOUND_OBJ_UKIKI_CHATTER_SHORT                       SOUND_ARG_LOAD(5, 0, 0x39, 0x00, 8)
#define SOUND_OBJ_UKIKI_CHATTER_IDLE                        SOUND_ARG_LOAD(5, 0, 0x3A, 0x00, 8)
#define SOUND_OBJ_UKIKI_STEP_DEFAULT                        SOUND_ARG_LOAD(5, 0, 0x3B, 0x00, 8)
#define SOUND_OBJ_UKIKI_STEP_LEAVES                         SOUND_ARG_LOAD(5, 0, 0x3C, 0x00, 8)
#define SOUND_OBJ_KOOPA_TALK                                SOUND_ARG_LOAD(5, 0, 0x3D, 0xA0, 8)
#define SOUND_OBJ_KOOPA_DAMAGE                              SOUND_ARG_LOAD(5, 0, 0x3E, 0xA0, 8)
/* not verified */ #define SOUND_OBJ_KLEPTO1                SOUND_ARG_LOAD(5, 0, 0x3F, 0x40, 8)
/* not verified */ #define SOUND_OBJ_KLEPTO2                SOUND_ARG_LOAD(5, 0, 0x40, 0x60, 8)
#define SOUND_OBJ_KING_BOBOMB_TALK                          SOUND_ARG_LOAD(5, 0, 0x41, 0x00, 8)
#define SOUND_OBJ_KING_BOBOMB_JUMP                          SOUND_ARG_LOAD(5, 0, 0x46, 0x80, 8)
#define SOUND_OBJ_KING_WHOMP_DEATH                          SOUND_ARG_LOAD(5, 1, 0x47, 0xC0, 8)
#define SOUND_OBJ_BOO_LAUGH_LONG                            SOUND_ARG_LOAD(5, 0, 0x48, 0x00, 8)
/* not verified */ #define SOUND_OBJ_EEL                    SOUND_ARG_LOAD(5, 0, 0x4A, 0x00, 8)
/* not verified */ #define SOUND_OBJ_EEL_2                  SOUND_ARG_LOAD(5, 2, 0x4A, 0x00, 8)
#define SOUND_OBJ_EYEROK_SHOW_EYE                           SOUND_ARG_LOAD(5, 2, 0x4B, 0x00, 8)
#define SOUND_OBJ_MR_BLIZZARD_ALERT                         SOUND_ARG_LOAD(5, 0, 0x4C, 0x00, 8)
#define SOUND_OBJ_SNUFIT_SHOOT                              SOUND_ARG_LOAD(5, 0, 0x4D, 0x00, 8)
#define SOUND_OBJ_SKEETER_WALK                              SOUND_ARG_LOAD(5, 0, 0x4E, 0x00, 8)
/* not verified */ #define SOUND_OBJ_WALKING_WATER          SOUND_ARG_LOAD(5, 0, 0x4F, 0x00, 8)
#define SOUND_OBJ_BIRD_CHIRP3                               SOUND_ARG_LOAD(5, 0, 0x51, 0x40, 0)
#define SOUND_OBJ_PIRANHA_PLANT_APPEAR                      SOUND_ARG_LOAD(5, 0, 0x54, 0x20, 8)
#define SOUND_OBJ_FLAME_BLOWN                               SOUND_ARG_LOAD(5, 0, 0x55, 0x80, 8)
#define SOUND_OBJ_MAD_PIANO_CHOMPING                        SOUND_ARG_LOAD(5, 2, 0x56, 0x40, 8)
#define SOUND_OBJ_BOBOMB_BUDDY_TALK                         SOUND_ARG_LOAD(5, 0, 0x58, 0x40, 8)
/* not verified */ #define SOUND_OBJ_SPINY_UNK59            SOUND_ARG_LOAD(5, 0, 0x59, 0x10, 8)
#define SOUND_OBJ_WIGGLER_HIGH_PITCH                        SOUND_ARG_LOAD(5, 0, 0x5C, 0x40, 8)
#define SOUND_OBJ_HEAVEHO_TOSSED                            SOUND_ARG_LOAD(5, 0, 0x5D, 0x40, 8)
/* not verified */ #define SOUND_OBJ_WIGGLER_DEATH          0x505E
#define SOUND_OBJ_BOWSER_INTRO_LAUGH                        SOUND_ARG_LOAD(5, 0, 0x5F, 0x80, 9)
/* not verified */ #define SOUND_OBJ_ENEMY_DEATH_HIGH       SOUND_ARG_LOAD(5, 0, 0x60, 0xB0, 8)
/* not verified */ #define SOUND_OBJ_ENEMY_DEATH_LOW        SOUND_ARG_LOAD(5, 0, 0x61, 0xB0, 8)
#define SOUND_OBJ_SWOOP_DEATH                               SOUND_ARG_LOAD(5, 0, 0x62, 0xB0, 8)
#define SOUND_OBJ_KOOPA_FLYGUY_DEATH                        SOUND_ARG_LOAD(5, 0, 0x63, 0xB0, 8)
#define SOUND_OBJ_POKEY_DEATH                               SOUND_ARG_LOAD(5, 0, 0x63, 0xC0, 8)
/* not verified */ #define SOUND_OBJ_SNOWMAN_BOUNCE         SOUND_ARG_LOAD(5, 0, 0x64, 0xC0, 8)
#define SOUND_OBJ_SNOWMAN_EXPLODE                           SOUND_ARG_LOAD(5, 0, 0x65, 0xD0, 8)
/* not verified */ #define SOUND_OBJ_POUNDING_LOUD          SOUND_ARG_LOAD(5, 0, 0x68, 0x40, 8)
/* not verified */ #define SOUND_OBJ_MIPS_RABBIT            SOUND_ARG_LOAD(5, 0, 0x6A, 0x00, 8)
/* not verified */ #define SOUND_OBJ_MIPS_RABBIT_WATER      SOUND_ARG_LOAD(5, 0, 0x6C, 0x00, 8)
#define SOUND_OBJ_EYEROK_EXPLODE                            SOUND_ARG_LOAD(5, 0, 0x6D, 0x00, 8)
#define SOUND_OBJ_CHUCKYA_DEATH                             SOUND_ARG_LOAD(5, 1, 0x6E, 0x00, 8)
#define SOUND_OBJ_WIGGLER_TALK                              SOUND_ARG_LOAD(5, 0, 0x6F, 0x00, 8)
#define SOUND_OBJ_WIGGLER_ATTACKED                          SOUND_ARG_LOAD(5, 0, 0x70, 0x60, 8)
#define SOUND_OBJ_WIGGLER_LOW_PITCH                         SOUND_ARG_LOAD(5, 0, 0x71, 0x20, 8)
#define SOUND_OBJ_SNUFIT_SKEETER_DEATH                      SOUND_ARG_LOAD(5, 0, 0x72, 0xC0, 8)
#define SOUND_OBJ_BUBBA_CHOMP                               SOUND_ARG_LOAD(5, 0, 0x73, 0x40, 8)
#define SOUND_OBJ_ENEMY_DEFEAT_SHRINK                       SOUND_ARG_LOAD(5, 0, 0x74, 0x40, 8)

#define SOUND_AIR_BOWSER_SPIT_FIRE              SOUND_ARG_LOAD(6, 0, 0x00, 0x00, 0)
#define SOUND_AIR_UNK01                         0x6001 // ?
#define SOUND_AIR_LAKITU_FLY                    SOUND_ARG_LOAD(6, 0, 0x02, 0x80, 0)
#define SOUND_AIR_LAKITU_FLY_HIGHPRIO           SOUND_ARG_LOAD(6, 0, 0x02, 0xFF, 0)
#define SOUND_AIR_AMP_BUZZ                      SOUND_ARG_LOAD(6, 0, 0x03, 0x40, 0)
#define SOUND_AIR_BLOW_FIRE                     SOUND_ARG_LOAD(6, 0, 0x04, 0x80, 0)
#define SOUND_AIR_BLOW_WIND                     SOUND_ARG_LOAD(6, 0, 0x04, 0x40, 0)
#define SOUND_AIR_ROUGH_SLIDE                   SOUND_ARG_LOAD(6, 0, 0x05, 0x00, 0)
#define SOUND_AIR_HEAVEHO_MOVE                  SOUND_ARG_LOAD(6, 0, 0x06, 0x40, 0)
#define SOUND_AIR_UNK07                         0x6007 // ?
#define SOUND_AIR_BOBOMB_LIT_FUSE               SOUND_ARG_LOAD(6, 0, 0x08, 0x60, 0)
#define SOUND_AIR_HOWLING_WIND                  SOUND_ARG_LOAD(6, 0, 0x09, 0x80, 0)
#define SOUND_AIR_CHUCKYA_MOVE                  SOUND_ARG_LOAD(6, 0, 0x0A, 0x40, 0)
#define SOUND_AIR_PEACH_TWINKLE                 SOUND_ARG_LOAD(6, 0, 0x0B, 0x40, 0)
#define SOUND_AIR_CASTLE_OUTDOORS_AMBIENT       SOUND_ARG_LOAD(6, 0, 0x10, 0x40, 0)

/* Menu Sound Effects */
#define SOUND_MENU_CHANGE_SELECT                            SOUND_ARG_LOAD(7, 0, 0x00, 0xF8, 8)
/* not verified */ #define SOUND_MENU_REVERSE_PAUSE         0x7001
#define SOUND_MENU_PAUSE                                    SOUND_ARG_LOAD(7, 0, 0x02, 0xF0, 8)
#define SOUND_MENU_PAUSE_HIGHPRIO                           SOUND_ARG_LOAD(7, 0, 0x02, 0xFF, 8)
#define SOUND_MENU_PAUSE_2                                  SOUND_ARG_LOAD(7, 0, 0x03, 0xFF, 8)
#define SOUND_MENU_MESSAGE_APPEAR                           SOUND_ARG_LOAD(7, 0, 0x04, 0x00, 8)
#define SOUND_MENU_MESSAGE_DISAPPEAR                        SOUND_ARG_LOAD(7, 0, 0x05, 0x00, 8)
#define SOUND_MENU_CAMERA_ZOOM_IN                           SOUND_ARG_LOAD(7, 0, 0x06, 0x00, 8)
#define SOUND_MENU_CAMERA_ZOOM_OUT                          SOUND_ARG_LOAD(7, 0, 0x07, 0x00, 8)
#define SOUND_MENU_PINCH_MARIO_FACE                         SOUND_ARG_LOAD(7, 0, 0x08, 0x00, 8)
#define SOUND_MENU_LET_GO_MARIO_FACE                        SOUND_ARG_LOAD(7, 0, 0x09, 0x00, 8)
#define SOUND_MENU_HAND_APPEAR                              SOUND_ARG_LOAD(7, 0, 0x0A, 0x00, 8)
#define SOUND_MENU_HAND_DISAPPEAR                           SOUND_ARG_LOAD(7, 0, 0x0B, 0x00, 8)
/* not verified */ #define SOUND_MENU_UNK0C                 SOUND_ARG_LOAD(7, 0, 0x0C, 0x00, 8)
/* not verified */ #define SOUND_MENU_POWER_METER           SOUND_ARG_LOAD(7, 0, 0x0D, 0x00, 8)
#define SOUND_MENU_CAMERA_BUZZ                              SOUND_ARG_LOAD(7, 0, 0x0E, 0x00, 8)
#define SOUND_MENU_CAMERA_TURN                              SOUND_ARG_LOAD(7, 0, 0x0F, 0x00, 8)
/* not verified */ #define SOUND_MENU_UNK10                 0x7010
#define SOUND_MENU_CLICK_FILE_SELECT                        SOUND_ARG_LOAD(7, 0, 0x11, 0x00, 8)
/* not verified */ #define SOUND_MENU_MESSAGE_NEXT_PAGE     SOUND_ARG_LOAD(7, 0, 0x13, 0x00, 8)
#define SOUND_MENU_COIN_ITS_A_ME_MARIO                      SOUND_ARG_LOAD(7, 0, 0x14, 0x00, 8)
#define SOUND_MENU_YOSHI_GAIN_LIVES                         SOUND_ARG_LOAD(7, 0, 0x15, 0x00, 8)
#define SOUND_MENU_ENTER_PIPE                               SOUND_ARG_LOAD(7, 0, 0x16, 0xA0, 8)
#define SOUND_MENU_EXIT_PIPE                                SOUND_ARG_LOAD(7, 0, 0x17, 0xA0, 8)
#define SOUND_MENU_BOWSER_LAUGH                             SOUND_ARG_LOAD(7, 0, 0x18, 0x80, 8)
#define SOUND_MENU_ENTER_HOLE                               SOUND_ARG_LOAD(7, 1, 0x19, 0x80, 8)
/* not verified */ #define SOUND_MENU_CLICK_CHANGE_VIEW     SOUND_ARG_LOAD(7, 0, 0x1A, 0x80, 8)
/* not verified */ #define SOUND_MENU_CAMERA_UNUSED1        0x701B
/* not verified */ #define SOUND_MENU_CAMERA_UNUSED2        0x701C
/* not verified */ #define SOUND_MENU_MARIO_CASTLE_WARP     SOUND_ARG_LOAD(7, 0, 0x1D, 0xB0, 8)
#define SOUND_MENU_STAR_SOUND                               SOUND_ARG_LOAD(7, 0, 0x1E, 0xFF, 8)
#define SOUND_MENU_THANK_YOU_PLAYING_MY_GAME                SOUND_ARG_LOAD(7, 0, 0x1F, 0xFF, 8)
/* not verified */ #define SOUND_MENU_READ_A_SIGN           0x7020
/* not verified */ #define SOUND_MENU_EXIT_A_SIGN           0x7021
/* not verified */ #define SOUND_MENU_MARIO_CASTLE_WARP2    SOUND_ARG_LOAD(7, 0, 0x22, 0x20, 8)
#define SOUND_MENU_STAR_SOUND_OKEY_DOKEY                    SOUND_ARG_LOAD(7, 0, 0x23, 0xFF, 8)
#define SOUND_MENU_STAR_SOUND_LETS_A_GO                     SOUND_ARG_LOAD(7, 0, 0x24, 0xFF, 8)

// US/EU only; an index between 0-7 or 0-4 is added to the sound ID before
// playing, producing the same sound with different pitch.
#define SOUND_MENU_COLLECT_RED_COIN             SOUND_ARG_LOAD(7, 8, 0x28, 0x90, 8)
#define SOUND_MENU_COLLECT_SECRET               SOUND_ARG_LOAD(7, 0, 0x30, 0x20, 8)

// Channel 8 loads sounds from the same place as channel 3, making it possible
// to play two channel 3 sounds at once (since just one sound from each channel
// can play at a given time).
#define SOUND_GENERAL2_BOBOMB_EXPLOSION         SOUND_ARG_LOAD(8, 0, 0x2E, 0x20, 8)
#define SOUND_GENERAL2_PURPLE_SWITCH            SOUND_ARG_LOAD(8, 0, 0x3E, 0xC0, 8)
#define SOUND_GENERAL2_ROTATING_BLOCK_CLICK     SOUND_ARG_LOAD(8, 0, 0x40, 0x00, 8)
#define SOUND_GENERAL2_SPINDEL_ROLL             SOUND_ARG_LOAD(8, 0, 0x48, 0x20, 8)
#define SOUND_GENERAL2_PYRAMID_TOP_SPIN         SOUND_ARG_LOAD(8, 1, 0x4B, 0xE0, 8)
#define SOUND_GENERAL2_PYRAMID_TOP_EXPLOSION    SOUND_ARG_LOAD(8, 1, 0x4C, 0xF0, 8)
#define SOUND_GENERAL2_BIRD_CHIRP2              SOUND_ARG_LOAD(8, 0, 0x50, 0x40, 0)
#define SOUND_GENERAL2_SWITCH_TICK_FAST         SOUND_ARG_LOAD(8, 0, 0x54, 0xF0, 1)
#define SOUND_GENERAL2_SWITCH_TICK_SLOW         SOUND_ARG_LOAD(8, 0, 0x55, 0xF0, 1)
#define SOUND_GENERAL2_STAR_APPEARS             SOUND_ARG_LOAD(8, 0, 0x57, 0xFF, 9)
#define SOUND_GENERAL2_ROTATING_BLOCK_ALERT     SOUND_ARG_LOAD(8, 0, 0x59, 0x00, 8)
#define SOUND_GENERAL2_BOWSER_EXPLODE           SOUND_ARG_LOAD(8, 0, 0x60, 0x00, 8)
#define SOUND_GENERAL2_BOWSER_KEY               SOUND_ARG_LOAD(8, 0, 0x61, 0x00, 8)
#define SOUND_GENERAL2_1UP_APPEAR               SOUND_ARG_LOAD(8, 0, 0x63, 0xD0, 8)
#define SOUND_GENERAL2_RIGHT_ANSWER             SOUND_ARG_LOAD(8, 0, 0x6A, 0xA0, 8)

// Channel 9 loads sounds from the same place as channel 5.
#define SOUND_OBJ2_BOWSER_ROAR                  SOUND_ARG_LOAD(9, 0, 0x04, 0x00, 8)
#define SOUND_OBJ2_PIRANHA_PLANT_BITE           SOUND_ARG_LOAD(9, 0, 0x10, 0x50, 8)
#define SOUND_OBJ2_PIRANHA_PLANT_DYING          SOUND_ARG_LOAD(9, 0, 0x11, 0x60, 8)
#define SOUND_OBJ2_BOWSER_PUZZLE_PIECE_MOVE     SOUND_ARG_LOAD(9, 0, 0x19, 0x20, 8)
#define SOUND_OBJ2_BULLY_ATTACKED               SOUND_ARG_LOAD(9, 0, 0x1C, 0x00, 8)
#define SOUND_OBJ2_KING_BOBOMB_DAMAGE           SOUND_ARG_LOAD(9, 1, 0x42, 0x40, 8)
#define SOUND_OBJ2_SCUTTLEBUG_WALK              SOUND_ARG_LOAD(9, 0, 0x43, 0x40, 8)
#define SOUND_OBJ2_SCUTTLEBUG_ALERT             SOUND_ARG_LOAD(9, 0, 0x44, 0x40, 8)
#define SOUND_OBJ2_BABY_PENGUIN_YELL            SOUND_ARG_LOAD(9, 0, 0x45, 0x00, 8)
#define SOUND_OBJ2_SWOOP                        SOUND_ARG_LOAD(9, 0, 0x49, 0x00, 8)
#define SOUND_OBJ2_BIRD_CHIRP1                  SOUND_ARG_LOAD(9, 0, 0x52, 0x40, 0)
#define SOUND_OBJ2_LARGE_BULLY_ATTACKED         SOUND_ARG_LOAD(9, 0, 0x57, 0x00, 8)
#define SOUND_OBJ2_EYEROK_SOUND_SHORT           SOUND_ARG_LOAD(9, 3, 0x5A, 0x00, 8)
#define SOUND_OBJ2_WHOMP_SOUND_SHORT            SOUND_ARG_LOAD(9, 3, 0x5A, 0xC0, 8)
#define SOUND_OBJ2_EYEROK_SOUND_LONG            SOUND_ARG_LOAD(9, 2, 0x5B, 0x00, 8)
#define SOUND_OBJ2_BOWSER_TELEPORT              SOUND_ARG_LOAD(9, 0, 0x66, 0x80, 8)
#define SOUND_OBJ2_MONTY_MOLE_APPEAR            SOUND_ARG_LOAD(9, 0, 0x67, 0x80, 8)
#define SOUND_OBJ2_BOSS_DIALOG_GRUNT            SOUND_ARG_LOAD(9, 0, 0x69, 0x40, 8)
#define SOUND_OBJ2_MRI_SPINNING                 SOUND_ARG_LOAD(9, 0, 0x6B, 0x00, 8)

#endif /* AUDIO_DEFINES_H */
