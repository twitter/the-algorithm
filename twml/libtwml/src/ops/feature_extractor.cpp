# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"
# nclude <map>
# nclude <vector>

REG STER_OP("FeatureExtractor")
.Attr("T: {float, double} = DT_FLOAT")
. nput("mask_ n: bool")
. nput(" ds_ n:  nt64")
. nput("keys_ n:  nt64")
. nput("values_ n: T")
. nput("codes_ n:  nt64")
. nput("types_ n:  nt8")
.Output(" ds_out:  nt64")
.Output("keys_out:  nt64")
.Output("values_out: T")
.Output("codes_out:  nt64")
.Output("types_out:  nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that extracts t  des red  nd ces of a Tensor based on a mask

 nput
  mask_ n: boolean Tensor that determ nes wh ch are t   nd ces to be kept (bool)
   ds_ n:  nput  nd ces Tensor ( nt64)
  keys_ n:  nput keys Tensor ( nt64)
  values_ n:  nput values Tensor (float/double)
  codes_ n:  nput codes Tensor ( nt64)
  types_ n:  nput types Tensor( nt8)

Outputs
   ds_out: output  nd ces Tensor ( nt64)
  keys_out: output keys Tensor ( nt64)
  values_out: output values Tensor (float/double)
  codes_out: output codes Tensor ( nt64)
  types_out: output types Tensor( nt8)

)doc");
template <typena  T>
class FeatureExtractor : publ c OpKernel {
 publ c:
  expl c  FeatureExtractor(OpKernelConstruct on* context)
      : OpKernel(context) {}

  template <typena  A, typena  U>
  bool allequal(const A &t, const U &u) {
      return t == u;
  }

  template <typena  A, typena  U, typena ... Ot rs>
  bool allequal(const A &t, const U &u, Ot rs const &... args) {
      return (t == u) && allequal(u, args...);
  }

  vo d Compute(OpKernelContext* context) overr de {
    // Get  nput tensors
    const Tensor&  nput_mask = context-> nput(0);
    const Tensor&  nput_ ds = context-> nput(1);
    const Tensor&  nput_keys = context-> nput(2);
    const Tensor&  nput_values = context-> nput(3);
    const Tensor&  nput_codes = context-> nput(4);
    const Tensor&  nput_types = context-> nput(5);

    auto mask =  nput_mask.flat<bool>();
    auto  ds =  nput_ ds.flat< nt64>();
    auto keys =  nput_keys.flat< nt64>();
    auto codes =  nput_codes.flat< nt64>();
    auto values =  nput_values.flat<T>();
    auto types =  nput_types.flat< nt8>();

    // Ver fy that all Tensors have t  sa  s ze.
    OP_REQU RES(context, allequal(mask.s ze(),  ds.s ze(), keys.s ze(), codes.s ze(), values.s ze(), types.s ze()),
                errors:: nval dArgu nt("all  nput vectors must be t  sa  s ze."));

    // Get t  s ze of t  output vectors by count ng t  numbers of trues.
     nt total_s ze = 0;
    for ( nt   = 0;   < mask.s ze();  ++) {
       f (mask( ))
        total_s ze += 1;
    }

    // Shape  s t  number of Trues  n t  mask E gen::Tensor
    TensorShape shape_out = {total_s ze};

    // Create t  output tensors
    Tensor* output_codes = nullptr;
    Tensor* output_ ds = nullptr;
    Tensor* output_values = nullptr;
    Tensor* output_types = nullptr;
    Tensor* output_keys = nullptr;

    OP_REQU RES_OK(context, context->allocate_output(0, shape_out, &output_ ds));
    OP_REQU RES_OK(context, context->allocate_output(1, shape_out, &output_keys));
    OP_REQU RES_OK(context, context->allocate_output(2, shape_out, &output_values));
    OP_REQU RES_OK(context, context->allocate_output(3, shape_out, &output_codes));
    OP_REQU RES_OK(context, context->allocate_output(4, shape_out, &output_types));

    auto output_ ds_ = output_ ds->flat< nt64>();
    auto output_keys_ = output_keys->flat< nt64>();
    auto output_codes_ = output_codes->flat< nt64>();
    auto output_values_ = output_values->flat<T>();
    auto output_types_ = output_types->flat< nt8>();

    //  erate through t  mask and set values to output E gen::Tensors
     nt j = 0;
    for ( nt   = 0;   < mask.s ze();  ++) {
       f (mask( )) {
        output_ ds_(j) =  ds( );
        output_keys_(j) = keys( );
        output_values_(j) = values( );
        output_codes_(j) = codes( );
        output_types_(j) = types( );
        ++j;
      }
    }
  }
};

#def ne REG STER(Type)                        \
                                              \
  REG STER_KERNEL_BU LDER(                    \
  Na ("FeatureExtractor")  \
  .Dev ce(DEV CE_CPU)                         \
  .TypeConstra nt<Type>("T"),                 \
  FeatureExtractor<Type>);  \

REG STER(float);
REG STER(double);
