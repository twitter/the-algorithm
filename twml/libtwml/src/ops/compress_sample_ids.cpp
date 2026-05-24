#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <algorithm>    // std::fill_n

using namespace tensorflow;

REGISTER_OP("CompressSampleIds")
.Attr("T: {int32}")
.Input("input: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    c->set_output(0, c->Vector(c->kUnknownDim));
    return Status::OK();
  });


template<typename T>
class CompressSampleIds : public OpKernel {
 public:
  explicit CompressSampleIds(OpKernelConstruction* context) : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    // Grab the input tensor
    const Tensor& input_tensor = context->input(0);
    auto input = input_tensor.flat<T>();
    const int N = input.size();

    // Check for improper input
    bool error = (N > 0 && input(0) < 0);
    for (int i = 1; !error && i < N; i++) {
      error = input(i - 1) > input(i);
    }

    OP_REQUIRES(
      context, !error,
      errors::InvalidArgument(
        "Error in CompressSampleIds. SampleIds must be non-negative and non-decreasing"
      )
    );

    // choose output size, either last input element + 1, or 0
    int output_size = 0;
    if (N > 0) {
      output_size = input(N - 1) + 1;
    }

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQUIRES_OK(
      context,
      context->allocate_output(0, TensorShape({output_size}), &output_tensor)
    );
    auto output_flat = output_tensor->flat<T>();

    // Zero-initialize output
    for (int i = 0; i < output_size; i++) {
      output_flat(i) = 0;
    }

    // count how many of each input element
    for (int i = 0; i < N; i++) {
      output_flat(input(i)) ++;
    }
  }
};

REGISTER_OP("DecompressSampleIds")
.Attr("T: {int32}")
.Input("input: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    c->set_output(0, c->Vector(c->kUnknownDim));
    return Status::OK();
  });


template<typename T>
class DecompressSampleIds : public OpKernel {
 public:
  explicit DecompressSampleIds(OpKernelConstruction* context) : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    // Grab the input tensor
    const Tensor& input_tensor = context->input(0);
    auto input = input_tensor.flat<T>();
    const int N = input.size();

    // Check for improper input
    bool error = false;
    int output_size = 0;
    for (int i = 0; !error && i < N; i++) {
      error = input(i) < 0;
      output_size += input(i);
    }

    OP_REQUIRES(
      context, !error,
      errors::InvalidArgument(
        "Error in DecompressSampleIds. Inputs must be non-negative."
      )
    );

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQUIRES_OK(
      context,
      context->allocate_output(0, TensorShape({output_size}),&output_tensor)
    );
    auto output_flat = output_tensor->flat<T>();

    T *output_data = output_flat.data();
    for (int current_sample = 0; current_sample < N; current_sample++) {
      std::fill_n(output_data, input(current_sample), current_sample);
      output_data += input(current_sample);
    }
  }
};



#define REGISTER(Type)              \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("CompressSampleIds")       \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    CompressSampleIds<Type>);       \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("DecompressSampleIds")     \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    DecompressSampleIds<Type>);     \
                                    \

REGISTER(int32);
