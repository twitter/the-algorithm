#include <ultra64.h>
#include "sm64.h"

#include "game/level_update.h"
#include "math_util.h"
#include "game/memory.h"
#include "graph_node.h"
#include "game/rendering_graph_node.h"
#include "game/area.h"
#include "geo_layout.h"

// unused Mtx(s)
s16 identityMtx[4][4] = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
s16 zeroMtx[4][4] = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

Vec3f gVec3fZero = { 0.0f, 0.0f, 0.0f };
Vec3s gVec3sZero = { 0, 0, 0 };
Vec3f gVec3fOne = { 1.0f, 1.0f, 1.0f };
UNUSED Vec3s gVec3sOne = { 1, 1, 1 };

/**
 * Initialize a geo node with a given type. Sets all links such that there
 * are no siblings, parent or children for this node.
 */
void init_scene_graph_node_links(struct GraphNode *graphNode, s32 type) {
    graphNode->type = type;
    graphNode->flags = GRAPH_RENDER_ACTIVE;
    graphNode->prev = graphNode;
    graphNode->next = graphNode;
    graphNode->parent = NULL;
    graphNode->children = NULL;
}

/**
 * Allocated and returns a newly created root node
 */
struct GraphNodeRoot *init_graph_node_root(struct AllocOnlyPool *pool, struct GraphNodeRoot *graphNode,
                                           s16 areaIndex, s16 x, s16 y, s16 width, s16 height) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeRoot));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_ROOT);

        graphNode->areaIndex = areaIndex;
        graphNode->unk15 = 0;
        graphNode->x = x;
        graphNode->y = y;
        graphNode->width = width;
        graphNode->height = height;
        graphNode->views = NULL;
        graphNode->numViews = 0;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created otrhographic projection node
 */
struct GraphNodeOrthoProjection *
init_graph_node_ortho_projection(struct AllocOnlyPool *pool, struct GraphNodeOrthoProjection *graphNode,
                                 f32 scale) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeOrthoProjection));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_ORTHO_PROJECTION);
        graphNode->scale = scale;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created perspective node
 */
struct GraphNodePerspective *init_graph_node_perspective(struct AllocOnlyPool *pool,
                                                         struct GraphNodePerspective *graphNode,
                                                         f32 fov, s16 near, s16 far,
                                                         GraphNodeFunc nodeFunc, s32 unused) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodePerspective));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->fnNode.node, GRAPH_NODE_TYPE_PERSPECTIVE);

        graphNode->fov = fov;
        graphNode->near = near;
        graphNode->far = far;
        graphNode->fnNode.func = nodeFunc;
        graphNode->unused = unused;

        if (nodeFunc != NULL) {
            nodeFunc(GEO_CONTEXT_CREATE, &graphNode->fnNode.node, pool);
        }
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created start node
 */
struct GraphNodeStart *init_graph_node_start(struct AllocOnlyPool *pool,
                                             struct GraphNodeStart *graphNode) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeStart));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_START);
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created master list node
 */
struct GraphNodeMasterList *init_graph_node_master_list(struct AllocOnlyPool *pool,
                                                        struct GraphNodeMasterList *graphNode, s16 on) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeMasterList));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_MASTER_LIST);

        if (on) {
            graphNode->node.flags |= GRAPH_RENDER_Z_BUFFER;
        }
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created render range node
 */
struct GraphNodeLevelOfDetail *init_graph_node_render_range(struct AllocOnlyPool *pool,
                                                            struct GraphNodeLevelOfDetail *graphNode,
                                                            s16 minDistance, s16 maxDistance) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeLevelOfDetail));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_LEVEL_OF_DETAIL);
        graphNode->minDistance = minDistance;
        graphNode->maxDistance = maxDistance;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created switch case node
 */
struct GraphNodeSwitchCase *init_graph_node_switch_case(struct AllocOnlyPool *pool,
                                                        struct GraphNodeSwitchCase *graphNode,
                                                        s16 numCases, s16 selectedCase,
                                                        GraphNodeFunc nodeFunc, s32 unused) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeSwitchCase));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->fnNode.node, GRAPH_NODE_TYPE_SWITCH_CASE);
        graphNode->numCases = numCases;
        graphNode->selectedCase = selectedCase;
        graphNode->fnNode.func = nodeFunc;
        graphNode->unused = unused;

        if (nodeFunc != NULL) {
            nodeFunc(GEO_CONTEXT_CREATE, &graphNode->fnNode.node, pool);
        }
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created camera node
 */
struct GraphNodeCamera *init_graph_node_camera(struct AllocOnlyPool *pool,
                                               struct GraphNodeCamera *graphNode, f32 *pos,
                                               f32 *focus, GraphNodeFunc func, s32 mode) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeCamera));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->fnNode.node, GRAPH_NODE_TYPE_CAMERA);
        vec3f_copy(graphNode->pos, pos);
        vec3f_copy(graphNode->focus, focus);
        graphNode->fnNode.func = func;
        graphNode->config.mode = mode;
        graphNode->roll = 0;
        graphNode->rollScreen = 0;

        if (func != NULL) {
            func(GEO_CONTEXT_CREATE, &graphNode->fnNode.node, pool);
        }
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created translation rotation node
 */
struct GraphNodeTranslationRotation *
init_graph_node_translation_rotation(struct AllocOnlyPool *pool,
                                     struct GraphNodeTranslationRotation *graphNode, s32 drawingLayer,
                                     void *displayList, Vec3s translation, Vec3s rotation) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeTranslationRotation));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_TRANSLATION_ROTATION);

        vec3s_copy(graphNode->translation, translation);
        vec3s_copy(graphNode->rotation, rotation);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created translation node
 */
struct GraphNodeTranslation *init_graph_node_translation(struct AllocOnlyPool *pool,
                                                         struct GraphNodeTranslation *graphNode,
                                                         s32 drawingLayer, void *displayList,
                                                         Vec3s translation) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeTranslation));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_TRANSLATION);

        vec3s_copy(graphNode->translation, translation);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created rotation node
 */
struct GraphNodeRotation *init_graph_node_rotation(struct AllocOnlyPool *pool,
                                                   struct GraphNodeRotation *graphNode,
                                                   s32 drawingLayer, void *displayList,
                                                   Vec3s rotation) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeRotation));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_ROTATION);
        vec3s_copy(graphNode->rotation, rotation);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created scaling node
 */
struct GraphNodeScale *init_graph_node_scale(struct AllocOnlyPool *pool,
                                             struct GraphNodeScale *graphNode, s32 drawingLayer,
                                             void *displayList, f32 scale) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeScale));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_SCALE);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->scale = scale;
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created object node
 */
struct GraphNodeObject *init_graph_node_object(struct AllocOnlyPool *pool,
                                               struct GraphNodeObject *graphNode,
                                               struct GraphNode *sharedChild, Vec3f pos, Vec3s angle,
                                               Vec3f scale) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeObject));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_OBJECT);
        vec3f_copy(graphNode->pos, pos);
        vec3f_copy(graphNode->scale, scale);
        vec3s_copy(graphNode->angle, angle);
        graphNode->sharedChild = sharedChild;
        graphNode->throwMatrix = NULL;
        graphNode->unk38.animID = 0;
        graphNode->unk38.curAnim = NULL;
        graphNode->unk38.animFrame = 0;
        graphNode->unk38.animFrameAccelAssist = 0;
        graphNode->unk38.animAccel = 0x10000;
        graphNode->unk38.animTimer = 0;
        graphNode->node.flags |= GRAPH_RENDER_HAS_ANIMATION;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created frustum culling radius node
 */
struct GraphNodeCullingRadius *init_graph_node_culling_radius(struct AllocOnlyPool *pool,
                                                              struct GraphNodeCullingRadius *graphNode,
                                                              s16 radius) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeCullingRadius));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_CULLING_RADIUS);
        graphNode->cullingRadius = radius;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created animated part node
 */
struct GraphNodeAnimatedPart *init_graph_node_animated_part(struct AllocOnlyPool *pool,
                                                            struct GraphNodeAnimatedPart *graphNode,
                                                            s32 drawingLayer, void *displayList,
                                                            Vec3s translation) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeAnimatedPart));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_ANIMATED_PART);
        vec3s_copy(graphNode->translation, translation);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created billboard node
 */
struct GraphNodeBillboard *init_graph_node_billboard(struct AllocOnlyPool *pool,
                                                     struct GraphNodeBillboard *graphNode,
                                                     s32 drawingLayer, void *displayList,
                                                     Vec3s translation) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeBillboard));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_BILLBOARD);
        vec3s_copy(graphNode->translation, translation);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created displaylist node
 */
struct GraphNodeDisplayList *init_graph_node_display_list(struct AllocOnlyPool *pool,
                                                          struct GraphNodeDisplayList *graphNode,
                                                          s32 drawingLayer, void *displayList) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeDisplayList));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_DISPLAY_LIST);
        graphNode->node.flags = (drawingLayer << 8) | (graphNode->node.flags & 0xFF);
        graphNode->displayList = displayList;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created shadow node
 */
struct GraphNodeShadow *init_graph_node_shadow(struct AllocOnlyPool *pool,
                                               struct GraphNodeShadow *graphNode, s16 shadowScale,
                                               u8 shadowSolidity, u8 shadowType) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeShadow));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_SHADOW);
        graphNode->shadowScale = shadowScale;
        graphNode->shadowSolidity = shadowSolidity;
        graphNode->shadowType = shadowType;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created object parent node
 */
struct GraphNodeObjectParent *init_graph_node_object_parent(struct AllocOnlyPool *pool,
                                                            struct GraphNodeObjectParent *graphNode,
                                                            struct GraphNode *sharedChild) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeObjectParent));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->node, GRAPH_NODE_TYPE_OBJECT_PARENT);
        graphNode->sharedChild = sharedChild;
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created generated node
 */
struct GraphNodeGenerated *init_graph_node_generated(struct AllocOnlyPool *pool,
                                                     struct GraphNodeGenerated *graphNode,
                                                     GraphNodeFunc gfxFunc, s32 parameter) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeGenerated));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->fnNode.node, GRAPH_NODE_TYPE_GENERATED_LIST);
        graphNode->fnNode.func = gfxFunc;
        graphNode->parameter = parameter;

        if (gfxFunc != NULL) {
            gfxFunc(GEO_CONTEXT_CREATE, &graphNode->fnNode.node, pool);
        }
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created background node
 */
struct GraphNodeBackground *init_graph_node_background(struct AllocOnlyPool *pool,
                                                       struct GraphNodeBackground *graphNode,
                                                       u16 background, GraphNodeFunc backgroundFunc,
                                                       s32 zero) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeBackground));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->fnNode.node, GRAPH_NODE_TYPE_BACKGROUND);

        graphNode->background = (background << 16) | background;
        graphNode->fnNode.func = backgroundFunc;
        graphNode->unused = zero; // always 0, unused

        if (backgroundFunc != NULL) {
            backgroundFunc(GEO_CONTEXT_CREATE, &graphNode->fnNode.node, pool);
        }
    }

    return graphNode;
}

/**
 * Allocates and returns a newly created held object node
 */
struct GraphNodeHeldObject *init_graph_node_held_object(struct AllocOnlyPool *pool,
                                                        struct GraphNodeHeldObject *graphNode,
                                                        struct Object *objNode,
                                                        Vec3s translation,
                                                        GraphNodeFunc nodeFunc, s32 playerIndex) {
    if (pool != NULL) {
        graphNode = alloc_only_pool_alloc(pool, sizeof(struct GraphNodeHeldObject));
    }

    if (graphNode != NULL) {
        init_scene_graph_node_links(&graphNode->fnNode.node, GRAPH_NODE_TYPE_HELD_OBJ);
        vec3s_copy(graphNode->translation, translation);
        graphNode->objNode = objNode;
        graphNode->fnNode.func = nodeFunc;
        graphNode->playerIndex = playerIndex;

        if (nodeFunc != NULL) {
            nodeFunc(GEO_CONTEXT_CREATE, &graphNode->fnNode.node, pool);
        }
    }

    return graphNode;
}

/**
 * Adds 'childNode' to the end of the list children from 'parent'
 */
struct GraphNode *geo_add_child(struct GraphNode *parent, struct GraphNode *childNode) {
    struct GraphNode *parentFirstChild;
    struct GraphNode *parentLastChild;

    if (childNode != NULL) {
        childNode->parent = parent;
        parentFirstChild = parent->children;

        if (parentFirstChild == NULL) {
            parent->children = childNode;
            childNode->prev = childNode;
            childNode->next = childNode;
        } else {
            parentLastChild = parentFirstChild->prev;
            childNode->prev = parentLastChild;
            childNode->next = parentFirstChild;
            parentFirstChild->prev = childNode;
            parentLastChild->next = childNode;
        }
    }

    return childNode;
}

/**
 * Remove a node from the scene graph. It changes the links with its
 * siblings and with its parent, it doesn't deallocate the memory
 * since geo nodes are allocated in a pointer-bumping pool that
 * gets thrown out when changing areas.
 */
struct GraphNode *geo_remove_child(struct GraphNode *graphNode) {
    struct GraphNode *parent;
    struct GraphNode **firstChild;

    parent = graphNode->parent;
    firstChild = &parent->children;

    // Remove link with siblings
    graphNode->prev->next = graphNode->next;
    graphNode->next->prev = graphNode->prev;

    // If this node was the first child, a new first child must be chosen
    if (*firstChild == graphNode) {
        // The list is circular, so this checks whether it was the only child
        if (graphNode->next == graphNode) {
            *firstChild = NULL; // Parent has no children anymore
        } else {
            *firstChild = graphNode->next; // Choose a new first child
        }
    }

    return parent;
}

/**
 * Reorders the given node so it's the first child of its parent.
 * This is called on the Mario object when he is spawned. That's why Mario's
 * object is always drawn before any other objects. (Note that the geo order
 * is independent from processing group order, where Mario is not first.)
 */
struct GraphNode *geo_make_first_child(struct GraphNode *newFirstChild) {
    struct GraphNode *lastSibling;
    struct GraphNode *parent;
    struct GraphNode **firstChild;

    parent = newFirstChild->parent;
    firstChild = &parent->children;

    if (*firstChild != newFirstChild) {
        if ((*firstChild)->prev != newFirstChild) {
            newFirstChild->prev->next = newFirstChild->next;
            newFirstChild->next->prev = newFirstChild->prev;
            lastSibling = (*firstChild)->prev;
            newFirstChild->prev = lastSibling;
            newFirstChild->next = *firstChild;
            (*firstChild)->prev = newFirstChild;
            lastSibling->next = newFirstChild;
        }
        *firstChild = newFirstChild;
    }

    return parent;
}

/**
 * Helper function for geo_call_global_function_nodes that recursively
 * traverses the scene graph and calls the functions of global nodes.
 */
void geo_call_global_function_nodes_helper(struct GraphNode *graphNode, s32 callContext) {
    struct GraphNode **globalPtr;
    struct GraphNode *curNode;
    struct FnGraphNode *asFnNode;

    curNode = graphNode;

    do {
        asFnNode = (struct FnGraphNode *) curNode;

        if (curNode->type & GRAPH_NODE_TYPE_FUNCTIONAL) {
            if (asFnNode->func != NULL) {
                asFnNode->func(callContext, curNode, NULL);
            }
        }

        if (curNode->children != NULL) {
            switch (curNode->type) {
                case GRAPH_NODE_TYPE_MASTER_LIST:
                    globalPtr = (struct GraphNode **) &gCurGraphNodeMasterList;
                    break;
                case GRAPH_NODE_TYPE_PERSPECTIVE:
                    globalPtr = (struct GraphNode **) &gCurGraphNodeCamFrustum;
                    break;
                case GRAPH_NODE_TYPE_CAMERA:
                    globalPtr = (struct GraphNode **) &gCurGraphNodeCamera;
                    break;
                case GRAPH_NODE_TYPE_OBJECT:
                    globalPtr = (struct GraphNode **) &gCurGraphNodeObject;
                    break;
                default:
                    globalPtr = NULL;
                    break;
            }

            if (globalPtr != NULL) {
                *globalPtr = curNode;
            }

            geo_call_global_function_nodes_helper(curNode->children, callContext);

            if (globalPtr != NULL) {
                *globalPtr = NULL;
            }
        }
    } while ((curNode = curNode->next) != graphNode);
}

/**
 * Call the update functions of geo nodes that are stored in global variables.
 * These variables include gCurGraphNodeMasterList, gCurGraphNodeCamFrustum,
 * gCurGraphNodeCamera and gCurGraphNodeObject.
 * callContext is one of the GEO_CONTEXT_ defines.
 * The graphNode argument should be of type GraphNodeRoot.
 */
void geo_call_global_function_nodes(struct GraphNode *graphNode, s32 callContext) {
    if (graphNode->flags & GRAPH_RENDER_ACTIVE) {
        gCurGraphNodeRoot = (struct GraphNodeRoot *) graphNode;

        if (graphNode->children != NULL) {
            geo_call_global_function_nodes_helper(graphNode->children, callContext);
        }

        gCurGraphNodeRoot = 0;
    }
}

/**
 * When objects are cleared, this is called on all object nodes (loaded or unloaded).
 */
void geo_reset_object_node(struct GraphNodeObject *graphNode) {
    init_graph_node_object(NULL, graphNode, 0, gVec3fZero, gVec3sZero, gVec3fOne);

    geo_add_child(&gObjParentGraphNode, &graphNode->node);
    graphNode->node.flags &= ~GRAPH_RENDER_ACTIVE;
}

/**
 * Initialize an object node using the given parameters
 */
void geo_obj_init(struct GraphNodeObject *graphNode, void *sharedChild, Vec3f pos, Vec3s angle) {
    vec3f_set(graphNode->scale, 1.0f, 1.0f, 1.0f);
    vec3f_copy(graphNode->pos, pos);
    vec3s_copy(graphNode->angle, angle);

    graphNode->sharedChild = sharedChild;
    graphNode->unk4C = 0;
    graphNode->throwMatrix = NULL;
    graphNode->unk38.curAnim = NULL;

    graphNode->node.flags |= GRAPH_RENDER_ACTIVE;
    graphNode->node.flags &= ~GRAPH_RENDER_INVISIBLE;
    graphNode->node.flags |= GRAPH_RENDER_HAS_ANIMATION;
    graphNode->node.flags &= ~GRAPH_RENDER_BILLBOARD;
}

/**
 * Initialize and object node using the given SpawnInfo struct
 */
void geo_obj_init_spawninfo(struct GraphNodeObject *graphNode, struct SpawnInfo *spawn) {
    vec3f_set(graphNode->scale, 1.0f, 1.0f, 1.0f);
    vec3s_copy(graphNode->angle, spawn->startAngle);

    graphNode->pos[0] = (f32) spawn->startPos[0];
    graphNode->pos[1] = (f32) spawn->startPos[1];
    graphNode->pos[2] = (f32) spawn->startPos[2];

    graphNode->unk18 = spawn->areaIndex;
    graphNode->unk19 = spawn->activeAreaIndex;
    graphNode->sharedChild = spawn->unk18;
    graphNode->unk4C = spawn;
    graphNode->throwMatrix = NULL;
    graphNode->unk38.curAnim = 0;

    graphNode->node.flags |= GRAPH_RENDER_ACTIVE;
    graphNode->node.flags &= ~GRAPH_RENDER_INVISIBLE;
    graphNode->node.flags |= GRAPH_RENDER_HAS_ANIMATION;
    graphNode->node.flags &= ~GRAPH_RENDER_BILLBOARD;
}

/**
 * Initialize the animation of an object node
 */
void geo_obj_init_animation(struct GraphNodeObject *graphNode, struct Animation **animPtrAddr) {
    struct Animation **animSegmented = segmented_to_virtual(animPtrAddr);
    struct Animation *anim = segmented_to_virtual(*animSegmented);

    if (graphNode->unk38.curAnim != anim) {
        graphNode->unk38.curAnim = anim;
        graphNode->unk38.animFrame = anim->unk04 + ((anim->flags & ANIM_FLAG_FORWARD) ? 1 : -1);
        graphNode->unk38.animAccel = 0;
        graphNode->unk38.animYTrans = 0;
    }
}

/**
 * Initialize the animation of an object node
 */
void geo_obj_init_animation_accel(struct GraphNodeObject *graphNode, struct Animation **animPtrAddr, u32 animAccel) {
    struct Animation **animSegmented = segmented_to_virtual(animPtrAddr);
    struct Animation *anim = segmented_to_virtual(*animSegmented);

    if (graphNode->unk38.curAnim != anim) {
        graphNode->unk38.curAnim = anim;
        graphNode->unk38.animYTrans = 0;
        graphNode->unk38.animFrameAccelAssist =
            (anim->unk04 << 16) + ((anim->flags & ANIM_FLAG_FORWARD) ? animAccel : -animAccel);
        graphNode->unk38.animFrame = graphNode->unk38.animFrameAccelAssist >> 16;
    }

    graphNode->unk38.animAccel = animAccel;
}

/**
 * Retrieves an index into animation data based on the attribute pointer
 * An attribute is an x-, y- or z-component of the translation / rotation for a part
 * Each attribute is a pair of s16's, where the first s16 represents the maximum frame
 * and the second s16 the actual index. This index can be used to index in the array
 * with actual animation values.
 */
s32 retrieve_animation_index(s32 frame, u16 **attributes) {
    s32 result;

    if (frame < (*attributes)[0]) {
        result = (*attributes)[1] + frame;
    } else {
        result = (*attributes)[1] + (*attributes)[0] - 1;
    }

    *attributes += 2;

    return result;
}

/**
 * Update the animation frame of an object. The animation flags determine
 * whether it plays forwards or backwards, and whether it stops or loops at
 * the end etc.
 */
s16 geo_update_animation_frame(struct GraphNodeObject_sub *obj, s32 *accelAssist) {
    s32 result;
    struct Animation *anim;

    anim = obj->curAnim;

    if (obj->animTimer == gAreaUpdateCounter || anim->flags & ANIM_FLAG_2) {
        if (accelAssist != NULL) {
            accelAssist[0] = obj->animFrameAccelAssist;
        }

        return obj->animFrame;
    }

    if (anim->flags & ANIM_FLAG_FORWARD) {
        if (obj->animAccel) {
            result = obj->animFrameAccelAssist - obj->animAccel;
        } else {
            result = (obj->animFrame - 1) << 16;
        }

        if (GET_HIGH_S16_OF_32(result) < anim->unk06) {
            if (anim->flags & ANIM_FLAG_NOLOOP) {
                SET_HIGH_S16_OF_32(result, anim->unk06);
            } else {
                SET_HIGH_S16_OF_32(result, anim->unk08 - 1);
            }
        }
    } else {
        if (obj->animAccel != 0) {
            result = obj->animFrameAccelAssist + obj->animAccel;
        } else {
            result = (obj->animFrame + 1) << 16;
        }

        if (GET_HIGH_S16_OF_32(result) >= anim->unk08) {
            if (anim->flags & ANIM_FLAG_NOLOOP) {
                SET_HIGH_S16_OF_32(result, anim->unk08 - 1);
            } else {
                SET_HIGH_S16_OF_32(result, anim->unk06);
            }
        }
    }

    if (accelAssist != 0) {
        accelAssist[0] = result;
    }

    return GET_HIGH_S16_OF_32(result);
}

/**
 * Unused function to retrieve an object's current animation translation
 * Assumes that it has x, y and z data in animations, which isn't always the
 * case since some animation types only have vertical or lateral translation.
 * This might have been used for positioning the shadow under an object, which
 * currently happens in-line in geo_process_shadow where it also accounts for
 * animations without lateral translation.
 */
void geo_retreive_animation_translation(struct GraphNodeObject *obj, Vec3f position) {
    struct Animation *animation = obj->unk38.curAnim;
    u16 *attribute;
    s16 *values;
    s16 frame;

    if (animation != NULL) {
        attribute = segmented_to_virtual((void *) animation->index);
        values = segmented_to_virtual((void *) animation->values);

        frame = obj->unk38.animFrame;

        if (frame < 0) {
            frame = 0;
        }

        if (1) // ? necessary to match
        {
            position[0] = (f32) values[retrieve_animation_index(frame, &attribute)];
            position[1] = (f32) values[retrieve_animation_index(frame, &attribute)];
            position[2] = (f32) values[retrieve_animation_index(frame, &attribute)];
        }
    } else {
        vec3f_set(position, 0, 0, 0);
    }
}

/**
 * Unused function to find the root of the geo node tree, which should be a
 * GraphNodeRoot. If it is not for some reason, null is returned.
 */
struct GraphNodeRoot *geo_find_root(struct GraphNode *graphNode) {
    struct GraphNodeRoot *resGraphNode = NULL;

    while (graphNode->parent != NULL) {
        graphNode = graphNode->parent;
    }

    if (graphNode->type == GRAPH_NODE_TYPE_ROOT) {
        resGraphNode = (struct GraphNodeRoot *) graphNode;
    }

    return resGraphNode;
}
