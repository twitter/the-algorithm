#include "tensorflow/core/framework/op.h"
#include "tensorflow/core/framework/shape_inference.h"
#include "tensorflow/core/framework/op_kernel.h"

#include <twml.h>
#include "tensorflow_utils.h"
#include "resource_utils.h"

#include <algorithm>
using std::string;

REGISTER_OP("GetStringTensorsFromDataRecord")
.Attr("feature_id: int")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("strings: string")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns string tensors from the data record.

Attr
  feature_id: The hashed id of the feature name.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  ids: A 1D int64 tensor representing the input index in a given batch.
  strings: A 1D string tensor representing the decoded strings from the batch.
)doc");

REGISTER_OP("GetStringTensorsFromHashedDataRecord")
.Attr("feature_id: int")
.Input("hashed_data_record_handle: resource")
.Output("ids: int64")
.Output("strings: string")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns string tensors from the hashed data record.

Attr
  feature_id: The hashed id of the feature name.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  ids: A 1D int64 tensor representing the input index in a given batch.
  strings: A 1D string tensor representing the decoded strings from the batch.
)doc");

template<typename Resource>
class GetStringTensorsOp : public OpKernel {
 private:
  int64 feature_id;

 public:
  explicit GetStringTensorsOp(OpKernelConstruction *context)
      : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
  }

  void Compute(OpKernelContext *context) override {
    auto handle = getHandle<Resource>(context, 0);
    const int64 batch_size = static_cast<int64>(handle->records.size());
    const auto &records = handle->records;

    try {
      int64 total_size = 0;
      for (const auto &record : records) {
        try {
          const auto &tensor = record.getRawTensor(feature_id);
          total_size += static_cast<int64>(tensor.getNumElements());
        } catch(const std::out_of_range &err) {
          LOG(WARNING) << "Ignoring missing string tensor with key: " << feature_id << std::endl;
          continue;
        }
      }

      twml::ThriftReader reader(nullptr);
      TensorShape shape = {total_size};
      Tensor *strings_tensor = nullptr;
      Tensor *ids_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, shape, &ids_tensor));
      OP_REQUIRES_OK(context, context->allocate_output(1, shape, &strings_tensor));

      auto strings_data = strings_tensor->flat<string>().data();
      auto ids_data = ids_tensor->flat<int64>().data();

      for (int64 i = 0; i < batch_size; i++) {
        const auto &record = records[i];
        try {
          const twml::RawTensor &tensor = record.getRawTensor(feature_id);
          const uint8_t *buffer = static_cast<const uint8_t *>(tensor.getData<void>());
          const int64 num_strings = static_cast<int64>(tensor.getNumElements());
          reader.setBuffer(buffer);

          for (int64 j = 0; j < num_strings; j++) {
            const uint8_t *curr_begin = nullptr;
            const auto curr_length = reader.getRawBuffer<uint8_t>(&curr_begin);
            strings_data[j] = std::string(curr_begin, curr_begin + curr_length);
            ids_data[j] = i;
          }
          ids_data += num_strings;
          strings_data += num_strings;
        } catch(const std::out_of_range &err) {
          continue;
        }
      }
    } catch(const std::exception &err) {
      context->CtxFailureWithWarning(errors::InvalidArgument(err.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("GetStringTensorsFromDataRecord")
  .Device(DEVICE_CPU),
  GetStringTensorsOp<DataRecordResource>);

REGISTER_KERNEL_BUILDER(
  Name("GetStringTensorsFromHashedDataRecord")
  .Device(DEVICE_CPU),
  GetStringTensorsOp<HashedDataRecordResource>);

REGISTER_OP("GetTensorsFromDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_id: int")
.Input("data_record_handle: resource")
.Output("output: string")
.Output("out_shape: int64")
.Output("out_type: string")
.Output("out_endian: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from the data record.

Attr
  feature_id: The hashed id of the feature name.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  output: A 2D byte tensor representing the requested feature.
  out_shape: A tensor containing [batch_size, thrift_shape].
  out_type: Output type returned as a string tensor of size 1.
  out_endian: Endianness of the bytes returned a tensor of size 1. 0: litte, 1: big.
)doc");

REGISTER_OP("GetTensorsFromHashedDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_id: int")
.Input("hashed_data_record_handle: resource")
.Output("output: string")
.Output("out_shape: int64")
.Output("out_type: string")
.Output("out_endian: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that returns decodes and tensors from the hashed data record.

Attr
  feature_id: The hashed id of the feature name.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  output: A 2D byte tensor representing the requested feature.
  out_shape: A tensor containing [batch_size, thrift_shape].
  out_type: Output type returned as a string tensor of size 1.
  out_endian: Endianness of the bytes returned a tensor of size 1. 0: litte, 1: big.
)doc");

template<class Resource>
class GetTensorsOp : public OpKernel {
 private:
  bool assert_shape;
  int64 feature_id;

 public:
  explicit GetTensorsOp(OpKernelConstruction *context)
      : OpKernel(context), assert_shape(true) {
    OP_REQUIRES_OK(context, context->GetAttr("assert_shape", &assert_shape));
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
  }

  void Compute(OpKernelContext *context) override {
    auto handle = getHandle<Resource>(context, 0);
    uint64 batch_size = handle->records.size();
    const auto &records = handle->records;

    try {
      TensorShape raw_shape = {static_cast<int64>(batch_size)};
      Tensor* output_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(0, raw_shape, &output_tensor));
      auto output_flat = output_tensor->flat<string>();
      auto output_data = output_flat.data();

      twml_type type = TWML_TYPE_UNKNOWN;
      bool is_big_endian = false;

      std::vector<uint64> shape(1, batch_size);
      uint64 length = 0;

      for (auto record : records) {
        const twml::RawTensor tensor = record.getRawTensor(feature_id);
        const auto &curr_dims = tensor.getDims();
        const auto curr_type = tensor.getType();
        const bool curr_is_big_endian = tensor.is_big_endian();
        const uint64 curr_length = tensor.getRawLength();

        // Create the output tensor based on first tensor
        if (shape.size() == 1) {
          // Push the shape of individual tensors into shape
          shape.reserve(curr_dims.size() + 1);
          shape.insert(shape.end(), curr_dims.begin(), curr_dims.end());
          type = curr_type;
          is_big_endian = curr_is_big_endian;
          length = curr_length;

        } else {
          if (assert_shape) {
            // Assert shape of all tensors is the same.
            bool is_same_shape = std::equal(shape.begin() + 1, shape.end(), curr_dims.begin());

            if (!is_same_shape || length != curr_length) {
              throw std::runtime_error("TensorShape mismatch for feature_id: "
                                       + std::to_string(feature_id));
            }
          }

          // Assert type and endianness of all tensors is the same.
          if (type != curr_type || is_big_endian != curr_is_big_endian) {
            throw std::runtime_error("Tensor type mismatch for feature_id: "
                                     + std::to_string(feature_id));
          }
        }

        // Copy from datarecord to output
        const uint8 *tensor_data = reinterpret_cast<const uint8 *>(tensor.getData<void>());
        *output_data = std::string(tensor_data, tensor_data + curr_length);

        // Increment it for the next tensor in the batch.
        output_data++;
      }

      Tensor *shape_tensor = nullptr;
      TensorShape shape_shape = {static_cast<int64>(shape.size())};
      OP_REQUIRES_OK(context, context->allocate_output(1, shape_shape, &shape_tensor));
      auto shape_flat = shape_tensor->flat<int64>();
      for (int i = 0; i < static_cast<int>(shape.size()); i++) {
        shape_flat(i) = shape[i];
      }

      Tensor* type_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(2, {}, &type_tensor));
      type_tensor->scalar<string>()() = twml::getTypeName(type);

      Tensor* endian_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(3, {}, &endian_tensor));
      endian_tensor->scalar<uint8>()() = is_big_endian;
    } catch(const std::exception &err) {
      context->CtxFailureWithWarning(errors::InvalidArgument(err.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("GetTensorsFromDataRecord")
  .Device(DEVICE_CPU),
  GetTensorsOp<DataRecordResource>);

REGISTER_KERNEL_BUILDER(
  Name("GetTensorsFromHashedDataRecord")
  .Device(DEVICE_CPU),
  GetTensorsOp<HashedDataRecordResource>);

REGISTER_OP("GetTensorsWithMissingMaskFromDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_id: int")
.Attr("default_shape: list(int)")
.Attr("dtype_size: int")
.Input("data_record_handle: resource")
.Output("output: string")
.Output("out_type: string")
.Output("out_endian: uint8")
.Output("is_found: bool")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from the data record.

Attr
  assert_shape: Specifies if the shape needs to be same across the batch.
  feature_id: The hashed id of the feature name.
  default_shape: Expected shape of output tensor.
  dtype_size: expected size of each element.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  output: A 2D byte tensor representing the requested feature.
  out_type: A string tensor represnting the type.
  out_endian: Endianness of the bytes returned a tensor of size 1. 0: litte, 1: big.
  is_missing: A boolean tensor of length batch_size represnting if the tensor was found for an input.
)doc");

REGISTER_OP("GetTensorsWithMissingMaskFromHashedDataRecord")
.Attr("assert_shape: bool")
.Attr("feature_id: int")
.Attr("default_shape: list(int)")
.Attr("dtype_size: int")
.Input("hashed_data_record_handle: resource")
.Output("output: string")
.Output("out_type: string")
.Output("out_endian: uint8")
.Output("is_found: bool")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from the data record.

Attr
  assert_shape: Specifies if the shape needs to be same across the batch.
  feature_id: The hashed id of the feature name.
  default_shape: Expected shape of output tensor.
  dtype_size: expected size of each element.

Input
  hashed_data_record_handle: Resource handle to HashedDataRecord.

Outputs
  output: A 2D byte tensor representing the requested feature.
  out_type: A string tensor represnting the type.
  out_endian: Endianness of the bytes returned a tensor of size 1. 0: litte, 1: big.
  is_missing: A boolean tensor of length batch_size represnting if the tensor was found for an input.
)doc");

template<class Resource>
class GetTensorsWithMissingMaskOp : public OpKernel {
 private:
  bool assert_shape;
  int64 feature_id;
  int64 dtype_size;
  std::vector<int64> shape;

 public:
  explicit GetTensorsWithMissingMaskOp(OpKernelConstruction *context)
      : OpKernel(context), assert_shape(true) {
    OP_REQUIRES_OK(context, context->GetAttr("assert_shape", &assert_shape));
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
    OP_REQUIRES_OK(context, context->GetAttr("default_shape", &shape));
    OP_REQUIRES_OK(context, context->GetAttr("dtype_size", &dtype_size));
  }

  void Compute(OpKernelContext *context) override {
    auto handle = getHandle<Resource>(context, 0);
    uint64 batch_size = handle->records.size();
    const auto &records = handle->records;

    try {
      TensorShape raw_shape = {static_cast<int64>(batch_size)};
      Tensor* output_tensor = nullptr;
      Tensor* is_found_tensor = nullptr;

      OP_REQUIRES_OK(context, context->allocate_output(0, raw_shape, &output_tensor));
      OP_REQUIRES_OK(context, context->allocate_output(3, raw_shape, &is_found_tensor));

      auto output_flat = output_tensor->flat<string>();
      auto output_data = output_flat.data();
      auto is_found_data = is_found_tensor->flat<bool>().data();

      twml_type type = TWML_TYPE_UNKNOWN;
      bool is_big_endian = false;

      uint64 length = std::accumulate(shape.begin(), shape.end(), dtype_size, std::multiplies<int64>());
      for (auto record : records) {
        try {
          const twml::RawTensor tensor = record.getRawTensor(feature_id);
          const auto &curr_dims = tensor.getDims();
          const auto curr_type = tensor.getType();
          const bool curr_is_big_endian = tensor.is_big_endian();
          const uint64 curr_length = tensor.getRawLength();

          if (type == TWML_TYPE_UNKNOWN) {
            type = curr_type;
            is_big_endian = curr_is_big_endian;
            // FloatTensors are stored as a list of doubles.
            // If the requested dtype_size is 4, update the length.
            // NOTE: All the missing tensors before this have wrong length, this is fixed at the end.
            if (type == TWML_TYPE_DOUBLE && is_big_endian && dtype_size == 4) {
              length = length * 2;
            }
          } else {
            // Assert type and endianness of all tensors is the same.
            if (type != curr_type || is_big_endian != curr_is_big_endian) {
              throw std::runtime_error("Tensor type mismatch for feature_id: "
                                       + std::to_string(feature_id));
            }
          }

          // Assert shape of all tensors is the same.
          if (assert_shape && type != TWML_TYPE_UNKNOWN) {
            // Assert shape of all tensors is the same.
            bool is_same_shape = std::equal(shape.begin(), shape.end(), curr_dims.begin());

            if (!is_same_shape || length != curr_length) {
              throw std::runtime_error("TensorShape mismatch for feature_id: "
                                       + std::to_string(feature_id));
            }
          }

          // Copy from datarecord to output
          const uint8 *tensor_data = reinterpret_cast<const uint8 *>(tensor.getData<void>());
          *output_data = std::string(tensor_data, tensor_data + curr_length);
          *is_found_data = true;
        } catch(const std::out_of_range &err) {
          *output_data = std::string();
          output_data->resize(length);
          *is_found_data = false;
        }

        // Increment it for the next tensor in the batch.
        output_data++;
        is_found_data++;
      }

      // Reset pointers to the beginning
      output_data = output_flat.data();
      is_found_data = is_found_tensor->flat<bool>().data();

      // Resize any missing tensors before type (and hence true length) was known.
      if (type == TWML_TYPE_DOUBLE) {
        for (int64 i = 0; i < static_cast<int64>(records.size()); i++) {
          if (!is_found_data[i]) {
            output_data[i].resize(length);
          }
        }
      }

      Tensor* type_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(1, {}, &type_tensor));
      type_tensor->scalar<string>()() = twml::getTypeName(type);

      Tensor* endian_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(2, {}, &endian_tensor));
      endian_tensor->scalar<uint8>()() = is_big_endian;
    } catch(const std::exception &err) {
      context->CtxFailureWithWarning(errors::InvalidArgument(err.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("GetTensorsWithMissingMaskFromDataRecord")
  .Device(DEVICE_CPU),
  GetTensorsWithMissingMaskOp<DataRecordResource>);

REGISTER_KERNEL_BUILDER(
  Name("GetTensorsWithMissingMaskFromHashedDataRecord")
  .Device(DEVICE_CPU),
  GetTensorsWithMissingMaskOp<HashedDataRecordResource>);

REGISTER_OP("GetSparseTensorsFromDataRecord")
.Attr("feature_id: int")
.Input("data_record_handle: resource")
.Output("ids: int64")
.Output("indices: string")
.Output("values: string")
.Output("dense_shape: int64")
.Output("values_type: string")
.Output("valueendian: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from the data record.

Attr
  feature_id: The hashed id of the feature name.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  ids: A 1D tensor representing which input in the batch the value belongs to.
  indices: An string tensor containing indices of the sparse tensor as bytes.
  values: An string tensor containing values of the sparse tensor as bytes.
  dense_shape: A tensor containing [batch_size, thrift_shape].
  values_type: The data type of value tensor returned as a string tensor of size 1.
  values_endian: Endianness of the bytes returned a tensor of size 1. 0: litte, 1: big.
)doc");

REGISTER_OP("GetSparseTensorsFromHashedDataRecord")
.Attr("feature_id: int")
.Input("hashed_data_record_handle: resource")
.Output("ids: int64")
.Output("indices: string")
.Output("values: string")
.Output("dense_shape: int64")
.Output("values_type: string")
.Output("values_endian: uint8")
.SetShapeFn([](::tensorflow::shape_inference::InferenceContext* c) {
    return Status::OK();
  }).Doc(R"doc(
A tensorflow OP that decodes and returns tensors from the data record.

Attr
  feature_id: The hashed id of the feature name.

Input
  data_record_handle: Resource handle to DataRecord.

Outputs
  ids: A 1D tensor representing which input in the batch the value belongs to.
  indices: An string tensor containing indices of the sparse tensor as bytes.
  values: An string tensor containing values of the sparse tensor as bytes.
  dense_shape: A tensor containing [batch_size, thrift_shape].
  values_type: The data type of value tensor returned as a string tensor of size 1.
  values_endian: Endianness of the bytes returned a tensor of size 1. 0: litte, 1: big.
)doc");

template<typename Resource>
class GetSparseTensorsOp : public OpKernel {
 private:
  int64 feature_id;

 public:
  explicit GetSparseTensorsOp(OpKernelConstruction *context)
      : OpKernel(context) {
    OP_REQUIRES_OK(context, context->GetAttr("feature_id", &feature_id));
  }

  void Compute(OpKernelContext *context) override {
    auto handle = getHandle<Resource>(context, 0);
    const int64 batch_size = static_cast<int64>(handle->records.size());
    const auto &records = handle->records;

    try {
      twml_type type = TWML_TYPE_UNKNOWN;
      bool is_big_endian = false;

      std::vector<uint64> shape(1, batch_size);

      int64 total_length = 0;
      std::vector<int64> lengths;
      lengths.reserve(batch_size);

      int64 total_indices_length = 0;
      std::vector<int64> indices_raw_lengths;
      std::vector<const uint8 *> indices_data_ptrs;
      indices_raw_lengths.reserve(batch_size);
      indices_data_ptrs.reserve(batch_size);

      int64 total_values_length = 0;
      std::vector<int64> values_raw_lengths;
      std::vector<const uint8 *> values_data_ptrs;
      values_raw_lengths.reserve(batch_size);
      values_data_ptrs.reserve(batch_size);

      for (auto record : records) {
        const twml::RawSparseTensor sparse_tensor = record.getRawSparseTensor(feature_id);
        const twml::RawTensor indices = sparse_tensor.indices();
        const twml::RawTensor values = sparse_tensor.values();
        const auto &dense_shape = sparse_tensor.denseShape();
        const auto indices_type = indices.getType();
        const auto indices_is_big_endian = indices.is_big_endian();
        const auto values_type = values.getType();
        const bool values_is_big_endian = values.is_big_endian();

        const uint64 indices_length = indices.getDims().back();
        const uint64 values_length = values.getDims().back();

        auto indices_raw_length = indices.getRawLength();
        auto values_raw_length = values.getRawLength();

        auto indices_data_ptr = reinterpret_cast<const uint8 *>(indices.getData<void>());
        auto values_data_ptr = reinterpret_cast<const uint8 *>(values.getData<void>());

        indices_raw_lengths.push_back(indices_raw_length);
        values_raw_lengths.push_back(values_raw_length);

        indices_data_ptrs.push_back(indices_data_ptr);
        values_data_ptrs.push_back(values_data_ptr);

        total_indices_length += indices_raw_length;
        total_values_length += values_raw_length;

        if (shape.size() == 1) {
          shape.reserve(dense_shape.size() + 1);
          shape.insert(shape.end(), dense_shape.begin(), dense_shape.end());
          type = values_type;
          is_big_endian = values_is_big_endian;
        }

        // Assert shape of all tensors is the same.
        if (!std::equal(shape.begin() + 1, shape.end(), dense_shape.begin())) {
          throw std::runtime_error("dense_shape of sparse tensors doesn't match for feature_id: "
                                   + std::to_string(feature_id));
        }
        // Assert type of all values tensor is the same.
        if (type != values_type || is_big_endian != values_is_big_endian) {
          throw std::runtime_error("The type of values do not match for feature_id: "
                                   + std::to_string(feature_id));
        }
        // Assert indices tensor is big endian and of type INT64.
        if (indices_type != TWML_TYPE_INT64 || !indices_is_big_endian) {
          throw std::runtime_error("Unexpected type for index tensor for feature_id: "
                                   + std::to_string(feature_id));
        }

        if (indices_length != values_length) {
          throw std::runtime_error("The length of values and indices does not match for : "
                                   + std::to_string(feature_id));
        }

        lengths.push_back(indices_length);
        total_length += indices_length;
      }

      Tensor* ids_tensor = nullptr;
      TensorShape ids_shape = {static_cast<int64>(total_length)};
      OP_REQUIRES_OK(context, context->allocate_output(0, ids_shape, &ids_tensor));
      auto ids_tensor_flat = ids_tensor->flat<int64>();
      auto ids_tensor_data = ids_tensor_flat.data();

      TensorShape raw_shape = {static_cast<int64>(1)};

      Tensor* indices_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(1, raw_shape, &indices_tensor));
      auto indices_tensor_flat = indices_tensor->flat<string>();
      auto indices_tensor_string = indices_tensor_flat.data();
      indices_tensor_string->resize(total_indices_length);
      auto indices_tensor_iter = indices_tensor_string->begin();

      Tensor* values_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(2, raw_shape, &values_tensor));
      auto values_tensor_flat = values_tensor->flat<string>();
      auto values_tensor_string = values_tensor_flat.data();
      values_tensor_string->resize(total_values_length);
      auto values_tensor_iter = values_tensor_string->begin();

      for (int64 i = 0; i < batch_size; i++) {
        // Fill in the data for id == i for all values in the current input.
        std::fill(ids_tensor_data, ids_tensor_data + lengths[i], i);
        ids_tensor_data += lengths[i];

        indices_tensor_iter = std::copy(indices_data_ptrs[i],
                                        indices_data_ptrs[i] + indices_raw_lengths[i],
                                        indices_tensor_iter);

        values_tensor_iter = std::copy(values_data_ptrs[i],
                                        values_data_ptrs[i] + values_raw_lengths[i],
                                        values_tensor_iter);
      }

      Tensor *shape_tensor = nullptr;
      TensorShape shape_shape = {static_cast<int64>(shape.size())};
      OP_REQUIRES_OK(context, context->allocate_output(3, shape_shape, &shape_tensor));
      auto shape_flat = shape_tensor->flat<int64>();
      for (int i = 0; i < static_cast<int>(shape.size()); i++) {
        shape_flat(i) = shape[i];
      }

      Tensor* type_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(4, {}, &type_tensor));
      type_tensor->scalar<string>()() = twml::getTypeName(type);

      Tensor* endian_tensor = nullptr;
      OP_REQUIRES_OK(context, context->allocate_output(5, {}, &endian_tensor));
      endian_tensor->scalar<uint8>()() = is_big_endian;
    } catch(const std::exception &err) {
      context->CtxFailureWithWarning(errors::InvalidArgument(err.what()));
    }
  }
};

REGISTER_KERNEL_BUILDER(
  Name("GetSparseTensorsFromDataRecord")
  .Device(DEVICE_CPU),
  GetSparseTensorsOp<DataRecordResource>);

REGISTER_KERNEL_BUILDER(
  Name("GetSparseTensorsFromHashedDataRecord")
  .Device(DEVICE_CPU),
  GetSparseTensorsOp<HashedDataRecordResource>);
