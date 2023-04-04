#ifndef CONFIG_H
#define CONFIG_H

/**
 * @file config.h
 * A catch-all file for configuring various bugfixes and other settings
 * (maybe eventually) in SM64
 */

// Bug Fixes
// --| Post-JP Version Nintendo Bug Fixes
/// Fixes bug where obtaining over 999 coins sets the number of lives to 999 (or -25)
#define BUGFIX_MAX_LIVES (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug where the Boss music won't fade out after defeating King Bob-omb
#define BUGFIX_KING_BOB_OMB_FADE_MUSIC (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug in Bob-Omb Battlefield where entering a warp stops the Koopa race music
#define BUGFIX_KOOPA_RACE_MUSIC (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug where Piranha Plants do not reset their action state when the
/// player exits their activation radius.
#define BUGFIX_PIRANHA_PLANT_STATE_RESET (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug where sleeping Piranha Plants damage players that bump into them
#define BUGFIX_PIRANHA_PLANT_SLEEP_DAMAGE (0 || VERSION_US || VERSION_SH)
/// Fixes bug where it shows a star when you grab a key in bowser battle stages
#define BUGFIX_STAR_BOWSER_KEY (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug that enables Mario in time stop even if is not ready to speak
#define BUGFIX_DIALOG_TIME_STOP (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug that causes Mario to still collide with Bowser in BitS after his defeat
#define BUGFIX_BOWSER_COLLIDE_BITS_DEAD (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug where Bowser wouldn't reset his speed when fallen off (and adds missing checks)
#define BUGFIX_BOWSER_FALLEN_OFF_STAGE (0 || VERSION_US || VERSION_EU || VERSION_SH)
/// Fixes bug where Bowser would look weird while fading out
#define BUGFIX_BOWSER_FADING_OUT (0 || VERSION_US || VERSION_EU || VERSION_SH)

// Support Rumble Pak
#define ENABLE_RUMBLE (0 || VERSION_SH)

// Screen Size Defines
#define SCREEN_WIDTH 320
#define SCREEN_HEIGHT 240

// Border Height Define for NTSC Versions
#ifdef TARGET_N64
#ifndef VERSION_EU
#define BORDER_HEIGHT 8
#else
#define BORDER_HEIGHT 1
#endif
#else
// What's the point of having a border?
#define BORDER_HEIGHT 0
#endif

#endif // CONFIG_H
