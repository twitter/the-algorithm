# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

us ng na space tensorflow;

REG STER_OP("Add1")
.Attr("T: {float, double,  nt32}")
. nput(" nput1: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    c->set_output(0, c-> nput(0));
    return Status::OK();
  });


template<typena  T>
class Add1 : publ c OpKernel {
 publ c:
  expl c  Add1(OpKernelConstruct on* context) : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    // Grab t   nput tensor
    const Tensor&  nput_tensor = context-> nput(0);
    auto  nput =  nput_tensor.flat<T>();

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0,  nput_tensor.shape(),
                             &output_tensor));
    auto output_flat = output_tensor->flat<T>();

    // Add 1 to  nput and ass gn to output
    const  nt N =  nput.s ze();
    for ( nt   = 0;   < N;  ++) {
      output_flat( ) =  nput( ) + 1;
    }
  }
};


REG STER_OP("Add1Grad")
.Attr("T: {float, double,  nt32}")
. nput("grad_output: T")
.Output("grad_ nput: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    c->set_output(0, c-> nput(0));
    return Status::OK();
  });

template<typena  T>
class Add1Grad : publ c OpKernel {
 publ c:
  expl c  Add1Grad(OpKernelConstruct on* context) : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    // Grab t   nput tensor
    const Tensor& grad_output_tensor = context-> nput(0);
    auto grad_output = grad_output_tensor.flat<T>();

    // Create an grad_ nput tensor
    Tensor* grad_ nput_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0, grad_output_tensor.shape(),
                             &grad_ nput_tensor));

    auto grad_ nput_flat = grad_ nput_tensor->flat<T>();

    // Copy from grad_output to grad_ nput
    const  nt N = grad_output.s ze();
    for ( nt   = 0;   < N;  ++) {
      grad_ nput_flat( ) = grad_output( );
    }
  }
};

#def ne REG STER(Type)              \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("Add1")                    \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    Add1<Type>);                    \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("Add1Grad")                \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    Add1Grad<Type>);                \

REG STER(float);
REG STER(double);
REG STER( nt32);
