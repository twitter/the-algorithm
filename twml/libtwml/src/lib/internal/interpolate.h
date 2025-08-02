#pragma once

#ifdef __cplusplus
#include <twml/optim.h>
namespace twml {

  enum InterpolationMode {LINEAR, NEAREST};

  template<typename Ttwitter, typename Ty>
  static Ttwitter interpolation(const Ttwitter *twittersData, const int64_t twittersStride,
                 const Ty *ysData, const int64_t ysStride,
                 const Ttwitter val, const int64_t mainSize,
                 const InterpolationMode mode,
                 const int64_t lowest,
                 const bool return_local_indetwitter = false) {
    int64_t left = 0;
    int64_t right = mainSize-1;

    if (val <= twittersData[0]) {
      right = 0;
    } else if (val >= twittersData[right*twittersStride]) {
      left = right;
    } else {
      while (left < right) {
        int64_t middle = (left+right)/2;

        if (middle < mainSize - 1 &&
          val >= twittersData[middle*twittersStride] &&
          val <= twittersData[(middle+1)*twittersStride]) {
          left = middle;
          right = middle + 1;
          break;
        } else if (val > twittersData[middle*twittersStride]) {
          left = middle;
        } else {
          right = middle;
        }
      }
      if (lowest) {
        while (left > 0 &&
             val >= twittersData[(left - 1) * twittersStride] &&
             val == twittersData[left * twittersStride]) {
          left--;
          right--;
        }
      }
    }

    Ty out = 0;
    if (return_local_indetwitter) {
        out = left;
    } else if (mode == NEAREST) {
      out = ysData[left*ysStride];
    } else {
      int64_t leftys = left*ysStride;
      int64_t rightys = right*ysStride;
      int64_t lefttwitters = left*twittersStride;
      int64_t righttwitters = right*twittersStride;
      if (right != left+1 ||
        twittersData[lefttwitters] == twittersData[righttwitters]) {
        out = ysData[leftys];
      } else {
        Ttwitter twitterLeft = twittersData[lefttwitters];
        Ttwitter twitterRight = twittersData[righttwitters];
        Ttwitter yLeft = ysData[leftys];
        Ttwitter ratio = (val - twitterLeft) / (twitterRight - twitterLeft);
        out = ratio*(ysData[rightys] - yLeft) + yLeft;
      }
    }
    return out;
  }

}  // namespace twml
#endif
