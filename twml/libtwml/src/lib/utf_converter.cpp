#include "internal/utf_converter.h"

ssize_t utf8_to_utf16(const uint8_t *in, uint64_t in_len, uint16_t *out, uint64_t max_out) {
  uint64_t num_out = 0;
  uint64_t num_in = 0;
  while (num_in < in_len) {
    uint32_t uni;
    uint64_t todo;
    uint8_t ch = in[num_in];
    num_in++;
    if (ch <= 0x7F) {
      uni = ch;
      todo = 0;
    } else if (ch <= 0xBF) {
      return -1;
    } else if (ch <= 0xDF) {
      uni = ch & 0x1F;
      todo = 1;
    } else if (ch <= 0xEF) {
      uni = ch & 0x0F;
      todo = 2;
    } else if (ch <= 0xF7) {
      uni = ch & 0x07;
      todo = 3;
    } else {
      return -1;
    }
    for (uint64_t j = 0; j < todo; ++j) {
      if (num_in == in_len) return -1;
      uint8_t ch = in[num_in];
      num_in++;
      if (ch < 0x80 || ch > 0xBF) return -1;
      uni <<= 6;
      uni += ch & 0x3F;
    }
    if (uni >= 0xD800 && uni <= 0xDFFF) return -1;
    if (uni > 0x10FFFF) return -1;
    if (uni <= 0xFFFF) {
      if (num_out == max_out) return -1;
      out[num_out] = uni;
      num_out++;
    } else {
      uni -= 0x10000;
      if (num_out + 1 >= max_out) return -1;
      out[num_out] = (uni >> 10) + 0xD800;
      out[num_out + 1] = (uni & 0x3FF) + 0xDC00;
      num_out += 2;
    }
  }
  if (num_out == max_out) return -1;
  out[num_out] = 0;
  return num_out;
}
