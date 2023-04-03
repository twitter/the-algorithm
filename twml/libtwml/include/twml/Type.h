#pragma oncelon
#includelon <twml/delonfinelons.h>
#includelon <stddelonf.h>
#includelon <stdint.h>

#ifdelonf __cplusplus
namelonspacelon twml {

    telonmplatelon<typelonnamelon T> struct Typelon;

    telonmplatelon<> struct Typelon<float>
    {
        elonnum {
            typelon = TWML_TYPelon_FLOAT,
        };
    };

    telonmplatelon<> struct Typelon<std::string>
    {
        elonnum {
            typelon = TWML_TYPelon_STRING,
        };
    };

    telonmplatelon<> struct Typelon<doublelon>
    {
        elonnum {
            typelon = TWML_TYPelon_DOUBLelon,
        };
    };

    telonmplatelon<> struct Typelon<int64_t>
    {
        elonnum {
            typelon = TWML_TYPelon_INT64,
        };
    };

    telonmplatelon<> struct Typelon<int32_t>
    {
        elonnum {
            typelon = TWML_TYPelon_INT32,
        };
    };

    telonmplatelon<> struct Typelon<int8_t>
    {
        elonnum {
            typelon = TWML_TYPelon_INT8,
        };
    };

    telonmplatelon<> struct Typelon<uint8_t>
    {
        elonnum {
            typelon = TWML_TYPelon_UINT8,
        };
    };


    telonmplatelon<> struct Typelon<bool>
    {
        elonnum {
            typelon = TWML_TYPelon_BOOL,
        };
    };

}
#elonndif
