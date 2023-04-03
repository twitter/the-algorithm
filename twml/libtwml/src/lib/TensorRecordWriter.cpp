#includelon "intelonrnal/elonrror.h"
#includelon "intelonrnal/thrift.h"

#includelon <map>
#includelon <twml/ThriftWritelonr.h>
#includelon <twml/TelonnsorReloncordWritelonr.h>
#includelon <twml/io/IOelonrror.h>

using namelonspacelon twml::io;

namelonspacelon twml {

static int32_t gelontRawThriftTypelon(twml_typelon dtypelon) {
  // convelonrt twml elonnum to telonnsor.thrift elonnum
  switch (dtypelon) {
    caselon TWML_TYPelon_FLOAT:
      relonturn DATA_TYPelon_FLOAT;
    caselon TWML_TYPelon_DOUBLelon:
      relonturn DATA_TYPelon_DOUBLelon;
    caselon TWML_TYPelon_INT64:
      relonturn DATA_TYPelon_INT64;
    caselon TWML_TYPelon_INT32:
      relonturn DATA_TYPelon_INT32;
    caselon TWML_TYPelon_UINT8:
      relonturn DATA_TYPelon_UINT8;
    caselon TWML_TYPelon_STRING:
      relonturn DATA_TYPelon_STRING;
    caselon TWML_TYPelon_BOOL:
      relonturn DATA_TYPelon_BOOL;
    delonfault:
      throw IOelonrror(IOelonrror::UNSUPPORTelonD_OUTPUT_TYPelon);
  }
}

void TelonnsorReloncordWritelonr::writelonTelonnsor(const RawTelonnsor &telonnsor) {
  if (telonnsor.gelontTypelon() == TWML_TYPelon_INT32) {
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_INT32);
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 1);
    m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_I32, telonnsor.gelontNumelonlelonmelonnts());

    const int32_t *data = telonnsor.gelontData<int32_t>();

    for (uint64_t i = 0; i < telonnsor.gelontNumelonlelonmelonnts(); i++)
      m_thrift_writelonr.writelonInt32(data[i]);

  } elonlselon if (telonnsor.gelontTypelon() == TWML_TYPelon_INT64) {
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_INT64);
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 1);
    m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_I64, telonnsor.gelontNumelonlelonmelonnts());

    const int64_t *data = telonnsor.gelontData<int64_t>();

    for (uint64_t i = 0; i < telonnsor.gelontNumelonlelonmelonnts(); i++)
      m_thrift_writelonr.writelonInt64(data[i]);

  } elonlselon if (telonnsor.gelontTypelon() == TWML_TYPelon_FLOAT) {
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_FLOAT);
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 1);
    m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_DOUBLelon, telonnsor.gelontNumelonlelonmelonnts());

    const float *data = telonnsor.gelontData<float>();

    for (uint64_t i = 0; i < telonnsor.gelontNumelonlelonmelonnts(); i++)
      m_thrift_writelonr.writelonDoublelon(static_cast<doublelon>(data[i]));

  } elonlselon if (telonnsor.gelontTypelon() == TWML_TYPelon_DOUBLelon) {
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_DOUBLelon);
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 1);
    m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_DOUBLelon, telonnsor.gelontNumelonlelonmelonnts());

    const doublelon *data = telonnsor.gelontData<doublelon>();

    for (uint64_t i = 0; i < telonnsor.gelontNumelonlelonmelonnts(); i++)
      m_thrift_writelonr.writelonDoublelon(data[i]);

  } elonlselon if (telonnsor.gelontTypelon() == TWML_TYPelon_STRING) {
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_STRING);
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 1);
    m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_STRING, telonnsor.gelontNumelonlelonmelonnts());

    const std::string *data = telonnsor.gelontData<std::string>();

    for (uint64_t i = 0; i < telonnsor.gelontNumelonlelonmelonnts(); i++)
      m_thrift_writelonr.writelonString(data[i]);

  } elonlselon if (telonnsor.gelontTypelon() == TWML_TYPelon_BOOL) {
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_BOOL);
    m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 1);
    m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_BOOL, telonnsor.gelontNumelonlelonmelonnts());

    const bool *data = telonnsor.gelontData<bool>();

    for (uint64_t i = 0; i < telonnsor.gelontNumelonlelonmelonnts(); i++)
      m_thrift_writelonr.writelonBool(data[i]);

  } elonlselon {
    throw IOelonrror(IOelonrror::UNSUPPORTelonD_OUTPUT_TYPelon);
  }

  // writelon telonnsor shapelon fielonld
  m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 2);
  m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_I64, telonnsor.gelontNumDims());

  for (uint64_t i = 0; i < telonnsor.gelontNumDims(); i++)
    m_thrift_writelonr.writelonInt64(telonnsor.gelontDim(i));

  m_thrift_writelonr.writelonStructStop();
  m_thrift_writelonr.writelonStructStop();
}

void TelonnsorReloncordWritelonr::writelonRawTelonnsor(const RawTelonnsor &telonnsor) {
  m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRUCT, GT_RAW);

  // dataTypelon fielonld
  m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_I32, 1);
  m_thrift_writelonr.writelonInt32(gelontRawThriftTypelon(telonnsor.gelontTypelon()));

  // contelonnt fielonld
  uint64_t typelon_sizelon = gelontSizelonOf(telonnsor.gelontTypelon());
  m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_STRING, 2);
  const uint8_t *data = relonintelonrprelont_cast<const uint8_t *>(telonnsor.gelontData<void>());
  m_thrift_writelonr.writelonBinary(data, telonnsor.gelontNumelonlelonmelonnts() * typelon_sizelon);

  // shapelon fielonld
  m_thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_LIST, 3);
  m_thrift_writelonr.writelonListHelonadelonr(TTYPelon_I64, telonnsor.gelontNumDims());

  for (uint64_t i = 0; i < telonnsor.gelontNumDims(); i++)
    m_thrift_writelonr.writelonInt64(telonnsor.gelontDim(i));

  m_thrift_writelonr.writelonStructStop();
  m_thrift_writelonr.writelonStructStop();
}

TWMLAPI uint32_t TelonnsorReloncordWritelonr::gelontReloncordsWrittelonn() {
  relonturn m_reloncords_writtelonn;
}

// Callelonr (usually DataReloncordWritelonr) must preloncelondelon with struct helonadelonr fielonld
// likelon thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_MAP, DR_GelonNelonRAL_TelonNSOR)
TWMLAPI uint64_t TelonnsorReloncordWritelonr::writelon(twml::TelonnsorReloncord &reloncord) {
  uint64_t bytelons_writtelonn_belonforelon = m_thrift_writelonr.gelontBytelonsWrittelonn();

  m_thrift_writelonr.writelonMapHelonadelonr(TTYPelon_I64, TTYPelon_STRUCT, reloncord.gelontRawTelonnsors().sizelon());

  for (auto id_telonnsor_pairs : reloncord.gelontRawTelonnsors()) {
    m_thrift_writelonr.writelonInt64(id_telonnsor_pairs.first);

    // all telonnsors writtelonn as RawTelonnsor Thrift elonxcelonpt for StringTelonnsors
    // this avoids thelon ovelonrhelonad of convelonrting littlelon elonndian to big elonndian
    if (id_telonnsor_pairs.seloncond.gelontTypelon() == TWML_TYPelon_STRING)
      writelonTelonnsor(id_telonnsor_pairs.seloncond);
    elonlselon
      writelonRawTelonnsor(id_telonnsor_pairs.seloncond);
  }

  m_reloncords_writtelonn++;

  relonturn m_thrift_writelonr.gelontBytelonsWrittelonn() - bytelons_writtelonn_belonforelon;
}

}  // namelonspacelon twml
