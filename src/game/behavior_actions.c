#include <ultra64.h>

#include "sm64.h"
#include "types.h"
#include "behavior_actions.h"
#include "game_init.h"
#include "main.h"
#include "mario.h"
#include "engine/behavior_script.h"
#include "engine/math_util.h"
#include "object_helpers.h"
#include "behavior_data.h"
#include "obj_behaviors.h"
#include "engine/surface_collision.h"
#include "engine/surface_load.h"
#include "level_update.h"
#include "audio/external.h"
#include "seq_ids.h"
#include "dialog_ids.h"
#include "save_file.h"
#include "area.h"
#include "engine/graph_node.h"
#include "camera.h"
#include "spawn_object.h"
#include "mario_actions_cutscene.h"
#include "object_list_processor.h"
#include "spawn_sound.h"
#include "debug.h"
#include "object_constants.h"
#include "mario_step.h"
#include "obj_behaviors_2.h"
#include "paintings.h"
#include "platform_displacement.h"
#include "interaction.h"
#include "ingame_menu.h"
#include "rendering_graph_node.h"
#include "level_table.h"
#include "thread6.h"

#define o gCurrentObject

// BSS
s16 D_8035FF10;

struct WFRotatingPlatformData {
    s16 pad;
    s16 scale;
    const Collision *collisionData;
    s16 collisionDistance;
};

struct Struct8032F34C {
    s16 numBridgeSections;
    s16 bridgeRelativeStartingXorZ;
    s16 platformWidth;
    s16 model;
    const void *segAddr;
};

struct Struct8032F698 {
    void *unk0;
    s16 unk1;
    s16 unk2;
    s16 unk3;
    s16 unk4;
};

struct Struct802C0DF0 {
    u8 unk0;
    u8 unk1;
    u8 unk2;
    u8 model;
    const BehaviorScript *behavior;
};

struct Struct8032F754 {
    s32 unk0;
    Vec3f unk1;
    f32 unk2;
};

struct Struct8032FCE8 {
    s16 unk0;
    s16 unk1;
    void *unk2;
};

extern void bhv_pole_base_loop();
extern s16 gDebugInfo[][8];
extern s8 gDoorAdjacentRooms[][2];
extern u8 inside_castle_seg7_collision_ddd_warp_2[];
extern u8 inside_castle_seg7_collision_ddd_warp[];
extern s32 gDialogResponse;
extern s32 gObjCutsceneDone;
extern u8 gRecentCutscene;
extern s8 *D_8032F96C[];
extern s16 bowser_seg6_unkmoveshorts_060576FC[];
extern struct Animation *blue_fish_seg3_anims_0301C2B0[];
extern struct Animation *cyan_fish_seg6_anims_0600E264[];
extern struct Animation *blue_fish_seg3_anims_0301C2B0[];

void common_anchor_mario_behavior(f32, f32, s32);

s32 mario_moving_fast_enough_to_make_piranha_plant_bite(void);
void obj_set_secondary_camera_focus(void);

s32 D_8032F0C0[] = { SAVE_FLAG_HAVE_WING_CAP, SAVE_FLAG_HAVE_METAL_CAP, SAVE_FLAG_HAVE_VANISH_CAP };

// Boo Roll
s16 D_8032F0CC[] = { 6047, 5664, 5292, 4934, 4587, 4254, 3933, 3624, 3329, 3046, 2775,
                     2517, 2271, 2039, 1818, 1611, 1416, 1233, 1063, 906,  761,  629,
                     509,  402,  308,  226,  157,  100,  56,   25,   4,    0 };

#include "behaviors/star_door.inc.c"
#include "behaviors/mr_i.inc.c"
#include "behaviors/pole.inc.c"
#include "behaviors/thi_top.inc.c"
#include "behaviors/capswitch.inc.c"
#include "behaviors/king_bobomb.inc.c"
#include "behaviors/beta_chest.inc.c"
#include "behaviors/water_objs.inc.c"
#include "behaviors/cannon.inc.c"
#include "behaviors/chuckya.inc.c"
#include "behaviors/breakable_wall.inc.c"
#include "behaviors/kickable_board.inc.c"
#include "behaviors/tower_door.inc.c"
#include "behaviors/rotating_platform.inc.c"
#include "behaviors/koopa_shell_underwater.inc.c"
#include "behaviors/warp.inc.c"
#include "behaviors/white_puff_explode.inc.c"

// not in behavior file
struct SpawnParticlesInfo D_8032F270 = { 2, 20, MODEL_MIST, 0, 40, 5, 30, 20, 252, 30, 330.0f, 10.0f };

// generate_wind_puffs/dust (something like that)
void spawn_mist_particles_variable(s32 sp18, s32 sp1C, f32 sp20) {
    D_8032F270.sizeBase = sp20;
    D_8032F270.sizeRange = sp20 / 20.0;
    D_8032F270.offsetY = sp1C;
    if (sp18 == 0) {
        D_8032F270.count = 20;
    } else if (sp18 > 20) {
        D_8032F270.count = sp18;
    } else {
        D_8032F270.count = 4;
    }
    cur_obj_spawn_particles(&D_8032F270);
}

#include "behaviors/sparkle_spawn_star.inc.c"
#include "behaviors/coin.inc.c"
#include "behaviors/collide_particles.inc.c"
#include "behaviors/door.inc.c"
#include "behaviors/thwomp.inc.c"
#include "behaviors/tumbling_bridge.inc.c"
#include "behaviors/elevator.inc.c"
#include "behaviors/water_mist_particle.inc.c"
#include "behaviors/break_particles.inc.c"
#include "behaviors/water_mist.inc.c"
#include "behaviors/ground_particles.inc.c"
#include "behaviors/wind.inc.c"
#include "behaviors/unused_particle_spawn.inc.c"
#include "behaviors/ukiki_cage.inc.c"
#include "behaviors/falling_rising_platform.inc.c"
#include "behaviors/fishing_boo.inc.c"
#include "behaviors/flamethrower.inc.c"
#include "behaviors/bouncing_fireball.inc.c"
#include "behaviors/shock_wave.inc.c"
#include "behaviors/flame_mario.inc.c"
#include "behaviors/beta_fish_splash_spawner.inc.c"
#include "behaviors/spindrift.inc.c"
#include "behaviors/tower_platform.inc.c"
#include "behaviors/tree_particles.inc.c"
#include "behaviors/square_platform_cycle.inc.c"
#include "behaviors/piranha_bubbles.inc.c"
#include "behaviors/purple_switch.inc.c"
#include "behaviors/metal_box.inc.c"
#include "behaviors/switch_hidden_objects.inc.c"
#include "behaviors/breakable_box.inc.c"

// not sure what this is doing here. not in a behavior file.
Gfx *geo_move_mario_part_from_parent(s32 run, UNUSED struct GraphNode *node, Mat4 mtx) {
    Mat4 sp20;
    struct Object *sp1C;

    if (run == TRUE) {
        sp1C = (struct Object *) gCurGraphNodeObject;
        if (sp1C == gMarioObject && sp1C->prevObj != NULL) {
            create_transformation_from_matrices(sp20, mtx, gCurGraphNodeCamera->matrixPtr);
            obj_update_pos_from_parent_transformation(sp20, sp1C->prevObj);
            obj_set_gfx_pos_from_pos(sp1C->prevObj);
        }
    }
    return NULL;
}

#include "behaviors/heave_ho.inc.c"
#include "behaviors/spawn_star_exit.inc.c"
#include "behaviors/unused_poundable_platform.inc.c"
#include "behaviors/beta_trampoline.inc.c"
#include "behaviors/jumping_box.inc.c"
#include "behaviors/boo_cage.inc.c"

// not in behavior file
// n is the number of objects to spawn, r if the rate of change of phase (frequency?)
void spawn_sparkle_particles(s32 n, s32 a1, s32 a2, s32 r) {
    s32 i;
    s16 separation = 0x10000 / n; // Evenly spread around a circle
    for (i = 0; i < n; i++) {
        spawn_object_relative(0, sins(D_8035FF10 + i * separation) * a1, (i + 1) * a2,
                              coss(D_8035FF10 + i * separation) * a1, o, MODEL_NONE, bhvSparkleSpawn);
    }

  if (1)
  {
  }

    D_8035FF10 += r * 0x100;
}

#include "behaviors/beta_boo_key.inc.c"
#include "behaviors/grand_star.inc.c"
#include "behaviors/bowser_key.inc.c"
#include "behaviors/bullet_bill.inc.c"
#include "behaviors/bowser.inc.c"
#include "behaviors/blue_fish.inc.c"

// Not in behavior file, duplicate of vec3f_copy except without bad return.
// Used in a few behavior files.
void vec3f_copy_2(Vec3f dest, Vec3f src) {
    dest[0] = src[0];
    dest[1] = src[1];
    dest[2] = src[2];
}

#include "behaviors/checkerboard_platform.inc.c"
#include "behaviors/ddd_warp.inc.c"
#include "behaviors/water_pillar.inc.c"
#include "behaviors/moat_drainer.inc.c"
#include "behaviors/bowser_key_cutscene.inc.c"
#include "behaviors/moat_grill.inc.c"
#include "behaviors/clock_arm.inc.c"
#include "behaviors/ukiki.inc.c"
#include "behaviors/lll_octagonal_rotating_mesh.inc.c"
#include "behaviors/lll_sinking_rock_block.inc.c"
#include "behaviors/lll_rotating_hex_flame.inc.c"
#include "behaviors/lll_floating_wood_piece.inc.c"
#include "behaviors/lll_volcano_flames.inc.c"
#include "behaviors/lll_hexagonal_ring.inc.c"
#include "behaviors/lll_sinking_rectangle.inc.c"
#include "behaviors/tilting_inverted_pyramid.inc.c"
#include "behaviors/koopa_shell.inc.c"
#include "behaviors/tox_box.inc.c"
#include "behaviors/piranha_plant.inc.c"
#include "behaviors/bowser_puzzle_piece.inc.c"

s32 set_obj_anim_with_accel_and_sound(s16 a0, s16 a1, s32 a2) {
    f32 sp1C;
    if ((sp1C = o->header.gfx.unk38.animAccel / (f32) 0x10000) == 0)
        sp1C = 1.0f;
    if (cur_obj_check_anim_frame_in_range(a0, sp1C) || cur_obj_check_anim_frame_in_range(a1, sp1C)) {
        cur_obj_play_sound_2(a2);
        return 1;
    }
    return 0;
}

#include "behaviors/tuxie.inc.c"
#include "behaviors/fish.inc.c"
#include "behaviors/express_elevator.inc.c"
#include "behaviors/bub.inc.c"
#include "behaviors/exclamation_box.inc.c"
#include "behaviors/sound_spawner.inc.c"
#include "behaviors/ddd_sub.inc.c"
#include "behaviors/sushi.inc.c"
#include "behaviors/jrb_ship.inc.c"
#include "behaviors/white_puff.inc.c"
#include "behaviors/blue_coin.inc.c"
#include "behaviors/grill_door.inc.c"
#include "behaviors/wdw_water_level.inc.c"
#include "behaviors/tweester.inc.c"
#include "behaviors/boo.inc.c"
#include "behaviors/bbh_tilting_trap.inc.c"
#include "behaviors/bbh_haunted_bookshelf.inc.c"
#include "behaviors/bbh_merry_go_round.inc.c"
#include "behaviors/static_checkered_platform.inc.c"
#include "behaviors/beta_bowser_anchor.inc.c"
#ifndef VERSION_JP
#include "behaviors/music_touch.inc.c"
#endif
#include "behaviors/castle_floor_trap.inc.c"
#include "behaviors/pole_base.inc.c"
#include "behaviors/sparkle_spawn.inc.c"
#include "behaviors/scuttlebug.inc.c" // :scuttleeyes:
#include "behaviors/whomp.inc.c"
#include "behaviors/water_splashes_and_waves.inc.c"
#include "behaviors/strong_wind_particle.inc.c"
#include "behaviors/sl_snowman_wind.inc.c"
#include "behaviors/sl_walking_penguin.inc.c"
