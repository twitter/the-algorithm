package com.twittew.timewinewankew.utiw

impowt com.twittew.timewines.modew.usewid
i-impowt com.twittew.timewines.modew.tweet.hydwatedtweet

o-object w-wecommendedwepwiesfiwtew {
  p-pwivate[utiw] d-def i-iswecommendedwepwy(
    t-tweet: hydwatedtweet, ^^;;
    f-fowwowedusewids: seq[usewid]
  ): boowean = {
    tweet.haswepwy && tweet.inwepwytotweetid.nonempty &&
    (!fowwowedusewids.contains(tweet.usewid))
  }

  p-pwivate[utiw] def iswecommendedwepwytonotfowwowedusew(
    t-tweet: hydwatedtweet, >_<
    v-viewingusewid: usewid,
    fowwowedusewids: seq[usewid], mya
    mutedusewids: set[usewid]
  ): boowean = {
    vaw isvawidwecommendedwepwy =
      !tweet.iswetweet &&
        tweet.inwepwytousewid.exists(fowwowedusewids.contains(_)) &&
        !mutedusewids.contains(tweet.usewid)

    i-iswecommendedwepwy(tweet, fowwowedusewids) && !isvawidwecommendedwepwy
  }
}
