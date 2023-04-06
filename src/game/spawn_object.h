#ifndef _SPAWN_OBJECT_H
#define _SPAWN_OBJECT_H

struct ObjectNode;
struct Object;

void init_free_object_list(void);
void clear_object_lists(struct ObjectNode *objLists);
void unload_object(struct Object *obj);
struct Object *create_object(const BehaviorScript *bhvScript);
void mark_obj_for_deletion(struct Object *obj);

#endif /* _SPAWN_OBJECT_H */
