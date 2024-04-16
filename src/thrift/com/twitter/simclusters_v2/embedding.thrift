na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.embedd ng
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"
 nclude "com/tw ter/s mclusters_v2/onl ne_store.thr ft"

struct S mClusterW hScore {
  1: requ red  32 cluster d(personalDataType = ' nferred nterests')
  2: requ red double score(personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

struct TopS mClustersW hScore {
  1: requ red l st<S mClusterW hScore> topClusters
  2: requ red onl ne_store.ModelVers on modelVers on
}(pers sted = 'true', hasPersonalData = 'true')

struct  nternal dW hScore {
  1: requ red  dent f er. nternal d  nternal d
  2: requ red double score(personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

struct  nternal dEmbedd ng {
  1: requ red l st< nternal dW hScore> embedd ng
}(pers sted = 'true', hasPersonalData = 'true')

struct Semant cCoreEnt yW hScore {
  1: requ red  64 ent y d(personalDataType = 'Semant ccoreClass f cat on')
  2: requ red double score(personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

struct TopSemant cCoreEnt  esW hScore {
  1: requ red l st<Semant cCoreEnt yW hScore> topEnt  es
}(pers sted = 'true', hasPersonalData = 'true')

struct Pers stedFullCluster d {
  1: requ red onl ne_store.ModelVers on modelVers on
  2: requ red  32 cluster d(personalDataType = ' nferred nterests')
}(pers sted = 'true', hasPersonalData = 'true')

struct DayPart  onedCluster d {
  1: requ red  32 cluster d(personalDataType = ' nferred nterests')
  2: requ red str ng dayPart  on // format: yyyy-MM-dd
}

struct TopProducerW hScore {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red double score(personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

struct TopProducersW hScore {
  1: requ red l st<TopProducerW hScore> topProducers
}(pers sted = 'true', hasPersonalData = 'true')

struct T etW hScore {
  1: requ red  64 t et d(personalDataType = 'T et d')
  2: requ red double score(personalDataType = 'Engage ntScore')
}(pers sted = 'true', hasPersonalData = 'true')

struct T etsW hScore {
  1: requ red l st<T etW hScore> t ets
}(pers sted = 'true', hasPersonalData = 'true')

struct T etTopKT etsW hScore {
  1: requ red  64 t et d(personalDataType = 'T et d')
  2: requ red T etsW hScore topkT etsW hScore
}(pers sted = 'true', hasPersonalData = 'true')

/**
  * T  gener c S mClustersEmbedd ng for onl ne long-term storage and real-t   calculat on.
  * Use S mClustersEmbedd ng d as t  only  dent f er.
  * Warn ng: Doesn't  nclude model vers on and embedd ng type  n t  value struct.
  **/
struct S mClustersEmbedd ng {
  1: requ red l st<S mClusterW hScore> embedd ng
}(pers sted = 'true', hasPersonalData = 'true')

struct S mClustersEmbedd ngW hScore {
  1: requ red S mClustersEmbedd ng embedd ng
  2: requ red double score
}(pers sted = 'true', hasPersonalData = 'false')

/**
  * T   s t  recom nded structure for aggregat ng embedd ngs w h t   decay - t   tadata
  * stores t   nformat on needed for decayed aggregat on.
  **/
struct S mClustersEmbedd ngW h tadata {
  1: requ red S mClustersEmbedd ng embedd ng
  2: requ red S mClustersEmbedd ng tadata  tadata
}(hasPersonalData = 'true')

struct S mClustersEmbedd ng dW hScore {
  1: requ red  dent f er.S mClustersEmbedd ng d  d
  2: requ red double score
}(pers sted = 'true', hasPersonalData = 'false')

struct S mClustersMult Embedd ngByValues {
  1: requ red l st<S mClustersEmbedd ngW hScore> embedd ngs
}(pers sted = 'true', hasPersonalData = 'false')

struct S mClustersMult Embedd ngBy ds {
  1: requ red l st<S mClustersEmbedd ng dW hScore>  ds
}(pers sted = 'true', hasPersonalData = 'false')

/**
 * Gener c S mClusters Mult ple Embedd ngs. T   dent f er.S mClustersMult Embedd ng d  s t  key of
 * t  mult ple embedd ng.
 **/
un on S mClustersMult Embedd ng {
  1: S mClustersMult Embedd ngByValues values
  2: S mClustersMult Embedd ngBy ds  ds
}(pers sted = 'true', hasPersonalData = 'false')

/**
  * T   tadata of a S mClustersEmbedd ng. T  updatedCount represent t  vers on of t  Embedd ng.
  * For t et embedd ng, t  updatedCount  s sa /close to t  favor e count.
  **/
struct S mClustersEmbedd ng tadata {
  1: opt onal  64 updatedAtMs
  2: opt onal  64 updatedCount
}(pers sted = 'true', hasPersonalData = 'true')

/**
  * T  data structure for Pers stentS mClustersEmbedd ng Store
  **/
struct Pers stentS mClustersEmbedd ng {
  1: requ red S mClustersEmbedd ng embedd ng
  2: requ red S mClustersEmbedd ng tadata  tadata
}(pers sted = 'true', hasPersonalData = 'true')

/**
  * T  data structure for t  Mult  Model Pers stentS mClustersEmbedd ng Store
  **/
struct Mult ModelPers stentS mClustersEmbedd ng {
  1: requ red map<onl ne_store.ModelVers on, Pers stentS mClustersEmbedd ng> mult ModelPers stentS mClustersEmbedd ng
}(pers sted = 'true', hasPersonalData = 'true')
