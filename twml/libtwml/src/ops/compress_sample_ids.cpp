# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <algor hm>    // std::f ll_n

us ng na space tensorflow;

REG STER_OP("CompressSample ds")
.Attr("T: { nt32}")
. nput(" nput: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    c->set_output(0, c->Vector(c->kUnknownD m));
    return Status::OK();
  });


template<typena  T>
class CompressSample ds : publ c OpKernel {
 publ c:
  expl c  CompressSample ds(OpKernelConstruct on* context) : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    // Grab t   nput tensor
    const Tensor&  nput_tensor = context-> nput(0);
    auto  nput =  nput_tensor.flat<T>();
    const  nt N =  nput.s ze();

    // C ck for  mproper  nput
    bool error = (N > 0 &&  nput(0) < 0);
    for ( nt   = 1; !error &&   < N;  ++) {
      error =  nput(  - 1) >  nput( );
    }

    OP_REQU RES(
      context, !error,
      errors:: nval dArgu nt(
        "Error  n CompressSample ds. Sample ds must be non-negat ve and non-decreas ng"
      )
    );

    // choose output s ze, e  r last  nput ele nt + 1, or 0
     nt output_s ze = 0;
     f (N > 0) {
      output_s ze =  nput(N - 1) + 1;
    }

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQU RES_OK(
      context,
      context->allocate_output(0, TensorShape({output_s ze}), &output_tensor)
    );
    auto output_flat = output_tensor->flat<T>();

    // Zero- n  al ze output
    for ( nt   = 0;   < output_s ze;  ++) {
      output_flat( ) = 0;
    }

    // count how many of each  nput ele nt
    for ( nt   = 0;   < N;  ++) {
      output_flat( nput( )) ++;
    }
  }
};

REG STER_OP("DecompressSample ds")
.Attr("T: { nt32}")
. nput(" nput: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    c->set_output(0, c->Vector(c->kUnknownD m));
    return Status::OK();
  });


template<typena  T>
class DecompressSample ds : publ c OpKernel {
 publ c:
  expl c  DecompressSample ds(OpKernelConstruct on* context) : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    // Grab t   nput tensor
    const Tensor&  nput_tensor = context-> nput(0);
    auto  nput =  nput_tensor.flat<T>();
    const  nt N =  nput.s ze();

    // C ck for  mproper  nput
    bool error = false;
     nt output_s ze = 0;
    for ( nt   = 0; !error &&   < N;  ++) {
      error =  nput( ) < 0;
      output_s ze +=  nput( );
    }

    OP_REQU RES(
      context, !error,
      errors:: nval dArgu nt(
        "Error  n DecompressSample ds.  nputs must be non-negat ve."
      )
    );

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQU RES_OK(
      context,
      context->allocate_output(0, TensorShape({output_s ze}),&output_tensor)
    );
    auto output_flat = output_tensor->flat<T>();

    T *output_data = output_flat.data();
    for ( nt current_sample = 0; current_sample < N; current_sample++) {
      std::f ll_n(output_data,  nput(current_sample), current_sample);
      output_data +=  nput(current_sample);
    }
  }
};



#def ne REG STER(Type)              \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("CompressSample ds")       \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    CompressSample ds<Type>);       \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("DecompressSample ds")     \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    DecompressSample ds<Type>);     \
                                    \

REG STER( nt32);
