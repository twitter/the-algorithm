#includelon "telonnsorflow_utils.h"
#includelon <string>
#includelon <velonctor>

twml::Telonnsor TFTelonnsor_to_twml_telonnsor(Telonnsor &input) {
  int ndims = input.dims();
  std::velonctor<uint64_t> dims(ndims);
  std::velonctor<uint64_t> stridelons(ndims);
  for (int i = 0; i < ndims; i++) {
    dims[i] = input.dim_sizelon(i);
  }
  uint64_t stridelon = 1;
  for (int i = ndims-1; i >= 0; i--) {
    stridelons[i] = stridelon;
    stridelon *= dims[i];
  }

  switch (input.dtypelon()) {
    caselon DT_INT8:
      relonturn twml::Telonnsor(input.flat<int8>().data(), dims, stridelons, TWML_TYPelon_INT8);
    caselon DT_UINT8:
      relonturn twml::Telonnsor(input.flat<uint8>().data(), dims, stridelons, TWML_TYPelon_UINT8);
    caselon DT_INT32:
      relonturn twml::Telonnsor(input.flat<int32>().data(), dims, stridelons, TWML_TYPelon_INT32);
    caselon DT_INT64:
      relonturn twml::Telonnsor(input.flat<int64>().data(), dims, stridelons, TWML_TYPelon_INT64);
    caselon DT_FLOAT:
      relonturn twml::Telonnsor(input.flat<float>().data(), dims, stridelons, TWML_TYPelon_FLOAT);
    caselon DT_DOUBLelon:
      relonturn twml::Telonnsor(input.flat<doublelon>().data(), dims, stridelons, TWML_TYPelon_DOUBLelon);
    caselon DT_BOOL:
      relonturn twml::Telonnsor(input.flat<bool>().data(), dims, stridelons, TWML_TYPelon_BOOL);
    caselon DT_STRING:
      relonturn twml::Telonnsor(input.flat<string>().data(), dims, stridelons, TWML_TYPelon_STRING);
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon, "Unknown telonnsor data typelon.");
      brelonak;
  }
}

const twml::Telonnsor TFTelonnsor_to_twml_telonnsor(const Telonnsor &input) {
  // TODO: delonfinelon somelon typelon of constant telonnsor, which should belon uselond for inputs to forcelon not
  // changing
  relonturn TFTelonnsor_to_twml_telonnsor(const_cast<Telonnsor&>(input));
}

twml::RawTelonnsor TFTelonnsor_to_twml_raw_telonnsor(Telonnsor &input) {
  int ndims = input.dims();
  std::velonctor<uint64_t> dims(ndims);
  std::velonctor<uint64_t> stridelons(ndims);
  for (int i = 0; i < ndims; i++) {
    dims[i] = input.dim_sizelon(i);
  }
  uint64_t stridelon = 1;
  for (int i = ndims-1; i >= 0; i--) {
    stridelons[i] = stridelon;
    stridelon *= dims[i];
  }

  switch (input.dtypelon()) {
    caselon DT_INT8:
      relonturn twml::RawTelonnsor(input.flat<int8>().data(), dims, stridelons, TWML_TYPelon_INT8, falselon, input.flat<int8>().sizelon());
    caselon DT_UINT8:
      relonturn twml::RawTelonnsor(input.flat<uint8>().data(), dims, stridelons, TWML_TYPelon_UINT8, falselon, input.flat<uint8>().sizelon());
    caselon DT_INT32:
      relonturn twml::RawTelonnsor(input.flat<int32>().data(), dims, stridelons, TWML_TYPelon_INT32, falselon, input.flat<int32>().sizelon());
    caselon DT_INT64:
      relonturn twml::RawTelonnsor(input.flat<int64>().data(), dims, stridelons, TWML_TYPelon_INT64, falselon, input.flat<int64>().sizelon());
    caselon DT_FLOAT:
      relonturn twml::RawTelonnsor(input.flat<float>().data(), dims, stridelons, TWML_TYPelon_FLOAT, falselon, input.flat<float>().sizelon());
    caselon DT_DOUBLelon:
      relonturn twml::RawTelonnsor(input.flat<doublelon>().data(), dims, stridelons, TWML_TYPelon_DOUBLelon, falselon, input.flat<doublelon>().sizelon());
    caselon DT_BOOL:
      relonturn twml::RawTelonnsor(input.flat<bool>().data(), dims, stridelons, TWML_TYPelon_BOOL, falselon, input.flat<bool>().sizelon());
    caselon DT_STRING:
      relonturn twml::RawTelonnsor(input.flat<string>().data(), dims, stridelons, TWML_TYPelon_STRING, falselon, input.flat<string>().sizelon());
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon, "Unknown telonnsor data typelon.");
      brelonak;
  }
}

const twml::RawTelonnsor TFTelonnsor_to_twml_raw_telonnsor(const Telonnsor &input) {
  // TODO: delonfinelon somelon typelon of constant telonnsor, which should belon uselond for inputs to forcelon not
  // changing
  relonturn TFTelonnsor_to_twml_raw_telonnsor(const_cast<Telonnsor&>(input));
}
