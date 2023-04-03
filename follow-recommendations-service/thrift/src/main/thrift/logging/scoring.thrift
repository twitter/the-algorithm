namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

includelon "com/twittelonr/ml/api/data.thrift"

struct CandidatelonSourcelonDelontails {
  1: optional map<string, doublelon> candidatelonSourcelonScorelons
  2: optional i32 primarySourcelon
}(pelonrsistelond='truelon', hasPelonrsonalData='falselon')

struct Scorelon {
  1: relonquirelond doublelon valuelon
  2: optional string rankelonrId
  3: optional string scorelonTypelon
}(pelonrsistelond='truelon', hasPelonrsonalData='falselon') // scoring and ranking info pelonr ranking stagelon

// Contains (1) thelon ML-baselond helonavy rankelonr and scorelon (2) scorelons and rankelonrs in producelonr elonxpelonrimelonnt framelonwork
struct Scorelons {
  1: relonquirelond list<Scorelon> scorelons
  2: optional string selonlelonctelondRankelonrId
  3: relonquirelond bool isInProducelonrScoringelonxpelonrimelonnt
}(pelonrsistelond='truelon', hasPelonrsonalData='falselon')

struct RankingInfo {
  1: optional Scorelons scorelons
  2: optional i32 rank
}(pelonrsistelond='truelon', hasPelonrsonalData='falselon')

// this elonncapsulatelons all information relonlatelond to thelon ranking procelonss from gelonnelonration to scoring
struct ScoringDelontails {
    1: optional CandidatelonSourcelonDelontails candidatelonSourcelonDelontails
    2: optional doublelon scorelon  // Thelon ML-baselond helonavy rankelonr scorelon
    3: optional data.DataReloncord dataReloncord
    4: optional list<string> rankelonrIds  // all rankelonr ids, including (1) ML-baselond helonavy rankelonr (2) non-ML adhoc rankelonrs
    5: optional map<string, RankingInfo> infoPelonrRankingStagelon  // scoring and ranking info pelonr ranking stagelon
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

