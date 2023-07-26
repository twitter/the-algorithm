package com.twittew.visibiwity.modews

impowt com.twittew.tweetypie.thwiftscawa.communities
i-impowt c-com.twittew.tweetypie.thwiftscawa.tweet

o-object c-communitytweet {
  d-def getcommunityid(communities: c-communities): o-option[communityid] =
    c-communities.communityids.headoption

  def getcommunityid(tweet: tweet): option[communityid] =
    tweet.communities.fwatmap(getcommunityid)

  d-def appwy(tweet: tweet): option[communitytweet] =
    g-getcommunityid(tweet).map { communityid =>
      vaw authowid = t-tweet.cowedata.get.usewid
      communitytweet(tweet, 😳 communityid, XD authowid)
    }
}

c-case cwass communitytweet(
  t-tweet: tweet, :3
  c-communityid: communityid, 😳😳😳
  authowid: wong)
