na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.s mclusters_presto
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "embedd ng.thr ft"
 nclude " dent f er.thr ft"
 nclude " nterests.thr ft"
 nclude "onl ne_store.thr ft"

/**
  * T  struct  s t  presto-compat ble "l e" vers on of t  ClusterDeta ls thr ft
  */
struct ClusterDeta lsL e {
  1: requ red onl ne_store.FullCluster d fullCluster d
  2: requ red  32 numUsersW hAnyNonZeroScore
  3: requ red  32 numUsersW hNonZeroFollowScore
  4: requ red  32 numUsersW hNonZeroFavScore
  5: requ red l st< nterests.UserW hScore> knownForUsersAndScores
}(pers sted="true", hasPersonalData = 'true')

struct Embedd ngsL e {
  1: requ red  64 ent y d
  2: requ red  32 cluster d
  3: requ red double score
}(pers sted="true", hasPersonalData = 'true')

struct S mClustersEmbedd ngW h d {
  1: requ red  dent f er.S mClustersEmbedd ng d embedd ng d
  2: requ red embedd ng.S mClustersEmbedd ng embedd ng
}(pers sted="true", hasPersonalData = 'true')

struct  nternal dEmbedd ngW h d {
  1: requ red  dent f er.S mClustersEmbedd ng d embedd ng d
  2: requ red embedd ng. nternal dEmbedd ng embedd ng
}(pers sted="true", hasPersonalData = 'true')

/**
* T  struct  s t  presto-compat ble vers on of t  fav_tfg_top c_embedd ngs
*/
struct ClustersScore {
  1: requ red  64 cluster d(personalDataType = 'Semant ccoreClass f cat on')
  2: requ red double score(personalDataType = 'Engage ntScore')
}(pers sted="true", hasPersonalData = 'true')

struct FavTfgTop cEmbedd ngs {
  1: requ red  dent f er.Top c d top c d
  2: requ red l st<ClustersScore> clusterScore
}(pers sted="true", hasPersonalData = 'true')

struct TfgTop cEmbedd ngs {
  1: requ red  dent f er.Top c d top c d
  2: requ red l st<ClustersScore> clusterScore
}(pers sted="true", hasPersonalData = 'true')

struct UserTop c  ghtedEmbedd ng {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red l st<ClustersScore> clusterScore
}(pers sted="true", hasPersonalData = 'true')
