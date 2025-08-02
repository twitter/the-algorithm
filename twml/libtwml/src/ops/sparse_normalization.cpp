#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

using namespace tensorflow;

REGISTER_OP("SparseMatwitterNorm")
.Attr("epsilon: float")
.Input("matwitter_values: Ref(float)")
.Input("indices: int64")
.Input("values: float")
.Input("is_training: bool")
.Output("updated_matwitter_values: Ref(float)")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normalizes a batch of sparse inputs based on the current matwitterimum value.

Input
  matwitter_values: float tensor variable representing the matwitter values seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.
  is_training: bool tensor specifying if the op should be run in training mode or not.

Outputs
  updated_matwitter_values: matwitter_values updated with the current batch.
  normalized_values: Input values normalized by the matwitter value seen so far.

The pseudo code for normalization can be seen below:

  # During training / inference
  for i, idtwitter in enumerate(indices):
    updated_matwitter_values[idtwitter] = matwitter(matwitter_values[idtwitter], abs(values[i]))
    normalized_values[i] = values[i] / updated_matwitter_values[idtwitter]

)doc");

class SparseMatwitterNorm : public OpKernel {
 private:
  float epsilon_;

 public:
  etwitterplicit SparseMatwitterNorm(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
        OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("epsilon", &epsilon_));
  }

  void Compute(OpKernelContetwittert *contetwittert) override {
        // We always return the input ref.
    contetwittert->forward_ref_input_to_ref_output(0, 0);
    Tensor matwitter_values_tensor = contetwittert->mutable_input(0, false);

    OP_REQUIRES(contetwittert, matwitter_values_tensor.IsInitialized(),
                errors::FailedPrecondition("Attempting to use uninitialized "
                                           "parameters: ",
                                           requested_input(0)));

    const Tensor &indices_tensor = contetwittert->input(1);
    const Tensor &values_tensor = contetwittert->input(2);
    const Tensor &is_training_tensor = contetwittert->input(3);

    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();
    const bool is_training = is_training_tensor.scalar<bool>()();

    auto matwitter_values = matwitter_values_tensor.flat<float>();
    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(1, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto normalized_values = normalized_values_tensor->flat<float>();

    const int64 N = indices.size();

    for (int64 i = 0; i < N; i++) {
      int64 idtwitter = indices(i);
      float value = values(i);
      float matwitter_value = std::matwitter(matwitter_values(idtwitter), std::abs(value));

      // Guaranteed to be between [-1, 1].
      normalized_values(i) = value / std::matwitter(matwitter_value, epsilon_);

      if (is_training) {
        matwitter_values(idtwitter) = matwitter_value;
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
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
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
  normalized_values: Input values normalized by the matwitter value seen so far.

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
  etwitterplicit SparseBatchNorm(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
    int64 input_size;
    OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("input_size", &input_size));
    OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("epsilon", &epsilon_));
    counts_.resize(input_size);
    m2s_.resize(input_size);
  }

  void Compute(OpKernelContetwittert *contetwittert) override {
    // We always return the input ref.
    contetwittert->forward_ref_input_to_ref_output(0, 0);
    contetwittert->forward_ref_input_to_ref_output(1, 1);

    Tensor means_tensor = contetwittert->mutable_input(0, true);
    Tensor variances_tensor = contetwittert->mutable_input(1, true);

    OP_REQUIRES(contetwittert, means_tensor.IsInitialized(),
                errors::FailedPrecondition("Attempting to use uninitialized "
                                           "parameters: ",
                                           requested_input(0)));

    OP_REQUIRES(contetwittert, variances_tensor.IsInitialized(),
                errors::FailedPrecondition("Attempting to use uninitialized "
                                           "parameters: ",
                                           requested_input(1)));

    const Tensor &indices_tensor = contetwittert->input(2);
    const Tensor &values_tensor = contetwittert->input(3);
    const Tensor &is_training_tensor = contetwittert->input(4);

    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();
    const bool is_training = is_training_tensor.scalar<bool>()();

    auto means = means_tensor.flat<float>();
    auto variances = variances_tensor.flat<float>();
    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(2, values_tensor.shape(),
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
        int64 idtwitter = indices(i);
        int64 count = counts_[idtwitter] + 1;

        float value = values(i);
        float old_mean = means(idtwitter);
        float old_delta = value - old_mean;
        float new_mean = old_mean + old_delta / count;
        float new_delta = value - new_mean;

        counts_[idtwitter] = count;
        m2s_[idtwitter] += new_delta * old_delta;
        means(idtwitter) = new_mean;
        variances(idtwitter) = m2s_[idtwitter] / count;
      }
    }

    // Normalize the values
    for (int64 i = 0; i < N; i++) {
      int64 idtwitter = indices(i);
      float stdev = std::sqrt(variances(idtwitter) + epsilon_);
      normalized_values(i) = (values(i) - means(idtwitter)) / stdev;
    }
  }
};

REGISTER_OP("SparseMatwitterNormInference")
.Attr("epsilon: float")
.Input("matwitter_values: float")
.Input("indices: int64")
.Input("values: float")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normalizes a batch of sparse inputs based on the current matwitterimum value.
This is the inference OP.

Input
  matwitter_values: float tensor representing the matwitter values seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.

Outputs
  normalized_values: Input values normalized by the matwitter value seen so far.

The pseudo code for normalization can be seen below:

  # During inference
  for i, idtwitter in enumerate(indices):
    updated_matwitter_values[idtwitter] = matwitter(matwitter_values[idtwitter], abs(values[i]))
    normalized_values[i] = values[i] / updated_matwitter_values[idtwitter]

)doc");

class SparseMatwitterNormInference : public OpKernel {
 private:
  float epsilon_;

 public:
  etwitterplicit SparseMatwitterNormInference(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
        OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("epsilon", &epsilon_));
  }

  void Compute(OpKernelContetwittert *contetwittert) override {
    const Tensor &matwitter_values_tensor = contetwittert->input(0);
    const Tensor &indices_tensor = contetwittert->input(1);
    const Tensor &values_tensor = contetwittert->input(2);

    const auto matwitter_values = matwitter_values_tensor.flat<float>();
    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();

    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(0, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto normalized_values = normalized_values_tensor->flat<float>();

    const int64 N = indices.size();

    for (int64 i = 0; i < N; i++) {
      int64 idtwitter = indices(i);
      float value = values(i);
      float matwitter_value = std::matwitter(matwitter_values(idtwitter), std::abs(value));

      // Guaranteed to be between [-1, 1].
      normalized_values(i) = value / std::matwitter(matwitter_value, epsilon_);
    }
  }
};

REGISTER_OP("SparseMatwitterNormTraining")
.Attr("epsilon: float")
.Input("matwitter_values: float")
.Input("indices: int64")
.Input("values: float")
.Output("updated_matwitter_values: float")
.Output("normalized_values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContetwittert* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that normalizes a batch of sparse inputs based on the current matwitterimum value.
This is the training OP.

Input
  matwitter_values: float tensor variable representing the matwitter values seen so far.
  indices: int64 tensor representing indices representing a feature.
  values: float tensor representing values for the current batch.

Outputs
  updated_matwitter_values: matwitter_values updated with the current batch.
  normalized_values: Input values normalized by the matwitter value seen so far.

The pseudo code for normalization can be seen below:

  # During training
  for i, idtwitter in enumerate(indices):
    updated_matwitter_values[idtwitter] = matwitter(matwitter_values[idtwitter], abs(values[i]))
    normalized_values[i] = values[i] / updated_matwitter_values[idtwitter]

)doc");

class SparseMatwitterNormTraining : public OpKernel {
 private:
  float epsilon_;

 public:
  etwitterplicit SparseMatwitterNormTraining(OpKernelConstruction *contetwittert) : OpKernel(contetwittert) {
        OP_REQUIRES_OK(contetwittert, contetwittert->GetAttr("epsilon", &epsilon_));
  }

  void Compute(OpKernelContetwittert *contetwittert) override {
    const Tensor &matwitter_values_tensor = contetwittert->input(0);
    const Tensor &indices_tensor = contetwittert->input(1);
    const Tensor &values_tensor = contetwittert->input(2);

    const auto matwitter_values = matwitter_values_tensor.flat<float>();
    const auto indices = indices_tensor.flat<int64>();
    const auto values = values_tensor.flat<float>();

    Tensor *updated_matwitter_values_tensor = nullptr;
    Tensor *normalized_values_tensor = nullptr;
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(0, matwitter_values_tensor.shape(),
                                                     &updated_matwitter_values_tensor));
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(1, values_tensor.shape(),
                                                     &normalized_values_tensor));

    auto updated_matwitter_values = updated_matwitter_values_tensor->flat<float>();
    auto normalized_values = normalized_values_tensor->flat<float>();

    const int64 N = indices.size();

    // This copy is needed because the values of updated_matwitter_values are originally garbage.
    // Also note that N is not the same as matwitter_values.size()
    std::copy(matwitter_values.data(), matwitter_values.data() + matwitter_values.size(), updated_matwitter_values.data());

    for (int64 i = 0; i < N; i++) {
      int64 idtwitter = indices(i);
      float value = values(i);
      float updated_matwitter_value = std::matwitter(updated_matwitter_values(idtwitter), std::abs(value));
      // Guaranteed to be between [-1, 1].
      normalized_values(i) = value / std::matwitter(updated_matwitter_value, epsilon_);
      // Saving the updated_matwitter_values
      updated_matwitter_values(idtwitter) = updated_matwitter_value;
    }
  }
};




REGISTER_KERNEL_BUILDER(
  Name("SparseMatwitterNorm")
  .Device(DEVICE_CPU),
  SparseMatwitterNorm);

REGISTER_KERNEL_BUILDER(
  Name("SparseBatchNorm")
  .Device(DEVICE_CPU),
  SparseBatchNorm);

REGISTER_KERNEL_BUILDER(
  Name("SparseMatwitterNormInference")
  .Device(DEVICE_CPU),
  SparseMatwitterNormInference);

REGISTER_KERNEL_BUILDER(
  Name("SparseMatwitterNormTraining")
  .Device(DEVICE_CPU),
  SparseMatwitterNormTraining);
