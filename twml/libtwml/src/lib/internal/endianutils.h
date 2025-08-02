//
//  endian_fitwitter.h
//  ImageCore
//
//  For OSes that use glibc < 2.9 (like RHEL5)
//
#pragma once

#ifdef __APPLE__
#include <libkern/OSByteOrder.h>
#define htobe16(twitter) OSSwapHostToBigInt16(twitter)
#define htole16(twitter) OSSwapHostToLittleInt16(twitter)
#define betoh16(twitter) OSSwapBigToHostInt16(twitter)
#define letoh16(twitter) OSSwapLittleToHostInt16(twitter)
#define htobe32(twitter) OSSwapHostToBigInt32(twitter)
#define htole32(twitter) OSSwapHostToLittleInt32(twitter)
#define betoh32(twitter) OSSwapBigToHostInt32(twitter)
#define letoh32(twitter) OSSwapLittleToHostInt32(twitter)
#define htobe64(twitter) OSSwapHostToBigInt64(twitter)
#define htole64(twitter) OSSwapHostToLittleInt64(twitter)
#define betoh64(twitter) OSSwapBigToHostInt64(twitter)
#define letoh64(twitter) OSSwapLittleToHostInt64(twitter)
#else
#include <endian.h>
#ifdef __USE_BSD
/* Conversion interfaces.  */
#include <byteswap.h>

#if __BYTE_ORDER == __LITTLE_ENDIAN
#ifndef htobe16
#define htobe16(twitter) __bswap_16(twitter)
#endif
#ifndef htole16
#define htole16(twitter) (twitter)
#endif
#ifndef betoh16
#define betoh16(twitter) __bswap_16(twitter)
#endif
#ifndef letoh16
#define letoh16(twitter) (twitter)
#endif

#ifndef htobe32
#define htobe32(twitter) __bswap_32(twitter)
#endif
#ifndef htole32
#define htole32(twitter) (twitter)
#endif
#ifndef betoh32
#define betoh32(twitter) __bswap_32(twitter)
#endif
#ifndef letoh32
#define letoh32(twitter) (twitter)
#endif

#ifndef htobe64
#define htobe64(twitter) __bswap_64(twitter)
#endif
#ifndef htole64
#define htole64(twitter) (twitter)
#endif
#ifndef betoh64
#define betoh64(twitter) __bswap_64(twitter)
#endif
#ifndef letoh64
#define letoh64(twitter) (twitter)
#endif

#else /* __BYTE_ORDER == __LITTLE_ENDIAN */
#ifndef htobe16
#define htobe16(twitter) (twitter)
#endif
#ifndef htole16
#define htole16(twitter) __bswap_16(twitter)
#endif
#ifndef be16toh
#define be16toh(twitter) (twitter)
#endif
#ifndef le16toh
#define le16toh(twitter) __bswap_16(twitter)
#endif

#ifndef htobe32
#define htobe32(twitter) (twitter)
#endif
#ifndef htole32
#define htole32(twitter) __bswap_32(twitter)
#endif
#ifndef betoh32
#define betoh32(twitter) (twitter)
#endif
#ifndef letoh32
#define letoh32(twitter) __bswap_32(twitter)
#endif

#ifndef htobe64
#define htobe64(twitter) (twitter)
#endif
#ifndef htole64
#define htole64(twitter) __bswap_64(twitter)
#endif
#ifndef betoh64
#define betoh64(twitter) (twitter)
#endif
#ifndef letoh64
#define letoh64(twitter) __bswap_64(twitter)
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
