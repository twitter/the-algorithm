#include <ultra64.h>
#include <macros.h>
#include "gd_types.h"
#include "objects.h"
#include "skin_movement.h"
#include "debug_utils.h"
#include "joints.h"
#include "skin.h"
#include "gd_math.h"

/* bss */
struct ObjWeight *sSkinNetCurWeight;
static Mat4f D_801B9EA8; // TODO: rename to sHead2Mtx?
static struct ObjJoint *D_801B9EE8;

/* @ 22FDB0 for 0x180 */
void Unknown801815E0(Mat4f *mtx) {
    struct GdVec3f scratchVec;

    scratchVec.x = (*mtx)[0][0];
    scratchVec.y = (*mtx)[0][1];
    scratchVec.z = (*mtx)[0][2];
    gd_normalize_vec3f(&scratchVec);
    (*mtx)[0][0] = scratchVec.x;
    (*mtx)[0][1] = scratchVec.y;
    (*mtx)[0][2] = scratchVec.z;

    scratchVec.x = (*mtx)[1][0];
    scratchVec.y = (*mtx)[1][1];
    scratchVec.z = (*mtx)[1][2];
    gd_normalize_vec3f(&scratchVec);
    (*mtx)[1][0] = scratchVec.x;
    (*mtx)[1][1] = scratchVec.y;
    (*mtx)[1][2] = scratchVec.z;

    scratchVec.x = (*mtx)[2][0];
    scratchVec.y = (*mtx)[2][1];
    scratchVec.z = (*mtx)[2][2];
    gd_normalize_vec3f(&scratchVec);
    (*mtx)[2][0] = scratchVec.x;
    (*mtx)[2][1] = scratchVec.y;
    (*mtx)[2][2] = scratchVec.z;
}

/* @ 22FF30 for 0xDC */
/* called with ObjNext->unk1A8 (variable obj ptr?) ->unk20 or ->unk24 ptr*/
// TODO: figure out the proper object type for a0
void func_80181760(struct ObjGroup *a0) {
    register f32 sp1C;
    register struct Links *link;
    struct ObjVertex *vtx;

    for (link = a0->link1C; link != NULL; link = link->next) {
        vtx = (struct ObjVertex *) link->obj;

        if ((sp1C = vtx->scaleFactor) != 0.0f) {
            vtx->pos.x = vtx->initPos.x * sp1C;
            vtx->pos.y = vtx->initPos.y * sp1C;
            vtx->pos.z = vtx->initPos.z * sp1C;
        } else {
            vtx->pos.x = vtx->pos.y = vtx->pos.z = 0.0f;
        }
    }
}

/* @ 23000C for 0x58; orig name: func8018183C*/
void move_skin(struct ObjNet *net) {
    UNUSED u8 pad1C[8];

    if (net->unk1A8 != NULL) {
        func_80181760(net->unk1A8->unk24);
    }
}

/* @ 230064 for 0x13C*/
void func_80181894(struct ObjJoint *joint) {
    register struct ObjGroup *weightGroup; // baseGroup? weights Only?
    struct GdVec3f stackVec;
    register struct ObjWeight *curWeight;
    register struct ObjVertex *connectedVtx;
    register struct Links *link;
    register f32 scaleFactor;
    struct GdObj *linkedObj;

    weightGroup = joint->unk1F4;
    if (weightGroup != NULL) {
        for (link = weightGroup->link1C; link != NULL; link = link->next) {
            linkedObj = link->obj;
            curWeight = (struct ObjWeight *) linkedObj;

            if (curWeight->unk38 > 0.0) //? 0.0f
            {
                stackVec.x = curWeight->vec20.x;
                stackVec.y = curWeight->vec20.y;
                stackVec.z = curWeight->vec20.z;
                gd_rotate_and_translate_vec3f(&stackVec, &joint->matE8);

                connectedVtx = curWeight->unk3C;
                scaleFactor = curWeight->unk38;

                connectedVtx->pos.x += stackVec.x * scaleFactor;
                connectedVtx->pos.y += stackVec.y * scaleFactor;
                connectedVtx->pos.z += stackVec.z * scaleFactor;
            }
        }
    }
}

/* @ 2301A0 for 0x110 */
void Unknown801819D0(struct ObjVertex *vtx) {
    struct GdVec3f localVec;
    UNUSED u8 pad24[0x10];

    if (sTargetWeightID++ == sSkinNetCurWeight->id) {
        sSkinNetCurWeight->unk3C = vtx;
        localVec.x = vtx->pos.x;
        localVec.y = vtx->pos.y;
        localVec.z = vtx->pos.z;

        gd_rotate_and_translate_vec3f(&localVec, &D_801B9EA8);
        sSkinNetCurWeight->vec20.x = localVec.x;
        sSkinNetCurWeight->vec20.y = localVec.y;
        sSkinNetCurWeight->vec20.z = localVec.z;

        vtx->scaleFactor -= sSkinNetCurWeight->unk38;
    }
}

/* @ 2302B0 for 0xA8; orig name: Unknown80181AE0 */
void reset_weight(struct ObjWeight *weight) {
    UNUSED u32 vtxCount;
    UNUSED u32 pad20;
    struct ObjGroup *skinGroup;

    sSkinNetCurWeight = weight;
    sTargetWeightID = 0;
    if ((skinGroup = gGdSkinNet->skinGrp) != NULL) {
        vtxCount =
            apply_to_obj_types_in_group(OBJ_TYPE_VERTICES, (applyproc_t) Unknown801819D0, skinGroup);
    } else {
        fatal_printf("reset_weight(): Skin net has no SkinGroup");
    }

    if (weight->unk3C == NULL) {
        fatal_printf("reset_weight(): Skin vertex ID %d not found", weight->id);
    }
}

/* @ 230358 for 0x78; rename to reset_joint_weight? */
void Unknown80181B88(struct ObjJoint *joint) {
    struct ObjGroup *group;

    gd_inverse_mat4f(&joint->matE8, &D_801B9EA8);
    D_801B9EE8 = joint;
    if ((group = joint->unk1F4) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_WEIGHTS, (applyproc_t) reset_weight, group);
    }
}
