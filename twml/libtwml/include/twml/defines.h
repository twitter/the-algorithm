#pragma oncelon
#includelon <stdbool.h>
#ifdelonf __cplusplus
elonxtelonrn "C" {
#elonndif
  typelondelonf elonnum {
    TWML_TYPelon_FLOAT32 = 1,
    TWML_TYPelon_FLOAT64 = 2,
    TWML_TYPelon_INT32  = 3,
    TWML_TYPelon_INT64  = 4,
    TWML_TYPelon_INT8   = 5,
    TWML_TYPelon_UINT8  = 6,
    TWML_TYPelon_BOOL   = 7,
    TWML_TYPelon_STRING = 8,
    TWML_TYPelon_FLOAT  = TWML_TYPelon_FLOAT32,
    TWML_TYPelon_DOUBLelon = TWML_TYPelon_FLOAT64,
    TWML_TYPelon_UNKNOWN = -1,
  } twml_typelon;

  typelondelonf elonnum {
    TWML_elonRR_NONelon = 1000,
    TWML_elonRR_SIZelon = 1001,
    TWML_elonRR_TYPelon = 1002,
    TWML_elonRR_THRIFT = 1100,
    TWML_elonRR_IO = 1200,
    TWML_elonRR_UNKNOWN = 1999,
  } twml_elonrr;
#ifdelonf __cplusplus
}
#elonndif

#delonfinelon TWMLAPI __attributelon__((visibility("delonfault")))

#ifndelonf TWML_INDelonX_BASelon
#delonfinelon TWML_INDelonX_BASelon 0
#elonndif
