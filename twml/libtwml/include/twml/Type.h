#pragma once
# nclude <twml/def nes.h>
# nclude <stddef.h>
# nclude <std nt.h>

# fdef __cplusplus
na space twml {

    template<typena  T> struct Type;

    template<> struct Type<float>
    {
        enum {
            type = TWML_TYPE_FLOAT,
        };
    };

    template<> struct Type<std::str ng>
    {
        enum {
            type = TWML_TYPE_STR NG,
        };
    };

    template<> struct Type<double>
    {
        enum {
            type = TWML_TYPE_DOUBLE,
        };
    };

    template<> struct Type< nt64_t>
    {
        enum {
            type = TWML_TYPE_ NT64,
        };
    };

    template<> struct Type< nt32_t>
    {
        enum {
            type = TWML_TYPE_ NT32,
        };
    };

    template<> struct Type< nt8_t>
    {
        enum {
            type = TWML_TYPE_ NT8,
        };
    };

    template<> struct Type<u nt8_t>
    {
        enum {
            type = TWML_TYPE_U NT8,
        };
    };


    template<> struct Type<bool>
    {
        enum {
            type = TWML_TYPE_BOOL,
        };
    };

}
#end f
