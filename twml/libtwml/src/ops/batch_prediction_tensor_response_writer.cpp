#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("BatchPredictionTensorResponseWriter")
.Attr("T: list({string, int32, int64, float, double})")
.Input("keys: int64")
.Input("values: T")
.Output("result: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that packages keys and dense tensors into a BatchPredictionResponse.

values: list of tensors
keys: feature ids from the original BatchPredictionRequest. (int64)

Outputs
  bytes: output BatchPredictionRequest serialized using Thrift into a uint8 tensor.
)doc");

class BatchPredictionTensorResponseWriter : public OpKernel {
 public:
  explicit BatchPredictionTensorResponseWriter(OpKernelConstruction* context)
  : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    const Tensor& keys = context->input(0);

    try {
      // set keys as twml::Tensor
      const twml::Tensor in_keys_ = TFTensor_to_twml_tensor(keys);

      // check sizes
      uint64_t num_keys = in_keys_.getNumElements();
      uint64_t num_values = context->num_inputs() - 1;

      OP_REQUIRES(context, num_values % num_keys == 0,
        errors::InvalidArgument("Number of dense tensors not multiple of dense keys"));

      // set dense tensor values
      std::vector<twml::RawTensor> in_values_;
      for (int i = 1; i < context->num_inputs(); i++) {
        in_values_.push_back(TFTensor_to_twml_raw_tensor(context->input(i)));
      }

      // no continuous predictions in this op, only tensors
      const twml::Tensor dummy_cont_keys_;
      const twml::Tensor dummy_cont_values_;

      // call constructor BatchPredictionResponse
      twml::BatchPredictionResponse tempResult(
        dummy_cont_keys_, dummy_cont_values_, in_keys_, in_values_);

      // determine the length of the result
      int len = tempResult.encodedSize();
      TensorShape result_shape = {1, len};

      // Create an output tensor, the size is determined by the content of input.
      Tensor* result = NULL;
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

REGISTER_KERNEL_BUILDER(
    Name("BatchPredictionTensorResponseWriter").Device(DEVICE_CPU),
    BatchPredictionTensorResponseWriter);
