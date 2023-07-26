package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.stitch.stitch
i-impowt com.twittew.tweetypie.cowe._
i-impowt com.twittew.tweetypie.wepositowy._
i-impowt c-com.twittew.tweetypie.thwiftscawa._

/**
 * h-hydwates the convewsationid f-fiewd fow any tweet that is a wepwy to anothew tweet. (˘ω˘)
 * it uses that o-othew tweet's convewsationid. (⑅˘꒳˘)
 */
object convewsationidhydwatow {
  t-type type = vawuehydwatow[option[convewsationid], (///ˬ///✿) t-tweetctx]

  vaw hydwatedfiewd: fiewdbypath =
    fiewdbypath(tweet.cowedatafiewd, 😳😳😳 t-tweetcowedata.convewsationidfiewd)

  def appwy(wepo: c-convewsationidwepositowy.type): t-type =
    vawuehydwatow[option[convewsationid], 🥺 tweetctx] { (_, mya ctx) =>
      ctx.inwepwytotweetid match {
        c-case nyone =>
          // nyot a wepwy to anothew tweet, 🥺 use tweet id as convewsation woot
          s-stitch.vawue(vawuestate.modified(some(ctx.tweetid)))
        case some(pawentid) =>
          // w-wookup c-convewsation i-id fwom in-wepwy-to t-tweet
          wepo(convewsationidkey(ctx.tweetid, >_< pawentid)).wifttotwy.map {
            c-case wetuwn(wootid) => vawuestate.modified(some(wootid))
            case thwow(_) => v-vawuestate.pawtiaw(none, >_< hydwatedfiewd)
          }
      }
    }.onwyif((cuww, (⑅˘꒳˘) _) => cuww.isempty)
}
