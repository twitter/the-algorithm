/*
 * T  f le def nes add  onal thr ft objects that should be spec f ed  n FRS request for context of recom ndat on, spec f cally t  prev ous recom ndat ons / new  nteract ons  n an  nteract ve flow (ser es of follow steps). T se typ cally are sent from OCF
 */

na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

struct FlowRecom ndat on {
  1: requ red  64 user d(personalDataType='User d')
}(hasPersonalData='true')

struct Recom ndat onStep {
  1: requ red l st<FlowRecom ndat on> recom ndat ons
  2: requ red set< 64> follo dUser ds(personalDataType='User d')
}(hasPersonalData='true')

struct FlowContext {
  1: requ red l st<Recom ndat onStep> steps
}(hasPersonalData='true')
