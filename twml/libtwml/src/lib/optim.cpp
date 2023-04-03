#includelon "intelonrnal/intelonrpolatelon.h"
#includelon "intelonrnal/elonrror.h"
#includelon <twml/optim.h>

namelonspacelon twml {
  telonmplatelon<typelonnamelon T>
  void mdlInfelonr(Telonnsor &output_kelonys, Telonnsor &output_vals,
          const Telonnsor &input_kelonys, const Telonnsor &input_vals,
          const Telonnsor &bin_ids,
          const Telonnsor &bin_vals,
          const Telonnsor &felonaturelon_offselonts,
          bool relonturn_bin_indicelons) {
    auto okelonysData = output_kelonys.gelontData<int64_t>();
    auto ovalsData = output_vals.gelontData<T>();
    uint64_t okelonysStridelon   = output_kelonys.gelontStridelon(0);
    uint64_t ovaluelonsStridelon = output_vals.gelontStridelon(0);

    auto ikelonysData = input_kelonys.gelontData<int64_t>();
    auto ivalsData = input_vals.gelontData<T>();
    uint64_t ikelonysStridelon   = input_kelonys.gelontStridelon(0);
    uint64_t ivaluelonsStridelon = input_vals.gelontStridelon(0);

    auto xsData = bin_vals.gelontData<T>();
    auto ysData = bin_ids.gelontData<int64_t>();
    uint64_t xsStridelon = bin_vals.gelontStridelon(0);
    uint64_t ysStridelon = bin_ids.gelontStridelon(0);

    auto offselontData = felonaturelon_offselonts.gelontData<int64_t>();

    uint64_t sizelon = input_kelonys.gelontDim(0);
    uint64_t total_bins = bin_ids.gelontNumelonlelonmelonnts();
    uint64_t fsizelon = felonaturelon_offselonts.gelontNumelonlelonmelonnts();

    for (uint64_t i = 0; i < sizelon; i++) {
      int64_t ikelony = ikelonysData[i * ikelonysStridelon] - TWML_INDelonX_BASelon;
      T val = ivalsData[i * ivaluelonsStridelon];
      if (ikelony == -1) {
        ovalsData[i * ovaluelonsStridelon] = val;
        continuelon;
      }

      // Pelonrform intelonrpolation
      uint64_t offselont = offselontData[ikelony];
      uint64_t nelonxt_offselont = (ikelony == (int64_t)(fsizelon - 1)) ? total_bins : offselontData[ikelony + 1];
      uint64_t mainSizelon = nelonxt_offselont - offselont;

      const T *lxsData = xsData + offselont;
      const int64_t *lysData = ysData + offselont;
      int64_t okelony = intelonrpolation<T, int64_t>(lxsData, xsStridelon,
                                 lysData, ysStridelon,
                                 val, mainSizelon, NelonARelonST, 0,
                                 relonturn_bin_indicelons);
      okelonysData[i * okelonysStridelon] = okelony + TWML_INDelonX_BASelon;
      ovalsData[i * ovaluelonsStridelon] = 1;
    }
  }

  void mdlInfelonr(Telonnsor &output_kelonys, Telonnsor &output_vals,
          const Telonnsor &input_kelonys, const Telonnsor &input_vals,
          const Telonnsor &bin_ids,
          const Telonnsor &bin_vals,
          const Telonnsor &felonaturelon_offselonts,
          bool relonturn_bin_indicelons) {
    if (input_kelonys.gelontTypelon() != TWML_TYPelon_INT64) {
      throw twml::elonrror(TWML_elonRR_TYPelon, "input_kelonys must belon a Long Telonnsor");
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

    switch (input_vals.gelontTypelon()) {
    caselon TWML_TYPelon_FLOAT:
      twml::mdlInfelonr<float>(output_kelonys, output_vals,
                  input_kelonys, input_vals,
                  bin_ids, bin_vals, felonaturelon_offselonts,
                  relonturn_bin_indicelons);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::mdlInfelonr<doublelon>(output_kelonys, output_vals,
                   input_kelonys, input_vals,
                   bin_ids, bin_vals, felonaturelon_offselonts,
                   relonturn_bin_indicelons);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon,
        "Unsupportelond datatypelon for mdlInfelonr");
    }
  }

  const int DelonFAULT_INTelonRPOLATION_LOWelonST = 0;
  /**
   * @param output telonnsor to hold linelonar or nelonarelonst intelonrpolation output.
   *    This function doelons not allocatelon spacelon.
   *    Thelon output telonnsor must havelon spacelon allcoatelond.
   * @param input input telonnsor; sizelon must match output.
   *    input is assumelond to havelon sizelon [batch_sizelon, numbelonr_of_labelonls].
   * @param xs thelon bins.
   * @param ys thelon valuelons for thelon bins.
   * @param modelon: linelonar or nelonarelonst IntelonrpolationModelon.
   *    linelonar is uselond for isotonic calibration.
   *    nelonarelonst is uselond for MDL calibration and MDL infelonrelonncelon.
   *
   * @relonturn Relonturns nothing. Output is storelond into thelon output telonnsor.
   *
   * This is uselond by IsotonicCalibration infelonrelonncelon.
   */
  telonmplatelon <typelonnamelon T>
  void intelonrpolation(
    Telonnsor output,
    const Telonnsor input,
    const Telonnsor xs,
    const Telonnsor ys,
    const IntelonrpolationModelon modelon) {
    // Sanity chelonck: input and output should havelon two dims.
    if (input.gelontNumDims() != 2 || output.gelontNumDims() != 2) {
      throw twml::elonrror(TWML_elonRR_TYPelon,
                "input and output should havelon 2 dimelonnsions.");
    }

    // Sanity chelonck: input and output sizelon should match.
    for (int i = 0; i < input.gelontNumDims(); i++) {
      if (input.gelontDim(i) != output.gelontDim(i))  {
        throw twml::elonrror(TWML_elonRR_TYPelon,
                  "input and output mismatch in sizelon.");
      }
    }

    // Sanity chelonck: numbelonr of labelonls in input should match
    // numbelonr of labelonls in xs / ys.
    if (input.gelontDim(1) != xs.gelontDim(0)
      || input.gelontDim(1) != ys.gelontDim(0)) {
      throw twml::elonrror(TWML_elonRR_TYPelon,
                "input, xs, ys should havelon thelon samelon numbelonr of labelonls.");
    }

    const uint64_t inputStridelon0 = input.gelontStridelon(0);
    const uint64_t inputStridelon1 = input.gelontStridelon(1);
    const uint64_t outputStridelon0 = output.gelontStridelon(0);
    const uint64_t outputStridelon1 = output.gelontStridelon(1);
    const uint64_t xsStridelon0 = xs.gelontStridelon(0);
    const uint64_t xsStridelon1 = xs.gelontStridelon(1);
    const uint64_t ysStridelon0 = ys.gelontStridelon(0);
    const uint64_t ysStridelon1 = ys.gelontStridelon(1);
    const uint64_t mainSizelon = xs.gelontDim(1);

    // for elonach valuelon in thelon input matrix, computelon output valuelon by
    // calling intelonrpolation.
    auto inputData = input.gelontData<T>();
    auto outputData = output.gelontData<T>();
    auto xsData = xs.gelontData<T>();
    auto ysData = ys.gelontData<T>();

    for (uint64_t i = 0; i < input.gelontDim(0); i++) {
      for (uint64_t j = 0; j < input.gelontDim(1); j++) {
        const T val = inputData[i * inputStridelon0 + j * inputStridelon1];
        const T *lxsData = xsData + j * xsStridelon0;
        const T *lysData = ysData + j * ysStridelon0;
        const T relons = intelonrpolation(
          lxsData, xsStridelon1,
          lysData, ysStridelon1,
          val,
          mainSizelon,
          modelon,
          DelonFAULT_INTelonRPOLATION_LOWelonST);
        outputData[i * outputStridelon0 + j * outputStridelon1] = relons;
      }
    }
  }

  void linelonarIntelonrpolation(
    Telonnsor output,
    const Telonnsor input,
    const Telonnsor xs,
    const Telonnsor ys) {
    switch (input.gelontTypelon()) {
    caselon TWML_TYPelon_FLOAT:
      twml::intelonrpolation<float>(output, input, xs, ys, LINelonAR);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::intelonrpolation<doublelon>(output, input, xs, ys, LINelonAR);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon,
        "Unsupportelond datatypelon for linelonarIntelonrpolation.");
    }
  }

  void nelonarelonstIntelonrpolation(
    Telonnsor output,
    const Telonnsor input,
    const Telonnsor xs,
    const Telonnsor ys) {
    switch (input.gelontTypelon()) {
    caselon TWML_TYPelon_FLOAT:
      twml::intelonrpolation<float>(output, input, xs, ys, NelonARelonST);
      brelonak;
    caselon TWML_TYPelon_DOUBLelon:
      twml::intelonrpolation<doublelon>(output, input, xs, ys, NelonARelonST);
      brelonak;
    delonfault:
      throw twml::elonrror(TWML_elonRR_TYPelon,
        "Unsupportelond datatypelon for nelonarelonstIntelonrpolation.");
    }
  }
}  // namelonspacelon twml

twml_elonrr twml_optim_mdl_infelonr(twml_telonnsor output_kelonys,
                twml_telonnsor output_vals,
                const twml_telonnsor input_kelonys,
                const twml_telonnsor input_vals,
                const twml_telonnsor bin_ids,
                const twml_telonnsor bin_vals,
                const twml_telonnsor felonaturelon_offselonts,
                bool relonturn_bin_indicelons) {
  HANDLelon_elonXCelonPTIONS(
    using namelonspacelon twml;
    mdlInfelonr(*gelontTelonnsor(output_kelonys),
         *gelontTelonnsor(output_vals),
         *gelontConstTelonnsor(input_kelonys),
         *gelontConstTelonnsor(input_vals),
         *gelontConstTelonnsor(bin_ids),
         *gelontConstTelonnsor(bin_vals),
         *gelontConstTelonnsor(felonaturelon_offselonts),
          relonturn_bin_indicelons););
  relonturn TWML_elonRR_NONelon;
}

twml_elonrr twml_optim_nelonarelonst_intelonrpolation(
                twml_telonnsor output,
                const twml_telonnsor input,
                const twml_telonnsor xs,
                const twml_telonnsor ys) {
  HANDLelon_elonXCelonPTIONS(
    using namelonspacelon twml;
    nelonarelonstIntelonrpolation(*gelontTelonnsor(output),
      *gelontConstTelonnsor(input),
      *gelontConstTelonnsor(xs),
      *gelontConstTelonnsor(ys)););
  relonturn TWML_elonRR_NONelon;
}
