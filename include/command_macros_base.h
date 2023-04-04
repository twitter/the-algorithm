#ifndef COMMAND_MACROS_BASE_H
#define COMMAND_MACROS_BASE_H

#include "platform_info.h"

#if IS_BIG_ENDIAN
#if IS_64_BIT
#define CMD_BBBB(a, b, c, d) ((uintptr_t)(_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8) | _SHIFTL(c, 8, 8) | _SHIFTL(d, 0, 8)) << 32)
#define CMD_BBH(a, b, c) ((uintptr_t)(_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8) | _SHIFTL(c, 0, 16)) << 32)
#define CMD_HH(a, b) ((uintptr_t)(_SHIFTL(a, 16, 16) | _SHIFTL(b, 0, 16)) << 32)
#define CMD_W(a) ((uintptr_t)(a) << 32)
#else
#define CMD_BBBB(a, b, c, d) (_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8) | _SHIFTL(c, 8, 8) | _SHIFTL(d, 0, 8))
#define CMD_BBH(a, b, c) (_SHIFTL(a, 24, 8) | _SHIFTL(b, 16, 8) | _SHIFTL(c, 0, 16))
#define CMD_HH(a, b) (_SHIFTL(a, 16, 16) | _SHIFTL(b, 0, 16))
#define CMD_W(a) (a)
#endif
#else
#define CMD_BBBB(a, b, c, d) (_SHIFTL(a, 0, 8) | _SHIFTL(b, 8, 8) | _SHIFTL(c, 16, 8) | _SHIFTL(d, 24, 8))
#define CMD_BBH(a, b, c) (_SHIFTL(a, 0, 8) | _SHIFTL(b, 8, 8) | _SHIFTL(c, 16, 16))
#define CMD_HH(a, b) (_SHIFTL(a, 0, 16) | _SHIFTL(b, 16, 16))
#define CMD_W(a) (a)
#endif
#define CMD_PTR(a) ((uintptr_t)(a))

#define CMD_HHHHHH(a, b, c, d, e, f) CMD_HH(a, b), CMD_HH(c, d), CMD_HH(e, f)

#endif // COMMAND_MACROS_BASE_H
