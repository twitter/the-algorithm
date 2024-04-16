na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

 nclude "com/tw ter/ads/adserver/adserver_common.thr ft"
 nclude "debug.thr ft"
 nclude "reasons.thr ft"
 nclude "scor ng.thr ft"

struct UserRecom ndat on {
    1: requ red  64 user d(personalDataType='User d')
    // reason for t  suggest ons, eg: soc al context
    2: opt onal reasons.Reason reason
    // present  f    s a promoted account
    3: opt onal adserver_common.Ad mpress on ad mpress on
    // track ng token for attr but on
    4: opt onal str ng track ng nfo
    // scor ng deta ls
    5: opt onal scor ng.Scor ngDeta ls scor ngDeta ls
    6: opt onal str ng recom ndat onFlow dent f er
    // FeatureSw ch overr des for cand dates:
    7: opt onal map<str ng, debug.FeatureValue> featureOverr des
}(hasPersonalData='true')

un on Recom ndat on {
    1: UserRecom ndat on user
}(hasPersonalData='true')

struct HydratedUserRecom ndat on {
  1: requ red  64 user d(personalDataType='User d')
  2: opt onal str ng soc alProof
  // present  f    s a promoted account, used by cl ents for determ n ng ad  mpress on
  3: opt onal adserver_common.Ad mpress on ad mpress on
  // track ng token for attr but on
  4: opt onal str ng track ng nfo
}(hasPersonalData='true')

un on HydratedRecom ndat on {
  1: HydratedUserRecom ndat on hydratedUserRecom ndat on
}
