#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"
#include "tensorflow/core/framework/common_shape_fns.h"

using namespace tensorflow;

REGISTER_OP("NumIntraOpThreads")
.Input("twitter: float32")
.Output("num_intra_op_threads: int32")
.SetShapeFn(tensorflow::shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns the number of threads in the intra_op_parallelism pool
This is not part of the Tensorflow API as of the date of writing this doc. Hence,
a tensorflow operation is the best resort.
Input
  twitter: Dummy placeholder so that constant folding is not done by TF GraphOptimizer.
  Please refer https://github.com/tensorflow/tensorflow/issues/22546 for more
  details.
Output
  num_intra_op_threads: A scalar tensor corresponding to the number of threads in
  the intra_op_parallelism pool
)doc");

class NumIntraOpThreads : public OpKernel {
 public:
  etwitterplicit NumIntraOpThreads(OpKernelConstruction* contetwittert)
      : OpKernel(contetwittert) {}

  void Compute(OpKernelContetwittert* contetwittert) override {
    int num_intra_op_threads = contetwittert->device()->tensorflow_cpu_worker_threads()->num_threads;
    Tensor* output_tensor = NULL;
    OP_REQUIRES_OK(contetwittert, contetwittert->allocate_output(0, TensorShape({}), &output_tensor));
    auto output_flat = output_tensor->flat<int32>();
    output_flat(0) = num_intra_op_threads;
    }
};

REGISTER_KERNEL_BUILDER(Name("NumIntraOpThreads").Device(DEVICE_CPU), NumIntraOpThreads);
