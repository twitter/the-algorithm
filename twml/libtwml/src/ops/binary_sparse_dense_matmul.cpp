/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

// TWML modified to optimize binary features:
// - Sparse tensor values are assumed to be binary, so only add operation is done
//   rather than mul-add;
// - In house version of vectorization is used instead of Eigen;
// - Enable sharding and multithreading.

#define EIGEN_USE_THREADS

#include "binary_sparse_dense_matmul.h"
#include "binary_sparse_dense_matmul_impl.h"

#include "tensorflow/core/framework/bounds_check.h"
#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/framework/common_shape_fns.h"
#include "tensorflow/core/framework/shape_inference.h"

namespace tensorflow {

namespace shape_inference {
// TODO: The `a_value` is supposed to be all ones.
// Users should not call this op directly but to use it from `sparse_op` python library. 
// To make it consistent with original op, the signature remains the same currently,
//  we will think a better way to contrain correct use of this op.
// CX-18174
REGISTER_OP("BinarySparseTensorDenseMatMul")
    .Input("a_indices: Tindices")
    .Input("a_values: T")
    .Input("a_shape: int64")
    .Input("b: T")
    .Output("product: T")
    .Attr("T: type")
    .Attr("Tindices: {int32,int64} = DT_INT64")
    .Attr("adjoint_a: bool = false")
    .Attr("adjoint_b: bool = false")
    .SetShapeFn([](InferenceContext* c) {
      DimensionHandle unused_dim;
      ShapeHandle unused;
      ShapeHandle b;
      ShapeHandle a_shape;
      TF_RETURN_IF_ERROR(c->WithRank(c->input(0), 2, &unused));  // a_indices
      TF_RETURN_IF_ERROR(c->WithRank(c->input(1), 1, &unused));  // a_values
      TF_RETURN_IF_ERROR(c->MakeShapeFromShapeTensor(2, &a_shape));
      TF_RETURN_IF_ERROR(c->WithRank(a_shape, 2, &a_shape));
      TF_RETURN_IF_ERROR(c->WithRank(c->input(3), 2, &b));

      bool adjoint_a;
      bool adjoint_b;
      TF_RETURN_IF_ERROR(c->GetAttr("adjoint_a", &adjoint_a));
      TF_RETURN_IF_ERROR(c->GetAttr("adjoint_b", &adjoint_b));

      DimensionHandle output_right = c->Dim(b, adjoint_b ? 0 : 1);
      DimensionHandle output_left = c->Dim(a_shape, adjoint_a ? 1 : 0);
      DimensionHandle inner_left = c->Dim(a_shape, adjoint_a ? 0 : 1);
      DimensionHandle inner_right = c->Dim(b, adjoint_b ? 1 : 0);
      TF_RETURN_IF_ERROR(c->Merge(inner_left, inner_right, &unused_dim));
      c->set_output(0, c->Matrix(output_left, output_right));
      return Status::OK();
    });
}  // namespace shape_inference


typedef Eigen::ThreadPoolDevice CPUDevice;

template <typename Device, typename T, typename Tindices>
class BinarySparseTensorDenseMatMulOp : public OpKernel {
 public:
  explicit BinarySparseTensorDenseMatMulOp(OpKernelConstruction* ctx)
      : OpKernel(ctx) {
    OP_REQUIRES_OK(ctx, ctx->GetAttr("adjoint_a", &adjoint_a_));
    OP_REQUIRES_OK(ctx, ctx->GetAttr("adjoint_b", &adjoint_b_));
  }

  void Compute(OpKernelContext* ctx) override {
    const Tensor* a_indices;
    const Tensor* a_values;
    const Tensor* a_shape;
    const Tensor* b;
    OP_REQUIRES_OK(ctx, ctx->input("a_indices", &a_indices));
    OP_REQUIRES_OK(ctx, ctx->input("a_values", &a_values));
    OP_REQUIRES_OK(ctx, ctx->input("a_shape", &a_shape));
    OP_REQUIRES_OK(ctx, ctx->input("b", &b));

    // Check that the dimensions of the two matrices are valid.
    OP_REQUIRES(ctx, TensorShapeUtils::IsMatrix(b->shape()),
                errors::InvalidArgument("Tensor 'b' is not a matrix"));

    OP_REQUIRES(ctx, TensorShapeUtils::IsVector(a_shape->shape()),
                errors::InvalidArgument("Tensor 'a_shape' is not a vector"));

    OP_REQUIRES(
        ctx, a_shape->NumElements() == 2,
        errors::InvalidArgument("Tensor 'a_shape' must have 2 elements"));

    OP_REQUIRES(ctx, TensorShapeUtils::IsVector(a_values->shape()),
                errors::InvalidArgument("Tensor 'a_values' is not a vector"));

    OP_REQUIRES(ctx, TensorShapeUtils::IsMatrix(a_indices->shape()),
                errors::InvalidArgument("Tensor 'a_indices' is not a matrix"));

    const int64 nnz = a_indices->shape().dim_size(0);
    OP_REQUIRES(ctx, nnz == a_values->NumElements(),
                errors::InvalidArgument("Number of rows of a_indices does not "
                                        "match number of entries in a_values"));

    OP_REQUIRES(
        ctx, a_indices->shape().dim_size(1) == a_shape->NumElements(),
        errors::InvalidArgument("Number of columns of a_indices does not match "
                                "number of entries in a_shape"));

    auto a_shape_t = a_shape->vec<int64>();
    const int64 outer_left = (adjoint_a_) ? a_shape_t(1) : a_shape_t(0);
    const int64 outer_right =
        (adjoint_b_) ? b->shape().dim_size(0) : b->shape().dim_size(1);
    const int64 inner_left = (adjoint_a_) ? a_shape_t(0) : a_shape_t(1);
    const int64 inner_right =
        (adjoint_b_) ? b->shape().dim_size(1) : b->shape().dim_size(0);

    OP_REQUIRES(
        ctx, inner_right == inner_left,
        errors::InvalidArgument(
            "Cannot multiply A and B because inner dimension does not match: ",
            inner_left, " vs. ", inner_right,
            ".  Did you forget a transpose?  "
            "Dimensions of A: [",
            a_shape_t(0), ", ", a_shape_t(1),
            ").  Dimensions of B: ", b->shape().DebugString()));

    TensorShape out_shape({outer_left, outer_right});
    Tensor* out = nullptr;
    OP_REQUIRES_OK(ctx, ctx->allocate_output(0, out_shape, &out));

    if (out->NumElements() == 0) {
      // If a has shape [0, x] or b has shape [x, 0], the output shape
      // is a 0-element matrix, so there is nothing to do.
      return;
    }

    if (a_values->NumElements() == 0 || b->NumElements() == 0) {
      // If a has shape [x, 0] and b has shape [0, y], the
      // output shape is [x, y] where x and y are non-zero, so we fill
      // the output with zeros.
      out->flat<T>().device(ctx->eigen_device<Device>()) = 
          out->flat<T>().constant(T(0));
      return;
    }

#define MAYBE_ADJOINT(ADJ_A, ADJ_B)                                        \
  if (adjoint_a_ == ADJ_A && adjoint_b_ == ADJ_B) {                        \
    Status functor_status = functor::SparseTensorDenseMatMulFunctor<       \
        Device, T, Tindices, ADJ_A,                                        \
        ADJ_B>::Compute(ctx, a_indices, a_values, a_shape, b, out);        \
    OP_REQUIRES_OK(ctx, functor_status);                                   \
  }

    MAYBE_ADJOINT(false, false);
    MAYBE_ADJOINT(false, true);
    MAYBE_ADJOINT(true, false);
    MAYBE_ADJOINT(true, true);

#undef MAYBE_ADJOINT
  }

 private:
  bool adjoint_a_;
  bool adjoint_b_;
};

#define REGISTER_CPU(TypeT, TypeIndex)           \
  REGISTER_KERNEL_BUILDER(                       \
      Name("BinarySparseTensorDenseMatMul")      \
          .Device(DEVICE_CPU)                    \
          .TypeConstraint<TypeT>("T")            \
          .TypeConstraint<TypeIndex>("Tindices") \
          .HostMemory("a_shape"),                \
      BinarySparseTensorDenseMatMulOp<CPUDevice, TypeT, TypeIndex>);

#define REGISTER_KERNELS_CPU(T) \
  REGISTER_CPU(T, int64);       \
  REGISTER_CPU(T, int32)

REGISTER_KERNELS_CPU(float);
REGISTER_KERNELS_CPU(double);
REGISTER_KERNELS_CPU(int32);
REGISTER_KERNELS_CPU(complex64);
REGISTER_KERNELS_CPU(complex128);

namespace functor {

namespace {
Status KOutOfBoundsError(int64 k, std::size_t i, int rhs_index_a,
                         std::size_t lhs_right) {
  return errors::InvalidArgument("k (", k, ") from index[", i, ",", rhs_index_a,
                                 "] out of bounds (>=", lhs_right, ")");
}

Status MOutOfBoundsError(int64 m, std::size_t i, int lhs_index_a,
                         int64 out_dim0) {
  return errors::InvalidArgument("m (", m, ") from index[", i, ",", lhs_index_a,
                                 "] out of bounds (>=", out_dim0, ")");
}

}  // namespace


// The general functor just borrows the code from tf except that add is computed 
// instead of mul-add.
template <typename T, typename Tindices, bool ADJ_A, bool ADJ_B>
struct SparseTensorDenseMatMulFunctor<CPUDevice, T, Tindices, ADJ_A, ADJ_B> {
  // Vectorize certain operations above this size.
  static const std::size_t kNumVectorize = 32;

  static Status Compute(OpKernelContext* ctx,
                        const Tensor *a_indices,
                        const Tensor *a_values,
                        const Tensor *a_shape,
                        const Tensor *b,
                        Tensor *out) {
    return EigenCompute(ctx->eigen_device<CPUDevice>(), out->matrix<T>(),
                        a_indices->matrix<Tindices>(), a_values->vec<T>(),
                        b->matrix<T>());
  }

  static Status EigenCompute(const CPUDevice& d, typename TTypes<T>::Matrix out,
                             typename TTypes<Tindices>::ConstMatrix a_indices,
                             typename TTypes<T>::ConstVec a_values,
                             typename TTypes<T>::ConstMatrix b) {
    const std::size_t nnz = a_values.size();
    const std::size_t rhs_right = (ADJ_B ? b.dimension(0) : b.dimension(1));
    const std::size_t lhs_right = (ADJ_B ? b.dimension(1) : b.dimension(0));
    const int lhs_index_a = ADJ_A ? 1 : 0;
    const int rhs_index_a = ADJ_A ? 0 : 1;

    out.setZero();

    if (rhs_right < kNumVectorize) {
      // Disable vectorization if the RHS of output is too small
      auto maybe_adjoint_b = MaybeAdjoint<decltype(b), ADJ_B>(b);

      for (std::size_t i = 0; i < nnz; ++i) {
        const Tindices m = internal::SubtleMustCopy(a_indices(i, lhs_index_a));
        const Tindices k = internal::SubtleMustCopy(a_indices(i, rhs_index_a));
        if (!FastBoundsCheck(k, lhs_right)) {
          return KOutOfBoundsError(k, i, rhs_index_a, lhs_right);
        }
        if (!FastBoundsCheck(m, out.dimension(0))) {
          return MOutOfBoundsError(m, i, lhs_index_a, out.dimension(0));
        }
        for (std::size_t n = 0; n < rhs_right; ++n) {
          const T b_value = maybe_adjoint_b(k, n);
          out(m, n) += b_value;
        }
      }
    } else {
      // Vectorization via Eigen.
      const int b_chip_index = ADJ_B ? 1 : 0;

#define LOOP_NNZ(b_passed)                                                  \
  for (std::size_t i = 0; i < nnz; ++i) {                                   \
    const Tindices m = internal::SubtleMustCopy(a_indices(i, lhs_index_a)); \
    const Tindices k = internal::SubtleMustCopy(a_indices(i, rhs_index_a)); \
    if (!FastBoundsCheck(k, lhs_right)) {                                   \
      return KOutOfBoundsError(k, i, rhs_index_a, lhs_right);               \
    }                                                                       \
    if (!FastBoundsCheck(m, out.dimension(0))) {                            \
      return MOutOfBoundsError(m, i, lhs_index_a, out.dimension(0));        \
    }                                                                       \
    out.template chip<0>(m) += b_passed.template chip<b_chip_index>(k);     \
  }


      if (ADJ_B) {
        // Perform transpose and conjugation on B once, since we chip out B's
        // columns in the nnz loop.
        Eigen::array<int, 2> shuffle;  // preserve dimension order
        shuffle[0] = 1; shuffle[1] = 0;
        Eigen::Tensor<T, 2, Eigen::ColMajor> col_major_conj_b =
            b.swap_layout().shuffle(shuffle).conjugate();
        LOOP_NNZ(col_major_conj_b);
      } else {
        LOOP_NNZ(b);
      }
#undef LOOP_NNZ
    }
    return Status::OK();
  }
};


// We have only specified and optimised the case with no matrix transpose, 
// since it is the most typical usage in productions.
template <typename Tindices>
struct SparseTensorDenseMatMulFunctor<CPUDevice, 
                                      float, Tindices, false, false> {
  static Status Compute(OpKernelContext* ctx,
                        const Tensor *a_indices,
                        const Tensor *a_values,
                        const Tensor *a_shape,
                        const Tensor *b,
                        Tensor *out) {
    auto a_indices_ptr = a_indices->flat<Tindices>().data();     
    auto b_ptr = b->flat<float>().data();
    auto out_ptr = out->flat<float>().data();
    const int64 nnz = a_indices->shape().dim_size(0);
    const int64 outer_left = a_shape->vec<int64>()(0);
    const int64 outer_right = b->shape().dim_size(1);
    ParallelLookupAndSegmentSum<Tindices>(ctx, a_indices_ptr, b_ptr, nnz,
                                outer_left, outer_right, out_ptr);
    return Status::OK();
  }
};

}  // namespace functor

}  // namespace tensorflow
