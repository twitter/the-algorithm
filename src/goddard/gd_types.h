#ifndef GD_TYPES_H
#define GD_TYPES_H

#include <ultra64.h>

/* Vector Types */
struct GdVec3f {
    f32 x, y, z;
};

struct GdBoundingBox {
    f32 minX, minY, minZ;
    f32 maxX, maxY, maxZ;
};

struct GdTriangleF {
    struct GdVec3f p0, p1, p2;
};

struct GdAnimTransform {
    struct GdVec3f scale;
    struct GdVec3f rotate;  // each component specifies the degrees to rotate about that axis
    struct GdVec3f pos;
};

typedef f32 Mat4f[4][4];

struct GdColour {
    f32 r, g, b;
};

/* dynlist entries and types */
union DynUnion {
    void *ptr;
    char *str;
    s32 word;
};

struct DynList {
    s32 cmd;
    union DynUnion w1;
    union DynUnion w2;
    struct GdVec3f vec;
};

/* Goddard Code Object Structs */
/* Object Type Flags */
enum ObjTypeFlag {
    OBJ_TYPE_GROUPS    = 0x00000001,
    OBJ_TYPE_BONES     = 0x00000002,
    OBJ_TYPE_JOINTS    = 0x00000004,
    OBJ_TYPE_PARTICLES = 0x00000008,
    OBJ_TYPE_SHAPES    = 0x00000010,
    OBJ_TYPE_NETS      = 0x00000020,
    OBJ_TYPE_PLANES    = 0x00000040,
    OBJ_TYPE_FACES     = 0x00000080,
    OBJ_TYPE_VERTICES  = 0x00000100,
    OBJ_TYPE_CAMERAS   = 0x00000200,
    // 0x400 was not used
    OBJ_TYPE_MATERIALS = 0x00000800,
    OBJ_TYPE_WEIGHTS   = 0x00001000,
    OBJ_TYPE_GADGETS   = 0x00002000,
    OBJ_TYPE_VIEWS     = 0x00004000,
    OBJ_TYPE_LABELS    = 0x00008000,
    OBJ_TYPE_ANIMATORS = 0x00010000,
    OBJ_TYPE_VALPTRS   = 0x00020000,
    // 0x40000 was not used
    OBJ_TYPE_LIGHTS    = 0x00080000,
    OBJ_TYPE_ZONES     = 0x00100000,
    OBJ_TYPE_UNK200000 = 0x00200000
};
/* This constant seems to be used to indicate the type of any or all objects */
#define OBJ_TYPE_ALL 0x00FFFFFF


/// Function pointer for a `GdObj`'s drawing routine
typedef void (*drawmethod_t)(void *);
/// Flags for the drawFlags field of an GdObj
enum ObjDrawingFlags {
    OBJ_DRAW_UNK01   = 0x01,
    OBJ_INVISIBLE    = 0x02, ///< This `GdObj` shouldn't be drawn when updating a scene
    OBJ_PICKED       = 0x04, ///< This `GdObj` is held by the cursor
    OBJ_IS_GRABBABLE = 0x08, ///< This `GdObj` can be grabbed/picked by the cursor
    OBJ_HIGHLIGHTED  = 0x10
};

/**
 * The base of structure of all of Goddard's objects. It is present as a "header"
 * at the beginning of all `ObjX` structures, and as such, this type is used
 * when we need to generalize code to take different `ObjX`es.
 * Every GdObj created is connected together in a linked list with `prev` and `next` pointers.
 */
struct GdObj {
    /* 0x00 */ struct GdObj *prev;  // previous node in gGdObjectList linked list
    /* 0x04 */ struct GdObj *next;  // next node in gGdObjectList linked list
    /* 0x08 */ drawmethod_t objDrawFn;
    /* 0x0C */ enum ObjTypeFlag type;
    /* 0x10 */ s16 index;    ///< the index of this `GdObj` in the linked list
    /* 0x12 */ u16 drawFlags; ///< enumerated in `::ObjDrawingFlags`
    /* 0x14 Specific object data starts here  */
};

/* Used to create a linked list of objects (or data)
** within an ObjGroup */
struct ListNode {
    /* 0x00 */ struct ListNode *prev;
    /* 0x04 */ struct ListNode *next;
    /* 0x08 */ struct GdObj *obj;
};

// I think this is actually the same type as ListNode, and data is just a generic "void *" pointer.
struct VtxLink {
    struct VtxLink *prev;
    struct VtxLink *next;
    Vtx *data;
};

/* These are the compressed versions of ObjFace or ObjVertex that are
** pointed to by ListNode in the faceGroup and vtxGroup, if Group.linkType
** is set to 0x01. See `chk_shapegen` */
struct GdFaceData {
    u32 count;
    s32 type;
    u16 (*data)[4]; ///< (mtl id, vtx ids[3])
};

struct GdVtxData {
    u32 count;
    s32 type;
    s16 (*data)[3]; ///< [x, y, z]
};


/**
 * Used to group related objects together. Seems to mainly be used for `apply_to_obj_types_in_group`.
 */
struct ObjGroup {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct ObjGroup *prev;
    /* 0x18 */ struct ObjGroup *next;
    /* 0x1C */ struct ListNode *firstMember; ///< Head of a linked list for objects contained in this group
    /* 0x20 */ struct ListNode *lastMember; ///< Tail of a linked list for objects contained in this group
    /* 0x24 */ s32 memberTypes;   ///< OR'd collection of type flags for all objects in this group
    /* 0x28 */ s32 memberCount;  // number of objects in this group
    /* 0x2C */ s32 debugPrint;  // might also be a type?
    /* 0x30 */ s32 linkType;
    /* 0x34 */ char name[0x40]; ///< possibly, only referenced in old code
    /* 0x74 */ s32 id;
}; /* sizeof = 0x78 */

/* Known linkTypes
 * 0x00 : Normal (link to GdObj)
 * 0x01 : Compressed (vtx or face data)
 */

struct ObjBone {
    /* 0x000 */ struct GdObj header;
    /* 0x014 */ struct GdVec3f worldPos;   // "position"?? from dead code in draw_bone
    /* 0x020 */ struct ObjBone *prev;   // maybe, based on make_bone
    /* 0x024 */ struct ObjBone *next;   // maybe, based on make_bone
    /* 0x028 */ struct GdVec3f unk28;   // "rotation"?? from dead code in draw_bone
    /* 0x034 */ u8  filler1[12];
    /* 0x040 */ struct GdVec3f unk40;
    /* 0x04C */ u8  filler2[12];
    /* 0x058 */ struct GdVec3f unk58;  // orientation?
    /* 0x064 */ struct GdVec3f unk64;
    /* 0x070 */ Mat4f mat70;
    /* 0x0B0 */ Mat4f matB0;
    /* 0x0F0 */ struct ObjShape *shapePtr; // from dead code in draw_bone
    /* 0x0F4 */ f32 unkF4;              // also length?
    /* 0x0F8 */ f32 unkF8;              // length?
    /* 0x0FC */ f32 unkFC;              // also length?
    /* 0x100 */ s32 colourNum;             // "colour"
    /* 0x104 */ s32 unk104;             // "flags"
    /* 0x108 */ s32 id;
    /* 0x10C */ struct ObjGroup *unk10C; // group of joints?
    /* 0x110 */ f32 spring;
    /* 0x114 */ f32 unk114;
    /* 0x118 */ f32 unk118;
    /* 0x11C */ u8  filler3[8];
}; /* sizeof = 0x124 */

struct ObjJoint {
    /* 0x000 */ struct GdObj header;
    /* 0x014 */ struct GdVec3f worldPos;    // position in world space
    /* 0x020 */ struct ObjShape *shapePtr;
    /* 0x024 */ struct ObjJoint *prevjoint; // prev joint? linked joint?
    /* 0x028 */ struct ObjJoint *nextjoint;
    /* 0x02C */ void (*updateFunc)(struct ObjJoint*);  // seems to update attached objects? see grabbable_joint_update_func
    /* 0x030 */ struct GdVec3f unk30;   // huge array of vecs? another matrix?
    /* 0x03C */ struct GdVec3f unk3C;   // relative position?
    /* 0x048 */ struct GdVec3f unk48;
    /* 0x054 */ struct GdVec3f initPos;   // attached offset? (with +200 as well)
    /* 0x060 */ u8  filler1[12];
    /* 0x06C */ struct GdVec3f unk6C;   // initial rotation vec
    /* 0x078 */ struct GdVec3f velocity;
    /* 0x084 */ struct GdVec3f unk84;
    /* 0x090 */ struct GdVec3f unk90;
    /* 0x09C */ struct GdVec3f scale;
    /* 0x0A8 */ struct GdVec3f unkA8;
    /* 0x0B4 */ struct GdVec3f unkB4;
    /* 0x0C0 */ struct GdVec3f shapeOffset;
    /* 0x0CC */ struct GdVec3f unkCC;
    /* 0x0D8 */ u8  filler2[4];
    /* 0x0DC */ struct GdVec3f friction;
    /* 0x0E8 */ Mat4f matE8;     //matrix4x4
    /* 0x128 */ Mat4f mat128;    // "rot matrix"
    /* 0x168 */ Mat4f mat168;    // "id matrix"
    /* 0x1A8 */ struct GdVec3f unk1A8;
    /* 0x1B4 */ s32 id;
    /* 0x1B8 */ u8  filler3[4];
    /* 0x1BC */ s32 flags;     // "flags" - 0x2000 = grabbed
    /* 0x1C0 */ s32 unk1C0;
    /* 0x1C4 */ struct ObjGroup *unk1C4;    // bone group?
    /* 0x1C8 */ s32 colourNum;
    /* 0x1CC */ s32 type;     // 0 = normal joint, 5 = grabbable joint. seems to be set, but never used
    /* 0x1D0 */ struct ObjAnimator *rootAnimator;  // root animator? used by eye_joint_update_func
    /* 0x1D4 */ u8  filler4[32];
    /* 0x1F4 */ struct ObjGroup *weightGrp;    //Group of ObjWeights, only? skin weights?
    /* 0x1F8 */ struct ObjGroup *attachedObjsGrp;    //attach object group
    /* 0x1FC */ s32 attachFlags;                 //d_attach_to arg 0; "AttFlag"
    /* 0x200 */ struct GdVec3f attachOffset;
    /* 0x20C */ struct GdObj *attachedToObj;  // object that this object is attached to
    /* 0x210 */ u8  filler5[24];
    /* 0x228 */ f32 unk228;
}; /* sizeof = 0x22C */

/* Particle Types (+60)
   3 = Has groups of other particles in 6C?
*/

struct ObjParticle {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u8 filler1[8];
    /* 0x1C */ struct ObjShape *shapePtr;     // looks like a shape...
    /* 0x20 */ struct GdVec3f pos;    // some kind of position - relative? world?
    /* 0x2C */ u8 filler2[4];
    /* 0x30 */ f32 unk30;
    /* 0x34 */ u8 filler3[4];
    /* 0x38 */ struct GdVec3f unk38;
    /* 0x44 */ f32 unk44; //not vec?
    /* 0x48 */ f32 unk48; //not vec?
    /* 0x4C */ u8 filler4[4];
    /* 0x50 */ s32 id;
    /* 0x54 */ u32 flags;   // "dflags"?
    /* 0x58 */ s32 colourNum;
    /* 0x5C */ s32 timeout;  // when this reaches zero, the particle disappears
    /* 0x60 */ s32 unk60;   //type?
    /* 0x64 */ s32 unk64;   //type? (1 = has 50 sub-particles, 2,3 = has 30 sub-particles
    /* 0x68 */ u8 filler5[4];
    /* 0x6C */ struct ObjGroup *subParticlesGrp;   // group of other Particles ?
    /* 0x70 */ u8 filler6[4];
    /* 0x74 */ s32 unk74;
    /* 0x78 */ u8 unk78[4];
    /* 0x7C */ struct ObjAnimator *unk7C;   // guessing on type; doesn't seem to be used in final code
    /* 0x80 */ struct ObjLight *unk80;  // could be a Net or Light; not seen as non-null in running code
    /* 0x84 */ u8 filler7[44];
    /* 0xB0 */ s32 unkB0;   //state?
    /* 0xB4 */ struct ObjGroup *attachedObjsGrp;  // attach group? unused group of particles
    /* 0xB8 */ s32 attachFlags;   //attached arg0; "AttFlag"
    /* 0xBC */ struct GdObj *attachedToObj; // object that this object is attached to. looks like can be a Light or Camera
}; /* sizeof = 0xC0 */

/**
 * An object that represents the visual portion of a 3D model.
 */
struct ObjShape {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct ObjShape *prevShape;
    /* 0x18 */ struct ObjShape *nextShape;
    /* 0x1C */ struct ObjGroup *faceGroup;  /* face group; based on get_3DG1_shape */
    /* 0x20 */ struct ObjGroup *vtxGroup;  /* vtx group; based on get_3DG1_shape */
    /* 0x24 */ struct ObjGroup *scaledVtxGroup; /* group for type 2 shapenets only ? vertices whose scaleFactor is not 1 get put into this group. */
    /* 0x28 */ u8 filler1[4];
    /* 0x2C */ struct ObjGroup *mtlGroup;  /* what does this group do? materials? */
    /* 0x30 */ s32 unk30;
    /* 0x34 */ s32 faceCount;   /* face count? based on get_3DG1_shape */
    /* 0x38 */ s32 vtxCount;   /* vtx count? based on get_3DG1_shape */
    /* 0x3C */ s32 unk3C; // bool? if FALSE, then draw_shape_faces(shape)
    /* 0x40 */ u32 id;
    /* 0x44 */ s32 flag; // what are the flag values? only from dynlists?
    /* 0x48 */ s32 dlNums[2];  // gd dl number for each frame buffer (??) [0, 1]
               s32 unk50;      // frame number (index into dlNums)?
    /* 0x54 */ u8  filler2[4]; // part of above array??
    /* 0x58 */ f32 alpha;       // paramF? opacitiy? something with rendertype
    /* 0x5C */ char name[0x40];
}; /* sizeof = 0x9C */

/* 0x44 Flag Values
 * 0x01 -
 * 0x10 - Use vtx position as vtx normal? (`chk_shapegen`)
 */

/* netTypes
 * 0 - default?
 * 1 - shape net
 * 2 - something about the shape unk24 group having vertices too?
 * 3 - joints?
 * 4 - dynamic net? bone net?
 * 5 - particle net?
 * 6 - stub
 * 7 -
 */

struct ObjNet {
    /* 0x000 */ struct GdObj header;
    /* 0x014 */ struct GdVec3f worldPos;   // position? d_set_initpos + d_set_world_pos; print_net says world
    /* 0x020 */ struct GdVec3f initPos;   // position? d_set_initpos? attached offset? dynamic? scratch?
    /* 0x02C */ u8  filler1[8];
    /* 0x034 */ s32 flags;       // "dflags"?
    /* 0x038 */ u32 id;      // some sort of id? from move_net
    /* 0x03C */ s32 unk3C;      // state flags? vertex info flags?
    /* 0x040 */ s32 colourNum;
    /* 0x044 */ struct GdVec3f unusedForce;   // "force" (unused)
    /* 0x050 */ struct GdVec3f velocity;
    /* 0x05C */ struct GdVec3f rotation;
    /* 0x068 */ struct GdVec3f unk68;   //initial rotation?
    /* 0x074 */ struct GdVec3f collDisp;   // what is this?
    /* 0x080 */ struct GdVec3f collTorque;   // what is this?
    /* 0x08C */ struct GdVec3f unusedCollTorqueL;   // unused
    /* 0x098 */ struct GdVec3f unusedCollTorqueD;   // unused
    /* 0x0A4 */ struct GdVec3f torque;   // torque
    /* 0x0B0 */ struct GdVec3f centerOfGravity;   // "CofG" center of gravity?
    /* 0x0BC */ struct GdBoundingBox boundingBox;
    /* 0x0D4 */ struct GdVec3f unusedCollDispOff;   // unused
    /* 0x0E0 */ f32 unusedCollMaxD;              // unused
    /* 0x0E4 */ f32 maxRadius;
    /* 0x0E8 */ Mat4f matE8;
    /* 0x128 */ Mat4f mat128;
    /* 0x168 */ Mat4f mat168;             // "rotation matrix"
    /* 0x1A8 */ struct ObjShape *shapePtr;
    /* 0x1AC */ struct GdVec3f scale;
    /* 0x1B8 */ f32 unusedMass;              // unused
    /* 0x1BC */ s32 numModes;              // unused
    /* 0x1C0 */ struct ObjGroup *unk1C0;    // group of `ObjVertex` or `ObjParticle`
    /* 0x1C4 */ struct ObjGroup *skinGrp;   // SkinGroup (from reset_weight) (joints and bones)
    /* 0x1C8 */ struct ObjGroup *unk1C8;    // "node group" (joints, weights?)
    /* 0x1CC */ struct ObjGroup *unk1CC;    // plane group (only type 1?)
    /* 0x1D0 */ struct ObjGroup *unk1D0;    // vertex group
    /* 0x1D4 */ struct ObjGroup *attachedObjsGrp;
    /* 0x1D8 */ struct GdVec3f attachOffset;
    /* 0x1E4 */ s32 attachFlags;                 // d_attach_to arg 0; "AttFlag"
    /* 0x1E8 */ struct GdObj *attachedToObj;   // object that this object is attached to
    /* 0x1EC */ s32 netType;    // from move_net
    /* 0x1F0 */ struct ObjNet *unk1F0;  // or joint. guess from Unknown80192AD0
    /* 0x1F4 */ struct GdVec3f unk1F4;
    /* 0x200 */ struct GdVec3f unk200;
    /* 0x20C */ struct ObjGroup *unk20C;
    /* 0x210 */ s32 ctrlType;     // has no purpose
    /* 0x214 */ u8  filler2[8];
    /* 0x21C */ struct ObjGroup *unk21C;
}; /* sizeof = 0x220 */

struct ObjPlane {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u32 id;
    /* 0x18 */ s32 unk18; //bool;  contained within zone? (from its parent Net?)
    /* 0x1C */ f32 unk1C;
    /* 0x20 */ s32 unk20;
    /* 0x24 */ s32 unk24;
    /* 0x28 */ struct GdBoundingBox boundingBox;
    /* 0x40 */ struct ObjFace* unk40;
}; /* sizeof = 0x44*/

struct ObjVertex {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct GdVec3f initPos;
    /* 0x20 */ struct GdVec3f pos;     // new position after being moved by joints?
    /* 0x2C */ struct GdVec3f normal;  // normal? also color (like gbi?)
    /* 0x38 */ s16 id;
    /* 0x3C */ f32 scaleFactor;
    /* 0x40 */ f32 alpha;
    /* 0x44 */ struct VtxLink *gbiVerts;
}; /* sizeof = 0x48 */

/**
 * An object that represents a face in an `ObjShape`. It connects 3 or 4 vertices.
 */
struct ObjFace {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct GdColour colour;
    /* 0x20 */ s32 colourNum;                     // "colour" index
    /* 0x24 */ struct GdVec3f normal;
    /* 0x30 */ s32 vtxCount;
    /* 0x34 */ struct ObjVertex *vertices[4];  // these can also be s32 indices? which are then replaced by `find_thisface_verts`
    /* 0x44 */ s32 mtlId; // from compressed GdFaceData; -1 == coloured face?
    /* 0x48 */ struct ObjMaterial *mtl; // initialize to NULL; set by `map_face_materials` from mtlId
}; /* sizeof = 0x4C */

#define CAMERA_FLAG_CONTROLLABLE 0x4

struct ObjCamera {
    /* 0x000 */ struct GdObj header;
    /* 0x014 */ struct GdVec3f worldPos;   // position vec? from d_set_initpos
    /* 0x020 */ struct ObjCamera* prev;
    /* 0x024 */ struct ObjCamera* next;
    /* 0x028 */ s32 id;
    /* 0x02C */ s32 flags;   // flag of some sort
    /* 0x030 */ struct GdObj* unk30;   // pointer to some type of object
    /* 0x034 */ struct GdVec3f lookAt;  // point that the camera faces
    /* 0x040 */ struct GdVec3f unk40;   // relative position related?
    /* 0x04C */ struct GdVec3f unk4C;
    /* 0x058 */ f32 unk58;      // GdVec3f ?
    /* 0x05C */ u8  filler[4];
    /* 0x060 */ f32 unk60;
    /* 0x064 */ Mat4f unk64;    //matrix4x4
    /* 0x0A4 */ f32 unkA4;
    /* 0x0A8 */ Mat4f unkA8;    //matrix4x4
    /* 0x0E8 */ Mat4f unkE8;
    /* 0x128 */ struct GdVec3f unk128;  //possibly
    /* 0x134 */ struct GdVec3f unk134;
    /* 0x140 */ struct GdVec3f zoomPositions[4]; // zoom positions (*1, *1.5, *2, empty fourth)
    /* 0x170 */ s32 maxZoomLevel; // max number of zoom positions
    /* 0x174 */ s32 zoomLevel; // index into zoomPositions array
    /* 0x178 */ f32 unk178;
    /* 0x17C */ f32 unk17C;
    /* 0x180 */ struct GdVec3f unk180;
    /* 0x18C */ struct ObjView *unk18C; // view that has/is using this camera?
}; /* sizeof = 0x190 */

enum GdMtlTypes {
    GD_MTL_STUB_DL = 0x01,
    GD_MTL_BREAK = 0x04,
    GD_MTL_SHINE_DL = 0x10,
    GD_MTL_TEX_OFF = 0x20,
    GD_MTL_LIGHTS = 0x40 // uses default case
};

struct ObjMaterial {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u8  filler1[8];
    /* 0x1C */ s32 id;
    /* 0x20 */ char name[8];
    /* 0x28 */ s32 type;
    /* 0x2C */ u8  filler2[4];
    /* 0x30 */ struct GdColour Ka;  // ambient color
    /* 0x3C */ struct GdColour Kd;  // diffuse color
    /* 0x48 */ u8  filler3[16];
    /* 0x58 */ void *texture; //set by d_usetexture; never seems to be non-null though.
    /* 0x5C */ s32 gddlNumber;
}; /* sizeof = 0x60 */

struct ObjWeight {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u8  filler1[8];
    /* 0x1C */ s32 vtxId;  // ID of vertex that this weight applies to
    /* 0x20 */ struct GdVec3f vec20;    // based on func_80181894? maybe a GdBoundingBox?
    /* 0x2C */ u8  filler2[12];
    /* 0x38 */ f32 weightVal; // weight (unit?)
    /* 0x3C */ struct ObjVertex* vtx;
}; /* sizeof = 0x40 */

/* This union is used in ObjGadget for a variable typed field.
** The type can be found by checking group unk4C */
union ObjVarVal {
    s32 i;
    f32 f;
    u64 l;
};

/*
 * A slider control used to adjust the value of a variable
 */
struct ObjGadget {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct GdVec3f worldPos;    // "world" position vec?
    /* 0x20 */ s32 unk20;  // unused; only ever set to 0
    /* 0x24 */ s32 type;
    /* 0x28 */ f32 sliderPos;  // position of the slider (from 0 to 1)
    /* 0x2C */ u8 filler1[4];
    /* 0x30 */ union ObjVarVal varval; //retype and rename varval30
    /* 0x38 */ f32 rangeMin;
    /* 0x3C */ f32 rangeMax;
    /* 0x40 */ struct GdVec3f size;   // size (x = width, y = height)
    /* 0x4C */ struct ObjGroup *valueGrp;  // group containing `ObjValPtr`s controlled by this gadget 
    /* 0x50 */ struct ObjShape *shapePtr;
    /* 0x54 */ struct ObjGroup *unk54;  //node group?
    /* 0x58 */ u8 filler2[4];
    /* 0x5C */ s32 colourNum;
}; /* sizeof = 0x60 */

enum GdViewFlags {
    VIEW_2_COL_BUF      = 0x000008,
    VIEW_ALLOC_ZBUF     = 0x000010,
    VIEW_SAVE_TO_GLOBAL = 0x000040,
    VIEW_DEFAULT_PARENT = 0x000100,
    VIEW_BORDERED       = 0x000400,
    VIEW_UPDATE         = 0x000800,
    VIEW_UNK_1000       = 0x001000, // used in setup_view_buffers
    VIEW_UNK_2000       = 0x002000, // only see together with 0x4000
    VIEW_UNK_4000       = 0x004000,
    VIEW_COLOUR_BUF     = 0x008000,
    VIEW_Z_BUF          = 0x010000,
    VIEW_1_CYCLE        = 0x020000,
    VIEW_MOVEMENT       = 0x040000,
    VIEW_DRAW           = 0x080000,
    VIEW_WAS_UPDATED    = 0x100000,
    VIEW_LIGHT          = 0x200000
};

struct ObjView {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u8  filler1[8];
    /* 0x1C */ s32 unk1C; // set as nonexistent return of `setup_view_buffers`
    /* 0x20 */ s32 id;
    /* 0x24 */ struct ObjCamera *activeCam; // is this really active?
    /* 0x28 */ struct ObjGroup *components; // camera + joints + nets, etc..?
    /* 0x2C */ struct ObjGroup *lights;     // only lights?
    /* 0x30 */ struct GdObj *pickedObj; // selected with cursor (`update_view`)
    /* 0x34 */ enum GdViewFlags flags;
    /* 0x38 */ s32 projectionType; // enum? if 1 use guPerspective, if 0 (or 2?) use guOrtho (see `drawscene`)
    /* 0x3C */ struct GdVec3f upperLeft; // position vec?
    /* 0x48 */ f32 unk48; // what are these? are they another vec?
    /* 0x4C */ f32 unk4C;
    /* 0x50 */ u8  filler2[4];
    /* 0x54 */ struct GdVec3f lowerRight;
    /* 0x60 */ struct GdVec3f clipping; // z-coordinate of (x: near, y: far) clipping plane?
    /* 0x6C */ const char *namePtr;
    /* 0x70 */ s32 gdDlNum;
    /* 0x74 */ s32 unk74;
    /* 0x78 */ s32 unk78;
    /* 0x7C */ struct GdColour colour;
    /* 0x88 */ struct ObjView *parent; // maybe not a true parent, but link to buffers in parent?
    /* 0x8C */ void *zbuf;
    /* 0x90 */ void *colourBufs[2]; // frame buffers?
    /* 0x98 */ void (*proc)(struct ObjView *);   // Never non-null in game...?
    /* 0x9C */ s32 unk9C;
}; /* sizeof = 0xA0 */


typedef union ObjVarVal * (*valptrproc_t)(union ObjVarVal *, union ObjVarVal);

struct ObjLabel {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct GdVec3f position;
    /* 0x20 */ char *fmtstr;  // format string for displaying the value contained in `valptr`
    /* 0x24 */ s32 unk24;  // always 8
    /* 0x28 */ struct ObjValPtr *valptr;
    /* 0x2C */ valptrproc_t valfn;
    /* 0x30 */ s32 unk30;       // set to 3 or 4 in the code, but never actually used. could possibly be colourNum?
}; /* sizeof = 0x34 */

/* unk30 types:
 * 3 = f32? f32 pointer?
**/

struct ObjAnimator {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct ObjGroup* animatedPartsGrp;  // group containing objects animated by this animator. I think all of them are joints.
    /* 0x18 */ struct ObjGroup* animdataGrp;  //animation data? a group, but the link points to something weird..
    /* 0x1C */ u8  filler1[4];
    /* 0x20 */ s32 animSeqNum;  // which entry in the AnimDataInfo array to use
    /* 0x24 */ f32 unk24;
    /* 0x28 */ f32 frame;  // key frame number. This is a float so that it can interpolate between key frames, though I think in practice, it's always a whole number.
    /* 0x2C */ u8  filler2[4];
    /* 0x30 */ struct ObjGroup *attachedObjsGrp;
    /* 0x34 */ s32 attachFlags;   //attach arg0, not used
    /* 0x38 */ u8  filler3[12];
    /* 0x44 */ struct GdObj* attachedToObj;   // object that this object is attached to. Normally another Objanimator?
    /* 0x48 */ void (*controlFunc) (struct ObjAnimator *);  // function that "controls" the animation sequence by choosing the frame number
    /* 0x4C */ s32 state;   //state enum?
    /* 0x50 */ s32 nods;  // Counts the number of nods when Mario is dozing off. When this reaches zero, he wakes up again
    /* 0x54 */ s32 stillTimer;  // number of frames to remain in the part where Mario's head stays still with his eyes following the cursor
}; /* sizeof = 0x58 */

/* Animation Data Types */
enum GdAnimations {
    GD_ANIM_EMPTY                 = 0,  // Listed types are how the data are arranged in memory; maybe not be exact type
    GD_ANIM_MTX4x4                = 1,  // f32[4][4]
    GD_ANIM_SCALE3F_ROT3F_POS3F   = 2,  // f32[3][3]
    GD_ANIM_SCALE3S_POS3S_ROT3S   = 3,  // s16[9]
    GD_ANIM_SCALE3F_ROT3F_POS3F_2 = 4,  // f32[3][3]
    GD_ANIM_STUB                  = 5,
    GD_ANIM_ROT3S                 = 6,  // s16[3]
    GD_ANIM_POS3S                 = 7,  // s16[3]
    GD_ANIM_ROT3S_POS3S           = 8,  // s16[6]
    GD_ANIM_MTX4x4F_SCALE3F       = 9,  // {f32 mtx[4][4]; f32 vec[3];}
    GD_ANIM_CAMERA_EYE3S_LOOKAT3S = 11  // s16[6]
};
/* This struct is pointed to by the `obj` field in ListNode struct in the `animdata` ObjGroup */
struct AnimDataInfo {
    s32 count;  // count or -1 for end of array of AnimDataInfo structures
    enum GdAnimations type;  // types are used in "move_animator"
    void* data; // points to an array of `type` data
};
/* GD_ANIM_MTX4x4F_SCALE3F (9) type */
struct AnimMtxVec {
    /* 0x00 */ Mat4f matrix;
    /* 0x40 */ struct GdVec3f vec;  // seems to be a scale vec
};

enum ValPtrType {
    OBJ_VALUE_INT   = 1,
    OBJ_VALUE_FLOAT = 2
};

/**
 * An object that points to a value in memory (either a field of another object,
 * or a standalone variable). Used by `ObjLabel` and `ObjGadget` to manage their
 * values.
 */
struct ObjValPtr {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct GdObj *obj;   // maybe just a void *?
    /* 0x18 */ uintptr_t offset;  // value pointed to is `obj` + `offset`
    /* 0x1C */ enum ValPtrType datatype;
    /* 0x20 */ s32 flag;       // TODO: better name for this? If 0x40000, then `offset` is an offset to a field in `obj`. Otherwise, `obj` is NULL, and `offset` is the address of a variable. 
}; /* sizeof = 0x24 */

enum GdLightFlags {
    LIGHT_UNK02 = 0x02, // old type of light?
    LIGHT_NEW_UNCOUNTED = 0x10,
    LIGHT_UNK20 = 0x20 // new, actually used type of light? used for phong shading?
};

struct ObjLight {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u8  filler1[8];
    /* 0x1C */ s32 id;
    /* 0x20 */ char name[8];
    /* 0x28 */ u8  filler2[4];
    /* 0x2C */ s32 flags;
    /* 0x30 */ f32 unk30;       // color (5C) = Kd (50) * 30
    /* 0x34 */ u8  filler3[4];
    /* 0x38 */ f32 unk38; // calculated diffuse theta (in degrees?)
    /* 0x3C */ s32 unk3C;
    /* 0x40 */ s32 unk40;
    /* 0x44 */ u8  filler4[8];
    /* 0x4C */ s32 unk4C;
    /* 0x50 */ struct GdColour diffuse;
    /* 0x5C */ struct GdColour colour;
    /* 0x68 */ struct GdVec3f unk68;
    /* 0x74 */ struct GdVec3f position;
    /* 0x80 */ struct GdVec3f unk80;
    /* 0x8C */ struct GdVec3f unk8C;
    /* 0x98 */ s32 unk98;
    /* 0x9C */ struct ObjShape *unk9C;
}; /* sizeof = 0xA0 */

struct ObjZone {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ struct GdBoundingBox boundingBox;
    /* 0x2C */ struct ObjGroup *unk2C;   // plane group?
    /* 0x30 */ struct ObjGroup *unk30;   // guess based on Unknown801781DC; might have to change later
    /* 0x34 */ u8  filler[4];
}; /* sizeof = 0x38*/

struct ObjUnk200000 {
    /* 0x00 */ struct GdObj header;
    /* 0x14 */ u8  filler[28];
    /* 0x30 */ struct ObjVertex *unk30; //not sure; guessing for Unknown801781DC; 30 and 34 could switch with ObjZone
    /* 0x34 */ struct ObjFace *unk34;   //not sure; guessing for Unknown801781DC
}; /* sizeof = 0x38*/

#endif // GD_TYPES_H
