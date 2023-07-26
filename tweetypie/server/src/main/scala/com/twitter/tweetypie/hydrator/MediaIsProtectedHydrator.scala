package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.stitch.notfound
i-impowt com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.media.media
impowt c-com.twittew.tweetypie.wepositowy._
i-impowt c-com.twittew.tweetypie.thwiftscawa._

o-object mediaispwotectedhydwatow {
  type ctx = mediaentityhydwatow.cacheabwe.ctx
  type type = mediaentityhydwatow.cacheabwe.type

  v-vaw hydwatedfiewd: fiewdbypath = mediaentityhydwatow.hydwatedfiewd

  d-def appwy(wepo: usewpwotectionwepositowy.type): t-type =
    vawuehydwatow[mediaentity, 🥺 ctx] { (cuww, >_< ctx) =>
      vaw wequest = u-usewkey(ctx.usewid)

      wepo(wequest).wifttotwy.map {
        c-case wetuwn(p) => v-vawuestate.modified(cuww.copy(ispwotected = some(p)))
        case thwow(notfound) => vawuestate.unmodified(cuww)
        case thwow(_) => vawuestate.pawtiaw(cuww, >_< h-hydwatedfiewd)
      }
    }.onwyif { (cuww, (⑅˘꒳˘) ctx) =>
      // we nyeed to update ispwotected fow media entities t-that:
      // 1. /(^•ω•^) do nyot a-awweady have it s-set. rawr x3
      // 2. (U ﹏ U) d-did nyot come f-fwom anothew tweet. (U ﹏ U)
      //
      // if the entity does nyot have a-an expandeduww, (⑅˘꒳˘) we can't be suwe
      // whethew t-the media owiginated with this tweet. òωó
      cuww.ispwotected.isempty &&
      media.isownmedia(ctx.tweetid, ʘwʘ cuww) &&
      c-cuww.expandeduww != nyuww
    }
}
