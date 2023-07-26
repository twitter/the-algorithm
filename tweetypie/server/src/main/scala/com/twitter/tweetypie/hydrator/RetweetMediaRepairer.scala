package com.twittew.tweetypie
package h-hydwatow

/**
 * w-wetweets shouwd n-nyevew have t-theiw own media, -.- a-and shouwd nyevew b-be cached with a-a media
 * entity. (ˆ ﻌ ˆ)♡
 */
o-object wetweetmediawepaiwew extends mutation[tweet] {
  def appwy(tweet: tweet): option[tweet] = {
    i-if (iswetweet(tweet) && getmedia(tweet).nonempty)
      some(tweetwenses.media.set(tweet, (⑅˘꒳˘) n-nyiw))
    ewse
      n-none
  }
}
