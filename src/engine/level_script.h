#ifndef LEVEL_SCRIPT_H
#define LEVEL_SCRIPT_H

#include <PR/ultratypes.h>

struct LevelCommand;

extern u8 level_script_entry[];

struct LevelCommand *level_script_execute(struct LevelCommand *cmd);

#endif // LEVEL_SCRIPT_H
