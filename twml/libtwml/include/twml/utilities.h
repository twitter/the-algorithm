#pragma once
#ifdef __cplusplus
namespace twml {

inline int64_t mixDiscreteIdAndValue(int64_t key, int64_t value) {
  key ^= ((17LL + value) * 2654435761LL);
  return key;
}

inline int64_t mixStringIdAndValue(int64_t key, int32_t str_len, const uint8_t *str) {
  int32_t hash = 0;
  for (int32_t i = 0; i < str_len; i++) {
    hash = (31 * hash) + (int32_t)str[i];
  }
  return key ^ hash;
}
}
#endif