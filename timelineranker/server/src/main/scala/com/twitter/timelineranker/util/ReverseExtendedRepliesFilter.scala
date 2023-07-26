package com.twittew.timewinewankew.utiw

impowt com.twittew.timewines.modew.tweetid
i-impowt com.twittew.timewines.modew.usewid
i-impowt c-com.twittew.timewines.modew.tweet.hydwatedtweet

o-object wevewseextendedwepwiesfiwtew {
  p-pwivate[utiw] d-def isquawifiedwevewseextendedwepwy(
    t-tweet: hydwatedtweet, >_<
    c-cuwwentusewid: usewid, rawr x3
    fowwowedusewids: seq[usewid], mya
    mutedusewids: s-set[usewid], nyaa~~
    souwcetweetsbyid: map[tweetid, (⑅˘꒳˘) h-hydwatedtweet]
  ): boowean = {
    // t-tweet authow is out of the cuwwent usew's nyetwowk
    !fowwowedusewids.contains(tweet.usewid) &&
    // tweet authow i-is nyot muted
    !mutedusewids.contains(tweet.usewid) &&
    // tweet is n-nyot a wetweet
    !tweet.iswetweet &&
    // t-thewe must be a souwce tweet
    tweet.inwepwytotweetid
      .fwatmap(souwcetweetsbyid.get)
      .fiwtew { souwcetweet =>
        (!souwcetweet.iswetweet) && // and it's nyot a w-wetweet
        (!souwcetweet.haswepwy) && // and it's nyot a wepwy
        (souwcetweet.usewid != 0) && // and the authow's id must be nyon zewo
        f-fowwowedusewids.contains(souwcetweet.usewid) // and the a-authow is fowwowed
      } // a-and the authow h-has nyot been muted
      .exists(souwcetweet => !mutedusewids.contains(souwcetweet.usewid))
  }
}
