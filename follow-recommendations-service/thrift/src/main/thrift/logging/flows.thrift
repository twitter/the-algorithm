na space java com.tw ter.follow_recom ndat ons.logg ng.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.logg ng.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons.logg ng

struct Offl neFlowRecom ndat on {
  1: requ red  64 user d(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

struct Offl neRecom ndat onStep {
  1: requ red l st<Offl neFlowRecom ndat on> recom ndat ons
  2: requ red set< 64> follo dUser ds(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

struct Offl neFlowContext {
  1: requ red l st<Offl neRecom ndat onStep> steps
}(pers sted='true', hasPersonalData='true')
