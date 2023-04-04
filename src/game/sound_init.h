#ifndef SOUND_INIT_H
#define SOUND_INIT_H

#include <PR/ultratypes.h>

#include "macros.h"

#define SOUND_MENU_FLAG_HANDAPPEAR              0x01
#define SOUND_MENU_FLAG_HANDISAPPEAR            0x02
#define SOUND_MENU_FLAG_UNKNOWN1                0x04
#define SOUND_MENU_FLAG_PINCHMARIOFACE          0x08
#define SOUND_MENU_FLAG_PINCHMARIOFACE2         0x10
#define SOUND_MENU_FLAG_LETGOMARIOFACE          0x20
#define SOUND_MENU_FLAG_CAMERAZOOMIN            0x40
#define SOUND_MENU_FLAG_CAMERAZOOMOUT           0x80

#define SOUND_MENU_MODE_STEREO       0
#define SOUND_MENU_MODE_MONO         1
#define SOUND_MENU_MODE_HEADSET      2

void reset_volume(void);
void raise_background_noise(s32 a);
void lower_background_noise(s32 a);
void disable_background_sound(void);
void enable_background_sound(void);
void set_sound_mode(u16 soundMode);
void play_menu_sounds(s16 soundMenuFlags);
void play_painting_eject_sound(void);
void play_infinite_stairs_music(void);
void set_background_music(u16 a, u16 seqArgs, s16 fadeTimer);
void fadeout_music(s16 fadeOutTime);
void fadeout_level_music(s16 fadeTimer);
void play_cutscene_music(u16 seqArgs);
void play_shell_music(void);
void stop_shell_music(void);
void play_cap_music(u16 seqArgs);
void fadeout_cap_music(void);
void stop_cap_music(void);
void audio_game_loop_tick(void);
void thread4_sound(UNUSED void *arg);

#endif // SOUND_INIT_H
