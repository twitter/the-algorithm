na space java com.tw ter.follow_recom ndat ons.logg ng.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.logg ng.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons.logg ng

 nclude "cl ent_context.thr ft"
 nclude "debug.thr ft"
 nclude "d splay_context.thr ft"
 nclude "d splay_locat on.thr ft"
 nclude "recom ndat ons.thr ft"

struct Offl neRecom ndat onRequest {
    1: requ red cl ent_context.Offl neCl entContext cl entContext
    2: requ red d splay_locat on.Offl neD splayLocat on d splayLocat on
    3: opt onal d splay_context.Offl neD splayContext d splayContext
    4: opt onal  32 maxResults
    5: opt onal str ng cursor
    6: opt onal l st< 64> excluded ds(personalDataType='User d')
    7: opt onal bool fetchPromotedContent
    8: opt onal debug.Offl neDebugParams debugParams
}(pers sted='true', hasPersonalData='true')

struct Offl neRecom ndat onResponse {
    1: requ red l st<recom ndat ons.Offl neRecom ndat on> recom ndat ons
}(pers sted='true', hasPersonalData='true')

struct Recom ndat onLog {
    1: requ red Offl neRecom ndat onRequest request
    2: requ red Offl neRecom ndat onResponse response
    3: requ red  64 t  stampMs
}(pers sted='true', hasPersonalData='true')

struct Offl neScor ngUserRequest {
  1: requ red cl ent_context.Offl neCl entContext cl entContext
  2: requ red d splay_locat on.Offl neD splayLocat on d splayLocat on
  3: requ red l st<recom ndat ons.Offl neUserRecom ndat on> cand dates
}(pers sted='true', hasPersonalData='true')

struct Offl neScor ngUserResponse {
  1: requ red l st<recom ndat ons.Offl neUserRecom ndat on> cand dates
}(pers sted='true', hasPersonalData='true')

struct ScoredUsersLog {
  1: requ red Offl neScor ngUserRequest request
  2: requ red Offl neScor ngUserResponse response
    3: requ red  64 t  stampMs
}(pers sted='true', hasPersonalData='true')

struct Offl neRecom ndat onFlowUser tadata {
  1: opt onal  32 userS gnupAge(personalDataType = 'AgeOfAccount')
  2: opt onal str ng userState(personalDataType = 'UserState')
}(pers sted='true', hasPersonalData='true')

struct Offl neRecom ndat onFlowS gnals {
  1: opt onal str ng countryCode(personalDataType=' nferredCountry')
}(pers sted='true', hasPersonalData='true')

struct Offl neRecom ndat onFlowCand dateS ceCand dates {
  1: requ red str ng cand dateS ceNa 
  2: requ red l st< 64> cand dateUser ds(personalDataType='User d')
  3: opt onal l st<double> cand dateUserScores
}(pers sted='true', hasPersonalData='true')

struct Recom ndat onFlowLog {
  1: requ red cl ent_context.Offl neCl entContext cl entContext
  2: opt onal Offl neRecom ndat onFlowUser tadata user tadata
  3: opt onal Offl neRecom ndat onFlowS gnals s gnals
  4: requ red  64 t  stampMs
  5: requ red str ng recom ndat onFlow dent f er
  6: opt onal l st<Offl neRecom ndat onFlowCand dateS ceCand dates> f lteredCand dates
  7: opt onal l st<Offl neRecom ndat onFlowCand dateS ceCand dates> rankedCand dates
  8: opt onal l st<Offl neRecom ndat onFlowCand dateS ceCand dates> truncatedCand dates
}(pers sted='true', hasPersonalData='true')
