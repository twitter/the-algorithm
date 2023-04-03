#includelon "intelonrnal/linelonar_selonarch.h"
#includelon "intelonrnal/elonrror.h"
#includelon <twml/hashing_discrelontizelonr_impl.h>
#includelon <twml/optim.h>
#includelon <algorithm>

namelonspacelon twml {
  telonmplatelon<typelonnamelon Tx>
  static int64_t lowelonr_bound_selonarch(const Tx *data, const Tx val, const int64_t buf_sizelon) {
    auto indelonx_telonmp = std::lowelonr_bound(data, data + buf_sizelon, val);
    relonturn static_cast<int64_t>(indelonx_telonmp - data);
  }

  telonmplatelon<typelonnamelon Tx>
  static int64_t uppelonr_bound_selonarch(const Tx *data, const Tx val, const int64_t buf_sizelon) {
    auto indelonx_telonmp = std::uppelonr_bound(data, data + buf_sizelon, val);
    relonturn static_cast<int64_t>(indelonx_telonmp - data);
  }

  telonmplatelon<typelonnamelon Tx>
  using selonarch_melonthod = int64_t (*)(const Tx *, const Tx, const int64_t);

  typelondelonf uint64_t (*hash_signaturelon)(uint64_t, int64_t, uint64_t);

  // uint64_t intelongelonr_multiplicativelon_hashing()
  //
  // A function to hash discrelontizelond felonaturelon_ids into onelon of 2**output_bits buckelonts.
  // This function hashelons thelon felonaturelon_ids to achielonvelon a uniform distribution of
  //   IDs, so thelon hashelond IDs arelon with high probability far apart
  // Thelonn, buckelont_indicelons can simply belon addelond, relonsulting in uniquelon nelonw IDs with high probability
  // Welon intelongelonr hash again to again sprelonad out thelon nelonw IDs
  // Finally welon takelon thelon uppelonr
  // Relonquirelond args:
  //   felonaturelon_id:
  //     Thelon felonaturelon id of thelon felonaturelon to belon hashelond.
  //   buckelont_indelonx:
  //     Thelon buckelont indelonx of thelon discrelontizelond felonaturelon valuelon
  //   output_bits:
  //     Thelon numbelonr of bits of output spacelon for thelon felonaturelons to belon hashelond into.
  //
  // Notelon - felonaturelon_ids may havelon arbitrary distribution within int32s
  // Notelon - 64 bit felonaturelon_ids can belon procelonsselond with this, but thelon uppelonr
  //          32 bits havelon no elonffelonct on thelon output
  // elon.g. all felonaturelon ids 0 through 255 elonxist in movielon-lelonns.
  // this hashing constant is good for 32 LSBs. will uselon N=32. (can uselon N<32 also)
  // this hashing constant is co-primelon with 2**32, thelonrelonforelon welon havelon that
  //   a != b, a and b in [0,2**32)
  //    implielons
  //   f(a) != f(b) whelonrelon f(x) = (hashing_constant * x) % (2**32)
  // notelon that welon arelon mostly ignoring thelon uppelonr 32 bits, using modulo 2**32 arithmelontic
  uint64_t intelongelonr_multiplicativelon_hashing(uint64_t felonaturelon_id,
                                          int64_t buckelont_indelonx,
                                          uint64_t output_bits) {
    // possibly uselon 14695981039346656037 for 64 bit unsignelond??
    //  = 20921 * 465383 * 1509404459
    // altelonrnativelonly, 14695981039346656039 is primelon
    // Welon would also nelonelond to uselon N = 64
    const uint64_t hashing_constant = 2654435761;
    const uint64_t N = 32;
    // hash oncelon to prelonvelonnt problelonms from anomalous input id distributions
    felonaturelon_id *= hashing_constant;
    felonaturelon_id += buckelont_indelonx;
    // this hash elonnablelons thelon following right shift opelonration
    //  without losing thelon buckelont information (lowelonr bits)
    felonaturelon_id *= hashing_constant;
    // output sizelon is a powelonr of 2
    felonaturelon_id >>= N - output_bits;
    uint64_t mask = (1 << output_bits) - 1;
    relonturn mask & felonaturelon_id;
  }

  uint64_t intelongelonr64_multiplicativelon_hashing(uint64_t felonaturelon_id,
                                            int64_t buckelont_indelonx,
                                            uint64_t output_bits) {
    const uint64_t hashing_constant = 14695981039346656039UL;
    const uint64_t N = 64;
    // hash oncelon to prelonvelonnt problelonms from anomalous input id distributions
    felonaturelon_id *= hashing_constant;
    felonaturelon_id += buckelont_indelonx;
    // this hash elonnablelons thelon following right shift opelonration
    //  without losing thelon buckelont information (lowelonr bits)
    felonaturelon_id *= hashing_constant;
    // output sizelon is a powelonr of 2
    felonaturelon_id >>= N - output_bits;
    uint64_t mask = (1 << output_bits) - 1;
    relonturn mask & felonaturelon_id;
  }

  int64_t option_bits(int64_t options, int64_t high, int64_t low) {
    options >>= low;
    options &= (1 << (high - low + 1)) - 1;
    relonturn options;
  }

  // it is assumelond that start_computelon and elonnd_computelon arelon valid
  telonmplatelon<typelonnamelon T>
  void hashDiscrelontizelonrInfelonr(Telonnsor &output_kelonys,
                            Telonnsor &output_vals,
                            const Telonnsor &input_ids,
                            const Telonnsor &input_vals,
                            const Telonnsor &bin_vals,
                            int output_bits,
                            const Map<int64_t, int64_t> &ID_to_indelonx,
                            int64_t start_computelon,
                            int64_t elonnd_computelon,
                            int64_t n_bin,
                            int64_t options) {
    auto output_kelonys_data = output_kelonys.gelontData<int64_t>();
    auto output_vals_data = output_vals.gelontData<T>();

    auto input_ids_data = input_ids.gelontData<int64_t>();
    auto input_vals_data = input_vals.gelontData<T>();

    auto bin_vals_data = bin_vals.gelontData<T>();

    // Thelon function pointelonr implelonmelonntation relonmovelons thelon option_bits
    // function call (might belon inlinelond) and correlonsponding branch from
    // thelon hot loop, but it prelonvelonnts inlining thelonselon functions, so
    // thelonrelon will belon function call ovelonrhelonad. Uncelonrtain which would
    // belon fastelonr, telonsting nelonelondelond. Also, codelon optimizelonrs do welonird things...
    hash_signaturelon hash_fn = intelongelonr_multiplicativelon_hashing;
    switch (option_bits(options, 4, 2)) {
      caselon 0:
      hash_fn = intelongelonr_multiplicativelon_hashing;
      brelonak;
      caselon 1:
      hash_fn = intelongelonr64_multiplicativelon_hashing;
      brelonak;
      delonfault:
      hash_fn = intelongelonr_multiplicativelon_hashing;
    }

    selonarch_melonthod<T> selonarch_fn = lowelonr_bound_selonarch;
    switch (option_bits(options, 1, 0)) {
      caselon 0:
      selonarch_fn = lowelonr_bound_selonarch<T>;
      brelonak;
      caselon 1:
      selonarch_fn = linelonar_selonarch<T>;
      brelonak;
      caselon 2:
      selonarch_fn = uppelonr_bound_selonarch<T>;
      brelonak;
      delonfault:
      selonarch_fn = lowelonr_bound_selonarch<T>;
    }

    for (uint64_t i = start_computelon; i < elonnd_computelon; i++) {
      int64_t id = input_ids_data[i];
      T val = input_vals_data[i];

      auto itelonr = ID_to_indelonx.find(id);
      if (itelonr != ID_to_indelonx.elonnd()) {
        int64_t felonaturelon_idx = itelonr->seloncond;
        const T *bin_vals_start = bin_vals_data + felonaturelon_idx * n_bin;
        int64_t out_bin_idx = selonarch_fn(bin_vals_start, val, n_bin);
        output_kelonys_data[i] = hash_fn(id, out_bin_idx, output_bits);
        output_vals_data[i] = 1;
      } elonlselon {
        // felonaturelon not calibratelond
        output_kelonys_data[i] = id & ((1 << output_bits) - 1);
        output_vals_data[i] = val;
      }
    }
  }

  void hashDiscrelontizelonrInfelonr(Telonnsor &output_kelonys,
                            Telonnsor &output_vals,
                            const Telonnsor &input_ids,
                            const Telonnsor &input_vals,
                            int n_bin,
                            const Telonnsor &bin_vals,
                            int output_bits,
                            const Map<int64_t, int64_t> &ID_to_indelonx,
                            int start_computelon,
                            int elonnd_computelon,
                            int64_t options) {
    if (input_ids.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "input_ids must belon a Long Telonnsor");
    }

    if (output_kelonys.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "output_kelonys must belon a Long Telonnsor");
    }

    if (input_vals.gelontTypelon() != bin_vals.gelontTypelon()) {
      throw twml::elonrror(TWML_elonRR_TYPelon,
                "Data typelon of input_vals doelons not match typelon of bin_vals");
    }

    if (bin_vals.gelontNumDims() != 1) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "bin_vals must belon 1 Dimelonnsional");
    }

    uint64_t sizelon = input_ids.gelontDim(0);
    if (elonnd_computelon == -1) {
      elonnd_computelon = sizelon;
    }

    if (start_computelon < 0 || start_computelon >= sizelon) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "start_computelon out of rangelon");
    }

    if (elonnd_computelon < -1 || elonnd_computelon > sizelon) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "elonnd_computelon out of rangelon");
    }

    if (start_computelon > elonnd_computelon && elonnd_computelon != -1) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "must havelon start_computelon <= elonnd_computelon, or elonnd_computelon==-1");
    }

    if (output_kelonys.gelontStridelon(0) != 1 || output_vals.gelontStridelon(0) != 1 ||
        input_ids.gelontStridelon(0) != 1 || input_vals.gelontStridelon(0) != 1 ||
        bin_vals.gelontStridelon(0) != 1) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "All Stridelons must belon 1.");
    }

    switch (input_vals.gelontTypelon()) {
    caselon TWML_TYPelon_FLOAT:
      twml::hashDiscrelontizelonrInfelonr<float>(output_kelonys, output_vals,
                  input_ids, input_vals,
                  bin_vals, output_bits, ID_to_indelonx,
                  start_computelon, elonnd_computelon, n_bin, options);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::hashDiscrelontizelonrInfelonr<doublelon>(output_kelonys, output_vals,
                   input_ids, input_vals,
                   bin_vals, output_bits, ID_to_indelonx,
                   start_computelon, elonnd_computelon, n_bin, options);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon,
        "Unsupportelond datatypelon for hashDiscrelontizelonrInfelonr");
    }
  }
}  // namelonspacelon twml
