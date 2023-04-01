#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include <map>
#include <vector>

REGISTER_OP("FeatureExtractor")
.Attr("T: {float, double} = DT_FLOAT")
.Input("mask_in: bool")
.Input("ids_in: int64")
.Input("keys_in: int64")
.Input("values_in: T")
.Input("codes_in: int64")
.Input("types_in: int8")
.Output("ids_out: int64")
.Output("keys_out: int64")
.Output("values_out: T")
.Output("codes_out: int64")
.Output("types_out: int8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that extracts the desired indices of a Tensor based on a mask

Input
  mask_in: boolean Tensor that determines which are the indices to be kept (bool)
  ids_in: input indices Tensor (int64)
  keys_in: input keys Tensor (int64)
  values_in: input values Tensor (float/double)
  codes_in: input codes Tensor (int64)
  types_in: input types Tensor(int8)

Outputs
  ids_out: output indices Tensor (int64)
  keys_out: output keys Tensor (int64)
  values_out: output values Tensor (float/double)
  codes_out: output codes Tensor (int64)
  types_out: output types Tensor(int8)

)doc");
template <typename T>
class FeatureExtractor : public OpKernel {
 public:
  explicit FeatureExtractor(OpKernelConstruction* context)
      : OpKernel(context) {}

  template <typename A, typename U>
  bool allequal(const A &t, const U &u) {
      return t == u;
  }

  template <typename A, typename U, typename... Others>
  bool allequal(const A &t, const U &u, Others const &... args) {
      return (t == u) && allequal(u, args...);
  }

  void Compute(OpKernelContext* context) override {
    // Get input tensors
    const Tensor& input_mask = context->input(0);
    const Tensor& input_ids = context->input(1);
    const Tensor& input_keys = context->input(2);
    const Tensor& input_values = context->input(3);
    const Tensor& input_codes = context->input(4);
    const Tensor& input_types = context->input(5);

    auto mask = input_mask.flat<bool>();
    auto ids = input_ids.flat<int64>();
    auto keys = input_keys.flat<int64>();
    auto codes = input_codes.flat<int64>();
    auto values = input_values.flat<T>();
    auto types = input_types.flat<int8>();

    // Verify that all Tensors have the same size.
    OP_REQUIRES(context, allequal(mask.size(), ids.size(), keys.size(), codes.size(), values.size(), types.size()),
                errors::InvalidArgument("all input vectors must be the same size."));

    // Get the size of the output vectors by counting the numbers of trues.
    int total_size = 0;
    for (int i = 0; i < mask.size(); i++) {
      if (mask(i))
        total_size += 1;
    }

    // Shape is the number of Trues in the mask Eigen::Tensor
    TensorShape shape_out = {total_size};

    // Create the output tensors
    Tensor* output_codes = nullptr;
    Tensor* output_ids = nullptr;
    Tensor* output_values = nullptr;
    Tensor* output_types = nullptr;
    Tensor* output_keys = nullptr;

    OP_REQUIRES_OK(context, context->allocate_output(0, shape_out, &output_ids));
    OP_REQUIRES_OK(context, context->allocate_output(1, shape_out, &output_keys));
    OP_REQUIRES_OK(context, context->allocate_output(2, shape_out, &output_values));
    OP_REQUIRES_OK(context, context->allocate_output(3, shape_out, &output_codes));
    OP_REQUIRES_OK(context, context->allocate_output(4, shape_out, &output_types));

    auto output_ids_ = output_ids->flat<int64>();
    auto output_keys_ = output_keys->flat<int64>();
    auto output_codes_ = output_codes->flat<int64>();
    auto output_values_ = output_values->flat<T>();
    auto output_types_ = output_types->flat<int8>();

    // Iterate through the mask and set values to output Eigen::Tensors
    int j = 0;
    for (int i = 0; i < mask.size(); i++) {
      if (mask(i)) {
        output_ids_(j) = ids(i);
        output_keys_(j) = keys(i);
        output_values_(j) = values(i);
        output_codes_(j) = codes(i);
        output_types_(j) = types(i);
        ++j;
      }
    }
  }
};

#define REGISTER(Type)                        \
                                              \
  REGISTER_KERNEL_BUILDER(                    \
  Name("FeatureExtractor")  \
  .Device(DEVICE_CPU)                         \
  .TypeConstraint<Type>("T"),                 \
  FeatureExtractor<Type>);  \

REGISTER(float);
REGISTER(double);
