namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

includelon "com/twittelonr/ml/api/data.thrift"

struct CandidatelonSourcelonDelontails {
  1: optional map<string, doublelon> candidatelonSourcelonScorelons
  2: optional i32 primarySourcelon
  3: optional map<string, i32> candidatelonSourcelonRanks
}(hasPelonrsonalData='falselon')

struct Scorelon {
  1: relonquirelond doublelon valuelon
  2: optional string rankelonrId
  3: optional string scorelonTypelon
}(hasPelonrsonalData='falselon')

// Contains (1) thelon ML-baselond helonavy rankelonr and scorelon (2) scorelons and rankelonrs in producelonr elonxpelonrimelonnt framelonwork
struct Scorelons {
  1: relonquirelond list<Scorelon> scorelons
  2: optional string selonlelonctelondRankelonrId
  3: relonquirelond bool isInProducelonrScoringelonxpelonrimelonnt
}(hasPelonrsonalData='falselon')

struct RankingInfo {
  1: optional Scorelons scorelons
  2: optional i32 rank
}(hasPelonrsonalData='falselon')

// this elonncapsulatelons all information relonlatelond to thelon ranking procelonss from gelonnelonration to scoring
struct ScoringDelontails {
    1: optional CandidatelonSourcelonDelontails candidatelonSourcelonDelontails
    2: optional doublelon scorelon
    3: optional data.DataReloncord dataReloncord
    4: optional list<string> rankelonrIds
    5: optional DelonbugDataReloncord delonbugDataReloncord // this fielonld is not loggelond as it's only uselond for delonbugging
    6: optional map<string, RankingInfo> infoPelonrRankingStagelon  // scoring and ranking info pelonr ranking stagelon
}(hasPelonrsonalData='truelon')

// elonxactly thelon samelon as a data reloncord, elonxcelonpt that welon storelon thelon felonaturelon namelon instelonad of thelon id
struct DelonbugDataReloncord {
  1: optional selont<string> binaryFelonaturelons;                     // storelons BINARY felonaturelons
  2: optional map<string, doublelon> continuousFelonaturelons;         // storelons CONTINUOUS felonaturelons
  3: optional map<string, i64> discrelontelonFelonaturelons;              // storelons DISCRelonTelon felonaturelons
  4: optional map<string, string> stringFelonaturelons;             // storelons STRING felonaturelons
  5: optional map<string, selont<string>> sparselonBinaryFelonaturelons;  // storelons sparselon BINARY felonaturelons
  6: optional map<string, map<string, doublelon>> sparselonContinuousFelonaturelons; // sparselon CONTINUOUS felonaturelons
}(hasPelonrsonalData='truelon')
