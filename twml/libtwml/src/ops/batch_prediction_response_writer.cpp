# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

REG STER_OP("BatchPred ct onResponseWr er")
.Attr("T: {float, double}")
. nput("keys:  nt64")
. nput("values: T")
.Output("result: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that packages keys and values  nto a BatchPred ct onResponse.

values:  nput feature value. (float/double)
keys: feature  ds from t  or g nal BatchPred ct onRequest. ( nt64)

Outputs
  bytes: output BatchPred ct onRequest ser al zed us ng Thr ft  nto a u nt8 tensor.
)doc");

template<typena  T>
class BatchPred ct onResponseWr er : publ c OpKernel {
 publ c:
  expl c  BatchPred ct onResponseWr er(OpKernelConstruct on* context)
  : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    const Tensor& keys = context-> nput(0);
    const Tensor& values = context-> nput(1);

    try {
      // Ensure t   nner d  ns on matc s.
       f (values.d m_s ze(values.d ms() - 1) != keys.d m_s ze(keys.d ms() - 1)) {
        throw std::runt  _error("T  s zes of keys and values need to match");
      }

      // set  nputs as twml::Tensor
      const twml::Tensor  n_keys_ = TFTensor_to_twml_tensor(keys);
      const twml::Tensor  n_values_ = TFTensor_to_twml_tensor(values);
      // no tensors  n t  op
      const twml::Tensor dum _dense_keys_;
      const std::vector<twml::RawTensor> dum _dense_values_;

      // call constructor BatchPred ct onResponse
      twml::BatchPred ct onResponse tempResult(
         n_keys_,  n_values_, dum _dense_keys_, dum _dense_values_);

      // determ ne t  length of t  result
       nt len = tempResult.encodedS ze();
      TensorShape result_shape = {1, len};

      // Create an output tensor, t  s ze  s determ ned by t  content of  nput.
      Tensor* result = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, result_shape,
                                                       &result));
      twml::Tensor out_result = TFTensor_to_twml_tensor(*result);

      // Call wr er of BatchPred ct onResponse
      tempResult.wr e(out_result);
    } catch(const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

#def ne REG STER(Type)                     \
                                           \
  REG STER_KERNEL_BU LDER(                 \
    Na ("BatchPred ct onResponseWr er")  \
    .Dev ce(DEV CE_CPU)                    \
    .TypeConstra nt<Type>("T"),            \
    BatchPred ct onResponseWr er<Type>);  \

REG STER(float);
REG STER(double);
