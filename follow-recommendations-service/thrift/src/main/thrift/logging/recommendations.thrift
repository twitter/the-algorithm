na space java com.tw ter.follow_recom ndat ons.logg ng.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.logg ng.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons.logg ng

 nclude "com/tw ter/ads/adserver/adserver_common.thr ft"
 nclude "reasons.thr ft"
 nclude "track ng.thr ft"
 nclude "scor ng.thr ft"

// Offl ne equal of UserRecom ndat on
struct Offl neUserRecom ndat on {
    1: requ red  64 user d(personalDataType='User d')
    // reason for t  suggest ons, eg: soc al context
    2: opt onal reasons.Reason reason
    // present  f    s a promoted account
    3: opt onal adserver_common.Ad mpress on ad mpress on
  // track ng token (unser al zed) for attr but on
  4: opt onal track ng.Track ngToken track ngToken
    // scor ng deta ls
    5: opt onal scor ng.Scor ngDeta ls scor ngDeta ls
}(pers sted='true', hasPersonalData='true')

// Offl ne equal of Recom ndat on
un on Offl neRecom ndat on {
    1: Offl neUserRecom ndat on user
}(pers sted='true', hasPersonalData='true')
