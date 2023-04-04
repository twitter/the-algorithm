#include <PR/ultratypes.h>

#include "macros.h"
#include "dynlist_macros.h"
#include "dynlists.h"
#include "../dynlist_proc.h"

static s16 verts_mario_eyebrow_right[][3] = {
    { 105, 391, 363 }, { 62, 370, 378 },  { 86, 366, 370 },  { 171, 380, 339 }, { 139, 377, 353 },
    { 193, 353, 331 }, { 204, 314, 297 }, { 175, 342, 305 }, { 209, 361, 291 }, { 124, 360, 323 },
    { 161, 393, 306 }, { 230, 323, 286 }, { 233, 276, 289 }, { 123, 402, 319 }, { 85, 399, 334 },
    { 73, 355, 342 },  { 45, 330, 354 },  { 17, 294, 356 },  { 29, 348, 361 },  { 212, 358, 323 },
    { 214, 324, 318 }, { 139, 394, 353 }, { 57, 339, 377 },  { 231, 330, 311 }, { 58, 384, 349 },
    { 41, 345, 382 },
};

static struct GdVtxData vtx_mario_eyebrow_right = { ARRAY_COUNT(verts_mario_eyebrow_right), 0x1, verts_mario_eyebrow_right };

static u16 facedata_mario_eyebrow_right[][4] = {
    { 0, 0, 1, 2 },    { 0, 3, 4, 5 },    { 0, 20, 23, 19 }, { 0, 1, 25, 22 }, { 0, 0, 21, 13 },
    { 0, 2, 15, 9 },   { 0, 7, 6, 20 },   { 0, 3, 19, 8 },   { 0, 25, 1, 24 }, { 0, 19, 5, 20 },
    { 0, 19, 3, 5 },   { 0, 3, 21, 4 },   { 0, 21, 0, 4 },   { 0, 0, 2, 4 },   { 0, 1, 22, 2 },
    { 0, 20, 12, 23 }, { 0, 13, 14, 0 },  { 0, 4, 2, 9 },    { 0, 20, 5, 7 },  { 0, 22, 17, 16 },
    { 0, 8, 10, 3 },   { 0, 24, 18, 25 }, { 0, 23, 11, 8 },  { 0, 14, 24, 1 }, { 0, 5, 4, 9 },
    { 0, 2, 22, 15 },  { 0, 11, 23, 12 }, { 0, 25, 18, 17 }, { 0, 20, 6, 12 }, { 0, 10, 13, 21 },
    { 0, 8, 19, 23 },  { 0, 25, 17, 22 }, { 0, 1, 0, 14 },   { 0, 21, 3, 10 }, { 0, 22, 16, 15 },
    { 0, 9, 7, 5 },
};

static struct GdFaceData faces_mario_eyebrow_right = { ARRAY_COUNT(facedata_mario_eyebrow_right), 0x1, facedata_mario_eyebrow_right };

struct DynList dynlist_mario_eyebrow_right_shape[] = {
    BeginList(),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_RIGHT_EYEBROW_VTX_GROUP),
        LinkWithPtr(&vtx_mario_eyebrow_right),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_RIGHT_EYEBROW_TRI_GROUP),
        LinkWithPtr(&faces_mario_eyebrow_right),

    StartGroup(DYNOBJ_MARIO_RIGHT_EYEBROW_MTL_GROUP),
        MakeDynObj(D_MATERIAL, 0),
            SetId(0),
            SetAmbient(0.0, 0.005, 0.0),  // Why is green 0.005 on the right eyebrow, but 0.0 on the left eyebrow?
            SetDiffuse(0.0, 0.0, 0.0),
    EndGroup(DYNOBJ_MARIO_RIGHT_EYEBROW_MTL_GROUP),

    MakeDynObj(D_SHAPE, DYNOBJ_MARIO_RIGHT_EYEBROW_SHAPE),
        SetNodeGroup(DYNOBJ_MARIO_RIGHT_EYEBROW_VTX_GROUP),
        SetPlaneGroup(DYNOBJ_MARIO_RIGHT_EYEBROW_TRI_GROUP),
        SetMaterialGroup(DYNOBJ_MARIO_RIGHT_EYEBROW_MTL_GROUP),

    EndList(),
};

static s16 verts_mario_eyebrow_left[][3] = {
    { -57, 339, 377 },  { -17, 294, 356 },  { -45, 341, 383 },  { -45, 330, 354 },  { -73, 355, 342 },
    { -52, 377, 349 },  { -139, 394, 353 }, { -123, 402, 319 }, { -161, 393, 306 }, { -233, 276, 289 },
    { -204, 314, 297 }, { -214, 324, 318 }, { -29, 335, 363 },  { -231, 330, 311 }, { -230, 323, 286 },
    { -86, 366, 370 },  { -124, 360, 323 }, { -139, 377, 353 }, { -193, 353, 331 }, { -62, 370, 378 },
    { -85, 399, 334 },  { -209, 361, 291 }, { -171, 380, 339 }, { -175, 342, 305 }, { -105, 391, 363 },
    { -212, 358, 323 },
};

static struct GdVtxData vtx_mario_eyebrow_left = { ARRAY_COUNT(verts_mario_eyebrow_left), 0x1, verts_mario_eyebrow_left };

static u16 facedata_mario_eyebrow_left[][4] = {
    { 0, 0, 1, 2 },    { 0, 8, 22, 6 },   { 0, 6, 7, 8 },    { 0, 9, 10, 11 },  { 0, 1, 12, 2 },
    { 0, 9, 13, 14 },  { 0, 4, 0, 15 },   { 0, 16, 17, 18 }, { 0, 19, 5, 20 },  { 0, 21, 14, 13 },
    { 0, 2, 12, 5 },   { 0, 22, 8, 21 },  { 0, 3, 1, 0 },    { 0, 23, 18, 11 }, { 0, 16, 15, 17 },
    { 0, 24, 20, 7 },  { 0, 13, 9, 11 },  { 0, 15, 0, 19 },  { 0, 17, 15, 24 }, { 0, 17, 24, 6 },
    { 0, 17, 6, 22 },  { 0, 18, 22, 25 }, { 0, 11, 18, 25 }, { 0, 4, 3, 0 },    { 0, 18, 23, 16 },
    { 0, 20, 24, 19 }, { 0, 13, 25, 21 }, { 0, 5, 19, 2 },   { 0, 21, 25, 22 }, { 0, 11, 10, 23 },
    { 0, 18, 17, 22 }, { 0, 15, 19, 24 }, { 0, 16, 4, 15 },  { 0, 7, 6, 24 },   { 0, 0, 2, 19 },
    { 0, 25, 13, 11 },
};

static struct GdFaceData faces_mario_eyebrow_left = { ARRAY_COUNT(facedata_mario_eyebrow_left), 0x1, facedata_mario_eyebrow_left };

struct DynList dynlist_mario_eyebrow_left_shape[] = {
    BeginList(),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_LEFT_EYEBROW_VTX_GROUP),
        LinkWithPtr(&vtx_mario_eyebrow_left),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_LEFT_EYEBROW_TRI_GROUP),
        LinkWithPtr(&faces_mario_eyebrow_left),

    StartGroup(DYNOBJ_MARIO_LEFT_EYEBROW_MTL_GROUP),
        MakeDynObj(D_MATERIAL, 0),
            SetId(0),
            SetAmbient(0.0, 0.0, 0.0),
            SetDiffuse(0.0, 0.0, 0.0),
    EndGroup(DYNOBJ_MARIO_LEFT_EYEBROW_MTL_GROUP),

    MakeDynObj(D_SHAPE, DYNOBJ_MARIO_LEFT_EYEBROW_SHAPE),
        SetNodeGroup(DYNOBJ_MARIO_LEFT_EYEBROW_VTX_GROUP),
        SetPlaneGroup(DYNOBJ_MARIO_LEFT_EYEBROW_TRI_GROUP),
        SetMaterialGroup(DYNOBJ_MARIO_LEFT_EYEBROW_MTL_GROUP),

    EndList(),
};

static s16 verts_mario_mustache[][3] = {
    { -202, 15, 400 },   { -295, -13, 358 },  { -287, -45, 362 },  { 229, -89, 385 },
    { 214, -126, 385 },  { 221, -131, 360 },  { -266, 73, 363 },   { -202, 15, 375 },
    { -154, -160, 372 }, { -154, -148, 397 }, { -191, -150, 387 }, { 276, -74, 345 },
    { 287, -45, 362 },   { 276, -74, 370 },   { -221, -131, 360 }, { -214, -126, 385 },
    { -229, -89, 385 },  { -298, -45, 337 },  { 293, 20, 357 },    { 295, -13, 358 },
    { 307, -13, 333 },   { 202, 15, 400 },    { 266, 73, 363 },    { 202, 15, 375 },
    { -95, -25, 457 },   { 95, -25, 457 },    { -95, -25, 406 },   { 95, -25, 406 },
    { 154, -148, 397 },  { 110, -178, 416 },  { 121, -188, 384 },  { 266, 88, 338 },
    { -293, 20, 357 },   { -266, 88, 338 },   { -276, -74, 370 },  { -239, -95, 359 },
    { 197, -155, 362 },  { 191, -150, 387 },  { -68, -181, 427 },  { -110, -178, 416 },
    { -75, -191, 396 },  { -4, -157, 406 },   { 4, -157, 406 },    { -4, -157, 444 },
    { -197, -155, 362 }, { 154, -160, 372 },  { 239, -95, 359 },   { 298, -45, 337 },
    { -307, -13, 333 },  { -276, -74, 345 },  { 68, -181, 427 },   { 4, -157, 444 },
    { -121, -188, 384 }, { 75, -191, 396 },   { 304, 20, 332 },    { -304, 20, 332 },
};

static struct GdVtxData vtx_mario_mustache = { ARRAY_COUNT(verts_mario_mustache), 0x1, verts_mario_mustache };

static u16 facedata_mario_mustache[][4] = {
    { 0, 0, 1, 2 },    { 0, 3, 4, 5 },    { 0, 3, 5, 46 },   { 0, 6, 0, 7 },    { 0, 6, 7, 33 },
    { 0, 8, 9, 10 },   { 0, 8, 10, 44 },  { 0, 11, 47, 12 }, { 0, 11, 12, 13 }, { 0, 14, 15, 16 },
    { 0, 14, 16, 35 }, { 0, 2, 1, 48 },   { 0, 2, 48, 17 },  { 0, 18, 19, 20 }, { 0, 18, 20, 54 },
    { 0, 21, 22, 31 }, { 0, 21, 31, 23 }, { 0, 24, 25, 27 }, { 0, 24, 27, 26 }, { 0, 27, 25, 21 },
    { 0, 27, 21, 23 }, { 0, 0, 24, 26 },  { 0, 0, 26, 7 },   { 0, 28, 29, 30 }, { 0, 28, 30, 45 },
    { 0, 31, 22, 18 }, { 0, 31, 18, 54 }, { 0, 32, 6, 33 },  { 0, 32, 33, 55 }, { 0, 16, 34, 49 },
    { 0, 16, 49, 35 }, { 0, 36, 5, 4 },   { 0, 36, 4, 37 },  { 0, 38, 39, 52 }, { 0, 38, 52, 40 },
    { 0, 41, 42, 51 }, { 0, 41, 51, 43 }, { 0, 10, 15, 14 }, { 0, 10, 14, 44 }, { 0, 37, 28, 45 },
    { 0, 37, 45, 36 }, { 0, 35, 7, 26 },  { 0, 13, 3, 46 },  { 0, 13, 46, 11 }, { 0, 19, 12, 47 },
    { 0, 19, 47, 20 }, { 0, 1, 32, 55 },  { 0, 1, 55, 48 },  { 0, 34, 2, 17 },  { 0, 34, 17, 49 },
    { 0, 43, 38, 40 }, { 0, 43, 40, 41 }, { 0, 42, 53, 50 }, { 0, 42, 50, 51 }, { 0, 39, 9, 8 },
    { 0, 39, 8, 52 },  { 0, 29, 50, 53 }, { 0, 29, 53, 30 }, { 0, 51, 50, 25 }, { 0, 43, 51, 25 },
    { 0, 43, 25, 24 }, { 0, 29, 28, 25 }, { 0, 3, 13, 12 },  { 0, 3, 12, 21 },  { 0, 3, 21, 25 },
    { 0, 50, 29, 25 }, { 0, 25, 37, 4 },  { 0, 25, 28, 37 }, { 0, 25, 4, 3 },   { 0, 9, 39, 24 },
    { 0, 16, 15, 24 }, { 0, 39, 38, 24 }, { 0, 38, 43, 24 }, { 0, 10, 9, 24 },  { 0, 24, 0, 16 },
    { 0, 15, 10, 24 }, { 0, 8, 44, 14 },  { 0, 18, 22, 21 }, { 0, 19, 18, 21 }, { 0, 12, 19, 21 },
    { 0, 0, 6, 32 },   { 0, 0, 32, 1 },   { 0, 2, 34, 16 },  { 0, 2, 16, 0 },   { 0, 23, 11, 46 },
    { 0, 8, 14, 35 },  { 0, 23, 31, 54 }, { 0, 46, 27, 23 }, { 0, 8, 35, 26 },  { 0, 27, 46, 45 },
    { 0, 54, 20, 47 }, { 0, 54, 47, 11 }, { 0, 55, 33, 7 },  { 0, 7, 49, 55 },  { 0, 49, 17, 55 },
    { 0, 17, 48, 55 }, { 0, 35, 49, 7 },  { 0, 54, 11, 23 }, { 0, 46, 5, 45 },  { 0, 5, 36, 45 },
};

static struct GdFaceData faces_mario_mustache = { ARRAY_COUNT(facedata_mario_mustache), 0x1, facedata_mario_mustache };

struct DynList dynlist_mario_mustache_shape[] = {
    BeginList(),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_MUSTACHE_VTX_GROUP),
        LinkWithPtr(&vtx_mario_mustache),

    MakeDynObj(D_DATA_GRP, DYNOBJ_MARIO_MUSTACHE_TRI_GROUP),
        LinkWithPtr(&faces_mario_mustache),

    StartGroup(DYNOBJ_MARIO_MUSTACHE_MTL_GROUP),
        MakeDynObj(D_MATERIAL, 0),
            SetId(0),
            SetAmbient(0.0, 0.0, 0.0),
            SetDiffuse(0.0, 0.0, 0.0),
    EndGroup(DYNOBJ_MARIO_MUSTACHE_MTL_GROUP),

    MakeDynObj(D_SHAPE, DYNOBJ_MARIO_MUSTACHE_SHAPE),
        SetNodeGroup(DYNOBJ_MARIO_MUSTACHE_VTX_GROUP),
        SetPlaneGroup(DYNOBJ_MARIO_MUSTACHE_TRI_GROUP),
        SetMaterialGroup(DYNOBJ_MARIO_MUSTACHE_MTL_GROUP),

    EndList(),
};
