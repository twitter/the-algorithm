# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"
# nclude "res ce_ut ls.h"

# nclude <algor hm>
us ng std::str ng;

template<typena   ndexType, typena  ValueType, bool calc_batch_s ze>
vo d ComputeF xedLengthTensor(OpKernelContext *context,  nt64 max_length_) {
  try {
    const Tensor& seg nt_ ds = context-> nput(0);
    const Tensor& values = context-> nput(1);
    const Tensor& pad_value = context-> nput(2);

    auto  nd ces_flat = seg nt_ ds.flat< ndexType>();
    auto values_flat = values.flat<ValueType>();

    auto pad_value_scalar = pad_value.scalar<ValueType>()();

    // Get max mum length from batch  f user hasn't spec f ed  .
     nt64 max_length = max_length_;
     f (max_length < 0 &&  nd ces_flat.s ze() > 0) {
       nt64 current_ d =  nd ces_flat(0);
       nt64 current_length = 1;

      for ( nt64   = 1;   <  nd ces_flat.s ze();  ++) {
         f (current_ d ==  nd ces_flat( )) {
          current_length++;
        } else {
          current_ d =  nd ces_flat( );
          max_length = std::max(max_length, current_length);
          current_length = 1;
        }
      }
      // T   s needed  f t  last batch  s t  longest sequence.
      max_length = std::max(max_length, current_length);
    }

     nt64 batch_s ze = 0;
     f (calc_batch_s ze) {
       f ( nd ces_flat.s ze() > 0) {
        // T  last value of seg nt_ ds w ll have value batch_s ze  1;
        batch_s ze = 1 +  nd ces_flat( nd ces_flat.s ze() - 1);
      } else {
        batch_s ze = 0;
      }
    } else {
      const Tensor& batch_s ze_tensor = context-> nput(3);
      batch_s ze = batch_s ze_tensor.flat< nt64>()(0);
    }

    TensorShape output_shape = {batch_s ze, max_length};
    Tensor* f xed_length = nullptr;
    OP_REQU RES_OK(context, context->allocate_output(0, output_shape, &f xed_length));

    auto f xed_length_flat = f xed_length->flat<ValueType>();

     nt64 n = 0;
     nt64 offset = 0;
    for ( nt64   = 0;   < batch_s ze;  ++) {
      for ( nt64 j = 0; j < max_length; j++) {
         f (n <  nd ces_flat.s ze() &&  nd ces_flat(n) ==  ) {
          // Copy from var able length tensor.
          f xed_length_flat(offset + j) = values_flat(n);
          n++;
        } else {
          // Pad to f xed length.
          f xed_length_flat(offset + j) = pad_value_scalar;
        }
      }
      // Corner case: truncate to max_length  f user spec f ed max_length < current length.
      wh le (n <  nd ces_flat.s ze() &&   ==  nd ces_flat(n)) n++;

      // Update output po nter
      offset += max_length;
    }
  } catch (const std::except on &err) {
    context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(err.what()));
  }
}

REG STER_OP("F xedLengthTensor")
.Attr(" ndexType: { nt64,  nt32}")
.Attr("ValueType: { nt64,  nt32, str ng}")
.Attr("max_length:  nt")
. nput("seg nt_ ds:  ndexType")
. nput("values: ValueType")
. nput("pad_value: ValueType")
.Output("f xed_length: ValueType")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert var able length seg nts  nto f xed length tensor.

Attr
  max_length: T  s ze of t   nner most ( .e. last) d  ns on.

 nput
  seg nt_ ds: 1D  nput tensor conta n ng t  sorted seg nt_ ds.
  values: 1D  nput tensor conta n ng t  values.
  pad_value: T  value used for padd ng t  f xed length tensor.

Outputs
  f xed_length: A f xed length tensor of s ze [batch_s ze, max_length].
)doc");

template<typena   ndexType, typena  ValueType>
class F xedLengthTensor: publ c OpKernel {
 publ c:
  expl c  F xedLengthTensor(OpKernelConstruct on *context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("max_length", &max_length_));
  }

 pr vate:
   nt64 max_length_;

  vo d Compute(OpKernelContext *context) overr de {
    ComputeF xedLengthTensor< ndexType, ValueType, true>(context, max_length_);
  }
};

REG STER_OP("F xedLengthTensorV2")
.Attr(" ndexType: { nt64,  nt32}")
.Attr("ValueType: { nt64,  nt32, str ng}")
.Attr("max_length:  nt")
. nput("seg nt_ ds:  ndexType")
. nput("values: ValueType")
. nput("pad_value: ValueType")
. nput("batch_s ze:  nt64")
.Output("f xed_length: ValueType")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(

A tensorflow OP to convert var able length seg nts  nto f xed length tensor.

Attr
  max_length: T  s ze of t   nner most ( .e. last) d  ns on.

 nput
  seg nt_ ds: 1D  nput tensor conta n ng t  sorted seg nt_ ds.
  values: 1D  nput tensor conta n ng t  values.
  pad_value: T  value used for padd ng t  f xed length tensor.
  batch_s ze: T  batch s ze to use.

Outputs
  f xed_length: A f xed length tensor of s ze [batch_s ze, max_length].
)doc");

template<typena   ndexType, typena  ValueType>
class F xedLengthTensorV2: publ c OpKernel {
 publ c:
  expl c  F xedLengthTensorV2(OpKernelConstruct on *context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("max_length", &max_length_));
  }

 pr vate:
   nt64 max_length_;

  vo d Compute(OpKernelContext *context) overr de {
    ComputeF xedLengthTensor< ndexType, ValueType, false>(context, max_length_);
  }
};

#def ne REG STER_SPARSE_TO_F XED_LENGTH( ndexType, ValueType)   \
  REG STER_KERNEL_BU LDER(                                      \
    Na ("F xedLengthTensor")                                   \
    .Dev ce(DEV CE_CPU)                                         \
    .TypeConstra nt< ndexType>(" ndexType")                     \
    .TypeConstra nt<ValueType>("ValueType"),                    \
    F xedLengthTensor< ndexType, ValueType>);                   \
                                                                \
  REG STER_KERNEL_BU LDER(                                      \
    Na ("F xedLengthTensorV2")                                 \
    .Dev ce(DEV CE_CPU)                                         \
    .TypeConstra nt< ndexType>(" ndexType")                     \
    .TypeConstra nt<ValueType>("ValueType"),                    \
    F xedLengthTensorV2< ndexType, ValueType>);                 \

REG STER_SPARSE_TO_F XED_LENGTH( nt64,  nt64)
REG STER_SPARSE_TO_F XED_LENGTH( nt64,  nt32)
REG STER_SPARSE_TO_F XED_LENGTH( nt64, str ng)
REG STER_SPARSE_TO_F XED_LENGTH( nt32,  nt64)
REG STER_SPARSE_TO_F XED_LENGTH( nt32,  nt32)
REG STER_SPARSE_TO_F XED_LENGTH( nt32, str ng)
