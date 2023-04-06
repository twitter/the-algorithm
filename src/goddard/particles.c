#include <ultra64.h>
#include <macros.h>
#include "gd_types.h"
#include "draw_objects.h"
#include "objects.h"
#include "particles.h"
#include "dynlist_proc.h"
#include "debug_utils.h"
#include "skin.h"
#include "gd_math.h"
#include "renderer.h"

// static types
typedef union {
    struct ObjVertex *vtx;
    struct ObjParticle *ptc;
} VtxPtc;

struct Connection {
    u8 filler0[0x1C];
    VtxPtc unk1C;
    VtxPtc unk20;
    f32 unk24;
    u32 unk28; // union tag? 0 = vertex; 1 = particle?
};

// data
static void *sUnused801A81D0 = NULL;
static s32 D_801A81D4[25] = {
    /*  ID?    X    Y    Z */
    9,  3,  12,  -14, 25, 5,  16,  -25, 42, 4,  15, -39, 55,
    -6, 20, -23, 70,  -2, 20, -23, 135, 0,  16, 0,  0 /* Terminator */
};
static s32 D_801A8238[5] = {
    /*  ID? X   Y  Z */
    15, 0, 22, 0, 0 /* Terminator */
};

// static bss
static struct ObjFace *D_801B9EF0;

// fn declarations
struct Connection *func_801825FC(struct ObjVertex *, struct ObjVertex *);
void func_80181C00(struct ObjVertex *, struct ObjVertex *);
void Unknown80181D14(struct ObjFace *);
void func_80181EB0(struct Connection *);
void func_80182088(struct Connection *);
void move_particle(struct ObjParticle *);
struct Connection *func_801825FC(struct ObjVertex *, struct ObjVertex *);
int func_80182778(struct ObjParticle *);
void func_80182A08(struct ObjParticle *, struct GdVec3f *b);
void func_801838D0(struct ObjParticle *);
void Unknown801835C8(struct ObjParticle *ptc);

/* 2303D0 -> 2304E4 */
// rename to "connect_in_skinnet_if_needed"?
void func_80181C00(struct ObjVertex *vtx1, struct ObjVertex *vtx2) {
    struct GdObj *sp2C;
    register struct Links *link;

    if (vtx1 == vtx2) {
        return;
    }
    link = gGdSkinNet->unk1C0->link1C;
    while (link != NULL) {
        // FIXME: types
        struct Connection *sp24 = (void *) link->obj;

        if ((sp24->unk1C.vtx == vtx1 || sp24->unk1C.vtx == vtx2)
            && (sp24->unk20.vtx == vtx1 || sp24->unk20.vtx == vtx2)) {
            break;
        }
        link = link->next;
    }
    if (link == NULL) {
        // FIXME: types
        sp2C = (void *) func_801825FC(vtx1, vtx2);
        addto_group(gGdSkinNet->unk1C0, sp2C);
    }
}

/* 2304E4 -> 230680 */
void Unknown80181D14(struct ObjFace *face) {
    s32 i;
    s32 j;
    struct ObjVertex *vtx1;
    struct ObjVertex *vtx2;

    for (i = 0; i < face->vtxCount - 1; i++) {
        vtx1 = face->vertices[i];
        vtx2 = face->vertices[i + 1];
        func_80181C00(vtx1, vtx2);
    }
    if (D_801B9EF0 != NULL) {
        for (i = 0; i < face->vtxCount; i++) {
            vtx1 = face->vertices[i];
            for (j = 0; j < D_801B9EF0->vtxCount; j++) {
                vtx2 = D_801B9EF0->vertices[j];
                func_80181C00(vtx1, vtx2);
            }
        }
    }
    D_801B9EF0 = face;
}

/* 230680 -> 230858 */
void func_80181EB0(struct Connection *cxn) {
    struct GdVec3f sp34;
    UNUSED u8 unused[0x2C];
    struct ObjParticle *sp4 = cxn->unk1C.ptc;
    struct ObjParticle *sp0 = cxn->unk20.ptc;

    sp34.x = 0.0f;
    sp34.y = sp4->unk20.y - sp0->unk20.y;
    sp34.z = 0.0f;
    sp34.y *= 0.01;
    sp4->unk38.x -= sp34.x;
    sp4->unk38.y -= sp34.y;
    sp4->unk38.z -= sp34.z;
    sp0->unk38.x += sp34.x;
    sp0->unk38.y += sp34.y;
    sp0->unk38.z += sp34.z;
    if (!(sp4->unk54 & 2)) {
        sp4->unk20.x -= sp34.x;
        sp4->unk20.y -= sp34.y;
        sp4->unk20.z -= sp34.z;
    }
    if (!(sp0->unk54 & 2)) {
        sp0->unk20.x += sp34.x;
        sp0->unk20.y += sp34.y;
        sp0->unk20.z += sp34.z;
    }
}

/* @ 230858 -> 230B70 */
void func_80182088(struct Connection *cxn) {
    struct GdVec3f sp4C;
    UNUSED u8 unused[0x24];
    f32 sp24;
    f32 sp20;
    struct ObjParticle *sp1C;
    struct ObjParticle *sp18;

    if (cxn->unk28 & 1) {
        func_80181EB0(cxn);
        return;
    }
    sp1C = cxn->unk1C.ptc;
    sp18 = cxn->unk20.ptc;
    sp4C.x = sp1C->unk20.x - sp18->unk20.x;
    sp4C.y = sp1C->unk20.y - sp18->unk20.y;
    sp4C.z = sp1C->unk20.z - sp18->unk20.z;
    sp20 = gd_vec3f_magnitude(&sp4C);
    sp24 = sp20 - cxn->unk24;
    sp4C.x /= sp20;
    sp4C.y /= sp20;
    sp4C.z /= sp20;
    sp4C.x *= sp24 * 0.1;
    sp4C.y *= sp24 * 0.1;
    sp4C.z *= sp24 * 0.1;
    sp1C->unk38.x -= sp4C.x;
    sp1C->unk38.y -= sp4C.y;
    sp1C->unk38.z -= sp4C.z;
    sp18->unk38.x += sp4C.x;
    sp18->unk38.y += sp4C.y;
    sp18->unk38.z += sp4C.z;
    if (!(sp1C->unk54 & 2)) {
        sp1C->unk20.x -= sp4C.x;
        sp1C->unk20.y -= sp4C.y;
        sp1C->unk20.z -= sp4C.z;
    }
    if (!(sp18->unk54 & 2)) {
        sp18->unk20.x += sp4C.x;
        sp18->unk20.y += sp4C.y;
        sp18->unk20.z += sp4C.z;
    }
}

/* 230B70 -> 230CC0 */
void func_801823A0(struct ObjNet *net) {
    register struct Links *link;
    struct Connection *cxn;

    gGdSkinNet = net;
    switch (net->unk3C) {
        case 1: // Shape; Are these flags the same as net->netType (+0x1EC)?
            net->unk1C8 = ((struct ObjShape *) net->unk1A8)->vtxGroup;
            net->unk1C0 = make_group(0);
            D_801B9EF0 = NULL;

            apply_to_obj_types_in_group(OBJ_TYPE_FACES, (applyproc_t) Unknown80181D14,
                                        ((struct ObjShape *) net->unk1A8)->faceGroup);
            net->unk3C = 2;
            break;
        case 2:
            link = net->unk1C0->link1C;
            while (link != NULL) {
                // FIXME: types
                cxn = (struct Connection *) link->obj;
                func_80182088(cxn);
                link = link->next;
            }
            apply_to_obj_types_in_group(OBJ_TYPE_PARTICLES, (applyproc_t) move_particle, net->unk1C8);
            apply_to_obj_types_in_group(OBJ_TYPE_PLANES, (applyproc_t) reset_plane, net->unk1CC);
            break;
    }
}

/* 230CC0 -> 230DCC */
struct ObjParticle *make_particle(u32 a, s32 b, f32 x, f32 y, f32 z) {
    struct ObjParticle *sp2C = (struct ObjParticle *) make_object(OBJ_TYPE_PARTICLES);
    UNUSED u8 unused[8];

    sp2C->unk20.x = x;
    sp2C->unk20.y = y;
    sp2C->unk20.z = z;
    sp2C->unk38.x = sp2C->unk38.y = sp2C->unk38.z = 0.0f;
    sp2C->unk58 = b;
    sp2C->unk54 = a | 8;
    sp2C->unk5C = -1;
    sp2C->id = D_801B9E40; /* should this be D_801B9E40++? */
    sp2C->unk1C = 0;
    sp2C->unkB0 = 1;
    return sp2C;
}

/* 230DCC -> 230F48 */
struct Connection *func_801825FC(struct ObjVertex *vtx1, struct ObjVertex *vtx2) {
    struct Connection *sp34 = gd_malloc_perm(44);
    struct GdVec3f sp28;
    struct GdVec3f sp1C;

    if (sp34 == NULL) {
        fatal_print("Cant allocate connection memory!");
    }
    sp34->unk1C.vtx = vtx1;
    sp34->unk20.vtx = vtx2;
    push_dynobj_stash();
    set_cur_dynobj(vtx1);
    d_get_world_pos(&sp28);
    set_cur_dynobj(vtx2);
    d_get_world_pos(&sp1C);
    sp28.x -= sp1C.x;
    sp28.y -= sp1C.y;
    sp28.z -= sp1C.z;
    sp34->unk24 = gd_vec3f_magnitude(&sp28);
    // Duplicate conditional. Possibly should've checked `vtx2`;
    // Also, this shouldn't be called with particle types...
    if (vtx1->header.type == OBJ_TYPE_PARTICLES && vtx1->header.type == OBJ_TYPE_PARTICLES) {
        if ((((struct ObjParticle *) vtx1)->unk54 & 4) && (((struct ObjParticle *) vtx2)->unk54 & 4)) {
            sp34->unk28 |= 1;
        }
    }
    pop_dynobj_stash();
    return sp34;
}

/* 230F48 -> 2311D8 */
int func_80182778(struct ObjParticle *ptc) {
    s32 sp4 = 0;

    if (ptc->unk7C->unk20 == 2 && ptc->unk74 == 1) {
        while (D_801A81D4[sp4] != 0) {
            if (D_801A81D4[sp4] == ptc->unk7C->unk28) {
                ptc->unk20.x = D_801A81D4[sp4 + 1] * 10.0f;
                ptc->unk20.y = D_801A81D4[sp4 + 2] * 10.0f;
                ptc->unk20.z = D_801A81D4[sp4 + 3] * 10.0f;
                return TRUE;
            }
            sp4 += 4;
        }
    }
    if (ptc->unk7C->unk20 == 1 && ptc->unk74 == 1) {
        while (D_801A8238[sp4] != 0) {
            if (D_801A8238[sp4] == ptc->unk7C->unk28) {
                ptc->unk20.x = D_801A8238[sp4 + 1] * 10.0f;
                ptc->unk20.y = D_801A8238[sp4 + 2] * 10.0f;
                ptc->unk20.z = D_801A8238[sp4 + 3] * 10.0f;
                return TRUE;
            }
            sp4 += 4;
        }
    }
    return FALSE;
}

/* 2311D8 -> 231454 */
void func_80182A08(struct ObjParticle *ptc, struct GdVec3f *b) {
    register struct Links *link;
    struct ObjParticle *sp20;

    if (ptc->unk6C != NULL) {
        link = ptc->unk6C->link1C;
        while (link != NULL) {
            // FIXME: types
            sp20 = (struct ObjParticle *) link->obj;
            if (sp20->unk5C <= 0) {
                sp20->unk20.x = ptc->unk20.x;
                sp20->unk20.y = ptc->unk20.y;
                sp20->unk20.z = ptc->unk20.z;
                sp20->unk5C = 12.0f - func_8018D560() * 5.0f;
                do {
                    sp20->unk38.x = func_8018D560() * 50.0 - 25.0;
                    sp20->unk38.y = func_8018D560() * 50.0 - 25.0;
                    sp20->unk38.z = func_8018D560() * 50.0 - 25.0;
                } while (gd_vec3f_magnitude(&sp20->unk38) > 30.0);
                sp20->unk38.x += b->x;
                sp20->unk38.y += b->y;
                sp20->unk38.z += b->z;
                sp20->header.drawFlags &= ~OBJ_NOT_DRAWABLE;
                sp20->unk54 |= 8;
            }
            link = link->next;
        }
    }
}

/* 231454 -> 231D40; orig name: Unknown80182C84 */
void move_particle(struct ObjParticle *ptc) {
    f32 sp7C;
    UNUSED u8 unused2[12];
    struct GdVec3f sp64;
    struct ObjParticle *sp60;
    UNUSED u8 unused1[4];
    s32 sp58;
    UNUSED u8 unused4[4];
    UNUSED u8 unused5[4];
    struct ObjCamera *sp4C;
    struct GdVec3f sp40;
    struct GdVec3f sp34;

    if (ptc->unk54 & 2) {
        return;
    }
    if (!(ptc->unk54 & 8)) {
        return;
    }
    if (ptc->unk60 == 3) {
        sp40.x = -gViewUpdateCamera->unkE8[2][0] * 50.0f;
        sp40.y = -gViewUpdateCamera->unkE8[2][1] * 50.0f;
        sp40.z = gViewUpdateCamera->unkE8[2][2] * 50.0f;
        sp34.x = gViewUpdateCamera->unkE8[2][0] * -20.0f;
        sp34.y = gViewUpdateCamera->unkE8[2][1] * -20.0f;
        sp34.z = gViewUpdateCamera->unkE8[2][2] * -20.0f;
    }
    if (ptc->unkBC != NULL) {
        set_cur_dynobj(ptc->unkBC);
        if (ptc->unk60 == 3) {
            if (ptc->unk64 == 3) {
                sp4C = (struct ObjCamera *) ptc->unkBC;
                // Camera->unk18C = ObjView here
                if (sp4C->unk18C->pickedObj != NULL) {
                    set_cur_dynobj(sp4C->unk18C->pickedObj);
                    ptc->unk54 |= 0x20;
                    ; // needed to match
                } else {
                    ptc->unk54 &= ~0x10;
                    ptc->unk54 &= ~0x20;
                }
            }
        }
        d_get_world_pos(&sp64);
        ptc->unk20.x = sp64.x;
        ptc->unk20.y = sp64.y;
        ptc->unk20.z = sp64.z;
    }
    sp7C = -0.4f;
    ptc->unk20.x += ptc->unk38.x;
    ptc->unk20.y += ptc->unk38.y;
    ptc->unk20.z += ptc->unk38.z;
    if (ptc->unk54 & 1) {
        ptc->unk38.y += sp7C;
    }
    func_801838D0(ptc);
    if (ptc->unkB0 == 1) {
        if (0) {
        }
        ptc->unkB0 = 2;
        if (ptc->unk60 == 3) {
            switch (ptc->unk64) {
                case 1:
                    ptc->unk6C = make_group(0);
                    for (sp58 = 0; sp58 < 50; sp58++) {
                        sp60 = make_particle(1, -1, ptc->unk20.x, ptc->unk20.y, ptc->unk20.z);
                        sp60->unk1C = ptc->unk1C;
                        addto_group(ptc->unk6C, &sp60->header);
                        sp60->unk54 &= ~8;
                    }
                    break;
                case 2:
                case 3:
                    ptc->unk6C = make_group(0);
                    for (sp58 = 0; sp58 < 30; sp58++) {
                        sp60 = make_particle(1, -1, ptc->unk20.x, ptc->unk20.y, ptc->unk20.z);
                        sp60->unk1C = (void *) ptc->unk1C;
                        addto_group(ptc->unk6C, &sp60->header);
                        sp60->unk54 &= ~8;
                    }
                    break;
            }
        }
    } else if (0) {
    }
    ptc->unk38.x *= 0.9;
    ptc->unk38.y *= 0.9;
    ptc->unk38.z *= 0.9;
    if (ptc->unk60 == 3) {
        switch (ptc->unk64) {
            case 1:
                if (func_80182778(ptc) && ptc->unk6C != NULL) {
                    register struct Links *link;

                    if (ptc->unk80 != NULL) {
                        ptc->unk80->unk3C |= 1;
                        ptc->unk80->position.x = ptc->unk20.x;
                        ptc->unk80->position.y = ptc->unk20.y;
                        ptc->unk80->position.z = ptc->unk20.z;
                    }
                    link = ptc->unk6C->link1C;
                    while (link != NULL) {
                        struct ObjParticle *sp2C = (struct ObjParticle *) link->obj;

                        sp2C->unk20.x = ptc->unk20.x;
                        sp2C->unk20.y = ptc->unk20.y;
                        sp2C->unk20.z = ptc->unk20.z;
                        sp2C->unk5C = 20;
                        do {
                            sp2C->unk38.x = func_8018D560() * 64.0 - 32.0;
                            sp2C->unk38.y = func_8018D560() * 64.0 - 32.0;
                            sp2C->unk38.z = func_8018D560() * 64.0 - 32.0;
                        } while (gd_vec3f_magnitude(&sp2C->unk38) > 32.0);
                        sp2C->unk30 = func_8018D560() * 180.0f;
                        sp2C->header.drawFlags &= ~OBJ_NOT_DRAWABLE;
                        sp2C->unk54 |= 8;
                        link = link->next;
                    }
                }
                break;
            case 3:
                if ((ptc->unk54 & 0x20) && !(ptc->unk54 & 0x10)) {
                    func_80182A08(ptc, &sp40);
                    ptc->unk54 |= 0x10;
                }
                break;
            case 2:
                func_80182A08(ptc, &sp34);
                break;
        }
        apply_to_obj_types_in_group(OBJ_TYPE_PARTICLES, (applyproc_t) move_particle, ptc->unk6C);
    }
    if (ptc->unk5C >= 0) {
        if (ptc->unk5C-- <= 0) {
            ptc->header.drawFlags |= OBJ_NOT_DRAWABLE;
            ptc->unk54 &= ~8;
        }
    }
}

/* 231D40 -> 231D98; orig name: func_80183570 */
void move_particles_in_grp(struct ObjGroup *group) {
    start_timer("particles");
    gGdSkinNet = NULL;
    apply_to_obj_types_in_group(OBJ_TYPE_PARTICLES, (applyproc_t) move_particle, group);
    stop_timer("particles");
}

#define ABS(x) ((x) < 0.0f ? -(x) : (x))
/* 231D98 -> 232040 */
void Unknown801835C8(struct ObjParticle *ptc) {
    struct GdVec3f sp54;
    f32 sp50;
    register struct Links *link;

    gd_printf("p(%d)=", ptc->unkB4->objCount);
    link = ptc->unkB4->link1C;
    while (link != NULL) {
        // FIXME: types
        struct ObjParticle *sp48 = (struct ObjParticle *) link->obj;

        sp54.x = sp48->unk20.x - ptc->unk20.x;
        sp54.y = sp48->unk20.y - ptc->unk20.y;
        sp54.z = sp48->unk20.z - ptc->unk20.z;
        sp50 = 150.0f - (ABS(sp54.x) + ABS(sp54.y) + ABS(sp54.z));
        gd_printf(",%f ", sp50);
        sp50 *= 0.00000005;
        ptc->unk20.x += sp50 * sp54.x;
        ptc->unk20.y += sp50 * sp54.y;
        ptc->unk20.z += sp50 * sp54.z;
        sp48->unk20.x -= sp50 * sp54.x;
        sp48->unk20.y -= sp50 * sp54.y;
        sp48->unk20.z -= sp50 * sp54.z;
        link = link->next;
    }
    gd_printf("\n");
}

void Unknown80183870(UNUSED s32 a) {
}

void Unknown80183884(UNUSED s32 a) {
}

void Unknown80183898(UNUSED s32 a, UNUSED s32 b, UNUSED s32 c) {
}

void Unknown801838B4(UNUSED s32 a, UNUSED s32 b, UNUSED s32 c) {
}

/* 2320A0 -> 2320D4; pad to 2320E0 */
void func_801838D0(struct ObjParticle *ptc) {
    D_801B9E3C = ptc;
    if (ptc->unk20.y < -15.0f) {
    }
}
