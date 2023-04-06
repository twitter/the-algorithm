#include <ultra64.h>
#include <macros.h>
#include <stdio.h>

#include "gd_types.h"
#include "old_menu.h"
#include "objects.h"
#include "dynlist_proc.h"
#include "debug_utils.h"
#include "renderer.h"

/**
 * @file old_menu.c
 *
 * This file contains code for rendering what appears to be an old menuing system.
 * It is hard to tell, as most of the menuing code is stubbed out.
 * It also contains code for creating labels and gadget, which are `GdObj`s that
 * allow for displaying text and memory values on screen. Those `GdObj`s are not
 * created in-game, but there are some functions in `renderer.c` that use
 * them, and those functions may still work if called.
 */

// bss
static char sMenuStrBuf[0x100];
static struct GdVec3f sStaticVec;
static struct GdVec3f unusedVec;
static struct ObjGadget *sCurGadgetPtr;

// forward declarations
void func_8018BCB8(struct ObjGadget *);

/* 239EC0 -> 239F78 */
void get_objvalue(union ObjVarVal *dst, enum ValPtrType type, void *base, size_t offset) {
    union ObjVarVal *objAddr = (void *) ((u8 *) base + offset);

    switch (type) {
        case OBJ_VALUE_INT:
            dst->i = objAddr->i;
            break;
        case OBJ_VALUE_FLOAT:
            dst->f = objAddr->f;
            break;
        default:
            fatal_printf("%s: Undefined ValueType", "get_objvalue");
    }
}

/* 239F78 -> 23A00C */
void Unknown8018B7A8(void *a0) {
    struct GdVec3f sp1C;

    set_cur_dynobj(a0);
    d_get_init_pos(&sp1C);

    sp1C.x += sStaticVec.x;
    sp1C.y += sStaticVec.y;
    sp1C.z += sStaticVec.z;
    d_set_world_pos(sp1C.x, sp1C.y, sp1C.z);
}

/* 23A00C -> 23A068 */
void Proc8018B83C(void *a0) {
    struct ObjGroup *argGroup = a0;
    apply_to_obj_types_in_group(OBJ_TYPE_GADGETS, (applyproc_t) func_8018BCB8, argGroup);
    apply_to_obj_types_in_group(OBJ_TYPE_VIEWS, (applyproc_t) Proc801A43DC, gGdViewsGroup);
}

/* 23A068 -> 23A0D0; orig name: Unknown8018B898 */
void cat_grp_name_to_buf(struct ObjGroup *group) {
    char buf[0x100]; // sp18

    if (group->debugPrint == 1) {
        sprintf(buf, "| %s %%x%d", group->name, (u32) (uintptr_t) group);
        gd_strcat(sMenuStrBuf, buf); // gd_strcat?
    }
}

/* 23A0D0 -> 23A190 */
void *Unknown8018B900(struct ObjGroup *grp) {
    void *mainMenu;
    void *defaultSettingMenu;
    void *controlerMenu;

    gd_strcpy(sMenuStrBuf, "Default Settings %t %F"); // gd_strcpy?
    apply_to_obj_types_in_group(OBJ_TYPE_GROUPS, (applyproc_t) cat_grp_name_to_buf, grp);
    defaultSettingMenu = func_801A43F0(sMenuStrBuf, &Proc8018B83C);
    controlerMenu = func_801A43F0(
        "Control Type %t %F| U-64 Analogue Joystick %x1 | Keyboard %x2 | Mouse %x3", &Proc801A4410);
    mainMenu =
        func_801A43F0("Dynamics %t |\t\t\tReset Positions %f |\t\t\tSet Defaults %m |\t\t\tSet "
                      "Controller %m |\t\t\tRe-Calibrate Controller %f |\t\t\tQuit %f",
                      &func_8017E2B8, defaultSettingMenu, controlerMenu, &Proc801A4424, &gd_exit);

    return mainMenu;
}

/* 23A190 -> 23A250 */
struct ObjLabel *make_label(struct ObjValPtrs *ptr, char *str, s32 a2, f32 x, f32 y, f32 z) {
    struct ObjLabel *label = (struct ObjLabel *) make_object(OBJ_TYPE_LABELS);
    label->valfn = NULL;
    label->valptr = ptr;
    label->fmtstr = str;
    label->unk24 = a2;
    label->unk30 = 4;
    label->vec14.x = x;
    label->vec14.y = y;
    label->vec14.z = z;

    return label;
}

/* 23A250 -> 23A32C */
struct ObjGadget *make_gadget(UNUSED s32 a0, s32 a1) {
    struct ObjGadget *gdgt = (struct ObjGadget *) make_object(OBJ_TYPE_GADGETS);
    gdgt->unk4C = NULL;
    gdgt->unk3C = 1.0f;
    gdgt->unk38 = 0.0f;
    gdgt->unk20 = a1;
    gdgt->unk5C = 0;
    gdgt->unk28 = 1.0f;
    gdgt->unk40.x = 100.0f;
    gdgt->unk40.y = 10.0f;
    gdgt->unk40.z = 10.0f;

    return gdgt;
}

/* 23A32C -> 23A3E4 */
void set_objvalue(union ObjVarVal *src, enum ValPtrType type, void *base, size_t offset) {
    union ObjVarVal *dst = (void *) ((u8 *) base + offset);
    switch (type) {
        case OBJ_VALUE_INT:
            dst->i = src->i;
            break;
        case OBJ_VALUE_FLOAT:
            dst->f = src->f;
            break;
        default:
            fatal_printf("%s: Undefined ValueType", "set_objvalue");
    }
}

/* 23A3E4 -> 23A488; orig name: Unknown8018BD54 */
void set_static_gdgt_value(struct ObjValPtrs *vp) {
    switch (vp->datatype) {
        case OBJ_VALUE_FLOAT:
            set_objvalue(&sCurGadgetPtr->varval, OBJ_VALUE_FLOAT, vp->obj, vp->offset);
            break;
        case OBJ_VALUE_INT:
            set_objvalue(&sCurGadgetPtr->varval, OBJ_VALUE_INT, vp->obj, vp->offset);
            break;
    }
}

/* 23A488 -> 23A4D0 */
void func_8018BCB8(struct ObjGadget *gdgt) {
    UNUSED u8 pad[4];

    sCurGadgetPtr = gdgt;
    apply_to_obj_types_in_group(OBJ_TYPE_VALPTRS, (applyproc_t) set_static_gdgt_value, gdgt->unk4C);
}

/* 23A4D0 -> 23A784 */
void adjust_gadget(struct ObjGadget *gdgt, s32 a1, s32 a2) {
    UNUSED u8 pad[8];
    f32 sp2C;
    struct ObjValPtrs *vp;

    if (gdgt->unk24 == 1) {
        gdgt->unk28 += a2 * (-sCurrentMoveCamera->unk40.z * 1.0E-5);
    } else if (gdgt->unk24 == 2) {
        gdgt->unk28 += a1 * (-sCurrentMoveCamera->unk40.z * 1.0E-5);
    }

    if (gdgt->unk28 < 0.0f) {
        gdgt->unk28 = 0.0f;
    } else if (gdgt->unk28 > 1.0f) {
        gdgt->unk28 = 1.0f;
    }

    sp2C = gdgt->unk3C - gdgt->unk38;

    if (gdgt->unk4C != NULL) {
        vp = (struct ObjValPtrs *) gdgt->unk4C->link1C->obj;

        switch (vp->datatype) {
            case OBJ_VALUE_FLOAT:
                gdgt->varval.f = gdgt->unk28 * sp2C + gdgt->unk38;
                break;
            case OBJ_VALUE_INT:
                gdgt->varval.i = ((s32)(gdgt->unk28 * sp2C)) + gdgt->unk38;
                break;
            default:
                fatal_printf("%s: Undefined ValueType", "adjust_gadget");
        }
    }

    func_8018BCB8(gdgt);
}

/* 23A784 -> 23A940; orig name: Unknown8018BFB4 */
void reset_gadget(struct ObjGadget *gdgt) {
    UNUSED u8 pad[8];
    f32 sp34;
    struct ObjValPtrs *vp;

    if (gdgt->unk3C - gdgt->unk38 == 0.0f) {
        fatal_printf("gadget has zero range (%f -> %f)\n", gdgt->unk38, gdgt->unk3C);
    }

    sp34 = (f32)(1.0 / (gdgt->unk3C - gdgt->unk38));

    if (gdgt->unk4C != NULL) {
        vp = (struct ObjValPtrs *) gdgt->unk4C->link1C->obj;

        switch (vp->datatype) {
            case OBJ_VALUE_FLOAT:
                get_objvalue(&gdgt->varval, OBJ_VALUE_FLOAT, vp->obj, vp->offset);
                gdgt->unk28 = (gdgt->varval.f - gdgt->unk38) * sp34;
                break;
            case OBJ_VALUE_INT:
                get_objvalue(&gdgt->varval, OBJ_VALUE_INT, vp->obj, vp->offset);
                gdgt->unk28 = (gdgt->varval.i - gdgt->unk38) * sp34;
                break;
            default:
                fatal_printf("%s: Undefined ValueType", "reset_gadget");
        }
    }
}

/* 23A940 -> 23A980 */
void reset_gadgets_in_grp(struct ObjGroup *grp) {
    apply_to_obj_types_in_group(OBJ_TYPE_GADGETS, (applyproc_t) reset_gadget, grp);
}
