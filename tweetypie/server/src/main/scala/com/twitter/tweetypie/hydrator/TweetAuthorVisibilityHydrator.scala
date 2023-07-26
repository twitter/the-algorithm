package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.stitch.stitch
i-impowt com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.wepositowy._

/**
 * e-ensuwes that t-the tweet's authow a-and souwce t-tweet's authow (if wetweet) awe visibwe to the
 * viewing usew - ctx.opts.fowusewid - w-when enfowcevisibiwityfiwtewing is twue. σωσ
 * if eithew of t-these usews is not visibwe then a-a fiwtewedstate.suppwess wiww be wetuwned. OwO
 *
 * nyote: bwocking w-wewationship is nyot checked hewe, 😳😳😳 t-this means if v-viewing usew `fowusewid` is bwocked
 * by eithew the tweet's authow ow souwce t-tweet's authow, 😳😳😳 this wiww nyot fiwtew out the tweet. o.O
 */
object tweetauthowvisibiwityhydwatow {
  t-type type = vawuehydwatow[unit, ( ͡o ω ͡o ) tweetctx]

  def a-appwy(wepo: usewvisibiwitywepositowy.type): type =
    v-vawuehydwatow[unit, (U ﹏ U) t-tweetctx] { (_, (///ˬ///✿) c-ctx) =>
      vaw ids = seq(ctx.usewid) ++ c-ctx.souwceusewid
      vaw keys = ids.map(id => towepoquewy(id, >w< c-ctx))

      stitch
        .twavewse(keys)(wepo.appwy).fwatmap { wesponses =>
          vaw fs: option[fiwtewedstate.unavaiwabwe] = wesponses.fwatten.headoption

          fs match {
            c-case some(fs: fiwtewedstate.unavaiwabwe) => s-stitch.exception(fs)
            c-case nyone => v-vawuestate.stitchunmodifiedunit
          }
        }
    }.onwyif((_, rawr ctx) => ctx.opts.enfowcevisibiwityfiwtewing)

  pwivate def towepoquewy(usewid: usewid, mya ctx: tweetctx) =
    u-usewvisibiwitywepositowy.quewy(
      u-usewkey(usewid), ^^
      ctx.opts.fowusewid, 😳😳😳
      c-ctx.tweetid, mya
      c-ctx.iswetweet, 😳
      ctx.opts.isinnewquotedtweet, -.-
      s-some(ctx.opts.safetywevew))
}
