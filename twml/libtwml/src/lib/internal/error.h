#pragma once
# nclude <twml/Error.h>
# nclude < ostream>

#def ne HANDLE_EXCEPT ONS(fn) do {              \
        try {                                   \
            fn                                  \
        } catch(const twml::Error &e) {         \
            std::cerr << e.what() << std::endl; \
            return e.err();                     \
        } catch(...) {                          \
            std::cerr << "Unknown error\n";     \
            return TWML_ERR_UNKNOWN;            \
        }                                       \
    } wh le(0)

#def ne TWML_CHECK(fn, msg) do {                \
        twml_err err = fn;                      \
         f (err == TWML_ERR_NONE) break;        \
        throw twml::Error(err, msg);            \
    } wh le(0)


#def ne CHECK_THR FT_TYPE(real_type, expected_type, type) do {      \
     nt real_type_val = real_type;                                  \
     f (real_type_val != expected_type) {                           \
      throw twml::Thr ft nval dType(real_type_val, __func__, type); \
    }                                                               \
  } wh le(0)
