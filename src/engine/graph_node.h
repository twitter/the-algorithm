#ifndef _GRAPH_NODE_H_
#define _GRAPH_NODE_H_

#include "types.h"
#include "game/memory.h"

struct AllocOnlyPool;

extern struct GraphNodeMasterList *gCurGraphNodeMasterList;
extern struct GraphNodePerspective *gCurGraphNodeCamFrustum;
extern struct GraphNodeCamera *gCurGraphNodeCamera;
extern struct GraphNodeHeldObject *gCurGraphNodeHeldObject;
extern u16 gAreaUpdateCounter;

extern struct GraphNode *gCurRootGraphNode;
extern struct GraphNode *gCurGraphNodeList[];

extern s16 gCurGraphNodeIndex;

extern struct GraphNode gObjParentGraphNode;

extern Vec3f gVec3fZero;
extern Vec3s gVec3sZero;
extern Vec3f gVec3fOne;
extern Vec3s gVec3sOne;

#define GRAPH_RENDER_ACTIVE         (1 << 0)
#define GRAPH_RENDER_CHILDREN_FIRST (1 << 1)
#define GRAPH_RENDER_BILLBOARD      (1 << 2)
#define GRAPH_RENDER_Z_BUFFER       (1 << 3)
#define GRAPH_RENDER_INVISIBLE      (1 << 4)
#define GRAPH_RENDER_HAS_ANIMATION  (1 << 5)

// Whether the node type has a function pointer of type GraphNodeFunc
#define GRAPH_NODE_TYPE_FUNCTIONAL            0x100

// Type used for Bowser and an unused geo function in obj_behaviors.c
#define GRAPH_NODE_TYPE_400                   0x400				

// The discriminant for different types of geo nodes
#define GRAPH_NODE_TYPE_ROOT                  0x001
#define GRAPH_NODE_TYPE_ORTHO_PROJECTION      0x002
#define GRAPH_NODE_TYPE_PERSPECTIVE          (0x003 | GRAPH_NODE_TYPE_FUNCTIONAL)
#define GRAPH_NODE_TYPE_MASTER_LIST           0x004
#define GRAPH_NODE_TYPE_START                 0x00A
#define GRAPH_NODE_TYPE_LEVEL_OF_DETAIL       0x00B
#define GRAPH_NODE_TYPE_SWITCH_CASE          (0x00C | GRAPH_NODE_TYPE_FUNCTIONAL)
#define GRAPH_NODE_TYPE_CAMERA               (0x014 | GRAPH_NODE_TYPE_FUNCTIONAL)
#define GRAPH_NODE_TYPE_TRANSLATION_ROTATION  0x015
#define GRAPH_NODE_TYPE_TRANSLATION           0x016
#define GRAPH_NODE_TYPE_ROTATION              0x017
#define GRAPH_NODE_TYPE_OBJECT                0x018
#define GRAPH_NODE_TYPE_ANIMATED_PART         0x019
#define GRAPH_NODE_TYPE_BILLBOARD             0x01A
#define GRAPH_NODE_TYPE_DISPLAY_LIST          0x01B
#define GRAPH_NODE_TYPE_SCALE                 0x01C
#define GRAPH_NODE_TYPE_SHADOW                0x028
#define GRAPH_NODE_TYPE_OBJECT_PARENT         0x029
#define GRAPH_NODE_TYPE_GENERATED_LIST       (0x02A | GRAPH_NODE_TYPE_FUNCTIONAL)
#define GRAPH_NODE_TYPE_BACKGROUND           (0x02C | GRAPH_NODE_TYPE_FUNCTIONAL)
#define GRAPH_NODE_TYPE_HELD_OBJ             (0x02E | GRAPH_NODE_TYPE_FUNCTIONAL)
#define GRAPH_NODE_TYPE_CULLING_RADIUS        0x02F

// The number of master lists. A master list determines the order and render
// mode with which display lists are drawn.
#define GFX_NUM_MASTER_LISTS 8

// Passed as first argument to a GraphNodeFunc to give information about in
// which context it was called and what it is expected to do.
#define GEO_CONTEXT_CREATE        0 // called when node is created from a geo command
#define GEO_CONTEXT_RENDER        1 // called from rendering_graph_node.c
#define GEO_CONTEXT_AREA_UNLOAD   2 // called when unloading an area
#define GEO_CONTEXT_AREA_LOAD     3 // called when loading an area
#define GEO_CONTEXT_AREA_INIT     4 // called when initializing the 8 areas
#define GEO_CONTEXT_HELD_OBJ      5 // called when processing a GraphNodeHeldObj

// The signature for a function stored in a geo node
// The context argument depends on the callContext:
// - for GEO_CONTEXT_CREATE it is the AllocOnlyPool from which the node was allocated
// - for GEO_CONTEXT_RENDER or GEO_CONTEXT_HELD_OBJ it is the top of the float matrix stack with type Mat4
// - for GEO_CONTEXT_AREA_* it is the root geo node
typedef Gfx *(*GraphNodeFunc)(s32 callContext, struct GraphNode *node, void *context);

/** An extension of a graph node that includes a function pointer.
 *  Many graph node types have an update function that gets called
 *  when they are processed.
 */
struct FnGraphNode
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ GraphNodeFunc func;
};

/** The very root of the geo tree. Specifies the viewport.
 */
struct GraphNodeRoot
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ u8 areaIndex;
    /*0x15*/ s8 unk15; // ?
    /*0x16*/ s16 x;
    /*0x18*/ s16 y;
    /*0x1A*/ s16 width; // half width, 160
    /*0x1C*/ s16 height; // half height
    /*0x1E*/ s16 numViews; // number of entries in mystery array
    /*0x20*/ struct GraphNode **views;
};

/** A node that sets up an orthographic projection based on the global
 *  root node. Used to draw the skybox image.
 */
struct GraphNodeOrthoProjection
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ f32 scale;
};

/** A node that sets up a perspective projection. Used for drawing the
 *  game world. It does not set up the camera position, that is done by
 *  the child of this node, which has type GraphNodeCamera.
 */
struct GraphNodePerspective
{
    /*0x00*/ struct FnGraphNode fnNode;
    /*0x18*/ s32 unused;
    /*0x1C*/ f32 fov;   // horizontal field of view in degrees
    /*0x20*/ s16 near;  // near clipping plane
    /*0x22*/ s16 far;   // far clipping plane
};

/** An entry in the master list. It is a linked list of display lists
 *  carrying a transformation matrix.
 */
struct DisplayListNode
{
    void *transform;
    void *displayList;
    struct DisplayListNode *next;
};

/** GraphNode that manages the 8 top-level display lists that will be drawn
 *  Each list has its own render mode, so for example water is drawn in a
 *  different master list than opaque objects.
 *  It also sets the z-buffer on before rendering and off after.
 */
struct GraphNodeMasterList
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ struct DisplayListNode *listHeads[GFX_NUM_MASTER_LISTS];
    /*0x34*/ struct DisplayListNode *listTails[GFX_NUM_MASTER_LISTS];
};

/** Simply used as a parent to group multiple children.
 *  Does not have any additional functionality.
 */
struct GraphNodeStart
{
    /*0x00*/ struct GraphNode node;
};

/** GraphNode that only renders its children if the current transformation matrix
 *  has a z-translation (in camera space) greater than minDistance and less than
 *  maxDistance.
 *  Usage examples: Mario has three level's of detail: Normal, low-poly arms only, and fully low-poly
 *  The tower in Whomp's fortress has two levels of detail.
 */
struct GraphNodeLevelOfDetail
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ s16 minDistance;
    /*0x16*/ s16 maxDistance;
};

/** GraphNode that renders exactly one of its children.
 *  Which one is rendered is determined by the field 'selectedCase'
 *  which is set in the node's function.
 *  Usage examples: room visibility, coin animation, blinking, Mario's power-up / hand pose / cap
 */
struct GraphNodeSwitchCase
{
    /*0x00*/ struct FnGraphNode fnNode;
    /*0x18*/ s32 unused;
    /*0x1C*/ s16 numCases;
    /*0x1E*/ s16 selectedCase;
};

/**
 * GraphNode that specifies the location and aim of the camera.
 * When the roll is 0, the up vector is (0, 1, 0).
 */
struct GraphNodeCamera
{
    /*0x00*/ struct FnGraphNode fnNode;
    /*0x18*/ union {
        // When the node is created, a mode is assigned to the node.
        // Later in geo_camera_main a Camera is allocated,
        // the mode is passed to the struct, and the field is overridden
        // by a pointer to the struct. Gotta save those 4 bytes.
        s32 mode;
        struct Camera *camera;
    } config;
    /*0x1C*/ Vec3f pos;
    /*0x28*/ Vec3f focus;
    /*0x34*/ void *matrixPtr; // pointer to look-at matrix of this camera as a Mat4
    /*0x38*/ s16 roll; // roll in look at matrix. Doesn't account for light direction unlike rollScreen.
    /*0x3A*/ s16 rollScreen; // rolls screen while keeping the light direction consistent
};

/** GraphNode that translates and rotates its children.
 *  Usage example: wing cap wings.
 *  There is a dprint function that sets the translation and rotation values
 *  based on the ENEMYINFO array.
 *  The display list can be null, in which case it won't draw anything itself.
 */
struct GraphNodeTranslationRotation
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
    /*0x18*/ Vec3s translation;
    /*0x1E*/ Vec3s rotation;
};

/** GraphNode that translates itself and its children.
 *  Usage example: SUPER MARIO logo letters in debug level select.
 *  The display list can be null, in which case it won't draw anything itself.
 */
struct GraphNodeTranslation
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
    /*0x18*/ Vec3s translation;
    u8 pad1E[2];
};

/** GraphNode that rotates itself and its children.
 *  Usage example: Mario torso / head rotation. Its parameters are dynamically
 *  set by a parent script node in that case.
 *  The display list can be null, in which case it won't draw anything itself.
 */
struct GraphNodeRotation
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
    /*0x18*/ Vec3s rotation;
    u8 pad1E[2];
};

/** GraphNode part that transforms itself and its children based on animation
 *  data. This animation data is not stored in the node itself but in global
 *  variables that are set when object nodes are processed if the object has
 *  animation.
 *  Usef for Mario, enemies and anything else with animation data.
 *  The display list can be null, in which case it won't draw anything itself.
 */
struct GraphNodeAnimatedPart
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
    /*0x18*/ Vec3s translation;
};

/** A GraphNode that draws a display list rotated in a way to always face the
 *  camera. Note that if the entire object is a billboard (like a coin or 1-up)
 *  then it simply sets the billboard flag for the entire object, this node is
 *  used for billboard parts (like a chuckya or goomba body).
 */
struct GraphNodeBillboard
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
    /*0x18*/ Vec3s translation;
};

/** A GraphNode that simply draws a display list without doing any
 *  transformation beforehand. It does inherit the parent's transformation.
 */
struct GraphNodeDisplayList
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
};

/** GraphNode part that scales itself and its children.
 *  Usage example: Mario's fist or shoe, which grows when attacking. This can't
 *  be done with an animated part sine animation data doesn't support scaling.
 *  Note that many scaling animations (like a goomba getting stomped) happen on
 *  the entire object. This node is only used when a single part needs to be scaled.
 *  There is also a level command that scales the entire level, used for THI.
 *  The display list can be null, in which case it won't draw anything itself.
 */
struct GraphNodeScale
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ void *displayList;
    /*0x18*/ f32 scale;
};

/** GraphNode that draws a shadow under an object.
 *  Every object starts with a shadow node.
 *  The shadow type determines the shape (round or rectangular), vertices (4 or 9)
 *  and other features.
 */
struct GraphNodeShadow
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ s16 shadowScale; // diameter (when a circle) or side (when a square) of shadow
    /*0x16*/ u8 shadowSolidity; // opacity of shadow, 255 = opaque
    /*0x17*/ u8 shadowType; // see ShadowType enum in shadow.h
};

/** GraphNode that contains as its sharedChild a group node containing all
 *  object nodes.
 */
struct GraphNodeObjectParent
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ struct GraphNode *sharedChild;
};

/** GraphNode that draws display lists not directly in memory but generated by
 *  a function.
 *  Used for wobbling paintings, water, environment effects.
 *  It might not draw anything, it could also just update something.
 *  For example: there is a node that stops water flow when the game is paused.
 *  The parameter field gives extra context info. For shifting sand or paintings,
 *  it can determine which texture to use.
 */
struct GraphNodeGenerated
{
    /*0x00*/ struct FnGraphNode fnNode;
    /*0x18*/ u32 parameter; // extra context for the function
};

/** GraphNode that draws a background image or a rectangle of a color.
 *  Drawn in an orthgraphic projection, used for skyboxes.
 */
struct GraphNodeBackground
{
    /*0x00*/ struct FnGraphNode fnNode;
    /*0x18*/ s32 unused;
    /*0x1C*/ s32 background; // background ID, or rgba5551 color if fnNode.func is null
};

/** Renders the object that Mario is holding.
 */
struct GraphNodeHeldObject
{
    /*0x00*/ struct FnGraphNode fnNode;
    /*0x18*/ s32 playerIndex;
    /*0x1C*/ struct Object *objNode;
    /*0x20*/ Vec3s translation;
};

/** A node that allows an object to specify a different culling radius than the
 *  default one of 300. For this to work, it needs to be a direct child of the
 *  object node. Used for very large objects, such as shockwave rings that Bowser
 *  creates, tornados, the big eel.
 */
struct GraphNodeCullingRadius
{
    /*0x00*/ struct GraphNode node;
    /*0x14*/ s16 cullingRadius; // specifies the 'sphere radius' for purposes of frustrum culling
    u8 pad1E[2];
};

void init_scene_graph_node_links(struct GraphNode *, s32);

struct GraphNodeRoot *init_graph_node_root(struct AllocOnlyPool *, struct GraphNodeRoot *,
    s16, s16 x, s16 y, s16 width, s16 height);
struct GraphNodeOrthoProjection *init_graph_node_ortho_projection(struct AllocOnlyPool *, struct GraphNodeOrthoProjection *, f32);
struct GraphNodePerspective *init_graph_node_perspective(struct AllocOnlyPool *pool, struct GraphNodePerspective *sp1c,
    f32 sp20, s16 sp26, s16 sp2a, GraphNodeFunc sp2c, s32 sp30);
struct GraphNodeStart *init_graph_node_start(struct AllocOnlyPool *pool, struct GraphNodeStart *sp1c);
struct GraphNodeMasterList *init_graph_node_master_list(struct AllocOnlyPool *pool, struct GraphNodeMasterList *, s16 sp22);
struct GraphNodeLevelOfDetail *init_graph_node_render_range(struct AllocOnlyPool *pool, struct GraphNodeLevelOfDetail *graphNode,
    s16 minDistance, s16 maxDistance);
struct GraphNodeSwitchCase *init_graph_node_switch_case(struct AllocOnlyPool *pool, struct GraphNodeSwitchCase *graphNode,
    s16 numCases, s16 sp26, GraphNodeFunc nodeFunc, s32 sp2c);
struct GraphNodeCamera *init_graph_node_camera(struct AllocOnlyPool *pool, struct GraphNodeCamera * sp1c,
    f32 *sp20, f32 *sp24, GraphNodeFunc sp28, s32 sp2c);
struct GraphNodeTranslationRotation *init_graph_node_translation_rotation(struct AllocOnlyPool *pool,
    struct GraphNodeTranslationRotation *graphNode, s32 drawingLayer, void *displayList, Vec3s sp28, Vec3s sp2c);
struct GraphNodeTranslation *init_graph_node_translation(struct AllocOnlyPool *pool, struct GraphNodeTranslation *graphNode,
    s32 drawingLayer, void *displayList, Vec3s sp28);
struct GraphNodeRotation *init_graph_node_rotation(struct AllocOnlyPool *pool, struct GraphNodeRotation *graphNode,
    s32 drawingLayer, void *displayList, Vec3s sp28);
struct GraphNodeScale *init_graph_node_scale(struct AllocOnlyPool *pool,
    struct GraphNodeScale *graphNode, s32 drawingLayer, void *displayList, f32 sp28);
struct GraphNodeObject *init_graph_node_object(struct AllocOnlyPool *pool, struct GraphNodeObject *graphNode,
    struct GraphNode *sp20, Vec3f pos, Vec3s angle, Vec3f scale);
struct GraphNodeCullingRadius *init_graph_node_culling_radius(struct AllocOnlyPool *pool, struct GraphNodeCullingRadius *sp1c,
    s16 sp22);
struct GraphNodeAnimatedPart *init_graph_node_animated_part(struct AllocOnlyPool *pool, struct GraphNodeAnimatedPart *graphNode,
    s32 drawingLayer, void *displayList, Vec3s relativePos);
struct GraphNodeBillboard *init_graph_node_billboard(struct AllocOnlyPool *pool,
    struct GraphNodeBillboard *graphNode, s32 drawingLayer, void *displayList, Vec3s sp28);
struct GraphNodeDisplayList *init_graph_node_display_list(struct AllocOnlyPool *pool, struct GraphNodeDisplayList *graphNode,
    s32 drawingLayer, void *displayList);
struct GraphNodeShadow *init_graph_node_shadow(struct AllocOnlyPool *pool, struct GraphNodeShadow *sp1c,
    s16 sp22, u8 sp27, u8 sp2b);
struct GraphNodeObjectParent *init_graph_node_object_parent(struct AllocOnlyPool *pool, struct GraphNodeObjectParent *sp1c,
    struct GraphNode *sp20);
struct GraphNodeGenerated *init_graph_node_generated(struct AllocOnlyPool *pool, struct GraphNodeGenerated *sp1c,
    GraphNodeFunc sp20, s32 sp24);
struct GraphNodeBackground *init_graph_node_background(struct AllocOnlyPool *pool, struct GraphNodeBackground *sp1c,
    u16 sp22, GraphNodeFunc sp24, s32 sp28);
struct GraphNodeHeldObject *init_graph_node_held_object(struct AllocOnlyPool *pool, struct GraphNodeHeldObject *sp1c,
    struct Object *objNode, Vec3s translation, GraphNodeFunc nodeFunc, s32 playerIndex);

struct GraphNode *geo_add_child(struct GraphNode *, struct GraphNode *);
struct GraphNode *geo_remove_child(struct GraphNode *);
struct GraphNode *geo_make_first_child(struct GraphNode *a0);

void geo_call_global_function_nodes_helper(struct GraphNode *, s32);
void geo_call_global_function_nodes(struct GraphNode *graphNode, s32 sp1c);

void geo_reset_object_node(struct GraphNodeObject *sp20);
void geo_obj_init(struct GraphNodeObject *sp18, void *sp1c, Vec3f sp20, Vec3s sp24);
void geo_obj_init_spawninfo(struct GraphNodeObject *sp18, struct SpawnInfo *sp1c);
void geo_obj_init_animation(struct GraphNodeObject *graphNode, struct Animation **animPtrAddr);
void geo_obj_init_animation_accel(struct GraphNodeObject *graphNode, struct Animation **animPtrAddr, u32 animAccel);

s32 retrieve_animation_index(s32 a0, u16 **a1);

s16 geo_update_animation_frame(struct GraphNodeObject_sub *a0, s32* a1);
void geo_retreive_animation_translation(struct GraphNodeObject *sp28, Vec3f sp2c);

struct GraphNodeRoot *geo_find_root(struct GraphNode *graphNode);

s16 *read_vec3s_to_vec3f(Vec3f, s16 *src);
s16 *read_vec3s(Vec3s dst, s16 *src);
s16 *read_vec3s_angle(Vec3s dst, s16 *src);

void register_scene_graph_node(struct GraphNode *);

#endif /* _GRAPH_NODE_H_ */
