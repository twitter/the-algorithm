package com.twittew.tweetypie
package c-cowe

impowt c-com.twittew.featuweswitches.v2.featuweswitchwesuwts
i-impowt com.twittew.tweetypie.thwiftscawa._

o-object tweetdata {
  o-object wenses {
    v-vaw tweet: w-wens[tweetdata, mya t-tweet] = wens[tweetdata, ʘwʘ tweet](_.tweet, (˘ω˘) _.copy(_))

    vaw suppwess: wens[tweetdata, (U ﹏ U) option[fiwtewedstate.suppwess]] =
      wens[tweetdata, ^•ﻌ•^ option[fiwtewedstate.suppwess]](
        _.suppwess, (˘ω˘)
        (td, :3 s-suppwess) => td.copy(suppwess = suppwess)
      )

    v-vaw souwcetweetwesuwt: w-wens[tweetdata, ^^;; option[tweetwesuwt]] =
      wens[tweetdata, 🥺 option[tweetwesuwt]](
        _.souwcetweetwesuwt, (⑅˘꒳˘)
        (td, nyaa~~ s-souwcetweetwesuwt) => td.copy(souwcetweetwesuwt = s-souwcetweetwesuwt)
      )

    v-vaw quotedtweetwesuwt: wens[tweetdata, :3 option[quotedtweetwesuwt]] =
      wens[tweetdata, ( ͡o ω ͡o ) option[quotedtweetwesuwt]](
        _.quotedtweetwesuwt, mya
        (td, q-quotedtweetwesuwt) => td.copy(quotedtweetwesuwt = quotedtweetwesuwt)
      )

    vaw cacheabwetweetwesuwt: wens[tweetdata, (///ˬ///✿) o-option[tweetwesuwt]] =
      wens[tweetdata, (˘ω˘) o-option[tweetwesuwt]](
        _.cacheabwetweetwesuwt, ^^;;
        (td, (✿oωo) c-cacheabwetweetwesuwt) => t-td.copy(cacheabwetweetwesuwt = c-cacheabwetweetwesuwt)
      )

    vaw tweetcounts: wens[tweetdata, (U ﹏ U) o-option[statuscounts]] =
      wens[tweetdata, -.- option[statuscounts]](
        _.tweet.counts, ^•ﻌ•^
        (td, t-tweetcounts) => td.copy(tweet = td.tweet.copy(counts = tweetcounts))
      )
  }

  def fwomcachedtweet(cachedtweet: cachedtweet, rawr c-cachedat: time): tweetdata =
    t-tweetdata(
      t-tweet = c-cachedtweet.tweet, (˘ω˘)
      compwetedhydwations = cachedtweet.compwetedhydwations.toset, nyaa~~
      cachedat = s-some(cachedat), UwU
      isbouncedeweted = c-cachedtweet.isbouncedeweted.contains(twue)
    )
}

/**
 * encapsuwates a-a tweet a-and some hydwation metadata in t-the hydwation pipewine. :3
 *
 * @pawam cachedat if t-the tweet was wead fwom cache, `cachedat` contains t-the time at which
 * the tweet w-was wwitten to cache. (⑅˘꒳˘)
 */
case c-cwass tweetdata(
  t-tweet: tweet, (///ˬ///✿)
  suppwess: option[fiwtewedstate.suppwess] = nyone, ^^;;
  compwetedhydwations: set[hydwationtype] = set.empty, >_<
  cachedat: option[time] = nyone, rawr x3
  s-souwcetweetwesuwt: o-option[tweetwesuwt] = nyone, /(^•ω•^)
  q-quotedtweetwesuwt: o-option[quotedtweetwesuwt] = n-nyone, :3
  cacheabwetweetwesuwt: option[tweetwesuwt] = nyone, (ꈍᴗꈍ)
  stowedtweetwesuwt: o-option[stowedtweetwesuwt] = nyone, /(^•ω•^)
  featuweswitchwesuwts: option[featuweswitchwesuwts] = nyone, (⑅˘꒳˘)
  // the isbouncedeweted fwag i-is onwy used when weading fwom a-an undewwying
  // t-tweet wepo a-and caching wecowds fow nyot-found t-tweets. ( ͡o ω ͡o ) it onwy e-exists
  // as a-a fwag on tweetdata t-to mawshaw bounce-deweted thwough the wayewed
  // t-twansfowming c-caches injected i-into cachingtweetwepositowy, òωó u-uwtimatewy
  // s-stowing this fwag in thwift on cachedtweet. (⑅˘꒳˘)
  //
  // duwing t-tweet hydwation, XD tweetdata.isbouncedeweted is unused and
  // shouwd awways be fawse. -.-
  isbouncedeweted: b-boowean = fawse) {

  def addhydwated(fiewdids: set[hydwationtype]): t-tweetdata =
    c-copy(compwetedhydwations = c-compwetedhydwations ++ fiewdids)

  def t-tocachedtweet: cachedtweet =
    c-cachedtweet(
      t-tweet = tweet, :3
      compwetedhydwations = compwetedhydwations, nyaa~~
      isbouncedeweted = if (isbouncedeweted) some(twue) ewse n-nyone
    )
}
