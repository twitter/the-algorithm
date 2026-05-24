#pragma once

#include <twml/Tensor.h>
#include <twml/RawTensor.h>
#include <twml/ThriftWriter.h>

namespace twml {

    // Encodes a batch of model predictions as a list of Thrift DataRecord
    // objects inside a Thrift BatchPredictionResponse object. Prediction
    // values are continousFeatures inside each DataRecord.
    //
    // The BatchPredictionResponseWriter TensorFlow operator uses this class
    // to determine the size of the output tensor to allocate. The operator
    // then allocates memory for the output tensor and uses this class to
    // write binary Thrift to the output tensor.
    //
    class BatchPredictionResponse {
    private:
      uint64_t batch_size_;
      const Tensor &keys_;
      const Tensor &values_;  // prediction values (batch_size * num_keys)
      const Tensor &dense_keys_;
      const std::vector<RawTensor> &dense_values_;

      inline uint64_t getBatchSize() { return batch_size_; }
      inline bool hasContinuous() { return keys_.getNumDims() > 0; }
      inline bool hasDenseTensors() { return dense_keys_.getNumDims() > 0; }

      inline uint64_t getPredictionSize() {
        return values_.getNumDims() > 1 ? values_.getDim(1) : 1;
      };

      void encode(twml::ThriftWriter &thrift_writer);

      template <typename T>
      void serializePredictions(twml::ThriftWriter &thrift_writer);

    public:
      // keys:         'continuousFeatures' prediction keys
      // values:       'continuousFeatures' prediction values (batch_size * num_keys)
      // dense_keys:   'tensors' prediction keys
      // dense_values: 'tensors' prediction values (batch_size * num_keys)
      BatchPredictionResponse(
        const Tensor &keys, const Tensor &values,
        const Tensor &dense_keys, const std::vector<RawTensor> &dense_values);

      // Calculate the size of the Thrift encoded output (but do not encode).
      // The BatchPredictionResponseWriter TensorFlow operator uses this value
      // to allocate the output tensor.
      uint64_t encodedSize();

      // Write the BatchPredictionResponse as binary Thrift. The
      // BatchPredictionResponseWriter operator uses this method to populate
      // the output tensor.
      void write(Tensor &result);
    };
}
