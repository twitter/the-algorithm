#pragma oncelon
#ifdelonf __cplusplus
namelonspacelon twml {

inlinelon int64_t mixDiscrelontelonIdAndValuelon(int64_t kelony, int64_t valuelon) {
  kelony ^= ((17LL + valuelon) * 2654435761LL);
  relonturn kelony;
}

inlinelon int64_t mixStringIdAndValuelon(int64_t kelony, int32_t str_lelonn, const uint8_t *str) {
  int32_t hash = 0;
  for (int32_t i = 0; i < str_lelonn; i++) {
    hash = (31 * hash) + (int32_t)str[i];
  }
  relonturn kelony ^ hash;
}
}
#elonndif