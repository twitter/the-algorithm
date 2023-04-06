#ifndef _SOUND_INIT_H
#define _SOUND_INIT_H

#include "types.h"

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

extern void reset_volume(void);
extern void raise_background_noise(s32);
extern void lower_background_noise(s32);
extern void disable_background_sound(void);
extern void enable_background_sound(void);
extern void set_sound_mode(u16);
extern void play_menu_sounds(s16 a);
extern void play_painting_eject_sound(void);
extern void play_infinite_stairs_music(void);
extern void set_background_music(u16, u16, s16);
extern void fadeout_music(s16);
extern void fadeout_level_music(s16 fadeTimer);
extern void play_cutscene_music(u16);
extern void play_shell_music(void);
extern void stop_shell_music(void);
extern void play_cap_music(u16);
extern void fadeout_cap_music(void);
extern void stop_cap_music(void);
extern void audio_game_loop_tick(void);
extern void thread4_sound(void *);

#endif /* _SOUND_INIT_H */
