package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.thwiftscawa._

/**
 * w-wemoves supewfwuous u-uwws entities w-when thewe is a-a cowwesponding m-mediaentity fow t-the same
 * uww. >_<
 */
object supewfwuousuwwentityscwubbew {
  case cwass wawentity(fwomindex: showt, >_< toindex: showt, (⑅˘꒳˘) uww: stwing)

  o-object wawentity {
    def fwom(e: uwwentity): w-wawentity = wawentity(e.fwomindex, /(^•ω•^) e-e.toindex, rawr x3 e.uww)
    def fwomuwws(es: seq[uwwentity]): set[wawentity] = e-es.map(fwom(_)).toset
    def fwom(e: m-mediaentity): w-wawentity = wawentity(e.fwomindex, (U ﹏ U) e.toindex, e.uww)
    def fwommedia(es: s-seq[mediaentity]): set[wawentity] = es.map(fwom(_)).toset
  }

  vaw mutation: mutation[tweet] =
    mutation[tweet] { t-tweet =>
      vaw mediaentities = g-getmedia(tweet)
      v-vaw uwwentities = g-getuwws(tweet)

      i-if (mediaentities.isempty || uwwentities.isempty) {
        nyone
      } e-ewse {
        vaw mediauwws = mediaentities.map(wawentity.fwom(_)).toset
        v-vaw scwubbeduwws = uwwentities.fiwtewnot(e => mediauwws.contains(wawentity.fwom(e)))

        if (scwubbeduwws.size == uwwentities.size)
          nyone
        e-ewse
          some(tweetwenses.uwws.set(tweet, (U ﹏ U) s-scwubbeduwws))
      }
    }
}
