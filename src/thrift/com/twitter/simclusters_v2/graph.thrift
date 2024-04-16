na space java com.tw ter.s mclusters_v2.thr ftjava
na space py gen.tw ter.s mclusters_v2.graph
#@na space scala com.tw ter.s mclusters_v2.thr ftscala
#@na space strato com.tw ter.s mclusters_v2

struct DecayedSums {
  // last t   t  decayed sum was updated,  n m ll s. 
  1: requ red  64 lastUpdatedT  stamp

  // a map from half l fe (spec f ed  n days) to t  decayed sum
  2: requ red map< 32, double> halfL fe nDaysToDecayedSums
}(pers sted = 'true', hasPersonalData = 'false')

struct EdgeW hDecayed  ghts {
  1: requ red  64 s ce d(personalDataType = 'User d')
  2: requ red  64 dest nat on d(personalDataType = 'User d')
  3: requ red DecayedSums   ghts
}(pers sted="true", hasPersonalData = "true")

struct Ne ghborW h  ghts {
  1: requ red  64 ne ghbor d(personalDataType = 'User d')
  2: opt onal bool  sFollo d(personalDataType = 'Follow')
  3: opt onal double followScoreNormal zedByNe ghborFollo rsL2(personalDataType = 'Engage ntsPubl c')
  4: opt onal double favScoreHalfL fe100Days(personalDataType = 'Engage ntsPubl c')
  5: opt onal double favScoreHalfL fe100DaysNormal zedByNe ghborFaversL2(personalDataType = 'Engage ntsPubl c')

  // log(favScoreHalfL fe100Days + 1)
  6: opt onal double logFavScore(personalDataType = 'Engage ntsPubl c')

  // log(favScoreHalfL fe100Days + 1) normal zed so that a user's  ncom ng   ghts have un  l2 norm
  7: opt onal double logFavScoreL2Normal zed(personalDataType = 'Engage ntsPubl c')

}(pers sted = 'true', hasPersonalData = 'true')

struct UserAndNe ghbors {
  1: requ red  64 user d(personalDataType = 'User d')
  2: requ red l st<Ne ghborW h  ghts> ne ghbors
}(pers sted="true", hasPersonalData = 'true')

struct NormsAndCounts {
  1: requ red  64 user d(personalDataType = 'User d')
  2: opt onal double follo rL2Norm(personalDataType = 'CountOfFollo rsAndFollo es')
  3: opt onal double faverL2Norm(personalDataType = 'Engage ntsPubl c')
  4: opt onal  64 follo rCount(personalDataType = 'CountOfFollo rsAndFollo es')
  5: opt onal  64 faverCount(personalDataType = 'Engage ntsPubl c')

  // sum of t    ghts on t   ncom ng edges w re so one fav'ed t  producer
  6: opt onal double fav  ghtsOnFavEdgesSum(personalDataType = 'Engage ntsPubl c')

  // sum of t  fav   ghts on all t  follo rs of t  producer
  7: opt onal double fav  ghtsOnFollowEdgesSum(personalDataType = 'Engage ntsPubl c')
  // log(favScore + 1)
  8: opt onal double logFavL2Norm(personalDataType = 'Engage ntsPubl c')

  // sum of log(favScore + 1) on t   ncom ng edges w re so one fav'ed t  producer
  9: opt onal double logFav  ghtsOnFavEdgesSum(personalDataType = 'Engage ntsPubl c')

  // sum of log(favScore + 1) on all t  follo rs of t  producer
  10: opt onal double logFav  ghtsOnFollowEdgesSum(personalDataType = 'Engage ntsPubl c')

}(pers sted="true", hasPersonalData = 'true')
