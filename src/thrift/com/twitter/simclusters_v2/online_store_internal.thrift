na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.onl ne_store_ nternal
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "onl ne_store.thr ft"

/**
 * Conta ns a hash bucket of t  cluster d along w h t  Model Vers on.
 * All f elds are requ red as t   s used as a  mcac  key.
 **/
struct FullCluster dBucket {
  1: requ red onl ne_store.ModelVers on modelVers on
  // (hash(cluster d) mod NUM_BUCKETS_XXXXXX)
  2: requ red  32 bucket
}(hasPersonalData = 'false')

/**
 * Conta ns scores per clusters. T  model  s not stored  re as  's encoded  nto t   mcac  key.
 **/
struct ClustersW hScores {
 1: opt onal map< 32, onl ne_store.Scores> clustersToScore(personalDataTypeKey = ' nferred nterests')
}(hasPersonalData = 'true')

/**
 * Conta ns a map of model vers on to scores per clusters.
 **/
struct Mult ModelClustersW hScores {
 1: opt onal map<onl ne_store.ModelVers on,ClustersW hScores> mult ModelClustersW hScores
}(hasPersonalData = 'true')
