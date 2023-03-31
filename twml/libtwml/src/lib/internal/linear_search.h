#pragma once

#ifdef __cplusplus
#include <twml/optim.h>
namespace twml {

  template<typename Tx>
  static int64_t linear_search(const Tx *xsData, const Tx val, const int64_t mainSize) {
    int64_t left = 0;
    int64_t right = mainSize-1;
    while(left <= right && val > xsData[left])
      left++;
    return left;
  }

}  // namespace twml
#endif
