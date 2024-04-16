na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.cluster ng
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

/**
 * Struct that represents an ordered l st of producer clusters.
 * T  l st  s  ant to be ordered by decreas ng cluster s ze.
 **/
struct OrderedClustersAnd mbers {
  1: requ red l st<set< 64>> orderedClustersAnd mbers (personalDataType = 'User d')
  // work around BQ not support ng nested struct such as l st<set>
  2: opt onal l st<Cluster mbers> orderedClustersAnd mbersStruct (personalDataType = 'User d')
}(pers sted = 'true', hasPersonalData = 'true')

struct Cluster mbers {
  1: requ red set< 64> cluster mbers (personalDataType = 'User d')
}(pers sted = 'true', hasPersonalData = 'true')
