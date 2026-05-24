#include "internal/endianutils.h"

#include <twml/ThriftReader.h>
#include <twml/Error.h>

#include <cstring>

namespace twml {

uint8_t ThriftReader::readByte() {
  return readDirect<uint8_t>();
}

int16_t ThriftReader::readInt16() {
  return betoh16(readDirect<int16_t>());
}

int32_t ThriftReader::readInt32() {
  return betoh32(readDirect<int32_t>());
}

int64_t ThriftReader::readInt64() {
  return betoh64(readDirect<int64_t>());
}

double ThriftReader::readDouble() {
  double val;
  int64_t *val_proxy = reinterpret_cast<int64_t*>(&val);
  *val_proxy = readInt64();
  return val;
}

}  // namespace twml
