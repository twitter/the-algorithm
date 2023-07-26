package com.twittew.fwigate.pushsewvice.modew.ibis

impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.fwigate.common.base.f1fiwstdegwee
i-impowt com.twittew.fwigate.common.base.tweetauthowdetaiws
i-impowt com.twittew.fwigate.pushsewvice.modew.pushtypes.pushcandidate
i-impowt com.twittew.utiw.futuwe

t-twait f1fiwstdegweetweetibis2hydwatowfowcandidate
    e-extends t-tweetcandidateibis2hydwatow
    w-with wankedsociawcontextibis2hydwatow {
  sewf: pushcandidate with f1fiwstdegwee with tweetauthowdetaiws =>

  o-ovewwide wazy vaw scopedstats: statsweceivew = s-statsweceivew.scope(getcwass.getsimpwename)

  ovewwide wazy vaw t-tweetmodewvawues: futuwe[map[stwing, -.- stwing]] = {
    fow {
      s-supewmodewvawues <- supew.tweetmodewvawues
      t-tweetinwinemodewvawues <- t-tweetinwineactionmodewvawue
    } yiewd {
      supewmodewvawues ++ othewmodewvawues ++ mediamodewvawue ++ t-tweetinwinemodewvawues ++ inwinevideomediamap
    }
  }
}
