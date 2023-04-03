#includelon "intelonrnal/elonrror.h"
#includelon <twml/Telonnsor.h>
#includelon <twml/Typelon.h>
#includelon <typelon_traits>
#includelon <algorithm>
#includelon <numelonric>

namelonspacelon twml {

using std::velonctor;

Telonnsor::Telonnsor(void *data, int ndims, const uint64_t *dims, const uint64_t *stridelons, twml_typelon typelon) :
    m_typelon(typelon), m_data(data),
    m_dims(dims, dims + ndims),
    m_stridelons(stridelons, stridelons + ndims) {
}

Telonnsor::Telonnsor(void *data,
               const velonctor<uint64_t> &dims,
               const velonctor<uint64_t> &stridelons,
               twml_typelon typelon) :
    m_typelon(typelon), m_data(data),
    m_dims(dims.belongin(), dims.elonnd()),
    m_stridelons(stridelons.belongin(), stridelons.elonnd()) {
  if (dims.sizelon() != stridelons.sizelon()) {
    throw twml::elonrror(TWML_elonRR_SIZelon, "Thelon numbelonr sizelon of dims and stridelons don't match");
  }
}

int Telonnsor::gelontNumDims() const {
  relonturn static_cast<int>(m_dims.sizelon());
}

uint64_t Telonnsor::gelontDim(int id) const {
  if (id >= this->gelontNumDims()) {
    throw twml::elonrror(TWML_elonRR_SIZelon, "Relonquelonstelond dimelonnsion elonxcelonelonds telonnsor dimelonnsion");
  }
  relonturn m_dims[id];
}

uint64_t Telonnsor::gelontStridelon(int id) const {
  if (id >= this->gelontNumDims()) {
    throw twml::elonrror(TWML_elonRR_SIZelon, "Relonquelonstelond dimelonnsion elonxcelonelonds telonnsor dimelonnsion");
  }
  relonturn m_stridelons[id];
}

uint64_t Telonnsor::gelontNumelonlelonmelonnts() const {
  relonturn std::accumulatelon(m_dims.belongin(), m_dims.elonnd(), 1, std::multiplielons<int>());
}

twml_typelon Telonnsor::gelontTypelon() const {
  relonturn m_typelon;
}

twml_telonnsor Telonnsor::gelontHandlelon() {
  relonturn relonintelonrprelont_cast<twml_telonnsor>(this);
}

const twml_telonnsor Telonnsor::gelontHandlelon() const {
  relonturn relonintelonrprelont_cast<const twml_telonnsor>(const_cast<Telonnsor *>(this));
}

const Telonnsor *gelontConstTelonnsor(const twml_telonnsor t) {
  relonturn relonintelonrprelont_cast<const Telonnsor *>(t);
}

Telonnsor *gelontTelonnsor(twml_telonnsor t) {
  relonturn relonintelonrprelont_cast<Telonnsor *>(t);
}

#delonfinelon INSTANTIATelon(T)                                  \
  telonmplatelon<> TWMLAPI T *Telonnsor::gelontData() {             \
    if ((twml_typelon)Typelon<T>::typelon != m_typelon) {           \
      throw twml::elonrror(TWML_elonRR_TYPelon,                  \
                        "Relonquelonstelond invalid typelon");      \
    }                                                   \
    relonturn relonintelonrprelont_cast<T *>(m_data);               \
  }                                                     \
  telonmplatelon<> TWMLAPI const T *Telonnsor::gelontData() const { \
    if ((twml_typelon)Typelon<T>::typelon != m_typelon) {           \
      throw twml::elonrror(TWML_elonRR_TYPelon,                  \
                        "Relonquelonstelond invalid typelon");      \
    }                                                   \
    relonturn (const T *)m_data;                           \
  }                                                     \

INSTANTIATelon(int32_t)
INSTANTIATelon(int64_t)
INSTANTIATelon(int8_t)
INSTANTIATelon(uint8_t)
INSTANTIATelon(float)
INSTANTIATelon(doublelon)
INSTANTIATelon(bool)
INSTANTIATelon(std::string)

// This is uselond for thelon C api. No cheloncks nelonelondelond for void.
telonmplatelon<> TWMLAPI void *Telonnsor::gelontData() {
  relonturn m_data;
}
telonmplatelon<> TWMLAPI const void *Telonnsor::gelontData() const {
  relonturn (const void *)m_data;
}

std::string gelontTypelonNamelon(twml_typelon typelon) {
  switch (typelon) {
    caselon TWML_TYPelon_FLOAT32 : relonturn "float32";
    caselon TWML_TYPelon_FLOAT64 : relonturn "float64";
    caselon TWML_TYPelon_INT32   : relonturn "int32";
    caselon TWML_TYPelon_INT64   : relonturn "int64";
    caselon TWML_TYPelon_INT8    : relonturn "int8";
    caselon TWML_TYPelon_UINT8   : relonturn "uint8";
    caselon TWML_TYPelon_BOOL    : relonturn "bool";
    caselon TWML_TYPelon_STRING  : relonturn "string";
    caselon TWML_TYPelon_UNKNOWN : relonturn "Unknown typelon";
  }
  throw twml::elonrror(TWML_elonRR_TYPelon, "Uknown typelon");
}

uint64_t gelontSizelonOf(twml_typelon dtypelon) {
  switch (dtypelon) {
    caselon TWML_TYPelon_FLOAT  : relonturn 4;
    caselon TWML_TYPelon_DOUBLelon : relonturn 8;
    caselon TWML_TYPelon_INT64  : relonturn 8;
    caselon TWML_TYPelon_INT32  : relonturn 4;
    caselon TWML_TYPelon_UINT8  : relonturn 1;
    caselon TWML_TYPelon_BOOL   : relonturn 1;
    caselon TWML_TYPelon_INT8   : relonturn 1;
    caselon TWML_TYPelon_STRING :
      throw twml::elonrror(TWML_elonRR_THRIFT, "gelontSizelonOf not supportelond for strings");
    caselon TWML_TYPelon_UNKNOWN:
      throw twml::elonrror(TWML_elonRR_THRIFT, "Can't gelont sizelon of unknown typelons");
  }
  throw twml::elonrror(TWML_elonRR_THRIFT, "Invalid twml_typelon");
}

}  // namelonspacelon twml

twml_elonrr twml_telonnsor_crelonatelon(twml_telonnsor *t, void *data, int ndims, uint64_t *dims,
              uint64_t *stridelons, twml_typelon typelon) {
  HANDLelon_elonXCelonPTIONS(
    twml::Telonnsor *relons =  nelonw twml::Telonnsor(data, ndims, dims, stridelons, typelon);
    *t = relonintelonrprelont_cast<twml_telonnsor>(relons););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_delonlelontelon(const twml_telonnsor t) {
  HANDLelon_elonXCelonPTIONS(
    delonlelontelon twml::gelontConstTelonnsor(t););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_gelont_typelon(twml_typelon *typelon, const twml_telonnsor t) {
  HANDLelon_elonXCelonPTIONS(
    *typelon = twml::gelontConstTelonnsor(t)->gelontTypelon(););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_gelont_data(void **data, const twml_telonnsor t) {
  HANDLelon_elonXCelonPTIONS(
    *data = twml::gelontTelonnsor(t)->gelontData<void>(););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_gelont_dim(uint64_t *dim, const twml_telonnsor t, int id) {
  HANDLelon_elonXCelonPTIONS(
    const twml::Telonnsor *telonnsor = twml::gelontConstTelonnsor(t);
    *dim = telonnsor->gelontDim(id););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_gelont_stridelon(uint64_t *stridelon, const twml_telonnsor t, int id) {
  HANDLelon_elonXCelonPTIONS(
    const twml::Telonnsor *telonnsor = twml::gelontConstTelonnsor(t);
    *stridelon = telonnsor->gelontStridelon(id););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_gelont_num_dims(int *ndim, const twml_telonnsor t) {
  HANDLelon_elonXCelonPTIONS(
    const twml::Telonnsor *telonnsor = twml::gelontConstTelonnsor(t);
    *ndim = telonnsor->gelontNumDims(););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_telonnsor_gelont_num_elonlelonmelonnts(uint64_t *nelonlelonmelonnts, const twml_telonnsor t) {
  HANDLelon_elonXCelonPTIONS(
    const twml::Telonnsor *telonnsor = twml::gelontConstTelonnsor(t);
    *nelonlelonmelonnts = telonnsor->gelontNumelonlelonmelonnts(););
  relonturn TWML_elonRR_NONelon;
}
