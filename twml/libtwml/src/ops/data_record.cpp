#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include <twml/functions.h>
#include <twml/utilities.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

#include <algorithm>

using std::string;

REGISTER_OP("DecodeDataRecord")
.Attr("InputType: {uint8, string}")
.Attr("keep_features: list(int)")
.Attr("keep_codes: list(int)")
.Attr("label_features: list(int)")
.Attr("weight_features: list(int) = []")
.Input("input_bytes: InputType")
.Output("data_record_handle: resource")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that creates a handle for the datarecord.

Attr
  keep_features: a list of int ids to keep.
  keep_codes: their corresponding code.
  label_features: list of feature ids representing the labels.
  weight_features: list of feature ids representing the weights. Defaults to empty list.
  shared_name: name used by the resource handle inside the resource manager.
  container: name used by the container of the resources.

shared_name and container are required when inheriting from ResourceOpKernel.

Input
  input_bytes: Input tensor containing the serialized batch of HashedDataRecords.

Outputs
  data_record_handle: A resource handle to the DataRecord struct.
)doc");

template<typename InputType>
class DecodeDataRecord : public OpKernel {
 public:
  explicit DecodeDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {
    std::vector<int64> keep_features;
    std::vector<int64> keep_codes;

    std::vector<int64> label_features;
    std::vector<int64> weight_features;

    OP_REQUIRES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQUIRES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQUIRES_OK(context, context->GetAttr("label_features", &label_features));
    OP_REQUIRES_OK(context, context->GetAttr("weight_features", &weight_features));

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

  void Compute(OpKernelContext* context) override {
    try {
      DataRecordResource *resource = nullptr;
      OP_REQUIRES_OK(context, makeResourceHandle<DataRecordResource>(context, 0, &resource));

      // Store the input bytes in the resource so it isnt freed before the resource.
      // This is necessary because we are not copying the contents for tensors.
      resource->input = context->input(0);
      int batch_size = getBatchSize<InputType>(resource->input);
      int num_labels = static_cast<int>(m_labels_map.size());
      int num_weights = static_cast<int>(m_weights_map.size());

      twml::DataRecordReader reader;
      reader.setKeepMap(&m_keep_map);
      reader.setLabelsMap(&m_labels_map);

      // Do not set weight map if it is empty. This will take a faster path.
      if (num_weights != 0) {
        reader.setWeightsMap(&m_weights_map);
      }

      resource->records.clear();
      resource->records.reserve(batch_size);
      for (int i = 0; i < batch_size; i++) {
        resource->records.emplace_back(num_labels, num_weights);
      }

      for (int64 id = 0; id < batch_size; id++) {
        const uint8_t *input_bytes = getInputBytes<InputType>(resource->input, id);
        reader.setBuffer(input_bytes);
        // decode the reader
        resource->records[id].decode(reader);
      }
      // This should be fine because m_keep_map should never go out of scope.
      resource->keep_map = &m_keep_map;
      resource->num_weights = num_weights;
      resource->num_labels = num_labels;
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

int64_t count_if_exists(const twml::DataRecord::BinaryFeatures &set,
                        const twml::Map<int64_t, int64_t> *const keep_map) {
  int64_t count = 0;
  for (const auto &key : set) {
    if (keep_map->find(key) == keep_map->end()) continue;
    count++;
  }
  return count;
}

// This works for continuous, discrete, and string features
template<typename V>
int64_t count_if_exists(const twml::Map<int64_t, V> &map,
                        const twml::Map<int64_t, int64_t> *const keep_map) {
  int64_t count = 0;
  for (const auto &elem : map) {
    if (keep_map->find(elem.first) == keep_map->end()) continue;
    count++;
  }
  return count;
}

int64_t count_if_exists(const twml::DataRecord::SparseBinaryFeatures &map,
                        const twml::Map<int64_t, int64_t> *const keep_map) {
  int64_t count = 0;
  for (const auto &elem : map) {
    if (keep_map->find(elem.first) == keep_map->end()) continue;
    count += elem.second.size();
  }
  return count;
}

int64_t count_if_exists(const twml::DataRecord::SparseContinuousFeatures &map,
                        const twml::Map<int64_t, int64_t> *const keep_map) {
  int64_t count = 0;
  for (const auto &elem : map) {
    if (keep_map->find(elem.first) == keep_map->end()) continue;
    count += elem.second.size();
  }
  return count;
}

REGISTER_OP("GetBinaryFeatures")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("keys: int64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads binary features
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  values: always set to 1 (float)
)doc");

class GetBinaryFeatures : public OpKernel {
 public:
  explicit GetBinaryFeatures(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

      int64 common_binary_size = count_if_exists(common.getBinary(), handle->keep_map);
      int64 total_binary_size = records.size() * common_binary_size;
      for (int id = 0; id < records.size(); id++) {
        total_binary_size += count_if_exists(handle->records[id].getBinary(), handle->keep_map);
      }
      const int total_size = static_cast<int>(total_binary_size);

      TensorShape shape = {total_size};
      Tensor* keys = nullptr;
      Tensor* ids = nullptr;
      Tensor* values = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &values));

      uint64_t offset = 0;
      auto keys_flat = keys->flat<int64>();
      auto ids_flat = ids->flat<int64>();
      auto values_flat = values->flat<float>();

      for (int64 id = 0; id < records.size(); id++) {
        for (const auto &it : common.getBinary()) {
          if (handle->keep_map->find(it) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it;
          offset++;
        }
        for (const auto &it : records[id].getBinary()) {
          if (handle->keep_map->find(it) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it;
          offset++;
        }
      }
      // All the values for binary features are 1.
      std::fill(values_flat.data(), values_flat.data() + total_size, 1);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetContinuousFeatures")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("keys: int64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads continuous features
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: Datarecord keys (int64)
  values: Datarecord values(float)
)doc");

class GetContinuousFeatures : public OpKernel {
 public:
  explicit GetContinuousFeatures(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

      int64 common_continuous_size = count_if_exists(common.getContinuous(), handle->keep_map);
      int64 total_continuous_size = records.size() * common_continuous_size;
      for (int id = 0; id < records.size(); id++) {
        total_continuous_size += count_if_exists(handle->records[id].getContinuous(),
                                                 handle->keep_map);
      }
      const int total_size = static_cast<int>(total_continuous_size);

      TensorShape shape = {total_size};
      Tensor* keys = nullptr;
      Tensor* values = nullptr;
      Tensor* ids = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &values));

      uint64_t offset = 0;
      auto keys_flat = keys->flat<int64>();
      auto values_flat = values->flat<float>();
      auto ids_flat = ids->flat<int64>();

      for (int64 id = 0; id < records.size(); id++) {
        for (const auto &it : common.getContinuous()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it.first;
          values_flat(offset) = it.second;
          offset++;
        }
        for (const auto &it : records[id].getContinuous()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it.first;
          values_flat(offset) = it.second;
          offset++;
        }
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetDiscreteFeatures")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("keys: int64")
.Output("values: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads discrete features
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  values: DataRecord values(int64)
)doc");

class GetDiscreteFeatures : public OpKernel {
 public:
  explicit GetDiscreteFeatures(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

      int64 common_discrete_size = count_if_exists(common.getDiscrete(), handle->keep_map);
      int64 total_discrete_size = records.size() * common_discrete_size;
      for (int id = 0; id < records.size(); id++) {
        total_discrete_size += count_if_exists(handle->records[id].getDiscrete(),
                                               handle->keep_map);
      }
      const int total_size = static_cast<int>(total_discrete_size);

      TensorShape shape = {total_size};
      Tensor* keys = nullptr;
      Tensor* values = nullptr;
      Tensor* ids = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &values));

      uint64_t offset = 0;
      auto keys_flat = keys->flat<int64>();
      auto values_flat = values->flat<int64>();
      auto ids_flat = ids->flat<int64>();

      for (int64 id = 0; id < records.size(); id++) {
        for (const auto &it : common.getDiscrete()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it.first;
          values_flat(offset) = it.second;
          offset++;
        }
        for (const auto &it : records[id].getDiscrete()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it.first;
          values_flat(offset) = it.second;
          offset++;
        }
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetStringFeatures")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("keys: int64")
.Output("names: string")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads string features
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  names: DataRecord values(string)
  values: always set to 1 (float)
)doc");

class GetStringFeatures : public OpKernel {
 public:
  explicit GetStringFeatures(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

      int64 common_string_size = count_if_exists(common.getString(), handle->keep_map);
      int64 total_string_size = records.size() * common_string_size;
      for (int id = 0; id < records.size(); id++) {
        total_string_size += count_if_exists(handle->records[id].getString(),
                                             handle->keep_map);
      }
      const int total_size = static_cast<int>(total_string_size);

      TensorShape shape = {total_size};
      Tensor* keys = nullptr;
      Tensor* names = nullptr;
      Tensor* ids = nullptr;
      Tensor*values = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &names));
      OP_REQUIRES_OK(context, context->allocate_output(3, shape, &values));

      uint64_t offset = 0;
      auto keys_flat = keys->flat<int64>();
      auto names_flat = names->flat<string>();
      auto ids_flat = ids->flat<int64>();
      auto values_flat = values->flat<float>();

      std::fill(values_flat.data(), values_flat.data() + total_size, 1);
      for (int64 id = 0; id < records.size(); id++) {
        for (const auto &it : common.getString()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it.first;
          names_flat(offset) = it.second;
          offset++;
        }
        for (const auto &it : records[id].getString()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          ids_flat(offset) = id;
          keys_flat(offset) = it.first;
          names_flat(offset) = it.second;
          offset++;
        }
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetSparseBinaryFeatures")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("keys: int64")
.Output("names: string")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads sparse binary features
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  names: DataRecord values(string)
  values: always set to 1 (float)
)doc");

class GetSparseBinaryFeatures : public OpKernel {
 public:
  explicit GetSparseBinaryFeatures(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

      int64 common_sparse_binary_size = count_if_exists(common.getSparseBinary(), handle->keep_map);
      int64 total_sparse_binary_size = records.size() * common_sparse_binary_size;
      for (int id = 0; id < records.size(); id++) {
        total_sparse_binary_size += count_if_exists(handle->records[id].getSparseBinary(),
                                                    handle->keep_map);
      }
      const int total_size = static_cast<int>(total_sparse_binary_size);

      TensorShape shape = {total_size};
      Tensor* keys = nullptr;
      Tensor* names = nullptr;
      Tensor* ids = nullptr;
      Tensor* values = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &names));
      OP_REQUIRES_OK(context, context->allocate_output(3, shape, &values));

      uint64_t offset = 0;
      auto keys_flat = keys->flat<int64>();
      auto names_flat = names->flat<string>();
      auto ids_flat = ids->flat<int64>();
      auto values_flat = values->flat<float>();

      // All the values for sparse binary features are 1.
      std::fill(values_flat.data(), values_flat.data() + total_size, 1);
      for (int64 id = 0; id < records.size(); id++) {
        for (const auto &it : common.getSparseBinary()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          for (const auto &it_inner : it.second) {
            ids_flat(offset) = id;
            keys_flat(offset) = it.first;
            names_flat(offset) = it_inner;
            offset++;
          }
        }
        for (const auto &it : records[id].getSparseBinary()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          for (const auto &it_inner : it.second) {
            ids_flat(offset) = id;
            keys_flat(offset) = it.first;
            names_flat(offset) = it_inner;
            offset++;
          }
        }
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetSparseContinuousFeatures")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("keys: int64")
.Output("values: float")
.Output("names: string")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that reads sparse continuous features
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  values: DataRecord values(float)
  names: DataRecord values(string)
)doc");

class GetSparseContinuousFeatures : public OpKernel {
 public:
  explicit GetSparseContinuousFeatures(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;
      const auto &common = handle->common;

      int64 common_sparse_continuous_size = count_if_exists(common.getSparseContinuous(),
                                                            handle->keep_map);
      int64 total_sparse_continuous_size = records.size() * common_sparse_continuous_size;
      for (int id = 0; id < records.size(); id++) {
        total_sparse_continuous_size += count_if_exists(handle->records[id].getSparseContinuous(),
                                                        handle->keep_map);
      }
      const int total_size = static_cast<int>(total_sparse_continuous_size);

      TensorShape shape = {total_size};
      Tensor* keys = nullptr;
      Tensor* values = nullptr;
      Tensor* names = nullptr;
      Tensor* ids = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &values));
      OP_REQUIRES_OK(context, context->allocate_output(3, shape, &names));

      uint64_t offset = 0;
      auto keys_flat = keys->flat<int64>();
      auto values_flat = values->flat<float>();
      auto names_flat = names->flat<string>();
      auto ids_flat = ids->flat<int64>();

      for (int64 id = 0; id < records.size(); id++) {
        // copying the contents of the maps of maps
        for (const auto &it : common.getSparseContinuous()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          // for each id; iterate through the number of maps corresponding to that id
          for (const auto &it_inner : it.second) {
            ids_flat(offset) = id;
            keys_flat(offset) = it.first;
            names_flat(offset) = it_inner.first;
            values_flat(offset) = it_inner.second;
            offset++;
          }
        }
        // copying the contents of the maps of maps
        for (const auto &it : records[id].getSparseContinuous()) {
          if (handle->keep_map->find(it.first) == handle->keep_map->end()) continue;
          // for each id; iterate through the number of maps corresponding to that id
          for (const auto &it_inner : it.second) {
            ids_flat(offset) = id;
            keys_flat(offset) = it.first;
            names_flat(offset) = it_inner.first;
            values_flat(offset) = it_inner.second;
            offset++;
          }
        }
      }
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetBatchSizeFromDataRecord")
.Input("data_record_handle: resource")
.Output("batch_size: int64")
.SetShapeFn(shape_inference::ScalarShape)
.Doc(R"doc(
A tensorflow OP that returns batch size from the data record.
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  batch_size: Number of records held in the handle.
)doc");

class GetBatchSizeFromDataRecord : public OpKernel {
 public:
  explicit GetBatchSizeFromDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      Tensor *output;
      OP_REQUIRES_OK(context, context->allocate_output(0, TensorShape({}), &output));
      output->scalar<int64>()() = handle->records.size();
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetLabelsFromDataRecord")
.Input("data_record_handle: resource")
.Output("labels: float")
.Attr("default_label: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns labels from the data record.

Attr
  default_label: The value used when a label is absent in a data record.

Input
  data_record_handle: Resource handle to DataRecord

Outputs
  labels: A 2D tensor of size [batch_size, num_labels] containing the label values.
)doc");

class GetLabelsFromDataRecord : public OpKernel {
 private:
  float default_label;

 public:
  explicit GetLabelsFromDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("default_label", &default_label));
  }

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
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

REGISTER_OP("GetWeightsFromDataRecord")
.Input("data_record_handle: resource")
.Output("weights: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns weights from the data record.
Input
  data_record_handle: Resource handle to DataRecord

Outputs
  weights: A 2D tensor of size [batch_size, num_weights] containing the weight values.
)doc");

class GetWeightsFromDataRecord : public OpKernel {
 public:
  explicit GetWeightsFromDataRecord(OpKernelConstruction* context)
      : OpKernel(context) {}

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
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

template<typename ValueType, typename FeatureType, typename TensorType>
void SetValueGroup(
const FeatureType& type,
const int64& feature_id,
const int64& id,
const ValueType& default_value,
TensorType values_flat) {
  auto it = type.find(feature_id);
  values_flat(id) = (it == type.end()) ? default_value : it->second;
}

template<typename ValueType, typename TensorType>
// overloading for BinaryFeatures; as it needs to set a value of 1
void SetValueGroup(
const twml::DataRecord::BinaryFeatures& type,
const int64& feature_id,
const int64& id,
const ValueType& default_value,
TensorType values_flat) {
  auto it = type.find(feature_id);
  values_flat(id) = (it == type.end()) ? default_value : 1;
}

// Helper for Group Extraction of Dense Features
template<typename ValueType, typename FeatureType>
void ComputeHelperGroupFeaturesAsTensors(
OpKernelContext* context,
const std::vector<int64>& feature_ids,
ValueType& default_value,
std::function<const FeatureType&(const twml::DataRecord&)> f) {
  auto handle = getHandle<DataRecordResource>(context, 0);
  const auto &records = handle->records;
  // Output shape is 2D; where the first dimension corresponds to the batch_size
  // and the second corresponds to the number of features passed to the TF Op.
  const int batch_size = static_cast<int64>(handle->records.size());
  const int num_feature_ids = static_cast<int>(feature_ids.size());
  TensorShape shape = {batch_size, num_feature_ids};

  // Define the output
  Tensor* values = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(0, shape, &values));
  auto values_flat = values->flat<ValueType>();

  for (int64 id = 0; id < records.size(); id++) {
    const auto &type = f(records[id]);
    const auto id_offset = id * feature_ids.size();
    for (int64 fid = 0; fid < feature_ids.size(); fid++) {
      auto feature_id = feature_ids[fid];
      // The value is set to default if it does not exist in the current DataRecord
      SetValueGroup(type, feature_id, id_offset + fid, default_value, values_flat);
    }
  }
}

// Helper for Single Extraction of Dense Features
template<typename ValueType, typename FeatureType>
void ComputeHelperFeaturesAsTensors(
OpKernelContext* context,
ValueType& default_value,
int64 feature_id,
std::function<const FeatureType&(const twml::DataRecord&)> f) {
  auto handle = getHandle<DataRecordResource>(context, 0);
  const auto &records = handle->records;
  // Output shape is 2D; where the first dimension corresponds to the batch_size
  // and the second corresponds to the number of features passed to the TF Op.
  const int total_size = static_cast<int64>(handle->records.size());
  TensorShape shape = {total_size};

  // Define the output
  Tensor* values = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(0, shape, &values));
  auto values_flat = values->flat<ValueType>();
  for (int64 id = 0; id < records.size(); id++) {
    const auto &type = f(records[id]);
    SetValueGroup(type, feature_id, id, default_value, values_flat);
  }
}

REGISTER_OP("GetBinaryAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_id: int")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_id: Id representing the feature whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  values: A Tensor corresponding to the value of the feature_id across multiple DataRecords
)doc");

class GetBinaryAsTensor : public OpKernel {
 private:
  int64 feature_id;
  float default_value;

 public:
  explicit GetBinaryAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::BinaryFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::BinaryFeatures& { return record.getBinary(); };
      ComputeHelperFeaturesAsTensors(context, default_value, feature_id, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetContinuousAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_id: int")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_id: Id representing the feature whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  values: A Tensor corresponding to the value of the feature_id across multiple DataRecords
)doc");

class GetContinuousAsTensor : public OpKernel {
 private:
  int64 feature_id;
  float default_value;

 public:
  explicit GetContinuousAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::ContinuousFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::ContinuousFeatures& { return record.getContinuous(); };
      ComputeHelperFeaturesAsTensors(context, default_value, feature_id, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetDiscreteAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_id: int")
.Attr("default_value: int")
.Output("values: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_id: Id representing the feature whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  values: A Tensor corresponding to the value of the feature_id across multiple DataRecords
)doc");

class GetDiscreteAsTensor : public OpKernel {
 private:
  int64 feature_id;
  int64 default_value;

 public:
  explicit GetDiscreteAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::DiscreteFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::DiscreteFeatures& { return record.getDiscrete(); };
      ComputeHelperFeaturesAsTensors(context, default_value, feature_id, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetStringAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_id: int")
.Attr("default_value: string")
.Output("names: string")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_id: Id representing the feature whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  names: A Tensor corresponding to the value of the feature_id across multiple DataRecords
)doc");

class GetStringAsTensor : public OpKernel {
 private:
  int64 feature_id;
  string default_value;

 public:
  explicit GetStringAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::StringFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::StringFeatures& { return record.getString(); };
      ComputeHelperFeaturesAsTensors(context, default_value, feature_id, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};


REGISTER_OP("GetBinaryGroupAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_ids: list(int)")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_ids: List of ids representing the features whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  values: A Tensor corresponding to the values of the feature_ids across multiple DataRecords
)doc");


class GetBinaryGroupAsTensor : public OpKernel {
 private:
  float default_value;
  std::vector<int64> feature_ids;

 public:
  explicit GetBinaryGroupAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_ids", &feature_ids));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
       std::function<const twml::DataRecord::BinaryFeatures &(const twml::DataRecord &)> f =
        [](const twml::DataRecord& record) ->const twml::DataRecord::BinaryFeatures& { return record.getBinary(); };
       ComputeHelperGroupFeaturesAsTensors(context, feature_ids, default_value, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};


REGISTER_OP("GetContinuousGroupAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_ids: list(int)")
.Attr("default_value: float")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_ids: List of ids representing the features whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  values: A Tensor corresponding to the values of the feature_ids across multiple DataRecords
)doc");

class GetContinuousGroupAsTensor : public OpKernel {
 private:
  float default_value;
  std::vector<int64> feature_ids;

 public:
  explicit GetContinuousGroupAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_ids", &feature_ids));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::ContinuousFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::ContinuousFeatures& { return record.getContinuous(); };
      ComputeHelperGroupFeaturesAsTensors(context, feature_ids, default_value, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetDiscreteGroupAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_ids: list(int)")
.Attr("default_value: int")
.Output("values: int64")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_ids: List of ids representing the features whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  values: A Tensor corresponding to the values of the feature_ids across multiple DataRecords
)doc");

class GetDiscreteGroupAsTensor : public OpKernel {
 private:
  std::vector<int64> feature_ids;
  int64 default_value;

 public:
  explicit GetDiscreteGroupAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_ids", &feature_ids));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::DiscreteFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::DiscreteFeatures& { return record.getDiscrete(); };
      ComputeHelperGroupFeaturesAsTensors(context, feature_ids, default_value, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetStringGroupAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_ids: list(int)")
.Attr("default_value: string")
.Output("names: string")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns a Dense Tensor with the values of a particular feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_ids: List of ids representing the features whose values will be extracted.
  default_value: default_value to be inputted if the values are missing from the current DataRecord.
Outputs
  names: A Tensor corresponding to the values of the feature_ids across multiple DataRecords
)doc");

class GetStringGroupAsTensor : public OpKernel {
 private:
  std::vector<int64> feature_ids;
  string default_value;

 public:
  explicit GetStringGroupAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_ids", &feature_ids));
    OP_REQUIRES_OK(context, context->GetAttr("default_value", &default_value));
  }

  void Compute(OpKernelContext* context) override {
    try {
      std::function<const twml::DataRecord::StringFeatures &(const twml::DataRecord &)> f =
       [](const twml::DataRecord& record) ->const twml::DataRecord::StringFeatures& { return record.getString(); };
    ComputeHelperGroupFeaturesAsTensors(context, feature_ids, default_value, f);
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetSparseBinaryAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_id: int")
.Output("ids: int64")
.Output("keys: int64")
.Output("names: string")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns tensors corresponding to the ids, keys and names of a particular
feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_id: Id representing the feature whose values will be extracted.
Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  names: DataRecord values(string)
)doc");
class GetSparseBinaryAsTensor : public OpKernel {
 private:
  int64 feature_id;

 public:
  explicit GetSparseBinaryAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
  }

  void Compute(OpKernelContext* context) override {
    try {
      // We need two passes to the data:
      // 1 to compute the output size of the tensor
      // 2 to copy the values to the tensor
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;

      // Creating a vector we increment every time a key is found
      std::vector<std::string> temp_names;
      std::vector<int64> temp_ids;

      for (int64 id = 0; id < records.size(); id++) {
        const auto &sparse_binary = records[id].getSparseBinary();
        auto it = sparse_binary.find(feature_id);
        // Find all instances of key in DataRecord
        if (it != sparse_binary.end()) {
          // insert to temp_names all the values in the dictionary value
          temp_names.insert(temp_names.end(), it->second.begin(), it->second.end());
          temp_ids.insert(temp_ids.end(), it->second.size(), id);
        }
      }

      // The total_size will be the that of the saved vector
      const int total_size = static_cast<int64>(temp_names.size());
      TensorShape shape = {total_size};
      Tensor* ids = nullptr;
      Tensor* keys = nullptr;
      Tensor* names = nullptr;

      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &names));

      auto keys_flat = keys->flat<int64>();
      auto names_flat = names->flat<string>();
      auto ids_flat = ids->flat<int64>();

      // The feature id value will always be the same
      std::fill(keys_flat.data(), keys_flat.data() + total_size, feature_id);
      std::copy(temp_names.begin(), temp_names.end(), names_flat.data());
      std::copy(temp_ids.begin(), temp_ids.end(), ids_flat.data());
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetSparseContinuousAsTensor")
.Input("data_record_handle: resource")
.Attr("feature_id: int")
.Output("ids: int64")
.Output("keys: int64")
.Output("names: string")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns tensors corresponding to the ids, keys, names and values of a particular
feature_id.
Input
  data_record_handle: Resource handle to DataRecord
Attr
  feature_id: Id representing the feature whose values will be extracted.
Outputs
  ids: ids specifies the index of the records[id] in the batch (int64)
  keys: DataRecord keys (int64)
  names: DataRecord values(string)
  values: DataRecord values(float)
)doc");
class GetSparseContinuousAsTensor : public OpKernel {
 private:
  int64 feature_id;

 public:
  explicit GetSparseContinuousAsTensor(OpKernelConstruction* context) : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
  }

  void Compute(OpKernelContext* context) override {
    try {
      // We need two passes to the data:
      // 1 to compute the output size of the tensor
      // 2 to copy the values to the tensor
      auto handle = getHandle<DataRecordResource>(context, 0);
      const auto &records = handle->records;

      // Creating a vector we increment every time a key is found
      std::vector<std::string> temp_names;
      std::vector<float> temp_values;
      std::vector<int64> temp_ids;

      for (int64 id = 0; id < records.size(); id++) {
        const auto &sparse_continuous = records[id].getSparseContinuous();
        auto it = sparse_continuous.find(feature_id);
        // Find all instances of key in DataRecord
        if (it != sparse_continuous.end()) {
          // insert to temp_names all the values in the dictionary value
          auto value_map = it->second;
          for (auto& elem : value_map) {
             temp_names.push_back(elem.first);
             temp_values.push_back(elem.second);
             temp_ids.push_back(id);
          }
        }
      }

      // The total_size will be the that of the saved vector
      const int total_size = static_cast<int64>(temp_names.size());
      TensorShape shape = {total_size};
      Tensor* ids = nullptr;
      Tensor* keys = nullptr;
      Tensor* names = nullptr;
      Tensor* values = nullptr;

      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
      OP_REQUIRES_OK(context, context->allocate_output(2, shape, &names));
      OP_REQUIRES_OK(context, context->allocate_output(3, shape, &values));

      auto keys_flat = keys->flat<int64>();
      auto names_flat = names->flat<string>();
      auto ids_flat = ids->flat<int64>();
      auto values_flat = values->flat<float>();

      // The feature id value will always be the same
      std::fill(keys_flat.data(), keys_flat.data() + total_size, feature_id);
      std::copy(temp_names.begin(), temp_names.end(), names_flat.data());
      std::copy(temp_ids.begin(), temp_ids.end(), ids_flat.data());
      std::copy(temp_values.begin(), temp_values.end(), values_flat.data());
    } catch (const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

// Helper function to add ids, keys and values to common vector
inline void addIdsKeysValuesToVectors(
  const int64 id,
  const int64 key,
  const double value,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  ids.push_back(id);
  keys.push_back(key);
  values.push_back(value);
}

struct KeepFeatures {
  KeepFeatures() : vec(), set() {}
  template<typename ContainerType>
  KeepFeatures(const std::vector<int64> &keep_features,
               const ContainerType *const container) {
    vec.reserve(keep_features.size());
#ifdef USE_DENSE_HASH
    set.resize(keep_features.size());
    set.set_empty_key(0);
#else
    set.reserve(keep_features.size());
#endif  // USE_DENSE_HASH
    set.max_load_factor(0.5);
    for (const auto &elem : keep_features) {
      if (container->find(elem) == container->end()) continue;
      vec.push_back(elem);
      set.insert(elem);
    }
  }
  size_t size() const {
    return vec.size();
  }
  std::vector<int64> vec;
  twml::Set<int64> set;
};

// Helper Function to Filter and Hash Feature for Binary Features
void filterAndHashFeature(
  const twml::DataRecord::BinaryFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      addIdsKeysValuesToVectors(current_id, *iter, 1, ids, keys, values);
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem) == keep_features.set.end()) continue;
      addIdsKeysValuesToVectors(current_id, elem, 1, ids, keys, values);
    }
  }
}

// Helper Function to Filter and Hash Feature for Continuous Features
void filterAndHashFeature(
  const twml::DataRecord::ContinuousFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      addIdsKeysValuesToVectors(current_id, iter->first, iter->second, ids, keys, values);
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem.first) == keep_features.set.end()) continue;
      addIdsKeysValuesToVectors(current_id, elem.first, elem.second, ids, keys, values);
    }
  }
}

// Helper Function to Filter and Hash Feature for Discrete Features
void filterAndHashFeature(
  const twml::DataRecord::DiscreteFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      int64_t key = twml::mixDiscreteIdAndValue(iter->first, iter->second);
      addIdsKeysValuesToVectors(current_id, key, 1, ids, keys, values);
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem.first) == keep_features.set.end()) continue;
      int64_t key = twml::mixDiscreteIdAndValue(elem.first, elem.second);
      addIdsKeysValuesToVectors(current_id, key, 1, ids, keys, values);
    }
  }
}

// Helper Function to Filter and Hash Feature for String Features
void filterAndHashFeature(
  const twml::DataRecord::StringFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      int64_t key = twml::mixStringIdAndValue(
        iter->first,
        iter->second.size(),
        reinterpret_cast<const uint8_t*>(iter->second.c_str()));
      addIdsKeysValuesToVectors(current_id, key, 1, ids, keys, values);
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem.first) == keep_features.set.end()) continue;
      int64_t key = twml::mixStringIdAndValue(
        elem.first,
        elem.second.size(),
        reinterpret_cast<const uint8_t*>(elem.second.c_str()));
      addIdsKeysValuesToVectors(current_id, key, 1, ids, keys, values);
    }
  }
}

// Helper Function to Filter and Hash Feature for Sparse Binary Features
void filterAndHashFeature(
  const twml::DataRecord::SparseBinaryFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      for (const auto &name : iter->second) {
        int64_t key = twml::mixStringIdAndValue(iter->first, name.size(),
                                                reinterpret_cast<const uint8_t*>(name.c_str()));
        addIdsKeysValuesToVectors(current_id, key, 1, ids, keys, values);
      }
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem.first) == keep_features.set.end()) continue;
      for (const auto &name : elem.second) {
        int64_t key = twml::mixStringIdAndValue(elem.first, name.size(),
                                                reinterpret_cast<const uint8_t*>(name.c_str()));
        addIdsKeysValuesToVectors(current_id, key, 1, ids, keys, values);
      }
    }
  }
}

// Helper Function to Filter and Hash Feature for Sparse Continuous Features
void filterAndHashFeature(
  const twml::DataRecord::SparseContinuousFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      for (const auto &map : iter->second) {
        int64_t key = twml::mixStringIdAndValue(
          iter->first,
          map.first.size(),
          reinterpret_cast<const uint8_t*>(map.first.c_str()));
        addIdsKeysValuesToVectors(current_id, key, map.second, ids, keys, values);
      }
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem.first) == keep_features.set.end()) continue;
      for (const auto &map : elem.second) {
        int64_t key = twml::mixStringIdAndValue(
          elem.first,
          map.first.size(),
          reinterpret_cast<const uint8_t*>(map.first.c_str()));
        addIdsKeysValuesToVectors(current_id, key, map.second, ids, keys, values);
      }
    }
  }
}

// Helper Function to Filter and Hash Feature for Sparse Continuous Features
void filterAndHashFeatureCompat(
  const twml::DataRecord::SparseContinuousFeatures& features,
  const int64 current_id,
  const KeepFeatures &keep_features,
  std::vector<int64>& ids,
  std::vector<int64>& keys,
  std::vector<float>& values) {
  if (keep_features.size() < 2 * features.size()) {
    for (const auto &f : keep_features.vec) {
      const auto &iter = features.find(f);
      if (iter == features.end()) continue;
      for (const auto &map : iter->second) {
        int64_t key = twml::featureId(map.first);
        addIdsKeysValuesToVectors(current_id, key, map.second, ids, keys, values);
      }
    }
  } else {
    for (const auto &elem : features) {
      if (keep_features.set.find(elem.first) == keep_features.set.end()) continue;
      for (const auto &map : elem.second) {
        int64_t key = twml::featureId(map.first);
        addIdsKeysValuesToVectors(current_id, key, map.second, ids, keys, values);
      }
    }
  }
}

void copy_if_exists(std::vector<int64>& out,
                    const std::vector<int64>& in,
                    const twml::Map<int64_t, int64_t> *const map) {
  out.reserve(in.size());
  for (const auto &elem : in) {
    if (map->find(elem) == map->end()) continue;
    out.push_back(elem);
  }
}

void ComputeHashedFeaturesAsTensor(OpKernelContext* context,
                                   const DataRecordResource *const handle,
                                   const KeepFeatures &binary_keep_features,
                                   const KeepFeatures &continuous_keep_features,
                                   const KeepFeatures &discrete_keep_features,
                                   const KeepFeatures &string_keep_features,
                                   const KeepFeatures &sparse_binary_keep_features,
                                   const KeepFeatures &sparse_continuous_keep_features,
                                   bool sparse_continuous_compatibility) {

  const auto &records = handle->records;
  uint64_t estimated_size = (binary_keep_features.size() + continuous_keep_features.size() +
                             discrete_keep_features.size() + string_keep_features.size() +
                             sparse_binary_keep_features.size() +
                             sparse_continuous_keep_features.size());
  // Construct temporary vectors for common features
  std::vector<int64> common_ids, common_keys, temp_ids, temp_keys;
  std::vector<float> common_values, temp_values;
  common_ids.reserve(estimated_size);
  common_keys.reserve(estimated_size);
  common_values.reserve(estimated_size);

  const auto &common_binary = handle->common.getBinary();
  const auto &common_continuous = handle->common.getContinuous();
  const auto &common_discrete = handle->common.getDiscrete();
  const auto &common_string = handle->common.getString();
  const auto &common_sparse_binary = handle->common.getSparseBinary();
  const auto &common_sparse_continuous = handle->common.getSparseContinuous();

  filterAndHashFeature(common_binary, 0, binary_keep_features,
                       common_ids, common_keys, common_values);
  filterAndHashFeature(common_continuous, 0, continuous_keep_features,
                       common_ids, common_keys, common_values);
  filterAndHashFeature(common_discrete, 0, discrete_keep_features,
                       common_ids, common_keys, common_values);
  filterAndHashFeature(common_string, 0, string_keep_features,
                       common_ids, common_keys, common_values);
  filterAndHashFeature(common_sparse_binary, 0, sparse_binary_keep_features,
                       common_ids, common_keys, common_values);
  if (sparse_continuous_compatibility) {
    filterAndHashFeatureCompat(common_sparse_continuous, 0, sparse_continuous_keep_features,
                               common_ids, common_keys, common_values);
  } else {
    filterAndHashFeature(common_sparse_continuous, 0, sparse_continuous_keep_features,
                         common_ids, common_keys, common_values);
  }
  common_ids.clear();
  // Construct temporary vectors for all features
  estimated_size = (estimated_size + common_keys.size()) * records.size();
  temp_ids.reserve(estimated_size);
  temp_keys.reserve(estimated_size);
  temp_values.reserve(estimated_size);

  for (int64 id = 0; id < records.size(); id++) {
    temp_ids.insert(temp_ids.end(), common_keys.size(), id);
    temp_keys.insert(temp_keys.end(), common_keys.begin(), common_keys.end());
    temp_values.insert(temp_values.end(), common_values.begin(), common_values.end());
    const auto &binary = records[id].getBinary();
    const auto &continuous = records[id].getContinuous();
    const auto &discrete = records[id].getDiscrete();
    const auto &str = records[id].getString();
    const auto &sparse_binary = records[id].getSparseBinary();
    const auto &sparse_continuous = records[id].getSparseContinuous();

    filterAndHashFeature(binary, id, binary_keep_features,
                         temp_ids, temp_keys, temp_values);
    filterAndHashFeature(continuous, id, continuous_keep_features,
                         temp_ids, temp_keys, temp_values);
    filterAndHashFeature(discrete, id, discrete_keep_features,
                         temp_ids, temp_keys, temp_values);
    filterAndHashFeature(str, id, string_keep_features,
                         temp_ids, temp_keys, temp_values);
    filterAndHashFeature(sparse_binary, id, sparse_binary_keep_features,
                         temp_ids, temp_keys, temp_values);
    if (sparse_continuous_compatibility) {
      filterAndHashFeatureCompat(sparse_continuous, id, sparse_continuous_keep_features,
                                 temp_ids, temp_keys, temp_values);
    } else {
      filterAndHashFeature(sparse_continuous, id, sparse_continuous_keep_features,
                           temp_ids, temp_keys, temp_values);
    }
  }

  // Copy the temporary vectors into the output Tensors
  TensorShape shape = {static_cast<int64>(temp_ids.size())};
  Tensor* ids = nullptr;
  Tensor* keys = nullptr;
  Tensor* values = nullptr;
  OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids));
  OP_REQUIRES_OK(context, context->allocate_output(1, shape, &keys));
  OP_REQUIRES_OK(context, context->allocate_output(2, shape, &values));
  auto ids_flat = ids->flat<int64>();
  auto keys_flat = keys->flat<int64>();
  auto values_flat = values->flat<float>();
  std::copy(temp_ids.begin(), temp_ids.end(), ids_flat.data());
  std::copy(temp_keys.begin(), temp_keys.end(), keys_flat.data());
  std::copy(temp_values.begin(), temp_values.end(), values_flat.data());
}

REGISTER_OP("GetHashedFeaturesAsSparseTensor")
.Input("data_record_handle: resource")
.Attr("binary_keep_features: list(int)")
.Attr("continuous_keep_features: list(int)")
.Attr("discrete_keep_features: list(int)")
.Attr("string_keep_features: list(int)")
.Attr("sparse_binary_keep_features: list(int)")
.Attr("sparse_continuous_keep_features: list(int)")
.Output("ids: int64")
.Output("keys: int64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  return Status::OK();
}).Doc(R"doc(
A tensorflow OP for returning required features of different type as
a single sparse tensor. Hashing trick is applied.

Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records in the batch (int64)
  keys: DataRecord keys (int64)
  values: DataRecord values (float)
)doc");

class GetHashedFeaturesAsSparseTensor: public OpKernel {
 public:
  explicit GetHashedFeaturesAsSparseTensor(OpKernelConstruction* context): OpKernel(context) {
    // Get the list of features to keep for each feature type
    OP_REQUIRES_OK(context, context->GetAttr("binary_keep_features", &binary_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("continuous_keep_features", &continuous_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("discrete_keep_features", &discrete_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("string_keep_features", &string_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("sparse_binary_keep_features", &sparse_binary_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("sparse_continuous_keep_features", &sparse_continuous_keep_features_));
  }

 private:
  std::vector<int64> binary_keep_features_, continuous_keep_features_, discrete_keep_features_;
  std::vector<int64> string_keep_features_, sparse_binary_keep_features_, sparse_continuous_keep_features_;

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      // Create a new list of keep features based on the original keep_set.
      // This is to ensure compatibility with existing behavior such as:
      //  - Ensure no new features are decoded in this op.
      //  - Ensure labels or weights dont get included here.
      // TODO: Should we return features requested by user here even if they are labels / weights?
      KeepFeatures binary_keep_features(binary_keep_features_, handle->keep_map);
      KeepFeatures continuous_keep_features(continuous_keep_features_, handle->keep_map);
      KeepFeatures discrete_keep_features(discrete_keep_features_, handle->keep_map);
      KeepFeatures string_keep_features(string_keep_features_, handle->keep_map);
      KeepFeatures sparse_binary_keep_features(sparse_binary_keep_features_, handle->keep_map);
      KeepFeatures sparse_continuous_keep_features(sparse_continuous_keep_features_, handle->keep_map);
      ComputeHashedFeaturesAsTensor(context, handle.get(),
                                    binary_keep_features,
                                    continuous_keep_features,
                                    discrete_keep_features,
                                    string_keep_features,
                                    sparse_binary_keep_features,
                                    sparse_continuous_keep_features,
                                    false);
    } catch(const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};

REGISTER_OP("GetHashedFeaturesAsSparseTensorV2")
.Input("data_record_handle: resource")
.Attr("binary_keep_features: list(int)")
.Attr("continuous_keep_features: list(int)")
.Attr("discrete_keep_features: list(int)")
.Attr("string_keep_features: list(int)")
.Attr("sparse_binary_keep_features: list(int)")
.Attr("sparse_continuous_keep_features: list(int)")
.Attr("keep_features: list(int)")
.Attr("keep_codes: list(int)")
.Attr("decode_mode: int = 0")
.Output("ids: int64")
.Output("keys: int64")
.Output("values: float")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
  return Status::OK();
}).Doc(R"doc(
A tensorflow OP for returning required features of different type as
a single sparse tensor. Hashing trick is applied.

Input
  data_record_handle: Resource handle to DataRecord

Outputs
  ids: ids specifies the index of the records in the batch (int64)
  keys: DataRecord keys (int64)
  values: DataRecord values (float)
)doc");

class GetHashedFeaturesAsSparseTensorV2: public OpKernel {
 public:
  explicit GetHashedFeaturesAsSparseTensorV2(OpKernelConstruction* context): OpKernel(context) {
    std::vector<int64> keep_features;
    std::vector<int64> keep_codes;
    std::vector<int64> binary_keep_features_, continuous_keep_features_, discrete_keep_features_;
    std::vector<int64> string_keep_features_, sparse_binary_keep_features_, sparse_continuous_keep_features_;

    // Get the list of features to keep for each feature type
    OP_REQUIRES_OK(context, context->GetAttr("binary_keep_features", &binary_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("continuous_keep_features", &continuous_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("discrete_keep_features", &discrete_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("string_keep_features", &string_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("sparse_binary_keep_features", &sparse_binary_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("sparse_continuous_keep_features", &sparse_continuous_keep_features_));
    OP_REQUIRES_OK(context, context->GetAttr("keep_features", &keep_features));
    OP_REQUIRES_OK(context, context->GetAttr("keep_codes", &keep_codes));
    OP_REQUIRES_OK(context, context->GetAttr("decode_mode", &m_decode_mode));

    twml::Map<int64_t, int64_t> keep_map;
#ifdef USE_DENSE_HASH
    keep_map.set_empty_key(0);
#endif  // USE_DENSE_HASH
    for (uint64_t i = 0; i < keep_features.size(); i++) {
      keep_map[keep_features[i]] = keep_codes[i];
    }


    binary_keep_features = KeepFeatures(binary_keep_features_, &keep_map);
    continuous_keep_features = KeepFeatures(continuous_keep_features_, &keep_map);
    discrete_keep_features = KeepFeatures(discrete_keep_features_, &keep_map);
    string_keep_features = KeepFeatures(string_keep_features_, &keep_map);
    sparse_binary_keep_features = KeepFeatures(sparse_binary_keep_features_, &keep_map);
    sparse_continuous_keep_features = KeepFeatures(sparse_continuous_keep_features_, &keep_map);

  }

 private:
  KeepFeatures binary_keep_features, continuous_keep_features, discrete_keep_features;
  KeepFeatures string_keep_features, sparse_binary_keep_features, sparse_continuous_keep_features;
  int64 m_decode_mode;

  void Compute(OpKernelContext* context) override {
    try {
      auto handle = getHandle<DataRecordResource>(context, 0);
      // Create a new list of keep features based on the original keep_set.
      // This is to ensure compatibility with existing behavior such as:
      //  - Ensure no new features are decoded in this op.
      //  - Ensure labels or weights dont get included here.
      // TODO: Should we return features requested by user here even if they are labels / weights?
      ComputeHashedFeaturesAsTensor(context, handle.get(),
                                    binary_keep_features,
                                    continuous_keep_features,
                                    discrete_keep_features,
                                    string_keep_features,
                                    sparse_binary_keep_features,
                                    sparse_continuous_keep_features,
                                    m_decode_mode == 0);
    } catch(const std::exception &e) {
      context->CtxFailureWithWarning(errors::InvalidArgument(e.what()));
    }
  }
};


#define REGISTER_DECODE_DATA_RECORD(InputType)  \
  REGISTER_KERNEL_BUILDER(                      \
    Name("DecodeDataRecord")                    \
    .Device(DEVICE_CPU)                         \
    .TypeConstraint<InputType>("InputType"),    \
    DecodeDataRecord<InputType>);               \

REGISTER_DECODE_DATA_RECORD(uint8)
REGISTER_DECODE_DATA_RECORD(string)

#define REGISTER_GETTER(FIELD)                  \
  REGISTER_KERNEL_BUILDER(                      \
    Name("Get" #FIELD "Features")               \
    .Device(DEVICE_CPU),                        \
    Get##FIELD##Features);                      \

#define REGISTER_GETTER_FROM_DR(FIELD)          \
  REGISTER_KERNEL_BUILDER(                      \
    Name("Get" #FIELD "FromDataRecord")         \
    .Device(DEVICE_CPU),                        \
    Get##FIELD##FromDataRecord);                \

#define REGISTER_GETTER_AS_TENSOR(FIELD)        \
  REGISTER_KERNEL_BUILDER(                      \
    Name("Get" #FIELD "AsTensor")               \
    .Device(DEVICE_CPU),                        \
    Get##FIELD##AsTensor);                      \


#define REGISTER_GETTER_GROUP_AS_TENSOR(FIELD)  \
  REGISTER_KERNEL_BUILDER(                      \
    Name("Get" #FIELD "GroupAsTensor")          \
    .Device(DEVICE_CPU),                        \
    Get##FIELD##GroupAsTensor);                 \

REGISTER_GETTER(Binary)
REGISTER_GETTER(Continuous)
REGISTER_GETTER(Discrete)
REGISTER_GETTER(String)
REGISTER_GETTER(SparseBinary)
REGISTER_GETTER(SparseContinuous)
REGISTER_GETTER_FROM_DR(BatchSize)
REGISTER_GETTER_FROM_DR(Labels)
REGISTER_GETTER_FROM_DR(Weights)
REGISTER_GETTER_AS_TENSOR(Binary)
REGISTER_GETTER_AS_TENSOR(Continuous)
REGISTER_GETTER_AS_TENSOR(Discrete)
REGISTER_GETTER_AS_TENSOR(String)
REGISTER_GETTER_AS_TENSOR(SparseBinary)
REGISTER_GETTER_AS_TENSOR(SparseContinuous)
REGISTER_GETTER_GROUP_AS_TENSOR(Binary)
REGISTER_GETTER_GROUP_AS_TENSOR(Continuous)
REGISTER_GETTER_GROUP_AS_TENSOR(Discrete)
REGISTER_GETTER_GROUP_AS_TENSOR(String)
REGISTER_KERNEL_BUILDER(
  Name("GetHashedFeaturesAsSparseTensor")
  .Device(DEVICE_CPU),
  GetHashedFeaturesAsSparseTensor);
REGISTER_KERNEL_BUILDER(
  Name("GetHashedFeaturesAsSparseTensorV2")
  .Device(DEVICE_CPU),
  GetHashedFeaturesAsSparseTensorV2);
