#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

#include <algorithm>
using std::string;

template<typename IndexType, typename ValueType, bool calc_batch_size>
void ComputeFixedLengthTensor(OpKernelContext *context, int64 max_length_) {
  try {
    const Tensor& segment_ids = context->input(0);
    const Tensor& values = context->input(1);
    const Tensor& pad_value = context->input(2);

    auto indices_flat = segment_ids.flat<IndexType>();
    auto values_flat = values.flat<ValueType>();

    auto pad_value_scalar = pad_value.scalar<ValueType>()();

    // Get maximum length from batch if user hasn't specified it.
    int64 max_length = max_length_;
    if (max_length < 0 && indices_flat.size() > 0) {
      int64 current_id = indices_flat(0);
      int64 current_length = 1;

      for (int64 i = 1; i < indices_flat.size(); i++) {
        if (current_id == indices_flat(i)) {
          current_length++;
        } else {
          current_id = indices_flat(i);
          max_length = std::max(max_length, current_length);
          current_length = 1;
        }
      }
      // This is needed if the last batch is the longest sequence.
      max_length = std::max(max_length, current_length);
    }

    int64 batch_size = 0;
    if (calc_batch_size) {
      if (indices_flat.size() > 0) {
        // The last value of segment_ids will have value batch_size  1;
        batch_size = 1 + indices_flat(indices_flat.size() - 1);
      } else {
        batch_size = 0;
      }
    } else {
      const Tensor& batch_size_tensor = context->input(3);
      batch_size = batch_size_tensor.flat<int64>()(0);
    }

    TensorShape output_shape = {batch_size, max_length};
    Tensor* fixed_length = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, output_shape, &fixed_length));

    auto fixed_length_flat = fixed_length->flat<ValueType>();

    int64 n = 0;
    int64 offset = 0;
    for (int64 i = 0; i < batch_size; i++) {
      for (int64 j = 0; j < max_length; j++) {
        if (n < indices_flat.size() && indices_flat(n) == i) {
          // Copy from variable length tensor.
          fixed_length_flat(offset + j) = values_flat(n);
          n++;
        } else {
          // Pad to fixed length.
          fixed_length_flat(offset + j) = pad_value_scalar;
        }
      }
      // Corner case: truncate to max_length if user specified max_length < current length.
      while (n < indices_flat.size() && i == indices_flat(n)) n++;

      // Update output pointer
      offset += max_length;
    }
  } catch (const std::exception &err) {
    context->CtxFailureWithWarning(errors::InvalidArgument(err.what()));
  }
}

REGISTER_OP("FixedLengthTensor")
.Attr("IndexType: {int64, int32}")
.Attr("ValueType: {int64, int32, string}")
.Attr("max_length: int")
.Input("segment_ids: IndexType")
.Input("values: ValueType")
.Input("pad_value: ValueType")
.Output("fixed_length: ValueType")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert variable length segments into fixed length tensor.

Attr
  max_length: The size of the inner most (i.e. last) dimension.

Input
  segment_ids: 1D input tensor containing the sorted segment_ids.
  values: 1D input tensor containing the values.
  pad_value: The value used for padding the fixed length tensor.

Outputs
  fixed_length: A fixed length tensor of size [batch_size, max_length].
)doc");

template<typename IndexType, typename ValueType>
class FixedLengthTensor: public OpKernel {
 public:
  explicit FixedLengthTensor(OpKernelConstruction *context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("max_length", &max_length_));
  }

 private:
  int64 max_length_;

  void Compute(OpKernelContext *context) override {
    ComputeFixedLengthTensor<IndexType, ValueType, true>(context, max_length_);
  }
};

REGISTER_OP("FixedLengthTensorV2")
.Attr("IndexType: {int64, int32}")
.Attr("ValueType: {int64, int32, string}")
.Attr("max_length: int")
.Input("segment_ids: IndexType")
.Input("values: ValueType")
.Input("pad_value: ValueType")
.Input("batch_size: int64")
.Output("fixed_length: ValueType")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert variable length segments into fixed length tensor.

Attr
  max_length: The size of the inner most (i.e. last) dimension.

Input
  segment_ids: 1D input tensor containing the sorted segment_ids.
  values: 1D input tensor containing the values.
  pad_value: The value used for padding the fixed length tensor.
  batch_size: The batch size to use.

Outputs
  fixed_length: A fixed length tensor of size [batch_size, max_length].
)doc");

template<typename IndexType, typename ValueType>
class FixedLengthTensorV2: public OpKernel {
 public:
  explicit FixedLengthTensorV2(OpKernelConstruction *context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("max_length", &max_length_));
  }

 private:
  int64 max_length_;

  void Compute(OpKernelContext *context) override {
    ComputeFixedLengthTensor<IndexType, ValueType, false>(context, max_length_);
  }
};

#define REGISTER_SPARSE_TO_FIXED_LENGTH(IndexType, ValueType)   \
  REGISTER_KERNEL_BUILDER(                                      \
    Name("FixedLengthTensor")                                   \
    .Device(DEVICE_CPU)                                         \
    .TypeConstraint<IndexType>("IndexType")                     \
    .TypeConstraint<ValueType>("ValueType"),                    \
    FixedLengthTensor<IndexType, ValueType>);                   \
                                                                \
  REGISTER_KERNEL_BUILDER(                                      \
    Name("FixedLengthTensorV2")                                 \
    .Device(DEVICE_CPU)                                         \
    .TypeConstraint<IndexType>("IndexType")                     \
    .TypeConstraint<ValueType>("ValueType"),                    \
    FixedLengthTensorV2<IndexType, ValueType>);                 \

REGISTER_SPARSE_TO_FIXED_LENGTH(int64, int64)
REGISTER_SPARSE_TO_FIXED_LENGTH(int64, int32)
REGISTER_SPARSE_TO_FIXED_LENGTH(int64, string)
REGISTER_SPARSE_TO_FIXED_LENGTH(int32, int64)
REGISTER_SPARSE_TO_FIXED_LENGTH(int32, int32)
REGISTER_SPARSE_TO_FIXED_LENGTH(int32, string)
