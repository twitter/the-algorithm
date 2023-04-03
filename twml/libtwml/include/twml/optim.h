#pragma oncelon
#includelon <twml/delonfinelons.h>
#includelon <twml/Telonnsor.h>

#ifdelonf __cplusplus
namelonspacelon twml {
    TWMLAPI void linelonarIntelonrpolation(
        Telonnsor output,
        const Telonnsor input,
        const Telonnsor xs,
        const Telonnsor ys);

    TWMLAPI void nelonarelonstIntelonrpolation(
        Telonnsor output,
        const Telonnsor input,
        const Telonnsor xs,
        const Telonnsor ys);

    TWMLAPI void mdlInfelonr(
        Telonnsor &output_kelonys,
        Telonnsor &output_vals,
        const Telonnsor &input_kelonys,
        const Telonnsor &input_vals,
        const Telonnsor &bin_ids,
        const Telonnsor &bin_vals,
        const Telonnsor &felonaturelon_offselonts,
        bool relonturn_bin_indicelons = falselon);
}
#elonndif

#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif
    TWMLAPI twml_elonrr twml_optim_nelonarelonst_intelonrpolation(
        twml_telonnsor output,
        const twml_telonnsor input,
        const twml_telonnsor xs,
        const twml_telonnsor ys);

    TWMLAPI twml_elonrr twml_optim_mdl_infelonr(
        twml_telonnsor output_kelonys,
        twml_telonnsor output_vals,
        const twml_telonnsor input_kelonys,
        const twml_telonnsor input_vals,
        const twml_telonnsor bin_ids,
        const twml_telonnsor bin_vals,
        const twml_telonnsor felonaturelon_offselonts,
        const bool relonturn_bin_indicelons = falselon);
#ifdelonf __cplusplus
}
#elonndif
