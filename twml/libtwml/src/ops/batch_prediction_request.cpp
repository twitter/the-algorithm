#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

REGISTER_OP("DecodeAndHashBatchPredictionRequest")
.Input("input_bytes: uint8")
.Attr("keep_features: list(int)")
.Attr("keep_codes: list(int)")
.Attr("decode_mode: int = 0")
.Output("hashed_data_record_handle: resource")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that decodes batch prediction request and creates a handle to the batch of hashed data records.

Attr
  keep_features: a list of int ids to keep.
  keep_codes: their corresponding code.
  decode_mode: integer, indicates which decoding method to use. Let a sparse continuous
    have a feature_name and a dict of {name: value}. 0 indicates feature_ids are computed
    as hash(name). 1 indicates feature_ids are computed as hash(feature_name, name)
  shared_name: name used by the resource handle inside the resource manager.
  container: name used by the container of the resources.

shared_name and container are required when inheriting from ResourceOpKernel.

Input
  input_bytes: Input tensor containing the serialized batch of BatchPredictionRequest.

Outputs
  hashed_data_record_handle: A resource handle to the HashedDataRecordResource containing batch of HashedDataRecords.
)doc");

class DecodeAndHashBatchPredictionRequest : public OpKernel {
 public:
  explicit DecodeAndHashBatchPredictionRequest(OpKernelConstruction* context)
      : OpKernel(context) {
    std::vector<int64> keep_features;
    std::vector<int64> keep_codes;

    OP_REQUIRES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQUIRES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQUIRES_OK(context, context->GetAttr("decode_mode", &m_decode_mode));

    OP_REQUIRES(context, keep_features.size() == keep_codes.size(),
                errors::InvalidArgument("keep keys and values must have same size."));

#ifdef USE_DENSE_HASH
    m_keep_map.set_empty_key(0);
#endif  // USE_DENSE_HASH

    for (uint64_t i = 0; i < keep_features.size(); i++) {
      m_keep_map[keep_features[i]] = keep_codes[i];
    }
  }

 private:
  twml::Map<int64_t, int64_t> m_keep_map;
  int64 m_decode_mode;

  void Compute(OpKernelContext* context) override {
    try {
      HashedDataRecordResource *resource = nullptr;
      OP_REQUIRES_OK(context, makeResourceHandle<HashedDataRecordResource>(context, 0, &resource));

      // Store the input bytes in the resource so it isnt freed before the resource.
      // This is necessary because we are not copying the contents for tensors.
      resource->input = context->input(0);
      const uint8_t *input_bytes = resource->input.flat<uint8>().data();
      twml::HashedDataRecordReader reader;
      twml::HashedBatchPredictionRequest bpr;
      reader.setKeepMap(&m_keep_map);
      reader.setBuffer(input_bytes);
      reader.setDecodeMode(m_decode_mode);
      bpr.decode(reader);

      resource->common = std::move(bpr.common());
      resource->records = std::move(bpr.requests());

      // Each datarecord has a copy of common features.
      // Initialize total_size by common_size * num_records
      int64 common_size = static_cast<int64>(resource->common.totalSize());
      int64 num_records = static_cast<int64>(resource->records.size());
      int64 total_size = common_size * num_records;
      for (const auto &record : resource->records) {
        total_size += static_cast<int64>(record.totalSize());
      }

      resource->total_size = total_size;
      resource->num_labels = 0;
      resource->num_weights = 0;
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("DecodeAndHashBatchPredictionRequest").Device(DEVICE_CPU),
  DecodeAndHashBatchPredictionRequest);

REGISTER_OP("DecodeBatchPredictionRequest")
.Input("input_bytes: uint8")
.Attr("keep_features: list(int)")
.Attr("keep_codes: list(int)")
.Output("data_record_handle: resource")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that decodes batch prediction request and creates a handle to the batch of data records.

Attr
  keep_features: a list of int ids to keep.
  keep_codes: their corresponding code.
  shared_name: name used by the resource handle inside the resource manager.
  container: name used by the container of the resources.

shared_name and container are required when inheriting from ResourceOpKernel.

Input
  input_bytes: Input tensor containing the serialized batch of BatchPredictionRequest.

Outputs
  data_record_handle: A resource handle to the DataRecordResource containing batch of DataRecords.
)doc");

class DecodeBatchPredictionRequest : public OpKernel {
 public:
  explicit DecodeBatchPredictionRequest(OpKernelConstruction* context)
      : OpKernel(context) {
    std::vector<int64> keep_features;
    std::vector<int64> keep_codes;

    OP_REQUIRES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQUIRES_OK(context, context->GetAttr("keep_codes", &keep_codes));

    OP_REQUIRES(context, keep_features.size() == keep_codes.size(),
                errors::InvalidArgument("keep keys and values must have same size."));

#ifdef USE_DENSE_HASH
    m_keep_map.set_empty_key(0);
#endif  // USE_DENSE_HASH

    for (uint64_t i = 0; i < keep_features.size(); i++) {
      m_keep_map[keep_features[i]] = keep_codes[i];
    }
  }

 private:
  twml::Map<int64_t, int64_t> m_keep_map;

  void Compute(OpKernelContext* context) override {
    try {
      DataRecordResource *resource = nullptr;
      OP_REQUIRES_OK(context, makeResourceHandle<DataRecordResource>(context, 0, &resource));

      // Store the input bytes in the resource so it isnt freed before the resource.
      // This is necessary because we are not copying the contents for tensors.
      resource->input = context->input(0);
      const uint8_t *input_bytes = resource->input.flat<uint8>().data();
      twml::DataRecordReader reader;
      twml::BatchPredictionRequest bpr;
      reader.setKeepMap(&m_keep_map);
      reader.setBuffer(input_bytes);
      bpr.decode(reader);

      resource->common = std::move(bpr.common());
      resource->records = std::move(bpr.requests());

      resource->num_weights = 0;
      resource->num_labels = 0;
      resource->keep_map = &m_keep_map;
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("DecodeBatchPredictionRequest").Device(DEVICE_CPU),
  DecodeBatchPredictionRequest);
