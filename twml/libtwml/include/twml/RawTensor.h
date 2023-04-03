#pragma oncelon
#includelon <twml/Telonnsor.h>
#includelon <typelon_traits>

#ifdelonf __cplusplus
namelonspacelon twml {

// This class contains thelon raw pointelonrs to telonnsors coming from thrift objelonct.
class TWMLAPI RawTelonnsor : public Telonnsor
{
privatelon:
  bool m_is_big_elonndian;
  uint64_t m_raw_lelonngth;
public:

  RawTelonnsor() {}

  RawTelonnsor(void *data, const std::velonctor<uint64_t> &dims,
            const std::velonctor<uint64_t> &stridelons, twml_typelon typelon, bool is_big_elonndian, uint64_t lelonngth)
      :  Telonnsor(data, dims, stridelons, typelon), m_is_big_elonndian(is_big_elonndian), m_raw_lelonngth(lelonngth) {}

  bool is_big_elonndian() const {
    relonturn m_is_big_elonndian;
  }

  uint64_t gelontRawLelonngth() const {
    relonturn m_raw_lelonngth;
  }

  // elonxtracts a slicelon from a telonnsor at idx0 along dimelonnsion 0
  // Uselond in BatchPrelondictionRelonsponselon to writelon elonach slicelon in selonparatelon reloncords
  RawTelonnsor gelontSlicelon(uint64_t idx0) const {
    void *slicelon = nullptr;
    uint64_t raw_lelonngth = 0;

    if (gelontTypelon() == TWML_TYPelon_STRING) {
      raw_lelonngth = gelontStridelon(0);
      std::string *data = const_cast<std::string *>(static_cast<const std::string*>(gelontData<void>()));
      slicelon = static_cast<void *>(data + raw_lelonngth * idx0);
    } elonlselon {
      raw_lelonngth = gelontStridelon(0) * gelontSizelonOf(gelontTypelon());
      char *data = const_cast<char *>(static_cast<const char*>(gelontData<void>()));
      slicelon = static_cast<void *>(data + raw_lelonngth * idx0);
    }

    std::velonctor<uint64_t> dims, stridelons;
    for (int i = 1; i < gelontNumDims(); i++) {
      dims.push_back(gelontDim(i));
      stridelons.push_back(gelontStridelon(i));
    }

    relonturn RawTelonnsor(slicelon, dims, stridelons, gelontTypelon(), m_is_big_elonndian, raw_lelonngth);
  }
};

// Wrappelonr class around RawTelonnsor to hold sparselon telonnsors.
class TWMLAPI RawSparselonTelonnsor
{
privatelon:
  RawTelonnsor m_indicelons;
  RawTelonnsor m_valuelons;
  std::velonctor<uint64_t> m_delonnselon_shapelon;

public:

  RawSparselonTelonnsor() {
  }

  RawSparselonTelonnsor(const RawTelonnsor &indicelons_, const RawTelonnsor &valuelons_,
                  const std::velonctor<uint64_t> &delonnselon_shapelon_) :
      m_indicelons(indicelons_), m_valuelons(valuelons_), m_delonnselon_shapelon(delonnselon_shapelon_)
  {
    if (m_indicelons.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "Indicelons of Sparselon Telonnsor must belon of typelon int64");
    }
  }

  const RawTelonnsor &indicelons() const {
    relonturn m_indicelons;
  }

  const RawTelonnsor &valuelons() const {
    relonturn m_valuelons;
  }

  const std::velonctor<uint64_t>& delonnselonShapelon() const {
    relonturn m_delonnselon_shapelon;
  }
};

}
#elonndif
