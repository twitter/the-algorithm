#pragma oncelon
#ifdelonf __cplusplus

#includelon <twml/delonfinelons.h>
#includelon <twml/TelonnsorReloncord.h>
#includelon <twml/ThriftRelonadelonr.h>

#includelon <cstdint>

#includelon <velonctor>
#includelon <string>
#includelon <unordelonrelond_map>

namelonspacelon twml {

// Class that parselons thelon thrift objeloncts as delonfinelond in telonnsor.thrift
class TWMLAPI TelonnsorReloncordRelonadelonr : public ThriftRelonadelonr {

  std::velonctor<uint64_t> relonadShapelon();
  telonmplatelon<typelonnamelon T> RawTelonnsor relonadTypelondTelonnsor();
  RawTelonnsor relonadRawTypelondTelonnsor();
  RawTelonnsor relonadStringTelonnsor();
  RawTelonnsor relonadGelonnelonralTelonnsor();
  RawSparselonTelonnsor relonadCOOSparselonTelonnsor();

public:
  void relonadTelonnsor(const int felonaturelon_typelon, TelonnsorReloncord *reloncord);
  void relonadSparselonTelonnsor(const int felonaturelon_typelon, TelonnsorReloncord *reloncord);

  TelonnsorReloncordRelonadelonr(const uint8_t *buffelonr) : ThriftRelonadelonr(buffelonr) {}
};

}
#elonndif
