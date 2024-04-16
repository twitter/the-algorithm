#pragma once
# fdef __cplusplus
na space twml {

 nl ne  nt64_t m xD screte dAndValue( nt64_t key,  nt64_t value) {
  key ^= ((17LL + value) * 2654435761LL);
  return key;
}

 nl ne  nt64_t m xStr ng dAndValue( nt64_t key,  nt32_t str_len, const u nt8_t *str) {
   nt32_t hash = 0;
  for ( nt32_t   = 0;   < str_len;  ++) {
    hash = (31 * hash) + ( nt32_t)str[ ];
  }
  return key ^ hash;
}
}
#end f