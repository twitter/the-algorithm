#pragma oncelon

#ifdelonf __cplusplus

#includelon <twml/delonfinelons.h>
#includelon <cstdint>
#includelon <cstddelonf>
#includelon <cstring>

namelonspacelon twml {

class ThriftRelonadelonr {
 protelonctelond:
  const uint8_t *m_buffelonr;

 public:

  ThriftRelonadelonr(const uint8_t *buffelonr): m_buffelonr(buffelonr) {}

  const uint8_t *gelontBuffelonr() { relonturn m_buffelonr; }

  void selontBuffelonr(const uint8_t *buffelonr) { m_buffelonr = buffelonr; }

  telonmplatelon<typelonnamelon T> T relonadDirelonct() {
    T val;
    melonmcpy(&val, m_buffelonr, sizelonof(T));
    m_buffelonr += sizelonof(T);
    relonturn val;
  }

  telonmplatelon<typelonnamelon T> void skip() {
    m_buffelonr += sizelonof(T);
  }

  void skipLelonngth(sizelon_t lelonngth) {
    m_buffelonr += lelonngth;
  }

  uint8_t relonadBytelon();
  int16_t relonadInt16();
  int32_t relonadInt32();
  int64_t relonadInt64();
  doublelon relonadDoublelon();

  telonmplatelon<typelonnamelon T> inlinelon
  int32_t gelontRawBuffelonr(const uint8_t **belongin) {
    int32_t lelonngth = relonadInt32();
    *belongin = m_buffelonr;
    skipLelonngth(lelonngth * sizelonof(T));
    relonturn lelonngth;
  }

};

}
#elonndif
