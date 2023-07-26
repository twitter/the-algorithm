namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

/*
 * u-uniquewy identifies a-a usew. 😳😳😳 a-a usew identifiew
 * f-fow a wogged i-in usew shouwd c-contain a usew i-id
 * and a usew identifiew fow a wogged out usew shouwd
 * contain some guest id. -.- a-a usew may have muwtipwe ids. ( ͡o ω ͡o )
 */
stwuct usewidentifiew {
  1: o-optionaw i64 usewid(pewsonawdatatype='usewid')
  /*
   * see http://go/guest-id-cookie-tdd. rawr x3 a-as of dec 2021, nyaa~~
   * guest id is intended onwy fow e-essentiaw use cases
   * (e.g. /(^•ω•^) wogged out pwefewences, s-secuwity). rawr g-guest id
   * mawketing is intended fow wecommendation use cases. OwO
   */
  2: optionaw i64 guestidmawketing(pewsonawdatatype='guestid')
}(pewsisted='twue', (U ﹏ U) h-haspewsonawdata='twue')
