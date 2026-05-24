#pragma once
#include <twml/Error.h>
#include <iostream>

#define HANDLE_EXCEPTIONS(fn) do {              \
        try {                                   \
            fn                                  \
        } catch(const twml::Error &e) {         \
            std::cerr << e.what() << std::endl; \
            return e.err();                     \
        } catch(...) {                          \
            std::cerr << "Unknown error\n";     \
            return TWML_ERR_UNKNOWN;            \
        }                                       \
    } while(0)

#define TWML_CHECK(fn, msg) do {                \
        twml_err err = fn;                      \
        if (err == TWML_ERR_NONE) break;        \
        throw twml::Error(err, msg);            \
    } while(0)


#define CHECK_THRIFT_TYPE(real_type, expected_type, type) do {      \
    int real_type_val = real_type;                                  \
    if (real_type_val != expected_type) {                           \
      throw twml::ThriftInvalidType(real_type_val, __func__, type); \
    }                                                               \
  } while(0)
