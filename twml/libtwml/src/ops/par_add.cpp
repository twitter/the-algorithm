#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/util/work_sharder.h"
#include "tensorflow/core/lib/core/threadpool.h"
#include "tensorflow/core/platform/env.h"
#include "tensorflow/core/platform/mutex.h"
#include "tensorflow/core/platform/logging.h"
#include <iostream>

#include <vector>

using namespace tensorflow;

REGISTER_OP("ParAdd")
  .Input("input_a: float")
  .Input("input_b: float")
  .Output("a_plus_b: float")
  .SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
      c->set_output(0, c->input(0));
      return Status::OK();
  });


class ParAddOp : public OpKernel {
 public:
  explicit ParAddOp(OpKernelConstruction* context) : OpKernel(context) {
  }

  void Compute(OpKernelContext* context) override {
    // Grab the input tensor
    const Tensor& input_tensor0 = context->input(0);
    auto input_flat0 = input_tensor0.flat<float>();
    const Tensor& input_tensor1 = context->input(1);
    auto input_flat1 = input_tensor1.flat<float>();

    OP_REQUIRES(context, input_tensor0.shape() == input_tensor1.shape(),
                errors::InvalidArgument("Input tensors must be identical shape."));

    // Create an output tensor
    Tensor* output_tensor = NULL;
    OP_REQUIRES_OK(context,
                   context->allocate_output(0,
                                            input_tensor0.shape(),
                                            &output_tensor));
    auto output_flat = output_tensor->flat<float>();

    // PARALLEL ADD
    const int N = input_flat0.size();

    // retrieve the thread pool from the op context
    auto worker_threads = *(context->device()->tensorflow_cpu_worker_threads());

    // Definition of the computation thread
    auto task = [=, &input_flat0, &input_flat1, &output_flat](int64 start, int64 limit) {
      for (; start < limit; ++start) {
        output_flat(start) = input_flat0(start) + input_flat1(start);
      }
    };

    // this is a heuristic. high number is likely to be sharded into smaller pieces
    int64 cost_per_unit = 1;

    // let Tensorflow split up the work as it sees fit
    Shard(worker_threads.num_threads,
          worker_threads.workers,
          N,
          cost_per_unit,
          task);
  }
};

REGISTER_KERNEL_BUILDER(Name("ParAdd").Device(DEVICE_CPU), ParAddOp);


