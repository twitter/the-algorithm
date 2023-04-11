// For details of how to encode and decode thrift, check
// https://github.com/apache/thrift/blob/master/doc/specs/thrift-binary-protocol.md

// Definitions of the thrift binary format
typedef enum {
  TTYPE_STOP   = 0,
  TTYPE_VOID   = 1,
  TTYPE_BOOL   = 2,
  TTYPE_BYTE   = 3,
  TTYPE_DOUBLE = 4,
  TTYPE_I16    = 6,
  TTYPE_I32    = 8,
  TTYPE_I64    = 10,
  TTYPE_STRING = 11,
  TTYPE_STRUCT = 12,
  TTYPE_MAP    = 13,
  TTYPE_SET    = 14,
  TTYPE_LIST   = 15,
  TTYPE_ENUM   = 16,
} TTYPES;

// Fields of a batch prediction response
typedef enum {
  BPR_DUMMY ,
  BPR_PREDICTIONS,
} BPR_FIELDS;

// Fields of a datarecord
typedef enum {
  DR_CROSS             , // fake field for crosses
  DR_BINARY            ,
  DR_CONTINUOUS        ,
  DR_DISCRETE          ,
  DR_STRING            ,
  DR_SPARSE_BINARY     ,
  DR_SPARSE_CONTINUOUS ,
  DR_BLOB              ,
  DR_GENERAL_TENSOR    ,
  DR_SPARSE_TENSOR     ,
} DR_FIELDS;

// Fields for General tensor
typedef enum {
  GT_DUMMY  , // dummy field
  GT_RAW    ,
  GT_STRING ,
  GT_INT32  ,
  GT_INT64  ,
  GT_FLOAT  ,
  GT_DOUBLE ,
  GT_BOOL   ,
} GT_FIELDS;

typedef enum {
  SP_DUMMY  , // dummy field
  SP_COO    ,
} SP_FIELDS;

// Enum values from tensor.thrift
typedef enum {
  DATA_TYPE_FLOAT  ,
  DATA_TYPE_DOUBLE ,
  DATA_TYPE_INT32  ,
  DATA_TYPE_INT64  ,
  DATA_TYPE_UINT8  ,
  DATA_TYPE_STRING ,
  DATA_TYPE_BYTE   ,
  DATA_TYPE_BOOL   ,
} DATA_TYPES;
