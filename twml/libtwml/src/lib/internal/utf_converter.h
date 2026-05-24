#ifndef _UTF_CONVERTER_H_
#define _UTF_CONVERTER_H_

#include <stddef.h>
#include <stdint.h>
#include <sys/types.h>

ssize_t utf8_to_utf16(const uint8_t *in, uint64_t in_len, uint16_t *out, uint64_t max_out);

#endif
