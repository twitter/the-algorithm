#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("BatchPredictionResponseWriter")
.Attr("T: {float, double}")
.Input("keys: int64")
.Input("values: T")
.Output("result: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that packages keys and values into a BatchPredictionResponse.

values: input feature value. (float/double)
keys: feature ids from the original BatchPredictionRequest. (int64)

Outputs
  bytes: output BatchPredictionRequest serialized using Thrift into a uint8 tensor.
)doc");

template<typename T>
class BatchPredictionResponseWriter : public OpKernel {
 public:
  explicit BatchPredictionResponseWriter(OpKernelConstruction* context)
  : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    const Tensor& keys = context->input(0);
    const Tensor& values = context->input(1);

    try {
      // Ensure the inner dimension matches.
      if (values.dim_size(values.dims() - 1) != keys.dim_size(keys.dims() - 1)) {
        throw std::runtime_error("The sizes of keys and values need to match");
      }

      // set inputs as twml::Tensor
      const twml::Tensor in_keys_ = TFTensor_to_twml_tensor(keys);
      const twml::Tensor in_values_ = TFTensor_to_twml_tensor(values);
      // no tensors in this op
      const twml::Tensor dummy_dense_keys_;
      const std::vector<twml::RawTensor> dummy_dense_values_;

      // call constructor BatchPredictionResponse
      twml::BatchPredictionResponse tempResult(
        in_keys_, in_values_, dummy_dense_keys_, dummy_dense_values_);

      // determine the length of the result
      int len = tempResult.encodedSize();
      TensorShape result_shape = {1, len};

      // Create an output tensor, the size is determined by the content of input.
      Tensor* result = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, result_shape,
                                                       &result));
      twml::Tensor out_result = TFTensor_to_twml_tensor(*result);

      // Call writer of BatchPredictionResponse
      tempResult.write(out_result);
    } catch(const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

#define REGISTER(Type)                     \
                                           \
  REGISTER_KERNEL_BUILDER(                 \
    Name("BatchPredictionResponseWriter")  \
    .Device(DEVICE_CPU)                    \
    .TypeConstraint<Type>("T"),            \
    BatchPredictionResponseWriter<Type>);  \

REGISTER(float);
REGISTER(double);
