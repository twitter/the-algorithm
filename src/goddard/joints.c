#include <ultra64.h>
#include <macros.h>

#ifdef VERSION_EU
#include "prevent_bss_reordering.h"
#endif

#include "gd_types.h"
#include "gd_macros.h"
#include "joints.h"
#include "gd_main.h"
#include "sfx.h"
#include "draw_objects.h"
#include "objects.h"
#include "skin_movement.h"
#include "dynlist_proc.h"
#include "debug_utils.h"
#include "skin.h"
#include "gd_math.h"
#include "renderer.h"

// data
static s32 D_801A82D0 = 0;
static struct ObjBone *gGdTempBone = NULL; // @ 801A82D4

// bss
s32 sTargetWeightID; // @ 801BA960

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

/* 23CCF0 -> 23D3B8 */
void Proc8018E520(struct ObjJoint *self) {
    UNUSED u8 pad78[0xC8 - 0x78];
    Mat4f *sp74;
    UNUSED u8 pad70[4];
    struct GdVec3f sp64;
    UNUSED u8 pad50[0x10];
    register struct Links *att; // sp4C?
    UNUSED u8 pad48[0x8];
    struct GdObj *attobj; // sp44

    sp64.x = self->mat128[3][0] - self->unk54.x;
    sp64.y = self->mat128[3][1] - self->unk54.y;
    sp64.z = self->mat128[3][2] - self->unk54.z;

    if (self->header.drawFlags & OBJ_PICKED) {
        self->unk78.x = sp64.x * -0.25; //? -0.25f
        self->unk78.y = sp64.y * -0.25; //? -0.25f
        self->unk78.z = sp64.z * -0.25; //? -0.25f

        self->unk1BC |= 0x2000;
        ; // necessary?
    } else {
        if (gGdCtrl.trgR == FALSE) // R-trigger not held or released
        {
            self->unk78.x -= sp64.x * 0.5; //? 0.5f
            self->unk78.y -= sp64.y * 0.5; //? 0.5f
            self->unk78.z -= sp64.z * 0.5; //? 0.5f

            self->unk78.x *= 0.8; //? 0.8f
            self->unk78.y *= 0.8; //? 0.8f
            self->unk78.z *= 0.8; //? 0.8f

            if (ABS(self->unk78.x) + ABS(self->unk78.y) + ABS(self->unk78.z) < 1.0) //? 1.0f
            {
                if (ABS(sp64.x) + ABS(sp64.y) + ABS(sp64.z) < 1.0) //? 1.0f
                {
                    self->unk78.x = self->unk78.y = self->unk78.z = 0.0f;
                    self->mat128[3][0] -= sp64.x;
                    self->mat128[3][1] -= sp64.y;
                    self->mat128[3][2] -= sp64.z;
                }
            }

            if (self->unk1BC & 0x2000) {
                gd_play_sfx(GD_SFX_LET_GO_FACE);
            }

            self->unk1BC &= ~0x2000;
            ; // necessary?
        } else {
            self->unk78.x = self->unk78.y = self->unk78.z = 0.0f;
        }
    }

    self->mat128[3][0] += self->unk78.x;
    self->mat128[3][1] += self->unk78.y;
    self->mat128[3][2] += self->unk78.z;

    if (self->header.drawFlags & OBJ_PICKED) {
        gGdCtrl.csrX -= (gGdCtrl.csrX - gGdCtrl.csrXatApress) * 0.2;
        gGdCtrl.csrY -= (gGdCtrl.csrY - gGdCtrl.csrYatApress) * 0.2;
    }

    sp64.x = self->mat128[3][0] - self->unk54.x;
    sp64.y = self->mat128[3][1] - self->unk54.y;
    sp64.z = self->mat128[3][2] - self->unk54.z;

    for (att = self->unk1F8->link1C; att != NULL; att = att->next) {
        attobj = att->obj;
        set_cur_dynobj(attobj);
        sp74 = d_get_matrix_ptr();
        gd_add_vec3f_to_mat4f_offset(sp74, &sp64);
    }
}

/* 23D3B8 -> 23D62C */
void Proc8018EBE8(struct ObjJoint *self) {
    Mat4f *sp5C;
    struct GdVec3f sp50;
    struct GdVec3f sp44;
    UNUSED u8 pad2c[0x18];
    register struct Links *att; // sp28
    struct GdObj *attobj;       // sp24

    if (sCurrentMoveCamera == NULL) {
        return;
    }

    if (self->unk1D0 != NULL) {
        if (self->unk1D0->unk4C != 7) {
            return;
        }
    }

    set_cur_dynobj(self);
    sp5C = d_get_rot_mtx_ptr();
    sp44.x = (*sp5C)[3][0];
    sp44.y = (*sp5C)[3][1];
    sp44.z = (*sp5C)[3][2];
    func_80179B9C(&sp44, sCurrentMoveCamera, sCurrentMoveView);

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

    for (att = self->unk1F8->link1C; att != NULL; att = att->next) {
        attobj = att->obj;
        set_cur_dynobj(attobj);
        sp5C = d_get_rot_mtx_ptr();
        gd_add_vec3f_to_mat4f_offset(sp5C, &sp50);
    }
}

/* 23D62C -> 23D748; not called */
void Unknown8018EE5C(struct ObjJoint *j1, struct ObjJoint *j2, struct ObjJoint *j3) {
    struct GdVec3f vec;
    struct ObjJoint *curj;

    if (j3 == NULL) {
        return;
    }

    vec.z = j3->unk14.x;
    vec.y = j3->unk14.y;
    vec.x = j3->unk14.z;

    curj = j1;
    while (curj != NULL) {
        set_joint_vecs(curj, curj->unk14.x + vec.z, curj->unk14.y + vec.y, curj->unk14.z + vec.x);
        if (curj == j2) {
            break;
        }
        curj = curj->prevjoint;
    }
}

/* 23D748 -> 23D818; orig name: func_8018EF78 */
void set_joint_vecs(struct ObjJoint *j, f32 x, f32 y, f32 z) {
    j->unk14.x = x;
    j->unk14.y = y;
    j->unk14.z = z;

    j->unk30.x = x;
    j->unk30.y = y;
    j->unk30.z = z;

    j->unk3C.x = x;
    j->unk3C.y = y;
    j->unk3C.z = z;

    j->unk54.x = x;
    j->unk54.y = y;
    j->unk54.z = z;

    j->mat128[3][0] = x;
    j->mat128[3][1] = y;
    j->mat128[3][2] = z;
}

/* 23D818 -> 23DA18 */
struct ObjJoint *make_joint(s32 flags, f32 x, f32 y, f32 z) {
    struct ObjJoint *j; // sp24
    struct ObjJoint *oldhead;
    UNUSED u32 pad1C;

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
    j->unk1CC = 0;
    j->id = sJointCount;
    j->unk1BC = flags;

    if (!(j->unk1BC & 0x1)) {
        sJointNotF1Count++;
    }

    if (j->unk1BC & 0x1) {
        j->unk1C8 = 2;
    } else {
        j->unk1C8 = 9;
    }

    j->unk1C4 = NULL;
    j->unk20 = NULL;
    j->unk9C.x = 1.0f;
    j->unk9C.y = 1.0f;
    j->unk9C.z = 1.0f;
    j->unkDC.x = 0.0f;
    j->unkDC.y = 0.0f;
    j->unkDC.z = 0.0f;
    j->fn2C = NULL;

    return j;
}

/* 23DA18 -> 23DAF8; orig name: func_8018F248 */
struct ObjJoint *make_joint_withshape(struct ObjShape *shape, s32 flags, f32 x, f32 y, f32 z) {
    struct ObjJoint *j;

    j = make_joint(0, x, y, z);
    j->unk20 = shape;
    j->unk1CC = 5;
    j->unk1BC |= flags;
    j->unk1C8 = 9;
    j->header.drawFlags |= OBJ_IS_GRABBALE;
    j->header.drawFlags |= OBJ_NOT_DRAWABLE;
    j->fn2C = &Proc8018E520;
    j->unk1D0 = NULL;

    return j;
}

/* 23DAF8 -> 23DC9C */
void func_8018F328(struct ObjBone *b) {
    struct ObjJoint *sp24;
    struct ObjJoint *sp20;
    struct ObjGroup *grp; // sp1C
    struct Links *link;   // sp18

    grp = b->unk10C;
    link = grp->link1C;
    sp24 = (struct ObjJoint *) link->obj;
    link = link->next;
    sp20 = (struct ObjJoint *) link->obj;

    b->unk14.x = (sp24->unk14.x + sp20->unk14.x) / 2.0; //?2.0f
    b->unk14.y = (sp24->unk14.y + sp20->unk14.y) / 2.0; //?2.0f
    b->unk14.z = (sp24->unk14.z + sp20->unk14.z) / 2.0; //?2.0f

    b->unk58.x = sp20->unk14.x - sp24->unk14.x;
    b->unk58.y = sp20->unk14.y - sp24->unk14.y;
    b->unk58.z = sp20->unk14.z - sp24->unk14.z;

    gd_normalize_vec3f(&b->unk58);
    gd_create_origin_lookat(&b->matB0, &b->unk58, 0); //? 0.0f
}

/* 23DC9C -> 23DCF0 */
void Unknown8018F4CC(struct ObjJoint *j) {
    if (j->unk1BC & 0x1000) {
        j->unkB4.x = D_801BA968.x;
        j->unkB4.y = D_801BA968.y;
        j->unkB4.z = D_801BA968.z;
    }
}

/* 23DCF0 -> 23E06C */
void func_8018F520(struct ObjBone *b) {
    struct ObjJoint *spAC;
    struct ObjJoint *spA8;
    UNUSED u32 pad[3];
    struct GdVec3f sp90;
    struct GdVec3f sp84;
    struct GdVec3f sp78;
    struct GdVec3f sp6C;
    f32 sp68;
    f32 sp64;
    struct ObjGroup *grp; // sp60
    struct Links *link;
    Mat4f mtx; // sp1C

    grp = b->unk10C;
    link = grp->link1C;
    spAC = (struct ObjJoint *) link->obj;
    link = link->next;
    spA8 = (struct ObjJoint *) link->obj;

    b->unk14.x = (spAC->unk14.x + spA8->unk14.x) / 2.0; //? 2.0f;
    b->unk14.y = (spAC->unk14.y + spA8->unk14.y) / 2.0; //? 2.0f;
    b->unk14.z = (spAC->unk14.z + spA8->unk14.z) / 2.0; //? 2.0f;
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

    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown8018F4CC, b->unk10C);
}

/* 23E06C -> 23E238 */
void func_8018F89C(struct ObjBone *b) {
    struct ObjJoint *spAC;
    struct ObjJoint *spA8;
    UNUSED u8 pad64[0x44];
    struct ObjGroup *grp; // sp60
    struct Links *link;   // sp5c
    Mat4f mtx;            // sp1c

    grp = b->unk10C;
    link = grp->link1C;
    spAC = (struct ObjJoint *) link->obj;
    link = link->next;
    spA8 = (struct ObjJoint *) link->obj;

    b->unk14.x = (spAC->unk14.x + spA8->unk14.x) / 2.0; //? 2.0f;
    b->unk14.y = (spAC->unk14.y + spA8->unk14.y) / 2.0; //? 2.0f;
    b->unk14.z = (spAC->unk14.z + spA8->unk14.z) / 2.0; //? 2.0f;

    gd_mult_mat4f(&b->matB0, &gGdSkinNet->mat128, &mtx);
    gd_copy_mat4f(&mtx, &b->mat70);

    D_801BA968.x = -b->mat70[2][0];
    D_801BA968.y = -b->mat70[2][1];
    D_801BA968.z = -b->mat70[2][2];
    D_801BA964 = &b->mat70;

    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown8018F4CC, b->unk10C);
}

/* 23E238 -> 23E298 */
void Unknown8018FA68(struct ObjBone *b) {
    if (b->unk104 & (0x8 | 0x2)) {
        func_8018F89C(b);
    } else {
        func_8018F520(b);
    }
}

/* 23E298 -> 23E328; orig name: func_8018FAC8 */
s32 set_skin_weight(struct ObjJoint *j, s32 id, struct ObjVertex *vtx, f32 weight) {
    struct ObjWeight *w;

    if (j->unk1F4 == NULL) {
        j->unk1F4 = make_group(0);
    }
    w = make_weight(0, id, vtx, weight);
    addto_group(j->unk1F4, &w->header);

    return TRUE;
}

/* 23E328 -> 23E474 */
void func_8018FB58(struct ObjBone *b) {
    struct GdVec3f vec;  // sp2c
    struct ObjJoint *j1; // sp28
    struct ObjJoint *j2;
    struct Links *link;
    struct ObjGroup *grp;

    grp = b->unk10C;
    link = grp->link1C;
    j1 = (struct ObjJoint *) link->obj;
    link = link->next;
    j2 = (struct ObjJoint *) link->obj;

    vec.x = j1->unk14.x - j2->unk14.x;
    vec.y = j1->unk14.y - j2->unk14.y;
    vec.z = j1->unk14.z - j2->unk14.z;

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

    if (b->unk10C->objCount == 2) {
        func_8018FB58(b);
    }
}

/* 23E56C -> 23E6E4 */
struct ObjBone *make_bone(s32 a0, struct ObjJoint *j1, struct ObjJoint *j2, UNUSED s32 a3) {
    struct ObjBone *b; // sp34
    struct ObjBone *oldhead;
    UNUSED u32 pad1C[5];

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
    b->unk100 = 0;
    b->unk104 = a0;
    b->unkF0 = NULL;
    gd_set_identity_mat4(&b->mat70);
    b->unk110 = 0.8f;
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
void Unknown8018FF14(UNUSED u32 a0) {
}

/* 23E6F8 -> 23E758; not called */
void Unknown8018FF28(struct ObjJoint *a0, struct ObjJoint *a1) {
    if (a1->unk1BC & 0x1) {
        a0->unk84.x -= a1->unk84.x;
        a0->unk84.y -= a1->unk84.y;
        a0->unk84.z -= a1->unk84.z;
    }
}

/* 23E758 -> 23E7B8; not called */
void Unknown8018FF88(s32 size) {
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
    struct Links *bonelink;   // 08
    struct Links *link;       // 04

    grp = a3->unk1C4;

    if (grp == NULL) {
        return 0;
    }
    link = grp->link1C;
    if (link == NULL) {
        return 0;
    }

    while (link != NULL) {
        if ((b = (struct ObjBone *) link->obj) != NULL) {
            bonegrp = b->unk10C;
            bonelink = bonegrp->link1C;

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
    UNUSED u8 pad64[0x7c - 0x64];
    f32 sp60;
    f32 sp5C;
    f32 sp58;
    UNUSED u8 pad1C[0x58 - 0x1C];

    return;

    b->unk58.x = sp7C.x;
    b->unk58.y = sp7C.y;
    b->unk58.z = sp7C.z;

    if (b->unk104 & 0x8) {
        sp58 = gd_vec3f_magnitude(&sp7C);
        if (sp58 == 0.0f) {
            sp58 = 1.0f;
        }
        sp60 = (b->unkF8 / sp58) * b->unk110;
    }

    if (b->unk104 & 0x4) {
        if (sp60 > (sp58 = gd_vec3f_magnitude(&sp7C))) {
            sp5C = b->unk110;
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
            sp5C = b->unk110;
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

    if (j->unk1BC & 0x1 || (j->unk1BC & 0x1000) == 0) {
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
void func_80190574(s32 a0, struct ObjJoint *a1, struct ObjJoint *a2, f32 x, f32 y, f32 z) // sp278
{
    struct ObjJoint *sp274; // = a2?
    struct ObjJoint *sp270; // mid-point of stack array?
    struct ObjJoint *sp26C; // jointstackarr[i]? curjoint?
    UNUSED u32 pad268;
    UNUSED u32 sp264 = 0;
    UNUSED u32 sp258[3]; // unused vec?
    struct GdVec3f sp24C;
    struct GdVec3f sp240;
    UNUSED u32 pad238[2];
    s32 sp234; // i?
    s32 sp230;
    s32 sp22C = 1;
    UNUSED u32 pad228;
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
                if (sp26C->unk1BC & 0x1) {
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
void Unknown80190A20(void) {
    struct ObjJoint *j; // sp3c
    UNUSED u32 pad38;
    struct GdVec3f vec; // sp2C
    struct ObjGroup *grp;
    struct Links *link;
    struct ObjBone *b;

    j = gGdJointList;
    while (j != NULL) {
        if (j->unk1BC & 0x40) {
            grp = j->unk1C4;
            link = grp->link1C;
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
void func_80190B54(struct ObjJoint *a0, struct ObjJoint *a1, struct GdVec3f *a2) // b0
{
    struct GdVec3f spA4;
    UNUSED struct GdVec3f pad98;
    struct GdVec3f sp8C;
    struct GdVec3f sp80;
    f32 sp7C;
    f32 sp78;
    Mat4f sp38;
    UNUSED u8 pad1C[0x1C];

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
void Unknown80190E68(struct GdObj *obj, f32 x, f32 y, f32 z) {
    struct ObjJoint *sp44;
    struct GdObj *sp40;
    struct GdVec3f vec; // sp34
    UNUSED u32 pad1C[6];

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
        sp40 = ((struct ObjJoint *) sp40)->unk20C; //"attached object"
    }
}

/* 23F70C -> 23F978 */
f32 func_80190F3C(struct ObjJoint *a0, f32 a1, f32 a2, f32 a3) {
    struct ObjJoint *curj; // 34
    s32 i;                 // 30
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
void Unknown801911A8(struct ObjJoint *j) {
    j->unkCC.x = j->unkC0.x; // storing "shape offset"?
    j->unkCC.y = j->unkC0.y;
    j->unkCC.z = j->unkC0.z;

    gd_rotate_and_translate_vec3f(&j->unkCC, &gGdSkinNet->mat128);
}

/* 23F9F0 -> 23FB90 */
void Unknown80191220(struct ObjJoint *j) {
    j->unk48.x = j->unk54.x; // storing "attached offset"?
    j->unk48.y = j->unk54.y;
    j->unk48.z = j->unk54.z;

    gd_mat4f_mult_vec3f(&j->unk48, &gGdSkinNet->mat128);
    j->unk3C.x = j->unk48.x;
    j->unk3C.y = j->unk48.y;
    j->unk3C.z = j->unk48.z;
    j->unk14.x = gGdSkinNet->unk14.x;
    j->unk14.y = gGdSkinNet->unk14.y;
    j->unk14.z = gGdSkinNet->unk14.z;

    j->unk14.x += j->unk3C.x;
    j->unk14.y += j->unk3C.y;
    j->unk14.z += j->unk3C.z;
    j->unk1A8.x = j->unk1A8.y = j->unk1A8.z = 0.0f;
    gGdCounter.ctr0++;
}

/* 23FB90 -> 23FBC0 */
void Unknown801913C0(struct ObjJoint *j) {
    UNUSED u32 pad[4];
    func_80181894(j);
}

/* 23FBC0 -> 23FCC8 */
void Unknown801913F0(struct ObjJoint *j) {
    j->unk78.x = j->unk14.x;
    j->unk78.y = j->unk14.y;
    j->unk78.z = j->unk14.z;

    j->unk78.x -= j->unk30.x;
    j->unk78.y -= j->unk30.y;
    j->unk78.z -= j->unk30.z;

    j->unk30.x = j->unk14.x;
    j->unk30.y = j->unk14.y;
    j->unk30.z = j->unk14.z;

    gd_copy_mat4f(&gGdSkinNet->mat128, &j->matE8);
}

/* 23FCC8 -> 23FCDC */
void Unknown801914F8(UNUSED struct ObjJoint *j) {
}

/* 23FCDC -> 23FDD4; not called */
void Unknown8019150C(Mat4f *a0, struct GdVec3f *a1) {
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
void func_80191604(struct ObjJoint *j) {
    j->unk14.x = j->unk54.x;
    j->unk14.y = j->unk54.y;
    j->unk14.z = j->unk54.z;

    j->unk30.x = j->unk54.x;
    j->unk30.y = j->unk54.y;
    j->unk30.z = j->unk54.z;

    j->unk3C.x = j->unk54.x;
    j->unk3C.y = j->unk54.y;
    j->unk3C.z = j->unk54.z;

    j->unk78.x = j->unk78.y = j->unk78.z = 0.0f;
    j->unk84.x = j->unk84.y = j->unk84.z = 0.0f;
    j->unk90.x = j->unk90.y = j->unk90.z = 0.0f;
    j->unk1A8.x = j->unk1A8.y = j->unk1A8.z = 0.0f;

    gd_set_identity_mat4(&j->mat168);
    gd_scale_mat4f_by_vec3f(&j->mat168, (struct GdVec3f *) &j->unk9C);
    gd_rot_mat_about_vec(&j->mat168, (struct GdVec3f *) &j->unk6C);
    gd_add_vec3f_to_mat4f_offset(&j->mat168, &j->unk200);
    gd_copy_mat4f(&j->mat168, &j->matE8);

    gd_set_identity_mat4(&j->mat128);
    gd_add_vec3f_to_mat4f_offset(&j->mat128, &j->unk54);
}

/* 23FFF4 -> 2400C4 */
void Unknown80191824(struct ObjJoint *j) {
    UNUSED struct ObjNet *sp14;
    UNUSED u32 pad00[4];

    sp14 = gGdSkinNet->unk1F0;
    if (j->unk1BC & 0x1) {
        j->unk14.x = gGdSkinNet->unk14.x;
        j->unk14.y = gGdSkinNet->unk14.y;
        j->unk14.z = gGdSkinNet->unk14.z;

        j->unk3C.x = gGdSkinNet->unk14.x;
        j->unk3C.y = gGdSkinNet->unk14.y;
        j->unk3C.z = gGdSkinNet->unk14.z;
    }
}

/* 2400C4 -> 2401EC; not called */
void Unknown801918F4(struct ObjJoint *j) {
    f32 sp4;

    j->unk78.x = j->unk3C.x;
    j->unk78.y = j->unk3C.y;
    j->unk78.z = j->unk3C.z;

    j->unk78.x -= j->unk30.x;
    j->unk78.y -= j->unk30.y;
    j->unk78.z -= j->unk30.z;

    j->unk30.x = j->unk3C.x;
    j->unk30.y = j->unk3C.y;
    j->unk30.z = j->unk3C.z;

    sp4 = -4.0f;

    if (!(j->unk1BC & 0x41)) {
        j->unk78.y += sp4 * 0.2; //? 0.2f

        j->unk3C.x += j->unk78.x;
        j->unk3C.y += j->unk78.y;
        j->unk3C.z += j->unk78.z;
    }
}

/* 2401EC -> 2403C8; not called */
void Unknown80191A1C(struct ObjBone *a0) {
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
        tempjoint = gGdTempBone->unk10C->link1C->obj;
        argjoint = a0->unk10C->link1C->next->obj;
        set_cur_dynobj(argjoint);
        d_get_rel_pos(&sp24);
        set_cur_dynobj(tempjoint);
        d_get_rel_pos(&sp18);

        sp24.x -= sp18.x;
        sp24.y -= sp18.y;
        sp24.z -= sp18.z;
        gd_normalize_vec3f(&sp24);

        sp3C = -sp3C * 50.0; //? 50.0f
        if (!(((struct ObjJoint *) argjoint)->unk1BC & 0x1)) {
            func_80190F3C((struct ObjJoint *) argjoint, sp24.x * sp3C, sp24.y * sp3C, sp24.z * sp3C);
        }
    }
    gGdTempBone = a0;
}

/* 2403C8 -> 240530 */
void Unknown80191BF8(struct ObjJoint *j) {
    f32 sp1C;
    f32 sp18 = -2.0f;

    if (!(j->unk1BC & 0x1)) {
        j->unk3C.y += sp18;
    }

    if ((sp1C = j->unk3C.y - (D_801A8058 + 30.0f)) < 0.0f && j->unk78.y < 0.0f) {
        sp1C += j->unk78.y;
        sp1C *= 0.8; //? 0.8f
        func_80190F3C(j, -j->unk78.x * 0.7, -sp1C, -j->unk78.z * 0.7);
    }

    func_80190F3C(j, 0.0f, 0.0f, 0.0f);
}

/* 240530 -> 240624 */
void Unknown80191D60(struct ObjJoint *j) {
    j->unk78.x += j->unk3C.x - j->unk14.x;
    j->unk78.y += j->unk3C.y - j->unk14.y;
    j->unk78.z += j->unk3C.z - j->unk14.z;

    j->unk78.x *= 0.9; //? 0.9f
    j->unk78.y *= 0.9; //? 0.9f
    j->unk78.z *= 0.9; //? 0.9f

    j->unk14.x += j->unk78.x;
    j->unk14.y += j->unk78.y;
    j->unk14.z += j->unk78.z;
}

/* 240624 -> 240658 */
void Unknown80191E54(struct ObjJoint *j) {
    j->unk3C.x = j->unk14.x;
    j->unk3C.y = j->unk14.y;
    j->unk3C.z = j->unk14.z;
}

/* 240658 -> 2406B8 */
void func_80191E88(struct ObjGroup *grp) {
    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80191BF8, grp);
    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80191D60, grp);
    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80191E54, grp);
}

/* 2406B8 -> 2406E0; orig name: func_80191EE8 */
void reset_joint_counts(void) {
    sJointCount = 0;
    sJointNotF1Count = 0;
    sBoneCount = 0;
}
