# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"
# nclude "res ce_ut ls.h"

# nclude <algor hm>
us ng std::str ng;

REG STER_OP("GetStr ngTensorsFromDataRecord")
.Attr("feature_ d:  nt")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("str ngs: str ng")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns str ng tensors from t  data record.

Attr
  feature_ d: T  has d  d of t  feature na .

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
   ds: A 1D  nt64 tensor represent ng t   nput  ndex  n a g ven batch.
  str ngs: A 1D str ng tensor represent ng t  decoded str ngs from t  batch.
)doc");

REG STER_OP("GetStr ngTensorsFromHas dDataRecord")
.Attr("feature_ d:  nt")
. nput("has d_data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("str ngs: str ng")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns str ng tensors from t  has d data record.

Attr
  feature_ d: T  has d  d of t  feature na .

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
   ds: A 1D  nt64 tensor represent ng t   nput  ndex  n a g ven batch.
  str ngs: A 1D str ng tensor represent ng t  decoded str ngs from t  batch.
)doc");

template<typena  Res ce>
class GetStr ngTensorsOp : publ c OpKernel {
 pr vate:
   nt64 feature_ d;

 publ c:
  expl c  GetStr ngTensorsOp(OpKernelConstruct on *context)
      : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
  }

  vo d Compute(OpKernelContext *context) overr de {
    auto handle = getHandle<Res ce>(context, 0);
    const  nt64 batch_s ze = stat c_cast< nt64>(handle->records.s ze());
    const auto &records = handle->records;

    try {
       nt64 total_s ze = 0;
      for (const auto &record : records) {
        try {
          const auto &tensor = record.getRawTensor(feature_ d);
          total_s ze += stat c_cast< nt64>(tensor.getNumEle nts());
        } catch(const std::out_of_range &err) {
          LOG(WARN NG) << " gnor ng m ss ng str ng tensor w h key: " << feature_ d << std::endl;
          cont nue;
        }
      }

      twml::Thr ftReader reader(nullptr);
      TensorShape shape = {total_s ze};
      Tensor *str ngs_tensor = nullptr;
      Tensor * ds_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds_tensor));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &str ngs_tensor));

      auto str ngs_data = str ngs_tensor->flat<str ng>().data();
      auto  ds_data =  ds_tensor->flat< nt64>().data();

      for ( nt64   = 0;   < batch_s ze;  ++) {
        const auto &record = records[ ];
        try {
          const twml::RawTensor &tensor = record.getRawTensor(feature_ d);
          const u nt8_t *buffer = stat c_cast<const u nt8_t *>(tensor.getData<vo d>());
          const  nt64 num_str ngs = stat c_cast< nt64>(tensor.getNumEle nts());
          reader.setBuffer(buffer);

          for ( nt64 j = 0; j < num_str ngs; j++) {
            const u nt8_t *curr_beg n = nullptr;
            const auto curr_length = reader.getRawBuffer<u nt8_t>(&curr_beg n);
            str ngs_data[j] = std::str ng(curr_beg n, curr_beg n + curr_length);
             ds_data[j] =  ;
          }
           ds_data += num_str ngs;
          str ngs_data += num_str ngs;
        } catch(const std::out_of_range &err) {
          cont nue;
        }
      }
    } catch(const std::except on &err) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(err.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("GetStr ngTensorsFromDataRecord")
  .Dev ce(DEV CE_CPU),
  GetStr ngTensorsOp<DataRecordRes ce>);

REG STER_KERNEL_BU LDER(
  Na ("GetStr ngTensorsFromHas dDataRecord")
  .Dev ce(DEV CE_CPU),
  GetStr ngTensorsOp<Has dDataRecordRes ce>);

REG STER_OP("GetTensorsFromDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_ d:  nt")
. nput("data_record_handle: res ce")
.Output("output: str ng")
.Output("out_shape:  nt64")
.Output("out_type: str ng")
.Output("out_end an: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from t  data record.

Attr
  feature_ d: T  has d  d of t  feature na .

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
  output: A 2D byte tensor represent ng t  requested feature.
  out_shape: A tensor conta n ng [batch_s ze, thr ft_shape].
  out_type: Output type returned as a str ng tensor of s ze 1.
  out_end an: End anness of t  bytes returned a tensor of s ze 1. 0: l te, 1: b g.
)doc");

REG STER_OP("GetTensorsFromHas dDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_ d:  nt")
. nput("has d_data_record_handle: res ce")
.Output("output: str ng")
.Output("out_shape:  nt64")
.Output("out_type: str ng")
.Output("out_end an: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns decodes and tensors from t  has d data record.

Attr
  feature_ d: T  has d  d of t  feature na .

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
  output: A 2D byte tensor represent ng t  requested feature.
  out_shape: A tensor conta n ng [batch_s ze, thr ft_shape].
  out_type: Output type returned as a str ng tensor of s ze 1.
  out_end an: End anness of t  bytes returned a tensor of s ze 1. 0: l te, 1: b g.
)doc");

template<class Res ce>
class GetTensorsOp : publ c OpKernel {
 pr vate:
  bool assert_shape;
   nt64 feature_ d;

 publ c:
  expl c  GetTensorsOp(OpKernelConstruct on *context)
      : OpKernel(context), assert_shape(true) {
    OP_REQU RES_OK(context, context->GetAttr("assert_shape", &assert_shape));
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
  }

  vo d Compute(OpKernelContext *context) overr de {
    auto handle = getHandle<Res ce>(context, 0);
    u nt64 batch_s ze = handle->records.s ze();
    const auto &records = handle->records;

    try {
      TensorShape raw_shape = {stat c_cast< nt64>(batch_s ze)};
      Tensor* output_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, raw_shape, &output_tensor));
      auto output_flat = output_tensor->flat<str ng>();
      auto output_data = output_flat.data();

      twml_type type = TWML_TYPE_UNKNOWN;
      bool  s_b g_end an = false;

      std::vector<u nt64> shape(1, batch_s ze);
      u nt64 length = 0;

      for (auto record : records) {
        const twml::RawTensor tensor = record.getRawTensor(feature_ d);
        const auto &curr_d ms = tensor.getD ms();
        const auto curr_type = tensor.getType();
        const bool curr_ s_b g_end an = tensor. s_b g_end an();
        const u nt64 curr_length = tensor.getRawLength();

        // Create t  output tensor based on f rst tensor
         f (shape.s ze() == 1) {
          // Push t  shape of  nd v dual tensors  nto shape
          shape.reserve(curr_d ms.s ze() + 1);
          shape. nsert(shape.end(), curr_d ms.beg n(), curr_d ms.end());
          type = curr_type;
           s_b g_end an = curr_ s_b g_end an;
          length = curr_length;

        } else {
           f (assert_shape) {
            // Assert shape of all tensors  s t  sa .
            bool  s_sa _shape = std::equal(shape.beg n() + 1, shape.end(), curr_d ms.beg n());

             f (! s_sa _shape || length != curr_length) {
              throw std::runt  _error("TensorShape m smatch for feature_ d: "
                                       + std::to_str ng(feature_ d));
            }
          }

          // Assert type and end anness of all tensors  s t  sa .
           f (type != curr_type ||  s_b g_end an != curr_ s_b g_end an) {
            throw std::runt  _error("Tensor type m smatch for feature_ d: "
                                     + std::to_str ng(feature_ d));
          }
        }

        // Copy from datarecord to output
        const u nt8 *tensor_data = re nterpret_cast<const u nt8 *>(tensor.getData<vo d>());
        *output_data = std::str ng(tensor_data, tensor_data + curr_length);

        //  ncre nt   for t  next tensor  n t  batch.
        output_data++;
      }

      Tensor *shape_tensor = nullptr;
      TensorShape shape_shape = {stat c_cast< nt64>(shape.s ze())};
      OP_REQU RES_OK(context, context->allocate_output(1, shape_shape, &shape_tensor));
      auto shape_flat = shape_tensor->flat< nt64>();
      for ( nt   = 0;   < stat c_cast< nt>(shape.s ze());  ++) {
        shape_flat( ) = shape[ ];
      }

      Tensor* type_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(2, {}, &type_tensor));
      type_tensor->scalar<str ng>()() = twml::getTypeNa (type);

      Tensor* end an_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(3, {}, &end an_tensor));
      end an_tensor->scalar<u nt8>()() =  s_b g_end an;
    } catch(const std::except on &err) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(err.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("GetTensorsFromDataRecord")
  .Dev ce(DEV CE_CPU),
  GetTensorsOp<DataRecordRes ce>);

REG STER_KERNEL_BU LDER(
  Na ("GetTensorsFromHas dDataRecord")
  .Dev ce(DEV CE_CPU),
  GetTensorsOp<Has dDataRecordRes ce>);

REG STER_OP("GetTensorsW hM ss ngMaskFromDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_ d:  nt")
.Attr("default_shape: l st( nt)")
.Attr("dtype_s ze:  nt")
. nput("data_record_handle: res ce")
.Output("output: str ng")
.Output("out_type: str ng")
.Output("out_end an: u nt8")
.Output(" s_found: bool")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from t  data record.

Attr
  assert_shape: Spec f es  f t  shape needs to be sa  across t  batch.
  feature_ d: T  has d  d of t  feature na .
  default_shape: Expected shape of output tensor.
  dtype_s ze: expected s ze of each ele nt.

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
  output: A 2D byte tensor represent ng t  requested feature.
  out_type: A str ng tensor represnt ng t  type.
  out_end an: End anness of t  bytes returned a tensor of s ze 1. 0: l te, 1: b g.
   s_m ss ng: A boolean tensor of length batch_s ze represnt ng  f t  tensor was found for an  nput.
)doc");

REG STER_OP("GetTensorsW hM ss ngMaskFromHas dDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_ d:  nt")
.Attr("default_shape: l st( nt)")
.Attr("dtype_s ze:  nt")
. nput("has d_data_record_handle: res ce")
.Output("output: str ng")
.Output("out_type: str ng")
.Output("out_end an: u nt8")
.Output(" s_found: bool")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from t  data record.

Attr
  assert_shape: Spec f es  f t  shape needs to be sa  across t  batch.
  feature_ d: T  has d  d of t  feature na .
  default_shape: Expected shape of output tensor.
  dtype_s ze: expected s ze of each ele nt.

 nput
  has d_data_record_handle: Res ce handle to Has dDataRecord.

Outputs
  output: A 2D byte tensor represent ng t  requested feature.
  out_type: A str ng tensor represnt ng t  type.
  out_end an: End anness of t  bytes returned a tensor of s ze 1. 0: l te, 1: b g.
   s_m ss ng: A boolean tensor of length batch_s ze represnt ng  f t  tensor was found for an  nput.
)doc");

template<class Res ce>
class GetTensorsW hM ss ngMaskOp : publ c OpKernel {
 pr vate:
  bool assert_shape;
   nt64 feature_ d;
   nt64 dtype_s ze;
  std::vector< nt64> shape;

 publ c:
  expl c  GetTensorsW hM ss ngMaskOp(OpKernelConstruct on *context)
      : OpKernel(context), assert_shape(true) {
    OP_REQU RES_OK(context, context->GetAttr("assert_shape", &assert_shape));
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
    OP_REQU RES_OK(context, context->GetAttr("default_shape", &shape));
    OP_REQU RES_OK(context, context->GetAttr("dtype_s ze", &dtype_s ze));
  }

  vo d Compute(OpKernelContext *context) overr de {
    auto handle = getHandle<Res ce>(context, 0);
    u nt64 batch_s ze = handle->records.s ze();
    const auto &records = handle->records;

    try {
      TensorShape raw_shape = {stat c_cast< nt64>(batch_s ze)};
      Tensor* output_tensor = nullptr;
      Tensor*  s_found_tensor = nullptr;

      OP_REQU RES_OK(context, context->allocate_output(0, raw_shape, &output_tensor));
      OP_REQU RES_OK(context, context->allocate_output(3, raw_shape, & s_found_tensor));

      auto output_flat = output_tensor->flat<str ng>();
      auto output_data = output_flat.data();
      auto  s_found_data =  s_found_tensor->flat<bool>().data();

      twml_type type = TWML_TYPE_UNKNOWN;
      bool  s_b g_end an = false;

      u nt64 length = std::accumulate(shape.beg n(), shape.end(), dtype_s ze, std::mult pl es< nt64>());
      for (auto record : records) {
        try {
          const twml::RawTensor tensor = record.getRawTensor(feature_ d);
          const auto &curr_d ms = tensor.getD ms();
          const auto curr_type = tensor.getType();
          const bool curr_ s_b g_end an = tensor. s_b g_end an();
          const u nt64 curr_length = tensor.getRawLength();

           f (type == TWML_TYPE_UNKNOWN) {
            type = curr_type;
             s_b g_end an = curr_ s_b g_end an;
            // FloatTensors are stored as a l st of doubles.
            //  f t  requested dtype_s ze  s 4, update t  length.
            // NOTE: All t  m ss ng tensors before t  have wrong length, t   s f xed at t  end.
             f (type == TWML_TYPE_DOUBLE &&  s_b g_end an && dtype_s ze == 4) {
              length = length * 2;
            }
          } else {
            // Assert type and end anness of all tensors  s t  sa .
             f (type != curr_type ||  s_b g_end an != curr_ s_b g_end an) {
              throw std::runt  _error("Tensor type m smatch for feature_ d: "
                                       + std::to_str ng(feature_ d));
            }
          }

          // Assert shape of all tensors  s t  sa .
           f (assert_shape && type != TWML_TYPE_UNKNOWN) {
            // Assert shape of all tensors  s t  sa .
            bool  s_sa _shape = std::equal(shape.beg n(), shape.end(), curr_d ms.beg n());

             f (! s_sa _shape || length != curr_length) {
              throw std::runt  _error("TensorShape m smatch for feature_ d: "
                                       + std::to_str ng(feature_ d));
            }
          }

          // Copy from datarecord to output
          const u nt8 *tensor_data = re nterpret_cast<const u nt8 *>(tensor.getData<vo d>());
          *output_data = std::str ng(tensor_data, tensor_data + curr_length);
          * s_found_data = true;
        } catch(const std::out_of_range &err) {
          *output_data = std::str ng();
          output_data->res ze(length);
          * s_found_data = false;
        }

        //  ncre nt   for t  next tensor  n t  batch.
        output_data++;
         s_found_data++;
      }

      // Reset po nters to t  beg nn ng
      output_data = output_flat.data();
       s_found_data =  s_found_tensor->flat<bool>().data();

      // Res ze any m ss ng tensors before type (and  nce true length) was known.
       f (type == TWML_TYPE_DOUBLE) {
        for ( nt64   = 0;   < stat c_cast< nt64>(records.s ze());  ++) {
           f (! s_found_data[ ]) {
            output_data[ ].res ze(length);
          }
        }
      }

      Tensor* type_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(1, {}, &type_tensor));
      type_tensor->scalar<str ng>()() = twml::getTypeNa (type);

      Tensor* end an_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(2, {}, &end an_tensor));
      end an_tensor->scalar<u nt8>()() =  s_b g_end an;
    } catch(const std::except on &err) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(err.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("GetTensorsW hM ss ngMaskFromDataRecord")
  .Dev ce(DEV CE_CPU),
  GetTensorsW hM ss ngMaskOp<DataRecordRes ce>);

REG STER_KERNEL_BU LDER(
  Na ("GetTensorsW hM ss ngMaskFromHas dDataRecord")
  .Dev ce(DEV CE_CPU),
  GetTensorsW hM ss ngMaskOp<Has dDataRecordRes ce>);

REG STER_OP("GetSparseTensorsFromDataRecord")
.Attr("feature_ d:  nt")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output(" nd ces: str ng")
.Output("values: str ng")
.Output("dense_shape:  nt64")
.Output("values_type: str ng")
.Output("valueend an: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from t  data record.

Attr
  feature_ d: T  has d  d of t  feature na .

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
   ds: A 1D tensor represent ng wh ch  nput  n t  batch t  value belongs to.
   nd ces: An str ng tensor conta n ng  nd ces of t  sparse tensor as bytes.
  values: An str ng tensor conta n ng values of t  sparse tensor as bytes.
  dense_shape: A tensor conta n ng [batch_s ze, thr ft_shape].
  values_type: T  data type of value tensor returned as a str ng tensor of s ze 1.
  values_end an: End anness of t  bytes returned a tensor of s ze 1. 0: l te, 1: b g.
)doc");

REG STER_OP("GetSparseTensorsFromHas dDataRecord")
.Attr("feature_ d:  nt")
. nput("has d_data_record_handle: res ce")
.Output(" ds:  nt64")
.Output(" nd ces: str ng")
.Output("values: str ng")
.Output("dense_shape:  nt64")
.Output("values_type: str ng")
.Output("values_end an: u nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from t  data record.

Attr
  feature_ d: T  has d  d of t  feature na .

 nput
  data_record_handle: Res ce handle to DataRecord.

Outputs
   ds: A 1D tensor represent ng wh ch  nput  n t  batch t  value belongs to.
   nd ces: An str ng tensor conta n ng  nd ces of t  sparse tensor as bytes.
  values: An str ng tensor conta n ng values of t  sparse tensor as bytes.
  dense_shape: A tensor conta n ng [batch_s ze, thr ft_shape].
  values_type: T  data type of value tensor returned as a str ng tensor of s ze 1.
  values_end an: End anness of t  bytes returned a tensor of s ze 1. 0: l te, 1: b g.
)doc");

template<typena  Res ce>
class GetSparseTensorsOp : publ c OpKernel {
 pr vate:
   nt64 feature_ d;

 publ c:
  expl c  GetSparseTensorsOp(OpKernelConstruct on *context)
      : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
  }

  vo d Compute(OpKernelContext *context) overr de {
    auto handle = getHandle<Res ce>(context, 0);
    const  nt64 batch_s ze = stat c_cast< nt64>(handle->records.s ze());
    const auto &records = handle->records;

    try {
      twml_type type = TWML_TYPE_UNKNOWN;
      bool  s_b g_end an = false;

      std::vector<u nt64> shape(1, batch_s ze);

       nt64 total_length = 0;
      std::vector< nt64> lengths;
      lengths.reserve(batch_s ze);

       nt64 total_ nd ces_length = 0;
      std::vector< nt64>  nd ces_raw_lengths;
      std::vector<const u nt8 *>  nd ces_data_ptrs;
       nd ces_raw_lengths.reserve(batch_s ze);
       nd ces_data_ptrs.reserve(batch_s ze);

       nt64 total_values_length = 0;
      std::vector< nt64> values_raw_lengths;
      std::vector<const u nt8 *> values_data_ptrs;
      values_raw_lengths.reserve(batch_s ze);
      values_data_ptrs.reserve(batch_s ze);

      for (auto record : records) {
        const twml::RawSparseTensor sparse_tensor = record.getRawSparseTensor(feature_ d);
        const twml::RawTensor  nd ces = sparse_tensor. nd ces();
        const twml::RawTensor values = sparse_tensor.values();
        const auto &dense_shape = sparse_tensor.denseShape();
        const auto  nd ces_type =  nd ces.getType();
        const auto  nd ces_ s_b g_end an =  nd ces. s_b g_end an();
        const auto values_type = values.getType();
        const bool values_ s_b g_end an = values. s_b g_end an();

        const u nt64  nd ces_length =  nd ces.getD ms().back();
        const u nt64 values_length = values.getD ms().back();

        auto  nd ces_raw_length =  nd ces.getRawLength();
        auto values_raw_length = values.getRawLength();

        auto  nd ces_data_ptr = re nterpret_cast<const u nt8 *>( nd ces.getData<vo d>());
        auto values_data_ptr = re nterpret_cast<const u nt8 *>(values.getData<vo d>());

         nd ces_raw_lengths.push_back( nd ces_raw_length);
        values_raw_lengths.push_back(values_raw_length);

         nd ces_data_ptrs.push_back( nd ces_data_ptr);
        values_data_ptrs.push_back(values_data_ptr);

        total_ nd ces_length +=  nd ces_raw_length;
        total_values_length += values_raw_length;

         f (shape.s ze() == 1) {
          shape.reserve(dense_shape.s ze() + 1);
          shape. nsert(shape.end(), dense_shape.beg n(), dense_shape.end());
          type = values_type;
           s_b g_end an = values_ s_b g_end an;
        }

        // Assert shape of all tensors  s t  sa .
         f (!std::equal(shape.beg n() + 1, shape.end(), dense_shape.beg n())) {
          throw std::runt  _error("dense_shape of sparse tensors doesn't match for feature_ d: "
                                   + std::to_str ng(feature_ d));
        }
        // Assert type of all values tensor  s t  sa .
         f (type != values_type ||  s_b g_end an != values_ s_b g_end an) {
          throw std::runt  _error("T  type of values do not match for feature_ d: "
                                   + std::to_str ng(feature_ d));
        }
        // Assert  nd ces tensor  s b g end an and of type  NT64.
         f ( nd ces_type != TWML_TYPE_ NT64 || ! nd ces_ s_b g_end an) {
          throw std::runt  _error("Unexpected type for  ndex tensor for feature_ d: "
                                   + std::to_str ng(feature_ d));
        }

         f ( nd ces_length != values_length) {
          throw std::runt  _error("T  length of values and  nd ces does not match for : "
                                   + std::to_str ng(feature_ d));
        }

        lengths.push_back( nd ces_length);
        total_length +=  nd ces_length;
      }

      Tensor*  ds_tensor = nullptr;
      TensorShape  ds_shape = {stat c_cast< nt64>(total_length)};
      OP_REQU RES_OK(context, context->allocate_output(0,  ds_shape, & ds_tensor));
      auto  ds_tensor_flat =  ds_tensor->flat< nt64>();
      auto  ds_tensor_data =  ds_tensor_flat.data();

      TensorShape raw_shape = {stat c_cast< nt64>(1)};

      Tensor*  nd ces_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(1, raw_shape, & nd ces_tensor));
      auto  nd ces_tensor_flat =  nd ces_tensor->flat<str ng>();
      auto  nd ces_tensor_str ng =  nd ces_tensor_flat.data();
       nd ces_tensor_str ng->res ze(total_ nd ces_length);
      auto  nd ces_tensor_ er =  nd ces_tensor_str ng->beg n();

      Tensor* values_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(2, raw_shape, &values_tensor));
      auto values_tensor_flat = values_tensor->flat<str ng>();
      auto values_tensor_str ng = values_tensor_flat.data();
      values_tensor_str ng->res ze(total_values_length);
      auto values_tensor_ er = values_tensor_str ng->beg n();

      for ( nt64   = 0;   < batch_s ze;  ++) {
        // F ll  n t  data for  d ==   for all values  n t  current  nput.
        std::f ll( ds_tensor_data,  ds_tensor_data + lengths[ ],  );
         ds_tensor_data += lengths[ ];

         nd ces_tensor_ er = std::copy( nd ces_data_ptrs[ ],
                                         nd ces_data_ptrs[ ] +  nd ces_raw_lengths[ ],
                                         nd ces_tensor_ er);

        values_tensor_ er = std::copy(values_data_ptrs[ ],
                                        values_data_ptrs[ ] + values_raw_lengths[ ],
                                        values_tensor_ er);
      }

      Tensor *shape_tensor = nullptr;
      TensorShape shape_shape = {stat c_cast< nt64>(shape.s ze())};
      OP_REQU RES_OK(context, context->allocate_output(3, shape_shape, &shape_tensor));
      auto shape_flat = shape_tensor->flat< nt64>();
      for ( nt   = 0;   < stat c_cast< nt>(shape.s ze());  ++) {
        shape_flat( ) = shape[ ];
      }

      Tensor* type_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(4, {}, &type_tensor));
      type_tensor->scalar<str ng>()() = twml::getTypeNa (type);

      Tensor* end an_tensor = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(5, {}, &end an_tensor));
      end an_tensor->scalar<u nt8>()() =  s_b g_end an;
    } catch(const std::except on &err) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(err.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("GetSparseTensorsFromDataRecord")
  .Dev ce(DEV CE_CPU),
  GetSparseTensorsOp<DataRecordRes ce>);

REG STER_KERNEL_BU LDER(
  Na ("GetSparseTensorsFromHas dDataRecord")
  .Dev ce(DEV CE_CPU),
  GetSparseTensorsOp<Has dDataRecordRes ce>);
