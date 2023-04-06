#include <ultra64.h>

#include "sm64.h"
#include "engine/behavior_script.h"
#include "object_helpers.h"
#include "audio/external.h"
#include "spawn_sound.h"
#include "object_list_processor.h"
#include "behavior_data.h"
#include "engine/graph_node.h"
#include "thread6.h"

/*
 * execute an object's current sound state with a provided array
 * of sound states. Used for the stepping sounds of various
 * objects. (King Bobomb, Bowser, King Whomp)
 */
void exec_anim_sound_state(struct SoundState *soundStates) {
    s32 stateIdx = gCurrentObject->oSoundStateID;

    switch (soundStates[stateIdx].playSound) {
        // since we have an array of sound states cooresponding to
        // various behaviors, not all entries intend to play sounds. the
        // boolean being 0 for unused entries skips these states.
        case FALSE:
            break;
        case TRUE: {
            s32 animFrame;

            // in the sound state information, -1 (0xFF) is for empty
            // animFrame entries. These checks skips them.
            if ((animFrame = soundStates[stateIdx].animFrame1) >= 0) {
                if (cur_obj_check_anim_frame(animFrame)) {
                    cur_obj_play_sound_2(soundStates[stateIdx].soundMagic);
                }
            }

            if ((animFrame = soundStates[stateIdx].animFrame2) >= 0) {
                if (cur_obj_check_anim_frame(animFrame)) {
                    cur_obj_play_sound_2(soundStates[stateIdx].soundMagic);
                }
            }
        } break;
    }
}

/*
 * Create a sound spawner for objects that need a sound play once.
 * (Breakable walls, King Bobomb exploding, etc)
 */
void create_sound_spawner(s32 soundMagic) {
    struct Object *obj = spawn_object(gCurrentObject, 0, bhvSoundSpawner);

    obj->oSoundEffectUnkF4 = soundMagic;
}

/*
 * The following 2 functions are relevent to the sound state function
 * above. While only cur_obj_play_sound_2 is used, they may have been intended as
 * seperate left/right leg functions that went unused.
 */
void cur_obj_play_sound_1(s32 soundMagic) {
    if (gCurrentObject->header.gfx.node.flags & GRAPH_RENDER_ACTIVE) {
        play_sound(soundMagic, gCurrentObject->header.gfx.cameraToObject);
    }
}

void cur_obj_play_sound_2(s32 soundMagic) {
    if (gCurrentObject->header.gfx.node.flags & GRAPH_RENDER_ACTIVE) {
        play_sound(soundMagic, gCurrentObject->header.gfx.cameraToObject);
#ifdef VERSION_SH
        if (soundMagic == SOUND_OBJ_BOWSER_WALK) {
            queue_rumble_data(3, 60);
        }
        if (soundMagic == SOUND_OBJ_POUNDING_LOUD) {
            queue_rumble_data(3, 60);
        }
        if (soundMagic == SOUND_OBJ_WHOMP_LOWPRIO) {
            queue_rumble_data(5, 80);
        }
#endif
    }
}

/*
 * These 2 functions below are completely unreferenced in all versions
 * of Super Mario 64. They are likely functions which facilitated
 * calculation of distance of an object to volume, since these are
 * common implementations of such a concept, and the fact they are
 * adjacent to other sound functions. The fact there are 2 functions
 * might show that the developers were testing several ranges, or certain
 * objects had different ranges, or had these for other unknown purposes.
 * Technically, these functions are only educated guesses. Trust these
 * interpretations at your own discrection.
 */
int calc_dist_to_volume_range_1(f32 distance) // range from 60-124
{
    s32 volume;

    if (distance < 500.0f) {
        volume = 127;
    } else if (1500.0f < distance) {
        volume = 0;
    } else {
        volume = (((distance - 500.0f) / 1000.0f) * 64.0f) + 60.0f;
    }

    return volume;
}

int calc_dist_to_volume_range_2(f32 distance) // range from 79.2-143.2
{
    s32 volume;

    if (distance < 1300.0f) {
        volume = 127;
    } else if (2300.0f < distance) {
        volume = 0;
    } else {
        volume = (((distance - 1000.0f) / 1000.0f) * 64.0f) + 60.0f;
    }

    return volume;
}
