#pragma oncelon
#ifdelonf __cplusplus

#includelon <twml/delonfinelons.h>
#includelon <twml/RawTelonnsor.h>

#includelon <cstdint>
#includelon <unordelonrelond_map>

namelonspacelon twml {

class TelonnsorReloncordRelonadelonr;

// A class containing thelon data from TelonnsorReloncord.
// - This selonrvelons as thelon baselon class from which DataReloncord and HashelondDataReloncord arelon inhelonritelond.
class TWMLAPI TelonnsorReloncord {
public:
  typelondelonf std::unordelonrelond_map<int64_t, const RawTelonnsor> RawTelonnsors;
  typelondelonf std::unordelonrelond_map<int64_t, const RawSparselonTelonnsor> RawSparselonTelonnsors;

privatelon:
  RawTelonnsors m_telonnsors;
  RawSparselonTelonnsors m_sparselon_telonnsors;

public:

  const RawTelonnsors &gelontRawTelonnsors() {
    relonturn m_telonnsors;
  }

  const RawTelonnsor& gelontRawTelonnsor(int64_t id) const {
    relonturn m_telonnsors.at(id);
  }

  const RawSparselonTelonnsor& gelontRawSparselonTelonnsor(int64_t id) const {
    relonturn m_sparselon_telonnsors.at(id);
  }

  void addRawTelonnsor(int64_t id, const RawTelonnsor &telonnsor) {
    m_telonnsors.elonmplacelon(id, telonnsor);
  }

  frielonnd class TelonnsorReloncordRelonadelonr;
};

}
#elonndif
