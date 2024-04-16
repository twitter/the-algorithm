# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

REG STER_OP("BatchPred ct onTensorResponseWr er")
.Attr("T: l st({str ng,  nt32,  nt64, float, double})")
. nput("keys:  nt64")
. nput("values: T")
.Output("result: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that packages keys and dense tensors  nto a BatchPred ct onResponse.

values: l st of tensors
keys: feature  ds from t  or g nal BatchPred ct onRequest. ( nt64)

Outputs
  bytes: output BatchPred ct onRequest ser al zed us ng Thr ft  nto a u nt8 tensor.
)doc");

class BatchPred ct onTensorResponseWr er : publ c OpKernel {
 publ c:
  expl c  BatchPred ct onTensorResponseWr er(OpKernelConstruct on* context)
  : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    const Tensor& keys = context-> nput(0);

    try {
      // set keys as twml::Tensor
      const twml::Tensor  n_keys_ = TFTensor_to_twml_tensor(keys);

      // c ck s zes
      u nt64_t num_keys =  n_keys_.getNumEle nts();
      u nt64_t num_values = context->num_ nputs() - 1;

      OP_REQU RES(context, num_values % num_keys == 0,
        errors:: nval dArgu nt("Number of dense tensors not mult ple of dense keys"));

      // set dense tensor values
      std::vector<twml::RawTensor>  n_values_;
      for ( nt   = 1;   < context->num_ nputs();  ++) {
         n_values_.push_back(TFTensor_to_twml_raw_tensor(context-> nput( )));
      }

      // no cont nuous pred ct ons  n t  op, only tensors
      const twml::Tensor dum _cont_keys_;
      const twml::Tensor dum _cont_values_;

      // call constructor BatchPred ct onResponse
      twml::BatchPred ct onResponse tempResult(
        dum _cont_keys_, dum _cont_values_,  n_keys_,  n_values_);

      // determ ne t  length of t  result
       nt len = tempResult.encodedS ze();
      TensorShape result_shape = {1, len};

      // Create an output tensor, t  s ze  s determ ned by t  content of  nput.
      Tensor* result = NULL;
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

REG STER_KERNEL_BU LDER(
    Na ("BatchPred ct onTensorResponseWr er").Dev ce(DEV CE_CPU),
    BatchPred ct onTensorResponseWr er);
