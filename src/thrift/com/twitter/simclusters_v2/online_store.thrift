na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.onl ne_store
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "ent y.thr ft"
 nclude "com/tw ter/algeb rd_ nternal/algeb rd.thr ft"

/**
 * A S mClusters model vers on.
 **/
enum ModelVers on {
	MODEL_20M_145K_dec11 = 1, // DEPRECATED
	MODEL_20M_145K_updated = 2, // DEPRECATED
  MODEL_20M_145K_2020 = 3,
  RESERVED_4 = 4,
  RESERVED_5 = 5,
  RESERVED_6 = 6
}(pers sted = 'true', hasPersonalData = 'false')

/**
 * Un quely  dent f es a S mCluster. All f elds are requ red as t   s used as a  mcac  key.
 **/
struct FullCluster d {
  1: requ red ModelVers on modelVers on
  2: requ red  32 cluster d
}(pers sted='true', hasPersonalData = 'false')

/**
 * Conta ns a set of scores per cluster.
 **/
struct Scores {
  1: opt onal algeb rd.DecayedValue favClusterNormal zed8HrHalfL feScore
  2: opt onal algeb rd.DecayedValue followClusterNormal zed8HrHalfL feScore
}(hasPersonalData = 'false')

/**
 * A comb nat on of ent y and model. All f elds are requ red as t   s used as a  mcac  key.
 **/
struct Ent yW hVers on {
  1: requ red ent y.S mClusterEnt y ent y
  2: requ red ModelVers on vers on
}(hasPersonalData = 'true')

/**
 * Conta ns top K clusters w h correspond ng scores.  're represent ng clusters purely us ng  nts, and
 * om t ng t  modelVers on, s nce that  s  ncluded  n t   mcac  key.
 **/
struct TopKClustersW hScores {
  1: opt onal map< 32, Scores> topClustersByFavClusterNormal zedScore(personalDataTypeKey = ' nferred nterests')
  2: opt onal map< 32, Scores> topClustersByFollowClusterNormal zedScore(personalDataTypeKey = ' nferred nterests')
}(hasPersonalData = 'true')

/**
 * Conta ns top K text ent  es w h correspond ng scores.   're om t ng t  modelVers on,
 * s nce that  s  ncluded  n t   mcac  key.
 **/
struct TopKEnt  esW hScores {
  1: opt onal map<ent y.T etTextEnt y, Scores> topEnt  esByFavClusterNormal zedScore
  2: opt onal map<ent y.T etTextEnt y, Scores> topEnt  esByFollowClusterNormal zedScore
}(hasPersonalData = 'true')

/**
 * Conta ns top K t ets w h correspond ng scores.  're om t ng t  modelVers on,
 * s nce that  s  ncluded  n t   mcac  key.
 **/
struct TopKT etsW hScores {
  1: opt onal map< 64, Scores> topT etsByFavClusterNormal zedScore(personalDataTypeKey='T et d')
  2: opt onal map< 64, Scores> topT etsByFollowClusterNormal zedScore(personalDataTypeKey='T et d')
}(hasPersonalData = 'true')

/**
 * Conta ns FullCluster d and t  correspond ng top K t ets and scores.
 **/
struct Cluster dToTopKT etsW hScores {
  1: requ red FullCluster d cluster d
  2: requ red TopKT etsW hScores topKT etsW hScores
}(hasPersonalData = 'true')

/**
 * Conta ns a map of Model Vers on to top K clusters w h correspond ng scores.
 **/
struct Mult ModelTopKClustersW hScores {
  1: opt onal map<ModelVers on, TopKClustersW hScores> mult ModelTopKClustersW hScores
}(hasPersonalData = 'true')

/**
 * Conta ns a map of Model Vers on top K t ets w h correspond ng scores.
 **/
struct Mult ModelTopKT etsW hScores {
  1: opt onal map<ModelVers on, TopKT etsW hScores> mult ModelTopKT etsW hScores
}(hasPersonalData = 'true')
