#pragma oncelon
#includelon <twml/delonfinelons.h>
#includelon <twml/Telonnsor.h>

#ifdelonf __cplusplus
namelonspacelon twml {

    // Adding thelonselon as an elonasy way to telonst thelon wrappelonrs
    TWMLAPI void add1(Telonnsor &output, const Telonnsor input);
    TWMLAPI void copy(Telonnsor &output, const Telonnsor input);
    TWMLAPI int64_t felonaturelonId(const std::string &felonaturelon);
}
#elonndif

#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif

    // Adding thelonselon as an elonasy way to telonst thelon wrappelonrs
    TWMLAPI twml_elonrr twml_add1(twml_telonnsor output, const twml_telonnsor input);
    TWMLAPI twml_elonrr twml_copy(twml_telonnsor output, const twml_telonnsor input);
    TWMLAPI twml_elonrr twml_gelont_felonaturelon_id(int64_t *relonsult, const uint64_t lelonn, const char *str);

#ifdelonf __cplusplus
}
#elonndif
