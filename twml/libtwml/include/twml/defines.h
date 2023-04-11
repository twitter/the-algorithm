#pragma once
#include <stdbool.h>
#ifdef __cplusplus
extern "C" {
#endif
  typedef enum {
    TWML_TYPE_FLOAT32 = 1,
    TWML_TYPE_FLOAT64 = 2,
    TWML_TYPE_INT32  = 3,
    TWML_TYPE_INT64  = 4,
    TWML_TYPE_INT8   = 5,
    TWML_TYPE_UINT8  = 6,
    TWML_TYPE_BOOL   = 7,
    TWML_TYPE_STRING = 8,
    TWML_TYPE_FLOAT  = TWML_TYPE_FLOAT32,
    TWML_TYPE_DOUBLE = TWML_TYPE_FLOAT64,
    TWML_TYPE_UNKNOWN = -1,
  } twml_type;

  typedef enum {
    TWML_ERR_NONE = 1000,
    TWML_ERR_SIZE = 1001,
    TWML_ERR_TYPE = 1002,
    TWML_ERR_THRIFT = 1100,
    TWML_ERR_IO = 1200,
    TWML_ERR_UNKNOWN = 1999,
  } twml_err;
#ifdef __cplusplus
}
#endif

#define TWMLAPI __attribute__((visibility("default")))

#ifndef TWML_INDEX_BASE
#define TWML_INDEX_BASE 0
#endif
