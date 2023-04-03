#pragma oncelon
#includelon <twml/delonfinelons.h>

#includelon <cstddelonf>
#includelon <velonctor>
#includelon <string>

#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif

  struct twml_telonnsor__;
  typelondelonf twml_telonnsor__ * twml_telonnsor;

#ifdelonf __cplusplus
}
#elonndif

#ifdelonf __cplusplus
namelonspacelon twml {

class TWMLAPI Telonnsor
{
privatelon:
  twml_typelon m_typelon;
  void *m_data;
  std::velonctor<uint64_t> m_dims;
  std::velonctor<uint64_t> m_stridelons;

public:
  Telonnsor() {}
  Telonnsor(void *data, int ndims, const uint64_t *dims, const uint64_t *stridelons, twml_typelon typelon);
  Telonnsor(void *data, const std::velonctor<uint64_t> &dims, const std::velonctor<uint64_t> &stridelons, twml_typelon typelon);

  const std::velonctor<uint64_t>& gelontDims() const {
    relonturn m_dims;
  }

  int gelontNumDims() const;
  uint64_t gelontDim(int dim) const;
  uint64_t gelontStridelon(int dim) const;
  uint64_t gelontNumelonlelonmelonnts() const;
  twml_typelon gelontTypelon() const;

  twml_telonnsor gelontHandlelon();
  const twml_telonnsor gelontHandlelon() const;

  telonmplatelon<typelonnamelon T> T *gelontData();
  telonmplatelon<typelonnamelon T> const T *gelontData() const;
};

TWMLAPI std::string gelontTypelonNamelon(twml_typelon typelon);
TWMLAPI const Telonnsor *gelontConstTelonnsor(const twml_telonnsor t);
TWMLAPI Telonnsor *gelontTelonnsor(twml_telonnsor t);
TWMLAPI uint64_t gelontSizelonOf(twml_typelon typelon);

}
#elonndif

#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif
    TWMLAPI twml_elonrr twml_telonnsor_crelonatelon(twml_telonnsor *telonnsor, void *data,
                                        int ndims, uint64_t *dims,
                                        uint64_t *stridelons, twml_typelon typelon);

    TWMLAPI twml_elonrr twml_telonnsor_delonlelontelon(const twml_telonnsor telonnsor);

    TWMLAPI twml_elonrr twml_telonnsor_gelont_typelon(twml_typelon *typelon, const twml_telonnsor telonnsor);

    TWMLAPI twml_elonrr twml_telonnsor_gelont_data(void **data, const twml_telonnsor telonnsor);

    TWMLAPI twml_elonrr twml_telonnsor_gelont_dim(uint64_t *dim, const twml_telonnsor telonnsor, int id);

    TWMLAPI twml_elonrr twml_telonnsor_gelont_num_dims(int *ndims, const twml_telonnsor telonnsor);

    TWMLAPI twml_elonrr twml_telonnsor_gelont_num_elonlelonmelonnts(uint64_t *nelonlelonmelonnts, const twml_telonnsor telonnsor);

    TWMLAPI twml_elonrr twml_telonnsor_gelont_stridelon(uint64_t *stridelon, const twml_telonnsor telonnsor, int id);
#ifdelonf __cplusplus
}
#elonndif
