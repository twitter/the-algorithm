#include <PR/ultratypes.h>

#ifdef VERSION_EU
#include "prevent_bss_reordering.h"
#endif

#include "debug_utils.h"
#include "gd_main.h"
#include "gd_math.h"
#include "gd_types.h"
#include "joints.h"
#include "macros.h"
#include "objects.h"
#include "particles.h"
#include "renderer.h"
#include "skin.h"
#include "skin_movement.h"

// bss
struct ObjNet *gGdSkinNet; // @ 801BAAF0

static s32 D_801BAAF4;
static s32 sNetCount; // @ 801BAAF8

/* 2406E0 -> 240894 */
void compute_net_bounding_box(struct ObjNet *net) {
    reset_bounding_box();
    if (net->unk1D0 != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) add_obj_pos_to_bounding_box, net->unk1D0);
    }
    if (net->unk1C8 != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_ALL, (applyproc_t) add_obj_pos_to_bounding_box, net->unk1C8);
    }
    gSomeBoundingBox.minX *= net->scale.x;
    gSomeBoundingBox.maxX *= net->scale.x;
    gSomeBoundingBox.minY *= net->scale.y;
    gSomeBoundingBox.maxY *= net->scale.y;
    gSomeBoundingBox.minZ *= net->scale.z;
    gSomeBoundingBox.maxZ *= net->scale.z;

    net->boundingBox.minX = gSomeBoundingBox.minX;
    net->boundingBox.minY = gSomeBoundingBox.minY;
    net->boundingBox.minZ = gSomeBoundingBox.minZ;
    net->boundingBox.maxX = gSomeBoundingBox.maxX;
    net->boundingBox.maxY = gSomeBoundingBox.maxY;
    net->boundingBox.maxZ = gSomeBoundingBox.maxZ;
}

/* 240894 -> 240A64; orig name: func_801920C4 */
void reset_net(struct ObjNet *net) {
    struct ObjGroup *grp;

    printf("reset_net %d\n", net->id);

    net->worldPos.x = net->initPos.x;
    net->worldPos.y = net->initPos.y;
    net->worldPos.z = net->initPos.z;
    net->velocity.x = net->velocity.y = net->velocity.z = 0.0f;
    net->torque.x = net->torque.y = net->torque.z = 0.0f;

    compute_net_bounding_box(net);
    gd_print_vec("net scale: ", &net->scale);
    gd_print_bounding_box("net box: ", &net->boundingBox);

    gGdSkinNet = net;
    D_801BAAF4 = 0;
    gd_set_identity_mat4(&net->mat168);
    gd_set_identity_mat4(&net->matE8);
    gd_rot_mat_about_vec(&net->matE8, &net->unk68); // set rot mtx to initial rotation?
    gd_add_vec3f_to_mat4f_offset(&net->matE8, &net->worldPos); // set to initial position?
    gd_copy_mat4f(&net->matE8, &net->mat128);

    if ((grp = net->unk1C8) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) reset_joint, grp);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191220, grp);
        apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) func_8018FB58, grp);
        apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) func_8018FA68, grp);
    }
}

/* 240A64 -> 240ACC */
void func_80192294(struct ObjNet *net) {
    UNUSED s32 sp1C = 0;

    if (net->attachedToObj == NULL) {
        restart_timer("childpos");
        sp1C = transform_child_objects_recursive(&net->header, NULL);
        split_timer("childpos");
    }
}

/* 240ACC -> 240B84 */
void func_801922FC(struct ObjNet *net) {
    struct ObjGroup *group; // 24
    UNUSED u8 filler[8];

    gGdSkinNet = net;
    // TODO: netype constants?
    if (net->netType == 4) {
        if (net->shapePtr != NULL) {
            D_801B9E38 = &net->mat128;
            scale_verts(net->shapePtr->vtxGroup);
        }
        if ((group = net->unk1C8) != NULL) {
            apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) reset_joint_weights, group);
        }
    }
}

/* 240B84 -> 240CF8 */
struct ObjNet *make_net(UNUSED s32 a0, struct ObjShape *shapedata, struct ObjGroup *a2,
                        struct ObjGroup *a3, struct ObjGroup *a4) {
    struct ObjNet *net;

    net = (struct ObjNet *) make_object(OBJ_TYPE_NETS);
    gd_set_identity_mat4(&net->mat128);
    net->initPos.x = net->initPos.y = net->initPos.z = 0.0f;
    net->id = ++sNetCount;
    net->scale.x = net->scale.y = net->scale.z = 1.0f;
    net->shapePtr = shapedata;
    net->unk1C8 = a2;
    net->unk1CC = a3;
    net->unk1D0 = a4;
    net->netType = 0;
    net->ctrlType = 0;
    net->unk21C = NULL;
    net->unk3C = 1;
    net->colourNum = 0;
    net->skinGrp = NULL;
    reset_net(net);

    return net;
}

/* 240CF8 -> 240E74 */
void func_80192528(struct ObjNet *net) {
    net->unusedForce.x = net->unusedForce.y = net->unusedForce.z = 0.0f;
    net->collDisp.x = net->collDisp.y = net->collDisp.z = 0.0f;
    net->collTorque.x = net->collTorque.y = net->collTorque.z = 0.0f;
    net->unusedCollDispOff.x = net->unusedCollDispOff.y = net->unusedCollDispOff.z = 0.0f;
    net->unusedCollMaxD = 0.0f;

    gGdCounter.ctr0 = 0;
    gGdCounter.ctr1 = 0;
    D_801B9E18.x = 0.0f;
    D_801B9E18.y = 0.0f;
    D_801B9E18.z = 0.0f;
    D_801B9E28.x = 0.0f;
    D_801B9E28.y = 0.0f;
    D_801B9E28.z = 0.0f;
    D_801B9E34 = 0.0f;

    if (net->flags & 0x1) {
        net->velocity.y += -4.0; //? 4.0f
    }

    net->worldPos.x += net->velocity.x / 1.0f;
    net->worldPos.y += net->velocity.y / 1.0f;
    net->worldPos.z += net->velocity.z / 1.0f;
}

/* 240E74 -> 2412A0 */
void collision_something_801926A4(struct ObjNet *net) {
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

    net->torque.x += net->collTorque.x;
    net->torque.y += net->collTorque.y;
    net->torque.z += net->collTorque.z;
    net->collDisp.x *= 1.0; // 1.0f;
    net->collDisp.y *= 1.0; // 1.0f;
    net->collDisp.z *= 1.0; // 1.0f;
    net->velocity.x += net->collDisp.x;
    net->velocity.y += net->collDisp.y;
    net->velocity.z += net->collDisp.z;
    net->worldPos.x += net->collDisp.x;
    net->worldPos.y += net->collDisp.y;
    net->worldPos.z += net->collDisp.z;
    func_8017E9EC(net);

    net->torque.x *= 0.98; //? 0.98f
    net->torque.z *= 0.98; //? 0.98f
    net->torque.y *= 0.9;  //? 0.9f
}

/* 2412A0 -> 24142C; not called */
void func_80192AD0(struct ObjNet *net) {
    UNUSED u8 filler1[4];
    struct ObjGroup *sp60;
    UNUSED u8 filler2[68];
    struct ObjNet *sp18;

    if ((sp60 = net->unk1C8) == NULL) {
        return;
    }

    sp18 = net->unk1F0;
    net->worldPos.x = net->unk1F4.x;
    net->worldPos.y = net->unk1F4.y;
    net->worldPos.z = net->unk1F4.z;
    gd_rotate_and_translate_vec3f(&net->worldPos, &sp18->mat128);

    net->worldPos.x += net->unk1F0->worldPos.x;
    net->worldPos.y += net->unk1F0->worldPos.y;
    net->worldPos.z += net->unk1F0->worldPos.z;
    net->unk200.x = 0.0f;
    net->unk200.y = 10.0f;
    net->unk200.z = -4.0f;
    gd_rotate_and_translate_vec3f(&net->unk200, &sp18->mat128);

    apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191824, sp60);
    func_80191E88(sp60);
    apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) func_8018F328, net->unk20C);
}

/* 24142C -> 24149C; orig name: func_80192C5C */
void move_bonesnet(struct ObjNet *net) {
    struct ObjGroup *sp24;
    UNUSED u8 filler[12];

    imin("move_bonesnet");
    gd_set_identity_mat4(&D_801B9DC8);
    if ((sp24 = net->unk1C8) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_801913C0, sp24);
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
        menu_cb_reset_positions();
    }
    gd_set_identity_mat4(&D_801B9DC8);

    if (gGdCtrl.unk30 != NULL) {
        sp24.x = net->mat128[0][0];
        sp24.y = net->mat128[0][1];
        sp24.z = net->mat128[0][2];
        gd_create_rot_mat_angular(&sp38, &sp24, 4.0f);
        gd_mult_mat4f(&sp38, &D_801B9DC8, &D_801B9DC8);
        net->torque.x = net->torque.y = net->torque.z = 0.0f;
    }

    if (gGdCtrl.unk28 != NULL) {
        sp24.x = net->mat128[0][0];
        sp24.y = net->mat128[0][1];
        sp24.z = net->mat128[0][2];
        gd_create_rot_mat_angular(&sp38, &sp24, -4.0f);
        gd_mult_mat4f(&sp38, &D_801B9DC8, &D_801B9DC8);
        net->torque.x = net->torque.y = net->torque.z = 0.0f;
    }

    if (gGdCtrl.newStartPress) {
        return;
    } // start was pressed

    switch (net->ctrlType) {
        case 2:
            break;
    }

    func_80192528(net);
    if ((group = net->unk1C8) != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_80191220, group);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_801913F0, group);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) stub_joints_2, group);
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_801911A8, group);
    }

    collision_something_801926A4(net);
    gd_mult_mat4f(&net->mat128, &D_801B9DC8, &net->mat128);
    if (group != NULL) {
        apply_to_obj_types_in_group(OBJ_TYPE_JOINTS, (applyproc_t) func_801913C0, group);
        apply_to_obj_types_in_group(OBJ_TYPE_BONES, (applyproc_t) func_8018FA68, group);
    }
}

/* 241768 -> 241AB4; orig name: func_80192F98 */
void convert_gd_verts_to_Vn(struct ObjGroup *grp) {
    UNUSED u8 filler1[20];
    Vtx *vn;       // 28
    u8 nx, ny, nz; // 24, 25, 26
    UNUSED u8 filler2[4];
    register struct VtxLink *vtxlink; // a1
#ifndef GBI_FLOATS
    register s16 *vnPos;              // a2
#endif
    register s16 x;                   // a3
    register s16 y;                   // t0
    register s16 z;                   // t1
    register struct ObjVertex *vtx;   // t2
    register struct ListNode *link;      // t3
    struct GdObj *obj;                // sp4

    for (link = grp->firstMember; link != NULL; link = link->next) {
        obj = link->obj;
        vtx = (struct ObjVertex *) obj;
        x = (s16) vtx->pos.x;
        y = (s16) vtx->pos.y;
        z = (s16) vtx->pos.z;

        nx = (u8)(vtx->normal.x * 255.0f);
        ny = (u8)(vtx->normal.y * 255.0f);
        nz = (u8)(vtx->normal.z * 255.0f);

        for (vtxlink = vtx->gbiVerts; vtxlink != NULL; vtxlink = vtxlink->prev) {
#ifndef GBI_FLOATS
            vnPos = vtxlink->data->n.ob;
            vn = vtxlink->data;
            *vnPos++ = x;
            *vnPos++ = y;
            *vnPos++ = z;
#else
            vn = vtxlink->data;
            vn->n.ob[0] = x;
            vn->n.ob[1] = y;
            vn->n.ob[2] = z;
#endif
            vn->n.n[0] = nx;
            vn->n.n[1] = ny;
            vn->n.n[2] = nz;
        }
    }
}

/* 241AB4 -> 241BCC; orig name: func_801932E4 */
void convert_gd_verts_to_Vtx(struct ObjGroup *grp) {
    UNUSED u8 filler[24];
    register struct VtxLink *vtxlink; // a1
#ifndef GBI_FLOATS
    register s16 *vtxcoords;          // a2
#endif
    register s16 x;                   // a3
    register s16 y;                   // t0
    register s16 z;                   // t1
    register struct ObjVertex *vtx;   // t2
    register struct ListNode *link;      // t3
    struct GdObj *obj;                // sp4

    for (link = grp->firstMember; link != NULL; link = link->next) {
        obj = link->obj;
        vtx = (struct ObjVertex *) obj;
        x = (s16) vtx->pos.x;
        y = (s16) vtx->pos.y;
        z = (s16) vtx->pos.z;

        for (vtxlink = vtx->gbiVerts; vtxlink != NULL; vtxlink = vtxlink->prev) {
#ifndef GBI_FLOATS
            vtxcoords = vtxlink->data->v.ob;
            vtxcoords[0] = x;
            vtxcoords[1] = y;
            vtxcoords[2] = z;
#else
            vtxlink->data->v.ob[0] = x;
            vtxlink->data->v.ob[1] = y;
            vtxlink->data->v.ob[2] = z;
#endif
        }
    }
}

/* 241BCC -> 241CA0; orig name: Proc801933FC */
void convert_net_verts(struct ObjNet *net) {
    if (net->shapePtr != NULL) {
        if (net->shapePtr->unk30) {
            convert_gd_verts_to_Vn(net->shapePtr->vtxGroup);
        }
    }

    switch (net->netType) {
        case 2:
            if (net->shapePtr != NULL) {
                convert_gd_verts_to_Vtx(net->shapePtr->scaledVtxGroup);
            }
            break;
    }
}

/* 241CA0 -> 241D6C */
static void move_joints_in_net(struct ObjNet *net) {
    struct ObjGroup *grp;        // 2c
    register struct ListNode *link; // s0
    struct GdObj *obj;           // 24

    if ((grp = net->unk1C8) != NULL) {
        for (link = grp->firstMember; link != NULL; link = link->next) {
            obj = link->obj;
            switch (obj->type) {
                case OBJ_TYPE_JOINTS:
                    if (((struct ObjJoint *) obj)->updateFunc != NULL) {
                        (*((struct ObjJoint *) obj)->updateFunc)((struct ObjJoint *) obj);
                    }
                    break;
                default:;
            }
        }
    }
}

/* 241D6C -> 241E94; orig name: func_8019359C */
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
            move_joints_in_net(net);
            break;
        case 5:
            func_801823A0(net);
            break;
        case 6:
            break;
        default:
            fatal_printf("move_net(%d(%d)): Undefined net type", net->id, net->netType);
    }
}

/* 241E94 -> 241F0C; orig name: func_801936C4 */
void move_nets(struct ObjGroup *group) {
    imin("move_nets");
    restart_timer("move_nets");
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) func_80192294, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) move_net, group);
    split_timer("move_nets");
    imout();
}

/* 241F0C -> 242018 */
void func_8019373C(struct ObjNet *net) {
    register struct ListNode *link;
    struct ObjVertex *vtx;

    switch (net->netType) {
        case 2:
            if (net->shapePtr != NULL) {
                net->shapePtr->scaledVtxGroup = make_group(0);
                for (link = net->shapePtr->vtxGroup->firstMember; link != NULL; link = link->next) {
                    vtx = (struct ObjVertex *) link->obj;
                    if (vtx->scaleFactor != 1.0) {
                        addto_group(net->shapePtr->scaledVtxGroup, &vtx->header);
                    }
                }
            }
            break;
    }
}

/* 242018 -> 24208C */
void func_80193848(struct ObjGroup *group) {
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) reset_net, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) func_80192294, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) func_801922FC, group);
    apply_to_obj_types_in_group(OBJ_TYPE_NETS, (applyproc_t) func_8019373C, group);
}

/* 24208C -> 2422E0; not called; orig name: func_801938BC */
void gd_print_net(struct ObjNet *net) {
    gd_printf("Flags:%x\n", net->flags);
    gd_print_vec("World:", &net->worldPos);
    gd_print_vec("Force:", &net->unusedForce);
    gd_print_vec("Vel:", &net->velocity);
    gd_print_vec("Rot:", &net->rotation);
    gd_print_vec("CollDisp:", &net->collDisp);
    gd_print_vec("CollTorque:", &net->collTorque);
    gd_print_vec("CollTorqueL:", &net->unusedCollTorqueL);
    gd_print_vec("CollTorqueD:", &net->unusedCollTorqueD);
    gd_print_vec("Torque:", &net->torque);
    gd_print_vec("CofG:", &net->centerOfGravity);
    gd_print_bounding_box("BoundBox:", &net->boundingBox);
    gd_print_vec("CollDispOff:", &net->unusedCollDispOff);
    gd_printf("CollMaxD: %f\n", net->unusedCollMaxD);
    gd_printf("MaxRadius: %f\n", net->maxRadius);
    gd_print_mtx("Matrix:", &net->mat128);
    if (net->shapePtr != NULL) {
        gd_printf("ShapePtr: %x (%s)\n", (u32) (uintptr_t) net->shapePtr, net->shapePtr->name);
    } else {
        gd_printf("ShapePtr: NULL\n");
    }
    gd_print_vec("Scale:", &net->scale);
    gd_printf("Mass: %f\n", net->unusedMass);
    gd_printf("NumModes: %d\n", net->numModes);
    gd_printf("NodeGroup: %x\n", (u32) (uintptr_t) net->unk1C8);
    gd_printf("PlaneGroup: %x\n", (u32) (uintptr_t) net->unk1CC);
    gd_printf("VertexGroup: %x\n", (u32) (uintptr_t) net->unk1D0);
}

/* 2422E0 -> 2422F8; orig name: func_80193B10 */
void reset_net_count(void) {
    sNetCount = 0;
}
