# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"

us ng na space tensorflow;

REG STER_OP("DataRecordTensorWr er")
.Attr("T: l st({str ng,  nt32,  nt64, float, double, bool})")
. nput("keys:  nt64")
. nput("values: T")
.Output("result: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  return Status::OK();
  }).Doc(R"doc(

A tensorflow OP that packages keys and dense tensors  nto a DataRecord.

values: l st of tensors
keys: feature  ds from t  or g nal DataRecord ( nt64)

Outputs
  bytes: output DataRecord ser al zed us ng Thr ft  nto a u nt8 tensor.
)doc");

class DataRecordTensorWr er : publ c OpKernel {
 publ c:
  expl c  DataRecordTensorWr er(OpKernelConstruct on* context)
  : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    const Tensor& keys = context-> nput(0);

    try {
      // set keys as twml::Tensor
      const twml::Tensor  n_keys_ = TFTensor_to_twml_tensor(keys);

      // c ck s zes
      u nt64_t num_keys =  n_keys_.getNumEle nts();
      u nt64_t num_values = context->num_ nputs() - 1;

      OP_REQU RES(context, num_keys == num_values,
        errors:: nval dArgu nt("Number of dense keys and dense tensors do not match"));

      // populate DataRecord object
      const  nt64_t *keys =  n_keys_.getData< nt64_t>();
      twml::DataRecord record = twml::DataRecord();

      for ( nt   = 1;   < context->num_ nputs();  ++) {
        const twml::RawTensor& value = TFTensor_to_twml_raw_tensor(context-> nput( ));
        record.addRawTensor(keys[ -1], value);
      }

      // determ ne t  length of t  encoded result (no  mory  s cop ed)
      twml::Thr ftWr er thr ft_dry_wr er = twml::Thr ftWr er(nullptr, 0, true);
      twml::DataRecordWr er record_dry_wr er = twml::DataRecordWr er(thr ft_dry_wr er);
      record_dry_wr er.wr e(record);
       nt len = thr ft_dry_wr er.getBytesWr ten();
      TensorShape result_shape = {1, len};

      // allocate output tensor
      Tensor* result = NULL;
      OP_REQU RES_OK(context, context->allocate_output(0, result_shape, &result));
      twml::Tensor out_result = TFTensor_to_twml_tensor(*result);

      // wr e to output tensor
      u nt8_t *buffer = out_result.getData<u nt8_t>();
      twml::Thr ftWr er thr ft_wr er = twml::Thr ftWr er(buffer, len, false);
      twml::DataRecordWr er record_wr er = twml::DataRecordWr er(thr ft_wr er);
      record_wr er.wr e(record);
    } catch(const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
    Na ("DataRecordTensorWr er").Dev ce(DEV CE_CPU),
    DataRecordTensorWr er);
