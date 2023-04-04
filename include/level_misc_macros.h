#ifndef LEVEL_MISC_MACROS_H
#define LEVEL_MISC_MACROS_H

#define MACRO_OBJECT_WITH_BEH_PARAM(preset, yaw, posX, posY, posZ, behParam) \
    ((s16)((yaw * 0x10 / 45) << 9) | (preset + 0x1F)), posX, posY, posZ, behParam

#define MACRO_OBJECT(preset, yaw, posX, posY, posZ) \
    MACRO_OBJECT_WITH_BEH_PARAM(preset, yaw, posX, posY, posZ, 0)

#define MACRO_OBJECT_END() \
    0x001E

#define SPECIAL_OBJECT(preset, posX, posY, posZ) \
    preset, posX, posY, posZ

#define SPECIAL_OBJECT_WITH_YAW(preset, posX, posY, posZ, yaw) \
    preset, posX, posY, posZ, yaw

#define SPECIAL_OBJECT_WITH_YAW_AND_PARAM(preset, posX, posY, posZ, yaw, param) \
    preset, posX, posY, posZ, yaw, param

#define TRAJECTORY_POS(trajId, x, y, z) \
    trajId, x, y, z

#define TRAJECTORY_END() \
    -1

#endif // LEVEL_MISC_MACROS_H
