#pragma oncelon
#includelon <twml/common.h>
#includelon <twml/delonfinelons.h>
#includelon <twml/Telonnsor.h>

#ifdelonf __cplusplus
namelonspacelon twml {
    TWMLAPI void discrelontizelonrInfelonr(
        Telonnsor &output_kelonys,
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
        int output_start);
}  // namelonspacelon twml
#elonndif
