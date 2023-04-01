#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("IsotonicCalibration")
.Attr("T: {float, double}")
.Input("input: T")
.Input("xs: T")
.Input("ys: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  // output shape should be the same as input shape.
  c->set_output(0, c->input(0));
  return Status::OK();
}).Doc(R"doc(

This operation calibrates probabilities by fitting to a piece-wise non-decreasing function.

Input
  input: A tensor containing uncalibrated probabilities.
  xs: A tensor containing the boundaries of the bins.
  ys: A tensor contianing calibrated values for the corresponding bins.

Expected Sizes:
  input: [batch_size, num_labels].
  xs, ys: [num_labels, num_bins].

Expected Types:
  input: float or double.
  xs, ys: same as input.

Outputs
  output: A tensor containing calibrated probabilities with same shape and size as input.

)doc");

template<typename T>
class IsotonicCalibration : public OpKernel {
 public:
  explicit IsotonicCalibration(OpKernelConstruction* context)
      : OpKernel(context) {}


  void Compute(OpKernelContext* context) override {
    const Tensor& input = context->input(0);
    const Tensor& xs = context->input(1);
    const Tensor& ys = context->input(2);

    Tensor* output = nullptr;
    OP_REQUIRES_OK(
      context,
      context->allocate_output(0, input.shape(), &output));

    try {
      const twml::Tensor twml_input = TFTensor_to_twml_tensor(input);
      const twml::Tensor twml_xs = TFTensor_to_twml_tensor(xs);
      const twml::Tensor twml_ys = TFTensor_to_twml_tensor(ys);
      twml::Tensor twml_output = TFTensor_to_twml_tensor(*output);

      twml::linearInterpolation(twml_output, twml_input, twml_xs, twml_ys);
    }  catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

#define REGISTER(Type)                \
                                      \
  REGISTER_KERNEL_BUILDER(            \
    Name("IsotonicCalibration")       \
    .Device(DEVICE_CPU)               \
    .TypeConstraint<Type>("T"),       \
    IsotonicCalibration<Type>);       \

REGISTER(float);
REGISTER(double);
