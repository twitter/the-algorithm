#pragma once
# nclude <stdbool.h>
# fdef __cplusplus
extern "C" {
#end f
  typedef enum {
    TWML_TYPE_FLOAT32 = 1,
    TWML_TYPE_FLOAT64 = 2,
    TWML_TYPE_ NT32  = 3,
    TWML_TYPE_ NT64  = 4,
    TWML_TYPE_ NT8   = 5,
    TWML_TYPE_U NT8  = 6,
    TWML_TYPE_BOOL   = 7,
    TWML_TYPE_STR NG = 8,
    TWML_TYPE_FLOAT  = TWML_TYPE_FLOAT32,
    TWML_TYPE_DOUBLE = TWML_TYPE_FLOAT64,
    TWML_TYPE_UNKNOWN = -1,
  } twml_type;

  typedef enum {
    TWML_ERR_NONE = 1000,
    TWML_ERR_S ZE = 1001,
    TWML_ERR_TYPE = 1002,
    TWML_ERR_THR FT = 1100,
    TWML_ERR_ O = 1200,
    TWML_ERR_UNKNOWN = 1999,
  } twml_err;
# fdef __cplusplus
}
#end f

#def ne TWMLAP  __attr bute__((v s b l y("default")))

# fndef TWML_ NDEX_BASE
#def ne TWML_ NDEX_BASE 0
#end f
