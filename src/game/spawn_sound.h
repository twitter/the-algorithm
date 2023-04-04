#ifndef SPAWN_SOUND_H
#define SPAWN_SOUND_H

#include <PR/ultratypes.h>

struct SoundState {
    s16 playSound; // if this is 1, the info below will
                   // be used to determine the sound behavior
                   // for the object. if 0, this is skipped.
    s8 animFrame1; // the sound state provides 2 anim frames for
                   // when the provided sound can be played.
                   // these 2 variables probably coorespond to
                   // left/right foot.
    s8 animFrame2;
    s32 soundMagic;
};

void cur_obj_play_sound_1(s32 soundMagic);
void cur_obj_play_sound_2(s32 soundMagic);
void create_sound_spawner(s32 soundMagic);
void exec_anim_sound_state(struct SoundState *soundStates);

#endif // SPAWN_SOUND_H
