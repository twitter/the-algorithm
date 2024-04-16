# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

REG STER_OP("Feature d")
.Attr("feature_na s: l st(str ng)")
.Output("output:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that has s a l st of str ngs  nto  nt64. T   s used for feature na  hash ng.

Attr
  feature_na s: a l st of str ng feature na s (l st(str ng)).

Outputs
  ouput: has s correspond ng to t  str ng feature na s ( nt64).
)doc");


class Feature d : publ c OpKernel {
 pr vate:
    std::vector<str ng>  nput_vector;

 publ c:
  expl c  Feature d(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_na s", & nput_vector));
  }

  vo d Compute(OpKernelContext* context) overr de {
    // Get s ze of t   nput_vector and create TensorShape shape
    const  nt total_s ze = stat c_cast< nt>( nput_vector.s ze());
    TensorShape shape = {total_s ze};

    // Create an output tensor
    Tensor* output_tensor = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0, shape,
                             &output_tensor));
    auto output_flat = output_tensor->flat< nt64>();

    // Transform t   nput tensor  nto a  nt64
    for ( nt   = 0;   < total_s ze;  ++) {
      output_flat( ) = twml::feature d( nput_vector[ ]);
    }
  }
};


REG STER_KERNEL_BU LDER(
  Na ("Feature d")
  .Dev ce(DEV CE_CPU),
  Feature d);
