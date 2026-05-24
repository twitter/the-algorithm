#include "internal/thrift.h"
#include "internal/error.h"

#include <twml/DataRecordReader.h>
#include <twml/HashedDataRecordReader.h>
#include <twml/BatchPredictionRequest.h>
#include <twml/Error.h>

#include <algorithm>
#include <cstring>
#include <cstdint>

namespace twml {

template<typename RecordType>
void GenericBatchPredictionRequest<RecordType>::decode(Reader &reader) {
  uint8_t feature_type = reader.readByte();
  while (feature_type != TTYPE_STOP) {
    int16_t field_id = reader.readInt16();

    switch (field_id) {
      case 1: {
        CHECK_THRIFT_TYPE(feature_type, TTYPE_LIST, "list");
        CHECK_THRIFT_TYPE(reader.readByte(), TTYPE_STRUCT, "list_element");

        int32_t length = reader.readInt32();
        m_requests.resize(length, RecordType(this->num_labels, this->num_weights));
        for (auto &request : m_requests) {
          request.decode(reader);
        }

        break;
      }
      case 2: {
        CHECK_THRIFT_TYPE(feature_type, TTYPE_STRUCT, "commonFeatures");
        m_common_features.decode(reader);
        break;
      }
      default: throw ThriftInvalidField(field_id, __func__);
    }

    feature_type = reader.readByte();
  }
  return;
}


// Instantiate decoders.
template void GenericBatchPredictionRequest<HashedDataRecord>::decode(HashedDataRecordReader &reader);
template void GenericBatchPredictionRequest<DataRecord>::decode(DataRecordReader &reader);

}  // namespace twml
