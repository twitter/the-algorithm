#ifndef INTERACTION_H
#define INTERACTION_H

#include <PR/ultratypes.h>

#include "types.h"

#define INTERACT_HOOT           /* 0x00000001 */ (1 <<  0)
#define INTERACT_GRABBABLE      /* 0x00000002 */ (1 <<  1)
#define INTERACT_DOOR           /* 0x00000004 */ (1 <<  2)
#define INTERACT_DAMAGE         /* 0x00000008 */ (1 <<  3)
#define INTERACT_COIN           /* 0x00000010 */ (1 <<  4)
#define INTERACT_CAP            /* 0x00000020 */ (1 <<  5)
#define INTERACT_POLE           /* 0x00000040 */ (1 <<  6)
#define INTERACT_KOOPA          /* 0x00000080 */ (1 <<  7)
#define INTERACT_UNKNOWN_08     /* 0x00000100 */ (1 <<  8)
#define INTERACT_BREAKABLE      /* 0x00000200 */ (1 <<  9)
#define INTERACT_STRONG_WIND    /* 0x00000400 */ (1 << 10)
#define INTERACT_WARP_DOOR      /* 0x00000800 */ (1 << 11)
#define INTERACT_STAR_OR_KEY    /* 0x00001000 */ (1 << 12)
#define INTERACT_WARP           /* 0x00002000 */ (1 << 13)
#define INTERACT_CANNON_BASE    /* 0x00004000 */ (1 << 14)
#define INTERACT_BOUNCE_TOP     /* 0x00008000 */ (1 << 15)
#define INTERACT_WATER_RING     /* 0x00010000 */ (1 << 16)
#define INTERACT_BULLY          /* 0x00020000 */ (1 << 17)
#define INTERACT_FLAME          /* 0x00040000 */ (1 << 18)
#define INTERACT_KOOPA_SHELL    /* 0x00080000 */ (1 << 19)
#define INTERACT_BOUNCE_TOP2    /* 0x00100000 */ (1 << 20)
#define INTERACT_MR_BLIZZARD    /* 0x00200000 */ (1 << 21)
#define INTERACT_HIT_FROM_BELOW /* 0x00400000 */ (1 << 22)
#define INTERACT_TEXT           /* 0x00800000 */ (1 << 23)
#define INTERACT_TORNADO        /* 0x01000000 */ (1 << 24)
#define INTERACT_WHIRLPOOL      /* 0x02000000 */ (1 << 25)
#define INTERACT_CLAM_OR_BUBBA  /* 0x04000000 */ (1 << 26)
#define INTERACT_BBH_ENTRANCE   /* 0x08000000 */ (1 << 27)
#define INTERACT_SNUFIT_BULLET  /* 0x10000000 */ (1 << 28)
#define INTERACT_SHOCK          /* 0x20000000 */ (1 << 29)
#define INTERACT_IGLOO_BARRIER  /* 0x40000000 */ (1 << 30)
#define INTERACT_UNKNOWN_31     /* 0x80000000 */ (1 << 31)


// INTERACT_WARP
#define INT_SUBTYPE_FADING_WARP 0x00000001

// Damaging interactions
#define INT_SUBTYPE_DELAY_INVINCIBILITY 0x00000002
#define INT_SUBTYPE_BIG_KNOCKBACK 0x00000008 /* Used by Bowser, sets Mario's forward velocity to 40 on hit */

// INTERACT_GRABBABLE
#define INT_SUBTYPE_GRABS_MARIO 0x00000004 /* Also makes the object heavy */
#define INT_SUBTYPE_HOLDABLE_NPC 0x00000010 /* Allows the object to be gently dropped, and sets vertical speed to 0 when dropped with no forwards velocity */
#define INT_SUBTYPE_DROP_IMMEDIATELY 0x00000040 /* This gets set by grabbable NPCs that talk to Mario to make him drop them after the dialog is finished */
#define INT_SUBTYPE_KICKABLE 0x00000100
#define INT_SUBTYPE_NOT_GRABBABLE 0x00000200 /* Used by Heavy-Ho to allow it to throw Mario, without Mario being able to pick it up */

// INTERACT_DOOR
#define INT_SUBTYPE_STAR_DOOR 0x00000020

//INTERACT_BOUNCE_TOP
#define INT_SUBTYPE_TWIRL_BOUNCE 0x00000080

// INTERACT_STAR_OR_KEY
#define INT_SUBTYPE_NO_EXIT 0x00000400
#define INT_SUBTYPE_GRAND_STAR 0x00000800

// INTERACT_TEXT
#define INT_SUBTYPE_SIGN 0x00001000
#define INT_SUBTYPE_NPC 0x00004000

// INTERACT_CLAM_OR_BUBBA
#define INT_SUBTYPE_EATS_MARIO 0x00002000


#define ATTACK_PUNCH                 1
#define ATTACK_KICK_OR_TRIP          2
#define ATTACK_FROM_ABOVE            3
#define ATTACK_GROUND_POUND_OR_TWIRL 4
#define ATTACK_FAST_ATTACK           5
#define ATTACK_FROM_BELOW            6

#define INT_STATUS_ATTACK_MASK 0x000000FF

// Mario Interaction Status
#define INT_STATUS_MARIO_STUNNED         (1 <<  0) /* 0x00000001 */
#define INT_STATUS_MARIO_KNOCKBACK_DMG   (1 <<  1) /* 0x00000002 */
#define INT_STATUS_MARIO_UNK2            (1 <<  2) /* 0x00000004 */
#define INT_STATUS_MARIO_DROP_OBJECT     (1 <<  3) /* 0x00000008 */
#define INT_STATUS_MARIO_SHOCKWAVE       (1 <<  4) /* 0x00000010 */
#define INT_STATUS_MARIO_UNK5            (1 <<  5) /* 0x00000020 */
#define INT_STATUS_MARIO_UNK6            (1 <<  6) /* 0x00000040 */
#define INT_STATUS_MARIO_UNK7            (1 <<  7) /* 0x00000080 */

// Object Interaction Status
#define INT_STATUS_GRABBED_MARIO         (1 << 11) /* 0x00000800 */
#define INT_STATUS_ATTACKED_MARIO        (1 << 13) /* 0x00002000 */
#define INT_STATUS_WAS_ATTACKED          (1 << 14) /* 0x00004000 */
#define INT_STATUS_INTERACTED            (1 << 15) /* 0x00008000 */
#define INT_STATUS_UNK16                 (1 << 16) /* 0x00010000 */
#define INT_STATUS_UNK17                 (1 << 17) /* 0x00020000 */
#define INT_STATUS_UNK18                 (1 << 18) /* 0x00040000 */
#define INT_STATUS_UNK19                 (1 << 19) /* 0x00080000 */
#define INT_STATUS_TRAP_TURN             (1 << 20) /* 0x00100000 */
#define INT_STATUS_HIT_MINE              (1 << 21) /* 0x00200000 */
#define INT_STATUS_STOP_RIDING           (1 << 22) /* 0x00400000 */
#define INT_STATUS_TOUCHED_BOB_OMB       (1 << 23) /* 0x00800000 */


s16 mario_obj_angle_to_object(struct MarioState *m, struct Object *o);
void mario_stop_riding_object(struct MarioState *m);
void mario_grab_used_object(struct MarioState *m);
void mario_drop_held_object(struct MarioState *m);
void mario_throw_held_object(struct MarioState *m);
void mario_stop_riding_and_holding(struct MarioState *m);
u32 does_mario_have_normal_cap_on_head(struct MarioState *m);
void mario_blow_off_cap(struct MarioState *m, f32 capSpeed);
u32 mario_lose_cap_to_enemy(u32 arg);
void mario_retrieve_cap(void);
struct Object *mario_get_collided_object(struct MarioState *m, u32 interactType);
u32 mario_check_object_grab(struct MarioState *m);
u32 get_door_save_file_flag(struct Object *door);
void mario_process_interactions(struct MarioState *m);
void mario_handle_special_floors(struct MarioState *m);

#endif // INTERACTION_H
