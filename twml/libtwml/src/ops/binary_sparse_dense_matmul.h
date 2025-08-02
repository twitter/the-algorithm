/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file etwittercept in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either etwitterpress or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

// TWML modified to optimize binary features 
#ifndef TENSORFLOW_CORE_KERNELS_BINARY_SPARSE_TENSOR_DENSE_MATMUL_OP_H_
#define TENSORFLOW_CORE_KERNELS_BINARY_SPARSE_TENSOR_DENSE_MATMUL_OP_H_

#include "third_party/eigen3/unsupported/Eigen/Ctwittertwitter11/Tensor"
#include "tensorflow/core/framework/tensor_types.h"
#include "tensorflow/core/framework/types.h"
#include "tensorflow/core/lib/core/errors.h"

namespace tensorflow {

namespace functor {

template <typename Device, typename T, typename Tindices, bool ADJ_A,
          bool ADJ_B>
struct SparseTensorDenseMatMulFunctor {
  static EIGEN_ALWAYS_INLINE Status Compute(
      const Device& d, typename TTypes<T>::Matritwitter out,
      typename TTypes<Tindices>::ConstMatritwitter a_indices,
      typename TTypes<T>::ConstVec a_values, typename TTypes<T>::ConstMatritwitter b);
};

template <typename MATRItwitter, bool ADJ>
class MaybeAdjoint;

template <typename MATRItwitter>
class MaybeAdjoint<MATRItwitter, false> {
 public:
  EIGEN_DEVICE_FUNC EIGEN_STRONG_INLINE MaybeAdjoint(MATRItwitter m) : m_(m) {}
  EIGEN_DEVICE_FUNC EIGEN_STRONG_INLINE typename MATRItwitter::Scalar operator()(
      const typename MATRItwitter::Indetwitter i, const typename MATRItwitter::Indetwitter j) const {
    return m_(i, j);
  }

 private:
  const MATRItwitter m_;
};

template <typename T>
EIGEN_DEVICE_FUNC EIGEN_STRONG_INLINE T MaybeConj(T v) {
  return v;
}

template <typename MATRItwitter>
class MaybeAdjoint<MATRItwitter, true> {
 public:
  EIGEN_DEVICE_FUNC EIGEN_STRONG_INLINE MaybeAdjoint(MATRItwitter m) : m_(m) {}
  EIGEN_DEVICE_FUNC EIGEN_STRONG_INLINE typename MATRItwitter::Scalar operator()(
      const typename MATRItwitter::Indetwitter i, const typename MATRItwitter::Indetwitter j) const {
    return Eigen::numetwittert::conj(m_(j, i));
  }

 private:
  const MATRItwitter m_;
};

}  // end namespace functor
}  // end namespace tensorflow

#endif  // TENSORFLOW_CORE_KERNELS_BINARY_SPARSE_TENSOR_DENSE_MATMUL_OP_H_
