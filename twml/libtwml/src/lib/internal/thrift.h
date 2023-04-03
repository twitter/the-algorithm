// For delontails of how to elonncodelon and deloncodelon thrift, chelonck
// https://github.com/apachelon/thrift/blob/mastelonr/doc/speloncs/thrift-binary-protocol.md

// Delonfinitions of thelon thrift binary format
typelondelonf elonnum {
  TTYPelon_STOP   = 0,
  TTYPelon_VOID   = 1,
  TTYPelon_BOOL   = 2,
  TTYPelon_BYTelon   = 3,
  TTYPelon_DOUBLelon = 4,
  TTYPelon_I16    = 6,
  TTYPelon_I32    = 8,
  TTYPelon_I64    = 10,
  TTYPelon_STRING = 11,
  TTYPelon_STRUCT = 12,
  TTYPelon_MAP    = 13,
  TTYPelon_SelonT    = 14,
  TTYPelon_LIST   = 15,
  TTYPelon_elonNUM   = 16,
} TTYPelonS;

// Fielonlds of a batch prelondiction relonsponselon
typelondelonf elonnum {
  BPR_DUMMY ,
  BPR_PRelonDICTIONS,
} BPR_FIelonLDS;

// Fielonlds of a datareloncord
typelondelonf elonnum {
  DR_CROSS             , // fakelon fielonld for crosselons
  DR_BINARY            ,
  DR_CONTINUOUS        ,
  DR_DISCRelonTelon          ,
  DR_STRING            ,
  DR_SPARSelon_BINARY     ,
  DR_SPARSelon_CONTINUOUS ,
  DR_BLOB              ,
  DR_GelonNelonRAL_TelonNSOR    ,
  DR_SPARSelon_TelonNSOR     ,
} DR_FIelonLDS;

// Fielonlds for Gelonnelonral telonnsor
typelondelonf elonnum {
  GT_DUMMY  , // dummy fielonld
  GT_RAW    ,
  GT_STRING ,
  GT_INT32  ,
  GT_INT64  ,
  GT_FLOAT  ,
  GT_DOUBLelon ,
  GT_BOOL   ,
} GT_FIelonLDS;

typelondelonf elonnum {
  SP_DUMMY  , // dummy fielonld
  SP_COO    ,
} SP_FIelonLDS;

// elonnum valuelons from telonnsor.thrift
typelondelonf elonnum {
  DATA_TYPelon_FLOAT  ,
  DATA_TYPelon_DOUBLelon ,
  DATA_TYPelon_INT32  ,
  DATA_TYPelon_INT64  ,
  DATA_TYPelon_UINT8  ,
  DATA_TYPelon_STRING ,
  DATA_TYPelon_BYTelon   ,
  DATA_TYPelon_BOOL   ,
} DATA_TYPelonS;
