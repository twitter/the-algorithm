#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;


void ComputeDiscretizers(OpKernelContext* context, const bool return_bin_indices = false) {
  const Tensor& keys = context->input(0);
  const Tensor& vals = context->input(1);
  const Tensor& bin_ids = context->input(2);
  const Tensor& bin_vals = context->input(3);
  const Tensor& feature_offsets = context->input(4);

  Tensor* new_keys = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(0, keys.shape(),
                                                   &new_keys));
  Tensor* new_vals = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(1, keys.shape(),
                                                   &new_vals));

  try {
    twml::Tensor out_keys_ = TFTensor_to_twml_tensor(*new_keys);
    twml::Tensor out_vals_ = TFTensor_to_twml_tensor(*new_vals);

    const twml::Tensor in_keys_ = TFTensor_to_twml_tensor(keys);
    const twml::Tensor in_vals_ = TFTensor_to_twml_tensor(vals);
    const twml::Tensor bin_ids_ = TFTensor_to_twml_tensor(bin_ids);
    const twml::Tensor bin_vals_ = TFTensor_to_twml_tensor(bin_vals);
    const twml::Tensor feature_offsets_ = TFTensor_to_twml_tensor(feature_offsets);
    twml::mdlInfer(out_keys_, out_vals_,
                   in_keys_, in_vals_,
                   bin_ids_, bin_vals_,
                   feature_offsets_,
                   return_bin_indices);
  }  catch (const std::exception &e) {
    context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
  }
}

REGISTER_OP("MDL")
.Attr("T: {float, double}")
.Input("keys: int64")
.Input("vals: T")
.Input("bin_ids: int64")
.Input("bin_vals: T")
.Input("feature_offsets: int64")
.Output("new_keys: int64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    // TODO: check sizes
    c->set_output(0, c->input(0));
    c->set_output(1, c->input(0));
    return Status::OK();
}).Doc(R"doc(

This operation discretizes a tensor containing continuous features.

Input
  keys: A tensor containing feature ids.
  vals: A tensor containing values at corresponding feature ids.
  bin_ids: A tensor containing the discretized feature id for a given bin.
  bin_vals: A tensor containing the bin boundaries for value at a given feature id.
  feature_offsets: Specifies the starting location of bins for a given feature id.

Expected Sizes:
  keys, vals: [N].
  bin_ids, bin_vals: [sum_{n=1}^{n=num_classes} num_bins(n)]

  where
  - N is the number of sparse features in the current batch.
  - [0, num_classes) represents the range each feature id can take.
  - num_bins(n) is the number of bins for a given feature id.
  - If num_bins is fixed, then xs, ys are of size [num_classes * num_bins].

Expected Types:
  keys, bin_ids: int64.
  vals: float or double.
  bin_vals: same as vals.

Before using MDL, you should use a hashmap to get the intersection of
input `keys` with the features that MDL knows about:
::
  keys, vals # keys can be in range [0, 1 << 63)
  mdl_keys = hashmap.find(keys) # mdl_keys are now in range [0, num_classes_from_calibration)
  mdl_keys = where (mdl_keys != -1) # Ignore keys not found


Inside MDL, the following is happening:
::
  start = offsets[key[i]]
  end = offsets[key[i] + 1]
  idx = binary_search for val[i] in [bin_vals[start], bin_vals[end]]

  result_keys[i] = bin_ids[idx]
  val[i] = 1 # binary feature value

Outputs
  new_keys: The discretized feature ids with same shape and size as keys.
  new_vals: The discretized values with the same shape and size as vals.

)doc");


template<typename T>
class MDL : public OpKernel {
 public:
  explicit MDL(OpKernelConstruction* context) : OpKernel(context) {
  }

  void Compute(OpKernelContext* context) override {
    ComputeDiscretizers(context);
  }
};

REGISTER_OP("PercentileDiscretizer")
.Attr("T: {float, double}")
.Input("keys: int64")
.Input("vals: T")
.Input("bin_ids: int64")
.Input("bin_vals: T")
.Input("feature_offsets: int64")
.Output("new_keys: int64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    // TODO: check sizes
    c->set_output(0, c->input(0));
    c->set_output(1, c->input(0));
    return Status::OK();
}).Doc(R"doc(

This operation discretizes a tensor containing continuous features.

Input
  keys: A tensor containing feature ids.
  vals: A tensor containing values at corresponding feature ids.
  bin_ids: A tensor containing the discretized feature id for a given bin.
  bin_vals: A tensor containing the bin boundaries for value at a given feature id.
  feature_offsets: Specifies the starting location of bins for a given feature id.

Expected Sizes:
  keys, vals: [N].
  bin_ids, bin_vals: [sum_{n=1}^{n=num_classes} num_bins(n)]

  where
  - N is the number of sparse features in the current batch.
  - [0, num_classes) represents the range each feature id can take.
  - num_bins(n) is the number of bins for a given feature id.
  - If num_bins is fixed, then xs, ys are of size [num_classes * num_bins].

Expected Types:
  keys, bin_ids: int64.
  vals: float or double.
  bin_vals: same as vals.

Before using PercentileDiscretizer, you should use a hashmap to get the intersection of
input `keys` with the features that PercentileDiscretizer knows about:
::
  keys, vals # keys can be in range [0, 1 << 63)
  percentile_discretizer_keys = hashmap.find(keys) # percentile_discretizer_keys are now in range [0, num_classes_from_calibration)
  percentile_discretizer_keys = where (percentile_discretizer_keys != -1) # Ignore keys not found


Inside PercentileDiscretizer, the following is happening:
::
  start = offsets[key[i]]
  end = offsets[key[i] + 1]
  idx = binary_search for val[i] in [bin_vals[start], bin_vals[end]]

  result_keys[i] = bin_ids[idx]
  val[i] = 1 # binary feature value

Outputs
  new_keys: The discretized feature ids with same shape and size as keys.
  new_vals: The discretized values with the same shape and size as vals.

)doc");

template<typename T>
class PercentileDiscretizer : public OpKernel {
 public:
  explicit PercentileDiscretizer(OpKernelConstruction* context) : OpKernel(context) {
  }

  void Compute(OpKernelContext* context) override {
    ComputeDiscretizers(context);
  }
};


REGISTER_OP("PercentileDiscretizerBinIndices")
.Attr("T: {float, double}")
.Input("keys: int64")
.Input("vals: T")
.Input("bin_ids: int64")
.Input("bin_vals: T")
.Input("feature_offsets: int64")
.Output("new_keys: int64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    // TODO: check sizes
    c->set_output(0, c->input(0));
    c->set_output(1, c->input(0));
    return Status::OK();
}).Doc(R"doc(

This operation discretizes a tensor containing continuous features.
If the feature id and bin id of the discretized value is the same on multiple runs, they
will always be assigned to the same output key and value, regardless of the bin_id assigned during
calibration.

Input
  keys: A tensor containing feature ids.
  vals: A tensor containing values at corresponding feature ids.
  bin_ids: A tensor containing the discretized feature id for a given bin.
  bin_vals: A tensor containing the bin boundaries for value at a given feature id.
  feature_offsets: Specifies the starting location of bins for a given feature id.

Expected Sizes:
  keys, vals: [N].
  bin_ids, bin_vals: [sum_{n=1}^{n=num_classes} num_bins(n)]

  where
  - N is the number of sparse features in the current batch.
  - [0, num_classes) represents the range each feature id can take.
  - num_bins(n) is the number of bins for a given feature id.
  - If num_bins is fixed, then xs, ys are of size [num_classes * num_bins].

Expected Types:
  keys, bin_ids: int64.
  vals: float or double.
  bin_vals: same as vals.

Before using PercentileDiscretizerBinIndices, you should use a hashmap to get the intersection of
input `keys` with the features that PercentileDiscretizerBinIndices knows about:
::
  keys, vals # keys can be in range [0, 1 << 63)
  percentile_discretizer_keys = hashmap.find(keys) # percentile_discretizer_keys are now in range [0, num_classes_from_calibration)
  percentile_discretizer_keys = where (percentile_discretizer_keys != -1) # Ignore keys not found


Inside PercentileDiscretizerBinIndices, the following is happening:
::
  start = offsets[key[i]]
  end = offsets[key[i] + 1]
  idx = binary_search for val[i] in [bin_vals[start], bin_vals[end]]

  result_keys[i] = bin_ids[idx]
  val[i] = 1 # binary feature value

Outputs
  new_keys: The discretized feature ids with same shape and size as keys.
  new_vals: The discretized values with the same shape and size as vals.

)doc");

template<typename T>
class PercentileDiscretizerBinIndices : public OpKernel {
 public:
  explicit PercentileDiscretizerBinIndices(OpKernelConstruction* context) : OpKernel(context) {
  }

  void Compute(OpKernelContext* context) override {
    ComputeDiscretizers(context, true);
  }
};


#define REGISTER(Type)              \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("PercentileDiscretizerBinIndices")   \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    PercentileDiscretizerBinIndices<Type>);   \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("PercentileDiscretizer")   \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    PercentileDiscretizer<Type>);   \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("MDL")                     \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    MDL<Type>);                     \

REGISTER(float);
REGISTER(double);
