# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/common_shape_fns.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <chrono>
# nclude <thread>

us ng na space tensorflow;

REG STER_OP("Sleep")
. nput("num_m ll seconds:  nt32")
.Output("sleep_t  _ n_ms:  nt32")
.SetShapeFn(tensorflow::shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that sleeps for spec f ed number of m ll seconds. 
T   s a proxy to determ ne t  number of  nter_op_parallel sm pool. 
T   s not part of t  Tensorflow AP  as of t  date of wr  ng t  
doc.  nce, a tensorflow operat on  s t  best resort.
 nput
  num_m ll seconds: A scalar tensor correspond ng to t  number
  of m ll seconds t  operat on should sleep for
Output
  sleep_t  _ n_ms: A scalar tensor correspond ng to t  
  actual number of m ll seconds for wh ch t  operat on slept
)doc");

class SleepOp : publ c OpKernel {
 publ c:
    expl c  SleepOp(OpKernelConstruct on* context) : OpKernel(context) {}

    vo d Compute(OpKernelContext* context) overr de {
      // Grab t   nput tensor
      const Tensor&  nput_tensor = context-> nput(0);
      auto  nput =  nput_tensor.flat< nt32>();

      // Sleep for spec f ed m ll seconds
      auto start = std::chrono::h gh_resolut on_clock::now();
      std::t _thread::sleep_for(std::chrono::m ll seconds( nput(0)));
      auto end = std::chrono::h gh_resolut on_clock::now();
      std::chrono::durat on<double, std::m ll > elapsed = end-start;

      // Set t  output tensor
      Tensor* output_tensor = NULL;
      OP_REQU RES_OK(context, context->allocate_output(0, TensorShape({}), &output_tensor));
      auto output_flat = output_tensor->flat< nt32>();
      output_flat(0) = elapsed.count();
    }
};

REG STER_KERNEL_BU LDER(Na ("Sleep").Dev ce(DEV CE_CPU), SleepOp);
