#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/common_shape_fns.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <chrono>
#include <thread>

using namespace tensorflow;

REGISTER_OP("Sleep")
.Input("num_milliseconds: int32")
.Output("sleep_time_in_ms: int32")
.SetShapeFn(tensorflow::shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that sleeps for specified number of milliseconds. 
This is a proxy to determine the number of inter_op_parallelism pool. 
This is not part of the Tensorflow API as of the date of writing this 
doc. Hence, a tensorflow operation is the best resort.
Input
  num_milliseconds: A scalar tensor corresponding to the number
  of milliseconds the operation should sleep for
Output
  sleep_time_in_ms: A scalar tensor corresponding to the 
  actual number of milliseconds for which the operation slept
)doc");

class SleepOp : public OpKernel {
 public:
    explicit SleepOp(OpKernelConstruction* context) : OpKernel(context) {}

    void Compute(OpKernelContext* context) override {
      // Grab the input tensor
      const Tensor& input_tensor = context->input(0);
      auto input = input_tensor.flat<int32>();

      // Sleep for specified milliseconds
      auto start = std::chrono::high_resolution_clock::now();
      std::this_thread::sleep_for(std::chrono::milliseconds(input(0)));
      auto end = std::chrono::high_resolution_clock::now();
      std::chrono::duration<double, std::milli> elapsed = end-start;

      // Set the output tensor
      Tensor* output_tensor = NULL;
      OP_REQUIRES_OK(context, context->allocate_output(0, TensorShape({}), &output_tensor));
      auto output_flat = output_tensor->flat<int32>();
      output_flat(0) = elapsed.count();
    }
};

REGISTER_KERNEL_BUILDER(Name("Sleep").Device(DEVICE_CPU), SleepOp);
