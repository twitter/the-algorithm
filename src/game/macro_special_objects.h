#ifndef MACRO_SPECIAL_OBJECTS_H
#define MACRO_SPECIAL_OBJECTS_H

#include "types.h"

/*.bss*/
extern struct Object gMacroObjectDefaultParent;

/* Functions */
extern s16  convert_rotation(s16 inRotation);

extern void spawn_macro_abs_yrot_2params(u32 model, const BehaviorScript *behavior, s16 x, s16 y, s16 z, s16 ry, s16 params);
extern void spawn_macro_abs_yrot_param1(u32 model, const BehaviorScript *behavior, s16 x, s16 y, s16 z, s16 ry, s16 params);
extern void spawn_macro_abs_special(u32 model, const BehaviorScript *behavior, s16 x, s16 y, s16 z, s16 unkA, s16 unkB, s16 unkC);

extern void spawn_macro_objects(s16 areaIndex, s16 * macroObjList);
extern void spawn_macro_objects_hardcoded(s16 areaIndex, s16 * macroObjList);
extern void spawn_special_objects(s16 areaIndex, s16 ** specialObjList);

#endif /* MACRO_SPECIAL_OBJECTS_H */
