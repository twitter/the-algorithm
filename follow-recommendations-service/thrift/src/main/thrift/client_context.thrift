na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

// Caller/Cl ent level spec f c context (e.g, user  d/guest  d/app  d).
struct Cl entContext {
  1: opt onal  64 user d(personalDataType='User d')
  2: opt onal  64 guest d(personalDataType='Guest d')
  3: opt onal  64 app d(personalDataType='App d')
  4: opt onal str ng  pAddress(personalDataType=' pAddress')
  5: opt onal str ng userAgent(personalDataType='UserAgent')
  6: opt onal str ng countryCode(personalDataType=' nferredCountry')
  7: opt onal str ng languageCode(personalDataType=' nferredLanguage')
  9: opt onal bool  sTwoff ce(personalDataType=' nferredLocat on')
  10: opt onal set<str ng> userRoles
  11: opt onal str ng dev ce d(personalDataType='Dev ce d')
  12: opt onal  64 guest dAds(personalDataType='Guest d')
  13: opt onal  64 guest dMarket ng(personalDataType='Guest d')
}(hasPersonalData='true')
