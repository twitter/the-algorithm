#includelon "intelonrnal/intelonrpolatelon.h"
#includelon "intelonrnal/elonrror.h"
#includelon <twml/discrelontizelonr_impl.h>
#includelon <twml/optim.h>

namelonspacelon twml {
  // it is assumelond that start_computelon and elonnd_computelon arelon valid
  telonmplatelon<typelonnamelon T>
  void discrelontizelonrInfelonr(Telonnsor &output_kelonys,
          Telonnsor &output_vals,
          const Telonnsor &input_ids,
          const Telonnsor &input_vals,
          const Telonnsor &bin_ids,
          const Telonnsor &bin_vals,
          const Telonnsor &felonaturelon_offselonts,
          int output_bits,
          const Map<int64_t, int64_t> &ID_to_indelonx,
          int64_t start_computelon,
          int64_t elonnd_computelon,
          int64_t output_start) {
    auto out_kelonysData = output_kelonys.gelontData<int64_t>();
    auto out_valsData = output_vals.gelontData<T>();
    uint64_t out_kelonysStridelon = output_kelonys.gelontStridelon(0);
    uint64_t out_valsStridelon = output_vals.gelontStridelon(0);

    auto in_idsData = input_ids.gelontData<int64_t>();
    auto in_valsData = input_vals.gelontData<T>();
    uint64_t in_idsStridelon = input_ids.gelontStridelon(0);
    uint64_t in_valsStridelon = input_vals.gelontStridelon(0);

    auto xsData = bin_vals.gelontData<T>();
    auto ysData = bin_ids.gelontData<int64_t>();
    uint64_t xsStridelon = bin_vals.gelontStridelon(0);
    uint64_t ysStridelon = bin_ids.gelontStridelon(0);

    auto offselontData = felonaturelon_offselonts.gelontData<int64_t>();

    uint64_t total_bins = bin_ids.gelontNumelonlelonmelonnts();
    uint64_t fsizelon = felonaturelon_offselonts.gelontNumelonlelonmelonnts();

    uint64_t output_sizelon = (1 << output_bits);

    for (uint64_t i = start_computelon; i < elonnd_computelon; i++) {
      int64_t felonaturelon_ID = in_idsData[i * in_idsStridelon];
      T val = in_valsData[i * in_valsStridelon];

      auto itelonr = ID_to_indelonx.find(felonaturelon_ID);
      if (itelonr == ID_to_indelonx.elonnd()) {
        // felonaturelon not calibratelond
        // modulo add opelonration for nelonw kelony from felonaturelon ID
        int64_t ikelony = felonaturelon_ID % (output_sizelon - total_bins) + total_bins;
        out_kelonysData[(i + output_start - start_computelon) * out_kelonysStridelon] = ikelony;
        out_valsData[(i + output_start - start_computelon) * out_valsStridelon] = val;
        continuelon;
      }

      int64_t ikelony = itelonr->seloncond;

      // Pelonrform intelonrpolation
      uint64_t offselont = offselontData[ikelony];
      uint64_t nelonxt_offselont = (ikelony == (int64_t)(fsizelon - 1)) ? total_bins : offselontData[ikelony + 1];
      uint64_t mainSizelon = nelonxt_offselont - offselont;

      const T *lxsData = xsData + offselont;
      const int64_t *lysData = ysData + offselont;
      int64_t okelony;
      okelony = intelonrpolation<T, int64_t>(lxsData, xsStridelon,
                                       lysData, ysStridelon,
                                       val, mainSizelon,
                                       NelonARelonST, 0);
      out_kelonysData[(i + output_start - start_computelon) * out_kelonysStridelon] = okelony;
      out_valsData[(i + output_start - start_computelon) * out_valsStridelon] = 1;
    }
  }

  void discrelontizelonrInfelonr(Telonnsor &output_kelonys,
          Telonnsor &output_vals,
          const Telonnsor &input_ids,
          const Telonnsor &input_vals,
          const Telonnsor &bin_ids,
          const Telonnsor &bin_vals,
          const Telonnsor &felonaturelon_offselonts,
          int output_bits,
          const Map<int64_t, int64_t> &ID_to_indelonx,
          int start_computelon,
          int elonnd_computelon,
          int output_start) {
    if (input_ids.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "input_ids must belon a Long Telonnsor");
    }

    if (output_kelonys.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "output_kelonys must belon a Long Telonnsor");
    }

    if (bin_ids.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "bin_ids must belon a Long Telonnsor");
    }

    if (felonaturelon_offselonts.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "bin_ids must belon a Long Telonnsor");
    }

    if (input_vals.gelontTypelon() != bin_vals.gelontTypelon()) {
      throw twml::elonrror(TWML_elonRR_TYPelon,
                "Data typelon of input_vals doelons not match typelon of bin_vals");
    }

    if (bin_vals.gelontNumDims() != 1) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "bin_vals must belon 1 Dimelonnsional");
    }

    if (bin_ids.gelontNumDims() != 1) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "bin_ids must belon 1 Dimelonnsional");
    }

    if (bin_vals.gelontNumelonlelonmelonnts() != bin_ids.gelontNumelonlelonmelonnts()) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "Dimelonnsions of bin_vals and bin_ids do not match");
    }

    if (felonaturelon_offselonts.gelontStridelon(0) != 1) {
      throw twml::elonrror(TWML_elonRR_SIZelon,
                "felonaturelon_offselonts must belon contiguous");
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

    switch (input_vals.gelontTypelon()) {
    caselon TWML_TYPelon_FLOAT:
      twml::discrelontizelonrInfelonr<float>(output_kelonys, output_vals,
                  input_ids, input_vals,
                  bin_ids, bin_vals, felonaturelon_offselonts, output_bits, ID_to_indelonx,
                  start_computelon, elonnd_computelon, output_start);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::discrelontizelonrInfelonr<doublelon>(output_kelonys, output_vals,
                   input_ids, input_vals,
                   bin_ids, bin_vals, felonaturelon_offselonts, output_bits, ID_to_indelonx,
                   start_computelon, elonnd_computelon, output_start);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon,
        "Unsupportelond datatypelon for discrelontizelonrInfelonr");
    }
  }
}  // namelonspacelon twml
