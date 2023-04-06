#include <ultra64.h>
#include <macros.h>

#include "gd_types.h"
#include "shape_helper.h"
#include "gd_main.h"
#include "draw_objects.h"
#include "objects.h"
#include "particles.h"
#include "dynlist_proc.h"
#include "debug_utils.h"
#include "joints.h"
#include "skin.h"
#include "gd_math.h"
#include "renderer.h"

#include "dynlists/dynlists.h"
#include "dynlists/dynlist_macros.h"

#ifndef VERSION_EU
#include <prevent_bss_reordering.h>
#endif

// types
struct UnkData {
    struct GdTriangleF tri;
    s32 a, b;
    struct UnkData *self;
};

// data
struct ObjGroup *gMarioFaceGrp = NULL;     // @ 801A82E0; returned by load_dynlist
struct ObjShape *D_801A82E4 = NULL;        // Shape used for drawing lights?
static struct ObjShape *D_801A82E8 = NULL; // returned by load_dynlist
struct ObjShape *gShapeRedSpark = NULL;    // @ 801A82EC
struct ObjShape *gShapeSilverSpark = NULL;    // @ 801A82F0
struct ObjShape *gShapeRedStar = NULL;     // @ 801A82F4
struct ObjShape *gShapeSilverStar = NULL;  // @ 801A82F8
static struct UnkData sUnref801A82FC = { { {
                                               1.0,
                                               1.0,
                                               1.0,
                                           },
                                           { 0.0, 0.0, 0.0 },
                                           { 0.0, 0.0, 0.0 } },
                                         1,
                                         4,
                                         &sUnref801A82FC };
static struct UnkData sUnref801A832C = { { {
                                               1.0,
                                               1.0,
                                               1.0,
                                           },
                                           { 0.0, 0.0, 0.0 },
                                           { 0.0, 0.0, 0.0 } },
                                         1,
                                         4,
                                         &sUnref801A832C };
static struct UnkData sUnref801A835C = { { {
                                               1.0,
                                               1.0,
                                               1.0,
                                           },
                                           { 0.0, 0.0, 0.0 },
                                           { 0.0, 0.0, 0.0 } },
                                         1,
                                         4,
                                         &sUnref801A835C };
static s32 sUnref801A838C[6] = { 0 };
struct ObjShape *D_801A83A4 = NULL;
static s32 sUnref801A83A8[31] = { 0 };
static struct DynList sSimpleDylist[8] = {
    StartList(),
    StartGroup("simpleg"),
    MakeDynObj(D_NET, "simple"),
    SetType(3),
    SetShapePtrPtr(&D_801A83A4),
    EndGroup("simpleg"),
    UseObj("simpleg"),
    StopList(),
};
static struct DynList sDynlist801A84E4[3] = {
    StartList(),
    SetFlag(0x1800),
    StopList(),
};
static struct DynList sDynlist801A85B3[5] = {
    StartList(), JumpToList(sDynlist801A84E4), SetFlag(0x400), SetFriction(0.04, 0.01, 0.01),
    StopList(),
};
static struct DynList sDynlist801A85A4[4] = {
    StartList(),
    JumpToList(sDynlist801A84E4),
    SetFriction(0.04, 0.01, 0.01),
    StopList(),
};
static struct DynList sDynlist801A8604[4] = {
    StartList(),
    JumpToList(sDynlist801A84E4),
    SetFriction(0.005, 0.005, 0.005),
    StopList(),
};
static f64 D_801A8668 = 0.0;

// bss
static u8 sUnrefSpaceB00[0x2C];           // @ 801BAB00
static struct ObjGroup *sCubeShapeGroup;  // @ 801BAB2C
static u8 sUnrefSpaceB30[0xC];            // @ 801BAB30
static struct ObjShape *sCubeShape;       // @ 801BAB3C
static u8 sUnrefSpaceB40[0x8];            // @ 801BAB40
static char sGdLineBuf[0x100];            // @ 801BAB48
static s32 sGdLineBufCsr;                 // @ 801BAC48
static struct GdFile *sGdShapeFile;       // @ 801BAC4C
static struct ObjShape *sGdShapeListHead; // @ 801BAC50
static u32 sGdShapeCount;                 // @ 801BAC54
static u8 sUnrefSpaceC58[0x8];            // @ 801BAC58
static struct GdVec3f D_801BAC60;
static u32 sUnrefSpaceC6C; // @ 801BAC6C
static u32 sUnrefSpaceC70; // @ 801BAC70
static struct ObjPlane *D_801BAC74;
static struct ObjPlane *D_801BAC78; // sShapeNetHead?
static u8 sUnrefSpaceC80[0x1C];     // @ 801BAC80
static struct ObjFace *D_801BAC9C;
static struct ObjFace *D_801BACA0;
static u8 sUnrefSpaceCA8[0x10]; // @ 801BACA8
/// factor for scaling vertices in an `ObjShape` when calling `scale_verts_in_shape()`
static struct GdVec3f sVertexScaleFactor;
/// factor for translating vertices in an `ObjShape` when calling `translate_verts_in_shape()`
static struct GdVec3f sVertexTranslateOffset;
static u8 sUnrefSpaceCD8[0x30];     // @ 801BACD8
static struct ObjGroup *D_801BAD08; // group of planes from make_netfromshape
static u8 sUnrefSpaceD10[0x20];     // @ 801BAD10
static struct GdVec3f D_801BAD30;   // printed with "c="
static u8 sUnrefSpaceD40[0x120];    // @ 801BAD40

// Forward Declarations
struct ObjMaterial *find_or_add_new_mtl(struct ObjGroup *, s32, f32, f32, f32);

/* @ 245A50 for 0x40 */
/* Something to do with shape list/group initialization? */
void func_80197280(void) {
    sGdShapeCount = 0;
    sGdShapeListHead = NULL;
    gGdLightGroup = make_group(0);
}

/* @ 245A90 for 0x24C; orig name: func_801972C0 */
void calc_face_normal(struct ObjFace *face) {
    UNUSED u32 pad5C;
    struct GdVec3f sp50;
    struct GdVec3f sp44;
    struct GdVec3f sp38;
    struct GdVec3f sp2C; // Normal?
    struct ObjVertex *sp28;
    struct ObjVertex *sp24;
    struct ObjVertex *sp20;
    UNUSED u32 pad1C;
    f32 sp18;

    sp18 = 1000.0f;
    add_to_stacktrace("calc_facenormal");

    if (face->vtxCount > 2) {
        sp28 = face->vertices[0];
        sp50.x = sp28->pos.x; // Obj->Vec3f at 0x20
        sp50.y = sp28->pos.y;
        sp50.z = sp28->pos.z;

        sp24 = face->vertices[1];
        sp44.x = sp24->pos.x;
        sp44.y = sp24->pos.y;
        sp44.z = sp24->pos.z;

        sp20 = face->vertices[2];
        sp38.x = sp20->pos.x;
        sp38.y = sp20->pos.y;
        sp38.z = sp20->pos.z;

        // Calculate normal cross product ?
        sp2C.x =
            (((sp44.y - sp50.y) * (sp38.z - sp44.z)) - ((sp44.z - sp50.z) * (sp38.y - sp44.y))) * sp18;
        // 245C04
        sp2C.y =
            (((sp44.z - sp50.z) * (sp38.x - sp44.x)) - ((sp44.x - sp50.x) * (sp38.z - sp44.z))) * sp18;
        // 245C44
        sp2C.z =
            (((sp44.x - sp50.x) * (sp38.y - sp44.y)) - ((sp44.y - sp50.y) * (sp38.x - sp44.x))) * sp18;
        // 245C84
        gd_normalize_vec3f(&sp2C);
        face->normal.x = sp2C.x;
        face->normal.y = sp2C.y;
        face->normal.z = sp2C.z;
    }
    imout();
}

/* @ 245CDC for 0x118 */
struct ObjVertex *gd_make_vertex(f32 x, f32 y, f32 z) {
    struct ObjVertex *vtx;

    vtx = (struct ObjVertex *) make_object(OBJ_TYPE_VERTICES);
    vtx->id = 0xD1D4;

    vtx->pos.x = x;
    vtx->pos.y = y;
    vtx->pos.z = z;

    vtx->initPos.x = x;
    vtx->initPos.y = y;
    vtx->initPos.z = z;

    vtx->scaleFactor = 1.0f;
    vtx->gbiVerts = NULL;
    vtx->alpha = 1.0f;

    vtx->normal.x = 0.0f;
    vtx->normal.y = 1.0f;
    vtx->normal.z = 0.0f;

    return vtx;
}

/* @ 245DF4 for 0xAC */
struct ObjFace *make_face_with_colour(f32 r, f32 g, f32 b) {
    struct ObjFace *newFace;

    add_to_stacktrace("make_face");
    newFace = (struct ObjFace *) make_object(OBJ_TYPE_FACES);

    newFace->colour.r = r;
    newFace->colour.g = g;
    newFace->colour.b = b;

    newFace->vtxCount = 0;
    newFace->mtlId = -1;
    newFace->mtl = NULL;

    imout();
    return newFace;
}

/* @ 245EA0 for 0x6C */
struct ObjFace *make_face_with_material(struct ObjMaterial *mtl) {
    struct ObjFace *newFace;

    newFace = (struct ObjFace *) make_object(OBJ_TYPE_FACES);

    newFace->vtxCount = 0;
    newFace->mtlId = mtl->id;
    newFace->mtl = mtl;

    return newFace;
}

/* @ 245F0C for 0x88 */
void Unknown8019773C(struct ObjFace *face, struct ObjVertex *vtx1, struct ObjVertex *vtx2,
                     struct ObjVertex *vtx3, struct ObjVertex *vtx4) {
    face->vertices[0] = vtx1;
    face->vertices[1] = vtx2;
    face->vertices[2] = vtx3;
    face->vertices[3] = vtx4;
    face->vtxCount = 4;
    calc_face_normal(face);
}

/* @ 245F94 for 0x78; orig name: func_801977C4 */
void add_3_vtx_to_face(struct ObjFace *face, struct ObjVertex *vtx1, struct ObjVertex *vtx2,
                       struct ObjVertex *vtx3) {
    face->vertices[0] = vtx1;
    face->vertices[1] = vtx2;
    face->vertices[2] = vtx3;
    face->vtxCount = 3;
    calc_face_normal(face);
}

/* @ 24600C for 0x198 */
struct ObjShape *make_shape(s32 flag, const char *name) {
    struct ObjShape *newShape;
    struct ObjShape *curShapeHead;
    UNUSED u32 pad;

    newShape = (struct ObjShape *) make_object(OBJ_TYPE_SHAPES);

    if (name != NULL) {
        gd_strcpy(newShape->name, name);
    } else {
        gd_strcpy(newShape->name, "NoName");
    }

    sGdShapeCount++;

    curShapeHead = sGdShapeListHead;
    sGdShapeListHead = newShape;

    if (curShapeHead != NULL) {
        newShape->nextShape = curShapeHead;
        curShapeHead->prevShape = newShape;
    }

    newShape->id = sGdShapeCount;
    newShape->flag = flag;

    newShape->vtxCount = 0;
    newShape->faceCount = 0;
    newShape->gdDls[0] = 0;
    newShape->gdDls[1] = 0;
    newShape->unk3C = 0;
    newShape->faceGroup = NULL; /* whoops, NULL-ed twice */

    newShape->unk58 = 1.0f;

    newShape->vtxGroup = NULL;
    newShape->faceGroup = NULL;
    newShape->mtlGroup = NULL;
    newShape->unk30 = 0;
    newShape->gdDls[2] = 0;

    return newShape;
}

/* @ 2461A4 for 0x30; orig name: func_801979D4 */
void clear_buf_to_cr(void) {
    sGdLineBufCsr = 0;
    sGdLineBuf[sGdLineBufCsr] = '\r';
}

/* @ 2461D4 for 0x2c; orig name: func_80197A04 */
s8 get_current_buf_char(void) {
    return sGdLineBuf[sGdLineBufCsr];
}

/* @ 246200 for 0x64; orig name: func_80197A30 */
s8 get_and_advance_buf(void) {
    if (get_current_buf_char() == '\0') {
        return '\0';
    }

    return sGdLineBuf[sGdLineBufCsr++];
}

/* @ 246264 for 0x80; orig name: func_80197A94 */
s8 load_next_line_into_buf(void) {
    sGdLineBufCsr = 0;

    if (gd_feof(sGdShapeFile) != 0) {
        sGdLineBuf[sGdLineBufCsr] = '\0';
    } else {
        gd_fread_line(sGdLineBuf, 0xFF, sGdShapeFile);
    }

    return get_current_buf_char();
}

/* @ 2462E4 for 0x38; orig name: func_80197B14 */
s32 is_line_end(char c) {
    return c == '\r' || c == '\n';
}

/* @ 24631C for 0x38; orig name: func_80197B4C */
s32 is_white_space(char c) {
    return c == ' ' || c == '\t';
}

/* @ 246354 for 0xEC; orig name: func_80197B84 */
/* Advances buffer cursor to next non-white-space character, if possible.
 * Returns TRUE if a character is found, or FALSE if EOF or \0 */
s32 scan_to_next_non_whitespace(void) {
    char curChar;

    for (curChar = get_current_buf_char(); curChar != '\0'; curChar = get_current_buf_char()) {
        if (is_white_space(curChar)) {
            get_and_advance_buf();
            continue;
        }

        if (curChar == '\x1a') { //'SUB' character: "soft EOF" in older systems
            return FALSE;
            continue; // unreachable
        }

        if (is_line_end(curChar)) {
            if (load_next_line_into_buf() == '\0') {
                return FALSE;
            }
        } else {
            break;
        }
    }

    return !!curChar;
}

/* @ 246440 for 0xE0; orig name: func_80197C70 */
s32 is_next_buf_word(char *a0) {
    char curChar;
    char wordBuf[0xfc];
    u32 bufLength;

    bufLength = 0;
    for (curChar = get_and_advance_buf(); curChar != '\0'; curChar = get_and_advance_buf()) {
        if (is_white_space(curChar) || is_line_end(curChar)) {
            break;
            continue; // unreachable + nonsensical
        }
        wordBuf[bufLength] = curChar;
        bufLength++;
    }

    wordBuf[bufLength] = '\0';

    return !gd_str_not_equal(a0, wordBuf);
}

/* @ 246520 for 0x198; orig name: func_80197D50 */
s32 getfloat(f32 *floatPtr) {
    char charBuf[0x100];
    u32 bufCsr;
    char curChar;
    u32 sp34;
    f64 parsedDouble;

    add_to_stacktrace("getfloat");

    if (is_line_end(get_current_buf_char())) {
        fatal_printf("getfloat(): Unexpected EOL");
    }

    while (is_white_space(get_current_buf_char())) {
        get_and_advance_buf();
    }

    bufCsr = 0;

    for (curChar = get_and_advance_buf(); curChar != '\0'; curChar = get_and_advance_buf()) {
        if (!is_white_space(curChar) && !is_line_end(curChar)) {
            charBuf[bufCsr] = curChar;
            bufCsr++;
        } else {
            break;
        }
    }

    charBuf[bufCsr] = '\0';

    parsedDouble = gd_lazy_atof(charBuf, &sp34);
    *floatPtr = (f32) parsedDouble;

    imout();
    return !!bufCsr;
}

/* @ 2466B8 for 0x180; orig name: func_80197EE8 */
s32 getint(s32 *intPtr) {
    char charBuf[0x100];
    u32 bufCsr;
    char curChar;

    add_to_stacktrace("getint");

    if (is_line_end(get_current_buf_char())) {
        fatal_printf("getint(): Unexpected EOL");
    }

    while (is_white_space(get_current_buf_char())) {
        get_and_advance_buf();
    }

    bufCsr = 0;
    for (curChar = get_and_advance_buf(); curChar != '\0'; curChar = get_and_advance_buf()) {
        if (is_white_space(curChar) || is_line_end(curChar)) {
            break;
        }

        charBuf[bufCsr] = curChar;
        bufCsr++;
    }

    charBuf[bufCsr] = '\0';
    *intPtr = gd_atoi(charBuf);

    imout();
    return !!bufCsr;
}

/* @ 246838 for 0x14 */
void Unknown80198068(UNUSED f32 a0) {
    printf("max=%f\n", a0);
}

/* @ 24684C for 0x6C */
void func_8019807C(struct ObjVertex *vtx) {
    gd_rot_2d_vec(D_801BAC60.x, &vtx->pos.y, &vtx->pos.z);
    gd_rot_2d_vec(D_801BAC60.y, &vtx->pos.x, &vtx->pos.z);
    gd_rot_2d_vec(D_801BAC60.z, &vtx->pos.x, &vtx->pos.y);
}

/* @ 2468B8 for 0x6C */
void func_801980E8(f32 *a0) {
    gd_rot_2d_vec(D_801BAC60.x, &a0[1], &a0[2]);
    gd_rot_2d_vec(D_801BAC60.y, &a0[0], &a0[2]);
    gd_rot_2d_vec(D_801BAC60.z, &a0[0], &a0[1]);
}

/* @ 246924 for 0x30 */
void Unknown80198154(f32 x, f32 y, f32 z) {
    D_801BAC60.x = x;
    D_801BAC60.y = y;
    D_801BAC60.z = z;
}

/* @ 246954 for 0x6c */
void Unknown80198184(struct ObjShape *shape, f32 x, f32 y, f32 z) {
    UNUSED struct GdVec3f unusedVec;
    unusedVec.x = x;
    unusedVec.y = y;
    unusedVec.z = z;

    apply_to_obj_types_in_group(OBJ_TYPE_VERTICES, (applyproc_t) func_8019807C, shape->vtxGroup);
}

/* @ 2469C0 for 0xc8 */
void scale_obj_position(struct GdObj *obj) {
    struct GdVec3f pos;

    if (obj->type == OBJ_TYPE_GROUPS) {
        return;
    }

    set_cur_dynobj(obj);
    d_get_rel_pos(&pos);

    pos.x *= sVertexScaleFactor.x;
    pos.y *= sVertexScaleFactor.y;
    pos.z *= sVertexScaleFactor.z;

    d_set_rel_pos(pos.x, pos.y, pos.z);
    d_set_init_pos(pos.x, pos.y, pos.z);
}

/* @ 246A88 for 0x94 */
void translate_obj_position(struct GdObj *obj) {
    struct GdVec3f pos;

    set_cur_dynobj(obj);
    d_get_rel_pos(&pos);

    pos.x += sVertexTranslateOffset.x;
    pos.y += sVertexTranslateOffset.y;
    pos.z += sVertexTranslateOffset.z;

    d_set_rel_pos(pos.x, pos.y, pos.z);
}

/* @ 246B1C for 0x88 */
void scale_verts_in_shape(struct ObjShape *shape, f32 x, f32 y, f32 z) {
    sVertexScaleFactor.x = x;
    sVertexScaleFactor.y = y;
    sVertexScaleFactor.z = z;

    if (shape->vtxGroup != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) scale_obj_position, shape->vtxGroup);
    }
}

/* @ 246BA4 for 0x70; not called */
// Guessing on the type of a0
void translate_verts_in_shape(struct ObjShape *shape, f32 x, f32 y, f32 z) {
    sVertexTranslateOffset.x = x;
    sVertexTranslateOffset.y = y;
    sVertexTranslateOffset.z = z;

    apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) translate_obj_position, shape->vtxGroup);
}

/* @ 246C14 for 0xe0 */
void Unknown80198444(struct ObjVertex *vtx) {
    f64 distance;

    func_8017BD20(vtx);

    distance = vtx->pos.x * vtx->pos.x + vtx->pos.y * vtx->pos.y + vtx->pos.z * vtx->pos.z;

    if (distance != 0.0) {
        distance = gd_sqrt_d(distance); // sqrtd?

        if (distance > D_801A8668) {
            D_801A8668 = distance;
        }
    }
}

/* @ 246CF4 for 0xc4 */
void Unknown80198524(struct ObjVertex *vtx) {
    vtx->pos.x -= D_801BAD30.x;
    vtx->pos.y -= D_801BAD30.y;
    vtx->pos.z -= D_801BAD30.z;

    vtx->pos.x /= D_801A8668;
    vtx->pos.y /= D_801A8668;
    vtx->pos.z /= D_801A8668;
}

/* @ 246DB8 for 0x11c */
void Unknown801985E8(struct ObjShape *shape) {
    struct GdPlaneF sp18;

    D_801A8668 = 0.0;
    func_8017BCB0();
    apply_to_obj_types_in_group(OBJ_TYPE_VERTICES, (applyproc_t) Unknown80198444, shape->vtxGroup);

    func_8017BE60(&sp18);

    D_801BAD30.x = (f32)((sp18.p0.x + sp18.p1.x) / 2.0); //? 2.0f
    D_801BAD30.y = (f32)((sp18.p0.y + sp18.p1.y) / 2.0); //? 2.0f
    D_801BAD30.z = (f32)((sp18.p0.z + sp18.p1.z) / 2.0); //? 2.0f

    gd_print_vec("c=", &D_801BAD30);

    apply_to_obj_types_in_group(OBJ_TYPE_VERTICES, (applyproc_t) Unknown80198524, shape->vtxGroup);
}

/* @ 246ED4 for 0x4FC; orig name: func_80198704 */
void get_3DG1_shape(struct ObjShape *shape) {
    UNUSED u8 pad78[8];
    struct GdVec3f tempNormal; /* maybe? */
    s32 curFaceVtx;
    s32 faceVtxID;
    s32 totalVtx;
    s32 totalFacePoints;
    struct GdVec3f tempVec;
    struct ObjFace *newFace;
    struct ObjVertex *vtxHead = NULL; // ptr to first made ObjVertex in the Obj* list
    s32 vtxCount = 0;
    struct ObjFace *faceHead = NULL; // ptr to first made OBjFace in the Obj* list
    s32 faceCount = 0;
    struct ObjFace **facePtrArr;
    struct ObjVertex **vtxPtrArr;
    struct ObjMaterial *mtl;

    shape->mtlGroup = make_group(0);
    add_to_stacktrace("get_3DG1_shape");

    vtxPtrArr = gd_malloc_perm(72000 * sizeof(struct ObjVertex *)); // 288,000 = 72,000 * 4
    facePtrArr = gd_malloc_perm(76000 * sizeof(struct ObjFace *));  // 304,000 = 76,000 * 4

    tempNormal.x = 0.0f;
    tempNormal.y = 0.0f;
    tempNormal.z = 1.0f;

    load_next_line_into_buf();
    if (!getint(&totalVtx)) {
        fatal_printf("Missing number of points");
    }

    load_next_line_into_buf();
    while (scan_to_next_non_whitespace()) {
        getfloat(&tempVec.x);
        getfloat(&tempVec.y);
        getfloat(&tempVec.z);
        vtxPtrArr[vtxCount] = gd_make_vertex(tempVec.x, tempVec.y, tempVec.z);

        if (vtxHead == NULL) {
            vtxHead = vtxPtrArr[vtxCount];
        }

        func_8019807C(vtxPtrArr[vtxCount]);
        vtxCount++;

        if (vtxCount >= 4000) {
            fatal_printf("Too many vertices in shape data");
        }

        shape->vtxCount++;
        clear_buf_to_cr();

        if (--totalVtx == 0) { /* Count down vertex ponts */
            break;
        }
    }

    while (scan_to_next_non_whitespace()) {
        if (!getint(&totalFacePoints)) {
            fatal_printf("Missing number of points in face");
        }

        mtl = find_or_add_new_mtl(shape->mtlGroup, 0, tempNormal.x, tempNormal.y, tempNormal.z);
        newFace = make_face_with_material(mtl);

        if (faceHead == NULL) {
            faceHead = newFace;
        }

        facePtrArr[faceCount] = newFace;
        faceCount++;
        if (faceCount >= 4000) {
            fatal_printf("Too many faces in shape data");
        }

        curFaceVtx = 0;
        while (get_current_buf_char() != '\0') {
            getint(&faceVtxID);

            if (curFaceVtx > 3) {
                fatal_printf("Too many points in a face(%d)", curFaceVtx);
            }

            newFace->vertices[curFaceVtx] = vtxPtrArr[faceVtxID];
            curFaceVtx++;

            if (is_line_end(get_current_buf_char()) || --totalFacePoints == 0) {
                break;
            }
        }

        newFace->vtxCount = curFaceVtx;

        if (newFace->vtxCount > 3) {
            fatal_printf("Too many points in a face(%d)", newFace->vtxCount);
        }

        calc_face_normal(newFace);

        tempNormal.x = newFace->normal.x > 0.0f ? 1.0f : 0.0f;
        tempNormal.y = newFace->normal.y > 0.0f ? 1.0f : 0.0f;
        tempNormal.z = newFace->normal.z > 0.0f ? 1.0f : 0.0f;

        shape->faceCount++;

        clear_buf_to_cr();
    }

    gd_free(vtxPtrArr);
    gd_free(facePtrArr);

    shape->vtxGroup = make_group_of_type(OBJ_TYPE_VERTICES, (struct GdObj *) vtxHead, NULL);
    shape->faceGroup = make_group_of_type(OBJ_TYPE_FACES, (struct GdObj *) faceHead, NULL);

    imout();
}

/* @ 2473D0 for 0x390; orig name: func_80198C00 */
void get_OBJ_shape(struct ObjShape *shape) {
    UNUSED u8 pad7D54[4];
    struct GdColour faceClr;
    s32 curFaceVtx;
    s32 faceVtxIndex;
    struct GdVec3f tempVec;
    struct ObjFace *newFace;
    struct ObjVertex *vtxArr[4000];
    struct ObjFace *faceArr[4000];
    s32 faceCount = 0;
    s32 vtxCount = 0;

    faceClr.r = 1.0f;
    faceClr.g = 0.5f;
    faceClr.b = 1.0f;

    sGdLineBufCsr = 0;

    while (scan_to_next_non_whitespace()) {
        switch (get_and_advance_buf()) {
            case 'v':
                getfloat(&tempVec.x);
                getfloat(&tempVec.y);
                getfloat(&tempVec.z);

                vtxArr[vtxCount] = gd_make_vertex(tempVec.x, tempVec.y, tempVec.z);
                func_8019807C(vtxArr[vtxCount]);
                vtxCount++;

                if (vtxCount >= 4000) {
                    fatal_printf("Too many vertices in shape data");
                }

                shape->vtxCount++;
                break;

            case 'f':
                newFace = make_face_with_colour(faceClr.r, faceClr.g, faceClr.b);
                faceArr[faceCount] = newFace;
                faceCount++;

                if (faceCount >= 4000) {
                    fatal_printf("Too many faces in shape data");
                }

                curFaceVtx = 0;
                while (get_current_buf_char() != '\0') {
                    getint(&faceVtxIndex);

                    if (curFaceVtx > 3) {
                        fatal_printf("Too many points in a face(%d)", curFaceVtx);
                    }

                    /* .obj vertex list is 1-indexed */
                    newFace->vertices[curFaceVtx] = vtxArr[faceVtxIndex - 1];
                    curFaceVtx++;

                    if (is_line_end(get_current_buf_char())) {
                        break;
                    }
                }

                /* These are already set by make_face_with_colour... */
                newFace->colour.r = faceClr.r;
                newFace->colour.g = faceClr.g;
                newFace->colour.b = faceClr.b;

                newFace->vtxCount = curFaceVtx;

                if (newFace->vtxCount > 3) {
                    fatal_printf("Too many points in a face(%d)", newFace->vtxCount);
                }

                calc_face_normal(newFace);

                shape->faceCount++;
                break;

            case 'g':
                break;
            case '#':
                break;
            default:
                break;
        }

        clear_buf_to_cr();
    }

    shape->vtxGroup = make_group_of_type(OBJ_TYPE_VERTICES, (struct GdObj *) vtxArr[0], NULL);
    shape->faceGroup = make_group_of_type(OBJ_TYPE_FACES, (struct GdObj *) faceArr[0], NULL);
}

/* @ 247760 for 0x124; orig name: func_80198F90 */
struct ObjGroup *group_faces_in_mtl_grp(struct ObjGroup *mtlGroup, struct GdObj *fromObj,
                                        struct GdObj *toObj) {
    struct ObjMaterial *curObjAsMtl;
    struct ObjGroup *newGroup;
    struct GdObj *curObj;
    register struct Links *grpLink;
    struct GdObj *curLinkedObj;

    newGroup = make_group(0);

    for (grpLink = mtlGroup->link1C; grpLink != NULL; grpLink = grpLink->next) {
        curLinkedObj = grpLink->obj;
        curObjAsMtl = (struct ObjMaterial *) curLinkedObj;

        curObj = fromObj;
        while (curObj != NULL) {
            if (curObj == toObj) {
                break;
            }

            if (curObj->type == OBJ_TYPE_FACES) {
                if (((struct ObjFace *) curObj)->mtl == curObjAsMtl) {
                    addto_group(newGroup, curObj);
                }
            }
            curObj = curObj->prev;
        }
    }

    return newGroup;
}

/* @ 247884 for 0x13c; orig name: func_801990B4 */
struct ObjMaterial *find_or_add_new_mtl(struct ObjGroup *group, UNUSED s32 a1, f32 r, f32 g, f32 b) {
    struct ObjMaterial *newMtl;
    register struct Links *curLink;
    struct ObjMaterial *foundMtl;

    for (curLink = group->link1C; curLink != NULL; curLink = curLink->next) {
        foundMtl = (struct ObjMaterial *) curLink->obj;

        if (foundMtl->header.type == OBJ_TYPE_MATERIALS) {
            if (foundMtl->Kd.r == r) {
                if (foundMtl->Kd.g == g) {
                    if (foundMtl->Kd.b == b) {
                        return foundMtl;
                    }
                }
            }
        }
    }

    newMtl = make_material(0, NULL, 1);
    set_cur_dynobj(newMtl);
    d_set_diffuse(r, g, b);
    addto_group(group, (struct GdObj *) newMtl);

    return newMtl;
}

/* @ 2479C0 for 0x470; orig name: func_801991F0 */
void read_ARK_shape(struct ObjShape *shape, char *fileName) {
    union {
        s8 bytes[0x48];
        struct {
            u8 pad[0x40];
            s32 word40;
            s32 word44;
        } data;
    } fileInfo;

    union {
        s8 bytes[0x10];
        struct {
            f32 v[3];
            s32 faceCount;
        } data;
    } faceInfo; // face normal x,y,z? + count

    union {
        s8 bytes[0x10];
        struct {
            s32 vtxCount;
            f32 x, y, z;
        } data;
    } face; // face vtx count + vtx x,y,z ?

    union {
        s8 bytes[0x18];
        struct {
            f32 v[3];
            f32 nv[3]; /* Guessing on the normals; they aren't used */
        } data;
    } vtx;

    UNUSED u8 pad54[4];
    struct GdVec3f sp48;
    struct ObjFace *sp44;          // newly made face with mtl sp34;
    struct ObjFace *sp40 = NULL;   // first made face
    struct ObjVertex *sp3C;        // newly made vtx
    struct ObjVertex *sp38 = NULL; // first made vtx
    struct ObjMaterial *sp34;      // found or new mtl for face
    UNUSED s32 sp30 = 0;
    UNUSED s32 sp2C = 0;

    shape->mtlGroup = make_group(0);

    sp48.x = 1.0f;
    sp48.y = 0.5f;
    sp48.z = 1.0f;

    sGdShapeFile = gd_fopen(fileName, "rb");

    if (sGdShapeFile == NULL) {
        fatal_printf("Cant load shape '%s'", fileName);
    }

    gd_fread(fileInfo.bytes, 0x48, 1, sGdShapeFile);
    func_801A5998(&fileInfo.bytes[0x40]); // face count?
    func_801A5998(&fileInfo.bytes[0x44]);

    while (fileInfo.data.word40-- > 0) {
        gd_fread(faceInfo.bytes, 0x10, 1, sGdShapeFile);
        func_801A59C0(&faceInfo.bytes[0x0]);
        func_801A59C0(&faceInfo.bytes[0x4]);
        func_801A59C0(&faceInfo.bytes[0x8]);

        sp48.x = faceInfo.data.v[0];
        sp48.y = faceInfo.data.v[1];
        sp48.z = faceInfo.data.v[2];

        sp34 = find_or_add_new_mtl(shape->mtlGroup, 0, sp48.x, sp48.y, sp48.z);

        func_801A5998(&faceInfo.bytes[0xC]);

        while (faceInfo.data.faceCount-- > 0) {
            shape->faceCount++;
            gd_fread(face.bytes, 0x10, 1, sGdShapeFile);
            func_801A59C0(&face.bytes[0x4]); // read word as f32?
            func_801A59C0(&face.bytes[0x8]);
            func_801A59C0(&face.bytes[0xC]);

            sp44 = make_face_with_material(sp34);

            if (sp40 == NULL) {
                sp40 = sp44;
            }

            func_801A5998(&face.bytes[0x0]);

            if (face.data.vtxCount > 3) {
                while (face.data.vtxCount-- > 0) {
                    gd_fread(vtx.bytes, 0x18, 1, sGdShapeFile);
                }
                continue;
            }

            while (face.data.vtxCount-- > 0) {
                shape->vtxCount++;
                gd_fread(vtx.bytes, 0x18, 1, sGdShapeFile);
                func_801A59C0(&vtx.bytes[0x00]);
                func_801A59C0(&vtx.bytes[0x04]);
                func_801A59C0(&vtx.bytes[0x08]);
                func_801A59C0(&vtx.bytes[0x0C]);
                func_801A59C0(&vtx.bytes[0x10]);
                func_801A59C0(&vtx.bytes[0x14]);

                func_801980E8(vtx.data.v);
                sp3C = gd_make_vertex(vtx.data.v[0], vtx.data.v[1], vtx.data.v[2]);

                if (sp44->vtxCount > 3) {
                    fatal_printf("Too many points in a face(%d)", sp44->vtxCount);
                }

                sp44->vertices[sp44->vtxCount] = sp3C;
                sp44->vtxCount++;

                if (sp38 == NULL) {
                    sp38 = sp3C;
                }
            }

            calc_face_normal(sp44);
        }
    }

    shape->vtxGroup = make_group_of_type(OBJ_TYPE_VERTICES, (struct GdObj *) sp38, NULL);
    shape->faceGroup = group_faces_in_mtl_grp(shape->mtlGroup, (struct GdObj *) sp40, NULL);
    gd_fclose(sGdShapeFile);
}

/* @ 247E30 for 0x148; orig name: Unknown80199660 */
struct GdFile *get_shape_from_file(struct ObjShape *shape, char *fileName) {
    printf("Loading %s...\n", fileName);
    start_memtracker(fileName);
    shape->unk3C = 0;
    shape->faceCount = 0;
    shape->vtxCount = 0;

    if (gd_str_contains(fileName, ".ark")) {
        read_ARK_shape(shape, fileName);
    } else {
        sGdShapeFile = gd_fopen(fileName, "r");

        if (sGdShapeFile == NULL) {
            fatal_printf("Cant open shape '%s'", fileName);
        }

        sGdLineBufCsr = 0;
        sGdLineBuf[sGdLineBufCsr] = '\0';
        load_next_line_into_buf();

        if (is_next_buf_word("3DG1")) {
            get_3DG1_shape(shape);
        } else {
            get_OBJ_shape(shape);
        }

        printf("Num Vertices=%d\n", shape->vtxCount);
        printf("Num Faces=%d\n", shape->faceCount);
        printf("\n");

        gd_fclose(sGdShapeFile);
    }

    stop_memtracker(fileName);

    return sGdShapeFile;
}

/* @ 247F78 for 0x69c; orig name: Unknown801997A8 */
struct ObjShape *make_grid_shape(enum ObjTypeFlag gridType, s32 a1, s32 a2, s32 a3, s32 a4) {
    UNUSED u32 pad1074;
    void *objBuf[32][32]; // vertex or particle depending on gridType
    f32 sp70;
    f32 sp6C;
    f32 sp68;
    UNUSED u32 pad64;
    UNUSED u32 pad60;
    f32 sp5C;
    s32 parI;
    s32 row;
    s32 col;
    UNUSED s32 sp4C = 0;
    struct ObjShape *gridShape;
    f32 sp44;
    struct ObjFace *sp40 = NULL;  // first made shape?
    struct ObjGroup *parOrVtxGrp; // group of made particles or vertices (based on gridType)
    UNUSED u32 pad38;
    struct ObjGroup *mtlGroup;
    struct GdVec3f *sp30;     // GdVec3f* ? from gd_get_colour
    struct GdVec3f *sp2C;     //^
    struct ObjMaterial *mtl1; // first made material
    struct ObjMaterial *mtl2; // second made material
    UNUSED u32 pad20;

    sp30 = (struct GdVec3f *) gd_get_colour(a1);
    sp2C = (struct GdVec3f *) gd_get_colour(a2);

    mtl1 = make_material(0, NULL, 1);
    set_cur_dynobj((struct GdObj *) mtl1);
    d_set_diffuse(sp30->x, sp30->y, sp30->z);
    mtl1->type = 0x40;

    mtl2 = make_material(0, NULL, 2);
    set_cur_dynobj((struct GdObj *) mtl2);
    d_set_diffuse(sp2C->x, sp2C->y, sp2C->z);
    mtl2->type = 0x40;

    mtlGroup = make_group(2, mtl1, mtl2);
    gridShape = make_shape(0, "grid");
    gridShape->faceCount = 0;
    gridShape->vtxCount = 0;

    sp44 = 2.0 / a3; //? 2.0f
    sp5C = -1.0f;
    sp6C = 0.0f;
    sp70 = -1.0f;

    for (col = 0; col <= a4; col++) {
        sp68 = sp5C;
        for (row = 0; row <= a3; row++) {
            gridShape->vtxCount++;
            if (gridType == OBJ_TYPE_VERTICES) {
                objBuf[row][col] = gd_make_vertex(sp68, sp6C, sp70);
            } else if (gridType == OBJ_TYPE_PARTICLES) {
                objBuf[row][col] = make_particle(0, 0, sp68, sp6C + 2.0f, sp70);
                ((struct ObjParticle *) objBuf[row][col])->unk44 = (1.0 + sp68) / 2.0;
                ((struct ObjParticle *) objBuf[row][col])->unk48 = (1.0 + sp70) / 2.0;
            }
            sp68 += sp44;
        }
        sp70 += sp44;
    }

    for (col = 0; col < a4; col++) {
        for (row = 0; row < a3; row++) {
            gridShape->faceCount += 2;
            if (a1 != a2) {
                if ((row + col) & 1) {
                    D_801BAC9C = make_face_with_material(mtl1);
                    D_801BACA0 = make_face_with_material(mtl1);
                } else {
                    D_801BAC9C = make_face_with_material(mtl2);
                    D_801BACA0 = make_face_with_material(mtl2);
                }
            } else {
                D_801BAC9C = make_face_with_material(mtl1);
                D_801BACA0 = make_face_with_material(mtl2);
            }

            if (sp40 == NULL) {
                sp40 = D_801BAC9C;
            }

            add_3_vtx_to_face(D_801BAC9C, objBuf[row][col + 1], objBuf[row + 1][col + 1],
                              objBuf[row][col]);
            add_3_vtx_to_face(D_801BACA0, objBuf[row + 1][col + 1], objBuf[row + 1][col],
                              objBuf[row][col]);
        }
    }

    if (gridType == OBJ_TYPE_PARTICLES) {
        for (parI = 0; parI <= a3; parI++) {
            ((struct ObjParticle *) objBuf[parI][0])->unk54 |= 2;
            ((struct ObjParticle *) objBuf[parI][a4])->unk54 |= 2;
        }

        for (parI = 0; parI <= a4; parI++) {
            ((struct ObjParticle *) objBuf[0][parI])->unk54 |= 2;
            ((struct ObjParticle *) objBuf[a3][parI])->unk54 |= 2;
        }
    }

    parOrVtxGrp = make_group_of_type(gridType, (struct GdObj *) objBuf[0][0], NULL);
    gridShape->vtxGroup = parOrVtxGrp;
    gridShape->mtlGroup = mtlGroup;

    gridShape->faceGroup = group_faces_in_mtl_grp(gridShape->mtlGroup, (struct GdObj *) sp40, NULL);

    printf("grid: points=%d, faces=%d\n", gridShape->vtxGroup->id, gridShape->faceGroup->id);
    return gridShape;
}

/* @ 248614 for 0x44 */
void Unknown80199E44(UNUSED s32 a0, struct GdObj *a1, struct GdObj *a2, UNUSED s32 a3) {
    UNUSED struct ObjGroup *sp1C = make_group(2, a1, a2);
}

/* @ 248658 for 0x5c */
void Unknown80199E88(struct ObjFace *face) {
    // TODO: remove cast when make_plane is updated
    D_801BAC74 = make_plane(FALSE, face);

    if (D_801BAC78 == NULL) {
        D_801BAC78 = D_801BAC74;
    }
}

/* @ 2486B4 for 0xbc; orig name: func_80199EE4 */
struct ObjNet *make_netfromshape(struct ObjShape *shape) {
    struct ObjNet *newNet;

    if (shape == NULL) {
        fatal_printf("make_netfromshape(): null shape ptr");
    }

    D_801BAC78 = NULL;
    apply_to_obj_types_in_group(OBJ_TYPE_FACES, (applyproc_t) Unknown80199E88, shape->faceGroup);
    D_801BAD08 = make_group_of_type(OBJ_TYPE_PLANES, (struct GdObj *) D_801BAC78, NULL);
    newNet = make_net(0, shape, NULL, D_801BAD08, shape->vtxGroup);
    newNet->netType = 1;

    return newNet;
}

/* @ 248770 for 0xc8; orig name: Proc80199FA0 */
void animate_mario_head_gameover(struct ObjAnimator *self) {
    switch (self->unk4C) {
        case 0:
            self->unk28 = 1.0f;
            self->unk20 = 1;
            self->unk4C = 1;
            break;
        case 1:
            self->unk28 += 1.0f;
            if (self->unk28 == 166.0f) {
                self->unk28 = 69.0f;
                self->unk4C = 4;
                self->fn48 = &animate_mario_head_normal;
                self->unk20 = 0;
            }
            break;
    }
}

/* @ 248838 for 0x310; orig name: Proc8019A068 */
void animate_mario_head_normal(struct ObjAnimator *self) {
    s32 state = 0; // TODO: label these states
    s32 aBtnPressed = gGdCtrl.btnApressed;

    switch (self->unk4C) {
        case 0:
            self->unk28 = 1.0f;
            self->unk20 = 0;
            state = 2;
            self->unk50 = 5;
            break;
        case 2:
            if (aBtnPressed) {
                state = 5;
            }

            self->unk28 += 1.0f;

            if (self->unk28 == 810.0f) {
                self->unk28 = 750.0f;
                self->unk50 -= 1;
                if (self->unk50 == 0) {
                    state = 3;
                }
            }
            break;
        case 3:
            self->unk28 += 1.0f;

            if (self->unk28 == 820.0f) {
                self->unk28 = 69.0f;
                state = 4;
            }
            break;
        case 4:
            self->unk28 += 1.0f;

            if (self->unk28 == 660.0f) {
                self->unk28 = 661.0f;
                state = 2;
                self->unk50 = 5;
            }
            break;
        case 5:
            if (self->unk28 == 660.0f) {
                state = 7;
            } else if (self->unk28 > 660.0f) {
                self->unk28 -= 1.0f;
            } else if (self->unk28 < 660.0f) {
                self->unk28 += 1.0f;
            }

            self->unk54 = 150;
            break;
        case 7:
            if (aBtnPressed) {
                self->unk54 = 300;
            } else {
                self->unk54--;
                if (self->unk54 == 0) {
                    state = 6;
                }
            }
            self->unk28 = 660.0f;
            break;
        case 6:
            state = 2;
            self->unk50 = 5;
            break;
    }

    if (state != 0) {
        self->unk4C = state;
    }
}

/* @ 248B48 for 0x740; orig name: func_8019A378 */
s32 load_mario_head(void (*aniFn)(struct ObjAnimator *)) {
    struct ObjNet *sp54; // net made with sp48 group
    UNUSED u8 pad4C[0x54 - 0x4c];
    struct ObjGroup *sp48; // Joint group
    UNUSED u8 pad40[0x48 - 0x40];
    struct ObjGroup *sp3C;
    struct GdObj *sp38;       // object list head before making a bunch of joints
    struct GdObj *sp34;       // d_use_obj returned
    struct ObjJoint *sp30;    // created joint pointer
    struct ObjCamera *sp2C;   // dNewCamera
    struct ObjAnimator *sp28; // dNewAnim
    struct ObjParticle *sp24; // particle (?)

    start_memtracker("mario face");
    d_copystr_to_idbuf("l");

    dynid_is_int(TRUE);
    sp28 = (struct ObjAnimator *) d_makeobj(D_ANIMATOR, AsDynId(1001));
    sp28->fn48 = aniFn;
    dynid_is_int(FALSE);
    // FIXME: make segment address work once seg4 is disassembled
    gMarioFaceGrp = (struct ObjGroup *) load_dynlist(dynlist_mario_master);
    stop_memtracker("mario face");

    sp2C = (struct ObjCamera *) d_makeobj(D_CAMERA, NULL);
    d_set_rel_pos(0.0f, 200.0f, 2000.0f);
    d_set_world_pos(0.0f, 200.0f, 2000.0f);
    d_set_flags(4);
    sp2C->unk34.x = 0.0f;
    sp2C->unk34.y = 200.0f;
    sp2C->unk34.z = 0.0f;

    addto_group(gMarioFaceGrp, &sp2C->header);
    addto_group(gMarioFaceGrp, &sp28->header);

    d_copystr_to_idbuf(NULL);
    sp24 = make_particle(0, 1, 0.0f, 0.0f, 0.0f);
    sp24->unk60 = 3;
    sp24->unk64 = 3;
    sp24->unkBC = &sp2C->header;
    sp24->unk1C = gShapeSilverSpark;
    addto_group(gGdLightGroup, &sp24->header);

    sp24 = make_particle(0, 1, 0.0f, 0.0f, 0.0f);
    sp24->unk60 = 3;
    sp24->unk64 = 2;
    sp24->unkBC = d_use_obj("N228l"); // probably a camera
    sp24->unk1C = gShapeSilverSpark;
    addto_group(gGdLightGroup, &sp24->header);

    sp24 = make_particle(0, 2, 0.0f, 0.0f, 0.0f);
    sp24->unk60 = 3;
    sp24->unk64 = 2;
    sp24->unkBC = d_use_obj("N231l"); // probably a camera
    sp24->unk1C = gShapeRedSpark;
    addto_group(gGdLightGroup, &sp24->header);

    sp3C = (struct ObjGroup *) d_use_obj("N1000l");
    create_gddl_for_shapes(sp3C);
    sp38 = gGdObjectList;

    sp30 = make_joint_withshape(D_801A82E8, 0, -500.0f, 0.0f, -150.0f);
    sp34 = d_use_obj("N167l");
    sp30->unk1F8 = make_group(1, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, 500.0f, 0.0f, -150.0f);
    sp34 = d_use_obj("N176l");
    sp30->unk1F8 = make_group(1, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, 0.0f, 700.0f, 300.0f);
    sp34 = d_use_obj("N131l");
    sp30->unk1F8 = make_group(1, sp34);

    sp34 = d_use_obj("N206l");
    addto_group(sp30->unk1F8, sp34);

    sp34 = d_use_obj("N215l");
    addto_group(sp30->unk1F8, sp34);

    sp34 = d_use_obj("N31l");
    addto_group(sp30->unk1F8, sp34);

    sp34 = d_use_obj("N65l");
    addto_group(sp30->unk1F8, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, 0.0f, 0.0f, 600.0f);
    sp34 = d_use_obj("N185l");
    sp30->unk1F8 = make_group(1, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, 0.0f, -300.0f, 300.0f);
    sp34 = d_use_obj("N194l");
    sp30->unk1F8 = make_group(1, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, 250.0f, -150.0f, 300.0f);
    sp34 = d_use_obj("N158l");
    sp30->unk1F8 = make_group(1, sp34);

    sp34 = d_use_obj("N15l");
    addto_group(sp30->unk1F8, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, -250.0f, -150.0f, 300.0f);
    sp34 = d_use_obj("N149l");
    sp30->unk1F8 = make_group(1, sp34);

    sp34 = d_use_obj("N6l");
    addto_group(sp30->unk1F8, sp34);

    sp30 = make_joint_withshape(D_801A82E8, 0, 100.0f, 200.0f, 400.0f);
    sp34 = d_use_obj("N112l");
    sp30->unk1F8 = make_group(1, sp34);

    sp30->fn2C = &Proc8018EBE8;
    sp30->unk1D0 = sp28;
    sp30->header.drawFlags &= ~OBJ_IS_GRABBALE;

    sp30 = make_joint_withshape(D_801A82E8, 0, -100.0f, 200.0f, 400.0f);
    sp34 = d_use_obj("N96l");
    sp30->unk1F8 = make_group(1, sp34);

    sp30->fn2C = &Proc8018EBE8;
    sp30->unk1D0 = sp28;
    sp30->header.drawFlags &= ~OBJ_IS_GRABBALE;

    sp48 = make_group_of_type(OBJ_TYPE_JOINTS, sp38, NULL);
    sp54 = make_net(0, NULL, sp48, NULL, NULL);
    sp54->netType = 3;
    addto_group(gMarioFaceGrp, &sp48->header);
    addto_groupfirst(gMarioFaceGrp, &sp54->header);

    return 0;
}

/* @ 249288 for 0xe0 */
void load_shapes2(void) {
    add_to_stacktrace("load_shapes2()");
    reset_dynlist();
    func_80197280();
    sCubeShape = make_shape(0, "cube");
    D_801A82E4 = (struct ObjShape *) load_dynlist(dynlist_unused);
    scale_verts_in_shape(D_801A82E4, 200.0f, 200.0f, 200.0f);

    D_801A82E8 = (struct ObjShape *) load_dynlist(dynlist_test_cube);
    scale_verts_in_shape(D_801A82E8, 30.0f, 30.0f, 30.0f);
    sCubeShapeGroup = make_group_of_type(OBJ_TYPE_SHAPES, &sCubeShape->header, NULL);
    create_gddl_for_shapes(sCubeShapeGroup);

    imout();
}

/* @ 249368 -> 249594 */
struct ObjGroup *Unknown8019AB98(UNUSED u32 a0) {
    struct ObjLight *light1;
    struct ObjLight *light2;
    struct GdObj *oldObjHead = gGdObjectList; // obj head node before making lights

    light1 = make_light(0, NULL, 0);
    light1->position.x = 100.0f;
    light1->position.y = 200.0f;
    light1->position.z = 300.0f;

    light1->diffuse.r = 1.0f;
    light1->diffuse.g = 0.0f;
    light1->diffuse.b = 0.0f;

    light1->unk30 = 1.0f;

    light1->unk68.x = 0.4f;
    light1->unk68.y = 0.9f;

    light1->unk80.x = 4.0f;
    light1->unk80.y = 4.0f;
    light1->unk80.z = 2.0f;

    light2 = make_light(0, NULL, 1);
    light2->position.x = 100.0f;
    light2->position.y = 200.0f;
    light2->position.z = 300.0f;

    light2->diffuse.r = 0.0f;
    light2->diffuse.g = 0.0f;
    light2->diffuse.b = 1.0f;

    light2->unk30 = 1.0f;

    light2->unk80.x = -4.0f;
    light2->unk80.y = 4.0f;
    light2->unk80.z = -2.0f;

    gGdLightGroup = make_group_of_type(OBJ_TYPE_LIGHTS, oldObjHead, NULL);

    return gGdLightGroup;
}

/* @ 249594 for 0x100 */
struct ObjGroup *Unknown8019ADC4(UNUSED u32 a0) {
    UNUSED struct ObjLight *unusedLight;
    struct ObjLight *newLight;
    struct GdObj *oldObjHead;

    unusedLight = make_light(0, NULL, 0);
    oldObjHead = gGdObjectList;
    newLight = make_light(0, NULL, 0);

    newLight->position.x = 0.0f;
    newLight->position.y = -500.0f;
    newLight->position.z = 0.0f;

    newLight->diffuse.r = 1.0f;
    newLight->diffuse.g = 0.0f;
    newLight->diffuse.b = 0.0f;

    newLight->unk30 = 1.0f;

    gGdLightGroup = make_group_of_type(OBJ_TYPE_LIGHTS, oldObjHead, NULL);

    return gGdLightGroup;
}

/* @ 249694 for 0x5c */
struct ObjGroup *Unknown8019AEC4(UNUSED u32 a0) {
    UNUSED u32 sp24;
    UNUSED u32 sp20;
    UNUSED struct GdObj *sp1C;

    sp1C = gGdObjectList;
    gGdLightGroup = make_group(0);
    return gGdLightGroup;
}
