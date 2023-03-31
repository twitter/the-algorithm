#pragma once
#include <twml/defines.h>
#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
namespace twml {

    template<typename T> struct Type;

    template<> struct Type<float>
    {
        enum {
            type = TWML_TYPE_FLOAT,
        };
    };

    template<> struct Type<std::string>
    {
        enum {
            type = TWML_TYPE_STRING,
        };
    };

    template<> struct Type<double>
    {
        enum {
            type = TWML_TYPE_DOUBLE,
        };
    };

    template<> struct Type<int64_t>
    {
        enum {
            type = TWML_TYPE_INT64,
        };
    };

    template<> struct Type<int32_t>
    {
        enum {
            type = TWML_TYPE_INT32,
        };
    };

    template<> struct Type<int8_t>
    {
        enum {
            type = TWML_TYPE_INT8,
        };
    };

    template<> struct Type<uint8_t>
    {
        enum {
            type = TWML_TYPE_UINT8,
        };
    };


    template<> struct Type<bool>
    {
        enum {
            type = TWML_TYPE_BOOL,
        };
    };

}
#endif
