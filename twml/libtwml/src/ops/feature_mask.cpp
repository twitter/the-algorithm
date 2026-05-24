#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include <map>
#include <vector>
#include <set>

REGISTER_OP("FeatureMask")
.Attr("T: {int64, int8}")
.Input("keep: T")
.Attr("list_keep: list(int)")
.Output("mask: bool")

.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that creates a mask of the indices that should be kept.

Attribute
list_keep: list of values which should be kept(list(int))

Input
  keep: Tensor for which we will apply the mask (int64, int8)

Outputs
  mask: boolean Tensor. (bool)

)doc");
template <typename T>
class FeatureMask : public OpKernel {
 private:
  std::set<int64> feature_set_keep;

 public:
  explicit FeatureMask(OpKernelConstruction* context)
      : OpKernel(context) {
        std::vector<int64> feature_list_keep;
        OP_REQUIRES_OK(context, context->GetAttr("list_keep", &feature_list_keep));
        // create set that contains the content of the feature_list_keep, since tensorflow does not allow
        // me to directly ouput the contents of list_keep to a set
        feature_set_keep = std::set<int64>(feature_list_keep.begin(), feature_list_keep.end());
      }

  void Compute(OpKernelContext* context) override {
    // Get size of the input_vector and create TensorShape shape
    const Tensor& input = context->input(0);

    auto keep = input.flat<T>();

    // Create an output tensor
    Tensor* output_mask = nullptr;

    // Output shape is determined and now we can copy the contents of the vector to the output Tensor.
    const int total_size_out = static_cast<int>(keep.size());

    TensorShape shape_out = {total_size_out};

    OP_REQUIRES_OK(context, context->allocate_output(0, shape_out, &output_mask));

    auto output_mask_ = output_mask->flat<bool>();

    // Check if value is in set, output is boolean
    for (int j = 0; j < keep.size(); j++){
      output_mask_(j) = (feature_set_keep.count(keep(j)));
    }
  }
};


#define REGISTER(Type)                        \
                                              \
  REGISTER_KERNEL_BUILDER(                    \
  Name("FeatureMask")  \
  .Device(DEVICE_CPU)                         \
  .TypeConstraint<Type>("T"),                 \
  FeatureMask<Type>);  \

REGISTER(int64);
REGISTER(int8);
