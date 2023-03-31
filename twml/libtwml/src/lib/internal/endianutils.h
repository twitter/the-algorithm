//
//  endian_fix.h
//  ImageCore
//
//  For OSes that use glibc < 2.9 (like RHEL5)
//
#pragma once

#ifdef __APPLE__
#include <libkern/OSByteOrder.h>
#define htobe16(x) OSSwapHostToBigInt16(x)
#define htole16(x) OSSwapHostToLittleInt16(x)
#define betoh16(x) OSSwapBigToHostInt16(x)
#define letoh16(x) OSSwapLittleToHostInt16(x)
#define htobe32(x) OSSwapHostToBigInt32(x)
#define htole32(x) OSSwapHostToLittleInt32(x)
#define betoh32(x) OSSwapBigToHostInt32(x)
#define letoh32(x) OSSwapLittleToHostInt32(x)
#define htobe64(x) OSSwapHostToBigInt64(x)
#define htole64(x) OSSwapHostToLittleInt64(x)
#define betoh64(x) OSSwapBigToHostInt64(x)
#define letoh64(x) OSSwapLittleToHostInt64(x)
#else
#include <endian.h>
#ifdef __USE_BSD
/* Conversion interfaces.  */
#include <byteswap.h>

#if __BYTE_ORDER == __LITTLE_ENDIAN
#ifndef htobe16
#define htobe16(x) __bswap_16(x)
#endif
#ifndef htole16
#define htole16(x) (x)
#endif
#ifndef betoh16
#define betoh16(x) __bswap_16(x)
#endif
#ifndef letoh16
#define letoh16(x) (x)
#endif

#ifndef htobe32
#define htobe32(x) __bswap_32(x)
#endif
#ifndef htole32
#define htole32(x) (x)
#endif
#ifndef betoh32
#define betoh32(x) __bswap_32(x)
#endif
#ifndef letoh32
#define letoh32(x) (x)
#endif

#ifndef htobe64
#define htobe64(x) __bswap_64(x)
#endif
#ifndef htole64
#define htole64(x) (x)
#endif
#ifndef betoh64
#define betoh64(x) __bswap_64(x)
#endif
#ifndef letoh64
#define letoh64(x) (x)
#endif

#else /* __BYTE_ORDER == __LITTLE_ENDIAN */
#ifndef htobe16
#define htobe16(x) (x)
#endif
#ifndef htole16
#define htole16(x) __bswap_16(x)
#endif
#ifndef be16toh
#define be16toh(x) (x)
#endif
#ifndef le16toh
#define le16toh(x) __bswap_16(x)
#endif

#ifndef htobe32
#define htobe32(x) (x)
#endif
#ifndef htole32
#define htole32(x) __bswap_32(x)
#endif
#ifndef betoh32
#define betoh32(x) (x)
#endif
#ifndef letoh32
#define letoh32(x) __bswap_32(x)
#endif

#ifndef htobe64
#define htobe64(x) (x)
#endif
#ifndef htole64
#define htole64(x) __bswap_64(x)
#endif
#ifndef betoh64
#define betoh64(x) (x)
#endif
#ifndef letoh64
#define letoh64(x) __bswap_64(x)
#endif

#endif /* __BYTE_ORDER == __LITTLE_ENDIAN */

#else  /* __USE_BSD */
#ifndef betoh16
#define betoh16 be16toh
#endif

#ifndef betoh32
#define betoh32 be32toh
#endif

#ifndef betoh64
#define betoh64 be64toh
#endif

#ifndef letoh16
#define letoh16 le16toh
#endif

#ifndef letoh32
#define letoh32 le32toh
#endif

#ifndef letoh64
#define letoh64 le64toh
#endif

#endif /* __USE_BSD */
#endif /* __APPLE__ */
