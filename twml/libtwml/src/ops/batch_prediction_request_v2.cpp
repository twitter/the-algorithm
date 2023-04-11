#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <cstdint>
#include <twml.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

#include <iterator>

template<typename InputType, typename RecordType>
class DecodeBatchPredictionRequestKernel : public OpKernel {
 public:
  explicit DecodeBatchPredictionRequestKernel(OpKernelConstruction* context)
      : OpKernel(context) {
    std::vector<int64> keep_features;
    std::vector<int64> keep_codes;

    std::vector<int64> label_features;
    std::vector<int64> weight_features;

    OP_REQUIRES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQUIRES_OK(context, context->GetAttr("keep_codes", &keep_codes));

    OP_REQUIRES_OK(context, context->GetAttr("label_features", &label_features));
    OP_REQUIRES_OK(context, context->GetAttr("weight_features", &weight_features));
    OP_REQUIRES_OK(context, context->GetAttr("decode_mode", &m_decode_mode));

    OP_REQUIRES(context, keep_features.size() == keep_codes.size(),
                errors::InvalidArgument("keep keys and values must have same size."));

#ifdef USE_DENSE_HASH
    m_keep_map.set_empty_key(0);
    m_labels_map.set_empty_key(0);
    m_weights_map.set_empty_key(0);
#endif  // USE_DENSE_HASH

    for (uint64_t i = 0; i < keep_features.size(); i++) {
      m_keep_map[keep_features[i]] = keep_codes[i];
    }

    for (uint64_t i = 0; i < label_features.size(); i++) {
      m_labels_map[label_features[i]] = i;
    }

    for (uint64_t i = 0; i < weight_features.size(); i++) {
      m_weights_map[weight_features[i]] = i;
    }
  }

 protected:
  twml::Map<int64_t, int64_t> m_keep_map;
  twml::Map<int64_t, int64_t> m_labels_map;
  twml::Map<int64_t, int64_t> m_weights_map;
  int64 m_decode_mode;

  template<typename ResourceType>
  void Decode(OpKernelContext* context, ResourceType *resource) {
    resource->input = context->input(0);
    const uint8_t *input_bytes = getInputBytes<InputType>(resource->input, 0);
    int num_labels = static_cast<int>(m_labels_map.size());
    int num_weights = static_cast<int>(m_weights_map.size());

    typename RecordType::Reader reader;
    twml::GenericBatchPredictionRequest<RecordType> bpr(num_labels, num_weights);

    reader.setKeepMap(&m_keep_map);
    reader.setLabelsMap(&m_labels_map);
    reader.setBuffer(input_bytes);
    reader.setDecodeMode(m_decode_mode);
    // Do not set weight map if it is empty. This will take a faster path.
    if (num_weights != 0) {
        reader.setWeightsMap(&m_weights_map);
    }
    bpr.decode(reader);

    resource->common = std::move(bpr.common());
    resource->records = std::move(bpr.requests());

    resource->num_labels = num_labels;
    resource->num_weights = num_weights;
  }
};


REGISTER_OP("DecodeAndHashBatchPredictionRequestV2")
.Attr("InputType: {uint8, string}")
.Input("input_bytes: InputType")
.Attr("keep_features: list(int)")
.Attr("keep_codes: list(int)")
.Attr("label_features: list(int)")
.Attr("weight_features: list(int) = []")
.Attr("decode_mode: int = 0")
.Output("hashed_data_record_handle: resource")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that decodes a list/batch of data records and creates a handle to the batch of hashed data records.

Compared to DecodeAndHashBatchPredictionRequest, DecodeAndHashBatchPredictionRequestV2 is used for training instead
of serving. Thus label_features and weight_features[optional] must be passed, and labels and weights are extracted in
the output.
DecodeAndHashBatchPredictionRequestV2 controls what DataRecords we want to process together in a batch in training.
For instance, we can put all instances for a query in the same batch when training a ranking model.
Notice that this OP was added separately to make sure we would not break the API for DecodeAndHashBatchPredictionRequest.
It requires some discussions if we merge the two ops into a single .cpp file in a future API revision.

Attr
  keep_features: a list of int ids to keep.
  keep_codes: their corresponding code.
  label_features: list of feature ids representing the labels.
  weight_features: list of feature ids representing the weights. Defaults to empty list.
  decode_mode: integer, indicates which decoding method to use. Let a sparse continuous
    have a feature_name and a dict of {name: value}. 0 indicates feature_ids are computed
    as hash(name). 1 indicates feature_ids are computed as hash(feature_name, name)

Input
  input_bytes: Input tensor containing the serialized batch of BatchPredictionRequest.

Outputs
  hashed_data_record_handle: A resource handle to the HashedDataRecordResource containing batch of HashedDataRecords.
)doc");

template<typename InputType>
class DecodeAndHashBatchPredictionRequestV2 :
    public DecodeBatchPredictionRequestKernel<InputType, twml::HashedDataRecord> {

public:
  DecodeAndHashBatchPredictionRequestV2(OpKernelConstruction *context)
    : DecodeBatchPredictionRequestKernel<InputType, twml::HashedDataRecord>(context) {
  }

 private:
  void Compute(OpKernelContext* context) override {
    try {
      HashedDataRecordResource *resource = nullptr;
      OP_REQUIRES_OK(
        context,
        makeResourceHandle<HashedDataRecordResource>(context, 0, &resource));

      this->Decode(context, resource);

      // Each datarecord has a copy of common features.
      // Initialize total_size by common_size * num_records
      int64 common_size = static_cast<int64>(resource->common.totalSize());
      int64 num_records = static_cast<int64>(resource->records.size());
      int64 total_size = common_size * num_records;
      for (const auto &record : resource->records) {
        total_size += static_cast<int64>(record.totalSize());
      }

      resource->total_size = total_size;
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("DecodeBatchPredictionRequestV2")
.Attr("InputType: {uint8, string}")
.Input("input_bytes: InputType")
.Attr("keep_features: list(int)")
.Attr("keep_codes: list(int)")
.Attr("label_features: list(int)")
.Attr("weight_features: list(int) = []")
.Attr("decode_mode: int = 0")
.Output("data_record_handle: resource")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that decodes batch prediction request and creates a handle to the batch of data records.

Attr
  keep_features: a list of int ids to keep.
  keep_codes: their corresponding code.
  shared_name: name used by the resource handle inside the resource manager.
  label_features: list of feature ids representing the labels.
  weight_features: list of feature ids representing the weights. Defaults to empty list.
  decode_mode: reserved, do not use.

Input
  input_bytes: Input tensor containing the serialized batch of BatchPredictionRequest.

Outputs
  data_record_handle: A resource handle to the DataRecordResource containing batch of DataRecords.
)doc");


template<typename InputType>
class DecodeBatchPredictionRequestV2 :
    public DecodeBatchPredictionRequestKernel<InputType, twml::DataRecord> {
public:
  DecodeBatchPredictionRequestV2(OpKernelConstruction *context)
    : DecodeBatchPredictionRequestKernel<InputType, twml::DataRecord>(context) {
  }

private:
  void Compute(OpKernelContext* context) override {
    try {
      DataRecordResource *resource = nullptr;
      OP_REQUIRES_OK(
        context,
        makeResourceHandle<DataRecordResource>(context, 0, &resource));
      this->Decode(context, resource);
      resource->keep_map = &(this->m_keep_map);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

#define REGISTER_DECODE_OPS(InputType)                      \
    REGISTER_KERNEL_BUILDER(                                \
        Name("DecodeAndHashBatchPredictionRequestV2")       \
        .Device(DEVICE_CPU)                                 \
        .TypeConstraint<InputType>("InputType"),            \
        DecodeAndHashBatchPredictionRequestV2<InputType>);  \
    REGISTER_KERNEL_BUILDER(                                \
        Name("DecodeBatchPredictionRequestV2")              \
        .Device(DEVICE_CPU)                                 \
        .TypeConstraint<InputType>("InputType"),            \
        DecodeBatchPredictionRequestV2<InputType>);         \

REGISTER_DECODE_OPS(uint8)
REGISTER_DECODE_OPS(string)
