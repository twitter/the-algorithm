# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;


vo d ComputeD scret zers(OpKernelContext* context, const bool return_b n_ nd ces = false) {
  const Tensor& keys = context-> nput(0);
  const Tensor& vals = context-> nput(1);
  const Tensor& b n_ ds = context-> nput(2);
  const Tensor& b n_vals = context-> nput(3);
  const Tensor& feature_offsets = context-> nput(4);

  Tensor* new_keys = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(0, keys.shape(),
                                                   &new_keys));
  Tensor* new_vals = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(1, keys.shape(),
                                                   &new_vals));

  try {
    twml::Tensor out_keys_ = TFTensor_to_twml_tensor(*new_keys);
    twml::Tensor out_vals_ = TFTensor_to_twml_tensor(*new_vals);

    const twml::Tensor  n_keys_ = TFTensor_to_twml_tensor(keys);
    const twml::Tensor  n_vals_ = TFTensor_to_twml_tensor(vals);
    const twml::Tensor b n_ ds_ = TFTensor_to_twml_tensor(b n_ ds);
    const twml::Tensor b n_vals_ = TFTensor_to_twml_tensor(b n_vals);
    const twml::Tensor feature_offsets_ = TFTensor_to_twml_tensor(feature_offsets);
    twml::mdl nfer(out_keys_, out_vals_,
                    n_keys_,  n_vals_,
                   b n_ ds_, b n_vals_,
                   feature_offsets_,
                   return_b n_ nd ces);
  }  catch (const std::except on &e) {
    context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
  }
}

REG STER_OP("MDL")
.Attr("T: {float, double}")
. nput("keys:  nt64")
. nput("vals: T")
. nput("b n_ ds:  nt64")
. nput("b n_vals: T")
. nput("feature_offsets:  nt64")
.Output("new_keys:  nt64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    // TODO: c ck s zes
    c->set_output(0, c-> nput(0));
    c->set_output(1, c-> nput(0));
    return Status::OK();
}).Doc(R"doc(

T  operat on d scret zes a tensor conta n ng cont nuous features.

 nput
  keys: A tensor conta n ng feature  ds.
  vals: A tensor conta n ng values at correspond ng feature  ds.
  b n_ ds: A tensor conta n ng t  d scret zed feature  d for a g ven b n.
  b n_vals: A tensor conta n ng t  b n boundar es for value at a g ven feature  d.
  feature_offsets: Spec f es t  start ng locat on of b ns for a g ven feature  d.

Expected S zes:
  keys, vals: [N].
  b n_ ds, b n_vals: [sum_{n=1}^{n=num_classes} num_b ns(n)]

  w re
  - N  s t  number of sparse features  n t  current batch.
  - [0, num_classes) represents t  range each feature  d can take.
  - num_b ns(n)  s t  number of b ns for a g ven feature  d.
  -  f num_b ns  s f xed, t n xs, ys are of s ze [num_classes * num_b ns].

Expected Types:
  keys, b n_ ds:  nt64.
  vals: float or double.
  b n_vals: sa  as vals.

Before us ng MDL,   should use a hashmap to get t   ntersect on of
 nput `keys` w h t  features that MDL knows about:
::
  keys, vals # keys can be  n range [0, 1 << 63)
  mdl_keys = hashmap.f nd(keys) # mdl_keys are now  n range [0, num_classes_from_cal brat on)
  mdl_keys = w re (mdl_keys != -1) #  gnore keys not found


 ns de MDL, t  follow ng  s happen ng:
::
  start = offsets[key[ ]]
  end = offsets[key[ ] + 1]
   dx = b nary_search for val[ ]  n [b n_vals[start], b n_vals[end]]

  result_keys[ ] = b n_ ds[ dx]
  val[ ] = 1 # b nary feature value

Outputs
  new_keys: T  d scret zed feature  ds w h sa  shape and s ze as keys.
  new_vals: T  d scret zed values w h t  sa  shape and s ze as vals.

)doc");


template<typena  T>
class MDL : publ c OpKernel {
 publ c:
  expl c  MDL(OpKernelConstruct on* context) : OpKernel(context) {
  }

  vo d Compute(OpKernelContext* context) overr de {
    ComputeD scret zers(context);
  }
};

REG STER_OP("Percent leD scret zer")
.Attr("T: {float, double}")
. nput("keys:  nt64")
. nput("vals: T")
. nput("b n_ ds:  nt64")
. nput("b n_vals: T")
. nput("feature_offsets:  nt64")
.Output("new_keys:  nt64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    // TODO: c ck s zes
    c->set_output(0, c-> nput(0));
    c->set_output(1, c-> nput(0));
    return Status::OK();
}).Doc(R"doc(

T  operat on d scret zes a tensor conta n ng cont nuous features.

 nput
  keys: A tensor conta n ng feature  ds.
  vals: A tensor conta n ng values at correspond ng feature  ds.
  b n_ ds: A tensor conta n ng t  d scret zed feature  d for a g ven b n.
  b n_vals: A tensor conta n ng t  b n boundar es for value at a g ven feature  d.
  feature_offsets: Spec f es t  start ng locat on of b ns for a g ven feature  d.

Expected S zes:
  keys, vals: [N].
  b n_ ds, b n_vals: [sum_{n=1}^{n=num_classes} num_b ns(n)]

  w re
  - N  s t  number of sparse features  n t  current batch.
  - [0, num_classes) represents t  range each feature  d can take.
  - num_b ns(n)  s t  number of b ns for a g ven feature  d.
  -  f num_b ns  s f xed, t n xs, ys are of s ze [num_classes * num_b ns].

Expected Types:
  keys, b n_ ds:  nt64.
  vals: float or double.
  b n_vals: sa  as vals.

Before us ng Percent leD scret zer,   should use a hashmap to get t   ntersect on of
 nput `keys` w h t  features that Percent leD scret zer knows about:
::
  keys, vals # keys can be  n range [0, 1 << 63)
  percent le_d scret zer_keys = hashmap.f nd(keys) # percent le_d scret zer_keys are now  n range [0, num_classes_from_cal brat on)
  percent le_d scret zer_keys = w re (percent le_d scret zer_keys != -1) #  gnore keys not found


 ns de Percent leD scret zer, t  follow ng  s happen ng:
::
  start = offsets[key[ ]]
  end = offsets[key[ ] + 1]
   dx = b nary_search for val[ ]  n [b n_vals[start], b n_vals[end]]

  result_keys[ ] = b n_ ds[ dx]
  val[ ] = 1 # b nary feature value

Outputs
  new_keys: T  d scret zed feature  ds w h sa  shape and s ze as keys.
  new_vals: T  d scret zed values w h t  sa  shape and s ze as vals.

)doc");

template<typena  T>
class Percent leD scret zer : publ c OpKernel {
 publ c:
  expl c  Percent leD scret zer(OpKernelConstruct on* context) : OpKernel(context) {
  }

  vo d Compute(OpKernelContext* context) overr de {
    ComputeD scret zers(context);
  }
};


REG STER_OP("Percent leD scret zerB n nd ces")
.Attr("T: {float, double}")
. nput("keys:  nt64")
. nput("vals: T")
. nput("b n_ ds:  nt64")
. nput("b n_vals: T")
. nput("feature_offsets:  nt64")
.Output("new_keys:  nt64")
.Output("new_vals: T")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    // TODO: c ck s zes
    c->set_output(0, c-> nput(0));
    c->set_output(1, c-> nput(0));
    return Status::OK();
}).Doc(R"doc(

T  operat on d scret zes a tensor conta n ng cont nuous features.
 f t  feature  d and b n  d of t  d scret zed value  s t  sa  on mult ple runs, t y
w ll always be ass gned to t  sa  output key and value, regardless of t  b n_ d ass gned dur ng
cal brat on.

 nput
  keys: A tensor conta n ng feature  ds.
  vals: A tensor conta n ng values at correspond ng feature  ds.
  b n_ ds: A tensor conta n ng t  d scret zed feature  d for a g ven b n.
  b n_vals: A tensor conta n ng t  b n boundar es for value at a g ven feature  d.
  feature_offsets: Spec f es t  start ng locat on of b ns for a g ven feature  d.

Expected S zes:
  keys, vals: [N].
  b n_ ds, b n_vals: [sum_{n=1}^{n=num_classes} num_b ns(n)]

  w re
  - N  s t  number of sparse features  n t  current batch.
  - [0, num_classes) represents t  range each feature  d can take.
  - num_b ns(n)  s t  number of b ns for a g ven feature  d.
  -  f num_b ns  s f xed, t n xs, ys are of s ze [num_classes * num_b ns].

Expected Types:
  keys, b n_ ds:  nt64.
  vals: float or double.
  b n_vals: sa  as vals.

Before us ng Percent leD scret zerB n nd ces,   should use a hashmap to get t   ntersect on of
 nput `keys` w h t  features that Percent leD scret zerB n nd ces knows about:
::
  keys, vals # keys can be  n range [0, 1 << 63)
  percent le_d scret zer_keys = hashmap.f nd(keys) # percent le_d scret zer_keys are now  n range [0, num_classes_from_cal brat on)
  percent le_d scret zer_keys = w re (percent le_d scret zer_keys != -1) #  gnore keys not found


 ns de Percent leD scret zerB n nd ces, t  follow ng  s happen ng:
::
  start = offsets[key[ ]]
  end = offsets[key[ ] + 1]
   dx = b nary_search for val[ ]  n [b n_vals[start], b n_vals[end]]

  result_keys[ ] = b n_ ds[ dx]
  val[ ] = 1 # b nary feature value

Outputs
  new_keys: T  d scret zed feature  ds w h sa  shape and s ze as keys.
  new_vals: T  d scret zed values w h t  sa  shape and s ze as vals.

)doc");

template<typena  T>
class Percent leD scret zerB n nd ces : publ c OpKernel {
 publ c:
  expl c  Percent leD scret zerB n nd ces(OpKernelConstruct on* context) : OpKernel(context) {
  }

  vo d Compute(OpKernelContext* context) overr de {
    ComputeD scret zers(context, true);
  }
};


#def ne REG STER(Type)              \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("Percent leD scret zerB n nd ces")   \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    Percent leD scret zerB n nd ces<Type>);   \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("Percent leD scret zer")   \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    Percent leD scret zer<Type>);   \
                                    \
  REG STER_KERNEL_BU LDER(          \
    Na ("MDL")                     \
    .Dev ce(DEV CE_CPU)             \
    .TypeConstra nt<Type>("T"),     \
    MDL<Type>);                     \

REG STER(float);
REG STER(double);
