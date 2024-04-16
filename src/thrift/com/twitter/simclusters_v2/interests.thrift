na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2. nterests
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

/**
 * All of t  scores below assu  that t  knownFor vector for each cluster  s already
 * of un  L2 norm  .e. sum of squares  s 1. 
 **/
struct UserTo nterested nClusterScores {
  // dot product of user's b nary follow vector w h knownFor vector for t  cluster
  // T P: By default, use t  score or favScore. 
  1: opt onal double followScore(personalDataType = 'CountOfFollo rsAndFollo es')

  // f rst compute followScore as def ned above
  // t n compute L2 norm of t  vector of t se scores for t  cluster
  // d v de by that.
  // essent ally t  more people are  nterested  n t  cluster, t  lo r t  score gets
  // T P: Use t  score  f y  use case needs to penal ze clusters that a lot of ot r 
  // users are also  nterested  n
  2: opt onal double followScoreClusterNormal zedOnly(personalDataType = 'CountOfFollo rsAndFollo es')

  // dot product of user's producer normal zed follow vector and knownFor vector for t  cluster
  //  .e.  ^th entry  n t  normal zed follow vector = 1.0/sqrt(number of follo rs of user  )
  // T P: Use t  score  f y  use case needs to penal ze clusters w re t  users known for
  // that cluster are popular. 
  3: opt onal double followScoreProducerNormal zedOnly(personalDataType = 'CountOfFollo rsAndFollo es')

  // f rst compute followScoreProducerNormal zedOnly
  // t n compute L2 norm of t  vector of t se scores for t  cluster
  // d v de by that.
  // essent ally t  more people are  nterested  n t  cluster, t  lo r t  score gets
  // T P: Use t  score  f y  use case needs to penal ze both clusters that a lot of ot r
  // users are  nterested  n, as  ll as clusters w re t  users known for that cluster are 
  // popular.
  4: opt onal double followScoreClusterAndProducerNormal zed(personalDataType = 'CountOfFollo rsAndFollo es')

  // dot product of user's favScoreHalfL fe100Days vector w h knownFor vector for t  cluster 
  // T P: By default, use t  score or followScore. 
  5: opt onal double favScore(personalDataType = 'Engage ntsPubl c')

  // f rst compute favScore as def ned above
  // t n compute L2 norm of t  vector of t se scores for t  cluster
  // d v de by that.
  // essent ally t  more people are  nterested  n t  cluster, t  lo r t  score gets
  // T P: Use t  score  f y  use case needs to penal ze clusters that a lot of ot r 
  // users are also  nterested  n
  6: opt onal double favScoreClusterNormal zedOnly(personalDataType = 'Engage ntsPubl c')

  // dot product of user's favScoreHalfL fe100DaysNormal zedByNe ghborFaversL2 vector w h 
  // knownFor vector for t  cluster
  // T P: Use t  score  f y  use case needs to penal ze clusters w re t  users known for
  // that cluster are popular. 
  7: opt onal double favScoreProducerNormal zedOnly(personalDataType = 'Engage ntsPubl c')

  // f rst compute favScoreProducerNormal zedOnly as def ned above
  // t n compute L2 norm of t  vector of t se scores for t  cluster
  // d v de by that.
  // essent ally t  more people are  nterested  n t  cluster, t  lo r t  score gets
  // T P: Use t  score  f y  use case needs to penal ze both clusters that a lot of ot r
  // users are  nterested  n, as  ll as clusters w re t  users known for that cluster are 
  // popular.
  8: opt onal double favScoreClusterAndProducerNormal zed(personalDataType = 'Engage ntsPubl c')

  // l st of users who're known for t  cluster as  ll as are be ng follo d by t  user.
  9: opt onal l st< 64> usersBe ngFollo d(personalDataType = 'User d')
 
  // l st of users who're known for t  cluster as  ll as  re faved at so  po nt by t  user. 
  10: opt onal l st< 64> usersThat reFaved(personalDataType = 'User d')

  // A pretty close upper bound on t  number of users who are  nterested  n t  cluster. 
  // Useful to know  f t   s a n c  commun y or a popular top c. 
  11: opt onal  32 numUsers nterested nT ClusterUpperBound

  // dot product of user's logFavScore vector w h knownFor vector for t  cluster 
  // T P: t  score  s under exper  ntat ons
  12: opt onal double logFavScore(personalDataType = 'Engage ntsPubl c')

  // f rst compute logFavScore as def ned above
  // t n compute L2 norm of t  vector of t se scores for t  cluster
  // d v de by that.
  // essent ally t  more people are  nterested  n t  cluster, t  lo r t  score gets
  // T P: t  score  s under exper  ntat ons
  13: opt onal double logFavScoreClusterNormal zedOnly(personalDataType = 'Engage ntsPubl c')

  // actual count of number of users who're known for t  cluster as  ll as are be ng follo d by t  user.
  14: opt onal  32 numUsersBe ngFollo d

  // actual count of number of users who're known for t  cluster as  ll as  re faved at so  po nt by t  user. 
  15: opt onal  32 numUsersThat reFaved
}(pers sted = 'true', hasPersonalData = 'true')

struct UserTo nterested nClusters {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red str ng knownForModelVers on
  3: requ red map< 32, UserTo nterested nClusterScores> cluster dToScores(personalDataTypeKey = ' nferred nterests')
}(pers sted="true", hasPersonalData = 'true')

struct LanguageToClusters {
  1: requ red str ng language
  2: requ red str ng knownForModelVers on
  3: requ red map< 32, UserTo nterested nClusterScores> cluster dToScores(personalDataTypeKey = ' nferred nterests')
}(pers sted="true", hasPersonalData = 'true')

struct ClustersUser s nterested n {
  1: requ red str ng knownForModelVers on
  2: requ red map< 32, UserTo nterested nClusterScores> cluster dToScores(personalDataTypeKey = ' nferred nterests')
}(pers sted = 'true', hasPersonalData = 'true')

struct UserToKnownForClusters {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red str ng knownForModelVers on
  3: requ red map< 32, UserToKnownForClusterScores> cluster dToScores(personalDataTypeKey = ' nferred nterests')
}(pers sted="true", hasPersonalData = 'true')

struct UserToKnownForClusterScores {
  1: opt onal double knownForScore
}(pers sted = 'true', hasPersonalData = 'false')

struct ClustersUser sKnownFor {
  1: requ red str ng knownForModelVers on
  2: requ red map< 32, UserToKnownForClusterScores> cluster dToScores(personalDataTypeKey = ' nferred nterests')
}(pers sted = 'true', hasPersonalData = 'true')

/** Thr ft struct for stor ng quant le bounds output by QTreeMono d  n Algeb rd */
struct Quant leBounds {
  1: requ red double lo rBound
  2: requ red double upperBound
}(pers sted = 'true', hasPersonalData = 'false')

/** Thr ft struct g v ng t  deta ls of t  d str but on of a set of doubles */
struct D str but onDeta ls {
  1: requ red double  an
  2: opt onal double standardDev at on
  3: opt onal double m n
  4: opt onal Quant leBounds p25
  5: opt onal Quant leBounds p50
  6: opt onal Quant leBounds p75
  7: opt onal Quant leBounds p95
  8: opt onal double max
}(pers sted = 'true', hasPersonalData = 'false')

/** Note that t  modelVers on  re  s spec f ed so w re outs de, spec f cally, as part of t  key */
struct ClusterNe ghbor {
  1: requ red  32 cluster d
  /** Note that followCos neS m lar y  s sa  as dot product over followScoreClusterNormal zedOnly
   * s nce those scores form a un  vector **/
  2: opt onal double followCos neS m lar y
  /** Note that favCos neS m lar y  s sa  as dot product over favScoreClusterNormal zedOnly
   * s nce those scores form a un  vector **/
  3: opt onal double favCos neS m lar y
  /** Note that logFavCos neS m lar y  s sa  as dot product over logFavScoreClusterNormal zedOnly
   * s nce those scores form a un  vector **/
  4: opt onal double logFavCos neS m lar y
}(pers sted = 'true', hasPersonalData = 'false')

/** Useful for stor ng t  l st of users known for a cluster */
struct UserW hScore {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red double score
}(pers sted="true", hasPersonalData = 'true')

// deprecated
struct EdgeCut {
  1: requ red double cutEdges
  2: requ red double totalVolu 
}(pers sted = 'true', hasPersonalData = 'false')

struct ClusterQual y {
  // deprecated
  1: opt onal EdgeCut deprecated_un  ghtedEdgeCut
  // deprecated
  2: opt onal EdgeCut deprecated_edge  ghtedCut
  // deprecated
  3: opt onal EdgeCut deprecated_nodeAndEdge  ghtedCut

  // correlat on of actual   ght of (u, v) w h  (u & v  n sa  cluster) * score(u) * score(v)
  4: opt onal double   ghtAndProductOfNodeScoresCorrelat on

  // fract on of edges stay ng  ns de cluster d v ded by total edges from nodes  n t  cluster
  5: opt onal double un  ghtedRecall

  // fract on of edge   ghts stay ng  ns de cluster d v ded by total edge   ghts from nodes  n t  cluster
  6: opt onal double   ghtedRecall

  // total edges from nodes  n t  cluster
  7: opt onal double un  ghtedRecallDenom nator

  // total edge   ghts from nodes  n t  cluster
  8: opt onal double   ghtedRecallDenom nator

  // sum of edge   ghts  ns de cluster / { #nodes * (#nodes - 1) }
  9: opt onal double relat vePrec s onNu rator

  // above d v ded by t  sum of edge   ghts  n t  total graph / { n * (n - 1) }
  10: opt onal double relat vePrec s on
}(pers sted = 'true', hasPersonalData = 'false')

/**
* T  struct  s t  value of t  ClusterDeta ls key-value dataset.
* T  key  s (modelVers on, cluster d)
**/
struct ClusterDeta ls {
  1: requ red  32 numUsersW hAnyNonZeroScore
  2: requ red  32 numUsersW hNonZeroFollowScore
  3: requ red  32 numUsersW hNonZeroFavScore
  4: opt onal D str but onDeta ls followScoreD str but onDeta ls
  5: opt onal D str but onDeta ls favScoreD str but onDeta ls
  6: opt onal l st<UserW hScore> knownForUsersAndScores
  7: opt onal l st<ClusterNe ghbor> ne ghborClusters
  // fract on of users who're known for t  cluster who're marked NSFW_User  n UserS ce
  8: opt onal double fract onKnownForMarkedNSFWUser
  // t  major languages that t  cluster's known_fors have as t  r "language" f eld  n
  // UserS ce, and t  fract ons
  9: opt onal map<str ng, double> languageToFract onDev ceLanguage
  // t  major country codes that t  cluster's known_fors have as t  r "account_country_code"
  // f eld  n UserS ce, and t  fract ons
  10: opt onal map<str ng, double> countryCodeToFract onKnownForW hCountryCode
  11: opt onal ClusterQual y qual y asuredOnS msGraph
  12: opt onal D str but onDeta ls logFavScoreD str but onDeta ls
  // fract on of languages t  cluster's known_fors produce based on what pengu n_user_languages dataset  nfers
  13: opt onal map<str ng, double> languageToFract on nferredLanguage
}(pers sted="true", hasPersonalData = 'true')

struct SampledEdge {
  1: requ red  64 follo r d(personalDataType = 'User d')
  2: requ red  64 follo e d(personalDataType = 'User d')
  3: opt onal double favWt fFollowEdge
  4: opt onal double favWt fFavEdge
  5: opt onal double followScoreToCluster
  6: opt onal double favScoreToCluster
  7: opt onal double pred ctedFollowScore
  8: opt onal double pred ctedFavScore
}(pers sted="true", hasPersonalData = 'true')

/**
* T  key  re  s (modelVers on, cluster d)
**/
struct B part eClusterQual y {
  1: opt onal double  nClusterFollowEdges
  2: opt onal double  nClusterFavEdges
  3: opt onal double favWtSumOf nClusterFollowEdges
  4: opt onal double favWtSumOf nClusterFavEdges
  5: opt onal double outgo ngFollowEdges
  6: opt onal double outgo ngFavEdges
  7: opt onal double favWtSumOfOutgo ngFollowEdges
  8: opt onal double favWtSumOfOutgo ngFavEdges
  9: opt onal double  ncom ngFollowEdges
  10: opt onal double  ncom ngFavEdges
  11: opt onal double favWtSumOf ncom ngFollowEdges
  12: opt onal double favWtSumOf ncom ngFavEdges
  13: opt onal  32  nterested nS ze
  14: opt onal l st<SampledEdge> sampledEdges
  15: opt onal  32 knownForS ze
  16: opt onal double correlat onOfFavWt fFollowW hPred ctedFollow
  17: opt onal double correlat onOfFavWt fFavW hPred ctedFav
  18: opt onal double relat vePrec s onUs ngFavWt fFav
  19: opt onal double averagePrec s onOfWholeGraphUs ngFavWt fFav
}(pers sted="true", hasPersonalData = 'true')
