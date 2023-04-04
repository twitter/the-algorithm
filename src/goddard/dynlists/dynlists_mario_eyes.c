#include <PR/ultratypes.h>

#include "macros.h"
#include "dynlist_macros.h"
#include "dynlists.h"
#include "../dynlist_proc.h"

static s16 verts_mario_eye_right[][3] = {
    { 306, 26, 83 },   { 318, 18, 81 },   { 312, -13, 94 },  { 308, 43, 53 },   { 320, 35, 50 },
    { 308, 47, 12 },   { 320, 39, 9 },    { 316, 31, -30 },  { 304, 39, -27 },  { 311, 11, -58 },
    { 299, 19, -55 },  { 304, -21, -66 }, { 287, -46, -51 }, { 299, -54, -53 }, { 285, -64, -20 },
    { 297, -72, -23 }, { 286, -69, 20 },  { 299, -76, 17 },  { 301, -68, 57 },  { 289, -60, 60 },
    { 305, -47, 85 },  { 294, -40, 88 },  { 315, -35, 13 },  { 316, -33, 21 },  { 317, -28, 25 },
    { 293, -14, -64 }, { 316, -17, 67 },  { 312, -37, 61 },  { 310, -50, 44 },  { 307, -53, -14 },
    { 308, -42, -33 }, { 311, -22, -41 }, { 315, -2, -36 },  { 319, 10, -18 },  { 322, 13, 39 },
    { 320, 2, 59 },    { 318, -20, 29 },  { 308, -57, 15 },  { 315, -34, 4 },   { 315, -30, 0 },
    { 316, -22, -4 },  { 318, -14, -1 },  { 319, -9, 2 },    { 322, 16, 10 },   { 320, -8, 20 },
    { 319, -13, 24 },  { 320, -7, 11 },   { 301, -6, 96 },
};

static struct GdVtxData vtx_mario_eye_right = { ARRAY_COUNT(verts_mario_eye_right), 0x1, verts_mario_eye_right };

static u16 facedata_mario_eye_right[][4] = {
    { 1, 2, 1, 0 },    { 1, 1, 4, 3 },    { 1, 4, 6, 5 },    { 1, 6, 7, 5 },    { 1, 7, 9, 8 },
    { 1, 9, 11, 10 },  { 1, 11, 13, 12 }, { 1, 13, 15, 14 }, { 1, 15, 17, 16 }, { 1, 17, 18, 16 },
    { 1, 18, 20, 19 }, { 1, 20, 2, 21 },  { 1, 0, 47, 2 },   { 1, 2, 26, 1 },   { 1, 20, 26, 2 },
    { 1, 18, 27, 20 }, { 1, 17, 28, 18 }, { 1, 15, 29, 17 }, { 1, 13, 30, 15 }, { 1, 11, 31, 13 },
    { 1, 9, 31, 11 },  { 1, 7, 32, 9 },   { 1, 6, 33, 7 },   { 1, 4, 34, 6 },   { 1, 1, 35, 4 },
    { 1, 35, 34, 4 },  { 1, 34, 43, 6 },  { 1, 6, 43, 33 },  { 1, 7, 33, 32 },  { 1, 9, 32, 31 },
    { 1, 31, 30, 13 }, { 1, 30, 29, 15 }, { 1, 29, 37, 17 }, { 1, 17, 37, 28 }, { 1, 18, 28, 27 },
    { 1, 20, 27, 26 }, { 1, 26, 35, 1 },  { 1, 3, 0, 1 },    { 1, 5, 3, 4 },    { 1, 7, 8, 5 },
    { 1, 9, 10, 8 },   { 1, 11, 25, 10 }, { 1, 12, 25, 11 }, { 1, 14, 12, 13 }, { 1, 16, 14, 15 },
    { 1, 18, 19, 16 }, { 1, 2, 47, 21 },  { 1, 20, 21, 19 }, { 2, 26, 36, 35 }, { 2, 27, 36, 26 },
    { 2, 28, 24, 27 }, { 2, 37, 23, 28 }, { 2, 29, 38, 37 }, { 2, 30, 39, 29 }, { 2, 31, 40, 30 },
    { 2, 32, 40, 31 }, { 2, 33, 41, 32 }, { 2, 43, 42, 33 }, { 2, 34, 44, 43 }, { 2, 35, 45, 34 },
    { 2, 45, 44, 34 }, { 2, 44, 46, 43 }, { 2, 43, 46, 42 }, { 2, 33, 42, 41 }, { 2, 32, 41, 40 },
    { 2, 40, 39, 30 }, { 2, 39, 38, 29 }, { 2, 38, 22, 37 }, { 2, 37, 22, 23 }, { 2, 28, 23, 24 },
    { 2, 27, 24, 36 }, { 2, 36, 45, 35 }, { 3, 24, 23, 22 }, { 3, 22, 36, 24 }, { 3, 22, 38, 36 },
    { 3, 38, 39, 36 }, { 3, 39, 40, 36 }, { 3, 40, 41, 36 }, { 3, 41, 45, 36 }, { 3, 41, 42, 45 },
    { 3, 42, 46, 45 }, { 3, 46, 44, 45 },
};

static struct GdFaceData faces_mario_eye_right = { ARRAY_COUNT(facedata_mario_eye_right), 0x1, facedata_mario_eye_right };

struct DynList dynlist_mario_eye_right_shape[] = {
    BeginList(),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_RIGHT_EYE_VTX_GROUP),
        LinkWithPtr(&vtx_mario_eye_right),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_RIGHT_EYE_TRI_GROUP),
        LinkWithPtr(&faces_mario_eye_right),

    StartGroup(DYNOBJ_MARIO_RIGHT_EYE_MTL_GROUP),
        // ???
        MakeDynObj(D_MATERIAL, 0),
            SetId(0),
            SetAmbient(0.0, 0.291, 1.0),
            SetDiffuse(0.0, 0.291, 1.0),
        // Iris color
        MakeDynObj(D_MATERIAL, 0),
            SetId(1),
            SetAmbient(0.0, 0.576, 1.0),
            SetDiffuse(0.0, 0.576, 1.0),
        // Pupil color
        MakeDynObj(D_MATERIAL, 0),
            SetId(2),
            SetAmbient(0.0, 0.0, 0.0),
            SetDiffuse(0.0, 0.0, 0.0),
        // Color of spot in the middle of pupil
        MakeDynObj(D_MATERIAL, 0),
            SetId(3),
            SetAmbient(1.0, 1.0, 1.0),
            SetDiffuse(1.0, 1.0, 1.0),
    EndGroup(DYNOBJ_MARIO_RIGHT_EYE_MTL_GROUP),

    MakeDynObj(D_SHAPE, DYNOBJ_MARIO_RIGHT_EYE_SHAPE),
        SetNodeGroup(DYNOBJ_MARIO_RIGHT_EYE_VTX_GROUP),
        SetPlaneGroup(DYNOBJ_MARIO_RIGHT_EYE_TRI_GROUP),
        SetMaterialGroup(DYNOBJ_MARIO_RIGHT_EYE_MTL_GROUP),

    EndList(),
};

static s16 verts_mario_eye_left[][3] = {
    { 302, 35, -81 },  { 316, 28, -79 },  { 311, -2, -97 },  { 304, 48, -48 },  { 318, 40, -46 },
    { 302, 46, -7 },   { 316, 38, -5 },   { 311, 24, 32 },   { 297, 32, 30 },   { 305, 0, 56 },
    { 291, 7, 55 },    { 298, -34, 60 },  { 280, -57, 40 },  { 294, -64, 42 },  { 279, -70, 7 },
    { 294, -78, 9 },   { 282, -68, -33 }, { 296, -76, -31 }, { 300, -62, -69 }, { 286, -54, -71 },
    { 305, -37, -94 }, { 291, -29, -96 }, { 314, -35, -20 }, { 315, -32, -28 }, { 316, -27, -32 },
    { 285, -26, 58 },  { 316, -9, -71 },  { 312, -30, -69 }, { 310, -46, -53 }, { 305, -58, 3 },
    { 305, -49, 24 },  { 307, -30, 35 },  { 311, -10, 33 },  { 316, 4, 17 },    { 321, 16, -39 },
    { 319, 8, -60 },   { 318, -18, -34 }, { 307, -56, -25 }, { 313, -35, -11 }, { 314, -32, -6 },
    { 315, -25, 0 },   { 316, -17, -3 },  { 318, -11, -6 },  { 320, 14, -9 },   { 319, -8, -23 },
    { 319, -11, -28 }, { 319, -8, -14 },  { 297, 5, -99 },
};

static struct GdVtxData vtx_mario_eye_left = { ARRAY_COUNT(verts_mario_eye_left), 0x1, verts_mario_eye_left };

static u16 facedata_mario_eye_left[][4] = {
    { 1, 0, 1, 2 },    { 1, 3, 4, 1 },    { 1, 5, 6, 4 },    { 1, 5, 7, 6 },    { 1, 8, 9, 7 },
    { 1, 10, 11, 9 },  { 1, 12, 13, 11 }, { 1, 14, 15, 13 }, { 1, 16, 17, 15 }, { 1, 16, 18, 17 },
    { 1, 19, 20, 18 }, { 1, 21, 2, 20 },  { 1, 2, 47, 0 },   { 1, 1, 26, 2 },   { 1, 2, 26, 20 },
    { 1, 20, 27, 18 }, { 1, 18, 28, 17 }, { 1, 17, 29, 15 }, { 1, 15, 30, 13 }, { 1, 13, 31, 11 },
    { 1, 11, 31, 9 },  { 1, 9, 32, 7 },   { 1, 7, 33, 6 },   { 1, 6, 34, 4 },   { 1, 4, 35, 1 },
    { 1, 4, 34, 35 },  { 1, 6, 43, 34 },  { 1, 33, 43, 6 },  { 1, 32, 33, 7 },  { 1, 31, 32, 9 },
    { 1, 13, 30, 31 }, { 1, 15, 29, 30 }, { 1, 17, 37, 29 }, { 1, 28, 37, 17 }, { 1, 27, 28, 18 },
    { 1, 26, 27, 20 }, { 1, 1, 35, 26 },  { 1, 1, 0, 3 },    { 1, 4, 3, 5 },    { 1, 5, 8, 7 },
    { 1, 8, 10, 9 },   { 1, 10, 25, 11 }, { 1, 11, 25, 12 }, { 1, 13, 12, 14 }, { 1, 15, 14, 16 },
    { 1, 16, 19, 18 }, { 1, 21, 47, 2 },  { 1, 19, 21, 20 }, { 2, 35, 36, 26 }, { 2, 26, 36, 27 },
    { 2, 27, 24, 28 }, { 2, 28, 23, 37 }, { 2, 37, 38, 29 }, { 2, 29, 39, 30 }, { 2, 30, 40, 31 },
    { 2, 31, 40, 32 }, { 2, 32, 41, 33 }, { 2, 33, 42, 43 }, { 2, 43, 44, 34 }, { 2, 34, 45, 35 },
    { 2, 34, 44, 45 }, { 2, 43, 46, 44 }, { 2, 42, 46, 43 }, { 2, 41, 42, 33 }, { 2, 40, 41, 32 },
    { 2, 30, 39, 40 }, { 2, 29, 38, 39 }, { 2, 37, 22, 38 }, { 2, 23, 22, 37 }, { 2, 24, 23, 28 },
    { 2, 36, 24, 27 }, { 2, 35, 45, 36 }, { 3, 22, 23, 24 }, { 3, 24, 36, 22 }, { 3, 36, 38, 22 },
    { 3, 36, 39, 38 }, { 3, 36, 40, 39 }, { 3, 36, 41, 40 }, { 3, 36, 45, 41 }, { 3, 45, 42, 41 },
    { 3, 45, 46, 42 }, { 3, 45, 44, 46 },
};

static struct GdFaceData faces_mario_eye_left = { ARRAY_COUNT(facedata_mario_eye_left), 0x1, facedata_mario_eye_left };

struct DynList dynlist_mario_eye_left_shape[28] = {
    BeginList(),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_LEFT_EYE_VTX_GROUP),
        LinkWithPtr(&vtx_mario_eye_left),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_LEFT_EYE_TRI_GROUP),
        LinkWithPtr(&faces_mario_eye_left),

    StartGroup(DYNOBJ_MARIO_LEFT_EYE_MTL_GROUP),
        // ???
        MakeDynObj(D_MATERIAL, 0),
            SetId(0),
            SetAmbient(0.0, 0.291, 1.0),
            SetDiffuse(0.0, 0.291, 1.0),
        // Iris color
        MakeDynObj(D_MATERIAL, 0),
            SetId(1),
            SetAmbient(0.0, 0.576, 1.0),
            SetDiffuse(0.0, 0.576, 1.0),
        // Pupil color
        MakeDynObj(D_MATERIAL, 0),
            SetId(2),
            SetAmbient(0.0, 0.0, 0.0),
            SetDiffuse(0.0, 0.0, 0.0),
        // Color of spot in the middle of pupil
        MakeDynObj(D_MATERIAL, 0),
            SetId(3),
            SetAmbient(1.0, 1.0, 1.0),
            SetDiffuse(1.0, 1.0, 1.0),
    EndGroup(DYNOBJ_MARIO_LEFT_EYE_MTL_GROUP),

    MakeDynObj(D_SHAPE, DYNOBJ_MARIO_LEFT_EYE_SHAPE),
        SetNodeGroup(DYNOBJ_MARIO_LEFT_EYE_VTX_GROUP),
        SetPlaneGroup(DYNOBJ_MARIO_LEFT_EYE_TRI_GROUP),
        SetMaterialGroup(DYNOBJ_MARIO_LEFT_EYE_MTL_GROUP),

    EndList(),
};
