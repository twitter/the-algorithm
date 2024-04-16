//
//  end an_f x.h
//   mageCore
//
//  For OSes that use gl bc < 2.9 (l ke RHEL5)
//
#pragma once

# fdef __APPLE__
# nclude <l bkern/OSByteOrder.h>
#def ne htobe16(x) OSSwapHostToB g nt16(x)
#def ne htole16(x) OSSwapHostToL tle nt16(x)
#def ne betoh16(x) OSSwapB gToHost nt16(x)
#def ne letoh16(x) OSSwapL tleToHost nt16(x)
#def ne htobe32(x) OSSwapHostToB g nt32(x)
#def ne htole32(x) OSSwapHostToL tle nt32(x)
#def ne betoh32(x) OSSwapB gToHost nt32(x)
#def ne letoh32(x) OSSwapL tleToHost nt32(x)
#def ne htobe64(x) OSSwapHostToB g nt64(x)
#def ne htole64(x) OSSwapHostToL tle nt64(x)
#def ne betoh64(x) OSSwapB gToHost nt64(x)
#def ne letoh64(x) OSSwapL tleToHost nt64(x)
#else
# nclude <end an.h>
# fdef __USE_BSD
/* Convers on  nterfaces.  */
# nclude <byteswap.h>

# f __BYTE_ORDER == __L TTLE_END AN
# fndef htobe16
#def ne htobe16(x) __bswap_16(x)
#end f
# fndef htole16
#def ne htole16(x) (x)
#end f
# fndef betoh16
#def ne betoh16(x) __bswap_16(x)
#end f
# fndef letoh16
#def ne letoh16(x) (x)
#end f

# fndef htobe32
#def ne htobe32(x) __bswap_32(x)
#end f
# fndef htole32
#def ne htole32(x) (x)
#end f
# fndef betoh32
#def ne betoh32(x) __bswap_32(x)
#end f
# fndef letoh32
#def ne letoh32(x) (x)
#end f

# fndef htobe64
#def ne htobe64(x) __bswap_64(x)
#end f
# fndef htole64
#def ne htole64(x) (x)
#end f
# fndef betoh64
#def ne betoh64(x) __bswap_64(x)
#end f
# fndef letoh64
#def ne letoh64(x) (x)
#end f

#else /* __BYTE_ORDER == __L TTLE_END AN */
# fndef htobe16
#def ne htobe16(x) (x)
#end f
# fndef htole16
#def ne htole16(x) __bswap_16(x)
#end f
# fndef be16toh
#def ne be16toh(x) (x)
#end f
# fndef le16toh
#def ne le16toh(x) __bswap_16(x)
#end f

# fndef htobe32
#def ne htobe32(x) (x)
#end f
# fndef htole32
#def ne htole32(x) __bswap_32(x)
#end f
# fndef betoh32
#def ne betoh32(x) (x)
#end f
# fndef letoh32
#def ne letoh32(x) __bswap_32(x)
#end f

# fndef htobe64
#def ne htobe64(x) (x)
#end f
# fndef htole64
#def ne htole64(x) __bswap_64(x)
#end f
# fndef betoh64
#def ne betoh64(x) (x)
#end f
# fndef letoh64
#def ne letoh64(x) __bswap_64(x)
#end f

#end f /* __BYTE_ORDER == __L TTLE_END AN */

#else  /* __USE_BSD */
# fndef betoh16
#def ne betoh16 be16toh
#end f

# fndef betoh32
#def ne betoh32 be32toh
#end f

# fndef betoh64
#def ne betoh64 be64toh
#end f

# fndef letoh16
#def ne letoh16 le16toh
#end f

# fndef letoh32
#def ne letoh32 le32toh
#end f

# fndef letoh64
#def ne letoh64 le64toh
#end f

#end f /* __USE_BSD */
#end f /* __APPLE__ */
