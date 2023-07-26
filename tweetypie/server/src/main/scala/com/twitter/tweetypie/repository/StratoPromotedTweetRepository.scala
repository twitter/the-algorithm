package com.twittew.tweetypie.wepositowy

impowt c-com.twittew.stitch.stitch
i-impowt c-com.twittew.stwato.cwient.fetchew
i-impowt com.twittew.tweetypie.tweetid
i-impowt com.twittew.stwato.cwient.{cwient => s-stwatocwient}

o-object stwatopwomotedtweetwepositowy {
  t-type type = tweetid => stitch[boowean]

  vaw cowumn = "tweetypie/ispwomoted.tweet"

  def appwy(cwient: s-stwatocwient): type = {
    vaw fetchew: fetchew[tweetid, (ˆ ﻌ ˆ)♡ unit, b-boowean] =
      cwient.fetchew[tweetid, (⑅˘꒳˘) b-boowean](cowumn)

    tweetid => fetchew.fetch(tweetid).map(f => f.v.getowewse(fawse))
  }
}
