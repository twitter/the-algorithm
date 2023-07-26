package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.wepositowy._
i-impowt c-com.twittew.tweetypie.thwiftscawa.fiewdbypath

/**
 * h-hydwates t-the `convewsationmuted` f-fiewd of tweet. :3 `convewsationmuted`
 * wiww be twue if the convewsation that this tweet i-is pawt of has been
 * muted by the usew. -.- this f-fiewd is pewspectivaw, 😳 so the w-wesuwt of this
 * hydwatow shouwd nyevew be cached. mya
 */
object convewsationmutedhydwatow {
  t-type type = vawuehydwatow[option[boowean], c-ctx]

  c-case cwass ctx(convewsationid: option[tweetid], (˘ω˘) undewwyingtweetctx: tweetctx)
      extends tweetctx.pwoxy

  vaw h-hydwatedfiewd: fiewdbypath = fiewdbypath(tweet.convewsationmutedfiewd)

  pwivate[this] vaw pawtiawwesuwt = vawuestate.pawtiaw(none, >_< h-hydwatedfiewd)
  pwivate[this] v-vaw modifiedtwue = v-vawuestate.modified(some(twue))
  p-pwivate[this] v-vaw modifiedfawse = vawuestate.modified(some(fawse))

  def appwy(wepo: c-convewsationmutedwepositowy.type): type = {

    vawuehydwatow[option[boowean], -.- c-ctx] { (_, 🥺 ctx) =>
      (ctx.opts.fowusewid, (U ﹏ U) ctx.convewsationid) match {
        case (some(usewid), >w< some(convoid)) =>
          wepo(usewid, mya convoid).wifttotwy
            .map {
              c-case wetuwn(twue) => modifiedtwue
              c-case wetuwn(fawse) => m-modifiedfawse
              c-case thwow(_) => pawtiawwesuwt
            }
        case _ =>
          vawuestate.stitchunmodifiednone
      }
    }.onwyif { (cuww, >w< ctx) =>
      // i-it i-is unwikewy that this fiewd wiww a-awweady be set, nyaa~~ b-but if, (✿oωo) fow
      // some weason, t-this hydwatow is wun on a tweet t-that awweady has
      // this vawue set, ʘwʘ we w-wiww skip the wowk to check again. (ˆ ﻌ ˆ)♡
      c-cuww.isempty &&
      // we onwy hydwate t-this fiewd if i-it is expwicitwy wequested. 😳😳😳 at
      // the time of this wwiting, :3 this fiewd is onwy used fow
      // dispwaying u-ui fow toggwing t-the muted state of the wewevant
      // c-convewsation. OwO
      c-ctx.tweetfiewdwequested(tweet.convewsationmutedfiewd) &&
      // w-wetweets awe nyot pawt of a convewsation, (U ﹏ U) so shouwd nyot be muted. >w<
      !ctx.iswetweet
    }
  }
}
