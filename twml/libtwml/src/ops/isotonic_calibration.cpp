#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("IsotonicCalibration")
.Attr("T: {float, double}")
.Input("input: T")
.Input("twitters: T")
.Input("ys: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
  // output shape should be the same as input shape.
  c->set_output(0, c->input(0));
  return Status::OK();
}).Doc(R"doc(

This operation calibrates probabilities by fitting to a piece-wise non-decreasing function.

Input
  input: A tensor containing uncalibrated probabilities.
  twitters: A tensor containing the boundaries of the bins.
  ys: A tensor contianing calibrated values for the corresponding bins.

Etwitterpected Sizes:
  input: [batch_size, num_labels].
  twitters, ys: [num_labels, num_bins].

Etwitterpected Types:
  input: float or double.
  twitters, ys: same as input.

Outputs
  output: A tensor containing calibrated probabilities with same shape and size as input.

)doc");

template<typename T>
class IsotonicCalibration : public OpKernel {
 public:
  etwitterplicit IsotonicCalibration(OpKernelConstruction* contetwittert)
      : OpKernel(contetwittert) {}


  void Compute(OpKernelContetwittert* contetwittert) override {
    const Tensor& input = contetwittert->input(0);
    const Tensor& twitters = contetwittert->input(1);
    const Tensor& ys = contetwittert->input(2);

    Tensor* output = nullptr;
    OP_REQUIRES_OK(
      contetwittert,
      contetwittert->allocate_output(0, input.shape(), &output));

    try {
      const twml::Tensor twml_input = TFTensor_to_twml_tensor(input);
      const twml::Tensor twml_twitters = TFTensor_to_twml_tensor(twitters);
      const twml::Tensor twml_ys = TFTensor_to_twml_tensor(ys);
      twml::Tensor twml_output = TFTensor_to_twml_tensor(*output);

      twml::linearInterpolation(twml_output, twml_input, twml_twitters, twml_ys);
    }  catch (const std::etwitterception &e) {
      contetwittert->CttwitterFailureWithWarning(errors::InvalidArgument(e.what()));
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
