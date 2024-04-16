# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"
# nclude "res ce_ut ls.h"

# nclude <funct onal>

REG STER_OP("DecodeAndHashDataRecord")
.Attr(" nputType: {u nt8, str ng}")
. nput(" nput_bytes:  nputType")
.Attr("keep_features: l st( nt)")
.Attr("keep_codes: l st( nt)")
.Attr("label_features: l st( nt)")
.Attr("  ght_features: l st( nt) = []")
.Attr("decode_mode:  nt = 0")
.Output("has d_data_record_handle: res ce")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that creates a handle for t  has d data record.

Attr
  keep_features: a l st of  nt  ds to keep.
  keep_codes: t  r correspond ng code.
  label_features: l st of feature  ds represent ng t  labels.
    ght_features: l st of feature  ds represent ng t    ghts. Defaults to empty l st.
  decode_mode:  nteger,  nd cates wh ch decod ng  thod to use. Let a sparse cont nuous
    have a feature_na  and a d ct of {na : value}. 0  nd cates feature_ ds are computed
    as hash(na ). 1  nd cates feature_ ds are computed as hash(feature_na , na )
  shared_na : na  used by t  res ce handle  ns de t  res ce manager.
  conta ner: na  used by t  conta ner of t  res ces.

 nput
   nput_bytes:  nput tensor conta n ng t  ser al zed batch of Has dDataRecords.

Outputs
  has d_data_record_handle: A res ce handle to batch of Has dDataRecords.
)doc");

template<typena   nputType>
class DecodeAndHashDataRecord : publ c OpKernel {
 publ c:
  expl c  DecodeAndHashDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {
    std::vector< nt64> keep_features;
    std::vector< nt64> keep_codes;

    std::vector< nt64> label_features;
    std::vector< nt64>   ght_features;

    OP_REQU RES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQU RES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQU RES_OK(context, context->GetAttr("label_features", &label_features));
    OP_REQU RES_OK(context, context->GetAttr("  ght_features", &  ght_features));
    OP_REQU RES_OK(context, context->GetAttr("decode_mode", &m_decode_mode));

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
   nt64 m_decode_mode;

  vo d Compute(OpKernelContext* context) overr de {
    try {
      Has dDataRecordRes ce *res ce = nullptr;
      OP_REQU RES_OK(context, makeRes ceHandle<Has dDataRecordRes ce>(context, 0, &res ce));

      // Store t   nput bytes  n t  res ce so    snt freed before t  res ce.
      // T   s necessary because   are not copy ng t  contents for tensors.
      res ce-> nput = context-> nput(0);
       nt batch_s ze = getBatchS ze< nputType>(res ce-> nput);
       nt num_labels = stat c_cast< nt>(m_labels_map.s ze());
       nt num_  ghts = stat c_cast< nt>(m_  ghts_map.s ze());

      twml::Has dDataRecordReader reader;
      reader.setKeepMap(&m_keep_map);
      reader.setLabelsMap(&m_labels_map);
      reader.setDecodeMode(m_decode_mode);

      // Do not set   ght map  f    s empty. T  w ll take a faster path.
       f (num_  ghts != 0) {
        reader.set  ghtsMap(&m_  ghts_map);
      }

      res ce->records.clear();
      res ce->records.reserve(batch_s ze);

       nt64 total_s ze = 0;

      for ( nt  d = 0;  d < batch_s ze;  d++) {
        const u nt8_t * nput_bytes = get nputBytes< nputType>(res ce-> nput,  d);
        reader.setBuffer( nput_bytes);
        res ce->records.emplace_back(num_labels, num_  ghts);
        res ce->records[ d].decode(reader);
        total_s ze += stat c_cast< nt64>(res ce->records[ d].totalS ze());
      }

      res ce->total_s ze = total_s ze;
      res ce->num_labels = num_labels;
      res ce->num_  ghts = num_  ghts;
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("Get dsFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output(" ds:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns unhas d  ds from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
   ds:  ds spec f es t   ndex of t  records[ d]  n t  batch ( nt64)
)doc");

// T  Kernel  s used for both tra n ng and serv ng once t  res ce  s created.
class Get dsFromHas dDataRecord : publ c OpKernel {
 publ c:
  expl c  Get dsFromHas dDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<Has dDataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;
      const  nt64 common_s ze = stat c_cast< nt64>(common.totalS ze());
      const  nt64 total_s ze = handle->total_s ze;
      TensorShape shape = {total_s ze};

      Tensor * ds;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, & ds));

       nt  d = 0;
       nt64 offset = 0;
      auto  ds_flat =  ds->flat< nt64>();
      for (const auto &record : records) {
        // S nce common features are added to each  nput, add t  common_s ze to t  current s ze.
        // For tra n ng common_s ze == 0, for serv ng   can be a non-zero value.
         nt64 curr_s ze = stat c_cast< nt64>(record.totalS ze()) + common_s ze;
        std::f ll( ds_flat.data() + offset,  ds_flat.data() + offset + curr_s ze,  d);
        offset += curr_s ze;
         d++;
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};


// OutType: Output Tensor Type. F eldType: T  storage type used  ns de Has dDatarecord.
template<typena  OutType, typena  F eldType>
class GetOutputFromHas dDataRecord : publ c OpKernel {
 protected:
  us ng Getter = std::funct on<const std::vector<F eldType>&(const twml::Has dDataRecord &)>;
  Getter getter;

 publ c:
  expl c  GetOutputFromHas dDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<Has dDataRecordRes ce>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;
      const  nt64 total_s ze = handle->total_s ze;
      TensorShape shape = {total_s ze};

      Tensor *output;
      OP_REQU RES_OK(context, context->allocate_output(0, shape, &output));

      const auto &common_output = getter(common);

      auto output_data = output->flat<OutType>().data();
      for (const auto &record : records) {
        // T   s does not copy anyth ng dur ng tra n ng as common_s ze == 0
        //   w ll copy t  relevant common features com ng from a batch pred ct on request.
        output_data = std::copy(common_output.beg n(), common_output.end(), output_data);

        // Copy t  current record to output.
        const auto& rec_output = getter(record);
        output_data = std::copy(rec_output.beg n(), rec_output.end(), output_data);
      }
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetUKeysFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("ukeys:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns unhas d keys from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  ukeys: unhased keys / raw feature  ds from t  or g nal request.
)doc");

class GetUKeysFromHas dDataRecord : publ c GetOutputFromHas dDataRecord< nt64,  nt64_t> {
 publ c:
  expl c  GetUKeysFromHas dDataRecord(OpKernelConstruct on* context)
      : GetOutputFromHas dDataRecord< nt64,  nt64_t>(context){
    getter = [](const twml::Has dDataRecord &record) -> const std::vector< nt64_t> & {
      return record.keys();
    };
  }
};

REG STER_OP("GetKeysFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("keys:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns keys from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  keys: keys after raw feature  ds are has d w h values ( nt64)
)doc");

class GetKeysFromHas dDataRecord : publ c GetOutputFromHas dDataRecord< nt64,  nt64_t> {
 publ c:
  expl c  GetKeysFromHas dDataRecord(OpKernelConstruct on* context)
      : GetOutputFromHas dDataRecord< nt64,  nt64_t>(context){
    getter = [](const twml::Has dDataRecord &record) -> const std::vector< nt64_t> & {
      return record.transfor d_keys();
    };
  }
};

REG STER_OP("GetValuesFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns values from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  values: feature values.
)doc");

class GetValuesFromHas dDataRecord : publ c GetOutputFromHas dDataRecord<float, double> {
 publ c:
  expl c  GetValuesFromHas dDataRecord(OpKernelConstruct on* context)
      : GetOutputFromHas dDataRecord<float, double>(context){
    getter = [](const twml::Has dDataRecord &record) -> const std::vector<double> & {
      return record.values();
    };
  }
};

REG STER_OP("GetCodesFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("codes:  nt64")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns codes from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  codes: deepb rd feature code, usually from A,B,C,D ...  n t  conf g.
)doc");

class GetCodesFromHas dDataRecord : publ c GetOutputFromHas dDataRecord< nt64,  nt64_t> {
 publ c:
  expl c  GetCodesFromHas dDataRecord(OpKernelConstruct on* context)
      : GetOutputFromHas dDataRecord< nt64,  nt64_t>(context){
    getter = [](const twml::Has dDataRecord &record) -> const std::vector< nt64_t> & {
      return record.codes();
    };
  }
};

REG STER_OP("GetTypesFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("types:  nt8")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns types from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  types: feature types correspond ng to B NARY, D SCRETE, etc.
)doc");

class GetTypesFromHas dDataRecord : publ c GetOutputFromHas dDataRecord< nt8, u nt8_t> {
 publ c:
  expl c  GetTypesFromHas dDataRecord(OpKernelConstruct on* context)
      : GetOutputFromHas dDataRecord< nt8, u nt8_t>(context){
    getter = [](const twml::Has dDataRecord &record) -> const std::vector<u nt8_t> & {
      return record.types();
    };
  }
};

REG STER_OP("GetBatchS zeFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("batch_s ze:  nt64")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns batch s ze from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  batch_s ze: Number of records  ld  n t  handle.
)doc");

class GetBatchS zeFromHas dDataRecord : publ c OpKernel {
 publ c:
  expl c  GetBatchS zeFromHas dDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<Has dDataRecordRes ce>(context, 0);
      Tensor *output;
      OP_REQU RES_OK(context, context->allocate_output(0, TensorShape({}), &output));
      output->scalar< nt64>()() = handle->records.s ze();
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetTotalS zeFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("total_s ze:  nt64")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns total s ze from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  total_s ze: Total number of keys / values  n t  batch.
)doc");

class GetTotalS zeFromHas dDataRecord : publ c OpKernel {
 publ c:
  expl c  GetTotalS zeFromHas dDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<Has dDataRecordRes ce>(context, 0);

      Tensor *output;
      OP_REQU RES_OK(context, context->allocate_output(0, TensorShape({}), &output));
      output->scalar< nt64>()() = handle->total_s ze;
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_OP("GetLabelsFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("labels: float")
.Attr("default_label: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns labels from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
  labels: A 2D tensor of s ze [batch_s ze, num_labels] conta n ng t  label values.
)doc");

class GetLabelsFromHas dDataRecord : publ c OpKernel {
 pr vate:
  float default_label;

 publ c:
  expl c  GetLabelsFromHas dDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {
    OP_REQU RES_OK(context, context->GetAttr("default_label", &default_label));
  }

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<Has dDataRecordRes ce>(context, 0);
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

REG STER_OP("Get  ghtsFromHas dDataRecord")
. nput("has d_data_record_handle: res ce")
.Output("  ghts: float")
.SetShapeFn([](::tensorflow::shape_ nference:: nferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns   ghts from t  has d data record.
 nput
  has d_data_record_handle: Res ce handle to DataRecord

Outputs
    ghts: A 2D tensor of s ze [batch_s ze, num_  ghts] conta n ng t    ght values.
)doc");

class Get  ghtsFromHas dDataRecord : publ c OpKernel {
 publ c:
  expl c  Get  ghtsFromHas dDataRecord(OpKernelConstruct on* context)
      : OpKernel(context) {}

  vo d Compute(OpKernelContext* context) overr de {
    try {
      auto handle = getHandle<Has dDataRecordRes ce>(context, 0);
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


#def ne REG STER_DECODE_AND_HASH( nputType)     \
  REG STER_KERNEL_BU LDER(                      \
    Na ("DecodeAndHashDataRecord")             \
    .Dev ce(DEV CE_CPU)                         \
    .TypeConstra nt< nputType>(" nputType"),    \
    DecodeAndHashDataRecord< nputType>);        \

REG STER_DECODE_AND_HASH(u nt8)
REG STER_DECODE_AND_HASH(str ng)

#def ne REG STER_GETTER(F ELD)                  \
  REG STER_KERNEL_BU LDER(                      \
    Na ("Get" #F ELD "FromHas dDataRecord")   \
    .Dev ce(DEV CE_CPU),                        \
    Get##F ELD##FromHas dDataRecord);          \

REG STER_GETTER( ds)
REG STER_GETTER(UKeys)
REG STER_GETTER(Keys)
REG STER_GETTER(Values)
REG STER_GETTER(Codes)
REG STER_GETTER(Types)
REG STER_GETTER(BatchS ze)
REG STER_GETTER(TotalS ze)
REG STER_GETTER(Labels)
REG STER_GETTER(  ghts)
