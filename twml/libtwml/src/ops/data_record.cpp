# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude <twml/funct ons.h>
# nclude <twml/ut l  es.h>
# nclude "tensorflow_ut ls.h"
# nclude "res ce_ut ls.h"

# nclude <algor hm>

us ng std::str ng;

REG STER_OP("DecodeDataRecord")
.Attr(" nputType: {u nt8, str ng}")
.Attr("keep_features: l st( nt)")
.Attr("keep_codes: l st( nt)")
.Attr("label_features: l st( nt)")
.Attr("  ght_features: l st( nt) = []")
. nput(" nput_bytes:  nputType")
.Output("data_record_handle: res ce")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that creates a handle for t  datarecord.

Attr
  keep_features: a l st of  nt  ds to keep.
  keep_codes: t  r correspond ng code.
  label_features: l st of feature  ds represent ng t  labels.
    ght_features: l st of feature  ds represent ng t    ghts. Defaults to empty l st.
  shared_na : na  used by t  res ce handle  ns de t  res ce manager.
  conta ner: na  used by t  conta ner of t  res ces.

shared_na  and conta ner are requ red w n  n r  ng from Res ceOpKernel.

 nput
   nput_bytes:  nput tensor conta n ng t  ser al zed batch of Has dDataRecords.

Outputs
  data_record_handle: A res ce handle to t  DataRecord struct.
)doc");

template<typena   nputType>
class DecodeDataRecord : publ c OpKernel {
 publ c:
  expl c  DecodeDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {
    std::vector< nt64> keep_features;
    std::vector< nt64> keep_codes;

    std::vector< nt64> label_features;
    std::vector< nt64>   ght_features;

    OP_REQU RES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQU RES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQU RES_OK(context, context->GetAttr("label_features", &label_features));
    OP_REQU RES_OK(context, context->GetAttr("  ght_features", &  ght_features));

    OP_REQU RES(context, keep_features.s ze() == keep_codes.s ze(),
                errors:: nval dArgu nt("keep keys and values must have sa  s ze."));

# fdef USE_DENSE_HASH
    m_keep_map.set_empty_key(0);
    m_labels_map.set_empty_key(0);
    m_  ghts_map.set_empty_key(0);
#end f  // USE_DENSE_HASH

    for (u nt64_t   = 0;   < keep_features.s ze();  ++) {
      m_keep_map[keep_features[ ]] = keep_codes[ ];
    }

    for (u nt64_t   = 0;   < label_features.s ze();  ++) {
      m_labels_map[label_features[ ]] =  ;
    }

    for (u nt64_t   = 0;   <   ght_features.s ze();  ++) {
      m_  ghts_map[  ght_features[ ]] =  ;
    }
  }

 pr vate:
  twml::Map< nt64_t,  nt64_t> m_keep_map;
  twml::Map< nt64_t,  nt64_t> m_labels_map;
  twml::Map< nt64_t,  nt64_t> m_  ghts_map;

  vo d Compute(OpKernelContext* context) overr de {
    try {
      DataRecordRes ce *res ce = nullptr;
      OP_REQU RES_OK(context, makeRes ceHandle<DataRecordRes ce>(context, 0, &res ce));

      // Store t   nput bytes  n t  res ce so    snt freed before t  res ce.
      // T   s necessary because   are not copy ng t  contents for tensors.
      res ce-> nput = context-> nput(0);
       nt batch_s ze = getBatchS ze< nputType>(res ce-> nput);
       nt num_labels = stat c_cast< nt>(m_labels_map.s ze());
       nt num_  ghts = stat c_cast< nt>(m_  ghts_map.s ze());

      twml::DataRecordReader reader;
      reader.setKeepMap(&m_keep_map);
      reader.setLabelsMap(&m_labels_map);

      // Do not set   ght map  f    s empty. T  w ll take a faster path.
       f (num_  ghts != 0) {
        reader.set  ghtsMap(&m_  ghts_map);
      }

      res ce->records.clear();
      res ce->records.reserve(batch_s ze);
      for ( nt   = 0;   < batch_s ze;  ++) {
        res ce->records.emplace_back(num_labels, num_  ghts);
      }

      for ( nt64  d = 0;  d < batch_s ze;  d++) {
        const u nt8_t * nput_bytes = get nputBytes< nputType>(res ce-> nput,  d);
        reader.setBuffer( nput_bytes);
        // decode t  reader
        res ce->records[ d].decode(reader);
      }
      // T  should be f ne because m_keep_map should never go out of scope.
      res ce->keep_map = &m_keep_map;
      res ce->num_  ghts = num_  ghts;
      res ce->num_labels = num_labels;
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

 nt64_t count_ f_ex sts(const twml::DataRecord::B naryFeatures &set,
                        const twml::Map< nt64_t,  nt64_t> *const keep_map) {
   nt64_t count = 0;
  for (const auto &key : set) {
     f (keep_map->f nd(key) == keep_map->end()) cont nue;
    count++;
  }
  return count;
}

// T  works for cont nuous, d screte, and str ng features
template<typena  V>
 nt64_t count_ f_ex sts(const twml::Map< nt64_t, V> &map,
                        const twml::Map< nt64_t,  nt64_t> *const keep_map) {
   nt64_t count = 0;
  for (const auto &elem : map) {
     f (keep_map->f nd(elem.f rst) == keep_map->end()) cont nue;
    count++;
  }
  return count;
}

 nt64_t count_ f_ex sts(const twml::DataRecord::SparseB naryFeatures &map,
                        const twml::Map< nt64_t,  nt64_t> *const keep_map) {
   nt64_t count = 0;
  for (const auto &elem : map) {
     f (keep_map->f nd(elem.f rst) == keep_map->end()) cont nue;
    count += elem.second.s ze();
  }
  return count;
}

 nt64_t count_ f_ex sts(const twml::DataRecord::SparseCont nuousFeatures &map,
                        const twml::Map< nt64_t,  nt64_t> *const keep_map) {
   nt64_t count = 0;
  for (const auto &elem : map) {
     f (keep_map->f nd(elem.f rst) == keep_map->end()) cont nue;
    count += elem.second.s ze();
  }
  return count;
}

REG STER_OP("GetB naryFeatures")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads b nary features
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  values: always set to 1 (float)
)doc");

class GetB naryFeatures : publ c OpKernel {
 publ c:
  expl c  GetB naryFeatures(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

       nt64 common_b nary_s ze = count_ f_ex sts(common.getB nary(), handle->keep_map);
       nt64 total_b nary_s ze = records.s ze() * common_b nary_s ze;
      for ( nt  d = 0;  d < records.s ze();  d++) {
        total_b nary_s ze += count_ f_ex sts(handle->records[ d].getB nary(), handle->keep_map);
      }
      const  nt total_s ze = stat c_cast< nt>(total_b nary_s ze);

      TensorShape shape = {total_s ze};
      Tensor* keys = nullptr;
      Tensor*  ds = nullptr;
      Tensor* values = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &values));

      u nt64_t offset = 0;
      auto keys_flat = keys->flat< nt64>();
      auto  ds_flat =  ds->flat< nt64>();
      auto values_flat = values->flat<float>();

      for ( nt64  d = 0;  d < records.s ze();  d++) {
        for (const auto &  : common.getB nary()) {
           f (handle->keep_map->f nd( ) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  ;
          offset++;
        }
        for (const auto &  : records[ d].getB nary()) {
           f (handle->keep_map->f nd( ) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  ;
          offset++;
        }
      }
      // All t  values for b nary features are 1.
      std::f ll(values_flat.data(), values_flat.data() + total_s ze, 1);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetCont nuousFeatures")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads cont nuous features
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: Datarecord keys ( nt64)
  values: Datarecord values(float)
)doc");

class GetCont nuousFeatures : publ c OpKernel {
 publ c:
  expl c  GetCont nuousFeatures(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

       nt64 common_cont nuous_s ze = count_ f_ex sts(common.getCont nuous(), handle->keep_map);
       nt64 total_cont nuous_s ze = records.s ze() * common_cont nuous_s ze;
      for ( nt  d = 0;  d < records.s ze();  d++) {
        total_cont nuous_s ze += count_ f_ex sts(handle->records[ d].getCont nuous(),
                                                 handle->keep_map);
      }
      const  nt total_s ze = stat c_cast< nt>(total_cont nuous_s ze);

      TensorShape shape = {total_s ze};
      Tensor* keys = nullptr;
      Tensor* values = nullptr;
      Tensor*  ds = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &values));

      u nt64_t offset = 0;
      auto keys_flat = keys->flat< nt64>();
      auto values_flat = values->flat<float>();
      auto  ds_flat =  ds->flat< nt64>();

      for ( nt64  d = 0;  d < records.s ze();  d++) {
        for (const auto &  : common.getCont nuous()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  .f rst;
          values_flat(offset) =  .second;
          offset++;
        }
        for (const auto &  : records[ d].getCont nuous()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  .f rst;
          values_flat(offset) =  .second;
          offset++;
        }
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetD screteFeatures")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("values:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads d screte features
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  values: DataRecord values( nt64)
)doc");

class GetD screteFeatures : publ c OpKernel {
 publ c:
  expl c  GetD screteFeatures(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

       nt64 common_d screte_s ze = count_ f_ex sts(common.getD screte(), handle->keep_map);
       nt64 total_d screte_s ze = records.s ze() * common_d screte_s ze;
      for ( nt  d = 0;  d < records.s ze();  d++) {
        total_d screte_s ze += count_ f_ex sts(handle->records[ d].getD screte(),
                                               handle->keep_map);
      }
      const  nt total_s ze = stat c_cast< nt>(total_d screte_s ze);

      TensorShape shape = {total_s ze};
      Tensor* keys = nullptr;
      Tensor* values = nullptr;
      Tensor*  ds = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &values));

      u nt64_t offset = 0;
      auto keys_flat = keys->flat< nt64>();
      auto values_flat = values->flat< nt64>();
      auto  ds_flat =  ds->flat< nt64>();

      for ( nt64  d = 0;  d < records.s ze();  d++) {
        for (const auto &  : common.getD screte()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  .f rst;
          values_flat(offset) =  .second;
          offset++;
        }
        for (const auto &  : records[ d].getD screte()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  .f rst;
          values_flat(offset) =  .second;
          offset++;
        }
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetStr ngFeatures")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("na s: str ng")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads str ng features
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  na s: DataRecord values(str ng)
  values: always set to 1 (float)
)doc");

class GetStr ngFeatures : publ c OpKernel {
 publ c:
  expl c  GetStr ngFeatures(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

       nt64 common_str ng_s ze = count_ f_ex sts(common.getStr ng(), handle->keep_map);
       nt64 total_str ng_s ze = records.s ze() * common_str ng_s ze;
      for ( nt  d = 0;  d < records.s ze();  d++) {
        total_str ng_s ze += count_ f_ex sts(handle->records[ d].getStr ng(),
                                             handle->keep_map);
      }
      const  nt total_s ze = stat c_cast< nt>(total_str ng_s ze);

      TensorShape shape = {total_s ze};
      Tensor* keys = nullptr;
      Tensor* na s = nullptr;
      Tensor*  ds = nullptr;
      Tensor*values = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &na s));
      OP_REQU RES_OK(context, context->allocate_output(3, shape, &values));

      u nt64_t offset = 0;
      auto keys_flat = keys->flat< nt64>();
      auto na s_flat = na s->flat<str ng>();
      auto  ds_flat =  ds->flat< nt64>();
      auto values_flat = values->flat<float>();

      std::f ll(values_flat.data(), values_flat.data() + total_s ze, 1);
      for ( nt64  d = 0;  d < records.s ze();  d++) {
        for (const auto &  : common.getStr ng()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  .f rst;
          na s_flat(offset) =  .second;
          offset++;
        }
        for (const auto &  : records[ d].getStr ng()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
           ds_flat(offset) =  d;
          keys_flat(offset) =  .f rst;
          na s_flat(offset) =  .second;
          offset++;
        }
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetSparseB naryFeatures")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("na s: str ng")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads sparse b nary features
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  na s: DataRecord values(str ng)
  values: always set to 1 (float)
)doc");

class GetSparseB naryFeatures : publ c OpKernel {
 publ c:
  expl c  GetSparseB naryFeatures(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

       nt64 common_sparse_b nary_s ze = count_ f_ex sts(common.getSparseB nary(), handle->keep_map);
       nt64 total_sparse_b nary_s ze = records.s ze() * common_sparse_b nary_s ze;
      for ( nt  d = 0;  d < records.s ze();  d++) {
        total_sparse_b nary_s ze += count_ f_ex sts(handle->records[ d].getSparseB nary(),
                                                    handle->keep_map);
      }
      const  nt total_s ze = stat c_cast< nt>(total_sparse_b nary_s ze);

      TensorShape shape = {total_s ze};
      Tensor* keys = nullptr;
      Tensor* na s = nullptr;
      Tensor*  ds = nullptr;
      Tensor* values = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &na s));
      OP_REQU RES_OK(context, context->allocate_output(3, shape, &values));

      u nt64_t offset = 0;
      auto keys_flat = keys->flat< nt64>();
      auto na s_flat = na s->flat<str ng>();
      auto  ds_flat =  ds->flat< nt64>();
      auto values_flat = values->flat<float>();

      // All t  values for sparse b nary features are 1.
      std::f ll(values_flat.data(), values_flat.data() + total_s ze, 1);
      for ( nt64  d = 0;  d < records.s ze();  d++) {
        for (const auto &  : common.getSparseB nary()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
          for (const auto & _ nner :  .second) {
             ds_flat(offset) =  d;
            keys_flat(offset) =  .f rst;
            na s_flat(offset) =  _ nner;
            offset++;
          }
        }
        for (const auto &  : records[ d].getSparseB nary()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
          for (const auto & _ nner :  .second) {
             ds_flat(offset) =  d;
            keys_flat(offset) =  .f rst;
            na s_flat(offset) =  _ nner;
            offset++;
          }
        }
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetSparseCont nuousFeatures")
. nput("data_record_handle: res ce")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("values: float")
.Output("na s: str ng")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads sparse cont nuous features
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  values: DataRecord values(float)
  na s: DataRecord values(str ng)
)doc");

class GetSparseCont nuousFeatures : publ c OpKernel {
 publ c:
  expl c  GetSparseCont nuousFeatures(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

       nt64 common_sparse_cont nuous_s ze = count_ f_ex sts(common.getSparseCont nuous(),
                                                            handle->keep_map);
       nt64 total_sparse_cont nuous_s ze = records.s ze() * common_sparse_cont nuous_s ze;
      for ( nt  d = 0;  d < records.s ze();  d++) {
        total_sparse_cont nuous_s ze += count_ f_ex sts(handle->records[ d].getSparseCont nuous(),
                                                        handle->keep_map);
      }
      const  nt total_s ze = stat c_cast< nt>(total_sparse_cont nuous_s ze);

      TensorShape shape = {total_s ze};
      Tensor* keys = nullptr;
      Tensor* values = nullptr;
      Tensor* na s = nullptr;
      Tensor*  ds = nullptr;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &values));
      OP_REQU RES_OK(context, context->allocate_output(3, shape, &na s));

      u nt64_t offset = 0;
      auto keys_flat = keys->flat< nt64>();
      auto values_flat = values->flat<float>();
      auto na s_flat = na s->flat<str ng>();
      auto  ds_flat =  ds->flat< nt64>();

      for ( nt64  d = 0;  d < records.s ze();  d++) {
        // copy ng t  contents of t  maps of maps
        for (const auto &  : common.getSparseCont nuous()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
          // for each  d;  erate through t  number of maps correspond ng to that  d
          for (const auto & _ nner :  .second) {
             ds_flat(offset) =  d;
            keys_flat(offset) =  .f rst;
            na s_flat(offset) =  _ nner.f rst;
            values_flat(offset) =  _ nner.second;
            offset++;
          }
        }
        // copy ng t  contents of t  maps of maps
        for (const auto &  : records[ d].getSparseCont nuous()) {
           f (handle->keep_map->f nd( .f rst) == handle->keep_map->end()) cont nue;
          // for each  d;  erate through t  number of maps correspond ng to that  d
          for (const auto & _ nner :  .second) {
             ds_flat(offset) =  d;
            keys_flat(offset) =  .f rst;
            na s_flat(offset) =  _ nner.f rst;
            values_flat(offset) =  _ nner.second;
            offset++;
          }
        }
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetBatchS zeFromDataRecord")
. nput("data_record_handle: res ce")
.Output("batch_s ze:  nt64")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns batch s ze from t  data record.
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
  batch_s ze: Number of records  ld  n t  handle.
)doc");

class GetBatchS zeFromDataRecord : publ c OpKernel {
 publ c:
  expl c  GetBatchS zeFromDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      Tensor *output;
      OP_REQU RES_OK(context, context->allocate_output(0, TensorShape({}), &output));
      output->scalar< nt64>()() = handle->records.s ze();
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetLabelsFromDataRecord")
. nput("data_record_handle: res ce")
.Output("labels: float")
.Attr("default_label: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns labels from t  data record.

Attr
  default_label: T  value used w n a label  s absent  n a data record.

 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
  labels: A 2D tensor of s ze [batch_s ze, num_labels] conta n ng t  label values.
)doc");

class GetLabelsFromDataRecord : publ c OpKernel {
 pr vate:
  float default_label;

 publ c:
  expl c  GetLabelsFromDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("default_label", &default_label));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const  nt num_labels = stat c_cast< nt>(handle->num_labels);
      TensorShape shape = {stat c_cast< nt64>(handle->records.s ze()), num_labels};

      Tensor *labels;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, &labels));

      // T  default value of label  s not present  n data record  s std::nanf
      // For cont nuous labels, change that to a default_label or label.
      auto func = [t ](float label) -> float {
        return std:: snan(label) ? default_label : label;
      };

      auto labels_data = labels->flat<float>().data();
      for (const auto &record : records) {
        const auto& rec_labels = record.labels();
        labels_data = std::transform(rec_labels.beg n(), rec_labels.end(), labels_data, func);
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("Get  ghtsFromDataRecord")
. nput("data_record_handle: res ce")
.Output("  ghts: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns   ghts from t  data record.
 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
    ghts: A 2D tensor of s ze [batch_s ze, num_  ghts] conta n ng t    ght values.
)doc");

class Get  ghtsFromDataRecord : publ c OpKernel {
 publ c:
  expl c  Get  ghtsFromDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const  nt num_  ghts = stat c_cast< nt>(handle->num_  ghts);
      TensorShape shape = {stat c_cast< nt64>(handle->records.s ze()), num_  ghts};

      Tensor *  ghts;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, &  ghts));

      auto   ghts_data =   ghts->flat<float>().data();
      for (const auto &record : records) {
        const auto& rec_  ghts = record.  ghts();
          ghts_data = std::copy(rec_  ghts.beg n(), rec_  ghts.end(),   ghts_data);
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

template<typena  ValueType, typena  FeatureType, typena  TensorType>
vo d SetValueGroup(
const FeatureType& type,
const  nt64& feature_ d,
const  nt64&  d,
const ValueType& default_value,
TensorType values_flat) {
  auto   = type.f nd(feature_ d);
  values_flat( d) = (  == type.end()) ? default_value :  ->second;
}

template<typena  ValueType, typena  TensorType>
// overload ng for B naryFeatures; as   needs to set a value of 1
vo d SetValueGroup(
const twml::DataRecord::B naryFeatures& type,
const  nt64& feature_ d,
const  nt64&  d,
const ValueType& default_value,
TensorType values_flat) {
  auto   = type.f nd(feature_ d);
  values_flat( d) = (  == type.end()) ? default_value : 1;
}

//  lper for Group Extract on of Dense Features
template<typena  ValueType, typena  FeatureType>
vo d Compute lperGroupFeaturesAsTensors(
OpKernelContext* context,
const std::vector< nt64>& feature_ ds,
ValueType& default_value,
std::funct on<const FeatureType&(const twml::DataRecord&)> f) {
  auto handle = getHandle<DataRecordRes ce>(context, 0);
  const auto &records = handle->records;
  // Output shape  s 2D; w re t  f rst d  ns on corresponds to t  batch_s ze
  // and t  second corresponds to t  number of features passed to t  TF Op.
  const  nt batch_s ze = stat c_cast< nt64>(handle->records.s ze());
  const  nt num_feature_ ds = stat c_cast< nt>(feature_ ds.s ze());
  TensorShape shape = {batch_s ze, num_feature_ ds};

  // Def ne t  output
  Tensor* values = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(0, shape, &values));
  auto values_flat = values->flat<ValueType>();

  for ( nt64  d = 0;  d < records.s ze();  d++) {
    const auto &type = f(records[ d]);
    const auto  d_offset =  d * feature_ ds.s ze();
    for ( nt64 f d = 0; f d < feature_ ds.s ze(); f d++) {
      auto feature_ d = feature_ ds[f d];
      // T  value  s set to default  f   does not ex st  n t  current DataRecord
      SetValueGroup(type, feature_ d,  d_offset + f d, default_value, values_flat);
    }
  }
}

//  lper for S ngle Extract on of Dense Features
template<typena  ValueType, typena  FeatureType>
vo d Compute lperFeaturesAsTensors(
OpKernelContext* context,
ValueType& default_value,
 nt64 feature_ d,
std::funct on<const FeatureType&(const twml::DataRecord&)> f) {
  auto handle = getHandle<DataRecordRes ce>(context, 0);
  const auto &records = handle->records;
  // Output shape  s 2D; w re t  f rst d  ns on corresponds to t  batch_s ze
  // and t  second corresponds to t  number of features passed to t  TF Op.
  const  nt total_s ze = stat c_cast< nt64>(handle->records.s ze());
  TensorShape shape = {total_s ze};

  // Def ne t  output
  Tensor* values = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(0, shape, &values));
  auto values_flat = values->flat<ValueType>();
  for ( nt64  d = 0;  d < records.s ze();  d++) {
    const auto &type = f(records[ d]);
    SetValueGroup(type, feature_ d,  d, default_value, values_flat);
  }
}

REG STER_OP("GetB naryAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ d:  nt")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ d:  d represent ng t  feature whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  values: A Tensor correspond ng to t  value of t  feature_ d across mult ple DataRecords
)doc");

class GetB naryAsTensor : publ c OpKernel {
 pr vate:
   nt64 feature_ d;
  float default_value;

 publ c:
  expl c  GetB naryAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::B naryFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::B naryFeatures& { return record.getB nary(); };
      Compute lperFeaturesAsTensors(context, default_value, feature_ d, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetCont nuousAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ d:  nt")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ d:  d represent ng t  feature whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  values: A Tensor correspond ng to t  value of t  feature_ d across mult ple DataRecords
)doc");

class GetCont nuousAsTensor : publ c OpKernel {
 pr vate:
   nt64 feature_ d;
  float default_value;

 publ c:
  expl c  GetCont nuousAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::Cont nuousFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::Cont nuousFeatures& { return record.getCont nuous(); };
      Compute lperFeaturesAsTensors(context, default_value, feature_ d, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetD screteAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ d:  nt")
.Attr("default_value:  nt")
.Output("values:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ d:  d represent ng t  feature whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  values: A Tensor correspond ng to t  value of t  feature_ d across mult ple DataRecords
)doc");

class GetD screteAsTensor : publ c OpKernel {
 pr vate:
   nt64 feature_ d;
   nt64 default_value;

 publ c:
  expl c  GetD screteAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::D screteFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::D screteFeatures& { return record.getD screte(); };
      Compute lperFeaturesAsTensors(context, default_value, feature_ d, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetStr ngAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ d:  nt")
.Attr("default_value: str ng")
.Output("na s: str ng")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ d:  d represent ng t  feature whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  na s: A Tensor correspond ng to t  value of t  feature_ d across mult ple DataRecords
)doc");

class GetStr ngAsTensor : publ c OpKernel {
 pr vate:
   nt64 feature_ d;
  str ng default_value;

 publ c:
  expl c  GetStr ngAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::Str ngFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::Str ngFeatures& { return record.getStr ng(); };
      Compute lperFeaturesAsTensors(context, default_value, feature_ d, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};


REG STER_OP("GetB naryGroupAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ ds: l st( nt)")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ ds: L st of  ds represent ng t  features whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  values: A Tensor correspond ng to t  values of t  feature_ ds across mult ple DataRecords
)doc");


class GetB naryGroupAsTensor : publ c OpKernel {
 pr vate:
  float default_value;
  std::vector< nt64> feature_ ds;

 publ c:
  expl c  GetB naryGroupAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ ds", &feature_ ds));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
       std::funct on<const twml::DataRecord::B naryFeatures &(const twml::DataRecord &)> f =
        [](const twml::DataRecord& record) ->const twml::DataRecord::B naryFeatures& { return record.getB nary(); };
       Compute lperGroupFeaturesAsTensors(context, feature_ ds, default_value, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};


REG STER_OP("GetCont nuousGroupAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ ds: l st( nt)")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ ds: L st of  ds represent ng t  features whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  values: A Tensor correspond ng to t  values of t  feature_ ds across mult ple DataRecords
)doc");

class GetCont nuousGroupAsTensor : publ c OpKernel {
 pr vate:
  float default_value;
  std::vector< nt64> feature_ ds;

 publ c:
  expl c  GetCont nuousGroupAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ ds", &feature_ ds));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::Cont nuousFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::Cont nuousFeatures& { return record.getCont nuous(); };
      Compute lperGroupFeaturesAsTensors(context, feature_ ds, default_value, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetD screteGroupAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ ds: l st( nt)")
.Attr("default_value:  nt")
.Output("values:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ ds: L st of  ds represent ng t  features whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  values: A Tensor correspond ng to t  values of t  feature_ ds across mult ple DataRecords
)doc");

class GetD screteGroupAsTensor : publ c OpKernel {
 pr vate:
  std::vector< nt64> feature_ ds;
   nt64 default_value;

 publ c:
  expl c  GetD screteGroupAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ ds", &feature_ ds));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::D screteFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::D screteFeatures& { return record.getD screte(); };
      Compute lperGroupFeaturesAsTensors(context, feature_ ds, default_value, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetStr ngGroupAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ ds: l st( nt)")
.Attr("default_value: str ng")
.Output("na s: str ng")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor w h t  values of a part cular feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ ds: L st of  ds represent ng t  features whose values w ll be extracted.
  default_value: default_value to be  nputted  f t  values are m ss ng from t  current DataRecord.
Outputs
  na s: A Tensor correspond ng to t  values of t  feature_ ds across mult ple DataRecords
)doc");

class GetStr ngGroupAsTensor : publ c OpKernel {
 pr vate:
  std::vector< nt64> feature_ ds;
  str ng default_value;

 publ c:
  expl c  GetStr ngGroupAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ ds", &feature_ ds));
    OP_REQU RES_OK(context, context->GetAttr("default_value", &default_value));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      std::funct on<const twml::DataRecord::Str ngFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::Str ngFeatures& { return record.getStr ng(); };
    Compute lperGroupFeaturesAsTensors(context, feature_ ds, default_value, f);
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetSparseB naryAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ d:  nt")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("na s: str ng")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns tensors correspond ng to t   ds, keys and na s of a part cular
feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ d:  d represent ng t  feature whose values w ll be extracted.
Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  na s: DataRecord values(str ng)
)doc");
class GetSparseB naryAsTensor : publ c OpKernel {
 pr vate:
   nt64 feature_ d;

 publ c:
  expl c  GetSparseB naryAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      //   need two passes to t  data:
      // 1 to compute t  output s ze of t  tensor
      // 2 to copy t  values to t  tensor
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;

      // Creat ng a vector    ncre nt every t   a key  s found
      std::vector<std::str ng> temp_na s;
      std::vector< nt64> temp_ ds;

      for ( nt64  d = 0;  d < records.s ze();  d++) {
        const auto &sparse_b nary = records[ d].getSparseB nary();
        auto   = sparse_b nary.f nd(feature_ d);
        // F nd all  nstances of key  n DataRecord
         f (  != sparse_b nary.end()) {
          //  nsert to temp_na s all t  values  n t  d ct onary value
          temp_na s. nsert(temp_na s.end(),  ->second.beg n(),  ->second.end());
          temp_ ds. nsert(temp_ ds.end(),  ->second.s ze(),  d);
        }
      }

      // T  total_s ze w ll be t  that of t  saved vector
      const  nt total_s ze = stat c_cast< nt64>(temp_na s.s ze());
      TensorShape shape = {total_s ze};
      Tensor*  ds = nullptr;
      Tensor* keys = nullptr;
      Tensor* na s = nullptr;

      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &na s));

      auto keys_flat = keys->flat< nt64>();
      auto na s_flat = na s->flat<str ng>();
      auto  ds_flat =  ds->flat< nt64>();

      // T  feature  d value w ll always be t  sa 
      std::f ll(keys_flat.data(), keys_flat.data() + total_s ze, feature_ d);
      std::copy(temp_na s.beg n(), temp_na s.end(), na s_flat.data());
      std::copy(temp_ ds.beg n(), temp_ ds.end(),  ds_flat.data());
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetSparseCont nuousAsTensor")
. nput("data_record_handle: res ce")
.Attr("feature_ d:  nt")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("na s: str ng")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns tensors correspond ng to t   ds, keys, na s and values of a part cular
feature_ d.
 nput
  data_record_handle: Res ce handle to DataRecord
Attr
  feature_ d:  d represent ng t  feature whose values w ll be extracted.
Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  na s: DataRecord values(str ng)
  values: DataRecord values(float)
)doc");
class GetSparseCont nuousAsTensor : publ c OpKernel {
 pr vate:
   nt64 feature_ d;

 publ c:
  expl c  GetSparseCont nuousAsTensor(OpKernelConstruct on* context) : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("feature_ d", &feature_ d));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      //   need two passes to t  data:
      // 1 to compute t  output s ze of t  tensor
      // 2 to copy t  values to t  tensor
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      const auto &records = handle->records;

      // Creat ng a vector    ncre nt every t   a key  s found
      std::vector<std::str ng> temp_na s;
      std::vector<float> temp_values;
      std::vector< nt64> temp_ ds;

      for ( nt64  d = 0;  d < records.s ze();  d++) {
        const auto &sparse_cont nuous = records[ d].getSparseCont nuous();
        auto   = sparse_cont nuous.f nd(feature_ d);
        // F nd all  nstances of key  n DataRecord
         f (  != sparse_cont nuous.end()) {
          //  nsert to temp_na s all t  values  n t  d ct onary value
          auto value_map =  ->second;
          for (auto& elem : value_map) {
             temp_na s.push_back(elem.f rst);
             temp_values.push_back(elem.second);
             temp_ ds.push_back( d);
          }
        }
      }

      // T  total_s ze w ll be t  that of t  saved vector
      const  nt total_s ze = stat c_cast< nt64>(temp_na s.s ze());
      TensorShape shape = {total_s ze};
      Tensor*  ds = nullptr;
      Tensor* keys = nullptr;
      Tensor* na s = nullptr;
      Tensor* values = nullptr;

      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
      OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQU RES_OK(context, context->allocate_output(2, shape, &na s));
      OP_REQU RES_OK(context, context->allocate_output(3, shape, &values));

      auto keys_flat = keys->flat< nt64>();
      auto na s_flat = na s->flat<str ng>();
      auto  ds_flat =  ds->flat< nt64>();
      auto values_flat = values->flat<float>();

      // T  feature  d value w ll always be t  sa 
      std::f ll(keys_flat.data(), keys_flat.data() + total_s ze, feature_ d);
      std::copy(temp_na s.beg n(), temp_na s.end(), na s_flat.data());
      std::copy(temp_ ds.beg n(), temp_ ds.end(),  ds_flat.data());
      std::copy(temp_values.beg n(), temp_values.end(), values_flat.data());
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

//  lper funct on to add  ds, keys and values to common vector
 nl ne vo d add dsKeysValuesToVectors(
  const  nt64  d,
  const  nt64 key,
  const double value,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   ds.push_back( d);
  keys.push_back(key);
  values.push_back(value);
}

struct KeepFeatures {
  KeepFeatures() : vec(), set() {}
  template<typena  Conta nerType>
  KeepFeatures(const std::vector< nt64> &keep_features,
               const Conta nerType *const conta ner) {
    vec.reserve(keep_features.s ze());
# fdef USE_DENSE_HASH
    set.res ze(keep_features.s ze());
    set.set_empty_key(0);
#else
    set.reserve(keep_features.s ze());
#end f  // USE_DENSE_HASH
    set.max_load_factor(0.5);
    for (const auto &elem : keep_features) {
       f (conta ner->f nd(elem) == conta ner->end()) cont nue;
      vec.push_back(elem);
      set. nsert(elem);
    }
  }
  s ze_t s ze() const {
    return vec.s ze();
  }
  std::vector< nt64> vec;
  twml::Set< nt64> set;
};

//  lper Funct on to F lter and Hash Feature for B nary Features
vo d f lterAndHashFeature(
  const twml::DataRecord::B naryFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
      add dsKeysValuesToVectors(current_ d, * er, 1,  ds, keys, values);
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem) == keep_features.set.end()) cont nue;
      add dsKeysValuesToVectors(current_ d, elem, 1,  ds, keys, values);
    }
  }
}

//  lper Funct on to F lter and Hash Feature for Cont nuous Features
vo d f lterAndHashFeature(
  const twml::DataRecord::Cont nuousFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
      add dsKeysValuesToVectors(current_ d,  er->f rst,  er->second,  ds, keys, values);
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem.f rst) == keep_features.set.end()) cont nue;
      add dsKeysValuesToVectors(current_ d, elem.f rst, elem.second,  ds, keys, values);
    }
  }
}

//  lper Funct on to F lter and Hash Feature for D screte Features
vo d f lterAndHashFeature(
  const twml::DataRecord::D screteFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
       nt64_t key = twml::m xD screte dAndValue( er->f rst,  er->second);
      add dsKeysValuesToVectors(current_ d, key, 1,  ds, keys, values);
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem.f rst) == keep_features.set.end()) cont nue;
       nt64_t key = twml::m xD screte dAndValue(elem.f rst, elem.second);
      add dsKeysValuesToVectors(current_ d, key, 1,  ds, keys, values);
    }
  }
}

//  lper Funct on to F lter and Hash Feature for Str ng Features
vo d f lterAndHashFeature(
  const twml::DataRecord::Str ngFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
       nt64_t key = twml::m xStr ng dAndValue(
         er->f rst,
         er->second.s ze(),
        re nterpret_cast<const u nt8_t*>( er->second.c_str()));
      add dsKeysValuesToVectors(current_ d, key, 1,  ds, keys, values);
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem.f rst) == keep_features.set.end()) cont nue;
       nt64_t key = twml::m xStr ng dAndValue(
        elem.f rst,
        elem.second.s ze(),
        re nterpret_cast<const u nt8_t*>(elem.second.c_str()));
      add dsKeysValuesToVectors(current_ d, key, 1,  ds, keys, values);
    }
  }
}

//  lper Funct on to F lter and Hash Feature for Sparse B nary Features
vo d f lterAndHashFeature(
  const twml::DataRecord::SparseB naryFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
      for (const auto &na  :  er->second) {
         nt64_t key = twml::m xStr ng dAndValue( er->f rst, na .s ze(),
                                                re nterpret_cast<const u nt8_t*>(na .c_str()));
        add dsKeysValuesToVectors(current_ d, key, 1,  ds, keys, values);
      }
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem.f rst) == keep_features.set.end()) cont nue;
      for (const auto &na  : elem.second) {
         nt64_t key = twml::m xStr ng dAndValue(elem.f rst, na .s ze(),
                                                re nterpret_cast<const u nt8_t*>(na .c_str()));
        add dsKeysValuesToVectors(current_ d, key, 1,  ds, keys, values);
      }
    }
  }
}

//  lper Funct on to F lter and Hash Feature for Sparse Cont nuous Features
vo d f lterAndHashFeature(
  const twml::DataRecord::SparseCont nuousFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
      for (const auto &map :  er->second) {
         nt64_t key = twml::m xStr ng dAndValue(
           er->f rst,
          map.f rst.s ze(),
          re nterpret_cast<const u nt8_t*>(map.f rst.c_str()));
        add dsKeysValuesToVectors(current_ d, key, map.second,  ds, keys, values);
      }
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem.f rst) == keep_features.set.end()) cont nue;
      for (const auto &map : elem.second) {
         nt64_t key = twml::m xStr ng dAndValue(
          elem.f rst,
          map.f rst.s ze(),
          re nterpret_cast<const u nt8_t*>(map.f rst.c_str()));
        add dsKeysValuesToVectors(current_ d, key, map.second,  ds, keys, values);
      }
    }
  }
}

//  lper Funct on to F lter and Hash Feature for Sparse Cont nuous Features
vo d f lterAndHashFeatureCompat(
  const twml::DataRecord::SparseCont nuousFeatures& features,
  const  nt64 current_ d,
  const KeepFeatures &keep_features,
  std::vector< nt64>&  ds,
  std::vector< nt64>& keys,
  std::vector<float>& values) {
   f (keep_features.s ze() < 2 * features.s ze()) {
    for (const auto &f : keep_features.vec) {
      const auto & er = features.f nd(f);
       f ( er == features.end()) cont nue;
      for (const auto &map :  er->second) {
         nt64_t key = twml::feature d(map.f rst);
        add dsKeysValuesToVectors(current_ d, key, map.second,  ds, keys, values);
      }
    }
  } else {
    for (const auto &elem : features) {
       f (keep_features.set.f nd(elem.f rst) == keep_features.set.end()) cont nue;
      for (const auto &map : elem.second) {
         nt64_t key = twml::feature d(map.f rst);
        add dsKeysValuesToVectors(current_ d, key, map.second,  ds, keys, values);
      }
    }
  }
}

vo d copy_ f_ex sts(std::vector< nt64>& out,
                    const std::vector< nt64>&  n,
                    const twml::Map< nt64_t,  nt64_t> *const map) {
  out.reserve( n.s ze());
  for (const auto &elem :  n) {
     f (map->f nd(elem) == map->end()) cont nue;
    out.push_back(elem);
  }
}

vo d ComputeHas dFeaturesAsTensor(OpKernelContext* context,
                                   const DataRecordRes ce *const handle,
                                   const KeepFeatures &b nary_keep_features,
                                   const KeepFeatures &cont nuous_keep_features,
                                   const KeepFeatures &d screte_keep_features,
                                   const KeepFeatures &str ng_keep_features,
                                   const KeepFeatures &sparse_b nary_keep_features,
                                   const KeepFeatures &sparse_cont nuous_keep_features,
                                   bool sparse_cont nuous_compat b l y) {

  const auto &records = handle->records;
  u nt64_t est mated_s ze = (b nary_keep_features.s ze() + cont nuous_keep_features.s ze() +
                             d screte_keep_features.s ze() + str ng_keep_features.s ze() +
                             sparse_b nary_keep_features.s ze() +
                             sparse_cont nuous_keep_features.s ze());
  // Construct temporary vectors for common features
  std::vector< nt64> common_ ds, common_keys, temp_ ds, temp_keys;
  std::vector<float> common_values, temp_values;
  common_ ds.reserve(est mated_s ze);
  common_keys.reserve(est mated_s ze);
  common_values.reserve(est mated_s ze);

  const auto &common_b nary = handle->common.getB nary();
  const auto &common_cont nuous = handle->common.getCont nuous();
  const auto &common_d screte = handle->common.getD screte();
  const auto &common_str ng = handle->common.getStr ng();
  const auto &common_sparse_b nary = handle->common.getSparseB nary();
  const auto &common_sparse_cont nuous = handle->common.getSparseCont nuous();

  f lterAndHashFeature(common_b nary, 0, b nary_keep_features,
                       common_ ds, common_keys, common_values);
  f lterAndHashFeature(common_cont nuous, 0, cont nuous_keep_features,
                       common_ ds, common_keys, common_values);
  f lterAndHashFeature(common_d screte, 0, d screte_keep_features,
                       common_ ds, common_keys, common_values);
  f lterAndHashFeature(common_str ng, 0, str ng_keep_features,
                       common_ ds, common_keys, common_values);
  f lterAndHashFeature(common_sparse_b nary, 0, sparse_b nary_keep_features,
                       common_ ds, common_keys, common_values);
   f (sparse_cont nuous_compat b l y) {
    f lterAndHashFeatureCompat(common_sparse_cont nuous, 0, sparse_cont nuous_keep_features,
                               common_ ds, common_keys, common_values);
  } else {
    f lterAndHashFeature(common_sparse_cont nuous, 0, sparse_cont nuous_keep_features,
                         common_ ds, common_keys, common_values);
  }
  common_ ds.clear();
  // Construct temporary vectors for all features
  est mated_s ze = (est mated_s ze + common_keys.s ze()) * records.s ze();
  temp_ ds.reserve(est mated_s ze);
  temp_keys.reserve(est mated_s ze);
  temp_values.reserve(est mated_s ze);

  for ( nt64  d = 0;  d < records.s ze();  d++) {
    temp_ ds. nsert(temp_ ds.end(), common_keys.s ze(),  d);
    temp_keys. nsert(temp_keys.end(), common_keys.beg n(), common_keys.end());
    temp_values. nsert(temp_values.end(), common_values.beg n(), common_values.end());
    const auto &b nary = records[ d].getB nary();
    const auto &cont nuous = records[ d].getCont nuous();
    const auto &d screte = records[ d].getD screte();
    const auto &str = records[ d].getStr ng();
    const auto &sparse_b nary = records[ d].getSparseB nary();
    const auto &sparse_cont nuous = records[ d].getSparseCont nuous();

    f lterAndHashFeature(b nary,  d, b nary_keep_features,
                         temp_ ds, temp_keys, temp_values);
    f lterAndHashFeature(cont nuous,  d, cont nuous_keep_features,
                         temp_ ds, temp_keys, temp_values);
    f lterAndHashFeature(d screte,  d, d screte_keep_features,
                         temp_ ds, temp_keys, temp_values);
    f lterAndHashFeature(str,  d, str ng_keep_features,
                         temp_ ds, temp_keys, temp_values);
    f lterAndHashFeature(sparse_b nary,  d, sparse_b nary_keep_features,
                         temp_ ds, temp_keys, temp_values);
     f (sparse_cont nuous_compat b l y) {
      f lterAndHashFeatureCompat(sparse_cont nuous,  d, sparse_cont nuous_keep_features,
                                 temp_ ds, temp_keys, temp_values);
    } else {
      f lterAndHashFeature(sparse_cont nuous,  d, sparse_cont nuous_keep_features,
                           temp_ ds, temp_keys, temp_values);
    }
  }

  // Copy t  temporary vectors  nto t  output Tensors
  TensorShape shape = {stat c_cast< nt64>(temp_ ds.s ze())};
  Tensor*  ds = nullptr;
  Tensor* keys = nullptr;
  Tensor* values = nullptr;
  OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));
  OP_REQU RES_OK(context, context->allocate_output(1, shape, &keys));
  OP_REQU RES_OK(context, context->allocate_output(2, shape, &values));
  auto  ds_flat =  ds->flat< nt64>();
  auto keys_flat = keys->flat< nt64>();
  auto values_flat = values->flat<float>();
  std::copy(temp_ ds.beg n(), temp_ ds.end(),  ds_flat.data());
  std::copy(temp_keys.beg n(), temp_keys.end(), keys_flat.data());
  std::copy(temp_values.beg n(), temp_values.end(), values_flat.data());
}

REG STER_OP("GetHas dFeaturesAsSparseTensor")
. nput("data_record_handle: res ce")
.Attr("b nary_keep_features: l st( nt)")
.Attr("cont nuous_keep_features: l st( nt)")
.Attr("d screte_keep_features: l st( nt)")
.Attr("str ng_keep_features: l st( nt)")
.Attr("sparse_b nary_keep_features: l st( nt)")
.Attr("sparse_cont nuous_keep_features: l st( nt)")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  return Status::OK();
}).Doc(R"doc(
A tensorflow OP for return ng requ red features of d fferent type as
a s ngle sparse tensor. Hash ng tr ck  s appl ed.

 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  values: DataRecord values (float)
)doc");

class GetHas dFeaturesAsSparseTensor: publ c OpKernel {
 publ c:
  expl c  GetHas dFeaturesAsSparseTensor(OpKernelConstruct on* context): OpKernel(context) {
    // Get t  l st of features to keep for each feature type
    OP_REQU RES_OK(context, context->GetAttr("b nary_keep_features", &b nary_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("cont nuous_keep_features", &cont nuous_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("d screte_keep_features", &d screte_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("str ng_keep_features", &str ng_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("sparse_b nary_keep_features", &sparse_b nary_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("sparse_cont nuous_keep_features", &sparse_cont nuous_keep_features_));
  }

 pr vate:
  std::vector< nt64> b nary_keep_features_, cont nuous_keep_features_, d screte_keep_features_;
  std::vector< nt64> str ng_keep_features_, sparse_b nary_keep_features_, sparse_cont nuous_keep_features_;

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      // Create a new l st of keep features based on t  or g nal keep_set.
      // T   s to ensure compat b l y w h ex st ng behav or such as:
      //  - Ensure no new features are decoded  n t  op.
      //  - Ensure labels or   ghts dont get  ncluded  re.
      // TODO: Should   return features requested by user  re even  f t y are labels /   ghts?
      KeepFeatures b nary_keep_features(b nary_keep_features_, handle->keep_map);
      KeepFeatures cont nuous_keep_features(cont nuous_keep_features_, handle->keep_map);
      KeepFeatures d screte_keep_features(d screte_keep_features_, handle->keep_map);
      KeepFeatures str ng_keep_features(str ng_keep_features_, handle->keep_map);
      KeepFeatures sparse_b nary_keep_features(sparse_b nary_keep_features_, handle->keep_map);
      KeepFeatures sparse_cont nuous_keep_features(sparse_cont nuous_keep_features_, handle->keep_map);
      ComputeHas dFeaturesAsTensor(context, handle.get(),
                                    b nary_keep_features,
                                    cont nuous_keep_features,
                                    d screte_keep_features,
                                    str ng_keep_features,
                                    sparse_b nary_keep_features,
                                    sparse_cont nuous_keep_features,
                                    false);
    } catch(const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetHas dFeaturesAsSparseTensorV2")
. nput("data_record_handle: res ce")
.Attr("b nary_keep_features: l st( nt)")
.Attr("cont nuous_keep_features: l st( nt)")
.Attr("d screte_keep_features: l st( nt)")
.Attr("str ng_keep_features: l st( nt)")
.Attr("sparse_b nary_keep_features: l st( nt)")
.Attr("sparse_cont nuous_keep_features: l st( nt)")
.Attr("keep_features: l st( nt)")
.Attr("keep_codes: l st( nt)")
.Attr("decode_mode:  nt = 0")
.Output(" ds:  nt64")
.Output("keys:  nt64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
  return Status::OK();
}).Doc(R"doc(
A tensorflow OP for return ng requ red features of d fferent type as
a s ngle sparse tensor. Hash ng tr ck  s appl ed.

 nput
  data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records  n t  batch ( nt64)
  keys: DataRecord keys ( nt64)
  values: DataRecord values (float)
)doc");

class GetHas dFeaturesAsSparseTensorV2: publ c OpKernel {
 publ c:
  expl c  GetHas dFeaturesAsSparseTensorV2(OpKernelConstruct on* context): OpKernel(context) {
    std::vector< nt64> keep_features;
    std::vector< nt64> keep_codes;
    std::vector< nt64> b nary_keep_features_, cont nuous_keep_features_, d screte_keep_features_;
    std::vector< nt64> str ng_keep_features_, sparse_b nary_keep_features_, sparse_cont nuous_keep_features_;

    // Get t  l st of features to keep for each feature type
    OP_REQU RES_OK(context, context->GetAttr("b nary_keep_features", &b nary_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("cont nuous_keep_features", &cont nuous_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("d screte_keep_features", &d screte_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("str ng_keep_features", &str ng_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("sparse_b nary_keep_features", &sparse_b nary_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("sparse_cont nuous_keep_features", &sparse_cont nuous_keep_features_));
    OP_REQU RES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQU RES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQU RES_OK(context, context->GetAttr("decode_mode", &m_decode_mode));

    twml::Map< nt64_t,  nt64_t> keep_map;
# fdef USE_DENSE_HASH
    keep_map.set_empty_key(0);
#end f  // USE_DENSE_HASH
    for (u nt64_t   = 0;   < keep_features.s ze();  ++) {
      keep_map[keep_features[ ]] = keep_codes[ ];
    }


    b nary_keep_features = KeepFeatures(b nary_keep_features_, &keep_map);
    cont nuous_keep_features = KeepFeatures(cont nuous_keep_features_, &keep_map);
    d screte_keep_features = KeepFeatures(d screte_keep_features_, &keep_map);
    str ng_keep_features = KeepFeatures(str ng_keep_features_, &keep_map);
    sparse_b nary_keep_features = KeepFeatures(sparse_b nary_keep_features_, &keep_map);
    sparse_cont nuous_keep_features = KeepFeatures(sparse_cont nuous_keep_features_, &keep_map);

  }

 pr vate:
  KeepFeatures b nary_keep_features, cont nuous_keep_features, d screte_keep_features;
  KeepFeatures str ng_keep_features, sparse_b nary_keep_features, sparse_cont nuous_keep_features;
   nt64 m_decode_mode;

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<DataRecordRes ce>(context, 0);
      // Create a new l st of keep features based on t  or g nal keep_set.
      // T   s to ensure compat b l y w h ex st ng behav or such as:
      //  - Ensure no new features are decoded  n t  op.
      //  - Ensure labels or   ghts dont get  ncluded  re.
      // TODO: Should   return features requested by user  re even  f t y are labels /   ghts?
      ComputeHas dFeaturesAsTensor(context, handle.get(),
                                    b nary_keep_features,
                                    cont nuous_keep_features,
                                    d screte_keep_features,
                                    str ng_keep_features,
                                    sparse_b nary_keep_features,
                                    sparse_cont nuous_keep_features,
                                    m_decode_mode == 0);
    } catch(const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};


#def ne REG STER_DECODE_DATA_RECORD( nputType)  \
  REG STER_KERNEL_BU LDER(                      \
    Na ("DecodeDataRecord")                    \
    .Dev ce(DEV CE_CPU)                         \
    .TypeConstra nt< nputType>(" nputType"),    \
    DecodeDataRecord< nputType>);               \

REG STER_DECODE_DATA_RECORD(u nt8)
REG STER_DECODE_DATA_RECORD(str ng)

#def ne REG STER_GETTER(F ELD)                  \
  REG STER_KERNEL_BU LDER(                      \
    Na ("Get" #F ELD "Features")               \
    .Dev ce(DEV CE_CPU),                        \
    Get##F ELD##Features);                      \

#def ne REG STER_GETTER_FROM_DR(F ELD)          \
  REG STER_KERNEL_BU LDER(                      \
    Na ("Get" #F ELD "FromDataRecord")         \
    .Dev ce(DEV CE_CPU),                        \
    Get##F ELD##FromDataRecord);                \

#def ne REG STER_GETTER_AS_TENSOR(F ELD)        \
  REG STER_KERNEL_BU LDER(                      \
    Na ("Get" #F ELD "AsTensor")               \
    .Dev ce(DEV CE_CPU),                        \
    Get##F ELD##AsTensor);                      \


#def ne REG STER_GETTER_GROUP_AS_TENSOR(F ELD)  \
  REG STER_KERNEL_BU LDER(                      \
    Na ("Get" #F ELD "GroupAsTensor")          \
    .Dev ce(DEV CE_CPU),                        \
    Get##F ELD##GroupAsTensor);                 \

REG STER_GETTER(B nary)
REG STER_GETTER(Cont nuous)
REG STER_GETTER(D screte)
REG STER_GETTER(Str ng)
REG STER_GETTER(SparseB nary)
REG STER_GETTER(SparseCont nuous)
REG STER_GETTER_FROM_DR(BatchS ze)
REG STER_GETTER_FROM_DR(Labels)
REG STER_GETTER_FROM_DR(  ghts)
REG STER_GETTER_AS_TENSOR(B nary)
REG STER_GETTER_AS_TENSOR(Cont nuous)
REG STER_GETTER_AS_TENSOR(D screte)
REG STER_GETTER_AS_TENSOR(Str ng)
REG STER_GETTER_AS_TENSOR(SparseB nary)
REG STER_GETTER_AS_TENSOR(SparseCont nuous)
REG STER_GETTER_GROUP_AS_TENSOR(B nary)
REG STER_GETTER_GROUP_AS_TENSOR(Cont nuous)
REG STER_GETTER_GROUP_AS_TENSOR(D screte)
REG STER_GETTER_GROUP_AS_TENSOR(Str ng)
REG STER_KERNEL_BU LDER(
  Na ("GetHas dFeaturesAsSparseTensor")
  .Dev ce(DEV CE_CPU),
  GetHas dFeaturesAsSparseTensor);
REG STER_KERNEL_BU LDER(
  Na ("GetHas dFeaturesAsSparseTensorV2")
  .Dev ce(DEV CE_CPU),
  GetHas dFeaturesAsSparseTensorV2);
