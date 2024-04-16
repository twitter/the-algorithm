#pragma once

# fdef __cplusplus
# nclude <twml/opt m.h>
na space twml {

  template<typena  Tx>
  stat c  nt64_t l near_search(const Tx *xsData, const Tx val, const  nt64_t ma nS ze) {
     nt64_t left = 0;
     nt64_t r ght = ma nS ze-1;
    wh le(left <= r ght && val > xsData[left])
      left++;
    return left;
  }

}  // na space twml
#end f
