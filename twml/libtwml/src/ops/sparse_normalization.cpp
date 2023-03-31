#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

using namespace tensorflow;

REGISTER_OP("SparseMaxNorm")
.Attr("epsilon: float")
.Input("max_values: Ref(float)")
.Input("indices: int64")
.Input("values: float")
.Input("is_training: bool")
.Output("updated_max_values: Ref(float)")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normalizes a batch of sparse inputs based on the current maximum value.

Input
  max_values: float tensor variable representing the max values seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.
  is_training: bool tensor specifying if the op should be run in training mode or not.

Outputs
  updated_max_values: max_values updated with the current batch.
  normalized_values: Input values normalized by the max value seen so far.

The pseudo code for normalization can be seen below:

  # During training / inference
  for i, idx in enumerate(indices):
    updated_max_values[idx] = max(max_values[idx], abs(values[i]))
    normalized_values[i] = values[i] / updated_max_values[idx]

)doc");

class SparseMaxNorm : public OpKernel {
 private:
  float epsilon_;

 public:
  explicit SparseMaxNorm(OpKernelConstruction *context) : OpKernel(context) {
        OP_REQUIRES_OK(context, context->GetAttr("epsilon", &epsilon_));
  }

  void Compute(OpKernelContext *context) override {
        // We always return the input ref.
    context->forward_ref_input_to_ref_output(0, 0);
    Tensor max_values_tensor = context->mutable_input(0, false);

    OP_REQUIRES(context, max_values_tensor.IsInitialized(),
                errors::FailedPrecondition("Attempting to use uninitialized "
                                           "parameters: ",
                                           requested_input(0)));

    const Tensor &indices_tensor = context->input(1);
    const Tensor &values_tensor = context->input(2);
    const Tensor &is_training_tensor = context->input(3);

    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();
    const bool is_training = is_training_tensor.scalar<bool>()();

    auto max_values = max_values_tensor.flat<float>();
    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(1, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto normalized_values = normalized_values_tensor->flat<float>();

    const int64 N = indices.size();

    for (int64 i = 0; i < N; i++) {
      int64 idx = indices(i);
      float value = values(i);
      float max_value = std::max(max_values(idx), std::abs(value));

      // Guaranteed to be between [-1, 1].
      normalized_values(i) = value / std::max(max_value, epsilon_);

      if (is_training) {
        max_values(idx) = max_value;
      }
    }
  }
};

REGISTER_OP("SparseBatchNorm")
.Attr("input_size: int")
.Attr("epsilon: float")
.Input("means: Ref(float)")
.Input("variances: Ref(float)")
.Input("indices: int64")
.Input("values: float")
.Input("is_training: bool")
.Output("updated_means: Ref(float)")
.Output("updated_vars: Ref(float)")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that performs batch normalization.

Attr
  input_size: Size of the inputs.
  epsilon: The minimum value of the variance.

Input
  mean: float tensor variable representing the running mean seen so far.
  variances: float tensor variable representing the running variance seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.
  is_training: bool tensor specifying if the op should be run in training mode or not.

Outputs
  updated_means: mean updated with the current batch.
  updated_vars: variances updated with the current batch.
  normalized_values: Input values normalized by the max value seen so far.

The pseudo code for normalization can be seen below:

    if is_training:
      means, variances = update_metrics(means, variances, values)

    normalized_values = (values - means) / sqrt(variances + epsilon)
    return normalized_values * gamma + beta

)doc");

class SparseBatchNorm : public OpKernel {
 private:
  std::vector<int64> counts_;
  std::vector<float> m2s_;
  float epsilon_;

 public:
  explicit SparseBatchNorm(OpKernelConstruction *context) : OpKernel(context) {
    int64 input_size;
    OP_REQUIRES_OK(context, context->GetAttr("input_size", &input_size));
    OP_REQUIRES_OK(context, context->GetAttr("epsilon", &epsilon_));
    counts_.resize(input_size);
    m2s_.resize(input_size);
  }

  void Compute(OpKernelContext *context) override {
    // We always return the input ref.
    context->forward_ref_input_to_ref_output(0, 0);
    context->forward_ref_input_to_ref_output(1, 1);

    Tensor means_tensor = context->mutable_input(0, true);
    Tensor variances_tensor = context->mutable_input(1, true);

    OP_REQUIRES(context, means_tensor.IsInitialized(),
                errors::FailedPrecondition("Attempting to use uninitialized "
                                           "parameters: ",
                                           requested_input(0)));

    OP_REQUIRES(context, variances_tensor.IsInitialized(),
                errors::FailedPrecondition("Attempting to use uninitialized "
                                           "parameters: ",
                                           requested_input(1)));

    const Tensor &indices_tensor = context->input(2);
    const Tensor &values_tensor = context->input(3);
    const Tensor &is_training_tensor = context->input(4);

    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();
    const bool is_training = is_training_tensor.scalar<bool>()();

    auto means = means_tensor.flat<float>();
    auto variances = variances_tensor.flat<float>();
    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(2, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto normalized_values = normalized_values_tensor->flat<float>();
    const int64 N = indices.size();

    if (is_training) {
      // Accumulate, mean, count, sum of squared differences.
      // Reference wiki:
      // https://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Online_algorithm
      // Reference paper:
      // https://www.jstor.org/stable/1266577?seq=1#page_scan_tab_contents
      for (int64 i = 0; i < N; i++) {
        int64 idx = indices(i);
        int64 count = counts_[idx] + 1;

        float value = values(i);
        float old_mean = means(idx);
        float old_delta = value - old_mean;
        float new_mean = old_mean + old_delta / count;
        float new_delta = value - new_mean;

        counts_[idx] = count;
        m2s_[idx] += new_delta * old_delta;
        means(idx) = new_mean;
        variances(idx) = m2s_[idx] / count;
      }
    }

    // Normalize the values
    for (int64 i = 0; i < N; i++) {
      int64 idx = indices(i);
      float stdev = std::sqrt(variances(idx) + epsilon_);
      normalized_values(i) = (values(i) - means(idx)) / stdev;
    }
  }
};

REGISTER_OP("SparseMaxNormInference")
.Attr("epsilon: float")
.Input("max_values: float")
.Input("indices: int64")
.Input("values: float")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normalizes a batch of sparse inputs based on the current maximum value.
This is the inference OP.

Input
  max_values: float tensor representing the max values seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.

Outputs
  normalized_values: Input values normalized by the max value seen so far.

The pseudo code for normalization can be seen below:

  # During inference
  for i, idx in enumerate(indices):
    updated_max_values[idx] = max(max_values[idx], abs(values[i]))
    normalized_values[i] = values[i] / updated_max_values[idx]

)doc");

class SparseMaxNormInference : public OpKernel {
 private:
  float epsilon_;

 public:
  explicit SparseMaxNormInference(OpKernelConstruction *context) : OpKernel(context) {
        OP_REQUIRES_OK(context, context->GetAttr("epsilon", &epsilon_));
  }

  void Compute(OpKernelContext *context) override {
    const Tensor &max_values_tensor = context->input(0);
    const Tensor &indices_tensor = context->input(1);
    const Tensor &values_tensor = context->input(2);

    const auto max_values = max_values_tensor.flat<float>();
    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();

    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto normalized_values = normalized_values_tensor->flat<float>();

    const int64 N = indices.size();

    for (int64 i = 0; i < N; i++) {
      int64 idx = indices(i);
      float value = values(i);
      float max_value = std::max(max_values(idx), std::abs(value));

      // Guaranteed to be between [-1, 1].
      normalized_values(i) = value / std::max(max_value, epsilon_);
    }
  }
};

REGISTER_OP("SparseMaxNormTraining")
.Attr("epsilon: float")
.Input("max_values: float")
.Input("indices: int64")
.Input("values: float")
.Output("updated_max_values: float")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normalizes a batch of sparse inputs based on the current maximum value.
This is the training OP.

Input
  max_values: float tensor variable representing the max values seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.

Outputs
  updated_max_values: max_values updated with the current batch.
  normalized_values: Input values normalized by the max value seen so far.

The pseudo code for normalization can be seen below:

  # During training
  for i, idx in enumerate(indices):
    updated_max_values[idx] = max(max_values[idx], abs(values[i]))
    normalized_values[i] = values[i] / updated_max_values[idx]

)doc");

class SparseMaxNormTraining : public OpKernel {
 private:
  float epsilon_;

 public:
  explicit SparseMaxNormTraining(OpKernelConstruction *context) : OpKernel(context) {
        OP_REQUIRES_OK(context, context->GetAttr("epsilon", &epsilon_));
  }

  void Compute(OpKernelContext *context) override {
    const Tensor &max_values_tensor = context->input(0);
    const Tensor &indices_tensor = context->input(1);
    const Tensor &values_tensor = context->input(2);

    const auto max_values = max_values_tensor.flat<float>();
    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();

    Tensor *updated_max_values_tensor = nullptr;
    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(context, context->allocate_output(0, max_values_tensor.shape(),
                                                     &updated_max_values_tensor));
    OP_REQUIRES_OK(context, context->allocate_output(1, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto updated_max_values = updated_max_values_tensor->flat<float>();
    auto normalized_values = normalized_values_tensor->flat<float>();

    const int64 N = indices.size();

    // This copy is needed because the values of updated_max_values are originally garbage.
    // Also note that N is not the same as max_values.size()
    std::copy(max_values.data(), max_values.data() + max_values.size(), updated_max_values.data());

    for (int64 i = 0; i < N; i++) {
      int64 idx = indices(i);
      float value = values(i);
      float updated_max_value = std::max(updated_max_values(idx), std::abs(value));
      // Guaranteed to be between [-1, 1].
      normalized_values(i) = value / std::max(updated_max_value, epsilon_);
      // Saving the updated_max_values
      updated_max_values(idx) = updated_max_value;
    }
  }
};




REGISTER_KERNEL_BUILDER(
  Name("SparseMaxNorm")
  .Device(DEVICE_CPU),
  SparseMaxNorm);

REGISTER_KERNEL_BUILDER(
  Name("SparseBatchNorm")
  .Device(DEVICE_CPU),
  SparseBatchNorm);

REGISTER_KERNEL_BUILDER(
  Name("SparseMaxNormInference")
  .Device(DEVICE_CPU),
  SparseMaxNormInference);

REGISTER_KERNEL_BUILDER(
  Name("SparseMaxNormTraining")
  .Device(DEVICE_CPU),
  SparseMaxNormTraining);
