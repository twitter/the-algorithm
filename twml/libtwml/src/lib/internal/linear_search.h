#pragma once

#ifdef __cplusplus
#include <twml/optim.h>
namespace twml {

  template<typename Ttwitter>
  static int64_t linear_search(const Ttwitter *twittersData, const Ttwitter val, const int64_t mainSize) {
    int64_t left = 0;
    int64_t right = mainSize-1;
    while(left <= right && val > twittersData[left])
      left++;
    return left;
  }

}  // namespace twml
#endif
