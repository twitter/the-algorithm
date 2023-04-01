#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("FeatureId")
.Attr("feature_names: list(string)")
.Output("output: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that hashes a list of strings into int64. This is used for feature name hashing.

Attr
  feature_names: a list of string feature names (list(string)).

Outputs
  ouput: hashes corresponding to the string feature names (int64).
)doc");


class FeatureId : public OpKernel {
 private:
    std::vector<string> input_vector;

 public:
  explicit FeatureId(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_names", &input_vector));
  }

  void Compute(OpKernelContext* context) override {
    // Get size of the input_vector and create TensorShape shape
    const int total_size = static_cast<int>(input_vector.size());
    TensorShape shape = {total_size};

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, shape,
                             &output_tensor));
    auto output_flat = output_tensor->flat<int64>();

    // Transform the input tensor into a int64
    for (int i = 0; i < total_size; i++) {
      output_flat(i) = twml::featureId(input_vector[i]);
    }
  }
};


REGISTER_KERNEL_BUILDER(
  Name("FeatureId")
  .Device(DEVICE_CPU),
  FeatureId);
