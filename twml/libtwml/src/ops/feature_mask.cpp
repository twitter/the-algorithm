# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"
# nclude <map>
# nclude <vector>
# nclude <set>

REG STER_OP("FeatureMask")
.Attr("T: { nt64,  nt8}")
. nput("keep: T")
.Attr("l st_keep: l st( nt)")
.Output("mask: bool")

.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that creates a mask of t   nd ces that should be kept.

Attr bute
l st_keep: l st of values wh ch should be kept(l st( nt))

 nput
  keep: Tensor for wh ch   w ll apply t  mask ( nt64,  nt8)

Outputs
  mask: boolean Tensor. (bool)

)doc");
template <typena  T>
class FeatureMask : publ c OpKernel {
 pr vate:
  std::set< nt64> feature_set_keep;

 publ c:
  expl c  FeatureMask(OpKernelConstruct on* context)
      : OpKernel(context) {
        std::vector< nt64> feature_l st_keep;
        OP_REQU RES_OK(context, context->GetAttr("l st_keep", &feature_l st_keep));
        // create set that conta ns t  content of t  feature_l st_keep, s nce tensorflow does not allow
        //   to d rectly ouput t  contents of l st_keep to a set
        feature_set_keep = std::set< nt64>(feature_l st_keep.beg n(), feature_l st_keep.end());
      }

  vo d Compute(OpKernelContext* context) overr de {
    // Get s ze of t   nput_vector and create TensorShape shape
    const Tensor&  nput = context-> nput(0);

    auto keep =  nput.flat<T>();

    // Create an output tensor
    Tensor* output_mask = nullptr;

    // Output shape  s determ ned and now   can copy t  contents of t  vector to t  output Tensor.
    const  nt total_s ze_out = stat c_cast< nt>(keep.s ze());

    TensorShape shape_out = {total_s ze_out};

    OP_REQU RES_OK(context, context->allocate_output(0, shape_out, &output_mask));

    auto output_mask_ = output_mask->flat<bool>();

    // C ck  f value  s  n set, output  s boolean
    for ( nt j = 0; j < keep.s ze(); j++){
      output_mask_(j) = (feature_set_keep.count(keep(j)));
    }
  }
};


#def ne REG STER(Type)                        \
                                              \
  REG STER_KERNEL_BU LDER(                    \
  Na ("FeatureMask")  \
  .Dev ce(DEV CE_CPU)                         \
  .TypeConstra nt<Type>("T"),                 \
  FeatureMask<Type>);  \

REG STER( nt64);
REG STER( nt8);
