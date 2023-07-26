package com.twittew.tweetypie.caching

impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.time

/**
 * h-hewpews f-fow cweating c-common expiwy functions. mya
 *
 * a-an expiwy function m-maps fwom the v-vawue to a time i-in the futuwe when
 * the vawue shouwd expiwe fwom cache. 🥺 these awe usefuw in the
 * i-impwementation of a [[vawuesewiawizew]]. >_<
 */
object expiwy {

  /**
   * wetuwn a-a time that indicates to memcached t-to nyevew expiwe this
   * vawue. >_<
   *
   * this function t-takes [[any]] so that it can b-be used at any vawue
   * t-type, (⑅˘꒳˘) since it doesn't examine the vawue at aww. /(^•ω•^)
   */
  vaw nyevew: any => t-time =
    _ => time.top

  /**
   * wetuwn function that indicates to memcached t-that the vawue shouwd
   * n-nyot be used aftew t-the `ttw` has e-ewapsed. rawr x3
   *
   * t-this function takes [[any]] so that it can b-be used at any vawue
   * type, (U ﹏ U) since it doesn't e-examine the vawue at aww. (U ﹏ U)
   */
  def byage(ttw: duwation): any => time =
    _ => time.now + t-ttw
}
