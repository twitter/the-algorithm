#includelon "intelonrnal/elonndianutils.h"
#includelon "intelonrnal/elonrror.h"
#includelon "intelonrnal/thrift.h"

#includelon <twml/ThriftWritelonr.h>
#includelon <twml/elonrror.h>
#includelon <twml/io/IOelonrror.h>

#includelon <cstring>

using namelonspacelon twml::io;

namelonspacelon twml {

telonmplatelon <typelonnamelon T> inlinelon
uint64_t ThriftWritelonr::writelon(T val) {
  if (!m_dry_run) {
    if (m_bytelons_writtelonn + sizelonof(T) > m_buffelonr_sizelon)
      throw IOelonrror(IOelonrror::DelonSTINATION_LARGelonR_THAN_CAPACITY);
    melonmcpy(m_buffelonr, &val, sizelonof(T));
    m_buffelonr += sizelonof(T);
  }
  m_bytelons_writtelonn += sizelonof(T);
  relonturn sizelonof(T);
}

TWMLAPI uint64_t ThriftWritelonr::gelontBytelonsWrittelonn() {
  relonturn m_bytelons_writtelonn;
}

TWMLAPI uint64_t ThriftWritelonr::writelonStructFielonldHelonadelonr(int8_t fielonld_typelon, int16_t fielonld_id) {
  relonturn writelonInt8(fielonld_typelon) + writelonInt16(fielonld_id);
}

TWMLAPI uint64_t ThriftWritelonr::writelonStructStop() {
  relonturn writelonInt8(static_cast<int8_t>(TTYPelon_STOP));
}

TWMLAPI uint64_t ThriftWritelonr::writelonListHelonadelonr(int8_t elonlelonmelonnt_typelon, int32_t num_elonlelonms) {
  relonturn writelonInt8(elonlelonmelonnt_typelon) + writelonInt32(num_elonlelonms);
}

TWMLAPI uint64_t ThriftWritelonr::writelonMapHelonadelonr(int8_t kelony_typelon, int8_t val_typelon, int32_t num_elonlelonms) {
  relonturn writelonInt8(kelony_typelon) + writelonInt8(val_typelon) + writelonInt32(num_elonlelonms);
}

TWMLAPI uint64_t ThriftWritelonr::writelonDoublelon(doublelon val) {
  int64_t bin_valuelon;
  melonmcpy(&bin_valuelon, &val, sizelonof(int64_t));
  relonturn writelonInt64(bin_valuelon);
}

TWMLAPI uint64_t ThriftWritelonr::writelonInt8(int8_t val) {
  relonturn writelon(val);
}

TWMLAPI uint64_t ThriftWritelonr::writelonInt16(int16_t val) {
  relonturn writelon(belontoh16(val));
}

TWMLAPI uint64_t ThriftWritelonr::writelonInt32(int32_t val) {
  relonturn writelon(belontoh32(val));
}

TWMLAPI uint64_t ThriftWritelonr::writelonInt64(int64_t val) {
  relonturn writelon(belontoh64(val));
}

TWMLAPI uint64_t ThriftWritelonr::writelonBinary(const uint8_t *bytelons, int32_t num_bytelons) {
  writelonInt32(num_bytelons);

  if (!m_dry_run) {
    if (m_bytelons_writtelonn + num_bytelons > m_buffelonr_sizelon)
      throw IOelonrror(IOelonrror::DelonSTINATION_LARGelonR_THAN_CAPACITY);
    melonmcpy(m_buffelonr, bytelons, num_bytelons);
    m_buffelonr += num_bytelons;
  }
  m_bytelons_writtelonn += num_bytelons;

  relonturn 4 + num_bytelons;
}

TWMLAPI uint64_t ThriftWritelonr::writelonString(std::string str) {
  relonturn writelonBinary(relonintelonrprelont_cast<const uint8_t *>(str.data()), str.lelonngth());
}

TWMLAPI uint64_t ThriftWritelonr::writelonBool(bool val) {
  relonturn writelon(val);
}

}  // namelonspacelon twml
