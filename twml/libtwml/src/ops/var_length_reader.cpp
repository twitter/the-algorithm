# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

us ng na space tensorflow;

REG STER_OP("VarLengthReader")
. nput(" nput1:  nt32")
.Output("output:  nt32")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    ::tensorflow::shape_ nference::ShapeHandle  nput;
    // c ck that  nput has only 1 d  ns on.
    TF_RETURN_ F_ERROR(c->W hRank(c-> nput(0), 1, & nput));
    // t re's no  nference on output shape.
    return Status::OK();
  });


class VarLengthReaderOp : publ c OpKernel {
 publ c:
  expl c  VarLengthReaderOp(OpKernelConstruct on* context) : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    // Grab t   nput tensor
    const Tensor&  nput_tensor = context-> nput(0);
    auto  nput =  nput_tensor.flat< nt32>();

    // get t  f rst ele nt  n t   nput tensor, use   as output shape.
     nt32 len =  nput(0);
    TensorShape output_shape = {1, len};

    // Create an output tensor, t  s ze  s determ ned by t  content of  nput.
    Tensor* output_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0, output_shape, &output_tensor));

    auto output_flat = output_tensor->flat< nt32>();

    // F ll output w h ones.
    const  nt N = output_flat.s ze();
    for ( nt   = 0;   < N;  ++) {
      output_flat( ) = 1;
    }
  }
};

REG STER_KERNEL_BU LDER(Na ("VarLengthReader").Dev ce(DEV CE_CPU), VarLengthReaderOp);
