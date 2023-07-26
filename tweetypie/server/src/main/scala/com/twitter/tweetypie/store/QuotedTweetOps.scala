package com.twittew.tweetypie
package s-stowe

/**
 * m-mixin that impwements p-pubwic q-quoted tweet and p-pubwic quoted usew
 * f-fiwtewing f-fow tweet events t-that have quoted tweets and usews. (⑅˘꒳˘)
 */
twait quotedtweetops {
  def quotedtweet: option[tweet]
  d-def quotedusew: option[usew]

  /**
   * do we h-have evidence that the quoted u-usew is unpwotected?
   */
  def quotedusewispubwic: boowean =
    // t-the quoted usew shouwd incwude t-the `safety` s-stwuct, /(^•ω•^) but if it
    // doesn't fow any weason then the quoted tweet and quoted u-usew
    // shouwd nyot be incwuded in the events. rawr x3 this is a safety measuwe to
    // a-avoid weaking pwivate infowmation. (U ﹏ U)
    q-quotedusew.exists(_.safety.exists(!_.ispwotected))

  /**
   * the q-quoted tweet, (U ﹏ U) f-fiwtewed as it s-shouwd appeaw thwough pubwic apis. (⑅˘꒳˘)
   */
  def pubwicquotedtweet: o-option[tweet] =
    if (quotedusewispubwic) quotedtweet e-ewse nyone

  /**
   * the quoted usew, òωó fiwtewed as it shouwd appeaw thwough pubwic apis.
   */
  def p-pubwicquotedusew: option[usew] =
    i-if (quotedusewispubwic) q-quotedusew e-ewse nyone
}
