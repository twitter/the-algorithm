#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

#include <functional>

REGISTER_OP("DecodeAndHashDataRecord")
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
A tensorflow OP that creates a handle for the hashed data record.

Attr
  keep_features: a list of int ids to keep.
  keep_codes: their corresponding code.
  label_features: list of feature ids representing the labels.
  weight_features: list of feature ids representing the weights. Defaults to empty list.
  decode_mode: integer, indicates which decoding method to use. Let a sparse continuous
    have a feature_name and a dict of {name: value}. 0 indicates feature_ids are computed
    as hash(name). 1 indicates feature_ids are computed as hash(feature_name, name)
  shared_name: name used by the resource handle inside the resource manager.
  container: name used by the container of the resources.

Input
  input_bytes: Input tensor containing the serialized batch of HashedDataRecords.

Outputs
  hashed_data_record_handle: A resource handle to batch of HashedDataRecords.
)doc");

template<typename InputType>
class DecodeAndHashDataRecord : public OpKernel {
 public:
  explicit DecodeAndHashDataRecord(OpKernelConstruction* context)
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

 private:
  twml::Map<int64_t, int64_t> m_keep_map;
  twml::Map<int64_t, int64_t> m_labels_map;
  twml::Map<int64_t, int64_t> m_weights_map;
  int64 m_decode_mode;

  void Compute(OpKernelContext* context) override {
    try {
      HashedDataRecordResource *resource = nullptr;
      OP_REQUIRES_OK(context, makeResourceHandle<HashedDataRecordResource>(context, 0, &resource));

      // Store the input bytes in the resource so it isnt freed before the resource.
      // This is necessary because we are not copying the contents for tensors.
      resource->input = context->input(0);
      int batch_size = getBatchSize<InputType>(resource->input);
      int num_labels = static_cast<int>(m_labels_map.size());
      int num_weights = static_cast<int>(m_weights_map.size());

      twml::HashedDataRecordReader reader;
      reader.setKeepMap(&m_keep_map);
      reader.setLabelsMap(&m_labels_map);
      reader.setDecodeMode(m_decode_mode);

      // Do not set weight map if it is empty. This will take a faster path.
      if (num_weights != 0) {
        reader.setWeightsMap(&m_weights_map);
      }

      resource->records.clear();
      resource->records.reserve(batch_size);

      int64 total_size = 0;

      for (int id = 0; id < batch_size; id++) {
        const uint8_t *input_bytes = getInputBytes<InputType>(resource->input, id);
        reader.setBuffer(input_bytes);
        resource->records.emplace_back(num_labels, num_weights);
        resource->records[id].decode(reader);
        total_size += static_cast<int64>(resource->records[id].totalSize());
      }

      resource->total_size = total_size;
      resource->num_labels = num_labels;
      resource->num_weights = num_weights;
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetIdsFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("ids: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns unhashed ids from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
)doc");

// This Kernel is used for both training and serving once the resource is created.
class GetIdsFromHashedDataRecord : public OpKernel {
 public:
  explicit GetIdsFromHashedDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<HashedDataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;
      const int64 common_size = static_cast<int64>(common.totalSize());
      const int64 total_size = handle->total_size;
      TensorShape shape = {total_size};

      Tensor *ids;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));

      int id = 0;
      int64 offset = 0;
      auto ids_flat = ids->flat<int64>();
      for (const auto &record : records) {
        // Since common features are added to each input, add the common_size to the current size.
        // For training common_size == 0, for serving it can be a non-zero value.
        int64 curr_size = static_cast<int64>(record.totalSize()) + common_size;
        std::fill(ids_flat.data() + offset, ids_flat.data() + offset + curr_size, id);
        offset += curr_size;
        id++;
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};


// OutType: Output Tensor Type. FieldType: The storage type used inside HashedDatarecord.
template<typename OutType, typename FieldType>
class GetOutputFromHashedDataRecord : public OpKernel {
 protected:
  using Getter = std::function<const std::vector<FieldType>&(const twml::HashedDataRecord &)>;
  Getter getter;

 public:
  explicit GetOutputFromHashedDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<HashedDataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;
      const int64 total_size = handle->total_size;
      TensorShape shape = {total_size};

      Tensor *output;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &output));

      const auto &common_output = getter(common);

      auto output_data = output->flat<OutType>().data();
      for (const auto &record : records) {
        // This is does not copy anything during training as common_size == 0
        // It will copy the relevant common features coming from a batch prediction request.
        output_data = std::copy(common_output.begin(), common_output.end(), output_data);

        // Copy the current record to output.
        const auto& rec_output = getter(record);
        output_data = std::copy(rec_output.begin(), rec_output.end(), output_data);
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetUKeysFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("ukeys: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns unhashed keys from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  ukeys: unhased keys / raw feature ids from the original request.
)doc");

class GetUKeysFromHashedDataRecord : public GetOutputFromHashedDataRecord<int64, int64_t> {
 public:
  explicit GetUKeysFromHashedDataRecord(OpKernelConstruction* context)
      : GetOutputFromHashedDataRecord<int64, int64_t>(context){
    getter = [](const twml::HashedDataRecord &record) -> const std::vector<int64_t> & {
      return record.keys();
    };
  }
};

REGISTER_OP("GetKeysFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("keys: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns keys from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  keys: keys after raw feature ids are hashed with values (int64)
)doc");

class GetKeysFromHashedDataRecord : public GetOutputFromHashedDataRecord<int64, int64_t> {
 public:
  explicit GetKeysFromHashedDataRecord(OpKernelConstruction* context)
      : GetOutputFromHashedDataRecord<int64, int64_t>(context){
    getter = [](const twml::HashedDataRecord &record) -> const std::vector<int64_t> & {
      return record.transformed_keys();
    };
  }
};

REGISTER_OP("GetValuesFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns values from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  values: feature values.
)doc");

class GetValuesFromHashedDataRecord : public GetOutputFromHashedDataRecord<float, double> {
 public:
  explicit GetValuesFromHashedDataRecord(OpKernelConstruction* context)
      : GetOutputFromHashedDataRecord<float, double>(context){
    getter = [](const twml::HashedDataRecord &record) -> const std::vector<double> & {
      return record.values();
    };
  }
};

REGISTER_OP("GetCodesFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("codes: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns codes from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  codes: deepbird feature code, usually from A,B,C,D ... in the config.
)doc");

class GetCodesFromHashedDataRecord : public GetOutputFromHashedDataRecord<int64, int64_t> {
 public:
  explicit GetCodesFromHashedDataRecord(OpKernelConstruction* context)
      : GetOutputFromHashedDataRecord<int64, int64_t>(context){
    getter = [](const twml::HashedDataRecord &record) -> const std::vector<int64_t> & {
      return record.codes();
    };
  }
};

REGISTER_OP("GetTypesFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("types: int8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns types from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  types: feature types corresponding to BINARY, DISCRETE, etc.
)doc");

class GetTypesFromHashedDataRecord : public GetOutputFromHashedDataRecord<int8, uint8_t> {
 public:
  explicit GetTypesFromHashedDataRecord(OpKernelConstruction* context)
      : GetOutputFromHashedDataRecord<int8, uint8_t>(context){
    getter = [](const twml::HashedDataRecord &record) -> const std::vector<uint8_t> & {
      return record.types();
    };
  }
};

REGISTER_OP("GetBatchSizeFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("batch_size: int64")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns batch size from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  batch_size: Number of records held in the handle.
)doc");

class GetBatchSizeFromHashedDataRecord : public OpKernel {
 public:
  explicit GetBatchSizeFromHashedDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<HashedDataRecordResource>(context, 0);
      Tensor *output;
      OP_REQUIRES_OK(context, context->allocate_output(0, TensorShape({}), &output));
      output->scalar<int64>()() = handle->records.size();
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetTotalSizeFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("total_size: int64")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns total size from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  total_size: Total number of keys / values in the batch.
)doc");

class GetTotalSizeFromHashedDataRecord : public OpKernel {
 public:
  explicit GetTotalSizeFromHashedDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<HashedDataRecordResource>(context, 0);

      Tensor *output;
      OP_REQUIRES_OK(context, context->allocate_output(0, TensorShape({}), &output));
      output->scalar<int64>()() = handle->total_size;
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetLabelsFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("labels: float")
.Attr("default_label: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns labels from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  labels: A 2D tensor of size [batch_size, num_labels] containing the label values.
)doc");

class GetLabelsFromHashedDataRecord : public OpKernel {
 private:
  float default_label;

 public:
  explicit GetLabelsFromHashedDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("default_label", &default_label));
  }

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<HashedDataRecordResource>(context, 0);
      const auto &records = handle->records;
      const int num_labels = static_cast<int>(handle->num_labels);
      TensorShape shape = {static_cast<int64>(handle->records.size()), num_labels};

      Tensor *labels;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &labels));

      // The default value of label is not present in data record is std::nanf
      // For continuous labels, change that to a default_label or label.
      auto func = [this](float label) -> float {
        return std::isnan(label) ? default_label : label;
      };

      auto labels_data = labels->flat<float>().data();
      for (const auto &record : records) {
        const auto& rec_labels = record.labels();
        labels_data = std::transform(rec_labels.begin(), rec_labels.end(), labels_data, func);
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetWeightsFromHashedDataRecord")
.Input("hashed_data_record_handle: resource")
.Output("weights: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns weights from the hashed data record.
Input
  hashed_data_record_handle: Resource handle to DataRecord

Outputs
  weights: A 2D tensor of size [batch_size, num_weights] containing the weight values.
)doc");

class GetWeightsFromHashedDataRecord : public OpKernel {
 public:
  explicit GetWeightsFromHashedDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<HashedDataRecordResource>(context, 0);
      const auto &records = handle->records;
      const int num_weights = static_cast<int>(handle->num_weights);
      TensorShape shape = {static_cast<int64>(handle->records.size()), num_weights};

      Tensor *weights;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &weights));

      auto weights_data = weights->flat<float>().data();
      for (const auto &record : records) {
        const auto& rec_weights = record.weights();
        weights_data = std::copy(rec_weights.begin(), rec_weights.end(), weights_data);
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};


#define REGISTER_DECODE_AND_HASH(InputType)     \
  REGISTER_KERNEL_BUILDER(                      \
    Name("DecodeAndHashDataRecord")             \
    .Device(DEVICE_CPU)                         \
    .TypeConstraint<InputType>("InputType"),    \
    DecodeAndHashDataRecord<InputType>);        \

REGISTER_DECODE_AND_HASH(uint8)
REGISTER_DECODE_AND_HASH(string)

#define REGISTER_GETTER(FIELD)                  \
  REGISTER_KERNEL_BUILDER(                      \
    Name("Get" #FIELD "FromHashedDataRecord")   \
    .Device(DEVICE_CPU),                        \
    Get##FIELD##FromHashedDataRecord);          \

REGISTER_GETTER(Ids)
REGISTER_GETTER(UKeys)
REGISTER_GETTER(Keys)
REGISTER_GETTER(Values)
REGISTER_GETTER(Codes)
REGISTER_GETTER(Types)
REGISTER_GETTER(BatchSize)
REGISTER_GETTER(TotalSize)
REGISTER_GETTER(Labels)
REGISTER_GETTER(Weights)
