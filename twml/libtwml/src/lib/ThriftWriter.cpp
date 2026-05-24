#include "internal/endianutils.h"
#include "internal/error.h"
#include "internal/thrift.h"

#include <twml/ThriftWriter.h>
#include <twml/Error.h>
#include <twml/io/IOError.h>

#include <cstring>

using namespace twml::io;

namespace twml {

template <typename T> inline
uint64_t ThriftWriter::write(T val) {
  if (!m_dry_run) {
    if (m_bytes_written + sizeof(T) > m_buffer_size)
      throw IOError(IOError::DESTINATION_LARGER_THAN_CAPACITY);
    memcpy(m_buffer, &val, sizeof(T));
    m_buffer += sizeof(T);
  }
  m_bytes_written += sizeof(T);
  return sizeof(T);
}

TWMLAPI uint64_t ThriftWriter::getBytesWritten() {
  return m_bytes_written;
}

TWMLAPI uint64_t ThriftWriter::writeStructFieldHeader(int8_t field_type, int16_t field_id) {
  return writeInt8(field_type) + writeInt16(field_id);
}

TWMLAPI uint64_t ThriftWriter::writeStructStop() {
  return writeInt8(static_cast<int8_t>(TTYPE_STOP));
}

TWMLAPI uint64_t ThriftWriter::writeListHeader(int8_t element_type, int32_t num_elems) {
  return writeInt8(element_type) + writeInt32(num_elems);
}

TWMLAPI uint64_t ThriftWriter::writeMapHeader(int8_t key_type, int8_t val_type, int32_t num_elems) {
  return writeInt8(key_type) + writeInt8(val_type) + writeInt32(num_elems);
}

TWMLAPI uint64_t ThriftWriter::writeDouble(double val) {
  int64_t bin_value;
  memcpy(&bin_value, &val, sizeof(int64_t));
  return writeInt64(bin_value);
}

TWMLAPI uint64_t ThriftWriter::writeInt8(int8_t val) {
  return write(val);
}

TWMLAPI uint64_t ThriftWriter::writeInt16(int16_t val) {
  return write(betoh16(val));
}

TWMLAPI uint64_t ThriftWriter::writeInt32(int32_t val) {
  return write(betoh32(val));
}

TWMLAPI uint64_t ThriftWriter::writeInt64(int64_t val) {
  return write(betoh64(val));
}

TWMLAPI uint64_t ThriftWriter::writeBinary(const uint8_t *bytes, int32_t num_bytes) {
  writeInt32(num_bytes);

  if (!m_dry_run) {
    if (m_bytes_written + num_bytes > m_buffer_size)
      throw IOError(IOError::DESTINATION_LARGER_THAN_CAPACITY);
    memcpy(m_buffer, bytes, num_bytes);
    m_buffer += num_bytes;
  }
  m_bytes_written += num_bytes;

  return 4 + num_bytes;
}

TWMLAPI uint64_t ThriftWriter::writeString(std::string str) {
  return writeBinary(reinterpret_cast<const uint8_t *>(str.data()), str.length());
}

TWMLAPI uint64_t ThriftWriter::writeBool(bool val) {
  return write(val);
}

}  // namespace twml
