package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.tweetypie.cowe.vawuestate
i-impowt c-com.twittew.tweetypie.thwiftscawa.mentionentity
i-impowt com.twittew.tweetypie.unmentions.thwiftscawa.unmentiondata

o-object unmentiondatahydwatow {
  t-type type = v-vawuehydwatow[option[unmentiondata], 😳 ctx]

  case cwass ctx(
    convewsationid: option[tweetid], XD
    m-mentions: seq[mentionentity], :3
    undewwyingtweetctx: tweetctx)
      extends t-tweetctx.pwoxy

  def appwy(): t-type = {
    vawuehydwatow.map[option[unmentiondata], 😳😳😳 ctx] { (_, ctx) =>
      v-vaw mentionedusewids: seq[usewid] = c-ctx.mentions.fwatmap(_.usewid)

      vawuestate.modified(
        s-some(unmentiondata(ctx.convewsationid, -.- option(mentionedusewids).fiwtew(_.nonempty)))
      )
    }
  }.onwyif { (_, ( ͡o ω ͡o ) ctx) =>
    ctx.tweetfiewdwequested(tweet.unmentiondatafiewd)
  }
}
