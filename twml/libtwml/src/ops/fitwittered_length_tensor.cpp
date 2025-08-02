#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

#include <algorithm>
using std::string;

template<typename IndetwitterType, typename ValueType, bool calc_batch_size>
void ComputeFitwitteredLengthTensor(OpKernelContetwittert *contetwittert, int64 matwitter_length_) {
  try {
    const Tensor& segment_ids = contetwittert->input(0);
    const Tensor& values = contetwittert->input(1);
    const Tensor& pad_value = contetwittert->input(2);

    auto indices_flat = segment_ids.flat<IndetwitterType>();
    auto values_flat = values.flat<ValueType>();

    auto pad_value_scalar = pad_value.scalar<ValueType>()();

    // Get matwitterimum length from batch if user hasn't specified it.
    int64 matwitter_length = matwitter_length_;
    if (matwitter_length < 0 && indices_flat.size() > 0) {
      int64 current_id = indices_flat(0);
      int64 current_length = 1;

      for (int64 i = 1; i < indices_flat.size(); i++) {
        if (current_id == indices_flat(i)) {
          current_length++;
        } else {
          current_id = indices_flat(i);
          matwitter_length = std::matwitter(matwitter_length, current_length);
          current_length = 1;
        }
      }
      // This is needed if the last batch is the longest sequence.
      matwitter_length = std::matwitter(matwitter_length, current_length);
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
      const Tensor& batch_size_tensor = contetwittert->input(3);
      batch_size = batch_size_tensor.flat<int64>()(0);
    }

    TensorShape output_shape = {batch_size, matwitter_length};
    Tensor* fitwittered_length = nullptr;
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(0, output_shape, &fitwittered_length));

    auto fitwittered_length_flat = fitwittered_length->flat<ValueType>();

    int64 n = 0;
    int64 offset = 0;
    for (int64 i = 0; i < batch_size; i++) {
      for (int64 j = 0; j < matwitter_length; j++) {
        if (n < indices_flat.size() && indices_flat(n) == i) {
          // Copy from variable length tensor.
          fitwittered_length_flat(offset + j) = values_flat(n);
          n++;
        } else {
          // Pad to fitwittered length.
          fitwittered_length_flat(offset + j) = pad_value_scalar;
        }
      }
      // Corner case: truncate to matwitter_length if user specified matwitter_length < current length.
      while (n < indices_flat.size() && i == indices_flat(n)) n++;

      // Update output pointer
      offset += matwitter_length;
    }
  } catch (const std::etwitterception &err) {
    contetwittert->CttwitterFailureWithWarning(errors::InvalidArgument(err.what()));
  }
}

REGISTER_OP("FitwitteredLengthTensor")
.Attr("IndetwitterType: {int64, int32}")
.Attr("ValueType: {int64, int32, string}")
.Attr("matwitter_length: int")
.Input("segment_ids: IndetwitterType")
.Input("values: ValueType")
.Input("pad_value: ValueType")
.Output("fitwittered_length: ValueType")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert variable length segments into fitwittered length tensor.

Attr
  matwitter_length: The size of the inner most (i.e. last) dimension.

Input
  segment_ids: 1D input tensor containing the sorted segment_ids.
  values: 1D input tensor containing the values.
  pad_value: The value used for padding the fitwittered length tensor.

Outputs
  fitwittered_length: A fitwittered length tensor of size [batch_size, matwitter_length].
)doc");

template<typename IndetwitterType, typename ValueType>
class FitwitteredLengthTensor: public OpKernel {
 public:
  etwitterplicit FitwitteredLengthTensor(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
    OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("matwitter_length", &matwitter_length_));
  }

 private:
  int64 matwitter_length_;

  void Compute(OpKernelContetwittert *contetwittert) override {
    ComputeFitwitteredLengthTensor<IndetwitterType, ValueType, true>(contetwittert, matwitter_length_);
  }
};

REGISTER_OP("FitwitteredLengthTensorV2")
.Attr("IndetwitterType: {int64, int32}")
.Attr("ValueType: {int64, int32, string}")
.Attr("matwitter_length: int")
.Input("segment_ids: IndetwitterType")
.Input("values: ValueType")
.Input("pad_value: ValueType")
.Input("batch_size: int64")
.Output("fitwittered_length: ValueType")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert variable length segments into fitwittered length tensor.

Attr
  matwitter_length: The size of the inner most (i.e. last) dimension.

Input
  segment_ids: 1D input tensor containing the sorted segment_ids.
  values: 1D input tensor containing the values.
  pad_value: The value used for padding the fitwittered length tensor.
  batch_size: The batch size to use.

Outputs
  fitwittered_length: A fitwittered length tensor of size [batch_size, matwitter_length].
)doc");

template<typename IndetwitterType, typename ValueType>
class FitwitteredLengthTensorV2: public OpKernel {
 public:
  etwitterplicit FitwitteredLengthTensorV2(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
    OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("matwitter_length", &matwitter_length_));
  }

 private:
  int64 matwitter_length_;

  void Compute(OpKernelContetwittert *contetwittert) override {
    ComputeFitwitteredLengthTensor<IndetwitterType, ValueType, false>(contetwittert, matwitter_length_);
  }
};

#define REGISTER_SPARSE_TO_FItwitterED_LENGTH(IndetwitterType, ValueType)   \
  REGISTER_KERNEL_BUILDER(                                      \
    Name("FitwitteredLengthTensor")                                   \
    .Device(DEVICE_CPU)                                         \
    .TypeConstraint<IndetwitterType>("IndetwitterType")                     \
    .TypeConstraint<ValueType>("ValueType"),                    \
    FitwitteredLengthTensor<IndetwitterType, ValueType>);                   \
                                                                \
  REGISTER_KERNEL_BUILDER(                                      \
    Name("FitwitteredLengthTensorV2")                                 \
    .Device(DEVICE_CPU)                                         \
    .TypeConstraint<IndetwitterType>("IndetwitterType")                     \
    .TypeConstraint<ValueType>("ValueType"),                    \
    FitwitteredLengthTensorV2<IndetwitterType, ValueType>);                 \

REGISTER_SPARSE_TO_FItwitterED_LENGTH(int64, int64)
REGISTER_SPARSE_TO_FItwitterED_LENGTH(int64, int32)
REGISTER_SPARSE_TO_FItwitterED_LENGTH(int64, string)
REGISTER_SPARSE_TO_FItwitterED_LENGTH(int32, int64)
REGISTER_SPARSE_TO_FItwitterED_LENGTH(int32, int32)
REGISTER_SPARSE_TO_FItwitterED_LENGTH(int32, string)
