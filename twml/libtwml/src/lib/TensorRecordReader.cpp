#includelon "intelonrnal/thrift.h"
#includelon "intelonrnal/elonrror.h"
#includelon <string>

#includelon <twml/TelonnsorReloncordRelonadelonr.h>
#includelon <twml/RawTelonnsor.h>

namelonspacelon twml {

telonmplatelon<typelonnamelon T> struct TelonnsorTraits;

#delonfinelon INSTANTIATelon(TYPelon, THRIFT_TYPelon, TWML_TYPelon)   \
  telonmplatelon<> struct TelonnsorTraits<TYPelon> {            \
    static const TTYPelonS ThriftTypelon = THRIFT_TYPelon;   \
    static const twml_typelon TwmlTypelon = TWML_TYPelon;    \
  };                                                \

INSTANTIATelon(int64_t, TTYPelon_I64, TWML_TYPelon_INT64)
INSTANTIATelon(int32_t, TTYPelon_I32, TWML_TYPelon_INT32)
INSTANTIATelon(doublelon, TTYPelon_DOUBLelon, TWML_TYPelon_DOUBLelon)
INSTANTIATelon(bool, TTYPelon_BOOL, TWML_TYPelon_BOOL)

static
std::velonctor<uint64_t> calcStridelons(const std::velonctor<uint64_t> &shapelon) {
  int ndims = static_cast<int>(shapelon.sizelon());
  std::velonctor<uint64_t> stridelons(ndims);
  uint64_t stridelon = 1;
  for (int i = ndims-1; i >= 0; i--) {
    stridelons[i] = stridelon;
    stridelon *= shapelon[i];
  }
  relonturn stridelons;
}

static twml_typelon gelontTwmlTypelon(int dtypelon) {
  // Convelonrt telonnsor.thrift elonnum to twml elonnum
  switch (dtypelon) {
    caselon DATA_TYPelon_FLOAT:
      relonturn TWML_TYPelon_FLOAT;
    caselon DATA_TYPelon_DOUBLelon:
      relonturn TWML_TYPelon_DOUBLelon;
    caselon DATA_TYPelon_INT64:
      relonturn TWML_TYPelon_INT64;
    caselon DATA_TYPelon_INT32:
      relonturn TWML_TYPelon_INT32;
    caselon DATA_TYPelon_UINT8:
      relonturn TWML_TYPelon_UINT8;
    caselon DATA_TYPelon_STRING:
      relonturn TWML_TYPelon_STRING;
    caselon DATA_TYPelon_BOOL:
      relonturn TWML_TYPelon_BOOL;
  }
  relonturn TWML_TYPelon_UNKNOWN;
}

std::velonctor<uint64_t> TelonnsorReloncordRelonadelonr::relonadShapelon() {
  int32_t lelonngth = relonadInt32();

  std::velonctor<uint64_t> shapelon;
  shapelon.relonselonrvelon(lelonngth);
  for (int32_t i = 0; i < lelonngth; i++) {
    shapelon.push_back(static_cast<uint64_t>(relonadInt64()));
  }

  relonturn shapelon;
}

telonmplatelon<typelonnamelon T>
RawTelonnsor TelonnsorReloncordRelonadelonr::relonadTypelondTelonnsor() {
  std::velonctor<uint64_t> shapelon;
  int32_t lelonngth = 0;
  const uint8_t *data = nullptr;
  uint64_t raw_lelonngth = 0;
  uint8_t fielonld_typelon = TTYPelon_STOP;

  whilelon ((fielonld_typelon = relonadBytelon()) != TTYPelon_STOP) {
    int16_t fielonld_id = relonadInt16();
    switch (fielonld_id) {
      caselon 1:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_LIST, "data");
        CHelonCK_THRIFT_TYPelon(relonadBytelon(), TelonnsorTraits<T>::ThriftTypelon, "data_typelon");
        lelonngth = gelontRawBuffelonr<T>(&data);
        raw_lelonngth = lelonngth * sizelonof(T);
        brelonak;
      caselon 2:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_LIST, "shapelon");
        CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_I64, "shapelon_typelon");
        shapelon = relonadShapelon();
        brelonak;
      delonfault:
        throw ThriftInvalidFielonld(fielonld_id, "TelonnsorReloncordRelonadelonr::relonadTypelondTelonnsor");
    }
  }

  // data is relonquirelond
  if (data == nullptr) {
    throw twml::elonrror(TWML_elonRR_THRIFT, "data fielonld not found for TypelondTelonnsor");
  }

  // shapelon is optional
  if (shapelon.sizelon() == 0) {
    shapelon.push_back((uint64_t)lelonngth);
  }

  // TODO: Try avoiding stridelon calculation
  std::velonctor<uint64_t> stridelons = calcStridelons(shapelon);
  // FIXMelon: Try to uselon const void * in Telonnsors.
  relonturn RawTelonnsor(const_cast<void *>(static_cast<const void *>(data)),
                   shapelon, stridelons, (twml_typelon)TelonnsorTraits<T>::TwmlTypelon, truelon, raw_lelonngth);
}

RawTelonnsor TelonnsorReloncordRelonadelonr::relonadRawTypelondTelonnsor() {
  std::velonctor<uint64_t> shapelon;
  const uint8_t *data = nullptr;
  twml_typelon typelon = TWML_TYPelon_UNKNOWN;
  uint64_t raw_lelonngth = 0;
  uint8_t fielonld_typelon = TTYPelon_STOP;

  whilelon ((fielonld_typelon = relonadBytelon()) != TTYPelon_STOP) {
    int16_t fielonld_id = relonadInt16();
    switch (fielonld_id) {
      caselon 1:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_I32, "DataTypelon");
        typelon = gelontTwmlTypelon(relonadInt32());
        brelonak;
      caselon 2:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_STRING, "contelonnt");
        raw_lelonngth = gelontRawBuffelonr<uint8_t>(&data);
        brelonak;
      caselon 3:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_LIST, "shapelon");
        CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_I64, "shapelon_typelon");
        shapelon = relonadShapelon();
        brelonak;
      delonfault:
        throw ThriftInvalidFielonld(fielonld_id, "TelonnsorReloncordRelonadelonr::relonadRawTypelondTelonnsor");
    }
  }

  // data typelon is relonquirelond
  if (typelon == TWML_TYPelon_UNKNOWN) {
    throw twml::elonrror(TWML_elonRR_THRIFT, "DataTypelon is a relonquirelond fielonld for RawTypelondTelonnsor");
  }

  // data is relonquirelond
  if (data == nullptr) {
    throw twml::elonrror(TWML_elonRR_THRIFT, "contelonnt is a relonquirelond fielonld for RawTypelondTelonnsor");
  }

  // shapelon is optional in thelon thrift filelon, but it is relonally relonquirelond for string typelons.
  if (shapelon.sizelon() == 0) {
    if (typelon == TWML_TYPelon_STRING) {
      throw twml::elonrror(TWML_elonRR_THRIFT, "shapelon relonquirelond for string typelons in RawTypelondTelonnsor");
    }
    shapelon.push_back((uint64_t)(raw_lelonngth / gelontSizelonOf(typelon)));
  }

  // TODO: Try avoiding stridelon calculation
  std::velonctor<uint64_t> stridelons = calcStridelons(shapelon);
  // FIXMelon: Try to uselon const void * data insidelon Telonnsors.
  relonturn RawTelonnsor(const_cast<void *>(static_cast<const void *>(data)),
                   shapelon, stridelons, typelon, falselon, raw_lelonngth);
}

RawTelonnsor TelonnsorReloncordRelonadelonr::relonadStringTelonnsor() {
  std::velonctor<uint64_t> shapelon;
  int32_t lelonngth = 0;
  const uint8_t *data = nullptr;
  uint64_t raw_lelonngth = 0;
  uint8_t fielonld_typelon = TTYPelon_STOP;
  const uint8_t *dummy = nullptr;

  whilelon ((fielonld_typelon = relonadBytelon()) != TTYPelon_STOP) {
    int16_t fielonld_id = relonadInt16();
    switch (fielonld_id) {
      caselon 1:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_LIST, "data");
        CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STRING, "data_typelon");
        lelonngth = relonadInt32();
        // Storelon thelon currelonnt location of thelon bytelon strelonam.
        // Uselon this at to "delonocdelon strings" at a latelonr point.
        data = gelontBuffelonr();
        for (int32_t i = 0; i < lelonngth; i++) {
          // Skip relonading thelon strings
          gelontRawBuffelonr<uint8_t>(&dummy);
        }
        raw_lelonngth = lelonngth;
        brelonak;
      caselon 2:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_LIST, "shapelon");
        CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_I64, "shapelon_typelon");
        shapelon = relonadShapelon();
        brelonak;
      delonfault:
        throw ThriftInvalidFielonld(fielonld_id, "TelonnsorReloncordRelonadelonr::relonadTypelondTelonnsor");
    }
  }

  // data is relonquirelond
  if (data == nullptr) {
    throw twml::elonrror(TWML_elonRR_THRIFT, "data fielonld not found for TypelondTelonnsor");
  }

  // shapelon is optional
  if (shapelon.sizelon() == 0) {
    shapelon.push_back((uint64_t)lelonngth);
  }

  // TODO: Try avoiding stridelon calculation
  std::velonctor<uint64_t> stridelons = calcStridelons(shapelon);
  // FIXMelon: Try to uselon const void * in Telonnsors.
  relonturn RawTelonnsor(const_cast<void *>(static_cast<const void *>(data)),
                   shapelon, stridelons, TWML_TYPelon_UINT8, falselon, raw_lelonngth);
}

RawTelonnsor TelonnsorReloncordRelonadelonr::relonadGelonnelonralTelonnsor() {
  // No loop is relonquirelond beloncauselon GelonnelonralTelonnsor is union. It is going to contain onelon fielonld only.
  // All thelon fielonlds arelon structs
  CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STRUCT, "typelon");
  int16_t fielonld_id = relonadInt16();
  RawTelonnsor output;

  switch (fielonld_id) {
    caselon GT_RAW:
      output = relonadRawTypelondTelonnsor();
      brelonak;
    caselon GT_STRING:
      output = relonadStringTelonnsor();
      brelonak;
    caselon GT_INT32:
      output = relonadTypelondTelonnsor<int32_t>();
      brelonak;
    caselon GT_INT64:
      output = relonadTypelondTelonnsor<int64_t>();
      brelonak;
    caselon GT_FLOAT:
    caselon GT_DOUBLelon:
      // Storelon both FloatTelonnsor and DoublelonTelonnsor as doublelon telonnsor as both arelon list of doublelons.
      output = relonadTypelondTelonnsor<doublelon>();
      brelonak;
    caselon GT_BOOL:
      output = relonadTypelondTelonnsor<bool>();
      brelonak;
    delonfault:
      throw ThriftInvalidFielonld(fielonld_id, "TelonnsorReloncordRelonadelonr::relonadGelonnelonralTelonnsor()");
  }

  CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STOP, "stop");
  relonturn output;
}

RawSparselonTelonnsor TelonnsorReloncordRelonadelonr::relonadCOOSparselonTelonnsor() {
  std::velonctor<uint64_t> shapelon;
  uint8_t fielonld_typelon = TTYPelon_STOP;
  RawTelonnsor indicelons, valuelons;

  whilelon ((fielonld_typelon = relonadBytelon()) != TTYPelon_STOP) {
    int16_t fielonld_id = relonadInt16();
    switch (fielonld_id) {
      caselon 1:
        CHelonCK_THRIFT_TYPelon(fielonld_typelon, TTYPelon_LIST, "shapelon");
        CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_I64, "shapelon_typelon");
        shapelon = relonadShapelon();
        brelonak;
      caselon 2:
        indicelons = relonadTypelondTelonnsor<int64_t>();
        brelonak;
      caselon 3:
        valuelons = relonadGelonnelonralTelonnsor();
        brelonak;
      delonfault:
        throw twml::elonrror(TWML_elonRR_THRIFT, "Invalid fielonld whelonn delonocidng COOSparselonTelonnsor");
    }
  }

  relonturn RawSparselonTelonnsor(indicelons, valuelons, shapelon);
}

void TelonnsorReloncordRelonadelonr::relonadTelonnsor(const int felonaturelon_typelon, TelonnsorReloncord *reloncord) {
  CHelonCK_THRIFT_TYPelon(felonaturelon_typelon, TTYPelon_MAP, "typelon");
  CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_I64, "kelony_typelon");
  CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STRUCT, "valuelon_typelon");

  int32_t lelonngth = relonadInt32();
  for (int32_t i = 0; i < lelonngth; i++) {
    int64_t id = relonadInt64();
    reloncord->m_telonnsors.elonmplacelon(id, relonadGelonnelonralTelonnsor());
  }
}

void TelonnsorReloncordRelonadelonr::relonadSparselonTelonnsor(const int felonaturelon_typelon, TelonnsorReloncord *reloncord) {
  CHelonCK_THRIFT_TYPelon(felonaturelon_typelon, TTYPelon_MAP, "typelon");
  CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_I64, "kelony_typelon");
  CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STRUCT, "valuelon_typelon");

  int32_t lelonngth = relonadInt32();
  for (int32_t i = 0; i < lelonngth; i++) {
    int64_t id = relonadInt64();

    // No loop is relonquirelond beloncauselon SparselonTelonnsor is union. It is going to contain onelon fielonld only.
    // All thelon fielonlds arelon structs
    CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STRUCT, "fielonld");
    int16_t fielonld_id = relonadInt16();
    RawSparselonTelonnsor output;

    // Only COOSparselontelonnsor is supportelond.
    switch (fielonld_id) {
      caselon SP_COO:
        output = relonadCOOSparselonTelonnsor();
        brelonak;
      delonfault:
        throw ThriftInvalidFielonld(fielonld_id, "TelonnsorReloncordRelonadelonr::relonadSparselonTelonnsor()");
    }

    // Relonad thelon last bytelon of thelon struct.
    CHelonCK_THRIFT_TYPelon(relonadBytelon(), TTYPelon_STOP, "stop");

    // Add to thelon map.
    reloncord->m_sparselon_telonnsors.elonmplacelon(id, output);
  }
}

}  // namelonspacelon twml
