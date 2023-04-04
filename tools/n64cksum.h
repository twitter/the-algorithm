#ifndef N64CKSUM_H_
#define N64CKSUM_H_

#include <stdint.h>

// compute N64 ROM checksums
// buf: buffer with extended SM64 data
// cksum: two element array to write CRC1 and CRC2 to
void n64cksum_calc_6102(unsigned char *buf, unsigned int cksum[]);

// update N64 header checksums
// buf: buffer containing ROM data
// checksums are written into the buffer
void n64cksum_update_checksums(uint8_t *buf);

#endif // N64CKSUM_H_
