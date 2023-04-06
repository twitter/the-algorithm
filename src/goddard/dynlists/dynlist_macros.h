#ifndef _DYN_LIST_MACROS_H_
#define _DYN_LIST_MACROS_H_

/* DynListCmd Macros */
/* Necessary start command for the dynlist. List will not process otherwise. */
#define StartList() \
    { 53716, {0}, {0}, {0.0, 0.0, 0.0} }

/* Necessary stop command for the dynlist. */
#define StopList() \
    { 58, {0}, {0}, {0.0, 0.0, 0.0} }

/* Subsequent dynobj ids should be treated as ints, not as C string pointers. */
#define UseIntId(w2) \
    { 0, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the initial position of the current object */
/* Supported Objs: joints, particles, nets, vertices, cameras */
#define SetInitialPosition(x, y, z) \
    { 1, {0}, {0}, {(x), (y), (z)} }

/* Set the relative position of the current object */
/* Supported Objs: joints, particles, vertices, cameras, labels */
#define SetRelativePosition(x, y, z) \
    { 2, {0}, {0}, {(x), (y), (z)} }

/* Set the world position of the current object */
/* Supported Objs: joints, nets, vertices, cameras, gadgets, views */
#define SetWorldPosition(x, y, z) \
    { 3, {0}, {0}, {(x), (y), (z)} }

/* Set the normal of the current object */
/* Supported Objs: vertices */
#define SetNormal(x, y, z) \
    { 4, {0}, {0}, {(x), (y), (z)} }

/* Set the scale of the current object */
/* Supported Objs: joints, particles, nets, gadgets, views, lights */
#define SetScale(x, y, z) \
    { 5, {0}, {0}, {(x), (y), (z)} }

/* Set the rotation of the current object */
/* Supported Objs: joints, nets */
#define SetRotation(x, y, z) \
    { 6, {0}, {0}, {(x), (y), (z)} }

/* Set the half-word flag in the header of the current dynobj */
/* Supported Objs: all */
#define SetHeaderFlag(w2) \
    { 7, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the bits in an object specific flag with the provided flag */
/* Supported Objs: bones, joints, particles, shapes, nets, cameras, views, lights */
#define SetFlag(w2) \
    { 8, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Clear the bits in an object specific flag with the provided flag */
/* Supported Objs: bones, joints, particles, nets, cameras */
#define ClearFlag(w2) \
    { 9, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the friction vector of a Joint */
/* Supported Objs: joints */
#define SetFriction(x, y, z) \
    { 10, {0}, {0}, {(x), (y), (z)} }

/* Set the spring f32 of a Bone */
/* Supported Objs: bones */
#define SetSpring(x) \
    { 11, {0}, {0}, {(x), 0.0, 0.0} }

/* Jump to pointed dynlist. Once that list has finished processing, flow returns to current list. */
#define JumpToList(w1) \
    { 12, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Store either the enumerated "colour" number in an object, or the RGB f32 values the number refers to */
/* Supported Objs: joints, particles, nets, faces, gadgets */
#define SetColourNum(w2) \
    { 13, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Make an object of the specified type and id, and add that object to the dynobj pool. */
#define MakeDynObj(w2, w1) \
    { 15, {(void *)(w1)}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Make a group object that will contain all subsequently created objects once the EndGroup command is called. */
#define StartGroup(w1) \
    { 16, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Collect all objects created after the StartGroup command with the same id. */
/* Supported Objs: groups */
#define EndGroup(w1) \
    { 17, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Add the current dyn object to the Group with the called ID */
/* Supported Objs: groups */
#define AddToGroup(w1) \
    { 18, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set an object specific type flag. */
/* Supported Objs: groups, joints, particles, nets, materials, gadgets */
#define SetType(w2) \
    { 19, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Assign the material Group ID to the current dynobj Shape and check the Shape */
/* Supported Objs: shapes */
#define SetMaterialGroup(w1) \
    { 20, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Attach Group ID to the current dynobj */
/* Supported Objs: shapes, nets, gadgets, animators */
#define SetNodeGroup(w1) \
    { 21, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set the skin group of the current Net dynobj with the vertices from Shape ID */
/* Supported Objs: nets */
#define SetSkinShape(w1) \
    { 22, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set the plane group ID of the current dynobj */
/* Supported Objs: shapes, nets */
#define SetPlaneGroup(w1) \
    { 23, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set the current dynobj's shape pointer by dereferencing the ptr ptr */
/* Supported Objs: bones, joints, particles, nets, gadgets, lights */
#define SetShapePtrPtr(w1) \
    { 24, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set the current dynobj's shape pointer to Shape ID */
/* Supported Objs: bones, joints, particles, nets, gadgets */
#define SetShapePtr(w1) \
    { 25, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set offset of the connected shape */
/* Supported Objs: joints */
#define SetShapeOffset(x, y, z) \
    { 26, {0}, {0}, {(x), (y), (z)} }

/* Set the center of gravity of the current Net object */
/* Supported Objs: nets */
#define SetCenterOfGravity(x, y, z) \
    { 27, {0}, {0}, {(x), (y), (z)} }

/* Link Object ID to the current dynobj */
/* Supported Objs: groups, bones, faces, cameras, views, labels, animators */
#define LinkWith(w1) \
    { 28, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Link Object pointer to the current dynobj */
/* Supported Objs: groups, bones, faces, cameras, views, labels, animators */
#define LinkWithPtr(w1) \
    { 29, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set Object ID as the current dynobj */
/* Supported Objs: all */
#define UseObj(w1) \
    { 30, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set the current Net object's control type field */
/* Supported Objs: nets */
#define SetControlType(w2) \
    { 31, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the weight of the current Joint object with ID and VALUE */
/* Supported Objs: joints */
#define SetSkinWeight(w2, x) \
    { 32, {0}, {(void *)(w2)}, {(x), 0.0, 0.0} }

/* Set the ambient color of the current Material object */
/* Supported Objs: materials */
#define SetAmbient(x, y, z) \
    { 33, {0}, {0}, {(x), (y), (z)} }

/* Set the diffuse color of the current Material or Light object */
/* Supported Objs: materials, lights */
#define SetDiffuse(x, y, z) \
    { 34, {0}, {0}, {(x), (y), (z)} }

/* Set the numerical Object ID field (not dynobj id) */
/* Supported Objs: joints, vertices, materials, lights */
#define SetId(w2) \
    { 35, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the material id of the current Face */
/* Supported Objs: faces */
#define SetMaterial(w1, w2) \
    { 36, {(void *)(w1)}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Map Materials from Group ID to the current Group obj */
/* Supported Objs: groups */
#define MapMaterials(w1) \
    { 37, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Map vertices from Group ID to the current Group obj, and correct any vertex indices to pointers */
/* Supported Objs: groups */
#define MapVertices(w1) \
    { 38, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Stub command */
/* Supported Objs: joints */
#define Attach(w1) \
    { 39, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Attach the current dynobj with modifications made by FLAG to Object ID */
/* Supported Objs: joints, particles, nets, animators */
#define AttachTo(w2, w1) \
    { 40, {(void *)(w1)}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the offset of the attached object for the current dynobj */
/* Supported Objs: joints, particles, nets */
#define SetAttachOffset(x, y, z) \
    { 41, {0}, {0}, {(x), (y), (z)} }

/* Copy the C-string pointed to by PTR to the dynobj id buf */
#define CopyStrToIdBuf(w1) \
    { 43, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Set f32 paramter PARAM to VALUE. TODO: Ennumerate parameters */
/* Supported Objs: shapes, vertices, gadgets */
#define SetParamF(w2, x) \
    { 44, {0}, {(void *)(w2)}, {(x), 0.0, 0.0} }

/* Set pointer paramter PARAM to PTR */
/* Supported Objs: faces, views, labels */
#define SetParamPtr(w2, w1) \
    { 45, {(void *)(w1)}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Create a Net and a subgroup with ID. ARG1 is not used */
#define MakeNetWithSubGroup(w2, w1) \
    { 46, {(void *)(w1)}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Make Joint ID and attach the Net created with "MakeNetWithSubGroup". ARG1 is not used */
#define AttachNetToJoint(w2, w1) \
    { 47, {(void *)(w1)}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Ends a net sub-Group ID that was created with "MakeNetWithSubGroup" */
#define EndNetSubGroup(w1) \
    { 48, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Add a Vertex dynobj */
#define MakeVertex(x, y, z) \
    { 49, {0}, {0}, {(x), (y), (z)} }

/* Add a ValPtr dynobj */
#define MakeValPtr(id, flags, type, offset) \
    { 50, {(void *)(id)}, {(void *)(type)}, {(offset), (flags), 0.0} }

/* Add the texture pointed to by PTR to the current dynobj */
/* Supported Objs: materials */
#define UseTexture(w2) \
    { 52, {0}, {(void *)(w2)}, {0.0, 0.0, 0.0} }

/* Set the S and T values of the current dynobj */
/* Supported Objs: vertices */
#define SetTextureST(x, y) \
    { 53, {0}, {0}, {(x), (y), 0.0} }

/* Make a new Net from Shape ID */
#define MakeNetFromShapeId(w1) \
    { 54, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

/* Make a new Net from Shape double pointer PTR */
#define MakeNetFromShapeDblPtr(w1) \
    { 55, {(void *)(w1)}, {0}, {0.0, 0.0, 0.0} }

#endif /* _DYN_LIST_MACROS_H_ */
