# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"
# nclude "tensorflow/core/ut l/work_sharder.h"
# nclude "tensorflow/core/l b/core/threadpool.h"
# nclude "tensorflow/core/platform/env.h"
# nclude "tensorflow/core/platform/mutex.h"
# nclude "tensorflow/core/platform/logg ng.h"
# nclude < ostream>

# nclude <vector>

us ng na space tensorflow;

REG STER_OP("ParAdd")
  . nput(" nput_a: float")
  . nput(" nput_b: float")
  .Output("a_plus_b: float")
  .SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
      c->set_output(0, c-> nput(0));
      return Status::OK();
  });


class ParAddOp : publ c OpKernel {
 publ c:
  expl c  ParAddOp(OpKernelConstruct on* context) : OpKernel(context) {
  }

  vo d Compute(OpKernelContext* context) overr de {
    // Grab t   nput tensor
    const Tensor&  nput_tensor0 = context-> nput(0);
    auto  nput_flat0 =  nput_tensor0.flat<float>();
    const Tensor&  nput_tensor1 = context-> nput(1);
    auto  nput_flat1 =  nput_tensor1.flat<float>();

    OP_REQU RES(context,  nput_tensor0.shape() ==  nput_tensor1.shape(),
                errors:: nval dArgu nt(" nput tensors must be  dent cal shape."));

    // Create an output tensor
    Tensor* output_tensor = NULL;
    OP_REQU RES_OK(context,
                   context->allocate_output(0,
                                             nput_tensor0.shape(),
                                            &output_tensor));
    auto output_flat = output_tensor->flat<float>();

    // PARALLEL ADD
    const  nt N =  nput_flat0.s ze();

    // retr eve t  thread pool from t  op context
    auto worker_threads = *(context->dev ce()->tensorflow_cpu_worker_threads());

    // Def n  on of t  computat on thread
    auto task = [=, & nput_flat0, & nput_flat1, &output_flat]( nt64 start,  nt64 l m ) {
      for (; start < l m ; ++start) {
        output_flat(start) =  nput_flat0(start) +  nput_flat1(start);
      }
    };

    // t   s a  ur st c. h gh number  s l kely to be sharded  nto smaller p eces
     nt64 cost_per_un  = 1;

    // let Tensorflow spl  up t  work as   sees f 
    Shard(worker_threads.num_threads,
          worker_threads.workers,
          N,
          cost_per_un ,
          task);
  }
};

REG STER_KERNEL_BU LDER(Na ("ParAdd").Dev ce(DEV CE_CPU), ParAddOp);


