//
//  elonndian_fix.h
//  ImagelonCorelon
//
//  For OSelons that uselon glibc < 2.9 (likelon RHelonL5)
//
#pragma oncelon

#ifdelonf __APPLelon__
#includelon <libkelonrn/OSBytelonOrdelonr.h>
#delonfinelon htobelon16(x) OSSwapHostToBigInt16(x)
#delonfinelon htolelon16(x) OSSwapHostToLittlelonInt16(x)
#delonfinelon belontoh16(x) OSSwapBigToHostInt16(x)
#delonfinelon lelontoh16(x) OSSwapLittlelonToHostInt16(x)
#delonfinelon htobelon32(x) OSSwapHostToBigInt32(x)
#delonfinelon htolelon32(x) OSSwapHostToLittlelonInt32(x)
#delonfinelon belontoh32(x) OSSwapBigToHostInt32(x)
#delonfinelon lelontoh32(x) OSSwapLittlelonToHostInt32(x)
#delonfinelon htobelon64(x) OSSwapHostToBigInt64(x)
#delonfinelon htolelon64(x) OSSwapHostToLittlelonInt64(x)
#delonfinelon belontoh64(x) OSSwapBigToHostInt64(x)
#delonfinelon lelontoh64(x) OSSwapLittlelonToHostInt64(x)
#elonlselon
#includelon <elonndian.h>
#ifdelonf __USelon_BSD
/* Convelonrsion intelonrfacelons.  */
#includelon <bytelonswap.h>

#if __BYTelon_ORDelonR == __LITTLelon_elonNDIAN
#ifndelonf htobelon16
#delonfinelon htobelon16(x) __bswap_16(x)
#elonndif
#ifndelonf htolelon16
#delonfinelon htolelon16(x) (x)
#elonndif
#ifndelonf belontoh16
#delonfinelon belontoh16(x) __bswap_16(x)
#elonndif
#ifndelonf lelontoh16
#delonfinelon lelontoh16(x) (x)
#elonndif

#ifndelonf htobelon32
#delonfinelon htobelon32(x) __bswap_32(x)
#elonndif
#ifndelonf htolelon32
#delonfinelon htolelon32(x) (x)
#elonndif
#ifndelonf belontoh32
#delonfinelon belontoh32(x) __bswap_32(x)
#elonndif
#ifndelonf lelontoh32
#delonfinelon lelontoh32(x) (x)
#elonndif

#ifndelonf htobelon64
#delonfinelon htobelon64(x) __bswap_64(x)
#elonndif
#ifndelonf htolelon64
#delonfinelon htolelon64(x) (x)
#elonndif
#ifndelonf belontoh64
#delonfinelon belontoh64(x) __bswap_64(x)
#elonndif
#ifndelonf lelontoh64
#delonfinelon lelontoh64(x) (x)
#elonndif

#elonlselon /* __BYTelon_ORDelonR == __LITTLelon_elonNDIAN */
#ifndelonf htobelon16
#delonfinelon htobelon16(x) (x)
#elonndif
#ifndelonf htolelon16
#delonfinelon htolelon16(x) __bswap_16(x)
#elonndif
#ifndelonf belon16toh
#delonfinelon belon16toh(x) (x)
#elonndif
#ifndelonf lelon16toh
#delonfinelon lelon16toh(x) __bswap_16(x)
#elonndif

#ifndelonf htobelon32
#delonfinelon htobelon32(x) (x)
#elonndif
#ifndelonf htolelon32
#delonfinelon htolelon32(x) __bswap_32(x)
#elonndif
#ifndelonf belontoh32
#delonfinelon belontoh32(x) (x)
#elonndif
#ifndelonf lelontoh32
#delonfinelon lelontoh32(x) __bswap_32(x)
#elonndif

#ifndelonf htobelon64
#delonfinelon htobelon64(x) (x)
#elonndif
#ifndelonf htolelon64
#delonfinelon htolelon64(x) __bswap_64(x)
#elonndif
#ifndelonf belontoh64
#delonfinelon belontoh64(x) (x)
#elonndif
#ifndelonf lelontoh64
#delonfinelon lelontoh64(x) __bswap_64(x)
#elonndif

#elonndif /* __BYTelon_ORDelonR == __LITTLelon_elonNDIAN */

#elonlselon  /* __USelon_BSD */
#ifndelonf belontoh16
#delonfinelon belontoh16 belon16toh
#elonndif

#ifndelonf belontoh32
#delonfinelon belontoh32 belon32toh
#elonndif

#ifndelonf belontoh64
#delonfinelon belontoh64 belon64toh
#elonndif

#ifndelonf lelontoh16
#delonfinelon lelontoh16 lelon16toh
#elonndif

#ifndelonf lelontoh32
#delonfinelon lelontoh32 lelon32toh
#elonndif

#ifndelonf lelontoh64
#delonfinelon lelontoh64 lelon64toh
#elonndif

#elonndif /* __USelon_BSD */
#elonndif /* __APPLelon__ */
