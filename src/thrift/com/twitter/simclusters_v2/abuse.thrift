na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

 nclude "embedd ng.thr ft"
 nclude "s mclusters_presto.thr ft"

/**
 * Struct that assoc ates a user w h s mcluster scores for d fferent
 *  nteract on types. T   s  ant to be used as a feature to pred ct abuse.
 *
 * T  thr ft struct  s  ant for explorat on purposes.   does not have any
 * assumpt ons about what type of  nteract ons   use or what types of scores
 *   are keep ng track of.
 **/ 
struct AdhocS ngleS deClusterScores {
  1: requ red  64 user d(personalDataType = 'User d')
  //   can make t   nteract on types have arb rary na s.  n t  product on
  // vers on of t  dataset.   should have a d fferent f eld per  nteract on
  // type so that AP  of what  s  ncluded  s more clear.
  2: requ red map<str ng, embedd ng.S mClustersEmbedd ng>  nteract onScores
}(pers sted="true", hasPersonalData = 'true')

/**
* T   s a prod vers on of t  s ngle s de features.    s  ant to be used as a value  n a key
* value store. T  pa r of  althy and un althy scores w ll be d fferent depend ng on t  use case.
*   w ll use d fferent stores for d fferent user cases. For  nstance, t  f rst  nstance that
*    mple nt w ll use search abuse reports and  mpress ons.   can bu ld stores for new values
*  n t  future.
*
* T  consu r creates t   nteract ons wh ch t  author rece ves.  For  nstance, t  consu r
* creates an abuse report for an author. T  consu r scores are related to t   nteract on creat on
* behav or of t  consu r. T  author scores are related to t  w t r t  author rece ves t se
*  nteract ons.
*
**/
struct S ngleS deUserScores {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red double consu rUn althyScore(personalDataType = 'Engage ntScore')
  3: requ red double consu r althyScore(personalDataType = 'Engage ntScore')
  4: requ red double authorUn althyScore(personalDataType = 'Engage ntScore')
  5: requ red double author althyScore(personalDataType = 'Engage ntScore')
}(pers sted="true", hasPersonalData = 'true')

/**
* Struct that assoc ates a cluster-cluster  nteract on scores for d fferent
*  nteract on types.
**/
struct AdhocCrossS mCluster nteract onScores {
  1: requ red  64 cluster d
  2: requ red l st<s mclusters_presto.ClustersScore> clusterScores
}(pers sted="true")
