#pragma once

#ifdef __cplusplus

#include <twml/defines.h>
#include <cstdint>
#include <cstddef>
#include <cstring>

namespace twml {

class ThriftReader {
 protected:
  const uint8_t *m_buffer;

 public:

  ThriftReader(const uint8_t *buffer): m_buffer(buffer) {}

  const uint8_t *getBuffer() { return m_buffer; }

  void setBuffer(const uint8_t *buffer) { m_buffer = buffer; }

  template<typename T> T readDirect() {
    T val;
    memcpy(&val, m_buffer, sizeof(T));
    m_buffer += sizeof(T);
    return val;
  }

  template<typename T> void skip() {
    m_buffer += sizeof(T);
  }

  void skipLength(size_t length) {
    m_buffer += length;
  }

  uint8_t readByte();
  int16_t readInt16();
  int32_t readInt32();
  int64_t readInt64();
  double readDouble();

  template<typename T> inline
  int32_t getRawBuffer(const uint8_t **begin) {
    int32_t length = readInt32();
    *begin = m_buffer;
    skipLength(length * sizeof(T));
    return length;
  }

};

}
#endif
