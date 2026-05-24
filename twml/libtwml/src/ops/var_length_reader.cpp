#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

using namespace tensorflow;

REGISTER_OP("VarLengthReader")
.Input("input1: int32")
.Output("output: int32")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    ::tensorflow::shape_inference::ShapeHandle input;
    // check that input has only 1 dimension.
    TF_RETURN_IF_ERROR(c->WithRank(c->input(0), 1, &input));
    // there's no inference on output shape.
    return Status::OK();
  });


class VarLengthReaderOp : public OpKernel {
 public:
  explicit VarLengthReaderOp(OpKernelConstruction* context) : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    // Grab the input tensor
    const Tensor& input_tensor = context->input(0);
    auto input = input_tensor.flat<int32>();

    // get the first element in the input tensor, use it as output shape.
    int32 len = input(0);
    TensorShape output_shape = {1, len};

    // Create an output tensor, the size is determined by the content of input.
    Tensor* output_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, output_shape, &output_tensor));

    auto output_flat = output_tensor->flat<int32>();

    // Fill output with ones.
    const int N = output_flat.size();
    for (int i = 0; i < N; i++) {
      output_flat(i) = 1;
    }
  }
};

REGISTER_KERNEL_BUILDER(Name("VarLengthReader").Device(DEVICE_CPU), VarLengthReaderOp);
