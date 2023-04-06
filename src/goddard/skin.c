#include <ultra64.h>
#include <macros.h>
#include "gd_types.h"
#include "skin.h"
#include "gd_main.h"
#include "objects.h"
#include "skin_movement.h"
#include "particles.h"
#include "debug_utils.h"
#include "joints.h"
#include "gd_math.h"
#include "renderer.h"

// bss
struct ObjNet *gGdSkinNet; // @ 801BAAF0

static s32 D_801BAAF4;
static s32 sNetCount; // @ 801BAAF8

/* 2406E0 -> 240894 */
void func_80191F10(struct ObjNet *net) {
    func_8017BCB0();
    if (net->unk1D0 != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) func_8017BD20, net->unk1D0);
    }
    // L80191F58
    if (net->unk1C8 != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) func_8017BD20, net->unk1C8);
    }
    // L80191F8C
    D_801B9DA0.p0.x *= net->unk1AC.x;
    D_801B9DA0.p1.x *= net->unk1AC.x;
    D_801B9DA0.p0.y *= net->unk1AC.y;
    D_801B9DA0.p1.y *= net->unk1AC.y;
    D_801B9DA0.p0.z *= net->unk1AC.z;
    D_801B9DA0.p1.z *= net->unk1AC.z;

    net->unkBC.p0.x = D_801B9DA0.p0.x;
    net->unkBC.p0.y = D_801B9DA0.p0.y;
    net->unkBC.p0.z = D_801B9DA0.p0.z;
    net->unkBC.p1.x = D_801B9DA0.p1.x;
    net->unkBC.p1.y = D_801B9DA0.p1.y;
    net->unkBC.p1.z = D_801B9DA0.p1.z;
}

/* 240894 -> 240A64; orig name: func_801920C4 */
void reset_net(struct ObjNet *net) {
    struct ObjGroup *grp; // 24

    // based on move_net strings, unk38 is the likely parameter
    printf("reset_net %d\n", net->unk38);

    net->unk14.x = net->unk20.x;
    net->unk14.y = net->unk20.y;
    net->unk14.z = net->unk20.z;
    net->unk50.x = net->unk50.y = net->unk50.z = 0.0f;
    net->unkA4.x = net->unkA4.y = net->unkA4.z = 0.0f;

    func_80191F10(net);
    gd_print_vec("net scale: ", &net->unk1AC);
    gd_print_plane("net box: ", &net->unkBC);

    gGdSkinNet = net;
    D_801BAAF4 = 0;
    gd_set_identity_mat4(&net->mat168);
    gd_set_identity_mat4(&net->matE8);
    gd_rot_mat_about_vec(&net->matE8, &net->unk68); // set rot mtx to initial rotation?
    gd_add_vec3f_to_mat4f_offset(&net->matE8, &net->unk14); // set to initial position?
    gd_copy_mat4f(&net->matE8, &net->mat128);

    if ((grp = net->unk1C8) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191604, grp);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80191220, grp);
        apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) func_8018FB58, grp);
        apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) Unknown8018FA68, grp);
    }
}

/* 240A64 -> 240ACC */
void Unknown80192294(struct ObjNet *net) {
    UNUSED s32 sp1C = 0;

    if (net->unk1E8 == NULL) {
        restart_timer("childpos");
        sp1C = func_8017F054(&net->header, NULL);
        split_timer("childpos");
    }
}

/* 240ACC -> 240B84 */
void Unknown801922FC(struct ObjNet *net) {
    struct ObjGroup *group; // 24
    UNUSED u32 pad18[2];

    gGdSkinNet = net;
    // TODO: netype constants?
    if (net->netType == 4) {
        if (net->unk1A8 != NULL) {
            D_801B9E38 = &net->mat128;
            func_80181760(net->unk1A8->vtxGroup);
        }
        if ((group = net->unk1C8) != NULL) {
            apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80181B88, group);
        }
    }
}

/* 240B84 -> 240CF8 */
struct ObjNet *make_net(UNUSED s32 a0, struct ObjShape *shapedata, struct ObjGroup *a2,
                        struct ObjGroup *a3, struct ObjGroup *a4) {
    struct ObjNet *net; // 24

    net = (struct ObjNet *) make_object(OBJ_TYPE_NETS);
    gd_set_identity_mat4(&net->mat128);
    net->unk20.x = net->unk20.y = net->unk20.z = 0.0f;
    net->unk38 = ++sNetCount;
    net->unk1AC.x = net->unk1AC.y = net->unk1AC.z = 1.0f;
    net->unk1A8 = shapedata;
    net->unk1C8 = a2;
    net->unk1CC = a3;
    net->unk1D0 = a4;
    net->netType = 0;
    net->unk210 = 0;
    net->unk21C = NULL;
    net->unk3C = 1;
    net->unk40 = 0;
    net->skinGrp = NULL;
    reset_net(net);

    return net;
}

/* 240CF8 -> 240E74 */
void func_80192528(struct ObjNet *net) {
    net->unk44.x = net->unk44.y = net->unk44.z = 0.0f;
    net->unk74.x = net->unk74.y = net->unk74.z = 0.0f;
    net->unk80.x = net->unk80.y = net->unk80.z = 0.0f;
    net->unkD4.x = net->unkD4.y = net->unkD4.z = 0.0f;
    net->unkE0 = 0.0f;

    gGdCounter.ctr0 = 0;
    gGdCounter.ctr1 = 0;
    D_801B9E18.x = 0.0f;
    D_801B9E18.y = 0.0f;
    D_801B9E18.z = 0.0f;
    D_801B9E28.x = 0.0f;
    D_801B9E28.y = 0.0f;
    D_801B9E28.z = 0.0f;
    D_801B9E34 = 0.0f;

    if (net->unk34 & 0x1) {
        net->unk50.y += -4.0; //? 4.0f
    }

    net->unk14.x += net->unk50.x / 1.0f;
    net->unk14.y += net->unk50.y / 1.0f;
    net->unk14.z += net->unk50.z / 1.0f;
}

/* 240E74 -> 2412A0 */
void func_801926A4(struct ObjNet *net) {
    if (gGdCounter.ctr1 != 0) {
        if (D_801B9E34 != 0.0f) {
            D_801B9E28.x /= D_801B9E34;
            D_801B9E28.y /= D_801B9E34;
            D_801B9E28.z /= D_801B9E34;
        }

        D_801B9E28.x *= 1.0 / gGdCounter.ctr1; // !1.0f
        D_801B9E28.y *= 1.0 / gGdCounter.ctr1; // !1.0f
        D_801B9E28.z *= 1.0 / gGdCounter.ctr1; // !1.0f
        D_801B9E18.x *= 1.0 / gGdCounter.ctr1; // !1.0f
        D_801B9E18.y *= 1.0 / gGdCounter.ctr1; // !1.0f
        D_801B9E18.z *= 1.0 / gGdCounter.ctr1; // !1.0f

        func_8017E584(gGdSkinNet, &D_801B9E28, &D_801B9E18);
        func_8017E838(gGdSkinNet, &D_801B9E28, &D_801B9E18);
    }

    net->unkA4.x += net->unk80.x;
    net->unkA4.y += net->unk80.y;
    net->unkA4.z += net->unk80.z;
    net->unk74.x *= 1.0; // 1.0f;
    net->unk74.y *= 1.0; // 1.0f;
    net->unk74.z *= 1.0; // 1.0f;
    net->unk50.x += net->unk74.x;
    net->unk50.y += net->unk74.y;
    net->unk50.z += net->unk74.z;
    net->unk14.x += net->unk74.x;
    net->unk14.y += net->unk74.y;
    net->unk14.z += net->unk74.z;
    func_8017E9EC(net);

    net->unkA4.x *= 0.98; //? 0.98f
    net->unkA4.z *= 0.98; //? 0.98f
    net->unkA4.y *= 0.9;  //? 0.9f
}

/* 2412A0 -> 24142C; not called */
void Unknown80192AD0(struct ObjNet *net) {
    UNUSED u32 pad64;
    struct ObjGroup *sp60;
    UNUSED u32 pad20[0x10];
    UNUSED u32 sp1C;
    struct ObjNet *sp18;

    if ((sp60 = net->unk1C8) == NULL) {
        return;
    }

    sp18 = net->unk1F0;
    net->unk14.x = net->unk1F4.x;
    net->unk14.y = net->unk1F4.y;
    net->unk14.z = net->unk1F4.z;
    gd_rotate_and_translate_vec3f(&net->unk14, &sp18->mat128);

    net->unk14.x += net->unk1F0->unk14.x;
    net->unk14.y += net->unk1F0->unk14.y;
    net->unk14.z += net->unk1F0->unk14.z;
    net->unk200.x = 0.0f;
    net->unk200.y = 10.0f;
    net->unk200.z = -4.0f;
    gd_rotate_and_translate_vec3f(&net->unk200, &sp18->mat128);

    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80191824, sp60);
    func_80191E88(sp60);
    apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) func_8018F328, net->unk20C);
}

/* 24142C -> 24149C; orig name: func_80192C5C */
void move_bonesnet(struct ObjNet *net) {
    struct ObjGroup *sp24;
    UNUSED u32 pad18[3];

    add_to_stacktrace("move_bonesnet");
    gd_set_identity_mat4(&D_801B9DC8);
    if ((sp24 = net->unk1C8) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown801913C0, sp24);
    }
    imout();
}

/* 24149C -> 241768 */
void func_80192CCC(struct ObjNet *net) {
    Mat4f sp38;
    UNUSED struct GdControl *ctrl; // 34
    struct ObjGroup *group;        // 30
    struct GdVec3f sp24;

    ctrl = &gGdCtrl;
    if (gGdCtrl.unk2C != NULL) {
        func_8017E2B8();
    }
    gd_set_identity_mat4(&D_801B9DC8);

    if (gGdCtrl.unk30 != NULL) {
        sp24.x = net->mat128[0][0];
        sp24.y = net->mat128[0][1];
        sp24.z = net->mat128[0][2];
        gd_create_rot_mat_angular(&sp38, &sp24, 4.0f);
        gd_mult_mat4f(&sp38, &D_801B9DC8, &D_801B9DC8);
        net->unkA4.x = net->unkA4.y = net->unkA4.z = 0.0f;
    }

    if (gGdCtrl.unk28 != NULL) {
        sp24.x = net->mat128[0][0];
        sp24.y = net->mat128[0][1];
        sp24.z = net->mat128[0][2];
        gd_create_rot_mat_angular(&sp38, &sp24, -4.0f);
        gd_mult_mat4f(&sp38, &D_801B9DC8, &D_801B9DC8);
        net->unkA4.x = net->unkA4.y = net->unkA4.z = 0.0f;
    }

    if (gGdCtrl.newStartPress) {
        return;
    } // start was pressed

    switch (net->unk210) {
        case 2:
            break;
    }

    func_80192528(net);
    if ((group = net->unk1C8) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown80191220, group);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown801913F0, group);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown801914F8, group);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown801911A8, group);
    }

    func_801926A4(net);
    gd_mult_mat4f(&net->mat128, &D_801B9DC8, &net->mat128);
    if (group != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) Unknown801913C0, group);
        apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) Unknown8018FA68, group);
    }
}

/* 241768 -> 241AB4; orig name: func_80192F98 */
void convert_gd_verts_to_Vn(struct ObjGroup *grp) {
    UNUSED u8 pad[0x40 - 0x2c];
    Vtx *vn;       // 28
    u8 nx, ny, nz; // 24, 25, 26
    UNUSED u32 pad20;
    register struct VtxLink *vtxlink; // a1
    register s16 *vnPos;              // a2
    register s16 x;                   // a3
    register s16 y;                   // t0
    register s16 z;                   // t1
    register struct ObjVertex *vtx;   // t2
    register struct Links *link;      // t3
    struct GdObj *obj;                // sp4

    for (link = grp->link1C; link != NULL; link = link->next) {
        obj = link->obj;
        vtx = (struct ObjVertex *) obj;
        x = (s16) vtx->pos.x;
        y = (s16) vtx->pos.y;
        z = (s16) vtx->pos.z;

        nx = (u8)(vtx->normal.x * 255.0f);
        ny = (u8)(vtx->normal.y * 255.0f);
        nz = (u8)(vtx->normal.z * 255.0f);

        for (vtxlink = vtx->gbiVerts; vtxlink != NULL; vtxlink = vtxlink->prev) {
            vnPos = vtxlink->data->n.ob;
            vn = vtxlink->data;
            *vnPos++ = x;
            *vnPos++ = y;
            *vnPos++ = z;
            vn->n.n[0] = nx;
            vn->n.n[1] = ny;
            vn->n.n[2] = nz;
        }
    }
}

/* 241AB4 -> 241BCC; orig name: func_801932E4 */
void convert_gd_verts_to_Vtx(struct ObjGroup *grp) {
    UNUSED u32 pad24[6];
    register struct VtxLink *vtxlink; // a1
    register s16 *vtxcoords;          // a2
    register s16 x;                   // a3
    register s16 y;                   // t0
    register s16 z;                   // t1
    register struct ObjVertex *vtx;   // t2
    register struct Links *link;      // t3
    struct GdObj *obj;                // sp4

    for (link = grp->link1C; link != NULL; link = link->next) {
        obj = link->obj;
        vtx = (struct ObjVertex *) obj;
        x = (s16) vtx->pos.x;
        y = (s16) vtx->pos.y;
        z = (s16) vtx->pos.z;

        for (vtxlink = vtx->gbiVerts; vtxlink != NULL; vtxlink = vtxlink->prev) {
            vtxcoords = vtxlink->data->v.ob;
            vtxcoords[0] = x;
            vtxcoords[1] = y;
            vtxcoords[2] = z;
        }
    }
}

/* 241BCC -> 241CA0; orig name: Proc801933FC */
void convert_net_verts(struct ObjNet *net) {
    if (net->unk1A8 != NULL) {
        if (net->unk1A8->unk30) {
            convert_gd_verts_to_Vn(net->unk1A8->vtxGroup);
        }
    }

    switch (net->netType) {
        case 2:
            if (net->unk1A8 != NULL) {
                convert_gd_verts_to_Vtx(net->unk1A8->unk24);
            }
            break;
    }
}

/* 241CA0 -> 241D6C */
void func_801934D0(struct ObjNet *net) {
    struct ObjGroup *grp;        // 2c
    register struct Links *link; // s0
    struct GdObj *obj;           // 24

    if ((grp = net->unk1C8) != NULL) {
        for (link = grp->link1C; link != NULL; link = link->next) {
            obj = link->obj;
            switch (obj->type) {
                case OBJ_TYPE_JOINTS:
                    if (((struct ObjJoint *) obj)->fn2C != NULL) {
                        (*((struct ObjJoint *) obj)->fn2C)((struct ObjJoint *) obj);
                    }
                    break;
                default:;
            }
        }
    }
}

/* 241D6C -> 241E94; orig name: Unknown8019359C */
void move_net(struct ObjNet *net) {
    gGdSkinNet = net;

    switch (net->netType) {
        case 1:
            break;
        case 7:
            func_80192CCC(net);
            break;
        case 4:
            restart_timer("move_bones");
            move_bonesnet(net);
            split_timer("move_bones");
            break;
        case 2:
            restart_timer("move_skin");
            move_skin(net);
            split_timer("move_skin");
            break;
        case 3:
            func_801934D0(net);
            break;
        case 5:
            func_801823A0(net);
            break;
        case 6:
            break;
        default:
            fatal_printf("move_net(%d(%d)): Undefined net type", net->unk38, net->netType);
    }
}

/* 241E94 -> 241F0C; orig name: func_801936C4 */
void move_nets(struct ObjGroup *group) {
    add_to_stacktrace("move_nets");
    restart_timer("move_nets");
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) Unknown80192294, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) move_net, group);
    split_timer("move_nets");
    imout();
}

/* 241F0C -> 242018 */
void Unknown8019373C(struct ObjNet *net) {
    register struct Links *link; // s0
    struct ObjVertex *vtx;       // 20

    switch (net->netType) {
        case 2:
            if (net->unk1A8 != NULL) {
                net->unk1A8->unk24 = make_group(0);
                for (link = net->unk1A8->vtxGroup->link1C; link != NULL; link = link->next) {
                    vtx = (struct ObjVertex *) link->obj;
                    if (vtx->scaleFactor != 1.0) //? 1.0f
                    {
                        addto_group(net->unk1A8->unk24, &vtx->header);
                    }
                }
            }
            break;
    }
}

/* 242018 -> 24208C */
void func_80193848(struct ObjGroup *group) {
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) reset_net, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) Unknown80192294, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) Unknown801922FC, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) Unknown8019373C, group);
}

/* 24208C -> 2422E0; not called; orig name: Unknown801938BC */
void gd_print_net(struct ObjNet *net) {
    gd_printf("Flags:%x\n", net->unk34);
    gd_print_vec("World:", &net->unk14);
    gd_print_vec("Force:", &net->unk44);
    gd_print_vec("Vel:", &net->unk50);
    gd_print_vec("Rot:", &net->unk5C);
    gd_print_vec("CollDisp:", &net->unk74);
    gd_print_vec("CollTorque:", &net->unk80);
    gd_print_vec("CollTorqueL:", &net->unk8C);
    gd_print_vec("CollTorqueD:", &net->unk98);
    gd_print_vec("Torque:", &net->unkA4);
    gd_print_vec("CofG:", &net->unkB0);
    gd_print_plane("BoundBox:", &net->unkBC);
    gd_print_vec("CollDispOff:", &net->unkD4);
    gd_printf("CollMaxD: %f\n", net->unkE0);
    gd_printf("MaxRadius: %f\n", net->unkE4);
    gd_print_mtx("Matrix:", &net->mat128);
    if (net->unk1A8 != NULL) {
        gd_printf("ShapePtr: %x (%s)\n", (u32) (uintptr_t) net->unk1A8, net->unk1A8->name);
    } else {
        gd_printf("ShapePtr: NULL\n");
    }
    gd_print_vec("Scale:", &net->unk1AC);
    gd_printf("Mass: %f\n", net->unk1B8);
    gd_printf("NumModes: %d\n", net->unk1BC);
    gd_printf("NodeGroup: %x\n", (u32) (uintptr_t) net->unk1C8);
    gd_printf("PlaneGroup: %x\n", (u32) (uintptr_t) net->unk1CC);
    gd_printf("VertexGroup: %x\n", (u32) (uintptr_t) net->unk1D0);
}

/* 2422E0 -> 2422F8; orig name: func_80193B10 */
void reset_net_count(void) {
    sNetCount = 0;
}
