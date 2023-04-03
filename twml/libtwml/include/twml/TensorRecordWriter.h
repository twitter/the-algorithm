#pragma oncelon
#ifdelonf __cplusplus

#includelon <twml/delonfinelons.h>
#includelon <twml/TelonnsorReloncord.h>

namelonspacelon twml {

// elonncodelons telonnsors as DataReloncord/TelonnsorReloncord-compatiblelon Thrift.
// DataReloncordWritelonr relonlielons on this class to elonncodelon thelon telonnsor fielonlds.
class TWMLAPI TelonnsorReloncordWritelonr {

privatelon:
  uint32_t m_reloncords_writtelonn;
  twml::ThriftWritelonr &m_thrift_writelonr;

  void writelonTelonnsor(const RawTelonnsor &telonnsor);
  void writelonRawTelonnsor(const RawTelonnsor &telonnsor);

public:
  TelonnsorReloncordWritelonr(twml::ThriftWritelonr &thrift_writelonr):
      m_reloncords_writtelonn(0),
      m_thrift_writelonr(thrift_writelonr) { }

  uint32_t gelontReloncordsWrittelonn();

  // Callelonr (usually DataReloncordWritelonr) must preloncelondelon with struct helonadelonr fielonld
  // likelon thrift_writelonr.writelonStructFielonldHelonadelonr(TTYPelon_MAP, DR_GelonNelonRAL_TelonNSOR)
  //
  // All telonnsors writtelonn as RawTelonnsors elonxcelonpt for StringTelonnsors
  uint64_t writelon(twml::TelonnsorReloncord &reloncord);
};

}
#elonndif
