# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

REG STER_OP("Part  onSparseTensorMod")
.Attr("T: {float, double}")
. nput(" nd ces:  nt64")
. nput("values: T")
.Output("result: output_types")
.Attr("num_part  ons:  nt")
.Attr("output_types: l st({ nt64, float, double})")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  return Status::OK();
}).Doc(R"doc(

A tensorflow OP that part  ons an  nput batch represented as a sparse tensor
( nd ces are [ ds, keys])  nto separate sparse tensors to more opt mally place
sparse computat ons  n d str buted tra n ng.

 nputs
   nd ces:  nd ces from sparse tensor ([ ds, keys] from t  batch).
  values: Batch values from t  or g nal features d ct.

Attr
  num_part  ons: Number of part  ons to generate.
  output_types: A l st of types for t  output tensors l ke
                [tf. nt64, tf.float32, tf. nt64, tf.float32, ...]
                T  length must be 2 * num_part  ons (see Outputs below)

Outputs
  L st of dense tensors conta n ng for each part  on:
    - part  oned  nd ces tensor ([ ds, keys] from part  oned batch)
    - part  oned values tensor
  T  l st lenth  s 2 * num_part  ons. Example:
  [ [ ds_1, keys_1], values_1, [ ds_2, keys_2], values_2, ... ]
)doc");

template<typena  T>
class Part  onSparseTensorMod : publ c OpKernel {
 pr vate:
   nt64 num_part  ons;

 publ c:
  expl c  Part  onSparseTensorMod(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("num_part  ons", &num_part  ons));
    OP_REQU RES(context, num_part  ons > 0,
                errors:: nval dArgu nt("Number of part  ons must be pos  ve"));
  }

  vo d Compute(OpKernelContext* context) overr de {
    // grab  nput tensors
    const Tensor&  nd ces_tensor = context-> nput(0);  // ( ds, keys)
    const Tensor& values_tensor = context-> nput(1);

    // c ck s zes
     nt64 num_keys =  nd ces_tensor.shape().d m_s ze(0);
    OP_REQU RES(context,  nd ces_tensor.d ms() == 2,
                errors:: nval dArgu nt(" nd ces tensor must be 2D [ ds, keys]"));
    OP_REQU RES(context,  nd ces_tensor.shape().d m_s ze(1) == 2,
                errors:: nval dArgu nt(" nd ces tensor must have 2 cols [ ds, keys]"));
    OP_REQU RES(context, values_tensor.shape().d m_s ze(0) == num_keys,
                errors:: nval dArgu nt("Number of values must match number of keys"));

    // grab  nput vectors
    auto  nd ces =  nd ces_tensor.flat< nt64>();
    auto values = values_tensor.flat<T>();

    // count t  number of features that fall  n each part  on
    std::vector< nt64> part  on_counts(num_part  ons);

    for ( nt   = 0;   < num_keys;  ++) {
       nt64 key =  nd ces(2 *   + 1);
       nt64 part  on_ d = key % num_part  ons;
      part  on_counts[part  on_ d]++;
    }

    // allocate outputs for each part  on and keep references
    std::vector< nt64*> output_ nd ces_part  ons;
    std::vector<T*> output_values_part  ons;
    output_ nd ces_part  ons.reserve(num_part  ons);
    output_values_part  ons.reserve(num_part  ons);

    for ( nt   = 0;   < num_part  ons;  ++) {
      Tensor *output_ nd ces = nullptr, *output_values = nullptr;
      TensorShape shape_ nd ces = TensorShape({part  on_counts[ ], 2});
      TensorShape shape_values = TensorShape({part  on_counts[ ]});

      OP_REQU RES_OK(context, context->allocate_output(2 *  , shape_ nd ces, &output_ nd ces));
      OP_REQU RES_OK(context, context->allocate_output(2 *   + 1, shape_values, &output_values));

      output_ nd ces_part  ons.push_back(output_ nd ces->flat< nt64>().data());
      output_values_part  ons.push_back(output_values->flat<T>().data());
    }

    // ass gn a part  on  d to each feature
    // populate tensors for each part  on
    std::vector< nt64> part  on_ nd ces(num_part  ons);

    for ( nt   = 0;   < num_keys;  ++) {
       nt64 key =  nd ces(2 *   + 1);
       nt64 p d = key % num_part  ons;  // part  on  d
       nt64  dx = part  on_ nd ces[p d]++;

      output_ nd ces_part  ons[p d][2 *  dx] =  nd ces(2 *  );
      output_ nd ces_part  ons[p d][2 *  dx + 1] = key / num_part  ons;
      output_values_part  ons[p d][ dx] = values( );
    }
  }
};

#def ne REG STER(Type)                \
                                      \
  REG STER_KERNEL_BU LDER(            \
    Na ("Part  onSparseTensorMod")  \
    .Dev ce(DEV CE_CPU)               \
    .TypeConstra nt<Type>("T"),       \
    Part  onSparseTensorMod<Type>);  \

REG STER(float);
REG STER(double);
