#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

using namespace tensorflow;

REGISTER_OP("Add1")
.Attr("T: {float, double, int32}")
.Input("input1: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    c->set_output(0, c->input(0));
    return Status::OK();
  });


template<typename T>
class Add1 : public OpKernel {
 public:
  explicit Add1(OpKernelConstruction* context) : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    // Grab the input tensor
    const Tensor& input_tensor = context->input(0);
    auto input = input_tensor.flat<T>();

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, input_tensor.shape(),
                             &output_tensor));
    auto output_flat = output_tensor->flat<T>();

    // Add 1 to input and assign to output
    const int N = input.size();
    for (int i = 0; i < N; i++) {
      output_flat(i) = input(i) + 1;
    }
  }
};


REGISTER_OP("Add1Grad")
.Attr("T: {float, double, int32}")
.Input("grad_output: T")
.Output("grad_input: T")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    c->set_output(0, c->input(0));
    return Status::OK();
  });

template<typename T>
class Add1Grad : public OpKernel {
 public:
  explicit Add1Grad(OpKernelConstruction* context) : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    // Grab the input tensor
    const Tensor& grad_output_tensor = context->input(0);
    auto grad_output = grad_output_tensor.flat<T>();

    // Create an grad_input tensor
    Tensor* grad_input_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, grad_output_tensor.shape(),
                             &grad_input_tensor));

    auto grad_input_flat = grad_input_tensor->flat<T>();

    // Copy from grad_output to grad_input
    const int N = grad_output.size();
    for (int i = 0; i < N; i++) {
      grad_input_flat(i) = grad_output(i);
    }
  }
};

#define REGISTER(Type)              \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("Add1")                    \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    Add1<Type>);                    \
                                    \
  REGISTER_KERNEL_BUILDER(          \
    Name("Add1Grad")                \
    .Device(DEVICE_CPU)             \
    .TypeConstraint<Type>("T"),     \
    Add1Grad<Type>);                \

REGISTER(float);
REGISTER(double);
REGISTER(int32);
