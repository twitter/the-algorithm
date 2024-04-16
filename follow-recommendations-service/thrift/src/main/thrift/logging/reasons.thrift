na space java com.tw ter.follow_recom ndat ons.logg ng.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.logg ng.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons.logg ng

// Proof based on Follow relat onsh p
struct FollowProof {
  1: requ red l st< 64> user ds(personalDataType='User d')
  2: requ red  32 num ds(personalDataType='CountOfFollo rsAndFollo es')
}(pers sted='true', hasPersonalData='true')

// S m lar to user ds  n t  context (e.g. prof le d)
struct S m larToProof {
  1: requ red l st< 64> user ds(personalDataType='User d')
}(pers sted='true', hasPersonalData='true')

// Proof based on geo locat on
struct Popular nGeoProof {
  1: requ red str ng locat on(personalDataType=' nferredLocat on')
}(pers sted='true', hasPersonalData='true')

// Proof based on ttt  nterest
struct Ttt nterestProof {
  1: requ red  64  nterest d(personalDataType='Prov ded nterests')
  2: requ red str ng  nterestD splayNa (personalDataType='Prov ded nterests')
}(pers sted='true', hasPersonalData='true')

// Proof based on top cs
struct Top cProof {
  1: requ red  64 top c d(personalDataType='Prov ded nterests')
}(pers sted='true', hasPersonalData='true')

// Proof based on custom  nterest / search quer es
struct Custom nterestProof {
  1: requ red str ng custo r nterest(personalDataType='SearchQuery')
}(pers sted='true', hasPersonalData='true')

// Proof based on t et authors
struct T etsAuthorProof {
  1: requ red l st< 64> t et ds(personalDataType='T et d')
}(pers sted='true', hasPersonalData='true')

// Proof cand date  s of dev ce follow type
struct Dev ceFollowProof {
  1: requ red bool  sDev ceFollow(personalDataType='Ot rDev ce nfo')
}(pers sted='true', hasPersonalData='true')

// Account level proof that should be attac d to each cand date
struct AccountProof {
  1: opt onal FollowProof followProof
  2: opt onal S m larToProof s m larToProof
  3: opt onal Popular nGeoProof popular nGeoProof
  4: opt onal Ttt nterestProof ttt nterestProof
  5: opt onal Top cProof top cProof
  6: opt onal Custom nterestProof custom nterestProof
  7: opt onal T etsAuthorProof t etsAuthorProof
  8: opt onal Dev ceFollowProof dev ceFollowProof

}(pers sted='true', hasPersonalData='true')

struct Reason {
  1: opt onal AccountProof accountProof  
}(pers sted='true', hasPersonalData='true')
