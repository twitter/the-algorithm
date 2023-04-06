#include <ultra64.h>
#include <stdarg.h>
#include <stdio.h>
#include <macros.h>

#include "gd_types.h"
#include "gd_macros.h"

#include "gd_main.h"
#include "sfx.h"
#include "draw_objects.h"
#include "objects.h"
#include "particles.h"
#include "dynlist_proc.h"
#include "old_menu.h"
#include "debug_utils.h"
#include "joints.h"
#include "skin.h"
#include "gd_math.h"
#include "shape_helper.h"
#include "renderer.h"

// structs
struct Unk801B9E68 {
    /* 0x00 */ s32 count;
    /* 0x04 */ u8 pad[0x14];
}; /* sizeof() = 0x18 */

struct Unk8017F3CC {
    /*0x00*/ u8 pad[0x20];
    /*0x20*/ struct GdVec3f unk20;
};

// data
f32 D_801A81C0 = 0.0f;
f32 D_801A81C4 = 0.0f;

// bss
struct GdPlaneF D_801B9DA0;
struct ObjCamera *sCurrentMoveCamera; // @ 801B9DB8
struct ObjView *sCurrentMoveView;     // @ 801B9DBC
struct DebugCounters gGdCounter;      // @ 801B9DC0
Mat4f D_801B9DC8;
struct GdVec3f D_801B9E08;
struct ObjGroup *sCurrentMoveGrp; // @ 801B9E14
struct GdVec3f D_801B9E18;
struct GdVec3f D_801B9E28;
f32 D_801B9E34;
Mat4f *D_801B9E38;
struct ObjParticle *D_801B9E3C;
s32 D_801B9E40;
s32 D_801B9E44;
Mat4f *D_801B9E48;
struct ObjCamera *gGdCameraList; // @ 801B9E4C
void *D_801B9E50;
struct ObjGroup *gGdGroupList;  // @ 801B9E54
s32 gGdObjCount;                // @ 801B9E58
s32 gGdGroupCount;              // @ 801B9E5C
s32 gGdPlaneCount;              // @ 801B9E60
s32 gGdCameraCount;             // @ 801B9E64
struct Unk801B9E68 sGdViewInfo; // @ 801B9E68
void *D_801B9E80;
struct ObjJoint *gGdJointList;  // @ 801B9E84
struct ObjBone *gGdBoneList;    // @ 801B9E88
struct GdObj *gGdObjectList;    // @ 801B9E8C
struct ObjGroup *gGdViewsGroup; // @ 801B9E90

/* @ 22A480 for 0x70 */
void func_8017BCB0(void) { /* Initialize Plane? */
    D_801B9DA0.p0.x = 10000000.0f;
    D_801B9DA0.p0.y = 10000000.0f;
    D_801B9DA0.p0.z = 10000000.0f;

    D_801B9DA0.p1.x = -10000000.0f;
    D_801B9DA0.p1.y = -10000000.0f;
    D_801B9DA0.p1.z = -10000000.0f;
}

// TODO: fix first argument's type once type of set_cur_dynobj is known
// Should be an ObjVertex*
/* @ 22A4F0 for 0x140 */
void func_8017BD20(void *a0) {
    struct GdVec3f sp1c;

    set_cur_dynobj(a0);
    d_get_world_pos(&sp1c);

    if (sp1c.x < D_801B9DA0.p0.x) {
        D_801B9DA0.p0.x = sp1c.x;
    }

    if (sp1c.y < D_801B9DA0.p0.y) {
        D_801B9DA0.p0.y = sp1c.y;
    }

    if (sp1c.z < D_801B9DA0.p0.z) {
        D_801B9DA0.p0.z = sp1c.z;
    }

    if (sp1c.x > D_801B9DA0.p1.x) {
        D_801B9DA0.p1.x = sp1c.x;
    }

    if (sp1c.y > D_801B9DA0.p1.y) {
        D_801B9DA0.p1.y = sp1c.y;
    }

    if (sp1c.z > D_801B9DA0.p1.z) {
        D_801B9DA0.p1.z = sp1c.z;
    }
}

/* @ 22A630 for 0x70 */
void func_8017BE60(struct GdPlaneF *a0) {
    a0->p0.x = D_801B9DA0.p0.x;
    a0->p0.y = D_801B9DA0.p0.y;
    a0->p0.z = D_801B9DA0.p0.z;

    a0->p1.x = D_801B9DA0.p1.x;
    a0->p1.y = D_801B9DA0.p1.y;
    a0->p1.z = D_801B9DA0.p1.z;
}

/* @ 22A6A0 for 0x24 */
void func_8017BED0(UNUSED struct ObjGroup *a0, UNUSED struct GdObj *a1) {
    UNUSED u8 sp00[8];
    /* Debug stub? */
    return;
}

/* @ 22A6C4 for 0x2CC; orig. name: func_8017BEF4 */
const char *get_obj_name_str(enum ObjTypeFlag objFlag) {
    /* sp04 */ const char *objName;
    switch (objFlag) {
        case OBJ_TYPE_JOINTS:
            objName = "joints";
            break;
        case OBJ_TYPE_BONES:
            objName = "bones";
            break;
        case OBJ_TYPE_GROUPS:
            objName = "groups";
            break;
        case OBJ_TYPE_PARTICLES:
            objName = "particles";
            break;
        case OBJ_TYPE_SHAPES:
            objName = "shapes";
            break;
        case OBJ_TYPE_NETS:
            objName = "nets";
            break;
        case OBJ_TYPE_PLANES:
            objName = "planes";
            break;
        case OBJ_TYPE_VERTICES:
            objName = "vertices";
            break;
        case OBJ_TYPE_CAMERAS:
            objName = "cameras";
            break;
        case OBJ_TYPE_FACES:
            objName = "faces";
            break;
        case OBJ_TYPE_MATERIALS:
            objName = "materials";
            break;
        case OBJ_TYPE_LIGHTS:
            objName = "lights";
            break;
        case OBJ_TYPE_WEIGHTS:
            objName = "weights";
            break;
        case OBJ_TYPE_GADGETS:
            objName = "gadgets";
            break;
        case OBJ_TYPE_VIEWS:
            objName = "views";
            break;
        case OBJ_TYPE_LABELS:
            objName = "labels";
            break;
        case OBJ_TYPE_ANIMATORS:
            objName = "animators";
            break;
        case OBJ_TYPE_VALPTRS:
            objName = "valptrs";
            break;
        case OBJ_TYPE_ZONES:
            objName = "zones";
            break;
        default:
            objName = "unkown";
            break;
    }
    return objName;
}

/* @ 22A990 for 0x510 */
struct GdObj *make_object(enum ObjTypeFlag objType) {
    struct GdObj *newObj;
    struct GdObj *objListOldHead;
    s32 objSize;
    s32 i;
    drawmethod_t objDrawFn;
    const char *objNameStr;
    u8 *newObjBytes;
    s32 objPermanence = 0x10;

    add_to_stacktrace("make_object");
    switch (objType) {
        case OBJ_TYPE_JOINTS:
            objSize = sizeof(struct ObjJoint);
            objDrawFn = (drawmethod_t) draw_joint;
            break;
        case OBJ_TYPE_BONES:
            objSize = sizeof(struct ObjBone);
            objDrawFn = (drawmethod_t) draw_bone;
            break;
        case OBJ_TYPE_GROUPS:
            objSize = sizeof(struct ObjGroup);
            objDrawFn = (drawmethod_t) draw_group;
            break;
        case OBJ_TYPE_PARTICLES:
            objSize = sizeof(struct ObjParticle);
            objDrawFn = (drawmethod_t) draw_particle;
            break;
        case OBJ_TYPE_SHAPES:
            objSize = sizeof(struct ObjShape);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_UNK200000:
            objSize = sizeof(struct ObjUnk200000);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_NETS:
            objSize = sizeof(struct ObjNet);
            objDrawFn = (drawmethod_t) draw_net;
            break;
        case OBJ_TYPE_PLANES:
            objSize = sizeof(struct ObjPlane);
            objDrawFn = (drawmethod_t) draw_plane;
            break;
        case OBJ_TYPE_VERTICES:
            objSize = sizeof(struct ObjVertex);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_CAMERAS:
            objSize = sizeof(struct ObjCamera);
            objDrawFn = (drawmethod_t) draw_camera;
            break;
        case OBJ_TYPE_FACES:
            objSize = sizeof(struct ObjFace);
            objDrawFn = (drawmethod_t) draw_face;
            objPermanence = 1;
            break;
        case OBJ_TYPE_MATERIALS:
            objSize = sizeof(struct ObjMaterial);
            objDrawFn = (drawmethod_t) draw_material;
            break;
        case OBJ_TYPE_LIGHTS:
            objSize = sizeof(struct ObjLight);
            objDrawFn = (drawmethod_t) draw_light;
            break;
        case OBJ_TYPE_WEIGHTS:
            objSize = sizeof(struct ObjWeight);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_GADGETS:
            objSize = sizeof(struct ObjGadget);
            objDrawFn = (drawmethod_t) draw_gadget;
            break;
        case OBJ_TYPE_VIEWS:
            objSize = sizeof(struct ObjView);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_LABELS:
            objSize = sizeof(struct ObjLabel);
            objDrawFn = (drawmethod_t) draw_label;
            break;
        case OBJ_TYPE_ANIMATORS:
            objSize = sizeof(struct ObjAnimator);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_VALPTRS:
            objSize = sizeof(struct ObjValPtrs);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        case OBJ_TYPE_ZONES:
            objSize = sizeof(struct ObjZone);
            objDrawFn = (drawmethod_t) nop_obj_draw;
            break;
        default:
            fatal_print("make_object() : Unkown object!");
    }

    objNameStr = get_obj_name_str(objType);
    start_memtracker(objNameStr);

    newObj = gd_malloc(objSize, objPermanence);

    if (newObj == NULL) {
        fatal_printf("Cant allocate object '%s' memory!", objNameStr);
    }

    stop_memtracker(objNameStr);

    newObjBytes = (u8 *) newObj;
    for (i = 0; i < objSize; i++) {
        newObjBytes[i] = 0;
    }

    gGdObjCount++;
    objListOldHead = gGdObjectList;
    gGdObjectList = newObj;

    newObj->prev = NULL;
    if (objListOldHead != NULL) {
        newObj->next = objListOldHead;
        objListOldHead->prev = newObj;
    }
    newObj->number = gGdObjCount;
    newObj->type = objType;
    newObj->objDrawFn = objDrawFn;
    newObj->drawFlags = 0;

    imout();
    return newObj;
}

/* @ 22AEA0 for 0xD0; orig name: Unknown8017C6D0 */
struct ObjZone *make_zone(struct ObjGroup *a0, struct GdPlaneF *a1, struct ObjGroup *a2) {
    struct ObjZone *newZone = (struct ObjZone *) make_object(OBJ_TYPE_ZONES);

    newZone->unk14.p0.x = a1->p0.x;
    newZone->unk14.p0.y = a1->p0.y;
    newZone->unk14.p0.z = a1->p0.z;
    newZone->unk14.p1.x = a1->p1.x;
    newZone->unk14.p1.y = a1->p1.y;
    newZone->unk14.p1.z = a1->p1.z;
    // pointers? prev, next?
    newZone->unk2C = a2;
    newZone->unk30 = a0;

//! @bug Created `ObjZone` is not returned
#ifdef AVOID_UB
    return newZone;
#endif
}

/* @ 22AF70 for 0x60 */
struct ObjUnk200000 *Unknown8017C7A0(struct ObjVertex *a0, struct ObjFace *a1) {
    struct ObjUnk200000 *sp1C = (struct ObjUnk200000 *) make_object(OBJ_TYPE_UNK200000);

    sp1C->unk30 = a0;
    sp1C->unk34 = a1;

    return sp1C;
}

/* @ 22AFD0 for 0xC0; orig name: func_8017C800 */
struct Links *make_link_to_obj(struct Links *head, struct GdObj *a1) {
    struct Links *newLink;

    start_memtracker("links");

    newLink = gd_malloc_perm(sizeof(struct Links));

    if (newLink == NULL) {
        fatal_print("Cant allocate link memory!");
    }

    stop_memtracker("links");

    if (head != NULL) {
        head->next = newLink;
    }

    newLink->prev = head;
    newLink->next = NULL;
    newLink->obj = a1;

    return newLink;
}

/* @ 22B090 -> 22B154; orig name: func_8017C8C0 */
struct VtxLink *make_vtx_link(struct VtxLink *prevlink, Vtx *data) {
    struct VtxLink *newLink;

    newLink = gd_malloc_perm(sizeof(struct VtxLink));

    if (newLink == NULL) {
        fatal_print("Cant allocate link memory!");
    }

    if (prevlink != NULL) {
        prevlink->next = newLink;
    }

    newLink->prev = prevlink;
    newLink->next = NULL;
    newLink->data = data;
    // WTF?
    if (((uintptr_t)(newLink)) == 0x3F800000) {
        fatal_printf("bad3\n");
    }
    return newLink;
}

/* @ 22B154 for 0x88; orig name: func8017C984 */
struct ObjValPtrs *make_valptrs(struct GdObj *obj, s32 flags, enum ValPtrType type, size_t offset) {
    struct ObjValPtrs *sp1C = (struct ObjValPtrs *) make_object(OBJ_TYPE_VALPTRS);

    sp1C->obj = obj;
    sp1C->unk20 = flags;
    sp1C->offset = offset;
    sp1C->datatype = type;

    return sp1C;
}

/* @ 22B1DC for 0x430 */
void reset_plane(struct ObjPlane *plane) {
    struct ObjFace *sp4C;
    f32 sp48;
    f32 sp44;
    UNUSED u32 sp40;
    UNUSED u32 sp3C;
    UNUSED u32 sp38;
    s32 i;
    s32 sp30;
    register f32 sp28;

    add_to_stacktrace("reset_plane");

    sp4C = plane->unk40;
    calc_face_normal(sp4C);
    plane->unk1C = gd_dot_vec3f(&sp4C->vertices[0]->pos, &sp4C->normal);
    sp48 = 0.0f;

    sp28 = sp4C->normal.x < 0.0f ? -sp4C->normal.x : sp4C->normal.x;
    sp44 = sp28;
    if (sp44 > sp48) {
        sp30 = 0;
        sp48 = sp44;
    }

    sp28 = sp4C->normal.y < 0.0f ? -sp4C->normal.y : sp4C->normal.y;
    sp44 = sp28;
    if (sp44 > sp48) {
        sp30 = 1;
        sp48 = sp44;
    }

    sp28 = sp4C->normal.z < 0.0f ? -sp4C->normal.z : sp4C->normal.z;
    sp44 = sp28;
    if (sp44 > sp48) {
        sp30 = 2;
    }

    switch (sp30) {
        case 0:
            plane->unk20 = 1;
            plane->unk24 = 2;
            break;
        case 1:
            plane->unk20 = 0;
            plane->unk24 = 2;
            break;
        case 2:
            plane->unk20 = 0;
            plane->unk24 = 1;
            break;
    }

    func_8017BCB0();

    for (i = 0; i < sp4C->vtxCount; i++) {
        func_8017BD20(sp4C->vertices[i]);
    }

    plane->plane28.p0.x = D_801B9DA0.p0.x;
    plane->plane28.p0.y = D_801B9DA0.p0.y;
    plane->plane28.p0.z = D_801B9DA0.p0.z;
    plane->plane28.p1.x = D_801B9DA0.p1.x;
    plane->plane28.p1.y = D_801B9DA0.p1.y;
    plane->plane28.p1.z = D_801B9DA0.p1.z;

    if (plane->plane28.p1.x - plane->plane28.p0.x < 100.0f) {
        plane->plane28.p1.x += 50.0f;
        plane->plane28.p0.x -= 50.0f;
    }

    plane->plane28.p1.y += 200.0f;
    plane->plane28.p0.y -= 200.0f;

    if (plane->plane28.p1.z - plane->plane28.p0.z < 100.0f) {
        plane->plane28.p1.z += 50.0f;
        plane->plane28.p0.z -= 50.0f;
    }
    imout();
}

/* @ 22B60C for 0x94; orig name: func_8017CE3C */
struct ObjPlane *make_plane(s32 inZone, struct ObjFace *a1) {
    UNUSED u32 pad1C;
    struct ObjPlane *newPlane = (struct ObjPlane *) make_object(OBJ_TYPE_PLANES);

    gGdPlaneCount++;
    newPlane->id = gGdPlaneCount;
    newPlane->unk18 = inZone;
    newPlane->unk40 = a1;
    reset_plane(newPlane);

    return newPlane;
}

/* @ 22B6A0 for 0x21C; orig name: func_8017CED0 */
struct ObjCamera *make_camera(s32 a0, struct GdObj *a1) {
    struct ObjCamera *newCam;
    struct ObjCamera *oldCameraHead;

    newCam = (struct ObjCamera *) make_object(OBJ_TYPE_CAMERAS);

    gGdCameraCount++;
    newCam->id = gGdCameraCount;

    oldCameraHead = gGdCameraList;
    gGdCameraList = newCam;

    if (oldCameraHead != NULL) {
        newCam->next = oldCameraHead;
        oldCameraHead->prev = newCam;
    }

    newCam->unk2C = a0 | 0x10;
    newCam->unk30 = a1;
    gd_set_identity_mat4(&newCam->unk64);
    gd_set_identity_mat4(&newCam->unkA8);

    newCam->unk180.x = 1.0f;
    newCam->unk180.y = 0.1f;
    newCam->unk180.z = 1.0f;

    newCam->unk134.x = 4.0f;
    newCam->unk134.y = 4.0f;
    newCam->unk134.z = 4.0f;

    newCam->unk178 = 0.0f;
    newCam->unk17C = 0.25f;

    newCam->zoom = 0;
    newCam->zoomLevels = -1;

    newCam->unkA4 = 0.0f;

    newCam->unk34.x = newCam->unk34.y = newCam->unk34.z = 0.0f;
    newCam->unk14.x = newCam->unk14.y = newCam->unk14.z = 0.0f;

    return newCam;
}

/* @ 22B8BC for 0xA8; orig. name: func_8017D0EC */
struct ObjMaterial *make_material(UNUSED s32 a0, char *name, s32 id) {
    struct ObjMaterial *newMtl;

    newMtl = (struct ObjMaterial *) make_object(OBJ_TYPE_MATERIALS);

    if (name != NULL) {
        gd_strcpy(newMtl->name, name);
    } else {
        gd_strcpy(newMtl->name, "NoName");
    }

    newMtl->id = id;
    newMtl->gddlNumber = 0;
    newMtl->type = 16;

    return newMtl;
}

/* @ 22B964 for 0x114; orig name: func_8017D194 */
struct ObjLight *make_light(s32 flags, char *name, s32 id) {
    struct ObjLight *newLight;

    newLight = (struct ObjLight *) make_object(OBJ_TYPE_LIGHTS);

    if (name != NULL) {
        gd_strcpy(newLight->name, name);
    } else {
        gd_strcpy(newLight->name, "NoName");
    }

    newLight->id = id;
    newLight->unk30 = 1.0f;
    newLight->unk4C = 0;
    newLight->flags = flags | LIGHT_NEW_UNCOUNTED;
    newLight->unk98 = 0;
    newLight->unk40 = 0;

    newLight->unk68.x = newLight->unk68.y = newLight->unk68.z = 0;

    return newLight;
}

/* @ 22BA78 for 0x294; orig name: func_8017D2A8*/
struct ObjView *make_view(const char *name, s32 flags, s32 a2, s32 ulx, s32 uly, s32 lrx, s32 lry,
                          struct ObjGroup *parts) {
    struct ObjView *newView = (struct ObjView *) make_object(OBJ_TYPE_VIEWS);

    if (gGdViewsGroup == NULL) {
        gGdViewsGroup = make_group(0);
    }

    addto_group(gGdViewsGroup, &newView->header);

    newView->flags = flags | VIEW_UPDATE | VIEW_LIGHT;
    newView->id = sGdViewInfo.count++;

    if ((newView->components = parts) != NULL) {
        reset_nets_and_gadgets(parts);
    }

    newView->unk78 = 0;
    newView->unk38 = a2;

    newView->clipping.x = 30.0f;
    newView->clipping.y = 5000.0f;
    newView->clipping.z = 45.0f;

    newView->upperLeft.x = (f32) ulx;
    newView->upperLeft.y = (f32) uly;

    newView->lowerRight.x = (f32) lrx;
    newView->lowerRight.y = (f32) lry;

    newView->unk48 = 1.0f;
    newView->unk4C = 1.0f;

    newView->colour.r = newView->id * 0.1; //? 0.1f, unless the extra precision was wanted for the tenth
    newView->colour.g = 0.06f;
    newView->colour.b = 1.0f;

    newView->proc = NULL;
    newView->unk9C = 0;

    if (name != NULL) {
        newView->unk1C = setup_view_buffers(name, newView, ulx, uly, lrx, lry);
    }

    newView->namePtr = name;
    newView->lights = NULL;

    return newView;
}

/* @ 22BD0C for 0x78; orig name: func_8017D53C */
struct ObjAnimator *make_animator(void) {
    struct ObjAnimator *newAnim = (struct ObjAnimator *) make_object(OBJ_TYPE_ANIMATORS);
    newAnim->unk24 = 1.0f;
    newAnim->unk28 = 1.0f;

    newAnim->fn48 = NULL;
    newAnim->unk4C = 0;

    return newAnim;
}

/* @ 22BD84 for 0x78; orig name: func_8017D5B4 */
struct ObjWeight *make_weight(UNUSED s32 a0, s32 id, struct ObjVertex *vtx, f32 weight) {
    struct ObjWeight *newWeight = (struct ObjWeight *) make_object(OBJ_TYPE_WEIGHTS);

    newWeight->id = id;
    newWeight->unk38 = weight;
    newWeight->unk3C = vtx;

    return newWeight;
}

/* @ 22BDFC for 0xCC; orig name: func_8017D62C */
struct ObjGroup *make_group_of_type(enum ObjTypeFlag type, struct GdObj *fromObj, struct GdObj *toObj) {
    struct ObjGroup *newGroup;
    struct GdObj *curObj;

    newGroup = make_group(0);
    curObj = fromObj;

    while (curObj != NULL) {
        if (curObj->type & type) {
            addto_group(newGroup, curObj);
        }

        if (curObj == toObj) {
            break;
        }

        curObj = curObj->prev;
    }

    return newGroup;
}

/* @ 22BEC8 for 0x1CC; orig name: func_8017D6F8 */
void sprint_obj_id(char *str, struct GdObj *obj) {
    enum ObjTypeFlag type = obj->type;

    switch (type) {
        case OBJ_TYPE_BONES:
            sprintf(str, "b%d ", ((struct ObjBone *) obj)->id);
            break;
        case OBJ_TYPE_JOINTS:
            sprintf(str, "j%d ", ((struct ObjJoint *) obj)->id);
            break;
        case OBJ_TYPE_GROUPS:
            sprintf(str, "g%d ", ((struct ObjGroup *) obj)->id);
            break;
        case OBJ_TYPE_PARTICLES:
            sprintf(str, "p%d ", ((struct ObjParticle *) obj)->id);
            break;
        case OBJ_TYPE_NETS:
            sprintf(str, "net(no id) ");
            break;
        case OBJ_TYPE_CAMERAS:
            sprintf(str, "c%d ", ((struct ObjCamera *) obj)->id);
            break;
        case OBJ_TYPE_VERTICES:
            sprintf(str, "v(no id) ");
            break;
        case OBJ_TYPE_PLANES:
            sprintf(str, "pl%d ", ((struct ObjPlane *) obj)->id);
            break;
        default:
            sprintf(str, "?%x ", type);
            break;
    }
}

/* @ 22C094 for 0x210 */
struct ObjGroup *make_group(s32 count, ...) {
    va_list args;
    s32 i;
    UNUSED u32 sp5C;
    struct GdObj *curObj;
    UNUSED u32 sp54;
    UNUSED u32 sp50;
    UNUSED u32 sp4C;
    struct ObjGroup *newGroup;
    struct ObjGroup *oldGroupListHead;
    struct GdObj *vargObj;
    char idStrBuf[0x20];
    struct Links *curLink;

    newGroup = (struct ObjGroup *) make_object(OBJ_TYPE_GROUPS);
    newGroup->id = ++gGdGroupCount;
    newGroup->objCount = 0;
    newGroup->link1C = newGroup->link20 = NULL;

    printf("Made group no.%d\n", newGroup->id);

    oldGroupListHead = gGdGroupList;
    gGdGroupList = newGroup;
    if (oldGroupListHead != NULL) {
        newGroup->next = oldGroupListHead;
        oldGroupListHead->prev = newGroup;
    }

    if (count == 0) {
        return newGroup;
    }

    va_start(args, count);
    curLink = NULL;

    for (i = 0; i < count; i++) {
        // get the next pointer in the struct.
        vargObj = va_arg(args, struct GdObj *);

        if (vargObj == NULL) { // one of our pointers was NULL. raise an error.
            fatal_printf("make_group() NULL group ptr");
        }

        curObj = vargObj;
        newGroup->groupObjTypes |= curObj->type;
        addto_group(newGroup, vargObj);
    }
    va_end(args);

    curLink = newGroup->link1C;
    printf("Made group no.%d from: ", newGroup->id);
    while (curLink != NULL) {
        curObj = curLink->obj;
        sprint_obj_id(idStrBuf, curObj);
        printf("%s", idStrBuf);
        printf("\n");
        curLink = curLink->next;
    }

    return newGroup;
}

/* @ 22C2A4 for 0xEC */
void addto_group(struct ObjGroup *group, struct GdObj *obj) {
    char strbuf[0x20];
    UNUSED u8 pad[0x8];

    add_to_stacktrace("addto_group");

    if (group->link1C == NULL) {
        group->link1C = make_link_to_obj(NULL, obj);
        group->link20 = group->link1C;
    } else {
        group->link20 = make_link_to_obj(group->link20, obj);
    }

    group->groupObjTypes |= obj->type;
    group->objCount++;

    printf("Added ");
    sprint_obj_id(strbuf, obj);
    printf("%s", strbuf);
    printf(" to ");
    sprint_obj_id(strbuf, &group->header);
    printf("%s", strbuf);
    printf("\n");
    imout();
}

/* @ 22C390 for 0xFC; orig name: func_8017DBC0 */
void addto_groupfirst(struct ObjGroup *group, struct GdObj *obj) {
    struct Links *newLink;

    add_to_stacktrace("addto_groupfirst");

    if (group->link1C == NULL) {
        group->link1C = make_link_to_obj(NULL, obj);
        group->link20 = group->link1C;
    } else {
        newLink = make_link_to_obj(NULL, obj);
        group->link1C->prev = newLink;
        newLink->next = group->link1C;
        group->link1C = newLink;
    }

    group->groupObjTypes |= obj->type;
    group->objCount++;
    imout();
}

/* @ 22C48C for 0x84; orig name: func_8017DCBC */
s32 group_contains_obj(struct ObjGroup *group, struct GdObj *obj) {
    struct Links *objLink = group->link1C;

    while (objLink != NULL) {
        if (objLink->obj->number == obj->number) {
            return 1;
        }

        objLink = objLink->next;
    }

    return 0;
}

/* @ 22C510 for 0x4A8 */
/* this shows details about all objects in the main object linked list */
void show_details(enum ObjTypeFlag type) {
    enum ObjTypeFlag curObjType;
    struct Links *curGroupLink;
    struct ObjGroup *curSubGroup;
    struct GdObj *curObj;
    char idStrBuf[0x24];
    s32 curGroupTypes;

    gd_printf("\nDetails about: ");
    switch (type) {
        case OBJ_TYPE_GROUPS:
            gd_printf("Groups\n");
            break;
        case OBJ_TYPE_BONES:
            gd_printf("Bones\n");
            break;
        case OBJ_TYPE_JOINTS:
            gd_printf("Joints\n");
            break;
        case OBJ_TYPE_PARTICLES:
            gd_printf("Particles\n");
            break;
        default:
            gd_printf("Everything?\n");
            break;
    }

    curObj = gGdObjectList;
    while (curObj != NULL) {
        curObjType = curObj->type;
        if (curObjType == type) {
            sprint_obj_id(idStrBuf, curObj);
            switch (curObjType) {
                case OBJ_TYPE_GROUPS:
                    gd_printf("Group %s: ", idStrBuf);
                    curGroupTypes = ((struct ObjGroup *) curObj)->groupObjTypes;

                    if (curGroupTypes & OBJ_TYPE_GROUPS) {
                        gd_printf("groups ");
                    }
                    if (curGroupTypes & OBJ_TYPE_BONES) {
                        gd_printf("bones ");
                    }
                    if (curGroupTypes & OBJ_TYPE_JOINTS) {
                        gd_printf("joints ");
                    }
                    if (curGroupTypes & OBJ_TYPE_PARTICLES) {
                        gd_printf("particles ");
                    }
                    if (curGroupTypes & OBJ_TYPE_CAMERAS) {
                        gd_printf("cameras ");
                    }
                    if (curGroupTypes & OBJ_TYPE_NETS) {
                        gd_printf("nets ");
                    }
                    if (curGroupTypes & OBJ_TYPE_GADGETS) {
                        gd_printf("gadgets ");
                    }
                    if (curGroupTypes & OBJ_TYPE_LABELS) {
                        gd_printf("labels ");
                    }
                    if (curGroupTypes & OBJ_TYPE_FACES) {
                        gd_printf("face ");
                    }
                    if (curGroupTypes & OBJ_TYPE_VERTICES) {
                        gd_printf("vertex ");
                    }

                    curGroupLink = ((struct ObjGroup *) curObj)->link1C;
                    while (curGroupLink != NULL) {
                        sprint_obj_id(idStrBuf, curGroupLink->obj);
                        gd_printf("%s", idStrBuf);
                        curGroupLink = curGroupLink->next;
                    }
                    gd_printf("\n");
                    break;
                case OBJ_TYPE_BONES:
                    gd_printf("Bone %s: ", idStrBuf);
                    curSubGroup = ((struct ObjBone *) curObj)->unk10C;
                    curGroupLink = curSubGroup->link1C;
                    while (curGroupLink != NULL) {
                        sprint_obj_id(idStrBuf, curGroupLink->obj);
                        gd_printf("%s", idStrBuf);
                        curGroupLink = curGroupLink->next;
                    }
                    gd_printf("\n");
                    break;
                case OBJ_TYPE_JOINTS:
                    gd_printf("Joint %s: ", idStrBuf);
                    curSubGroup = ((struct ObjJoint *) curObj)->unk1C4;
                    curGroupLink = curSubGroup->link1C;
                    while (curGroupLink != NULL) {
                        sprint_obj_id(idStrBuf, curGroupLink->obj);
                        gd_printf("%s", idStrBuf);
                        curGroupLink = curGroupLink->next;
                    }
                    gd_printf("\n");
                    break;
                default:;
            }
        }
        curObj = curObj->next;
    }
}
/* @ 22C9B8 for 0x24 */
s32 Unknown8017E1E8(void) {
    s32 sp4 = 0;
    return sp4;
}
/* @ 22C9DC for 0x24 */
s32 func_8017E20C(void) {
    s32 sp4 = 0;
    return sp4;
}

/* @ 22CA00 for 0x88 */
void gd_loadtexture(struct GdObj *obj) {
    struct GdObj *localObjPtr = obj;

    switch (obj->type) {
        case OBJ_TYPE_JOINTS:
            func_80191604((struct ObjJoint *) localObjPtr);
            break;
        case OBJ_TYPE_NETS:
            reset_net((struct ObjNet *) localObjPtr);
            break;
        default:;
    }
}

/* @ 22CA88 for 0x38 */
void func_8017E2B8(void) {
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) gd_loadtexture, sCurrentMoveGrp);
}

/* @ 22CAC0 for 0xF0 */
struct GdObj *UnknownRecursive8017E2F0(struct GdObj *obj, enum ObjTypeFlag type) {
    UNUSED u32 sp2C;
    enum ObjTypeFlag curObjType;
    struct Links *curGroupLink;

    curObjType = obj->type;

    switch (curObjType) {
        case OBJ_TYPE_GROUPS:
            curGroupLink = ((struct ObjGroup *) obj)->link1C;
            while (curGroupLink != NULL) {
                UnknownRecursive8017E2F0(curGroupLink->obj, type);
                curGroupLink = curGroupLink->next;
            }
            break;
        case OBJ_TYPE_BONES:
            break;
        default:;
    }

    if (curObjType == type) {
        return obj;
    }

//! @bug Nothing is returned if a GdObj of `type` is not found
#ifdef AVOID_UB
    return NULL;
#endif
}

/* @ 22CBB0 for 0x1A4; orig name: func8017E3E0 */
s32 apply_to_obj_types_in_group(s32 types, applyproc_t fn, struct ObjGroup *group) {
    struct Links *curLink;
    struct Links *nextLink;
    struct GdObj *linkedObj;
    enum ObjTypeFlag linkedObjType;
    applyproc_t objFn;
    UNUSED u8 pad2C[0x20];
    s32 fnAppliedCount;

    fnAppliedCount = 0;

    //! @bug When `group` pointer is NULL, garbage is returned, not the
    //!      count of `fn` calls
    if (group == NULL) {
#ifdef AVOID_UB
        return fnAppliedCount;
#else
        return;
#endif
    }

    if (group->linkType & 1) { // compressed data, not an Obj
        return fnAppliedCount;
    }

    if (!((group->groupObjTypes & OBJ_TYPE_GROUPS) | (group->groupObjTypes & types))) {
        return fnAppliedCount;
    }

    objFn = fn;
    curLink = group->link1C;

    while (curLink != NULL) {
        linkedObj = curLink->obj;
        linkedObjType = linkedObj->type;
        nextLink = curLink->next;

        if (linkedObjType == OBJ_TYPE_GROUPS) {
            fnAppliedCount += apply_to_obj_types_in_group(types, fn, (struct ObjGroup *) linkedObj);
        }

        if (linkedObjType & types) {
            (*objFn)(linkedObj);
            fnAppliedCount++;
        }

        curLink = nextLink;
    }
    return fnAppliedCount;
}

/* @ 22CD54 for 0x2B4 */
void func_8017E584(struct ObjNet *a0, struct GdVec3f *a1, struct GdVec3f *a2) {
    struct GdVec3f sp94;
    struct GdVec3f sp88;
    struct GdVec3f sp7C;
    struct GdVec3f sp70;
    UNUSED u8 pad30[0x40]; // unused MyMatrix4x4? f32[4][4]
    f32 sp2C;
    UNUSED u32 sp28;
    struct GdVec3f sp1C;

    sp70.x = a2->x;
    sp70.y = a2->y;
    sp70.z = a2->z;

    gd_normalize_vec3f(&sp70);

    sp7C.x = a1->x;
    sp7C.y = a1->y;
    sp7C.z = a1->z;

    sp1C.x = a0->unkB0.x;
    sp1C.y = a0->unkB0.y;
    sp1C.z = a0->unkB0.z;

    gd_rotate_and_translate_vec3f(&sp1C, &a0->mat128);

    sp7C.x -= sp1C.x;
    sp7C.y -= sp1C.y;
    sp7C.z -= sp1C.z;

    if (gd_normalize_vec3f(&sp7C) == FALSE) {
        sp7C.x = -sp70.x;
        sp7C.y = -sp70.y;
        sp7C.z = -sp70.z;
    }

    gd_cross_vec3f(&sp70, a1, &sp94);
    sp2C = (f32) gd_sqrt_d((sp94.x * sp94.x) + (sp94.z * sp94.z));

    if (sp2C > 1000.0) { //? 1000.0f
        sp2C = 1000.0f;
    }

    sp2C /= 1000.0;    //? 1000.0f
    sp2C = 1.0 - sp2C; //? 1.0f - sp2C

    sp88.x = a2->x * sp2C;
    sp88.y = a2->y * sp2C;
    sp88.z = a2->z * sp2C;

    a0->unk74.x += sp88.x;
    a0->unk74.y += sp88.y;
    a0->unk74.z += sp88.z;
}

/* @ 22D008 for 0x1B4 */
void func_8017E838(struct ObjNet *a0, struct GdVec3f *a1, struct GdVec3f *a2) {
    UNUSED u32 sp84;
    UNUSED u32 sp80;
    UNUSED u32 sp7C;
    struct GdVec3f sp70;
    struct GdVec3f sp64;
    UNUSED u8 pad24[0x40];
    struct GdVec3f sp18;

    sp64.x = a1->x;
    sp64.y = a1->y;
    sp64.z = a1->z;

    sp18.x = a0->unkB0.x;
    sp18.y = a0->unkB0.y;
    sp18.z = a0->unkB0.z;

    gd_rotate_and_translate_vec3f(&sp18, &a0->mat128);

    sp64.x -= sp18.x;
    sp64.y -= sp18.y;
    sp64.z -= sp18.z;

    sp64.x *= 0.01; //? 0.01f;
    sp64.y *= 0.01; //? 0.01f;
    sp64.z *= 0.01; //? 0.01f;

    gd_cross_vec3f(a2, &sp64, &sp70);
    gd_clamp_vec3f(&sp70, 5.0f);

    a0->unk80.x += sp70.x;
    a0->unk80.y += sp70.y;
    a0->unk80.z += sp70.z;
}

/* @ 22D1BC for 0xA8 */
void func_8017E9EC(struct ObjNet *a0) {
    struct GdVec3f sp5C;
    Mat4f sp1C;
    f32 sp18;

    sp5C.x = a0->unkA4.x;
    sp5C.y = a0->unkA4.y;
    sp5C.z = a0->unkA4.z;

    gd_normalize_vec3f(&sp5C);
    sp18 = gd_vec3f_magnitude(&a0->unkA4);
    gd_create_rot_mat_angular(&sp1C, &sp5C, -sp18);
    gd_mult_mat4f(&D_801B9DC8, &sp1C, &D_801B9DC8);
}

/* @ 22D264 for 0x90 */
s32 Unknown8017EA94(struct GdVec3f *vec, Mat4f matrix) {
    if (vec->x >= matrix[2][2] && vec->x <= matrix[3][1] && vec->z >= matrix[3][0]
        && vec->z <= matrix[3][3]) {
        return 1;
    }

    return 0;
}

/* @ 22D2F4 for 0x1DC */
s32 Unknown8017EB24(struct GdObj *a0, struct GdObj *a1) {
    struct GdVec3f sp44;
    struct GdVec3f sp38;
    struct GdPlaneF *sp34;
    struct GdPlaneF *sp30;
    struct GdPlaneF sp18;

    set_cur_dynobj(a0);
    d_get_world_pos(&sp44);
    sp34 = d_get_plane();

    set_cur_dynobj(a1);
    d_get_world_pos(&sp38);
    sp30 = d_get_plane();

    sp18.p0.x = sp34->p0.x + sp30->p0.x;
    sp18.p0.y = sp34->p0.y + sp30->p0.y;
    sp18.p0.z = sp34->p0.z + sp30->p0.z;
    sp18.p1.x = sp34->p1.x + sp30->p1.x;
    sp18.p1.y = sp34->p1.y + sp30->p1.y;
    sp18.p1.z = sp34->p1.z + sp30->p1.z;

    D_801B9E08.x = sp38.x - sp44.x;
    D_801B9E08.y = sp38.y - sp44.y;
    D_801B9E08.z = sp38.z - sp44.z;

    if (D_801B9E08.x >= sp18.p0.x) {
        if (D_801B9E08.x <= sp18.p1.x) {
            if (D_801B9E08.z >= sp18.p0.z) {
                if (D_801B9E08.z <= sp18.p1.z) {
                    return 1;
                }
            }
        }
    }

    return 0;
}

/* @ 22D4D0 for 0xCC */
s32 Unknown8017ED00(struct GdObj *a0, struct GdPlaneF *a1) {
    struct GdVec3f sp1C;

    set_cur_dynobj(a0);
    d_get_world_pos(&sp1C);

    if (sp1C.x >= a1->p0.x) {
        if (sp1C.x <= a1->p1.x) {
            if (sp1C.z >= a1->p0.z) {
                if (sp1C.z <= a1->p1.z) {
                    return 1;
                }
            }
        }
    }

    return 0;
}

/* @ 22D59C for 0x90 */
s32 Unknown8017EDCC(struct GdVec3f *a0, struct GdPlaneF *a1) {
    if (a0->x >= a1->p0.x) {
        if (a0->x <= a1->p1.x) {
            if (a0->z >= a1->p0.z) {
                if (a0->z <= a1->p1.z) {
                    return 1;
                }
            }
        }
    }

    return 0;
}

/* @ 22D62C for 0x1F8; orig name: Unknown8017EE5C */
s32 gd_plane_point_within(struct GdPlaneF *a0, struct GdPlaneF *a1) {
    if (a0->p0.x >= a1->p0.x) {
        if (a0->p0.x <= a1->p1.x) {
            if (a0->p0.z >= a1->p0.z) {
                if (a0->p0.z <= a1->p1.z) {
                    return TRUE;
                }
            }
        }
    }

    if (a0->p1.x >= a1->p0.x) {
        if (a0->p1.x <= a1->p1.x) {
            if (a0->p0.z >= a1->p0.z) {
                if (a0->p0.z <= a1->p1.z) {
                    return TRUE;
                }
            }
        }
    }

    if (a0->p1.x >= a1->p0.x) {
        if (a0->p1.x <= a1->p1.x) {
            if (a0->p1.z >= a1->p0.z) {
                if (a0->p1.z <= a1->p1.z) {
                    return TRUE;
                }
            }
        }
    }

    if (a0->p0.x >= a1->p0.x) {
        if (a0->p0.x <= a1->p1.x) {
            if (a0->p1.z >= a1->p0.z) {
                if (a0->p1.z <= a1->p1.z) {
                    return TRUE;
                }
            }
        }
    }

    return FALSE;
}

/* @ 22D824 for 0x1BC */
s32 func_8017F054(struct GdObj *a0, struct GdObj *a1) {
    struct Links *curLink;
    struct ObjGroup *curGroup;
    UNUSED u32 sp54;
    Mat4f *sp50;
    Mat4f *sp4C;
    Mat4f *sp48;
    Mat4f *sp44;
    Mat4f *sp40;
    UNUSED u8 pad20[0x18];
    struct GdVec3f sp1C;

    if (a1 != NULL) {
        set_cur_dynobj(a1);
        sp50 = d_get_matrix_ptr();
        sp44 = (Mat4f *) d_get_rot_mtx_ptr();

        set_cur_dynobj(a0);
        sp4C = d_get_idn_mtx_ptr();
        sp40 = (Mat4f *) d_get_rot_mtx_ptr();

        d_get_scale(&sp1C);
        sp48 = d_get_matrix_ptr();

        gd_mult_mat4f(sp4C, sp50, sp48);
        gd_mult_mat4f(sp4C, sp44, sp40);
        gd_scale_mat4f_by_vec3f(sp40, &sp1C);
    } else {
        set_cur_dynobj(a0);
        sp48 = d_get_matrix_ptr();
        sp4C = d_get_idn_mtx_ptr();
        sp44 = (Mat4f *) d_get_rot_mtx_ptr();

        d_get_scale(&sp1C);
        gd_set_identity_mat4(sp48);
        gd_copy_mat4f(sp4C, sp44);
        gd_scale_mat4f_by_vec3f(sp44, &sp1C);
    }

    set_cur_dynobj(a0);
    curGroup = d_get_att_objgroup();

    if (curGroup != NULL) {
        curLink = curGroup->link1C;
        while (curLink != NULL) {
            func_8017F054(curLink->obj, a0);
            curLink = curLink->next;
        }
    }
    return 0;
}

/* @ 22D9E0 for 0x1BC */
s32 UnknownRecursive8017F210(struct GdObj *a0, struct GdObj *a1) {
    struct Links *sp6C;
    struct ObjGroup *sp68;
    UNUSED u32 sp64;
    UNUSED Mat4f *sp60;
    Mat4f *sp5C;
    UNUSED Mat4f *sp58;
    Mat4f *sp54;
    Mat4f *sp50;
    UNUSED u8 pad38[0x18];
    struct GdVec3f sp2C;
    s32 count = 0;

    count++;

    if (a1 != NULL) {
        set_cur_dynobj(a1);
        sp60 = d_get_matrix_ptr();
        sp54 = (Mat4f *) d_get_rot_mtx_ptr();

        set_cur_dynobj(a0);
        sp5C = d_get_idn_mtx_ptr();
        sp50 = (Mat4f *) d_get_rot_mtx_ptr();

        d_get_scale(&sp2C);
        gd_mult_mat4f(sp5C, sp54, sp50);
        gd_scale_mat4f_by_vec3f(sp50, &sp2C);
    } else {
        set_cur_dynobj(a0);
        sp58 = d_get_matrix_ptr();
        sp5C = d_get_idn_mtx_ptr();
        sp54 = (Mat4f *) d_get_rot_mtx_ptr();

        d_get_scale(&sp2C);
        gd_copy_mat4f(sp5C, sp54);
        gd_scale_mat4f_by_vec3f(sp54, &sp2C);
    }

    set_cur_dynobj(a0);
    sp68 = d_get_att_objgroup();

    if (sp68 != NULL) {
        sp6C = sp68->link1C;
        while (sp6C != NULL) {
            count += UnknownRecursive8017F210(sp6C->obj, a0);
            sp6C = sp6C->next;
        }
    }
    return count;
}

/* @ 22DB9C for 0x38; a0 might be ObjUnk200000* */
void Unknown8017F3CC(struct Unk8017F3CC *a0) {
    gd_rotate_and_translate_vec3f(&a0->unk20, D_801B9E48);
}

/* @ 22DBD4 for 0x20 */
void func_8017F404(UNUSED f32 a0, UNUSED struct GdObj *a1, UNUSED struct GdObj *a2) {
    UNUSED u8 pad[0x30];
}

/* @ 22DBF4 for 0x1A0 */
void func_8017F424(struct GdTriangleF *a0, struct GdTriangleF *a1, f32 a2) {
    Mat4f sp40;
    struct GdTriangleF sp1C;

    gd_set_identity_mat4(&sp40);

    if (a2 != 0.0f) {
        sp1C.p1.x = a0->p1.x + (a1->p1.x - a0->p1.x) * a2;
        sp1C.p1.y = a0->p1.y + (a1->p1.y - a0->p1.y) * a2;
        sp1C.p1.z = a0->p1.z + (a1->p1.z - a0->p1.z) * a2;
        sp1C.p2.x = a0->p2.x + (a1->p2.x - a0->p2.x) * a2;
        sp1C.p2.y = a0->p2.y + (a1->p2.y - a0->p2.y) * a2;
        sp1C.p2.z = a0->p2.z + (a1->p2.z - a0->p2.z) * a2;

        gd_scale_mat4f_by_vec3f(&sp40, &a0->p0);
        gd_rot_mat_about_vec(&sp40, &sp1C.p1);
        gd_add_vec3f_to_mat4f_offset(&sp40, &sp1C.p2);
    } else { // L8017F568
        d_set_scale(a0->p0.x, a0->p0.y, a0->p0.z);
        gd_rot_mat_about_vec(&sp40, &a0->p1);
        gd_add_vec3f_to_mat4f_offset(&sp40, &a0->p2);
    } // L8017F5A4
    d_set_idn_mtx(&sp40);
}

/* @ 22DD94 for 0x1060; orig name: Unknown8017F5C4 */
void move_animator(struct ObjAnimator *animObj) {
    struct AnimDataInfo *animData; // array?
    Mat4f *mtxArr;
    Mat4f localMtx;
    struct GdTriangleF *triPtr; // Used as GdTriangleF[] or GdTriangleF*
    struct GdTriangleF tri1;
    struct GdTriangleF tri2;
    s16(*triHArr)[9];              // GdTriangleH[]?
    s16(*vec3hArr)[3];             // MyVec3h[]?
    s16(*planeHArr)[6];            // GdPlaneH[]?
    s16(*camPlaneHArr)[6];         // camera GdPlaneH[]?
    struct GdObj *stubObj1 = NULL; // used only for call to stubbed function
    struct GdObj *stubObj2 = NULL; // used only for call to stubbed function
    UNUSED s32 sp50;
    UNUSED s32 sp4C;
    UNUSED s32 sp48;
    UNUSED struct GdVec3f unusedVec;
    s32 sp38;
    s32 sp34;
    f32 sp30;
    f32 scale = 0.1f;
    struct AnimMtxVec *sp28;
    register struct Links *link;
    struct GdObj *linkedObj;

    if (animObj->fn48 != NULL) {
        (*animObj->fn48)(animObj);
    }

    if (animObj->unk14 == NULL) {
        return;
    }

    animData = (struct AnimDataInfo *) animObj->animdata->link1C->obj;

    if (animObj->unk44 != NULL) {
        animObj->unk28 = ((struct ObjAnimator *) animObj->unk44)->unk28
                         / ((struct ObjAnimator *) animObj->unk44)->unk24;
        animData +=
            ((struct ObjAnimator *) animObj->unk44)->unk20; //...why offset this pointer like this...
    }

    if (animData->type == 0) {
        return;
    }

    unusedVec.x = 4.0f;
    unusedVec.y = 1.0f;
    unusedVec.z = 1.0f;

    if (animObj->unk28 > (f32) animData->count) {
        animObj->unk28 = 1.0f;
    } else if (animObj->unk28 < 0.0f) {
        animObj->unk28 = (f32) animData->count;
    }

    sp38 = animObj->unk28;
    sp30 = animObj->unk28 - (f32) sp38;
    sp34 = sp38 + 1;

    if (sp34 > animData->count) {
        sp34 = 1;
    }

    sp38--;
    sp34--;
    link = animObj->unk14->link1C;
    while (link != NULL) {
        linkedObj = link->obj;
        set_cur_dynobj(linkedObj);
        switch (animData->type) {
            case GD_ANIM_MATRIX: // data = Mat4f* (f32[4][4])
                mtxArr = (Mat4f *) animData->data;
                /* This needs be be un-dereferenced pointer addition to make the registers match */
                d_set_idn_mtx(mtxArr + (s32) animObj->unk28);
                break;
            case GD_ANIM_3H_SCALED: // data = s16(*)[3] or MyVec3h[]...
                vec3hArr = (s16(*)[3]) animData->data;

                d_get_scale(&tri1.p0);
                tri2.p0.x = tri1.p0.x;
                tri2.p0.y = tri1.p0.y;
                tri2.p0.z = tri1.p0.z;

                d_get_init_pos(&tri1.p2);
                tri2.p2.x = tri1.p2.x;
                tri2.p2.y = tri1.p2.y;
                tri2.p2.z = tri1.p2.z;

                tri1.p1.x = (f32) vec3hArr[sp38][0] * scale;
                tri1.p1.y = (f32) vec3hArr[sp38][1] * scale;
                tri1.p1.z = (f32) vec3hArr[sp38][2] * scale;

                tri2.p1.x = (f32) vec3hArr[sp34][0] * scale;
                tri2.p1.y = (f32) vec3hArr[sp34][1] * scale;
                tri2.p1.z = (f32) vec3hArr[sp34][2] * scale;

                func_8017F424(&tri1, &tri2, sp30);
                break;
            case GD_ANIM_3H: // data = s16(*)[3] or MyVec3h[]...
                vec3hArr = (s16(*)[3]) animData->data;

                d_get_scale(&tri1.p0);
                tri2.p0.x = tri1.p0.x;
                tri2.p0.y = tri1.p0.y;
                tri2.p0.z = tri1.p0.z;

                d_get_init_rot(&tri1.p1);
                tri2.p1.x = tri1.p1.x;
                tri2.p1.y = tri1.p1.y;
                tri2.p1.z = tri1.p1.z;

                tri1.p2.x = (f32) vec3hArr[sp38][0];
                tri1.p2.y = (f32) vec3hArr[sp38][1];
                tri1.p2.z = (f32) vec3hArr[sp38][2];

                tri2.p2.x = (f32) vec3hArr[sp34][0];
                tri2.p2.y = (f32) vec3hArr[sp34][1];
                tri2.p2.z = (f32) vec3hArr[sp34][2];

                func_8017F424(&tri1, &tri2, sp30);
                break;
            case GD_ANIM_6H_SCALED: // data = s16(*)[6] or GdPlaneH[]...
                planeHArr = (s16(*)[6]) animData->data;

                d_get_scale(&tri1.p0);
                tri2.p0.x = tri1.p0.x;
                tri2.p0.y = tri1.p0.y;
                tri2.p0.z = tri1.p0.z;

                tri1.p1.x = (f32) planeHArr[sp38][0] * scale;
                tri1.p1.y = (f32) planeHArr[sp38][1] * scale;
                tri1.p1.z = (f32) planeHArr[sp38][2] * scale;

                tri2.p1.x = (f32) planeHArr[sp34][0] * scale;
                tri2.p1.y = (f32) planeHArr[sp34][1] * scale;
                tri2.p1.z = (f32) planeHArr[sp34][2] * scale;

                tri1.p2.x = (f32) planeHArr[sp38][3];
                tri1.p2.y = (f32) planeHArr[sp38][4];
                tri1.p2.z = (f32) planeHArr[sp38][5];

                tri2.p2.x = (f32) planeHArr[sp34][3];
                tri2.p2.y = (f32) planeHArr[sp34][4];
                tri2.p2.z = (f32) planeHArr[sp34][5];

                func_8017F424(&tri1, &tri2, sp30);
                break;
            case GD_ANIM_9H: // data = s16(*)[9] or GdTriangleFH[]...
                triHArr = (s16(*)[9]) animData->data;

                tri1.p0.x = (f32) triHArr[sp38][0] * scale;
                tri1.p0.y = (f32) triHArr[sp38][1] * scale;
                tri1.p0.z = (f32) triHArr[sp38][2] * scale;

                tri1.p1.x = (f32) triHArr[sp38][3] * scale;
                tri1.p1.y = (f32) triHArr[sp38][4] * scale;
                tri1.p1.z = (f32) triHArr[sp38][5] * scale;

                tri1.p2.x = (f32) triHArr[sp38][6];
                tri1.p2.y = (f32) triHArr[sp38][7];
                tri1.p2.z = (f32) triHArr[sp38][8];

                tri2.p0.x = (f32) triHArr[sp34][0] * scale;
                tri2.p0.y = (f32) triHArr[sp34][1] * scale;
                tri2.p0.z = (f32) triHArr[sp34][2] * scale;

                tri2.p1.x = (f32) triHArr[sp34][3] * scale;
                tri2.p1.y = (f32) triHArr[sp34][4] * scale;
                tri2.p1.z = (f32) triHArr[sp34][5] * scale;

                tri2.p2.x = (f32) triHArr[sp34][6];
                tri2.p2.y = (f32) triHArr[sp34][7];
                tri2.p2.z = (f32) triHArr[sp34][8];

                func_8017F424(&tri1, &tri2, sp30);
                break;
            case GD_ANIM_CAMERA: // s16(*)[6]?
                if (linkedObj->type == OBJ_TYPE_CAMERAS) {
                    camPlaneHArr = animData->data;

                    tri1.p2.x = (f32) camPlaneHArr[sp38][0];
                    tri1.p2.y = (f32) camPlaneHArr[sp38][1];
                    tri1.p2.z = (f32) camPlaneHArr[sp38][2];

                    tri2.p2.x = (f32) camPlaneHArr[sp38][3];
                    tri2.p2.y = (f32) camPlaneHArr[sp38][4];
                    tri2.p2.z = (f32) camPlaneHArr[sp38][5];

                    ((struct ObjCamera *) linkedObj)->unk14.x = tri1.p2.x;
                    ((struct ObjCamera *) linkedObj)->unk14.y = tri1.p2.y;
                    ((struct ObjCamera *) linkedObj)->unk14.z = tri1.p2.z;

                    ((struct ObjCamera *) linkedObj)->unk34.x = tri2.p2.x;
                    ((struct ObjCamera *) linkedObj)->unk34.y = tri2.p2.y;
                    ((struct ObjCamera *) linkedObj)->unk34.z = tri2.p2.z;
                }
                break;
            case GD_ANIM_TRI_F_2: // GdTriangleF[]
                triPtr = (struct GdTriangleF *) animData->data;
                func_8017F424(&triPtr[sp38], &triPtr[sp34], sp30);
                break;
            case GD_ANIM_MTX_VEC: // AnimMtxVec[]
                sp28 = &((struct AnimMtxVec *) animData->data)[sp38];
                d_set_idn_mtx(&sp28->matrix);
                d_set_scale(sp28->vec.x, sp28->vec.y, sp28->vec.z);
                break;
            case GD_ANIM_TRI_F_4: // GdTriangleF*
                triPtr = (struct GdTriangleF *) animData->data;
                gd_set_identity_mat4(&localMtx);
                gd_scale_mat4f_by_vec3f(&localMtx, &triPtr->p0);
                gd_rot_mat_about_vec(&localMtx, &triPtr->p1);
                gd_add_vec3f_to_mat4f_offset(&localMtx, &triPtr->p2);
                d_set_idn_mtx(&localMtx);
                break;
            case GD_ANIM_STUB:
                if (stubObj1 == NULL) {
                    stubObj1 = linkedObj;
                } else {
                    if (stubObj2 == NULL) {
                        stubObj2 = linkedObj;
                        func_8017F404(animObj->unk28, stubObj1, stubObj2);
                    } else {
                        fatal_printf("Too many objects to morph");
                    }
                }
                break;
            default:
                fatal_printf("move_animator(): Unkown animation data type");
        }
        link = link->next;
    }
}

/* @ 22EDF4 for 0x300; orig name: Unknown80180624 */
void drag_picked_object(struct GdObj *inputObj) {
    UNUSED u32 spE4;
    UNUSED u32 spE0;
    UNUSED u32 spDC;
    struct GdVec3f spD0;
    struct GdVec3f spC4;
    struct GdControl *ctrl;
    Mat4f sp80;
    Mat4f sp40;
    UNUSED u32 pad34[3];
    struct GdObj *obj;
    UNUSED u32 pad2C;
    f32 sp28;

    ctrl = &gGdCtrl;

    if (gViewUpdateCamera == NULL) {
        return;
    }

    sp28 = gd_vec3f_magnitude(&gViewUpdateCamera->unk40);
    sp28 /= 1000.0f;

    spD0.x = ((f32)(ctrl->csrX - ctrl->csrXatApress)) * sp28;
    spD0.y = ((f32) - (ctrl->csrY - ctrl->csrYatApress)) * sp28;
    spD0.z = 0.0f;

    gd_inverse_mat4f(&gViewUpdateCamera->unkE8, &sp40);
    gd_mat4f_mult_vec3f(&spD0, &sp40);

    obj = inputObj;
    if ((inputObj->drawFlags & OBJ_PICKED) && gGdCtrl.btnApressed) {
        gd_play_sfx(GD_SFX_PINCH_FACE);
        // Note: this second sfx won't play, as it is "overwritten" by the first
        if (ABS(ctrl->stickDeltaX) + ABS(ctrl->stickDeltaY) >= 11) {
            gd_play_sfx(GD_SFX_PINCH_FACE_2);
        }

        switch (inputObj->type) {
            case OBJ_TYPE_JOINTS:
                ((struct ObjJoint *) obj)->mat128[3][0] += spD0.x;
                ((struct ObjJoint *) obj)->mat128[3][1] += spD0.y;
                ((struct ObjJoint *) obj)->mat128[3][2] += spD0.z;
                break;
            case OBJ_TYPE_GADGETS:
                break;
            case OBJ_TYPE_NETS:
                gd_inverse_mat4f(&((struct ObjNet *) obj)->mat128, &sp80);
                spC4.x = spD0.x;
                spC4.y = spD0.y;
                spC4.z = spD0.z;

                gd_mat4f_mult_vec3f(&spC4, &sp80);
                ((struct ObjNet *) obj)->matE8[3][0] += spD0.x;
                ((struct ObjNet *) obj)->matE8[3][1] += spD0.y;
                ((struct ObjNet *) obj)->matE8[3][2] += spD0.z;
                break;
            case OBJ_TYPE_PARTICLES:
                break;
            default:;
        }
    }
}

/* @ 22F0F4 for 0x50; orig name: func_80180924*/
void move_animators(struct ObjGroup *group) {
    restart_timer("move_animators");
    apply_to_obj_types_in_group(OBJ_TYPE_ANIMATORS, (applyproc_t) move_animator, group);
    split_timer("move_animators");
}

/* @ 22F144 for 0x3C; orig name: func_80180974 */
void find_and_drag_picked_object(struct ObjGroup *group) {
    apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) drag_picked_object, group);
}

/* @ 22F180 for 0x624; orig name: Unknown801809B0 */
void move_camera(struct ObjCamera *cam) {
    struct GdObj *spEC;
    struct GdVec3f spE0;
    struct GdVec3f spD4;
    struct GdVec3f spC8;
    UNUSED u8 padBC[0xC8 - 0xBC];
    struct GdVec3f spB0;
    Mat4f sp70;
    UNUSED u8 pad30[0x70 - 0x30];
    Mat4f *sp2C;
    struct GdControl *ctrl; // 28

    ctrl = &gGdCtrl;
    if ((cam->unk2C & 0x10) == 0) {
        return;
    }

    spE0.x = spE0.y = spE0.z = 0.0f;
    spB0.x = spB0.y = spB0.z = 0.0f;

    if ((spEC = cam->unk30) != NULL) {
        set_cur_dynobj(spEC);
        d_get_world_pos(&spE0);
        d_get_matrix(&sp70);

        spC8.x = sp70[2][0] - cam->unk58;
        spC8.z = sp70[2][2] - cam->unk60;

        cam->unk58 += spC8.x * cam->unk180.y;
        cam->unk60 += spC8.z * cam->unk180.y;

        cam->unkA8[2][0] = cam->unk58;
        cam->unkA8[2][1] = 0.0f;
        cam->unkA8[2][2] = cam->unk60;

        cam->unkA8[0][0] = cam->unkA8[2][2];
        cam->unkA8[0][1] = 0.0f;
        cam->unkA8[0][2] = -cam->unkA8[2][0];

        cam->unkA8[1][0] = 0.0f;
        cam->unkA8[1][1] = 1.0f;
        cam->unkA8[1][2] = 0.0f;

        gd_set_identity_mat4(&cam->unkA8);
    } else {
        gd_set_identity_mat4(&cam->unkA8);
    }

    sp2C = &cam->unk64;
    if ((cam->unk2C & 0x4) != 0) { // new B press
        if (ctrl->btnB != FALSE && ctrl->prevFrame->btnB == FALSE) {
            cam->zoom++;
            if (cam->zoom > cam->zoomLevels) {
                cam->zoom = 0;
            }

            switch (cam->zoom) {
                case 0:
                    gd_play_sfx(GD_SFX_CAM_ZOOM_IN);
                    break;
                case 1:
                case 2:
                    gd_play_sfx(GD_SFX_CAM_ZOOM_OUT);
                    break;
            }
        }

        if (ctrl->cleft) {
            cam->unk128.y += cam->unk134.y;
        }

        if (ctrl->cright) {
            cam->unk128.y -= cam->unk134.y;
        }

        if (ctrl->cup) {
            cam->unk128.x += cam->unk134.x;
        }

        if (ctrl->cdown) {
            cam->unk128.x -= cam->unk134.x;
        }

        cam->unk128.x = gd_clamp_f32(cam->unk128.x, 80.0f);

        cam->unk4C.x = cam->positions[cam->zoom].x;
        cam->unk4C.y = cam->positions[cam->zoom].y;
        cam->unk4C.z = cam->positions[cam->zoom].z;

        gd_rot_2d_vec(cam->unk128.x, &cam->unk4C.y, &cam->unk4C.z);
        gd_rot_2d_vec(-cam->unk128.y, &cam->unk4C.x, &cam->unk4C.z);

        cam->unk40.x += (cam->unk4C.x - cam->unk40.x) * cam->unk17C;
        cam->unk40.y += (cam->unk4C.y - cam->unk40.y) * cam->unk17C;
        cam->unk40.z += (cam->unk4C.z - cam->unk40.z) * cam->unk17C;
    } else {
        gd_set_identity_mat4(sp2C);
    }

    spD4.x = cam->unk40.x;
    spD4.y = cam->unk40.y;
    spD4.z = cam->unk40.z;

    spD4.x += spB0.x;
    spD4.y += spB0.y;
    spD4.z += spB0.z;

    gd_mult_mat4f(sp2C, &cam->unkA8, &cam->unkA8);
    gd_mat4f_mult_vec3f(&spD4, &cam->unkA8);

    cam->unk14.x = spD4.x;
    cam->unk14.y = spD4.y;
    cam->unk14.z = spD4.z;

    cam->unk14.x += spE0.x;
    cam->unk14.y += spE0.y;
    cam->unk14.z += spE0.z;
}

/* @ 22F7A4 for 0x38; orig name: func_80180FD4 */
void move_cameras_in_grp(struct ObjGroup *group) {
    apply_to_obj_types_in_group(OBJ_TYPE_CAMERAS, (applyproc_t) move_camera, group);
}

/* @ 22F7DC for 0x36C*/
void Unknown8018100C(struct ObjLight *light) {
    Mat4f mtx;
    UNUSED u32 pad1C[3];

    if (light->unk40 == 3) {
        if (light->unk30 > 0.0) { //? 0.0f
            light->unk30 -= 0.2;  //? 0.2f
        }

        if (light->unk30 < 0.0f) {
            light->unk30 = 0.0f;
        }

        if ((light->unk3C & 0x1) != 0) {
            light->unk30 = 1.0f;
        }

        light->unk3C &= ~1;
    }
    // if (1)?
    return;
    // unreachable
    light->position.x += light->unk80.x;
    light->position.y += light->unk80.y;
    light->position.z += light->unk80.z;

    // should be position.x for second comparison?
    if (light->position.x > 500.0f || light->position.y < -500.0f) {
        light->unk80.x = -light->unk80.x;
    }

    if (light->position.y > 500.0f || light->position.y < -500.0f) {
        light->unk80.y = -light->unk80.y;
    }

    if (light->position.z > 500.0f || light->position.z < -500.0f) {
        light->unk80.z = -light->unk80.z;
    }

    return;
    // more unreachable
    D_801A81C0 += 1.0; //? 1.0f
    D_801A81C4 += 0.6; //? 0.6f

    gd_set_identity_mat4(&mtx);
    gd_absrot_mat4(&mtx, GD_Y_AXIS, light->unk68.y);
    gd_absrot_mat4(&mtx, GD_X_AXIS, light->unk68.x);
    gd_absrot_mat4(&mtx, GD_Z_AXIS, light->unk68.z);
    gd_mat4f_mult_vec3f(&light->unk8C, &mtx);

    light->position.x = light->unk8C.x;
    light->position.y = light->unk8C.y;
    light->position.z = light->unk8C.z;
    return;
    // even more unreachable
    gd_mat4f_mult_vec3f(&light->unk80, &mtx);
    imout(); // this call would cause an issue if it was reachable
}

/* @ 22FB48 for 0x38; orig name: func_80181378 */
void move_lights_in_grp(struct ObjGroup *group) {
    apply_to_obj_types_in_group(OBJ_TYPE_LIGHTS, (applyproc_t) Unknown8018100C, group);
}

/* @ 22FB80 for 0xAC; orig name: func_801813B0 */
void move_group_members(void) {
    s32 i;

    if (gGdMoveScene != 0) {
        reset_gadgets_in_grp(sCurrentMoveGrp);
        move_lights_in_grp(sCurrentMoveGrp);
        move_particles_in_grp(sCurrentMoveGrp);
        move_animators(sCurrentMoveGrp);

        for (i = 0; i <= 0; i++) {
            move_nets(sCurrentMoveGrp);
        }

        move_cameras_in_grp(sCurrentMoveGrp);
    }
}

/* @ 22FC2C for 0x98; orig name: func_8018145C */
void proc_view_movement(struct ObjView *view) {
    add_to_stacktrace("movement");
    sCurrentMoveCamera = view->activeCam;
    sCurrentMoveView = view;
    if ((sCurrentMoveGrp = view->components) != NULL) {
        move_group_members();
    }
    if ((sCurrentMoveGrp = view->lights) != NULL) {
        move_group_members();
    }
    imout();
}

/* @ 22FCC4 for 0x44; orig name: func_801814F4 */
void reset_nets_and_gadgets(struct ObjGroup *group) {
    func_80193848(group);
    apply_to_obj_types_in_group(OBJ_TYPE_GADGETS, (applyproc_t) reset_gadget, group);
}

/* @ 22FD08 for 0x9C; orig name: func_80181538*/
void null_obj_lists(void) {
    D_801B9E44 = 0;
    gGdObjCount = 0;
    gGdGroupCount = 0;
    gGdPlaneCount = 0;
    gGdCameraCount = 0;
    sGdViewInfo.count = 0;

    gGdCameraList = NULL;
    D_801B9E50 = NULL;
    gGdBoneList = NULL;
    gGdJointList = NULL;
    gGdGroupList = NULL;
    D_801B9E80 = NULL;
    gGdObjectList = NULL;
    gGdViewsGroup = NULL;

    reset_net_count();
    reset_joint_counts();
}
