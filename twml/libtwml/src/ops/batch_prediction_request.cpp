# nclude "tensorflow/core/fra work/op.h"
# nclude "tensorflow/core/fra work/shape_ nference.h"
# nclude "tensorflow/core/fra work/op_kernel.h"

# nclude <twml.h>
# nclude "tensorflow_ut ls.h"
# nclude "res ce_ut ls.h"

REG STER_OP("DecodeAndHashBatchPred ct onRequest")
. nput(" nput_bytes: u nt8")
.Attr("keep_features: l st( nt)")
.Attr("keep_codes: l st( nt)")
.Attr("decode_mode:  nt = 0")
.Output("has d_data_record_handle: res ce")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that decodes batch pred ct on request and creates a handle to t  batch of has d data records.

Attr
  keep_features: a l st of  nt  ds to keep.
  keep_codes: t  r correspond ng code.
  decode_mode:  nteger,  nd cates wh ch decod ng  thod to use. Let a sparse cont nuous
    have a feature_na  and a d ct of {na : value}. 0  nd cates feature_ ds are computed
    as hash(na ). 1  nd cates feature_ ds are computed as hash(feature_na , na )
  shared_na : na  used by t  res ce handle  ns de t  res ce manager.
  conta ner: na  used by t  conta ner of t  res ces.

shared_na  and conta ner are requ red w n  n r  ng from Res ceOpKernel.

 nput
   nput_bytes:  nput tensor conta n ng t  ser al zed batch of BatchPred ct onRequest.

Outputs
  has d_data_record_handle: A res ce handle to t  Has dDataRecordRes ce conta n ng batch of Has dDataRecords.
)doc");

class DecodeAndHashBatchPred ct onRequest : publ c OpKernel {
 publ c:
  expl c  DecodeAndHashBatchPred ct onRequest(OpKernelConstruct on* context)
      : OpKernel(context) {
    std::vector< nt64> keep_features;
    std::vector< nt64> keep_codes;

    OP_REQU RES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQU RES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQU RES_OK(context, context->GetAttr("decode_mode", &m_decode_mode));

    OP_REQU RES(context, keep_features.s ze() == keep_codes.s ze(),
                errors:: nval dArgu nt("keep keys and values must have sa  s ze."));

# fdef USE_DENSE_HASH
    m_keep_map.set_empty_key(0);
#end f  // USE_DENSE_HASH

    for (u nt64_t   = 0;   < keep_features.s ze();  ++) {
      m_keep_map[keep_features[ ]] = keep_codes[ ];
    }
  }

 pr vate:
  twml::Map< nt64_t,  nt64_t> m_keep_map;
   nt64 m_decode_mode;

  vo d Compute(OpKernelContext* context) overr de {
    try {
      Has dDataRecordRes ce *res ce = nullptr;
      OP_REQU RES_OK(context, makeRes ceHandle<Has dDataRecordRes ce>(context, 0, &res ce));

      // Store t   nput bytes  n t  res ce so    snt freed before t  res ce.
      // T   s necessary because   are not copy ng t  contents for tensors.
      res ce-> nput = context-> nput(0);
      const u nt8_t * nput_bytes = res ce-> nput.flat<u nt8>().data();
      twml::Has dDataRecordReader reader;
      twml::Has dBatchPred ct onRequest bpr;
      reader.setKeepMap(&m_keep_map);
      reader.setBuffer( nput_bytes);
      reader.setDecodeMode(m_decode_mode);
      bpr.decode(reader);

      res ce->common = std::move(bpr.common());
      res ce->records = std::move(bpr.requests());

      // Each datarecord has a copy of common features.
      //  n  al ze total_s ze by common_s ze * num_records
       nt64 common_s ze = stat c_cast< nt64>(res ce->common.totalS ze());
       nt64 num_records = stat c_cast< nt64>(res ce->records.s ze());
       nt64 total_s ze = common_s ze * num_records;
      for (const auto &record : res ce->records) {
        total_s ze += stat c_cast< nt64>(record.totalS ze());
      }

      res ce->total_s ze = total_s ze;
      res ce->num_labels = 0;
      res ce->num_  ghts = 0;
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("DecodeAndHashBatchPred ct onRequest").Dev ce(DEV CE_CPU),
  DecodeAndHashBatchPred ct onRequest);

REG STER_OP("DecodeBatchPred ct onRequest")
. nput(" nput_bytes: u nt8")
.Attr("keep_features: l st( nt)")
.Attr("keep_codes: l st( nt)")
.Output("data_record_handle: res ce")
.SetShapeFn(shape_ nference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that decodes batch pred ct on request and creates a handle to t  batch of data records.

Attr
  keep_features: a l st of  nt  ds to keep.
  keep_codes: t  r correspond ng code.
  shared_na : na  used by t  res ce handle  ns de t  res ce manager.
  conta ner: na  used by t  conta ner of t  res ces.

shared_na  and conta ner are requ red w n  n r  ng from Res ceOpKernel.

 nput
   nput_bytes:  nput tensor conta n ng t  ser al zed batch of BatchPred ct onRequest.

Outputs
  data_record_handle: A res ce handle to t  DataRecordRes ce conta n ng batch of DataRecords.
)doc");

class DecodeBatchPred ct onRequest : publ c OpKernel {
 publ c:
  expl c  DecodeBatchPred ct onRequest(OpKernelConstruct on* context)
      : OpKernel(context) {
    std::vector< nt64> keep_features;
    std::vector< nt64> keep_codes;

    OP_REQU RES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQU RES_OK(context, context->GetAttr("keep_codes", &keep_codes));

    OP_REQU RES(context, keep_features.s ze() == keep_codes.s ze(),
                errors:: nval dArgu nt("keep keys and values must have sa  s ze."));

# fdef USE_DENSE_HASH
    m_keep_map.set_empty_key(0);
#end f  // USE_DENSE_HASH

    for (u nt64_t   = 0;   < keep_features.s ze();  ++) {
      m_keep_map[keep_features[ ]] = keep_codes[ ];
    }
  }

 pr vate:
  twml::Map< nt64_t,  nt64_t> m_keep_map;

  vo d Compute(OpKernelContext* context) overr de {
    try {
      DataRecordRes ce *res ce = nullptr;
      OP_REQU RES_OK(context, makeRes ceHandle<DataRecordRes ce>(context, 0, &res ce));

      // Store t   nput bytes  n t  res ce so    snt freed before t  res ce.
      // T   s necessary because   are not copy ng t  contents for tensors.
      res ce-> nput = context-> nput(0);
      const u nt8_t * nput_bytes = res ce-> nput.flat<u nt8>().data();
      twml::DataRecordReader reader;
      twml::BatchPred ct onRequest bpr;
      reader.setKeepMap(&m_keep_map);
      reader.setBuffer( nput_bytes);
      bpr.decode(reader);

      res ce->common = std::move(bpr.common());
      res ce->records = std::move(bpr.requests());

      res ce->num_  ghts = 0;
      res ce->num_labels = 0;
      res ce->keep_map = &m_keep_map;
    } catch (const std::except on &e) {
      context->CtxFa lureW hWarn ng(errors:: nval dArgu nt(e.what()));
    }
  }
};

REG STER_KERNEL_BU LDER(
  Na ("DecodeBatchPred ct onRequest").Dev ce(DEV CE_CPU),
  DecodeBatchPred ct onRequest);
