#ifndef GD_DYNLIST_MACROS_H
#define GD_DYNLIST_MACROS_H

/* DynListCmd Macros */

/**
 * Must be the first command in a dynlist.
 */
#define BeginList() \
    { 53716, {0}, {0}, {0.0, 0.0, 0.0} }

/**
 * Must be the last command in a dynlist.
 */
#define EndList() \
    { 58, {0}, {0}, {0.0, 0.0, 0.0} }

/**
 * If `enable` is TRUE, then subsequent object names are treated as integers
 * rather than strings.
 */
#define UseIntegerNames(enable) \
    { 0, {0}, {(void *)(enable)}, {0.0, 0.0, 0.0} }

/**
 * Set the initial position of the current object
 * Supported Objs: joints, particles, nets, vertices, cameras 
 */
#define SetInitialPosition(x, y, z) \
    { 1, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the relative position of the current object
 * Supported Objs: joints, particles, vertices, cameras, labels
 */
#define SetRelativePosition(x, y, z) \
    { 2, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the world position of the current object
 * Supported Objs: joints, nets, vertices, cameras, gadgets, views
 */
#define SetWorldPosition(x, y, z) \
    { 3, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the normal of the current object
 * Supported Objs: vertices
 */
#define SetNormal(x, y, z) \
    { 4, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the scale of the current object
 * Supported Objs: joints, particles, nets, gadgets, views, lights
 */
#define SetScale(x, y, z) \
    { 5, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the rotation of the current object
 * Supported Objs: joints, nets
 */
#define SetRotation(x, y, z) \
    { 6, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the specified bits in the object's `drawFlags` field
 * Supported Objs: all
 */
#define SetDrawFlag(flags) \
    { 7, {0}, {(void *)(flags)}, {0.0, 0.0, 0.0} }

/**
 * Set the specified bits in the object specific flag
 * Supported Objs: bones, joints, particles, shapes, nets, cameras, views, lights
 */
#define SetFlag(flags) \
    { 8, {0}, {(void *)(flags)}, {0.0, 0.0, 0.0} }

/**
 * Clear the specified bits in the object specific flag
 * Supported Objs: bones, joints, particles, nets, cameras
 */
#define ClearFlag(flags) \
    { 9, {0}, {(void *)(flags)}, {0.0, 0.0, 0.0} }

/**
 * Set the friction vector of a Joint
 * Supported Objs: joints
 */
#define SetFriction(x, y, z) \
    { 10, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the spring value of a Bone
 * Supported Objs: bones
 */
#define SetSpring(spring) \
    { 11, {0}, {0}, {(spring), 0.0, 0.0} }

/**
 * Jump to pointed dynlist. Once that list has finished processing, flow returns
 * to the current list.
 */
#define CallList(list) \
    { 12, {(void *)(list)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Sets the object's color to one of the predefined colors (see draw_objects.h
 * for the list of colors.
 * Supported Objs: joints, particles, nets, faces, gadgets
 */
#define SetColourNum(colourNum) \
    { 13, {0}, {(void *)(colourNum)}, {0.0, 0.0, 0.0} }

/**
 * Make an object of the specified type and name, and set it as the current
 * object.
 */
#define MakeDynObj(type, name) \
    { 15, {(void *)(name)}, {(void *)(type)}, {0.0, 0.0, 0.0} }

/**
 * Make a group that will contain all subsequently created objects once the
 * EndGroup command is called.
 */
#define StartGroup(grpName) \
    { 16, {(void *)(grpName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * End a group. All objects created between StartGroup and EndGroup are added to
 * the group.
 */
#define EndGroup(grpName) \
    { 17, {(void *)(grpName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Add the current object to the specified group.
 * Supported Objs: all
 */
#define AddToGroup(grpName) \
    { 18, {(void *)(grpName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set an object specific type flag.
 * Supported Objs: groups, joints, particles, nets, materials, gadgets
 */
#define SetType(type) \
    { 19, {0}, {(void *)(type)}, {0.0, 0.0, 0.0} }

/**
 * Set the current shape's material group to the specified group. 
 * Supported Objs: shapes
 */
#define SetMaterialGroup(mtlGrpName) \
    { 20, {(void *)(mtlGrpName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Assign the specified group to the current object. The purpose of the group
 * depends on the current object's type. For shapes, it sets the vertex data.
 * For animators, it sets the animation data. For nets, it sets ???. For
 * gadgets, it sets ???.
 * Supported Objs: shapes, nets, gadgets, animators
 */
#define SetNodeGroup(grpName) \
    { 21, {(void *)(grpName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the skin group of the current Net object with the vertices from the
 * specified shape.
 * Supported Objs: nets
 */
#define SetSkinShape(shapeName) \
    { 22, {(void *)(shapeName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the plane (face) group of the current object.
 * Supported Objs: shapes, nets
 */
#define SetPlaneGroup(planeGrpName) \
    { 23, {(void *)(planeGrpName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the current object's shape, where `shapePtr` is a pointer to an
 * `ObjShape`.
 * Supported Objs: bones, joints, particles, nets, gadgets, lights
 */
#define SetShapePtrPtr(shapePtr) \
    { 24, {(void *)(shapePtr)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the current object's shape, where `shapeName` is the name of a shape
 * object.
 * Supported Objs: bones, joints, particles, nets, gadgets
 */
#define SetShapePtr(shapeName) \
    { 25, {(void *)(shapeName)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set offset of the connected shape
 * Supported Objs: joints
 */
#define SetShapeOffset(x, y, z) \
    { 26, {0}, {0}, {(x), (y), (z)} }

/**
 * Set the center of gravity of the current Net object
 * Supported Objs: nets
 */
#define SetCenterOfGravity(x, y, z) \
    { 27, {0}, {0}, {(x), (y), (z)} }

// TODO:

/* Link Object ID to the current dynobj */
/* Supported Objs: groups, bones, faces, cameras, views, labels, animators */
#define LinkWith(w1) \
    { 28, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Link Object pointer to the current dynobj */
/* Supported Objs: groups, bones, faces, cameras, views, labels, animators */
#define LinkWithPtr(w1) \
    { 29, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the specified object as the current object.
 * Supported Objs: all
 */
#define UseObj(name) \
    { 30, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the current Net object's control type field. Control type is never used
 * for anything, so this command effectively does nothing.
 * Supported Objs: nets
 */
#define SetControlType(w2) \
    { 31, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/**
 * Set the weight percentage of the specified vertex controlled by the current
 * Joint object.
 * Supported Objs: joints
 */
#define SetSkinWeight(vtxNum, weight) \
    { 32, {0}, {(void *)(vtxNum)}, {(weight), 0.0, 0.0} }

/**
 * Set the ambient color of the current Material object.
 * Supported Objs: materials
 */
#define SetAmbient(r, g, b) \
    { 33, {0}, {0}, {(r), (g), (b)} }

/**
 * Set the diffuse color of the current Material or Light object.
 * Supported Objs: materials, lights
 */
#define SetDiffuse(r, g, b) \
    { 34, {0}, {0}, {(r), (g), (b)} }

/**
 * Set the object specific ID field.
 * Supported Objs: joints, vertices, materials, lights
 */
#define SetId(id) \
    { 35, {0}, {(void *)(id)}, {0.0, 0.0, 0.0} }

/**
 * Set the material id of the current Face
 * Supported Objs: faces
 */
#define SetMaterial(id) \
    { 36, {0}, {(void *)(id)}, {0.0, 0.0, 0.0} }

/**
 * For all faces in the current Group, resolve their material IDs to actual
 * `ObjMaterial`s.
 * Supported Objs: groups
 */
#define MapMaterials(name) \
    { 37, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * For all faces in the current Group, resolve their vertex indices to pointers
 * to actual `ObjVertex`es. Calculate normals for all vertices in the the group
 * specified by `name`
 * Supported Objs: groups
 */
#define MapVertices(name) \
    { 38, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Stub command (does nothing).
 * Supported Objs: joints
 */
#define Attach(name) \
    { 39, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Attach the current object to the specified object, using the specified flags.
 * Supported Objs: joints, particles, nets, animators
 */
#define AttachTo(flags, name) \
    { 40, {(void *)(name)}, {(void *)(flags)}, {0.0, 0.0, 0.0} }

/**
 * Set the point at which the current object is attached to its parent object
 * Supported Objs: joints, particles, nets
 */
#define SetAttachOffset(x, y, z) \
    { 41, {0}, {0}, {(x), (y), (z)} }

/**
 * Set a "suffix" to use with dynobj names. All commands that take a name as a
 * parameter will have this suffix appended to the name.
 */
#define SetNameSuffix(suffix) \
    { 43, {(void *)(suffix)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Set the float paramter `param` to `value`.
 * For Shapes, the following parameters are supported:
 *     PARM_F_ALPHA - the alpha (opacity) of the shape
 * For Gadgets, the following parameters are supported:
 *     PARM_F_RANGE_MIN - the minimum value of the gadget
 *     PARM_F_RANGE_MAX - the maximum value of the gadget
 *     PARM_F_VARVAL - the current value of the gadget
 * For Vertices, the following parameters are supported:
 *     PARM_F_ALPHA - the alpha (opacity) of the vertex
 * Supported Objs: shapes, vertices, gadgets
 */
#define SetParamF(param, value) \
    { 44, {0}, {(void *)(param)}, {(value), 0.0, 0.0} }

/**
 * Set pointer paramter `param` to `value`
 * For Labels, the following parameters are supported:
 *     PARM_PTR_CHAR - the format string for the label text
 * For Views, the following parameters are supported:
 *     PARM_PTR_CHAR - the name of the view
 * For Faces, the following parameters are supported:
 *     PARM_PTR_OBJ_VTX - (not actually a pointer) index of a vertex created with `MakeVertex`.
 * Supported Objs: faces, views, labels */
#define SetParamPtr(param, value) \
    { 45, {(void *)(value)}, {(void *)(param)}, {0.0, 0.0, 0.0} }

/**
 * Create a Net with the specified name, and add a group to it.
 */
#define MakeNetWithSubGroup(name) \
    { 46, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Make a Joint and attach it to the Net created with "MakeNetWithSubGroup".
 */
#define MakeAttachedJoint(name) \
    { 47, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * End a Net that was created with "MakeNetWithSubGroup"
 */
#define EndNetWithSubGroup(name) \
    { 48, {(void *)(name)}, {0}, {0.0, 0.0, 0.0} }

/**
 * Add a Vertex dynobj
 */
#define MakeVertex(x, y, z) \
    { 49, {0}, {0}, {(x), (y), (z)} }

/**
 * Add a ValPtr dynobj
 */
#define MakeValPtr(id, flags, type, offset) \
    { 50, {(void *)(id)}, {(void *)(type)}, {(offset), (flags), 0.0} }

/**
 * Set the texture of the current Material dynobj. Note that textures are not
 * actually supported.
 * Supported Objs: materials
 */
#define UseTexture(texture) \
    { 52, {0}, {(void *)(texture)}, {0.0, 0.0, 0.0} }

/**
 * Stub command (does nothing).
 * Supported Objs: vertices
 */
#define SetTextureST(s, t) \
    { 53, {0}, {0}, {(s), (t), 0.0} }

/* Make a new Net from Shape ID */
#define MakeNetFromShape(shape) \
    { 54, {(void *)(shape)}, {0}, {0.0, 0.0, 0.0} }

/* Make a new Net from Shape double pointer PTR */
#define MakeNetFromShapePtrPtr(w1) \
    { 55, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

#endif // GD_DYNLIST_MACROS_H
