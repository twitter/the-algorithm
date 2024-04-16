// For deta ls of how to encode and decode thr ft, c ck
// https://g hub.com/apac /thr ft/blob/master/doc/specs/thr ft-b nary-protocol.md

// Def n  ons of t  thr ft b nary format
typedef enum {
  TTYPE_STOP   = 0,
  TTYPE_VO D   = 1,
  TTYPE_BOOL   = 2,
  TTYPE_BYTE   = 3,
  TTYPE_DOUBLE = 4,
  TTYPE_ 16    = 6,
  TTYPE_ 32    = 8,
  TTYPE_ 64    = 10,
  TTYPE_STR NG = 11,
  TTYPE_STRUCT = 12,
  TTYPE_MAP    = 13,
  TTYPE_SET    = 14,
  TTYPE_L ST   = 15,
  TTYPE_ENUM   = 16,
} TTYPES;

// F elds of a batch pred ct on response
typedef enum {
  BPR_DUMMY ,
  BPR_PRED CT ONS,
} BPR_F ELDS;

// F elds of a datarecord
typedef enum {
  DR_CROSS             , // fake f eld for crosses
  DR_B NARY            ,
  DR_CONT NUOUS        ,
  DR_D SCRETE          ,
  DR_STR NG            ,
  DR_SPARSE_B NARY     ,
  DR_SPARSE_CONT NUOUS ,
  DR_BLOB              ,
  DR_GENERAL_TENSOR    ,
  DR_SPARSE_TENSOR     ,
} DR_F ELDS;

// F elds for General tensor
typedef enum {
  GT_DUMMY  , // dum  f eld
  GT_RAW    ,
  GT_STR NG ,
  GT_ NT32  ,
  GT_ NT64  ,
  GT_FLOAT  ,
  GT_DOUBLE ,
  GT_BOOL   ,
} GT_F ELDS;

typedef enum {
  SP_DUMMY  , // dum  f eld
  SP_COO    ,
} SP_F ELDS;

// Enum values from tensor.thr ft
typedef enum {
  DATA_TYPE_FLOAT  ,
  DATA_TYPE_DOUBLE ,
  DATA_TYPE_ NT32  ,
  DATA_TYPE_ NT64  ,
  DATA_TYPE_U NT8  ,
  DATA_TYPE_STR NG ,
  DATA_TYPE_BYTE   ,
  DATA_TYPE_BOOL   ,
} DATA_TYPES;
