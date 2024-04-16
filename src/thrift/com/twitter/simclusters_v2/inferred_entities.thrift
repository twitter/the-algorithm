na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2. nferred_ent  es
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

// T  S mClusters type   use to  nfer ent y  nterests about a user
// Currently used for S mClusters Compl ance to store a user's  nferred  nterests

 nclude "onl ne_store.thr ft"

enum ClusterType {
  KnownFor        = 1,
   nterested n    = 2
}(pers sted = 'true', hasPersonalData = 'false')

struct S mClustersS ce {
  1: requ red ClusterType clusterType
  2: requ red onl ne_store.ModelVers on modelVers on
}(pers sted = 'true', hasPersonalData = 'false')

// T  s ce of ent  es   use to  nfer ent y  nterests about a user
enum Ent yS ce {
  S mClusters20M145KDec11Ent yEmbedd ngsByFavScore = 1, // deprecated
  S mClusters20M145KUpdatedEnt yEmbedd ngsByFavScore = 2, // deprecated
  UTTAccountRecom ndat ons = 3 # dataset bu lt by Onboard ng team
  S mClusters20M145K2020Ent yEmbedd ngsByFavScore = 4
}(pers sted = 'true', hasPersonalData = 'false')

struct  nferredEnt y {
  1: requ red  64 ent y d(personalDataType = 'Semant ccoreClass f cat on')
  2: requ red double score(personalDataType = 'Engage ntScore')
  3: opt onal S mClustersS ce s mclusterS ce
  4: opt onal Ent yS ce ent yS ce
}(pers sted = 'true', hasPersonalData = 'true')

struct S mClusters nferredEnt  es {
  1: requ red l st< nferredEnt y> ent  es
}(pers sted = 'true', hasPersonalData = 'true')
