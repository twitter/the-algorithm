#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"

using namespace tensorflow;

REGISTER_OP("PartitionSparseTensorMod")
.Attr("T: {float, double}")
.Input("indices: int64")
.Input("values: T")
.Output("result: output_types")
.Attr("num_partitions: int")
.Attr("output_types: list({int64, float, double})")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  return Status::OK();
}).Doc(R"doc(

A tensorflow OP that partitions an input batch represented as a sparse tensor
(indices are [ids, keys]) into separate sparse tensors to more optimally place
sparse computations in distributed training.

Inputs
  indices: Indices from sparse tensor ([ids, keys] from the batch).
  values: Batch values from the original features dict.

Attr
  num_partitions: Number of partitions to generate.
  output_types: A list of types for the output tensors like
                [tf.int64, tf.float32, tf.int64, tf.float32, ...]
                The length must be 2 * num_partitions (see Outputs below)

Outputs
  List of dense tensors containing for each partition:
    - partitioned indices tensor ([ids, keys] from partitioned batch)
    - partitioned values tensor
  The list lenth is 2 * num_partitions. Example:
  [ [ids_1, keys_1], values_1, [ids_2, keys_2], values_2, ... ]
)doc");

template<typename T>
class PartitionSparseTensorMod : public OpKernel {
 private:
  int64 num_partitions;

 public:
  explicit PartitionSparseTensorMod(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("num_partitions", &num_partitions));
    OP_REQUIRES(context, num_partitions > 0,
                errors::InvalidArgument("Number of partitions must be positive"));
  }

  void Compute(OpKernelContext* context) override {
    // grab input tensors
    const Tensor& indices_tensor = context->input(0);  // (ids, keys)
    const Tensor& values_tensor = context->input(1);

    // check sizes
    int64 num_keys = indices_tensor.shape().dim_size(0);
    OP_REQUIRES(context, indices_tensor.dims() == 2,
                errors::InvalidArgument("Indices tensor must be 2D [ids, keys]"));
    OP_REQUIRES(context, indices_tensor.shape().dim_size(1) == 2,
                errors::InvalidArgument("Indices tensor must have 2 cols [ids, keys]"));
    OP_REQUIRES(context, values_tensor.shape().dim_size(0) == num_keys,
                errors::InvalidArgument("Number of values must match number of keys"));

    // grab input vectors
    auto indices = indices_tensor.flat<int64>();
    auto values = values_tensor.flat<T>();

    // count the number of features that fall in each partition
    std::vector<int64> partition_counts(num_partitions);

    for (int i = 0; i < num_keys; i++) {
      int64 key = indices(2 * i + 1);
      int64 partition_id = key % num_partitions;
      partition_counts[partition_id]++;
    }

    // allocate outputs for each partition and keep references
    std::vector<int64*> output_indices_partitions;
    std::vector<T*> output_values_partitions;
    output_indices_partitions.reserve(num_partitions);
    output_values_partitions.reserve(num_partitions);

    for (int i = 0; i < num_partitions; i++) {
      Tensor *output_indices = nullptr, *output_values = nullptr;
      TensorShape shape_indices = TensorShape({partition_counts[i], 2});
      TensorShape shape_values = TensorShape({partition_counts[i]});

      OP_REQUIRES_OK(context, context->allocate_output(2 * i, shape_indices, &output_indices));
      OP_REQUIRES_OK(context, context->allocate_output(2 * i + 1, shape_values, &output_values));

      output_indices_partitions.push_back(output_indices->flat<int64>().data());
      output_values_partitions.push_back(output_values->flat<T>().data());
    }

    // assign a partition id to each feature
    // populate tensors for each partition
    std::vector<int64> partition_indices(num_partitions);

    for (int i = 0; i < num_keys; i++) {
      int64 key = indices(2 * i + 1);
      int64 pid = key % num_partitions;  // partition id
      int64 idx = partition_indices[pid]++;

      output_indices_partitions[pid][2 * idx] = indices(2 * i);
      output_indices_partitions[pid][2 * idx + 1] = key / num_partitions;
      output_values_partitions[pid][idx] = values(i);
    }
  }
};

#define REGISTER(Type)                \
                                      \
  REGISTER_KERNEL_BUILDER(            \
    Name("PartitionSparseTensorMod")  \
    .Device(DEVICE_CPU)               \
    .TypeConstraint<Type>("T"),       \
    PartitionSparseTensorMod<Type>);  \

REGISTER(float);
REGISTER(double);
