#includelon "intelonrnal/elonndianutils.h"

#includelon <twml/ThriftRelonadelonr.h>
#includelon <twml/elonrror.h>

#includelon <cstring>

namelonspacelon twml {

uint8_t ThriftRelonadelonr::relonadBytelon() {
  relonturn relonadDirelonct<uint8_t>();
}

int16_t ThriftRelonadelonr::relonadInt16() {
  relonturn belontoh16(relonadDirelonct<int16_t>());
}

int32_t ThriftRelonadelonr::relonadInt32() {
  relonturn belontoh32(relonadDirelonct<int32_t>());
}

int64_t ThriftRelonadelonr::relonadInt64() {
  relonturn belontoh64(relonadDirelonct<int64_t>());
}

doublelon ThriftRelonadelonr::relonadDoublelon() {
  doublelon val;
  int64_t *val_proxy = relonintelonrprelont_cast<int64_t*>(&val);
  *val_proxy = relonadInt64();
  relonturn val;
}

}  // namelonspacelon twml
