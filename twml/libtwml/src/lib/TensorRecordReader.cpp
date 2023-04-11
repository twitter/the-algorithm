#include "internal/thrift.h"
#include "internal/error.h"
#include <string>

#include <twml/TensorRecordReader.h>
#include <twml/RawTensor.h>

namespace twml {

template<typename T> struct TensorTraits;

#define INSTANTIATE(TYPE, THRIFT_TYPE, TWML_TYPE)   \
  template<> struct TensorTraits<TYPE> {            \
    static const TTYPES ThriftType = THRIFT_TYPE;   \
    static const twml_type TwmlType = TWML_TYPE;    \
  };                                                \

INSTANTIATE(int64_t, TTYPE_I64, TWML_TYPE_INT64)
INSTANTIATE(int32_t, TTYPE_I32, TWML_TYPE_INT32)
INSTANTIATE(double, TTYPE_DOUBLE, TWML_TYPE_DOUBLE)
INSTANTIATE(bool, TTYPE_BOOL, TWML_TYPE_BOOL)

static
std::vector<uint64_t> calcStrides(const std::vector<uint64_t> &shape) {
  int ndims = static_cast<int>(shape.size());
  std::vector<uint64_t> strides(ndims);
  uint64_t stride = 1;
  for (int i = ndims-1; i >= 0; i--) {
    strides[i] = stride;
    stride *= shape[i];
  }
  return strides;
}

static twml_type getTwmlType(int dtype) {
  // Convert tensor.thrift enum to twml enum
  switch (dtype) {
    case DATA_TYPE_FLOAT:
      return TWML_TYPE_FLOAT;
    case DATA_TYPE_DOUBLE:
      return TWML_TYPE_DOUBLE;
    case DATA_TYPE_INT64:
      return TWML_TYPE_INT64;
    case DATA_TYPE_INT32:
      return TWML_TYPE_INT32;
    case DATA_TYPE_UINT8:
      return TWML_TYPE_UINT8;
    case DATA_TYPE_STRING:
      return TWML_TYPE_STRING;
    case DATA_TYPE_BOOL:
      return TWML_TYPE_BOOL;
  }
  return TWML_TYPE_UNKNOWN;
}

std::vector<uint64_t> TensorRecordReader::readShape() {
  int32_t length = readInt32();

  std::vector<uint64_t> shape;
  shape.reserve(length);
  for (int32_t i = 0; i < length; i++) {
    shape.push_back(static_cast<uint64_t>(readInt64()));
  }

  return shape;
}

template<typename T>
RawTensor TensorRecordReader::readTypedTensor() {
  std::vector<uint64_t> shape;
  int32_t length = 0;
  const uint8_t *data = nullptr;
  uint64_t raw_length = 0;
  uint8_t field_type = TTYPE_STOP;

  while ((field_type = readByte()) != TTYPE_STOP) {
    int16_t field_id = readInt16();
    switch (field_id) {
      case 1:
        CHECK_THRIFT_TYPE(field_type, TTYPE_LIST, "data");
        CHECK_THRIFT_TYPE(readByte(), TensorTraits<T>::ThriftType, "data_type");
        length = getRawBuffer<T>(&data);
        raw_length = length * sizeof(T);
        break;
      case 2:
        CHECK_THRIFT_TYPE(field_type, TTYPE_LIST, "shape");
        CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "shape_type");
        shape = readShape();
        break;
      default:
        throw ThriftInvalidField(field_id, "TensorRecordReader::readTypedTensor");
    }
  }

  // data is required
  if (data == nullptr) {
    throw twml::Error(TWML_ERR_THRIFT, "data field not found for TypedTensor");
  }

  // shape is optional
  if (shape.size() == 0) {
    shape.push_back((uint64_t)length);
  }

  // TODO: Try avoiding stride calculation
  std::vector<uint64_t> strides = calcStrides(shape);
  // FIXME: Try to use const void * in Tensors.
  return RawTensor(const_cast<void *>(static_cast<const void *>(data)),
                   shape, strides, (twml_type)TensorTraits<T>::TwmlType, true, raw_length);
}

RawTensor TensorRecordReader::readRawTypedTensor() {
  std::vector<uint64_t> shape;
  const uint8_t *data = nullptr;
  twml_type type = TWML_TYPE_UNKNOWN;
  uint64_t raw_length = 0;
  uint8_t field_type = TTYPE_STOP;

  while ((field_type = readByte()) != TTYPE_STOP) {
    int16_t field_id = readInt16();
    switch (field_id) {
      case 1:
        CHECK_THRIFT_TYPE(field_type, TTYPE_I32, "DataType");
        type = getTwmlType(readInt32());
        break;
      case 2:
        CHECK_THRIFT_TYPE(field_type, TTYPE_STRING, "content");
        raw_length = getRawBuffer<uint8_t>(&data);
        break;
      case 3:
        CHECK_THRIFT_TYPE(field_type, TTYPE_LIST, "shape");
        CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "shape_type");
        shape = readShape();
        break;
      default:
        throw ThriftInvalidField(field_id, "TensorRecordReader::readRawTypedTensor");
    }
  }

  // data type is required
  if (type == TWML_TYPE_UNKNOWN) {
    throw twml::Error(TWML_ERR_THRIFT, "DataType is a required field for RawTypedTensor");
  }

  // data is required
  if (data == nullptr) {
    throw twml::Error(TWML_ERR_THRIFT, "content is a required field for RawTypedTensor");
  }

  // shape is optional in the thrift file, but it is really required for string types.
  if (shape.size() == 0) {
    if (type == TWML_TYPE_STRING) {
      throw twml::Error(TWML_ERR_THRIFT, "shape required for string types in RawTypedTensor");
    }
    shape.push_back((uint64_t)(raw_length / getSizeOf(type)));
  }

  // TODO: Try avoiding stride calculation
  std::vector<uint64_t> strides = calcStrides(shape);
  // FIXME: Try to use const void * data inside Tensors.
  return RawTensor(const_cast<void *>(static_cast<const void *>(data)),
                   shape, strides, type, false, raw_length);
}

RawTensor TensorRecordReader::readStringTensor() {
  std::vector<uint64_t> shape;
  int32_t length = 0;
  const uint8_t *data = nullptr;
  uint64_t raw_length = 0;
  uint8_t field_type = TTYPE_STOP;
  const uint8_t *dummy = nullptr;

  while ((field_type = readByte()) != TTYPE_STOP) {
    int16_t field_id = readInt16();
    switch (field_id) {
      case 1:
        CHECK_THRIFT_TYPE(field_type, TTYPE_LIST, "data");
        CHECK_THRIFT_TYPE(readByte(), TTYPE_STRING, "data_type");
        length = readInt32();
        // Store the current location of the byte stream.
        // Use this at to "deocde strings" at a later point.
        data = getBuffer();
        for (int32_t i = 0; i < length; i++) {
          // Skip reading the strings
          getRawBuffer<uint8_t>(&dummy);
        }
        raw_length = length;
        break;
      case 2:
        CHECK_THRIFT_TYPE(field_type, TTYPE_LIST, "shape");
        CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "shape_type");
        shape = readShape();
        break;
      default:
        throw ThriftInvalidField(field_id, "TensorRecordReader::readTypedTensor");
    }
  }

  // data is required
  if (data == nullptr) {
    throw twml::Error(TWML_ERR_THRIFT, "data field not found for TypedTensor");
  }

  // shape is optional
  if (shape.size() == 0) {
    shape.push_back((uint64_t)length);
  }

  // TODO: Try avoiding stride calculation
  std::vector<uint64_t> strides = calcStrides(shape);
  // FIXME: Try to use const void * in Tensors.
  return RawTensor(const_cast<void *>(static_cast<const void *>(data)),
                   shape, strides, TWML_TYPE_UINT8, false, raw_length);
}

RawTensor TensorRecordReader::readGeneralTensor() {
  // No loop is required because GeneralTensor is union. It is going to contain one field only.
  // All the fields are structs
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRUCT, "type");
  int16_t field_id = readInt16();
  RawTensor output;

  switch (field_id) {
    case GT_RAW:
      output = readRawTypedTensor();
      break;
    case GT_STRING:
      output = readStringTensor();
      break;
    case GT_INT32:
      output = readTypedTensor<int32_t>();
      break;
    case GT_INT64:
      output = readTypedTensor<int64_t>();
      break;
    case GT_FLOAT:
    case GT_DOUBLE:
      // Store both FloatTensor and DoubleTensor as double tensor as both are list of doubles.
      output = readTypedTensor<double>();
      break;
    case GT_BOOL:
      output = readTypedTensor<bool>();
      break;
    default:
      throw ThriftInvalidField(field_id, "TensorRecordReader::readGeneralTensor()");
  }

  CHECK_THRIFT_TYPE(readByte(), TTYPE_STOP, "stop");
  return output;
}

RawSparseTensor TensorRecordReader::readCOOSparseTensor() {
  std::vector<uint64_t> shape;
  uint8_t field_type = TTYPE_STOP;
  RawTensor indices, values;

  while ((field_type = readByte()) != TTYPE_STOP) {
    int16_t field_id = readInt16();
    switch (field_id) {
      case 1:
        CHECK_THRIFT_TYPE(field_type, TTYPE_LIST, "shape");
        CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "shape_type");
        shape = readShape();
        break;
      case 2:
        indices = readTypedTensor<int64_t>();
        break;
      case 3:
        values = readGeneralTensor();
        break;
      default:
        throw twml::Error(TWML_ERR_THRIFT, "Invalid field when deocidng COOSparseTensor");
    }
  }

  return RawSparseTensor(indices, values, shape);
}

void TensorRecordReader::readTensor(const int feature_type, TensorRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRUCT, "value_type");

  int32_t length = readInt32();
  for (int32_t i = 0; i < length; i++) {
    int64_t id = readInt64();
    record->m_tensors.emplace(id, readGeneralTensor());
  }
}

void TensorRecordReader::readSparseTensor(const int feature_type, TensorRecord *record) {
  CHECK_THRIFT_TYPE(feature_type, TTYPE_MAP, "type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_I64, "key_type");
  CHECK_THRIFT_TYPE(readByte(), TTYPE_STRUCT, "value_type");

  int32_t length = readInt32();
  for (int32_t i = 0; i < length; i++) {
    int64_t id = readInt64();

    // No loop is required because SparseTensor is union. It is going to contain one field only.
    // All the fields are structs
    CHECK_THRIFT_TYPE(readByte(), TTYPE_STRUCT, "field");
    int16_t field_id = readInt16();
    RawSparseTensor output;

    // Only COOSparsetensor is supported.
    switch (field_id) {
      case SP_COO:
        output = readCOOSparseTensor();
        break;
      default:
        throw ThriftInvalidField(field_id, "TensorRecordReader::readSparseTensor()");
    }

    // Read the last byte of the struct.
    CHECK_THRIFT_TYPE(readByte(), TTYPE_STOP, "stop");

    // Add to the map.
    record->m_sparse_tensors.emplace(id, output);
  }
}

}  // namespace twml
