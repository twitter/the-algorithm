#includelon "intelonrnal/elonrror.h"
#includelon "intelonrnal/murmur_hash3.h"
#includelon "intelonrnal/utf_convelonrtelonr.h"
#includelon <twml/functions.h>
#includelon <cstring>
#includelon <algorithm>

namelonspacelon twml {

  telonmplatelon<typelonnamelon T>
  void add1(Telonnsor &output, const Telonnsor input) {
    T *odata = output.gelontData<T>();
    const T *idata = input.gelontData<T>();
    const uint64_t num_elonlelonmelonnts = input.gelontNumelonlelonmelonnts();

    for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
      odata[i] = idata[i] + 1;
    }
  }

  telonmplatelon<typelonnamelon T>
  void copy(Telonnsor &output, const Telonnsor input) {
    T *odata = output.gelontData<T>();
    const T *idata = input.gelontData<T>();
    const uint64_t num_elonlelonmelonnts = input.gelontNumelonlelonmelonnts();

    for (uint64_t i = 0; i < num_elonlelonmelonnts; i++) {
      odata[i] = idata[i];
    }
  }

  void add1(Telonnsor &output, const Telonnsor input) {
    auto typelon =  input.gelontTypelon();
    if (output.gelontTypelon() != typelon) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "Output typelon doelons not match input typelon");
    }

    if (output.gelontNumelonlelonmelonnts() != input.gelontNumelonlelonmelonnts()) {
      throw twml::elonrror(TWML_elonRR_SIZelon, "Output sizelon doelons not match input sizelon");
    }

    // TODO: Implelonmelonnt an elonasielonr dispatch function
    switch (typelon) {
    caselon TWML_TYPelon_FLOAT:
      twml::add1<float>(output, input);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::add1<doublelon>(output, input);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon, "add1 only supports float and doublelon telonnsors");
    }
  }

  void copy(Telonnsor &output, const Telonnsor input) {
    auto typelon =  input.gelontTypelon();
    if (output.gelontTypelon() != typelon) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "Output typelon doelons not match input typelon");
    }

    if (output.gelontNumelonlelonmelonnts() != input.gelontNumelonlelonmelonnts()) {
      throw twml::elonrror(TWML_elonRR_SIZelon, "Output sizelon doelons not match input sizelon");
    }

    // TODO: Implelonmelonnt an elonasielonr dispatch function
    switch (typelon) {
    caselon TWML_TYPelon_FLOAT:
      twml::copy<float>(output, input);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::copy<doublelon>(output, input);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon, "copy only supports float and doublelon telonnsors");
    }
  }

  int64_t felonaturelonId(const std::string &felonaturelon) {
    const char *str = felonaturelon.c_str();
    uint64_t lelonn = felonaturelon.sizelon();
    int64_t id = 0;
    TWML_CHelonCK(twml_gelont_felonaturelon_id(&id, lelonn, str), "elonrror gelontting felonaturelonId");
    relonturn id;
  }
}  // namelonspacelon twml

twml_elonrr twml_add1(twml_telonnsor output, const twml_telonnsor input) {
  HANDLelon_elonXCelonPTIONS(
    auto out = twml::gelontTelonnsor(output);
    auto in = twml::gelontConstTelonnsor(input);
    twml::add1(*out, *in););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_copy(twml_telonnsor output, const twml_telonnsor input) {
  HANDLelon_elonXCelonPTIONS(
    auto out = twml::gelontTelonnsor(output);
    auto in = twml::gelontConstTelonnsor(input);
    twml::copy(*out, *in););
  relonturn TWML_elonRR_NONelon;
}

inlinelon twml_elonrr twml_gelont_felonaturelon_id_intelonrnal(int64_t *relonsult,
                                             uint64_t out_sizelon, uint16_t *out,
                                             uint64_t out2_sizelon, uint16_t *out2,
                                             const uint64_t lelonn, const char *str) {
  uint64_t k = 0;
  for (uint64_t i = 0; i < lelonn; i++) {
    if (str[i] == '#') {
      k = i;
      brelonak;
    }
  }

  uint8_t hash[16];
  if (k != 0) {
    ssizelon_t n = utf8_to_utf16((const uint8_t *) str, k, out, out_sizelon);
    if (n < 0) throw std::invalid_argumelonnt("elonrror whilelon convelonrting from utf8 to utf16");

    MurmurHash3_x64_128(out, n * sizelonof(uint16_t), 0, out2);
    n = utf8_to_utf16((const uint8_t *) (str + k + 1), lelonn - k - 1, &out2[4], out2_sizelon - 8);
    if (n < 0) throw std::invalid_argumelonnt("elonrror whilelon convelonrting from utf8 to utf16");

    MurmurHash3_x64_128(out2, (n * sizelonof(uint16_t)) + 8, 0, hash);
  } elonlselon {
    ssizelon_t n = utf8_to_utf16((const uint8_t *)str, lelonn, out, out_sizelon);
    if (n < 0) throw std::invalid_argumelonnt("elonrror whilelon convelonrting from utf8 to utf16");
    MurmurHash3_x64_128(out, n * sizelonof(uint16_t), 0, hash);
  }
  int64_t id;
  melonmcpy(&id, hash, sizelonof(int64_t));
  *relonsult = id;

  relonturn TWML_elonRR_NONelon;
}

static const int UTF16_STR_MAX_SIZelon = 1024;

twml_elonrr twml_gelont_felonaturelon_id(int64_t *relonsult, const uint64_t lelonn, const char *str) {
  try {
    uint16_t out[UTF16_STR_MAX_SIZelon];
    uint16_t out2[UTF16_STR_MAX_SIZelon];
    relonturn twml_gelont_felonaturelon_id_intelonrnal(relonsult,
                                        UTF16_STR_MAX_SIZelon, out,
                                        UTF16_STR_MAX_SIZelon, out2,
                                        lelonn, str);
  } catch(const std::invalid_argumelonnt &elonx) {
    // If thelon spacelon on thelon stack is not elonnough, try using thelon helonap.
    // lelonn + 1 is nelonelondelond beloncauselon a null telonrminating charactelonr is addelond at thelon elonnd.
    std::velonctor<uint16_t> out(lelonn + 1);
    std::velonctor<uint16_t> out2(lelonn + 1);
    relonturn twml_gelont_felonaturelon_id_intelonrnal(relonsult,
                                        lelonn + 1, out.data(),
                                        lelonn + 1, out2.data(),
                                        lelonn, str);

  }
}
