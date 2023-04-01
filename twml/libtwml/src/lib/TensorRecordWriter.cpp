#include "internal/error.h"
#include "internal/thrift.h"

#include <map>
#include <twml/ThriftWriter.h>
#include <twml/TensorRecordWriter.h>
#include <twml/io/IOError.h>

using namespace twml::io;

namespace twml {

static int32_t getRawThriftType(twml_type dtype) {
  // convert twml enum to tensor.thrift enum
  switch (dtype) {
    case TWML_TYPE_FLOAT:
      return DATA_TYPE_FLOAT;
    case TWML_TYPE_DOUBLE:
      return DATA_TYPE_DOUBLE;
    case TWML_TYPE_INT64:
      return DATA_TYPE_INT64;
    case TWML_TYPE_INT32:
      return DATA_TYPE_INT32;
    case TWML_TYPE_UINT8:
      return DATA_TYPE_UINT8;
    case TWML_TYPE_STRING:
      return DATA_TYPE_STRING;
    case TWML_TYPE_BOOL:
      return DATA_TYPE_BOOL;
    default:
      throw IOError(IOError::UNSUPPORTED_OUTPUT_TYPE);
  }
}

void TensorRecordWriter::writeTensor(const RawTensor &tensor) {
  if (tensor.getType() == TWML_TYPE_INT32) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_INT32);
    m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 1);
    m_thrift_writer.writeListHeader(TTYPE_I32, tensor.getNumElements());

    const int32_t *data = tensor.getData<int32_t>();

    for (uint64_t i = 0; i < tensor.getNumElements(); i++)
      m_thrift_writer.writeInt32(data[i]);

  } else if (tensor.getType() == TWML_TYPE_INT64) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_INT64);
    m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 1);
    m_thrift_writer.writeListHeader(TTYPE_I64, tensor.getNumElements());

    const int64_t *data = tensor.getData<int64_t>();

    for (uint64_t i = 0; i < tensor.getNumElements(); i++)
      m_thrift_writer.writeInt64(data[i]);

  } else if (tensor.getType() == TWML_TYPE_FLOAT) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_FLOAT);
    m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 1);
    m_thrift_writer.writeListHeader(TTYPE_DOUBLE, tensor.getNumElements());

    const float *data = tensor.getData<float>();

    for (uint64_t i = 0; i < tensor.getNumElements(); i++)
      m_thrift_writer.writeDouble(static_cast<double>(data[i]));

  } else if (tensor.getType() == TWML_TYPE_DOUBLE) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_DOUBLE);
    m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 1);
    m_thrift_writer.writeListHeader(TTYPE_DOUBLE, tensor.getNumElements());

    const double *data = tensor.getData<double>();

    for (uint64_t i = 0; i < tensor.getNumElements(); i++)
      m_thrift_writer.writeDouble(data[i]);

  } else if (tensor.getType() == TWML_TYPE_STRING) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_STRING);
    m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 1);
    m_thrift_writer.writeListHeader(TTYPE_STRING, tensor.getNumElements());

    const std::string *data = tensor.getData<std::string>();

    for (uint64_t i = 0; i < tensor.getNumElements(); i++)
      m_thrift_writer.writeString(data[i]);

  } else if (tensor.getType() == TWML_TYPE_BOOL) {
    m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_BOOL);
    m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 1);
    m_thrift_writer.writeListHeader(TTYPE_BOOL, tensor.getNumElements());

    const bool *data = tensor.getData<bool>();

    for (uint64_t i = 0; i < tensor.getNumElements(); i++)
      m_thrift_writer.writeBool(data[i]);

  } else {
    throw IOError(IOError::UNSUPPORTED_OUTPUT_TYPE);
  }

  // write tensor shape field
  m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 2);
  m_thrift_writer.writeListHeader(TTYPE_I64, tensor.getNumDims());

  for (uint64_t i = 0; i < tensor.getNumDims(); i++)
    m_thrift_writer.writeInt64(tensor.getDim(i));

  m_thrift_writer.writeStructStop();
  m_thrift_writer.writeStructStop();
}

void TensorRecordWriter::writeRawTensor(const RawTensor &tensor) {
  m_thrift_writer.writeStructFieldHeader(TTYPE_STRUCT, GT_RAW);

  // dataType field
  m_thrift_writer.writeStructFieldHeader(TTYPE_I32, 1);
  m_thrift_writer.writeInt32(getRawThriftType(tensor.getType()));

  // content field
  uint64_t type_size = getSizeOf(tensor.getType());
  m_thrift_writer.writeStructFieldHeader(TTYPE_STRING, 2);
  const uint8_t *data = reinterpret_cast<const uint8_t *>(tensor.getData<void>());
  m_thrift_writer.writeBinary(data, tensor.getNumElements() * type_size);

  // shape field
  m_thrift_writer.writeStructFieldHeader(TTYPE_LIST, 3);
  m_thrift_writer.writeListHeader(TTYPE_I64, tensor.getNumDims());

  for (uint64_t i = 0; i < tensor.getNumDims(); i++)
    m_thrift_writer.writeInt64(tensor.getDim(i));

  m_thrift_writer.writeStructStop();
  m_thrift_writer.writeStructStop();
}

TWMLAPI uint32_t TensorRecordWriter::getRecordsWritten() {
  return m_records_written;
}

// Caller (usually DataRecordWriter) must precede with struct header field
// like thrift_writer.writeStructFieldHeader(TTYPE_MAP, DR_GENERAL_TENSOR)
TWMLAPI uint64_t TensorRecordWriter::write(twml::TensorRecord &record) {
  uint64_t bytes_written_before = m_thrift_writer.getBytesWritten();

  m_thrift_writer.writeMapHeader(TTYPE_I64, TTYPE_STRUCT, record.getRawTensors().size());

  for (auto id_tensor_pairs : record.getRawTensors()) {
    m_thrift_writer.writeInt64(id_tensor_pairs.first);

    // all tensors written as RawTensor Thrift except for StringTensors
    // this avoids the overhead of converting little endian to big endian
    if (id_tensor_pairs.second.getType() == TWML_TYPE_STRING)
      writeTensor(id_tensor_pairs.second);
    else
      writeRawTensor(id_tensor_pairs.second);
  }

  m_records_written++;

  return m_thrift_writer.getBytesWritten() - bytes_written_before;
}

}  // namespace twml
