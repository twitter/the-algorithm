na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

 nclude "ads.thr ft"
 nclude "cand date_generat on_key.thr ft"
 nclude "cr_m xer.thr ft"
 nclude " tr c_tags.thr ft"
 nclude "product.thr ft"
 nclude "related_t et.thr ft"
 nclude "s ce_type.thr ft"
 nclude "uteg.thr ft"
 nclude "com/tw ter/ml/ap /data.thr ft"
 nclude "com/tw ter/s mclusters_v2/ dent f er.thr ft"

struct V TT etCand datesScr be {
  1: requ red  64 uu d (personalDataType = 'Un versallyUn que dent f erUu d') # RequestUU D - un que scr be  d for every request that co s  n. Sa  request but d fferent stages of scr be log (FetchCand date, F lter, etc) share t  sa  uu d
  2: requ red  64 user d (personalDataType = 'User d')
  3: requ red l st<V TT etCand dateScr be> cand dates
  7: requ red product.Product product
  8: requ red l st< mpressesedBucket nfo>  mpressedBuckets
} (pers sted='true', hasPersonalData = 'true')

struct V TT etCand dateScr be {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: requ red  64 author d (personalDataType = 'User d')
  3: requ red double score
  4: requ red l st< tr c_tags. tr cTag>  tr cTags
} (pers sted='true', hasPersonalData = 'true')

struct GetT etsRecom ndat onsScr be {
  1: requ red  64 uu d (personalDataType = 'Un versallyUn que dent f erUu d') # RequestUU D - un que scr be  d for every request that co s  n. Sa  request but d fferent stages of scr be log (FetchCand date, F lter, etc) share t  sa  uu d
  2: requ red  64 user d (personalDataType = 'User d')
  3: requ red Result result
  4: opt onal  64 trace d
  5: opt onal Performance tr cs performance tr cs
  6: opt onal l st< mpressesedBucket nfo>  mpressedBuckets
} (pers sted='true', hasPersonalData = 'true')

struct S ceS gnal {
  # opt onal, s nce that t  next step covers all  nfo  re
  1: opt onal  dent f er. nternal d  d
} (pers sted='true')

struct Performance tr cs {
  1: opt onal  64 latencyMs
} (pers sted='true')

struct T etCand dateW h tadata {
  1: requ red  64 t et d (personalDataType = 'T et d')
  2: opt onal cand date_generat on_key.Cand dateGenerat onKey cand dateGenerat onKey
  3: opt onal  64 author d (personalDataType = 'User d') # only for  nterleaveResult for hydrat ng tra n ng data
  4: opt onal double score # score w h respect to cand dateGenerat onKey
  5: opt onal data.DataRecord dataRecord # attach any features to t  cand date
  6: opt onal  32 numCand dateGenerat onKeys # num Cand dateGenerat onKeys generat ng t  t et d  
} (pers sted='true')

struct FetchS gnalS cesResult { 
  1: opt onal set<S ceS gnal> s gnals
} (pers sted='true')

struct FetchCand datesResult {
  1: opt onal l st<T etCand dateW h tadata> t ets
} (pers sted='true')

struct PreRankF lterResult {
  1: opt onal l st<T etCand dateW h tadata> t ets
} (pers sted='true')

struct  nterleaveResult {
  1: opt onal l st<T etCand dateW h tadata> t ets
} (pers sted='true')

struct RankResult {
  1: opt onal l st<T etCand dateW h tadata> t ets
} (pers sted='true')

struct TopLevelAp Result {
  1: requ red  64 t  stamp (personalDataType = 'Pr vateT  stamp')
  2: requ red cr_m xer.CrM xerT etRequest request
  3: requ red cr_m xer.CrM xerT etResponse response
} (pers sted='true')

un on Result {
  1: FetchS gnalS cesResult fetchS gnalS cesResult
  2: FetchCand datesResult fetchCand datesResult
  3: PreRankF lterResult preRankF lterResult
  4:  nterleaveResult  nterleaveResult
  5: RankResult rankResult
  6: TopLevelAp Result topLevelAp Result
} (pers sted='true', hasPersonalData = 'true')

struct  mpressesedBucket nfo {
  1: requ red  64 exper  nt d (personalDataType = 'Exper  nt d')
  2: requ red str ng bucketNa 
  3: requ red  32 vers on
} (pers sted='true')

############# RelatedT ets Scr be #############

struct GetRelatedT etsScr be {
  1: requ red  64 uu d (personalDataType = 'Un versallyUn que dent f erUu d') # RequestUU D - un que scr be  d for every request that co s  n. Sa  request but d fferent stages of scr be log (FetchCand date, F lter, etc) share t  sa  uu d
  2: requ red  dent f er. nternal d  nternal d
  3: requ red RelatedT etResult relatedT etResult
  4: opt onal  64 requester d (personalDataType = 'User d')
  5: opt onal  64 guest d (personalDataType = 'Guest d')
  6: opt onal  64 trace d
  7: opt onal Performance tr cs performance tr cs
  8: opt onal l st< mpressesedBucket nfo>  mpressedBuckets
} (pers sted='true', hasPersonalData = 'true')

struct RelatedT etTopLevelAp Result {
  1: requ red  64 t  stamp (personalDataType = 'Pr vateT  stamp')
  2: requ red related_t et.RelatedT etRequest request
  3: requ red related_t et.RelatedT etResponse response
} (pers sted='true')

un on RelatedT etResult {
  1: RelatedT etTopLevelAp Result relatedT etTopLevelAp Result
  2: FetchCand datesResult fetchCand datesResult
  3: PreRankF lterResult preRankF lterResult # results after seqent al f lters
  #  f later   need rankResult,   can add    re
} (pers sted='true', hasPersonalData = 'true')

############# UtegT ets Scr be #############

struct GetUtegT etsScr be {
  1: requ red  64 uu d (personalDataType = 'Un versallyUn que dent f erUu d') # RequestUU D - un que scr be  d for every request that co s  n. Sa  request but d fferent stages of scr be log (FetchCand date, F lter, etc) share t  sa  uu d
  2: requ red  64 user d (personalDataType = 'User d')
  3: requ red UtegT etResult utegT etResult
  4: opt onal  64 trace d
  5: opt onal Performance tr cs performance tr cs
  6: opt onal l st< mpressesedBucket nfo>  mpressedBuckets
} (pers sted='true', hasPersonalData = 'true')

struct UtegT etTopLevelAp Result {
  1: requ red  64 t  stamp (personalDataType = 'Pr vateT  stamp')
  2: requ red uteg.UtegT etRequest request
  3: requ red uteg.UtegT etResponse response
} (pers sted='true')

un on UtegT etResult {
  1: UtegT etTopLevelAp Result utegT etTopLevelAp Result
  2: FetchCand datesResult fetchCand datesResult
  #  f later   need rankResult,   can add    re
} (pers sted='true', hasPersonalData = 'true')

############# getAdsRecom ndat ons() Scr be #############

struct GetAdsRecom ndat onsScr be {
  1: requ red  64 uu d (personalDataType = 'Un versallyUn que dent f erUu d') # RequestUU D - un que scr be  d for every request that co s  n. Sa  request but d fferent stages of scr be log (FetchCand date, F lter, etc) share t  sa  uu d
  2: requ red  64 user d (personalDataType = 'User d')
  3: requ red AdsRecom ndat onsResult result
  4: opt onal  64 trace d
  5: opt onal Performance tr cs performance tr cs
  6: opt onal l st< mpressesedBucket nfo>  mpressedBuckets
} (pers sted='true', hasPersonalData = 'true')

struct AdsRecom ndat onTopLevelAp Result {
  1: requ red  64 t  stamp (personalDataType = 'Pr vateT  stamp')
  2: requ red ads.AdsRequest request
  3: requ red ads.AdsResponse response
} (pers sted='true')

un on AdsRecom ndat onsResult{
  1: AdsRecom ndat onTopLevelAp Result adsRecom ndat onTopLevelAp Result
  2: FetchCand datesResult fetchCand datesResult
}(pers sted='true', hasPersonalData = 'true')
