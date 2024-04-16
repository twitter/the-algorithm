na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

/*
 * Un quely  dent f es a user. A user  dent f er
 * for a logged  n user should conta n a user  d
 * and a user  dent f er for a logged out user should
 * conta n so  guest  d. A user may have mult ple  ds.
 */
struct User dent f er {
  1: opt onal  64 user d(personalDataType='User d')
  /*
   * See http://go/guest- d-cook e-tdd. As of Dec 2021,
   * guest  d  s  ntended only for essent al use cases
   * (e.g. logged out preferences, secur y). Guest  d
   * market ng  s  ntended for recom ndat on use cases.
   */
  2: opt onal  64 guest dMarket ng(personalDataType='Guest d')
}(pers sted='true', hasPersonalData='true')
