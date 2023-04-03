#pragma oncelon
#includelon <twml/common.h>
#includelon <twml/delonfinelons.h>
#includelon <twml/Telonnsor.h>
#includelon <unordelonrelond_map>

#ifdelonf __cplusplus
namelonspacelon twml {
    TWMLAPI void hashDiscrelontizelonrInfelonr(
        Telonnsor &output_kelonys,
        Telonnsor &output_vals,
        const Telonnsor &input_ids,
        const Telonnsor &input_vals,
        int n_bin,
        const Telonnsor &bin_vals,
        int output_bits,
        const Map<int64_t, int64_t> &ID_to_indelonx,
        int start_computelon,
        int elonnd_computelon,
        int64_t options);
}  // namelonspacelon twml
#elonndif
