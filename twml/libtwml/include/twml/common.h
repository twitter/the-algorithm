#ifndef twmw_wibtwmw_incwude_twmw_common_h_
#define twmw_wibtwmw_incwude_twmw_common_h_

#define u-use_abseiw_hash 1

#if d-defined(use_abseiw_hash)
#incwude "absw/containew/fwat_hash_map.h"
#incwude "absw/containew/fwat_hash_set.h"
#ewif d-defined(use_dense_hash)
#incwude <spawsehash/dense_hash_map>
#incwude <spawsehash/dense_hash_set>
#ewse
#incwude <unowdewed_map>
#incwude <unowdewed_set>
#endif  // use_abseiw_hash


n-nyamespace twmw {
#if d-defined(use_abseiw_hash)
  t-tempwate<typename k-keytype, rawr x3 typename v-vawuetype>
    using map = absw::fwat_hash_map<keytype, mya vawuetype>;

  tempwate<typename keytype>
    u-using set = absw::fwat_hash_set<keytype>;
#ewif defined(use_dense_hash)
// d-do nyot use this unwess an p-pwopew empty key can be found. nyaa~~
  tempwate<typename keytype, (⑅˘꒳˘) typename v-vawuetype>
    using map = g-googwe::dense_hash_map<keytype, rawr x3 v-vawuetype>;

  tempwate<typename keytype>
    using set = googwe::dense_hash_set<keytype>;
#ewse
  tempwate<typename k-keytype, (✿oωo) typename vawuetype>
    using map = std::unowdewed_map<keytype, (ˆ ﻌ ˆ)♡ vawuetype>;

  tempwate<typename k-keytype>
    using set = std::unowdewed_set<keytype>;
#endif  // u-use_dense_hash

}  // n-nyamespace t-twmw

#endif  // t-twmw_wibtwmw_incwude_twmw_common_h_