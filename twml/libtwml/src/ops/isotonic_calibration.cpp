# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

REG STER_OP(" soton cCal brat on")
.Attr("T: {float, double}")
. nput(" nput: T")
. nput("xs: T")
. nput("ys: T")
.Output("output: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  // output shape should be t  sa  as  nput shape.
  c->set_output(0, c-> nput(0));
  return Status::OK();
}).Doc(R"doc(

T  operat on cal brates probab l  es by f t ng to a p ece-w se non-decreas ng funct on.

 nput
   nput: A tensor conta n ng uncal brated probab l  es.
  xs: A tensor conta n ng t  boundar es of t  b ns.
  ys: A tensor cont an ng cal brated values for t  correspond ng b ns.

Expected S zes:
   nput: [batch_s ze, num_labels].
  xs, ys: [num_labels, num_b ns].

Expected Types:
   nput: float or double.
  xs, ys: sa  as  nput.

Outputs
  output: A tensor conta n ng cal brated probab l  es w h sa  shape and s ze as  nput.

)doc");

template<typena  T>
class  soton cCal brat on : publ c OpKernel {
 publ c:
  expl c   soton cCal brat on(OpKernelConstruct on* context)
      : OpKernel(context) {}


  vo d Compute(OpKernelContext* context) overr de {
    const Tensor&  nput = context-> nput(0);
    const Tensor& xs = context-> nput(1);
    const Tensor& ys = context-> nput(2);

    Tensor* output = nullptr;
    OP_REQU RES_OK(
      context,
      context->allocate_output(0,  nput.shape(), &output));

    try {
      const twml::Tensor twml_ nput = TFTensor_to_twml_tensor( nput);
      const twml::Tensor twml_xs = TFTensor_to_twml_tensor(xs);
      const twml::Tensor twml_ys = TFTensor_to_twml_tensor(ys);
      twml::Tensor twml_output = TFTensor_to_twml_tensor(*output);

      twml::l near nterpolat on(twml_output, twml_ nput, twml_xs, twml_ys);
    }  catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

#def ne REG STER(Type)                \
                                      \
  REG STER_KERNEL_BU LDER(            \
    Na (" soton cCal brat on")       \
    .Dev ce(DEV CE_CPU)               \
    .TypeConstra nt<Type>("T"),       \
     soton cCal brat on<Type>);       \

REG STER(float);
REG STER(double);
