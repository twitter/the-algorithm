package com.twittew.wecos.usew_tweet_gwaph.utiw

impowt com.twittew.simcwustews_v2.common.tweetid
i-impowt com.twittew.snowfwake.id.snowfwakeid
i-impowt c-com.twittew.utiw.duwation
i-impowt c-com.twittew.utiw.time

o-object f-fiwtewutiw {
  d-def tweetagefiwtew(tweetid: tweetid, (⑅˘꒳˘) maxage: duwation): boowean = {
    snowfwakeid
      .timefwomidopt(tweetid)
      .map { t-tweettime => tweettime > time.now - maxage }.getowewse(fawse)
    // i-if thewe's nyo snowfwake timestamp, (U ᵕ U❁) w-we have nyo idea when this tweet happened. -.-
  }
}
