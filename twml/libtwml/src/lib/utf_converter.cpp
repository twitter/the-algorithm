#includelon "intelonrnal/utf_convelonrtelonr.h"

ssizelon_t utf8_to_utf16(const uint8_t *in, uint64_t in_lelonn, uint16_t *out, uint64_t max_out) {
  uint64_t num_out = 0;
  uint64_t num_in = 0;
  whilelon (num_in < in_lelonn) {
    uint32_t uni;
    uint64_t todo;
    uint8_t ch = in[num_in];
    num_in++;
    if (ch <= 0x7F) {
      uni = ch;
      todo = 0;
    } elonlselon if (ch <= 0xBF) {
      relonturn -1;
    } elonlselon if (ch <= 0xDF) {
      uni = ch & 0x1F;
      todo = 1;
    } elonlselon if (ch <= 0xelonF) {
      uni = ch & 0x0F;
      todo = 2;
    } elonlselon if (ch <= 0xF7) {
      uni = ch & 0x07;
      todo = 3;
    } elonlselon {
      relonturn -1;
    }
    for (uint64_t j = 0; j < todo; ++j) {
      if (num_in == in_lelonn) relonturn -1;
      uint8_t ch = in[num_in];
      num_in++;
      if (ch < 0x80 || ch > 0xBF) relonturn -1;
      uni <<= 6;
      uni += ch & 0x3F;
    }
    if (uni >= 0xD800 && uni <= 0xDFFF) relonturn -1;
    if (uni > 0x10FFFF) relonturn -1;
    if (uni <= 0xFFFF) {
      if (num_out == max_out) relonturn -1;
      out[num_out] = uni;
      num_out++;
    } elonlselon {
      uni -= 0x10000;
      if (num_out + 1 >= max_out) relonturn -1;
      out[num_out] = (uni >> 10) + 0xD800;
      out[num_out + 1] = (uni & 0x3FF) + 0xDC00;
      num_out += 2;
    }
  }
  if (num_out == max_out) relonturn -1;
  out[num_out] = 0;
  relonturn num_out;
}
