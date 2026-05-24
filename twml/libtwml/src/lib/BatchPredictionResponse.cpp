#include "internal/endianutils.h"
#include "internal/error.h"
#include "internal/thrift.h"

#include <twml/Tensor.h>
#include <twml/BatchPredictionResponse.h>
#include <twml/DataRecord.h>
#include <twml/ThriftWriter.h>
#include <twml/DataRecordWriter.h>

#include <inttypes.h>
#include <stdint.h>
#include <unistd.h>
#include <string.h>

#include <algorithm>

// When the number of predictions is very high, as some cases that Ads wants, the generic thrift
// encoder becomes super expensive because we have to deal with lua tables.
// This function is a special operation to efficiently write a batch prediction responses based on
// tensors.
namespace twml {

BatchPredictionResponse::BatchPredictionResponse(
  const Tensor &keys, const Tensor &values,
  const Tensor &dense_keys, const std::vector<RawTensor> &dense_values
) : keys_(keys), values_(values), dense_keys_(dense_keys), dense_values_(dense_values) {
  // determine batch size
  if (values_.getNumDims() > 0) {
    batch_size_ = values_.getDim(0);
  } else if (dense_keys_.getNumElements() < 1) {
    throw twml::Error(TWML_ERR_TYPE, "Continuous values and dense tensors are both empty");
  } else if (dense_keys_.getNumElements() != dense_values_.size()) {
    throw twml::Error(TWML_ERR_TYPE, "Number of tensors not equal to number of keys");
  } else {
    // dim 0 for each tensor indexes batch elements
    std::vector<uint64_t> batch_sizes;
    batch_sizes.reserve(dense_values_.size());

    for (int i = 0; i < dense_values_.size(); i++)
      batch_sizes.push_back(dense_values_.at(i).getDim(0));

    if (std::adjacent_find(
          batch_sizes.begin(),
          batch_sizes.end(),
          std::not_equal_to<uint64_t>()) != batch_sizes.end())
      throw twml::Error(TWML_ERR_TYPE, "Batch size (dim 0) for all tensors must be the same");

    batch_size_ = dense_values.at(0).getDim(0);
  }
}

void BatchPredictionResponse::encode(twml::ThriftWriter &thrift_writer) {
  if (hasContinuous()) {
    switch (values_.getType()) {
      case TWML_TYPE_FLOAT:
        serializePredictions<float>(thrift_writer);
        break;
      case TWML_TYPE_DOUBLE:
        serializePredictions<double>(thrift_writer);
        break;
      default:
        throw twml::Error(TWML_ERR_TYPE, "Predictions must be float or double.");
    }
  } else {
    // dense tensor predictions
    serializePredictions<double>(thrift_writer);
  }
}

template <typename T>
void BatchPredictionResponse::serializePredictions(twml::ThriftWriter &thrift_writer) {
  twml::DataRecordWriter record_writer = twml::DataRecordWriter(thrift_writer);

  // start BatchPredictionResponse
  thrift_writer.writeStructFieldHeader(TTYPE_LIST, BPR_PREDICTIONS);
  thrift_writer.writeListHeader(TTYPE_STRUCT, getBatchSize());

  for (int i = 0; i < getBatchSize(); i++) {
    twml::DataRecord record = twml::DataRecord();

    if (hasContinuous()) {
      const T *values = values_.getData<T>();
      const int64_t *local_keys = keys_.getData<int64_t>();
      const T *local_values = values + (i * getPredictionSize());
      record.addContinuous(local_keys, getPredictionSize(), local_values);
    }

    if (hasDenseTensors()) {
      const int64_t *local_dense_keys = dense_keys_.getData<int64_t>();

      for (int j = 0; j < dense_keys_.getNumElements(); j++) {
        const RawTensor &dense_value = dense_values_.at(j).getSlice(i);
        record.addRawTensor(local_dense_keys[j], dense_value);
      }
    }

    record_writer.write(record);
  }

  // end BatchPredictionResponse
  thrift_writer.writeStructStop();
}

// calculate expected binary Thrift size (no memory is copied)
uint64_t BatchPredictionResponse::encodedSize() {
  bool dry_mode = true;
  twml::ThriftWriter dry_writer = twml::ThriftWriter(nullptr, 0, dry_mode);
  encode(dry_writer);
  return dry_writer.getBytesWritten();
}

void BatchPredictionResponse::write(Tensor &result) {
  size_t result_size = result.getNumElements();
  uint8_t *result_data = result.getData<uint8_t>();

  if (result_size != this->encodedSize()) {
    throw twml::Error(TWML_ERR_SIZE, "Sizes do not match");
  }

  twml::ThriftWriter writer = twml::ThriftWriter(result_data, result_size);
  encode(writer);
}

}  // namespace twml
