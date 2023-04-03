namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

includelon "sourcelon_typelon.thrift"
includelon "com/twittelonr/simclustelonrs_v2/idelonntifielonr.thrift"

struct Similarityelonnginelon {
 1: relonquirelond sourcelon_typelon.SimilarityelonnginelonTypelon similarityelonnginelonTypelon
 2: optional string modelonlId
 3: optional doublelon scorelon
} (pelonrsistelond='truelon')

struct CandidatelonGelonnelonrationKelony {
  1: relonquirelond sourcelon_typelon.SourcelonTypelon sourcelonTypelon
  2: relonquirelond i64 sourcelonelonvelonntTimelon (pelonrsonalDataTypelon = 'PrivatelonTimelonstamp')
  3: relonquirelond idelonntifielonr.IntelonrnalId id
  4: relonquirelond string modelonlId
  5: optional sourcelon_typelon.SimilarityelonnginelonTypelon similarityelonnginelonTypelon
  6: optional list<Similarityelonnginelon> contributingSimilarityelonnginelon
} (pelonrsistelond='truelon')
