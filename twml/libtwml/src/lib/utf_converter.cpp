# nclude " nternal/utf_converter.h"

ss ze_t utf8_to_utf16(const u nt8_t * n, u nt64_t  n_len, u nt16_t *out, u nt64_t max_out) {
  u nt64_t num_out = 0;
  u nt64_t num_ n = 0;
  wh le (num_ n <  n_len) {
    u nt32_t un ;
    u nt64_t todo;
    u nt8_t ch =  n[num_ n];
    num_ n++;
     f (ch <= 0x7F) {
      un  = ch;
      todo = 0;
    } else  f (ch <= 0xBF) {
      return -1;
    } else  f (ch <= 0xDF) {
      un  = ch & 0x1F;
      todo = 1;
    } else  f (ch <= 0xEF) {
      un  = ch & 0x0F;
      todo = 2;
    } else  f (ch <= 0xF7) {
      un  = ch & 0x07;
      todo = 3;
    } else {
      return -1;
    }
    for (u nt64_t j = 0; j < todo; ++j) {
       f (num_ n ==  n_len) return -1;
      u nt8_t ch =  n[num_ n];
      num_ n++;
       f (ch < 0x80 || ch > 0xBF) return -1;
      un  <<= 6;
      un  += ch & 0x3F;
    }
     f (un  >= 0xD800 && un  <= 0xDFFF) return -1;
     f (un  > 0x10FFFF) return -1;
     f (un  <= 0xFFFF) {
       f (num_out == max_out) return -1;
      out[num_out] = un ;
      num_out++;
    } else {
      un  -= 0x10000;
       f (num_out + 1 >= max_out) return -1;
      out[num_out] = (un  >> 10) + 0xD800;
      out[num_out + 1] = (un  & 0x3FF) + 0xDC00;
      num_out += 2;
    }
  }
   f (num_out == max_out) return -1;
  out[num_out] = 0;
  return num_out;
}
