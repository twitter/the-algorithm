# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/fra work/common_shape_fns.h"

us ng na space tensorflow;

REG STER_OP("Num ntraOpThreads")
. nput("x: float32")
.Output("num_ ntra_op_threads:  nt32")
.SetShapeFn(tensorflow::shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns t  number of threads  n t   ntra_op_parallel sm pool
T   s not part of t  Tensorflow AP  as of t  date of wr  ng t  doc.  nce,
a tensorflow operat on  s t  best resort.
 nput
  x: Dum  placeholder so that constant fold ng  s not done by TF GraphOpt m zer.
  Please refer https://g hub.com/tensorflow/tensorflow/ ssues/22546 for more
  deta ls.
Output
  num_ ntra_op_threads: A scalar tensor correspond ng to t  number of threads  n
  t   ntra_op_parallel sm pool
)doc");

class Num ntraOpThreads : publ c OpKernel {
 publ c:
  expl c  Num ntraOpThreads(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
     nt num_ ntra_op_threads = context->dev ce()->tensorflow_cpu_worker_threads()->num_threads;
    Tensor* output_tensor = NULL;
    OP_REQU RES_OK(context, context->allocate_output(0, TensorShape({}), &output_tensor));
    auto output_flat = output_tensor->flat< nt32>();
    output_flat(0) = num_ ntra_op_threads;
    }
};

REG STER_KERNEL_BU LDER(Na ("Num ntraOpThreads").Dev ce(DEV CE_CPU), Num ntraOpThreads);
