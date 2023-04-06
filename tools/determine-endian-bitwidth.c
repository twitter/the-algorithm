#include "platform_info.h"

#if IS_BIG_ENDIAN && IS_64_BIT
#error msgbegin --endian big --bitwidth 64 msgend
#elif IS_BIG_ENDIAN && !IS_64_BIT
#error msgbegin --endian big --bitwidth 32 msgend
#elif !IS_BIG_ENDIAN && IS_64_BIT
#error msgbegin --endian little --bitwidth 64 msgend
#else
#error msgbegin --endian little --bitwidth 32 msgend
#endif
