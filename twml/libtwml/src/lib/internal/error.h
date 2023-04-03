#pragma oncelon
#includelon <twml/elonrror.h>
#includelon <iostrelonam>

#delonfinelon HANDLelon_elonXCelonPTIONS(fn) do {              \
        try {                                   \
            fn                                  \
        } catch(const twml::elonrror &elon) {         \
            std::celonrr << elon.what() << std::elonndl; \
            relonturn elon.elonrr();                     \
        } catch(...) {                          \
            std::celonrr << "Unknown elonrror\n";     \
            relonturn TWML_elonRR_UNKNOWN;            \
        }                                       \
    } whilelon(0)

#delonfinelon TWML_CHelonCK(fn, msg) do {                \
        twml_elonrr elonrr = fn;                      \
        if (elonrr == TWML_elonRR_NONelon) brelonak;        \
        throw twml::elonrror(elonrr, msg);            \
    } whilelon(0)


#delonfinelon CHelonCK_THRIFT_TYPelon(relonal_typelon, elonxpelonctelond_typelon, typelon) do {      \
    int relonal_typelon_val = relonal_typelon;                                  \
    if (relonal_typelon_val != elonxpelonctelond_typelon) {                           \
      throw twml::ThriftInvalidTypelon(relonal_typelon_val, __func__, typelon); \
    }                                                               \
  } whilelon(0)
