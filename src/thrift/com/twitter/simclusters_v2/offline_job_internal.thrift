na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.offl ne_job_ nternal
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "com/tw ter/algeb rd_ nternal/algeb rd.thr ft"

// For  nternal usage only. Ma nly for offl ne_evaluat on.
// Deprecated. Please use 'onl ne_store/ModelVers on'
enum Pers stedModelVers on {
  MODEL_20M_145K_dec11 = 1,
  MODEL_20M_145K_updated = 2,
  MODEL_20M_145K_2020 = 3,
  RESERVED_4 = 4,
  RESERVED_5 = 5
}(pers sted = 'true', hasPersonalData = 'false')

enum Pers stedScoreType {
  NORMAL ZED_FAV_8_HR_HALF_L FE = 1,
  NORMAL ZED_FOLLOW_8_HR_HALF_L FE = 2,
  NORMAL ZED_LOG_FAV_8_HR_HALF_L FE = 3,
  RESERVED_4 = 4,
  RESERVED_5 = 5
}(pers sted = 'true', hasPersonalData = 'false')

struct Pers stedScores {
  1: opt onal algeb rd.DecayedValue score
}(pers sted = 'true', hasPersonalData = 'false')

struct T etAndClusterScores {
  1: requ red  64 t et d(personalDataType = 'T et d')
  2: requ red  32 cluster d(personalDataType = ' nferred nterests')
  3: requ red Pers stedModelVers on modelVers on
  4: requ red Pers stedScores scores(personalDataType = 'Engage ntScore')
  5: opt onal Pers stedScoreType scoreType
}(pers sted="true", hasPersonalData = 'true')

struct T etTopKClustersW hScores {
  1: requ red  64 t et d(personalDataType = 'T et d')
  2: requ red Pers stedModelVers on modelVers on
  3: requ red map< 32, Pers stedScores> topKClusters(personalDataTypeKey = ' nferred nterests')
  4: opt onal Pers stedScoreType scoreType
}(pers sted="true", hasPersonalData = 'true')

struct ClusterTopKT etsW hScores {
  1: requ red  32 cluster d(personalDataType = ' nferred nterests')
  2: requ red Pers stedModelVers on modelVers on
  3: requ red map< 64, Pers stedScores> topKT ets(personalDataTypeKey = 'T et d')
  4: opt onal Pers stedScoreType scoreType
}(pers sted = 'true', hasPersonalData = 'true')

struct QueryAndClusterScores {
  1: requ red str ng query(personalDataType = 'SearchQuery')
  2: requ red  32 cluster d
  3: requ red Pers stedModelVers on modelVers on
  4: requ red Pers stedScores scores
}(pers sted = 'true', hasPersonalData = 'true')

struct QueryTopKClustersW hScores {
  1: requ red str ng query(personalDataType = 'SearchQuery')
  2: requ red Pers stedModelVers on modelVers on
  3: requ red map< 32, Pers stedScores> topKClusters
}(pers sted = 'true', hasPersonalData = 'true')
