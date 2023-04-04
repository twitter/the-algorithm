#include <PR/ultratypes.h>

#if defined(VERSION_EU) || defined(VERSION_SH)
#include "prevent_bss_reordering.h"
#endif

#include "debug_utils.h"
#include "draw_objects.h"
#include "dynlist_proc.h"
#include "gd_macros.h"
#include "gd_main.h"
#include "gd_math.h"
#include "gd_types.h"
#include "joints.h"
#include "macros.h"
#include "objects.h"
#include "renderer.h"
#include "sfx.h"
#include "skin.h"
#include "skin_movement.h"

// data
static s32 D_801A82D0 = 0;
static struct ObjBone *gGdTempBone = NULL; // @ 801A82D4

// bss
s32 sResetWeightVtxNum; // WTF? why is this not in skin_movement.c?

static Mat4f *D_801BA964;
static struct GdVec3f D_801BA968;
static s32 sJointCount;                   // @ 801BA974
static s32 sJointNotF1Count;              // @ 801BA978
static s32 sBoneCount;                    // @ 801BA97C
static s32 sJointArrLen;                  // @ 801BA980
static struct ObjJoint *sJointArr[10];    // @ 801BA988
static struct GdVec3f sJointArrVecs[10];  // @ 801BA9B0
static s32 sJointArr2Len;                 // @ 801BAA28
static struct ObjJoint *sJointArr2[10];   // @ 801BAA30
static struct GdVec3f sJointArr2Vecs[10]; // @ 801BAA58
static struct GdVec3f D_801BAAD0;
static struct GdVec3f D_801BAAE0;

// forward declarations
void set_joint_vecs(struct ObjJoint *, f32, f32, f32);

/**
 * Controls movement of grabbable joints
 */
void grabbable_joint_update_func(struct ObjJoint *self) {
    UNUSED u8 filler1[80];
    Mat4f *attObjMtx;
    UNUSED u8 filler2[4];
    struct GdVec3f offset;  // difference between current position and initial position
    UNUSED u8 filler3[16];
    register struct ListNode *att;
    UNUSED u8 filler4[8];
    struct GdObj *attobj;

    // The joint acts somewhat like a spring in that the further it is moved
    // from its original position, the more resistance it has to moving further

    offset.x = self->mat128[3][0] - self->initPos.x;
    offset.y = self->mat128[3][1] - self->initPos.y;
    offset.z = self->mat128[3][2] - self->initPos.z;

    if (self->header.drawFlags & OBJ_PICKED) {
        self->velocity.x = offset.x * -0.25;
        self->velocity.y = offset.y * -0.25;
        self->velocity.z = offset.z * -0.25;

        self->flags |= 0x2000;
        ;  // needed to match
    } else {
        if (gGdCtrl.trgR == FALSE) { // R trigger is released
            // Set velocity so that the joint approaches its initial position
            self->velocity.x -= offset.x * 0.5; //? 0.5f
            self->velocity.y -= offset.y * 0.5; //? 0.5f
            self->velocity.z -= offset.z * 0.5; //? 0.5f

            // Decay the velocity
            self->velocity.x *= 0.8; //? 0.8f
            self->velocity.y *= 0.8; //? 0.8f
            self->velocity.z *= 0.8; //? 0.8f

            // If the joint's velocity has decayed enough and it is very close
            // to its original position, stop its movement altogether
            if (ABS(self->velocity.x) + ABS(self->velocity.y) + ABS(self->velocity.z) < 1.0) {
                if (ABS(offset.x) + ABS(offset.y) + ABS(offset.z) < 1.0) {
                    self->velocity.x = self->velocity.y = self->velocity.z = 0.0f;
                    self->mat128[3][0] -= offset.x;
                    self->mat128[3][1] -= offset.y;
                    self->mat128[3][2] -= offset.z;
                }
            }

            if (self->flags & 0x2000) {
                gd_play_sfx(GD_SFX_LET_GO_FACE);
            }

            self->flags &= ~0x2000;
            ; // necessary?
        } else {
            // freeze position of joint
            self->velocity.x = self->velocity.y = self->velocity.z = 0.0f;
        }
    }

    // update position
    self->mat128[3][0] += self->velocity.x;
    self->mat128[3][1] += self->velocity.y;
    self->mat128[3][2] += self->velocity.z;

    if (self->header.drawFlags & OBJ_PICKED) {
        gGdCtrl.csrX -= (gGdCtrl.csrX - gGdCtrl.dragStartX) * 0.2;
        gGdCtrl.csrY -= (gGdCtrl.csrY - gGdCtrl.dragStartY) * 0.2;
    }

    // update position of attached objects
    offset.x = self->mat128[3][0] - self->initPos.x;
    offset.y = self->mat128[3][1] - self->initPos.y;
    offset.z = self->mat128[3][2] - self->initPos.z;
    for (att = self->attachedObjsGrp->firstMember; att != NULL; att = att->next) {
        attobj = att->obj;
        set_cur_dynobj(attobj);
        attObjMtx = d_get_matrix_ptr();
        gd_add_vec3f_to_mat4f_offset(attObjMtx, &offset);
    }
}

/**
 * Update function for Mario's eye joints, which makes them follow the cursor
 */
void eye_joint_update_func(struct ObjJoint *self) {
    Mat4f *sp5C;
    struct GdVec3f sp50;
    struct GdVec3f sp44;
    UNUSED u8 filler[24];
    register struct ListNode *att;
    struct GdObj *attobj;

    if (sCurrentMoveCamera == NULL) {
        return;
    }

    if (self->rootAnimator != NULL) {
        if (self->rootAnimator->state != 7) {
            return;
        }
    }

    set_cur_dynobj((struct GdObj *)self);
    sp5C = d_get_rot_mtx_ptr();
    sp44.x = (*sp5C)[3][0];
    sp44.y = (*sp5C)[3][1];
    sp44.z = (*sp5C)[3][2];
    world_pos_to_screen_coords(&sp44, sCurrentMoveCamera, sCurrentMoveView);

    sp50.x = gGdCtrl.csrX - sp44.x;
    sp50.y = -(gGdCtrl.csrY - sp44.y);
    sp50.z = 0.0f;

    sp50.x *= 2.0; //?2.0f
    sp50.y *= 2.0; //?2.0f
    sp50.z *= 2.0; //?2.0f
    if (gd_vec3f_magnitude(&sp50) > 30.0f) {
        gd_normalize_vec3f(&sp50);
        sp50.x *= 30.0f;
        sp50.y *= 30.0f;
        sp50.z *= 30.0f;
    }

    for (att = self->attachedObjsGrp->firstMember; att != NULL; att = att->next) {
        attobj = att->obj;
        set_cur_dynobj(attobj);
        sp5C = d_get_rot_mtx_ptr();
        gd_add_vec3f_to_mat4f_offset(sp5C, &sp50);
    }
}

/* 23D62C -> 23D748; not called */
void func_8018EE5C(struct ObjJoint *j1, struct ObjJoint *j2, struct ObjJoint *j3) {
    struct GdVec3f vec;
    struct ObjJoint *curj;

    if (j3 == NULL) {
        return;
    }

    vec.z = j3->worldPos.x;
    vec.y = j3->worldPos.y;
    vec.x = j3->worldPos.z;

    curj = j1;
    while (curj != NULL) {
        set_joint_vecs(curj, curj->worldPos.x + vec.z, curj->worldPos.y + vec.y, curj->worldPos.z + vec.x);
        if (curj == j2) {
            break;
        }
        curj = curj->prevjoint;
    }
}

/* 23D748 -> 23D818; orig name: func_8018EF78 */
void set_joint_vecs(struct ObjJoint *j, f32 x, f32 y, f32 z) {
    j->worldPos.x = x;
    j->worldPos.y = y;
    j->worldPos.z = z;

    j->unk30.x = x;
    j->unk30.y = y;
    j->unk30.z = z;

    j->unk3C.x = x;
    j->unk3C.y = y;
    j->unk3C.z = z;

    j->initPos.x = x;
    j->initPos.y = y;
    j->initPos.z = z;

    j->mat128[3][0] = x;
    j->mat128[3][1] = y;
    j->mat128[3][2] = z;
}

/* 23D818 -> 23DA18 */
struct ObjJoint *make_joint(s32 flags, f32 x, f32 y, f32 z) {
    struct ObjJoint *j; // sp24
    struct ObjJoint *oldhead;
    UNUSED u8 filler[4];

    j = (struct ObjJoint *) make_object(OBJ_TYPE_JOINTS);
    sJointCount++;
    oldhead = gGdJointList;
    gGdJointList = j;

    if (oldhead != NULL) {
        j->nextjoint = oldhead;
        oldhead->prevjoint = j;
    }
    gd_set_identity_mat4(&j->matE8);
    gd_set_identity_mat4(&j->mat128);
    set_joint_vecs(j, x, y, z);
    j->type = 0;
    j->id = sJointCount;
    j->flags = flags;

    if (!(j->flags & 0x1)) {
        sJointNotF1Count++;
    }

    if (j->flags & 0x1) {
        j->colourNum = COLOUR_RED;
    } else {
        j->colourNum = COLOUR_PINK;
    }

    j->unk1C4 = NULL;
    j->shapePtr = NULL;
    j->scale.x = 1.0f;
    j->scale.y = 1.0f;
    j->scale.z = 1.0f;
    j->friction.x = 0.0f;
    j->friction.y = 0.0f;
    j->friction.z = 0.0f;
    j->updateFunc = NULL;

    return j;
}

/**
 * Creates a joint that can be grabbed by the cursor. When moved, this joint
 * drags the joints in its unk1F8 group along with it. The `shape` does not
 * actually get rendered due to the joint's OBJ_INVISIBLE flag being set.
 */
struct ObjJoint *make_grabber_joint(struct ObjShape *shape, s32 flags, f32 x, f32 y, f32 z) {
    struct ObjJoint *j;

    j = make_joint(0, x, y, z);
    j->shapePtr = shape;
    j->type = 5;
    j->flags |= flags;
    j->colourNum = COLOUR_PINK;
    j->header.drawFlags |= OBJ_IS_GRABBABLE;
    j->header.drawFlags |= OBJ_INVISIBLE;
    j->updateFunc = grabbable_joint_update_func;
    j->rootAnimator = NULL;

    return j;
}

/* 23DAF8 -> 23DC9C */
void func_8018F328(struct ObjBone *b) {
    struct ObjJoint *joint1;
    struct ObjJoint *joint2;
    struct ObjGroup *grp; // sp1C
    struct ListNode *link;   // sp18

    grp = b->unk10C;
    link = grp->firstMember;
    joint1 = (struct ObjJoint *) link->obj;
    link = link->next;
    joint2 = (struct ObjJoint *) link->obj;

    // bone position is average of two connecting joints
    b->worldPos.x = (joint1->worldPos.x + joint2->worldPos.x) / 2.0; //?2.0f
    b->worldPos.y = (joint1->worldPos.y + joint2->worldPos.y) / 2.0; //?2.0f
    b->worldPos.z = (joint1->worldPos.z + joint2->worldPos.z) / 2.0; //?2.0f

    b->unk58.x = joint2->worldPos.x - joint1->worldPos.x;
    b->unk58.y = joint2->worldPos.y - joint1->worldPos.y;
    b->unk58.z = joint2->worldPos.z - joint1->worldPos.z;

    gd_normalize_vec3f(&b->unk58);
    gd_create_origin_lookat(&b->matB0, &b->unk58, 0); //? 0.0f
}

/* 23DC9C -> 23DCF0 */
void func_8018F4CC(struct ObjJoint *j) {
    if (j->flags & 0x1000) {
        j->unkB4.x = D_801BA968.x;
        j->unkB4.y = D_801BA968.y;
        j->unkB4.z = D_801BA968.z;
    }
}

/* 23DCF0 -> 23E06C */
void func_8018F520(struct ObjBone *b) {
    struct ObjJoint *joint1;
    struct ObjJoint *joint2;
    UNUSED u8 filler[12];
    struct GdVec3f sp90;
    struct GdVec3f sp84;
    struct GdVec3f sp78;
    struct GdVec3f sp6C;
    f32 sp68;
    f32 sp64;
    struct ObjGroup *grp; // sp60
    struct ListNode *link;
    Mat4f mtx; // sp1C

    grp = b->unk10C;
    link = grp->firstMember;
    joint1 = (struct ObjJoint *) link->obj;
    link = link->next;
    joint2 = (struct ObjJoint *) link->obj;

    // bone position is average of two connecting joints
    b->worldPos.x = (joint1->worldPos.x + joint2->worldPos.x) / 2.0; //? 2.0f;
    b->worldPos.y = (joint1->worldPos.y + joint2->worldPos.y) / 2.0; //? 2.0f;
    b->worldPos.z = (joint1->worldPos.z + joint2->worldPos.z) / 2.0; //? 2.0f;

    sp90.x = b->unk58.x;
    sp90.y = b->unk58.y;
    sp90.z = b->unk58.z;

    sp6C.x = sp90.x;
    sp6C.y = sp90.y;
    sp6C.z = sp90.z;

    sp6C.x -= b->unk64.x;
    sp6C.y -= b->unk64.y;
    sp6C.z -= b->unk64.z;
    b->unk64.x = sp90.x;
    b->unk64.y = sp90.y;
    b->unk64.z = sp90.z;

    sp68 = 5.4 / b->unkF8; //? 5.4f
    sp6C.x *= sp68;
    sp6C.y *= sp68;
    sp6C.z *= sp68;
    sp90.x *= sp68;
    sp90.y *= sp68;
    sp90.z *= sp68;

    gd_cross_vec3f(&sp90, &sp6C, &sp78);
    sp84.x = sp78.x;
    sp84.y = sp78.y;
    sp84.z = sp78.z;

    gd_normalize_vec3f(&sp84);
    sp64 = gd_vec3f_magnitude(&sp78);
    gd_create_rot_mat_angular(&mtx, &sp84, sp64);
    gd_mult_mat4f(&b->mat70, &mtx, &b->mat70);
    D_801BA968.x = b->mat70[2][0];
    D_801BA968.y = b->mat70[2][1];
    D_801BA968.z = b->mat70[2][2];
    D_801BA964 = &b->mat70;

    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_8018F4CC, b->unk10C);
}

/* 23E06C -> 23E238 */
void func_8018F89C(struct ObjBone *b) {
    struct ObjJoint *spAC;
    struct ObjJoint *spA8;
    UNUSED u8 filler[68];
    struct ObjGroup *grp; // sp60
    struct ListNode *link;   // sp5c
    Mat4f mtx;            // sp1c

    grp = b->unk10C;
    link = grp->firstMember;
    spAC = (struct ObjJoint *) link->obj;
    link = link->next;
    spA8 = (struct ObjJoint *) link->obj;

    b->worldPos.x = (spAC->worldPos.x + spA8->worldPos.x) / 2.0; //? 2.0f;
    b->worldPos.y = (spAC->worldPos.y + spA8->worldPos.y) / 2.0; //? 2.0f;
    b->worldPos.z = (spAC->worldPos.z + spA8->worldPos.z) / 2.0; //? 2.0f;

    gd_mult_mat4f(&b->matB0, &gGdSkinNet->mat128, &mtx);
    gd_copy_mat4f(&mtx, &b->mat70);

    D_801BA968.x = -b->mat70[2][0];
    D_801BA968.y = -b->mat70[2][1];
    D_801BA968.z = -b->mat70[2][2];
    D_801BA964 = &b->mat70;

    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_8018F4CC, b->unk10C);
}

/* 23E238 -> 23E298 */
void func_8018FA68(struct ObjBone *b) {
    if (b->unk104 & (0x8 | 0x2)) {
        func_8018F89C(b);
    } else {
        func_8018F520(b);
    }
}

/* 23E298 -> 23E328; orig name: func_8018FAC8 */
s32 set_skin_weight(struct ObjJoint *j, s32 id, struct ObjVertex *vtx /* always NULL */, f32 weight) {
    struct ObjWeight *w;

    if (j->weightGrp == NULL) {
        j->weightGrp = make_group(0);
    }
    w = make_weight(0, id, vtx, weight);
    addto_group(j->weightGrp, &w->header);

    return TRUE;
}

/* 23E328 -> 23E474 */
void func_8018FB58(struct ObjBone *b) {
    struct GdVec3f vec;  // sp2c
    struct ObjJoint *j1; // sp28
    struct ObjJoint *j2;
    struct ListNode *link;
    struct ObjGroup *grp;

    grp = b->unk10C;
    link = grp->firstMember;
    j1 = (struct ObjJoint *) link->obj;
    link = link->next;
    j2 = (struct ObjJoint *) link->obj;

    vec.x = j1->worldPos.x - j2->worldPos.x;
    vec.y = j1->worldPos.y - j2->worldPos.y;
    vec.z = j1->worldPos.z - j2->worldPos.z;

    b->unkF8 = gd_sqrt_d((vec.x * vec.x) + (vec.y * vec.y) + (vec.z * vec.z));
    b->unkF4 = b->unkF8;
    b->unkFC = b->unkF8;
    func_8018F328(b);
}

/* 23E474 -> 23E56C */
void add_joint2bone(struct ObjBone *b, struct ObjJoint *j) {
    if (j->header.type != OBJ_TYPE_JOINTS) {
        fatal_printf("add_joint2bone(): Can only add Joints to Bones");
    }

    if (b->unk10C == NULL) {
        b->unk10C = make_group(0);
    }
    addto_group(b->unk10C, &j->header);

    if (j->unk1C4 == NULL) {
        j->unk1C4 = make_group(0);
    }
    addto_group(j->unk1C4, &b->header);

    if (b->unk10C->memberCount == 2) {
        func_8018FB58(b);
    }
}

/* 23E56C -> 23E6E4 */
struct ObjBone *make_bone(s32 a0, struct ObjJoint *j1, struct ObjJoint *j2, UNUSED s32 a3) {
    struct ObjBone *b; // sp34
    struct ObjBone *oldhead;
    UNUSED u8 filler[20];

    b = (struct ObjBone *) make_object(OBJ_TYPE_BONES);
    sBoneCount++;
    b->id = sBoneCount;
    oldhead = gGdBoneList;
    gGdBoneList = b;

    if (oldhead != NULL) {
        b->next = oldhead;
        oldhead->prev = b;
    }
    b->unk10C = NULL;
    b->colourNum = 0;
    b->unk104 = a0;
    b->shapePtr = NULL;
    gd_set_identity_mat4(&b->mat70);
    b->spring = 0.8f;
    b->unk114 = 0.9f;
    b->unkF8 = 100.0f;

    if (j1 != NULL && j2 != NULL) {
        add_joint2bone(b, j1);
        add_joint2bone(b, j2);
    }

    printf("Made bone %d\n", b->id);
    return b;
}

/* 23E6E4 -> 23E6F8; not called */
void stub_joints_1(UNUSED u32 a0) {
}

/* 23E6F8 -> 23E758; not called */
void func_8018FF28(struct ObjJoint *a0, struct ObjJoint *a1) {
    if (a1->flags & 0x1) {
        a0->unk84.x -= a1->unk84.x;
        a0->unk84.y -= a1->unk84.y;
        a0->unk84.z -= a1->unk84.z;
    }
}

/**
 * Unused (not called) - possibly was used to print indent levels inside
 * recursive functions
 */
void print_some_spaces(s32 size) {
    s32 i;

    for (i = 0; i < size - 1; i++) {
        gd_printf("  ");
    }
}

/* 23E7B8 -> 23E938 */
s32 func_8018FFE8(struct ObjBone **a0, struct ObjJoint **a1, struct ObjJoint *a2, struct ObjJoint *a3) {
    struct ObjBone *b; // 1C
    struct ObjJoint *sp18;
    s32 sp14 = 0;
    struct ObjGroup *bonegrp; // 10
    struct ObjGroup *grp;     // 0c
    struct ListNode *bonelink;   // 08
    struct ListNode *link;       // 04

    grp = a3->unk1C4;

    if (grp == NULL) {
        return 0;
    }
    link = grp->firstMember;
    if (link == NULL) {
        return 0;
    }

    while (link != NULL) {
        if ((b = (struct ObjBone *) link->obj) != NULL) {
            bonegrp = b->unk10C;
            bonelink = bonegrp->firstMember;

            while (bonelink != NULL) {
                sp18 = (struct ObjJoint *) bonelink->obj;

                if (sp18 != a3 && sp18 != a2) {
                    a1[sp14] = sp18;
                    a0[sp14] = b;
                    sp14++;
                }

                bonelink = bonelink->next;
            }
        }
        link = link->next;
    }

    return sp14;
}

/* 23E938 -> 23EBB8 */
void func_80190168(struct ObjBone *b, UNUSED struct ObjJoint *a1, UNUSED struct ObjJoint *a2,
                   struct GdVec3f *a3) {
    struct GdVec3f sp7C;
    UNUSED u8 filler1[24];
    f32 sp60;
    f32 sp5C;
    f32 sp58;
    UNUSED u8 filler2[60];

    return;

    b->unk58.x = sp7C.x;
    b->unk58.y = sp7C.y;
    b->unk58.z = sp7C.z;

    if (b->unk104 & 0x8) {
        sp58 = gd_vec3f_magnitude(&sp7C);
        if (sp58 == 0.0f) {
            sp58 = 1.0f;
        }
        sp60 = (b->unkF8 / sp58) * b->spring;
    }

    if (b->unk104 & 0x4) {
        if (sp60 > (sp58 = gd_vec3f_magnitude(&sp7C))) {
            sp5C = b->spring;
            a3->x *= sp5C;
            a3->y *= sp5C;
            a3->z *= sp5C;
        } else {
            a3->x = 0.0f;
            a3->y = 0.0f;
            a3->z = 0.0f;
        }
    }

    if (b->unk104 & 0x2) {
        if (sp60 < (sp58 = gd_vec3f_magnitude(&sp7C))) {
            sp5C = b->spring;
            a3->x *= sp5C;
            a3->y *= sp5C;
            a3->z *= sp5C;
        } else {
            a3->x = 0.0f;
            a3->y = 0.0f;
            a3->z = 0.0f;
        }
    }
}

/* 23EBB8 -> 23ED44 */
void func_801903E8(struct ObjJoint *j, struct GdVec3f *a1, f32 x, f32 y, f32 z) {
    f32 sp14;
    struct GdVec3f sp8;

    if (j->flags & 0x1 || (j->flags & 0x1000) == 0) {
        j->unk3C.x += x;
        j->unk3C.y += y;
        j->unk3C.z += z;
        a1->x = a1->y = a1->z = 0.0f;
        ;

    } else {
        sp14 = (j->unkB4.x * x) + (j->unkB4.y * y) + (j->unkB4.z * z);
        sp8.x = j->unkB4.x * sp14;
        sp8.y = j->unkB4.y * sp14;
        sp8.z = j->unkB4.z * sp14;

        j->unk3C.x += sp8.x;
        j->unk3C.y += sp8.y;
        j->unk3C.z += sp8.z;

        a1->x = x - sp8.x;
        a1->y = y - sp8.y;
        a1->z = z - sp8.z;
    }
}

/* 23EBB8 -> 23F184 */
void func_80190574(s32 a0, struct ObjJoint *a1, struct ObjJoint *a2, f32 x, f32 y, f32 z) { // sp278
    struct ObjJoint *sp274; // = a2?
    struct ObjJoint *sp270; // mid-point of stack array?
    struct ObjJoint *sp26C; // jointstackarr[i]? curjoint?
    UNUSED u8 filler1[4];
    UNUSED u32 unused = 0;
    UNUSED u8 filler2[12]; // unused vec?
    struct GdVec3f sp24C;
    struct GdVec3f sp240;
    UNUSED u8 filler3[8];
    s32 sp234; // i?
    s32 sp230;
    s32 sp22C = 1;
    UNUSED u8 filler4[4];
    s32 sp224;
    s32 sp220;
    struct ObjJoint *sp120[0x40];
    struct ObjBone *sp20[0x40];

    for (sp230 = 1; sp230 < a0; sp230 *= 2) {
        sp22C = sp22C * 2 + 1;
    }

    if (a0 & 0x8000) {
        fatal_print("Too many nestings!\n");
    }

    printf("\n");
    printf("NIDmask: %d /  ", a0);

    a2->unk1C0 |= a0;
    sp224 = func_8018FFE8(sp20, sp120, a1, a2);
    func_801903E8(a2, &sp240, x, y, z);
    for (sp234 = 0; sp234 < sp224; sp234++) {
        if (a1 != NULL) {
            printf("branch %d from j%d-j%d(%d): ", sp234, a2->id, a1->id, sp224);
        } else {
            printf("branch %d from j%d(%d): ", sp234, a2->id, sp224);
        }

        sp274 = a2;
        sp26C = sp120[sp234];
        func_80190168(sp20[sp234], sp274, sp26C, &sp24C);
        do {
            sp220 = func_8018FFE8(&sp20[0x20], &sp120[0x20], sp274, sp26C);
            sp270 = sp120[0x20];
            if (sp26C->unk1C0 & sp22C) {
                break;
            }

            if (sp220 < 2) {
                if (sp26C->flags & 0x1) {
                    sJointArrLen++;
                    sJointArr[sJointArrLen] = sp274;
                    sJointArrVecs[sJointArrLen].x = -sp24C.x;
                    sJointArrVecs[sJointArrLen].y = -sp24C.y;
                    sJointArrVecs[sJointArrLen].z = -sp24C.z;

                    sp26C->unk90.x += sp24C.x;
                    sp26C->unk90.y += sp24C.y;
                    sp26C->unk90.z += sp24C.z;

                    sp26C->unk90.x += sp240.x;
                    sp26C->unk90.y += sp240.y;
                    sp26C->unk90.z += sp240.z;

                    sp240.x = sp240.y = sp240.z = 0.0f;
                    break;
                } else {
                    sp24C.x += sp240.x;
                    sp24C.y += sp240.y;
                    sp24C.z += sp240.z;

                    func_801903E8(sp26C, &sp240, sp24C.x, sp24C.y, sp24C.z);
                }

                if (sp220 == 1) {
                    func_80190168(sp20[0x20], sp26C, sp270, &sp24C);
                }
            }

            if (sp220 > 1) {
                func_80190574(a0 * 2, sp274, sp26C, sp24C.x, sp24C.y, sp24C.z);
                break;
            }

            sp274 = sp26C;
            sp26C = sp270;
        } while (sp220);
        printf("Exit");
        // probably sp274(sp26C) because it would make sense to print
        // the iterations of both of these loops.
        printf(" %d(%d)", sp274->id, sp26C->id);
        printf("R ");
        printf("\n");
    }

    printf("\n\n");
}

/* 23F184 -> 23F1F0 */
void func_801909B4(void) {
    struct ObjJoint *node;

    D_801A82D0 = 0;
    node = gGdJointList;
    while (node != NULL) {
        node->unk1C0 = 0;
        node = node->nextjoint;
    }
}

/* 23F1F0 -> 23F324; not called */
void func_80190A20(void) {
    struct ObjJoint *j; // sp3c
    UNUSED u8 filler[4];
    struct GdVec3f vec; // sp2C
    struct ObjGroup *grp;
    struct ListNode *link;
    struct ObjBone *b;

    j = gGdJointList;
    while (j != NULL) {
        if (j->flags & 0x40) {
            grp = j->unk1C4;
            link = grp->firstMember;
            b = (struct ObjBone *) link->obj;

            vec.z = b->unk40.x * 100.0f;
            vec.y = b->unk40.y * 100.0f;
            vec.x = b->unk40.z * 100.0f;
            func_80190574(1, NULL, j, vec.z, vec.y, vec.x);
        }

        j = j->nextjoint;
    }
}

/* 23F324 -> 23F638 */
void func_80190B54(struct ObjJoint *a0, struct ObjJoint *a1, struct GdVec3f *a2) { // b0
    struct GdVec3f spA4;
    UNUSED u8 filler1[12];
    struct GdVec3f sp8C;
    struct GdVec3f sp80;
    f32 sp7C;
    f32 sp78;
    Mat4f sp38;
    UNUSED u8 filler2[28];

    if (a1 != NULL) {
        spA4.x = a1->unk3C.x;
        spA4.y = a1->unk3C.y;
        spA4.z = a1->unk3C.z;

        spA4.x -= a0->unk3C.x;
        spA4.y -= a0->unk3C.y;
        spA4.z -= a0->unk3C.z;

        sp8C.x = spA4.x;
        sp8C.y = spA4.y;
        sp8C.z = spA4.z;
        gd_normalize_vec3f(&sp8C);

        sp7C = a1->unk228;

        D_801BAAE0.x = spA4.x - (sp8C.x * sp7C);
        D_801BAAE0.y = spA4.y - (sp8C.y * sp7C);
        D_801BAAE0.z = spA4.z - (sp8C.z * sp7C);

        sp78 = 5.4 / sp7C; //? 5.4f
        D_801BAAD0.x *= sp78;
        D_801BAAD0.y *= sp78;
        D_801BAAD0.z *= sp78;

        spA4.x *= sp78;
        spA4.y *= sp78;
        spA4.z *= sp78;

        gd_cross_vec3f(&spA4, &D_801BAAD0, &sp80);
        sp78 = gd_vec3f_magnitude(&sp80);
        gd_normalize_vec3f(&sp80);
        gd_create_rot_mat_angular(&sp38, &sp80, sp78);
        gd_mult_mat4f(&a0->matE8, &sp38, &a0->matE8);

    } else {
        D_801BAAE0.x = a2->x;
        D_801BAAE0.y = a2->y;
        D_801BAAE0.z = a2->z;
    }

    a0->unk3C.x += D_801BAAE0.x;
    a0->unk3C.y += D_801BAAE0.y;
    a0->unk3C.z += D_801BAAE0.z;

    D_801BAAD0.x = D_801BAAE0.x;
    D_801BAAD0.y = D_801BAAE0.y;
    D_801BAAD0.z = D_801BAAE0.z;
}

/* 23F638 -> 23F70C; not called */
void func_80190E68(struct GdObj *obj, f32 x, f32 y, f32 z) {
    struct ObjJoint *sp44;
    struct GdObj *sp40;
    struct GdVec3f vec;
    UNUSED u8 filler[24];

    vec.x = x;
    vec.y = y;
    vec.z = z;

    sp44 = NULL;
    sp40 = obj;
    while (sp40 != NULL) {
        if (sp40->type != OBJ_TYPE_JOINTS) {
            break;
        }

        func_80190B54(((struct ObjJoint *) sp40), sp44, &vec);
        sp44 = ((struct ObjJoint *) sp40);
        sp40 = ((struct ObjJoint *) sp40)->attachedToObj;
    }
}

/* 23F70C -> 23F978 */
f32 func_80190F3C(struct ObjJoint *a0, f32 a1, f32 a2, f32 a3) {
    struct ObjJoint *curj;
    s32 i;
    struct GdVec3f sp24;

    sp24.x = a0->unk3C.x;
    sp24.y = a0->unk3C.y;
    sp24.z = a0->unk3C.z;

    func_801909B4();
    sJointArrLen = 0;
    func_80190574(1, NULL, a0, a1, a2, a3);

    for (i = 1; i <= sJointArrLen; i++) {
        sJointArr2[i] = sJointArr[i];
        sJointArr2Vecs[i].x = sJointArrVecs[i].x;
        sJointArr2Vecs[i].y = sJointArrVecs[i].y;
        sJointArr2Vecs[i].z = sJointArrVecs[i].z;
    }
    printf("Num return joints (pass 1): %d\n", i);

    sJointArr2Len = sJointArrLen;
    sJointArrLen = 0;

    for (i = 1; i <= sJointArr2Len; i++) {
        func_801909B4();
        curj = sJointArr2[i];
        func_80190574(1, NULL, curj, sJointArr2Vecs[i].x, sJointArr2Vecs[i].y, sJointArr2Vecs[i].z);
    }
    printf("Num return joints (pass 2): %d\n", i);

    sp24.x -= a0->unk3C.x;
    sp24.y -= a0->unk3C.y;
    sp24.z -= a0->unk3C.z;

    return gd_vec3f_magnitude(&sp24);
}

/* 23F978 -> 23F9F0 */
void func_801911A8(struct ObjJoint *j) {
    j->unkCC.x = j->shapeOffset.x;
    j->unkCC.y = j->shapeOffset.y;
    j->unkCC.z = j->shapeOffset.z;

    gd_rotate_and_translate_vec3f(&j->unkCC, &gGdSkinNet->mat128);
}

/* 23F9F0 -> 23FB90 */
void func_80191220(struct ObjJoint *j) {
    j->unk48.x = j->initPos.x; // storing "attached offset"?
    j->unk48.y = j->initPos.y;
    j->unk48.z = j->initPos.z;

    gd_mat4f_mult_vec3f(&j->unk48, &gGdSkinNet->mat128);
    j->unk3C.x = j->unk48.x;
    j->unk3C.y = j->unk48.y;
    j->unk3C.z = j->unk48.z;
    j->worldPos.x = gGdSkinNet->worldPos.x;
    j->worldPos.y = gGdSkinNet->worldPos.y;
    j->worldPos.z = gGdSkinNet->worldPos.z;

    j->worldPos.x += j->unk3C.x;
    j->worldPos.y += j->unk3C.y;
    j->worldPos.z += j->unk3C.z;
    j->unk1A8.x = j->unk1A8.y = j->unk1A8.z = 0.0f;
    gGdCounter.ctr0++;
}

/* 23FB90 -> 23FBC0 */
void func_801913C0(struct ObjJoint *j) {
    UNUSED u8 filler[16];
    func_80181894(j);
}

/* 23FBC0 -> 23FCC8 */
void func_801913F0(struct ObjJoint *j) {
    // hmm...
    j->velocity.x = j->worldPos.x;
    j->velocity.y = j->worldPos.y;
    j->velocity.z = j->worldPos.z;

    j->velocity.x -= j->unk30.x;
    j->velocity.y -= j->unk30.y;
    j->velocity.z -= j->unk30.z;

    j->unk30.x = j->worldPos.x;
    j->unk30.y = j->worldPos.y;
    j->unk30.z = j->worldPos.z;

    gd_copy_mat4f(&gGdSkinNet->mat128, &j->matE8);
}

/* 23FCC8 -> 23FCDC */
void stub_joints_2(UNUSED struct ObjJoint *j) {
}

/* 23FCDC -> 23FDD4; not called */
void func_8019150C(Mat4f *a0, struct GdVec3f *a1) {
    struct GdVec3f sp1C;

    sp1C.x = (*a0)[3][0] / 10.0; //? 10.0f
    sp1C.y = (*a0)[3][1] / 10.0; //? 10.0f
    sp1C.z = (*a0)[3][2] / 10.0; //? 10.0f

    a1->x += sp1C.x;
    a1->y += sp1C.y;
    a1->z += sp1C.z;
    gd_mat4f_mult_vec3f(a1, a0);
}

/* 23FDD4 -> 23FFF4 */
void reset_joint(struct ObjJoint *j) {
    j->worldPos.x = j->initPos.x;
    j->worldPos.y = j->initPos.y;
    j->worldPos.z = j->initPos.z;

    j->unk30.x = j->initPos.x;
    j->unk30.y = j->initPos.y;
    j->unk30.z = j->initPos.z;

    j->unk3C.x = j->initPos.x;
    j->unk3C.y = j->initPos.y;
    j->unk3C.z = j->initPos.z;

    j->velocity.x = j->velocity.y = j->velocity.z = 0.0f;
    j->unk84.x = j->unk84.y = j->unk84.z = 0.0f;
    j->unk90.x = j->unk90.y = j->unk90.z = 0.0f;
    j->unk1A8.x = j->unk1A8.y = j->unk1A8.z = 0.0f;

    gd_set_identity_mat4(&j->mat168);
    gd_scale_mat4f_by_vec3f(&j->mat168, (struct GdVec3f *) &j->scale);
    gd_rot_mat_about_vec(&j->mat168, (struct GdVec3f *) &j->unk6C);
    gd_add_vec3f_to_mat4f_offset(&j->mat168, &j->attachOffset);
    gd_copy_mat4f(&j->mat168, &j->matE8);

    gd_set_identity_mat4(&j->mat128);
    gd_add_vec3f_to_mat4f_offset(&j->mat128, &j->initPos);
}

/* 23FFF4 -> 2400C4 */
void func_80191824(struct ObjJoint *j) {
    UNUSED struct ObjNet *sp14;
    UNUSED u8 filler[16];

    sp14 = gGdSkinNet->unk1F0;
    if (j->flags & 0x1) {
        j->worldPos.x = gGdSkinNet->worldPos.x;
        j->worldPos.y = gGdSkinNet->worldPos.y;
        j->worldPos.z = gGdSkinNet->worldPos.z;

        j->unk3C.x = gGdSkinNet->worldPos.x;
        j->unk3C.y = gGdSkinNet->worldPos.y;
        j->unk3C.z = gGdSkinNet->worldPos.z;
    }
}

/* 2400C4 -> 2401EC; not called */
void func_801918F4(struct ObjJoint *j) {
    f32 sp4;

    j->velocity.x = j->unk3C.x;
    j->velocity.y = j->unk3C.y;
    j->velocity.z = j->unk3C.z;

    j->velocity.x -= j->unk30.x;
    j->velocity.y -= j->unk30.y;
    j->velocity.z -= j->unk30.z;

    j->unk30.x = j->unk3C.x;
    j->unk30.y = j->unk3C.y;
    j->unk30.z = j->unk3C.z;

    sp4 = -4.0f;

    if (!(j->flags & 0x41)) {
        j->velocity.y += sp4 * 0.2; //? 0.2f

        j->unk3C.x += j->velocity.x;
        j->unk3C.y += j->velocity.y;
        j->unk3C.z += j->velocity.z;
    }
}

/* 2401EC -> 2403C8; not called */
void func_80191A1C(struct ObjBone *a0) {
    f32 sp3C;
    f32 sp38 = 0.0f;
    struct GdObj *argjoint;
    struct GdObj *tempjoint;
    struct GdVec3f sp24;
    struct GdVec3f sp18;

    if (gGdTempBone == NULL) {
        gGdTempBone = a0;
    }
    sp3C = gd_dot_vec3f(&gGdTempBone->unk40, &a0->unk40);
    a0->unk118 = sp3C;

    if ((sp3C -= sp38) < 0.0f) {
        tempjoint = gGdTempBone->unk10C->firstMember->obj;
        argjoint = a0->unk10C->firstMember->next->obj;
        set_cur_dynobj(argjoint);
        d_get_rel_pos(&sp24);
        set_cur_dynobj(tempjoint);
        d_get_rel_pos(&sp18);

        sp24.x -= sp18.x;
        sp24.y -= sp18.y;
        sp24.z -= sp18.z;
        gd_normalize_vec3f(&sp24);

        sp3C = -sp3C * 50.0; //? 50.0f
        if (!(((struct ObjJoint *) argjoint)->flags & 0x1)) {
            func_80190F3C((struct ObjJoint *) argjoint, sp24.x * sp3C, sp24.y * sp3C, sp24.z * sp3C);
        }
    }
    gGdTempBone = a0;
}

/* 2403C8 -> 240530 */
void func_80191BF8(struct ObjJoint *j) {
    f32 sp1C;
    f32 sp18 = -2.0f;

    if (!(j->flags & 0x1)) {
        j->unk3C.y += sp18;
    }

    if ((sp1C = j->unk3C.y - (D_801A8058 + 30.0f)) < 0.0f && j->velocity.y < 0.0f) {
        sp1C += j->velocity.y;
        sp1C *= 0.8; //? 0.8f
        func_80190F3C(j, -j->velocity.x * 0.7, -sp1C, -j->velocity.z * 0.7);
    }

    func_80190F3C(j, 0.0f, 0.0f, 0.0f);
}

/* 240530 -> 240624 */
void func_80191D60(struct ObjJoint *j) {
    j->velocity.x += j->unk3C.x - j->worldPos.x;
    j->velocity.y += j->unk3C.y - j->worldPos.y;
    j->velocity.z += j->unk3C.z - j->worldPos.z;

    j->velocity.x *= 0.9; //? 0.9f
    j->velocity.y *= 0.9; //? 0.9f
    j->velocity.z *= 0.9; //? 0.9f

    j->worldPos.x += j->velocity.x;
    j->worldPos.y += j->velocity.y;
    j->worldPos.z += j->velocity.z;
}

/* 240624 -> 240658 */
void func_80191E54(struct ObjJoint *j) {
    j->unk3C.x = j->worldPos.x;
    j->unk3C.y = j->worldPos.y;
    j->unk3C.z = j->worldPos.z;
}

/* 240658 -> 2406B8 */
void func_80191E88(struct ObjGroup *grp) {
    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191BF8, grp);
    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191D60, grp);
    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191E54, grp);
}

/* 2406B8 -> 2406E0; orig name: func_80191EE8 */
void reset_joint_counts(void) {
    sJointCount = 0;
    sJointNotF1Count = 0;
    sBoneCount = 0;
}
