#pragma once

# nclude <twml/Tensor.h>
# nclude <twml/RawTensor.h>
# nclude <twml/Thr ftWr er.h>

na space twml {

    // Encodes a batch of model pred ct ons as a l st of Thr ft DataRecord
    // objects  ns de a Thr ft BatchPred ct onResponse object. Pred ct on
    // values are cont nousFeatures  ns de each DataRecord.
    //
    // T  BatchPred ct onResponseWr er TensorFlow operator uses t  class
    // to determ ne t  s ze of t  output tensor to allocate. T  operator
    // t n allocates  mory for t  output tensor and uses t  class to
    // wr e b nary Thr ft to t  output tensor.
    //
    class BatchPred ct onResponse {
    pr vate:
      u nt64_t batch_s ze_;
      const Tensor &keys_;
      const Tensor &values_;  // pred ct on values (batch_s ze * num_keys)
      const Tensor &dense_keys_;
      const std::vector<RawTensor> &dense_values_;

       nl ne u nt64_t getBatchS ze() { return batch_s ze_; }
       nl ne bool hasCont nuous() { return keys_.getNumD ms() > 0; }
       nl ne bool hasDenseTensors() { return dense_keys_.getNumD ms() > 0; }

       nl ne u nt64_t getPred ct onS ze() {
        return values_.getNumD ms() > 1 ? values_.getD m(1) : 1;
      };

      vo d encode(twml::Thr ftWr er &thr ft_wr er);

      template <typena  T>
      vo d ser al zePred ct ons(twml::Thr ftWr er &thr ft_wr er);

    publ c:
      // keys:         'cont nuousFeatures' pred ct on keys
      // values:       'cont nuousFeatures' pred ct on values (batch_s ze * num_keys)
      // dense_keys:   'tensors' pred ct on keys
      // dense_values: 'tensors' pred ct on values (batch_s ze * num_keys)
      BatchPred ct onResponse(
        const Tensor &keys, const Tensor &values,
        const Tensor &dense_keys, const std::vector<RawTensor> &dense_values);

      // Calculate t  s ze of t  Thr ft encoded output (but do not encode).
      // T  BatchPred ct onResponseWr er TensorFlow operator uses t  value
      // to allocate t  output tensor.
      u nt64_t encodedS ze();

      // Wr e t  BatchPred ct onResponse as b nary Thr ft. T 
      // BatchPred ct onResponseWr er operator uses t   thod to populate
      // t  output tensor.
      vo d wr e(Tensor &result);
    };
}
