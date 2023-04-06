#include <ultra64.h>

#include "sm64.h"
#include "mario.h"
#include "debug.h"
#include "spawn_object.h"
#include "object_list_processor.h"
#include "interaction.h"

struct Object *debug_print_obj_collision(struct Object *a) {
    struct Object *sp24;
    UNUSED s32 unused;
    s32 i;

    for (i = 0; i < a->numCollidedObjs; i++) {
        print_debug_top_down_objectinfo("ON", 0);
        sp24 = a->collidedObjs[i];
        if (sp24 != gMarioObject) {
            return sp24;
        }
    }
    return NULL;
}

int detect_object_hitbox_overlap(struct Object *a, struct Object *b) {
    f32 sp3C = a->oPosY - a->hitboxDownOffset;
    f32 sp38 = b->oPosY - b->hitboxDownOffset;
    f32 dx = a->oPosX - b->oPosX;
    UNUSED f32 sp30 = sp3C - sp38;
    f32 dz = a->oPosZ - b->oPosZ;
    f32 collisionRadius = a->hitboxRadius + b->hitboxRadius;
    f32 distance = sqrtf(dx * dx + dz * dz);

    if (collisionRadius > distance) {
        f32 sp20 = a->hitboxHeight + sp3C;
        f32 sp1C = b->hitboxHeight + sp38;

        if (sp3C > sp1C) {
            return 0;
        }
        if (sp20 < sp38) {
            return 0;
        }
        if (a->numCollidedObjs >= 4) {
            return 0;
        }
        if (b->numCollidedObjs >= 4) {
            return 0;
        }
        a->collidedObjs[a->numCollidedObjs] = b;
        b->collidedObjs[b->numCollidedObjs] = a;
        a->collidedObjInteractTypes |= b->oInteractType;
        b->collidedObjInteractTypes |= a->oInteractType;
        a->numCollidedObjs++;
        b->numCollidedObjs++;
        return 1;
    }

    //! no return value
}

int detect_object_hurtbox_overlap(struct Object *a, struct Object *b) {
    f32 sp3C = a->oPosY - a->hitboxDownOffset;
    f32 sp38 = b->oPosY - b->hitboxDownOffset;
    f32 sp34 = a->oPosX - b->oPosX;
    UNUSED f32 sp30 = sp3C - sp38;
    f32 sp2C = a->oPosZ - b->oPosZ;
    f32 sp28 = a->hurtboxRadius + b->hurtboxRadius;
    f32 sp24 = sqrtf(sp34 * sp34 + sp2C * sp2C);

    if (a == gMarioObject) {
        b->oInteractionSubtype |= INT_SUBTYPE_DELAY_INVINCIBILITY;
    }

    if (sp28 > sp24) {
        f32 sp20 = a->hitboxHeight + sp3C;
        f32 sp1C = b->hurtboxHeight + sp38;

        if (sp3C > sp1C) {
            return 0;
        }
        if (sp20 < sp38) {
            return 0;
        }
        if (a == gMarioObject) {
            b->oInteractionSubtype &= ~INT_SUBTYPE_DELAY_INVINCIBILITY;
        }
        return 1;
    }

    //! no return value
}

void clear_object_collision(struct Object *a) {
    struct Object *sp4 = (struct Object *) a->header.next;

    while (sp4 != a) {
        sp4->numCollidedObjs = 0;
        sp4->collidedObjInteractTypes = 0;
        if (sp4->oIntangibleTimer > 0) {
            sp4->oIntangibleTimer--;
        }
        sp4 = (struct Object *) sp4->header.next;
    }
}

void check_collision_in_list(struct Object *a, struct Object *b, struct Object *c) {
    if (a->oIntangibleTimer == 0) {
        while (b != c) {
            if (b->oIntangibleTimer == 0) {
                if (detect_object_hitbox_overlap(a, b) && b->hurtboxRadius != 0.0f) {
                    detect_object_hurtbox_overlap(a, b);
                }
            }
            b = (struct Object *) b->header.next;
        }
    }
}

void check_player_object_collision(void) {
    struct Object *sp1C = (struct Object *) &gObjectLists[OBJ_LIST_PLAYER];
    struct Object *sp18 = (struct Object *) sp1C->header.next;

    while (sp18 != sp1C) {
        check_collision_in_list(sp18, (struct Object *) sp18->header.next, sp1C);
        check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_POLELIKE].next,
                      (struct Object *) &gObjectLists[OBJ_LIST_POLELIKE]);
        check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_LEVEL].next,
                      (struct Object *) &gObjectLists[OBJ_LIST_LEVEL]);
        check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_GENACTOR].next,
                      (struct Object *) &gObjectLists[OBJ_LIST_GENACTOR]);
        check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_PUSHABLE].next,
                      (struct Object *) &gObjectLists[OBJ_LIST_PUSHABLE]);
        check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_SURFACE].next,
                      (struct Object *) &gObjectLists[OBJ_LIST_SURFACE]);
        check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_DESTRUCTIVE].next,
                      (struct Object *) &gObjectLists[OBJ_LIST_DESTRUCTIVE]);
        sp18 = (struct Object *) sp18->header.next;
    }
}

void check_pushable_object_collision(void) {
    struct Object *sp1C = (struct Object *) &gObjectLists[OBJ_LIST_PUSHABLE];
    struct Object *sp18 = (struct Object *) sp1C->header.next;

    while (sp18 != sp1C) {
        check_collision_in_list(sp18, (struct Object *) sp18->header.next, sp1C);
        sp18 = (struct Object *) sp18->header.next;
    }
}

void check_destructive_object_collision(void) {
    struct Object *sp1C = (struct Object *) &gObjectLists[OBJ_LIST_DESTRUCTIVE];
    struct Object *sp18 = (struct Object *) sp1C->header.next;

    while (sp18 != sp1C) {
        if (sp18->oDistanceToMario < 2000.0f && !(sp18->activeFlags & ACTIVE_FLAG_UNK9)) {
            check_collision_in_list(sp18, (struct Object *) sp18->header.next, sp1C);
            check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_GENACTOR].next,
                          (struct Object *) &gObjectLists[OBJ_LIST_GENACTOR]);
            check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_PUSHABLE].next,
                          (struct Object *) &gObjectLists[OBJ_LIST_PUSHABLE]);
            check_collision_in_list(sp18, (struct Object *) gObjectLists[OBJ_LIST_SURFACE].next,
                          (struct Object *) &gObjectLists[OBJ_LIST_SURFACE]);
        }
        sp18 = (struct Object *) sp18->header.next;
    }
}

void detect_object_collisions(void) {
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_POLELIKE]);
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_PLAYER]);
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_PUSHABLE]);
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_GENACTOR]);
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_LEVEL]);
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_SURFACE]);
    clear_object_collision((struct Object *) &gObjectLists[OBJ_LIST_DESTRUCTIVE]);
    check_player_object_collision();
    check_destructive_object_collision();
    check_pushable_object_collision();
}
