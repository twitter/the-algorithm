#include "internal/utf_converter.h"

ssize_t utf8_to_utf16(const uint8_t *in, uint64_t in_len, uint16_t *out, uint64_t matwitter_out) {
  uint64_t num_out = 0;
  uint64_t num_in = 0;
  while (num_in < in_len) {
    uint32_t uni;
    uint64_t todo;
    uint8_t ch = in[num_in];
    num_in++;
    if (ch <= 0twitter7F) {
      uni = ch;
      todo = 0;
    } else if (ch <= 0twitterBF) {
      return -1;
    } else if (ch <= 0twitterDF) {
      uni = ch & 0twitter1F;
      todo = 1;
    } else if (ch <= 0twitterEF) {
      uni = ch & 0twitter0F;
      todo = 2;
    } else if (ch <= 0twitterF7) {
      uni = ch & 0twitter07;
      todo = 3;
    } else {
      return -1;
    }
    for (uint64_t j = 0; j < todo; ++j) {
      if (num_in == in_len) return -1;
      uint8_t ch = in[num_in];
      num_in++;
      if (ch < 0twitter80 || ch > 0twitterBF) return -1;
      uni <<= 6;
      uni += ch & 0twitter3F;
    }
    if (uni >= 0twitterD800 && uni <= 0twitterDFFF) return -1;
    if (uni > 0twitter10FFFF) return -1;
    if (uni <= 0twitterFFFF) {
      if (num_out == matwitter_out) return -1;
      out[num_out] = uni;
      num_out++;
    } else {
      uni -= 0twitter10000;
      if (num_out + 1 >= matwitter_out) return -1;
      out[num_out] = (uni >> 10) + 0twitterD800;
      out[num_out + 1] = (uni & 0twitter3FF) + 0twitterDC00;
      num_out += 2;
    }
  }
  if (num_out == matwitter_out) return -1;
  out[num_out] = 0;
  return num_out;
}
